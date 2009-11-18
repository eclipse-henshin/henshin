package org.eclipse.emf.henshin.statespace.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class forms the interface between the state space
 * tools and the Henshin interpreter engine. It is used to
 * compute new states and for verifying existing states.
 * 
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceTransformer {
	
	/**
	 * A convenience class for matches.
	 * @author Christian Krause
	 */
	public static class Match extends HashMap<EObject,EObject> {
		private static final long serialVersionUID = 1L;		
	}
	
	// TEMPORARY
	private class Rule {};
	
	// List of rules to be used:
	private List<Rule> rules;
	
	/**
	 * Default constructor.
	 */
	public StateSpaceTransformer() {
		this.rules = new ArrayList<Rule>();
	}
	
	/**
	 * Find all matches for a given model instance.
	 * @param model Model instance.
	 * @return All matches.
	 */
	public List<Match> findMatches(Resource model) {
		return null;
	}
	
	/**
	 * Apply a transformation rule. If the in-place flag is set to true,
	 * the input is modified directly. Otherwise a copy is made first.
	 * 
	 * @param input The input model instance.
	 * @param rule The rule to be applied.
	 * @param match The match to be used.
	 * @param inplace in-place flag.
	 * @return The generated output.
	 */
	public Resource applyRule(Resource input, Rule rule, Match match, boolean inplace) {
		
		// The output model instance:
		Resource output;
		if (inplace) {
			output = input;
		} else {
			output = new ResourceImpl();
			output.getContents().addAll(EcoreUtil.copyAll(input.getContents()));
		}
		
		// TODO Use the interpreter to generate the output:
		
		return output;
		
	}
	
	/**
	 * Get the list of rules used by this transformer instance.
	 * @return List of transformation rules.
	 */
	public List<Rule> getRules() {
		return rules;
	}
	
}
