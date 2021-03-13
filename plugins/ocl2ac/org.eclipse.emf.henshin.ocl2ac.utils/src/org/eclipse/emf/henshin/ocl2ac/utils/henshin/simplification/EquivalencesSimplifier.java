/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.utils.henshin.simplification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.emf.henshin.model.UnaryFormula;

public class EquivalencesSimplifier {

	private Formula formula;
	private HenshinFactory factory;

	public EquivalencesSimplifier(Formula formula) {
		this.formula = formula;
		this.factory = HenshinFactory.eINSTANCE;
	}

	public boolean simplify() {

		if (simplifyExists()) {
			return simplify();
		}
		return true;
	}

	private boolean insert(EObject container, Formula oldContent, Formula newContent) {
		if (container instanceof NestedCondition) {
			NestedCondition constraint = (NestedCondition) container;
			constraint.getConclusion().setFormula(newContent);
			return true;
		}
		if (container instanceof Graph) {
			Graph qlc = (Graph) container;
			qlc.setFormula(newContent);
			return true;
		}
		if (container instanceof Formula) {
			Formula f = (Formula) container;
			if (f instanceof UnaryFormula) {
				UnaryFormula not = (UnaryFormula) f;
				not.setChild(newContent);
				return true;
			}

			if (f instanceof BinaryFormula) {
				BinaryFormula binaryFormula = (BinaryFormula) f;
				if (binaryFormula.getLeft() == oldContent) {
					binaryFormula.setLeft(newContent);
					return true;
				}
				if (binaryFormula.getRight() == oldContent) {
					binaryFormula.setRight(newContent);
					return true;
				}
			}

		}
		return false;
	}

