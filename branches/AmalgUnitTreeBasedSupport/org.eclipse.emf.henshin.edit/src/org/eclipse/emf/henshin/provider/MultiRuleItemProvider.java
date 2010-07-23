/**
 * 
 */
package org.eclipse.emf.henshin.provider;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationWrapper;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CopyCommand.Helper;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;

/**
 * @author sjtuner
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
	 * 
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#getChildrenFeatures
	 * (java.lang.Object)
	 */
	@Override
	protected Collection<? extends EStructuralFeature> getChildrenFeatures(
			Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures
					.add(HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES);
		}// if
		return childrenFeatures;
	}// getChildrenFeatures

	/*
	 * (non-Javadoc)
	 * 
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
		EStructuralFeature sf = super.getChildFeature(target, child);
		return sf;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#collectNewChildDescriptors
	 * (java.util.Collection, java.lang.Object)
	 */
	@Override
	protected void collectNewChildDescriptors(
			Collection<Object> newChildDescriptors, Object object) {

		super.collectNewChildDescriptors(newChildDescriptors, object);

		// newChildDescriptors.add(createChildParameter(
		// HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES,
		// HenshinFactory.eINSTANCE.createRule()));

	}// collectNewChildDescriptors

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse
	 * .emf.common.notify.Notification)
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);
		
		switch (notification.getFeatureID(AmalgamationUnit.class)) {
		case HenshinPackage.AMALGAMATION_UNIT__MULTI_RULES:
			fireNotifyChanged(new NotificationWrapper(this, notification));
			return;
		}
		super.notifyChanged(notification);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#createWrapper(org.eclipse
	 * .emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature,
	 * java.lang.Object, int)
	 */
	@Override
	protected Object createWrapper(EObject object, EStructuralFeature feature,
			Object value, int index) {
		return super.createWrapper(object, feature, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#isWrappingNeeded(java
	 * .lang.Object)
	 */
	@Override
	protected boolean isWrappingNeeded(Object object) {
		return Boolean.TRUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * 
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#createDragAndDropCommand
	 * (org.eclipse.emf.edit.domain.EditingDomain, java.lang.Object, float, int,
	 * int, java.util.Collection)
	 */
	@Override
	protected Command createDragAndDropCommand(EditingDomain domain,
			Object owner, float location, int operations, int operation,
			Collection<?> collection) {

		if (new AddCommand(domain, (EObject) owner,
				HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES,
				collection).canExecute()) {
			return super.createDragAndDropCommand(domain, owner, location,
					operations, operation, collection);
		}// if
		return UnexecutableCommand.INSTANCE;

	}// createDragAndDropCommand

	/* (non-Javadoc)
	 * @see org.eclipse.emf.henshin.provider.TransientItemProvider#createRemoveCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection)
	 */
	@Override
	protected Command createRemoveCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Collection<?> collection) {
		// TODO Auto-generated method stub
		return super.createRemoveCommand(domain, owner, HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, collection);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.henshin.provider.TransientItemProvider#createAddCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection, int)
	 */
	@Override
	protected Command createAddCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Collection<?> collection, int index) {
		// TODO Auto-generated method stub
		return super.createAddCommand(domain, owner, HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, collection, index);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createSetCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object, int)
	 */
	@Override
	protected Command createSetCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Object value, int index) {
		// TODO Auto-generated method stub
		return super.createSetCommand(domain, owner, HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, value, index);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createSetCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	@Override
	protected Command createSetCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Object value) {
		// TODO Auto-generated method stub
		return super.createSetCommand(domain, owner, HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, value);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createCopyCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.edit.command.CopyCommand.Helper)
	 */
	@Override
	protected Command createCopyCommand(EditingDomain domain, EObject owner, Helper helper) {
		// TODO Auto-generated method stub
		return super.createCopyCommand(domain, owner, helper);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createCreateCopyCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.edit.command.CopyCommand.Helper)
	 */
	@Override
	protected Command createCreateCopyCommand(EditingDomain domain, EObject owner, Helper helper) {
		// TODO Auto-generated method stub
		return super.createCreateCopyCommand(domain, owner, helper);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createInitializeCopyCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.edit.command.CopyCommand.Helper)
	 */
	@Override
	protected Command createInitializeCopyCommand(EditingDomain domain, EObject owner, Helper helper) {
		// TODO Auto-generated method stub
		return super.createInitializeCopyCommand(domain, owner, helper);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createRemoveCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EReference, java.util.Collection)
	 */
	@Override
	protected Command createRemoveCommand(EditingDomain domain, EObject owner, EReference feature,
			Collection<?> collection) {
		// TODO Auto-generated method stub
		return super.createRemoveCommand(domain, owner, HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, collection);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createReplaceCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, org.eclipse.emf.ecore.EObject, java.util.Collection)
	 */
	@Override
	protected Command createReplaceCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, EObject value, Collection<?> collection) {
		// TODO Auto-generated method stub
		return super.createReplaceCommand(domain, owner, HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, value, collection);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createReplaceCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EReference, org.eclipse.emf.ecore.EObject, java.util.Collection)
	 */
	@Override
	protected Command createReplaceCommand(EditingDomain domain, EObject owner, EReference feature,
			EObject value, Collection<?> collection) {
		// TODO Auto-generated method stub
		return super.createReplaceCommand(domain, owner, HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, value, collection);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createAddCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EReference, java.util.Collection, int)
	 */
	@Override
	protected Command createAddCommand(EditingDomain domain, EObject owner, EReference feature,
			Collection<?> collection, int index) {
		// TODO Auto-generated method stub
		return super.createAddCommand(domain, owner, HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, collection, index);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createMoveCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object, int)
	 */
	@Override
	protected Command createMoveCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Object value, int index) {
		// TODO Auto-generated method stub
		return super.createMoveCommand(domain, owner, HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, value, index);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createMoveCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EReference, org.eclipse.emf.ecore.EObject, int)
	 */
	@Override
	protected Command createMoveCommand(EditingDomain domain, EObject owner, EReference feature,
			EObject value, int index) {
		// TODO Auto-generated method stub
		return super.createMoveCommand(domain, owner, HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, value, index);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createCreateChildCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object, int, java.util.Collection)
	 */
	@Override
	protected Command createCreateChildCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Object value, int index, Collection<?> collection) {
		// TODO Auto-generated method stub
		return super.createCreateChildCommand(domain, owner, HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, value, index, collection);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createCreateChildCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EReference, org.eclipse.emf.ecore.EObject, int, java.util.Collection)
	 */
	@Override
	protected Command createCreateChildCommand(EditingDomain domain, EObject owner,
			EReference feature, EObject value, int index, Collection<?> collection) {
		// TODO Auto-generated method stub
		return super.createCreateChildCommand(domain, owner, HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, value, index, collection);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.henshin.provider.TransientItemProvider#createCommand(java.lang.Object, org.eclipse.emf.edit.domain.EditingDomain, java.lang.Class, org.eclipse.emf.edit.command.CommandParameter)
	 */
	@Override
	public Command createCommand(Object object, EditingDomain domain, Class commandClass,
			CommandParameter commandParameter) {
		// TODO Auto-generated method stub
		return super.createCommand(object, domain, commandClass, commandParameter);
	}

	
	
	
	
	
	
}// class
