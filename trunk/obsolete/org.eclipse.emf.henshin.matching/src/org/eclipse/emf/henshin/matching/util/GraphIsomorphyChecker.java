package org.eclipse.emf.henshin.matching.util;

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
import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.matching.conditions.attribute.AttributeConditionHandler;
import org.eclipse.emf.henshin.matching.conditions.nested.IFormula;
import org.eclipse.emf.henshin.matching.conditions.nested.TrueFormula;
import org.eclipse.emf.henshin.matching.constraints.AttributeConstraint;
import org.eclipse.emf.henshin.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.matching.constraints.Matchfinder;
import org.eclipse.emf.henshin.matching.constraints.ReferenceConstraint;
import org.eclipse.emf.henshin.matching.constraints.Variable;

/**
 * A graph isomorphy checker for {@link EmfGraph}s.
 * @author Christian Krause
 */
public class GraphIsomorphyChecker {
	
	// Transformation options (used internally for the match finding):
	private static final TransformationOptions TRANSFORMATION_OPTIONS;
	
	// Attribute condition handles (used internally for the match finding):
	private static final AttributeConditionHandler ATTRIBUTE_CONDITION_HANDLER;

	// True formula:
	private static final IFormula TRUE_FORMULA;

	// Initialize static members:
	static {
		TRANSFORMATION_OPTIONS = new TransformationOptions();
		TRANSFORMATION_OPTIONS.setInjective(true);
		TRANSFORMATION_OPTIONS.setDeterministic(true);
		TRANSFORMATION_OPTIONS.setDangling(false);		
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
		ATTRIBUTE_CONDITION_HANDLER = new AttributeConditionHandler(new HashMap<String, Collection<String>>(), engine);
		TRUE_FORMULA = new TrueFormula();
	}
	
	// The source graph:
	private final EmfGraph source;
	
	// Number of links in the source graph:
	private int linkCount;
	
	// Ignored attributes:
	private final List<EAttribute> ignoredAttributes;
	
	// Object variables map:
	private Map<EObject, Variable> variablesMap;
	
	// Variables as a list:
	private List<Variable> variablesList;
	
	/**
	 * Default constructor.
	 * @param source Source graph.
	 * @param useAttributes Flag indicating whether attribute values should be used.
	 */
	public GraphIsomorphyChecker(final EmfGraph source, List<EAttribute> ignoredAttributes) {
		this.source = source;
		this.ignoredAttributes = ignoredAttributes;
		this.linkCount = computeLinkCount(source);
		initVariables();
	}
	
	/*
	 * Initialize variables.
	 */
	private void initVariables() {
		
		// Instantiate variables map and list:
		int objectCount = source.geteObjects().size();
		variablesMap = new HashMap<EObject, Variable>(objectCount);
		variablesList = new ArrayList<Variable>(objectCount);
		
		// Create a variable for every object:
		for (EObject object : source.geteObjects()) {
			Variable variable = new Variable(object.eClass(), true);
			variablesMap.put(object, variable);
			variablesList.add(variable);
		}

		// Create constraints:
		for (Map.Entry<EObject, Variable> entry : variablesMap.entrySet()) {
			EObject object = entry.getKey();
			Variable variable = entry.getValue();
			
			// Create attribute constraints if necessary:
			for (EAttribute attr : object.eClass().getEAllAttributes()) {
				if (!ignoredAttributes.contains(attr)) {
					variable.addConstraint(new AttributeConstraint(attr, object.eGet(attr)));
				}
			}
			
			// Create reference constraints:
			for (EReference ref : object.eClass().getEAllReferences()) {
				if (ref.isMany()) {
					@SuppressWarnings("unchecked")
					EList<EObject> targets = (EList<EObject>) object.eGet(ref);
					for (EObject target : targets) {
						variable.addConstraint(new ReferenceConstraint(variablesMap.get(target), ref));
					}
				} else {
					EObject target = (EObject) object.eGet(ref);
					if (target!=null) {
						variable.addConstraint(new ReferenceConstraint(variablesMap.get(target), ref));
					}
				}
			}	
		}
		
	}
	
	/**
	 * Check whether the argument graph is isomorphic to the source graph.
	 * @param graph Graph for which you want to check isomorphy.
	 * @param preMatch Optional (partial) match from source to the argument graph.
	 * @return <code>true</code> if the source and the graph are isomorphic.
	 */
	public boolean isIsomorphicTo(EmfGraph graph, Map<EObject,EObject> preMatch) {
		
		// We do a quick comparison of the object count first:
		if (source.geteObjects().size()!=graph.geteObjects().size()) {
			return false;
		}
		
		// Create the domain map:
		Map<Variable, DomainSlot> domainMap = new HashMap<Variable, DomainSlot>();
		
		// Create the domain slots:
		for (Map.Entry<EObject, Variable> entry : variablesMap.entrySet()) {
			DomainSlot domainSlot = new DomainSlot(ATTRIBUTE_CONDITION_HANDLER, new HashSet<EObject>(), TRANSFORMATION_OPTIONS);
			if (preMatch!=null) {
				EObject match = preMatch.get(entry.getKey());
				if (match!=null) {
					domainSlot.fixInstantiation(match);
				}
			}
			domainMap.put(entry.getValue(), domainSlot);
		}

		// Create the match finder:
		Matchfinder matchFinder = new Matchfinder(graph, domainMap, ATTRIBUTE_CONDITION_HANDLER);
		matchFinder.setVariables(variablesList);
		matchFinder.setFormula(TRUE_FORMULA);
		
		// Try to find a match:
		if (!matchFinder.findSolution()) {
			return false;
		}
		
		// We still need to verify that the link count is the same:
		if (linkCount!=computeLinkCount(graph)) {
			return false;
		}
		
		// No reason why they shouldn't be isomorphic:
		return true;
		
	}
	
	/*
	 * Compute the number of links in a graph.
	 */
	private static int computeLinkCount(EmfGraph graph) {
		int links = 0;
		for (EObject object : graph.geteObjects()) {
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
	
}
