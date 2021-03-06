package net.diogomarques.similarity;

/**
 * Utility class with methods to digitize arrays of integers that represent
 * on/off signals over time, e.g. [200,200,200] for 200 time units on, followed
 * by 200 time units off, followed by 200 time units on.
 * 
 * TODO evaluate sampling procedure. currently, a single point in time is used.
 * perhaps taking the majority in a given time slot can yield more realistic
 * representations.
 * 
 * @author Diogo Marques <diogohomemmarques@gmail.com>
 */
public class TimePatternDigitizer {

	/**
	 * Digitizes an array of longs into a BitSet.
	 * 
	 * @param pattern
	 *            an array of longs
	 * @param resolution
	 *            the number of samples taken from the pattern. Must be at least
	 *            1 and not exceed the total time in the pattern. E.g. for
	 *            pattern [10,10,10], the max resolution is 10+10+10=30.
	 * @return a representation of the given pattern in BitSet form, where the
	 *         size() equals the given resolution
	 * @throws PatternException
	 *             when the resolution is greater than the pattern's total time.
	 */
	public static FixedBitSet digitize(long[] pattern, int resolution) {
		if (resolution < 1)
			throw new IllegalArgumentException("Resolution must be over 0");
		long size = size(pattern);
		if (size < resolution)
			throw new IllegalArgumentException(
					"Pattern two short for the given resolution. Pattern size="
							+ size + " and resolution=" + resolution);
		FixedBitSet bitSet = new FixedBitSet(resolution);
		double samplingPeriod = size / (resolution * 1.0);
		double nextSampleTime = samplingPeriod;
		for (int i = 0; i < resolution; i++) {
			// TODO: merge cycles
			boolean sample = getBit(pattern, Math.round(nextSampleTime));
			bitSet.set(i, sample);
			nextSampleTime += samplingPeriod;
		}
		return bitSet;
	}

	protected static boolean getBit(long[] pattern, long ms) {
		if (ms < 1 || ms > size(pattern))
			throw new IllegalArgumentException("ms must be between 1 and "
					+ size(pattern));
		boolean value = true;
		long elapsedTime = 0;
		for (long t : pattern) {
			elapsedTime += t;
			if (elapsedTime >= ms)
				break;
			value = !value;
		}
		return value;
	}

	protected static long size(long[] pattern) {
		long size = 0;
		for (long feature : pattern)
			size += feature;
		return size;
	}

}
