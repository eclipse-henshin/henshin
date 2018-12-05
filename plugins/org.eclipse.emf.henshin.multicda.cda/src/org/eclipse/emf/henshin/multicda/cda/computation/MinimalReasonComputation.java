package org.eclipse.emf.henshin.multicda.cda.computation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.interpreter.util.HenshinEGraph;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Action.Type;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.impl.MappingListImpl;
import org.eclipse.emf.henshin.multicda.cda.CospanMappingToMaps;
import org.eclipse.emf.henshin.multicda.cda.MapOfLSetEnumerator;
import org.eclipse.emf.henshin.multicda.cda.Pushout;
import org.eclipse.emf.henshin.multicda.cda.ReasonFactory;
import org.eclipse.emf.henshin.multicda.cda.Utils;
import org.eclipse.emf.henshin.multicda.cda.Utils.DanglingCase;
import org.eclipse.emf.henshin.multicda.cda.Utils.DanglingEdge;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;
import org.eclipse.emf.henshin.multicda.cda.units.Span;
import org.eclipse.emf.henshin.multicda.cda.units.SpanMappings;

public class MinimalReasonComputation {
	static Action deleteAction = new Action(Action.Type.DELETE);
	static Action preserveAction = new Action(Action.Type.PRESERVE);
	static HenshinFactory henshinFactory = HenshinFactory.eINSTANCE;
	Map<Span, Pushout> span2pushout = new HashMap<>();

	protected Rule rule1;
	protected Rule rule2;

	// Performance optimization: Remember spans which have been used to compute MCRs
	protected Set<Span> checked;

	public MinimalReasonComputation(Rule rule1, Rule rule2) {
		this.rule1 = rule1;
		this.rule2 = rule2;
		checked = new HashSet<>();
	}

	public Set<ConflictReason> computeMinimalConflictReasons() {
		Set<ConflictReason> result = new HashSet<>();
		Set<Atom> candidates = new AtomCandidateComputation(rule1, rule2).computeAtomCandidates();
		for (Atom candidate : candidates) {
			if (Utils.attributesCheck(candidate))
				computeMinimalConflictReasons(candidate, result);
		}
		return result;
	}

	public void computeMinimalConflictReasons(Span s1, Set<ConflictReason> result) {
		if (!checked.contains(s1)) {
			if (isMinReason(s1)) {
				result.add((ConflictReason) ReasonFactory.eINSTANCE.createMinimalReason(s1));
				if (result.size() > 1)
					for (Reason r : result)
						r.hashCode();
				return;
			}
			Set<ConflictReason> extendedSpans = findExtensions(s1);
			for (Reason extendedSpan : extendedSpans) {
				computeMinimalConflictReasons(extendedSpan, result);
			}
			checked.add(s1);
		}
	}

	private boolean isMinReason(Span s1) {
		Pushout pushoutResult = getPushout(s1);
		boolean rule1EmbeddingIsDanglingFree = Utils.findDanglingEdgesOfRule1(rule1, pushoutResult.getRule1Mappings())
				.isEmpty();
		return rule1EmbeddingIsDanglingFree;
	}

	private Pushout getPushout(Span s1) {
		Pushout result = span2pushout.get(s1);
		if (result == null) {
			result = new Pushout(rule1, s1, rule2);
			span2pushout.put(s1, result);
		}
		return result;
	}

