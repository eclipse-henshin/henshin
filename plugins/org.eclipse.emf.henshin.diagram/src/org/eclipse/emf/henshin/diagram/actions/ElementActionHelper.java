/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.diagram.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;
import org.eclipse.emf.henshin.model.util.HenshinNACUtil;

/**
 * Internal utility methods for accessing actions.
 * @generated NOT
 * @author Christian Krause
 */
class ElementActionHelper {
	
	/**
	 * Name of the default NAC.
	 */
	static final String DEFAULT_NAC_NAME = "default";
	
	/**
	 * Determine the action for an element. If this returns <code>null</code>
	 * the element is not considered to be a proper action element and hence
	 * should not be displayed in an integrated rule view.
	 * @param element Element.
	 * @return Action or <code>null</code>.
	 */
	static <T extends EObject> Action getAction(T element) {
		
		// Must be contained in a graph:
		if (!(element.eContainer() instanceof Graph)) return null;
		Graph graph = (Graph) element.eContainer();
		
		// Graph must be part of a rule:
		Rule rule = graph.getContainerRule();
		if (rule==null) return null;
		
		// LHS element?
		if (graph==rule.getLhs()) {
			
			// Check if it is mapped to the RHS:
			T image = HenshinMappingUtil.getImage(element, rule.getRhs(), rule.getMappings());
			if (image!=null) {
				return new Action(ActionType.NONE);
			} else {
				return new Action(ActionType.DELETE);
			}
			
		}
		
		// RHS element?
		else if (graph==rule.getRhs()) {

			// If it has an origin in the LHS, it is a CREATE-action:
			T origin = HenshinMappingUtil.getOrigin(element, rule.getMappings());
			if (origin==null) return new Action(ActionType.CREATE);	
			
		}
		
		// NAC element?
		else if (graph.eContainer() instanceof NestedCondition) {
			
			// If it has an origin in the LHS, it is a NAC-action:
			NestedCondition nac = (NestedCondition) graph.eContainer();
			T origin = HenshinMappingUtil.getOrigin(element, nac.getMappings());
			if (origin==null) {
				if (DEFAULT_NAC_NAME.equals(graph.getName())) {
					return new Action(ActionType.FORBID);
				} else {
					return new Action(ActionType.FORBID, graph.getName());					
				}
			}
			
		}
		
		// At this point we know it is not considered as an action element.
		return null;
		
	}
	
	/**
	 * Get all elements in a rule that are associated with the given argument action.
	 * @param rule Rule.
	 * @return List of action elements.
	 */
	@SuppressWarnings("unchecked")
	static <T extends EObject> List<T> getActionElements(Rule rule, Action action, EReference containment) {
		
		// Get a list of elements to be checked:
		List<T> elements = new ArrayList<T>();
		
		// Add LHS elements:
		if (action==null || action.getType()==ActionType.DELETE) {
			elements.addAll((List<T>) rule.getLhs().eGet(containment));
		}
		
		// Add RHS elements:
		if (action==null || action.getType()==ActionType.CREATE) {
			elements.addAll((List<T>) rule.getRhs().eGet(containment));
		}
		
		// Add NAC elements:
		if (action==null || action.getType()==ActionType.FORBID) {
			for (NestedCondition nac : HenshinNACUtil.getAllNACs(rule)) {
				elements.addAll((List<T>) nac.getConclusion().eGet(containment));
			}
		}
		
		// Look for the elements that actually match the action:
		return filterElementsByAction(elements, action);
		
	}

	/**
	 * Returns a list of all elements of <code>elements</code>, which are
	 * associated with the given <code>action</code>. If <code>action</code> is
	 * null, the returned list contains all elements of the given list.
	 * 
	 * @param elements
	 *            Elements.
	 * @param action
	 *            Action.
	 * @return Action elements.
	 */
	static <T extends EObject> List<T> filterElementsByAction(List<T> elements, Action action) {
		
		// Collect all matching elements:
		List<T> result = new ArrayList<T>();
		for (T element : elements) {
			
			// Check if the current action is ok and add it:
			Action current = getAction(element);
			if (current!=null && (action==null || action.equals(current))) {
				result.add(element);
			}
			
		}
		return result;
		
	}
	
	/**
	 * Find or create a NAC for a FOBIRD action.
	 * @param action FORBID action.
	 * @param rule Rule.
	 * @return The NAC.
	 */
	static NestedCondition getOrCreateNAC(Action action, Rule rule) {
		
		// Make sure the action is of type FORBID.
		if (action==null || action.getType()!=ActionType.FORBID) {
			throw new IllegalArgumentException("NACs can be created only for FORBID actions");
		}
		
		// Determine the name of the NAC:
		String name = DEFAULT_NAC_NAME;
		String[] args = action.getArguments();
		if (args!=null && args.length>0 && args[0]!=null) {
			name = args[0];
		}
		
		// Find or create the NAC:
		NestedCondition nac = HenshinNACUtil.getNAC(rule, name);
		if (nac==null) {
			nac = HenshinNACUtil.createNAC(rule, name);
		}
		
		return nac;
		
	}
	
}
