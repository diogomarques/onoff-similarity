package net.diogomarques.similarity;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import net.diogomarques.similarity.FixedBitSetSimilarity.Metric;

import org.junit.Test;

public class FixedBitSetSimilarityTest {

	int resolution = 64;
	long[] pattern0 = { 200, 200, 200, 200, 200 };
	long[] pattern0equal = { 200, 200, 200, 200, 200 };
	long[] pattern0inverse = { 0, 200, 200, 200, 200, 200 };

	// S1
	String s1template = "[84, 267, 45, 116, 55, 124, 55, 277, 65, 570, 56, 333, 46]";

	@Test
	public void testAllEqual() {
		FixedBitSet set0 = TimePatternDigitizer.digitize(pattern0, resolution);
		FixedBitSet set1 = TimePatternDigitizer.digitize(pattern0equal,
				resolution);
		for (FixedBitSetSimilarity.Metric metric : FixedBitSetSimilarity.Metric
				.values()) {
			double score = FixedBitSetSimilarity.getScore(set0, set1, metric);
			assertEquals("metric " + metric.name()
					+ " should be 1 for equal patterns", 1.0, score, 0.0);
		}
	}

	@Test
	public void testAllInverse() {
		FixedBitSet set0 = TimePatternDigitizer.digitize(pattern0, resolution);
		// System.err.println("set0: " + set0);
		FixedBitSet set1 = TimePatternDigitizer.digitize(pattern0inverse,
				resolution);
		// System.err.println("set1: " + set1);
		for (FixedBitSetSimilarity.Metric metric : FixedBitSetSimilarity.Metric
				.values()) {
			double score = FixedBitSetSimilarity.getScore(set0, set1, metric);
			// System.err.println(metric.name() + " score: " + score);
			assertEquals("metric " + metric.name()
					+ " should be 0 for inverted patterns", 0.0, score, 0.0);
		}
	}

	@Test
	public void testAllSimilar() {
		// Data
		List<String> s1inputs = new ArrayList<>();
		s1inputs.add("[55, 277, 45, 126, 36, 134, 46, 286, 46, 600, 45, 315, 36]");
		s1inputs.add("[65, 267, 37, 134, 36, 125, 46, 296, 36, 637, 55, 305, 46]");
		s1inputs.add("[64, 277, 55, 125, 46, 115, 46, 277, 36, 695, 45, 306, 45]");
		s1inputs.add("[36, 305, 27, 134, 36, 135, 36, 305, 36, 657, 36, 343, 27]");
		// Test
		long[] template = LongArraysParser.parse(s1template);
		FixedBitSet set0 = TimePatternDigitizer.digitize(template, resolution);
		// System.err.println("set0: " + s1template + " - " + set0);
		for (String inputStr : s1inputs) {
			long[] input = LongArraysParser.parse(inputStr);
			FixedBitSet set1 = TimePatternDigitizer.digitize(input, resolution);
			// System.err.println("set1: " + inputStr + " - " + set1);
			for (FixedBitSetSimilarity.Metric metric : FixedBitSetSimilarity.Metric
					.values()) {
				double score = FixedBitSetSimilarity.getScore(set0, set1,
						metric);
				// System.err.println(metric.name() + " score: " + score);
				assertEquals("metric " + metric.name()
						+ " should not have extreme values", 0.5, score, 0.45);
			}
		}
	}

	@Test
	public void testAllSimilarPrecise() {
		// Template
		long[] template = LongArraysParser.parse(s1template);
		FixedBitSet set0 = TimePatternDigitizer.digitize(template, resolution);
		// Example
		String s1similar = "[55, 267, 36, 135, 36, 134, 37, 295, 28, 618, 36, 305, 37]";
		long[] example = LongArraysParser.parse(s1similar);
		FixedBitSet set1 = TimePatternDigitizer.digitize(example, resolution);
//		System.err.println("set0: " + set0 + "\nset1: " + set1);
		// Metric values from https://github.com/rockymadden/stringmetric
		double hamming = 1 - 7.0 / resolution;
		assertEquals(hamming,
				FixedBitSetSimilarity.getScore(set0, set1, Metric.Hamming), 0.0);
		// double jaccard = 0.4603;
		double jaccard = 0.5;
		assertEquals(jaccard,
				FixedBitSetSimilarity.getScore(set0, set1, Metric.Jaccard), 0.0);
		double dice = 0.6666;
		assertEquals(dice,
				FixedBitSetSimilarity.getScore(set0, set1, Metric.Dice), 0.0001);
		double cosine = 7.0 / Math.sqrt(13.0 * 8.0);
		assertEquals(cosine,
				FixedBitSetSimilarity.getScore(set0, set1, Metric.Cosine),
				0.00001);

	}
}
