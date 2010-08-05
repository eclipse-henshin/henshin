package org.eclipse.emf.henshin.statespace.cadp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.ValidationResult;
import org.eclipse.emf.henshin.statespace.util.StateSpaceSearch;

/**
 * CADP state space validator.
 * @author Christian Krause
 */
public class CADPStateSpaceValidator extends AbstractStateSpaceValidator {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceValidator#validate(org.eclipse.emf.henshin.statespace.StateSpace, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public ValidationResult validate(StateSpace stateSpace, IProgressMonitor monitor) throws Exception {
		
		monitor.beginTask("Validating property", 10);
		String name = stateSpace.eResource().getURI().trimFileExtension().lastSegment();
		
		// Check the CADP path first:
		File cadpBin = getCADPBin();
		
		// Export the state space to an AUT file:
		File aut = File.createTempFile(name, ".aut");
		exportAsAUT(stateSpace, aut, new SubProgressMonitor(monitor, 4));		// 40%
		
		// Convert the AUT file to a BCG file:
		File bcg = File.createTempFile(name, ".bcg");
		convertFile(aut, bcg, cadpBin.getAbsolutePath() + File.separator + "bcg_io");
		monitor.worked(2);														// 60%
		
		// Write the property to a MCL file:
		File mcl = File.createTempFile("property", ".mcl");
		writeToFile(mcl, property);
		monitor.worked(1);														// 70%
		
		// File for diagnostics output:
		File diag = File.createTempFile(name, ".bcg");
		
		// Invoke the bcg_open script to run the evaluator:
		Process process = Runtime.getRuntime().exec(new String[] {
				cadpBin.getParent() + File.separator + "com/bcg_open",
				bcg.getAbsolutePath(),
				"evaluator",
				"-diag",
				diag.getAbsolutePath(),
				mcl.getAbsolutePath(),
		});
		
		// Parse the output
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		Boolean result = null;
		boolean parseTrace = false;
		List<String> path = new ArrayList<String>();
		monitor.worked(1);														// 80%
		
		String line;
		while ((line = reader.readLine())!=null) {
			line = line.trim();
			if (line.length()==0) continue;
			if (parseTrace) {
				if (line.indexOf("<goal state>")>=0) {
					parseTrace = false;
				} else {
					path.add(line.startsWith("\"") ? line.substring(1, line.length()-1) : line);
				}
			} else {
				if ("TRUE".equals(line)) {
					result = Boolean.TRUE;
				}
				else if ("FALSE".equals(line)) {
					result = Boolean.FALSE;
				}
				else if (line.indexOf("<initial state>")>=0) {
					parseTrace = true;
				}
			}
		}
		monitor.worked(1);														// 90%
		
		// Clean up first:
		aut.delete();
		bcg.delete();
		mcl.delete();
		diag.delete();
		
		monitor.worked(1);														// 100%
		monitor.done();
		
		// Check the output:
		if (result==Boolean.TRUE) {
			return ValidationResult.VALID;
		}
		else if (result==Boolean.FALSE) {
			if (!path.isEmpty()) {
				return new ValidationResult(false, StateSpaceSearch.findTrace(stateSpace, path));
			}
			return ValidationResult.INVALID;			
		}
		else {
			throw new RuntimeException("CADP evaluator produced unexpected output.");
		}
		
	}
	
	/**
	 * Get the CADP 'bin.*' directory.
	 * @return The directory.
	 * @throws FileNotFoundException If the directory was not found.
	 */
	public static File getCADPBin() throws FileNotFoundException {
		String path = System.getenv("CADP");
		if (path==null) {
			throw new FileNotFoundException("CADP environment variable not set.");
		}
		File cadp = new File(path);
		if (!cadp.isDirectory()) {
			throw new FileNotFoundException("CADP home not found. Check the CADP environment variable.");
		}
		File[] bin = cadp.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith("bin");
			}
		});
		if (bin.length==0) {
			throw new FileNotFoundException("Cannot find 'bin' directory in CADP home.");
		}
		return bin[0];
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.Validator#getName()
	 */
	@Override
	public String getName() {
		return "CADP";
	}

}
