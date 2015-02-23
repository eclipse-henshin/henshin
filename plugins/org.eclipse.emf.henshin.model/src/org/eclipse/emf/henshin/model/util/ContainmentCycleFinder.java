package org.eclipse.emf.henshin.model.util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;

/**
 * Utility for the exploring of containment cycles in a {@link Graph} by a depth
 * first search
 * 
 * @author Kristopher Born, Daniel Strüber, Christian Krause
 *
 */
public class ContainmentCycleFinder {

	private List<List<Edge>> cycles;

	/**
	 * Searches for containment cycles in the graph.
	 * 
	 * @param graph
	 *            Graph to be searched for containment cycles
	 * @return A list of cycles.
	 */
	public List<List<Edge>> findContainmentCycles(Graph graph) {
		cycles = new LinkedList<List<Edge>>();
		for (Node node : graph.getNodes()) {
			searchContainmentCyclesRevursivelyDepthFirst(node,
					new LinkedList<Edge>());
		}
		return cycles;
	}

	/**
	 * Searches for containment cycles from the <code>currentNode</code> by
	 * recursive calling the method itself.
	 * 
	 * @param currentNode
	 *            The starting node to search for cycles.
	 * @param visitedEdges
	 *            Already passed edges to reach the current node.
	 */
	@SuppressWarnings("unchecked")
	private void searchContainmentCyclesRevursivelyDepthFirst(Node currentNode,
			LinkedList<Edge> visitedEdges) {
		// assemble relevant edges, i.e., outgoing containment or ingoing
		// container edges
		Set<Edge> relevantEdges = new HashSet<Edge>();
		for (Edge e : currentNode.getOutgoing()) {
			if (e.getType() != null && e.getType().isContainment())
				relevantEdges.add(e);
		}
		for (Edge e : currentNode.getIncoming()) {
			if (e.getType() != null && e.getType().isContainer())
				relevantEdges.add(e);
		}

		for (Edge edge : relevantEdges) {
			// a cycle is detected when the edge has already been passed in the
			// history
			if (visitedEdges.contains(edge)) {
				// cut out the found cycle and add it to the set of cycles
				extractCycle(visitedEdges, edge);
				return;
			}
			if (!visitedEdges.contains(edge)) {
				Node nextNode = (currentNode == edge.getSource() ) ? edge
						.getTarget() : edge.getSource();
				visitedEdges.add(edge);
				searchContainmentCyclesRevursivelyDepthFirst(nextNode,
						(LinkedList<Edge>) visitedEdges.clone());
			}
		}
	}

	/**
	 * This method extracts the cycle out of the history of passed edges and
	 * adds it to the cycles container.
	 * 
	 * @param pathOfEdges
	 *            History of passed edges by the exploration of the cycle
	 * @param startingEdge
	 *            Edge where the cycle begins and ends with the last checked
	 *            edge
	 */
	private void extractCycle(LinkedList<Edge> pathOfEdges, Edge startingEdge) {
		List<Edge> cycle = pathOfEdges.subList(
				pathOfEdges.indexOf(startingEdge), pathOfEdges.size());

		// prevent to add a cycle twice
		for (List<Edge> allreadyKnownCycle : cycles) {
			if (allreadyKnownCycle.size() == cycle.size()) {
				if (allreadyKnownCycle.containsAll(cycle))
					return;
			}
		}
		cycles.add(cycle);
	}

}
