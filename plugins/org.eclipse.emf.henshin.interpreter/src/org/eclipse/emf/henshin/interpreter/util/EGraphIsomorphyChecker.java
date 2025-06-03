/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.interpreter.ApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.matching.conditions.ConditionHandler;
import org.eclipse.emf.henshin.interpreter.matching.conditions.IFormula;
import org.eclipse.emf.henshin.interpreter.matching.constraints.AttributeConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.interpreter.matching.constraints.ReferenceConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Solution;
import org.eclipse.emf.henshin.interpreter.matching.constraints.SolutionFinder;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;

/**
 * A graph isomorphy checker for {@link EGraph}s.
 * 
 * @author Christian Krause
 */
public class EGraphIsomorphyChecker {

	// Attribute condition handles (used internally for the match finding):
	private static final ConditionHandler ATTRIBUTE_CONDITION_HANDLER;

	// Initialize static members:
	static {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
		ATTRIBUTE_CONDITION_HANDLER = new ConditionHandler(new HashMap<String, Collection<String>>(), engine);
	}

	// The source graph:
	private final EGraph source;

	// Number of links in the source graph:
	private int linkCount;

	// Ignored attributes:
	private final List<EAttribute> ignoredAttributes;

	// Object variables map:
	private Map<EObject, Variable> variablesMap;

	// Variables to EObjects map:
	private Map<Variable, EObject> variablesToEObjects;

	// Variables as a list:
	private List<Variable> variablesList;

	/**
	 * Default constructor.
	 * 
	 * @param source Source graph.
	 * @param ignoredAttributes Flag indicating whether attribute values should be ignored.
	 */
	public EGraphIsomorphyChecker(final EGraph source, List<EAttribute> ignoredAttributes) {
		this.source = source;
		this.ignoredAttributes = ignoredAttributes;
		this.linkCount = InterpreterUtil.countEdges(source);
		initVariables();
	}

	/*
	 * Initialize variables.
	 */
	private void initVariables() {

		// Instantiate variables map and list:
		int objectCount = source.size();
		variablesMap = new HashMap<EObject, Variable>(objectCount);
		variablesToEObjects = new HashMap<Variable, EObject>(objectCount);
		variablesList = new ArrayList<Variable>(objectCount);

		// Create a variable for every object:
		for (EObject object : source) {
			Variable variable = new Variable(object.eClass(), true);
			variablesMap.put(object, variable);
			variablesToEObjects.put(variable, object);
			variablesList.add(variable);
		}

		// Create constraints:
		for (Map.Entry<EObject, Variable> entry : variablesMap.entrySet()) {
			EObject object = entry.getKey();
			Variable variable = entry.getValue();

			// Create attribute constraints if necessary:
			for (EAttribute attr : object.eClass().getEAllAttributes()) {
				if (ignoredAttributes == null || !ignoredAttributes.contains(attr)) {
					variable.attributeConstraints.add(new AttributeConstraint(attr, object.eGet(attr), true));
				}
			}

			// Create reference constraints:
			for (EReference ref : object.eClass().getEAllReferences()) {
				if (ref.isMany()) {
					@SuppressWarnings("unchecked")
					EList<EObject> targets = (EList<EObject>) object.eGet(ref);
					for (EObject target : targets) {
						variable.referenceConstraints.add(new ReferenceConstraint(variablesMap.get(target), ref));
					}
				} else {
					EObject target = (EObject) object.eGet(ref);
					if (target != null) {
						Variable targetVariable = variablesMap.get(target);
						if (targetVariable != null) {
							variable.referenceConstraints.add(new ReferenceConstraint(targetVariable, ref));
						}
					}
				}
			}
		}

	}

	/**
	 * Check whether the argument graph is isomorphic to the source graph.
	 * 
	 * @param graph Graph for which you want to check isomorphy.
	 * @param partialMatch Optional partial match from source to the argument graph.
	 * @return <code>true</code> if the source and the graph are isomorphic.
	 */
	public boolean isIsomorphicTo(EGraph graph, Map<EObject, EObject> partialMatch) {
		return getIsomorphism(graph, partialMatch,null) != null; //no monitoring for isomorphic check
	}

	/**
	 * Find an isomorphism from the source graph to the argument graph
	 * 
	 * @param graph Graph to which you want to find an isomorphism.
	 * @param partialMatch Optional partial match from source to the argument graph.
	 * @param monitor Monitor to collect performance data
	 * @return map of source objects to target objects if the source and the graph are isomorphic or <code>null</code>
	 *         if the source and the graph are not isomorphic.
	 */
	public Map<EObject, EObject> getIsomorphism(EGraph graph, Map<EObject, EObject> partialMatch,ApplicationMonitor monitor) {

		// We do a quick comparison of the object count first:
		if (source.size() != graph.size()) {
			return null;
		}

		// Create the domain map:
		Map<Variable, DomainSlot> domainMap = new HashMap<Variable, DomainSlot>();

		// Create the domain slots:
		for (Map.Entry<EObject, Variable> entry : variablesMap.entrySet()) {
			DomainSlot domainSlot = new DomainSlot(ATTRIBUTE_CONDITION_HANDLER, new HashSet<EObject>(), true, false,
					true, true,null); //no monitoring for isomorphic check
			if (partialMatch != null) {
				EObject match = partialMatch.get(entry.getKey());
				if (match != null) {
					domainSlot.fixInstantiation(match);
				}
			}
			domainMap.put(entry.getValue(), domainSlot);
		}

		// Create the match finder:
		SolutionFinder matchFinder = new SolutionFinder(graph, domainMap, ATTRIBUTE_CONDITION_HANDLER,monitor);
		matchFinder.variables = variablesList;
		matchFinder.formula = IFormula.TRUE;

		// Try to find a match:
		Solution solution = matchFinder.getNextSolution();
		if (solution == null) {
			return null;
		}

		// We still need to verify that the link count is the same:
		if (linkCount != InterpreterUtil.countEdges(graph)) {
			return null;
		}

		Map<EObject, EObject> res = new HashMap<EObject, EObject>(variablesMap.size());
		for (Map.Entry<Variable, EObject> entry : solution.objectMatches.entrySet()) {
			Variable variable = entry.getKey();
			EObject match = entry.getValue();
			EObject origin = variablesToEObjects.get(variable);
			res.put(origin, match);
		}

		// No reason why they shouldn't be isomorphic:
		return res;

	}

}
