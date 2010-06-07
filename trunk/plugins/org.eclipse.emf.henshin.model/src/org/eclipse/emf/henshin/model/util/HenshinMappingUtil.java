/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University of Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.model.util;

import java.util.Collection;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;

/**
 * Common utility methods for Henshin {@link Mapping}s.
 * 
 * @generated NOT
 * @author Christian Krause
 */
public class HenshinMappingUtil {
	
	/**
	 * Find a mapping for a given node origin and image.
	 * 
	 * @param origin
	 *            Node origin.
	 * @param image
	 *            Node image.
	 * @param mappings
	 *            Mappings.
	 * @return The mapping if found, otherwise <code>null</code>.
	 */
	public static Mapping getMapping(Node origin, Node image,
			Collection<Mapping> mappings) {
		for (Mapping mapping : mappings) {
			if (mapping.getOrigin() == origin && mapping.getImage() == image) {
				return mapping;
			}
		}
		return null;
	}
	
	/**
	 * Create a new mapping between the origin and image node.
	 * If there exists already a mapping between the two nodes,
	 * it is returned instead.
	 * @param origin Origin node.
	 * @param image Image node.
	 * @param mappings Mappings.
	 * @return The newly created mapping.
	 */
	public static Mapping createMapping(Node origin, Node image,
			Collection<Mapping> mappings) {
		Mapping mapping = getMapping(origin, image, mappings);
		if (mapping==null) {
			mapping = HenshinFactory.eINSTANCE.createMapping();
			mapping.setOrigin(origin);
			mapping.setImage(image);
			mappings.add(mapping);
		}
		return mapping;
	}

	/**
	 * Remove a mapping between the given origin and image node.
	 * @param origin Origin node.
	 * @param image Image node.
	 * @param mappings Mappings.
	 * @return The removed mapping.
	 */
	public static Mapping removeMapping(Node origin, Node image,
			Collection<Mapping> mappings) {
		Mapping mapping = getMapping(origin, image, mappings);
		if (mapping!=null) {
			mappings.remove(mapping);
		}
		return mapping;
	}

	/**
	 * Find the image of a node with respect to a target graph and a list of
	 * mappings.
	 * 
	 * @param origin
	 *            Origin node.
	 * @param targetGraph
	 *            Target graph.
	 * @param mappings
	 *            Mappings.
	 * @return The image of the node.
	 */
	public static Node getNodeImage(Node origin, Graph targetGraph,
			Collection<Mapping> mappings) {
		Mapping mapping = getNodeImageMapping(origin, targetGraph, mappings);
		return (mapping != null) ? mapping.getImage() : null;
	}

	/**
	 * Find the origin of a node with respect to a list of mappings.
	 * 
	 * @param image
	 *            Image node.
	 * @param target
	 *            Target graph.
	 * @param mappings
	 *            Mappings.
	 * @return The image of the node.
	 */
	public static Node getNodeOrigin(Node image, Collection<Mapping> mappings) {
		Mapping mapping = getNodeOriginMapping(image, mappings);
		return (mapping != null) ? mapping.getOrigin() : null;
	}

	/**
	 * Find a corresponding mapping for a given origin nodes and target graph.
	 * 
	 * @param origin
	 *            Origin node.
	 * @param targetGraph
	 *            Target graph.
	 * @param mappings
	 *            Mappings.
	 * @return Mapping if found, <code>null</code> otherwise.
	 */
	public static Mapping getNodeImageMapping(Node origin, Graph targetGraph,
			Collection<Mapping> mappings) {
		for (Mapping mapping : mappings) {
			if (mapping.getOrigin() == origin
					&& mapping.getImage().getGraph() == targetGraph)
				return mapping;
		}
		return null;
	}

	/**
	 * Find the corresponding mapping for a given image node.
	 * 
	 * @param image
	 *            Image node.
	 * @param mappings
	 *            Mappings.
	 * @return Mapping if found, <code>null</code> otherwise.
	 */
	public static Mapping getNodeOriginMapping(Node image,
			Collection<Mapping> mappings) {
		for (Mapping mapping : mappings) {
			if (mapping.getImage() == image)
				return mapping;
		}
		return null;
	}

