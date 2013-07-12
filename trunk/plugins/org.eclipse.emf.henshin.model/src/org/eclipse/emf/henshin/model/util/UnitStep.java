package org.eclipse.emf.henshin.model.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.model.IteratedUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;

public class UnitStep {

	public final Unit unit;
	public final int step;

	public UnitStep(Unit unit, int step) {
		this.unit = unit;
		this.step = step;
	}

	@Override
	public String toString() {
		return "(" + unit + "," + step + ")";
	}

	public static Iterator<UnitStep> generateExecutionTrace(final Unit start, final boolean unfoldIteratedUnit, final Map<Rule,Integer> ruleStepCount) {
		final Map<Rule,Integer> theRuleStepCount = 
				(ruleStepCount!=null) ? ruleStepCount : new HashMap<Rule, Integer>();
		final Deque<UnitStep> stack = new ArrayDeque<UnitStep>();
		stack.push(new UnitStep(start, 0));
		return new Iterator<UnitStep>() {
			@Override
			public boolean hasNext() {
				return !stack.isEmpty();
			}
			@Override
			public UnitStep next() {
				UnitStep unitStep = stack.peek();
				UnitStep result = unitStep;
				Unit calledUnit = null;

				if (unitStep.unit instanceof IteratedUnit) {
					int iterations = Integer.parseInt(((IteratedUnit) unitStep.unit).getIterations());
					if (unitStep.step < iterations) {
						calledUnit = ((IteratedUnit) unitStep.unit).getSubUnit();
					}
				} else {
					List<Unit> subUnits = unitStep.unit.getSubUnits(false);
					if (unitStep.step < subUnits.size()) {
						calledUnit = subUnits.get(unitStep.step);
					}
				}

				if (calledUnit!=null) {
					stack.push(new UnitStep(calledUnit, 0));
				} else {
					while (!stack.isEmpty()) {
						UnitStep prevStep = stack.pop();
						int length;
						if (unfoldIteratedUnit && prevStep.unit instanceof IteratedUnit) {
							length = Integer.parseInt(((IteratedUnit) prevStep.unit).getIterations());
						}
						else if (prevStep.unit instanceof Rule && theRuleStepCount.containsKey(prevStep.unit)) {
							length = theRuleStepCount.get(prevStep.unit);
						}
						else {
							length = prevStep.unit.getSubUnits(false).size();
						}
						if (prevStep.step < length-1) {
							stack.push(new UnitStep(prevStep.unit, prevStep.step + 1));
							break;
						}
						unitStep = prevStep;
					}
				}
				return result;
			}
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

}
