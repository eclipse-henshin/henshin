package org.eclipse.emf.henshin.variability.mergein.conqat;

import org.conqat.engine.model_clones.model.IDirectedEdge;
import org.conqat.engine.model_clones.model.IModelGraph;
import org.conqat.engine.model_clones.model.INode;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinEdge;

/**
 * Represents a Henshin Edge in the {@link IModelGraph} graph representation required by
 * ConQAT.
 * 
 * @author strueber
 *
 */
public class ConqatEdge implements IDirectedEdge {
	private INode sourceNode;

	public ConqatEdge(HenshinEdge edge, INode sourceNode, INode targetNode) {
		this.sourceNode = sourceNode;
		this.targetNode = targetNode;
		this.label = computeLabel(edge);
	}

	private String computeLabel(HenshinEdge edge) {
		StringBuilder sb = new StringBuilder();
		sb.append(edge.getType().getName());
		sb.append(' ');
		sb.append(edge.getActionType().name());
		sb.append(' ');
		sb.append(edge.getRuleName());
		return sb.toString();
	}

	private INode targetNode;
	private String label;

	@Override
	public String getEquivalenceClassLabel() {
		return label;
	}

	@Override
	public INode getSourceNode() {
		return sourceNode;
	}

	@Override
	public INode getTargetNode() {
		return targetNode;
	}

}
