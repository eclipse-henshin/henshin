package org.eclipse.emf.henshin.variability.mergein.clone;

import java.util.Collection;
import java.util.Comparator;

import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinAttributeNode;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinEdge;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinGraph;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinGraphElement;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinNode;
import org.jgrapht.alg.isomorphism.VF2GraphIsomorphismInspector;
import org.jgrapht.graph.DefaultDirectedGraph;

/**
 * Wraps JGraphT's VF2GraphIsomorphismInspector so that it also considered
 * multi-edges.
 * 
 * @author strueber
 *
 * @param <X>
 * @param <Y>
 */
public class JGraphTIsomorphyChecker {

	public boolean checkIsomorphyWithMultiEdges(Collection<HenshinGraph> clones) {
		HenshinGraph first = clones.iterator().next();
		for (HenshinGraph current : clones) {
			if (current != first) {
				if (!checkIsomorphyWithMultiEdges(first, current))
					return false;
			}
		}

		return true;
	}

	private boolean checkIsomorphyWithMultiEdges(HenshinGraph g1, HenshinGraph g2) {
		DefaultDirectedGraph<HenshinGraphElement, HenshinEdge> g1_ = new DefaultDirectedGraph<HenshinGraphElement, HenshinEdge>(
				HenshinEdge.class);
		DefaultDirectedGraph<HenshinGraphElement, HenshinEdge> g2_ = new DefaultDirectedGraph<HenshinGraphElement, HenshinEdge>(
				HenshinEdge.class);

		populate(g1_, g1);
		populate(g2_, g2);

		Comparator<HenshinGraphElement> nodeComparator = new Comparator<HenshinGraphElement>() {
			@Override
			public int compare(HenshinGraphElement o1, HenshinGraphElement o2) {
				if (o1 == null)
					return -1;
				if (o2 == null)
					return -1;
				if (o1 instanceof HenshinNode && o2 instanceof HenshinNode) {
					if (((HenshinNode) o1).getType() == ((HenshinNode) o2).getType()
							&& ((HenshinNode) o1).getActionType() == ((HenshinNode) o2).getActionType())
						return 0;
				} else if (o1 instanceof HenshinEdge && o2 instanceof HenshinEdge) {
					if (((HenshinEdge) o1).getType() == ((HenshinEdge) o2).getType()
							&& ((HenshinEdge) o1).getActionType() == ((HenshinEdge) o2).getActionType())
						return 0;
				} else if (o1 instanceof HenshinAttributeNode && o2 instanceof HenshinAttributeNode) {
					if (((HenshinAttributeNode) o1).getType() == ((HenshinAttributeNode) o2).getType()
							&& ((HenshinAttributeNode) o1).getActionType() == ((HenshinAttributeNode) o2)
									.getActionType()
							&& ((HenshinAttributeNode) o1).getValue().equals(((HenshinAttributeNode) o2).getValue()))
						return 0;
				}
				return -1;
			}
		};

		Comparator<HenshinEdge> edgeComparator = new Comparator<HenshinEdge>() {
			@Override
			public int compare(HenshinEdge o1, HenshinEdge o2) {
				if (o1 == null)
					return -1;
				if (o2 == null)
					return -1;
				if (((HenshinEdge) o1).getType() == ((HenshinEdge) o2).getType()
						&& ((HenshinEdge) o1).getActionType() == ((HenshinEdge) o2).getActionType())
					return 0;
				return -1;
			}
		};

		VF2GraphIsomorphismInspector<HenshinGraphElement, HenshinEdge> insp = new VF2GraphIsomorphismInspector<HenshinGraphElement, HenshinEdge>(
				g1_, g2_, nodeComparator, edgeComparator, false);
		return insp.isomorphismExists();
	}

	private void populate(DefaultDirectedGraph<HenshinGraphElement, HenshinEdge> g_, HenshinGraph g) {
		for (HenshinNode v1 : g.vertexSet()) {
			g_.addVertex(v1);
		}
		for (HenshinNode v1 : g.vertexSet()) {
			for (HenshinNode v2 : g.vertexSet()) {
				for (HenshinEdge e : g.getAllEdges(v1, v2)) {
					g_.addVertex(e);
					g_.addEdge(v1, e, e);
					g_.addEdge(e, v2, new HenshinEdge(e.getHenshinGraph(), e.getType(), e.getActionType(),
							e.getRuleName(), false));
				}
			}
		}
	}

}
