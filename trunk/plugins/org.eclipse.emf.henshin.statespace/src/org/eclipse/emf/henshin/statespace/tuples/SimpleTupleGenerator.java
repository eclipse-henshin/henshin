package org.eclipse.emf.henshin.statespace.tuples;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpaceIndex;

/**
 * Simple tuple generator.
 * @author Christian Krause
 */
public class SimpleTupleGenerator implements TupleGenerator {

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.tuples.TupleGenerator#initialize(org.eclipse.emf.henshin.statespace.StateSpaceIndex, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void initialize(StateSpaceIndex index, IProgressMonitor monitor) {
		// nothing to do
		monitor.done();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.tuples.TupleGenerator#createTuple(org.eclipse.emf.henshin.statespace.State)
	 */
	@Override
	public Tuple createTuple(State state) {
		Tuple tuple = new Tuple(1);
		tuple.data()[0] = state.getIndex();
		return tuple;
	}

}
