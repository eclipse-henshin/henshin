/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University of Berlin - initial API and implementation
 *******************************************************************************/
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
import org.eclipse.emf.henshin.model.Port;
import org.eclipse.emf.henshin.model.PortKind;
import org.eclipse.emf.henshin.model.PortMapping;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;

public class UnitApplication {
	InterpreterEngine engine;
	TransformationUnit transformationUnit;

	Map<Port, Object> portValues;
	Map<Port, Object> oldPortValues;

	Stack<RuleApplication> appliedRules;

	public UnitApplication(InterpreterEngine engine,
			TransformationUnit transformationUnit) {
		this.engine = engine;
		this.transformationUnit = transformationUnit;
		this.portValues = new HashMap<Port, Object>();
		this.oldPortValues = new HashMap<Port, Object>(portValues);

		this.appliedRules = new Stack<RuleApplication>();
	}

	public UnitApplication(InterpreterEngine engine,
			TransformationUnit transformationUnit, Map<Port, Object> portValues) {
		this.engine = engine;
		this.transformationUnit = transformationUnit;
		this.portValues = portValues;
		this.oldPortValues = new HashMap<Port, Object>(portValues);

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
		Map<Port, Object> childPortValues = createChildPortValues(unit);
		return new UnitApplication(engine, unit, childPortValues);
	}

	private Map<Port, Object> createChildPortValues(TransformationUnit child) {
		Map<Port, Object> childPortValues = new HashMap<Port, Object>();
		for (PortMapping mapping : transformationUnit.getPortMappings()) {
			Port sourcePort = mapping.getSource();
			Port targetPort = mapping.getTarget();

			if (sourcePort.getDirection() == PortKind.INPUT
					|| sourcePort.getDirection() == PortKind.INPUT_OUTPUT)
				if (targetPort.getUnit() == child) {
					childPortValues.put(targetPort, portValues.get(sourcePort));
				}
		}
		return childPortValues;
	}

	private void updatePortValues(UnitApplication childUnit) {
		for (PortMapping mapping : transformationUnit.getPortMappings()) {
			Port sourcePort = mapping.getSource();
			Port targetPort = mapping.getTarget();

			if (targetPort.getDirection() == PortKind.OUTPUT
					|| targetPort.getDirection() == PortKind.INPUT_OUTPUT)
				if (sourcePort.getUnit() == childUnit) {
					portValues.put(targetPort, childUnit.portValues
							.get(sourcePort));
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
				updatePortValues(unitApplication);
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

		Match match = new Match(rule, ModelHelper.createAssignments(rule,
				portValues), ModelHelper.createPrematch(rule, portValues));

		RuleApplication ruleApplication = new RuleApplication(engine, rule);
		ruleApplication.setMatch(match);
		if (ruleApplication.apply()) {
			portValues = ModelHelper.generatePortValues(rule, ruleApplication
					.getComatch());
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
			portValues = ModelHelper.generatePortValues(amalUnit,
					amalgamatedRule.getComatch());
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
				updatePortValues(genericUnit);
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
			updatePortValues(genericIfUnit);
			appliedRules.addAll(genericIfUnit.appliedRules);

			TransformationUnit thenUnit = conditionalUnit.getThen();
			UnitApplication genericThenUnit = createApplicationFor(thenUnit);
			success = genericThenUnit.execute();
			if (success)
				updatePortValues(genericThenUnit);
			appliedRules.addAll(genericThenUnit.appliedRules);
		} else {
			if (conditionalUnit.getElse() != null) {
				TransformationUnit elseUnit = conditionalUnit.getElse();
				UnitApplication genericElseUnit = createApplicationFor(elseUnit);
				success = genericElseUnit.execute();
				if (success)
					updatePortValues(genericElseUnit);
				appliedRules.addAll(genericElseUnit.appliedRules);
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
				updatePortValues(genericUnit);
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
				updatePortValues(genericUnit);
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
