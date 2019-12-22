package org.eclipse.emf.henshin.variability.mergein.conqat;

import java.util.HashMap;
import java.util.Map;

import org.conqat.engine.model_clones.model.IDirectedEdge;
import org.conqat.engine.model_clones.model.INode;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinEdge;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinNode;

public class HenshinToConqatGraphElementMap {
	private Map<HenshinNode, INode> henshinNodeToINodeMap = new HashMap<HenshinNode, INode>();
	private Map<INode, HenshinNode> iNodeToHenshinNodeMap = new HashMap<INode, HenshinNode>();
	private Map<HenshinEdge, IDirectedEdge> henshinEdgeToIEdgeMap = new HashMap<HenshinEdge, IDirectedEdge>();
	private Map<IDirectedEdge, HenshinEdge> iEdgeToHenshinEdgeMap = new HashMap<IDirectedEdge, HenshinEdge>();
	
	public void put(HenshinNode henshinNode, INode iNode) {
		henshinNodeToINodeMap.put(henshinNode, iNode);
		iNodeToHenshinNodeMap.put(iNode, henshinNode);
	}
	
	public void put(INode iNode, HenshinNode henshinNode) {
		henshinNodeToINodeMap.put(henshinNode, iNode);
		iNodeToHenshinNodeMap.put(iNode, henshinNode);
	}
	
	public HenshinNode get(INode node) {
		return iNodeToHenshinNodeMap.get(node);
	}
	
	public INode get(HenshinNode node) {
		return henshinNodeToINodeMap.get(node);
	}
	
	public boolean contains(HenshinNode node) {
		return iNodeToHenshinNodeMap.containsKey(node);
	}

	public boolean contains(INode node) {
		return henshinNodeToINodeMap.containsKey(node);
	}
	
	public void put(HenshinEdge henshinEdge, IDirectedEdge iEdge) {
		henshinEdgeToIEdgeMap.put(henshinEdge, iEdge);
		iEdgeToHenshinEdgeMap.put(iEdge, henshinEdge);
	}
	
	public void put(IDirectedEdge iEdge, HenshinEdge henshinEdge) {
		henshinEdgeToIEdgeMap.put(henshinEdge, iEdge);
		iEdgeToHenshinEdgeMap.put(iEdge, henshinEdge);
	}
	
	public HenshinEdge get(IDirectedEdge edge) {
		return iEdgeToHenshinEdgeMap.get(edge);
	}
	
	public IDirectedEdge get(HenshinEdge edge) {
		return henshinEdgeToIEdgeMap.get(edge);
	}
	
	public boolean contains(HenshinEdge edge) {
		return iEdgeToHenshinEdgeMap.containsKey(edge);
	}

	public boolean contains(IDirectedEdge edge) {
		return henshinEdgeToIEdgeMap.containsKey(edge);
	}
}
