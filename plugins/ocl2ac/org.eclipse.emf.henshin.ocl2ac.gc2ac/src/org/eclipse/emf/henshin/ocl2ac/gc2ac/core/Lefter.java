/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.gc2ac.core;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Xor;
import org.eclipse.emf.henshin.model.impl.MappingListImpl;

/**
 * This class implements the transformation from right to left application
 * conditions.
 * 
 * This is done the following way: The inverse rule is recursively applied to
 * all graphs in the formula on the right hand side. The match for applying the
 * rule results from the mappings in the nested conditions along the formula
 * tree. For nested conditions this results in calculating some kind of pushout
 * complement. If the pushout complement does not exists, the result for the
 * sub-formula is false. For all other operators the transformation is passed
 * through.
 * 
 * Since there is no sufficient representation for the constant formulas false
 * and true in Henshin, all sub-formulas that become constant true or false
 * during the transformation (i.e. some pushout complement does not exist) are
 * eliminated from the formula and AND, OR and XOR operators with missing
 * children resolved.
 * 
 * @author Jan Steffen Becker (jan.steffen.becker@uni-oldenburg.de) and
 * 		   Nebras Nassar (nassarn@mathematik.uni-marburg.de)
 *
 */
public class Lefter {

	private Rule rule;
	private HenshinFactory factory;
	public boolean enableOptimizer = false;

	public Lefter(Rule rule) {
		this.rule = rule;
		this.factory = HenshinPackage.eINSTANCE.getHenshinFactory();
	}

	/**
	 * Transforms the right application condition of the rule (a formula
	 * attached to the Rhs) into a left application condition that is set as
	 * formula to the Lhs. The right application condition is removed.
	 * 
	 * If the resulting left application condition is false, a runtime exception
	 * is thrown.
	 */
	public void left() {
		switch (leftRecursive(rule.getLhs(), HenshinPackage.eINSTANCE.getGraph_Formula(), rule.getRhs().getFormula(),
				rule.getMappings())) {
		case COLLAPSES_FALSE:
			rule.getLhs().setFormula(null);
			throw new RuntimeException("Rule is not applicable since the application condition collapses to false.");
		case COLLAPSES_TRUE:
			rule.getLhs().setFormula(null);
			break;
		default:
			break;
		}
		rule.getRhs().setFormula(null);
	}

	/**
	 * Constants for possible results when recursively checking the dangling
	 * condition.
	 *
	 */
	private enum DanglingConditionResult {
		/**
		 * The condition collapses to true when performing Left.
		 */
		COLLAPSES_TRUE,
		/**
		 * The condition collapses to false when performing Left.
		 */
		COLLAPSES_FALSE,
		/**
		 * The condition does not collapse to a constant when performing Left.
		 */
		NO_COLLAPS;

		public DanglingConditionResult negate() {
			switch (this) {
			case COLLAPSES_FALSE:
				return DanglingConditionResult.COLLAPSES_TRUE;
			case COLLAPSES_TRUE:
				return DanglingConditionResult.COLLAPSES_FALSE;
			default:
				return this;
			}
		}
	}

