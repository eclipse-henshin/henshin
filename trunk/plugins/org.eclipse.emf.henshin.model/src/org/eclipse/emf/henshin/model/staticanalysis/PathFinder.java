package org.eclipse.emf.henshin.model.staticanalysis;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;

public class PathFinder {

	public static List<List<EReference>> findReferencePaths(Node source, Node target, boolean withPACs, boolean onlyPACs) {
		Set<List<EReference>> reflists = new LinkedHashSet<List<EReference>>();
		for (Path path : findEdgePaths(source, target, true, withPACs)) {
			if (onlyPACs && !path.isViaNestedCondition()) {
				continue;
			}
			List<EReference> list = path.toReferenceList(true);
			if (list!=null) {
				reflists.add(list);
			}
		}
		return new ArrayList<List<EReference>>(reflists);
	}
	
	public static List<Path> findEdgePaths(Node source, Node target, boolean withInverse, boolean withPACs) {
		
		Graph graph = source.getGraph();
		
		Path initial = new Path();
		initial.nodes.add(source);
		List<Path> tempPaths = new ArrayList<Path>(); 
		tempPaths.add(initial);
		List<Path> finalPaths = new ArrayList<Path>(); 

		while (!tempPaths.isEmpty()) {
			List<Path> newTempPaths = new ArrayList<Path>(); 
			for (Path path : tempPaths) {
				Node last = path.lastNode();
				
				List<Path> steps = doStep(last, withInverse);
				if (withPACs) {
					for (NestedCondition pac : graph.getPACs()) {
						Node image = pac.getMappings().getImage(last, pac.getConclusion());
						if (image!=null) {
							steps.addAll(doStep(image, withInverse));
						}
					}
				}
				
				for (Path step : steps) {
					step.retract();
					Path extended = path.copy();
					extended.append(step);
					if (extended.isCyclic()) {
						continue;
					}
					if (extended.lastNode()==target) {
						finalPaths.add(extended);
					} else {
						newTempPaths.add(extended);
					}
				}
				
			}
			tempPaths = newTempPaths;
		}
		return finalPaths;
	}
		
	private static List<Path> doStep(Node node, boolean withInverse) {
		List<Path> paths = new ArrayList<Path>();
		for (Edge outgoing : node.getOutgoing()) {
			Path path = new Path();
			path.nodes.add(node);
			path.edges.add(outgoing);
			path.nodes.add(outgoing.getTarget());
			paths.add(path);
		}
		if (withInverse) {
			for (Edge incoming : node.getIncoming()) {
				Path path = new Path();
				path.nodes.add(node);
				path.edges.add(incoming);
				path.nodes.add(incoming.getSource());
				paths.add(path);
			}
		}
		return paths;
	}
	
}
