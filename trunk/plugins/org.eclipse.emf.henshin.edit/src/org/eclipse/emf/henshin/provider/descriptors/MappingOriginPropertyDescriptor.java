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
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Property descriptor for the <code>origin</code> feature of model class
 * {@link Mapping}. This descriptor collects nodes which are provided as a combo
 * box. In particular, only those nodes shall be collected, which are suitable
 * as origin i.e. nodes have to be located in a LHS graph.
 * 
 * @author Stefan Jurack
 * 
 */
public class MappingOriginPropertyDescriptor extends ItemPropertyDescriptor {

	/**
	 * @param adapterFactory
	 * @param resourceLocator
	 * @param displayName
	 * @param description
	 * @param feature
	 */
	public MappingOriginPropertyDescriptor(AdapterFactory adapterFactory,
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
				 * Origin in a rule may be any node in the LHS.
				 */
				Rule rule = (Rule) eobject;
				result = rule.getLhs().getNodes();
			} else if (eobject instanceof NestedCondition) {
				/*
				 * Origin in a nested condition may be any node in the premise,
				 * which is the enclosing graph.
				 */
				NestedCondition nc = (NestedCondition) eobject;
				Graph graph = null;
				EObject container = nc.eContainer();
				while (graph == null && container != null) {
					if (container instanceof Graph)
						graph = (Graph) container;
					else
						container = container.eContainer();
				}// while
				if (graph != null) {
					result = graph.getNodes();
				}// if
			} else if (eobject instanceof AmalgamationUnit) {
				/*
				 * Origin in an amalgamation unit depends on the reference the
				 * mapping is contained in. Either the kernel rule's LHS nodes
				 * or RHS nodes may be the origins.
				 */
				AmalgamationUnit au = (AmalgamationUnit) eobject;
				EStructuralFeature sf = mapping.eContainingFeature();

				if (sf.getFeatureID() == HenshinPackage.AMALGAMATION_UNIT__LHS_MAPPINGS) {
					result = new ArrayList<Node>();
					result = au.getKernelRule().getLhs().getNodes();
				} else if (sf.getFeatureID() == HenshinPackage.AMALGAMATION_UNIT__RHS_MAPPINGS) {
					result = new ArrayList<Node>();
					result = au.getKernelRule().getRhs().getNodes();
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
