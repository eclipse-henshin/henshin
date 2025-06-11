package org.eclipse.emf.henshin.variability.mergein.conqat;

import org.conqat.engine.model_clones.model.IModelGraph;
import org.conqat.engine.model_clones.model.INode;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinAttributeNode;

/**
 * Represents a Henshin Attribute in the {@link IModelGraph} graph representation required by
 * ConQAT.
 * 
 * @author strueber
 *
 */
public class ConqatAttributeNode extends ConqatNode implements INode {

	public ConqatAttributeNode(HenshinAttributeNode node) {
		super(node);
		label = computeAttributeNoteLabel(node);
	}
	
	protected String computeAttributeNoteLabel(HenshinAttributeNode node) {
		StringBuilder sb = new StringBuilder();
		sb.append(super.computeLabel(node));
		sb.append(' ');
		sb.append(node.getValue());
		sb.append(' ');
		sb.append(node.getRuleName());
		return sb.toString();
	}


	@Override
	public int getWeight() {
		return 2;
	}

}
