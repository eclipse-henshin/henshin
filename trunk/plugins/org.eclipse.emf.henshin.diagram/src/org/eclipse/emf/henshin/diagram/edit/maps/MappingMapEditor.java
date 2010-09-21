package org.eclipse.emf.henshin.diagram.edit.maps;

import java.util.List;

import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * A map editor for mappings. Used for amalgamations.
 * This does not implement {@link MapEditor}.
 * @author Christian Krause
 */
public class MappingMapEditor {
	
	// Source and target rule.
	private Rule source, target;
	
	// LHS and RHS mappings.
	private List<Mapping> lhsMappings, rhsMappings;
	
	public MappingMapEditor(Rule source, Rule target, 
			List<Mapping> lhsMappings, List<Mapping> rhsMappings) {
		this.source = source;
		this.target = target;
		this.lhsMappings = lhsMappings;
		this.rhsMappings = rhsMappings;
	}
	
	public void moveMappedNode(Node node) {
		Node opposite = getOppositeNode(node);
		if (node.getGraph().getContainerRule()==source) {
			//new NodeMapEditor(source.getLhs(), target.getLhs(), lhsMappings).move();
		}
	}
	
	public void moveMappedElement(Object element) {
		if (element instanceof Node) {
			moveMappedNode((Node) element);
		}
	}
	
	private Node getOppositeNode(Node node) {
		if (node.getGraph().getContainerRule()==source) {
			return new NodeMapEditor(source.getRhs()).getOpposite(node);
		} else if (node.getGraph().getContainerRule()==target) {
			return new NodeMapEditor(target.getRhs()).getOpposite(node);
		} else {
			return null;
		}
	}
	
}
