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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.validation.StateSpaceXYPlot;
import org.eclipse.emf.henshin.statespace.validation.ValidationResult;

/**
 * PRISM state space validator.
 * @author Christian Krause
 */
public class PRISMStateSpaceValidator extends AbstractPRISMTool {
	
	/*
	 * English number format (used for parsing and printing).
	 */
	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.ENGLISH);
	
	/*
	 * Internal data class for PRISM experiments:
	 */
	public class Experiment {
		public Map<String,Double> constants = new LinkedHashMap<String,Double>();
		public double result = 0;
	}
	
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
		Process process = invokePRISM(stateSpace, cslFile, null, true, monitor);
		
		// Parse the experiments:
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		List<Experiment> experiments = new ArrayList<Experiment>();
		Experiment next;
		while ((next = parseExperiment(reader, monitor))!=null) {
			experiments.add(next);
			if (monitor.isCanceled()) {
				process.destroy();
				return null;
			}
		}
		
		// There must be at least one experiment:
		if (experiments.isEmpty()) {
			throw new RuntimeException("Unexpected PRISM output");
		}
		
		// Single result or a plot?
		if (experiments.size()==1) {
			return new ValidationResult(true, "Result: " + NUMBER_FORMAT.format(experiments.get(0).result));
		} else {
						
			// Find out which parameters are changing:
			List<String> changing = new ArrayList<String>();
			for (String param : experiments.get(0).constants.keySet()) {
				for (int i=1; i<experiments.size(); i++) {
					double val1 = experiments.get(i-1).constants.get(param);
					double val2 = experiments.get(i).constants.get(param);
					if (val1!=val2) {
						changing.add(param);
						break;
					}
				}
			}
			
			// Find out which parameter to use for the X-axis:
			String experimentWith = changing.get(0);
			String userPreference = RatesPropertiesManager.getPRISMExperiment(stateSpace);
			if (userPreference!=null && changing.contains(userPreference)) {
				experimentWith = userPreference;
			}
			
			// X-values for all plots:
			List<Double> theXValues = new ArrayList<Double>();
			for (Experiment experiment : experiments) {
				Double value = experiment.constants.get(experimentWith);
				if (!theXValues.contains(value)) theXValues.add(value);
			}

			// Create the plots:
			int count = experiments.size() / theXValues.size();
			double[][] xValues = new double[count][];
			double[][] yValues = new double[count][];
			
			// Now create the plots:
			for (int i=0; i<count; i++) {
				
				// X-values for this plot:
				xValues[i] = new double[theXValues.size()];
				for (int j=0; j<xValues[i].length; j++) {
					xValues[i][j] = theXValues.get(j);
				}
				
				// Y-values for this plot:
				yValues[i] = new double[xValues[i].length];
				for (int j=0; j<yValues[i].length; j++) {
					yValues[i][j] = experiments.get(i*xValues[i].length+j).result;
					
				}
				
			}
			
			// Almost done...
			StateSpaceXYPlot plot = new StateSpaceXYPlot(experimentWith, "Result", xValues, yValues);
			return new ValidationResult(true, null, plot);
			
		}
		
		
	}	
	
	/*
	 * Parse the output of PRISM.
	 */
	private Experiment parseExperiment(BufferedReader reader, IProgressMonitor monitor) throws Exception {
		
		// Create a new experiment object:
		Experiment experiment = new Experiment();
		String line = null, error = null;
		boolean parseError = false;
		
		// Parse the PRISM output:
		while ((line = reader.readLine())!=null) {
			
			// The next line:
			line = line.trim();
			if (line.length()==0) {
				if (parseError) {
					throw new RuntimeException(error);
				} else {
					continue;
				}
			}
			
			// Parse error?
			if (parseError) {
				error = error + "\n" + line;
			}
			else if (line.startsWith("Error")) {
				error = line;
				parseError = true;
			}
			else if (line.startsWith("Model constants:") && experiment.constants.isEmpty()) {
				System.out.println("\n" + line);
				line = line.substring("Model constants:".length()).trim();
				String[] constants = line.split(",");
				for (String constant : constants) {
					String[] keyval = constant.split("=");
					if (keyval.length!=2) throw new RuntimeException("Unexpected PRISM output");
					experiment.constants.put(keyval[0], NUMBER_FORMAT.parse(keyval[1]).doubleValue());
				}
			}
			else if (line.startsWith("Result:")) {
				System.out.println(line);
				line = line.substring("Result:".length()).trim();
				experiment.result = NUMBER_FORMAT.parse(line).doubleValue();
				return experiment; // done!
			}
			
			// Cancel?
			if (monitor.isCanceled()) {
				return null;
			}
		}
		
		// No result.
		return null;
		
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
