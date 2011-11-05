package org.eclipse.emf.henshin.matching.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.matching.constraints.AttributeConstraint;
import org.eclipse.emf.henshin.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.matching.constraints.ReferenceConstraint;
import org.eclipse.emf.henshin.matching.constraints.Variable;

/**
 * A graph isomorphy checker for {@link EmfGraph}s.
 * @author Christian Krause
 */
public class GraphIsomorphyChecker {
	
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
	@SuppressWarnings("unused")
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
					EList<EObject> target = (EList<EObject>) object.eGet(ref);
					
				} else {
					EObject target = (EObject) object.eGet(ref);
					
				}
				
				variable.addConstraint(new ReferenceConstraint(variable, ref));
			}
			
		}


		
	}
	
	public boolean isIsomorphic(EmfGraph graph, Map<EObject,EObject> preMatch) {
	
		Map<Variable, DomainSlot> domainMap = new HashMap<Variable, DomainSlot>();

		return false;
	}
	
}
