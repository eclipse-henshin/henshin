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
/**
 * 
 */
package org.eclipse.emf.henshin.provider.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Property descriptor for the <code>image</code> feature of model class
 * {@link Mapping}. This descriptor collects nodes which are provided as a combo
 * box. In particular, only those nodes shall be collected, which are suitable
 * as image according to a certain (pre-selected) origin.
 * 
 * @author Stefan Jurack
 * 
 */
public class MappingImagePropertyDescriptor extends ItemPropertyDescriptor {

	/**
	 * 
	 * 
	 * @param adapterFactory
	 * @param resourceLocator
	 * @param displayName
	 * @param description
	 * @param feature
	 * 
	 * @see ItemPropertyDescriptor
	 */
	public MappingImagePropertyDescriptor(AdapterFactory adapterFactory,
			ResourceLocator resourceLocator, String displayName,
			String description, EStructuralFeature feature) {
		super(adapterFactory, resourceLocator, displayName, description,
				feature, true, false, true, null, null, null);
	}// constructor

	/**
	 * Collects all nodes, which are provided by the combo box in a related
	 * property sheet.
	 * 
	 * @see org.eclipse.emf.edit.provider.ItemPropertyDescriptor#getComboBoxObjects(Object)
	 * 
	 */
	@Override
	protected Collection<?> getComboBoxObjects(Object object) {

		Collection<Node> result = null;

		if (object instanceof Mapping) {
			Mapping mapping = (Mapping) object;
			EObject eobject = mapping.eContainer();
			if (eobject instanceof Rule) {
				/*
				 * Image in a rule may be any node in the RHS.
				 */
				Rule rule = (Rule) eobject;
				result = rule.getRhs().getNodes();
				// Collection<EObject> result = getReachableObjectsOfType(
				// (EObject) object, feature.getEType());
				// result.removeAll(rule.getLhs().getNodes());
			} else if (eobject instanceof NestedCondition) {
				/*
				 * Image in a nested condition may be any node in the
				 * conclusion, which is the graph directly contained by the
				 * nested condition.
				 */
				NestedCondition nc = (NestedCondition) eobject;
				result = nc.getConclusion().getNodes();
			} else if (eobject instanceof AmalgamationUnit) {
				/*
				 * The image in an amalgamation unit depends on the reference
				 * the mapping is contained in. Either the all multi rule's LHS
				 * nodes or RHS nodes may be an image.
				 */
				AmalgamationUnit au = (AmalgamationUnit) eobject;
				EStructuralFeature sf = mapping.eContainingFeature();

				if (sf.getFeatureID() == HenshinPackage.AMALGAMATION_UNIT__LHS_MAPPINGS) {
					result = new ArrayList<Node>();
					for (Rule rule : au.getMultiRules()) {
						result.addAll(rule.getLhs().getNodes());
					}// for
				} else if (sf.getFeatureID() == HenshinPackage.AMALGAMATION_UNIT__RHS_MAPPINGS) {
					result = new ArrayList<Node>();
					for (Rule rule : au.getMultiRules()) {
						result.addAll(rule.getRhs().getNodes());
					}// for
				}// if else if
			}// if else if
		}// if

		if (result != null) {
			return Collections.unmodifiableCollection(result);
		} else {
			return super.getComboBoxObjects(object);
		}// if else
	}// getComboBoxObjects
}// class
