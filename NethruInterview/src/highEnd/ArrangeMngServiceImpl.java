/**
 * 
 */
package com.tmonet.srv.hetms.arrmng.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.srv.base.BaseService;
import com.tmonet.srv.base.util.SeedCrypt;
import com.tmonet.srv.base.util.StringUtils;
import com.tmonet.srv.hetms.arrmng.dao.ArrangeMngDao;
import com.tmonet.srv.hetms.resmng.dao.ResUseMngDao;
import com.tmonet.srv.hetms.sysmng.service.CommonMngService;

/**
 * <pre>
 * - 배차관리
 * </pre>
 */
@Service
public class ArrangeMngServiceImpl extends BaseService implements ArrangeMngService {
	
	@Autowired
	private ArrangeMngDao arrMngDao;
	
	@Autowired
	private ResUseMngDao resUseMngDao;
	
	@Autowired
	private CommonMngService commonMngService;

	@Override
	public int arrangeUpdate(HashMap<String, String> map) throws SQLException {
		String temp_no = null;
		HashMap<String, String> mstTempMap = null;
		String title = null;
		String msg = null;
		int result = arrMngDao.arrangeUpdate(map);	// 배차정보 변경
		
		if(result > 0) {

			map.put("res_num",  String.valueOf(map.get("RESERVE_NUM")));
			HashMap<String, String> msgMap = resUseMngDao.getReservemngDetail(map);
			log.debug("### [배차정보 변경 --> 고객한테 문자발송]msgMap---> " + msgMap.toString());
			log.debug("### [msgMap.RESERVE_STATE ---> " + msgMap.get("RESERVE_STATE"));
			
			// 현재 예약상태가 배차완료(RESB03)인 경우, 고객에게 배차변경안내(SMS004) 문자를 발송
			if(msgMap.get("RESERVE_STATE").equals("RESB03")){	
			
				temp_no = "SMS004";
				mstTempMap = commonMngService.getMsgTemplate(temp_no);
				title = mstTempMap.get("TITLE");
				msg = mstTempMap.get("MSG");
				msg = StringUtils.replace(msg, "#USER_NAME#", String.valueOf(msgMap.get("RESERVE_NAME")));
				msg = StringUtils.replace(msg, "#START_DT#", String.valueOf(msgMap.get("RES_START_DT")));
				msg = StringUtils.replace(msg, "#TAKE_POS_NAME#", String.valueOf(msgMap.get("RES_TAKE_POS_NAME")));
				msg = StringUtils.replace(msg, "#GETOFF_POS_NAME#", String.valueOf(msgMap.get("RES_GETOFF_POS_NAME")));
				msg = StringUtils.replace(msg, "#CAR_TYPE#", String.valueOf(msgMap.get("CAR_TYPE_NM")));
				msg = StringUtils.replace(msg, "#CAR_COLOR#", String.valueOf(msgMap.get("CAR_COLOR_NM")));
				msg = StringUtils.replace(msg, "#LANGUAGE_TYPE#", String.valueOf(msgMap.get("FOREIGN_TYPE_NM")));
				msg = StringUtils.replace(msg, "#DRIVER_NAME#", String.valueOf(map.get("driver_name")));
				msg = StringUtils.replace(msg, "#DRIVER_PHONE#", String.valueOf(map.get("driver_phone_num")));
				msg = StringUtils.replace(msg, "#CAR_NO#", String.valueOf(map.get("car_num")));
				
				log.debug("배차변경(고객) 문자 msg: " + msg);
				msgMap.put("reservphone", SeedCrypt.decrypt(msgMap.get("EN_RESERVE_PHONE_NUM").toString()));
				commonMngService.insertSms(temp_no, "MSGA01", String.valueOf(msgMap.get("reservphone")), title, msg, "Guest", String.valueOf(msgMap.get("RESERVE_NAME")), map.get("admId"));
			} else {
				
				temp_no = "SMS003";
				mstTempMap = commonMngService.getMsgTemplate(temp_no);
				title = mstTempMap.get("TITLE");
				msg = mstTempMap.get("MSG");
				msg = StringUtils.replace(msg, "#USER_NAME#", String.valueOf(msgMap.get("RESERVE_NAME")));
				msg = StringUtils.replace(msg, "#TAKE_POS_NAME#", String.valueOf(msgMap.get("RES_TAKE_POS_NAME")));
				msg = StringUtils.replace(msg, "#GETOFF_POS_NAME#", String.valueOf(msgMap.get("RES_GETOFF_POS_NAME")));
				msg = StringUtils.replace(msg, "#START_DT#", String.valueOf(msgMap.get("RES_START_DT")));
				msg = StringUtils.replace(msg, "#CAR_TYPE#", String.valueOf(msgMap.get("CAR_TYPE_NM")));
				msg = StringUtils.replace(msg, "#CAR_COLOR#", String.valueOf(msgMap.get("CAR_COLOR_NM")));
				msg = StringUtils.replace(msg, "#DRIVER_NAME#", String.valueOf(map.get("driver_name")));
				msg = StringUtils.replace(msg, "#DRIVER_PHONE#", String.valueOf(map.get("driver_phone_num")));
				msg = StringUtils.replace(msg, "#CAR_NO#", String.valueOf(map.get("car_num")));
				
				log.debug("배차완료(고객) 문자 msg: " + msg);
				msgMap.put("reservphone", SeedCrypt.decrypt(msgMap.get("EN_RESERVE_PHONE_NUM").toString()));
				commonMngService.insertSms(temp_no, "MSGA01", String.valueOf(msgMap.get("reservphone")), title, msg, "Guest", String.valueOf(msgMap.get("RESERVE_NAME")), map.get("admId"));
			}
			
			map.put("reserve_state", "RESB03");
			resUseMngDao.updateReserveState(map);	// 예약상태를 배차완료로 변경
			log.debug("### [배차확정 --> 배차완료(RESB03) --> 기사한테 문자발송]paramMap---> " + map.toString());
			
			/*SMS005	기사에 고객안내(고객 배정 완료시)	MSGA01	하이엔 고급택시 안내입니다.	#DRIVER_NAME# PD님 하이엔 고급택시 예약고객 배정 안내입니다.
				[예약시간] #START_DT#
				[승차위치] #TAKE_POS_NAME#
				[하차위치] #GETOFF_POS_NAME#
				위 예약의 기사님으로 배정되셨습니다.
				[전화번호] #USER_PHONE_NUM#
				[요금] #FARE#원 (하이엔 결제예정 / 승객께 받지마세요)	--> FARE3(이용요금)으로 발송한다.
				* 미팅시간 30분전 승객분께 꼭 전화주세요.
			
				항상 저희 하이엔과 함께해 주셔서 감사드립니다.*/
			temp_no = "SMS005";
			mstTempMap = commonMngService.getMsgTemplate(temp_no);
			title = mstTempMap.get("TITLE");
			msg = mstTempMap.get("MSG");
			msg = StringUtils.replace(msg, "#DRIVER_NAME#", String.valueOf(map.get("driver_name")));
			msg = StringUtils.replace(msg, "#START_DT#", String.valueOf(msgMap.get("RES_START_DT")));
			msg = StringUtils.replace(msg, "#TAKE_POS_NAME#", String.valueOf(msgMap.get("RES_TAKE_POS_NAME")));
			msg = StringUtils.replace(msg, "#GETOFF_POS_NAME#", String.valueOf(msgMap.get("RES_GETOFF_POS_NAME")));
			msg = StringUtils.replace(msg, "#USER_PHONE_NUM#", String.valueOf(msgMap.get("reservphone")));
			msg = StringUtils.replace(msg, "#FARE#", StringUtils.toNumFormat(String.valueOf(msgMap.get("FARE3"))));
			
			log.debug("배차완료(기사) 문자 msg: " + msg);
			commonMngService.insertSms(temp_no, "MSGA01", String.valueOf(map.get("driver_phone_num")), title, msg, "Guest", String.valueOf(map.get("driver_name")), map.get("admId"));
		}
		
		return result;
	}
	
}
