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
package org.eclipse.emf.henshin.statespace.external.mcrl2;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.external.AbstractFileBasedValidator;
import org.eclipse.emf.henshin.statespace.validation.ValidationResult;

/**
 * mCRL2 state space validator.
 * @author Christian Krause
 */
public class MCRL2StateSpaceValidator extends AbstractFileBasedValidator {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceValidator#validate(org.eclipse.emf.henshin.statespace.StateSpace, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public ValidationResult validate(StateSpace stateSpace, IProgressMonitor monitor) throws Exception {
		
		monitor.beginTask("Validating property...", 10);
		String name = stateSpace.eResource().getURI().trimFileExtension().lastSegment();
		
		// Export the state space to an AUT file:
		File aut = exportAsAUT(stateSpace, new SubProgressMonitor(monitor,4));	
		if (monitor.isCanceled()) return null;								// 40%
		
		// Minimize the LTS:
		File min = File.createTempFile(name, ".aut");
		convertFile(aut, min, new SubProgressMonitor(monitor,1), 
				"ltsconvert", "--equivalence=bisim");						// 50%
		if (monitor.isCanceled()) return null;
		
		// Create a dummy mCRL2 specification with the action declarations:
		File act = createTempFile(name, ".mcrl2", createActions(stateSpace));
		
		// Convert the LTS to a LPS:
		File lps = File.createTempFile(name, ".lps");
		convertFile(min, lps, new SubProgressMonitor(monitor,1), 
				"lts2lps", "--data=" + act.getAbsolutePath());				// 60%
		if (monitor.isCanceled()) return null;
		
		// Write the property to a MCL file:
		File mcl = createTempFile("property", ".mcl", property);
		
		// Generate a PBES from the LPS and the formula:
		File pbes = File.createTempFile(name, ".pbes");
		convertFile(lps, pbes, new SubProgressMonitor(monitor,2),
				"lps2pbes", "--formula=" + mcl.getAbsolutePath());			// 80%
		if (monitor.isCanceled()) return null;
		
		// Evaluate the PBES:
		monitor.subTask("Running pbes2bool...");
		String[] pbes2bool = new String[] { "pbes2bool", pbes.getAbsolutePath() };
		if (System.getProperty("os.name").startsWith("Linux")) {					// increase stack size
			pbes2bool = new String[] { "bash", "-c", "ulimit -s unlimited; pbes2bool " + pbes.getAbsolutePath() };
		}
		Process process = Runtime.getRuntime().exec(pbes2bool);
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		Boolean result = null;
		
		// Read the output:
		String line;
		while ((line = reader.readLine())!=null) {
			line = line.trim();
			System.out.println("pbes2bool:" + line);
			if (line.startsWith("The solution for the initial variable of the pbes is")) {
				if (line.endsWith("true")) result = Boolean.TRUE; 
				else if (line.endsWith("false")) result = Boolean.FALSE; 
				else throw new RuntimeException("pbes2bool produced unexpected output: " + line);
				break;
			}
			if (monitor.isCanceled()) {
				process.destroy();
				return null;
			}
		}
		process.waitFor();
		monitor.worked(1);													// 90%
		
		// Clean up. Don't delete the AUT file cause it is cached.
		min.delete();
		act.delete();
		lps.delete();
		mcl.delete();
		pbes.delete();
		
		monitor.worked(1);													// 100%
		monitor.done();
		
		// Check the result:
		if (result==Boolean.TRUE) {
			return ValidationResult.VALID;
		} else if (result==Boolean.FALSE) {
			return ValidationResult.INVALID;			
		} else {
			throw new RuntimeException("pbes2bool produced no output.");
		}
		
	}
	
	/*
	 * Create a string representations of the used actions.
	 */
	private String createActions(StateSpace stateSpace) {
		String actions = "act ";
		for (int i=0; i<stateSpace.getRules().size(); i++) {
			actions = actions + stateSpace.getRules().get(i).getName();
			if (i<stateSpace.getRules().size()-1) actions = actions + ", ";
		}
		return actions + ";";
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.Validator#getName()
	 */
	@Override
	public String getName() {
		return "mCRL2";
	}

}
