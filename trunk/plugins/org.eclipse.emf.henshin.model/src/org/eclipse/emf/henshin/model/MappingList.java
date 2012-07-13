package org.eclipse.emf.henshin.model;

import org.eclipse.emf.common.util.EList;

/**
 * Interface for {@link Mapping} lists. Provides helper methods.
 * 
 * @author Christian Krause
 *
 */
public interface MappingList extends EList<Mapping> {

	/**
	 * Find a mapping for a given node origin and image.
	 * @param origin Node origin.
	 * @param image Node image.
	 * @return The mapping if found, otherwise <code>null</code>.
	 */
	Mapping get(Node origin, Node image);

	/**
	 * Create and add a new mapping between the origin and image node.
	 * @param origin Origin node.
	 * @param image Image node.
	 * @return The newly created mapping.
	 */
	Mapping add(Node origin, Node image);

	/**
	 * Remove a mapping between the given origin and image node.
	 * @param origin Origin node.
	 * @param image Image node.
	 * @return The removed mapping.
	 */
	Mapping remove(Node origin, Node image);
		
	/**
	 * Find the origin of a node.
	 * @param image Image node.
	 * @return The origin of the node.
	 */
	Node getOrigin(Node image);

	/**
	 * Find the origin of an edge.
	 * @param image Image edge.
	 * @return Edge image.
	 */
	Edge getOrigin(Edge image);

	/**
	 * Get the origin of an attribute.
	 * @param image Image attribute.
	 * @return The origin attribute.
	 */
	Attribute getOrigin(Attribute image);

	/**
	 * Get the origin of an untyped object. This delegates to
	 * {@link #getOrigin(Node)}, {@link #getOrigin(Edge)} or
	 * {@link #getOrigin(Attribute)}. It throws an 
	 * {@link IllegalArgumentException} if the type of the
	 * object is unknown.
	 * @param object Image.
	 * @return The origin.
	 */
	<T> T getOrigin(T object);

	/**
	 * Find the image of a node in a given target graph.
	 * @param origin Origin node.
	 * @param imageGraph Image graph.
	 * @return The image of the node.
	 */
	Node getImage(Node origin, Graph imageGraph);

	/**
	 * Find the image of an edge.
	 * @param origin Origin edge.
	 * @param imageGraph Image graph.
	 * @return Edge image.
	 */
	Edge getImage(Edge origin, Graph imageGraph);

	/**
	 * Get the image of an attribute.
	 * @param origin Origin attribute.
	 * @param imageGraph Image graph.
	 * @return The image attribute.
	 */
	Attribute getImage(Attribute origin, Graph imageGraph);

	/**
	 * Get the image of an untyped object. This delegates to
	 * {@link #getImage(Node)}, {@link #getImage(Edge)} or
	 * {@link #getImage(Attribute)}. It throws an 
	 * {@link IllegalArgumentException} if the type of the
	 * object is unknown.
	 * @param origin Origin.
	 * @param imageGraph Image graph.
	 * @return The image.
	 */
	<T> T getImage(T origin, Graph imageGraph);

	/**
	 * Remove all invalid mappings.
	 */
	void removeInvalid();
	
}
