package org.eclipse.emf.henshin.statespace.external;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.eclipse.emf.henshin.statespace.StateSpaceExporter;
import org.eclipse.emf.henshin.statespace.StateSpaceIndex;
import org.eclipse.emf.henshin.statespace.tuples.TupleList;

/**
 * Abstract exporter class.
 * @author Christian Krause
 */
public abstract class AbstractStateSpaceExporter implements StateSpaceExporter {

	// The state space index:
	protected StateSpaceIndex index;
	
	// Tuples:
	protected TupleList tuples;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceExporter#setStateSpaceIndex(org.eclipse.emf.henshin.statespace.StateSpaceIndex)
	 */
	@Override
	public void setStateSpaceIndex(StateSpaceIndex index) {
		this.index = index;
	}
	
	/**
	 * Create a new output stream writer.
	 * @param file File.
	 * @return The writer.
	 * @throws IOException On errors.
	 */
	protected static OutputStreamWriter createWriter(File file) throws IOException {
		OutputStream out = new BufferedOutputStream(new FileOutputStream(file), 65536);
		OutputStreamWriter writer = new OutputStreamWriter(out);
		return writer;
	}
	
	public TupleList getTuples() {
		return tuples;
	}
	
}
