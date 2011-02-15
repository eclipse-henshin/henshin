/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.emf.henshin.provider.descriptors;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * Property descriptor for the <code>source</code> and the <code>target</code>
 * feature of model class {@link ParameterMapping}. This descriptor collects
 * {@link Parameter} objects from within the current (containing)
 * {@link TransformationUnit} and its direct subunits.<br>
 * 
 * @author Stefan Jurack (sjurack)
 * 
 */
public class ParameterMappingPropertyDescriptor extends ItemPropertyDescriptor {
	
	/**
	 * @param adapterFactory
	 * @param resourceLocator
	 * @param displayName
	 * @param description
	 * @param feature
	 */
	public ParameterMappingPropertyDescriptor(AdapterFactory adapterFactory,
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
		
		if (object instanceof ParameterMapping) {
			
			ParameterMapping pmapping = (ParameterMapping) object;
			TransformationUnit owningUnit = (TransformationUnit) pmapping.eContainer();
			
			Collection<Parameter> result = new HashSet<Parameter>();
			
			result.addAll(owningUnit.getParameters());
			
			for (TransformationUnit tu : owningUnit.getSubUnits(false)) {
				result.addAll(tu.getParameters());
			}// for
			
			return result;
		} else {
			return super.getComboBoxObjects(object);
		}// if else
	}// getComboBoxObjects
	
}// class
