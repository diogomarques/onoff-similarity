package net.diogomarques.similarity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FixedBitSetSimilarityTest {

	long[] pattern0 = { 200, 200, 200, 200, 200 };
	long[] pattern0equal = { 200, 200, 200, 200, 200 };
	long[] pattern0inverse = { 0, 200, 200, 200, 200, 200 };

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAllEqual() {
		int resolution = 64;
		FixedBitSet set0 = TimePatternDigitizer.digitize(pattern0, resolution);
		FixedBitSet set1 = TimePatternDigitizer.digitize(pattern0equal, resolution);
		for (FixedBitSetSimilarity.Metric metric : FixedBitSetSimilarity.Metric
				.values()) {
			double score = FixedBitSetSimilarity.getScore(set0, set1, metric);
			assertEquals("metric " + metric.name() + " should be 1 for equal patterns" , 1.0, score, 0.0);
		}
	}
	
	@Test
	public void testAllInverse() {
		int resolution = 64;
		FixedBitSet set0 = TimePatternDigitizer.digitize(pattern0, resolution);
//		System.err.println("set0: " + set0);
		FixedBitSet set1 = TimePatternDigitizer.digitize(pattern0inverse, resolution);
//		System.err.println("set1: " + set1);
		for (FixedBitSetSimilarity.Metric metric : FixedBitSetSimilarity.Metric
				.values()) {
			double score = FixedBitSetSimilarity.getScore(set0, set1, metric);
//			System.err.println(metric.name() + " score: " + score);
			assertEquals("metric " + metric.name() + " should be 0 for inverted patterns" , 0.0, score, 0.0);
		}
	}

}
