/**
 * 
 */
package org.eclipse.emf.henshin.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationWrapper;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;

/**
 * @author sjtuner
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
		
		switch (notification.getFeatureID(AmalgamationUnit.class)) {
			case HenshinPackage.AMALGAMATION_UNIT__KERNEL_RULE:
				fireNotifyChanged(new NotificationWrapper(this, notification));
				// fireNotifyChanged(new ViewerNotification(notification,
				// notification.getNotifier(), true, true));
				return;
		}
		super.notifyChanged(notification);
	}
	
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
		return super.createWrapper(object, feature, value, index);
	}
	
	/*
	 * (non-Javadoc)
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
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#createDragAndDropCommand
	 * (org.eclipse.emf.edit.domain.EditingDomain, java.lang.Object, float, int,
	 * int, java.util.Collection)
	 */
	@Override
	protected Command createDragAndDropCommand(EditingDomain domain, Object owner, float location,
			int operations, int operation, Collection<?> collection) {
		
		Object o = ((List) collection).get(0);
		if (new SetCommand(domain, (EObject) owner,
				HenshinPackage.Literals.AMALGAMATION_UNIT__KERNEL_RULE, o).canExecute()) {
			return super.createDragAndDropCommand(domain, owner, location, operations, operation,
					collection);
		}// if
		return UnexecutableCommand.INSTANCE;
		
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
