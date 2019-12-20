package org.eclipse.emf.henshin.variability.mergein.clustering.extension;

import java.util.Map;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroup;

public class ExtensionCloneRelation {

	/**
	 * A clone group is an extension of another clone group if includes
	 * all mappings of the other clone group and the same or a subset of its rules.
	 * @param basis
	 * @param extension
	 * @return
	 */
	public static boolean isExtensionClone(CloneGroup basis,
			CloneGroup extension) {
		if (basis.getSize() < extension.getSize() &&
				basis.getRules().containsAll(extension.getRules())
//				&& 				extension.getRules().containsAll(basis.getRules()) 
				&& containsMapping(basis, extension)) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean containsMapping(CloneGroup subCloneCandidate,
			CloneGroup superClone) {
		for (Node node : subCloneCandidate.getNodeMappings().keySet()) {
			if (superClone.getRules().contains(node.getGraph().getRule())) {
				Map<Rule, Node> innerMap = subCloneCandidate.getNodeMappings()
						.get(node);
				Map<Rule, Node> innerMapSuper = superClone.getNodeMappings()
						.get(node);
				if (innerMapSuper == null) {
					return false;
				}
				for (Rule rule : subCloneCandidate.getNodeMappings().get(node)
						.keySet()) {
					if (superClone.getRules().contains(rule)) {

						if (innerMap.get(rule) != innerMapSuper.get(rule)) {
							return false;
						}
					}
				}
			}
		}

		for (Edge edge : subCloneCandidate.getEdgeMappings().keySet()) {
			if (superClone.getRules().contains(edge.getGraph().getRule())) {
				Map<Rule, Edge> innerMap = subCloneCandidate.getEdgeMappings()
						.get(edge);
				Map<Rule, Edge> innerMapSuper = superClone.getEdgeMappings()
						.get(edge);
				if (innerMapSuper == null) {
					return false;
				}
				for (Rule rule : subCloneCandidate.getEdgeMappings().get(edge)
						.keySet()) {
					if (superClone.getRules().contains(rule)) {

						if (innerMap.get(rule) != innerMapSuper.get(rule)) {
							return false;
						}
					}
				}
			}

		}

		for (Attribute attribute : subCloneCandidate.getAttributeMappings()
				.keySet()) {
			if (superClone.getRules().contains(attribute.getGraph().getRule())) {
				Map<Rule, Attribute> innerMap = subCloneCandidate
						.getAttributeMappings().get(attribute);
				Map<Rule, Attribute> innerMapSuper = superClone
						.getAttributeMappings().get(attribute);
				if (innerMapSuper == null) {
					return false;
				}
				for (Rule rule : subCloneCandidate.getAttributeMappings()
						.get(attribute).keySet()) {
					if (superClone.getRules().contains(rule)) {

						if (innerMap.get(rule) != innerMapSuper.get(rule)) {
							return false;
						}
					}
				}

			}

		}
		return true;
	}


}
