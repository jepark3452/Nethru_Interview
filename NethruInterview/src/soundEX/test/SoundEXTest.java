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
	}
	
}
