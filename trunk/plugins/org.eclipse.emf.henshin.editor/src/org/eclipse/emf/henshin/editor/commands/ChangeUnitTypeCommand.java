/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.editor.commands;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.CountedUnit;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * Allows {@link TransformationUnit}s to be replaced by other TransformationUnits
 * (Currently implemented: Switch between Sequential, Independent, Priority Units)
 * @author Felix Rieger
 *
 */
public class ChangeUnitTypeCommand extends AbstractCommand {

	protected TransformationUnit unit;		// old unit
	protected TransformationUnit newUnit;	// new unit
	protected int oldUnitType;
	protected int newUnitType;
	protected Collection<?> affectedObjects;
	protected TransformationSystem ts;
	
	
	public ChangeUnitTypeCommand(TransformationUnit unit, int newUnitType) {
		this.unit = unit;
		this.newUnitType = newUnitType;
		if (unit != null) {
			ts = (TransformationSystem) unit.eContainer();
		}
	}
	
	
	@Override
	public void execute() {
		System.out.println("exec");
		// save the old unit's type for undo
		if (unit instanceof IndependentUnit) {
			oldUnitType = 1;
		} else if (unit instanceof SequentialUnit) {
			oldUnitType = 2;
		} else if (unit instanceof PriorityUnit) {
			oldUnitType = 3;
		}
		redo();
		
	}
	
	private void replaceUnit() {
		// get the transformation system that contains the unit
		TransformationSystem transformationSystem = ts;
		int unitIndex = transformationSystem.getTransformationUnits().indexOf(unit);	// get the unit's index so it will appear at the same place as the old unit in the tree editor
		if (unitIndex == -1) {
			unitIndex = transformationSystem.getTransformationUnits().indexOf(newUnit);	// if that fails because the unit doesn't exist in the transformation system, see if an old unit's index can be found. This should not happen.
			System.err.println("unit index -1:" + unit + "\n trying [result: " + unitIndex + "] " + newUnit);
		}
		newUnit = null;					// prepare a new unit
		EList<TransformationUnit> subUnitList = null;		// and its subunits

		// independent, sequential and priority units can be switched
		switch (newUnitType) {
		case 1:
			newUnit = HenshinFactory.eINSTANCE.createIndependentUnit();
			subUnitList = ((IndependentUnit) newUnit).getSubUnits();
			break;
		case 2:
			newUnit = HenshinFactory.eINSTANCE.createSequentialUnit();
			subUnitList = ((SequentialUnit) newUnit).getSubUnits();
			break;
		case 3:
			newUnit = HenshinFactory.eINSTANCE.createPriorityUnit();
			subUnitList = ((PriorityUnit) newUnit).getSubUnits();
			break;
		default:
			return;
		}
		
		// copy the old unit's attributes, subunits, parameters and parameter mapping
		subUnitList.addAll(unit.getSubUnits(false));
		newUnit.setName(unit.getName());
		newUnit.setActivated(unit.isActivated());
		newUnit.setDescription(unit.getDescription());
		newUnit.getParameters().addAll(unit.getParameters());
		newUnit.getParameterMappings().addAll(unit.getParameterMappings());
		// remove the old unit from the transformation system, add the new unit 
		if (unitIndex != -1) {
			transformationSystem.getTransformationUnits().remove(unitIndex);
			transformationSystem.getTransformationUnits().add(unitIndex, newUnit);
		} else {
			System.err.println("COULDN'T FIND UNITS IN TRANSFORMATION SYSTEM, ADDING A NEW ONE");
			System.err.println(unit);
			System.err.println(newUnit);
			transformationSystem.getTransformationUnits().add(newUnit);
		}
		
		// search for the old unit in the transformation system
		for (TransformationUnit tu : transformationSystem.getTransformationUnits()) {
			if (tu.getSubUnits(true).contains(unit)) {
				changeParameterMappingsRec(tu, unit, newUnit);	// change parameter mappings to the new unit
				replaceUnitRec(tu, unit, newUnit);	// replace references to the old unit
			}
		}
		
	}
	
	/**
	 * Recursively change parameter mappings
	 * @param parentUnit	the TransformationUnit where the recursion should start
	 * @param oldUnit	the unit to be replaced
	 * @param newUnit	the new unit
	 */
	private void changeParameterMappingsRec(TransformationUnit parentUnit, TransformationUnit oldUnit, TransformationUnit newUnit) {
		for (TransformationUnit tu : parentUnit.getSubUnits(false)) {
			if (tu.equals(oldUnit)) {
				changeParameterMappings(parentUnit, tu, newUnit);
			}
			if (tu.getSubUnits(false).size() > 0) {
				changeParameterMappingsRec(tu, oldUnit, newUnit);
			}
		}

	}
	
