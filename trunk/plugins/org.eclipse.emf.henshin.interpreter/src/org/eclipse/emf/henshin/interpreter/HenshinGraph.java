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
package org.eclipse.emf.henshin.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;

public class HenshinGraph extends EmfGraph implements Adapter {
	private Graph henshinGraph;
	
	private Map<Node, EObject> node2eObjectMap;
	private Map<EObject, Node> eObject2nodeMap;
	
	public HenshinGraph(Graph graph) {
		node2eObjectMap = new HashMap<Node, EObject>();
		eObject2nodeMap = new HashMap<EObject, Node>();
		
		henshinGraph = graph;
		
		henshin2emfGraph();
	}
	
	/**
	 * Translates the Henshin {@link Graph} in {@link #henshinGraph} into an
	 * ordinary EMF graph with {@link EObject}s, {@link EReference}s and so on.
	 * Afterwards, corresponding {@link EObject}s can be found in
	 * <code>eObjects</code> and correspondences in <code>eObject2nodeMap</code>
	 * and <code>node2eObjectMap</code>.
	 * <p>
	 * <strong>Remark:</strong> Currently, edges typed over <i>derived</i>
	 * {@link EReference}s (see {@link EReference#isDerived()}) are omitted
	 * during translation. Furthermore, if a typing {@link EReference}s is
	 * non-changeable (see {@link EReference#isChangeable()}) but its opposite
	 * (see {@link EReference#getEOpposite()}) is changeable, the reference is
	 * translated into its opposite.
	 */
	@SuppressWarnings("unchecked")
	private void henshin2emfGraph() {
		eObjects.clear();
		// henshinGraph.eAdapters().clear();
		eObject2nodeMap.clear();
		node2eObjectMap.clear();
		
		for (Node node : henshinGraph.getNodes()) {
			EObject eObject = node2eObjectMap.get(node);
			
			if (eObject == null) {
				EClass nodeType = node.getType();
				EFactory factory = nodeType.getEPackage().getEFactoryInstance();
				eObject = factory.create(nodeType);
				addEObjectToGraph(eObject);
				addSynchronizedPair(node, eObject);
			}
			
			for (Attribute attr : node.getAttributes()) {
				// don't notify me about changes that I made
				eObject.eAdapters().remove(this);
				EAttribute attrType = attr.getType();
				String attrValue = attr.getValue();
				attrValue = attrValue.replaceAll("\"", "");
				
				if (attrType.isMany()) {
					List<Object> attrValues = (List<Object>) eObject.eGet(attrType);
					attrValues.add(attrValue);
				} else {
					eObject.eSet(attrType,
							EcoreUtil.createFromString(attrType.getEAttributeType(), attrValue));
				}
				
				eObject.eAdapters().add(this);
			}
		}
		
		for (Edge edge : new ArrayList<Edge>(henshinGraph.getEdges())) {
			EReference edgeType = edge.getType();
			/*
			 * If reference <code>edgeType</code> is derived it is available
			 * implicitly and does not need to be set. Furthermore, if a
			 * reference is not changeable it is omitted as well.
			 */
			if (edgeType.isDerived())
				continue;
			
			EObject ownerObject = node2eObjectMap.get(edge.getSource());
			EObject targetObject = node2eObjectMap.get(edge.getTarget());
			
			/*
			 * If the edgeType is not changeable but its opposite is, then we
			 * switch to the opposite.
			 */
			if (!edgeType.isChangeable()) {
				if (edgeType.getEOpposite() != null && edgeType.getEOpposite().isChangeable()) {
					edgeType = edgeType.getEOpposite();
					// switch source and target
					EObject temp = ownerObject;
					ownerObject = targetObject;
					targetObject = temp;
				} else
					/*
					 * Otherwise we cannot handle the edge and omit it (or
					 * better: shall throw an exception)
					 */
					continue;
			}// if
			
			// don't notify me about changes that I made
			ownerObject.eAdapters().remove(this);
			
			if (edgeType.isMany()) {
				List<Object> edgeValues = (List<Object>) ownerObject.eGet(edgeType);
				edgeValues.add(targetObject);
			} else {
				ownerObject.eSet(edgeType, targetObject);
			}
			
			ownerObject.eAdapters().add(this);
		}
	}
	
	@Override
	public boolean addEObject(EObject eObject) {
		boolean isNew = super.addEObject(eObject);
		
		if (isNew) {
			HenshinFactory factory = HenshinFactory.eINSTANCE;
			
			Node node = eObject2nodeMap.get(eObject);
			if (node == null) {
				node = factory.createNode();
				node.setType(eObject.eClass());
				henshinGraph.getNodes().add(node);
				
				addSynchronizedPair(node, eObject);
			} else {
				if (!henshinGraph.getNodes().contains(node))
					henshinGraph.getNodes().add(node);
			}
		}
		
		return isNew;
	}
	
