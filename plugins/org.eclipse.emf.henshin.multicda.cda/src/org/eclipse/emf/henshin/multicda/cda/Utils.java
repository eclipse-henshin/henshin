package org.eclipse.emf.henshin.multicda.cda;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.interpreter.util.HenshinEGraph;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Action.Type;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;
import org.eclipse.emf.henshin.multicda.cda.units.Span;
import org.eclipse.emf.henshin.multicda.cda.units.SymmetricReason;
import org.eclipse.emf.henshin.multicda.cpa.result.Conflict;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalElement;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;
import org.eclipse.emf.henshin.multicda.cpa.result.Dependency;
import org.eclipse.emf.henshin.preprocessing.RulePair;

public abstract class Utils {
	private static final String INVERTED_TAG = "_Inv";
	private static HenshinFactory factory = HenshinFactory.eINSTANCE;

	public static Rule invertRule(Rule rule1) {

		Copier ruleC = new Copier();
		Rule invRule = (Rule) ruleC.copy(rule1);
		ruleC.copyReferences();

		String d = rule1.getDescription();
		invRule.setDescription((d == null ? "" : d) + INVERTED_TAG);

		Graph newLhs = invRule.getRhs();
		Graph newRhs = invRule.getLhs();

		newLhs.setName("LHS");
		invRule.setLhs(newLhs);
		newRhs.setName("RHS");
		invRule.setRhs(newRhs);

		MappingList mappings = invRule.getMappings();
		for (Mapping mapping : mappings) {
			Node original = mapping.getOrigin();
			Node image = mapping.getImage();
			mapping.setImage(original);
			mapping.setOrigin(image);
		}
		return invRule;
	}

	public static Rule invertRuleForForbid(Rule rule1) {
		Rule invRule = invertRule(rule1);
		for (Node n : invRule.getRhs().getNodes())
			if (invRule.getMappings().getOrigin(n) == null) {
				Node lhsNode = HenshinFactory.eINSTANCE.createNode(invRule.getLhs(), n.getType(), n.getName());
				invRule.getMappings().add(HenshinFactory.eINSTANCE.createMapping(lhsNode, n));
				for (Attribute a : n.getAttributes())
					HenshinFactory.eINSTANCE.createAttribute(lhsNode, a.getType(), a.getValue());
			}
		for (Edge e : invRule.getRhs().getEdges()) {
			Node lhsSource = invRule.getMappings().getOrigin(e.getSource());
			Node lhsTarget = invRule.getMappings().getOrigin(e.getTarget());
			if (lhsSource.getOutgoing(e.getType(), lhsTarget) == null)
				HenshinFactory.eINSTANCE.createEdge(lhsSource, lhsTarget, e.getType());
		}
		return invRule;
	}

	public static List<RulePair> prepareNonDeletingVersions(List<Rule> rules) {
		List<RulePair> copiesOfRulesWithoutDeletion = new LinkedList<RulePair>();
		for (Rule delete : rules) {
			Copier copier = new Copier();
			Rule nonDelete = (Rule) copier.copy(delete);
			String d = nonDelete.getDescription();
			nonDelete.setDescription((d == null ? "" : d) + "_NonDelete");
			copier.copyReferences();

			for (Node n : nonDelete.getActionNodes(new Action(Action.Type.DELETE))) {
				Node image = HenshinFactory.eINSTANCE.createNode(nonDelete.getRhs(), n.getType(), n.getName());
				nonDelete.getMappings().add(HenshinFactory.eINSTANCE.createMapping(n, image));
			}
			Map<Node, Set<Pair<Attribute, Attribute>>> nodes = getAttributeChanges(nonDelete);
			for (Node node : nodes.keySet()) {
				Set<Pair<Attribute, Attribute>> attributes = nodes.get(node);
				for (Pair<Attribute, Attribute> attribute : attributes)
					if (attribute.second == null)
						HenshinFactory.eINSTANCE.createAttribute(nonDelete.getMappings().getImage(node, null),
								attribute.first.getType(), attribute.first.getValue());
			}
			for (Edge e : nonDelete.getActionEdges(new Action(Action.Type.DELETE))) {
				Node s = nonDelete.getMappings().getImage(e.getSource(), null);
				Node t = nonDelete.getMappings().getImage(e.getTarget(), null);
				nonDelete.getRhs().getEdges().add(HenshinFactory.eINSTANCE.createEdge(s, t, e.getType()));
			}
			copiesOfRulesWithoutDeletion.add(new RulePair(nonDelete, delete));

		}
		return copiesOfRulesWithoutDeletion;
	}

	public static List<Rule> prepareNoneDeletingsVersionsRules(List<Rule> rules) {
		List<Rule> result = new ArrayList<Rule>();
		List<RulePair> pairs = prepareNonDeletingVersions(rules);
		for (RulePair rulePair : pairs) {
			result.add(rulePair.getCopy());
		}
		return result;
	}

	public static Rule nonDeleteRule(Rule rule) {
		List<Rule> result = new ArrayList<Rule>();
		result.add(rule);
		List<RulePair> pairs = prepareNonDeletingVersions(result);
		return pairs.get(0).getCopy();
	}

	public static Set<Rule> createNACRules(Rule rule1) {
		return getNestedRules(rule1, true);
	}

	public static Set<Rule> createPACRules(Rule rule1) {
		return getNestedRules(rule1, false);
	}

	private final static String NAC_TAG = "NAC";
	private final static String PAC_TAG = "PAC";

