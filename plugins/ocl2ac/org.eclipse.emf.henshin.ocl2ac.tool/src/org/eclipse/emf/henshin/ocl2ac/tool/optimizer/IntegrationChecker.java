/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.tool.optimizer;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import org.eclipse.emf.henshin.ocl2ac.gc2ac.core.NestedConditionPreparer;
import org.eclipse.emf.henshin.ocl2ac.gc2ac.util.RuleClassifier;
import laxcondition.Condition;
import nestedcondition.NestedCondition;
import nestedcondition.NestedConstraint;

public class IntegrationChecker {

	EList<EClass> graphsNodesTypes = new BasicEList<EClass>();
	EList<EReference> graphsEdgesTypes = new BasicEList<EReference>();
	EList<EAttribute> graphsAttributesTypes = new BasicEList<EAttribute>();

	/**
	 * The official ones
	 * 
	 * @param rule
	 * @param nestedConstraint
	 * @return
	 */
	public boolean mustIntegrate(Rule rule, NestedConstraint nestedConstraint) {

		if (isThereOverlap(rule, nestedConstraint)) {
			System.out.println("There is an overlap.");
			NestedConditionPreparer preparer = new NestedConditionPreparer(nestedConstraint);
			preparer.eliminateForAllANotExistsC();
			NestedCondition condition = preparer.getCondition();

			RuleClassifier rc = new RuleClassifier(rule);

			if (preparer.isOfFormExistsC(condition)) {
				System.out.println("The constraint is of form Exists C.");
				if (rc.doesRuleCreateOnly()) {
					System.out.println("The rule creates only.");
					System.out.println("There is an overlap but no need for the integration.");
					return false;
				}
			}

			if (preparer.isOfFormNotExistsC(condition)) {
				System.out.println("The constraint is of form Not Exists C.");
				if (rc.doesRuleDeleteOnly()) {
					System.out.println("The rule deletes only.");
					System.out.println("There is an overlap but no need for the integration.");
					return false;
				}
			}

			System.out.println("Go to the integration");
			return true;
		} else {
			System.out.println("No overlap at all");
			return false;
		}
	}

	/**
	 * 
	 * @param rule
	 * @param nestedConstraint
	 * @return
	 */
	private boolean isThereOverlap(Rule rule, NestedConstraint nestedConstraint) {
		EList<Node> createActionNodes = rule.getActionNodes(new Action(Action.Type.CREATE));
		EList<Node> deleteActionNodes = rule.getActionNodes(new Action(Action.Type.DELETE));
		EList<Node> ruleNodes = new BasicEList<Node>();
		ruleNodes.addAll(createActionNodes);
		ruleNodes.addAll(deleteActionNodes);
		EList<EClass> ruleNodesTypes = new BasicEList<EClass>();
		for (Node node : ruleNodes) {
			if (!ruleNodesTypes.contains(node.getType()))
				ruleNodesTypes.add(node.getType());
		}

		EList<Edge> createActionEdges = rule.getActionEdges(new Action(Action.Type.CREATE));
		EList<Edge> deleteActionEdges = rule.getActionEdges(new Action(Action.Type.DELETE));
		EList<Edge> ruleEdges = new BasicEList<Edge>();
		ruleEdges.addAll(createActionEdges);
		ruleEdges.addAll(deleteActionEdges);
		EList<EReference> ruleEdgesTypes = new BasicEList<EReference>();
		for (Edge edge : ruleEdges) {
			if (!ruleEdgesTypes.contains(edge.getType()))
				ruleEdgesTypes.add(edge.getType());
		}

		EList<Attribute> createActionAttribute = new BasicEList<Attribute>();
		for (Node node : rule.getRhs().getNodes()) {
			for (Attribute attr : node.getAttributes()) {
				if (attr.getAction() != null) {
					if (attr.getAction().getType() == Action.Type.CREATE)
						createActionAttribute.add(attr);
				}
			}
		}

		EList<Attribute> deleteActionAttribute = new BasicEList<Attribute>();
		for (Node node : rule.getLhs().getNodes()) {
			for (Attribute attr : node.getAttributes()) {
				if (attr.getAction().getType() == Action.Type.DELETE)
					deleteActionAttribute.add(attr);
			}
		}

		EList<Attribute> ruleAttributes = new BasicEList<Attribute>();
		ruleAttributes.addAll(createActionAttribute);
		ruleAttributes.addAll(deleteActionAttribute);

		EList<EAttribute> ruleAttributeTypes = new BasicEList<EAttribute>();
		for (Attribute attr : ruleAttributes) {
			if (!ruleAttributeTypes.contains(attr.getType()))
				ruleAttributeTypes.add(attr.getType());
		}

		fillNodesTypesAndEdgesTypes(nestedConstraint);

		if (areClanDisjoint(ruleNodesTypes, graphsNodesTypes) && areEdgeDisjoint(ruleEdgesTypes, graphsEdgesTypes)
				&& areAttibuteDisjoint(ruleAttributeTypes, graphsAttributesTypes))
			return false;
		else
			return true;
	}

