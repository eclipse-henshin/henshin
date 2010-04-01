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

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.emf.henshin.model.CountedUnit;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.emf.henshin.model.CountedUnit} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class CountedUnitItemProvider
	extends DescribedElementItemProvider
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
	public CountedUnitItemProvider(AdapterFactory adapterFactory) {
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

			addNamePropertyDescriptor(object);
			addActivatedPropertyDescriptor(object);
			addCountPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_NamedElement_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_NamedElement_name_feature", "_UI_NamedElement_type"),
				 HenshinPackage.Literals.NAMED_ELEMENT__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Activated feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addActivatedPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TransformationUnit_activated_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TransformationUnit_activated_feature", "_UI_TransformationUnit_type"),
				 HenshinPackage.Literals.TRANSFORMATION_UNIT__ACTIVATED,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Count feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCountPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_CountedUnit_count_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_CountedUnit_count_feature", "_UI_CountedUnit_type"),
				 HenshinPackage.Literals.COUNTED_UNIT__COUNT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
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
			childrenFeatures.add(HenshinPackage.Literals.TRANSFORMATION_UNIT__PORTS);
			childrenFeatures.add(HenshinPackage.Literals.TRANSFORMATION_UNIT__PORT_MAPPINGS);
			childrenFeatures.add(HenshinPackage.Literals.COUNTED_UNIT__SUB_UNIT);
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
	 * This returns CountedUnit.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/CountedUnit"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((CountedUnit)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_CountedUnit_type") :
			getString("_UI_CountedUnit_type") + " " + label;
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

		switch (notification.getFeatureID(CountedUnit.class)) {
			case HenshinPackage.COUNTED_UNIT__NAME:
			case HenshinPackage.COUNTED_UNIT__ACTIVATED:
			case HenshinPackage.COUNTED_UNIT__COUNT:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case HenshinPackage.COUNTED_UNIT__PORTS:
			case HenshinPackage.COUNTED_UNIT__PORT_MAPPINGS:
			case HenshinPackage.COUNTED_UNIT__SUB_UNIT:
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
				(HenshinPackage.Literals.TRANSFORMATION_UNIT__PORTS,
				 HenshinFactory.eINSTANCE.createPortObject()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.TRANSFORMATION_UNIT__PORTS,
				 HenshinFactory.eINSTANCE.createPortParameter()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.TRANSFORMATION_UNIT__PORT_MAPPINGS,
				 HenshinFactory.eINSTANCE.createPortMapping()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.COUNTED_UNIT__SUB_UNIT,
				 HenshinFactory.eINSTANCE.createRule()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.COUNTED_UNIT__SUB_UNIT,
				 HenshinFactory.eINSTANCE.createIndependentUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.COUNTED_UNIT__SUB_UNIT,
				 HenshinFactory.eINSTANCE.createSequentialUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.COUNTED_UNIT__SUB_UNIT,
				 HenshinFactory.eINSTANCE.createConditionalUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.COUNTED_UNIT__SUB_UNIT,
				 HenshinFactory.eINSTANCE.createPriorityUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.COUNTED_UNIT__SUB_UNIT,
				 HenshinFactory.eINSTANCE.createAmalgamatedUnit()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.COUNTED_UNIT__SUB_UNIT,
				 HenshinFactory.eINSTANCE.createCountedUnit()));
	}

}
