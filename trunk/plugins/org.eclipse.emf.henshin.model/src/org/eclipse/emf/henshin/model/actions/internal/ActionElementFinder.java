package org.eclipse.emf.henshin.model.actions.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.actions.Action;
import org.eclipse.emf.henshin.model.actions.ActionType;
import org.eclipse.emf.henshin.model.util.HenshinACUtil;

/**
 * Action element finder class.
 */
class ActionElementFinder {	
	
	/*
	 * Get all elements in a rule that are associated with the given argument action.
	 */
	@SuppressWarnings("unchecked")
	static <E extends GraphElement> List<E> getRuleElementCandidates(
			Rule kernel, Action action, EReference containment) {
		
		// Get a list of elements to be checked:
		List<E> candidates = new ArrayList<E>();
		
		// Determine the rules top be checked:
		List<Rule> rules = new ArrayList<Rule>();
		if (action==null || !action.isAmalgamated()) {
			rules.add(kernel);
		}
		if (action==null || action.isAmalgamated()) {
			rules.addAll(kernel.getAllMultiRules());
		}
		
		// Add LHS elements:
		if (action==null || action.getType()==ActionType.DELETE || action.getType()==ActionType.PRESERVE) {
			for (Rule rule : rules) {
				candidates.addAll((List<E>) rule.getLhs().eGet(containment));
			}
		}
		
		// Add RHS elements:
		if (action==null || action.getType()==ActionType.CREATE) {
			for (Rule rule : rules) {
				candidates.addAll((List<E>) rule.getRhs().eGet(containment));				
			}
		}

		// Add PAC elements:
		if (action==null || action.getType()==ActionType.REQUIRE) {
			for (Rule rule : rules) {
				for (NestedCondition pac : HenshinACUtil.getAllACs(rule, true)) {
					candidates.addAll((List<E>) pac.getConclusion().eGet(containment));
				}
			}
		}

		// Add NAC elements:
		if (action==null || action.getType()==ActionType.FORBID) {
			for (Rule rule : rules) {
				for (NestedCondition nac : HenshinACUtil.getAllACs(rule, false)) {
					candidates.addAll((List<E>) nac.getConclusion().eGet(containment));
				}
			}
		}
		
		// Look for the elements that actually match the action:
		return candidates;
		
	}
	
	/*
	 * For an arbitrary element in a rule graph, find the corresponding action element.
	 */
	static <E extends GraphElement> E getActionElement(E element, ActionHelper<E,Rule> helper) {		
		
		// Is the element itself already an action element?
		if (helper.getAction(element)!=null) {
			return element;
		} else {
			
			// Get the mappings:
			EObject container = element.getGraph().eContainer();
			
			if (container instanceof Rule) {
				
				Rule rule = (Rule) container;
				E origin = rule.getMappings().getOrigin(element);
				if (origin==null) origin = element;
				
				// Multi-rule of an amalgamation?
				E originInKernel = rule.getMultiMappings().getOrigin(origin);
				if (originInKernel!=null) {
					return getActionElement(originInKernel, helper);
				}
				return origin;
			}
			else if (container instanceof NestedCondition) {
				// Find the origin in the LHS:
				return getActionElement(((NestedCondition) container).getMappings().getOrigin(element), helper);
			}
			else {
				throw new RuntimeException("Graph neither contained in a Rule nor in a NestedCondition");
			}
			
			
		}
	}

}
