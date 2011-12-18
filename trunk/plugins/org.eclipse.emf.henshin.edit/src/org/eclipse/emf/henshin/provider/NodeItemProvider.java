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
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
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
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinMultiRuleUtil;
import org.eclipse.emf.henshin.provider.descriptors.NodeTypePropertyDescriptor;
import org.eclipse.emf.henshin.provider.util.IconUtil;
import org.eclipse.emf.henshin.provider.util.ItemPropertyDescriptorDecorator;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.emf.henshin.model.Node} object. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class NodeItemProvider extends NamedElementItemProvider implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider,
		IItemLabelProvider, IItemPropertySource, IItemColorProvider {
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
	 * @generated NOT
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);
			
			addTypePropertyDescriptor(object);
			addIncomingPropertyDescriptor(object);
			addOutgoingPropertyDescriptor(object);
			addAllEdgesPropertyDescriptor(object);
			List<IItemPropertyDescriptor> origDescriptors = itemPropertyDescriptors;
			itemPropertyDescriptors = new ArrayList<IItemPropertyDescriptor>(origDescriptors.size());
			for (IItemPropertyDescriptor origDescriptor : origDescriptors)
				itemPropertyDescriptors.add(new ItemPropertyDescriptorDecorator(origDescriptor) {
					public boolean canSetProperty(Object object) {
						return isUserEditable(object) && super.canSetProperty(object);
					}
				});
		}
		return itemPropertyDescriptors;
	}
	
	/**
	 * This adds a property descriptor for the Type feature. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	protected void addTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(new NodeTypePropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(), getString("_UI_Node_type_feature"), getString(
						"_UI_PropertyDescriptor_description", "_UI_Node_type_feature",
						"_UI_Node_type")));
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
		Object defaultImage = overlayImage(object,
				getResourceLocator().getImage("full/obj16/Node.png"));
		
		if (node.eContainer() == null) {
			// This is used to return the icon needed for the visual editor.
			// Otherwise, since the visual editor will create Nodese without
			// source, target nor type,
			// a red border will be drawn around the Node icon in the properties
			// view.
			return defaultImage;
		}
		
		// draw a red border around the icon if the node needs attention
		// (i.e. has no type)
		boolean needsAttention = false;
		needsAttention |= (node.getType() == null);
		
		if (needsAttention) {
			Object attentionOverlay = getResourceLocator().getImage("full/ovr16/Attn_ovr.png");
			defaultImage = IconUtil.getCompositeImage(defaultImage, attentionOverlay);
		}
		
		// get the container graph
		Graph graph = node.getGraph();
		if (graph == null) {
			return defaultImage;
		}
		
		// get the container
		Object container = node.getGraph().eContainer();
		
		if (container instanceof Rule) {
			Rule rule = (Rule) container;
			for (Mapping mapping : rule.getMappings()) {
				// if this node occurs in the Mapping-List, it is a preserved
				// node
				// i.e.
				// it is source or origin of a mapping from LHS to RHS
				if ((mapping.getOrigin() == node) || (mapping.getImage() == node))
					return defaultImage;
			}// for
			
			if (rule.getLhs() == graph) {
				Object deleteOverlay = getResourceLocator().getImage("full/ovr16/Del_ovr.png");
				return IconUtil.getCompositeImage(defaultImage, deleteOverlay);
			} else { // rule.getRhs() == graph
				Object createOverlay = getResourceLocator().getImage("full/ovr16/Create_ovr.png");
				return IconUtil.getCompositeImage(defaultImage, createOverlay);
			}// if else
			
		} else if (container instanceof NestedCondition) {
			NestedCondition nc = (NestedCondition) container;
			// Do the following only for negative application conditions (NACs)
			if (nc.isNegated()) {
				for (Mapping mapping : nc.getMappings()) {
					// if this node occurs in the Mapping-List, it is a mapped
					if (mapping.getImage() == node) // its the mapping 'image',
													// not
													// the visual one ;-)
						return defaultImage;
				}// for
				
				Object forbidOverlay = getResourceLocator().getImage("full/ovr16/Forbid_ovr.png");
				return IconUtil.getCompositeImage(defaultImage, forbidOverlay);
				
				/*
				 * Please note, mapped NAC-nodes are not marked by a 'forbid
				 * symbol' even though they belong to the forbidden structure as
				 * well. However, this shall indicate to the user in a straight
				 * way, that it is a mapped node.
				 */
			}// if negated
		}// if elseif
		
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
		String prefix = getString("_UI_Node_type");
		String label = getNodeLabel(node);
		return label.length() != 0 ? prefix + " " + label : prefix;
	}
	
	/**
	 * Compute a nice label for a node.
	 */
	public static String getNodeLabel(Node node) {
		String label = "";
		if (node.getName() != null)
			label = label + node.getName();
		if (node.getType() != null)
			label = label + ":" + node.getType().getName();
		return label.trim();
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
		switch (notification.getFeatureID(Node.class)) {
			case HenshinPackage.NODE__ATTRIBUTES:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(),
						true, false));
				break;
			case HenshinPackage.NODE__TYPE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(),
						false, true));
				break;
			case HenshinPackage.NAMED_ELEMENT__NAME:
				Node node = (Node) notification.getNotifier();
				notifyEdges(node, notification);
				break;
		}// switch
		super.notifyChanged(notification);
	}// notifyChanged
	
	private void notifyEdges(Node node, Notification notification) {
		List<Edge> edgeList = new ArrayList<Edge>(node.getIncoming());
		edgeList.addAll(node.getOutgoing());
		if (!edgeList.isEmpty()) {
			ItemProviderAdapter adapter = (ItemProviderAdapter) this.adapterFactory.adapt(
					edgeList.get(0), null);
			for (Edge edge : edgeList) {
				Notification notif = new ViewerNotification(notification, edge, false, true);
				adapter.fireNotifyChanged(notif);
			}// for
		}// if
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
	
	@Override
	protected Command createSetCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Object value, int index) {
		Node node = (Node) owner;
		CompoundCommand cmpCmd = new CompoundCommand(CompoundCommand.LAST_COMMAND_ALL);
		for (Node dependentNode : HenshinMultiRuleUtil.getDependentNodes(node)) {
			cmpCmd.append(createSetCommand(domain, dependentNode, feature, value, index));
		}
		cmpCmd.append(super.createSetCommand(domain, owner, feature, value, index));
		return cmpCmd.unwrap();
	}
	
	protected boolean isUserEditable(Object object) {
		Node node = (Node) object;
		
		if (node.getGraph() != null && (node.getGraph().isLhs() || node.getGraph().isRhs())) {
			Rule rule = node.getGraph().getContainerRule();
			return rule.getOriginInKernelRule(node) == null;
		}
		return true;
	}
	
	@Override
	public Object getForeground(Object object) {
		return isUserEditable(object) ? super.getForeground(object) : URI
				.createURI("color://rgb/0/0/255");
	}
	
}
