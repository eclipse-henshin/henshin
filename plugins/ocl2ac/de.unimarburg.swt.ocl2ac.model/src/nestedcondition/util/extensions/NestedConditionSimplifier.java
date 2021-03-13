/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package nestedcondition.util.extensions;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

import graph.Edge;
import graph.Graph;
import graph.Node;
import laxcondition.Operator;
import laxcondition.Quantifier;
import nestedcondition.EdgeMapping;
import nestedcondition.Formula;
import nestedcondition.Morphism;
import nestedcondition.NestedCondition;
import nestedcondition.NestedConstraint;
import nestedcondition.NestedconditionFactory;
import nestedcondition.NodeMapping;
import nestedcondition.QuantifiedCondition;
import nestedcondition.True;

public class NestedConditionSimplifier {

	private NestedConstraint constraint;
	private NestedconditionFactory factory;

	public NestedConditionSimplifier(NestedConstraint constraint) {
		this.constraint = constraint;
		this.factory = NestedconditionFactory.eINSTANCE;
	}

	public boolean simplify() {
		if (simplifyTrueOrFalseInFormula()) {
			return simplify();
		}
		if (simplifyNotNot()) {
			return simplify();
		}
		if (simplifyImplies()) {
			return simplify();
		}
		if (simplifyEquivalent()) {
			return simplify();
		}
		if (simplifyXor()) {
			return simplify();
		}
		if (simplifyExists()) {
			return simplify();
		}
		if (simplifyForAll()) {
			return simplify();
		}
		return true;
	}

	private boolean simplifyExists() {
		TreeIterator<EObject> iter = constraint.eAllContents();
		while (iter.hasNext()) {
			EObject eObject = iter.next();
			if (isExistsCondition(eObject)) {
				QuantifiedCondition outerCondition = (QuantifiedCondition) eObject;
				NestedCondition cond = outerCondition.getCondition();
				if (isExistsCondition(cond)) {
					QuantifiedCondition innerCondition = (QuantifiedCondition) cond;
					Morphism outerMorphism = outerCondition.getMorphism();
					Morphism innerMorphism = innerCondition.getMorphism();
					for (EdgeMapping outerEM : outerMorphism.getEdgeMappings()) {
						Edge gluingEdge = outerEM.getImage();
						for (EdgeMapping innerEM : innerMorphism.getEdgeMappings()) {
							if (innerEM.getOrigin() == gluingEdge) {
								outerEM.setImage(innerEM.getImage());
								break;
							}
						}
					}
					for (NodeMapping outerNM : outerMorphism.getNodeMappings()) {
						Node gluingNode = outerNM.getImage();
						for (NodeMapping innerNM : innerMorphism.getNodeMappings()) {
							if (innerNM.getOrigin() == gluingNode) {
								outerNM.setImage(innerNM.getImage());
								break;
							}
						}
					}
					outerMorphism.setTo(innerMorphism.getTo());
					Graph innerDomain = innerCondition.getCodomain();
					innerCondition.setCodomain(null);
					outerCondition.setCodomain(innerDomain);
					NestedCondition innerInnerCondition = innerCondition.getCondition();
					innerCondition.setCondition(null);
					outerCondition.setCondition(innerInnerCondition);
				}
			}
		}
		return false;
	}

