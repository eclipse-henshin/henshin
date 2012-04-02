package org.eclipse.emf.henshin.statespace.tuples;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceIndex;

/**
 * Interface for tuple generators.
 * @author Christian Krause
 */
public interface TupleGenerator {

	/**
	 * Initialize this tuple generator.
	 * @param index State space index.
	 * @param monitor Progress monitor.
	 * @throws StateSpaceException On errors.
	 */
	void initialize(StateSpaceIndex index, IProgressMonitor monitor) throws StateSpaceException;

	/**
	 * Create a tuple for a state.
	 * @param state State.
	 * @return The generated tuple.
	 * @throws StateSpaceException On errors.
	 */
	Tuple createTuple(State state) throws StateSpaceException;

}
