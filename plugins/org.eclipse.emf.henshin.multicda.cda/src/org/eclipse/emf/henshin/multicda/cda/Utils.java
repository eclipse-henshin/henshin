package org.eclipse.emf.henshin.multicda.cda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ddf.EscherBoolProperty;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.model.Action;
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
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;
import org.eclipse.emf.henshin.multicda.cpa.result.Conflict;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalElement;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;
import org.eclipse.emf.henshin.multicda.cpa.result.Dependency;
import org.eclipse.emf.henshin.preprocessing.RulePair;

import agg.util.Pair;

public abstract class Utils {
	private static HenshinFactory factory = HenshinFactory.eINSTANCE;

	public static Rule invertRule(Rule rule1) {

		Rule invRule1 = factory.createRule(rule1.getName());
		String d = invRule1.getDescription();
		invRule1.setDescription((d == null ? "" : d) + "_Inv");

		Copier copierForRhsToLhs = new Copier();
		Graph newLhs = (Graph) copierForRhsToLhs.copy(rule1.getRhs());
		copierForRhsToLhs.copyReferences();
		newLhs.setName("LHS");
		invRule1.setLhs(newLhs);

		Copier copierForLhsToRhs = new Copier();
		Graph newRhs = (Graph) copierForLhsToRhs.copy(rule1.getLhs());
		copierForLhsToRhs.copyReferences();
		newRhs.setName("RHS");
		invRule1.setRhs(newRhs);

		MappingList mappingsOfOriginalRule1 = rule1.getMappings();
		for (Mapping mappingInOriginalRule1 : mappingsOfOriginalRule1) {

			// identifizieren der ORIGIN in der neuen Regel
			Node imageInOriginalRule = mappingInOriginalRule1.getImage();
			EObject originInNewRule = copierForRhsToLhs.get(imageInOriginalRule);
			Node originInNewRuleNode = (Node) originInNewRule;

			Node originInOriginalRule = mappingInOriginalRule1.getOrigin();
			EObject imageInNewRule = copierForLhsToRhs.get(originInOriginalRule);
			Node imageInNewRuleNode = (Node) imageInNewRule;

			Mapping createdMapping = factory.createMapping(originInNewRuleNode, imageInNewRuleNode);
			invRule1.getMappings().add(createdMapping);
		}

		return invRule1;
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
			Map<Node, Set<Pair<Attribute, Attribute>>> nodes = getChangeNodes(nonDelete);
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

	public static Set<Rule> createNACRule(Rule rule1) {
		EList<NestedCondition> nacs = rule1.getLhs().getNACs();
		return getNestedRules(nacs, rule1);
	}

	public static Set<Rule> createPACRule(Rule rule1) {
		EList<NestedCondition> pacs = rule1.getLhs().getPACs();
		return getNestedRules(pacs, rule1);
	}

	private static Set<Rule> getNestedRules(EList<NestedCondition> ncs, Rule rule) {
		int nodeId = 0;
		int id = 0;
		Set<Rule> result = new HashSet<>();
		if (ncs == null || ncs.isEmpty())
			return result;
		for (NestedCondition nac : ncs) {
			Graph nacGraph = nac.getConclusion();
			Rule nacRule = factory.createRule(rule.getName());
			String d = nacRule.getDescription();
			nacRule.setDescription((d == null ? "" : d) + "_NACrule_" + id);

			Graph newRhs;
			Graph newLhs;

			Copier lhsCopier = new Copier();
			newLhs = (Graph) lhsCopier.copy(nacGraph);
			lhsCopier.copyReferences();
			nacRule.setLhs(newLhs);
			newLhs.setName("LHS");
			newLhs.setDescription(": this is the NAC of the Original Rule");

			Copier rhsCopier = new Copier();
			newRhs = (Graph) rhsCopier.copy(rule.getLhs());
			rhsCopier.copyReferences();
			nacRule.setRhs(newRhs);
			newRhs.setName("RHS");
			newRhs.setDescription(": this is the L Graph of the Original Rule");

			MappingList nacMappings = nac.getMappings();
			for (Mapping nacMapping : nacMappings) {

				Node imageInOriginalRule = nacMapping.getImage();
				EObject originInNewRule = lhsCopier.get(imageInOriginalRule);
				Node originInNewRuleNode = (Node) originInNewRule;

				Node originInOriginalRule = nacMapping.getOrigin();
				EObject imageInNewRule = rhsCopier.get(originInOriginalRule);
				Node imageInNewRuleNode = (Node) imageInNewRule;

				originInNewRuleNode.setName(imageInNewRuleNode.getName());

				Mapping createdMapping = factory.createMapping(originInNewRuleNode, imageInNewRuleNode);
				nacRule.getMappings().add(createdMapping);
			}
			for (Node n : nacRule.getLhs().getNodes())
				if (n.getName() == null || n.getName().isEmpty())
					n.setName("|f" + nodeId++ + "|");
			result.add(nacRule);
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
	 * identify the type of the both nodes which is the subtype of the other
	 * node.
	 * 
	 * @param node1
	 * @param node2
	 * @return returns the subnode type if one of both is, otherwise null.
	 */
	public static EClass identifySubNodeType(Node node1, Node node2) {
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

	public static Map<CriticalPair, Reason> compare(List<CriticalPair> cps, Set<Reason> reasons) {
		Map<CriticalPair, Reason> result = new HashMap<>();
		if (reasons == null || cps == null)
			return result;
		for (CriticalPair cp : cps)
			for (Reason reason : reasons) {
				boolean foundElement = false;
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

	/**
	 * @param graph
	 * @return
	 */
	public static EPackage graphToEPackage(Reason r) {
		Set<String> added = new HashSet<String>();
		EPackage result = EcoreFactory.eINSTANCE.createEPackage();
		result.setName(r.getRule1().getName() + Reason.NODE_SEPARATOR + r.getRule2().getName());
		result.setNsURI("http://cdapackage/" + r.getRule1().getName() + "/" + r.getRule2().getName() + "/"
				+ r.getClass().getSimpleName());
		result.setNsPrefix("CDAPackage");
		EList<EClassifier> classifiers = result.getEClassifiers();
		Set<GraphElement> deletions = r.getDeletionElementsInRule1_2();
		for (Node node : r.graph.getNodes()) {
			EClass n = getClassifier(r, deletions, node);
			added.add(n.getName());
			result.getEClassifiers().add(n);
		}

		for (Edge edge : r.graph.getEdges()) {
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

	public static EClass getClassifier(Reason r, Set<GraphElement> deletions, Node node) {
		EClassifier nodeClass = node.getType();
		EClass eclass = EcoreFactory.eINSTANCE.createEClass();
		eclass.setName(node.getName() + ":" + nodeClass.getName());
		Node image1L = r.getMappingIntoRule1(node).getImage();

		for (Attribute a1L : image1L.getAttributes()) {
			Node image1R = r.getRule1().getMappings().getImage(image1L, null);
			String name1 = "";
			if (image1R != null) {
				Attribute a1R = image1R.getAttribute(a1L.getType());
				if (r.getRule1().getDescription() != null && r.getRule1().getDescription().equals("INV"))
					name1 = (a1R == null ? "_" : a1R.getValue()) + "->" + a1L.getValue();
				else
					name1 = a1L.getValue() + "->" + (a1R == null ? "_" : a1R.getValue());
			}
			Node image2L = r.getMappingIntoRule2(node).getImage();
			Attribute a2L = image2L.getAttribute(a1L.getType());
			String name2 = (a2L == null ? "_" : a2L.getValue());

			Copier cAttribute = new Copier();
			EAttribute at = (EAttribute) cAttribute.copy(a1L.getType());
			cAttribute.copyReferences();
			at.setName(a1L.getType().getName() + "=" + name1 + Reason.NODE_SEPARATOR + name2);
			eclass.getEStructuralFeatures().add(at);
		}
		Node image1R = r.getRule1().getMappings().getImage(image1L, null);
		if (image1R != null)
			for (Attribute a1R : image1R.getAttributes())
				if (image1L.getAttribute(a1R.getType()) == null) {
					String name1 = "_->" + a1R.getValue();
					Node image2L = r.getMappingIntoRule2(node).getImage();
					Attribute a2L = image2L.getAttribute(a1R.getType());
					String name2 = (a2L == null ? "_" : a2L.getValue());

					Copier cAttribute = new Copier();
					EAttribute at = (EAttribute) cAttribute.copy(a1R.getType());
					cAttribute.copyReferences();
					at.setName(a1R.getType().getName() + "=" + name1 + Reason.NODE_SEPARATOR + name2);
					eclass.getEStructuralFeatures().add(at);
				}

		if (deletions.contains(node))
			eclass.setName("#" + eclass.getName() + "#");
		return eclass;
	}

	public static boolean checkAttributes(Reason reason) {
		for (Mapping m : reason.getMappingsInRule1()) {
			Node n1L = m.getImage();
			Node n2L = reason.getMappingIntoRule2(m.getOrigin()).getImage();
			for (Attribute attr1L : n1L.getAttributes()) {
				Attribute attr2L = n2L.getAttribute(attr1L.getType());
				if (attr1L != null && attr2L != null && !Utils.equalAttributes(attr1L, attr2L))
					return false;
			}
		}
		return true;
	}

	public static Map<Node, Set<Pair<Attribute, Attribute>>> getChangeNodes(Rule rule1) {
		Map<Node, Set<Pair<Attribute, Attribute>>> changeUse = new HashMap<>();
		for (Node nR : rule1.getRhs().getNodes()) {
			Pair<Node, Set<Pair<Attribute, Attribute>>> attributes = getChangeNodes(nR);
			if (attributes != null)
				if (!attributes.second.isEmpty())
					changeUse.put(attributes.first, attributes.second);
		}
		return changeUse;
	}

	public static Pair<Node, Set<Pair<Attribute, Attribute>>> getChangeNodes(Node nR) {
		if (nR == null)
			return null;
		if (nR.getGraph().getRule() == null)
			return new Pair<>(nR, new HashSet<>());
		Node nL = nR.getGraph().getRule().getMappings().getOrigin(nR);
		if (nL == null) {
			Node temp = nR.getGraph().getRule().getMappings().getImage(nR, null);
			if (temp == null)
				return new Pair<>(nR, new HashSet<>());
			nR = temp;
			nL = nR.getGraph().getRule().getMappings().getOrigin(nR);
		}
		Pair<Node, Set<Pair<Attribute, Attribute>>> attributes = new Pair<>(nL, new HashSet<>());
		if (nL != null && nR != null) {
			for (Attribute aL : nL.getAttributes()) {
				Attribute aR = nR.getAttribute(aL.getType());
				if (aR == null || !aL.getValue().equals(aR.getValue()))
					attributes.second.add(new Pair<>(aL, aR));
			}
			for (Attribute aR : nR.getAttributes()) {
				Attribute aL = nL.getAttribute(aR.getType());
				if (aL == null)
					attributes.second.add(new Pair<>(aL, aR));
			}
		}
		return attributes;
	}

	public static boolean equalAttributes(Attribute a1, Attribute a2) {
		Rule r1 = a1.getNode().getGraph().getRule();
		Rule r2 = a2.getNode().getGraph().getRule();
		boolean p1 = false;
		boolean p2 = false;
		for (Parameter p : r1.getParameters())
			if (p1 = p.getName().equals(a1.getValue()))
				break;
		for (Parameter p : r2.getParameters())
			if (p2 = p.getName().equals(a2.getValue()))
				break;
		if (!p1 && !p2)
			return a1.getValue().equals(a2.getValue());
		return true;
	}
}
