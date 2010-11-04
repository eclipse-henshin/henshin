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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.henshin.commands.dnd.NonContainmentTransientIPDragAndDropCommand;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * This is the item provider for a {@link TransformationUnit} object being
 * referred to by an {@link AmalgamationUnit}, in fact by its feature
 * {@link HenshinPackage#AMALGAMATION_UNIT__KERNEL_RULE}. <br>
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
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
		
		return new NonContainmentTransientIPDragAndDropCommand(domain, (TransformationUnit) target,
				HenshinPackage.Literals.AMALGAMATION_UNIT__KERNEL_RULE, collection);
	}// createDragAndDropCommand
	
}// class
