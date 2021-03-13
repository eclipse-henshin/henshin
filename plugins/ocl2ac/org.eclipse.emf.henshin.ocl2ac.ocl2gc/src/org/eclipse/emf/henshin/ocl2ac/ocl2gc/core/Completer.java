/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.ocl2gc.core;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.henshin.ocl2ac.ocl2gc.util.MorphismHelper;
import org.eclipse.emf.henshin.ocl2ac.ocl2gc.util.MorphismPair;
import graph.Graph;
import graph.GraphFactory;
import laxcondition.Condition;
import laxcondition.Formula;
import laxcondition.LaxCondition;
import laxcondition.Operator;
import laxcondition.QuantifiedLaxCondition;
import laxcondition.Quantifier;
import laxcondition.True;
import nestedcondition.NestedCondition;
import nestedcondition.NestedConstraint;
import nestedcondition.NestedconditionFactory;

public class Completer {

	private Condition condition = null;
	private NestedConstraint constraint = null;
	private NestedconditionFactory factory = null;
	private GraphFactory graphFactory = null;
	private EPackage typeGraph = null;

	public Completer(Condition condition) {
		this.condition = condition;

		this.factory = NestedconditionFactory.eINSTANCE;
		this.graphFactory = GraphFactory.eINSTANCE;
	}

	public long complete() {
		if (this.condition == null)
			return -2;
		this.typeGraph = condition.getTypeGraph();
		long start = System.currentTimeMillis();
		this.constraint = factory.createNestedConstraint();
		Graph emptyGraph = graphFactory.createGraph();
		emptyGraph.setTypegraph(typeGraph);
		this.constraint.setDomain(emptyGraph);
		this.constraint.setTypeGraph(typeGraph);
		this.constraint.setName(condition.getName());
		this.constraint.setCondition(complete(condition.getLaxCondition(), emptyGraph));
		long stop = System.currentTimeMillis();
		return (stop - start);
	}

	private NestedCondition complete(LaxCondition laxCondition, Graph graph) {
		if (laxCondition instanceof laxcondition.True) {
			return completeTrue((laxcondition.True) laxCondition, graph);
		}
		if (laxCondition instanceof laxcondition.Formula) {
			laxcondition.Formula formula = (laxcondition.Formula) laxCondition;
			System.out.println("Formula: " + formula + "; Graph: " + graph);
			if (formula.getOp().equals(Operator.NOT)) {
				return completeNot(formula, graph);
			}
			if (formula.getOp().equals(Operator.IMPLIES)) {
				return completeImplies(formula, graph);
			}
			if (formula.getOp().equals(Operator.EQUIVALENT)) {
				return completeEquivalent(formula, graph);
			}
			if (formula.getOp().equals(Operator.XOR)) {
				return completeXor(formula, graph);
			}
			if (formula.getOp().equals(Operator.AND)) {
				return completeAnd(formula, graph);
			}
			if (formula.getOp().equals(Operator.OR)) {
				return completeOr(formula, graph);
			}
		}
		if (laxCondition instanceof laxcondition.QuantifiedLaxCondition) {
			laxcondition.QuantifiedLaxCondition quantifiedLaxCondition = (laxcondition.QuantifiedLaxCondition) laxCondition;
			System.out.println("Q-Lax: " + quantifiedLaxCondition + "; Graph: " + graph);
			System.out.println("Quantifier: " + quantifiedLaxCondition.getQuantifier());
			if (quantifiedLaxCondition.getQuantifier().equals(Quantifier.EXISTS)) {
				return completeExists(quantifiedLaxCondition, graph);
			}
			if (quantifiedLaxCondition.getQuantifier().equals(Quantifier.FORALL)) {
				return completeForAll(quantifiedLaxCondition, graph);
			}
		}
		return null;
	}

	private NestedCondition completeForAll(QuantifiedLaxCondition quantifiedLaxCondition, Graph domain) {
		EList<MorphismPair> morphismPairs = new BasicEList<MorphismPair>();
		morphismPairs = MorphismHelper.getCompleteMorphisms(domain, quantifiedLaxCondition.getGraph());
		if (morphismPairs.isEmpty())
			return completeTrue(null, domain);
		if (morphismPairs.size() == 1) {
			return getForAllCondition(quantifiedLaxCondition, morphismPairs.get(0));
		} else {
			nestedcondition.Formula cond = factory.createFormula();
			cond.setDomain(domain);
			cond.setOperator(Operator.AND);
			for (MorphismPair morphismPair : morphismPairs) {
				cond.getArguments().add(getForAllCondition(quantifiedLaxCondition, morphismPair));
			}
			return cond;
		}
	}

