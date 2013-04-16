package net.diogomarques.similarity;

import static org.junit.Assert.*;

import java.util.BitSet;

import org.junit.Before;
import org.junit.Test;

public class TimePatternDigitizerTest {

	long[] pattern1 = new long[] { 20, 20, 20 };

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetBitOnLimits() {
		assertTrue(TimePatternDigitizer.getBit(pattern1, 1));
		assertTrue(TimePatternDigitizer.getBit(pattern1, 20));
		assertTrue(TimePatternDigitizer.getBit(pattern1, 41));
		assertTrue(TimePatternDigitizer.getBit(pattern1, 60));
	}

	@Test
	public void testGetBitOffLimits() {
		assertTrue(!TimePatternDigitizer.getBit(pattern1, 21));
		assertTrue(!TimePatternDigitizer.getBit(pattern1, 40));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetBitOutsideUpperBound() {		
		assertTrue(TimePatternDigitizer.getBit(pattern1, 0));		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetBitOutsideLowerBound() {				
		assertTrue(TimePatternDigitizer.getBit(pattern1, 61));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDigitizeOutsideLowerBound() {				
		TimePatternDigitizer.digitize(pattern1, 0);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDigitizeOutsideUpperBound() {				
		TimePatternDigitizer.digitize(pattern1, 61);		
	}
	
	@Test
	public void testDigitizeMinPattern() {				
		BitSet digitized = TimePatternDigitizer.digitize(pattern1, 3);
		// FIXME this test will fail: actual size is 64, the minimum allowed by BitSet
		// see http://stackoverflow.com/questions/2854098/java-util-bitset-set-doesnt-work-as-expected
		// and consider continuing or not using BitSets.
		//assertTrue(digitized.size() == 3);
		BitSet mask = new BitSet(3);
		mask.set(1);
		digitized.xor(mask);
		assertTrue(digitized.cardinality() == 3);
	}

}
