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
package org.eclipse.emf.henshin.provider;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DragAndDropFeedback;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.henshin.model.HenshinPackage;

/**
 * @author Stefan Jurack (sjurack)
 * 
 */
public class MultiRuleItemProvider extends TransientItemProvider {
	
	/**
	 * @param adapterFactory
	 * @param target
	 */
	public MultiRuleItemProvider(AdapterFactory adapterFactory, EObject target) {
		super(adapterFactory, target);
	}// constructor
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#getChildrenFeatures
	 * (java.lang.Object)
	 */
	@Override
	protected Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES);
		}// if
		return childrenFeatures;
	}// getChildrenFeatures
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#getText(java.lang.Object
	 * )
	 */
	@Override
	public String getText(Object object) {
		return getString("_UI_AmalgamationUnit_multiRules_feature");
	}// getText
	
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper
		// feature to use for
		// adding (see {@link AddCommand}) it as a child.
		if (isValidValue(target, child, HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES)) {
			return HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES;
		}
		return super.getChildFeature(target, child);
		// EStructuralFeature sf = super.getChildFeature(target, child);
		// return sf;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse
	 * .emf.common.notify.Notification)
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);
		super.notifyChanged(notification);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#isWrappingNeeded(java
	 * .lang.Object)
	 */
	@Override
	protected boolean isWrappingNeeded(Object object) {
		
		/*
		 * All children, the multi rules, if this item provider shall be
		 * wrapped, as they represent no containments but references to rules.
		 */
		return Boolean.TRUE;
	}// isWrappingNeeded
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#createWrapper(org.eclipse
	 * .emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature,
	 * java.lang.Object, int)
	 */
	@Override
	protected Object createWrapper(EObject object, EStructuralFeature feature, Object value,
			int index) {
		
		/*
		 * All children of this transient item provider represent referees of an
		 * AmalgamationUnit thus shall be replaced by a wrapper
		 */

		return new DelegatingWrapperTrafoUnitItemProvider(value, object, feature, index,
				adapterFactory);
	}// createWrapper
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#createDragAndDropCommand
	 * (org.eclipse.emf.edit.domain.EditingDomain, java.lang.Object, float, int,
	 * int, java.util.Collection)
	 */
	@Override
	protected Command createDragAndDropCommand(EditingDomain domain, Object owner, float location,
			int operations, int operation, Collection<?> collection) {
		
		if (new AddCommand(domain, (EObject) owner,
				HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, collection).canExecute()) {
			return super.createDragAndDropCommand(domain, owner, location,
					DragAndDropFeedback.DROP_LINK, operation, collection);
		}// if
		return UnexecutableCommand.INSTANCE;
		
	}// createDragAndDropCommand
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.henshin.provider.TransientItemProvider#createRemoveCommand
	 * (org.eclipse.emf.edit.domain.EditingDomain,
	 * org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature,
	 * java.util.Collection)
	 */
	@Override
	protected Command createRemoveCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Collection<?> collection) {
		// TODO Auto-generated method stub
		return super.createRemoveCommand(domain, owner,
				HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, collection);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.henshin.provider.TransientItemProvider#createAddCommand
	 * (org.eclipse.emf.edit.domain.EditingDomain,
	 * org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature,
	 * java.util.Collection, int)
	 */
	@Override
	protected Command createAddCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Collection<?> collection, int index) {
		// TODO Auto-generated method stub
		return super.createAddCommand(domain, owner,
				HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, collection, index);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#createRemoveCommand
	 * (org.eclipse.emf.edit.domain.EditingDomain,
	 * org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EReference,
	 * java.util.Collection)
	 */
	@Override
	protected Command createRemoveCommand(EditingDomain domain, EObject owner, EReference feature,
			Collection<?> collection) {
		// TODO Auto-generated method stub
		return super.createRemoveCommand(domain, owner,
				HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, collection);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#createReplaceCommand
	 * (org.eclipse.emf.edit.domain.EditingDomain,
	 * org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature,
	 * org.eclipse.emf.ecore.EObject, java.util.Collection)
	 */
	@Override
	protected Command createReplaceCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, EObject value, Collection<?> collection) {
		// TODO Auto-generated method stub
		return super.createReplaceCommand(domain, owner,
				HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, value, collection);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#createReplaceCommand
	 * (org.eclipse.emf.edit.domain.EditingDomain,
	 * org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EReference,
	 * org.eclipse.emf.ecore.EObject, java.util.Collection)
	 */
	@Override
	protected Command createReplaceCommand(EditingDomain domain, EObject owner, EReference feature,
			EObject value, Collection<?> collection) {
		// TODO Auto-generated method stub
		return super.createReplaceCommand(domain, owner,
				HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, value, collection);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#createAddCommand(org
	 * .eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.ecore.EReference, java.util.Collection, int)
	 */
	@Override
	protected Command createAddCommand(EditingDomain domain, EObject owner, EReference feature,
			Collection<?> collection, int index) {
		// TODO Auto-generated method stub
		return super.createAddCommand(domain, owner,
				HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, collection, index);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#createMoveCommand(org
	 * .eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object, int)
	 */
	@Override
	protected Command createMoveCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Object value, int index) {
		// TODO Auto-generated method stub
		return super.createMoveCommand(domain, owner,
				HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, value, index);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#createMoveCommand(org
	 * .eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.ecore.EReference, org.eclipse.emf.ecore.EObject, int)
	 */
	@Override
	protected Command createMoveCommand(EditingDomain domain, EObject owner, EReference feature,
			EObject value, int index) {
		// TODO Auto-generated method stub
		return super.createMoveCommand(domain, owner,
				HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, value, index);
	}
	
	// /* (non-Javadoc)
	// * @see
	// org.eclipse.emf.henshin.provider.TransientItemProvider#createCommand(java.lang.Object,
	// org.eclipse.emf.edit.domain.EditingDomain, java.lang.Class,
	// org.eclipse.emf.edit.command.CommandParameter)
	// */
	// @Override
	// public Command createCommand(Object object, EditingDomain domain, Class
	// commandClass,
	// CommandParameter commandParameter) {
	//
	// if (commandParameter.getFeature()==null)
	// commandParameter.feature=HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES;
	// return super.createCommand(object, domain, commandClass,
	// commandParameter);
	// }
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#createSetCommand(org
	 * .eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object, int)
	 */
	@Override
	protected Command createSetCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Object value, int index) {
		// TODO Auto-generated method stub
		return super.createSetCommand(domain, owner,
				HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, value, index);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#createSetCommand(org
	 * .eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	@Override
	protected Command createSetCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Object value) {
		// TODO Auto-generated method stub
		return super.createSetCommand(domain, owner,
				HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, value);
	}
	
}// class