	/**
	 * Find the image of an edge.
	 * 
	 * @param edge
	 *            Origin edge.
	 * @param targetGraph
	 *            Graph the sought image is contained in
	 * @param mappings
	 *            Mappings.
	 * @return Edge image.
	 */
	public static Edge getEdgeImage(Edge edge, Graph targetGraph,
			Collection<Mapping> mappings) {
		if (edge.getSource() == null || edge.getTarget() == null)
			return null;
		Node source = getNodeImage(edge.getSource(), targetGraph, mappings);
		Node target = getNodeImage(edge.getTarget(), targetGraph, mappings);
		if (source == null || target == null)
			return null;
		return source.findOutgoingEdgeByType(target, edge.getType());
	}

	/**
	 * Find the origin of an edge.
	 * 
	 * @param edge
	 *            Image edge.
	 * @param mappings
	 *            Mappings.
	 * @return Edge image.
	 */
	public static Edge getEdgeOrigin(Edge edge, Collection<Mapping> mappings) {
		if (edge.getSource() == null || edge.getTarget() == null)
			return null;
		Node source = getNodeOrigin(edge.getSource(), mappings);
		Node target = getNodeOrigin(edge.getTarget(), mappings);
		if (source == null || target == null)
			return null;
		return source.findOutgoingEdgeByType(target, edge.getType());
	}

	/**
	 * Find the image of an attribute.
	 */
	public static Attribute getAttributeImage(Attribute attribute, Graph targetGraph,
			Collection<Mapping> mappings) {
		if (attribute.getNode()==null)
			return null;
		Node nodeImage = getNodeImage(attribute.getNode(), targetGraph, mappings);
		if (nodeImage==null)
			return null;
		return nodeImage.findAttributeByType(attribute.getType());
	}

	/**
	 * Find the origin of an attribute.
	 */
	public static Attribute getAttributeOrigin(Attribute attribute, Collection<Mapping> mappings) {
		if (attribute.getNode()==null)
			return null;
		Node nodeOrigin = getNodeOrigin(attribute.getNode(), mappings);
		if (nodeOrigin==null)
			return null;
		return nodeOrigin.findAttributeByType(attribute.getType());
	}

	/**
	 * Find the image of a node or an edge. This is a wrapper for
	 * {@link #getNodeImage(Node, Graph, Collection)} and
	 * {@link #getEdgeImage(Edge, Graph, Collection)}.
	 * 
	 * @param <T>
	 *            Either {@link Node} or {@link Edge} class.
	 * @param element
	 *            A node or edge instance.
	 * @param target
	 *            Target graph.
	 * @param mappings
	 *            Mappings.
	 * @return Image element.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getImage(T element, Graph target,
			Collection<Mapping> mappings) {
		if (element instanceof Node) {
			return (T) getNodeImage((Node) element, target, mappings);
		} else if (element instanceof Edge) {
			return (T) getEdgeImage((Edge) element, target, mappings);
		} else if (element instanceof Attribute) {
			return (T) getAttributeImage((Attribute) element, target, mappings);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Find the image of a node or an edge. This is a wrapper for
	 * {@link #getNodeOrigin(Node, Collection)} and
	 * {@link #getEdgeOrigin(Edge, Collection)}.
	 * 
	 * @param <T>
	 *            Either {@link Node} or {@link Edge} class.
	 * @param element
	 *            A node or edge instance.
	 * @param mappings
	 *            Mappings.
	 * @return Origin element.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getOrigin(T element, Collection<Mapping> mappings) {
		if (element instanceof Node) {
			return (T) getNodeOrigin((Node) element, mappings);
		} else if (element instanceof Edge) {
			return (T) getEdgeOrigin((Edge) element, mappings);
		} else if (element instanceof Attribute) {
			return (T) getAttributeOrigin((Attribute) element, mappings);
		} else {
			throw new IllegalArgumentException();
		}
	}

}