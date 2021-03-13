/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package laxcondition.util.extensions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

import graph.Edge;
import graph.Graph;
import graph.Node;
import laxcondition.Condition;
import laxcondition.Formula;
import laxcondition.LaxCondition;
import laxcondition.LaxconditionFactory;
import laxcondition.Operator;
import laxcondition.QuantifiedLaxCondition;
import laxcondition.Quantifier;
import laxcondition.True;

public class LaxConditionSimplifier {

	private Condition condition;
	private LaxconditionFactory factory;

	public LaxConditionSimplifier(Condition condition) {
		this.condition = condition;
		this.factory = LaxconditionFactory.eINSTANCE;
	}

	public boolean simplify() {
		if (simplifyNames()) {
			return simplify();
		}
		if (simplifyTrueOrFalseInFormula()) {
			return simplify();
		}
		if (simplifyNotNot()) {
			return simplify();
		}

		if (simplifyExists()) {
			return simplify();
		}
		return true;
	}

	private boolean insert(EObject container, LaxCondition oldContent, LaxCondition newContent) {
		if (container instanceof Condition) {
			Condition constraint = (Condition) container;
			constraint.setLaxCondition(newContent);
			return true;
		}
		if (container instanceof QuantifiedLaxCondition) {
			QuantifiedLaxCondition qlc = (QuantifiedLaxCondition) container;
			qlc.setCondition(newContent);
			return true;
		}
		if (container instanceof Formula) {
			Formula f = (Formula) container;
			int index = f.getArguments().indexOf(oldContent);
			f.getArguments().set(index, newContent);
			return true;
		}
		return false;
	}

