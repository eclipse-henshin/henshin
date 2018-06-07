package org.eclipse.emf.henshin.multicda.cda.computation;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.ReasonFactory;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.DeleteReadConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;

public class ConflictReasonComputation {

	static Action deleteAction = new Action(Action.Type.DELETE);
	static Action preserveAction = new Action(Action.Type.PRESERVE);
	static HenshinFactory henshinFactory = HenshinFactory.eINSTANCE;

	protected Rule rule1;
	protected Rule rule2;

	public ConflictReasonComputation(Rule rule1, Rule rule2) {
		this.rule1 = rule1;
		this.rule2 = rule2;
	}

	public Set<ConflictReason> computeConflictReasons() {
		Set<MinimalConflictReason> minimalReasons = new MinimalReasonComputation(rule1, rule2)
				.computeMinimalConflictReasons();
		return computeConflictReasons(minimalReasons);
	}

	public Set<ConflictReason> computeConflictReasons(Set<MinimalConflictReason> minimalConflictReasons) {
		Set<ConflictReason> result = new HashSet<>();
		for (MinimalConflictReason mcr : minimalConflictReasons) {
			Set<MinimalConflictReason> remainingMCR = new HashSet<>(minimalConflictReasons);
			remainingMCR.remove(mcr);

			result.addAll(computeConflictReasons(mcr, remainingMCR));

			ConflictReason singletonIR;
			singletonIR = ReasonFactory.eINSTANCE.createDRReason(mcr);
			result.add(singletonIR);
		}
		return result;
	}

	private Set<DeleteReadConflictReason> computeConflictReasons(Reason current, Set<MinimalConflictReason> remaining) {
		Set<DeleteReadConflictReason> result = new HashSet<>();
		for (MinimalConflictReason mcr : remaining) {
			if (!haveCommonDeletionElement(current, mcr)) {
				DeleteReadConflictReason initialReason = joinToNewInitialReason(current, mcr);
				if (initialReason != null) {
					result.add(initialReason);
					result.addAll(computeConflictReasons(initialReason, remaining));
				}
			}
		}
		return result;
	}

	private boolean haveCommonDeletionElement(Reason current, MinimalConflictReason extensionCandidate) {
		Set<GraphElement> deletionElementsCur = current.getDeletionElementsInRule1();
		Set<GraphElement> deletionElementsCand = extensionCandidate.getDeletionElementsInRule1();
		return !Collections.disjoint(deletionElementsCur, deletionElementsCand);
	}

