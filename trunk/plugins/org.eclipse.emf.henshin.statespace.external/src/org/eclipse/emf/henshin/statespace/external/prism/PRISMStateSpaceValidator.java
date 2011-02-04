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

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.validation.ValidationResult;

/**
 * PRISM state space validator.
 * @author Christian Krause
 */
public class PRISMStateSpaceValidator extends AbstractPRISMTool {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator#validate(org.eclipse.emf.henshin.statespace.StateSpace, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public ValidationResult validate(StateSpace stateSpace, IProgressMonitor monitor) throws Exception {
		
		monitor.beginTask("Validating CSL property...", -1);
		
		// Generate the CSL file.
		File cslFile = createTempFile("property", ".csl", property);
		
		// Invoke the PRISM tool:
		monitor.subTask("Running PRISM...");
		Process process = invokePRISM(stateSpace, cslFile, null, monitor);
		
		// Parse the output
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		String line, error = null;
		boolean parseError = false;
		StringBuffer result = new StringBuffer();
		
		while ((line = reader.readLine())!=null) {
			
			line = line.trim();
			System.out.println(line);
			if (line.length()==0) {
				parseError = false;
				continue;
			}
			
			// Parse error?
			if (parseError) {
				error = error + "\n" + line;
			} else if (line.startsWith("Error")) {
				error = line;
				parseError = true;
			} else {
				result.append(line+"\n");				
			}
			
			if (monitor.isCanceled()) {
				process.destroy();
				return null;
			}
		}
		monitor.done();
		return new ValidationResult(true, result.toString());
		
	}	
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.Validator#getName()
	 */
	@Override
	public String getName() {
		return "PRISM";
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.Validator#usesProperty()
	 */
	@Override
	public boolean usesProperty() {
		return true;
	}
	
}
