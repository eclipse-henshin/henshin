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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.external.AbstractFileBasedValidator;
import org.eclipse.emf.henshin.statespace.properties.ParametersPropertiesManager;
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
		String actions = createActions(stateSpace);
		System.out.println(actions + "\n");
		File act = createTempFile(name, ".mcrl2", actions);
		
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
	private String createActions(StateSpace stateSpace) throws StateSpaceException {
		StringBuffer actions = new StringBuffer();
		if (!stateSpace.getEqualityHelper().isIgnoreNodeIDs()) {
			Map<EClass,Integer> maxIDs = getMaxIDs(stateSpace);
			for (EClass type : maxIDs.keySet()) {
				actions.append("sort " + type.getName() + " = struct ");
				char prefix = type.getName().toLowerCase().charAt(0);
				int max = maxIDs.get(type);
				if (max<0) max = 0;
				for (int i=0; i<=max; i++) {
					actions.append(String.valueOf(prefix) + i + ((i<max) ? " | " : ";\n"));
				}
			}
		}
		actions.append("act ");
		for (int i=0; i<stateSpace.getRules().size(); i++) {
			Rule rule = stateSpace.getRules().get(i);
			actions.append(rule.getName());
			if (!stateSpace.getEqualityHelper().isIgnoreNodeIDs()) {
				actions.append(" : ");
				List<Node> params = ParametersPropertiesManager.getParameters(stateSpace,rule);
				for (int j=0; j<params.size(); j++) {
					actions.append(params.get(j).getType().getName());
					if (j<params.size()-1) actions.append(" # ");
				}
			}
			actions.append("; ");
		}
		return actions.toString() + "\n";
	}

	
	private Map<EClass,Integer> getMaxIDs(StateSpace stateSpace) throws StateSpaceException {
		
		// Get the parameter types for all rules:
		Map<Rule,List<EClass>> paramTypes = new HashMap<Rule,List<EClass>>();
		for (Rule rule : stateSpace.getRules()) {
			List<Node> nodes = ParametersPropertiesManager.getParameters(stateSpace, rule);
			List<EClass> types = new ArrayList<EClass>();
			for (Node node : nodes) types.add(node.getType());
			paramTypes.put(rule,types);
		}
		
		// Now compute the max IDs:
		Map<EClass,Integer> max = new HashMap<EClass,Integer>();
		for (State state : stateSpace.getStates()) {
			for (Transition transition : state.getOutgoing()) {
				List<EClass> params = paramTypes.get(transition.getRule());
				int[] ids = transition.getParameterIDs();
				int count = Math.min(ids.length, params.size());
				for (int i=0; i<count; i++) {
					EClass type = params.get(i);
					if (!max.containsKey(type)) {
						max.put(type, 0);
					}
					if (max.get(type)<ids[i]) {
						max.put(type, ids[i]);
					}
				}
			}
		}
		return max;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.Validator#getName()
	 */
	@Override
	public String getName() {
		return "mCRL2";
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
