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
	 * Find the image of a node in a given target graph.
	 * @param origin Origin node.
	 * @param targetGraph Target graph.
	 * @return The image of the node.
	 */
	Node getImage(Node origin, Graph targetGraph);
	
	/**
	 * Find the origin of a node.
	 * @param image Image node.
	 * @return The origin of the node.
	 */
	Node getOrigin(Node image);
	
	/**
	 * Find the image of an edge.
	 * @param origin Origin edge.
	 * @param targetGraph Graph the sought image is contained in.
	 * @return Edge image.
	 */
	Edge getImage(Edge origin, Graph targetGraph);

	/**
	 * Find the origin of an edge.
	 * @param image Image edge.
	 * @return Edge image.
	 */
	Edge getOrigin(Edge image);

	/**
	 * Get the image of an attribute.
	 * @param attribute Origin attribute.
	 * @param targetGraph Target graph.
	 * @return The image attribute.
	 */
	Attribute getImage(Attribute attribute, Graph targetGraph);

	/**
	 * Get the origin of an attribute.
	 * @param attribute Image attribute.
	 * @return The origin attribute.
	 */
	Attribute getOrigin(Attribute attribute);

	/**
	 * Remove all invalid mappings.
	 */
	void removeInvalid();
	
}
