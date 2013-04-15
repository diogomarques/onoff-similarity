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
		 * distance</a> between two equal-sized bit arrays, and <i>s</i> the
		 * size of the arrays. The similarity score is 1 - h/s.
		 */
		Hamming,
		// TODO doc
		Dice, Tanimoto, Cosine;
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
		case Tanimoto:
			score = tanimoto(bitSet1Clone, bitSet2Clone);
			break;
		default:
			throw new IllegalArgumentException("No such metric available.");
		}
		return score;
	}
	
	/**
	 * Get the Hamming similarity score for vectors, between 0 and 1.
	 * 
	 * @return a score between 0.0 and 1.0
	 */
	public static double hamming(BitSet bitSet1, BitSet bitSet2) {		
		bitSet1.xor(bitSet2);
		double hammingDistance = 1.0 * bitSet1.cardinality();
		double hammingDisimiliraty = hammingDistance / bitSet1.size();
		return 1 - hammingDisimiliraty;
	}

	/**
	 * Get the Tanimoto similarity score, between 0 and 1.
	 * 
	 * @return the score
	 */
	public static double tanimoto(BitSet bitSet1, BitSet bitSet2) {
		BitSet and = (BitSet) bitSet1.clone();
		BitSet or = (BitSet) bitSet1.clone();
		and.and(bitSet2);
		or.or(bitSet2);
		return 1.0 * and.cardinality() / or.cardinality();
	}

	/**
	 * Get the Dice similarity score, between 0 and 1.
	 * 
	 * @return the score
	 */
	public static double dice(BitSet bitSet1, BitSet bitSet2) {
		BitSet andOnes = (BitSet) bitSet1.clone();
		andOnes.and(bitSet2);
		return (2.0 * andOnes.cardinality())
				/ (bitSet1.cardinality() + bitSet2.cardinality());
	}

	

	/**
	 * Get the Cosine similarity score for vectors, between 0 and 1.
	 * 
	 * @return a score between 0.0 and 1.0
	 */
	public static double cosine(BitSet bitSet1, BitSet bitSet2) {		
		BitSet and = (BitSet) bitSet1.clone();
		and.and(bitSet2);
		BitSet only1 = (BitSet) bitSet1.clone();
		only1.xor(bitSet2);
		only1.and(bitSet1);
		BitSet only2 = (BitSet) bitSet2.clone();
		only2.xor(bitSet1);
		only2.and(bitSet2);
		double cosineScore = (1.0 * and.cardinality())
				/ Math.sqrt(1.0 * ((only1.cardinality() + and.cardinality()) * (only2
						.cardinality() + and.cardinality())));
		return cosineScore;
	}
}
