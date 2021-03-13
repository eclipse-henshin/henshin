/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.gc2ac.util;

import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Rule;

import org.eclipse.emf.henshin.ocl2ac.gc2ac.core.OverlapOptimizer;
import org.eclipse.emf.henshin.ocl2ac.ocl2gc.util.JointPairs;
import laxcondition.Operator;
import laxcondition.Quantifier;
import morphisms.Mapping;
import morphisms.MorphismsFactory;
import morphisms.MorphismsPackage;
import morphisms.Pair;
import nestedcondition.Formula;
import nestedcondition.Morphism;
import nestedcondition.NestedCondition;
import nestedcondition.NestedConstraint;
import nestedcondition.NestedconditionFactory;
import nestedcondition.NodeMapping;
import nestedcondition.QuantifiedCondition;
import nestedcondition.True;

public class Shifter {

	private Morphism morphism;
	private NestedCondition condition;
	private EPackage typeModel;
	private NestedCondition result;
	private final NestedconditionFactory factory = NestedconditionFactory.eINSTANCE;

	private static boolean enableOptimizer = false;
	private static Rule rule;
	private static NestedConstraint constraint;

	private static boolean recursionEnd = false;

	public Shifter(Morphism morphism, NestedCondition condition, EPackage typeModel) {
		super();
		this.morphism = morphism;
		this.condition = condition;
		this.typeModel = typeModel;
	}

	public NestedCondition getResult() {
		return result;
	}

	public void shift() {
		if (recursionEnd) {
			result = null;
			return;
		}
		if (condition instanceof True) {
			shiftTrue();
		}
		if (condition instanceof Formula) {
			shiftFormular();
		}
		if (condition instanceof QuantifiedCondition) {
			shiftQuantifiedCondition();
		}
	}

	private void shiftQuantifiedCondition() {
		QuantifiedCondition cond = (QuantifiedCondition) condition;
		EList<MorphismPair> morphismPairs = constructMorphismPairs(morphism, cond.getMorphism());

		if (morphismPairs == null || morphismPairs.size() == 0 || recursionEnd) {
			recursionEnd = true;
			result = null;
			return;
		} else if (morphismPairs.size() == 1) {
			MorphismPair pair = morphismPairs.get(0);
			this.result = constructQuantifiedCondition(cond, pair);
		} else {
			Formula ret = factory.createFormula();
			ret.setDomain(morphism.getTo());
			if (cond.getQuantifier().equals(Quantifier.EXISTS)) {
				ret.setOperator(Operator.OR);
			} else {
				ret.setOperator(Operator.AND);
			}
			for (MorphismPair pair : morphismPairs) {
				QuantifiedCondition quantifiedCondition = constructQuantifiedCondition(cond, pair);
				ret.getArguments().add(quantifiedCondition);
			}
			this.result = ret;
		}
	}

	private QuantifiedCondition constructQuantifiedCondition(QuantifiedCondition cond, MorphismPair pair) {
		if (recursionEnd) {
			recursionEnd = true;
			result = null;
			return null;
		}
		QuantifiedCondition ret = factory.createQuantifiedCondition();
		ret.setQuantifier(cond.getQuantifier());
		ret.setDomain(pair.getAPrime().getFrom());
		ret.setCodomain(pair.getAPrime().getTo());
		ret.setMorphism(pair.getAPrime());
		Shifter shifter = new Shifter(pair.getBPrime(), cond.getCondition(), typeModel);
		shifter.shift();
		ret.setCondition(shifter.getResult());
		return ret;
	}

	private EList<MorphismPair> constructMorphismPairs(Morphism b, Morphism a) {
		EList<MorphismPair> morphismPairs = new BasicEList<MorphismPair>();
		Pair span = MorphismsPackage.eINSTANCE.getMorphismsFactory().createPair();
		span.setA(convertMorphismEMF2Graph(a));
		span.setB(convertMorphismEMF2Graph(b));
		Set<Pair> pairs = JointPairs.getCommutingPairs(span);
		// Optimizer
		if (isEnableOptimizer()) {
			OverlapOptimizer oo = new OverlapOptimizer(rule, constraint, pairs);
			oo.optimizeOverlap();
			if (pairs.size() == 0)
				System.out.println("The size of the pairs after the optimization is " + pairs.size());
		}

		if (pairs.size() == 0 || recursionEnd) {
			recursionEnd = true;
			return null;
		} else

		{
			for (Pair pair : pairs) {
				MorphismPair morphismPair = new MorphismPair();
				morphismPair.setMorphismAPrime(convertMorphismGraph2EMF(pair.getB()));
				morphismPair.setMorphismBPrime(convertMorphismGraph2EMF(pair.getA()));
				morphismPairs.add(morphismPair);
			}
			return morphismPairs;
		}
	}

	private Morphism convertMorphismGraph2EMF(morphisms.Morphism morphismGraph) {
		NestedconditionFactory factory = NestedconditionFactory.eINSTANCE;
		Morphism morphismEMF = factory.createMorphism();
		morphismEMF.setFrom(morphismGraph.getDomain());
		morphismEMF.setTo(morphismGraph.getCodomain());
		for (morphisms.Mapping m : morphismGraph.getMappings()) {
			NodeMapping nm = factory.createNodeMapping();
			nm.setOrigin(m.getOrigin());
			nm.setImage(m.getImage());
			morphismEMF.getNodeMappings().add(nm);
		}
		return morphismEMF;
	}

	private morphisms.Morphism convertMorphismEMF2Graph(Morphism morphismEMF) {
		MorphismsFactory factory = MorphismsFactory.eINSTANCE;
		morphisms.Morphism morphismGraph = factory.createMorphism();
		morphismGraph.setDomain(morphismEMF.getFrom());
		morphismGraph.setCodomain(morphismEMF.getTo());
		for (NodeMapping m : morphismEMF.getNodeMappings()) {
			Mapping mapping = factory.createMapping();
			mapping.setOrigin(m.getOrigin());
			mapping.setImage(m.getImage());
			morphismGraph.getMappings().add(mapping);
		}
		return morphismGraph;
	}

	private void shiftFormular() {
		if (recursionEnd) {
			recursionEnd = true;
			result = null;
			return;
		}
		Formula formula = (Formula) condition;
		Formula ret = factory.createFormula();
		ret.setDomain(morphism.getTo());
		ret.setOperator(formula.getOperator());
		for (NestedCondition arg : formula.getArguments()) {
			Shifter shifter = new Shifter(morphism, arg, typeModel);
			shifter.shift();
			if (recursionEnd) {
				recursionEnd = true;
				result = null;
				return;
			}
			ret.getArguments().add(shifter.getResult());
		}
		this.result = ret;
	}

	private void shiftTrue() {
		if (recursionEnd) {
			recursionEnd = true;
			result = null;
			return;
		}
		this.result = factory.createTrue();
		result.setDomain(morphism.getTo());
	}

	public boolean isEnableOptimizer() {
		return enableOptimizer;
	}

	public void setEnableOptimizer(boolean enableOptimizer, Rule rule, NestedConstraint constraint) {
		Shifter.enableOptimizer = enableOptimizer;
		Shifter.rule = rule;
		Shifter.constraint = constraint;
	}

	public static void reset() {
		recursionEnd = false;
		enableOptimizer = false;
	}
}
