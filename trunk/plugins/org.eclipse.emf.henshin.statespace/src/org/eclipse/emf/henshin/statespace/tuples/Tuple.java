package org.eclipse.emf.henshin.statespace.tuples;

import java.util.Arrays;

/**
 * Data class for tuples.
 * @author Christian Krause
 */
public class Tuple implements Cloneable {

	// The data.
	int[] data;
	
	/**
	 * Default constructor
	 * @param width Width of the tuple.
	 */
	public Tuple(int width) {
		data = new int[width];
	}
	
	/**
	 * Returns the data of this tuple.
	 * @return The data.
	 */
	public int[] data() {
		return data;
	}
	
	/**
	 * Return the width of this tuple.
	 * @return The width.
	 */
	public int width() {
		return data.length;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Arrays.hashCode(data);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Tuple) {
			return Arrays.equals(data, ((Tuple) o).data);
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String t = "(";
		for (int i=0; i<data.length; i++) {
			t = t + data[i];
			if (i<data.length-1) {
				t = t + ",";
			}
		}
		return t + ")";
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() {
		Tuple clone = new Tuple(data.length);
		System.arraycopy(data, 0, clone.data, 0, data.length);
		return clone;
	}

	public void revert() {
		int half = data.length / 2;
		for (int i=0; i<half; i++) {
			int d = data[i];
			data[i] = data[data.length-i-1];
			data[data.length-i-1] = d;
		}
	}
	
}
