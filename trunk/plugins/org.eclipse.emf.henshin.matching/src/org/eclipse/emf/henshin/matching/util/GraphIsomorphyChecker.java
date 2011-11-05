package org.eclipse.emf.henshin.matching.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.matching.conditions.attribute.AttributeConditionHandler;
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
	static {
		TRANSFORMATION_OPTIONS = new TransformationOptions();
		TRANSFORMATION_OPTIONS.setInjective(true);
		TRANSFORMATION_OPTIONS.setDeterministic(true);
	}
	
	// Attribute condition handles (used internally for the match finding):
	private static final AttributeConditionHandler ATTRIBUTE_CONDITION_HANDLER;
	static {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
		ATTRIBUTE_CONDITION_HANDLER = new AttributeConditionHandler(new HashMap<String, Collection<String>>(), engine);
	}
	
	// The source graph:
	private final EmfGraph source;
	
	// Flag indicating whether attribute values should be ignored:
	private final boolean ignoreAttributes;
	
	// Object variables:
	private Map<EObject, Variable> variables;
	
	/**
	 * Default constructor.
	 * @param source Source graph.
	 * @param ignoreAttributes Flag indicating whether attribute values should be ignored.
	 */
	public GraphIsomorphyChecker(EmfGraph source, boolean ignoreAttributes) {
		this.source = source;
		this.ignoreAttributes = ignoreAttributes;
		initVariables();
	}
	
	/*
	 * Initialize variables.
	 */
	private void initVariables() {
		
		// Instantiate variables map:
		variables = new HashMap<EObject, Variable>(source.geteObjects().size());
		
		// Create a variable for every object:
		for (EObject object : source.geteObjects()) {
			variables.put(object, new Variable(object.eClass()));
		}

		// Create constraints:
		for (Map.Entry<EObject, Variable> entry : variables.entrySet()) {
			EObject object = entry.getKey();
			Variable variable = entry.getValue();
			
			// Create attribute constraints if necessary:
			if (!ignoreAttributes) {
				for (EAttribute attr : object.eClass().getEAllAttributes()) {
					variable.addConstraint(new AttributeConstraint(attr, object.eGet(attr)));
				}
			}
			
			// Create reference constraints:
			for (EReference ref : object.eClass().getEAllReferences()) {
				if (ref.isMany()) {
					@SuppressWarnings("unchecked")
					EList<EObject> targets = (EList<EObject>) object.eGet(ref);
					for (EObject target : targets) {
						variable.addConstraint(new ReferenceConstraint(variables.get(target), ref));
					}
				} else {
					EObject target = (EObject) object.eGet(ref);
					if (target!=null) {
						variable.addConstraint(new ReferenceConstraint(variables.get(target), ref));
					}
				}
			}	
		}
		
	}
	
	public boolean isIsomorphicTo(EmfGraph graph) {
		
		// We do a quick comparison of the object count first:
		if (source.geteObjects().size()!=graph.geteObjects().size()) {
			return false;
		}
		
		// Create the domain map:
		Map<Variable, DomainSlot> domainMap = new HashMap<Variable, DomainSlot>();
		
		// Create the domain slots:
		for (Variable variable : variables.values()) {
			domainMap.put(variable, new DomainSlot(ATTRIBUTE_CONDITION_HANDLER, new HashSet<EObject>(), TRANSFORMATION_OPTIONS));
		}

		// Create the match finder:
		Matchfinder matchFinder = new Matchfinder(graph, domainMap, ATTRIBUTE_CONDITION_HANDLER);
		
		// Try to find a match:
		boolean matchFound = matchFinder.findSolution();
		
		// Check for surjectivity...
		
		return matchFound;
	}
	
}
