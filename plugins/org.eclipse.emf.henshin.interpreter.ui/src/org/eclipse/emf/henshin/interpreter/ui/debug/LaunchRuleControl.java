package org.eclipse.emf.henshin.interpreter.ui.debug;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.interpreter.ui.util.ParameterConfig;
import org.eclipse.emf.henshin.interpreter.ui.util.TransformOperation;
import org.eclipse.emf.henshin.interpreter.ui.wizard.ModelSelector;
import org.eclipse.emf.henshin.interpreter.ui.wizard.ModelSelector.ModelSelectorListener;
import org.eclipse.emf.henshin.interpreter.ui.wizard.ParameterEditTable;
import org.eclipse.emf.henshin.interpreter.ui.wizard.ParameterEditTable.ParameterChangeListener;
import org.eclipse.emf.henshin.interpreter.ui.wizard.UnitSelector;
import org.eclipse.emf.henshin.interpreter.ui.wizard.UnitSelector.UnitSelectionListener;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.ParameterKind;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class LaunchRuleControl {

	protected static int CONTROL_OFFSET = 5;

	ModelSelector moduleSelector;

	UnitSelector unitSelector;

	ModelSelector inputSelector;

	ModelSelector outputSelector;

	ParameterEditTable parameterEditor;

	Button openCompare;

	protected Unit initialUnit;

	protected Module module;

	protected List<Unit> allUnits;

	protected List<Unit> outerUnits;

	protected TransformOperation transformOperation;

	public Composite createControl(Composite parent, final RuleLaunchTab parentTab) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, true));

		transformOperation = new TransformOperation();

		IResource selected = null;

		if (module == null) {
			// show file selector to select module
			moduleSelector = new ModelSelector(container, selected, false);
			moduleSelector.getControl().setLayoutData(
					new GridData(SWT.FILL, SWT.FILL, true, false));
			((Group) moduleSelector.getControl()).setText("Module (.henshin file)");
			moduleSelector.addModelSelectorListener(new ModelSelectorListener() {
				@Override
				public boolean modelURIChanged(String modulePath) {
					// Create a resource set and load the resource:
					HenshinResourceSet resourceSet = new HenshinResourceSet();
					try {
						// Load the module:
						URI uri = URI.createURI(modulePath);
						module = resourceSet.getModule(uri, false);

						// display the units in the unit selector
						updateUnitSelector();
						parentTab.updateLaunchConfigurationDialog();

						return true;
					} catch (RuntimeException  e) {
						// no valid module path entered
						clearModule();
						return false;
					}
				}
			});
		} else {
			String path = module.eResource().getURI().toPlatformString(true);
			selected = ResourcesPlugin.getWorkspace().getRoot()
					.findMember(path);
		}

		unitSelector = new UnitSelector(container);
		unitSelector.getControl().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, false));

		inputSelector = new ModelSelector(container, selected, false);
		inputSelector.getControl().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, false));

		outputSelector = new ModelSelector(container, selected, true);
		outputSelector.getControl().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, false));

		inputSelector.addModelSelectorListener(new ModelSelectorListener() {
			@Override
			public boolean modelURIChanged(String modelURI) {
				String output = deriveOutputURI(modelURI);
				if (output != null) {
					outputSelector.setModelURI(output);
				}
				parentTab.updateLaunchConfigurationDialog();
				return true;
			}
		});

		parameterEditor = new ParameterEditTable(container);
		parameterEditor.getControl().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true));
		parameterEditor.addParameterChangeListener(new ParameterChangeListener() {

			@Override
			public void parameterChanged(ParameterConfig paramCfg) {
				// update name, type, and "unset" setting of the parameter
				transformOperation.getParameterConfiguration(paramCfg.getName()).setValue(paramCfg.getValue());
				transformOperation.getParameterConfiguration(paramCfg.getName()).setType(paramCfg.getType());
				transformOperation.getParameterConfiguration(paramCfg.getName()).setUnset(paramCfg.isUnset());
				parentTab.updateLaunchConfigurationDialog();
			}
		});

		openCompare = new Button(container, SWT.CHECK);
		openCompare.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		openCompare.setText("Open Compare");
		openCompare.setSelection(true);
		openCompare.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
				parentTab.updateLaunchConfigurationDialog();
			}

			@Override
			public void mouseDown(MouseEvent e) {}

			@Override
			public void mouseDoubleClick(MouseEvent e) {}
		});

		inputSelector.getBrowseWorkspaceButton().setFocus();

		unitSelector.addUnitSelectionListener(new UnitSelectionListener() {

			@Override
			public boolean unitSelected(int idx, boolean showInnerUnits) {
				Unit unit = showInnerUnits ?
						LaunchRuleControl.this.allUnits.get(idx) :
							LaunchRuleControl.this.outerUnits.get(idx);
				transformOperation.setUnit(unit, ParamUtil.getParameterPreferences(unit));

				for (ParameterConfig parameterConfig : transformOperation.getParameterConfigurations()) {
					if (parameterConfig.getKind() == ParameterKind.IN || parameterConfig.getKind() == ParameterKind.INOUT) {
						parameterConfig.setUnset(false); // has to be set
					}
					if (parameterConfig.getKind() == ParameterKind.OUT || parameterConfig.getKind() == ParameterKind.VAR) {
						parameterConfig.setUnset(true); // must not be set
					}
				}

				parameterEditor.setParameters(transformOperation.getParameterConfigurations());



				parentTab.updateLaunchConfigurationDialog();

				return false;
			}
		});

		return container;
	}

	private void updateUnitSelector() {
		allUnits = new ArrayList<Unit>();
		allUnits.addAll(module.getUnits());
		outerUnits = new ArrayList<Unit>();

		List<String> selectableUnitLabels = new ArrayList<String>();
		List<String> outerUnitLabels = new ArrayList<String>();

		int initIdx = -1;
		int idx = 0;
		Unit selectedUnit = initialUnit;
		for (Unit unit : allUnits) {
			String unitLabel = unit.toString();
			selectableUnitLabels.add(unitLabel);
			boolean isOuterUnit = true;
			for (Unit outerUnit : allUnits) {
				if (outerUnit.getSubUnits(true).contains(unit)) {
					isOuterUnit = false;
					break;
				}
			}
			if (isOuterUnit) {
				outerUnits.add(unit);
				outerUnitLabels.add(unitLabel);
			}

			if (initialUnit != null) {
				if (initialUnit == unit) {
					initIdx = idx;
					selectedUnit = unit;
				}
			} else {
				if (initIdx < 0 && unit.getName() != null
						&& unit.getName().toLowerCase().equals("main")) {
					initIdx = idx;
					selectedUnit = unit;
				}
			}
			idx++;
		}
		if (initIdx < 0) {
			initIdx = 0;
			selectedUnit = allUnits.get(0);
		}

		if (selectedUnit != null) {
			transformOperation.setUnit(selectedUnit,
					ParamUtil.getParameterPreferences(selectedUnit));
		}

		if (selectedUnit != null) {
			parameterEditor.setParameters(transformOperation
					.getParameterConfigurations());
		}

		unitSelector.setSelectableUnits(
				selectableUnitLabels.toArray(new String[0]),
				outerUnitLabels.toArray(new String[0]));
		unitSelector.setSelection(initIdx);
	}

	/**
	 * clears selectors relevant to the current module (i.e. the unit selector and parameter editor)
	 */
	private void clearModule() {
		unitSelector.setSelectableUnits(new String[0], new String[0]);
		parameterEditor.setParameters(new ArrayList<ParameterConfig>(0));
	}

	private String deriveOutputURI(String inputUri) {
		try {
			URI uri = URI.createURI(inputUri);
			String fileExt = uri.fileExtension();
			uri = uri.trimFileExtension();
			uri = URI.createURI(uri.toString() + "_transformed")
					.appendFileExtension(fileExt);
			return uri.toString();
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

}