	public boolean simplifyExists() {
		TreeIterator<EObject> iter = formula.eAllContents();
		while (iter.hasNext()) {
			EObject eObject = iter.next();
			if (isExistsCondition(eObject)) {
				NestedCondition outerCondition = (NestedCondition) eObject;

				if (isExistsCondition(outerCondition.getConclusion().getFormula())) {
					NestedCondition innerCondition = (NestedCondition) outerCondition.getConclusion().getFormula();

					if (isLeaf(innerCondition)) {
						Graph outerGraph = outerCondition.getConclusion();
						Graph innerGraph = innerCondition.getConclusion();

						// E1.c
						if (isSubGraph(outerGraph, innerGraph)) {
							outerCondition.setConclusion(innerGraph);
							System.out.println("(1) Equivalence E1.c");
							return true;
						}
						if (isSubGraph(innerGraph, outerGraph)) {
							outerCondition.getConclusion().setFormula(null);
							System.out.println("(2) Equivalence E1.c");
							return true;
						}

						// E1.b
						if (areClanDisjoint(innerGraph, outerGraph)) {
							EList<Node> innerNodes = innerGraph.getNodes();
							innerGraph.getNodes().clear();
							outerGraph.getNodes().addAll(innerNodes);
							EList<Edge> innerEdges = innerGraph.getEdges();
							innerGraph.getEdges().clear();
							outerGraph.getEdges().addAll(innerEdges);
							outerCondition.getConclusion().setFormula(null);
							System.out.println("Equivalence E1.b");
							return true;
						}

						// E1.a
						if (haveIntersection(innerGraph, outerGraph)) {
							EList<Intersection> intersections = getIntersections(outerGraph, innerGraph);
							EObject container = outerCondition.eContainer();
							if (intersections.size() == 1) {
								NestedCondition condition = factory.createNestedCondition();
								condition.setConclusion(glue(outerGraph, intersections.get(0), innerGraph));
								insert(container, outerCondition, condition);
								System.out.println("(1) Equivalence E1.a");
								return true;
							}
							// TODO manage or
							Or orFormula = factory.createOr();
							Or orRight = null;
							for (Intersection intersection : intersections) {
								NestedCondition condition = factory.createNestedCondition();
								condition.setConclusion(glue(outerCondition.getConclusion(), intersection,
										innerCondition.getConclusion()));
								orFormula.setLeft(condition);
								// condition.getConclusion().setFormula(null);
								orFormula.setLeft(condition);
								orRight = factory.createOr();
								orFormula.setRight(orRight);
								orFormula = orRight;
								// TODO call the henshin cleaner to refactor
								// having or onleft only
							}
							insert(container, outerCondition, orFormula);
							System.out.println("(2) Equivalence E1.a");
							return true;
						}

						// E3 case 1
						if (hasOnlyOneNode(innerGraph)) {
							Node innerNode = innerGraph.getNodes().get(0);
							if (hasTwoNames(innerNode)) {
								String name1 = getNames(innerNode.getName()).get(0);
								String name2 = getNames(innerNode.getName()).get(1);
								if (containsExactlyOne(outerGraph, name1, name2)) {
									rename(outerGraph, name1, name2);
									outerCondition.getConclusion().setFormula(null);
									System.out.println("Equivalence E3'");
									return true;
								}
							}
						}

					}
					System.err.println("not a leaf");
				}
				if (isAndFormula(outerCondition.getConclusion().getFormula())) {
					And andFormula = (And) outerCondition.getConclusion().getFormula();
					// if (andFormula.getArguments().size() == 2) {
					if (isExistsCondition(andFormula.getLeft()) && isExistsCondition(andFormula.getRight())) {
						NestedCondition cond1 = (NestedCondition) andFormula.getLeft();
						NestedCondition cond2 = (NestedCondition) andFormula.getRight();
						if (isLeaf(cond1) && isLeaf(cond2)) {
							Graph gr = outerCondition.getConclusion();
							Graph gr1 = cond1.getConclusion();
							Graph gr2 = cond2.getConclusion();
							if (hasOnlyOneNode(gr) && hasOnlyOneNode(gr2)) {
								Node node = gr.getNodes().get(0);
								Node node2 = gr2.getNodes().get(0);
								if (haveSameTypes(node, node2) && hasOneName(node) && hasTwoNames(node2)) {
									String name = node.getName();
									String name1 = node2.getName().split("=")[0];
									String name2 = node2.getName().split("=")[1];
									if (name.equals(name1) || name.equals(name2)) {
										if (containsExactlyOne(gr1, name1, name2)) {
											rename(gr1, name1, name2);
											outerCondition.getConclusion().setFormula(cond1);
											System.out.println("Equivalence E3");
											return true;
										}
									}
								}
							}
							if (hasOnlyOneNode(gr) && hasOnlyOneNode(gr1)) {
								Node node = gr.getNodes().get(0);
								Node node1 = gr1.getNodes().get(0);
								if (haveSameTypes(node, node1) && hasOneName(node) && hasTwoNames(node1)) {
									String name = node.getName();
									String name1 = node1.getName().split("=")[0];
									String name2 = node1.getName().split("=")[1];
									if (name.equals(name1) || name.equals(name2)) {
										if (containsExactlyOne(gr2, name1, name2)) {
											rename(gr2, name1, name2);
											outerCondition.getConclusion().setFormula(cond2);
											System.out.println("Equivalence E3");
											return true;
										}
									}
								}
							}
							System.out.println("=> haveRecurringNodes: " + haveRecurringNodes(gr1, gr2));
							System.out.println(
									"=> containsEachRecurringNode: " + containsEachRecurringNode(gr, gr1, gr2));
							if (haveRecurringNodes(gr1, gr2)) {
								if (containsEachRecurringNode(gr, gr1, gr2)
										|| containsNoRecurringNodeType(gr, gr1, gr2)) {
									if (haveIntersection(gr1, gr2)) {
										EList<Intersection> intersections = getIntersections(gr1, gr2);
										if (intersections.size() == 1) {
											NestedCondition condition = factory.createNestedCondition();
											condition.setConclusion((glue(gr1, intersections.get(0), gr2)));
											// condition.getConclusion().setFormula(null);
											outerCondition.getConclusion().setFormula(condition);
											System.out.println("Equivalence E2.a");
											return true;
										}
										// TODO Manager OR
										Or or = factory.createOr();
										Or orRight = null;
										for (Intersection intersection : intersections) {
											NestedCondition condition = factory.createNestedCondition();
											condition.setConclusion((glue(gr1, intersection, gr2)));
											// condition.getConclusion().setFormula(null);
											or.setLeft(condition);
											orRight = factory.createOr();
											or.setRight(orRight);
											or = orRight;
											// TODO Test it and call cleaner
										}
										outerCondition.getConclusion().setFormula(or);
										System.out.println("Equivalence E2.a");
										return true;
									}
								}
							}
						}
					}
					// }
				}
			}
			if (isAndFormula(eObject)) {
				And andFormula = (And) eObject;
				// if (andFormula.getArguments().size() == 2) {
				if (isExistsCondition(andFormula.getLeft()) && isExistsCondition(andFormula.getRight())) {
					NestedCondition cond1 = (NestedCondition) andFormula.getLeft();
					NestedCondition cond2 = (NestedCondition) andFormula.getRight();
					if (isLeaf(cond1) && isLeaf(cond2)) {
						Graph gr1 = cond1.getConclusion();
						Graph gr2 = cond2.getConclusion();
						if (areClanDisjoint(gr1, gr2) && areNodeNameDisjoint(gr1, gr2)) {
							EList<Node> nodes = gr2.getNodes();
							gr2.getNodes().clear();
							gr1.getNodes().addAll(nodes);
							EList<Edge> edges = gr2.getEdges();
							gr2.getEdges().clear();
							gr1.getEdges().addAll(edges);
							EObject container = andFormula.eContainer();
							insert(container, andFormula, cond1);
							System.out.println("Equivalence E2.b");
							return true;
						}
					}
				}
				// }

			}
		}

		System.err.println("return false");
		return false;
	}

