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
package org.eclipse.emf.henshin.statespace.cadp;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.ValidationResult;

/**
 * mCRL2 state space validator.
 * @author Christian Krause
 */
public class MCRL2StateSpaceValidator extends AbstractStateSpaceValidator {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceValidator#validate(org.eclipse.emf.henshin.statespace.StateSpace, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public ValidationResult validate(StateSpace stateSpace, IProgressMonitor monitor) throws Exception {
		
		monitor.beginTask("Validating property", 10);
		String name = stateSpace.eResource().getURI().trimFileExtension().lastSegment();
		
		// Export the state space to an AUT file:
		File aut = File.createTempFile(name, ".aut");
		exportAsAUT(stateSpace, aut, new SubProgressMonitor(monitor,4));	// 40%
		
		// Create a dummy mCRL2 specification:
		File mcrl2 = File.createTempFile(name, ".mcrl2");
		writeToFile(mcrl2, getDummySpecification(stateSpace));
		
		// Convert the dummy specification to an LPS:
		File lps = File.createTempFile(name, ".lps");
		convertFile(mcrl2, lps, "mcrl22lps");
		monitor.worked(1);													// 50%
		
		// Now convert the LTS to the real mCRL2 specification:
		convertFile(aut, mcrl2, "ltsconvert", "--equivalence=bisim", "--lps="+lps.getAbsolutePath());
		monitor.worked(1);													// 60%
		
		// Generate the LPS from the mCRL2 specification:
		convertFile(mcrl2, lps, "mcrl22lps");
		monitor.worked(1);													// 70%
		
		// Write the property to a MCL file:
		File mcl = File.createTempFile("property", ".mcl");
		writeToFile(mcl, property);
		
		// Generate a PBES from the LPS and the formula:
		File pbes = File.createTempFile(name, ".pbes");
		convertFile(lps, pbes, "lps2pbes", "--formula=" + mcl.getAbsolutePath());
		monitor.worked(1);													// 80%
		
		// Evaluate the PBES:
		Process process = Runtime.getRuntime().exec(new String[] { "pbes2bool", pbes.getAbsolutePath() } );
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		Boolean result = null;
		
		// Read the output:
		String line;
		while ((line = reader.readLine())!=null) {
			line = line.trim();
			if (line.startsWith("The solution for the initial variable of the pbes is")) {
				if (line.endsWith("true")) result = Boolean.TRUE; 
				else if (line.endsWith("false")) result = Boolean.TRUE; 
				else throw new RuntimeException("pbes2bool produced unexpected output: " + line);
				break;
			}
		}
		process.waitFor();
		monitor.worked(1);													// 90%
		
		// Clean up:
		aut.delete();
		mcrl2.delete();
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
			throw new RuntimeException("pbes2bool produced unexpected output.");
		}
		
	}
	
	/*
	 * Create a dummy specification with no behavior.
	 */
	private String getDummySpecification(StateSpace stateSpace) {
		String spec = "act ";
		for (int i=0; i<stateSpace.getRules().size(); i++) {
			spec = spec + stateSpace.getRules().get(i).getName();
			if (i<stateSpace.getRules().size()-1) spec = spec + ", ";
		}
		return spec + ";\n\ninit delta;\n";
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
