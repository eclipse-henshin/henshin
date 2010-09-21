package org.eclipse.emf.henshin.diagram.edit.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.diagram.edit.helpers.AmalgamationEditHelper;
import org.eclipse.emf.henshin.diagram.edit.maps.MapEditor;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinNACUtil;

/**
 * @generated NOT
 * @author Christian Krause
 */
public abstract class AbstractActionHelper<E extends EObject,C extends EObject> implements ActionHelper<E,C> {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.edit.actions.ActionHelper#getAction(java.lang.Object)
	 */
	public Action getAction(E element) {
		
		// Get the graph and the rule:
		Graph graph = getGraph(element);
		if (graph==null) return null;
		Rule rule = graph.getContainerRule();
		if (rule==null) return null;
		
		// Get the amalgamation unit, if existing:
		AmalgamationUnit amalgamation = 
			AmalgamationEditHelper.getAmalgamationFromMultiRule(rule);
		
		// Check if the element is amalgamated:
		boolean isAmalgamated = 
			isAmalgamated(element, amalgamation);
		
		// Get the amalgamation parameters:
		String[] amalgamationParams = 
			getAmalgamationParameters(element, rule, amalgamation);
		
		// If the rule is a multi-rule, but the action is not
		// amalgamated, the element is not an action element.
		if (amalgamation!=null && !isAmalgamated) {
			return null;
		}
		
		// Map editor.
		MapEditor<E> editor;
		
		// LHS element?
		if (graph==rule.getLhs()) {
			
			// Try to get the image in the RHS:
			editor = getMapEditor(rule.getRhs());
			E image = editor.getOpposite(element);
			
			// Check if it is mapped to the RHS:
			if (image!=null) {
				return new Action(ActionType.PRESERVE, 
						isAmalgamated, amalgamationParams);
			} else {
				return new Action(ActionType.DELETE,
						isAmalgamated, amalgamationParams);
			}
			
		}
		
		// RHS element?
		else if (graph==rule.getRhs()) {

			// Try to get the origin in the LHS:
			editor = getMapEditor(rule.getRhs());
			E origin = editor.getOpposite(element);
			
			// If it has an origin in the LHS, it is a CREATE-action:
			if (origin==null) {
				return new Action(ActionType.CREATE,
						isAmalgamated, amalgamationParams);
			}
			
		}
		
		// NAC element?
		else if (isNAC(graph)) {
			
			// Try to get the origin in the LHS:
			editor = getMapEditor(graph);
			E origin = editor.getOpposite(element);
			
			// If it has an origin in the LHS, it is a NAC-action:
			if (origin==null) {
				if (ActionNACUtil.DEFAULT_NAC_NAME.equals(graph.getName())) {
					return new Action(ActionType.FORBID);
				} else {
					return new Action(ActionType.FORBID, graph.getName());					
				}
			}
			
		}
		
		// At this point we know it is not considered as an action element.
		return null;
		
	}
		
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.edit.actions.ActionHelper#setAction(java.lang.Object, org.eclipse.emf.henshin.diagram.edit.actions.Action)
	 */
	public void setAction(E element, Action action) {
		
		// Check the current action.
		Action current = getAction(element);
		if (current==null) {
			throw new IllegalArgumentException();
		}
		if (action.equals(current)) return;
		
		// Get the container graph and rule.
		Graph graph = getGraph(element);
		Rule rule = graph.getContainerRule();

		// Map editor.
		MapEditor<E> editor;
		
		// Current action type = NONE?
		if (current.getType()==ActionType.PRESERVE) {
			
			// We know that the element is contained in the LHS and that it is mapped to a node in the RHS.
			editor = getMapEditor(rule.getRhs());
			E image = editor.getOpposite(element);
			
			// For DELETE actions, delete the image in the RHS:
			if (action.getType()==ActionType.DELETE) {
				editor.remove(image);
			}
			
			// For CREATE actions, replace the image in the RHS by the origin:
			else if (action.getType()==ActionType.CREATE) {
				editor.replace(image);
			}
			
			// For FORBID actions, delete the image in the RHS and move the node to the NAC:
			else if (action.getType()==ActionType.FORBID) {
				editor.remove(image);
				
				// Move the node to the NAC:
				NestedCondition nac = ActionNACUtil.getOrCreateNAC(action, rule);
				editor = getMapEditor(nac.getConclusion());
				editor.move(element);
				
			}
			
		}
		
		// Current action type = CREATE?
		else if (current.getType()==ActionType.CREATE) {
			
			// We know that the element is contained in the RHS and that it is not an image of a mapping.
			editor = getMapEditor(rule.getRhs());
			
			// We move the element to the LHS if the action type has changed:
			if (action.getType()!=ActionType.CREATE) {
				editor.move(element);
			}
			
			// For NONE actions, create a copy of the element in the RHS and map to it:
			if (action.getType()==ActionType.PRESERVE) {
				editor.copy(element);
			}
			
			// For FORBID actions, move the element further to the NAC:
			else if (action.getType()==ActionType.FORBID) {
				NestedCondition nac = ActionNACUtil.getOrCreateNAC(action, rule);
				editor = getMapEditor(nac.getConclusion());
				editor.move(element);
			}
			
		}

		// Current action type = DELETE?
		else if (current.getType()==ActionType.DELETE) {
			
			// We know that the element is contained in the LHS and that it has no image in the RHS.
			editor = getMapEditor(rule.getRhs());
			
			// For PRESERVE actions, create a copy of the element in the RHS and map to it:
			if (action.getType()==ActionType.PRESERVE) {
				editor.copy(element);
			}
			
			// For CREATE actions, move the element to the RHS:
			else if (action.getType()==ActionType.CREATE) {
				editor.move(element);
			}
			
			// For FORBID actions, move the element to the NAC:
			else if (action.getType()==ActionType.FORBID) {
				NestedCondition nac = ActionNACUtil.getOrCreateNAC(action, rule);
				editor = getMapEditor(nac.getConclusion());
				editor.move(element);
			}
			
		}		
		
		// Current action type = FORBID?
		else if (current.getType()==ActionType.FORBID) {
			
			// We know that the element is contained in a NAC and that it has no origin in the LHS.
			NestedCondition nac = (NestedCondition) graph.eContainer();
			editor = getMapEditor(nac.getConclusion());
			
			// We move the element to the LHS is the action type has changed:
			if (action.getType()!=ActionType.FORBID) {
				editor.move(element);
			}
			
			// For PRESERVE actions, create a copy in the RHS as well:
			if (action.getType()==ActionType.PRESERVE) {
				editor = getMapEditor(rule.getRhs());
				editor.copy(element);
			}

			// For FORBID actions, move the element to the new NAC:
			else if (action.getType()==ActionType.FORBID) {
				NestedCondition newNac = ActionNACUtil.getOrCreateNAC(action, rule);
				editor = getMapEditor(newNac.getConclusion());
				editor.move(element);
			}
			
			// Delete the NAC is it became empty or trivial due to the current change.
			if (HenshinNACUtil.isTrivialNAC(nac)) {
				HenshinNACUtil.removeNAC(rule, nac);
			}
			
		}
		
		// The action type is correct now.
		
		// We check now whether the amalgamation parameters are different.
		if (current.isAmalgamated()!=action.isAmalgamated()) {
			if (action.isAmalgamated()) {
				
			} else {
				
			}
		}
		
	}
	
