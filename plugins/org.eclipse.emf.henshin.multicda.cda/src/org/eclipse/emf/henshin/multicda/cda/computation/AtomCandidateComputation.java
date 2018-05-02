package org.eclipse.emf.henshin.multicda.cda.computation;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.eclipse.emf.henshin.multicda.cda.Utils;
import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ChangeAttrConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DeleteAttrConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DeleteUseConflictAtom;

import agg.util.Pair;

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

	public List<Atom> computeAtomCandidates() {
		List<Atom> result = new LinkedList<>();

		List<ModelElement> atomicDeletionElements = new LinkedList<>(rule1.getActionNodes(deleteAction));
		atomicDeletionElements.addAll(identifyAtomicDeletionEdges());
		for (ModelElement el1 : atomicDeletionElements) {
			addDeleteUseAtomCandidates(rule1, rule2, result, el1);
		}

		//WARNING: Preliminary implementation, not tested yet.
		if (ConflictAnalysis.COMPLETE_COMPUTATION) {
			Map<Node, Set<Pair<Attribute, Attribute>>> changeUse = Utils.getChangeNodes(rule1);
			for (Node n1 : changeUse.keySet())
				addChangeUseAtomCandidates(rule1, rule2, result, n1, changeUse.get(n1));
		}
		return result;
	}

	//WARNING: Preliminary implementation, not tested yet.
	protected void addChangeUseAtomCandidates(Rule rule1, Rule rule2, List<Atom> result, Node n1,
			Set<Pair<Attribute, Attribute>> changeUseAttrs1) {
		for (Node n2 : rule2.getLhs().getNodes()) {
			boolean r1NodeIsSuperTypeOfR2Node = n2.getType().getEAllSuperTypes().contains(n1.getType());
			boolean r2NodeIsSuperTypeOfR1Node = n1.getType().getEAllSuperTypes().contains(n2.getType());
			boolean identicalType = n2.getType() == n1.getType();
			boolean changeUse = false;
			boolean deleteUse = false;
			boolean createUse = false;
			if (r1NodeIsSuperTypeOfR2Node || r2NodeIsSuperTypeOfR1Node || identicalType) {
				for (Pair<Attribute, Attribute> a1 : changeUseAttrs1) {
					Attribute a1L = a1.first;
					Attribute a1R = a1.second;
					Attribute a2L = null;
					if (a1L == null)
						a2L = n2.getAttribute(a1R.getType());
					else
						a2L = n2.getAttribute(a1L.getType());
					if (a2L != null)
						if (a1L == null && a1R != null && !Utils.equalAttributes(a1R, a2L)) {
							createUse = true;
							break;
						} else if (a1L != null && a1R != null && !a1R.getValue().equals(a2L.getValue())
								&& Utils.equalAttributes(a1L, a2L)){
//								&& a1L.getValue().equals(a2L.getValue())) {
							changeUse = true;
							break;
						} else if (a1R == null && Utils.equalAttributes(a1L, a2L)) {
							deleteUse = true;
							break;
						}
				}
				if (changeUse || deleteUse || createUse) {
					Graph S1 = henshinFactory.createGraph();
					Set<Mapping> rule1Mappings = new HashSet<Mapping>();
					Set<Mapping> rule2Mappings = new HashSet<Mapping>();
					Utils.addNodeToGraph(n1, (Node) n2, S1, rule1Mappings, rule2Mappings);
					if (changeUse || createUse)
						result.add(new ChangeAttrConflictAtom(rule1Mappings, S1, rule2Mappings));
					else
						result.add(new DeleteAttrConflictAtom(rule1Mappings, S1, rule2Mappings));

				}
			}
		}
	}

	protected void addDeleteUseAtomCandidates(Rule rule1, Rule rule2, List<Atom> result, ModelElement el1) {
		if (el1 instanceof Node) {
			Node n1 = (Node) el1;
			for (Node n2 : rule2.getLhs().getNodes()) {
				boolean r1NodeIsSuperTypeOfR2Node = n2.getType().getEAllSuperTypes().contains(n1.getType());
				boolean r2NodeIsSuperTypeOfR1Node = n1.getType().getEAllSuperTypes().contains(n2.getType());
				boolean identicalType = n2.getType() == n1.getType();
				if (r1NodeIsSuperTypeOfR2Node || r2NodeIsSuperTypeOfR1Node || identicalType) {
					Graph S1 = henshinFactory.createGraph();
					Set<Mapping> rule1Mappings = new HashSet<Mapping>();
					Set<Mapping> rule2Mappings = new HashSet<Mapping>();
					Utils.addNodeToGraph(n1, (Node) n2, S1, rule1Mappings, rule2Mappings);
					Atom S1span = new DeleteUseConflictAtom(rule1Mappings, S1, rule2Mappings);
					result.add(S1span);
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
					Atom S1span = new DeleteUseConflictAtom(rule1Mappings, S1, rule2Mappings);
					result.add(S1span);

					S1.getEdges()
							.add(henshinFactory.createEdge(commonSourceNode, commonTargetNode, ((Edge) el2).getType()));
				}
			}
		}

	}

	private Set<Edge> identifyAtomicDeletionEdges() {
		Set<Edge> result = new HashSet<Edge>();
		for (Edge e1 : new HashSet<Edge>(rule1.getActionEdges(deleteAction))) {
			if (isPreserveNode(e1.getSource()) && isPreserveNode(e1.getTarget())) {
				for (Edge e2 : new HashSet<Edge>(rule2.getActionEdges(preserveAction))) {
					if (e2.getType() == e1.getType()) {
						result.add(e1);
					}
				}
			}

		}
		return result;
	}

	protected boolean isPreserveNode(Node node) {
		return node.getAction().getType() == Type.PRESERVE;
	}

}
