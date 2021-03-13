/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.gc2ac.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import org.eclipse.emf.henshin.ocl2ac.gc2ac.util.RuleClassifier;
import org.eclipse.emf.henshin.ocl2ac.ocl2gc.util.JointPairs;
import morphisms.Pair;
import nestedcondition.NestedCondition;
import nestedcondition.NestedConstraint;

/**
 * 
 * This class is for removing unwanted graphs from the overlap results without changing the goal of preserving the validity of the constraint.
 *
 */
public class OverlapOptimizer {

	private Rule rule;
	private NestedConstraint constraint;
	private NestedCondition condition;
	private Set<Pair> overlapPairs;

	public OverlapOptimizer(Rule rule, NestedConstraint constraint, Set<Pair> pairs) {
		this.rule = rule;
		this.constraint = constraint;
		this.overlapPairs = pairs;
	}

	/**
	 * 
	 */
	public void optimizeOverlap() {
		NestedConditionPreparer preparer = new NestedConditionPreparer(constraint);
		preparer.eliminateForAllANotExistsC();
		condition = preparer.getCondition();
		if (preparer.isOfFormNotExistsC(condition)) {
			System.out.println("-- The size of the overlap pairs is: " + overlapPairs.size());
			if (overlapPairs.size() > 0)
				removeThePushoutGraph();
			System.out.println(
					"-- After removing the pushout graph: The size of the overlap pairs is: " + overlapPairs.size());
			if (overlapPairs.size() > 0)
				removeGraphbeingOnlyOverlappedWithPreservedElements();
			System.out.println(
					"-- After removing the graph overlapped with only preserved elements: The size of the overlap pairs is: "
							+ overlapPairs.size());
		}
	}

	/**
	 * 
	 */
	private void removeThePushoutGraph() {
		Pair pushoutPair = JointPairs.getPushout();
		System.out.println("The pushout graph is removed.");
		System.out.println("PushoutNodes: " + pushoutPair.getA().getCodomain().getNodes());
		overlapPairs.remove(pushoutPair);
	}

	/**
	 * 
	 */
	private void removeGraphbeingOnlyOverlappedWithPreservedElements() {
		RuleClassifier rc = new RuleClassifier(rule);
		if (rc.preserveActionNodes.size() > 0) {
			// getting the created and deleted nodes by the rule
			EList<Node> crtDelnodes = new BasicEList<Node>();
			crtDelnodes.addAll(rc.createActionNodes);
			crtDelnodes.addAll(rc.deleteActionNodes);
			EList<Edge> createActionEdges = rc.createActionEdges;
			// getting the names of the created and deleted nodes by the rule
			ArrayList<String> nodeNames = new ArrayList<String>();
			if (crtDelnodes.size() > 0) {
				for (int i = 0; i < crtDelnodes.size(); i++) {
					Node node = crtDelnodes.get(i);
					if (node.getName() != null) {
						nodeNames.add(node.getName());
					}
				}
			}
			// If there is overlap with a one of the created or deleted node, we
			// do not remove the graph.
			boolean pairToremoved = false;
			Iterator<Pair> ite = overlapPairs.iterator();
			while (ite.hasNext()) {
				Pair p = ite.next();
				pairToremoved = true;
				if (crtDelnodes.size() > 0) {
					for (graph.Node node : p.getA().getCodomain().getNodes()) {
						if (node.getName().contains("=")) {
							for (String name : nodeNames) {
								if (node.getName().contains(name)) {
									pairToremoved = false;
									break;
								}
							}
						}
						if (pairToremoved == false)
							break;
					}
				}
				if (pairToremoved) {
					// check if there is overlap with created edges
					for (graph.Edge e : p.getA().getCodomain().getEdges()) {
						if (e.getSource().getName().contains("=") && e.getTarget().getName().contains("=")) {
							System.out.println("The src and tgt nodes of the edge of type " + e.getType().getName()
									+ " are merged.");
							if (containsEdgesWithTheSameName(createActionEdges, e)) {
								System.out.println("There is an overlap with a created edge of type "
										+ e.getType().getName() + ".");
								pairToremoved = false;
								break;
							}
						}
					}
					if (pairToremoved) {
						System.out.println("Removed: " + p.getA().getCodomain().getNodes());
						ite.remove();
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param hesnhinEdges
	 * @param edge
	 * @return
	 */
	private boolean containsEdgesWithTheSameName(EList<Edge> hesnhinEdges, graph.Edge edge) {
		if (hesnhinEdges == null || hesnhinEdges.size() == 0)
			return false;
		for (org.eclipse.emf.henshin.model.Edge henEdge : hesnhinEdges) {
			if (henEdge.getType().getName() == edge.getType().getName())
				return true;
		}
		return false;
	}

}
