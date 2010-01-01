package org.eclipse.emf.henshin.model.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Common utility methods for {@link Mapping}s.
 * @generated NOT
 * @author Christian Krause
 */
public class MappingUtil {
	
	/**
	 * Find all images of a given {@link Node}. This method assumes that the argument
	 * {@link Node} is properly contained in a {@link Graph} and that this {@link Graph} 
	 * is the left-hand side of a {@link Rule}.
	 * @param node Node.
	 * @return List of image nodes.
	 */
	public static List<Node> getImages(Node node) {
		
		// We assume the node is properly contained in a rule:
		Rule rule = (Rule) node.getGraph().eContainer();
		
		// Collect all images:
		List<Node> images = new ArrayList<Node>();
		for (Mapping mapping : rule.getMappings()) {
			if (mapping.getOrigin()==node) {
				images.add(mapping.getImage());
			}
		}
		return images;
		
	}

	/**
	 * Find the origin of a given {@link Node}. This method assumes that the argument
	 * {@link Node} is properly contained in a {@link Graph} and that this {@link Graph} 
	 * is either the right-hand side or a negative application condition of a {@link Rule}.
	 * @param node Node.
	 * @return Node origin.
	 */
	public static Node getOrigin(Node node) {
		
		// We assume the node is properly contained in a rule:
		Rule rule = (Rule) node.getGraph().eContainer();
		
		// Find the node origin:
		for (Mapping mapping : rule.getMappings()) {
			if (mapping.getImage()==node) {
				return mapping.getOrigin();
			}
		}
		return null;
		
	}

}