	public static Set<Rule> getNestedRules(Rule rule, boolean isNac) {
		int id = 0;
		int nodeId = 0;
		Set<Rule> result = new HashSet<>();
		for (NestedCondition nac : isNac ? rule.getLhs().getNACs() : rule.getLhs().getPACs()) {
			Rule ncRule = factory.createRule(rule.getName());
			String d = ncRule.getDescription();
			String nested = PAC_TAG;
			if (isNac)
				nested = NAC_TAG;
			ncRule.setDescription((d == null ? "" : d) + "_" + nested + "rule_" + id++);

			Copier cL = new Copier();
			Graph newLhs = (Graph) cL.copy(rule.getLhs());
			cL.copyReferences();
			if (isNac)
				newLhs = factory.createGraph();
			newLhs.setDescription(": this is the " + nested + " of the Original Rule");

			Copier cR = new Copier();
			Graph newRhs = (Graph) cR.copy(rule.getRhs());
			cR.copyReferences();
			if (isNac)
				newRhs = factory.createGraph();
			newRhs.setDescription(": this is the " + nested + " of the Original Rule");

			ncRule.setLhs(newLhs);
			ncRule.setRhs(newRhs);
			Map<Parameter, Parameter> parameterMap = new HashMap<>();
			for (Parameter p : rule.getParameters()) {
				parameterMap.put(p, factory.createParameter(p.getName()));
				ncRule.getParameters().add(parameterMap.get(p));
			}
			for (ParameterMapping pm : rule.getParameterMappings()) {
				ParameterMapping pM = factory.createParameterMapping();
				pM.setSource(parameterMap.get(pm.getSource()));
				pM.setTarget(parameterMap.get(pm.getTarget()));
				ncRule.getParameterMappings().add(pM);
			}

			Map<Node, Node> map = new HashMap<>();
			if (isNac) {
				for (Node n : rule.getLhs().getNodes()) {
					Node newLhsNode = factory.createNode(newLhs, n.getType(), n.getName());
					Node newRhsNode = factory.createNode(newRhs, n.getType(), n.getName());
					map.put(n, newLhsNode);
					Mapping createdMapping = factory.createMapping(newLhsNode, newRhsNode);
					ncRule.getMappings().add(createdMapping);
					ncRule.getMappings().add(factory.createMapping(n, newLhsNode));
				}
			} else
				for (Node n : rule.getLhs().getNodes()) {
					Node newL = (Node) cL.get(n);
					Node nR = rule.getMappings().getImage(n, null);
					ncRule.getMappings().add(factory.createMapping(n, newL));
					map.put(n, newL);
					if (nR != null) {
						Node newR = (Node) cR.get(nR);
						ncRule.getMappings().add(factory.createMapping(newL, newR));
					}
				}
			for (Edge e : rule.getLhs().getEdges()) {
				Node source = map.get(e.getSource());
				Node target = map.get(e.getTarget());
				if (source != null && target != null && source.getOutgoing(e.getType(), target) == null) {
					factory.createEdge(source, target, e.getType());
					factory.createEdge(ncRule.getMappings().getImage(source, null),
							ncRule.getMappings().getImage(target, null), e.getType());
				}
			}

			for (Node fn : nac.getConclusion().getNodes()) {
				Node fOrigin = nac.getMappings().getOrigin(fn);
				Node FNL = null;
				Node FNR = null;
				if (fOrigin != null) {
					FNL = map.get(fOrigin);
					FNR = ncRule.getMappings().getImage(FNL, null);
				} else {
					char type = isNac ? 'f' : 'r';
					String name = fn.getName();
					if (name == null) {
						name = "|" + type + nodeId + "|";
						nodeId++;
					}

					FNL = factory.createNode(newLhs, fn.getType(), name);
					FNR = factory.createNode(newRhs, fn.getType(), name);
					Mapping createdMapping = factory.createMapping(FNL, FNR);
					ncRule.getMappings().add(createdMapping);
				}
				map.put(fn, FNL);
				if (!isNac)
					for (Attribute a : fn.getAttributes()) {
						if (FNL.getAttribute(a.getType()) == null) {
							factory.createAttribute(FNL, a.getType(), a.getValue());
							factory.createAttribute(FNR, a.getType(), a.getValue());
						}
					}
			}
			for (Edge fe : nac.getConclusion().getEdges()) {
				Node source = map.get(fe.getSource());
				Node target = map.get(fe.getTarget());
				if (source != null && target != null && source.getOutgoing(fe.getType(), target) == null) {
					factory.createEdge(source, target, fe.getType());
					if (ncRule.getMappings().getImage(source, null) != null
							&& ncRule.getMappings().getImage(target, null) != null)
						factory.createEdge(ncRule.getMappings().getImage(source, null),
								ncRule.getMappings().getImage(target, null), fe.getType());
				}
			}
			result.add(ncRule);
		}
		return result;
	}

	public static Node addNodeToGraph(Node nodeInRule1, Node nodeInRule2, Graph S1, Set<Mapping> rule1Mappings,
			Set<Mapping> rule2Mappings) {
		EClass subNodeType = identifySubNodeType(nodeInRule1, nodeInRule2);
		Node commonNode = factory.createNode(S1, subNodeType,
				nodeInRule1.getName() + Reason.NODE_SEPARATOR + nodeInRule2.getName());

		rule1Mappings.add(factory.createMapping(commonNode, nodeInRule1));
		rule2Mappings.add(factory.createMapping(commonNode, nodeInRule2));
		return commonNode;
	}

	public static boolean nodeTypeEqual(Node node1, Node node2) {
		return identifySubNodeType(node1, node2) != null;
	}

	/**
	 * identify the type of the both nodes which is the subtype of the other node.
	 * 
	 * @param node1
	 * @param node2
	 * @return returns the subnode type if one of both is, otherwise null.
	 */
	public static EClass identifySubNodeType(Node node1, Node node2) {
		if (node1 == null || node2 == null)
			return null;
		if (node1.getType() == node2.getType() || node1.getType().getEAllSuperTypes().contains(node2.getType()))
			return node1.getType();
		if (node2.getType().getEAllSuperTypes().contains(node1.getType()))
			return node2.getType();
		return null;
	}

