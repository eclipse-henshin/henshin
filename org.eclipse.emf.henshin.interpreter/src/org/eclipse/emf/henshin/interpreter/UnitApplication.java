package org.eclipse.emf.henshin.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.interfaces.InterpreterEngine;
import org.eclipse.emf.henshin.interpreter.util.RuleMatch;
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
import org.eclipse.emf.henshin.model.SingleUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.Variable;

public class UnitApplication {
	InterpreterEngine engine;
	TransformationUnit transformationUnit;

	Map<String, Object> portValues;

	public UnitApplication(InterpreterEngine engine,
			TransformationUnit transformationUnit) {
		this.engine = engine;
		this.transformationUnit = transformationUnit;
		this.portValues = new HashMap<String, Object>();
	}

	public UnitApplication(InterpreterEngine engine,
			TransformationUnit transformationUnit,
			Map<String, Object> portValues) {
		this.engine = engine;
		this.transformationUnit = transformationUnit;
		this.portValues = portValues;
	}

	public boolean execute() {
		boolean success = false;

		switch (transformationUnit.eClass().getClassifierID()) {
		case HenshinPackage.SINGLE_UNIT:
			executeSingleUnit();
			break;
		case HenshinPackage.AMALGAMATED_UNIT:
			executeAmalgamatedUnit();
			break;
		case HenshinPackage.INDEPENDENT_UNIT:
			executeIndependentUnit();
			break;
		case HenshinPackage.SEQUENTIAL_UNIT:
			executeSequentialUnit();
			break;
		case HenshinPackage.CONDITIONAL_UNIT:
			executeConditionalUnit();
			break;
		case HenshinPackage.PRIORITY_UNIT:
			executePriorityUnit();
			break;
		case HenshinPackage.COUNTED_UNIT:
			executeCountedUnit();
			break;
		}

		return success;
	}

	public void undo() {
		//TODO(enrico): Implement undo()
	}

	private UnitApplication createApplicationFor(TransformationUnit unit) {
		return new UnitApplication(engine, unit, portValues);
	}

	private void updatePortValues(RuleMatch comatch) {
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

	private Map<String, Object> createAssignments() {
		Map<String, Object> assignments = new HashMap<String, Object>();
		for (Port port : transformationUnit.getPorts()) {
			if (port.getDirection() == PortKind.INPUT
					|| port.getDirection() == PortKind.INPUT_OUTPUT) {

				if (port instanceof PortParameter) {
					Variable var = ((PortParameter) port).getVariable();
					assignments.put(var.getName(), portValues.get(port
							.getName()));
				}
			}
		}
		return assignments;
	}

	private Map<Node, EObject> createPrematch() {
		Map<Node, EObject> prematch = new HashMap<Node, EObject>();
		for (Port port : transformationUnit.getPorts()) {
			if (port.getDirection() == PortKind.INPUT
					|| port.getDirection() == PortKind.INPUT_OUTPUT) {

				if (port instanceof PortObject) {
					Node node = ((PortObject) port).getNode();
					prematch
							.put(node, (EObject) portValues.get(port.getName()));
				}
			}
		}
		return prematch;
	}

	private boolean executeIndependentUnit() {
		boolean success = false;

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
				possibleUnits = new ArrayList<TransformationUnit>(
						independentUnit.getSubUnits());
				success = true;
			}
		}

		return success;
	}

	private boolean executeSingleUnit() {
		boolean success = false;

		SingleUnit singleUnit = (SingleUnit) transformationUnit;
		Rule rule = singleUnit.getRule();
		RuleApplication ruleApplication = new RuleApplication(engine, rule);
		RuleMatch match = new RuleMatch(rule, createAssignments(),
				createPrematch());
		ruleApplication.setMatch(match);
		success = ruleApplication.apply();
		if (success) {
			updatePortValues(ruleApplication.getComatch());
			// if (sequence != null)
			// sequence.addRuleApplication(ruleApplication);
		}

		return success;
	}

	private boolean executeAmalgamatedUnit() {
		boolean success = false;

		AmalgamatedUnit amalUnit = (AmalgamatedUnit) transformationUnit;
		success = engine.executeAmalgamatedUnit(amalUnit);

		return success;
	}

	private boolean executeSequentialUnit() {
		boolean success = false;

		SequentialUnit sequentialUnit = (SequentialUnit) transformationUnit;
		success = true;
		for (TransformationUnit subUnit : sequentialUnit.getSubUnits()) {
			UnitApplication genericUnit = createApplicationFor(subUnit);
			success = success && genericUnit.execute();
			if (!success) {
				break;
			}
		}

		return success;
	}

	private boolean executeConditionalUnit() {
		boolean success = false;

		ConditionalUnit conditionalUnit = (ConditionalUnit) transformationUnit;
		TransformationUnit ifUnit = conditionalUnit.getIf();
		UnitApplication genericIfUnit = createApplicationFor(ifUnit);
		if (genericIfUnit.execute()) {
			TransformationUnit thenUnit = conditionalUnit.getThen();
			UnitApplication genericThenUnit = createApplicationFor(thenUnit);
			success = genericThenUnit.execute();
		} else {
			if (conditionalUnit.getElse() != null) {
				TransformationUnit elseUnit = conditionalUnit.getElse();
				UnitApplication genericElseUnit = createApplicationFor(elseUnit);
				success = genericElseUnit.execute();
			}
		}

		return success;
	}

	private boolean executePriorityUnit() {
		boolean success = false;

		PriorityUnit priorityUnit = (PriorityUnit) transformationUnit;
		List<TransformationUnit> possibleUnits = new ArrayList<TransformationUnit>(
				priorityUnit.getSubUnits());

		while (possibleUnits.size() > 0) {
			UnitApplication genericUnit = createApplicationFor(possibleUnits
					.get(0));
			if (!genericUnit.execute()) {
				possibleUnits.remove(0);
			} else {
				possibleUnits = new ArrayList<TransformationUnit>(priorityUnit
						.getSubUnits());
				success = true;
			}
		}

		return success;
	}

	private boolean executeCountedUnit() {
		boolean success = false;

		CountedUnit countedUnit = (CountedUnit) transformationUnit;
		for (int i = 0; i < countedUnit.getCount(); i++) {
			UnitApplication genericUnit = createApplicationFor(countedUnit
					.getSubUnit());
			success = genericUnit.execute();
			if (!success) {
				break;
			}
		}

		return success;
	}

}
