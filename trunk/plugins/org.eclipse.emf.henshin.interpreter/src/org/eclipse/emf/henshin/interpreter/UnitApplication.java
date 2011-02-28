/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin,
 * Philipps-University Marburg and others. All rights reserved. This program and
 * the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.interpreter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Stack;

import org.eclipse.emf.henshin.interpreter.interfaces.InterpreterEngine;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.CountedUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;

public class UnitApplication extends Observable {
	InterpreterEngine engine;
	TransformationUnit transformationUnit;
	
	Map<Parameter, Object> parameterValues;
	Map<Parameter, Object> oldParameterValues;
	
	Stack<RuleApplication> appliedRules;
	Stack<RuleApplication> undoneRules;
	
	Collection<Observer> observers;
	
	/**
	 * Constructor<br>
	 * 
	 * Arguments must NOT be <code>null</code>.
	 * 
	 * @param engine
	 * @param transformationUnit
	 */
	public UnitApplication(InterpreterEngine engine, TransformationUnit transformationUnit) {
		if (engine == null)
			throw new IllegalArgumentException("engine can not be null");
		
		if (transformationUnit == null)
			throw new IllegalArgumentException("transformationUnit can not be null");
		
		this.engine = engine;
		this.transformationUnit = transformationUnit;
		this.parameterValues = new HashMap<Parameter, Object>();
		this.oldParameterValues = new HashMap<Parameter, Object>(parameterValues);
		
		this.appliedRules = new Stack<RuleApplication>();
		this.undoneRules = new Stack<RuleApplication>();
	}
	
	/**
	 * Constructor<br>
	 * 
	 * Arguments must NOT be <code>null</code>.
	 * 
	 * @param engine
	 * @param transformationUnit
	 * @param parameterValues
	 */
	public UnitApplication(InterpreterEngine engine, TransformationUnit transformationUnit,
			Map<Parameter, Object> parameterValues) {
		this.engine = engine;
		this.transformationUnit = transformationUnit;
		this.parameterValues = parameterValues;
		this.oldParameterValues = new HashMap<Parameter, Object>(parameterValues);
		
		this.appliedRules = new Stack<RuleApplication>();
	}
	
