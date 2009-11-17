package org.eclipse.emf.henshin.statespace.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;

/**
 * Static pretty-printer methods for state spaces.
 * @author Christian Krause
 * @deprecated
 */
public class StateSpacePrettyPrinter {
	
	/**
	 * Pretty print a state space to a writer.
	 * @param stateSpace State space.
	 * @param writer Writer.
	 * @param monitor Progress monitor.
	 * @throws IOException On I/O errors.
	 */
	public static void print(StateSpace stateSpace, Writer writer, IProgressMonitor monitor) throws IOException {
		
		monitor.beginTask("Writing state space", stateSpace.getStates().size());
		
		// Write all states:
		for (State state : stateSpace.getStates()) {
			writer.write(state.toString() + "\n");
			monitor.worked(1);
		}
		
		// Finished:
		monitor.done();
		
	}
	
	/**
	 * Pretty print a state space to a given output stream.
	 * @param stateSpace State space.
	 * @param out Output stream.
	 * @param monitor Progress monitor.
	 * @throws IOException On I/O errors.
	 */
	public static void print(StateSpace stateSpace, OutputStream out, IProgressMonitor monitor) throws IOException {
		Writer printer = new BufferedWriter(new PrintWriter(out));
		print(stateSpace, printer, monitor);
		printer.flush();
	}
	
	/**
	 * Pretty print a state space to a string.
	 * @param stateSpace State Space.
	 * @return String representation,
	 */
	public static String print(StateSpace stateSpace) {
		StringWriter writer = new StringWriter();
		try {
			print(stateSpace, writer, new NullProgressMonitor());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return writer.getBuffer().toString();
	}
	
}
