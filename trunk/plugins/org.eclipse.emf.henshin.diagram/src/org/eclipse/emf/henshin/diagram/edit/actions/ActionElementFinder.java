package org.eclipse.emf.henshin.diagram.edit.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.diagram.edit.helpers.AmalgamationEditHelper;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;
import org.eclipse.emf.henshin.model.util.HenshinNACUtil;

/**
 * @generated NOT
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
		
		// Check if the rule is a multi-rule of some amalgamation:
		AmalgamationUnit amalgamation = AmalgamationEditHelper.getAmalgamationFromKernelRule(kernel);
		
		// Determine the rules top be checked:
		List<Rule> rules = new ArrayList<Rule>();
		if (action==null || !action.isAmalgamated()) {
			rules.add(kernel);
		}
		if (action==null || action.isAmalgamated()) {
			if (amalgamation!=null) {
				rules.addAll(amalgamation.getMultiRules());
			}
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
		
		// Add NAC elements:
		if (action==null || action.getType()==ActionType.FORBID) {
			for (NestedCondition nac : HenshinNACUtil.getAllNACs(kernel)) {
				candidates.addAll((List<E>) nac.getConclusion().eGet(containment));
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
				E origin = HenshinMappingUtil.getOrigin(element, rule.getMappings());
				if (origin==null) origin = element;
				
				// Multi-rule of an amalgamation?
				AmalgamationUnit amalgamation = AmalgamationEditHelper.getAmalgamationFromMultiRule(rule);
				if (amalgamation!=null) {
					@SuppressWarnings("unchecked")
					E originInKernel = (E) AmalgamationEditHelper.getPreimageInKernelRule(origin, amalgamation);
					if (originInKernel!=null) {
						return originInKernel;
					}
				}
				return origin;
			}
			else if (container instanceof NestedCondition) {
				// Find the origin in the LHS:
				EList<Mapping> mappings = ((NestedCondition) container).getMappings();
				return HenshinMappingUtil.getOrigin(element, mappings);
			}
			else {
				throw new RuntimeException("Graph neither contained in a Rule nor in a NestedCondition");
			}
			
			
		}
	}

}
