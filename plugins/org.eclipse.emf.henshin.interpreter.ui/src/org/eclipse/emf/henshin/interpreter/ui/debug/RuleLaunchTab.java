package org.eclipse.emf.henshin.interpreter.ui.debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.interpreter.ui.util.ParameterConfig;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.swt.widgets.Composite;

public class RuleLaunchTab extends AbstractLaunchConfigurationTab {

	LaunchRuleControl ruleControl;
	
	@Override
	public void createControl(Composite parent) {		
		ruleControl = new LaunchRuleControl();
		setControl(ruleControl.createControl(parent, this));
	}
	
	@Override
	protected void updateLaunchConfigurationDialog() {
		super.updateLaunchConfigurationDialog();
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			String modulePath = configuration.getAttribute(IHenshinConfigConstants.MODULE_PATH, "");
			String inputUri = configuration.getAttribute(IHenshinConfigConstants.INPUT_URI, "");
			String outputUri = configuration.getAttribute(IHenshinConfigConstants.OUTPUT_URI, "");
			String unitName = configuration.getAttribute(IHenshinConfigConstants.UNIT_NAME, "");
			int unitIndex = configuration.getAttribute(IHenshinConfigConstants.UNIT_INDEX, -1);
			boolean openCompare = configuration.getAttribute(IHenshinConfigConstants.OPEN_COMPARE, true);
			
			Map<String, String> paramTypes = configuration.getAttribute(
					IHenshinConfigConstants.PARAMETER_TYPES, new HashMap<String, String>());
			Map<String, String> paramValues = configuration.getAttribute(
					IHenshinConfigConstants.PARAMETER_VALUES, new HashMap<String, String>());
			List<String> unsetParams = configuration.getAttribute(
					IHenshinConfigConstants.UNSET_PARAMETERS, new ArrayList<String>());
			
				ruleControl.moduleSelector.setModelURI(modulePath);
				ruleControl.inputSelector.setModelURI(inputUri);				
				ruleControl.outputSelector.setModelURI(outputUri);	
				ruleControl.unitSelector.setSelection(unitIndex);
				ruleControl.openCompare.setSelection(openCompare);
				
			if (!(modulePath.isEmpty() || unitName.isEmpty() || paramTypes.isEmpty() || paramValues.isEmpty())) {
				// first get the "default" ParameterConfigs of the unit (name, kind, default type)
				HenshinResourceSet resourceSet = new HenshinResourceSet();
				URI uri = URI.createURI(modulePath);
				Module module = resourceSet.getModule(uri, false);
				Unit unit = module.getUnit(unitName);
				List<ParameterConfig> paramConfigs = ParamUtil.getParameterPreferences(unit);

				ruleControl.transformOperation.setUnit(unit, paramConfigs);
				
				// fill the parameterConfigs with the types and values
				ParamUtil.fillParamConfigs(ruleControl.transformOperation.getParameterConfigurations(), paramTypes, paramValues, unsetParams);
				
				// show the parameters in the editor
				ruleControl.parameterEditor.setParameters(ruleControl.transformOperation.getParameterConfigurations());				
			}
			
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(IHenshinConfigConstants.MODULE_PATH,
				ruleControl.moduleSelector.getModelURI());
		configuration.setAttribute(IHenshinConfigConstants.INPUT_URI,
				ruleControl.inputSelector.getModelURI());
		configuration.setAttribute(IHenshinConfigConstants.OUTPUT_URI,
				ruleControl.outputSelector.getModelURI());
		if (ruleControl.transformOperation.getUnit() != null) {
			configuration.setAttribute(IHenshinConfigConstants.UNIT_NAME,
					ruleControl.transformOperation.getUnit().getName());
			configuration.setAttribute(IHenshinConfigConstants.UNIT_INDEX,
					ruleControl.unitSelector.getUnitSelector().getSelectionIndex());
		}
		configuration.setAttribute(IHenshinConfigConstants.OPEN_COMPARE, 
				ruleControl.openCompare.getSelection());
		
		// when creating a new configuration
		if (ruleControl.transformOperation.getParameterConfigurations() == null) return;
		
		Map<String, String> paramTypes = new HashMap<String, String>();
		Map<String, String> paramValues = new HashMap<String, String>();
		List<String> unsetParams = new ArrayList<String>();
		
		
		for (ParameterConfig paramCfg : ruleControl.transformOperation.getParameterConfigurations()) {
			// type and value maps have the parameter names as keys
			paramTypes.put(paramCfg.getName(), String.valueOf(paramCfg.getType()));
			paramValues.put(paramCfg.getName(), String.valueOf(paramCfg.getValue()));
			if (paramCfg.isUnset()) {
				unsetParams.add(paramCfg.getName());				
			}
		}
		
		configuration.setAttribute(IHenshinConfigConstants.PARAMETER_TYPES, paramTypes);
		configuration.setAttribute(IHenshinConfigConstants.PARAMETER_VALUES, paramValues);
		configuration.setAttribute(IHenshinConfigConstants.UNSET_PARAMETERS, unsetParams);
	}
	
	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		setErrorMessage(null);
		String modulePath = ruleControl.moduleSelector.getModelURI();

		if (modulePath.isEmpty()) {
			setErrorMessage("You must choose a module");
			return false;
		}
		if (ruleControl.unitSelector.getUnitSelector().getSelectionIndex() == -1
				|| ruleControl.transformOperation.getUnit() == null) {
			setErrorMessage("You must choose a unit");
			return false;
		}
		// check if the unit is a rule
		if (!(ruleControl.transformOperation.getUnit() instanceof Rule)) {
			setErrorMessage("Selected unit must be a rule");
		}
		
		if (ruleControl.inputSelector.getModelURI().isEmpty()) {
			setErrorMessage("You must choose an input URI");
			return false;
		}
		if (ruleControl.outputSelector.getModelURI().isEmpty()) {
			setErrorMessage("You must choose an output URI");
			return false;
		}
		return true;
	}

	@Override
	public String getName() {
		return "Henshin rule launch tab";
	}

}