	/*
	 * Get the container graph for an element.
	 */
	protected Graph getGraph(E e) {
		EObject current = e.eContainer();
		while (current!=null) {
			if (current instanceof Graph) return (Graph) current;
			current = current.eContainer();
		}
		return null;
	}
	
	/*
	 * Check if a graph belongs to a NAC.
	 */
	protected boolean isNAC(Graph graph) {
		return (graph.eContainer() instanceof NestedCondition);
	}
	
	/*
	 * Create a new map editor for a given target graph.
	 */
	protected abstract MapEditor<E> getMapEditor(Graph target);
	
	/*
	 * Returns a list of all elements of <code>elements</code>, which are
	 * associated with the given <code>action</code>. If <code>action</code> is
	 * null, the returned list contains all elements of the given list.
	 */
	protected List<E> filterElementsByAction(List<E> elements, Action action) {
		
		// Collect all matching elements:
		List<E> result = new ArrayList<E>();
		for (E element : elements) {
			
			// Check if the current action is ok and add it:
			Action current = getAction(element);
			if (current!=null && (action==null || action.equals(current))) {
				result.add(element);
			}
			
		}
		return result;
		
	}
	
	/*
	 * Helper method for checking whether the action of an element
	 * should be amalgamated.
	 */
	private boolean isAmalgamated(E element, AmalgamationUnit amalgamation) {
		if (amalgamation!=null &&
			element instanceof GraphElement) {
			try {
				return AmalgamationEditHelper.hasPreimageInKernelRule(
							(GraphElement) element, amalgamation);
			} catch (IllegalArgumentException e) {}
		}
		return false;
	}
	
	/*
	 * If an element has an amalgamated action, this method
	 * returns the proper parameters for the amalgamated action.
	 */
	private String[] getAmalgamationParameters(E element, 
			Rule multiRule, AmalgamationUnit amalgamation) {
		if (!isAmalgamated(element, amalgamation)) {
			return new String[] {};
		}
		String name = AmalgamationEditHelper.getMultiRuleName(multiRule, amalgamation);
		if (name==null || name.length()==0) {
			return new String[] {};
		} else {
			return new String[] { name };
		}
	}
	
}
