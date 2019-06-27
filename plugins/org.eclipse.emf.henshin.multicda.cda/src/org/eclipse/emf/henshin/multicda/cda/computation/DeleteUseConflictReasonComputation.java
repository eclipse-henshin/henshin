/**
 * 
 */
package org.eclipse.emf.henshin.multicda.cda.computation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.Pair;
import org.eclipse.emf.henshin.multicda.cda.Pushout;
import org.eclipse.emf.henshin.multicda.cda.ReasonFactory;
import org.eclipse.emf.henshin.multicda.cda.Utils;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.DeleteConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;

/**
 * 
 * @author vincentcuccu 17.12.2017, Jevgenij Huebert 2018
 */
public class DeleteUseConflictReasonComputation<T extends Reason> {

	private Rule rule1;
	private Rule rule2;
	private Set<T> normalCRs;
	private Set<T> DUCRs;

	private static HenshinFactory factory = HenshinFactory.eINSTANCE;

	/**
	 * constructor
	 * 
	 * @param rule1
	 * @param rule2
	 * @param conflictReasonsFromR22
	 */
	public DeleteUseConflictReasonComputation(Rule rule1, Rule rule2, Set<T> normalCR, Set<T> DUCRs) {
		this.rule1 = rule1;
		this.rule2 = rule2;
		this.normalCRs = normalCR;
		this.DUCRs = DUCRs;
	}

	/**
	 * constructs all Initial Reasons as candidates for r1 and r2
	 * 
	 * @param conflictReasons
	 * @return result
	 */
	public Set<T> computeDeleteUseConflictReason() {
		Set<T> result = new HashSet<>();
		for (T normal : normalCRs)
			computeDeleteUseConflictReasons(normal, result);
		result.addAll(Utils.computeCreateEdgeDeleteNode(DUCRs));
		return result;

	}

	/**
	 * the Method to encounter the delete read conflict reasons
	 * 
	 * @param s1
	 * @param result
	 */
	private void computeDeleteUseConflictReasons(T s1, Set<T> result) {
		Rule rule12 = s1.getRule1();
		Rule conflictRule2 = s1.getRule2();
		T span = null;

		if (findEmbeddingS1toK2(s1, rule1, rule2)) {
			Pushout pushout = new Pushout(rule1.getLhs(), s1, conflictRule2.getLhs(), false);
			if (Utils.findDanglingEdgesOfRule1(rule12, pushout.getRule1Mappings()).isEmpty()
					&& Utils.findDanglingEdgesOfRule1(conflictRule2, pushout.getRule2Mappings()).isEmpty())
				span = s1;
		} else {
			Set<Reason> ddSet = constructDeleteDeleteSet(s1);
			if (!ddSet.isEmpty())
				span = ReasonFactory.eINSTANCE.createSymmetricReason(s1, ddSet);
		}
		if (span != null && !violatesForbid(span)) {
			int size = result.size() + 1;
			result.add(span);
			if (size != result.size())
				System.err.println("SIZE ERROR!!! size of result is " + result.size() + " but should " + size);
		}
	}

	private boolean violatesForbid(T span) {
		for (Edge e : span.getRule1().getLhs().getEdges()) {
			Mapping sM = span.getMappingFromGraphToRule1(e.getSource());
			Mapping tM = span.getMappingFromGraphToRule1(e.getTarget());
			if (sM != null && tM != null) {
				Node s2 = span.getMappingIntoRule2(sM.getOrigin()).getImage();
				Node t2 = span.getMappingIntoRule2(tM.getOrigin()).getImage();
				if (s2.getOutgoing(e.getType(), t2) == null)
					for (NestedCondition nc : span.getRule2().getLhs().getNACs()) {
						s2 = nc.getMappings().getImage(s2, null);
						t2 = nc.getMappings().getImage(t2, null);
						if (s2.getOutgoing(e.getType(), t2) != null)
							return true;
					}
			}
		}
		return false;
	}

