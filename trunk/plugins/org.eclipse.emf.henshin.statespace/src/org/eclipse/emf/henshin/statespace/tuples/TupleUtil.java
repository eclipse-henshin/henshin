package org.eclipse.emf.henshin.statespace.tuples;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpaceIndex;

/**
 * Utility methods for working with tuples.
 * @author Christian Krause
 */
public class TupleUtil {

	/**
	 * Generate tuples for all states in a given state space.
	 * @param generator Tuple generator.
	 * @param index State space index.
	 * @param simplify Whether to simplify the tuples.
	 * @param monitor Progress monitor.
	 * @return List of tuples.
	 */
	public static List<Tuple> generateTuples(TupleGenerator generator, StateSpaceIndex index, 
			boolean simplify, IProgressMonitor monitor) {
		
		int stateCount = index.getStateSpace().getStates().size();
		monitor.beginTask("Generating tuples", stateCount*2+1);
		
		List<Tuple> tuples = new ArrayList<Tuple>();
		try {
			generator.initialize(index, new SubProgressMonitor(monitor,stateCount));
			for (State state : index.getStateSpace().getStates()) {
				tuples.add(generator.createTuple(state));
				monitor.worked(1);
			}
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
		
		if (simplify) {
			removeConstantColumns(tuples);
			simplifyColumns(tuples);
		}
		monitor.worked(1);
		monitor.done();
		return tuples;
	}

	/**
	 * Simplify the values in all columns.
	 * @param tuples List of tuples.
	 */
	private static void simplifyColumns(List<Tuple> tuples) {
		
		// At least one entry:
		if (tuples.isEmpty()) {
			return;
		}
		
		// Simplify all columns.
		int tupleSize = tuples.get(0).size();
		int tupleCount = tuples.size();
		for (int j=0; j<tupleSize; j++) {
			List<Integer> values = new ArrayList<Integer>();
			for (int i=0; i<tupleCount; i++) {
				Integer val = tuples.get(i).data[j];
				if (!values.contains(val)) {
					values.add(val);
				}
			}
			for (int i=0; i<tupleCount; i++) {
				Tuple tuple = tuples.get(i);
				tuple.data[j] = values.indexOf(tuple.data[j]);
			}
		}
		
	}

	/**
	 * Remove all columns with constant values.
	 * @param tuples List of tuples.
	 * @return Number of removed columns.
	 */
	public static int removeConstantColumns(List<Tuple> tuples) {

		// At least one entry:
		if (tuples.isEmpty()) {
			return 0;
		}
		
		// Find out which columns can be removed:
		Tuple first = tuples.get(0);
		int tupleSize = first.size();
		int tupleCount = tuples.size();
		boolean[] remove = new boolean[tupleSize];
		int removeCount = tupleSize;
		
		// First check for real constant columns:
		for (int j=0; j<tupleSize; j++) {
			remove[j] = true;			
			int d = first.data[j];
			for (int i=1; i<tupleCount; i++) {
				if (tuples.get(i).data[j]!=d) {
					remove[j] = false;
					removeCount--;
					break;
				}
			}
		}
		
		// Now for delta constants:
		for (int j=0; j<tupleSize; j++) {
			if (remove[j]) continue;
			for (int k=j+1; k<tupleSize; k++) {
				boolean doRemove = true;
				int d = first.data[j] - first.data[k];
				for (int i=1; i<tupleCount; i++) {
					int e = tuples.get(i).data[j] - tuples.get(i).data[k];
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
			int newSize = (tupleSize-removeCount);
			for (int i=0; i<tupleCount; i++) {
				Tuple t = tuples.get(i);
				int[] newData = new int[newSize];
				int k = 0;
				for (int j=0; j<tupleSize; j++) {
					if (!remove[j]) {
						newData[k++] = t.data[j];
					}
				}
				t.data = newData;
			}
		}
		
		// Done.
		return removeCount;
		
	}
	
	/**
	 * Pretty-print a list of tuples.
	 * @param tuples List of tuples.
	 * @return String representation.
	 */
	public static String toString(List<Tuple> tuples) {
		String s = "";
		for (Tuple t : tuples) {
			s = s + t + "\n";
		}
		return s;
	}
	
	
	public static List<Tuple> getRanges(List<Tuple> tuples) {
		if (tuples.isEmpty()) {
			return null;
		}
		Tuple mins = (Tuple) tuples.get(0).clone();
		Tuple maxs = (Tuple) tuples.get(0).clone();
		int size = mins.size();
		for (Tuple t : tuples) {
			for (int i=0; i<size; i++) {
				if (mins.data[i] > t.data[i]) {
					mins.data[i] = t.data[i];
				}
				if (maxs.data[i] < t.data[i]) {
					maxs.data[i] = t.data[i];
				}
			}
		}
		List<Tuple> minmax = new ArrayList<Tuple>();
		minmax.add(mins);
		minmax.add(maxs);
		return minmax;
	}
	
}