	public static void checkNull(Object o) throws IllegalArgumentException {
		checkNull(o, "object");
	}

	public static void checkNull(Object o, String name) throws IllegalArgumentException {
		if (null == o)
			throw new IllegalArgumentException(name + " must not be null");
	}

	public boolean isRuleSupported(Rule rule) {
		if (rule.getMultiRules().size() > 0) {
			if (rule.getLhs().getPACs().size() > 0)
				throw new RuntimeException("positive application conditions (PAC) are not supported");
		}
		return true;
	}

	public static void removeRedundantNodes(Graph graph2Copy, Collection<Mapping> mappingsS2Rule1Copies,
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

	public static Map<Node, Node> getS2toS1Map(Reason span1, Reason span2) {
		Map<Node, Node> s2ToS1 = new HashMap<Node, Node>();
		for (Node n1 : span1.getGraph().getNodes()) {
			for (Node n2 : span2.getGraph().getNodes()) {
				if (identifySubNodeType(n1, n2) != null) {
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

	public static Map<CriticalPair, Reason> compare(Set<CriticalPair> cps, Set<Reason> reasons) {
		Map<CriticalPair, Reason> result = new HashMap<>();
		if (reasons == null || cps == null)
			return result;
		for (CriticalPair cp : cps)
			for (Reason reason : reasons) {
				boolean foundElement = false;
				if (reason instanceof SymmetricReason)
					reason = ((SymmetricReason) reason).getS1();
				if ((cp instanceof Conflict && reason instanceof ConflictReason)
						|| (cp instanceof Dependency && reason instanceof DependencyReason)) {
					List<CriticalElement> delCE = cp.getCriticalElements();
					Set<GraphElement> delRE = reason.getDeletionElementsInRule1_2();
					if (delCE.size() == delRE.size())
						for (GraphElement d1 : delRE) {
							foundElement = false;
							for (CriticalElement d2 : delCE) {
								if ((d1 instanceof Node && d2.elementInFirstRule instanceof Node)
										|| (d1 instanceof Node && d2.elementInFirstRule instanceof Attribute)
										|| (d1 instanceof Edge && d2.elementInFirstRule instanceof Edge)) {
									if (d1.toString().equals(d2.toString())) {
										foundElement = true;
										break;
									}
								}
							}
						}
					if (foundElement) {
						result.put(cp, reason);
						break;
					}

				}
			}
		return result;

	}

	public static Set<DanglingEdge> findDanglingEdgesOfRule1(Rule rule, List<Mapping> embedding) {
		HashMap<Node, Node> mapL1toG = new HashMap<Node, Node>();
		HashMap<Node, Node> mapGtoL1 = new HashMap<Node, Node>();
		for (Mapping mapping : embedding) {
			mapL1toG.put(mapping.getOrigin(), mapping.getImage());
			mapGtoL1.put(mapping.getImage(), mapping.getOrigin());
		}

		Set<DanglingEdge> danglingEdges = new HashSet<DanglingEdge>();
		EList<Node> l1DeletingNodes = rule.getActionNodes(new Action(Action.Type.DELETE));
		for (Node l1Deleting : l1DeletingNodes) {

			Node poDeleting = mapL1toG.get(l1Deleting);

			// Since the pushout can have parallel edges with the same type, source,
			// and target, we compare the number of parallel edges.
			for (Edge edgePO : poDeleting.getOutgoing()) {
				Node l1DelTarget = mapGtoL1.get(edgePO.getTarget());
				int parallelPO = getParallelEdges(poDeleting, edgePO.getTarget(), edgePO.getType()).size();
				int parallelLhs1 = getParallelEdges(l1Deleting, l1DelTarget, edgePO.getType()).size();
				if (l1DelTarget == null)
					danglingEdges.add(new DanglingEdge(edgePO, DanglingCase.targetDangling));
				else if (parallelLhs1 < parallelPO)
					danglingEdges.add(new DanglingEdge(edgePO, DanglingCase.unspecifiedEdge));

			}
			for (Edge edgePO : poDeleting.getIncoming()) {
				Node l1DelSource = mapGtoL1.get(edgePO.getSource());
				int parallelPO = getParallelEdges(edgePO.getSource(), poDeleting, edgePO.getType()).size();
				int parallelLhs1 = getParallelEdges(l1DelSource, l1Deleting, edgePO.getType()).size();
				if (l1DelSource == null)
					danglingEdges.add(new DanglingEdge(edgePO, DanglingCase.sourceDangling));
				else if (parallelLhs1 < parallelPO)
					danglingEdges.add(new DanglingEdge(edgePO, DanglingCase.unspecifiedEdge));
			}

		}
		return danglingEdges;
	}

	public static Set<Edge> getParallelEdges(Node source, Node target, EReference type) {
		Set<Edge> result = new HashSet<Edge>();
		if (source == null || target == null)
			return result;
		for (Edge edge : source.getOutgoing()) {
			if (edge.getTarget() == target && edge.getType() == type)
				result.add(edge);
		}
		return result;
	}

	/**
	 * @param graph
	 * @return
	 */
	public static EPackage graphToEPackage(Graph g) {
		Set<String> added = new HashSet<String>();
		EPackage result = EcoreFactory.eINSTANCE.createEPackage();
		result.setName(g.getName());
		result.setNsURI("http://cdapackage/" + g.getName());
		result.setNsPrefix("CDAPackage");
		EList<EClassifier> classifiers = result.getEClassifiers();
		for (Node node : g.getNodes()) {
			EClass n = getSimpleClassifier(node);
			added.add(n.getName());
			classifiers.add(n);
		}
		for (Edge edge : g.getEdges()) {
			EClass s = getSimpleClassifier(edge.getSource());
			EClass t = getSimpleClassifier(edge.getTarget());

			if (!added.contains(s.getName())) {
				classifiers.add(s);
				added.add(s.getName());
			} else
				s = (EClass) result.getEClassifier(s.getName());
			if (!added.contains(t.getName())) {
				classifiers.add(t);
				added.add(t.getName());
			} else
				t = (EClass) result.getEClassifier(t.getName());

			EReference ref = EcoreFactory.eINSTANCE.createEReference();
			ref.setName(edge.getType().getName());
			ref.setEType(t);
			s.getEStructuralFeatures().add(ref);
		}
		return result;
	}

	public static EPackage spanToEPackage(Span r) {
		Set<String> added = new HashSet<String>();
		EPackage result = EcoreFactory.eINSTANCE.createEPackage();
		result.setName(r.getRule1().getName() + Reason.NODE_SEPARATOR + r.getRule2().getName());
		result.setNsURI("http://cdapackage/" + r.getRule1().getName() + "/" + r.getRule2().getName() + "/"
				+ r.getClass().getSimpleName());
		result.setNsPrefix("CDAPackage");
		EList<EClassifier> classifiers = result.getEClassifiers();
		Set<GraphElement> deletions = r.getDeletionElementsInRule1_2();
		for (Node node : r.getGraph().getNodes()) {
			EClass n = getClassifier(r, deletions, node);
			added.add(n.getName());
			classifiers.add(n);
		}

		for (Edge edge : r.getGraph().getEdges()) {
			EClass s = getClassifier(r, deletions, edge.getSource());
			EClass t = getClassifier(r, deletions, edge.getTarget());

			if (!added.contains(s.getName())) {
				classifiers.add(s);
				added.add(s.getName());
			} else
				s = (EClass) result.getEClassifier(s.getName());
			if (!added.contains(t.getName())) {
				classifiers.add(t);
				added.add(t.getName());
			} else
				t = (EClass) result.getEClassifier(t.getName());

			EReference ref = EcoreFactory.eINSTANCE.createEReference();
			ref.setName(edge.getType().getName());
			if (!r.getRule1().getRhs().getEdges().contains(edge)) {
				ref.setName("#" + ref.getName() + "#");
			}
			ref.setEType(t);
			s.getEStructuralFeatures().add(ref);

		}
		return result;
	}

	public static EClass getSimpleClassifier(Node node) {
		EClassifier nodeClass = node.getType();
		EClass eclass = EcoreFactory.eINSTANCE.createEClass();
		eclass.setName(node.getName() + ":" + nodeClass.getName());
		for (Attribute a : node.getAttributes()) {
			Copier cAttribute = new Copier();
			EAttribute at = (EAttribute) cAttribute.copy(a.getType());
			cAttribute.copyReferences();
			at.setName(a.getType().getName() + "=" + a.getValue());
			eclass.getEStructuralFeatures().add(at);
		}
		return eclass;
	}

	public static EClass getClassifier(Span r, Set<GraphElement> deletions, Node node) {
		EClassifier nodeClass = node.getType();
		EClass eclass = EcoreFactory.eINSTANCE.createEClass();
		eclass.setName(node.getName() + ":" + nodeClass.getName());
		Node image1L = r.getMappingIntoRule1(node).getImage();
		Node image2L = r.getMappingIntoRule2(node).getImage();
		Node image1R = r.getRule1().getMappings().getImage(image1L, null);
		Node image2R = r.getRule2().getMappings().getImage(image2L, null);

		for (Attribute a1L : image1L.getAttributes()) {
			String name1 = "";
			if (image1R != null) {
				Attribute a1R = image1R.getAttribute(a1L.getType());
				if (a1R != null && a1L.getValue().equals(a1R.getValue()))
					name1 = a1L.getValue();
				else
					name1 = a1L.getValue() + "->" + (a1R == null ? " " : a1R.getValue());
			} else
				name1 = a1L.getValue();
			Attribute a2L = image2L.getAttribute(a1L.getType());
			String name2 = " ";
			if (image2R != null) {
				Attribute a2R = image2R.getAttribute(a1L.getType());
				if (a2R != null && a1L.getValue().equals(a2R.getValue()))
					name2 = a1L.getValue();
				else
					name2 = (a2L == null ? " " : a2L.getValue()) + "->" + (a2R == null ? " " : a2R.getValue());
			} else if (a2L != null)
				name2 = a2L.getValue();

			Copier cAttribute = new Copier();
			EAttribute at = (EAttribute) cAttribute.copy(a1L.getType());
			cAttribute.copyReferences();
			at.setName(a1L.getType().getName() + "=" + name1 + " " + Reason.NODE_SEPARATOR + " " + name2);
			eclass.getEStructuralFeatures().add(at);
		}
		if (image1R != null)
			for (Attribute a1R : image1R.getAttributes())
				if (image1L.getAttribute(a1R.getType()) == null) {
					String name1 = " ->" + a1R.getValue();
					Attribute a2L = image2L.getAttribute(a1R.getType());
					Attribute a2R = image2R.getAttribute(a1R.getType());
					String name2 = (a2L == null ? " " : a2L.getValue()) + "->" + (a2R == null ? " " : a2R.getValue());

					Copier cAttribute = new Copier();
					EAttribute at = (EAttribute) cAttribute.copy(a1R.getType());
					cAttribute.copyReferences();
					at.setName(a1R.getType().getName() + "=" + name1 + " " + Reason.NODE_SEPARATOR + " " + name2);
					eclass.getEStructuralFeatures().add(at);
				}

		if (deletions.contains(node))
			eclass.setName("#" + eclass.getName() + "#");
		return eclass;
	}

	public static boolean isNcRule(Rule rule, boolean... nac) {
		String d = rule.getDescription();
		Boolean n = nac.length == 0 ? null : nac[0];
		if (d != null)
			if (n == null && (d.contains(Utils.NAC_TAG) || d.contains(Utils.PAC_TAG)))
				return true;
			else if (n != null && ((n && d.contains(Utils.NAC_TAG)) || (!n && d.contains(Utils.PAC_TAG))))
				return true;
		return false;
	}

	public static boolean isRealNcNode(Node n) {
		if (n == null)
			return false;
		Rule r = n.getGraph().getRule();
		if (isNcRule(r)) {
			if (n.getGraph().isRhs())
				n = r.getMappings().getOrigin(n);
			Node origin = r.getMappings().getOrigin(n);
			if (origin == null)
				return true;
			for (Attribute a : n.getAttributes())
				if (origin.getAttribute(a.getType()) == null)
					return true;
		}
		return false;
	}

	public static boolean isRealNcNode(Node n, boolean nac) {
		if (!isRealNcNode(n))
			return false;
		Rule r = n.getGraph().getRule();
		return r.getDescription() != null
				&& ((nac && r.getDescription().contains(NAC_TAG)) || (!nac && r.getDescription().contains(PAC_TAG)));
	}

	public static boolean isInverted(Rule rule) {
		String dsc = rule.getDescription();
		dsc = dsc == null ? "" : dsc;
		int parts = (dsc.length() - dsc.replaceAll(INVERTED_TAG, "").length()) / INVERTED_TAG.length();
		return parts % 2 == 1;
	}

	public static boolean attributesCheck(Span reason) {
		for (Mapping m : reason.getMappingsInRule1()) {
			Node n1L = m.getImage();
			Node n2L = reason.getMappingIntoRule2(m.getOrigin()).getImage();
			if (isInverted(reason.getRule1())) {
				if (isNcRule(reason.getRule2(), true)) {
					if (!strongAttributesCheck(n2L, n1L))
						return false;

				} else if (!attributesCheck(n2L, n1L))
					return false;
			} else if (!attributesCheck(n1L, n2L))
				return false;
		}
		return true;
	}

	public static boolean attributesCheck(Node node1, Node node2) {
		if (identifySubNodeType(node1, node2) == null)
			return false;
		for (Attribute attr1L : node1.getAttributes()) {
			Attribute attr2L = node2.getAttribute(attr1L.getType());
			if (attr2L != null && !Utils.equalAttributes(attr1L, attr2L))
				return false;
		}
		return true;
	}

	public static boolean strongAttributesCheck(Node node1, Node node2) {
		if (identifySubNodeType(node1, node2) == null)
			return false;
		for (Attribute attr1L : node1.getAttributes()) {
			Attribute attr2L = node2.getAttribute(attr1L.getType());
			if (!Utils.equalAttributes(attr1L, attr2L))
				return false;
		}
		return true;
	}

	public static boolean equalAttributes(Attribute a1, Attribute a2) {
		if (a1 == null || a2 == null || a1.getType() != a2.getType())
			return false;
		Rule r1 = a1.getNode().getGraph().getRule();
		Rule r2 = a2.getNode().getGraph().getRule();
		for (Parameter p : r1.getParameters())
			if (p.getName().equals(a1.getValue()))
				return true;
		for (Parameter p : r2.getParameters())
			if (p.getName().equals(a2.getValue()))
				return true;
		return a1.getValue().equals(a2.getValue());
	}

	public static Map<Node, Set<Pair<Attribute, Attribute>>> getAttributeChanges(Rule rule1) {
		Map<Node, Set<Pair<Attribute, Attribute>>> changeUse = new HashMap<>();
		for (Node nL : rule1.getLhs().getNodes()) {
			Pair<Node, Set<Pair<Attribute, Attribute>>> attributes = getAttributeChanges(nL);
			if (attributes != null)
				if (!attributes.second.isEmpty())
					changeUse.put(attributes.first, attributes.second);
		}
		return changeUse;
	}

	public static Pair<Node, Set<Pair<Attribute, Attribute>>> getAttributeChanges(Node node) {
		if (node.getGraph().isRhs()) {
			return null;
		}
		Pair<Node, Set<Pair<Attribute, Attribute>>> attributes = new Pair<>(node, new HashSet<>());
		Set<Attribute> added = new HashSet<>();
		for (Attribute a : node.getActionAttributes(new Action(Action.Type.CREATE))) {
			attributes.second.add(new Pair<>(null, a));
			added.add(a);
		}
		for (Attribute a : node.getActionAttributes(new Action(Action.Type.DELETE))) {
			attributes.second.add(new Pair<>(a, null));
			added.add(a);
		}

		for (Attribute a1 : node.getAttributes())
			if (!added.contains(a1)) {
				Attribute a2 = node.getGraph().getRule().getMappings().getImage(node, null).getAttribute(a1.getType());
				if (a2 != null && !a1.getValue().equals(a2.getValue()))
					attributes.second.add(new Pair<>(a1, a2));
			}
		return attributes;
	}

	public static boolean haveCommonDeletionElement(Reason current, ConflictReason extensionCandidate) {
		Set<GraphElement> deletionElementsCur = current.getDeletionElementsInRule1();
		Set<GraphElement> deletionElementsCand = extensionCandidate.getDeletionElementsInRule1();
		return !Collections.disjoint(deletionElementsCur, deletionElementsCand);
	}

	public static Set<ConflictReason> computeMinimalConflictReasons(Reason current, Set<ConflictReason> remaining) {
		Set<ConflictReason> result = new HashSet<>();
		for (ConflictReason mcr : remaining) {
			if (mcr.isMinimalReason() && !Utils.haveCommonDeletionElement(current, mcr)) {
				ConflictReason initialReason = ReasonFactory.eINSTANCE.createJoinedReason(current, mcr);
				if (initialReason != null) {
					result.add(initialReason);
					result.addAll(computeMinimalConflictReasons(initialReason, remaining));
				}
			}
		}
		return result;
	}

	private static Engine engine = new EngineImpl();

	public static boolean checkNcReason(Span reason, Rule originalR1, Rule originalR2) {
		if (!containsNCElements(reason))
			return false;
		if (validateCreateForbidReason(reason, originalR1, originalR2))
			return true;
		extendPreservedPartsOfReason(reason);
		return validateCreateForbidReason(reason, originalR1, originalR2);
	}

	public static boolean validateCreateForbidReason(Span reason, Rule originalR1, Rule originalR2) {

		// 4. Create PO: (R1 -> H1 <- N2)
		Pushout pH = new Pushout(reason.getRule1(), reason, reason.getRule2());
		Graph H = pH.getResultGraph();
		if (checkSuperTypes(H))
			return false;

		// 5. execute r1i on R1 -> H1, if possible, otherwise break:
		Pushout pG = new Pushout(reason.getRule1(), reason, reason.getRule2());
		Graph G = pG.getResultGraph();
		HenshinEGraph g;
		try {
			g = new HenshinEGraph(G);
		} catch (Exception e) {
			return false;
		}
		RuleApplication app = new RuleApplicationImpl(engine);
		app.setEGraph(g);
		app.setUnit(reason.getRule1());

		Match match = getMatch(pG.getRule1Mappings(), g, reason.getRule1());
		app.setCompleteMatch(match);
		if (!app.execute(null))
			return false;
		// if (G.getNodes().isEmpty())
		// return true;

		// Der Comatch is L1 -> G. There is a Trace H1 -o-> G.
		Match comatch = app.getResultMatch();
		Set<Mapping> trace = getTrace(H, G);

		// 6. Check, if L1 -> G AC1 accepts. Otherwise break.
		Copier c = new Copier();
		Graph GCopy = (Graph) c.copy(G);
		c.copyReferences();
		HenshinEGraph gCopy = new HenshinEGraph(GCopy);

		app.setEGraph(gCopy);
		app.setUnit(originalR1);
		app.setPartialMatch(comatch);
		if (!app.execute(null))
			return false;
		// 7. Set L2 -> H1 = L2 -> N2 -> H1.
		List<Mapping> L2N2 = getNacMappingsOfNacRule(reason.getRule2());
		List<Mapping> N2H1 = pH.getRule2Mappings();
		List<Mapping> L2H1 = composeMappings(L2N2, N2H1);

		// 8. Set L2 -o-> G = L2 -> H1 -o-> G. Check, if L2 -o-> G is total. Otherwise break.
		List<Mapping> H1G = new ArrayList<>(trace);
		List<Mapping> L2G = composeMappings(L2H1, H1G);
		if (L2G.size() != L2N2.size())
			return false; 
		try {
			g = new HenshinEGraph(G);
		} catch (Exception e) {
			return false;
		}
		app.setEGraph(g);
		app.setUnit(originalR2);

		match = getMatch(L2G, g, originalR2);
		app.setCompleteMatch(match);
		if (!app.execute(null))
			return false;

		return true;
	}

	public static boolean attributesDetected(Span reason) {
		for (Node n : reason.getRule1().getLhs().getNodes())
			if (!n.getAttributes().isEmpty())
				return true;
		for (Node n : reason.getRule2().getLhs().getNodes())
			if (!n.getAttributes().isEmpty())
				return true;
		return false;
	}

	public static void deleteParameters(Graph g, Rule r1, Rule r2) {
		Set<Attribute> toRemove = new HashSet<>();
		if (!r1.getParameters().isEmpty())
			for (Node n : g.getNodes()) {
				for (Attribute a : n.getAttributes())
					if (r1.getParameter(a.getValue()) != null)
						toRemove.add(a);
				n.getAttributes().removeAll(toRemove);
				toRemove.clear();
			}
		if (!r2.getParameters().isEmpty())
			for (Node n : g.getNodes()) {
				for (Attribute a : n.getAttributes())
					if (r2.getParameter(a.getValue()) != null)
						toRemove.add(a);
				n.getAttributes().removeAll(toRemove);
				toRemove.clear();
			}
	}

	private static boolean checkSuperTypes(Graph graph) {
		for (Edge e : graph.getEdges()) {
			EClass typeA = (EClass) e.getType().getEType();
			EClass typeB = e.getTarget().getType();
			if (isSuper(typeA, typeB))
				return true;
			typeB = e.getSource().getType();
			if (isSuper(typeA, typeB))
				return true;
		}
		return false;
	}

	public static boolean isSuper(EClass subClass, EClass superClass) {
		if (subClass.getESuperTypes().contains(superClass))
			return true;
		for (EClass eclass : subClass.getESuperTypes())
			if (isSuper(eclass, superClass))
				return true;
		return false;
	}

	public static boolean containsNCElements(Span reason) {
		for (Mapping map : reason.getMappingsInRule2()) {
			Node image = map.getImage();
			if (isRealNcNode(image))
				return true;
		}
		for (Edge e : reason.getGraph().getEdges()) {
			Node source = reason.getMappingIntoRule2(e.getSource()).getImage();
			Node target = reason.getMappingIntoRule2(e.getTarget()).getImage();
			source = reason.getRule2().getMappings().getOrigin(source);
			target = reason.getRule2().getMappings().getOrigin(target);
			if (source.getOutgoing(e.getType(), target) == null)
				return true;
		}
		return false;
	}

	public static void extendPreservedPartsOfReason(Span reason) {

		Set<Node> p1nodes = new HashSet<>();
		Set<Node> p2nodes = new HashSet<>();
		for (Node node : reason.getRule1().getActionNodes(new Action(Action.Type.PRESERVE))) {
			if (reason.getMappingFromGraphToRule1(node) == null)
				p1nodes.add(node);
		}
		for (Node node : reason.getRule2().getLhs().getNodes()) {
			if (reason.getMappingFromGraphToRule2(node) == null
					&& reason.getRule2().getMappings().getImage(node, null) != null)
				p2nodes.add(node);
		}
		Map<Node, Node> maps = mapNodes(p1nodes, p2nodes, null);
		for (Node n : maps.keySet()) {
			Node n2 = maps.get(n);
			String name = n.getName() + "_" + n2.getName();
			if (reason.getGraph().getNode(name) == null) {
				Node reasonNode = HenshinFactory.eINSTANCE.createNode(reason.getGraph(), n.getType(), name);
				reason.getMappingsInRule1().add(HenshinFactory.eINSTANCE.createMapping(reasonNode, n));
				reason.getMappingsInRule2().add(HenshinFactory.eINSTANCE.createMapping(reasonNode, n2));
				for (Edge e : n.getOutgoing())
					if (e.getAction().getType() == Type.PRESERVE) {
						Mapping target = reason.getMappingFromGraphToRule1(e.getTarget());
						if (target != null && reasonNode.getOutgoing(e.getType(), target.getOrigin()) == null)
							HenshinFactory.eINSTANCE.createEdge(reasonNode, target.getOrigin(), e.getType());
					}
				for (Edge e : n.getIncoming())
					if (e.getAction().getType() == Type.PRESERVE) {
						Mapping source = reason.getMappingFromGraphToRule1(e.getSource());
						if (source != null && source.getOrigin().getOutgoing(e.getType(), reasonNode) == null)
							HenshinFactory.eINSTANCE.createEdge(source.getOrigin(), reasonNode, e.getType());
					}
				for (Edge e : n2.getOutgoing())
					if (e.getAction().getType() == Type.PRESERVE) {
						Mapping target = reason.getMappingFromGraphToRule1(e.getTarget());
						if (target != null && reasonNode.getOutgoing(e.getType(), target.getOrigin()) == null)
							HenshinFactory.eINSTANCE.createEdge(reasonNode, target.getOrigin(), e.getType());
					}
				for (Edge e : n2.getIncoming())
					if (e.getAction().getType() == Type.PRESERVE) {
						Mapping source = reason.getMappingFromGraphToRule1(e.getSource());
						if (source != null && source.getOrigin().getOutgoing(e.getType(), reasonNode) == null)
							HenshinFactory.eINSTANCE.createEdge(source.getOrigin(), reasonNode, e.getType());
					}
			}
		}
	}

	private static Match getMatch(List<Mapping> neededMappings, HenshinEGraph g, Rule rule) {
		for (Match m : engine.findMatches(rule, g, null)) {
			if (m.getNodeTargets().size() == neededMappings.size()) {
				boolean found = true;
				for (Mapping mapping : neededMappings) {
					Node node = mapping.getOrigin();
					EObject graphNode = m.getNodeTarget(node);
					node = mapping.getImage();
					if (g.getObject2NodeMap().get(graphNode) != node) {
						found = false;
						break;
					}
				}
				if (found)
					return m;
			}
		}
		return null;
	}

	private static List<Mapping> getNacMappingsOfNacRule(Rule r) {
		List<Mapping> result = new ArrayList<>();
		for (Mapping m : r.getMappings())
			if (!r.getLhs().getNodes().contains(m.getOrigin()))
				result.add(factory.createMapping(m.getOrigin(), m.getImage()));
		return result;
	}

	public static List<Mapping> composeMappings(List<Mapping> map1, List<Mapping> map2) {
		List<Mapping> result = new ArrayList<>();
		for (Mapping m1 : map1)
			for (Mapping m2 : map2)
				if (m1.getImage() == m2.getOrigin()) {
					result.add(HenshinFactory.eINSTANCE.createMapping(m1.getOrigin(), m2.getImage()));
					break;
				}
		return result;
	}

	public static Set<Mapping> getTrace(Graph G, Graph H) {
		Set<Mapping> result = new HashSet<>();
		Map<Node, Node> map = new HashMap<>();
		for (Node nH : H.getNodes())
			for (Node nG : G.getNodes()) {
				if (nH.getName() != null && nG.getName() != null && map.get(nG) == null
						&& nH.getName().equals(nG.getName())) {
					map.put(nG, nH);
					result.add(HenshinFactory.eINSTANCE.createMapping(nG, nH));
					break;
				}
			}
		return result;
	}

	private static Map<EClass, Set<Node>> classifyNodes(Set<Node> nodes) {
		Map<EClass, Set<Node>> result = new HashMap<>();
		for (Node n : nodes) {
			Set<Node> classNodes = result.get(n.getType());
			if (classNodes == null) {
				classNodes = new HashSet<>();
				result.put(n.getType(), classNodes);
			}
			classNodes.add(n);
		}
		return result;
	}

	public static Map<Node, Node> mapNodes(Set<Node> nodes1, Set<Node> nodes2, Map<Node, Node> result,
			boolean... delete) {
		return mapNodes(nodes1, nodes2, nodes2, result, delete.length != 0 && delete[0]);
	}

	public static Map<Node, Node> mapNodes(Node nodes1, Node nodes2, Map<Node, Node> result, boolean... delete) {
		Set<Node> n1 = new HashSet<>();
		Set<Node> n2 = new HashSet<>();
		n1.add(nodes1);
		n2.add(nodes2);
		return mapNodes(n1, n2, result, delete);
	}

	public static Map<Node, Node> mapNodes(Node nodes1, Set<Node> nodes2, Map<Node, Node> result, boolean... delete) {
		Set<Node> n1 = new HashSet<>();
		n1.add(nodes1);
		return mapNodes(n1, nodes2, result, delete);
	}

	public static Map<Node, Node> mapNodes(Set<Node> nodes1, Node nodes2, Map<Node, Node> result, boolean... delete) {
		Set<Node> n2 = new HashSet<>();
		n2.add(nodes2);
		return mapNodes(nodes1, n2, result, delete);
	}

	public static Map<Node, Node> mapNodes(Set<Node> nodesOriginal1, Set<Node> nodesOriginal2, Set<Node> nodes2,
			Map<Node, Node> result, boolean allActionTypes) {
		if (result == null)
			result = new HashMap<>();
		for (Node n2 : nodes2) {
			for (Node n1 : nodesOriginal1)
				if (result.get(n1) == null) {
					if (Utils.strongAttributesCheck(n2, n1)) {
						boolean found = true;
						for (Edge e2 : n2.getOutgoing())
							if (found && nodesOriginal2.contains(e2.getTarget())) {
								EList<Edge> e1 = n1.getOutgoing(e2.getType());
								boolean foundEdge = false;
								for (Edge e : e1)
									if ((allActionTypes || e.getAction().getType() == Action.Type.PRESERVE)
											&& nodesOriginal1.contains(e.getTarget())) {
										foundEdge = true;
										break;
									}
								found = foundEdge;
							}
						if (found)
							for (Edge e2 : n2.getIncoming())
								if (found && nodesOriginal2.contains(e2.getSource())) {
									EList<Edge> e1 = n1.getIncoming(e2.getType());
									boolean foundEdge = false;
									for (Edge e : e1)
										if ((allActionTypes || e.getAction().getType() == Action.Type.PRESERVE)
												&& nodesOriginal1.contains(e.getTarget())) {
											foundEdge = true;
											break;
										}
									found = foundEdge;
								}
						if (found) {
							result.put(n1, n2);
							Set<Node> remained = new HashSet<>(nodes2);
							remained.remove(n2);
							if (remained.isEmpty()) {
								return result;
							}
							mapNodes(nodesOriginal1, nodesOriginal2, remained, result, allActionTypes);
							if (result.size() == nodesOriginal2.size()) {
								return result;
							}
							result.remove(n1);
						}
					}
				}
		}
		return result;
	}

	public static <T extends Span> Set<T> computeCreateEdgeDeleteNode(Set<T> s2Set) {
		Set<T> result = new HashSet<>();
		for (T s2 : s2Set)
			for (Edge cEdge : s2.getRule2().getActionEdges(new Action(Action.Type.CREATE))) {
				Node originS = s2.getRule2().getMappings().getOrigin(cEdge.getSource());
				boolean sourceIsDel = false;
				boolean targetIsDel = false;
				if (originS != null && s2.getMappingFromGraphToRule2(originS) != null) {
					originS = s2.getMappingFromGraphToRule2(originS).getOrigin();
					if (originS != null && s2.getMappingIntoRule1(originS) != null) {
						originS = s2.getMappingIntoRule1(originS).getImage();
						sourceIsDel = originS.getAction().getType() == Action.Type.DELETE;

					}
				}
				Node originT = s2.getRule2().getMappings().getOrigin(cEdge.getTarget());
				if (originT != null && s2.getMappingFromGraphToRule2(originT) != null) {
					originT = s2.getMappingFromGraphToRule2(originT).getOrigin();
					if (originT != null && s2.getMappingIntoRule1(originT) != null) {
						originT = s2.getMappingIntoRule1(originT).getImage();
						targetIsDel = originT.getAction().getType() == Action.Type.DELETE;
					}
				}
				if (sourceIsDel && targetIsDel) {
					Edge e1 = originS.getOutgoing(cEdge.getType(), originT);
					if (e1 == null)
						result.add(ReasonFactory.eINSTANCE.createCEDNReason(s2));
				} else if ((sourceIsDel ^ targetIsDel))
					result.add(ReasonFactory.eINSTANCE.createCEDNReason(s2));
			}
		return result;
	}

	public static <T extends Span> Set<T> filterDoubles(Set<T> spans) {
		Set<T> toRemove = new HashSet<>();
		for (T s : spans) {
			if (!s.isForbid() && !s.isRequire()) {
				for (T s2 : spans)
					if (s != s2 && s.equalElements(s2)) {
						toRemove.add(s);
						break;
					}
			}
		}
		spans.removeAll(toRemove);
		return spans;
	}

	public static enum DanglingCase {
		sourceDangling, targetDangling, unspecifiedEdge
	}

	public static class DanglingEdge {
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((danglingCase == null) ? 0 : danglingCase.hashCode());
			result = prime * result + ((edge == null) ? 0 : edge.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			DanglingEdge other = (DanglingEdge) obj;
			if (danglingCase != other.danglingCase)
				return false;
			if (edge == null) {
				if (other.edge != null)
					return false;
			} else if (!edge.equals(other.edge))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "DanglingEdge [edge=" + edge + ", danglingCase=" + danglingCase + "]";
		}

		public Edge edge;
		public DanglingCase danglingCase;

		public DanglingEdge(Edge edge, DanglingCase danglingCase) {
			super();
			this.edge = edge;
			this.danglingCase = danglingCase;
		}

		public Edge getEdge() {
			return edge;
		}

		public void setEdge(Edge edge) {
			this.edge = edge;
		}

		public DanglingCase getDanglingCase() {
			return danglingCase;
		}

		public void setDanglingCase(DanglingCase danglingCase) {
			this.danglingCase = danglingCase;
		}
	}
}