	/**
	 * 
	 * @param graph
	 * @return
	 */
	private boolean hasMoreThanOneContainer(org.eclipse.emf.henshin.model.Graph graph) {
		EList<org.eclipse.emf.henshin.model.Edge> containmentEdges = getEdgesTypedContainment(graph);
		for (int i = 0; i < containmentEdges.size() - 1; i++) {
			for (int j = i + 1; j < containmentEdges.size(); j++) {
				if (containmentEdges.get(i).getTarget() == containmentEdges.get(j).getTarget()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param graph
	 * @return
	 */
	private EList<org.eclipse.emf.henshin.model.Edge> getEdgesTypedContainment(
			org.eclipse.emf.henshin.model.Graph graph) {
		EList<org.eclipse.emf.henshin.model.Edge> containmentEdges = new BasicEList<org.eclipse.emf.henshin.model.Edge>();

		for (org.eclipse.emf.henshin.model.Edge edge : graph.getEdges()) {
			if (edge.getType().isContainment())
				containmentEdges.add(edge);
		}
		return containmentEdges;
	}

	/**
	 * calculates the left application condition for a nested condition. The
	 * dangling condition must be satisfied, i.e the pushout complement exists.
	 * 
	 * TODO Handle lefting of attribute constraints
	 * 
	 * @param parent
	 *            parent where the result shall be attached
	 * @param right
	 *            the nested condition to left
	 * @param mapping
	 *            the mapping to left over
	 */
	private DanglingConditionResult leftNestedCondition(EObject parent, EReference parentReference,
			NestedCondition right, MappingList mapping) {
		/*
		 * mapping lhs -----------> rhs | | lm| |rm | | V newMapping V newLhs
		 * --------> newRhs
		 * 
		 */
		assertInjectivityAndTotality(right);
		NestedCondition left = factory.createNestedCondition();
		parent.eSet(parentReference, left);
		Graph lhs = left.getHost();
		Graph newLhs = factory.createGraph();
		left.setConclusion(newLhs);
		Graph newRhs = right.getConclusion();
		EcoreUtil.Copier copier = new EcoreUtil.Copier();
		MappingList lm = left.getMappings();
		MappingList rm = right.getMappings();

		// copy the left hand side graph to the next level and add the mappings
		// for the lhs
		for (Node node : lhs.getNodes()) {
			Node copy = (Node) copier.copy(node); // attributes are copied
													// automatically
			newLhs.getNodes().add(copy);
			lm.add(node, copy);
		}
		newLhs.getEdges().addAll(copier.copyAll(lhs.getEdges()));
		copier.copyReferences(); // hook up references, especially source and
									// target of edges

		MappingList newMapping = new MappingListImpl(); // copy the mappings for
														// the next level. Make
														// it commute
		for (Mapping map : mapping) {
			Node l = lm.getImage(map.getOrigin(), newLhs);
			Node r = rm.getImage(map.getImage(), newRhs);
			newMapping.add(l, r);
		}
		/*
		 * Now we copy the nodes, edges and attributes that are introduced on
		 * the right hand side from rhs to newRhs. They occur in newRhs but do
		 * not have a pre-image ("origin") in rm. Because Henshin does not
		 * permit multiple edges and attributes we also check if there is
		 * already a pre-image in newMapping. In this case the lefted condition
		 * must not be satisfiable and we return COLLAPSES_FALSE.
		 */
		for (Node node : newRhs.getNodes()) {
			if (rm.getOrigin(node) == null) {
				Node copy = (Node) EcoreUtil.copy(node);
				newLhs.getNodes().add(copy);
				newMapping.add(copy, node);
			} else {

				if (enableOptimizer) {
					// Eliminate graph with more than one container
					if (hasMoreThanOneContainer(newRhs)) {
						System.out.println("The graph has nodes with more than one container is removed:");
						System.out.print(newLhs.getNodes());
						return DanglingConditionResult.COLLAPSES_FALSE;
					}
				}

				Node origin = newMapping.getOrigin(node);

				// We get the type from the match (constraint) and not from
				// the LHS. It is always the smaller type.
				if (origin != null && node != null && origin.getType().getName() != node.getType().getName()) {
					newMapping.remove(origin, node);
					origin.setType(node.getType());
					newMapping.add(origin, node);
				}

				for (Attribute attr : node.getAttributes()) {
					if (rm.getOrigin(attr) == null) {
						Attribute attrOrigin = newMapping.getOrigin(attr);
						if (attrOrigin == null && origin != null) {
							Attribute copy = EcoreUtil.copy(attr);
							origin.getAttributes().add(copy);
						} else {
							return DanglingConditionResult.COLLAPSES_FALSE;
						}
					}
				}

			}
		}

		for (Edge edge : newRhs.getEdges()) {
			if (rm.getOrigin(edge) == null) {
				if (newMapping.getOrigin(edge) == null) {
					Edge copy = (Edge) EcoreUtil.copy(edge);
					Node sourceOrigin = newMapping.getOrigin(edge.getSource());
					Node targetOrigin = newMapping.getOrigin(edge.getTarget());
					if (sourceOrigin == null || targetOrigin == null) {
						return DanglingConditionResult.COLLAPSES_FALSE;
					}
					copy.setSource(sourceOrigin);
					copy.setTarget(targetOrigin);
					newLhs.getEdges().add(copy);
				} else { // the required edge is to be removed by the rule
					return DanglingConditionResult.COLLAPSES_FALSE;
				}
			}
		}

		////For supporting parallel edges
		// for (Edge edge: newRhs.getEdges()) {
		// if (rm.getOrigin(edge) == null) {
		// if (newMapping.getOrigin(edge) == null) {
		// Edge copy = (Edge) EcoreUtil.copy(edge);
		// Node sourceOrigin = newMapping.getOrigin(edge.getSource());
		// Node targetOrigin = newMapping.getOrigin(edge.getTarget());
		// if (sourceOrigin == null || targetOrigin == null) {
		// return DanglingConditionResult.COLLAPSES_FALSE;
		// }
		// copy.setSource(sourceOrigin);
		// copy.setTarget(targetOrigin);
		// newLhs.getEdges().add(copy);
		// } else {
		// Edge copy = (Edge) EcoreUtil.copy(edge);
		// Node sourceOrigin = newMapping.getOrigin(edge.getSource());
		// Node targetOrigin = newMapping.getOrigin(edge.getTarget());
		// if (sourceOrigin == null || targetOrigin == null) {
		// return DanglingConditionResult.COLLAPSES_FALSE;
		// }
		// copy.setSource(sourceOrigin);
		// copy.setTarget(targetOrigin);
		// newLhs.getEdges().add(copy);
		// return DanglingConditionResult.NO_COLLAPS;
		// }
		// }
		// }

		switch (leftRecursive(newLhs, HenshinPackage.eINSTANCE.getGraph_Formula(), newRhs.getFormula(), newMapping)) {
		case COLLAPSES_FALSE:
			return DanglingConditionResult.COLLAPSES_FALSE;
		case COLLAPSES_TRUE:
			newLhs.setFormula(null);
			return DanglingConditionResult.NO_COLLAPS;
		default:
			return DanglingConditionResult.NO_COLLAPS;
		}

	}

	/**
	 * Checks if the mapping for a nested condition is a valid total and
	 * injective morphism on the node set.
	 * 
	 * @param condition
	 *            Nested Condition to check
	 */
	private void assertInjectivityAndTotality(NestedCondition condition) {
		MappingList mappings = condition.getMappings();
		Set<Node> originNodes = new HashSet<>();
		originNodes.addAll(condition.getHost().getNodes());
		Set<Node> imageNodes = new HashSet<>();
		imageNodes.addAll(condition.getConclusion().getNodes());
		for (Mapping mapping : mappings) {
			if (!originNodes.remove(mapping.getOrigin())) {
				throw new RuntimeException("Mapping for non-existing node or two mappings for one node");
			}
			if (!imageNodes.remove(mapping.getImage())) {
				throw new RuntimeException("Mapping to non-existing node or not injective");
			}
		}
		if (!originNodes.isEmpty()) {
			throw new RuntimeException("Not total");
		}
	}

	/**
	 * Applies the left operation recursively. Delegates to procedures for the
	 * types of sub-formula.
	 * 
	 * @param parent
	 *            parent where the lefted formula shall be attached to
	 * @param eReference
	 * @param formula
	 *            formula on the right hand side
	 * @param mappings
	 *            mappings (rule) from the left hand side to the right
	 */
	private DanglingConditionResult leftRecursive(EObject parent, EReference parentReference, Formula formula,
			MappingList mappings) {
		if (formula == null) {
			return DanglingConditionResult.NO_COLLAPS;
		}
		if (formula instanceof Not) {
			return leftNot(parent, parentReference, (Not) formula, mappings);
		} else if (formula instanceof And) {
			return leftAnd(parent, parentReference, (And) formula, mappings);
		} else if (formula instanceof Or) {
			return leftOr(parent, parentReference, (Or) formula, mappings);
		} else if (formula instanceof Xor) {
			return leftXor(parent, parentReference, (Xor) formula, mappings);
		} else if (formula instanceof NestedCondition) {
			return leftNestedCondition(parent, parentReference, (NestedCondition) formula, mappings);
		} else {
			throw new IllegalArgumentException();
		}

	}

	/**
	 * Applies the left operation for a NOT-formula.
	 * 
	 * @param parent
	 *            parent where the lefted formula shall be attached to
	 * @param formula
	 *            formula on the right hand side
	 * @param mappings
	 *            mappings (rule) from the left hand side to the right
	 */
	private DanglingConditionResult leftNot(EObject parent, EReference parentReference, Not formula,
			MappingList mappings) {
		Not lefted = factory.createNot();
		parent.eSet(parentReference, lefted);
		return leftRecursive(lefted, HenshinPackage.eINSTANCE.getUnaryFormula_Child(), formula.getChild(), mappings)
				.negate();
	}

	/**
	 * Applies the left operation for an AND-formula. If one child collapses to
	 * true, the AND-operator is omitted and the result for the other child is
	 * attached to the parent
	 * 
	 * @param parent
	 *            parent where the lefted formula shall be attached to
	 * @param formula
	 *            formula on the right hand side
	 * @param mappings
	 *            mappings (rule) from the left hand side to the right
	 */
	private DanglingConditionResult leftAnd(EObject parent, EReference parentReference, And formula,
			MappingList mappings) {
		And lefted = factory.createAnd();
		parent.eSet(parentReference, lefted);
		DanglingConditionResult leftResult = leftRecursive(lefted, HenshinPackage.eINSTANCE.getBinaryFormula_Left(),
				formula.getLeft(), mappings);
		if (leftResult == DanglingConditionResult.COLLAPSES_FALSE) {
			return DanglingConditionResult.COLLAPSES_FALSE;
		}
		DanglingConditionResult rightResult = leftRecursive(lefted, HenshinPackage.eINSTANCE.getBinaryFormula_Right(),
				formula.getRight(), mappings);
		if (rightResult == DanglingConditionResult.COLLAPSES_FALSE) {
			return DanglingConditionResult.COLLAPSES_FALSE;
		} else if (leftResult == DanglingConditionResult.COLLAPSES_TRUE) {
			parent.eSet(parentReference, lefted.getRight()); // pull up the
																// right child
			return rightResult;
		} else if (rightResult == DanglingConditionResult.COLLAPSES_TRUE) {
			parent.eSet(parentReference, lefted.getLeft()); // pull up the left
															// child
			return leftResult;
		}
		return DanglingConditionResult.NO_COLLAPS;
	}

	/**
	 * Applies the left operation for an OR-formula. If one child collapses to
	 * false, the OR-operator is omitted in the result and the result for the
	 * other child is attached to the parent
	 * 
	 * @param parent
	 *            parent where the lefted formula shall be attached to
	 * @param formula
	 *            formula on the right hand side
	 * @param mappings
	 *            mappings (rule) from the left hand side to the right
	 */
	private DanglingConditionResult leftOr(EObject parent, EReference parentReference, Or formula,
			MappingList mappings) {
		Or lefted = factory.createOr();
		parent.eSet(parentReference, lefted);
		DanglingConditionResult leftResult = leftRecursive(lefted, HenshinPackage.eINSTANCE.getBinaryFormula_Left(),
				formula.getLeft(), mappings);
		if (leftResult == DanglingConditionResult.COLLAPSES_TRUE) {
			return DanglingConditionResult.COLLAPSES_TRUE;
		}
		DanglingConditionResult rightResult = leftRecursive(lefted, HenshinPackage.eINSTANCE.getBinaryFormula_Right(),
				formula.getRight(), mappings);
		if (rightResult == DanglingConditionResult.COLLAPSES_TRUE) {
			return DanglingConditionResult.COLLAPSES_TRUE;
		} else if (leftResult == DanglingConditionResult.COLLAPSES_FALSE) {
			parent.eSet(parentReference, lefted.getRight()); // pull up the
																// right child
			return rightResult;
		} else if (rightResult == DanglingConditionResult.COLLAPSES_FALSE) {
			parent.eSet(parentReference, lefted.getLeft()); // pull up the right
															// child
			return leftResult;
		}
		return DanglingConditionResult.NO_COLLAPS;
	}

	/**
	 * Applies the left operation for an XOR-formula. If one child collapses to
	 * false, the XOR-operator is omitted and the result for the other child is
	 * attached to the parent
	 * 
	 * If one child collapses to true, the XOR-operator is omitted and the
	 * negation of the result for the other child is attached to the parent
	 * 
	 * @param parent
	 *            parent where the lefted formula shall be attached to
	 * @param formula
	 *            formula on the right hand side
	 * @param mappings
	 *            mappings (rule) from the left hand side to the right
	 */
	private DanglingConditionResult leftXor(EObject parent, EReference parentReference, Xor formula,
			MappingList mappings) {
		Xor lefted = factory.createXor();
		parent.eSet(parentReference, lefted);
		DanglingConditionResult leftResult = leftRecursive(lefted, HenshinPackage.eINSTANCE.getBinaryFormula_Left(),
				formula.getLeft(), mappings);
		DanglingConditionResult rightResult = leftRecursive(lefted, HenshinPackage.eINSTANCE.getBinaryFormula_Right(),
				formula.getRight(), mappings);
		if (rightResult == DanglingConditionResult.COLLAPSES_FALSE) {
			parent.eSet(parentReference, lefted.getLeft()); // pull up the left
															// child
			return leftResult;
		} else if (rightResult == DanglingConditionResult.COLLAPSES_TRUE) {
			Not negatedlefted = factory.createNot();
			negatedlefted.setChild(lefted.getLeft());
			parent.eSet(parentReference, negatedlefted);
			return leftResult.negate();
		} else if (leftResult == DanglingConditionResult.COLLAPSES_FALSE) {
			parent.eSet(parentReference, lefted.getRight()); // pull up the left
																// child
			return rightResult;
		} else if (leftResult == DanglingConditionResult.COLLAPSES_TRUE) {
			Not negatedlefted = factory.createNot();
			negatedlefted.setChild(lefted.getRight());
			parent.eSet(parentReference, negatedlefted);
			return rightResult.negate();
		}
		return DanglingConditionResult.NO_COLLAPS;
	}

}
