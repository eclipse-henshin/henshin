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
package org.eclipse.emf.henshin.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.emf.henshin.model.ConditionalUnit} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ConditionalUnitItemProvider
	extends TransformationUnitItemProvider
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConditionalUnitItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

		}
		return itemPropertyDescriptors;
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(HenshinPackage.Literals.CONDITIONAL_UNIT__IF);
			childrenFeatures.add(HenshinPackage.Literals.CONDITIONAL_UNIT__THEN);
			childrenFeatures.add(HenshinPackage.Literals.CONDITIONAL_UNIT__ELSE);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns ConditionalUnit.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ConditionalUnit"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((ConditionalUnit)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_ConditionalUnit_type") :
			getString("_UI_ConditionalUnit_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(ConditionalUnit.class)) {
			case HenshinPackage.CONDITIONAL_UNIT__IF:
			case HenshinPackage.CONDITIONAL_UNIT__THEN:
			case HenshinPackage.CONDITIONAL_UNIT__ELSE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__IF,
				 HenshinFactory.eINSTANCE.createRule()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__IF,
				 HenshinFactory.eINSTANCE.createIndependentUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__IF,
				 HenshinFactory.eINSTANCE.createSequentialUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__IF,
				 HenshinFactory.eINSTANCE.createConditionalUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__IF,
				 HenshinFactory.eINSTANCE.createPriorityUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__IF,
				 HenshinFactory.eINSTANCE.createAmalgamationUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__IF,
				 HenshinFactory.eINSTANCE.createCountedUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__THEN,
				 HenshinFactory.eINSTANCE.createRule()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__THEN,
				 HenshinFactory.eINSTANCE.createIndependentUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__THEN,
				 HenshinFactory.eINSTANCE.createSequentialUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__THEN,
				 HenshinFactory.eINSTANCE.createConditionalUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__THEN,
				 HenshinFactory.eINSTANCE.createPriorityUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__THEN,
				 HenshinFactory.eINSTANCE.createAmalgamationUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__THEN,
				 HenshinFactory.eINSTANCE.createCountedUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__ELSE,
				 HenshinFactory.eINSTANCE.createRule()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__ELSE,
				 HenshinFactory.eINSTANCE.createIndependentUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__ELSE,
				 HenshinFactory.eINSTANCE.createSequentialUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__ELSE,
				 HenshinFactory.eINSTANCE.createConditionalUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__ELSE,
				 HenshinFactory.eINSTANCE.createPriorityUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__ELSE,
				 HenshinFactory.eINSTANCE.createAmalgamationUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.CONDITIONAL_UNIT__ELSE,
				 HenshinFactory.eINSTANCE.createCountedUnit()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == HenshinPackage.Literals.CONDITIONAL_UNIT__IF ||
			childFeature == HenshinPackage.Literals.CONDITIONAL_UNIT__THEN ||
			childFeature == HenshinPackage.Literals.CONDITIONAL_UNIT__ELSE;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
