package org.eclipse.emf.henshin.statespace.cadp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Arrays;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceIndex;
import org.eclipse.emf.henshin.statespace.StateSpaceValidator;
import org.eclipse.emf.henshin.statespace.resource.StateSpaceResource;

/**
 * Abstract state space validator implementation.
 * Contains some helper methods for invoking commands
 * and converting files.
 * 
 * @author Christian Krause
 */
public abstract class AbstractStateSpaceValidator  implements StateSpaceValidator {
	
	// Property to be checked:
	protected String property;
	
	// State space index:
	protected StateSpaceIndex index;
	
	/**
	 * Export a state space as an AUT file.
	 * @param stateSpace State space.
	 * @param aut The AUT file.
	 * @throws IOException On I/O errors.
	 */
	protected File exportAsAUT(StateSpace stateSpace, File aut, IProgressMonitor monitor) throws IOException {
		OutputStream out = new BufferedOutputStream(new FileOutputStream(aut));
		((StateSpaceResource) stateSpace.eResource()).exportAsAUT(out, monitor);
		return aut;
	}
	
	/**
	 * Convert a file using a given command.
	 * @param input Input file.
	 * @param output Output file.
	 * @param command Command.
	 * @throws Exception On 
	 */
	protected void convertFile(File input, File output, String... command) throws Exception {
		command = Arrays.copyOf(command, command.length + 2);
		command[command.length-2] = input.getAbsolutePath();
		command[command.length-1] = output.getAbsolutePath();
		Process process = Runtime.getRuntime().exec(command);
		if (process.waitFor()!=0) {
			throw new RuntimeException(command[0] + " returned exit code " + process.exitValue());
		}
	}
	
	/**
	 * Write a string to a file.
	 * @param file File.
	 * @param content Content.
	 * @throws IOException I/O errors.
	 */
	protected void writeToFile(File file, String content) throws IOException {
		FileWriter writer = new FileWriter(file);
		writer.write(content);
		writer.close();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.Validator#setProperty(java.lang.String)
	 */
	@Override
	public void setProperty(String property) throws ParseException {
		this.property = property;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.Validator#setStateSpaceIndex(org.eclipse.emf.henshin.statespace.StateSpaceIndex)
	 */
	@Override
	public void setStateSpaceIndex(StateSpaceIndex index) {
		this.index = index;
	}
	
}
