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
import java.util.Iterator;
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
import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;

public class HenshinGraph extends EmfGraph implements Adapter {
	private Graph henshinGraph;

	private Map<Node, EObject> node2eObjectMap;
	private Map<EObject, Node> eObject2nodeMap;
	private Map<EObject, List<Connection>> eObject2Connections;

	public HenshinGraph(Graph graph) {
		node2eObjectMap = new HashMap<Node, EObject>();
		eObject2nodeMap = new HashMap<EObject, Node>();
		eObject2Connections = new HashMap<EObject, List<Connection>>();

		henshinGraph = graph;

		henshin2emfGraph();
	}

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
					List<Object> attrValues = (List<Object>) eObject
							.eGet(attrType);
					attrValues.add(attrValue);
				} else {
					eObject.eSet(
							attrType,
							EcoreUtil.createFromString(
									attrType.getEAttributeType(), attrValue));
				}

				eObject.eAdapters().add(this);
			}
		}

		for (Edge edge : new ArrayList<Edge>(henshinGraph.getEdges())) {
			EReference edgeType = edge.getType();
			EObject ownerObject = node2eObjectMap.get(edge.getSource());

			// don't notify me about changes that I made
			ownerObject.eAdapters().remove(this);
			EObject targetObject = node2eObjectMap.get(edge.getTarget());
			if (edgeType.isMany()) {
				List<Object> edgeValues = (List<Object>) ownerObject
						.eGet(edgeType);
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
				if (!henshinGraph.getNodes().contains(node)) {
					henshinGraph.getNodes().add(node);
					for (Connection connection : eObject2Connections
							.get(eObject)) {
						Edge edge = HenshinFactory.eINSTANCE.createEdge();
						edge.setType(connection.getTyp());
						edge.setSource(eObject2nodeMap.get(connection
								.getSource()));
						edge.setTarget(eObject2nodeMap.get(connection
								.getTarget()));
						edge.setGraph(henshinGraph);
					}
				}
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
				List<Connection> connections = new ArrayList<Connection>();
				for (Edge edge : list) {
					connections.add(new Connection(node2eObjectMap.get(edge
							.getSource()),
							node2eObjectMap.get(edge.getTarget()), edge
									.getType()));
					edge.setSource(null);
					edge.setTarget(null);
					edge.setGraph(null);
				}
				eObject2Connections.put(eObject, connections);
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
			if (oldValue != null) {
				removeFromHenshinGraph(owner, (EStructuralFeature) feature,
						oldValue);
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

	private void removeFromHenshinGraph(EObject owner,
			EStructuralFeature feature, Object value) {
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

				if (attribute != null) {
					attribute.setNode(null);
				}
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

					if (edge != null) {
						edge.setSource(null);
						edge.setTarget(null);
						edge.setGraph(null);
					} else {
						Iterator<Connection> iter = eObject2Connections.get(
								owner).iterator();
						while (iter.hasNext()) {
							Connection conn = iter.next();
							if (conn.getTarget() == value
									&& conn.getTyp() == feature) {
								iter.remove();
								break;
							}
						}
					}
				}
			}
		}
	}

	private void addToHenshinGraph(EObject owner, EStructuralFeature feature,
			Object value) {
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

	private class Connection {

		private EObject source;

		private EObject target;

		private EReference typ;

		public Connection(EObject source, EObject target, EReference typ) {
			this.source = source;
			this.target = target;
			this.typ = typ;
		}

		/**
		 * @return the source
		 */
		public synchronized EObject getSource() {
			return source;
		}

		/**
		 * @return the target
		 */
		public synchronized EObject getTarget() {
			return target;
		}

		/**
		 * @return the typ
		 */
		public synchronized EReference getTyp() {
			return typ;
		}

	}
}
