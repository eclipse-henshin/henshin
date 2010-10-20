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
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.IWrapperItemProvider;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.emf.henshin.model.AmalgamationUnit} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @author Stefan Jurack (sjurack)
 * @generated
 */
public class AmalgamationUnitItemProvider extends TransformationUnitItemProvider implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider,
		IItemLabelProvider, IItemPropertySource {

	protected List children = null;

	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AmalgamationUnitItemProvider(AdapterFactory adapterFactory) {
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

			addKernelRulePropertyDescriptor(object);
			addMultiRulesPropertyDescriptor(object);
			addLhsMappingsPropertyDescriptor(object);
			addRhsMappingsPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Kernel Rule feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addKernelRulePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_AmalgamationUnit_kernelRule_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_AmalgamationUnit_kernelRule_feature", "_UI_AmalgamationUnit_type"),
				HenshinPackage.Literals.AMALGAMATION_UNIT__KERNEL_RULE, true, false, true, null,
				null, null));
	}

	/**
	 * This adds a property descriptor for the Multi Rules feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMultiRulesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_AmalgamationUnit_multiRules_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_AmalgamationUnit_multiRules_feature", "_UI_AmalgamationUnit_type"),
				HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES, true, false, true, null,
				null, null));
	}

	/**
	 * This adds a property descriptor for the Lhs Mappings feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addLhsMappingsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_AmalgamationUnit_lhsMappings_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_AmalgamationUnit_lhsMappings_feature", "_UI_AmalgamationUnit_type"),
				HenshinPackage.Literals.AMALGAMATION_UNIT__LHS_MAPPINGS, true, false, true, null,
				null, null));
	}

	/**
	 * This adds a property descriptor for the Rhs Mappings feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addRhsMappingsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_AmalgamationUnit_rhsMappings_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_AmalgamationUnit_rhsMappings_feature", "_UI_AmalgamationUnit_type"),
				HenshinPackage.Literals.AMALGAMATION_UNIT__RHS_MAPPINGS, true, false, true, null,
				null, null));
	}

	/**
	 * This returns AmalgamationUnit.gif. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/AmalgamationUnit"));
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((AmalgamationUnit) object).getName();
		return label == null || label.length() == 0 ? getString("_UI_AmalgamationUnit_type")
				: getString("_UI_AmalgamationUnit_type") + " " + label;
	}

	
	/**
	 * This handles model notifications by calling {@link #updateChildren} to
	 * update any cached children and by creating a viewer notification, which
	 * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @author Stefan Jurack (sjurack)
	 * @generated NOT
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		// switch (notification.getFeatureID(TransformationUnit.class)) {
		// case HenshinPackage.AMALGAMATION_UNIT__KERNEL_RULE:
		// case HenshinPackage.AMALGAMATION_UNIT__MULTI_RULES:
		// case HenshinPackage.AMALGAMATION_UNIT__LHS_MAPPINGS:
		// case HenshinPackage.AMALGAMATION_UNIT__RHS_MAPPINGS:
		// fireNotifyChanged(new ViewerNotification(notification,
		// notification.getNotifier(), true, false));
		// return;
		// }
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing the children that can be created under this object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

	/**
	 * 
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#getChildren(java.lang
	 *      .Object)
	 * 
	 */
	@Override
	public Collection<?> getChildren(Object object) {
		AmalgamationUnit au = (AmalgamationUnit) object;
		if (children == null) {
			children = (List) super.getChildren(object);

			// Note, contents of methods getKernelRule, getMultiRules etc.
			// reflect this order
			children.add(0, new KernelRuleItemProvider(adapterFactory, au));
			children.add(1, new MultiRuleItemProvider(adapterFactory, au));
			children.add(2, new LhsMappingItemProvider(adapterFactory, au));
			children.add(3, new RhsMappingItemProvider(adapterFactory, au));
		}// if
		else {
			List l = (List) super.getChildren(object);

			// reuse item providers in the 'children' list
			l.add(0, children.get(0));
			l.add(1, children.get(1));
			l.add(2, children.get(2));
			l.add(3, children.get(3));
			children = l;
		}
		
		if (au.getMultiRules().size() > 0)
			children.removeAll(au.getMultiRules());
		if (au.getKernelRule() != null)
			children.remove(au.getKernelRule());
		if (au.getLhsMappings().size() > 0)
			children.removeAll(au.getLhsMappings());
		if (au.getRhsMappings().size() > 0)
			children.removeAll(au.getRhsMappings());		
		return children;

	}// getChildren

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.henshin.provider.TransformationUnitItemProvider#
	 * getChildrenFeatures(java.lang.Object)
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(HenshinPackage.Literals.AMALGAMATION_UNIT__KERNEL_RULE);
			childrenFeatures.add(HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES);