	/**
	 * 
	 * @param rule1
	 * @param rule2
	 * @param poDangling
	 * @param s1
	 * @param mappingOfRule1InOverlapG
	 * @param mappingOfRule2InOverlapG
	 * @return A set of fixing edges, that is, edges in R1 suitable to act as a
	 *         counterpart for the dangling edge originating from R2.
	 */
	public Set<Edge> findFixingEdges(Rule rule1, Rule rule2, DanglingEdge poDangling, CospanMappingToMaps comaps,
			Span s1) {
		SpanMappings maps = new SpanMappings(s1);
		Node poDanglingSource = poDangling.getEdge().getSource();
		Node poDanglingTarget = poDangling.getEdge().getTarget();
		Node r1DanglingSource = comaps.gToRule1.get(poDanglingSource);
		Node r1DanglingTarget = comaps.gToRule1.get(poDanglingTarget);

		if (poDangling.danglingCase == DanglingCase.sourceDangling) {
			Set<Edge> r1Candidates = new HashSet<Edge>(r1DanglingTarget.getIncoming(poDangling.getEdge().getType()));
			return r1Candidates.stream().filter(e -> maps.rule1ToS1.get(e.getSource()) == null)
					.collect(Collectors.toSet());
		} else if (poDangling.danglingCase == DanglingCase.targetDangling) {
			Set<Edge> c1Candidates = new HashSet<Edge>(r1DanglingSource.getOutgoing(poDangling.getEdge().getType()));
			return c1Candidates.stream().filter(e -> maps.rule1ToS1.get(e.getTarget()) == null)
					.collect(Collectors.toSet());
		} else if (poDangling.danglingCase == DanglingCase.unspecifiedEdge) {
			Set<Edge> result = new HashSet<Edge>();
			Edge e = r1DanglingSource.getOutgoing(poDangling.getEdge().getType(), r1DanglingTarget);
			if (e != null && maps.getEdgeMappingsRule1S1().get(e) == null)
				result.add(e);
			return result;
		}
		throw new RuntimeException("Invalid state: neither source nor target were dangling in L1!");
	}

	private Set<ConflictReason> findExtensions(Span s1) {
		Pushout pushoutResult = getPushout(s1);
		CospanMappingToMaps cospanMappings = new CospanMappingToMaps(pushoutResult.getRule1Mappings(),
				pushoutResult.getRule2Mappings());
		Set<DanglingEdge> danglingEdges = Utils.findDanglingEdgesOfRule1(rule1, pushoutResult.getRule1Mappings());

		Map<DanglingEdge, Set<Edge>> fixingEdgeMap = new HashMap<>();
		for (DanglingEdge danglingEdge : danglingEdges) {
			Set<Edge> fixing = findFixingEdges(rule1, rule2, danglingEdge, cospanMappings, s1);
			if (!fixing.isEmpty()) {
				fixingEdgeMap.put(danglingEdge, fixing);
			} else {
				return new HashSet<>();
			}
		}
		Set<ConflictReason> extensions = new HashSet<>();
		extensions = enumerateExtensions(s1, fixingEdgeMap, cospanMappings);
		return extensions;

	}

	public Set<ConflictReason> enumerateExtensions(Span originalSpan, Map<DanglingEdge, Set<Edge>> fixingEdges,
			CospanMappingToMaps comaps) {
		Set<ConflictReason> extensions = new HashSet<>();
		for (DanglingEdge danglingEdge : fixingEdges.keySet()) {
			if (danglingEdge.getDanglingCase() == DanglingCase.sourceDangling
					|| danglingEdge.getDanglingCase() == DanglingCase.targetDangling) {
				enumerateNodeExtensions(originalSpan, danglingEdge, fixingEdges, comaps, extensions);
			} else if (danglingEdge.getDanglingCase() == DanglingCase.unspecifiedEdge) {
				enumerateEdgeExtensions(originalSpan, danglingEdge, fixingEdges.get(danglingEdge), extensions);
			}
		}
		return extensions;
	}

