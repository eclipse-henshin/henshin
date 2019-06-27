package org.eclipse.emf.henshin.multicda.cda.computation;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Action.Type;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.ModelElement;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.ConflictAnalysis;
import org.eclipse.emf.henshin.multicda.cda.Pair;
import org.eclipse.emf.henshin.multicda.cda.Utils;
import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ChangeConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.CreateConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DeleteConflictAtom;

public class AtomCandidateComputation {
	static Action deleteAction = new Action(Action.Type.DELETE);
	static Action preserveAction = new Action(Action.Type.PRESERVE);
	static HenshinFactory henshinFactory = HenshinFactory.eINSTANCE;

	protected Rule rule1;
	protected Rule rule2;

	public AtomCandidateComputation(Rule rule1, Rule rule2) {
		this.rule1 = rule1;
		this.rule2 = rule2;
	}

	public Set<Atom> computeAtomCandidates() {
		Set<Atom> result = new HashSet<>();

		List<ModelElement> atomicDeletionElements = new LinkedList<>(rule1.getActionNodes(deleteAction));
		atomicDeletionElements.addAll(identifyAtomicDeletionEdges());
		for (ModelElement el1 : atomicDeletionElements) {
			addDeleteUseAtomCandidates(rule1, rule2, result, el1);
		}
		Map<Node, Set<Pair<Attribute, Attribute>>> changeUse = Utils.getAttributeChanges(rule1);
		for (Node n1 : changeUse.keySet())
			addChangeUseAtomCandidates(rule1, rule2, result, n1, changeUse.get(n1));
		return result;
	}

	protected void addChangeUseAtomCandidates(Rule rule1, Rule rule2, Set<Atom> result, Node n1L,
			Set<Pair<Attribute, Attribute>> changeUseAttrs1) {
		for (Node n2L : rule2.getLhs().getNodes()) {
			boolean changeUse = false;
			boolean deleteUse = false;
			boolean createUse = false;

			if (Utils.identifySubNodeType(n1L, n2L) == n1L.getType()) {
				for (Pair<Attribute, Attribute> a1 : changeUseAttrs1) {
					Attribute a1L = a1.first;
					Attribute a1R = a1.second;
					if (a1L != null)
						if (Utils.equalAttributes(a1L, n2L.getAttribute(a1L.getType())))
							if (a1R == null) {
								deleteUse = true;
								break;
							} else {
								changeUse = true;
								break;
							}
					Node n2R = n2L.getGraph().getRule().getMappings().getImage(n2L, null);
					if (a1L == null && a1R != null && n2R != null) {
						Attribute a2L = n2L.getAttribute(a1R.getType());
						Attribute a2R = n2R.getAttribute(a1R.getType());
						if (a2R != null && a2L == null) {
							createUse = true;
							break;
						}
					}
				}
				if (changeUse || deleteUse || createUse) {
					Graph S1 = henshinFactory.createGraph();
					Set<Mapping> rule1Mappings = new HashSet<Mapping>();
					Set<Mapping> rule2Mappings = new HashSet<Mapping>();
					Utils.addNodeToGraph(n1L, (Node) n2L, S1, rule1Mappings, rule2Mappings);
					if (createUse)
						result.add(new CreateConflictAtom(rule1Mappings, S1, rule2Mappings));
					else if (changeUse)
						result.add(new ChangeConflictAtom(rule1Mappings, S1, rule2Mappings));
					else
						result.add(new DeleteConflictAtom(rule1Mappings, S1, rule2Mappings));
				}
			}
		}

	}

	protected void addDeleteUseAtomCandidates(Rule rule1, Rule rule2, Set<Atom> result, ModelElement el1) {
		if (el1 instanceof Node) {
			Node n1 = (Node) el1;
			for (Node n2 : rule2.getLhs().getNodes()) {
				if (Utils.identifySubNodeType(n1, n2) == n1.getType()) {
					EList<Attribute> attrs = n2.getActionAttributes(new Action(Action.Type.CREATE));
					boolean allowed = true;
					for (Attribute a2 : attrs)
						for (Attribute a1 : n1.getAttributes())
							if (a1.getType() == a2.getType()) {
								allowed = false;
								break;
							}
					if (allowed) {
						Graph S1 = henshinFactory.createGraph();
						Set<Mapping> rule1Mappings = new HashSet<Mapping>();
						Set<Mapping> rule2Mappings = new HashSet<Mapping>();
						Utils.addNodeToGraph(n1, (Node) n2, S1, rule1Mappings, rule2Mappings);
						Atom S1span = new DeleteConflictAtom(rule1Mappings, S1, rule2Mappings);
						result.add(S1span);
					}
				}
			}
		}

		if (el1 instanceof Edge) {
			List<ModelElement> atomicUsageElements = new LinkedList<>();
			atomicUsageElements.addAll(rule2.getLhs().getEdges(((Edge) el1).getType()));
			for (ModelElement el2 : atomicUsageElements) {
				if (el2 instanceof Edge) {
					Graph S1 = henshinFactory.createGraph();
					Set<Mapping> rule1Mappings = new HashSet<>();
					Set<Mapping> rule2Mappings = new HashSet<>();

					Node commonSourceNode = Utils.addNodeToGraph(((Edge) el1).getSource(), ((Edge) el2).getSource(), S1,
							rule1Mappings, rule2Mappings);
					Node commonTargetNode = Utils.addNodeToGraph(((Edge) el1).getTarget(), ((Edge) el2).getTarget(), S1,
							rule1Mappings, rule2Mappings);
					Atom S1span = new DeleteConflictAtom(rule1Mappings, S1, rule2Mappings);
					if (S1span.getRule2() == null)
						System.out.println("Found");
					result.add(S1span);

					S1.getEdges()
							.add(henshinFactory.createEdge(commonSourceNode, commonTargetNode, ((Edge) el2).getType()));
				}
			}
		}
	}

	private Set<Edge> identifyAtomicDeletionEdges() {
		Set<Edge> result = new HashSet<Edge>();
		for (Edge e1 : new HashSet<Edge>(rule1.getActionEdges(deleteAction)))
			if (isPreserveNode(e1.getSource()) && isPreserveNode(e1.getTarget()))
				for (Edge e2 : rule2.getLhs().getEdges())
					if (e2.getType() == e1.getType())
						result.add(e1);
		return result;
	}

	protected boolean isPreserveNode(Node node) {
		return node.getAction().getType() == Type.PRESERVE;
	}

}
