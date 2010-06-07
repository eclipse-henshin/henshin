package org.eclipse.emf.henshin.diagram.edit.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
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
			Rule rule, Action action, EReference containment) {
		
		// Get a list of elements to be checked:
		List<E> candidates = new ArrayList<E>();
		
		// Add LHS elements:
		if (action==null || action.getType()==ActionType.DELETE) {
			candidates.addAll((List<E>) rule.getLhs().eGet(containment));
		}
		
		// Add RHS elements:
		if (action==null || action.getType()==ActionType.CREATE) {
			candidates.addAll((List<E>) rule.getRhs().eGet(containment));
		}
		
		// Add NAC elements:
		if (action==null || action.getType()==ActionType.FORBID) {
			for (NestedCondition nac : HenshinNACUtil.getAllNACs(rule)) {
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
			EList<Mapping> mappings = null;
			
			if (container instanceof Rule) {
				mappings = ((Rule) container).getMappings();
			}
			else if (container instanceof NestedCondition) {
				mappings = ((NestedCondition) container).getMappings();
			}
			else {
				throw new RuntimeException("Graph neither contained in a Rule nor in a NestedCondition");
			}
			
			// Find the origin in the LHS:
			return HenshinMappingUtil.getOrigin(element, mappings);
			
		}
	}

}
