package org.eclipse.emf.henshin.statespace.external.prism;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpaceIndex;
import org.eclipse.emf.henshin.statespace.StateSpacePlugin;
import org.eclipse.emf.henshin.statespace.validation.StateValidator;
import org.eclipse.emf.henshin.statespace.validation.Validator;


public class PRISMLabelExpander {

	/*
	 * Expand labels.
	 */
	public static String expandLabels(String template, StateSpaceIndex index) throws Exception {

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
		for (State state : index.getStateSpace().getStates()) {
			if (validator.validate(state, nullMonitor).isValid()) {
				if (result.length()>0) result = result + " | ";
				result = result + "s=" + state.getIndex();
			}
		}
		if (result.length()==0) result = "false";
		result = result + ";";

		// Replace the text with the result:
		String expanded = template.substring(0,start) + result + template.substring(end);
		
		// Do the rest:
		expanded = expandLabels(expanded, index);
		
		// Done:
		return expanded;
	}

}
