package org.eclipse.emf.henshin.interpreter.util;

import java.util.Collection;

/**
 * Aggregation functions for Henshin.
 */
public class Aggregations {

	/**
	 * Calculate the number of elements in a Collection.
	 * 
	 * @param elements Collection of objects.
	 * @return Number of elements.
	 */
	public static double COUNT(Collection<?> elements) {
		return elements.size();
	}

	/**
	 * Calculate the sum of a collection of numbers.
	 * 
	 * @param elements Collection of numbers.
	 * @return Sum of the numbers.
	 */
	public static double SUM(Collection<?> elements) {
		double sum = 0;
		ensureNumbers(elements);
		for (Object elem : elements) {
			sum += ((Number) elem).doubleValue();
		}
		return sum;
	}

	/**
	 * Calculate the minimum of a non-empty collection of numbers.
	 * 
	 * @param elements Non-empty collection of numbers.
	 * @return Minimum of the numbers.
	 */
	public static double MIN(Collection<?> elements) {
		ensureNonEmpty(elements);
		ensureNumbers(elements);
		double min = ((Number) elements.iterator().next()).doubleValue();
		for (Object elem : elements) {
			min = Math.min(min, ((Number) elem).doubleValue());
		}
		return min;
	}

	/**
	 * Calculate the minimum of a possibly empty collection of numbers.
	 * 
	 * @param elements Collection of numbers.
	 * @param valueForEmptyCollection Value to be used in case of an empty collection.
	 * @return Minimum of the numbers.
	 */
	public static double MIN(Collection<?> elements, Number valueForEmptyCollection) {
		return elements.isEmpty() ? valueForEmptyCollection.doubleValue() : MIN(elements);
	}

	/**
	 * Calculate the maximum of a non-empty collection of numbers.
	 * 
	 * @param elements Non-empty collection of numbers.
	 * @return Maximum of the numbers.
	 */
	public static double MAX(Collection<?> elements) {
		ensureNonEmpty(elements);
		ensureNumbers(elements);
		double max = ((Number) elements.iterator().next()).doubleValue();
		for (Object elem : elements) {
			max = Math.max(max, ((Number) elem).doubleValue());
		}
		return max;
	}

	/**
	 * Calculate the maximum of a possibly empty collection of numbers.
	 * 
	 * @param elements Collection of numbers.
	 * @param valueForEmptyCollection Value to be used in case of an empty collection.
	 * @return Maximum of the numbers.
	 */
	public static double MAX(Collection<?> elements, Number valueForEmptyCollection) {
		return elements.isEmpty() ? valueForEmptyCollection.doubleValue() : MAX(elements);
	}

	/**
	 * Calculate the average (arithmetic mean) of a non-empty collection of numbers.
	 * 
	 * @param elements Non-empty collection of numbers.
	 * @return Average of the numbers.
	 */
	public static double AVG(Collection<?> elements) {
		ensureNonEmpty(elements);
		return SUM(elements) / COUNT(elements);
	}

	/**
	 * Calculate the average (arithmetic mean) of a possibly empty collection of numbers.
	 * 
	 * @param elements Collection of numbers.
	 * @param valueForEmptyCollection Value to be used in case of an empty collection.
	 * @return Average of the numbers.
	 */
	public static double AVG(Collection<?> elements, Number valueForEmptyCollection) {
		return elements.isEmpty() ? valueForEmptyCollection.doubleValue() : AVG(elements);
	}

	/**
	 * Calculate the variance of a non-empty collection of numbers.
	 * 
	 * @param elements Non-empty collection of numbers.
	 * @return Variance of the numbers.
	 */
	public static double VAR(Collection<?> elements) {
		ensureNonEmpty(elements);
		double mean = AVG(elements);
		double temp = 0, a;
		for (Object elem : elements) {
			a = ((Number) elem).doubleValue();
			temp += (mean - a) * (mean - a);
		}
		return temp / COUNT(elements);
	}

	/**
	 * Calculate the standard deviation of a non-empty collection of numbers.
	 * 
	 * @param elements Non-empty collection of numbers.
	 * @return Standard deviation of the numbers.
	 */
	double STDEV(Collection<?> elements) {
		return Math.sqrt(VAR(elements));
	}

	/*
	 * Ensure that all elements in a list are numbers.
	 */
	private static void ensureNumbers(Collection<?> elements) {
		for (Object elem : elements) {
			if (!(elem instanceof Number)) {
				throw new IllegalArgumentException("Not a number: " + elem);
			}
		}
	}

	/*
	 * Ensure that a collection is non-empty.
	 */
	private static void ensureNonEmpty(Collection<?> elements) {
		if (elements.isEmpty()) {
			throw new IllegalArgumentException("Cannot aggegrate over empty set");
		}
	}

}
