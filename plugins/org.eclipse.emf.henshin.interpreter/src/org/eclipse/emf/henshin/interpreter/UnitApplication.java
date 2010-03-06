package org.eclipse.emf.henshin.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import org.eclipse.emf.henshin.interpreter.interfaces.InterpreterEngine;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.model.AmalgamatedUnit;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.CountedUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Port;
import org.eclipse.emf.henshin.model.PortKind;
import org.eclipse.emf.henshin.model.PortObject;
import org.eclipse.emf.henshin.model.PortParameter;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.Variable;

public class UnitApplication {
	InterpreterEngine engine;
	TransformationUnit transformationUnit;

	Map<String, Object> portValues;
	Map<String, Object> oldPortValues;

	Stack<RuleApplication> appliedRules;

	public UnitApplication(InterpreterEngine engine,
			TransformationUnit transformationUnit) {
		this.engine = engine;
		this.transformationUnit = transformationUnit;
		this.portValues = new HashMap<String, Object>();
		this.oldPortValues = new HashMap<String, Object>(portValues);

		this.appliedRules = new Stack<RuleApplication>();
	}

	public UnitApplication(InterpreterEngine engine,
			TransformationUnit transformationUnit,
			Map<String, Object> portValues) {
		this.engine = engine;
		this.transformationUnit = transformationUnit;
		this.portValues = portValues;
		this.oldPortValues = new HashMap<String, Object>(portValues);

		this.appliedRules = new Stack<RuleApplication>();
	}

	public boolean execute() {
		switch (transformationUnit.eClass().getClassifierID()) {
		case HenshinPackage.RULE:
			return executeRule();
		case HenshinPackage.AMALGAMATED_UNIT:
			return executeAmalgamatedUnit();
		case HenshinPackage.INDEPENDENT_UNIT:
			return executeIndependentUnit();
		case HenshinPackage.SEQUENTIAL_UNIT:
			return executeSequentialUnit();
		case HenshinPackage.CONDITIONAL_UNIT:
			return executeConditionalUnit();
		case HenshinPackage.PRIORITY_UNIT:
			return executePriorityUnit();
		case HenshinPackage.COUNTED_UNIT:
			return executeCountedUnit();
		}

		return false;
	}

	public void undo() {
		while (!appliedRules.isEmpty())
			appliedRules.pop().undo();

		portValues = oldPortValues;
	}

	private UnitApplication createApplicationFor(TransformationUnit unit) {
		return new UnitApplication(engine, unit, portValues);
	}

	private void updatePortValues(Match comatch) {
		for (Port port : transformationUnit.getPorts()) {
			if (port.getDirection() == PortKind.OUTPUT
					|| port.getDirection() == PortKind.INPUT_OUTPUT) {
				if (port instanceof PortParameter) {
					Variable var = ((PortParameter) port).getVariable();
					Object value = comatch.getParameterMapping().get(
							var.getName());
					portValues.put(port.getName(), value);
				} else {
					Node node = ((PortObject) port).getNode();
					Object value = comatch.getNodeMapping().get(node);
					portValues.put(port.getName(), value);
				}
			}
		}
	}

	private boolean executeIndependentUnit() {
		IndependentUnit independentUnit = (IndependentUnit) transformationUnit;
		List<TransformationUnit> possibleUnits = new ArrayList<TransformationUnit>(
				independentUnit.getSubUnits());

		while (possibleUnits.size() > 0) {
			int index = new Random().nextInt(possibleUnits.size());
			UnitApplication unitApplication = createApplicationFor(possibleUnits
					.get(index));
			if (!unitApplication.execute()) {
				possibleUnits.remove(index);
			} else {
				if (unitApplication.appliedRules.size() > 0) {
					appliedRules.addAll(unitApplication.appliedRules);
					possibleUnits = new ArrayList<TransformationUnit>(
							independentUnit.getSubUnits());
				}
			}
		}

		return true;
	}

	private boolean executeRule() {
		Rule rule = (Rule) transformationUnit;

		RuleApplication ruleApplication = new RuleApplication(engine, rule);
		Match match = new Match(rule, ModelHelper.createAssignments(
				transformationUnit, portValues), ModelHelper.createPrematch(
				transformationUnit, portValues));
		ruleApplication.setMatch(match);
		if (ruleApplication.apply()) {
			updatePortValues(ruleApplication.getComatch());
			appliedRules.push(ruleApplication);
			return true;
		} else {
			return false;
		}
	}

	private boolean executeAmalgamatedUnit() {
		AmalgamatedUnit amalUnit = (AmalgamatedUnit) transformationUnit;
		RuleApplication amalgamatedRule = engine.generateAmalgamatedRule(
				amalUnit, portValues);

		if (amalgamatedRule != null) {
			amalgamatedRule.apply();
			updatePortValues(amalgamatedRule.getComatch());
			appliedRules.push(amalgamatedRule);
			return true;
		} else {
			return false;
		}
	}

	private boolean executeSequentialUnit() {
		SequentialUnit sequentialUnit = (SequentialUnit) transformationUnit;
		for (TransformationUnit subUnit : sequentialUnit.getSubUnits()) {
			UnitApplication genericUnit = createApplicationFor(subUnit);
			if (genericUnit.execute()) {
				appliedRules.addAll(genericUnit.appliedRules);
			} else {
				undo();
				return false;
			}
		}

		return true;
	}

	private boolean executeConditionalUnit() {
		boolean success = false;

		ConditionalUnit conditionalUnit = (ConditionalUnit) transformationUnit;
		TransformationUnit ifUnit = conditionalUnit.getIf();
		UnitApplication genericIfUnit = createApplicationFor(ifUnit);
		if (genericIfUnit.execute()) {
			appliedRules.addAll(genericIfUnit.appliedRules);

			TransformationUnit thenUnit = conditionalUnit.getThen();
			UnitApplication genericThenUnit = createApplicationFor(thenUnit);
			success = genericThenUnit.execute();
			appliedRules.addAll(genericThenUnit.appliedRules);
		} else {
			if (conditionalUnit.getElse() != null) {
				TransformationUnit elseUnit = conditionalUnit.getElse();
				UnitApplication genericElseUnit = createApplicationFor(elseUnit);
				success = genericElseUnit.execute();
			}
		}

		if (!success)
			undo();

		return success;
	}

	private boolean executePriorityUnit() {
		PriorityUnit priorityUnit = (PriorityUnit) transformationUnit;
		List<TransformationUnit> possibleUnits = new ArrayList<TransformationUnit>(
				priorityUnit.getSubUnits());

		while (possibleUnits.size() > 0) {
			UnitApplication genericUnit = createApplicationFor(possibleUnits
					.get(0));
			if (!genericUnit.execute()) {
				possibleUnits.remove(0);
			} else {
				if (genericUnit.appliedRules.size() > 0) {
					appliedRules.addAll(genericUnit.appliedRules);
					possibleUnits = new ArrayList<TransformationUnit>(
							priorityUnit.getSubUnits());
				}
			}
		}

		return true;
	}

	private boolean executeCountedUnit() {
		CountedUnit countedUnit = (CountedUnit) transformationUnit;
		for (int i = 0; i < countedUnit.getCount(); i++) {
			UnitApplication genericUnit = createApplicationFor(countedUnit
					.getSubUnit());
			if (genericUnit.execute()) {
				appliedRules.addAll(genericUnit.appliedRules);
			} else {
				undo();
				return false;
			}
		}

		return true;
	}

	/**
	 * @return the transformationUnit
	 */
	public TransformationUnit getTransformationUnit() {
		return transformationUnit;
	}
}
