/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.gc2ac.core;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

import graph.Graph;
import laxcondition.Operator;
import laxcondition.Quantifier;
import nestedcondition.Formula;
import nestedcondition.NestedCondition;
import nestedcondition.NestedConstraint;
import nestedcondition.NestedconditionFactory;
import nestedcondition.QuantifiedCondition;
import nestedcondition.True;

public class NestedConditionPreparer {

	private NestedConstraint constraint;
	private NestedconditionFactory factory;

	public NestedConditionPreparer(NestedConstraint constraint) {
		this.constraint = constraint;
		this.factory = NestedconditionFactory.eINSTANCE;
	}

	public boolean prepare() {

		if (eliminateNotNot()) {
			return prepare();
		}
		if (eliminateImplies()) {
			return prepare();
		}

		if (eliminateEquivalent()) {
			return prepare();
		}

		if (eliminateForAll()) {
			return prepare();
		}
		if (reduceArgumentsList()) {
			return prepare();
		}
		return true;
	}

	private boolean reduceArgumentsList() {
		TreeIterator<EObject> iter = constraint.eAllContents();
		while (iter.hasNext()) {
			EObject eObject = iter.next();
			if (reduceArg(eObject))
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean eliminateForAllANotExistsC() {
		NestedCondition condition = constraint.getCondition();
		if (isOfFormForAllANotExistsC(condition)) {
			Graph startGraph = constraint.getDomain();
			QuantifiedCondition oldForall = (QuantifiedCondition) constraint.getCondition();
			Formula oldNot = (Formula) oldForall.getCondition();
			QuantifiedCondition oldExist = (QuantifiedCondition) oldNot.getArguments().get(0);
			oldNot.setDomain(startGraph);
			oldExist.setDomain(startGraph);
			oldExist.getMorphism().getNodeMappings().clear();
			oldExist.getMorphism().setFrom(startGraph);
			oldExist.getMorphism().setTo(oldExist.getCodomain());
			constraint.setCondition(oldNot);
			System.out.println("The constraint is of form ForAllNotExist C and is refactored to Not Exists C.");
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param condition
	 * @return
	 */
	public boolean isOfFormForAllANotExistsC(NestedCondition condition) {
		if (isForAllCondition(condition)) {
			QuantifiedCondition forallCondition = (QuantifiedCondition) condition;
			NestedCondition conFormula = forallCondition.getCondition();
			if (isNotFormula(conFormula)) {
				Formula notFormula = (Formula) conFormula;
				if (isExistCondition(notFormula.getArguments().get(0))) {
					QuantifiedCondition existCondition = (QuantifiedCondition) notFormula.getArguments().get(0);
					if (existCondition.getCondition() instanceof True) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param condition
	 * @return
	 */
	public boolean isOfFormNotExistsC(NestedCondition condition) {
		if (isNotFormula(condition)) {
			Formula notFormula = (Formula) condition;
			if (isExistCondition(notFormula.getArguments().get(0))) {
				QuantifiedCondition existCondition = (QuantifiedCondition) notFormula.getArguments().get(0);
				if (existCondition.getCondition() instanceof True) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param condition
	 * @return
	 */
	public boolean isOfFormExistsC(NestedCondition condition) {
		if (isExistCondition(condition)) {
			QuantifiedCondition existCondition = (QuantifiedCondition) condition;
			if (existCondition.getCondition() instanceof True) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param eObject
	 * @return
	 */
	private boolean isExistCondition(EObject eObject) {
		if (eObject instanceof QuantifiedCondition) {
			QuantifiedCondition cond = (QuantifiedCondition) eObject;
			return (cond.getQuantifier().equals(Quantifier.EXISTS));
		}
		return false;
	}

	private boolean reduceArg(EObject eObject) {
		if (isAndFormula(eObject) || isOrFormula(eObject)) {
			Formula formula = (Formula) eObject;
			if (formula.getArguments().size() > 2) {
				NestedCondition arg1 = formula.getArguments().get(0);
				NestedCondition arg2 = formula.getArguments().get(1);
				Formula newFormula = factory.createFormula();
				newFormula.setOperator(formula.getOperator());
				newFormula.setDomain(formula.getDomain());
				formula.getArguments().set(0, newFormula);
				formula.getArguments().remove(1);
				newFormula.getArguments().add(arg1);
				newFormula.getArguments().add(arg2);
				return true;
			}
		}
		return false;
	}

	private boolean eliminateForAll() {
		TreeIterator<EObject> iter = constraint.eAllContents();
		while (iter.hasNext()) {
			EObject eObject = iter.next();
			if (isForAllCondition(eObject)) {
				QuantifiedCondition outerCondition = (QuantifiedCondition) eObject;
				NestedCondition cond = outerCondition.getCondition();
				Formula outerFormula = factory.createFormula();
				outerFormula.setOperator(Operator.NOT);
				outerFormula.setDomain(outerCondition.getDomain());
				EObject container = outerCondition.eContainer();
				insert(container, outerCondition, outerFormula);
				outerFormula.getArguments().add(outerCondition);
				outerCondition.setQuantifier(Quantifier.EXISTS);
				Formula innerFormula = factory.createFormula();
				innerFormula.setOperator(Operator.NOT);
				innerFormula.setDomain(outerCondition.getCodomain());
				outerCondition.setCondition(innerFormula);
				innerFormula.getArguments().add(cond);
				return true;
			}
		}
		return false;
	}

	private boolean eliminateEquivalent() {
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

	private boolean eliminateImplies() {
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

	private boolean eliminateNotNot() {
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
		if (container instanceof NestedConstraint) {
			NestedConstraint constraint = (NestedConstraint) container;
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

	private boolean isForAllCondition(EObject eObject) {
		if (eObject instanceof QuantifiedCondition) {
			QuantifiedCondition cond = (QuantifiedCondition) eObject;
			return (cond.getQuantifier().equals(Quantifier.FORALL));
		}
		return false;
	}

	public NestedCondition getCondition() {
		return this.constraint.getCondition();
	}
}