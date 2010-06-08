/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University of Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.internal.matching;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.common.util.GraphSkeleton;
import org.eclipse.emf.henshin.common.util.TransformationOptions;
import org.eclipse.emf.henshin.internal.conditions.attribute.AttributeConditionHandler;
import org.eclipse.emf.henshin.internal.constraints.AttributeConstraint;
import org.eclipse.emf.henshin.internal.constraints.DomainChange;
import org.eclipse.emf.henshin.internal.constraints.ParameterConstraint;
import org.eclipse.emf.henshin.internal.constraints.ReferenceConstraint;

public class DomainSlot<TType, TNode> {
	// The variable which will initialize this domain slot. All other variables
	// which use this slot will only validate their constraints.
	Variable<TType, TNode> owner;

	// Flag that describes whether this domain slot is initialized. After
	// initialization the domain contains all possible values that are type
	// compatible with the type constraint of the owner variable.
	boolean initialized;

	// Flag that describes whether this domain slot is locked. A slot is locked
	// if a value from the domain is selected that fulfills the constraints of
	// the owner variable.
	boolean locked;

	GraphSkeleton<TType, TNode> graph;
	
	// The current fixed value for this domain slot. Instanciate() will pick one
	// value from the domain.
	TNode value;

	// All possible values this domain slot might use for instanciation.
	List<TNode> domain;

	// A copy of the domain state prior to the initialisation.
	// ReferenceConstraints can cause domain updates to slots that aren't
	// initialized yet.
	List<TNode> localChanges;

	// All changes done to other domain slots by ReferenceConstraints of
	// variables that use this domain slot.
	Map<DomainSlot<TType, TNode>, List<TNode>> slotChanges;

	// A collection of parameters that were initialized by contraints belonging
	// to variables of this domain slot.
	Collection<String> initializedParameters;

	// The handler for all attribute conditions. If a parameter constraints
	// fixes the value of a parameter, the handler checks all conditions.
	AttributeConditionHandler conditionHandler;

	// The options which shall be used by this domain slot.
	TransformationOptions options;

	// A collection of variables which constraints were already validated
	// against the current value.
	Collection<Variable<TType, TNode>> checkedVariables;

	// A collection of the values of all domain slots that are currently locked.
	// Required to ensure injectivity.
	Collection<TNode> usedObjects;

	public DomainSlot(AttributeConditionHandler conditionHandler,
			Collection<TNode> usedObjects, GraphSkeleton<TType, TNode> graph, TransformationOptions options) {
		this.locked = false;
		this.initialized = false;
		this.conditionHandler = conditionHandler;
		this.graph = graph;

		this.usedObjects = usedObjects;
		this.slotChanges = new HashMap<DomainSlot<TType, TNode>, List<TNode>>();
		this.initializedParameters = new ArrayList<String>();
		this.checkedVariables = new ArrayList<Variable<TType, TNode>>();

		this.options = options;
	}