	/**
	 * 
	 * 
	 * @param rule
	 * @param condition
	 * @return
	 */
	public boolean mustIntegrateWithoutAttribute(Rule rule, Condition condition) {

		EList<Node> createActionNodes = rule.getActionNodes(new Action(Action.Type.CREATE));
		EList<Node> deleteActionNodes = rule.getActionNodes(new Action(Action.Type.DELETE));
		EList<Node> ruleNodes = new BasicEList<Node>();
		ruleNodes.addAll(createActionNodes);
		ruleNodes.addAll(deleteActionNodes);
		EList<EClass> ruleNodesTypes = new BasicEList<EClass>();
		for (Node node : ruleNodes) {
			if (!ruleNodesTypes.contains(node.getType()))
				ruleNodesTypes.add(node.getType());
		}

		EList<Edge> createActionEdges = rule.getActionEdges(new Action(Action.Type.CREATE));
		EList<Edge> deleteActionEdges = rule.getActionEdges(new Action(Action.Type.DELETE));
		EList<Edge> ruleEdges = new BasicEList<Edge>();
		ruleEdges.addAll(createActionEdges);
		ruleEdges.addAll(deleteActionEdges);
		EList<EReference> ruleEdgesTypes = new BasicEList<EReference>();
		for (Edge edge : ruleEdges) {
			if (!ruleEdgesTypes.contains(edge.getType()))
				ruleEdgesTypes.add(edge.getType());
		}

		fillNodesTypesAndEdgesTypes(condition);

		if (areClanDisjoint(ruleNodesTypes, graphsNodesTypes) && areEdgeDisjoint(ruleEdgesTypes, graphsEdgesTypes))
			return false;
		else
			return true;
	}

	/**
	 * 
	 * @param nestedConstraint
	 */
	private void fillNodesTypesAndEdgesTypes(NestedConstraint nestedConstraint) {
		graphsNodesTypes.clear();
		graphsEdgesTypes.clear();
		TreeIterator<EObject> iter = nestedConstraint.eAllContents();
		while (iter.hasNext()) {
			EObject eObject = iter.next();
			if (eObject instanceof graph.Node) {
				graph.Node node = (graph.Node) eObject;
				if (!graphsNodesTypes.contains(node.getType()))
					graphsNodesTypes.add(node.getType());
			}

			if (eObject instanceof graph.Edge) {
				graph.Edge edge = (graph.Edge) eObject;
				if (!graphsEdgesTypes.contains(edge.getType()))
					graphsEdgesTypes.add(edge.getType());
			}

			if (eObject instanceof graph.Attribute) {
				graph.Attribute attribute = (graph.Attribute) eObject;
				if (!graphsAttributesTypes.contains(attribute.getType()))
					graphsAttributesTypes.add(attribute.getType());

			}
		}

	}

	/**
	 * 
	 * @param condition
	 */
	private void fillNodesTypesAndEdgesTypes(Condition condition) {
		graphsNodesTypes.clear();
		graphsEdgesTypes.clear();
		TreeIterator<EObject> iter = condition.eAllContents();
		while (iter.hasNext()) {
			EObject eObject = iter.next();
			if (eObject instanceof graph.Node) {
				graph.Node node = (graph.Node) eObject;
				if (!graphsNodesTypes.contains(node.getType()))
					graphsNodesTypes.add(node.getType());
			}

			if (eObject instanceof graph.Edge) {
				graph.Edge edge = (graph.Edge) eObject;
				if (!graphsEdgesTypes.contains(edge.getType()))
					graphsEdgesTypes.add(edge.getType());
			}

			if (eObject instanceof graph.Attribute) {
				graph.Attribute attribute = (graph.Attribute) eObject;
				if (!graphsAttributesTypes.contains(attribute.getType()))
					graphsAttributesTypes.add(attribute.getType());

			}
		}

	}

	/**
	 *
	 * 
	 * @param typesList1
	 * @param typesList2
	 * @return
	 */
	private boolean areClanDisjoint(EList<EClass> typesList1, EList<EClass> typesList2) {
		for (EClass type1 : typesList1) {
			EList<EClass> clan1 = getClan(type1);
			for (EClass type2 : typesList2) {
				if (clan1.contains(type2)) {
					return false;
				}
			}
		}
		for (EClass type2 : typesList2) {
			EList<EClass> clan2 = getClan(type2);
			for (EClass type1 : typesList1) {
				if (clan2.contains(type1)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * @param typesList1
	 * @param typesList2
	 * @return
	 */
	private boolean areEdgeDisjoint(EList<EReference> typesList1, EList<EReference> typesList2) {
		if (typesList1.size() <= typesList2.size()) {
			for (EReference type1 : typesList1) {
				if (typesList2.contains(type1))
					return false;
			}
		} else {
			for (EReference type2 : typesList2) {
				if (typesList1.contains(type2))
					return false;
			}
		}

		return true;
	}

	/**
	 * 
	 * @param typesList1
	 * @param typesList2
	 * @return
	 */
	private boolean areAttibuteDisjoint(EList<EAttribute> typesList1, EList<EAttribute> typesList2) {
		if (typesList1.size() <= typesList2.size()) {
			for (EAttribute type1 : typesList1) {
				if (typesList2.contains(type1))
					return false;
			}
		} else {
			for (EAttribute type2 : typesList2) {
				if (typesList1.contains(type2))
					return false;
			}
		}

		return true;
	}

	/**
	 * 
	 * @param eClass
	 * @return
	 */
	private EList<EClass> getClan(EClass eClass) {
		EList<EClass> eClasses = new BasicEList<EClass>();
		eClasses.add(eClass);
		eClasses.addAll(getAllSubclasses(eClass));
		return eClasses;
	}

	/**
	 * 
	 * @param eClass
	 * @return
	 */
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
}
