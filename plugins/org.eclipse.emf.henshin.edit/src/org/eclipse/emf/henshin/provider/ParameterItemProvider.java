/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemColorProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterKind;
import org.eclipse.emf.henshin.model.Unit;

/**
 * This is the item provider adapter for a {@link org.eclipse.emf.henshin.model.Parameter} object.
 * <!-- begin-user-doc --> 
 * <!-- end-user-doc -->
 * @generated
 */
public class ParameterItemProvider extends NamedElementItemProvider {
	
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterItemProvider(AdapterFactory adapterFactory) {
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

			addTypePropertyDescriptor(object);
			addKindPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}
	
	/**
	 * This adds a property descriptor for the Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Parameter_type_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Parameter_type_feature", "_UI_Parameter_type"),
				 HenshinPackage.Literals.PARAMETER__TYPE,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Kind feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addKindPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Parameter_kind_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Parameter_kind_feature", "_UI_Parameter_type"),
				 HenshinPackage.Literals.PARAMETER__KIND,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This returns an icon according to the {@link ParameterKind} of <em>object</em>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Object getImage(Object object) {
		ParameterKind parameterKind = ((Parameter) object).getKind();
		String iconPath = "";
		
		if(parameterKind == ParameterKind.IN) {
			iconPath = "full/obj16/ParameterIn";
		} else if(parameterKind == ParameterKind.OUT) {
			iconPath = "full/obj16/ParameterOut";
		} else if(parameterKind == ParameterKind.INOUT) {
			iconPath = "full/obj16/ParameterInOut";
		} else if(parameterKind == ParameterKind.VAR) {
			iconPath = "full/obj16/ParameterVar";
		} else {
			iconPath = "full/obj16/Parameter";
		}
		
		return overlayImage(object, getResourceLocator().getImage(iconPath));
	}
	
	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		String name = ((Parameter) object).getName();
		EClassifier type = ((Parameter) object).getType();
		String typename = (type!=null) ? ":" + type.getName() : "";
		return name == null || name.length() == 0 ?
			getString("_UI_Parameter_type") :
			getString("_UI_Parameter_type") + " " + name + typename;
	}
	
	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);
		fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
		
		// Notify the unit (pretend that the unit name changed so that the displayed text gets updated):
		Unit unit = ((Parameter) notification.getNotifier()).getUnit();
		if (unit!=null) {
			ItemProviderAdapter adapter = 
					(ItemProviderAdapter) adapterFactory.adapt(unit, null);
			Notification notif = new ENotificationImpl((InternalEObject) unit, 
					Notification.SET, HenshinPackage.eINSTANCE.getNamedElement_Name(), unit.getName(), unit.getName(), false);
			adapter.fireNotifyChanged(new ViewerNotification(notif, unit, false, true));
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
	}
	
}