	private NestedCondition getForAllCondition(QuantifiedLaxCondition quantifiedLaxCondition,
			MorphismPair morphismPair) {
		nestedcondition.QuantifiedCondition cond = factory.createQuantifiedCondition();
		cond.setQuantifier(Quantifier.FORALL);
		cond.setDomain(morphismPair.getP());
		cond.setMorphism(morphismPair.getMorphismP());
		cond.setCodomain(morphismPair.getResultGraph());
		cond.setCondition(complete(quantifiedLaxCondition.getCondition(), cond.getCodomain()));
		return cond;
	}

	private NestedCondition completeExists(QuantifiedLaxCondition quantifiedLaxCondition, Graph domain) {
		EList<MorphismPair> morphismPairs = new BasicEList<MorphismPair>();
		morphismPairs = MorphismHelper.getCompleteMorphisms(domain, quantifiedLaxCondition.getGraph());
		if (morphismPairs.isEmpty())
			return completeTrue(null, domain);
		if (morphismPairs.size() == 1) {
			return getExistsCondition(quantifiedLaxCondition, morphismPairs.get(0));
		} else {
			nestedcondition.Formula cond = factory.createFormula();
			cond.setDomain(domain);
			cond.setOperator(Operator.OR);
			for (MorphismPair morphismPair : morphismPairs) {
				cond.getArguments().add(getExistsCondition(quantifiedLaxCondition, morphismPair));
			}
			return cond;
		}
	}

	private NestedCondition getExistsCondition(QuantifiedLaxCondition quantifiedLaxCondition,
			MorphismPair morphismPair) {
		nestedcondition.QuantifiedCondition cond = factory.createQuantifiedCondition();
		cond.setQuantifier(Quantifier.EXISTS);
		cond.setDomain(morphismPair.getP());
		cond.setMorphism(morphismPair.getMorphismP());
		cond.setCodomain(morphismPair.getResultGraph());
		cond.setCondition(complete(quantifiedLaxCondition.getCondition(), cond.getCodomain()));
		return cond;
	}

	private NestedCondition completeOr(Formula formula, Graph graph) {
		nestedcondition.Formula cond = factory.createFormula();
		cond.setDomain(graph);
		cond.setOperator(Operator.OR);
		for (LaxCondition lax : formula.getArguments()) {
			cond.getArguments().add(complete(lax, graph));
		}
		return cond;
	}

	private NestedCondition completeAnd(Formula formula, Graph graph) {
		nestedcondition.Formula cond = factory.createFormula();
		cond.setDomain(graph);
		cond.setOperator(Operator.AND);
		for (LaxCondition lax : formula.getArguments()) {
			cond.getArguments().add(complete(lax, graph));
		}
		return cond;
	}

	private NestedCondition completeXor(Formula formula, Graph graph) {
		nestedcondition.Formula cond = factory.createFormula();
		cond.setDomain(graph);
		cond.setOperator(Operator.XOR);
		cond.getArguments().add(complete(formula.getArguments().get(0), graph));
		cond.getArguments().add(complete(formula.getArguments().get(1), graph));
		return cond;
	}

	private NestedCondition completeEquivalent(Formula formula, Graph graph) {
		nestedcondition.Formula cond = factory.createFormula();
		cond.setDomain(graph);
		cond.setOperator(Operator.EQUIVALENT);
		cond.getArguments().add(complete(formula.getArguments().get(0), graph));
		cond.getArguments().add(complete(formula.getArguments().get(1), graph));
		return cond;
	}

	private NestedCondition completeImplies(Formula formula, Graph graph) {
		nestedcondition.Formula cond = factory.createFormula();
		cond.setDomain(graph);
		cond.setOperator(Operator.IMPLIES);
		cond.getArguments().add(complete(formula.getArguments().get(0), graph));
		cond.getArguments().add(complete(formula.getArguments().get(1), graph));
		return cond;
	}

	private NestedCondition completeNot(Formula formula, Graph graph) {
		nestedcondition.Formula cond = factory.createFormula();
		cond.setDomain(graph);
		cond.setOperator(Operator.NOT);
		cond.getArguments().add(complete(formula.getArguments().get(0), graph));
		return cond;
	}

	private NestedCondition completeTrue(True laxCondition, Graph graph) {
		nestedcondition.True cond = factory.createTrue();
		cond.setDomain(graph);
		return cond;
	}

	public NestedConstraint getConstraint() {
		if (this.constraint == null) {
			this.constraint = factory.createNestedConstraint();
			this.constraint.setName("");
		}
		return constraint;
	}

}
