package org.eclipse.emf.henshin.variability.mergein.conqat;

import org.conqat.engine.model_clones.model.IModelGraph;
import org.conqat.engine.model_clones.model.INode;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinNode;

/**
 * Represents a Henshin Node in the {@link IModelGraph} graph representation required by
 * ConQAT.
 * 
 * @author strueber
 *
 */
public class ConqatNode implements INode {
	String label;

	public ConqatNode(HenshinNode node) {
		label = computeLabel(node);
	}

	protected String computeLabel(HenshinNode node) {
		StringBuilder sb = new StringBuilder();
		if (node.getType() instanceof ENamedElement) {
			sb.append(((ENamedElement)node.getType()).getName());
			sb.append(' ');
		}
		sb.append(node.getActionType().name());
		sb.append(' ');
		sb.append(node.getRuleName());
		return sb.toString();
	}

	@Override
	public String getEquivalenceClassLabel() {
		return label;
	}

	@Override
	public int getWeight() {
		return 2;
	}

}
