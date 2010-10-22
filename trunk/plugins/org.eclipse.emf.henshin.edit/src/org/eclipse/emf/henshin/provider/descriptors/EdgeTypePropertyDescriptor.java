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
package org.eclipse.emf.henshin.provider.descriptors;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.henshin.model.Edge;

/**
 * Property descriptor for the <code>type</code> feature of model class
 * {@link Edge}. This descriptor looks if source and/or target is/are set and
 * shows related types only.
 * 
 * @author Stefan Jurack
 * 
 */
public class EdgeTypePropertyDescriptor extends ItemPropertyDescriptor {
	
	/**
	 * @param adapterFactory
	 * @param resourceLocator
	 * @param displayName
	 * @param description
	 * @param feature
	 */
	public EdgeTypePropertyDescriptor(AdapterFactory adapterFactory,
			ResourceLocator resourceLocator, String displayName, String description,
			EStructuralFeature feature) {
		super(adapterFactory, resourceLocator, displayName, description, feature, true, false,
				true, null, null, null);
	}// constructor
	
	/**
	 * Collects all types, being provided by the combo box in a related property
	 * sheet.<br>
	 * If the given <code>object</code>, which is an Edge instance, is equipped
	 * with a source and/or target, only corresponding types are returned.
	 * Otherwise any type is returned.
	 * 
	 * 
	 * @see org.eclipse.emf.edit.provider.ItemPropertyDescriptor#getComboBoxObjects(Object)
	 * 
	 */
	@Override
	protected Collection<?> getComboBoxObjects(Object object) {
		
		if (object instanceof Edge) {
			Edge edge = (Edge) object;
			
			@SuppressWarnings("unchecked")
			Collection<EReference> edgeTypeList = (Collection<EReference>) super
					.getComboBoxObjects(object);
			
			if (edge.getSource() != null && edge.getSource().getType() != null) {
				EClass sourceType = edge.getSource().getType();
				Collection<EReference> c = sourceType.getEAllReferences();
				Iterator<EReference> it = edgeTypeList.iterator();
				while (it.hasNext()) {
					if (!c.contains(it.next())) it.remove();
				}// while
			}// if
			
			if (edge.getTarget() != null && edge.getTarget().getType() != null) {
				EClass targetType = edge.getTarget().getType();
				Iterator<EReference> it = edgeTypeList.iterator();
				while (it.hasNext()) {
					EClass ec = (EClass) it.next().getEType();
					if (!ec.isSuperTypeOf(targetType)) it.remove();
				}// while
			}// if
			
			if (!edgeTypeList.contains(null)) edgeTypeList.add(null);
			return edgeTypeList;
		}// if
		
		return super.getComboBoxObjects(object);
	}// getComboBoxObjects
	
}// class
