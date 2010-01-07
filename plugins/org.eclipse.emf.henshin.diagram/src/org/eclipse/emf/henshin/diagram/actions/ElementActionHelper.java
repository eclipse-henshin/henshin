package org.eclipse.emf.henshin.diagram.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinGraphUtil;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;

/**
 * Internal utility methods for accessing actions.
 * @generated NOT
 * @author Christian Krause
 */
class ElementActionHelper {
	
	/**
	 * Determine the action for an element. If this returns <code>null</code>
	 * the element is not considered to be a proper action element and hence
	 * should not be displayed in an integrated rule view.
	 * @param element Element.
	 * @return Action or <code>null</code>.
	 */
	static <T extends EObject> Action getAction(T element) {
		
		// Must be contained in a graph:
		if (element.eContainer() instanceof Graph == false) return null;
		Graph graph = (Graph) element.eContainer();
		
		// Graph must be part of a rule:
		Rule rule = HenshinGraphUtil.getRule(graph);
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
		
		// Otherwise find the origin in the LHS:
		T origin = HenshinMappingUtil.getOrigin(element, rule.getMappings());
		if (origin==null) {
			
			// CREATE-action?
			if (rule.getRhs()==graph) {
				return new Action(ActionType.CREATE);			
			}
			
		}
		
		// This point is not considered as an action-node.
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
		List<T> elements = null;
		if (action!=null) {
			
			ActionType type = action.getType();
			if (type==ActionType.DELETE) {
				elements = (List<T>) rule.getLhs().eGet(containment);
			}
			else if (type==ActionType.CREATE) {
				elements = (List<T>) rule.getRhs().eGet(containment);
			}
			
		}
		
		// Otherwise check all nodes:
		if (elements==null) {
			elements = new ArrayList<T>();
			elements.addAll((List<T>) rule.getLhs().eGet(containment));
			elements.addAll((List<T>) rule.getRhs().eGet(containment));
		}
		
		// Look for the elements that actually match the action:
		return getActionElements(elements, action);
		
	}

	/**
	 * Get elements associated with an action.
	 * @param elements Elements.
	 * @param action Action.
	 * @return Action elements.
	 */
	static <T extends EObject> List<T> getActionElements(List<T> elements, Action action) {
		
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

}