	private void enumerateEdgeExtensions(Span originalSpan, DanglingEdge danglingEdge, Set<Edge> set,
			Set<ConflictReason> extensions) {
		for (Edge fixingEdgeR1 : set) {
			ConflictReason mr = (ConflictReason) ReasonFactory.eINSTANCE.createMinimalReason(originalSpan);
			SpanMappings maps = new SpanMappings(mr);
			Node srcR1 = fixingEdgeR1.getSource();
			Node trgR1 = fixingEdgeR1.getTarget();
			Node srcS1 = maps.rule1ToS1.get(srcR1);
			Node trgS1 = maps.rule1ToS1.get(trgR1);
			Node srcR2 = maps.s1ToRule2.get(srcS1);
			Node trgR2 = maps.s1ToRule2.get(trgS1);

			Edge fixingR2 = srcR2.getOutgoing(fixingEdgeR1.getType(), trgR2);
			if (fixingR2 != null) {
				HenshinFactory.eINSTANCE.createEdge(srcS1, trgS1, fixingEdgeR1.getType());
				extensions.add(mr);
			}
		}

	}

	private void enumerateNodeExtensions(Span originalSpan, DanglingEdge danglingEdge,
			Map<DanglingEdge, Set<Edge>> fixingEdges, CospanMappingToMaps comaps, Set<ConflictReason> extensions) {
		// Choose an arbitrary fixing edge and use it to extend
		// the span with its source or target node. Additional fixing edges
		// might be consumed to fix additional dangling edges between the new
		// and old end of the fixing edge.
		Edge danglingEdgePO = danglingEdge.getEdge();
		for (Edge fixingEdgeR1 : fixingEdges.get(danglingEdge)) {
			ConflictReason span1 = (ConflictReason) ReasonFactory.eINSTANCE.createMinimalReason(originalSpan);
			SpanMappings maps = new SpanMappings(span1);
			Node srcR1 = fixingEdgeR1.getSource();
			Node trgR1 = fixingEdgeR1.getTarget();
			Node srcR2 = comaps.gToRule2.get(danglingEdgePO.getSource());
			Node trgR2 = comaps.gToRule2.get(danglingEdgePO.getTarget());
			Node srcS1 = maps.rule1ToS1.get(srcR1);
			Node trgS1 = maps.rule1ToS1.get(trgR1);

			boolean sourceDangling = true;
			if (srcS1 == null) {
				srcS1 = henshinFactory.createNode(span1.getGraph(), commonSubClass(srcR1, srcR2),
						srcR1.getName() + Reason.NODE_SEPARATOR + srcR2.getName());
				span1.mappingsInRule1.add(henshinFactory.createMapping(srcS1, srcR1));
				span1.mappingsInRule2.add(henshinFactory.createMapping(srcS1, srcR2));
			} else if (trgS1 == null) {
				sourceDangling = false;
				trgS1 = henshinFactory.createNode(span1.getGraph(), commonSubClass(trgR1, trgR2),
						trgR1.getName() + Reason.NODE_SEPARATOR + trgR2.getName());
				span1.mappingsInRule1.add(henshinFactory.createMapping(trgS1, trgR1));
				span1.mappingsInRule2.add(henshinFactory.createMapping(trgS1, trgR2));
			}

			// Treatment of conflict-atom edges
			EList<Node> preserveNodes = rule1.getActionNodes(new Action(Type.PRESERVE));
			if (preserveNodes.contains(fixingEdgeR1.getSource()) && preserveNodes.contains(fixingEdgeR1.getTarget())) {
				Map<Edge, Edge> entry = new HashMap<Edge, Edge>();
				entry.put(danglingEdgePO, fixingEdgeR1);
				createExtension(extensions, entry, span1);
			} else {// Treatment of conflict-atom nodes
				List<Map<Edge, Edge>> combinations = getFixingFamily(danglingEdgePO, sourceDangling, fixingEdges, span1,
						comaps);
				for (Map<Edge, Edge> combination : combinations) {
					createExtension(extensions, combination, span1);
				}

			}
		}
	}

	private EClass commonSubClass(Node node1, Node node2) {
		if (node1.getType() == node2.getType())
			return node1.getType();
		if (node1.getType().getEAllSuperTypes().contains(node2.getType()))
			return node1.getType();
		if (node2.getType().getEAllSuperTypes().contains(node1.getType()))
			return node2.getType();
		throw new RuntimeException("Incompatible types!");
	}