	private boolean simplifyForAll() {
		TreeIterator<EObject> iter = constraint.eAllContents();
		while (iter.hasNext()) {
			EObject eObject = iter.next();
			if (isForAllCondition(eObject)) {
				QuantifiedCondition outerCondition = (QuantifiedCondition) eObject;
				NestedCondition cond = outerCondition.getCondition();
				if (isForAllCondition(cond)) {
					QuantifiedCondition innerCondition = (QuantifiedCondition) cond;
					Morphism outerMorphism = outerCondition.getMorphism();
					Morphism innerMorphism = innerCondition.getMorphism();
					for (EdgeMapping outerEM : outerMorphism.getEdgeMappings()) {
						Edge gluingEdge = outerEM.getImage();
						for (EdgeMapping innerEM : innerMorphism.getEdgeMappings()) {
							if (innerEM.getOrigin() == gluingEdge) {
								outerEM.setImage(innerEM.getImage());
								break;
							}
						}
					}
					for (NodeMapping outerNM : outerMorphism.getNodeMappings()) {
						Node gluingNode = outerNM.getImage();
						for (NodeMapping innerNM : innerMorphism.getNodeMappings()) {
							if (innerNM.getOrigin() == gluingNode) {
								outerNM.setImage(innerNM.getImage());
								break;
							}
						}
					}
					outerMorphism.setTo(innerMorphism.getTo());
					Graph innerDomain = innerCondition.getCodomain();
					innerCondition.setCodomain(null);
					outerCondition.setCodomain(innerDomain);
					NestedCondition innerInnerCondition = innerCondition.getCondition();
					innerCondition.setCondition(null);
					outerCondition.setCondition(innerInnerCondition);
				}
			}
		}
		return false;
	}

	private boolean simplifyXor() {
		TreeIterator<EObject> iter = constraint.eAllContents();
		while (iter.hasNext()) {
			EObject eObject = iter.next();
			if (isXorFormula(eObject)) {
				Formula formula = (Formula) eObject;
				Formula firstArg = (Formula) getCopy(formula);
				Formula secondArg = (Formula) getCopy(formula);
				swapArguments(secondArg);
				negateFirstArgument(firstArg);
				negateFirstArgument(secondArg);
				firstArg.setOperator(Operator.AND);
				secondArg.setOperator(Operator.AND);
				formula.setOperator(Operator.OR);
				formula.getArguments().set(0, firstArg);
				formula.getArguments().set(1, secondArg);
				return true;
			}
		}
		return false;
	}

	private void negateFirstArgument(Formula formula) {
		NestedCondition firstArgument = formula.getArguments().get(0);
		Formula notFormula = factory.createFormula();
		notFormula.setOperator(Operator.NOT);
		formula.getArguments().set(0, notFormula);
		notFormula.getArguments().add(firstArgument);
	}

	private boolean simplifyEquivalent() {
		TreeIterator<EObject> iter = constraint.eAllContents();
		while (iter.hasNext()) {
			EObject eObject = iter.next();
			if (isEquivalentFormula(eObject)) {
				Formula formula = (Formula) eObject;
				Formula firstArg = (Formula) getCopy(formula);
				Formula secondArg = (Formula) getCopy(formula);
				swapArguments(secondArg);
				firstArg.setOperator(Operator.IMPLIES);
				secondArg.setOperator(Operator.IMPLIES);
				formula.setOperator(Operator.AND);
				formula.getArguments().set(0, firstArg);
				formula.getArguments().set(1, secondArg);
				return true;
			}
		}
		return false;
	}

	private void swapArguments(Formula formula) {
		NestedCondition arg1 = formula.getArguments().get(0);
		NestedCondition arg2 = formula.getArguments().get(1);
		formula.getArguments().set(0, arg2);
		formula.getArguments().set(1, arg1);
	}

	private EObject getCopy(EObject original) {
		Copier copier = new Copier();
		EObject copy = copier.copy(original);
		copier.copyReferences();
		return copy;
	}

	private boolean simplifyImplies() {
		TreeIterator<EObject> iter = constraint.eAllContents();
		while (iter.hasNext()) {
			EObject eObject = iter.next();
			if (isImpliesFormula(eObject)) {
				Formula formula = (Formula) eObject;
				NestedCondition arg1 = formula.getArguments().get(0);
				Formula notFormula = factory.createFormula();
				notFormula.setDomain(formula.getDomain());
				notFormula.setOperator(Operator.NOT);
				formula.setOperator(Operator.OR);
				formula.getArguments().set(0, notFormula);
				notFormula.getArguments().add(arg1);
				return true;
			}
		}
		return false;
	}

