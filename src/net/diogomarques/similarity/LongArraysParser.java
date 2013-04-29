package net.diogomarques.similarity;

import java.util.Arrays;

/**
 * Parses long arrays from their default String representation (given by
 * {@link Arrays#toString(long[])}).
 * 
 * @author Diogo Marques <diogohomemmarques@gmail.com>
 * 
 */
public class LongArraysParser {

	public static long[] parse(String arr) {
		String[] items = arr.replaceAll("\\[", "").replaceAll("\\]", "")
				.split(",");
		long[] results = new long[items.length];
		for (int i = 0; i < items.length; i++)
			results[i] = Long.parseLong(items[i].trim());
		return results;
	}

}
