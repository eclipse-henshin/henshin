/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved.
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace.external.prism;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.external.AbstractFileBasedValidator;

/**
 * Abstract PRISM tool wrapper.
 * @author Christian Krause
 */
public abstract class AbstractPRISMTool extends AbstractFileBasedValidator {
	
	// Properties key for PRISM path.
	public static final String PRISM_PATH_KEY = "prismPath";
	
	// Properties key for PRISM arguments.
	public static final String PRISM_ARGS_KEY = "prismArgs";
	
	/**
	 * Invoke PRISM.
	 * @param stateSpace State space.
	 * @param args Arguments.
	 * @param monitor Monitor.
	 * @return Created process.
	 * @throws Exception On errors.
	 */
	protected Process invokePRISM(StateSpace stateSpace, File formulaFile, 
			String[] args, IProgressMonitor monitor) throws Exception {
		
		// Generate the SM file.
		File smFile = generatePRISMFile(stateSpace, monitor);
		
		// Get the executable, path and arguments.
		String prism = getPRISMExecutable();
		String baseArgs = stateSpace.getProperties().get(PRISM_ARGS_KEY);
		if (baseArgs==null) baseArgs = "-fixdl -gaussseidel";
		String path = stateSpace.getProperties().get(PRISM_PATH_KEY);
		
		// Create the command.
		List<String> command = new ArrayList<String>();
		command.add(path!=null ? new File(path+File.separator+prism).getAbsolutePath() : prism);
		command.add(smFile.getAbsolutePath());
		if (formulaFile!=null) {
			command.add(formulaFile.getAbsolutePath());
		}
		if (baseArgs!=null) {
			for (String arg : baseArgs.split(" ")) {
				command.add(arg.trim());
			}
		}
		if (args!=null) {
			for (String arg : args) {
				command.add(arg.trim());
			}
		}
		
		// Now we can invoke the PRISM tool:
		System.out.println(command);
		return Runtime.getRuntime().exec(
				command.toArray(new String[] {}), 
				null, 
				path!=null ? new File(path) : null);
		
	}

	
	/**
	 * Generate a PRISM file from a state space.
	 * @param stateSpace State space.
	 * @return The generated file.
	 * @throws Exception On errors.
	 */
	protected File generatePRISMFile(StateSpace stateSpace, IProgressMonitor monitor) throws Exception {

		// Temporary file.
		String filename = stateSpace.eResource()!=null ? 
				stateSpace.eResource().getURI().trimFileExtension().lastSegment() : "statespace";
		File tmp = File.createTempFile(filename, ".sm");
		URI uri = URI.createFileURI(tmp.getAbsolutePath());
		
		// Do the export:
		PRISMStateSpaceExporter exporter = new PRISMStateSpaceExporter();
		exporter.export(stateSpace, uri, monitor);
		return tmp;
		
	}
	
	/*
	 * Get the name of the PRISM executable.
	 */
	protected String getPRISMExecutable() {
		return isWindows() ? "prism.bat" : "prism";
	}
		
}
