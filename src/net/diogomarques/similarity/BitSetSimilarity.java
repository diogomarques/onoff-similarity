package net.diogomarques.similarity;

import java.util.BitSet;

/**
 * Calculates simple similarity scores for equal-sized BitSets. All scores are
 * between 0.0 (completely different) and 1.0 (equal).
 * 
 * @author Diogo Marques <diogohomemmarques@gmail.com>
 * 
 */
public class BitSetSimilarity {

	/**
	 * Available metrics for similarity calculation.
	 */
	public static enum Metric {

		/**
		 * The Hamming similarity score is a measure of how many flips would be
		 * required to make both bit arrays equal. <br>
		 * Let <i>h</i> be the <a
		 * href="http://en.wikipedia.org/wiki/Hamming_distance">Hamming
		 * distance</a> (the number of ones in the XOR) between two equal-sized
		 * bit arrays, and <i>s</i> the size of the arrays. The similarity score
		 * is 1 - h/s.
		 */
		Hamming,
		/**
		 * The <a href="http://en.wikipedia.org/wiki/Jaccard_index">Jaccard
		 * coefficient</a> is a well-known similarity metric. <br>
		 * Let <i>A</i> and <i>B</i> be two bit arrays of equal length. The
		 * Jaccard coefficient can be expressed as |A AND B| / |A OR B| (the
		 * number of ones in the intersection over the number of ones in the
		 * union).
		 */
		Jaccard,
		/**
		 * The <a href="http://en.wikipedia.org/wiki/Dice%27s_coefficient">Dice-
		 * Sorensen coefficient</a> is another well-known similarity metric,
		 * expressing the shared information between two samples. <br>
		 * Let <i>A</i> and <i>B</i> be two bit arrays of equal length. The Dice
		 * coefficient can be expressed as (2 * |A AND B|) / (|A| + |B|) (two
		 * times the number of ones in the intersection over the sum of the
		 * number of ones in the bit arrays).
		 */
		Dice,
		/**
		 * The <a href="http://en.wikipedia.org/wiki/Cosine_distance">cosine
		 * similarity</a> measures the alignment in orientation of the two bit
		 * arrays (here seen has vectors). Let <i>A</i> and <i>B</i> be two bit
		 * arrays of equal length. The Cosine similarity can be expressed as |A
		 * AND B| / sqrt(|A| * |B|) (the number of ones in the intersection over
		 * the square root of the product of the number of ones in the bit
		 * arrays).
		 * 
		 */
		Cosine;
		// TODO other metrics (euclidean, 1-manhattan); why not significance
		// testing for binomial vars (Fisher/McNemar)?
	}

	/**
	 * Get a measure of similarity between two bit arrays.
	 * 
	 * @param bitSet1
	 *            one bit arrays
	 * @param bitSet2
	 *            other bit arrays
	 * @param metric
	 *            a {@link Metric}
	 * @return a score between 0.0 (completely different) to 1.0 (equal).
	 */
	public static double getScore(BitSet bitSet1, BitSet bitSet2, Metric metric) {
		if (bitSet1.size() != bitSet2.size())
			throw new IllegalArgumentException(
					"The measure is only defined for equal sized vectors.");
		BitSet bitSet1Clone = (BitSet) bitSet1.clone();
		BitSet bitSet2Clone = (BitSet) bitSet2.clone();
		double score = 0.0;
		switch (metric) {
		case Hamming:
			score = hamming(bitSet1Clone, bitSet2Clone);
			break;
		case Cosine:
			score = cosine(bitSet1Clone, bitSet2Clone);
			break;
		case Dice:
			score = dice(bitSet1Clone, bitSet2Clone);
			break;
		case Jaccard:
			score = jaccard(bitSet1Clone, bitSet2Clone);
			break;
		default:
			throw new IllegalArgumentException("No such metric available.");
		}
		return score;
	}

	// 1 - |a XOR b| / size
	private static double hamming(BitSet bitSet1, BitSet bitSet2) {
		bitSet1.xor(bitSet2);
		double hammingDistance = 1.0 * bitSet1.cardinality();
		double hammingDisimiliraty = hammingDistance / bitSet1.size();
		return 1 - hammingDisimiliraty;
	}

	// |a AND b| / |a OR b|
	private static double jaccard(BitSet bitSet1, BitSet bitSet2) {
		BitSet and = (BitSet) bitSet1.clone();
		BitSet or = (BitSet) bitSet1.clone();
		and.and(bitSet2);
		or.or(bitSet2);
		return 1.0 * and.cardinality() / or.cardinality();
	}

	// (2 * |a AND b|) / (|a| + |b|)
	private static double dice(BitSet bitSet1, BitSet bitSet2) {
		BitSet and = (BitSet) bitSet1.clone();
		and.and(bitSet2);
		return (2.0 * and.cardinality())
				/ (bitSet1.cardinality() + bitSet2.cardinality());
	}

	// |a AND b| / sqrt(|a| * |b|)
	private static double cosine(BitSet bitSet1, BitSet bitSet2) {
		BitSet and = (BitSet) bitSet1.clone();
		and.and(bitSet2);
		return (1.0 * and.cardinality())
				/ Math.sqrt(bitSet1.cardinality() * bitSet2.cardinality());
	}
}
