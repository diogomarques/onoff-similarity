package net.diogomarques.similarity;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class LongArraysParserTest {

	@Test
	public void test() {
		boolean result = Arrays.equals(new long[] { 100, 0, 100 },
				LongArraysParser.parse(Arrays
						.toString(new long[] { 100, 0, 100 })));
		assertTrue(result);
	}
}
