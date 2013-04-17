package net.diogomarques.similarity;


/**
 * Calculates similarity scores for equal-sized on/off patterns. All scores are
 * between 0.0 (completely different) and 1.0 (equal).
 * <p>
 * Use {@link Metric#values()} to see available similarity metrics.
 * 
 * @author Diogo Marques <diogohomemmarques@gmail.com>
 * 
 */
public class FixedBitSetSimilarity {

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
		 * arrays of equal length. The cosine similarity can be expressed as |A
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
	 * @param pattern1
	 *            one pattern
	 * @param pattern2
	 *            other pattern
	 * @param metric
	 *            a {@link Metric}
	 * @return a score between 0.0 (completely different) to 1.0 (equal).
	 */
	public static double getScore(FixedBitSet pattern1,
			FixedBitSet pattern2, Metric metric) {
		if (pattern1.size() != pattern2.size())
			throw new IllegalArgumentException("Bit array sizes must be equal");
		FixedBitSet bitSet1Clone = (FixedBitSet) pattern1.clone();
		FixedBitSet bitSet2Clone = (FixedBitSet) pattern2.clone();
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
			throw new IllegalArgumentException("Metric not available.");
		}
		return score;
	}

	// 1 - |a XOR b| / size
	private static double hamming(FixedBitSet bitSet1,
			FixedBitSet bitSet2) {
		bitSet1.xor(bitSet2);
		double hammingDistance = 1.0 * bitSet1.cardinality();
		double hammingDisimiliraty = hammingDistance / bitSet1.size();
		return 1 - hammingDisimiliraty;
	}

	// |a AND b| / |a OR b|
	private static double jaccard(FixedBitSet bitSet1,
			FixedBitSet bitSet2) {
		FixedBitSet and = (FixedBitSet) bitSet1.clone();
		FixedBitSet or = (FixedBitSet) bitSet1.clone();
		and.and(bitSet2);
		or.or(bitSet2);
		return 1.0 * and.cardinality() / or.cardinality();
	}

	// (2 * |a AND b|) / (|a| + |b|)
	private static double dice(FixedBitSet bitSet1, FixedBitSet bitSet2) {
		FixedBitSet and = (FixedBitSet) bitSet1.clone();
		and.and(bitSet2);
		return (2.0 * and.cardinality())
				/ (bitSet1.cardinality() + bitSet2.cardinality());
	}

	// |a AND b| / sqrt(|a| * |b|)
	private static double cosine(FixedBitSet bitSet1,
			FixedBitSet bitSet2) {
		FixedBitSet and = (FixedBitSet) bitSet1.clone();
		and.and(bitSet2);
		return (1.0 * and.cardinality())
				/ Math.sqrt(bitSet1.cardinality() * bitSet2.cardinality());
	}
}
