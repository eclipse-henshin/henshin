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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.validation.ValidationResult;

/**
 * PRISM steady-state tool.
 * @author Christian Krause
 */
public class PRISMSteadyStateTool extends AbstractPRISMTool {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator#validate(org.eclipse.emf.henshin.statespace.StateSpace, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public ValidationResult validate(StateSpace stateSpace, IProgressMonitor monitor) throws Exception {
		
		monitor.beginTask("Computing steady-state probabilities...", 2);
		
		// Generate the SM file.
		File smFile = generatePRISMFile(stateSpace);
		monitor.worked(1);
		
		// Invoke the PRISM tool:
		monitor.subTask("Running PRISM...");
		Process process = Runtime.getRuntime().exec(
				new String[] { "prism", smFile.getAbsolutePath(),
							   "-steadystate", "-gaussseidel", "-fixdl"});
		
		// Parse the output
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		List<Integer> states = new ArrayList<Integer>();
		List<Double> probabilities = new ArrayList<Double>();
		
		String line, time = null;
		boolean parseProbabilities = false;
		MessageFormat format = new MessageFormat("{0,number,integer}:({1,number,integer})={2,number}");
		while ((line = reader.readLine())!=null) {
			line = line.trim();
			System.out.println(line);
			if (line.length()==0) {
				parseProbabilities = false;
				continue;
			}
			if (parseProbabilities) {
				Object[] parsed = format.parse(line);
				states.add(((Long)parsed[0]).intValue()); 
				probabilities.add(((Double)parsed[2]).doubleValue()); 
			} else {
				if (line.startsWith("Probabilities:")) {
					parseProbabilities = true;
				} else
				if (line.startsWith("Time")) {
					time = line;
				}
			}
			if (monitor.isCanceled()) {
				process.destroy();
				return null;
			}
		}
		monitor.worked(1);
		
		// Pretty-print the results.
		StringBuffer result = new StringBuffer("Computed steady-state probabilities:\n\n");
		for (int i=0; i<states.size(); i++) {
			result.append("   State " + states.get(i) + ":\t" + probabilities.get(i) + "\n");
		}
		if (time!=null) result.append("\n" + time);
		return new ValidationResult(true, result.toString());
		
	}	
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.Validator#getName()
	 */
	@Override
	public String getName() {
		return "PRISM (steady-states)";
	}
		
}
