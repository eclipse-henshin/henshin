package org.eclipse.emf.henshin.statespace.tuples;

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
	public static TupleList generateTuples(TupleGenerator generator, StateSpaceIndex index, 
			boolean simplify, IProgressMonitor monitor) {
		
		int stateCount = index.getStateSpace().getStates().size();
		monitor.beginTask("Generating tuples", stateCount*2+1);
		
		TupleList tuples = new TupleList();
		try {
			generator.initialize(index, new SubProgressMonitor(monitor, stateCount));
			for (State state : index.getStateSpace().getStates()) {
				tuples.add(generator.createTuple(state));
				monitor.worked(1);
			}
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
		if (simplify) {
			tuples.removeConstantColumns();
			tuples.simplifyColumns();
		}
		monitor.worked(1);
		monitor.done();
		return tuples;
		
	}
	
}
