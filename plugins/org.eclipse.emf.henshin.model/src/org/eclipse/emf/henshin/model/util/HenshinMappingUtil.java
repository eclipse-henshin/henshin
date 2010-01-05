package org.eclipse.emf.henshin.model.util;

import java.util.Collection;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;

/**
 * Common utility methods for Henshin {@link Mapping}s.
 * @generated NOT
 * @author Christian Krause
 */
public class HenshinMappingUtil {
	
	/**
	 * Create a mapping for a given node origin and image.
	 * @param origin Origin node.
	 * @param image Image node.
	 * @return The created mapping.
	 */
	public static Mapping createMapping(Node origin, Node image) {
		Mapping mapping = HenshinFactory.eINSTANCE.createMapping();
		mapping.setOrigin(origin);
		mapping.setImage(image);
		return mapping;
	}
	
	/**
	 * Find a mapping for a given node origin and image.
	 * @param origin Node origin.
	 * @param image Node image.
	 * @param mappings Mappings.
	 * @return The mapping if found, otherwise <code>null</code>.
	 */
	public static Mapping getMapping(Node origin, Node image, Collection<Mapping> mappings) {
		for (Mapping mapping : mappings) {
			if (mapping.getOrigin()==origin && mapping.getImage()==image) {
				return mapping;
			}
		}
		return null;
	}
	
	/**
	 * Find the image of a node with respect to a target graph and a list of mappings.
	 * @param origin Origin node.
	 * @param target Target graph.
	 * @param mappings Mappings.
	 * @return The image of the node.
	 */
	public static Node getNodeImage(Node origin, Graph target, Collection<Mapping> mappings) {
		Mapping mapping = getNodeImageMapping(origin, target, mappings);
		return (mapping!=null) ? mapping.getImage() : null;
	}
	
	/**
	 * Find the origin of a node with respect to a list of mappings.
	 * @param image Image node.
	 * @param target Target graph.
	 * @param mappings Mappings.
	 * @return The image of the node.
	 */
	public static Node getNodeOrigin(Node image, Collection<Mapping> mappings) {
		Mapping mapping = getNodeOriginMapping(image, mappings);
		return (mapping!=null) ? mapping.getImage() : null;
	}

	/**
	 * Find a corresponding mapping for a given origin nodes and target graph.
	 * @param origin Origin node.
	 * @param graph Target graph.
	 * @param mappings Mappings.
	 * @return Mapping if found, <code>null</code> otherwise.
	 */
	public static Mapping getNodeImageMapping(Node origin, Graph graph, Collection<Mapping> mappings) {
		for (Mapping mapping : mappings) {
			if (mapping.getOrigin()==origin && mapping.getImage().getGraph()==graph) return mapping;
		}
		return null;
	}

	/**
	 * Find the corresponding mapping for a given image node.
	 * @param image Image node.
	 * @param mappings Mappings.
	 * @return Mapping if found, <code>null</code> otherwise.
	 */
	public static Mapping getNodeOriginMapping(Node image, Collection<Mapping> mappings) {		
		for (Mapping mapping : mappings) {
			if (mapping.getImage()==image) return mapping;
		}
		return null;
	}
	
	/**
	 * Find the image of an edge.
	 * @param edge Origin edge.
	 * @param mappings Mappings.
	 * @return Edge image.
	 */
	public static Edge getEdgeImage(Edge edge, Graph graph, Collection<Mapping> mappings) {
		if (edge.getSource()==null || edge.getTarget()==null) return null;
		Node source = getNodeImage(edge.getSource(), graph, mappings);
		Node target = getNodeImage(edge.getTarget(), graph, mappings);
		if (source==null || target==null) return null;
		return HenshinGraphUtil.findEdge(source, target, edge.getType());
	}

	/**
	 * Find the origin of an edge.
	 * @param edge Image edge.
	 * @param mappings Mappings.
	 * @return Edge image.
	 */
	public static Edge getEdgeOrigin(Edge edge, Collection<Mapping> mappings) {
		if (edge.getSource()==null || edge.getTarget()==null) return null;
		Node source = getNodeOrigin(edge.getSource(), mappings);
		Node target = getNodeOrigin(edge.getTarget(), mappings);
		if (source==null || target==null) return null;
		return HenshinGraphUtil.findEdge(source, target, edge.getType());
	}
	
	/**
	 * Find the image of a node or an edge. This is a wrapper for {@link #getNodeImage(Node, Graph, Collection)}
	 * and {@link #getEdgeImage(Edge, Graph, Collection)}.
	 * @param <T> Either {@link Node} or {@link Edge} class. 
	 * @param element A node or edge instance.
	 * @param target Target graph.
	 * @param mappings Mappings.
	 * @return Image element.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getImage(T element, Graph target, Collection<Mapping> mappings) {
		if (element instanceof Node) {
			return (T) getNodeImage((Node) element, target, mappings);
		} else if (element instanceof Edge) {
			return (T) getEdgeImage((Edge) element, target, mappings);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Find the image of a node or an edge. This is a wrapper for {@link #getNodeOrigin(Node, Collection)}
	 * and {@link #getEdgeOrigin(Edge, Collection)}.
	 * @param <T> Either {@link Node} or {@link Edge} class. 
	 * @param element A node or edge instance.
	 * @param mappings Mappings.
	 * @return Origin element.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getOrigin(T element, Collection<Mapping> mappings) {
		if (element instanceof Node) {
			return (T) getNodeOrigin((Node) element, mappings);
		} else if (element instanceof Edge) {
			return (T) getEdgeOrigin((Edge) element, mappings);
		} else {
			throw new IllegalArgumentException();
		}
	}

}
