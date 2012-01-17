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
package org.eclipse.emf.henshin.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemColorProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.henshin.commands.AddRuleCommand;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.emf.henshin.model.TransformationSystem} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class TransformationSystemItemProvider extends DescribedElementItemProvider implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider,
		IItemLabelProvider, IItemPropertySource, IItemColorProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TransformationSystemItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addNamePropertyDescriptor(object);
			addImportsPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}
	
	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
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
	 * This adds a property descriptor for the Imports feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addImportsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TransformationSystem_imports_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TransformationSystem_imports_feature", "_UI_TransformationSystem_type"),
				 HenshinPackage.Literals.TRANSFORMATION_SYSTEM__IMPORTS,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}
	
	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(HenshinPackage.Literals.TRANSFORMATION_SYSTEM__RULES);
			childrenFeatures.add(HenshinPackage.Literals.TRANSFORMATION_SYSTEM__INSTANCES);
			childrenFeatures.add(HenshinPackage.Literals.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS);
		}
		return childrenFeatures;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}
	
	/**
	 * This returns TransformationSystem.gif.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/TransformationSystem"));
	}
	
	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((TransformationSystem)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_TransformationSystem_type") :
			getString("_UI_TransformationSystem_type") + " " + label;
	}
	
	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(TransformationSystem.class)) {
			case HenshinPackage.TRANSFORMATION_SYSTEM__NAME:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case HenshinPackage.TRANSFORMATION_SYSTEM__RULES:
			case HenshinPackage.TRANSFORMATION_SYSTEM__INSTANCES:
			case HenshinPackage.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}
	
	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing the children that can be created under this object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
		
		newChildDescriptors.add(createChildParameter(
				HenshinPackage.Literals.TRANSFORMATION_SYSTEM__RULES,
				HenshinFactory.eINSTANCE.createRule()));
		
		newChildDescriptors.add(createChildParameter(
				HenshinPackage.Literals.TRANSFORMATION_SYSTEM__INSTANCES,
				HenshinFactory.eINSTANCE.createGraph()));
		
		// newChildDescriptors.add
		// (createChildParameter
		// (HenshinPackage.Literals.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS,
		// HenshinFactory.eINSTANCE.createRule()));
		
		newChildDescriptors.add(createChildParameter(
				HenshinPackage.Literals.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS,
				HenshinFactory.eINSTANCE.createIndependentUnit()));
		
		newChildDescriptors.add(createChildParameter(
				HenshinPackage.Literals.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS,
				HenshinFactory.eINSTANCE.createSequentialUnit()));
		
		newChildDescriptors.add(createChildParameter(
				HenshinPackage.Literals.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS,
				HenshinFactory.eINSTANCE.createConditionalUnit()));
		
		newChildDescriptors.add(createChildParameter(
				HenshinPackage.Literals.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS,
				HenshinFactory.eINSTANCE.createPriorityUnit()));
		
		newChildDescriptors.add(createChildParameter(
				HenshinPackage.Literals.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS,
				HenshinFactory.eINSTANCE.createAmalgamationUnit()));
		
		newChildDescriptors.add(createChildParameter(
				HenshinPackage.Literals.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS,
				HenshinFactory.eINSTANCE.createCountedUnit()));
	}
	
	/**
	 * This returns the label text for
	 * {@link org.eclipse.emf.edit.command.CreateChildCommand}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child,
			Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == HenshinPackage.Literals.TRANSFORMATION_SYSTEM__RULES ||
			childFeature == HenshinPackage.Literals.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}
	
	/**
	 * This creates a primitive {@link org.eclipse.emf.edit.command.AddCommand}.
	 * It provides a custom command for creating rules.
	 * 
	 * @generated NOT
	 */
	@SuppressWarnings("unchecked")
	protected Command createAddCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Collection<?> collection, int index) {
		if (feature == HenshinPackage.eINSTANCE.getTransformationSystem_Rules()) {
			return new AddRuleCommand(domain, (TransformationSystem) owner,
					(Collection<Rule>) collection, index);
		} else {
			return super.createAddCommand(domain, owner, feature, collection, index);
		}
	}
	
}
