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
import org.eclipse.emf.common.notify.NotificationWrapper;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.henshin.commands.NegligentRemoveCommand;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * This is the item provider for a {@link Parameter} object being referred to by
 * a {@link TransformationUnit}, in fact by its feature
 * {@link HenshinPackage#TRANSFORMATION_UNIT__PARAMETERS}. <br>
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * @author Stefan Jurack (sjurack)
 * 
 */
public class TrafoUnitParameterItemProvider extends TransientItemProvider {
	
	/**
	 * @param adapterFactory
	 * @param target
	 */
	public TrafoUnitParameterItemProvider(AdapterFactory adapterFactory, EObject target) {
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
			childrenFeatures.add(HenshinPackage.Literals.TRANSFORMATION_UNIT__PARAMETERS);
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
		return getString("_UI_TransformationUnit_parameters_feature");
	}// getText
	
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Parameter"));
	}	
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#collectNewChildDescriptors
	 * (java.util.Collection, java.lang.Object)
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		
		super.collectNewChildDescriptors(newChildDescriptors, object);
		
		newChildDescriptors.add(createChildParameter(
				HenshinPackage.Literals.TRANSFORMATION_UNIT__PARAMETERS,
				HenshinFactory.eINSTANCE.createMapping()));
		
	}// collectNewChildDescriptors
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse
	 * .emf.common.notify.Notification)
	 */
	@Override
	public void notifyChanged(Notification msg) {
		
		switch (msg.getFeatureID(Rule.class)) {
			case HenshinPackage.TRANSFORMATION_UNIT__PARAMETERS:
				fireNotifyChanged(new NotificationWrapper(this, msg));
				return;
		}
		super.notifyChanged(msg);
	}
	
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
				HenshinPackage.Literals.TRANSFORMATION_UNIT__PARAMETERS, collection).canExecute())
			return super.createDragAndDropCommand(domain, owner, location, operations, operation,
					collection);
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
		
		// Objects might have been removed as side effect of other remove
		// commands
		if (feature == HenshinPackage.Literals.TRANSFORMATION_UNIT__PARAMETERS)
			return new NegligentRemoveCommand(domain, owner, feature, collection);
		
		return super.createRemoveCommand(domain, owner, feature, collection);
	}
	
}// class
