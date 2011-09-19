/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.matching.constraints;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.matching.conditions.attribute.AttributeConditionHandler;
import org.eclipse.emf.henshin.matching.util.TransformationOptions;
import org.eclipse.emf.henshin.matching.EmfGraph;

public class DomainSlot {
	/**
	 * The variable which will initialize this domain slot. All other variables
	 * which use this slot will only validate their constraints.
	 */
	Variable owner;
	
	/**
	 * Flag that describes whether this domain slot is initialized. After
	 * initialization the domain contains all possible values that are type
	 * compatible with the type constraint of the owner variable.
	 */
	boolean initialized;
	
	/**
	 * Flag that describes whether this domain slot is locked. A slot is locked
	 * if a value from the domain is selected that fulfills the constraints of
	 * the owner variable.
	 */
	boolean locked;
	
	/**
	 * The current fixed value for this domain slot. Instantiate() will pick one
	 * value from the domain.
	 */
	EObject value;
	
	/** All possible values this domain slot might use for instantiation. */
	List<EObject> domain;
	
	/**
	 * A list of required values created by binary constraints from external
	 * variables.
	 */
	Collection<EObject> temporaryDomain;
	
	/**
	 * All changes done to other domain slots by ReferenceConstraints of
	 * variables that use this domain slot.
	 */
	Map<BinaryConstraint, DomainChange> remoteChangeMap;
	
	/**
	 * A collection of parameters that were initialized by constraints belonging
	 * to variables of this domain slot.
	 */
	Collection<String> initializedParameters;
	
	/**
	 * The handler for all attribute conditions. If a parameter constraints
	 * fixes the value of a parameter, the handler checks all conditions.
	 */
	AttributeConditionHandler conditionHandler;
	
	/** The options which shall be used by this domain slot. */
	TransformationOptions options;
	
	/**
	 * A collection of variables whose constraints were already validated
	 * against the current value.
	 */
	Collection<Variable> checkedVariables;
	
	/**
	 * A collection of the values of all domain slots that are currently locked.
	 * Required to ensure injectivity.
	 */
	Collection<EObject> usedObjects;
	
	/**
	 * Constructor
	 * 
	 * @param conditionHandler
	 * @param usedObjects
	 * @param options
	 */
	public DomainSlot(AttributeConditionHandler conditionHandler, Collection<EObject> usedObjects,
			TransformationOptions options) {
		this.locked = false;
		this.initialized = false;
		this.conditionHandler = conditionHandler;
		
		this.usedObjects = usedObjects;
		this.remoteChangeMap = new HashMap<BinaryConstraint, DomainChange>();
		this.initializedParameters = new ArrayList<String>();
		this.checkedVariables = new HashSet<Variable>();
		
		this.options = options;
	}
	
	/**
	 * Sets the value of the domain slot.
	 * 
	 * @param variable
	 * @param domainMap
	 * @param graph
	 * @return
	 */
	public boolean instantiate(Variable variable, Map<Variable, DomainSlot> domainMap,
			EmfGraph graph) {
		if (!initialized) {
			initialized = true;
			owner = variable;
			
			// If temporaryDomain is not null, there exist ReferenceConstraints
			// pointing to this slot.
			if (temporaryDomain != null) {
				domain = new ArrayList<EObject>(temporaryDomain);
			}
			
			variable.getTypeConstraint().initDomain(this, graph);
			if (domain.isEmpty()) return false;
			
			if (!options.isDeterministic()) Collections.shuffle(domain);
			
			if (options.isInjective()) domain.removeAll(usedObjects);
		}
		
		if (!locked) {
			if (domain.isEmpty()) return false;
			
			value = domain.get(domain.size() - 1);
			domain.remove(domain.size() - 1);
			usedObjects.add(value);
			locked = true;
		}
		
		if (!checkedVariables.contains(variable)) {
			if (!variable.getTypeConstraint().check(this)) return false;
			
			for (AttributeConstraint constraint : variable.getAttributeConstraints()) {
				if (!constraint.check(this)) return false;
			}
			
			if (options.isDangling()) {
				for (DanglingConstraint constraint : variable.getDanglingConstraints()) {
					if (!constraint.check(value, graph)) return false;
				}
			}
			
			for (ParameterConstraint constraint : variable.getParameterConstraints()) {
				if (!conditionHandler.isSet(constraint.parameterName))
					initializedParameters.add(constraint.parameterName);
				if (!constraint.check(this)) return false;
			}
			
			for (ReferenceConstraint constraint : variable.getReferenceConstraints()) {
				DomainSlot target = domainMap.get(constraint.getTarget());
				
				if (!constraint.check(this, target)) {
					return false;
				}
			}
			
			checkedVariables.add(variable);
		}
		
		return true;
	}
	
	/**
	 * Removes the lock on this domain slot. If the domain contains additional
	 * objects {@link #instantiate(Variable, Map, EmfGraph)} may be called
	 * again.
	 * 
	 * @param sender
	 *            The variable which uses this domain slot. Only the variable
	 *            which originally initialized this domain slot is able to
	 *            unlock it.
	 * 
	 * @return true, if another instantiation is possible.
	 */
	public boolean unlock(Variable sender) {
		// TODO: use ReferenceConstraints in inverse order in case of two
		// different edges are between the same two nodes
		for (ReferenceConstraint constraint : sender.getReferenceConstraints()) {
			DomainChange change = remoteChangeMap.get(constraint);
			if (change != null) {
				change.slot.temporaryDomain = change.originalValues;
				remoteChangeMap.remove(constraint);
			}
		}
		
		if (locked && sender == owner) {
			locked = false;
			usedObjects.remove(value);
			
			for (String parameterName : initializedParameters) {
				conditionHandler.unsetParameter(parameterName);
			}
			initializedParameters.clear();
			checkedVariables.clear();
			
			return !(domain == null || domain.isEmpty());
		} else {
			checkedVariables.remove(sender);
		}
		
		return false;
	}
	
	/**
	 * Resets this domain slot to the state before it was initialized.
	 * 
	 * @param sender
	 *            The variable which uses this domain slot. Only the variable
	 *            which originally initialized this domain slot is able to clear
	 *            it.
	 */
	public void clear(Variable sender) {
		unlock(sender);
		
		if (sender == owner) {
			initialized = false;
			
			owner = null;
			value = null;
			domain = null;
			remoteChangeMap = new HashMap<BinaryConstraint, DomainChange>();
		}
	}
	
	/**
	 * Checks whether the domain contains additional possible objects that may
	 * be valid for a match.
	 * 
	 * @return true, if instantiation might be possible.
	 */
	public boolean instantiationPossible() {
		if (domain == null) return false;
		
		if (!locked) return domain.size() > 0;
		
		return false;
	}
	
	/**
	 * Locks a specific value for this slot. The slot will also be locked and
	 * marked as initialized and the value can only be changed by calling this
	 * method again.
	 * 
	 * @param value
	 *            The object this domain slot will be mapped to.
	 */
	public void fixInstantiation(EObject value) {
		this.locked = true;
		this.value = value;
		this.initialized = true;
		this.usedObjects.add(value);
		this.owner = null;
	}
}
