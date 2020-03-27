package org.eclipse.emf.henshin.variability.matcher;

import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.matcher.VariabilityAwareMatcher.RuleInfo;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityFactory;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityGraphElement;

import aima.core.logic.propositional.parsing.ast.Sentence;

/**
 * This class prepares a variability-based rule for variability-based
 * application and matching. If the rule is expected to be used later, it is
 * required to call the undo() method after the application has been performed.
 * 
 * @author Daniel Str�ber
 *
 */
public class RulePreparator {
	public Rule rule;
	public boolean checkDangling;
	private boolean injectiveMatching;
	private boolean injectiveMatchingOriginal;
	private boolean baseRule;

	public HashSet<Node> removeNodes;
	public HashSet<Edge> removeEdges;
	public HashSet<Attribute> removeAttributes;
	public Map<Edge, Node> removeEdgeSources;
	public Map<Edge, Node> removeEdgeTargets;
	public Set<Mapping> removeMappings;
	public HashMap<EObject, EObject> removeElementContainers;
	public HashSet<Formula> removeFormulas;
	public HashMap<Mapping, EStructuralFeature> removeMappingContainingRef;
	public HashMap<Formula, EStructuralFeature> removeFormulaContainingRef;
	public HashMap<Formula, EObject> pulledUpFormulasToContainer;
	public HashMap<Formula, EStructuralFeature> pulledUpFormulasToContainingRef;
	public HashMap<Formula, EObject> pulledUpFormulasToOldContainer;
	public HashMap<Formula, EStructuralFeature> pulledUpFormulasToOldContainingRef;

	public RulePreparator(Rule rule) {
		this.rule = rule;
		this.checkDangling = rule.isCheckDangling();
	}

	/**
	 * Prepares the rule for variability-based merging and rule application:
	 * rejected elements and removed and the "injective" flag is set. Assumes that
	 * the reject() method has been invoked method before.
	 * 
	 * @param rule
	 * @param ruleInfo
	 * @param rejected
	 * @param executed
	 * @return
	 */
	public BitSet prepare(RuleInfo ruleInfo, Set<Sentence> rejected, boolean injectiveMatching, boolean baseRule) {
		this.baseRule = baseRule;
		this.injectiveMatching = injectiveMatching;
		injectiveMatchingOriginal = ruleInfo.rule.isInjectiveMatching();
		removeElementContainers = new HashMap<>();
		removeNodes = new HashSet<>();
		removeEdges = new HashSet<>();
		removeAttributes = new HashSet<>();
		removeEdgeSources = new HashMap<>();
		removeEdgeTargets = new HashMap<>();
		removeMappings = new HashSet<>();
		removeMappingContainingRef = new HashMap<>();
		removeFormulaContainingRef = new HashMap<>();
		removeFormulas = new HashSet<>();

		fillMaps(ruleInfo, rejected);

		BitSet bs = getRepresentation(rule, removeAttributes, removeNodes, removeEdges, removeFormulas,
				injectiveMatching);

		doPreparation();
		return bs;
	}

	private <T extends GraphElement> void addElementToRemoveList(boolean geIsVariabilityAware, GraphElement ge,
			Collection<T> list) {
		if (geIsVariabilityAware) {
			list.add((T) ((VariabilityGraphElement) ge).getGraphElement());
		} else {
			list.add((T) ge);
		}
	}

	private void fillMaps(RuleInfo ruleInfo, Set<Sentence> rejected) {

		for (Sentence expr : rejected) {
			Set<GraphElement> elements = ruleInfo.getPc2Elem().get(expr);
			if (elements == null)
				continue;

			for (GraphElement ge : elements) {
				boolean geIsVariabilityAware = ge instanceof VariabilityGraphElement;

				if (ge instanceof Node) {
					addElementToRemoveList(geIsVariabilityAware, ge, removeNodes);
					Set<Mapping> mappings = ruleInfo.getNode2Mapping().get((Node) ge);
					if (mappings != null) {
						removeMappings.addAll(mappings);
					}
					((Node) ge).getAllEdges()
							.forEach(edge -> addElementToRemoveList(geIsVariabilityAware, edge, removeEdges));
				} else if (ge instanceof Edge) {
					addElementToRemoveList(geIsVariabilityAware, ge, removeEdges);
				} else if (ge instanceof Attribute) {
					addElementToRemoveList(geIsVariabilityAware, ge, removeAttributes);
				}

			}
		}

		if (baseRule && rule.getLhs().getFormula() != null) {
			removeFormulas.add(rule.getLhs().getFormula());
			removeElementContainers.put(rule.getLhs().getFormula(), rule.getLhs());
			removeFormulaContainingRef.put(rule.getLhs().getFormula(), rule.getLhs().getFormula().eContainingFeature());
		} else {
			for (NestedCondition ac : rule.getLhs().getNestedConditions()) {
				Sentence acPC = ruleInfo.getExpressions()
						.get(VariabilityFactory.INSTANCE.createVariabilityNestedCondition(ac).getPresenceCondition());
				if (rejected.contains(acPC)) {
					Formula removeFormula = null;
					if (ac.isNAC())
						removeFormula = (Formula) ac.eContainer();
					else if (ac.isPAC())
						removeFormula = ac;

					removeFormulas.add(removeFormula);
					removeElementContainers.put(removeFormula, removeFormula.eContainer());
					removeFormulaContainingRef.put(removeFormula, removeFormula.eContainingFeature());
				}
			}
		}
	}

