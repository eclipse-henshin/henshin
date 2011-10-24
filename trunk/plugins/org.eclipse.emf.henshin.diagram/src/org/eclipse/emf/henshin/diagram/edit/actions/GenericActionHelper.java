package org.eclipse.emf.henshin.diagram.edit.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.diagram.edit.helpers.AmalgamationEditHelper;
import org.eclipse.emf.henshin.diagram.edit.maps.MapEditor;
import org.eclipse.emf.henshin.diagram.edit.maps.MappingMapEditor;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinNCUtil;

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
				if (ActionNCUtil.DEFAULT_NC_NAME.equals(graph.getName())) {
					return new Action(ActionType.FORBID);
				} else {
					return new Action(ActionType.FORBID, graph.getName());					
				}
			}
			
		// PAC element?
		} else if (!isNAC(graph)) {
			editor = getMapEditor(graph);
			E origin = editor.getOpposite(element);
			if (origin == null) {
				if (ActionNCUtil.DEFAULT_NC_NAME.equals(graph.getName())) {
					return new Action(ActionType.REQUIRE);
				} else {
					return new Action(ActionType.REQUIRE, graph.getName());
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
				NestedCondition nac = ActionNCUtil.getOrCreateNAC(action, rule);
				editor = getMapEditor(nac.getConclusion());
				editor.move(element);
				
			} 
			// REQUIRE 
			else if (action.getType() == ActionType.REQUIRE) {
				editor.remove(image);
				
				// Move the node to the PAC:
				NestedCondition pac = ActionNCUtil.getOrCreateNC(action, rule, true);
				editor = getMapEditor(pac.getConclusion());
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
				NestedCondition nac = ActionNCUtil.getOrCreateNAC(action, rule);
				editor = getMapEditor(nac.getConclusion());
				editor.move(element);
			}	
			
			// REQUIRE
			else if (action.getType() == ActionType.REQUIRE) {
				NestedCondition pac = ActionNCUtil.getOrCreateNC(action, rule, true);
				editor = getMapEditor(pac.getConclusion());
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
				NestedCondition nac = ActionNCUtil.getOrCreateNAC(action, rule);
				editor = getMapEditor(nac.getConclusion());
				editor.move(element);
			}	
			
			// REQUIRE
			else if (action.getType() == ActionType.REQUIRE) {
				NestedCondition pac = ActionNCUtil.getOrCreateNC(action, rule, true);
				editor = getMapEditor(pac.getConclusion());
				editor.move(element);
			}
			
		}		
		
		// Current action type = FORBID?
		else if (current.getType()==ActionType.FORBID) {
			
			// We know that the element is contained in a NAC and that it has no origin in the LHS.
			NestedCondition nac = (NestedCondition) graph.eContainer();
			editor = getMapEditor(nac.getConclusion());
			
			// We move the element to the LHS if the action type has changed:
			if (action.getType()!=ActionType.FORBID) {
				editor.move(element);
			}
			
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

			// For FORBID actions, move the element to the new NAC:
			else if (action.getType()==ActionType.FORBID) {
				NestedCondition newNac = ActionNCUtil.getOrCreateNAC(action, rule);
				editor = getMapEditor(newNac.getConclusion());
				editor.move(element);
			}
			
			// REQUIRE
			else if (action.getType() == ActionType.REQUIRE) {
				NestedCondition pac = ActionNCUtil.getOrCreateNC(action, rule, true);
				editor = getMapEditor(pac.getConclusion());
				editor.move(element);
			}
			
			// Delete the NAC is it became empty or trivial due to the current change.
			if (HenshinNCUtil.isTrivialNAC(nac)) {
				HenshinNCUtil.removeNAC(rule, nac);
			}
			
		}
		
		// THE ACTION TYPE IS CORRECT NOW.
		
		// We check now whether the amalgamation parameters are different.
		if (current.isAmalgamated()!=action.isAmalgamated()) {
			
			// Find the amalgamation and the kernel / multi rule.
			AmalgamationUnit amalgamation;
			Rule multi;
			if (action.isAmalgamated()) {
				multi = getOrCreateMultiRule(rule, action.getArguments());
				amalgamation = AmalgamationEditHelper.getAmalgamationFromKernelRule(rule);
			} else {
				multi = rule;
				amalgamation = AmalgamationEditHelper.getAmalgamationFromMultiRule(rule);
			}
			Rule kernel = amalgamation.getKernelRule();
			
			// First make sure the multi-rule is complete.
			sanitizeMultiRule(multi, amalgamation);
			
			// Move the element(s).
			if (action.getType()==ActionType.CREATE) {
				getMapEditor(kernel.getRhs(), multi.getRhs(), amalgamation.getRhsMappings()).move(element);
			}
			else if (action.getType()==ActionType.DELETE) {
				getMapEditor(kernel.getLhs(), multi.getLhs(), amalgamation.getLhsMappings()).move(element);
			}
			else if (action.getType()==ActionType.PRESERVE) {
				MappingMapEditor mappingEditor = new MappingMapEditor(kernel, multi, 
						amalgamation.getLhsMappings(), amalgamation.getRhsMappings());
				mappingEditor.moveMappedElement(element);
			}
			
			// Remove trivial multi-rules from the amalgamation:
			AmalgamationEditHelper.cleanUpAmalagamation(amalgamation);
			
		}
		
		// THE ACTION TYPE AND THE AMALGAMATED FLAG ARE CORRECT NOW.
		
		// The only thing that can be different now is the name of the multi-rule:
		if (current.isAmalgamated() && action.isAmalgamated()) {
			
			AmalgamationUnit amalgamation = AmalgamationEditHelper.getAmalgamationFromMultiRule(rule);
			Rule newMulti = getOrCreateMultiRule(amalgamation.getKernelRule(), action.getArguments());
			
			if (newMulti!=rule) {
				
				sanitizeMultiRule(newMulti, amalgamation);
				
				// Move the element(s).
				if (action.getType()==ActionType.CREATE) {
					getMapEditor(rule.getRhs(), newMulti.getRhs(), null).move(element);
				}
				else if (action.getType()==ActionType.DELETE) {
					getMapEditor(rule.getLhs(), newMulti.getLhs(), null).move(element);
				}
				else if (action.getType()==ActionType.PRESERVE) {
					MappingMapEditor mappingEditor = new MappingMapEditor(rule, newMulti, null, null);
					mappingEditor.moveMappedElement(element);
				}
				
				// Remove trivial multi-rules from the amalgamation:
				AmalgamationEditHelper.cleanUpAmalagamation(amalgamation);
			}
		}
			
	}
	
	
	private void sanitizeMultiRule(Rule multi, AmalgamationUnit amalgamation) {
		MappingMapEditor mappingEditor = new MappingMapEditor(
				amalgamation.getKernelRule(), multi, 
				amalgamation.getLhsMappings(), amalgamation.getRhsMappings());
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
	 * Check if a graph belongs to a NAC.
	 * true - graph is a NAC
	 * false - graph is not a NAC (possibly is a PAC)
	 */
	protected boolean isNAC(Graph graph) {
		boolean retval = false;
		boolean st = false;
		if (graph.eContainer() instanceof NestedCondition) {
			if (((NestedCondition) graph.eContainer()).eContainer() instanceof Not) {
				System.err.println(graph.getName() + " NAC by 'NOT'"   + "\t\t" + graph);
				retval = true;
				st = true;
			}
			if (((NestedCondition) graph.eContainer()).isNegated()) {
				System.err.println(graph.getName() + " NAC by negated flag" + "\t\t" + graph);
				retval = true;
				st = true;
			}
			if (!(((NestedCondition) graph.eContainer()).eContainer() instanceof Not)) {
				System.err.println(graph.getName() + " PAC by absence of 'NOT'" + "\t\t" + graph);
				st = true;
				retval = false;
			}
			if (!st) {
				System.err.println(graph.getName() + " didn't fulfill any criteria" + "\t\t" + graph);
				st = true;
			}
		}
		if (!st) {
			System.err.println(graph.getName() + " is not a NC");
		}
		return retval;
	}

	
	/*
	 * Create a new map editor for a given target graph.
	 */
	protected abstract MapEditor<E> getMapEditor(Graph target);

	/*
	 * Create a new map editor for a given source, target graph and mappings.
	 */
	protected abstract MapEditor<E> getMapEditor(Graph source, Graph target, List<Mapping> mappings);

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
				return AmalgamationEditHelper.getPreimageInKernelRule(
							(GraphElement) element, amalgamation)==null;
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
	
	
	private Rule getOrCreateMultiRule(Rule kernel, String[] actionArguments) {
		
		// Find or create the amalgamation unit:
		AmalgamationUnit amalgamation = AmalgamationEditHelper.getAmalgamationFromKernelRule(kernel);
		if (amalgamation==null) {
			amalgamation = HenshinFactory.eINSTANCE.createAmalgamationUnit();
			amalgamation.setName(kernel.getName());
			amalgamation.setKernelRule(kernel);
			kernel.getTransformationSystem().getTransformationUnits().add(amalgamation);
		}

		Rule multiRule;
		if (actionArguments.length==0) {
			multiRule = AmalgamationEditHelper.getDefaultMultiRule(kernel);
			if (multiRule==null) {
				multiRule = AmalgamationEditHelper.createDefaultMultiRule(amalgamation);
			}
		} else {
			String name = actionArguments[0].trim();
			multiRule = AmalgamationEditHelper.getMultiRule(kernel, name);
			if (multiRule==null) {
				multiRule = AmalgamationEditHelper.createMultiRule(amalgamation, name);
			}
		}
		return multiRule;
			
	}
	
}
