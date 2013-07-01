/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.ApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;

/**
 * Common utility methods for the Henshin interpreter.
 * @author Christian Krause
 */
public class InterpreterUtil {

	/**
	 * Find all matches for a rule.
	 * @param engine Engine.
	 * @param rule Rule to be matched.
	 * @param graph Target graph.
	 * @param partialMatch Partial match or <code>null</code>.
	 * @return List of found matches.
	 */
	public static List<Match> findAllMatches(Engine engine, Rule rule, EGraph graph, Match partialMatch) {
		List<Match> matches = new ArrayList<Match>();
		for (Match match : engine.findMatches(rule, graph, partialMatch)) {
			matches.add(match);
		}
		return matches;
	}

	/**
	 * Find all matches of all rules in a module. This does not consider
	 * submodules.
	 * @param engine Engine to be used.
	 * @param module Module to be used.
	 * @param graph Target graph.
	 * @return List of matches.
	 */
	public static List<Match> findAllMatches(Engine engine, Module module, EGraph graph) {
		List<Match> matches = new ArrayList<Match>();
		for (Unit unit : module.getUnits()) {
			if (unit instanceof Rule) {
				matches.addAll(findAllMatches(engine, (Rule) unit, graph, null));
			}
		}
		return matches;
	}

	/**
	 * Execute the given unit application and throws an {@link AssertionError} if it could
	 * not be successfully applied (if {@link UnitApplication#execute(ApplicationMonitor)}
	 * returns <code>false</code>). This is just a convenience method.
	 * @param application A unit application.
	 */
	public static void executeOrDie(UnitApplication application) {
		if (!application.execute(null)) {
			if (application instanceof RuleApplication) {
				throw new AssertionError("Error executing rule '" + application.getUnit().getName() + "'");								
			} else {
				throw new AssertionError("Error executing unit '" + application.getUnit().getName() + "'");				
			}
		}
	}
	
	/**
	 * Apply a unit to the contents of a resource. This automatically creates an {@link EGraph}
	 * and updates the contents of the resource.
	 * @param unit Unit to be applied.
	 * @param engine Engine to be used.
	 * @param resource Resource containing the model to be transformed.
	 * @return <code>true</code> if the unit was successfully applied.
	 */
	public static boolean applyToResource(Unit unit, Engine engine, Resource resource) {
		
		// Create the graph and the unit application:
		EGraph graph = new EGraphImpl(resource);
		UnitApplication application = new UnitApplicationImpl(engine, graph, unit, null);
		
		// Remember the old root objects:
		Set<EObject> oldRoots = new HashSet<EObject>();
		oldRoots.addAll(graph.getRoots());
		
		// Apply the unit:
		boolean result = application.execute(null);
		
		// Sync root objects:
		List<EObject> roots = graph.getRoots();
		Iterator<EObject> it = resource.getContents().iterator();
		while (it.hasNext()) {
			if (!roots.contains(it.next())) {
				it.remove();
			}
		}
		for (EObject root : roots) {
			if (!oldRoots.contains(root)) {
				resource.getContents().add(root);
			}
		}
		return result;
		
	}

	/**
	 * Check whether two {@link EGraph}s are isomorphic.
	 * @param graph1 First graph.
	 * @param graph2 Second graph.
	 * @return <code>true</code> if they are isomorphic.
	 */
	public static boolean areIsomorphic(EGraph graph1, EGraph graph2) {
		return new EGraphIsomorphyChecker(graph1, null).isIsomorphicTo(graph2, null);
	}

	
	/**
	 * Check whether the contents of two resources are isomorphic.
	 * @param resource1 First resource.
	 * @param resource2 Second resource.
	 * @return <code>true</code> if they are isomorphic.
	 */
	public static boolean areIsomorphic(Resource resource1, Resource resource2) {
		return areIsomorphic(new EGraphImpl(resource1), new EGraphImpl(resource2));
	}

	/**
	 * Count the number of edges/links in a graph.
	 * @param graph An {@link EGraph}
	 * @return Number of edges/links in that graph.
	 */
	public static int countEdges(EGraph graph) {
		int links = 0;
		for (EObject object : graph) {
			for (EReference ref : object.eClass().getEAllReferences()) {
				if (ref.isMany()) {
					links += ((EList<?>) object.eGet(ref)).size();
				} else {
					if (object.eGet(ref)!=null) links++;
				}
			}
		}
		return links;
	}
	
	/**
	 * Get a string representation of an object.
	 * @param object An object.
	 * @return A readable string representation.
	 */
	public static String objectToString(Object object) {
		if (object instanceof String) {
			return "'" + object + "'";
			
		}
		if (object instanceof DynamicEObjectImpl) {
			EClass eclass = ((DynamicEObjectImpl) object).eClass();
			if (eclass!=null) {
				String type = eclass.getName();
				EPackage epackage = eclass.getEPackage();
				while (epackage!=null) {
					type = epackage.getName() + "." + type;
					epackage = epackage.getESuperPackage();
				}
				String args = "";
				for (EAttribute att : eclass.getEAllAttributes()) {
					args = args + ", " + att.getName() + "=" + objectToString(((DynamicEObjectImpl) object).eGet(att));
				}
				
				return type + "@" + Integer.toHexString(object.hashCode()) + " (dynamic" + args + ")";
			}
		}
		return String.valueOf(object); // object could be null
	}
	
}
