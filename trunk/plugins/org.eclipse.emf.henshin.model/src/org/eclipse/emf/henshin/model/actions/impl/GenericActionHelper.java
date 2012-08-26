/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.model.actions.impl;

import org.eclipse.emf.henshin.model.Action.Type;

import static org.eclipse.emf.henshin.model.Action.Type.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Rule;

/**
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
		Rule rule = graph.getRule();
		if (rule==null) {
			return null;
		}
		
		// Get the kernel rule, if existing:
		Rule kernel = rule.getKernelRule();
		
		// Check if the element is amalgamated:
		boolean isMulti = isMulti(element);
		
		// Get the amalgamation parameters:
		String[] multiParams = getMultiParameters(element, rule);
		
		// If the rule is a multi-rule, but the action is not
		// a multi-action, the element is not an action element.
		if (kernel!=null && !isMulti) {
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
				return new Action(PRESERVE, isMulti, multiParams);
			} else {
				return new Action(DELETE, isMulti, multiParams);
			}
		}
		
		// RHS element?
		else if (graph==rule.getRhs()) {
			// Try to get the origin in the LHS:
			editor = getMapEditor(rule.getRhs());
			E origin = editor.getOpposite(element);
			
			// If it has an origin in the LHS, it is a CREATE-action:
			if (origin==null) {
				return new Action(CREATE, isMulti, multiParams);
			}
		}
		
		// PAC/NAC element?
		else if (graph.eContainer() instanceof NestedCondition) {
			
			// Find out whether it is a PAC, a NAC or something else:
			NestedCondition nc = (NestedCondition) graph.eContainer();
			Type type = null;
			if (nc.isPAC()) {
				type = REQUIRE;
			} else if (nc.isNAC()) {
				type = FORBID;
			}

			// If we know the type, we can continue:
			if (type!=null) {
				
				// Try to get the origin in the LHS:
				editor = getMapEditor(graph);
				E origin = editor.getOpposite(element);

				// If it has an origin in the LHS, it is a PAC/NAC-action:
				if (origin==null) {
					if (isMulti) {
						return new Action(type, true, multiParams);						
					} else {
						String name = graph.getName();
						if (name==null || name.trim().length()==0 || "default".equals(name)) {
							return new Action(type, false);
						} else {
							return new Action(type, false, name.trim());
						}
	
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
		Rule rule = graph.getRule();

		// Map editor.
		MapEditor<E> editor;
		
		// Current action type = PRESERVE?
		if (current.getType()==PRESERVE) {
			
			// We know that the element is contained in the LHS and that it is mapped to a node in the RHS.
			editor = getMapEditor(rule.getRhs());
			E image = editor.getOpposite(element);
			
			// For DELETE actions, delete the image in the RHS:
			if (action.getType()==DELETE) {
				editor.remove(image);
			}
			
			// For CREATE actions, replace the image in the RHS by the origin:
			else if (action.getType()==CREATE) {
				editor.replace(image);
			}
			
			// For REQUIRE / FORBID actions, delete the image in the RHS and move the node to the AC:
			else if (action.getType()==REQUIRE || action.getType()==FORBID) {
				
				// Remove the image in the RHS:
				editor.remove(image);
				
				// Move the node to the AC:
				NestedCondition ac = ActionACUtil.getOrCreateAC(action, rule);
				editor = getMapEditor(ac.getConclusion());
				editor.move(element);
				
			} 
			
		}
		
		// Current action type = CREATE?
		else if (current.getType()==CREATE) {
			
			// We know that the element is contained in the RHS and that it is not an image of a mapping.
			editor = getMapEditor(rule.getRhs());
			
			// We move the element to the LHS if the action type has changed:
			if (action.getType()!=CREATE) {
				editor.move(element);
			}
			
			// For NONE actions, create a copy of the element in the RHS and map to it:
			if (action.getType()==PRESERVE) {
				editor.copy(element);
			}
			
			// For REQUIRE / FORBID actions, move the element further to the AC:
			else if (action.getType()==REQUIRE || action.getType()==FORBID) {
				NestedCondition ac = ActionACUtil.getOrCreateAC(action, rule);
				editor = getMapEditor(ac.getConclusion());
				editor.move(element);
			}	
			
		}

		// Current action type = DELETE?
		else if (current.getType()==DELETE) {
			
			// We know that the element is contained in the LHS and that it has no image in the RHS.
			editor = getMapEditor(rule.getRhs());
			
			// For PRESERVE actions, create a copy of the element in the RHS and map to it:
			if (action.getType()==PRESERVE) {
				editor.copy(element);
			}
			
			// For CREATE actions, move the element to the RHS:
			else if (action.getType()==CREATE) {
				editor.move(element);
			}
			
			// For FORBID actions, move the element to the NAC:
			else if (action.getType()==REQUIRE ||  action.getType()==FORBID) {
				NestedCondition ac = ActionACUtil.getOrCreateAC(action, rule);
				editor = getMapEditor(ac.getConclusion());
				editor.move(element);
			}	
		}		
		
		// Current action type = REQUIRE or FORBID?
		else if (current.getType()==REQUIRE || current.getType()==FORBID) {
			
			// We know that the element is contained in a AC and that it has no origin in the LHS.
			NestedCondition ac = (NestedCondition) graph.eContainer();
			editor = getMapEditor(ac.getConclusion());
			
			// We move the element to the LHS in any case:
			editor.move(element);
			
			// For PRESERVE actions, create a copy in the RHS as well:
			if (action.getType()==PRESERVE) {
				editor = getMapEditor(rule.getRhs());
				editor.copy(element);
			}
			// For CREATE actions, move the element to the RHS:
			else if (action.getType()==CREATE) {
				editor = getMapEditor(rule.getRhs());
				editor.move(element);
			}			
			// For REQUIRE and FORBID actions, move the element to the new AC:
			else if (action.getType()==REQUIRE || action.getType()==FORBID) {
				NestedCondition newAc = ActionACUtil.getOrCreateAC(action, rule);
				editor = getMapEditor(newAc.getConclusion());
				editor.move(element);
			}
			
			// Delete the PAC/NAC if it became empty or trivial due to the current change.
			if (ac.isTrue()) {
				rule.removeNestedCondition(ac);
			}
			
		}
		
		// THE ACTION TYPE IS CORRECT NOW.
		
		// We check now whether the amalgamation parameters are different.
		if (current.isMulti()!=action.isMulti()) {
			
			// Find the amalgamation and the kernel / multi rule.
			Rule multi, kernel;
			if (action.isMulti()) {
				multi = getOrCreateMultiRule(rule, action.getArguments());
				kernel = rule;
			} else {
				kernel = rule.getKernelRule();
				multi = rule;
			}
			updateMultiElement(kernel, multi, action.getType(), element);
			
		}
		
		// THE ACTION TYPE AND THE MULTI-FLAG ARE CORRECT NOW.
		
		// The only thing that can be different now is the name of the multi-rule:
		if (current.isMulti() && action.isMulti()) {
			Rule kernelRule = rule.getKernelRule();
			Rule newMulti = getOrCreateMultiRule(kernelRule, action.getArguments());
			if (newMulti!=rule) {
				updateMultiElement(rule, newMulti, action.getType(), element);
			}
		}
			
	}
	
	private void updateMultiElement(Rule kernel, Rule multi, Type actionType, E element) {
		
		// First make sure the multi-rule is complete.
		sanitizeMultiRule(multi);

		// Move the element(s).
		if (actionType==CREATE) {
			getMapEditor(kernel.getRhs(), multi.getRhs(), multi.getMultiMappings()).move(element);
		}
		else if (actionType==DELETE) {
			getMapEditor(kernel.getLhs(), multi.getLhs(), multi.getMultiMappings()).move(element);
		}
		else if (actionType==PRESERVE) {
			MappingMapEditor mappingEditor = new MappingMapEditor(kernel, multi, multi.getMultiMappings());
			mappingEditor.moveMappedElement(element);
		}
		
		// Remove trivial multi-rules:
		kernel.removeTrivialMultiRules();
		
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
	 * is a multi-action.
	 */
	private boolean isMulti(E element) {
		GraphElement elem;
		if (element instanceof Attribute) {
			elem = ((Attribute) element).getNode();
		} else if (element instanceof GraphElement) {
			elem = (GraphElement) element;
		} else {
			return false;
		}
		Graph graph = elem.getGraph();
		if (graph==null) {
			return false;
		}
		Rule rule = graph.getRule();
		if (rule==null || rule.getKernelRule()==null) {
			return false;
		}
		if (rule.getMultiMappings().getOrigin(element)!=null) {
			return false;
		}
		return true;
	}
	
	/*
	 * If an element has a multi-action, this method
	 * returns the proper parameters for the multi-action.
	 */
	private String[] getMultiParameters(E element, Rule multiRule) {
		if (!isMulti(element)) {
			return new String[] {};
		}
		List<String> names = new ArrayList<String>();
		while (multiRule.isMultiRule()) {
			String name = multiRule.getName();
			names.add(name==null ? "" : name.trim());
			multiRule = multiRule.getKernelRule();
		}
		if (names.size()==1 && names.get(0).length()==0) {
			return new String[] {};
		} else {
			Collections.reverse(names);
			return names.toArray(new String[0]);
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
