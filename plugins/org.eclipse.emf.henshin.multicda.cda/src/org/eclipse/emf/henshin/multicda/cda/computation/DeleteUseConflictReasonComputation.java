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
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.ConflictAnalysis;
import org.eclipse.emf.henshin.multicda.cda.Pushout;
import org.eclipse.emf.henshin.multicda.cda.ReasonFactory;
import org.eclipse.emf.henshin.multicda.cda.Utils;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.DeleteReadConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;

import agg.util.Pair;

/**
 * WARNING: Preliminary implementation, not tested yet.
 * 
 * @author vincentcuccu 17.12.2017, Jevgenij Huebert 2018
 */
public class DeleteUseConflictReasonComputation<T extends Reason> {

	private Rule rule1;
	private Rule rule2;
	private Set<T> normalCRs;
	private Set<T> DUCRs;

	private MinimalReasonComputation helperForCheckDangling;
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
		if (ConflictAnalysis.COMPLETE_COMPUTATION)
			computeCreateEdgeDeleteNode(result);
		return result;

	}

	private void computeCreateEdgeDeleteNode(Set<T> result) {
		for (T s2 : DUCRs)
			for (GraphElement geImage : s2.getDeletionElementsInRule1())
				if (geImage instanceof Node)
					for (Edge e : s2.getRule2().getActionEdges(new Action(Action.Type.CREATE))) {
						Node origin = s2.getRule2().getMappings().getOrigin(e.getSource());
						Mapping m = s2.getMappingFromGraphToRule2(origin);
						if (m == null) {
							origin = s2.getRule2().getMappings().getOrigin(e.getTarget());
							m = s2.getMappingFromGraphToRule2(origin);
						}
						Node image = null;
						if (m != null)
							image = s2.getMappingIntoRule1(m.getOrigin()).getImage();
						if (geImage == image)
							result.add(ReasonFactory.eINSTANCE.createCEDNReason(s2));
						else {
							origin = s2.getRule2().getMappings().getOrigin(e.getTarget());
							m = s2.getMappingFromGraphToRule2(origin);
							if (m != null)
								image = s2.getMappingIntoRule1(m.getOrigin()).getImage();
							if (geImage == image)
								result.add(ReasonFactory.eINSTANCE.createCEDNReason(s2));
						}
					}
	}

	/**
	 * the Method to encounter the delete read conflict reasons
	 * 
	 * @param s1
	 * @param result
	 * @throws Exception
	 */
	private void computeDeleteUseConflictReasons(T s1, Set<T> result) {
		Rule rule12 = s1.getRule1();
		Rule conflictRule2 = s1.getRule2();
		helperForCheckDangling = new MinimalReasonComputation(rule1, rule2);

		if (findEmbeddingS1toK2(s1, rule1, rule2)) {
			Pushout pushout = new Pushout(rule1.getLhs(), s1, conflictRule2.getLhs(), false);
			if (helperForCheckDangling.findDanglingEdgesOfRule1(rule12, pushout.getRule1Mappings()).isEmpty()
					&& helperForCheckDangling.findDanglingEdgesOfRule1(conflictRule2, pushout.getRule2Mappings())
							.isEmpty()) {
				result.add(s1);
			}
		} else {
			Set<Reason> ddSet = constructDeleteDeleteSet(s1);
			if (!ddSet.isEmpty())
				result.add(ReasonFactory.eINSTANCE.createDDReason(s1, ddSet));
		}
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
		EList<Node> l2N = new BasicEList<>(rule2.getActionNodes(new Action(Action.Type.PRESERVE)));
		EList<Edge> l2E = new BasicEList<>(rule2.getActionEdges(new Action(Action.Type.PRESERVE)));

		if (ConflictAnalysis.COMPLETE_COMPUTATION) {
			List<GraphElement> toRemove = new ArrayList<>();
			Map<Node, Set<Pair<Attribute, Attribute>>> n1Attrs = Utils.getChangeNodes(rule1);
			for (Node n2 : l2N) {
				Pair<Node, Set<Pair<Attribute, Attribute>>> n2Attrs = Utils.getChangeNodes(n2);
				if (!n2Attrs.second.isEmpty())
					for (Node n1 : n1Attrs.keySet())
						if (Utils.identifySubNodeType(n1, n2Attrs.first) != null)
							for (Pair<Attribute, Attribute> n1Attr : n1Attrs.get(n1))
								for (Pair<Attribute, Attribute> n2Attr : n2Attrs.second)
									if (n2Attr.first != null && n1Attr.first != null
											&& n1Attr.first.getType() == n2Attr.first.getType())
										if (n1Attr.second == null || n2Attr.second == null
												|| (Utils.equalAttributes(n1Attr.first, n2Attr.first)))
											toRemove.add(n2);
			}
			l2N.removeAll(toRemove);
			for (Edge e : l2E) {
				if (toRemove.contains(e.getTarget()))
					toRemove.add(e.getTarget());
				if (toRemove.contains(e.getSource()))
					toRemove.add(e.getSource());
			}
			l2E.removeAll(toRemove);
		}
		List<Mapping> s1tol2 = computeMappings(s1, l2N, l2E);
		return !s1tol2.isEmpty();

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
				String[] names = first.getName().split(Reason.NODE_SEPARATOR);
				if (first.getType() == second.getType())
					if (names[1].equals(second.getName())) {
						Mapping mapping = factory.createMapping(first, second);
						G1toG2.add(mapping);
						found = true;
						break;
					}
			}
			if (!found)
				return new ArrayList<>();
		}
		Set<Edge> visited = new HashSet<>();
		for (Edge first : s1.getGraph().getEdges()) {
			found = false;
			for (Edge second : g2E) {
				String[] namesSource = first.getSource().getName().split(Reason.NODE_SEPARATOR);
				String[] namesTarget = first.getTarget().getName().split(Reason.NODE_SEPARATOR);
				if (namesSource[1].equals(second.getSource().getName())
						&& namesTarget[1].equals(second.getTarget().getName()) && visited.add(second)) {
					found = true;
					break;
				}
			}
			if (!found)
				return new ArrayList<>();

		}
		return G1toG2;
	}

	private static final String INTERSECTIONSEPERATOR = "_§_";

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
						if (helperForCheckDangling.findDanglingEdgesOfRule1(rule1, po.getRule1Mappings()).isEmpty()
								&& helperForCheckDangling.findDanglingEdgesOfRule1(rule2, po.getRule2Mappings())
										.isEmpty()) {
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
		if (precondition(sap, sp1, sp2))
			return null;
		Set<Mapping> mappingsInL1 = computeMappingStoL(pushout, rule1, sap, sp1, sp2);
		Set<Mapping> mappingsInL2 = computeMappingStoL(pushout, rule2, sap, sp2, sp1);
		uniqueSpan = new DeleteReadConflictReason(mappingsInL1, pushoutGraph, mappingsInL2);
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

	private boolean precondition(Reason sap, Reason sp1, Reason sp2) {
		Graph sapGraph = sap.getGraph();
		EList<Node> sapNodes = sapGraph.getNodes();
		for (Node node : sapNodes) {
			Mapping a = sap.getMappingIntoRule1(node);
			Mapping b = sap.getMappingIntoRule2(node);
			if (a != null && b != null) {
				Node s1Element = a.getImage();
				Node s2Element = b.getImage();
				if (s1Element != null && s2Element != null) {
					Mapping e1 = sp1.getMappingIntoRule1(s1Element);
					Mapping f1 = sp2.getMappingIntoRule2(s2Element);
					Mapping e2 = sp1.getMappingIntoRule2(s1Element);
					Mapping f2 = sp2.getMappingIntoRule1(s2Element);
					if (e1 != null && e2 != null && f1 != null && f2 != null) {
						Node l1eElement = e1.getImage();
						Node l1fElement = f1.getImage();
						Node l2eElement = e2.getImage();
						Node l2fElement = f2.getImage();
						if (!l1eElement.equals(l1fElement) || !l2eElement.equals(l2fElement))
							return false;
					} else
						return false;
				} else
					return false;
			} else
				return false;
		}
		return true;
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
		if (s1Apostrophe != null) {
			Reason s2Apostrophe = compatibleElements(sp2, sp1);
			if (s2Apostrophe != null) {
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
				if (checkOriginNodes(node1, node2, INTERSECTIONSEPERATOR)) {
					Node newNode = factory.createNode(result, node1.getType(), node1.getName());
					Mapping mappingFromGraphToRule12 = sp1.getMappingIntoRule1(node1);
					Node mappingFromGraphToRule1 = mappingFromGraphToRule12.getImage();
					Mapping createMapping = factory.createMapping(newNode, mappingFromGraphToRule1);
					mappingsIntoSpan1.add(createMapping);
					Mapping mappingIntoRule2 = sp2.getMappingIntoRule1(node2);
					Node image = mappingIntoRule2.getImage();
					Mapping createMapping2 = factory.createMapping(newNode, image);
					mappingsIntoSpan2.add(createMapping2);
				}
			}
		}
		for (Edge e1 : s1Edges) {
			for (Edge e2 : s2Edges) {
				Node source1 = e1.getSource();
				Node source2 = e2.getSource();
				Node target1 = e1.getTarget();
				Node target2 = e2.getTarget();
				if (checkEdges(e1, e2, INTERSECTIONSEPERATOR)) {
					Node source = null;
					Node target = null;
					EReference type = e1.getType();
					for (Node node : result.getNodes()) {
						if (checkOriginNodes(node, source1, INTERSECTIONSEPERATOR)
								&& checkOriginNodes(node, source2, INTERSECTIONSEPERATOR)) {
							source = node;
						}
						if (checkOriginNodes(node, target1, INTERSECTIONSEPERATOR)
								&& checkOriginNodes(node, target2, INTERSECTIONSEPERATOR)) {
							target = node;
						}
					}
					if (source != null && target != null) {
						factory.createEdge(source, target, type);
					}
				}
			}
		}
		Reason span = new DeleteReadConflictReason(mappingsIntoSpan1, result, mappingsIntoSpan2);
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
		Reason comSpan = new DeleteReadConflictReason(mappingsIntoSpan1, compatibleGraph, mappingsIntoSpan2);
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
				if (result == 2) {
					return y;
				} else if (result == 1)
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
		Mapping s11 = getMappingInRule(x, sp1.mappingsInRule1);
		Mapping s21 = getMappingInRule(y, sp2.mappingsInRule2);

		Mapping s12 = getMappingInRule(x, sp1.mappingsInRule2);
		Mapping s22 = getMappingInRule(y, sp2.mappingsInRule1);

		if (s11 != null && s21 != null && s12 != null && s22 != null) {
			boolean b1 = s11.getImage().getName().equals(s21.getImage().getName())
					&& Utils.nodeTypeEqual(s11.getImage(), s21.getImage());
			boolean b2 = s12.getImage().getName().equals(s22.getImage().getName())
					&& Utils.nodeTypeEqual(s12.getImage(), s22.getImage());
			return (b1 ? 1 : 0) + (b2 ? 1 : 0);
		}
		return 0;
	}

	/**
	 * @param e1
	 * @param e2
	 * @param regex
	 */
	private boolean checkEdges(Edge e1, Edge e2, String regex) {
		EReference e1Type = e1.getType();
		Node e1Source = e1.getSource();
		Node e1target = e1.getTarget();
		EReference e2Type = e2.getType();
		Node e2Source = e2.getSource();
		Node e2target = e2.getTarget();
		if (!e1Type.equals(e2Type)) {
			return false;
		}
		if (!checkOriginNodes(e1Source, e2Source, regex)) {
			return false;
		}
		if (!checkOriginNodes(e1target, e2target, regex)) {
			return false;
		}
		return true;
	}

	/**
	 * @param originNode
	 * @param mappingsInRule
	 * @return Mapping
	 */
	public Mapping getMappingInRule(Node originNode, Set<Mapping> mappingsInRule) {
		for (Mapping mapping : mappingsInRule) {
			if (checkOriginNodes(originNode, mapping.getOrigin(), Reason.NODE_SEPARATOR)) {
				return mapping;
			}
		}
		return null;
	}

	/**
	 * @param originNode
	 * @param origin
	 * @return
	 */
	private boolean checkOriginNodes(Node originNode, Node originNode2, String regex) {
		String[] revert = originNode.getName().split(regex);
		String[] revertMapping = originNode2.getName().split(regex);
		return (revert[0].equals(revertMapping[0]) && revert[1].equals(revertMapping[1])
				|| revert[0].equals(revertMapping[1]) && revert[1].equals(revertMapping[0]))
				&& (Utils.nodeTypeEqual(originNode, originNode2));
	}

	public static class NotCompatibleException extends Exception {
		private static final long serialVersionUID = 3555111140711032351L;

		public NotCompatibleException() {
			super("Ein Fehler bei der Delete Use Conflict Reason berechnung ist aufgetaucht.");
		}

	}

}
