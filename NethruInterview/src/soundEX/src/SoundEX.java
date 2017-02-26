package soundEX.src;

public class SoundEX {

	public String convertName(String inputName) {
		String result = "";
		
		result += inputName.charAt(0);
		for(int i=1; i < inputName.length(); i++) {
			result += getCode(inputName.charAt(i));
		}
		
		return result;
	}
	
	public String getCode(char ch) {
		String result = "";
		
		switch (ch) {
		case 'b':
		case 'f':
		case 'p':
		case 'v':
			result = "1";
			break;
			
		case 'c':
		case 'g':
		case 'j':
		case 'k':
		case 'q':
		case 's':
		case 'x':
		case 'z':
			result = "2";
			break;
			
		case 'd':
		case 't':
			result = "3";
			break;
			
		case 'l':
			result = "4";
			break;
			
		case 'm':
		case 'n':
			result = "5";
			break;
			
		case 'r':
			result = "6";
			break;
		
		// 모음처리
		case 'a':
		case 'e':
		case 'i':
		case 'o':
		case 'u':
			result = "a";
			break;
		
		// h,w 처리
		case 'h':
		case 'w':
			result = "h";
			break;
		}
		
		return result;
	}

}
