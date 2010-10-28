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
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.henshin.commands.dnd.KernelRuleDragAndDropCommand;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;

/**
 * @author Stefan Jurack (sjurack)
 * 
 */
public class KernelRuleItemProvider extends TransientItemProvider {
	
	/**
	 * @param adapterFactory
	 * @param target
	 */
	public KernelRuleItemProvider(AdapterFactory adapterFactory, EObject target) {
		super(adapterFactory, target);
	}// constructor
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#getChildFeature(java
	 * .lang.Object, java.lang.Object)
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		if (isValidValue(target, child, HenshinPackage.Literals.AMALGAMATION_UNIT__KERNEL_RULE)) {
			return HenshinPackage.Literals.AMALGAMATION_UNIT__KERNEL_RULE;
		}
		return super.getChildFeature(target, child);
	}// getChildFeature
	
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
			childrenFeatures.add(HenshinPackage.Literals.AMALGAMATION_UNIT__KERNEL_RULE);
		}// if
		return childrenFeatures;
	}// getChildrenFeatures
	
	// @Override
	// protected EStructuralFeature getChildFeature(Object object, Object child)
	// {
	// // Check the type of the specified child object and return the proper
	// feature to use for
	// // adding (see {@link AddCommand}) it as a child.
	// EStructuralFeature sf = super.getChildFeature(target, child);
	// return sf;
	// }
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#getText(java.lang.Object
	 * )
	 */
	@Override
	public String getText(Object object) {
		
		return getString("_UI_AmalgamationUnit_kernelRule_feature");
	}// getText
	
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
		 * The one child, the kernel rule, if this item provider shall be
		 * wrapped, as it represent no containment but a reference to a rule.
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
		 * The child of this transient item provider represents a referee of an
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
		
		return new KernelRuleDragAndDropCommand(domain, (AmalgamationUnit) target, collection);
	}// createDragAndDropCommand
	
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
		return super.createSetCommand(domain, owner,
				HenshinPackage.Literals.AMALGAMATION_UNIT__KERNEL_RULE, value, index);
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
		return super.createSetCommand(domain, owner,
				HenshinPackage.Literals.AMALGAMATION_UNIT__KERNEL_RULE, value);
	}
	
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
		return super.createRemoveCommand(domain, owner,
				HenshinPackage.Literals.AMALGAMATION_UNIT__KERNEL_RULE, collection);
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
		return super.createRemoveCommand(domain, owner,
				HenshinPackage.Literals.AMALGAMATION_UNIT__KERNEL_RULE, collection);
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
		return super.createReplaceCommand(domain, owner,
				HenshinPackage.Literals.AMALGAMATION_UNIT__KERNEL_RULE, value, collection);
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
		return super.createReplaceCommand(domain, owner,
				HenshinPackage.Literals.AMALGAMATION_UNIT__KERNEL_RULE, value, collection);
	}
	
}// class
