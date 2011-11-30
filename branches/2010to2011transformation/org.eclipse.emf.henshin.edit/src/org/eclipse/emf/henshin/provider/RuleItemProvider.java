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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.henshin.commands.GraphComplexUnsetCommand;
import org.eclipse.emf.henshin.commands.NegligentRemoveCommand;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.provider.trans.RuleMappingItemProvider;

/**
 * This is the item provider adapter for a {@link org.eclipse.emf.henshin.model.Rule} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class RuleItemProvider extends TransformationUnitItemProvider implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider,
		IItemLabelProvider, IItemPropertySource {
	
	/**
	 * Number of mappings which are shown in an unfold way. Any number above the
	 * given leads to a folding of them.
	 */
	public static final int MAX_UNFOLD_MAPPINGS = 5;
	
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public RuleItemProvider(AdapterFactory adapterFactory) {
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

			addCheckDanglingPropertyDescriptor(object);
			addInjectiveMatchingPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}
	
	/**
	 * This adds a property descriptor for the Check Dangling feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCheckDanglingPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Rule_checkDangling_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Rule_checkDangling_feature", "_UI_Rule_type"),
				 HenshinPackage.Literals.RULE__CHECK_DANGLING,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Injective Matching feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addInjectiveMatchingPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Rule_injectiveMatching_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Rule_injectiveMatching_feature", "_UI_Rule_type"),
				 HenshinPackage.Literals.RULE__INJECTIVE_MATCHING,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
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
			childrenFeatures.add(HenshinPackage.Literals.RULE__LHS);
			childrenFeatures.add(HenshinPackage.Literals.RULE__RHS);
			childrenFeatures.add(HenshinPackage.Literals.RULE__ATTRIBUTE_CONDITIONS);
			childrenFeatures.add(HenshinPackage.Literals.RULE__MAPPINGS);
			childrenFeatures.add(HenshinPackage.Literals.RULE__MULTI_RULES);
			childrenFeatures.add(HenshinPackage.Literals.RULE__MULTI_MAPPINGS);
		}
		return childrenFeatures;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#getChildren(java.lang
	 * .Object)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<?> getChildren(Object object) {
		Collection childrenList = super.getChildren(object);
		Rule rule = (Rule) object;
		if (rule.getMappings().size() > MAX_UNFOLD_MAPPINGS) {
			childrenList.removeAll(rule.getMappings());
			/*if (!filteringEnabled
					|| !filterProvider.isFiltered(HenshinPackage.eINSTANCE.getMapping()))
				childrenList.add(new RuleMappingItemProvider(adapterFactory, rule));			
			*/
			childrenList.add(new RuleMappingItemProvider(adapterFactory, rule));
		}
		return childrenList;
	}// getChildren
	
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
	 * This returns Rule.gif.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Rule"));
	}
	
	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((Rule)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Rule_type") :
			getString("_UI_Rule_type") + " " + label;
	}
	
	/**
	 * This handles model notifications by calling {@link #updateChildren} to
	 * update any cached children and by creating a viewer notification, which
	 * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);
		
		switch (notification.getFeatureID(Rule.class)) {
			case HenshinPackage.RULE__LHS:
			case HenshinPackage.RULE__RHS:
			case HenshinPackage.RULE__ATTRIBUTE_CONDITIONS:
			case HenshinPackage.RULE__MAPPINGS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(),
						true, false));
				notifyMappedNodes(notification);
				return;
		}
		super.notifyChanged(notification);
	}
	
	
	/**
	 * If a mapping is added or removed this directly affect the visualization
	 * of associated nodes in terms of preserve, create and delete icons.
	 * Consequently, related nodes are notified to be refreshed visually.
	 * 
	 * @param notification
	 */
	@SuppressWarnings("unchecked")
	private void notifyMappedNodes(Notification notification) {
		List<Mapping> mappings = new ArrayList<Mapping>();
		switch (notification.getEventType()) {
			case Notification.REMOVE:
				mappings.add((Mapping) notification.getOldValue());
				break;
			case Notification.ADD:
				mappings.add((Mapping) notification.getNewValue());
				break;
			case Notification.ADD_MANY:
				mappings.addAll((Collection<Mapping>) notification.getNewValue());
				break;
			case Notification.REMOVE_MANY:
				mappings.addAll((Collection<Mapping>) notification.getOldValue());
				break;
		}// switch
		
		if (!mappings.isEmpty()) {
			for (Mapping mapping : mappings) {
				if (mapping.getImage() != null)
					notifyNodeForRefresh(notification, mapping.getImage());
				if (mapping.getOrigin() != null)
					notifyNodeForRefresh(notification, mapping.getOrigin());
			}// for
		}// if
	}// notifyMappedNodes
	
	/**
	 * Notifies the given node to refresh its label (only). This affects the
	 * icon in particular, which shows if the node is created, deleted or
	 * preserve.
	 * 
	 * @param notification
	 * @param node
	 */
	private void notifyNodeForRefresh(Notification notification, Node node) {
		if (node != null) {
			ItemProviderAdapter adapter = (ItemProviderAdapter) this.adapterFactory.adapt(node,
					Node.class);
			Notification notif = new ViewerNotification(notification, node, false, true);
			adapter.fireNotifyChanged(notif);
		}// if
	}// notifyNodeForRefresh
	
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

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.RULE__LHS,
				 HenshinFactory.eINSTANCE.createGraph()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.RULE__RHS,
				 HenshinFactory.eINSTANCE.createGraph()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.RULE__ATTRIBUTE_CONDITIONS,
				 HenshinFactory.eINSTANCE.createAttributeCondition()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.RULE__MAPPINGS,
				 HenshinFactory.eINSTANCE.createMapping()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.RULE__MULTI_RULES,
				 HenshinFactory.eINSTANCE.createRule()));

		newChildDescriptors.add
			(createChildParameter
				(HenshinPackage.Literals.RULE__MULTI_MAPPINGS,
				 HenshinFactory.eINSTANCE.createMapping()));
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
			childFeature == HenshinPackage.Literals.RULE__LHS ||
			childFeature == HenshinPackage.Literals.RULE__RHS ||
			childFeature == HenshinPackage.Literals.RULE__MAPPINGS ||
			childFeature == HenshinPackage.Literals.RULE__MULTI_MAPPINGS;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}
	
	@Override
	protected Command createRemoveCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Collection<?> collection) {
		
		if (feature == HenshinPackage.Literals.RULE__MAPPINGS)
			return new NegligentRemoveCommand(domain, owner, feature, collection);
		
		return super.createRemoveCommand(domain, owner, feature, collection);
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
		
		if (feature == HenshinPackage.Literals.RULE__LHS
				|| feature == HenshinPackage.Literals.RULE__RHS)
			if (value == SetCommand.UNSET_VALUE)
				return new GraphComplexUnsetCommand(domain, owner, feature);
		
		return super.createSetCommand(domain, owner, feature, value);
	}
	
}
