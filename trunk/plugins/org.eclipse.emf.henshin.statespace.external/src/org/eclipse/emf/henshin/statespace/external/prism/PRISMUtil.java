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
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceIndex;
import org.eclipse.emf.henshin.statespace.StateSpacePlugin;
import org.eclipse.emf.henshin.statespace.external.prism.RatesPropertiesManager.Rate;
import org.eclipse.emf.henshin.statespace.validation.StateValidator;
import org.eclipse.emf.henshin.statespace.validation.Validator;

/**
 * PRISM utils.
 * @author Christian Krause
 */
public class PRISMUtil {
	
	/**
	 * Invoke PRISM on a state space.
	 * @param stateSpace State space.
	 * @param args Arguments.
	 * @param monitor Monitor.
	 * @return Created process.
	 * @throws Exception On errors.
	 */
	protected static Process invokePRISM(StateSpace stateSpace, File modelFile, File formulaFile, 
			String[] args, boolean allowExperiments, IProgressMonitor monitor) throws Exception {
				
		// Get the executable, path and arguments.
		File path = RatesPropertiesManager.getPRISMPath(stateSpace);
		String baseArgs = RatesPropertiesManager.getPRISMArgs(stateSpace);
		
		// Create the command.
		List<String> command = new ArrayList<String>();
		command.add(path!=null ? new File(path.getAbsolutePath()+File.separator+"prism").getAbsolutePath() : "prism");
		command.add(modelFile.getAbsolutePath());
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
		
		// Now add the constants arguments:
		String constants = "";
		for (Rule rule : stateSpace.getRules()) {
			Rate rate = RatesPropertiesManager.getRate(stateSpace,rule);
			if (rate==null) {
				throw new StateSpaceException("Rate for rule \"" + rule.getName() +
						"\" not defined (set property \"" + RatesPropertiesManager.getRateKey(rule) + "\")");
			}
			if (!allowExperiments && !rate.isConstant()) {
				throw new StateSpaceException("Rate for rule \"" + rule.getName() + "\" must be a constant");
			}
			if (constants.length()>0) {
				constants = constants + ",";
			}
			constants = constants + RatesPropertiesManager.getRateKey(rule) + "=" + rate;
			
		}
		if (constants.length()>0) {
			command.add("-const");
			command.add(constants);
		}
		
		// Now we can invoke the PRISM tool:
		System.out.println(command);
		return Runtime.getRuntime().exec(command.toArray(new String[] {}), null, path);
		
	}
	
	/*
	 * Expand labels.
	 */
	public static String expandLabels(String template, StateSpaceIndex index, IProgressMonitor monitor) throws Exception {

		// Find out how many sections need to be replaced:
		int sections = -1;
		String dummy1 = template;
		String dummy2 = template;
		do {
			dummy1 = dummy2;
			dummy2 = dummy2.replaceFirst("<<<", "xxx");
			sections++;
		} while (!dummy1.equals(dummy2));
		
		// Now do the expansion:
		monitor.beginTask("Expanding labels...", sections);
		for (int i=0; i<sections; i++) {
			template = doExpand(template, index, new SubProgressMonitor(monitor,1));
		}
		monitor.done();
		return template;

	}

	private static String doExpand(String template, StateSpaceIndex index, IProgressMonitor monitor) throws Exception {
		
		// Find <<< ... >>>
		int start = template.indexOf("<<<");
		if (start<0) return template;
		int end = template.indexOf(">>>", start);
		if (end<0) end = template.length();
		else end = end + 3;

		// Get the type: <<<TYPE ... >>>
		String toReplace = template.substring(start, end);
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
		monitor.beginTask("Expanding labels...", index.getStateSpace().getStates().size());
		for (State state : index.getStateSpace().getStates()) {
			if (validator.validate(state, nullMonitor).isValid()) {
				if (result.length()>0) result = result + " | ";
				result = result + "s=" + state.getIndex();
			}
			monitor.worked(1);
			if (monitor.isCanceled()) {
				return template;
			}
		}
		if (result.length()==0) result = "false";
		result = result + ";";

		// Replace the text with the result:
		String expanded = template.substring(0,start) + result + template.substring(end);
		
		// Done:
		monitor.done();
		return expanded;
	}

}