	private boolean simplifyNames() {
		TreeIterator<EObject> iter1 = condition.eAllContents();
		while (iter1.hasNext()) {
			EObject eObject1 = iter1.next();
			if (eObject1 instanceof Node) {
				Node node1 = (Node) eObject1;
				if (node1.getNames().size() > 1) {
					for (String name : node1.getNames()) {
						boolean onlyOnce = true;
						TreeIterator<EObject> iter2 = condition.eAllContents();
						while (iter2.hasNext()) {
							EObject eObject2 = iter2.next();
							if (eObject2 instanceof Node) {
								Node node2 = (Node) eObject2;
								if (node1 != node2 && node2.getNames().contains(name)) {
									onlyOnce = false;
									break;
								}
							}
						}
						if (onlyOnce) {
							node1.removeName(name);
							System.out.println("Removing name: " + name);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private boolean simplifyExists() {
		TreeIterator<EObject> iter = condition.eAllContents();
		while (iter.hasNext()) {
			EObject eObject = iter.next();
			if (isExistsCondition(eObject)) {
				QuantifiedLaxCondition outerCondition = (QuantifiedLaxCondition) eObject;
				LaxCondition cond = outerCondition.getCondition();
				if (isExistsCondition(cond)) {
					QuantifiedLaxCondition innerCondition = (QuantifiedLaxCondition) cond;
					if (isTrue(innerCondition.getCondition())) {
						Graph outerGraph = outerCondition.getGraph();
						Graph innerGraph = innerCondition.getGraph();
						if (isSubGraph(outerGraph, innerGraph)) {
							outerCondition.setGraph(innerGraph);
							outerCondition.setCondition(factory.createTrue());
							System.out.println("Equivalence E1.c");
							return true;
						}
						if (isSubGraph(innerGraph, outerGraph)) {
							outerCondition.setCondition(factory.createTrue());
							System.out.println("Equivalence E1.c");
							return true;
						}
						if (areClanDisjoint(innerGraph, outerGraph)) {
							EList<Node> innerNodes = innerGraph.getNodes();
							innerGraph.getNodes().clear();
							outerGraph.getNodes().addAll(innerNodes);
							EList<Edge> innerEdges = innerGraph.getEdges();
							innerGraph.getEdges().clear();
							outerGraph.getEdges().addAll(innerEdges);
							outerCondition.setCondition(factory.createTrue());
							System.out.println("Equivalence E1.b");
							return true;
						}
						if (haveIntersection(innerGraph, outerGraph)) {
							EList<Intersection> intersections = getIntersections(outerGraph, innerGraph);
							EObject container = outerCondition.eContainer();
							if (intersections.size() == 1) {
								QuantifiedLaxCondition condition = factory.createQuantifiedLaxCondition();
								condition.setQuantifier(Quantifier.EXISTS);
								condition.setGraph(glue(outerGraph, intersections.get(0), innerGraph));
								condition.setCondition(factory.createTrue());
								insert(container, outerCondition, condition);
								System.out.println("Equivalence E1.a");
								return true;
							}
							Formula formula = factory.createFormula();
							formula.setOp(Operator.OR);
							for (Intersection intersection : intersections) {
								QuantifiedLaxCondition condition = factory.createQuantifiedLaxCondition();
								condition.setQuantifier(Quantifier.EXISTS);
								condition.setGraph(
										glue(outerCondition.getGraph(), intersection, innerCondition.getGraph()));
								condition.setCondition(factory.createTrue());
								formula.getArguments().add(condition);
							}
							insert(container, outerCondition, formula);
							System.out.println("Equivalence E1.a");
							return true;
						}
						if (hasOnlyOneNode(innerGraph)) {
							Node innerNode = innerGraph.getNodes().get(0);
							if (hasTwoNames(innerNode)) {
								String name1 = innerNode.getNames().get(0);
								String name2 = innerNode.getNames().get(1);
								if (containsExactlyOne(outerGraph, name1, name2)) {
									rename(outerGraph, name1, name2);
									outerCondition.setCondition(factory.createTrue());
									System.out.println("Equivalence E3'");
									return true;
								}
							}
						}
					}
				}
				if (isAndFormula(cond)) {
					Formula andFormula = (Formula) cond;
					if (andFormula.getArguments().size() == 2) {
						if (isExistsCondition(andFormula.getArguments().get(0))
								&& isExistsCondition(andFormula.getArguments().get(1))) {
							QuantifiedLaxCondition cond1 = (QuantifiedLaxCondition) andFormula.getArguments().get(0);
							QuantifiedLaxCondition cond2 = (QuantifiedLaxCondition) andFormula.getArguments().get(1);
							if (cond1.getCondition() instanceof True && cond2.getCondition() instanceof True) {
								Graph gr = outerCondition.getGraph();
								Graph gr1 = cond1.getGraph();
								Graph gr2 = cond2.getGraph();
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
												outerCondition.setCondition(cond1);
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
												outerCondition.setCondition(cond2);
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
												QuantifiedLaxCondition condition = factory
														.createQuantifiedLaxCondition();
												condition.setQuantifier(Quantifier.EXISTS);
												condition.setGraph(glue(gr1, intersections.get(0), gr2));
												condition.setCondition(factory.createTrue());
												outerCondition.setCondition(condition);
												System.out.println("Equivalence E2.a");
												return true;
											}
											Formula formula = factory.createFormula();
											formula.setOp(Operator.OR);
											for (Intersection intersection : intersections) {
												QuantifiedLaxCondition condition = factory
														.createQuantifiedLaxCondition();
												condition.setQuantifier(Quantifier.EXISTS);
												condition.setGraph(glue(gr1, intersection, gr2));
												condition.setCondition(factory.createTrue());
												formula.getArguments().add(condition);
											}
											outerCondition.setCondition(formula);
											System.out.println("Equivalence E2.a");
											return true;
										}
									}
								}
							}
						}
					}
				}
			}
			if (isAndFormula(eObject)) {
				Formula andFormula = (Formula) eObject;
				if (andFormula.getArguments().size() == 2) {
					if (isExistsCondition(andFormula.getArguments().get(0))
							&& isExistsCondition(andFormula.getArguments().get(1))) {
						QuantifiedLaxCondition cond1 = (QuantifiedLaxCondition) andFormula.getArguments().get(0);
						QuantifiedLaxCondition cond2 = (QuantifiedLaxCondition) andFormula.getArguments().get(1);
						if (cond1.getCondition() instanceof True && cond2.getCondition() instanceof True) {
							Graph gr1 = cond1.getGraph();
							Graph gr2 = cond2.getGraph();
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
				}

			}
		}
		return false;
	}

	private boolean haveRecurringNodes(Graph gr1, Graph gr2) {
		EList<Node> recurringNodes = collectRecurringNodes(gr1, gr2);
		return (!recurringNodes.isEmpty());
	}

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

	private void rename(Graph graph, String name1, String name2) {
		String newName = name1 + "=" + name2;
		for (Node node : graph.getNodes()) {
			if (node.getName().equals(name1) || node.getName().equals(name2)) {
				node.setName(newName);
			}
		}
	}

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

	private boolean hasTwoNames(Node node) {
		return (node.getName().split("=").length == 2);
	}

	private boolean hasOneName(Node node) {
		return (node.getName().split("=").length == 1);
	}

	private boolean haveSameTypes(Node node1, Node node2) {
		return (node1.getType() == node2.getType());
	}

	private boolean hasOnlyOneNode(Graph graph) {
		return (graph.getNodes().size() == 1 && graph.getEdges().isEmpty());
	}

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
		graph.setTypegraph(gr1.getTypegraph());
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
			names.addAll(node.getNames());
		}
		return names;
	}

	private boolean haveIntersection(Graph gr1, Graph gr2) {
		EList<Intersection> intersections = getIntersections(gr1, gr2);
		return !intersections.isEmpty();
	}

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

	private boolean isSubGraph(Graph gr1, Graph gr2) {
		EList<NodeMapping> nodeMappings = new BasicEList<NodeMapping>();
		for (Node n1 : gr1.getNodes()) {
			for (Node n2 : gr2.getNodes()) {
				if (n1.getType() == n2.getType()) {
					if (n2.getNames().containsAll(n1.getNames())) {
						nodeMappings.add(new NodeMapping(n1, n2));
					}
				}
			}
		}
		if (gr1.getNodes().size() != nodeMappings.size()) {
			return false;
		}
		for (Edge e1 : gr1.getEdges()) {
			Node src1 = e1.getSource();
			NodeMapping srcMapping = getMapping(nodeMappings, src1);
			Node tgt1 = e1.getTarget();
			NodeMapping tgtMapping = getMapping(nodeMappings, tgt1);
			boolean hasTarget = false;
			for (Edge e2 : gr2.getEdges()) {
				Node src2 = e2.getSource();
				Node tgt2 = e2.getTarget();
				if (e1.getType() == e2.getType() && srcMapping.getTargetNode() == src2
						&& tgtMapping.getTargetNode() == tgt2) {
					hasTarget = true;
					break;
				}
			}
			if (!hasTarget) {
				return false;
			}
		}
		return true;
	}

	private NodeMapping getMapping(EList<NodeMapping> nodeMappings, Node node) {
		for (NodeMapping nm : nodeMappings) {
			if (nm.getSourceNode() == node) {
				return nm;
			}
		}
		return null;
	}

	private boolean simplifyTrueOrFalseInFormula() {
		TreeIterator<EObject> iter = condition.eAllContents();
		while (iter.hasNext()) {
			EObject eObject = iter.next();
			if (isAndFormula(eObject)) {
				Formula formula = (Formula) eObject;
				EObject container = formula.eContainer();
				LaxCondition arg1 = formula.getArguments().get(0);
				LaxCondition arg2 = formula.getArguments().get(1);
				if (isTrue(arg1)) {
					if (twoArguments(formula)) {
						return insert(container, formula, arg2);
					} else {
						return removeArgument(formula, arg1);
					}
				}
				if (isTrue(arg2)) {
					if (twoArguments(formula)) {
						return insert(container, formula, arg1);
					} else {
						return removeArgument(formula, arg2);
					}
				}
				if (isFalse(arg1)) {
					if (twoArguments(formula)) {
						return insert(container, formula, arg1);
					} else {
						return removeArgument(formula, arg2);
					}
				}
				if (isFalse(arg2)) {
					if (twoArguments(formula)) {
						return insert(container, formula, arg2);
					} else {
						return removeArgument(formula, arg1);
					}
				}
			}
			if (isOrFormula(eObject)) {
				Formula formula = (Formula) eObject;
				EObject container = formula.eContainer();
				LaxCondition arg1 = formula.getArguments().get(0);
				LaxCondition arg2 = formula.getArguments().get(1);
				if (isTrue(arg1)) {
					if (twoArguments(formula)) {
						return insert(container, formula, arg1);
					} else {
						return removeArgument(formula, arg2);
					}
				}
				if (isTrue(arg2)) {
					if (twoArguments(formula)) {
						return insert(container, formula, arg2);
					} else {
						return removeArgument(formula, arg1);
					}
				}
				if (isFalse(arg1)) {
					if (twoArguments(formula)) {
						return insert(container, formula, arg2);
					} else {
						return removeArgument(formula, arg1);
					}
				}
				if (isFalse(arg2)) {
					if (twoArguments(formula)) {
						return insert(container, formula, arg1);
					} else {
						return removeArgument(formula, arg2);
					}
				}
			}
		}
		return false;
	}

	private boolean removeArgument(Formula formula, LaxCondition cond) {
		return formula.getArguments().remove(cond);
	}

	private boolean twoArguments(Formula formula) {
		return (formula.getArguments().size() == 2);
	}

	private boolean isFalse(LaxCondition cond) {
		if (isNotFormula(cond)) {
			Formula formula = (Formula) cond;
			return isTrue(formula.getArguments().get(0));
		}
		return false;
	}

	private boolean isTrue(LaxCondition cond) {
		return (cond instanceof True);
	}

	private boolean simplifyNotNot() {
		TreeIterator<EObject> iter = condition.eAllContents();
		while (iter.hasNext()) {
			EObject eObject = iter.next();
			if (isNotFormula(eObject)) {
				Formula formula = (Formula) eObject;
				if (isNotFormula(formula.getArguments().get(0))) {
					Formula innerFormula = (Formula) formula.getArguments().get(0);
					LaxCondition innerCondition = innerFormula.getArguments().get(0);
					EObject container = formula.eContainer();
					return insert(container, formula, innerCondition);
				}
			}
		}
		return false;
	}

	private boolean isNotFormula(EObject eObject) {
		if (eObject instanceof Formula) {
			Formula formula = (Formula) eObject;
			return (formula.getOp().equals(Operator.NOT));
		}
		return false;
	}

	private boolean isAndFormula(EObject eObject) {
		if (eObject instanceof Formula) {
			Formula formula = (Formula) eObject;
			return (formula.getOp().equals(Operator.AND));
		}
		return false;
	}

	private boolean isOrFormula(EObject eObject) {
		if (eObject instanceof Formula) {
			Formula formula = (Formula) eObject;
			return (formula.getOp().equals(Operator.OR));
		}
		return false;
	}

	private boolean isExistsCondition(EObject eObject) {
		if (eObject instanceof QuantifiedLaxCondition) {
			QuantifiedLaxCondition cond = (QuantifiedLaxCondition) eObject;
			return (cond.getQuantifier().equals(Quantifier.EXISTS));
		}
		return false;
	}
}
