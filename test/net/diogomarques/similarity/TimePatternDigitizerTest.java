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

	@Test(expected = IllegalArgumentException.class)
	public void testDigitizeOutsideLowerBound() {
		TimePatternDigitizer.digitize(pattern1, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDigitizeOutsideUpperBound() {
		TimePatternDigitizer.digitize(pattern1, 61);
	}

	@Test
	public void testDigitizeMinPattern() {
		BitSet digitized = TimePatternDigitizer.digitize(pattern1, 3);
		assertTrue(digitized.size() == 3);
		BitSet mask = new BitSet(3);
		mask.set(1);
		digitized.xor(mask);
		assertTrue(digitized.cardinality() == 3);
	}

	@Test
	public void testDigitizeBigPattern() {
		long[] pattern2 = new long[] { 40, 40, 40, 40, 40 };
		int resolution = 64;
		BitSet digitized = TimePatternDigitizer.digitize(pattern2, resolution);
		assertTrue(digitized.size() == resolution);
		assertEquals("the number of bits set to one should be 3/5 of total",
				resolution * 3 / 5, digitized.cardinality(), 0.0);
	}
	
	@Test
	public void testDigitizeExactPattern() {
		long[] pattern2 = new long[] { 40, 40, 40, 40, 40 };
		int resolution = 40*5;
		BitSet digitized = TimePatternDigitizer.digitize(pattern2, resolution);
		assertTrue(digitized.size() == resolution);		
		for (int i = 0; i < pattern2.length; i++) {
			for (int j = 0; j < pattern2[i]; j++) {
				boolean currentBit = digitized.get((i*40)+(j));				
				if (i%2 == 0)
					assertTrue(currentBit);				
				else
					assertFalse(currentBit);				
			}			
		}
	}

}
