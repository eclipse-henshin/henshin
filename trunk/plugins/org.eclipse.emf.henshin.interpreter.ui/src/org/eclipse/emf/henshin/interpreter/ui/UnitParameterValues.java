/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.IParameterValues;
import org.eclipse.emf.henshin.model.HenshinRegistry;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;

public class UnitParameterValues implements IParameterValues {
	
	@Override
	public Map<String, TransformationUnit> getParameterValues() {
		Map<String, TransformationUnit> trafoUnits = new HashMap<String, TransformationUnit>();
		
		for (TransformationSystem trafoSystem : HenshinRegistry.INSTANCE.getTransformationSystems()) {
			for (TransformationUnit unit : trafoSystem.getTransformationUnits()) {
				trafoUnits.put(trafoSystem.getName() + "." + unit.getName(), unit);
			}
		}
		
		return trafoUnits;
	}
	
}
