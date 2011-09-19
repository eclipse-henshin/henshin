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
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpacePlugin;
import org.eclipse.emf.henshin.statespace.validation.StateSpaceXYPlot;
import org.eclipse.emf.henshin.statespace.validation.StateValidator;
import org.eclipse.emf.henshin.statespace.validation.ValidationResult;
import org.eclipse.emf.henshin.statespace.validation.Validator;

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
		File cslFile = createTempFile("property", ".csl", preprocessProperty(property,stateSpace,monitor));
		
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
			Set<String> parameters = experiments.get(0).constants.keySet();
			List<String> changing = new ArrayList<String>();
			for (String param : parameters) {
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
			String variable = changing.get(0);
			String userPreference = RatesPropertiesManager.getPRISMExperiment(stateSpace);
			if (userPreference!=null && changing.contains(userPreference)) {
				variable = userPreference;
			}
			
			// Now partition the experiments into plots:
			List<List<Experiment>> plots = new ArrayList<List<Experiment>>();
			for (Experiment experiment1 : experiments) {
				boolean found = false;
				for (List<Experiment> plot : plots) {
					Experiment experiment2 = plot.get(0);
					boolean compatible = true;
					for (String param : parameters) {
						Double val1 = experiment1.constants.get(param);
						Double val2 = experiment2.constants.get(param);
						if (!param.equals(variable) && !val1.equals(val2)) {
							compatible = false; 
							break;
						}
					}
					if (compatible) {
						plot.add(experiment1);
						found = true;
						break;
					}
				}
				if (!found) {
					plots.add(new ArrayList<Experiment>());
					plots.get(plots.size()-1).add(experiment1);
				}
			}
			
			// Create the plots:
			double[][] xValues = new double[plots.size()][];
			double[][] yValues = new double[plots.size()][];
			
			// Now create the plots:
			for (int i=0; i<plots.size(); i++) {
				
				// The current plot:
				List<Experiment> plot = plots.get(i);
				int length = plot.size();
				
				// X- and Y-values for this plot:
				xValues[i] = new double[length];
				yValues[i] = new double[length];
				for (int j=0; j<length; j++) {
					xValues[i][j] = plot.get(j).constants.get(variable);
					yValues[i][j] = plot.get(j).result;
				}
								
			}
			
			// Create the legend:
			String[] legend = null;
			if (plots.size()>1) {
				legend = new String[plots.size()];
				for (int i=0; i<legend.length; i++) {
					legend[i] = "";
					for (String param : changing) {
						if (param.equals(variable)) continue;
						if (legend[i].length()>0) {
							legend[i] = legend[i] + ",";
						}
						String value = NUMBER_FORMAT.format(experiments.get(i).constants.get(param));
						legend[i] = legend[i] + param + "=" + value;
					}
					
				}
			}
			
			// Almost done...
			StateSpaceXYPlot plot = new StateSpaceXYPlot(variable, "Result", xValues, yValues, legend);
			return new ValidationResult(true, null, plot);
			
		}
			
	}	
	
	/*
	 * Pre-process property.
	 */
	private String preprocessProperty(String property, StateSpace stateSpace, IProgressMonitor monitor) throws Exception {
		while (!monitor.isCanceled()) {
			
			// Find <<< ... >>>
			int start = property.indexOf("<<<");
			if (start<0) break;
			int end = property.indexOf(">>>", start);
			if (end<0) end = property.length();
			else end = end + 3;
			
			// Get the type: <<<TYPE ... >>>
			String toReplace = property.substring(start, end);
			String type = "";
			for (int i=3; i<toReplace.length(); i++) {
				if (Character.isWhitespace(toReplace.charAt(i))) {
					break;
				}
				type = type + toReplace.charAt(i);
			}
			
			// Find the state validator:
			StateValidator validator = null;
			for (Validator v : StateSpacePlugin.INSTANCE.getValidators().values()) {
				if (v.getName().startsWith(type) && v instanceof StateValidator) {
					validator = (StateValidator) v;
					break;
				}
			}
			if (validator==null) {
				throw new RuntimeException("Unknown validator \"" + type + "\"");
			}
			
			// Find all states which satisfy the property:
			String theProperty = toReplace.substring(3+type.length(), toReplace.length()-3).trim();
			validator.setStateSpaceIndex(index);
			validator.setProperty(theProperty);
			String result = "";
			IProgressMonitor nullMonitor = new NullProgressMonitor();
			for (State state : stateSpace.getStates()) {
				if (validator.validate(state, nullMonitor).isValid()) {
					if (result.length()>0) result = result + " | ";
					result = result + "s=" + state.getIndex();
				}
			}
			if (result.length()==0) result = "false";
			result = result + ";";
			
			// Replace the text with the result:
			property = property.substring(0,start) + result + property.substring(end);
			
		}
		return property;
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
