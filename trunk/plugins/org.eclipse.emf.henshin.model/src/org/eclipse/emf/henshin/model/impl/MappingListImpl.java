/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.model.impl;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Node;

/**
 * An implementation of the {@link MappingList} interface.
 * @author Christian Krause
 */
public class MappingListImpl extends EObjectContainmentEList<Mapping> implements MappingList {

	// Generated serial ID:
	private static final long serialVersionUID = -7095784906496813L;

	/**
	 * Default constructor
	 * @param owner Owner of this mapping list.
	 * @param featureID Feature ID.
	 */
	public MappingListImpl(InternalEObject owner, int featureID) {
		super(Mapping.class, owner, featureID);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#get(org.eclipse.emf.henshin.model.Node, org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public Mapping get(Node origin, Node image) {
		for (Mapping m : this) {
			if (m.getOrigin()==origin && m.getImage()==image) {
				return m;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#add(org.eclipse.emf.henshin.model.Node, org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public Mapping add(Node origin, Node image) {
		Mapping m = get(origin, image);
		if (m==null) {
			m = new MappingImpl();
			m.setOrigin(origin);
			m.setImage(image);
			super.add(m);
		}
		return m;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#add(org.eclipse.emf.henshin.model.Edge, org.eclipse.emf.henshin.model.Edge)
	 */
	@Override
	public void add(Edge origin, Edge image) {
		add(origin.getSource(), image.getSource());
		add(origin.getTarget(), image.getTarget());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#add(org.eclipse.emf.henshin.model.Attribute, org.eclipse.emf.henshin.model.Attribute)
	 */
	@Override
	public Mapping add(Attribute origin, Attribute image) {
		return add(origin.getNode(), image.getNode());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#add(org.eclipse.emf.henshin.model.GraphElement, org.eclipse.emf.henshin.model.GraphElement)
	 */
	@Override
	public <E extends GraphElement> void add(E origin, E image) {
		if (origin instanceof Node) {
			add((Node) origin, (Node) image);
		}
		else if (origin instanceof Edge) {
			add((Edge) origin, (Edge) image);
		}
		else if (origin instanceof Attribute) {
			add((Attribute) origin, (Attribute) image);
		}
		else {
			throw new IllegalArgumentException("Element of unknown type: " + image);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#remove(org.eclipse.emf.henshin.model.Node, org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public Mapping remove(Node origin, Node image) {
		Mapping m = get(origin, image);
		if (m!=null) {
			remove(m);
		}
		return m;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#remove(org.eclipse.emf.henshin.model.Edge, org.eclipse.emf.henshin.model.Edge)
	 */
	@Override
	public void remove(Edge origin, Edge image) {
		remove(origin.getSource(), image.getSource());
		remove(origin.getTarget(), image.getTarget());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#remove(org.eclipse.emf.henshin.model.Attribute, org.eclipse.emf.henshin.model.Attribute)
	 */
	@Override
	public Mapping remove(Attribute origin, Attribute image) {
		return remove(origin.getNode(), image.getNode());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#remove(org.eclipse.emf.henshin.model.GraphElement, org.eclipse.emf.henshin.model.GraphElement)
	 */
	@Override
	public <E extends GraphElement> void remove(E origin, E image) {
		if (origin instanceof Node) {
			remove((Node) origin, (Node) image);
		}
		else if (origin instanceof Edge) {
			remove((Edge) origin, (Edge) image);
		}
		else if (origin instanceof Attribute) {
			remove((Attribute) origin, (Attribute) image);
		}
		else {
			throw new IllegalArgumentException("Element of unknown type: " + origin);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#getImage(org.eclipse.emf.henshin.model.Node, org.eclipse.emf.henshin.model.Graph)
	 */
	@Override
	public Node getImage(Node origin, Graph imageGraph) {
		for (Mapping m : this) {
			if (m.getOrigin()==origin && m.getImage()!=null && 
				(imageGraph==null || imageGraph==m.getImage().getGraph())) {
				return m.getImage();
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#getOrigin(org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public Node getOrigin(Node image) {
		for (Mapping m : this) {
			if (m.getImage()==image && m.getOrigin()!=null) {
				return m.getOrigin();
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#getImage(org.eclipse.emf.henshin.model.Edge, org.eclipse.emf.henshin.model.Graph)
	 */
	@Override
	public Edge getImage(Edge origin, Graph imageGraph) {
		if (origin.getSource()==null || origin.getTarget()==null) {
			return null;
		}
		Node source = getImage(origin.getSource(), imageGraph);
		Node target = getImage(origin.getTarget(), imageGraph);
		if (source==null || target==null) {
			return null;
		}
		return source.getOutgoing(origin.getType(), target);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#getOrigin(org.eclipse.emf.henshin.model.Edge)
	 */
	@Override
	public Edge getOrigin(Edge image) {
		if (image.getSource()==null || image.getTarget()==null) {
			return null;
		}
		Node source = getOrigin(image.getSource());
		Node target = getOrigin(image.getTarget());
		if (source==null || target==null) {
			return null;
		}
		return source.getOutgoing(image.getType(), target);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#getImage(org.eclipse.emf.henshin.model.Attribute, org.eclipse.emf.henshin.model.Graph)
	 */
	@Override
	public Attribute getImage(Attribute origin, Graph imageGraph) {
		if (origin.getNode()==null) {
			return null;
		}
		Node nodeImage = getImage(origin.getNode(), imageGraph);
		if (nodeImage==null) {
			return null;
		}
		return nodeImage.getAttribute(origin.getType());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#getOrigin(org.eclipse.emf.henshin.model.Attribute)
	 */
	@Override
	public Attribute getOrigin(Attribute image) {
		if (image.getNode()==null) {
			return null;
		}
		Node nodeOrigin = getOrigin(image.getNode());
		if (nodeOrigin==null) {
			return null;
		}
		return nodeOrigin.getAttribute(image.getType());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#getOrigin(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E extends GraphElement> E getOrigin(E image) {
		if (image instanceof Node) {
			return (E) getOrigin((Node) image);
		}
		else if (image instanceof Edge) {
			return (E) getOrigin((Edge) image);
		}
		else if (image instanceof Attribute) {
			return (E) getOrigin((Attribute) image);
		}
		else {
			throw new IllegalArgumentException("Element of unknown type: " + image);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#getImage(java.lang.Object, org.eclipse.emf.henshin.model.Graph)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E extends GraphElement> E getImage(E origin, Graph imageGraph) {
		if (origin instanceof Node) {
			return (E) getImage((Node) origin, imageGraph);
		}
		else if (origin instanceof Edge) {
			return (E) getImage((Edge) origin, imageGraph);
		}
		else if (origin instanceof Attribute) {
			return (E) getImage((Attribute) origin, imageGraph);
		}
		else {
			throw new IllegalArgumentException("Element of unknown type: " + origin);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.model.MappingList#isOnto(org.eclipse.emf.henshin.model.Graph)
	 */
	@Override
	public boolean isOnto(Graph imageGraph) {
		if (imageGraph==null) {
			return false;
		}
		for (Node node : imageGraph.getNodes()) {
			// Not the image of a mapping? Or a different type?
			Node nodeOrigin = getOrigin(node);
			if (nodeOrigin==null || nodeOrigin.getType()!=node.getType()) {
				return false;
			}
			// Check the attributes of this node as well.
			for (Attribute attribute : node.getAttributes()) {
				Attribute attOrigin = getOrigin(attribute);
				if (attOrigin==null || !valueEquals(attribute.getValue(), attOrigin.getValue())) {
					return false;
				}
			}
		}
		for (Edge edge : imageGraph.getEdges()) {
			// Not the image of a mapping? Or a different type?
			Edge edgeOrigin = getOrigin(edge);
			if (edgeOrigin==null || edgeOrigin.getType()!=edge.getType()) {
				return false;
			}
		}
		return true;
	}

	/*
	 * Check if to attribute values are equal.
	 */
	static boolean valueEquals(String v1, String v2) {
		if (v1==null) {
			return (v2==null);
		}
		if (v2==null) {
			return false;
		}
		return v1.trim().equals(v2.trim());
	}

}
