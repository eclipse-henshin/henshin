package org.eclipse.emf.henshin.model.actions.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.actions.Action;
import org.eclipse.emf.henshin.model.actions.ActionType;
import org.eclipse.emf.henshin.model.util.HenshinACUtil;
import org.eclipse.emf.henshin.model.util.HenshinMultiRuleUtil;

/**
 * @generated NOT
 * @author Christian Krause
 */
public abstract class GenericActionHelper<E extends EObject,C extends EObject> implements ActionHelper<E,C> {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.edit.actions.ActionHelper#getAction(java.lang.Object)
	 */
	public Action getAction(E element) {
		
		// Get the graph and the rule:
		Graph graph = getGraph(element);
		if (graph==null) {
			return null;
		}
		Rule rule = graph.getContainerRule();
		if (rule==null) {
			return null;
		}
		
		// Get the kernel rule, if existing:
		Rule kernel = rule.getKernelRule();
		
		// Check if the element is amalgamated:
		boolean isAmalgamated = isAmalgamated(element);
		
		// Get the amalgamation parameters:
		String[] amalgamationParams = 
			getAmalgamationParameters(element, rule);
		
		// If the rule is a multi-rule, but the action is not
		// amalgamated, the element is not an action element.
		if (kernel!=null && !isAmalgamated) {
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
		
		// PAC/NAC element?
		else if (graph.eContainer() instanceof NestedCondition) {
			
			// Find out whether it is a PAC, a NAC or something else:
			NestedCondition nc = (NestedCondition) graph.eContainer();
			ActionType type = null;
			if (nc.isPAC()) {
				type = ActionType.REQUIRE;
			}
			else if (nc.isNAC()) {
				type = ActionType.FORBID;
			}

			// If we know the type, we can continue:
			if (type!=null) {
				
				// Try to get the origin in the LHS:
				editor = getMapEditor(graph);
				E origin = editor.getOpposite(element);

				// If it has an origin in the LHS, it is a NAC-action:
				if (origin==null) {
					String name = graph.getName();
					if (name==null || name.trim().length()==0 || 
						ActionACUtil.DEFAULT_AC_NAME.equals(graph.getName())) {
						return new Action(type, isAmalgamated);
					} else {
						return new Action(type, isAmalgamated, name.trim());
					}
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
		
		// Current action type = PRESERVE?
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
			
			// For REQUIRE / FORBID actions, delete the image in the RHS and move the node to the AC:
			else if (action.getType()==ActionType.REQUIRE ||
					 action.getType()==ActionType.FORBID) {
				
				// Remove the image in the RHS:
				editor.remove(image);
				
				// Move the node to the AC:
				NestedCondition ac = ActionACUtil.getOrCreateAC(action, rule);
				editor = getMapEditor(ac.getConclusion());
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
			
			// For REQUIRE / FORBID actions, move the element further to the AC:
			else if (action.getType()==ActionType.REQUIRE ||
					action.getType()==ActionType.FORBID) {
				
				NestedCondition ac = ActionACUtil.getOrCreateAC(action, rule);
				editor = getMapEditor(ac.getConclusion());
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
			else if (action.getType()==ActionType.REQUIRE || 
					 action.getType()==ActionType.FORBID) {
				
				NestedCondition ac = ActionACUtil.getOrCreateAC(action, rule);
				editor = getMapEditor(ac.getConclusion());
				editor.move(element);
			}	
		}		
		
		// Current action type = REQUIRE or FORBID?
		else if (current.getType()==ActionType.REQUIRE || 
				 current.getType()==ActionType.FORBID) {
			
			// We know that the element is contained in a AC and that it has no origin in the LHS.
			NestedCondition ac = (NestedCondition) graph.eContainer();
			editor = getMapEditor(ac.getConclusion());
			
			// We move the element to the LHS in any case:
			editor.move(element);
			
			// For PRESERVE actions, create a copy in the RHS as well:
			if (action.getType()==ActionType.PRESERVE) {
				editor = getMapEditor(rule.getRhs());
				editor.copy(element);
			}
			// For CREATE actions, move the element to the RHS:
			else if (action.getType()==ActionType.CREATE) {
				editor = getMapEditor(rule.getRhs());
				editor.move(element);
			}			
			// For REQUIRE and FORBID actions, move the element to the new AC:
			else if (action.getType()==ActionType.REQUIRE ||
					 action.getType()==ActionType.FORBID) {

				NestedCondition newAc = ActionACUtil.getOrCreateAC(action, rule);
				editor = getMapEditor(newAc.getConclusion());
				editor.move(element);
			}
			
			// Delete the NAC is it became empty or trivial due to the current change.
			if (HenshinACUtil.isTrivialAC(ac)) {
				HenshinACUtil.removeAC(rule, ac);
			}
			
		}
		
		// THE ACTION TYPE IS CORRECT NOW.
		
		// We check now whether the amalgamation parameters are different.
		if (current.isAmalgamated()!=action.isAmalgamated()) {
			
			// Find the amalgamation and the kernel / multi rule.
			Rule multi, kernel;
			if (action.isAmalgamated()) {
				multi = getOrCreateMultiRule(rule, action.getArguments());
				kernel = rule;
			} else {
				kernel = rule.getKernelRule();
				multi = rule;
			}
			updateMultiElement(kernel, multi, action.getType(), element);
			
		}
		
		// THE ACTION TYPE AND THE AMALGAMATED FLAG ARE CORRECT NOW.
		
		// The only thing that can be different now is the name of the multi-rule:
		if (current.isAmalgamated() && action.isAmalgamated()) {
			Rule kernelRule = rule.getKernelRule();
			Rule newMulti = getOrCreateMultiRule(kernelRule, action.getArguments());
			if (newMulti!=rule) {
				updateMultiElement(rule, newMulti, action.getType(), element);
			}
		}
			
	}
	
	private void updateMultiElement(Rule kernel, Rule multi, ActionType actionType, E element) {
		
		// First make sure the multi-rule is complete.
		sanitizeMultiRule(multi);

		// Move the element(s).
		if (actionType==ActionType.CREATE) {
			getMapEditor(kernel.getRhs(), multi.getRhs(), multi.getMultiMappings()).move(element);
		}
		else if (actionType==ActionType.DELETE) {
			getMapEditor(kernel.getLhs(), multi.getLhs(), multi.getMultiMappings()).move(element);
		}
		else if (actionType==ActionType.PRESERVE) {
			MappingMapEditor mappingEditor = new MappingMapEditor(kernel, multi, multi.getMultiMappings());
			mappingEditor.moveMappedElement(element);
		}
		
		// Remove trivial multi-rules from the amalgamation:
		HenshinMultiRuleUtil.removeTrivialMultiRules(kernel);
		
	}
	
	private void sanitizeMultiRule(Rule multi) {
		Rule kernelRule = multi.getKernelRule();
		
		MappingMapEditor mappingEditor = new MappingMapEditor(
				kernelRule, multi, 
				multi.getMultiMappings());
		mappingEditor.ensureCompleteness();
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
	 * Create a new map editor for a given target graph.
	 */
	protected abstract MapEditor<E> getMapEditor(Graph target);

	/*
	 * Create a new map editor for a given source, target graph and mappings.
	 */
	protected abstract MapEditor<E> getMapEditor(Graph source, Graph target, MappingList mappings);

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
	private boolean isAmalgamated(E element) {
		GraphElement elem;
		if (element instanceof Attribute) {
			elem = ((Attribute) element).getNode();
		}
		else if (element instanceof GraphElement) {
			elem = (GraphElement) element;
		}
		else {
			return false;
		}
		Graph graph = elem.getGraph();
		if (elem.getGraph()==null) {
			return false;
		}
		Rule rule = graph.getContainerRule();
		if (rule==null || rule.getKernelRule()==null) {
			return false;
		}
		if (rule.getMultiMappings().getOrigin(elem)!=null) {
			return false;
		}
		return true;
	}
	
	/*
	 * If an element has an amalgamated action, this method
	 * returns the proper parameters for the amalgamated action.
	 */
	private String[] getAmalgamationParameters(E element, Rule multiRule) {
		if (!isAmalgamated(element)) {
			return new String[] {};
		}
		String name = multiRule.getName();
		if (name==null || name.length()==0) {
			return new String[] {};
		} else {
			return new String[] { name };
		}
	}
	
	
	private Rule getOrCreateMultiRule(Rule kernel, String[] actionArguments) {
		
		// Derive the multi-rule name:
		String name = null;
		if (actionArguments.length>0 && actionArguments[0].trim().length()>0) {
			name = actionArguments[0].trim();
		}
		
		// Get or create the multi-rule:
		Rule multiRule = kernel.getMultiRule(name);
		if (multiRule==null) {
			multiRule = HenshinFactory.eINSTANCE.createRule();
			multiRule.setName(name);
			if (name==null) {
				kernel.getMultiRules().add(0, multiRule);
			} else {
				kernel.getMultiRules().add(multiRule);
			}
		}
		return multiRule;
			
	}
	
}
