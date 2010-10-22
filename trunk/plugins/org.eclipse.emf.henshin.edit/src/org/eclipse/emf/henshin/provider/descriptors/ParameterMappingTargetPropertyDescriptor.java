/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.emf.henshin.provider.descriptors;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.CountedUnit;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * Property descriptor for the <code>target</code> feature of model class
 * {@link ParameterMapping}. This descriptor collects {@link Parameter} objects
 * of direct subunits of the containing {@link TransformationUnit} and of its
 * parent unit.<br>
 * 
 * @author Stefan Jurack
 * 
 */
public class ParameterMappingTargetPropertyDescriptor extends ItemPropertyDescriptor {
	
	/**
	 * @param adapterFactory
	 * @param resourceLocator
	 * @param displayName
	 * @param description
	 * @param feature
	 */
	public ParameterMappingTargetPropertyDescriptor(AdapterFactory adapterFactory,
			ResourceLocator resourceLocator, String displayName, String description,
			EStructuralFeature feature) {
		super(adapterFactory, resourceLocator, displayName, description, feature, true, false,
				true, null, null, null);
	}// constructor
	
	/**
	 * Collects all parameters, which are provided by the combo box in a related
	 * property sheet.
	 * 
	 * @see org.eclipse.emf.edit.provider.ItemPropertyDescriptor#getComboBoxObjects(Object)
	 * 
	 */
	@Override
	protected Collection<?> getComboBoxObjects(Object object) {
		
		Collection<Parameter> result = null;
		
		if (object instanceof ParameterMapping) {
			
			ParameterMapping pmapping = (ParameterMapping) object;
			TransformationUnit owningUnit = (TransformationUnit) pmapping.eContainer();
			
			result = new ArrayList<Parameter>();
			
			Collection<TransformationUnit> relevantUnits;
			relevantUnits = getSubUnits(owningUnit);
			relevantUnits.addAll(getParentUnits(owningUnit));
			
			for (TransformationUnit subunit : relevantUnits) {
				result.addAll(subunit.getParameters());
			}// for
			
			return result;
		} else {
			return super.getComboBoxObjects(object);
		}// if else
	}// getComboBoxObjects
	
	/**
	 * Collects and returns the subunit of the given {@link TransformationUnit},
	 * respecting the exact kind the given unit.
	 * 
	 * @param unit
	 * @return
	 */
	private Collection<TransformationUnit> getSubUnits(TransformationUnit unit) {
		Collection<TransformationUnit> unitList = new ArrayList<TransformationUnit>();
		
		if (unit instanceof AmalgamationUnit) {
			AmalgamationUnit au = (AmalgamationUnit) unit;
			unitList.add(au.getKernelRule());
			unitList.addAll(au.getMultiRules());
		} else if (unit instanceof ConditionalUnit) {
			ConditionalUnit cu = (ConditionalUnit) unit;
			unitList.add(cu.getIf());
			unitList.add(cu.getThen());
			unitList.add(cu.getElse());
		} else if (unit instanceof CountedUnit) {
			unitList.add(((CountedUnit) unit).getSubUnit());
		} else if (unit instanceof IndependentUnit) {
			unitList.addAll(((IndependentUnit) unit).getSubUnits());
		} else if (unit instanceof PriorityUnit) {
			unitList.addAll(((PriorityUnit) unit).getSubUnits());
		} else if (unit instanceof SequentialUnit) {
			unitList.addAll(((SequentialUnit) unit).getSubUnits());
		}
		return unitList;
	}// getSubUnits
	
	/**
	 * Collects the list of unit, which refer (not contain!) to the given unit.
	 * 
	 * @param unit
	 * @return
	 */
	private Collection<TransformationUnit> getParentUnits(TransformationUnit unit) {
		Collection<TransformationUnit> unitList = new ArrayList<TransformationUnit>();
		
		if ((unit.eContainer() != null) && (unit.eContainer() instanceof TransformationSystem)) {
			TransformationSystem ts = (TransformationSystem) unit.eContainer();
			
			for (TransformationUnit tu : ts.getTransformationUnits()) {
				
				/**
				 * Since all TransformationUnits are contained in
				 * TransformationSystem, we only need to iterate over the list
				 * and check the directly contained subunits.
				 */
				Collection<TransformationUnit> c = getSubUnits(tu);
				if (c.contains(unit)) {
					unitList.add(tu);
				}// if
			}// for
		}// if
		
		return unitList;
	}// getSubUnits
	
}// class