	/**
	 * 
	 * @param span1
	 * @param span2
	 * @return a new InitialConflictReason or <code>null</code> if this ist not
	 *         possible
	 */
	private DeleteReadConflictReason joinToNewInitialReason(Reason span1, Reason span2) {
		Map<Node, Node> s2ToS1 = getS2toS1Map(span1, span2);
		if (s2ToS1 == null) // is null iff we cannot join them
			return null;

		// Copy G1 and its mappings to rules 1 and 2
		Copier g1ToCopy = new Copier();
		Graph graph1Copy = (Graph) g1ToCopy.copy(span1.getGraph());
		g1ToCopy.copyReferences();

		Copier mappingS1Copier = new Copier();
		Collection<Mapping> mappingsS1R1copies = mappingS1Copier.copyAll(span1.getMappingsInRule1());
		mappingS1Copier.copyReferences();
		Collection<Mapping> mappingS1R2copies = mappingS1Copier.copyAll(span1.getMappingsInRule2());
		mappingS1Copier.copyReferences();
		for (Mapping mapping : mappingsS1R1copies) {
			Node newOrigin = (Node) g1ToCopy.get(mapping.getOrigin());
			mapping.setOrigin(newOrigin);
		}
		for (Mapping mapping : mappingS1R2copies) {
			Node newOrigin = (Node) g1ToCopy.get(mapping.getOrigin());
			mapping.setOrigin(newOrigin);
		}

		// Copy G1 and its mappings to rules 1 and 2
		Copier g2toCopy = new Copier();
		Graph graph2Copy = (Graph) g2toCopy.copy(span2.getGraph());
		g2toCopy.copyReferences();

		// MAPPINGS of Graph2:
		Copier mappingsS2Copier = new Copier();
		Collection<Mapping> mappingsS2R1copies = mappingsS2Copier.copyAll(span2.getMappingsInRule1());
		mappingsS2Copier.copyReferences();
		Collection<Mapping> mappingS2R2copies = mappingsS2Copier.copyAll(span2.getMappingsInRule2());
		mappingsS2Copier.copyReferences();

		for (Mapping mapping : mappingsS2R1copies) {
			Node newOrigin = (Node) g2toCopy.get(mapping.getOrigin());
			mapping.setOrigin(newOrigin);
		}
		for (Mapping mapping : mappingS2R2copies) {
			Node newOrigin = (Node) g2toCopy.get(mapping.getOrigin());
			mapping.setOrigin(newOrigin);
		}

		// Identify redundant nodes in G2's copy, and reroute
		// their adjacent edges to G1's copy
		List<Node> toDeleteInG2Copy = new LinkedList<Node>();
		for (Edge edgeG2 : span2.getGraph().getEdges()) {
			Edge edgeG2Copy = (Edge) g2toCopy.get(edgeG2);
			if (s2ToS1.containsKey(edgeG2.getSource())) {
				Node nodeInGraph1 = s2ToS1.get(edgeG2.getSource());
				Node newSourceG1Copy = (Node) g1ToCopy.get(nodeInGraph1);
				toDeleteInG2Copy.add(edgeG2Copy.getSource());
				edgeG2Copy.setSource(newSourceG1Copy);
			}
			if (s2ToS1.containsKey(edgeG2.getTarget())) {
				Node nodeInGraph1 = s2ToS1.get(edgeG2.getTarget());
				Node newTargetG1Copy = (Node) g1ToCopy.get(nodeInGraph1);
				toDeleteInG2Copy.add(edgeG2Copy.getTarget());
				edgeG2Copy.setTarget(newTargetG1Copy);
			}
		}

		// Remove redundant nodes from G2's copy and their mappings into
		// rules 1 and 2
		removeRedundantNodes(graph2Copy, mappingsS2R1copies, mappingS2R2copies, toDeleteInG2Copy);

		// Add nodes, edges, and mappings to those of G1
		graph1Copy.getNodes().addAll(graph2Copy.getNodes());
		graph1Copy.getEdges().addAll(graph2Copy.getEdges());
		Set<Mapping> mappingsToR1 = new HashSet<Mapping>();
		mappingsToR1.addAll(mappingsS1R1copies);
		mappingsToR1.addAll(mappingsS2R1copies);
		Set<Mapping> mappingsToR2 = new HashSet<Mapping>();
		mappingsToR2.addAll(mappingS1R2copies);
		mappingsToR2.addAll(mappingS2R2copies);

		Set<Reason> originMCRs = new HashSet<>();
		if (span1 instanceof MinimalConflictReason) {
			originMCRs.add((MinimalConflictReason) span1);
		} else {
			originMCRs.addAll(span1.getOriginMCRs());
		}
		if (span2 instanceof MinimalConflictReason) {
			originMCRs.add((MinimalConflictReason) span2);
		} else {
			originMCRs.addAll(span1.getOriginMCRs());
		}
		DeleteReadConflictReason newInitialReason = new DeleteReadConflictReason(mappingsToR1, graph1Copy, mappingsToR2,
				originMCRs);

		return newInitialReason;
	}

	private void removeRedundantNodes(Graph graph2Copy, Collection<Mapping> mappingsS2Rule1Copies,
			Collection<Mapping> mappingsS2Rule2Copies, List<Node> toDeleteInG2Copy) {
		List<Mapping> toDeleteMappingsToRule1 = new LinkedList<Mapping>();
		for (Mapping mapS2ToRule1 : mappingsS2Rule1Copies) {
			if (toDeleteInG2Copy.contains(mapS2ToRule1.getOrigin())) {
				toDeleteMappingsToRule1.add(mapS2ToRule1);
			}
		}
		mappingsS2Rule1Copies.removeAll(toDeleteMappingsToRule1);

		List<Mapping> mappingsInRule2ToRemove = new LinkedList<Mapping>();
		for (Mapping mappingOfSpan2InRule2 : mappingsS2Rule2Copies) {
			if (toDeleteInG2Copy.contains(mappingOfSpan2InRule2.getOrigin())) {
				mappingsInRule2ToRemove.add(mappingOfSpan2InRule2);
			}
		}
		mappingsS2Rule2Copies.removeAll(mappingsInRule2ToRemove);
		graph2Copy.getNodes().removeAll(toDeleteInG2Copy);
	}

	private Map<Node, Node> getS2toS1Map(Reason span1, Reason span2) {
		Map<Node, Node> s2ToS1 = new HashMap<Node, Node>();
		for (Node n1 : span1.getGraph().getNodes()) {
			for (Node n2 : span2.getGraph().getNodes()) {
				if (n1.getType() == n2.getType()) {
					boolean sameImageInRule1 = (span1.getMappingIntoRule1(n1).getImage() == span2
							.getMappingIntoRule1(n2).getImage());
					boolean sameImageInRule2 = (span1.getMappingIntoRule2(n1).getImage() == span2
							.getMappingIntoRule2(n2).getImage());
					if (sameImageInRule1 && sameImageInRule2) {
						s2ToS1.put(n2, n1);
					} else if (sameImageInRule1 ^ sameImageInRule2) {
						return null;
					}
				}
			}
		}
		return s2ToS1;
	}

}