	/**
	 * Sets the value of the domain slot. The domain will
	 * 
	 * @param variable
	 * @param domainMap
	 * @param graph
	 * @return
	 */
	public boolean instanciate(Variable<TType, TNode> variable,
			Map<Variable<TType, TNode>, DomainSlot<TType, TNode>> domainMap) {
		if (!initialized) {
			initialized = true;
			owner = variable;

			localChanges = (domain != null) ? new ArrayList<TNode>(domain)
					: null;

			domain = variable.getTypeConstraint().reduceDomain(domain, graph);

			if (!options.isDeterministic())
				Collections.shuffle(domain);

			if (options.isInjective())
				domain.removeAll(usedObjects);

			if (domain.size() == 0)
				return false;

			for (AttributeConstraint<TNode> constraint : variable
					.getAttributeConstraints()) {
				constraint.reduceDomain(domain);
				if (domain.size() == 0)
					return false;
			}
		}

		if (!locked) {
			checkedVariables.add(variable);

			if (domain.size() == 0) {
				return false;
			} else {
				value = domain.get(domain.size() - 1);
				domain.remove(domain.size() - 1);
				usedObjects.add(value);

				locked = true;
			}

			for (ParameterConstraint<TNode> constraint : variable
					.getParameterConstraints()) {
				if (!conditionHandler.isSet(constraint.getParameterName()))
					initializedParameters.add(constraint.getParameterName());
				if (!constraint.check(value, conditionHandler))
					return false;
			}

			for (ReferenceConstraint<TNode> constraint : variable
					.getReferenceConstraints()) {
				DomainSlot<TType, TNode> target = domainMap
						.get(constraint.getTarget());
				if (!applyReferenceConstraint(constraint, target)) {
					return false;
				}
			}
		} else {
			if (!checkedVariables.contains(variable)) {
				checkedVariables.add(variable);

				if (!variable.getTypeConstraint().check(value))
					return false;

				for (AttributeConstraint<TNode> constraint : variable
						.getAttributeConstraints()) {
					if (!constraint.check(value))
						return false;
				}

				for (ParameterConstraint<TNode> constraint : variable
						.getParameterConstraints()) {
					if (!constraint.check(value, conditionHandler))
						return false;
				}

				for (ReferenceConstraint<TNode> constraint : variable
						.getReferenceConstraints()) {
					DomainSlot<TType, TNode> target = domainMap.get(constraint
							.getTarget());
					if (!applyReferenceConstraint(constraint, target)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Checks whether a reference constraint of a variable is fulfilled by the
	 * currently used value.
	 * 
	 * @param constraint
	 *            The reference constraint that should be checked.
	 * @param target
	 *            The domain slot which is used by the target variable of the
	 *            refrence constraint.
	 * 
	 * @return true, if the constraint is valid.
	 */
	private boolean applyReferenceConstraint(
			ReferenceConstraint<TNode> constraint, DomainSlot<TType, TNode> target) {
		if (target.locked) {
			return constraint.check(value, target.value);
		} else {
			DomainChange<TNode> change = constraint.reduceTargetDomain(value,
					target.domain);
			target.domain = change.remainingObjects;
			slotChanges.put(target, change.removedObjects);

			return target.domain != null && target.domain.size() > 0;
		}
	}

	/**
	 * Removes the lock on this domain slot. If the domain contains additional
	 * objects instanciate may be called again.
	 * 
	 * @param sender
	 *            The variable which uses this domain slot. Only the variable
	 *            which originally initialized this domain slot is able to
	 *            unlock it.
	 * 
	 * @return true, on success and if another instanciation is possible.
	 */
	public boolean unlock(Variable<TType, TNode> sender) {
		if (locked && sender == owner) {
			locked = false;
			usedObjects.remove(value);

			for (String parameterName : initializedParameters) {
				conditionHandler.unsetParameter(parameterName);
			}
			initializedParameters.clear();

			for (DomainSlot<TType, TNode> slot : slotChanges.keySet()) {
				List<TNode> removedObjects = slotChanges.get(slot);

				if (removedObjects != null) {
					slot.domain.addAll(removedObjects);
				} else
					slot.domain = null;
			}
			slotChanges.clear();
			checkedVariables.clear();

			return !(domain == null || domain.size() == 0);
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
	public void clear(Variable<TType, TNode> sender) {
		unlock(sender);

		if (sender == owner) {
			initialized = false;

			owner = null;
			value = null;
			domain = localChanges;
		}
	}

	/**
	 * Checks whether the domain contains additional possible objects that may
	 * be valid for a match.
	 * 
	 * @return true, if instanciation might be possible.
	 */
	public boolean instanciationPossible() {
		if (domain == null)
			return false;

		if (!locked)
			return domain.size() > 0;

		return false;
	}

	/**
	 * Fixes the value for this slot. The slot will also be locked and marked as
	 * initialized and the value can only be changed by calling this method
	 * again.
	 * 
	 * @param value
	 *            The object this domain slot will be mapped to.
	 */
	public void fixInstanciation(TNode value) {
		this.locked = true;
		this.value = value;
		this.initialized = true;
		this.usedObjects.add(value);
		this.owner = null;
	}
}
