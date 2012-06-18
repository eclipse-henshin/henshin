package org.eclipse.emf.henshin.interpreter.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.model.Rule;

public class InterpreterUtil {

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