	/**
	 * Recursively replace Units
	 * @param parentUnit	The Unit where the recursion should start
	 * @param oldUnit	The Unit to be replaced
	 * @param newUnit	The new Unit
	 */
	private void replaceUnitRec(TransformationUnit parentUnit, TransformationUnit oldUnit, TransformationUnit newUnit) {
		EList<TransformationUnit> subUnitList = null;

		if (parentUnit instanceof SequentialUnit) {
			subUnitList = ((SequentialUnit) parentUnit).getSubUnits();
		} else if (parentUnit instanceof IndependentUnit) {
			subUnitList = ((IndependentUnit) parentUnit).getSubUnits();
		} else if (parentUnit instanceof PriorityUnit) {
			subUnitList = ((PriorityUnit) parentUnit).getSubUnits();
		} else if (parentUnit instanceof ConditionalUnit) {
			ConditionalUnit cu = (ConditionalUnit) parentUnit;
			if (cu.getIf().equals(oldUnit)) {
				cu.setIf(newUnit);
			}
			if (cu.getThen().equals(oldUnit)) {
				cu.setThen(newUnit);
			}
			if (cu.getElse().equals(oldUnit)) {
				cu.setElse(newUnit);
			}
			return;
		} else if (parentUnit instanceof CountedUnit) {
			if (((CountedUnit) parentUnit).getSubUnit().equals(oldUnit)) {
				((CountedUnit) parentUnit).setSubUnit(newUnit);
			}
			return;
		}
		
		while(subUnitList.contains(oldUnit)) {
			int i = subUnitList.indexOf(oldUnit);
			subUnitList.remove(i);
			subUnitList.add(i, newUnit);
		}
		
		for (TransformationUnit tu : subUnitList) {
			if ((!(tu.equals(newUnit))) && tu.getSubUnits(true).contains(oldUnit)) {
				replaceUnitRec(tu, oldUnit, newUnit);
			}
		}
	}
	
	/**
	 * Change parameter mappings.
	 * @param parentUnit
	 * @param oldUnit
	 * @param newUnit
	 */
	private void changeParameterMappings(TransformationUnit parentUnit, TransformationUnit oldUnit, TransformationUnit newUnit) {
		List<ParameterMapping> parameterMappings = parentUnit.getParameterMappings();
		for (ParameterMapping pm : parameterMappings) {
			if (pm.getSource().getUnit().equals(oldUnit)) {
				int oldParameterIndex = oldUnit.getParameters().indexOf(pm.getSource());
				Parameter newParameter = newUnit.getParameters().get(oldParameterIndex);
				pm.setSource(newParameter);
			}
			
			if (pm.getTarget().getUnit().equals(oldUnit)) {
				int oldParameterIndex = oldUnit.getParameters().indexOf(pm.getTarget());
				Parameter newParameter = newUnit.getParameters().get(oldParameterIndex);
				pm.setTarget(newParameter);
			}
		}
	}

	@Override
	public void redo() {	
		if (newUnit != null) {

			TransformationUnit tmpUnit = unit;
			unit = newUnit;
			newUnit = tmpUnit;
			
			int unitIndex = ts.getTransformationUnits().indexOf(unit);
			ts.getTransformationUnits().remove(unitIndex);
			ts.getTransformationUnits().add(unitIndex, newUnit);
			
			for (TransformationUnit tu : ts.getTransformationUnits()) {
				if (tu.getSubUnits(true).contains(unit)) {
					changeParameterMappingsRec(tu, unit, newUnit);	// change parameter mappings to the new unit
					replaceUnitRec(tu, unit, newUnit);	// replace references to the old unit
				}
			}
		} else {
			replaceUnit();
		}
	}

	@Override
	public void undo() {
		TransformationUnit tmpUnit = unit;
		unit = newUnit;
		newUnit = tmpUnit;
		
		int unitIndex = ts.getTransformationUnits().indexOf(unit);
		ts.getTransformationUnits().remove(unitIndex);
		ts.getTransformationUnits().add(unitIndex, newUnit);
		
		for (TransformationUnit tu : ts.getTransformationUnits()) {
			if (tu.getSubUnits(true).contains(unit)) {
				changeParameterMappingsRec(tu, unit, newUnit);	// change parameter mappings to the new unit
				replaceUnitRec(tu, unit, newUnit);	// replace references to the old unit
			}
		}
		
	}
	
	@Override
	protected boolean prepare() {
		if ((unit instanceof IndependentUnit) || (unit instanceof SequentialUnit) || (unit instanceof PriorityUnit)) {
			return true;
		} else {
			return false;
		}
	}

}
