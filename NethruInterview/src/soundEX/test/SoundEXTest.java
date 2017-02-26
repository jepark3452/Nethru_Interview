package soundEX.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import soundEX.src.SoundEX;

public class SoundEXTest {
	private SoundEX se;
	
	@Before
	public void setUp() {
		se = new SoundEX();
	}
	
	@Test
	public void testConvertNameOne() throws Exception {
		assertEquals("a", se.convertName("a"));
		assertEquals("b", se.convertName("b"));
		assertEquals("c", se.convertName("c"));
	}
	
	@Test
	public void testConvertNameTwo() throws Exception {
		assertEquals("a1", se.convertName("ab"));
		assertEquals("a2", se.convertName("ac"));
		assertEquals("a3", se.convertName("ad"));
		assertEquals("aa", se.convertName("aa"));	
	}
	
	@Test
	public void testConvertNameThree() throws Exception {
		assertEquals("a12", se.convertName("abc"));
		assertEquals("a1a", se.convertName("aba"));
		assertEquals("aaa", se.convertName("aaa"));
		assertEquals("ahh", se.convertName("ahw"));
	}
	
	@Test
	public void testRemoveDuplication() throws Exception {
		assertEquals("a1213", se.removeDuplication("a1122113"));
	}
	
}