	private boolean simplifyTrueOrFalseInFormula() {
		TreeIterator<EObject> iter = constraint.eAllContents();
		while (iter.hasNext()) {
			EObject eObject = iter.next();
			if (isAndFormula(eObject)) {
				Formula formula = (Formula) eObject;
				EObject container = formula.eContainer();
				NestedCondition arg1 = formula.getArguments().get(0);
				NestedCondition arg2 = formula.getArguments().get(1);
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
				NestedCondition arg1 = formula.getArguments().get(0);
				NestedCondition arg2 = formula.getArguments().get(1);
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

	private boolean removeArgument(Formula formula, NestedCondition cond) {
		return formula.getArguments().remove(cond);
	}

	private boolean twoArguments(Formula formula) {
		return (formula.getArguments().size() == 2);
	}

	private boolean isFalse(NestedCondition cond) {
		if (isNotFormula(cond)) {
			Formula formula = (Formula) cond;
			return isTrue(formula.getArguments().get(0));
		}
		return false;
	}

	private boolean isTrue(NestedCondition cond) {
		return (cond instanceof True);
	}

	private boolean simplifyNotNot() {
		TreeIterator<EObject> iter = constraint.eAllContents();
		while (iter.hasNext()) {
			EObject eObject = iter.next();
			if (isNotFormula(eObject)) {
				Formula formula = (Formula) eObject;
				if (isNotFormula(formula.getArguments().get(0))) {
					Formula innerFormula = (Formula) formula.getArguments().get(0);
					NestedCondition innerCondition = innerFormula.getArguments().get(0);
					EObject container = formula.eContainer();
					return insert(container, formula, innerCondition);
				}
			}
		}
		return false;
	}

	private boolean insert(EObject container, NestedCondition oldContent, NestedCondition newContent) {
		if (container == constraint) {
			constraint.setCondition(newContent);
			return true;
		}
		if (container instanceof QuantifiedCondition) {
			QuantifiedCondition qlc = (QuantifiedCondition) container;
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

	private boolean isNotFormula(EObject eObject) {
		if (eObject instanceof Formula) {
			Formula formula = (Formula) eObject;
			return (formula.getOperator().equals(Operator.NOT));
		}
		return false;
	}

	private boolean isAndFormula(EObject eObject) {
		if (eObject instanceof Formula) {
			Formula formula = (Formula) eObject;
			return (formula.getOperator().equals(Operator.AND));
		}
		return false;
	}

	private boolean isOrFormula(EObject eObject) {
		if (eObject instanceof Formula) {
			Formula formula = (Formula) eObject;
			return (formula.getOperator().equals(Operator.OR));
		}
		return false;
	}

	private boolean isImpliesFormula(EObject eObject) {
		if (eObject instanceof Formula) {
			Formula formula = (Formula) eObject;
			return (formula.getOperator().equals(Operator.IMPLIES));
		}
		return false;
	}

	private boolean isEquivalentFormula(EObject eObject) {
		if (eObject instanceof Formula) {
			Formula formula = (Formula) eObject;
			return (formula.getOperator().equals(Operator.EQUIVALENT));
		}
		return false;
	}

	private boolean isXorFormula(EObject eObject) {
		if (eObject instanceof Formula) {
			Formula formula = (Formula) eObject;
			return (formula.getOperator().equals(Operator.XOR));
		}
		return false;
	}

	private boolean isExistsCondition(EObject eObject) {
		if (eObject instanceof QuantifiedCondition) {
			QuantifiedCondition cond = (QuantifiedCondition) eObject;
			return (cond.getQuantifier().equals(Quantifier.EXISTS));
		}
		return false;
	}

	private boolean isForAllCondition(EObject eObject) {
		if (eObject instanceof QuantifiedCondition) {
			QuantifiedCondition cond = (QuantifiedCondition) eObject;
			return (cond.getQuantifier().equals(Quantifier.FORALL));
		}
		return false;
	}
}