	/**
	 * returns true, if there is a match from S1 to K2, which is equal to the match
	 * of S1 to lhs of rule 2
	 * 
	 * @param s1
	 * @param rule2
	 * @return boolean
	 */
	public static boolean findEmbeddingS1toK2(Reason s1, Rule rule1, Rule rule2) {
		EList<Node> k2N = new BasicEList<>(rule2.getActionNodes(new Action(Action.Type.PRESERVE)));
		EList<Edge> k2E = new BasicEList<>(rule2.getActionEdges(new Action(Action.Type.PRESERVE)));
		removeDeleteAttributeNodes(rule1, rule2, k2N, k2E);
		List<Mapping> s1tok2 = computeMappings(s1, k2N, k2E);
		return !s1tok2.isEmpty();
	}

	private static void removeDeleteAttributeNodes(Rule rule1, Rule rule2, EList<Node> k2n, EList<Edge> k2e) {
		List<GraphElement> toRemove = new ArrayList<>();
		Map<Node, Set<Pair<Attribute, Attribute>>> n1Attrs = Utils.getAttributeChanges(rule1);
		Map<Node, Set<Pair<Attribute, Attribute>>> n2Attrs = Utils.getAttributeChanges(rule2);
		for (Node n1 : n1Attrs.keySet())
			for (Node n2 : n2Attrs.keySet())
				if (Utils.identifySubNodeType(n1, n2) != null)
					for (Pair<Attribute, Attribute> n1Attr : n1Attrs.get(n1))
						for (Pair<Attribute, Attribute> n2Attr : n2Attrs.get(n2))
							if (n1Attr.first != null && n2Attr.first != null
									&& Utils.equalAttributes(n1Attr.first, n2Attr.first)) // delete or change
								toRemove.add(n2);
							else if (n1Attr.first == null && n2Attr.first == null
									&& n1Attr.second.getType() == n2Attr.second.getType()) // create
								toRemove.add(n2);

		k2n.removeAll(toRemove);
		for (Edge e : k2e) {
			if (toRemove.contains(e.getTarget()))
				toRemove.add(e.getTarget());
			if (toRemove.contains(e.getSource()))
				toRemove.add(e.getSource());
		}
		k2e.removeAll(toRemove);
	}

	/**
	 * computes Mappings of two ELists of Nodes our of two Graphs
	 * 
	 * @param s1
	 * @param g2
	 * @return
	 */
	private static ArrayList<Mapping> computeMappings(Reason s1, EList<Node> g2N, EList<Edge> g2E) {
		ArrayList<Mapping> G1toG2 = new ArrayList<Mapping>();

		boolean found = false;
		for (Node first : s1.getGraph().getNodes()) {
			found = false;
			for (Node second : g2N) {
				if (s1.getMappingIntoRule2(first).getImage() == second) {
					Mapping mapping = factory.createMapping(first, second);
					G1toG2.add(mapping);
					found = true;
					break;
				}
			}
			if (!found)
				return new ArrayList<>();
		}

		for (Edge first : s1.getGraph().getEdges()) {
			found = false;
			for (Edge second : g2E)
				if (s1.getMappingIntoRule2(first.getSource()).getImage() == second.getSource()
						&& s1.getMappingIntoRule2(first.getTarget()).getImage() == second.getTarget()) {
					found = true;
					break;
				}
			if (!found)
				return new ArrayList<>();
		}
		return G1toG2;
	}

	private static final String INTERSECTIONSEPERATOR = "_ß_";

