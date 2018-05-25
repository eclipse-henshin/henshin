package org.eclipse.emf.henshin.multicda.cda;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;

public class CospanMappingToMaps {

	public Map<Node, Node> rule1ToG = new HashMap<Node, Node>();
	public Map<Node, Node> gToRule1 = new HashMap<Node, Node>();
	public Map<Node, Node> rule2ToG = new HashMap<Node, Node>();
	public Map<Node, Node> gToRule2 = new HashMap<Node, Node>();

	public CospanMappingToMaps(List<Mapping> mappingOfRule1InOverlapG, List<Mapping> mappinqgOfRule2InOverlapG) {

		for (Mapping mapping : mappingOfRule1InOverlapG) {
			rule1ToG.put(mapping.getOrigin(), mapping.getImage());
			gToRule1.put(mapping.getImage(), mapping.getOrigin());
		}
		for (Mapping mapping : mappinqgOfRule2InOverlapG) {
			rule2ToG.put(mapping.getOrigin(), mapping.getImage());
			gToRule2.put(mapping.getImage(), mapping.getOrigin());
		}
	}

}