	private void createExtension(Set<ConflictReason> extensions, Map<Edge, Edge> combination, ConflictReason span) {
		SpanMappings maps = new SpanMappings(span);
		for (Edge fixingEdge : combination.values()) {
			Node src = maps.rule1ToS1.get(fixingEdge.getSource());
			Node trg = maps.rule1ToS1.get(fixingEdge.getTarget());
			boolean found = false;
			for (Edge edge : src.getGraph().getEdges())
				if (edge.getSource() == src && edge.getTarget() == trg) {
					found = true;
					break;
				}
			if (!found)
				henshinFactory.createEdge(src, trg, fixingEdge.getType());
		}
		extensions.add(span);
	}

	private List<Map<Edge, Edge>> getFixingFamily(Edge danglingEdgePO, boolean sourceDangling,
			Map<DanglingEdge, Set<Edge>> fixingEdges, Reason span1, CospanMappingToMaps comaps) {
		Map<Edge, Set<Edge>> theFixingEdges = flattenFixingSet(fixingEdges);
		SpanMappings maps = new SpanMappings(span1);
		List<Edge> brotherEdges = new ArrayList<Edge>();
		List<Edge> sisterEdges = new ArrayList<Edge>();
		Node srcPO = danglingEdgePO.getSource();
		Node trgPO = danglingEdgePO.getTarget();
		if (sourceDangling) {
			brotherEdges = trgPO.getIncoming().stream().filter(e -> e.getSource() == srcPO)
					.collect(Collectors.toList());
			sisterEdges = trgPO.getOutgoing().stream().filter(e -> e.getTarget() == srcPO).collect(Collectors.toList());
		} else {
			brotherEdges = srcPO.getIncoming().stream().filter(e -> e.getSource() == trgPO)
					.collect(Collectors.toList());
			sisterEdges = srcPO.getOutgoing().stream().filter(e -> e.getTarget() == trgPO).collect(Collectors.toList());
		}
		Set<Edge> danglingFamilyPO = Stream.concat(brotherEdges.stream(), sisterEdges.stream())
				.collect(Collectors.toSet());
		Map<Edge, Set<Edge>> result = new HashMap<Edge, Set<Edge>>();

		for (Edge toFixPO : danglingFamilyPO) {
			Set<Edge> potentialFixes = theFixingEdges.get(toFixPO);
			Set<Edge> viable = new HashSet<Edge>();
			Set<Node> requiredMemberNodes = new HashSet<Node>();
			Node srcToFix = toFixPO.getSource();
			Node trgToFix = toFixPO.getTarget();
			requiredMemberNodes.add(maps.s1ToRule1.get(maps.rule2ToS1.get(comaps.gToRule2.get(srcToFix))));
			requiredMemberNodes.add(maps.s1ToRule1.get(maps.rule2ToS1.get(comaps.gToRule2.get(trgToFix))));

			for (Edge e : potentialFixes) {
				Set<Node> memberNodes = new HashSet<Node>();
				memberNodes.add(e.getSource());
				memberNodes.add(e.getTarget());
				if (memberNodes.equals(requiredMemberNodes))
					viable.add(e);
			}
			if (viable.isEmpty()) {
				return new LinkedList<Map<Edge, Edge>>();
			} else {
				result.put(toFixPO, viable);
			}
		}

		List<Map<Edge, Edge>> list = new LinkedList<Map<Edge, Edge>>();
		MapOfLSetEnumerator.combinations(result, list);
		return list;
	}

	private Map<Edge, Set<Edge>> flattenFixingSet(Map<DanglingEdge, Set<Edge>> fixingEdges) {
		Map<Edge, Set<Edge>> result = new HashMap<Edge, Set<Edge>>();
		for (Entry<DanglingEdge, Set<Edge>> entry : fixingEdges.entrySet()) {
			result.put(entry.getKey().getEdge(), entry.getValue());
		}
		return result;
	}
}
