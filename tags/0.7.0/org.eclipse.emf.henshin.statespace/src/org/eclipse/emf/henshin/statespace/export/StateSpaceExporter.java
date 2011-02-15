package org.eclipse.emf.henshin.statespace.export;

import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.statespace.StateSpace;

/**
 * Interface for state space exporters.
 * @author Christian Krause
 */
public interface StateSpaceExporter {
	
	/**
	 * Perform the export operation.
	 * @param stateSpace State space to be exported.
	 * @param uri URI where the state space should be exported to.
	 * @param monitor Progress monitor.
	 * @throws Exception On errors.
	 */
	void export(StateSpace stateSpace, URI uri, IProgressMonitor monitor) throws IOException;
	
	/**
	 * Get the name of this exported.
	 * @return The name.
	 */
	String getName();
	
	/**
	 * Get the list of file extensions supported by this exporter.
	 * @return List of file extensions.
	 */
	String[] getFileExtensions();
	
}
