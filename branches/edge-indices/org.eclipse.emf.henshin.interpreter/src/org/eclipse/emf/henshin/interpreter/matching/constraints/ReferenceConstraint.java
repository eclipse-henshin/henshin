/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.matching.constraints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * This constraint checks whether the value of an EReference contains 
 * objects from the target domain.
 * 
 * @author Enrico Biermann, Christian Krause
 */
public class ReferenceConstraint implements BinaryConstraint {
	
	// Target variable:
	final Variable targetVariable;
	
	// Reference:
	final EReference reference;
	
	// Index (either a constant or a parameter name):
	final Object index;

	// Whether the index is a constant or a parameter name:
	final boolean isConstantIndex;
	
	/**
	 * Default constructor.
	 * @param target Target variable.
	 * @param reference Reference.
	 * @param index Either a constant index (can be <code>null</code>) or a parameter name.
	 * @param isConstantIndex Whether the index a constant or a parameter name.
	 */
	public ReferenceConstraint(Variable target, EReference reference, Object index, boolean isConstantIndex) {
		this.targetVariable = target;
		this.reference = reference;
		this.index = index;
		this.isConstantIndex = isConstantIndex;
	}
	
	/**
	 * Convenience constructor.
	 * @param target Target variable.
	 * @param reference Reference.
	 */
	public ReferenceConstraint(Variable target, EReference reference) {
		this(target, reference, null, true);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.matching.constraints.BinaryConstraint#check(org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot, org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean check(DomainSlot source, DomainSlot target) {

		// Source variable must be locked:
		if (!source.locked) {
			return false;
		}

		// Get the target objects:
		List<EObject> targetObjects;
		if (reference.isMany()) {
			targetObjects = (List<EObject>) source.value.eGet(reference);
			if (targetObjects.isEmpty()) {
				return false;
			}
		} else {
			EObject obj = (EObject) source.value.eGet(reference);
			if (obj==null) {
				return false;
			}
			targetObjects = Collections.singletonList(obj);
		}

		// Calculate the index:
		Integer calculatedIndex = null;
		if (isConstantIndex) {
			calculatedIndex = (index!=null) ? ((Number) index).intValue() : null;
		} else {
			String parameterName = (String) index;
			if (source.conditionHandler.isSet(parameterName)) {
				calculatedIndex = ((Number) source.conditionHandler.getParameter(parameterName)).intValue();
			}
		}

		// Take care of negative indices:
		if (calculatedIndex!=null && calculatedIndex<0) {
			calculatedIndex = targetObjects.size() + calculatedIndex;
		}

		// Target domain slot locked?
		if (target.locked) {
			
			// Check if the parameter value still needs to be set:
			if (!isConstantIndex && !source.conditionHandler.isSet((String) index)) {
				
				// Try to initialize the parameter with real index. Might fail due to attribute conditions.
				calculatedIndex = targetObjects.indexOf(target.value);
				if (target.conditionHandler.setParameter((String) index, calculatedIndex)) {
					target.initializedParameters.add((String) index);
					return true;
				} else {
					target.conditionHandler.unsetParameter((String) index);
					return false;
				}
				
			} else {

				// Check if the reference constraint if fulfilled:
				return (calculatedIndex!=null) ?
						targetObjects.indexOf(target.value)==calculatedIndex :
						targetObjects.contains(target.value);
			}
			
		} else {
			
			// Target not locked yet. Create a domain change to restrict the target domain:
			DomainChange change = new DomainChange(target, target.temporaryDomain);
			source.remoteChangeMap.put(this, change);
			
			// Calculated temporary domain:
			if (calculatedIndex!=null) {
				if (calculatedIndex>=0 && calculatedIndex<targetObjects.size()) {
					target.temporaryDomain = Collections.singletonList(targetObjects.get(calculatedIndex));
				} else {
					target.temporaryDomain = Collections.emptyList();
				}
			} else {
				target.temporaryDomain = new ArrayList<EObject>(targetObjects);
			}
			if (change.originalValues!=null) {
				target.temporaryDomain.retainAll(change.originalValues);
			}
			
			// Temporary domain must not be empty:
			return !target.temporaryDomain.isEmpty();
			
		}

	}
	
}