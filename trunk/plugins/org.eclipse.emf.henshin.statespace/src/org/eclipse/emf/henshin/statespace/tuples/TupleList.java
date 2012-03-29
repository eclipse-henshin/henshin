package org.eclipse.emf.henshin.statespace.tuples;

import java.util.ArrayList;
import java.util.List;

/**
 * Tuple list class.
 * @author Christian Krause
 */
public class TupleList extends ArrayList<Tuple> {

	// Generated serial ID:
	private static final long serialVersionUID = -3815199168758095254L;

	public TupleList() {
		super();
	}
	
	public TupleList(int initialCapacity) {
		super(initialCapacity);
	}
	
	/**
	 * Get the width of the tuples.
	 * @return The width of the tuples or -1 if the list is empty.
	 */
	public int width() {
		if (isEmpty()) {
			return -1;
		} else {
			return get(0).width();
		}
	}
	
	/**
	 * Check whether this tuple list starts with the argument list.
	 * @param list Tuple list.
	 * @return <code>true</code> if it starts with the argument list.
	 */
	public boolean startsWith(TupleList list) {
		if (width()!=list.width() || size()>list.size()) {
			return false;
		}
		int size = list.size();
		for (int i=0; i<size; i++) {
			if (!get(i).equals(list.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Get the minimum and the maximum values in this list.
	 * @return Minimum and maximum values.
	 */
	public TupleList getMinMax() {
		if (isEmpty()) {
			return null;
		}
		Tuple mins = (Tuple) get(0).clone();
		Tuple maxs = (Tuple) get(0).clone();
		int width = mins.width();
		for (Tuple t : this) {
			for (int i=0; i<width; i++) {
				if (mins.data[i] > t.data[i]) {
					mins.data[i] = t.data[i];
				}
				if (maxs.data[i] < t.data[i]) {
					maxs.data[i] = t.data[i];
				}
			}
		}
		TupleList minmax = new TupleList();
		minmax.add(mins);
		minmax.add(maxs);
		return minmax;
	}

	/**
	 * Simplify the values in all columns.
	 */
	public void simplifyColumns() {
		if (isEmpty()) {
			return;
		}
		int width = width();
		int size = size();
		for (int j=0; j<width; j++) {
			List<Integer> values = new ArrayList<Integer>();
			values.add(0);
			for (int i=0; i<size; i++) {
				Integer val = get(i).data[j];
				if (!values.contains(val)) {
					values.add(val);
				}
			}
			for (int i=0; i<size; i++) {
				Tuple tuple = get(i);
				tuple.data[j] = values.indexOf(tuple.data[j]);
			}
		}
	}

	/**
	 * Remove all columns with constant values.
	 * @return Number of removed columns.
	 */
	public int removeConstantColumns() {
		if (isEmpty()) {
			return 0;
		}
		// Find out which columns can be removed:
		Tuple first = get(0);
		int width = width();
		int size = size();
		boolean[] remove = new boolean[width];
		int removeCount = width;
		// First check for real constant columns:
		for (int j=0; j<width; j++) {
			remove[j] = true;			
			int d = first.data[j];
			for (int i=1; i<size; i++) {
				if (get(i).data[j]!=d) {
					remove[j] = false;
					removeCount--;
					break;
				}
			}
		}
		// Now for delta constants:
		for (int j=0; j<width; j++) {
			if (remove[j]) continue;
			for (int k=j+1; k<width; k++) {
				boolean doRemove = true;
				int d = first.data[j] - first.data[k];
				for (int i=1; i<size; i++) {
					int e = get(i).data[j] - get(i).data[k];
					if (e!=d) {
						doRemove = false;
						break;
					}
				}
				if (doRemove) {
					remove[j] = true;
					removeCount++;
					break;
				}
			}
		}
		// Now do the removal:
		if (removeCount>0) {
			int newSize = (width-removeCount);
			for (int i=0; i<size; i++) {
				Tuple t = get(i);
				int[] newData = new int[newSize];
				int k = 0;
				for (int j=0; j<width; j++) {
					if (!remove[j]) {
						newData[k++] = t.data[j];
					}
				}
				t.data = newData;
			}
		}
		return removeCount;
	}


	/*
	 * (non-Javadoc)
	 * @see java.util.AbstractCollection#toString()
	 */
	@Override
	public String toString() {
		String s = "";
		for (Tuple t : this) {
			s = s + t + "\n";
		}
		return s;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() {
		TupleList clone = new TupleList(size());
		for (Tuple tuple : this) {
			clone.add((Tuple) tuple.clone());
		}
		return clone;
	}

}
