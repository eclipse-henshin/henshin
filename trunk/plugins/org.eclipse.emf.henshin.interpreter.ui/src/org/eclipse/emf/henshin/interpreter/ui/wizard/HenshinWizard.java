/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.ui.wizard;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.interpreter.ui.HenshinInterpreterUIPlugin;
import org.eclipse.emf.henshin.interpreter.ui.util.ParameterConfiguration;
import org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.ModelSelector;
import org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.ModelSelector.ModelSelectorListener;
import org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.ParameterEditTable;
import org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.ParameterEditTable.ParameterChangeListener;
import org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.UnitSelector;
import org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.UnitSelector.UnitSelectionListener;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

/**
 * @author Gregor Bonifer, Stefan Jurack, Christian Krause
 */
public class HenshinWizard extends Wizard implements UnitSelectionListener, ModelSelectorListener,
		ParameterChangeListener {
	
	final String HELP_CONTEXT_ID = "org.eclipse.emf.henshin.interpreter.ui.wizardHelp";
	
	protected static int CONTROL_OFFSET = 5;
	
	protected UnitSelector unitSelector;
	
	protected ModelSelector modelSelector;
	
	protected ParameterEditTable parameterEditor;
	
	protected Unit initialUnit;
	
	protected Module module;
	
	protected List<Unit> availableUnits;
	
	protected List<CompletionListener> completionListeners = new ArrayList<HenshinWizard.CompletionListener>();
	
	protected boolean uriFieldDirty = false;
	
	protected Henshination cfg;
	
	protected Button saveOnCancel;
	
	protected WizardPage page;
	
	protected IPreferenceStore store = HenshinInterpreterUIPlugin.getPlugin().getPreferenceStore();
	
	protected boolean unitSelectable = true;
	
	/**
	 * Constructor.
	 * @param unit Unit to be applied.
	 */
	public HenshinWizard(Unit unit) {
		this();
		unitSelectable = false;
		this.initialUnit = unit;
		this.module = unit.getModule();
	}
	
	/**
	 * Constructor.
	 * @param module Module to be used.
	 */
	public HenshinWizard(Module module) {
		this();
		unitSelectable = true;
		this.module = module;
	}
	
	/*
	 * Private constructor.
	 */
	private HenshinWizard() {
		setWindowTitle(HenshinInterpreterUIPlugin.LL("_UI_Wizard"));
		setDefaultPageImageDescriptor(
				ImageDescriptor.createFromURL(
				(URL) HenshinInterpreterUIPlugin.INSTANCE.getImage("Henshin_small.png")));
	}
	
	/**
	 * Get the label to be used for a unit.
	 * @param unit Unit.
	 * @return The label for the unit.
	 */
	protected String getUnitLabel(Unit unit) {
		return unit.toString(); //unit.getName() + "[" + unit.eClass().getName() + "]";
	}
	
	protected void initData() {
		
		/*
		 * We do now reload the unit/trafosystem in a separate ResourceSet in
		 * order to work with them without corrupting the original resource set,
		 * which might be used by an editor.
		 */

		HenshinResourceSet resourceSet = new HenshinResourceSet();
		URI moduleUri = module.eResource().getURI();
		module = resourceSet.getModule(moduleUri, false);
		
		if (initialUnit != null) {
			int index = initialUnit.getModule().getUnits().indexOf(initialUnit);
			initialUnit = module.getUnits().get(index);
		}
		
		availableUnits = new ArrayList<Unit>();
		availableUnits.addAll(module.getUnits());
		
		ArrayList<String> selectableUnitLabels = new ArrayList<String>();
		ArrayList<String> outerUnitLabels = new ArrayList<String>();
		
		int initIdx = -1;
		int idx = 0;
		Unit selectedUnit = initialUnit;
		for (Unit unit : availableUnits) {
			String unitLabel = getUnitLabel(unit);
			selectableUnitLabels.add(unitLabel);
			Boolean isOuterUnit = true;
			for(Unit outerUnit : availableUnits) {
				if(outerUnit.getSubUnits(true).contains(unit)) {
					isOuterUnit = false;
					break;
				}
			}
			if(isOuterUnit) {
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
			selectedUnit = availableUnits.get(0);
		}
		unitSelector.setSelectableUnits(selectableUnitLabels.toArray(new String[0]), outerUnitLabels.toArray(new String[0]));
		unitSelector.setSelection(initIdx);
		
		// Enable selector if no unit was given in the constructor:
		unitSelector.setEnabled(initialUnit==null);
		
		List<String> lastUsedModels = getModelPreferences();
		
		cfg = new Henshination();
		if (selectedUnit != null) {
			cfg.setUnit(selectedUnit, getParameterPreferences(selectedUnit));
		}
		
		if (lastUsedModels.get(0).length() > 0) {
			cfg.setModelUri(URI.createURI(lastUsedModels.get(0)));
		}
		
		modelSelector.setLastUsedModels(lastUsedModels.toArray(new String[0]));
		
		if (selectedUnit != null)
			parameterEditor.setParameters(cfg.getParameterConfigurations());
		
		unitSelector.addUnitSelectionListener(HenshinWizard.this);
		modelSelector.addModelSelectorListener(HenshinWizard.this);
		parameterEditor.addParameterChangeListener(HenshinWizard.this);
	}
	
	protected List<ParameterConfiguration> getParameterPreferences(Unit unit) {
		List<ParameterConfiguration> result = new ArrayList<ParameterConfiguration>();
		for (Parameter param : unit.getParameters())
			result.add(ParameterConfiguration.loadConfiguration(store, param.getName()));
		return result;
	}
	
	protected List<String> getModelPreferences() {
		List<String> lastUsedModels = new ArrayList<String>();
		for (int i = 0; i < 5; i++)
			lastUsedModels.add(store.getString("model_" + i));
		return lastUsedModels;
	}
	
	@Override
	public void addPages() {
		addPage(page = new WizardPage(HenshinInterpreterUIPlugin.LL("_UI_PseudoPage")) {
			{
				setDescription(HenshinInterpreterUIPlugin.LL("_UI_Wizard_DefaultDescription"));
			}
			
			public void performHelp() {
				PlatformUI.getWorkbench().getHelpSystem().displayHelp(HELP_CONTEXT_ID);				
			}
			
			@Override
			public void createControl(Composite parent) {
				Composite container = new Composite(parent, SWT.NONE);
				{
					container.setLayout(new FormLayout());
					GridData data = new GridData();
					data.verticalAlignment = GridData.FILL;
					data.grabExcessVerticalSpace = true;
					data.horizontalAlignment = GridData.FILL;
					data.grabExcessHorizontalSpace = true;
					container.setLayoutData(data);
				}
				
				unitSelector = new UnitSelector(container);
				{
					FormData data = new FormData();
					data.top = new FormAttachment(0, CONTROL_OFFSET);
					data.left = new FormAttachment(0, CONTROL_OFFSET);
					data.right = new FormAttachment(100, -CONTROL_OFFSET);
					unitSelector.getControl().setLayoutData(data);
				}
				
				IResource selected = null;
				if (module!=null) {
					String path = module.eResource().getURI().toPlatformString(true);
					selected = ResourcesPlugin.getWorkspace().getRoot().findMember(path);
				}
				
				modelSelector = new ModelSelector(container,selected);
				{
					FormData data = new FormData();
					data.top = new FormAttachment(unitSelector.getControl(), CONTROL_OFFSET);
					data.left = new FormAttachment(0, CONTROL_OFFSET);
					data.right = new FormAttachment(100, -CONTROL_OFFSET);
					modelSelector.getControl().setLayoutData(data);
				}
				
				parameterEditor = new ParameterEditTable(container);
				{
					FormData data = new FormData();
					data.top = new FormAttachment(modelSelector.getControl(), CONTROL_OFFSET);
					data.left = new FormAttachment(0, CONTROL_OFFSET);
					data.right = new FormAttachment(100, -CONTROL_OFFSET);
					data.bottom = new FormAttachment(100, -CONTROL_OFFSET);
					parameterEditor.getControl().setLayoutData(data);
				}
				
				setControl(container);
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#createPageControls(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPageControls(Composite pageContainer) {
		super.createPageControls(pageContainer);
		initData();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		
		if (uriFieldDirty) {
			page.setMessage(HenshinInterpreterUIPlugin.LL("_UI_CheckResourceRequired"), WizardPage.WARNING);
			return false;
		} else {
			page.setMessage(null);
		}
		
		if (cfg.getModelUri() == null || cfg.getUnit() == null) {
			return false;
		}
		
		List<String> errors = new ArrayList<String>();
		
		try {
			if (cfg.getModel() == null)
				return false;
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
		
		if (!cfg.getUnit().isActivated())
			errors.add(HenshinInterpreterUIPlugin.LL("_UI_Status_TransforamtionUnitNotActivated"));
		
		if (errors.size() == 0) {
			page.setErrorMessage(null);
			if (!cfg.isModelAffectedByTransformation())
				page.setMessage(
						HenshinInterpreterUIPlugin.LL("_UI_Status_TransformationDoesNotAffectModel"),
						WizardPage.WARNING);
			else
				page.setMessage(null);
			return true;
		} else {
			StringBuffer err = new StringBuffer();
			err.append(errors.size() > 1 ? HenshinInterpreterUIPlugin.LL("_UI_Status_MultipleErrors")
					: HenshinInterpreterUIPlugin.LL("_UI_Status_SingleError"));
			for (String errString : errors)
				err.append("\n - " + errString);
			page.setErrorMessage(err.toString());
			return false;
		}
	}
	
	public void performPreview() {
		try {
			cfg.createPreview().showDialog(getShell());
		} catch (HenshinationException e) {
			ErrorDialog.openError(this.getShell(), HenshinInterpreterUIPlugin
					.LL("_UI_HenshinationException_Title"), HenshinInterpreterUIPlugin
					.LL("_UI_HenshinationException_Msg"), new Status(Status.ERROR,
					HenshinInterpreterUIPlugin.PLUGIN_ID, e.getMessage()));
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		try {
			OperationHistoryFactory.getOperationHistory().execute(cfg.getUndoableOperation(), null,
					null);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		saveSettings();
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performCancel()
	 */
	@Override
	public boolean performCancel() {
		if (store.getBoolean("saveOnCancel"))
			saveSettings();
		return true;
	}
	
	protected void saveSettings() {
		if (cfg.getModelUri() != null) {
			String newModel = cfg.getModelUri().toString();
			List<String> models = getModelPreferences();
			models.remove(newModel);
			models.add(0, newModel);
			for (int i = 0; i < 5; i++)
				store.setValue("model_" + i, models.get(i));
		}
		
		for (ParameterConfiguration paramCfg : cfg.getParameterConfigurations())
			paramCfg.persist(store);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.UnitSelector.UnitSelectionListener#unitSelected(int)
	 */
	@Override
	public boolean unitSelected(int idx) {
		
		Unit unit = this.availableUnits.get(idx);
		
		cfg.setUnit(unit, getParameterPreferences(unit));
		
		parameterEditor.setParameters(cfg.getParameterConfigurations());
		
		fireCompletionChange();
		
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.ModelSelector.ModelSelectorListener#modelURIChanged(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public boolean modelURIChanged(URI modelUri) {
		cfg.setModelUri(modelUri);
		this.uriFieldDirty = false;
		fireCompletionChange();
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.ParameterEditTable.ParameterChangeListener#parameterChanged(org.eclipse.emf.henshin.interpreter.ui.wizard.ParameterConfiguration)
	 */
	@Override
	public void parameterChanged(ParameterConfiguration paramCfg) {
		// cfg.parameterValues.put(parameter.getName(), value);
		fireCompletionChange();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.ModelSelector.ModelSelectorListener#uriFieldDirty()
	 */
	@Override
	public void uriFieldDirty() {
		this.uriFieldDirty = true;
		fireCompletionChange();
	}
	
	protected void fireCompletionChange() {
		for (CompletionListener l : completionListeners)
			l.completionChanged();
	}
	
	public void addCompletionListener(CompletionListener listener) {
		completionListeners.add(listener);
	}
	
	public static interface CompletionListener {
		public void completionChanged();
	}
	
	// @Override
	// public boolean isHelpAvailable() {
	// return true;
	// }
	
}