	@Override
	public boolean removeEObject(EObject eObject) {
		boolean wasRemoved = super.removeEObject(eObject);
		
		if (wasRemoved) {
			Node node = eObject2nodeMap.get(eObject);
			
			if (node != null) {
				henshinGraph.getNodes().remove(node);
				List<Edge> list = new ArrayList<Edge>(node.getIncoming());
				list.addAll(node.getOutgoing());
				for (Edge edge : list) {
					edge.setSource(null);
					edge.setTarget(null);
					edge.setGraph(null);
				}
				removeSynchronizedPair(node, eObject);
			}
		}
		
		return wasRemoved;
		
	}
	
	/**
	 * This methods will update the EMF representation of the Henshin-Graph.
	 */
	public void updateEmfGraph() {
		henshin2emfGraph();
	}
	
	private void addSynchronizedPair(Node node, EObject eObject) {
		node2eObjectMap.put(node, eObject);
		eObject2nodeMap.put(eObject, node);
		
		eObject.eAdapters().add(this);
	}
	
	private void removeSynchronizedPair(Node node, EObject eObject) {
		// node2eObjectMap.remove(node);
		// eObject2nodeMap.remove(eObject);
		
		// eObject.eAdapters().remove(this);
	}
	
	@Override
	public Notifier getTarget() {
		return null;
	}
	
	@Override
	public boolean isAdapterForType(Object type) {
		return false;
	}
	
	@Override
	public void notifyChanged(Notification notification) {
		EObject owner = (EObject) notification.getNotifier();
		Node ownerNode = eObject2nodeMap.get(owner);
		Object feature = notification.getFeature();
		
		Object oldValue = notification.getOldValue();
		Object newValue = notification.getNewValue();
		
		if (feature instanceof EStructuralFeature && ownerNode != null) {
			// remove all deleted structures from the henshin graph
			if (oldValue != null && newValue != oldValue) {
				removeFromHenshinGraph(ownerNode, (EStructuralFeature) feature, oldValue);
			}
			
			// add new structures to henshin graph
			if (newValue != null) {
				addToHenshinGraph(owner, (EStructuralFeature) feature, newValue);
			}
		}
	}
	
	@Override
	public void setTarget(Notifier newTarget) {
	}
	
	private void removeFromHenshinGraph(Node owner, EStructuralFeature feature, Object value) {
		if (feature instanceof EAttribute) {
			Attribute attribute = null;
			for (Attribute nodeAttribute : owner.getAttributes()) {
				if (nodeAttribute.getType() == feature) {
					attribute = nodeAttribute;
					break;
				}
			}
			
			if (attribute != null) {
				attribute.setNode(null);
			}
		} else if (feature instanceof EReference) {
			Edge edge = null;
			
			if (value instanceof EObject) {
				Node targetNode = eObject2nodeMap.get(value);
				for (Edge outgoingEdge : owner.getOutgoing()) {
					if (outgoingEdge.getTarget() == targetNode && outgoingEdge.getType() == feature) {
						edge = outgoingEdge;
						break;
					}
				}
				
				if (edge != null) {
					edge.setSource(null);
					edge.setTarget(null);
					edge.setGraph(null);
				}
			}
		}
	}
	
	private void addToHenshinGraph(EObject owner, EStructuralFeature feature, Object value) {
		Node node = eObject2nodeMap.get(owner);
		
		if (node != null && value != null) {
			if (feature instanceof EAttribute) {
				Attribute attribute = null;
				for (Attribute nodeAttribute : node.getAttributes()) {
					if (nodeAttribute.getType() == feature) {
						attribute = nodeAttribute;
						break;
					}
				}
				
				if (attribute == null) {
					attribute = HenshinFactory.eINSTANCE.createAttribute();
					attribute.setType((EAttribute) feature);
					attribute.setNode(node);
				}
				attribute.setValue(value.toString());
				
			} else if (feature instanceof EReference) {
				Edge edge = null;
				
				if (value instanceof EObject) {
					Node targetNode = eObject2nodeMap.get(value);
					for (Edge outgoingEdge : node.getOutgoing()) {
						if (outgoingEdge.getTarget() == targetNode
								&& outgoingEdge.getType() == feature) {
							edge = outgoingEdge;
							break;
						}
					}
					
					if (edge == null) {
						edge = HenshinFactory.eINSTANCE.createEdge();
						edge.setSource(node);
						edge.setTarget(targetNode);
						edge.setGraph(henshinGraph);
						edge.setType((EReference) feature);
					}
				}
			}
		}
	}
	
	/**
	 * @return the node2eObjectMap
	 */
	public Map<Node, EObject> getNode2eObjectMap() {
		return node2eObjectMap;
	}
	
	/**
	 * @param node2eObjectMap
	 *            the node2eObjectMap to set
	 */
	public void setNode2eObjectMap(Map<Node, EObject> node2eObjectMap) {
		this.node2eObjectMap = node2eObjectMap;
	}
	
	/**
	 * @return the eObject2nodeMap
	 */
	public Map<EObject, Node> geteObject2nodeMap() {
		return eObject2nodeMap;
	}
	
	/**
	 * @param eObject2nodeMap
	 *            the eObject2nodeMap to set
	 */
	public void seteObject2nodeMap(Map<EObject, Node> eObject2nodeMap) {
		this.eObject2nodeMap = eObject2nodeMap;
	}
}