	/**
	 * 
	 * @param gr1
	 * @param gr2
	 * @return
	 */
	private boolean haveRecurringNodes(Graph gr1, Graph gr2) {
		EList<Node> recurringNodes = collectRecurringNodes(gr1, gr2);
		return (!recurringNodes.isEmpty());
	}

	/**
	 * 
	 * @param gr
	 * @param gr1
	 * @param gr2
	 * @return
	 */
	private boolean containsEachRecurringNode(Graph gr, Graph gr1, Graph gr2) {
		EList<Node> recurringNodes = collectRecurringNodes(gr1, gr2);
		for (Node recurringNode : recurringNodes) {
			boolean occurs = false;
			String nodeName = recurringNode.getName();
			EClass type = recurringNode.getType();
			for (Node node : gr.getNodes()) {
				if (node.getName().equals(nodeName) && node.getType() == type) {
					occurs = true;
					break;
				}
			}
			if (!occurs) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param gr
	 * @param gr1
	 * @param gr2
	 * @return
	 */
	private boolean containsNoRecurringNodeType(Graph gr, Graph gr1, Graph gr2) {
		EList<Node> recurringNodes = collectRecurringNodes(gr1, gr2);
		boolean occurs = false;
		for (Node recurringNode : recurringNodes) {
			EClass type = recurringNode.getType();
			for (Node node : gr.getNodes()) {
				if (node.getType() == type) {
					occurs = true;
					break;
				}
			}
		}
		return !occurs;
	}

	private EList<Node> collectRecurringNodes(Graph gr1, Graph gr2) {
		EList<Node> recurringNodes = new BasicEList<Node>();
		for (Node node1 : gr1.getNodes()) {
			String nodeName = node1.getName();
			EClass type = node1.getType();
			for (Node node2 : gr2.getNodes()) {
				if (node2.getName().equals(nodeName) && node2.getType() == type) {
					recurringNodes.add(node1);
					break;
				}
			}
		}
		return recurringNodes;
	}

	/**
	 * 
	 * @param graph
	 * @param name1
	 * @param name2
	 */
	private void rename(Graph graph, String name1, String name2) {
		String newName = name1 + "=" + name2;
		for (Node node : graph.getNodes()) {
			if (node.getName().equals(name1) || node.getName().equals(name2)) {
				node.setName(newName);
			}
		}
	}

	/**
	 * 
	 * 
	 * @param graph
	 * @param name1
	 * @param name2
	 * @return
	 */
	private boolean containsExactlyOne(Graph graph, String name1, String name2) {
		boolean exists1 = false;
		boolean exists2 = false;
		for (Node node : graph.getNodes()) {
			if (node.getName().equals(name1))
				exists1 = true;
			if (node.getName().equals(name2))
				exists2 = true;
		}
		return ((exists1 && !exists2) || (!exists1 && exists2));
	}

	/**
	 * 
	 * 
	 * @param node
	 * @return
	 */
	private boolean hasTwoNames(Node node) {
		return (node.getName().split("=").length == 2);
	}

	/**
	 * 
	 * 
	 * @param node
	 * @return
	 */
	private boolean hasOneName(Node node) {
		return (node.getName().split("=").length == 1);
	}

	/**
	 * OK
	 * 
	 * @param node1
	 * @param node2
	 * @return
	 */
	private boolean haveSameTypes(Node node1, Node node2) {
		return (node1.getType() == node2.getType());
	}

	/**
	 * 
	 * 
	 * @param graph
	 * @return
	 */
	private boolean hasOnlyOneNode(Graph graph) {
		return (graph.getNodes().size() == 1 && graph.getEdges().isEmpty());
	}

	/**
	 * OK
	 * 
	 * @param gr1
	 * @param intersection
	 * @param gr2
	 * @return
	 */
	private Graph glue(Graph gr1, Intersection intersection, Graph gr2) {
		Graph graph = intersection.getSourceGraph();

		for (Node node : gr1.getNodes()) {
			if (!intersection.containsSource(node)) {
				Node nodeCopy = EcoreUtil.copy(node);
				intersection.addNodeMapping(new NodeMapping(nodeCopy, node));
				graph.getNodes().add(nodeCopy);
			}
		}
		for (Node node : gr2.getNodes()) {
			if (!intersection.containsTarget(node)) {
				Node nodeCopy = EcoreUtil.copy(node);
				intersection.addNodeMapping(new NodeMapping(nodeCopy, node));
				graph.getNodes().add(nodeCopy);
			}
		}

		for (Edge edge : gr1.getEdges()) {
			if (!intersection.containsTarget(edge)) {
				Edge edgeCopy = EcoreUtil.copy(edge);
				edgeCopy.setSource(intersection.getSourceNode1(edge.getSource()));
				edgeCopy.setTarget(intersection.getSourceNode1(edge.getTarget()));
				graph.getEdges().add(edgeCopy);
			}
		}
		for (Edge edge : gr2.getEdges()) {
			if (!intersection.containsTarget(edge)) {
				Edge edgeCopy = EcoreUtil.copy(edge);
				edgeCopy.setSource(intersection.getSourceNode(edge.getSource()));
				edgeCopy.setTarget(intersection.getSourceNode(edge.getTarget()));
				graph.getEdges().add(edgeCopy);
			}
		}
		return graph;
	}

	private EList<Intersection> getIntersections(Graph gr1, Graph gr2) {
		EList<Intersection> intersections = new BasicEList<Intersection>();
		EList<Graph> subGraphs = new BasicEList<Graph>();
		fillSubGraphs(subGraphs, gr1);
		for (Graph graph : subGraphs) {
			if (isSubGraph(graph, gr2)) {
				intersections.add(getIntersection(graph, gr2));
			}
		}
		return intersections;
	}

	private Intersection getIntersection(Graph subGraph, Graph graph) {
		Intersection intersection = new Intersection();
		for (Node n1 : subGraph.getNodes()) {
			for (Node n2 : graph.getNodes()) {
				if (n1.getType() == n2.getType() && n1.getName().equals(n2.getName())) {
					intersection.addNodeMapping(new NodeMapping(n1, n2));
				}
			}
		}
		for (Edge e1 : subGraph.getEdges()) {
			Node src1 = e1.getSource();
			NodeMapping srcMapping = getMapping(intersection.getNodeMappings(), src1);
			Node tgt1 = e1.getTarget();
			NodeMapping tgtMapping = getMapping(intersection.getNodeMappings(), tgt1);
			for (Edge e2 : graph.getEdges()) {
				Node src2 = e2.getSource();
				Node tgt2 = e2.getTarget();
				if (e1.getType() == e2.getType() && srcMapping.getTargetNode() == src2
						&& tgtMapping.getTargetNode() == tgt2) {
					intersection.addEdgeMapping(new EdgeMapping(e1, e2));
					break;
				}
			}
		}
		return intersection;
	}

	private void fillSubGraphs(EList<Graph> subGraphs, Graph graph) {
		EList<Edge> edges = cloneEdges(graph);
		for (Edge edge : edges) {
			graph.getEdges().remove(edge);
			Graph gr = EcoreUtil.copy(graph);
			graph.getEdges().add(edge);
			testAndFill(subGraphs, gr);
		}
		EList<Node> nodes = cloneNodes(graph);
		for (Node node : nodes) {
			if (!isConnected(node, graph)) {
				graph.getNodes().remove(node);
				Graph gr = EcoreUtil.copy(graph);
				graph.getNodes().add(node);
				if (!isEmptyGraph(gr)) {
					testAndFill(subGraphs, gr);
				}
			}
		}
	}

	private boolean isEmptyGraph(Graph graph) {
		return (graph.getNodes().isEmpty() && graph.getEdges().isEmpty());
	}

	private boolean isConnected(Node node, Graph graph) {
		for (Edge edge : graph.getEdges()) {
			if (edge.getSource() == node)
				return true;
			if (edge.getTarget() == node)
				return true;
		}
		return false;
	}

	private void testAndFill(EList<Graph> graphs, Graph graph) {
		if (!contains(graphs, graph)) {
			graphs.add(graph);
			fillSubGraphs(graphs, graph);
		}
	}

	private boolean contains(EList<Graph> graphs, Graph graph) {
		for (Graph gr : graphs) {
			if (isSameGraph(gr, graph)) {
				return true;
			}
		}
		return false;
	}

	private boolean isSameGraph(Graph gr1, Graph gr2) {
		return (isSubGraph(gr1, gr2) && isSubGraph(gr2, gr1));
	}

	private EList<Node> cloneNodes(Graph graph) {
		EList<Node> nodes = new BasicEList<Node>();
		for (Node node : graph.getNodes()) {
			nodes.add(node);
		}
		return nodes;
	}

	private EList<Edge> cloneEdges(Graph graph) {
		EList<Edge> edges = new BasicEList<Edge>();
		for (Edge edge : graph.getEdges()) {
			edges.add(edge);
		}
		return edges;
	}

	/**
	 * 
	 * 
	 * @param gr1
	 * @param gr2
	 * @return
	 */
	private boolean areNodeNameDisjoint(Graph gr1, Graph gr2) {
		List<String> names1 = getNodeNames(gr1);
		List<String> names2 = getNodeNames(gr2);
		for (String str1 : names1) {
			for (String str2 : names2) {
				if (str1.equals(str2)) {
					return false;
				}
			}
		}
		return true;
	}

	private List<String> getNodeNames(Graph graph) {
		List<String> names = new ArrayList<String>();
		for (Node node : graph.getNodes()) {
			names.addAll(getNames(node.getName()));
		}
		return names;
	}

	private List<String> getNames(String name) {
		String[] names = name.split("=");
		return Arrays.asList(names);
	}

	/**
	 * 
	 * 
	 * @param gr1
	 * @param gr2
	 * @return
	 */
	private boolean haveIntersection(Graph gr1, Graph gr2) {
		EList<Intersection> intersections = getIntersections(gr1, gr2);
		return !intersections.isEmpty();
	}

	/**
	 * 
	 * @param gr1
	 * @param gr2
	 * @return
	 */
	private boolean areClanDisjoint(Graph gr1, Graph gr2) {
		for (Node n1 : gr1.getNodes()) {
			EList<EClass> clan1 = getClan(n1.getType());
			for (Node n2 : gr2.getNodes()) {
				if (clan1.contains(n2.getType())) {
					return false;
				}
			}
		}
		for (Node n2 : gr2.getNodes()) {
			EList<EClass> clan2 = getClan(n2.getType());
			for (Node n1 : gr1.getNodes()) {
				if (clan2.contains(n1.getType())) {
					return false;
				}
			}
		}
		return true;
	}

	private EList<EClass> getClan(EClass eClass) {
		EList<EClass> eClasses = new BasicEList<EClass>();
		eClasses.add(eClass);
		eClasses.addAll(getAllSubclasses(eClass));
		return eClasses;
	}

	private EList<EClass> getAllSubclasses(EClass eClass) {
		EList<EClass> eClasses = new BasicEList<EClass>();
		EPackage ePackage = eClass.getEPackage();
		TreeIterator<EObject> iter = ePackage.eAllContents();
		while (iter.hasNext()) {
			EObject eObject = iter.next();
			if (eObject instanceof EClass) {
				EClass clazz = (EClass) eObject;
				if (clazz.getEAllSuperTypes().contains(eClass)) {
					eClasses.add(clazz);
				}
			}
		}
		return eClasses;
	}

	/**
	 * 
	 * 
	 * @param graphA
	 * @param graphB
	 * @return
	 */
	public boolean isSubGraph(Graph graphA, Graph graphB) {
		HashMap<Node, Node> mappingNodeA2NodeB = new HashMap<Node, Node>();
		for (Node nodeA : graphA.getNodes()) {
			for (Node nodeB : graphB.getNodes()) {
				if (nodeA.getType() == nodeB.getType()) {
					if (getNames(nodeB.getName()).containsAll(getNames(nodeA.getName()))) {

						// TODO check it attribute
						if (nodeB.getAttributes().containsAll(nodeA.getAttributes())) {
							boolean attISO = true;
							for (Attribute a : nodeA.getAttributes()) {
								if (getBy(a, nodeB.getAttributes()) == null)
									attISO = false;
							}
							if (attISO)
								mappingNodeA2NodeB.put(nodeA, nodeB);
						}

					}
				}
			}
		}
		if (graphA.getNodes().size() != mappingNodeA2NodeB.size()) {
			return false;
		}
		for (Edge edgeA : graphA.getEdges()) {
			Node srcEdgeA = edgeA.getSource();
			Node tgtEdgeA = edgeA.getTarget();

			boolean edgeIsMapped = false;
			for (Edge edgeB : graphB.getEdges()) {
				Node srcEdgeB = edgeB.getSource();
				Node tgtEdgeB = edgeB.getTarget();
				if (edgeA.getType() == edgeB.getType() && mappingNodeA2NodeB.get(srcEdgeA) == srcEdgeB
						&& mappingNodeA2NodeB.get(tgtEdgeA) == tgtEdgeB) {
					edgeIsMapped = true;
					break;
				}
			}
			if (!edgeIsMapped) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * 
	 * @param a
	 * @param ls
	 * @return
	 */
	private Attribute getBy(Attribute a, EList<Attribute> ls) {
		for (Attribute b : ls) {
			if (b.getType() == a.getType() && b.getValue() == a.getValue()) {
				return b;
			}
		}

		return null;
	}

	private NodeMapping getMapping(EList<NodeMapping> nodeMappings, Node node) {
		for (NodeMapping nm : nodeMappings) {
			if (nm.getSourceNode() == node) {
				return nm;
			}
		}
		return null;
	}

	private boolean isLeaf(NestedCondition nestedCondition) {
		if (nestedCondition.getConclusion() == null)
			return true;
		else if (nestedCondition.getConclusion() != null && nestedCondition.getConclusion().getFormula() == null)
			return true;
		return false;
	}

	private boolean isAndFormula(EObject eObject) {
		if (eObject instanceof And)
			return true;
		else
			return false;
	}

	private boolean isExistsCondition(EObject eObject) {
		if (eObject instanceof NestedCondition)
			return true;
		else
			return false;
	}
}
