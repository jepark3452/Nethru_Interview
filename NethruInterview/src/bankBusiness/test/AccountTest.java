package bankBusiness.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import bankBusiness.src.Account;

public class AccountTest {
	private Account account;

	@Before
	public void setUp() {
		account = new Account(10000);
	}
	
	@Test
	public void testAccount() throws Exception {
		/* 생성자 테스트 */
	}
	
	@Test
	public void testGetBalance() throws Exception {
		/*if(account.getBalance() != 10000) {
			fail("getBalance() => " + account.getBalance() );
		}*/
		assertEquals("10000원으로 계좌 생성 후 잔고 조회", 10000, account.getBalance());
		
		account = new Account(1000);
		assertEquals("1000원으로 계좌 생성 후 잔고 조회", 1000, account.getBalance());
		
		account = new Account(0);
		assertEquals("0원으로 계좌 생성 후 잔고 조회", 0, account.getBalance());
	}
	
	@Test
	public void testDeposit() throws Exception {
		account.deposit(1000);
		assertEquals("10000원으로 계좌 생성 후, 1000원 입금", 11000, account.getBalance());
	}
	
	@Test
	public void testWithdraw() throws Exception {
		account.withdraw(1000);
		assertEquals("10000원으로 계좌 생성 후, 1000원 출금", 9000, account.getBalance());
	}
}
