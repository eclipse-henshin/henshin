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
package org.eclipse.emf.henshin.provider.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.UnaryFormula;

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
	 * @param adapterFactory
	 * @param resourceLocator
	 * @param displayName
	 * @param description
	 * @param feature
	 * 
	 * @see ItemPropertyDescriptor
	 */
	public MappingImagePropertyDescriptor(AdapterFactory adapterFactory,
			ResourceLocator resourceLocator, String displayName, String description,
			EStructuralFeature feature) {
		super(adapterFactory, resourceLocator, displayName, description, feature, true, false,
				true, null, null, null);
	}// constructor
	
	/**
	 * Collects all nodes, which are provided by the combo box in a related
	 * property sheet.
	 * 
	 * @see org.eclipse.emf.edit.provider.ItemPropertyDescriptor#getComboBoxObjects(Object)
	 */
	@Override
	protected Collection<?> getComboBoxObjects(Object object) {
		
		Collection<Node> result = new ArrayList<Node>();
		
		if (object instanceof Mapping) {
			Mapping mapping = (Mapping) object;
			EObject eobject = mapping.eContainer();
			if (eobject instanceof Rule) {
				/*
				 * The image of a mapping contained in a rule may be any node in
				 * the RHS or in a directly contained nested condition.
				 */
				Rule rule = (Rule) eobject;
				result.addAll(rule.getRhs().getNodes());
				
				Formula f = rule.getLhs().getFormula();
				if (f != null) {
					List<NestedCondition> ncList = new ArrayList<NestedCondition>();
					collectNestedConditions(f, ncList);
					
					for (NestedCondition nc : ncList) {
						result.addAll(nc.getConclusion().getNodes());
					}// for
				}// if
				
			} else if (eobject instanceof NestedCondition) {
				/*
				 * The image of a mapping contained in a nested condition may be
				 * any node in a directly contained nested condition.
				 */
				NestedCondition ncond = (NestedCondition) eobject;
				Formula f = ncond.getConclusion().getFormula();
				if (f != null) {
					List<NestedCondition> ncList = new ArrayList<NestedCondition>();
					collectNestedConditions(f, ncList);
					
					for (NestedCondition nc : ncList) {
						result.addAll(nc.getConclusion().getNodes());
					}// for
				}// if
				
			} else if (eobject instanceof AmalgamationUnit) {
				/*
				 * The image of a mapping contained in a an amalgamation unit
				 * may be any node in a LHS/RHS of a multi rule.
				 */
				AmalgamationUnit au = (AmalgamationUnit) eobject;
				EStructuralFeature sf = mapping.eContainingFeature();
				
				if (sf.getFeatureID() == HenshinPackage.AMALGAMATION_UNIT__LHS_MAPPINGS) {
					for (Rule rule : au.getMultiRules()) {
						result.addAll(rule.getLhs().getNodes());
					}// for
				} else if (sf.getFeatureID() == HenshinPackage.AMALGAMATION_UNIT__RHS_MAPPINGS) {
					for (Rule rule : au.getMultiRules()) {
						result.addAll(rule.getRhs().getNodes());
					}// for
				}// if else if
			}// if else if
		}// if
		
		return result;
	}// getComboBoxObjects
	
	/**
	 * Collects recursively all {@link NestedCondition}s the given
	 * <code>formula</code> is associated with, i.e. the hierarchy of the given
	 * <code>formula</code>. If the formula is a {@link NestedCondition} itself,
	 * it is added to the <code>resultList</code>.<br>
	 * Remark: Initially the resultList shall be not null.
	 * 
	 * @param formula
	 * @param resultList
	 */
	private void collectNestedConditions(Formula formula, List<NestedCondition> resultList) {
		
		if (formula == null) return;
		
		if (formula instanceof BinaryFormula) {
			BinaryFormula bf = (BinaryFormula) formula;
			collectNestedConditions(bf.getLeft(), resultList);
			collectNestedConditions(bf.getRight(), resultList);
		} else if (formula instanceof UnaryFormula) {
			UnaryFormula uf = (UnaryFormula) formula;
			collectNestedConditions(uf.getChild(), resultList);
		} else {
			resultList.add((NestedCondition) formula);
		}
	}// collectNestedCondition
	
}// class