	/**
	 * Prepares the rule for variability-based merging and rule application:
	 * rejected elements and removed and the "injective" flag is set. Assumes that
	 * the reject() method has been invoked method before.
	 */
	public void doPreparation() {
		if (removeNodes == null) {
			throw new IllegalStateException("This method may only be invoked after reject() has been invoked.");
		}

		removeFormulas();

		for (Mapping m : removeMappings) {
			EStructuralFeature feature = m.eContainingFeature();
			removeMappingContainingRef.put(m, feature);
			removeElementContainers.put(m, m.eContainer());
			((EList<EObject>) m.eContainer().eGet(feature)).remove(m);
		}
		for (Attribute a : removeAttributes) {
			removeElementContainers.put(a, a.getNode());
			a.getNode().getAttributes().remove(a);
		}
		for (Edge e : removeEdges) {
			removeElementContainers.put(e, e.getGraph());
			removeEdgeSources.put(e, e.getSource());
			removeEdgeTargets.put(e, e.getTarget());
			e.getGraph().getEdges().remove(e);
			e.getSource().getOutgoing().remove(e);
			e.getTarget().getIncoming().remove(e);
		}
		for (Node n : removeNodes) {
			removeElementContainers.put(n, n.getGraph());
			n.getGraph().getNodes().remove(n);
		}
		rule.setInjectiveMatching(injectiveMatching);
		rule.setCheckDangling(false); // Dangling edges are allowed in a partial
										// match.

	}

	/**
	 * Removes the formulas in the previously determined order.
	 */
	private void removeFormulas() {
		for (Formula formula : removeFormulas) {
			removeElementContainers.get(formula).eUnset(removeFormulaContainingRef.get(formula));
			removeElementContainers.get(formula).eSet(removeFormulaContainingRef.get(formula),
					HenshinFactory.eINSTANCE.createTrue());
		}
	}

	/**
	 * Puts the rule back in its original state: rejected elements and
	 * "injectiveMatching" flag are restored.
	 */
	public void undo() {
		rule.setCheckDangling(checkDangling);
		rule.setInjectiveMatching(injectiveMatchingOriginal);

		for (Node n : removeNodes) {
			((Graph) removeElementContainers.get(n)).getNodes().add(n);
		}
		for (Edge e : removeEdges) {
			((Graph) removeElementContainers.get(e)).getEdges().add(e);
			removeEdgeSources.get(e).getOutgoing().add(e);
			removeEdgeTargets.get(e).getIncoming().add(e);
		}

		for (Attribute a : removeAttributes) {
			((Node) removeElementContainers.get(a)).getAttributes().add(a);
		}

		for (Mapping m : removeMappings) {
			EStructuralFeature feature = removeMappingContainingRef.get(m);
			@SuppressWarnings("unchecked")
			EList<Mapping> list = (EList<Mapping>) removeElementContainers.get(m).eGet(feature);
			list.add(m);
		}

		restoreFormulas();

	}

	/**
	 * Restores the formulas in the previously determined order.
	 */
	private void restoreFormulas() {
		for (Formula formula : removeFormulas) {
			removeElementContainers.get(formula).eSet(removeFormulaContainingRef.get(formula), formula);
		}
//		fix
//		for (Formula f:pulledUpFormulasToOldContainingRef.keySet()) {
//			EObject container = pulledUpFormulasToOldContainer.get(f);
//			container.eSet(pulledUpFormulasToOldContainingRef.get(f), f);
//		}
//		for (Formula f:removeFormulaContainingRef.keySet()) {
//			EObject container = removeElementContainers.get(f);
//			container.eSet(removeFormulaContainingRef.get(f), f);
//		}
	}

	/**
	 * Calling this method ensures that the elements to be removed can later be
	 * added in the correct order to produce the original rule.
	 * 
	 * @param formulas All instances must be either a NestedCondition (i.e., a
	 *                 Graph) or a NOT
	 */
	private void determineRemoveOrder(Set<Formula> formulas) {
		Formula outer = rule.getLhs().getFormula(); //
		if (outer instanceof Not || outer instanceof NestedCondition) {
			Formula formula = formulas.iterator().next();
			if (formula == outer) {
				removeElementContainers.put(formula, rule.getLhs());
				removeFormulaContainingRef.put(formula, HenshinPackage.Literals.GRAPH__FORMULA);
			}
		} else if (outer instanceof And) {
			determineRemoverOrder((And) outer, formulas, rule.getLhs(), HenshinPackage.Literals.GRAPH__FORMULA);
		} else {
			throw new IllegalArgumentException(
					"TODO: Only AND-based nesting of applications conditions supported yet.");
		}
	}