//			childrenFeatures.add(HenshinPackage.Literals.AMALGAMATION_UNIT__LHS_MAPPINGS);
//			childrenFeatures.add(HenshinPackage.Literals.AMALGAMATION_UNIT__RHS_MAPPINGS);
		}
		return childrenFeatures;
	}

	/**
	 * Finds and returns the item provider of a child by its feature literal.
	 * (Helper method)
	 * 
	 * @param feature
	 * @return
	 */
	public Object findChildProvider(EStructuralFeature feature) {
		if (feature == HenshinPackage.Literals.AMALGAMATION_UNIT__KERNEL_RULE)
			return getKernelRuleItemProvider();
		if (feature == HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES)
			return getMuliRulesItemProvider();
		if (feature == HenshinPackage.Literals.AMALGAMATION_UNIT__LHS_MAPPINGS)
			return getLhsMappingsItemProvider();
		if (feature == HenshinPackage.Literals.AMALGAMATION_UNIT__RHS_MAPPINGS)
			return getRhsMappingsItemProvider();
		return null;
	}// resolveChildProvider

	/**
	 * @return
	 */
	public Object getKernelRuleItemProvider() {
		return children.get(0);
	}// getKernelRuleItemProvider

	/**
	 * @return
	 */
	public Object getMuliRulesItemProvider() {
		return children.get(1);
	}// getMuliRulesItemProvider

	/**
	 * @return
	 */
	public Object getLhsMappingsItemProvider() {
		return children.get(2);
	}// getLhsMappingsItemProvider

	/**
	 * @return
	 */
	public Object getRhsMappingsItemProvider() {
		return children.get(3);
	}// getRhsMappingsItemProvider

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#createCommand(java.
	 * lang.Object, org.eclipse.emf.edit.domain.EditingDomain, java.lang.Class,
	 * org.eclipse.emf.edit.command.CommandParameter)
	 */
	@Override
	public Command createCommand(Object object, EditingDomain domain,
			Class<? extends Command> commandClass, CommandParameter commandParameter) {

		// CompoundCommand command = new CompoundCommand(
		// CompoundCommand.MERGE_COMMAND_ALL);
		// if (commandParameter.collection!=null) {
		// Iterator<?> iterator = commandParameter.collection.iterator();
		// Object o;
		// while (iterator.hasNext()) {
		// o = iterator.next();
		// CommandParameter cp = unwrapItemAndCreateCommandParameter(
		// commandParameter, o);
		// Command c = null;
		// if (cp.feature != null && (cp.feature instanceof EStructuralFeature)
		// && !((EStructuralFeature) cp.feature).isMany()) {
		// commandClass = SetCommand.class;
		// }
		// c = super.createCommand(object, domain,commandClass, cp);
		// command.append(c);
		// }// while
		// } else {
		return super.createCommand(object, domain, commandClass, commandParameter);
		// }
		//
		// return command;
		// TODO Auto-generated method stub
		// return super.createCommand(object, domain, commandClass,
		// commandParameter);
	}

	/**
	 * 
	 * If the given collectionItem implements {@link IWrapperItemProvider}, it
	 * is unwrapped by obtaining a value from
	 * {@link IWrapperItemProvider#getValue getValue}. The unwrapping continues
	 * until a non-wrapper value is returned. This iterative unwrapping is
	 * required because values may be repeatedly wrapped, as children of a
	 * delegating wrapper. Furthermore, the feature contained in the wrapper is
	 * extracted and used to create a CommandParameter.<br>
	 * This is a workaround, see <a
	 * href="http://www.eclipse.org/forums/index.php?t=msg&th=172187&start=0"
	 * >this discussion</a>.
	 * 
	 * @param owner
	 * @param feature
	 * @param collectionItem
	 * @return
	 */
	private CommandParameter unwrapItemAndCreateCommandParameter(CommandParameter oldCP,
			Object collectionItem) {
		CommandParameter cp = new CommandParameter(oldCP.owner, oldCP.feature, oldCP.value,
				oldCP.index);
		// iter
		while (collectionItem instanceof IWrapperItemProvider) {
			IWrapperItemProvider wrapper = (IWrapperItemProvider) collectionItem;
			collectionItem = wrapper.getValue();
			if ((cp.feature == null) && (wrapper.getFeature() != null))
				cp.feature = wrapper.getFeature();
		}// while
		cp.collection = Collections.singletonList(collectionItem);
		return cp;
	}// unwrapItemAndCreateCommandParameter

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#createRemoveCommand
	 * (org.eclipse.emf.edit.domain.EditingDomain,
	 * org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature,
	 * java.util.Collection)
	 */
	@Override
	protected Command createRemoveCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Collection<?> collection) {

		return createWrappedCommand(super.createRemoveCommand(domain, owner, feature, collection),
				owner, feature);
	}// createRemoveCommand

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#createAddCommand(org
	 * .eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection, int)
	 */
	@Override
	protected Command createAddCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Collection<?> collection, int index) {
		return createWrappedCommand(
				super.createAddCommand(domain, owner, feature, collection, index), owner, feature);
	}// createAddCommand

	/**
	 * @param command
	 * @param owner
	 * @param feature
	 * @return
	 */
	protected Command createWrappedCommand(Command command, final EObject owner,
			final EStructuralFeature feature) {

		if (feature == HenshinPackage.Literals.AMALGAMATION_UNIT__KERNEL_RULE
				|| feature == HenshinPackage.Literals.AMALGAMATION_UNIT__MULTI_RULES
				|| feature == HenshinPackage.Literals.AMALGAMATION_UNIT__LHS_MAPPINGS
				|| feature == HenshinPackage.Literals.AMALGAMATION_UNIT__RHS_MAPPINGS) {
			return new CommandWrapper(command) {

				public Collection getAffectedObjects() {
					Collection affected = super.getAffectedObjects();
					if (affected.contains(owner)) {
						affected = Collections.singleton(findChildProvider(feature));
					}// if
					return affected;
				}// getAffectedObjects
			};
		}// if
		return command;
	}// createWrappedCommand

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		if (children != null) {
			((IDisposable) children.get(0)).dispose();
			((IDisposable) children.get(1)).dispose();
			((IDisposable) children.get(2)).dispose();
			((IDisposable) children.get(3)).dispose();
		}// if
	}// dispose

	// /**
	// * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	// * describing the children that can be created under this object. <!--
	// * begin-user-doc --> <!-- end-user-doc -->
	// *
	// * @generated NOT
	// */
	// @Override
	// protected void collectNewChildDescriptors(
	// Collection<Object> newChildDescriptors, Object object) {
	// super.collectNewChildDescriptors(newChildDescriptors, object);
	//
	// newChildDescriptors.add(createChildParameter(
	// HenshinPackage.Literals.AMALGAMATION_UNIT__RHS_MAPPINGS,
	// HenshinFactory.eINSTANCE.createMapping()));
	// }

}
