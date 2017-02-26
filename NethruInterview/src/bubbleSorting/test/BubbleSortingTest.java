package bubbleSorting.test;

import static org.junit.Assert.*;

import org.junit.Test;

import bubbleSorting.src.BubbleSorting;

public class BubbleSortingTest {
	private BubbleSorting bubbleSorting = new BubbleSorting();
	
	public void setUp() {
		bubbleSorting = new BubbleSorting();
	}
	
	@Test
	public void testSortingTwo() throws Exception {
		assertArrayEquals(new int[]{0,1}, bubbleSorting.sort(new int[]{0,1}));
		assertArrayEquals(new int[]{0,1}, bubbleSorting.sort(new int[]{1,0}));
	}
	
	@Test
	public void testSortingThree() throws Exception {
		assertArrayEquals(new int[]{3, 5, 8}, bubbleSorting.sort(new int[]{3, 5, 8}));
		assertArrayEquals(new int[]{3, 5, 8}, bubbleSorting.sort(new int[]{3, 8, 5}));
		assertArrayEquals(new int[]{3, 5, 8}, bubbleSorting.sort(new int[]{5, 3, 8}));
		assertArrayEquals(new int[]{3, 5, 8}, bubbleSorting.sort(new int[]{5, 8, 3}));
		assertArrayEquals(new int[]{3, 5, 8}, bubbleSorting.sort(new int[]{8, 3, 5}));
		assertArrayEquals(new int[]{3, 5, 8}, bubbleSorting.sort(new int[]{8, 5, 3}));
	}
	
	@Test
	public void testSortingFour() throws Exception {
		assertArrayEquals(new int[]{1, 3, 5, 8}, bubbleSorting.sort(new int[]{1, 3, 5, 8}));
		assertArrayEquals(new int[]{1, 3, 5, 8}, bubbleSorting.sort(new int[]{3, 1, 5, 8}));
		assertArrayEquals(new int[]{1, 3, 5, 8}, bubbleSorting.sort(new int[]{5, 3, 1, 8}));
		assertArrayEquals(new int[]{1, 3, 5, 8}, bubbleSorting.sort(new int[]{8, 3, 5, 1}));
	}
}
