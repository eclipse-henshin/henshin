/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.multicda.cpa.result;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Action.Type;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair.AppliedAnalysis;

/**
 * This class stores all the <code>CriticalPair</code>s as a result of a
 * critical pair analysis.
 * 
 * @author Florian Heﬂ, Kristopher Born
 *
 */
public class CPAResult implements Iterable<CriticalPair> {

	/**
	 * List of the critical pairs.
	 */
	private List<CriticalPair> essentialCriticalPairs;
	private List<CriticalPair> criticalPairs;

	/**
	 * Default constructor.
	 */
	public CPAResult() {
		essentialCriticalPairs = new ArrayList<CriticalPair>();
		criticalPairs = new ArrayList<CriticalPair>();
	}

	/**
	 * Adds a critical pair to this result set.
	 * 
	 * @param criticalPair
	 *            a critical pair which will be added to the result.
	 */
	public void addResult(CriticalPair criticalPair) {
		if (criticalPair.getAppliedAnalysis() == AppliedAnalysis.ESSENTIAL)
			essentialCriticalPairs.add(criticalPair);
		else
			criticalPairs.add(criticalPair);
	}

	/**
	 * Returns an iterator over essential critical pairs first, than the other critical pairs of the result set in proper
	 * sequence.
	 */
	public Iterator<CriticalPair> iterator() {
		return new Iterator<CriticalPair>() {
			Iterator<CriticalPair> cp = essentialCriticalPairs.iterator();
			boolean first = true;

			@Override
			public boolean hasNext() {
				if (cp.hasNext())
					return true;
				else if (first) {
					cp = criticalPairs.iterator();
					first = false;
					return hasNext();
				}
				return false;
			}

			@Override
			public CriticalPair next() {
				return cp.next();
			}
		};

	}

	/**
	 * Returns the list of critical pairs.
	 * 
	 * @return The list of critical pairs.
	 */
	public List<CriticalPair> getCriticalPairs() {
		List<CriticalPair> result = new ArrayList<>(essentialCriticalPairs);
		result.addAll(criticalPairs);
		return result;
	}

	/**
	 * Returns the list of critical pairs.
	 * 
	 * @return The list of critical pairs.
	 */
	public List<CriticalPair> getEssentialCriticalPairs() {
		return essentialCriticalPairs;
	}

	/**
	 * Returns the list of critical pairs.
	 * 
	 * @return The list of critical pairs.
	 */
	public List<CriticalPair> getOtherCriticalPairs() {
		return criticalPairs;
	}

	/**
	 * Returns the list of critical pairs.
	 * 
	 * @return The list of critical pairs.
	 */
	public List<CriticalPair> getInitialCriticalPairs() {
		return getInitialCriticalPairs(false);
	}

	public List<CriticalPair> getInitialCriticalPairs(boolean removeFromEssential) {
		List<CriticalPair> result = new ArrayList<>();
		Set<CriticalPair> toRemove = new HashSet<CriticalPair>();
		for (CriticalPair pair : essentialCriticalPairs) {
			// Only retain those CPs without isolated boundary nodes.
			// Since the set of isolated boundary nodes is hard to compute
			// directly
			// based on the available data structures, we just compute their
			// number instead.
			Set<Node> boundaryNodes = new HashSet<Node>();
			Set<Node> criticalBoundaryNodes = new HashSet<Node>();
			for (CriticalElement el : pair.getCriticalElements()) {
				if (el.elementInFirstRule instanceof Edge) {
					Edge edge = (Edge) el.elementInFirstRule;
					if (edge.getSource().getActionNode().getAction().getType() == Type.PRESERVE)
						boundaryNodes.add(edge.getSource());
					if (edge.getTarget().getActionNode().getAction().getType() == Type.PRESERVE)
						boundaryNodes.add(edge.getTarget());
				} else if (el.elementInFirstRule instanceof Node) {
					Node node = (Node) el.elementInFirstRule;
					if (node.getActionNode().getAction().getType() == Type.PRESERVE)
						criticalBoundaryNodes.add(node);
				}
			}
			int boundaryNodeCount = boundaryNodes.size() - criticalBoundaryNodes.size();

			EPackage overlap = (EPackage) pair.getMinimalModel();
			int overlapSize = overlap.getEClassifiers().size();
			if (pair.getCriticalElements().get(0).elementInFirstRule != null
					&& pair.getCriticalElements().get(0).elementInSecondRule != null) {
				int graph1Size = pair.getCriticalElements().get(0).elementInFirstRule.getGraph().getNodes().size();
				int graph2Size = pair.getCriticalElements().get(0).elementInSecondRule.getGraph().getNodes().size();
				Set<CriticalElement> criticalNodes = pair.getCriticalElements().stream()
						.filter(c -> c.elementInFirstRule instanceof Node).collect(Collectors.toSet());
				int criticalNodeCount = criticalNodes.size();

				int isolatedBoundaryNodeCount = graph1Size + graph2Size - overlapSize - criticalNodeCount
						- boundaryNodeCount;
				if (isolatedBoundaryNodeCount == 0) {
					result.add(pair);
					toRemove.add(pair);
				}
			}
		}
		if (removeFromEssential)
			essentialCriticalPairs.removeAll(toRemove);
		return result;
	}
	@Override
	public String toString() {
		return getCriticalPairs().toString();
	}
}
