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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.SetCommand;
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
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.provider.trans.ElseItemProvider;
import org.eclipse.emf.henshin.provider.trans.IfItemProvider;
import org.eclipse.emf.henshin.provider.trans.ThenItemProvider;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.emf.henshin.model.ConditionalUnit} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class ConditionalUnitItemProvider extends TransformationUnitItemProvider implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider,
		IItemLabelProvider, IItemPropertySource {
	
	@SuppressWarnings("rawtypes")
	protected List children = null;
	
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ConditionalUnitItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);
			
			addIfPropertyDescriptor(object);
			addThenPropertyDescriptor(object);
			addElsePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}
	
	/**
	 * This adds a property descriptor for the If feature. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 */
	protected void addIfPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ConditionalUnit_if_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_ConditionalUnit_if_feature",
						"_UI_ConditionalUnit_type"), HenshinPackage.Literals.CONDITIONAL_UNIT__IF,
				true, false, true, null, null, null));
	}// addIfPropertyDescriptor
	
	/**
	 * This adds a property descriptor for the Then feature. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 */
	protected void addThenPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(
						((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(),
						getString("_UI_ConditionalUnit_then_feature"),
						getString("_UI_PropertyDescriptor_description",
								"_UI_ConditionalUnit_then_feature", "_UI_ConditionalUnit_type"),
						HenshinPackage.Literals.CONDITIONAL_UNIT__THEN, true, false, true, null,
						null, null));
	}// addThenPropertyDescriptor
	
	/**
	 * This adds a property descriptor for the Else feature. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 */
	protected void addElsePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(
						((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(),
						getString("_UI_ConditionalUnit_else_feature"),
						getString("_UI_PropertyDescriptor_description",
								"_UI_ConditionalUnit_else_feature", "_UI_ConditionalUnit_type"),
						HenshinPackage.Literals.CONDITIONAL_UNIT__ELSE, true, false, true, null,
						null, null));
	}// addElsePropertyDescriptor
	
	/**
	 * This specifies how to implement {@link #getChildren} and is used to
	 * deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand},
	 * {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in
	 * {@link #createCommand}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			// childrenFeatures.add(HenshinPackage.Literals.CONDITIONAL_UNIT__IF);
			// childrenFeatures.add(HenshinPackage.Literals.CONDITIONAL_UNIT__THEN);
			// childrenFeatures.add(HenshinPackage.Literals.CONDITIONAL_UNIT__ELSE);
		}
		return childrenFeatures;
	}
	
	/**
	 * This returns ConditionalUnit.gif. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ConditionalUnit"));
	}
	
	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((ConditionalUnit) object).getName();
		return label == null || label.length() == 0 ? getString("_UI_ConditionalUnit_type")
				: getString("_UI_ConditionalUnit_type") + " " + label;
	}
	
	/**
	 * This handles model notifications by calling {@link #updateChildren} to
	 * update any cached children and by creating a viewer notification, which
	 * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);
		
		switch (notification.getFeatureID(ConditionalUnit.class)) {
			case HenshinPackage.CONDITIONAL_UNIT__IF:
			case HenshinPackage.CONDITIONAL_UNIT__THEN:
			case HenshinPackage.CONDITIONAL_UNIT__ELSE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(),
						true, false));
				return;
		}
		super.notifyChanged(notification);
	}
	
	/**
	 * 
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#getChildren(java.lang
	 *      .Object)
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<?> getChildren(Object object) {
		if (children == null) {
			ConditionalUnit cu = (ConditionalUnit) object;
			children = (List<?>) super.getChildren(object);
			
			// Note, contents of methods getKernelRule, getMultiRules etc.
			// reflect this order
			children.add(new IfItemProvider(adapterFactory, cu));
			children.add(new ThenItemProvider(adapterFactory, cu));
			children.add(new ElseItemProvider(adapterFactory, cu));
		}// if
		else {
			@SuppressWarnings("rawtypes")
			List l = (List<?>) super.getChildren(object);
			
			// reuse item providers in the 'children' list
			int size = children.size();
			l.add(children.get(size - 3));
			l.add(children.get(size - 2));
			l.add(children.get(size - 1));
			children = l;
		}
		
		return children;
	}// getChildren
	
	/**
	 * Finds and returns the item provider of a child by its feature literal.
	 * (Helper method)
	 * 
	 * @param feature
	 * @return
	 */
	public Object findChildProvider(EStructuralFeature feature) {
		if (feature == HenshinPackage.Literals.CONDITIONAL_UNIT__IF) return getIfItemProvider();
		if (feature == HenshinPackage.Literals.CONDITIONAL_UNIT__THEN)
			return getThenItemProvider();
		if (feature == HenshinPackage.Literals.CONDITIONAL_UNIT__ELSE)
			return getElseItemProvider();
		return null;
	}// resolveChildProvider
	
	/**
	 * @return
	 */
	public Object getIfItemProvider() {
		return children.get(0);
	}// getIfItemProvider
	
	/**
	 * @return
	 */
	public Object getThenItemProvider() {
		return children.get(1);
	}// getThenItemProvider
	
	/**
	 * @return
	 */
	public Object getElseItemProvider() {
		return children.get(2);
	}// getElseItemProvider
	
	/**
	 * @param command
	 * @param owner
	 * @param feature
	 * @return
	 */
	protected Command createWrappedCommand(Command command, final EObject owner,
			final EStructuralFeature feature) {
		
		if (feature == HenshinPackage.Literals.CONDITIONAL_UNIT__IF
				|| feature == HenshinPackage.Literals.CONDITIONAL_UNIT__THEN
				|| feature == HenshinPackage.Literals.CONDITIONAL_UNIT__ELSE) {
			return new CommandWrapper(command) {
				
				public Collection<?> getAffectedObjects() {
					Collection<?> affected = super.getAffectedObjects();
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
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#createCommand(java.
	 * lang.Object, org.eclipse.emf.edit.domain.EditingDomain, java.lang.Class,
	 * org.eclipse.emf.edit.command.CommandParameter)
	 */
	@Override
	public Command createCommand(Object object, EditingDomain domain,
			Class<? extends Command> commandClass, CommandParameter commandParameter) {
		/*
		 * In case of wrappers and transient items, by default the commands are
		 * not always created adequately, i.e. the feature information of the
		 * transient items contained in the wrappers are often thrown away
		 * instead of taken into account. This is (not generically) fixed by the
		 * following code: The commands and especially command parameters are
		 * build up from scratch taking given information about owner, value and
		 * features into account.
		 */

		// The behavior has to be fixed only if the features refer elements
		if (commandParameter.collection != null && !commandParameter.collection.isEmpty()) {
			Object realObject = unwrap(commandParameter.collection.iterator().next());
			if (realObject != object) {
				CompoundCommand command = new CompoundCommand(CompoundCommand.MERGE_COMMAND_ALL);
				Iterator<?> iterator = commandParameter.collection.iterator();
				Object o;
				while (iterator.hasNext()) {
					o = iterator.next();
					CommandParameter cp = unwrapItemAndCreateCommandParameter(commandParameter, o);
					Command c = null;
					if (cp.feature != null && (cp.feature instanceof EStructuralFeature)
							&& !((EStructuralFeature) cp.feature).isMany()) {
						commandClass = SetCommand.class;
					}
					c = super.createCommand(object, domain, commandClass, cp);
					command.append(c);
					command.setLabel(c.getLabel());
				}// while
				return command;
			} // if
		}// if
		return super.createCommand(object, domain, commandClass, commandParameter);
	}// createCommand
	
	/**
	 * 
	 * If the given <code>collectionItem</code> implements
	 * {@link IWrapperItemProvider}, it is unwrapped by obtaining a value from
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
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		if (children != null) {
			((IDisposable) children.get(0)).dispose();
			((IDisposable) children.get(1)).dispose();
			((IDisposable) children.get(2)).dispose();
		}// if
	}// dispose
	
	// /**
	// * This returns the label text for
	// * {@link org.eclipse.emf.edit.command.CreateChildCommand}. <!--
	// * begin-user-doc --> <!-- end-user-doc -->
	// *
	// * @generated
	// */
	// @Override
	// public String getCreateChildText(Object owner, Object feature, Object
	// child,
	// Collection<?> selection) {
	// Object childFeature = feature;
	// Object childObject = child;
	//
	// boolean qualify = childFeature ==
	// HenshinPackage.Literals.CONDITIONAL_UNIT__IF
	// || childFeature == HenshinPackage.Literals.CONDITIONAL_UNIT__THEN
	// || childFeature == HenshinPackage.Literals.CONDITIONAL_UNIT__ELSE;
	//
	// if (qualify) {
	// return getString("_UI_CreateChild_text2", new Object[] {
	// getTypeText(childObject),
	// getFeatureText(childFeature), getTypeText(owner) });
	// }
	// return super.getCreateChildText(owner, feature, child, selection);
	// }
	
}