	private void determineRemoverOrder(And and, Set<Formula> formulas, EObject container, EReference feature) {
		if (formulas.contains(and.getLeft()) && formulas.contains(and.getRight())) {
			removeFormulaContainingRef.put(and, feature);
			removeElementContainers.put(and, container);
		}
		if (!formulas.contains(and.getLeft()) && formulas.contains(and.getRight())) {
			designatePullupChild(and.getLeft(), and, HenshinPackage.Literals.BINARY_FORMULA__LEFT, container, feature);
		}
		if (formulas.contains(and.getLeft()) && !formulas.contains(and.getRight())) {
			designatePullupChild(and.getRight(), and, HenshinPackage.Literals.BINARY_FORMULA__RIGHT, container,
					feature);

		}
		if (!formulas.contains(and.getLeft()) && !formulas.contains(and.getRight())) {
			if (and.getLeft() instanceof And) {
				determineRemoverOrder((And) and.getLeft(), formulas, and, HenshinPackage.Literals.BINARY_FORMULA__LEFT);
			}
			if (and.getRight() instanceof And) {
				determineRemoverOrder((And) and.getRight(), formulas, and,
						HenshinPackage.Literals.BINARY_FORMULA__RIGHT);
			}
		}
	}

	private void designatePullupChild(Formula formula, And oldContainer, EReference oldFeature, EObject newContainer,
			EReference newFeature) {
		removeFormulaContainingRef.put(oldContainer, newFeature);
		removeElementContainers.put(oldContainer, newContainer);
		pulledUpFormulasToContainingRef.put(formula, newFeature);
		pulledUpFormulasToContainer.put(formula, newContainer);
		pulledUpFormulasToOldContainingRef.put(formula, oldFeature);
		pulledUpFormulasToOldContainer.put(formula, oldContainer);
	}

	/**
	 * A representation of the removed elements in a rule as a bit set. Aims at
	 * avoiding to match the same sub-rule on the same input twice.
	 * 
	 * @param rule
	 * @param deleteAttributes
	 * @param deleteNodes
	 * @param deleteEdges
	 * @param deleteFormula
	 * @param injectiveMatching
	 * @return
	 */
	private BitSet getRepresentation(Rule rule, Set<Attribute> deleteAttributes, Set<Node> deleteNodes,
			Set<Edge> deleteEdges, Set<Formula> deleteFormula, boolean injectiveMatching) {
		BitSet result = new BitSet(rule.getLhs().getNodes().size() + rule.getLhs().getEdges().size()
				+ rule.getLhs().getNestedConditions().size() + 1);

		result.set(0, injectiveMatching);
		int i = 1;

		for (NestedCondition nc : rule.getLhs().getNestedConditions()) {
			result.set(i, !deleteFormula.contains(nc));
			i++;
		}

		for (Node n : rule.getLhs().getNodes()) {
			result.set(i, !deleteNodes.contains(n));
			i++;
			for (Attribute a : n.getAttributes()) {
				result.set(i, !deleteAttributes.contains(a));
				i++;
			}
		}
		for (Edge e : rule.getLhs().getEdges()) {
			result.set(i, !deleteEdges.contains(e));
			i++;
		}

		for (Node n : rule.getRhs().getNodes()) {
			result.set(i, !deleteNodes.contains(n));
			i++;
			for (Attribute a : n.getAttributes()) {
				result.set(i, !deleteAttributes.contains(a));
				i++;
			}
		}
		for (Edge e : rule.getRhs().getEdges()) {
			result.set(i, !deleteEdges.contains(e));
			i++;
		}

		return result;
	}

	public RulePreparator getSnapShot() {
		RulePreparator result = new RulePreparator(rule);
		result.removeNodes = new HashSet<>(removeNodes);
		result.removeEdges = new HashSet<>(removeEdges);
		result.removeAttributes = new HashSet<>(removeAttributes);
		result.removeEdgeSources = new HashMap<>(removeEdgeSources);
		result.removeEdgeTargets = new HashMap<>(removeEdgeTargets);
		result.removeElementContainers = new HashMap<>(removeElementContainers);
		result.removeFormulas = new HashSet<>(removeFormulas);
		result.removeFormulaContainingRef = new HashMap<>(removeFormulaContainingRef);
//		result.pulledUpFormulasToContainer = new HashMap<Formula, EObject>(
//				pulledUpFormulasToContainer);
//		result.pulledUpFormulasToContainingRef = new HashMap<Formula, EStructuralFeature>(
//				pulledUpFormulasToContainingRef);
		result.removeMappings = new HashSet<>(removeMappings);
		result.removeMappingContainingRef = new HashMap<>(removeMappingContainingRef);
//		result.pulledUpFormulasToOldContainingRef = new HashMap<Formula, EStructuralFeature>();
//		result.pulledUpFormulasToOldContainer = new HashMap<Formula, EObject>();

		return result;

	}

}