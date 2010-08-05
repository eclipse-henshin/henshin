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
		
		// Convert the LTS to a mCRL2 specification:
		File mcrl2 = File.createTempFile(name, ".mcrl2");
		convertFile(aut, mcrl2, "ltsconvert", "--in=aut", "--out=mcrl2", "--equivalence=bisim");
		monitor.worked(1);													// 50%
		
		// Generate and LPS from the mCRL2 specification:
		File lps = File.createTempFile(name, ".lps");
		convertFile(aut, mcrl2, "ltsconvert");
		monitor.worked(1);													// 60%
		
		// Write the property to a MCL file:
		File mcl = File.createTempFile("property", ".mcl");
		writeToFile(mcl, property);
		monitor.worked(1);													// 70%
		
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
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.Validator#getName()
	 */
	@Override
	public String getName() {
		return "mCRL2";
	}

}
