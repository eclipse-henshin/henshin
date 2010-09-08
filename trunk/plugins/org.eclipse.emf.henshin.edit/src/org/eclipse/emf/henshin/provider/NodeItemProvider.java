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
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.emf.henshin.model.Node} object. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class NodeItemProvider extends NamedElementItemProvider implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider,
		IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NodeItemProvider(AdapterFactory adapterFactory) {
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

			addTypePropertyDescriptor(object);
			addIncomingPropertyDescriptor(object);
			addOutgoingPropertyDescriptor(object);
			addAllEdgesPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Type feature. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Node_type_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_Node_type_feature",
						"_UI_Node_type"), HenshinPackage.Literals.NODE__TYPE, true, false, true,
				null, null, null));
	}

	/**
	 * This adds a property descriptor for the Incoming feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addIncomingPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Node_incoming_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_Node_incoming_feature",
						"_UI_Node_type"), HenshinPackage.Literals.NODE__INCOMING, true, false,
				true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Outgoing feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addOutgoingPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Node_outgoing_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_Node_outgoing_feature",
						"_UI_Node_type"), HenshinPackage.Literals.NODE__OUTGOING, true, false,
				true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the All Edges feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addAllEdgesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Node_allEdges_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_Node_allEdges_feature",
						"_UI_Node_type"), HenshinPackage.Literals.NODE__ALL_EDGES, true, false,
				true, null, null, null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to
	 * deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand},
	 * {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in
	 * {@link #createCommand}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(HenshinPackage.Literals.NODE__ATTRIBUTES);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper
		// feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * <!-- begin-user-doc --> <br>
	 * This method returns different visualizations of a {@link Node} regarding
	 * to its occurrence in lhs, rhs, nestedconditions and related mappings.<br>
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Object getImage(Object object) {
		
		Node node = (Node) object;
		Object defaultImage = overlayImage(object, getResourceLocator().getImage("full/obj16/Node.png"));
		
		// get the container graph
		Graph graph = node.getGraph();
		if (graph==null) {
			return defaultImage;
		}
		
		// get the container rule
		Rule rule = node.getGraph().getContainerRule();
		if (rule==null) {
			return defaultImage;
		}
		
		for (Mapping mapping : rule.getMappings()) {
			// if this node occurs in the Mapping-List, it is a preserved node
			// i.e.
			// it is source or origin of a mapping from LHS to RHS
			if ((mapping.getOrigin() == node) || (mapping.getImage() == node))
				return defaultImage;
		}// for

		if (rule.getLhs() == graph) {
			return overlayImage(object, getResourceLocator().getImage("full/obj16/Node_Delete.png"));
		} else if (rule.getRhs() == graph) {
			return overlayImage(object, getResourceLocator().getImage("full/obj16/Node_Add.png"));
		} else if (graph.eContainer() instanceof NestedCondition) {
			NestedCondition nc = (NestedCondition) graph.eContainer();
			// Do the following only for negative application conditions (NACs)
			if (nc.isNegated()) {
				for (Mapping mapping : nc.getMappings()) {
					// if this node occurs in the Mapping-List, it is a mapped
					// from
					// the LHS of the rule
					if (mapping.getImage() == node) // its the mapping 'image',
													// not
													// the visual one ;-)
						return defaultImage;
				}// for
				return overlayImage(object,
						getResourceLocator().getImage("full/obj16/Node_Forbid.png"));
				/*
				 * Please note, NAC nodes which are mapped by LHS nodes are not
				 * marked by a 'forbid symbol' even though they belong to the
				 * forbidden structure as well. However, this shall indicate to
				 * the user in a straight way, that it is a mapped node.
				 */
			}// if negated
		}// if else if

		return defaultImage;
	}// getImage

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {

		Node node = (Node) object;
		String label = getString("_UI_Node_type");

		String name = "";
		if (node.getName() != null)
			name = name + node.getName();
		if (node.getType() != null)
			name = name + ":" + node.getType().getName();
		name = name.trim();

		return name.length() != 0 ? label + " " + name : label;

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

		switch (notification.getFeatureID(Node.class)) {
		case HenshinPackage.NODE__ATTRIBUTES:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(),
					true, false));
			return;
		}
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

		newChildDescriptors.add(createChildParameter(HenshinPackage.Literals.NODE__ATTRIBUTES,
				HenshinFactory.eINSTANCE.createAttribute()));
	}

}