	public boolean execute() {
		switch (transformationUnit.eClass().getClassifierID()) {
			case HenshinPackage.RULE:
				return executeRule();
			case HenshinPackage.AMALGAMATION_UNIT:
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
	
	/**
	 * Undoes the application of that unit and resets all parameter values to
	 * the state before the unit was executed.
	 */
	public void undo() {
		while (!appliedRules.isEmpty()) {
			RuleApplication ruleApplication = appliedRules.pop();
			ruleApplication.undo();
			undoneRules.push(ruleApplication);
		}
		
		parameterValues = oldParameterValues;
	}
	
	/**
	 * Redoes the application of that unit and resets all parameter values to
	 * the state before the unit was executed.
	 */
	public void redo() {
		while (!undoneRules.isEmpty()) {
			RuleApplication ruleApplication = undoneRules.pop();
			ruleApplication.redo();
			appliedRules.push(ruleApplication);
		}
	}
	
	private UnitApplication createApplicationFor(TransformationUnit unit) {
		Map<Parameter, Object> childPortValues = createChildParameterValues(unit);
		return new UnitApplication(engine, unit, childPortValues);
	}
	
	private Map<Parameter, Object> createChildParameterValues(TransformationUnit child) {
		Map<Parameter, Object> childParameterValues = new HashMap<Parameter, Object>();
		for (ParameterMapping mapping : transformationUnit.getParameterMappings()) {
			Parameter sourceParameter = mapping.getSource();
			Parameter targetParameter = mapping.getTarget();
			
			if (targetParameter.getUnit() == child) {
				childParameterValues.put(targetParameter, parameterValues.get(sourceParameter));
			}
		}
		return childParameterValues;
	}
	
	private void updateParameterValues(UnitApplication childUnit) {
		for (ParameterMapping mapping : transformationUnit.getParameterMappings()) {
			Parameter sourceParameter = mapping.getSource();
			Parameter targetParameter = mapping.getTarget();
			
			if (sourceParameter.getUnit() == childUnit.getTransformationUnit()) {
				for (Parameter p : childUnit.parameterValues.keySet()) {
					if (p.getName().equals(sourceParameter.getName())) {
						parameterValues.put(targetParameter, childUnit.parameterValues.get(p));
						break;
					}
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
			UnitApplication unitApplication = createApplicationFor(possibleUnits.get(index));
			if (!unitApplication.execute()) {
				possibleUnits.remove(index);
			} else {
				updateParameterValues(unitApplication);
				if (unitApplication.appliedRules.size() > 0) {
					appliedRules.addAll(unitApplication.appliedRules);
				}
				return true;
			}
		}
		
		return false;
	}
	
	private boolean executeRule() {
		Rule rule = (Rule) transformationUnit;
		
		Match match = new Match(rule, parameterValues, ModelHelper.createPrematch(rule,
				parameterValues));
		
		RuleApplication ruleApplication = new RuleApplication(engine, rule);
		ruleApplication.setMatch(match);
		if (ruleApplication.apply()) {
			System.out.println(rule.getName());
			parameterValues = ruleApplication.getComatch().getParameterValues();
			appliedRules.push(ruleApplication);
			return true;
		} else {
			return false;
		}
	}
	
	private boolean executeAmalgamatedUnit() {
		AmalgamationUnit amalUnit = (AmalgamationUnit) transformationUnit;
		RuleApplication amalgamationRule = engine.generateAmalgamationRule(amalUnit,
				parameterValues);
		
		if (amalgamationRule != null) {
			amalgamationRule.apply();
			parameterValues = amalgamationRule.getComatch().getParameterValues();
			appliedRules.push(amalgamationRule);
			return true;
		} else {
			return false;
		}
	}
	
	private boolean executeSequentialUnit() {
		SequentialUnit sequentialUnit = (SequentialUnit) transformationUnit;
		
		for (int i = 0; i < sequentialUnit.getSubUnits().size(); i++) {
			TransformationUnit subUnit = sequentialUnit.getSubUnits().get(i);
			UnitApplication genericUnit = createApplicationFor(subUnit);
			if (genericUnit.execute()) {
				updateParameterValues(genericUnit);
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
			updateParameterValues(genericIfUnit);
			appliedRules.addAll(genericIfUnit.appliedRules);
			
			TransformationUnit thenUnit = conditionalUnit.getThen();
			UnitApplication genericThenUnit = createApplicationFor(thenUnit);
			success = genericThenUnit.execute();
			if (success)
				updateParameterValues(genericThenUnit);
			appliedRules.addAll(genericThenUnit.appliedRules);
		} else {
			if (conditionalUnit.getElse() != null) {
				TransformationUnit elseUnit = conditionalUnit.getElse();
				UnitApplication genericElseUnit = createApplicationFor(elseUnit);
				success = genericElseUnit.execute();
				if (success)
					updateParameterValues(genericElseUnit);
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
			UnitApplication genericUnit = createApplicationFor(possibleUnits.get(0));
			if (!genericUnit.execute()) {
				possibleUnits.remove(0);
			} else {
				updateParameterValues(genericUnit);
				if (genericUnit.appliedRules.size() > 0) {
					appliedRules.addAll(genericUnit.appliedRules);
				}
				return true;
			}
		}
		
		return false;
	}
	
	private boolean executeCountedUnit() {
		CountedUnit countedUnit = (CountedUnit) transformationUnit;
		int count = countedUnit.getCount();
		
		for (int i = 0; i < count || count == -1; i++) {
			UnitApplication genericUnit = createApplicationFor(countedUnit.getSubUnit());
			
			if (genericUnit.execute()) {
				updateParameterValues(genericUnit);
				appliedRules.addAll(genericUnit.appliedRules);
			} else {
				if (count != -1) {
					undo();
					return false;
				} else
					break;
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
	
	/**
	 * Sets a value corresponding to a parameter with the given name. If a
	 * mapping between the related parameter and a value is already given, this
	 * mapping is replace. If no such mapping exists, a new one is created.
	 * 
	 * @param name
	 *            name of the Parameter
	 * @param value
	 *            (new) value of the Parameter
	 */
	public void setParameterValue(String name, Object value) {
		
		Parameter parameter = this.transformationUnit.getParameterByName(name);
		if (parameter != null)
			this.parameterValues.put(parameter, value);
	}// setParameterValue
	
	/**
	 * Returns the mapped value corresponding to a parameter with the given
	 * name. If no value is mapped, <code>null</code> is returned. Furthermore,
	 * if no such parameter is found, <code>null</code> is returned as well.
	 * 
	 * @param name
	 *            name of the Parameter
	 * @return
	 */
	public Object getParameterValue(String name) {
		
		if (this.parameterValues != null) {
			Parameter parameter = this.transformationUnit.getParameterByName(name);
			if (parameter != null)
				return this.parameterValues.get(parameter);
		}// if
		return null;
	}// getPortValue
	
	public Map<Parameter, Object> getParameterValues() {
		return parameterValues;
	}

	/**
	 * Sets the UnitApplication's values for Parameters 
	 * @param assignments Map between Parameter names and their values
	 */
	public void setParameterValues(Map<String, Object> assignments) {
		parameterValues.clear();
		for (String s : assignments.keySet()) {
			setParameterValue(s, assignments.get(s));
		}
	}
	
	/**
	 * Returns the applied RuleApplications
	 * @return the applied RuleApplications
	 */
	public Stack<RuleApplication> getAppliedRules() {
		return appliedRules;
	}
	
	/**
	 * Returns the {@link InterpreterEngine} of this {@link RuleApplication}.<br>
	 * <br>
	 * <strong>Remark</strong>: Note, that any modification on the engine may
	 * lead to unpredictable side effects.
	 * 
	 * @return the interpreterEngine
	 */
	public InterpreterEngine getInterpreterEngine() {
		return engine;
	}// getInterpreterEngine
	
}
