package org.eclipse.emf.henshin.interpreter.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.IParameterValues;
import org.eclipse.emf.henshin.interpreter.util.HenshinRegistry;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;

public class UnitParameterValues implements IParameterValues {
	
	@Override
	public Map getParameterValues() {
		Map<String, TransformationUnit> trafoUnits = new HashMap<String, TransformationUnit>();
		
		for (TransformationSystem trafoSystem : HenshinRegistry.instance.getTransformationSystems()) {
			for (TransformationUnit unit : trafoSystem.getTransformationUnits()) {
				trafoUnits.put(trafoSystem.getName() + "." + unit.getName(), unit);
			}
		}
		
		return trafoUnits;
	}
	
}
