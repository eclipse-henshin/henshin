package org.eclipse.emf.henshin.statespace.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.parser.StateSpaceLexer;
import org.eclipse.emf.henshin.statespace.parser.StateSpaceParser;

/**
 * Utility methods for loading and saving state spaces.
 * @author Christian Krause
 */
public class StateSpaceLoader {
	
	private static final String PLUGIN_ID = "org.eclipse.emf.henshin.statespace";
	
	/**
	 * Load a state space from a file.
	 * @param file Input file.
	 * @return State space.
	 * @throws CoreException On I/O or parser errors.
	 */
	public static StateSpace load(IFile file) throws CoreException {
        
        try {
        	
        	// Set up lexer:
    		ANTLRInputStream in = new ANTLRInputStream(file.getContents());		
            StateSpaceLexer lexer = new StateSpaceLexer(in);
           	CommonTokenStream tokens = new CommonTokenStream(lexer);
            
           	// Run the parser:
           	StateSpaceParser parser = new StateSpaceParser(tokens);
        	StateSpace result = parser.stateSpace();
        	return result;
        
        } catch (Throwable t)  {
			throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID, 0, "Error loading state space", t));
        }
        
	}
	
	
	/**
	 * Save a state space to a file.
	 * @param stateSpace State space.
	 * @param file File.
	 * @param monitor Progress monitor.
	 * @throws CoreException On I/O errors.
	 */
	public static void save(final StateSpace stateSpace, IFile file, IProgressMonitor monitor) throws CoreException {
		
		// Write contents to a buffer:
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();		
		try {
			StateSpacePrettyPrinter.print(stateSpace, buffer, monitor);
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID, 0, "Error saving state space", e));
		}
		
		// Write the buffer content to the target file:
		ByteArrayInputStream in = new ByteArrayInputStream(buffer.toByteArray());
		file.setContents(in, true, false, monitor);
		
	}
	
}
