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
	
	ParameterItemDelegator pItemDelegator;
	
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
		
		this.pItemDelegator = new ParameterItemDelegator(adapterFactory, resourceLocator);
		// redundant variable but prevents from permanent casts in method
		// getComboBoxObjects
		this.itemDelegator = this.pItemDelegator;
		
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
			// declare the current trafo unit to the item delegator
			this.pItemDelegator.setCurrentTrafoUnit(owningUnit);
			
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
	
	/**
	 * @author Stefan Jurack (sjurack)
	 * 
	 */
	private class ParameterItemDelegator extends ItemDelegator {
		TransformationUnit currentUnit;
		
		public ParameterItemDelegator(AdapterFactory adapterFactory, ResourceLocator resourceLocator) {
			super(adapterFactory, resourceLocator);
			currentUnit = null;
		}
		
		@Override
		public String getText(Object object) {
			Parameter p = (Parameter) object;
			String text = normalize(p.getName());
			if (currentUnit == null || !currentUnit.equals(p.getUnit())) {
				final String unitName = normalize(p.getUnit().getName());
				text = text + "  [" + unitName + "]";
			}
			return text;
			
		}// getText
		
		private String normalize(String s) {
			return (s == null || s.trim().isEmpty()) ? "_" : s;
		}
		
		/**
		 * Sets the owning unit of the current parameter mapping in order to
		 * optimize the visualization of parameters in the combo box.
		 * 
		 * @param unit
		 */
		public void setCurrentTrafoUnit(TransformationUnit unit) {
			this.currentUnit = unit;
		}// setCurrentTrafoUnit
		
	}// inner class
	
}// class
