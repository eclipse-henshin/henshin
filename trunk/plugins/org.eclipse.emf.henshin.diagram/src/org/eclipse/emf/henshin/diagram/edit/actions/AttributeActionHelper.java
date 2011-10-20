package org.eclipse.emf.henshin.diagram.edit.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.diagram.edit.maps.AttributeMapEditor;
import org.eclipse.emf.henshin.diagram.edit.maps.MapEditor;
import org.eclipse.emf.henshin.diagram.edit.maps.NodeMapEditor;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinNCUtil;

/**
 * @generated NOT
 */
public class AttributeActionHelper extends GenericActionHelper<Attribute,Node> {
	
	/**
	 * Static instance.
	 */
	public static final AttributeActionHelper INSTANCE = new AttributeActionHelper();
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.edit.actions.ActionHelper#getActionElements(java.lang.Object, org.eclipse.emf.henshin.diagram.edit.actions.Action)
	 */
	public List<Attribute> getActionElements(Node node, Action action) {		
		
		// Compute list of candidates:
		Rule rule = node.getGraph().getContainerRule();
		List<Attribute> candidates = new ArrayList<Attribute>();
		
		// Attributes in the LHS:
		Node lhsNode = NodeActionHelper.INSTANCE.getLhsNode(node);
		if (lhsNode!=null) {
			candidates.addAll(lhsNode.getAttributes());

			// Attributes in the RHS:
			Node rhsNode = new NodeMapEditor(rule.getRhs()).getOpposite(lhsNode);
			if (rhsNode!=null) {
				candidates.addAll(rhsNode.getAttributes());
			}

			// Attributes in the NACs:
			for (NestedCondition nac : HenshinNCUtil.getAllNACs(rule)) {
				Node nacNode = new NodeMapEditor(nac.getConclusion()).getOpposite(lhsNode);
				if (nacNode!=null) {
					candidates.addAll(nacNode.getAttributes());
				}
			}
		} else {
			candidates.addAll(node.getAttributes());
		}
		
		// Filter by action:
		return filterElementsByAction(candidates, action);
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.edit.actions.AbstractActionHelper#getMapEditor(org.eclipse.emf.henshin.model.Graph)
	 */
	@Override
	protected MapEditor<Attribute> getMapEditor(Graph target) {
		return new AttributeMapEditor(target);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.edit.actions.AbstractActionHelper#getMapEditor(org.eclipse.emf.henshin.model.Graph, org.eclipse.emf.henshin.model.Graph, java.util.List)
	 */
	@Override
	protected MapEditor<Attribute> getMapEditor(Graph source, Graph target, List<Mapping> mappings) {
		return new AttributeMapEditor(source, target, mappings);
	}

}