	/**
	 * @param r2
	 * @param r1
	 * @param sp1
	 * @return
	 * @throws Exception
	 */
	private Set<Reason> constructDeleteDeleteSet(Reason sp1) {
		Set<Reason> result = new TreeSet<>();
		for (Reason sp2 : DUCRs) {
			Reason s = compatibleSpans(sp1, sp2);
			if (s != null) {
				if (!isEmpty(s.getGraph())) {
					Pushout pushout = new Pushout(sp1.getGraph(), s, sp2.getGraph(), false);
					Reason l1Sl2 = computeL1SL2Span(rule1, pushout, rule2, s, sp1, sp2);
					if (l1Sl2 != null) {
						Pushout po = new Pushout(rule1.getLhs(), l1Sl2, rule2.getLhs(), false);
						if (Utils.findDanglingEdgesOfRule1(rule1, po.getRule1Mappings()).isEmpty()
								&& Utils.findDanglingEdgesOfRule1(rule2, po.getRule2Mappings()).isEmpty()) {
							result.add(sp2);
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * @param rule1
	 * @param pushout
	 * @param sap
	 * @param sp2
	 * @param sp1
	 * @return
	 */
	private Reason computeL1SL2Span(Rule rule1, Pushout pushout, Rule rule2, Reason sap, Reason sp1, Reason sp2) {
		Reason uniqueSpan = null;
		Graph pushoutGraph = pushout.getResultGraph();
		// if (precondition(sap, sp1, sp2)) //TODO: Nachfragen ob die Precondition so
		// richtig ist: sie schmeiﬂt meiner meinung nach immer false
		// return null;
		Set<Mapping> mappingsInL1 = computeMappingStoL(pushout, rule1, sap, sp1, sp2);
		Set<Mapping> mappingsInL2 = computeMappingStoL(pushout, rule2, sap, sp2, sp1);
		uniqueSpan = new DeleteConflictReason(mappingsInL1, pushoutGraph, mappingsInL2);
		uniqueSpan.setRule1(rule1);
		uniqueSpan.setRule2(rule2);
		return uniqueSpan;
	}

	/**
	 * @param pushout
	 * @param sap
	 * @param sp2
	 * @param sp1
	 * @param rule12
	 * @return
	 */
	private Set<Mapping> computeMappingStoL(Pushout pushout, Rule rule, Reason sap, Reason sp1, Reason sp2) {
		HashSet<Mapping> result = new HashSet<Mapping>();
		Graph pushoutGraph = pushout.getResultGraph();
		List<Mapping> s1ToS = pushout.getRule1Mappings();
		List<Mapping> s2ToS = pushout.getRule2Mappings();
		EList<Node> nodes = pushoutGraph.getNodes();
		for (Node s : nodes) {
			Node node = (Node) s;
			Mapping c = getMappingFromSpan(node, s1ToS);
			Mapping d = getMappingFromSpan(node, s2ToS);
			if (c != null) {
				Node s1Element = c.getOrigin();
				Mapping e = null;
				Mapping e1 = sp1.getMappingIntoRule1(s1Element);
				Mapping e2 = sp2.getMappingIntoRule2(s1Element);
				if (e1 != null) {
					e = e1;
				} else if (e2 != null) {
					e = e2;
				} else {
					return null;
				}
				Node lElement = e.getImage();
				Graph lhs = rule.getLhs();
				nodes = lhs.getNodes();
				if (nodes.contains(lElement)) {
					Mapping createMapping = factory.createMapping(node, lElement);
					result.add(createMapping);
				} else {
					for (Node n : nodes) {
						if (n.getName().equals(lElement.getName())) {
							Mapping createMapping = factory.createMapping(node, n);
							result.add(createMapping);
						}
					}
				}
			} else if (d != null) {
				Node s2Element = d.getOrigin();
				Mapping f = null;
				Mapping f1 = sp2.getMappingIntoRule2(s2Element);
				Mapping f2 = sp1.getMappingIntoRule1(s2Element);
				if (f1 != null) {
					f = f1;
				} else if (f2 != null) {
					f = f2;
				} else {
					return null;
				}
				Node lElement = f.getImage();
				Graph lhs = rule.getLhs();
				nodes = lhs.getNodes();
				if (nodes.contains(lElement)) {
					Mapping createMapping = factory.createMapping(node, lElement);
					result.add(createMapping);
				} else {
					for (Node n : nodes) {
						if (n.getName().equals(lElement.getName())) {
							Mapping createMapping = factory.createMapping(node, n);
							result.add(createMapping);
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * @param node
	 * @param mappings
	 * @return
	 */
	private Mapping getMappingFromSpan(Node node, List<Mapping> mappings) {
		for (Mapping mapping : mappings) {
			if (node.equals(mapping.getImage())) {
				return mapping;
			}
		}
		return null;
	}

	/**
	 * @param graph
	 * @return
	 */
	private boolean isEmpty(Graph graph) {
		if (graph.getNodes().isEmpty() && graph.getEdges().isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * @param sp2
	 * @param sp1
	 * @return
	 */
	private Reason compatibleSpans(Reason sp1, Reason sp2) {
		Reason s1Apostrophe = compatibleElements(sp1, sp2);
		if (s1Apostrophe != null && !s1Apostrophe.getGraph().getNodes().isEmpty()) {
			Reason s2Apostrophe = compatibleElements(sp2, sp1);
			if (s2Apostrophe != null && !s2Apostrophe.getGraph().getNodes().isEmpty()) {
				Reason intersection = intersection(s1Apostrophe, s2Apostrophe);
				Graph sApostroph = intersection.getGraph();
				if (intersection != null && sApostroph != null) {
					return intersection;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
		return null;
	}

	/**
	 * @param graph1
	 * @param graph2
	 * @param sp2
	 * @param sp1
	 * @return
	 */
	private Reason intersection(Reason sp1, Reason sp2) {
		Graph result = factory.createGraph("S'");
		Graph graph1 = sp1.getGraph();
		Graph graph2 = sp2.getGraph();
		EList<Node> s1Nodes = graph1.getNodes();
		EList<Edge> s1Edges = graph1.getEdges();
		EList<Node> s2Nodes = graph2.getNodes();
		EList<Edge> s2Edges = graph2.getEdges();
		Set<Mapping> mappingsIntoSpan1 = new HashSet<Mapping>();
		Set<Mapping> mappingsIntoSpan2 = new HashSet<Mapping>();
		for (Node node1 : s1Nodes) {
			for (Node node2 : s2Nodes) {
				if (checkOriginNodes(node1, node2, sp1, sp2)) {
					Node newNode = factory.createNode(result, node1.getType(), node1.getName());
					Mapping mappingFromGraphToRule12 = sp1.getMappingIntoRule1(node1);
					Node mappingFromGraphToRule1 = mappingFromGraphToRule12.getImage();
					Mapping createMapping = factory.createMapping(newNode, mappingFromGraphToRule1);
					mappingsIntoSpan1.add(createMapping);
					Mapping mappingIntoRule2 = sp2.getMappingIntoRule1(node2);
					Node image = mappingIntoRule2.getImage();
					Mapping createMapping2 = factory.createMapping(newNode, image);
					mappingsIntoSpan2.add(createMapping2);
					break;
				}
			}
		}
		for (Edge e1 : s1Edges) {
			for (Edge e2 : s2Edges) {
				Node source1 = e1.getSource();
				Node source2 = e2.getSource();
				Node target1 = e1.getTarget();
				Node target2 = e2.getTarget();
				if (checkEdges(e1, e2, sp1, sp2)) {
					Node source = null;
					Node target = null;
					EReference type = e1.getType();
					for (Node node : result.getNodes()) {
						if (checkOriginNodes(node, source1, sp1, sp2) && checkOriginNodes(node, source2, sp1, sp2)) {
							source = node;
						}
						if (checkOriginNodes(node, target1, sp1, sp2) && checkOriginNodes(node, target2, sp1, sp2)) {
							target = node;
						}
					}
					if (source != null && target != null) {
						factory.createEdge(source, target, type);
					}
				}
			}
		}
		Reason span = new DeleteConflictReason(mappingsIntoSpan1, result, mappingsIntoSpan2);
		return span;
	}

	/**
	 * @param sp1
	 * @param sp2
	 * @param s1Apostrophe
	 * @return
	 */
	private Reason compatibleElements(Reason sp1, Reason sp2) {
		Graph compatibleGraph = factory.createGraph("Compatible");
		EList<Node> s1Nodes = sp1.getGraph().getNodes();
		Set<Mapping> mappingsIntoSpan1 = new HashSet<Mapping>();
		Set<Mapping> mappingsIntoSpan2 = new HashSet<Mapping>();
		for (Node x : s1Nodes) {
			try {
				Node y = existCompatibleElement(x, sp1, sp2);
				if (y != null) {
					EClass xType = x.getType();
					String newName = x.getName() + INTERSECTIONSEPERATOR + y.getName();
					Node newNode = factory.createNode(compatibleGraph, xType, newName);
					Mapping createMapping = factory.createMapping(newNode, x);
					Mapping createMapping2 = factory.createMapping(newNode, y);
					mappingsIntoSpan1.add(createMapping);
					mappingsIntoSpan2.add(createMapping2);
				}
			} catch (NotCompatibleException e) {
				return null;
			}
		}
		Reason comSpan = new DeleteConflictReason(mappingsIntoSpan1, compatibleGraph, mappingsIntoSpan2);
		return comSpan;
	}

	/**
	 * @param x
	 * @param sp1
	 * @param sp2
	 * @param conflictReason2
	 * @return
	 * @throws Exception
	 */
	private Node existCompatibleElement(Node x, Reason sp1, Reason sp2) throws NotCompatibleException {
		EList<Node> s1Nodes = sp1.getGraph().getNodes();
		EList<Node> s2Nodes = sp2.getGraph().getNodes();
		if (s1Nodes.contains(x)) {
			for (Node y : s2Nodes) {
				int result = checkEqualityR1(x, y, sp1, sp2);
				if (result == 2)
					return y;
				else if (result == 1)
					throw new NotCompatibleException();
			}
			return null;
		} else {
			throw new NotCompatibleException();
		}
	}

	/**
	 * @param x1
	 * @param y1
	 * @param sp1
	 * @param sp2
	 * @return
	 */
	private int checkEqualityR1(Node x, Node y, Reason sp1, Reason sp2) {
		Mapping s11 = sp1.getMappingIntoRule1(x);
		Mapping s21 = sp2.getMappingIntoRule2(y);

		Mapping s12 = sp1.getMappingIntoRule2(x);
		Mapping s22 = sp2.getMappingIntoRule1(y);

		if (s11 != null && s21 != null && s12 != null && s22 != null)
			return (s11.getImage() == s21.getImage() ? 1 : 0) + (s12.getImage() == s22.getImage() ? 1 : 0);
		return 0;
	}

	/**
	 * @param e1
	 * @param e2
	 * @param regex
	 */
	private boolean checkEdges(Edge e1, Edge e2, Reason sp1, Reason sp2) {
		EReference e1Type = e1.getType();
		Node e1Source = e1.getSource();
		Node e1target = e1.getTarget();
		EReference e2Type = e2.getType();
		Node e2Source = e2.getSource();
		Node e2target = e2.getTarget();
		if (!e1Type.equals(e2Type)) {
			return false;
		}
		if (!checkOriginNodes(e1Source, e2Source, sp1, sp2)) {
			return false;
		}
		if (!checkOriginNodes(e1target, e2target, sp1, sp2)) {
			return false;
		}
		return true;
	}

	/**
	 * @param originNode
	 * @param origin
	 * @return
	 */
	private boolean checkOriginNodes(Node originNode, Node originNode2, Reason sp1, Reason sp2) {
		if (sp1.getMappingIntoRule1(originNode).getImage() == sp2.getMappingIntoRule2(originNode2).getImage()
				&& sp1.getMappingIntoRule2(originNode).getImage() == sp2.getMappingIntoRule1(originNode2).getImage())
			return true;
		return false;
	}

	public static class NotCompatibleException extends Exception {
		private static final long serialVersionUID = 3555111140711032351L;

		public NotCompatibleException() {
			super("Ein Fehler bei der Delete Use Conflict Reason berechnung ist aufgetaucht.");
		}

	}

}
