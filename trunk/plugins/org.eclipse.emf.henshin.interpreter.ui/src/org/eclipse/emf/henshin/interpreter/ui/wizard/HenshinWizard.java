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
package org.eclipse.emf.henshin.interpreter.ui.wizard;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.interpreter.ui.InterpreterUIPlugin;
import org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.ModelSelector;
import org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.ModelSelector.ModelSelectorListener;
import org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.ParameterEditTable;
import org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.ParameterEditTable.ParameterChangeListener;
import org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.UnitSelector;
import org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.UnitSelector.UnitSelectionListener;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
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

/** 
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack
 */
public class HenshinWizard extends Wizard implements UnitSelectionListener, ModelSelectorListener,
		ParameterChangeListener {
	
	protected static int CONTROL_OFFSET = 5;
	
	protected UnitSelector unitSelector;
	
	protected ModelSelector modelSelector;
	
	protected ParameterEditTable parameterEditor;
	
	protected TransformationUnit initialUnit;
	
	protected TransformationSystem transformationSystem;
	
	protected ArrayList<TransformationUnit> availableUnits;
	
	protected Collection<CompletionListener> completionListeners = new ArrayList<HenshinWizard.CompletionListener>();
	
	protected boolean uriFieldDirty = false;
	
	protected Henshination cfg;
	
	protected Button saveOnCancel;
	
	protected WizardPage page;
	
	protected IPreferenceStore store = InterpreterUIPlugin.getPlugin().getPreferenceStore();
	
	protected boolean unitSelectable = true;
	
	public HenshinWizard(TransformationUnit tUnit) {
		this();
		unitSelectable = false;
		this.initialUnit = tUnit;
		this.transformationSystem = (TransformationSystem) tUnit.eContainer();
	}
	
	public HenshinWizard(TransformationSystem tSystem) {
		this();
		unitSelectable = true;
		this.transformationSystem = tSystem;
	}
	
	private HenshinWizard() {
		setWindowTitle(InterpreterUIPlugin.LL("_UI_Wizard"));
		setDefaultPageImageDescriptor(ImageDescriptor
				.createFromURL((URL) InterpreterUIPlugin.INSTANCE.getImage("Henshin_small.png")));
	}
	
	protected String getUnitLabel(TransformationUnit unit) {
		return unit.getName() + "[" + unit.eClass().getName() + "]";
	}
	
	protected void initData() {
		/*
		 * We do now reload the unit/trafosystem in a separate ResourceSet in
		 * order to work with them without corrupting the original resource set,
		 * which might be used by an editor.
		 */

		ResourceSet rs = new ResourceSetImpl();
		URI trafoUri = this.transformationSystem.eResource().getURI();
		Resource r = rs.createResource(trafoUri);
		
		try {
			r.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.transformationSystem = (TransformationSystem) r.getContents().get(0);
		if (this.initialUnit != null) {
			String id = initialUnit.eResource().getURIFragment(initialUnit);
			List<TransformationUnit> l = new ArrayList<TransformationUnit>(
					this.transformationSystem.getRules());
			l.addAll(this.transformationSystem.getTransformationUnits());
			for (TransformationUnit unit : l) {
				if (r.getURIFragment(unit).equals(id)) {
					this.initialUnit = unit;
					break;
				}
			}
		}
		
		availableUnits = new ArrayList<TransformationUnit>();
		availableUnits.addAll(transformationSystem.getTransformationUnits());
		availableUnits.addAll(transformationSystem.getRules());
		
		ArrayList<String> unitLabels = new ArrayList<String>();
		int initIdx = -1;
		int idx = 0;
		TransformationUnit selectedUnit = initialUnit;
		for (TransformationUnit unit : availableUnits) {
			unitLabels.add(getUnitLabel(unit));
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
		unitSelector.setSelectableUnits(unitLabels.toArray(new String[0]));
		
		unitSelector.setSelection(initIdx);
		
		// enable selector if no unit was given to constructor
		//
		unitSelector.setEnabled(initialUnit == null);
		
		List<String> lastUsedModels = getModelPreferences();
		
		
		cfg = new Henshination();
		if (selectedUnit != null)
			cfg.setTransformationUnit(selectedUnit,getParameterPreferences(selectedUnit));
		
		if (lastUsedModels.get(0).length() > 0)
			cfg.setModelUri(URI.createURI(lastUsedModels.get(0)));
		
		
		
		modelSelector.setLastUsedModels(lastUsedModels.toArray(new String[0]));
		
		if (selectedUnit != null)
			parameterEditor.setParameters(cfg.getParameterConfigurations());
		
		unitSelector.addUnitSelectionListener(HenshinWizard.this);
		modelSelector.addModelSelectorListener(HenshinWizard.this);
		parameterEditor.addParameterChangeListener(HenshinWizard.this);
	}
	
	protected List<ParameterConfiguration> getParameterPreferences(TransformationUnit unit) {
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
		addPage(page = new WizardPage(InterpreterUIPlugin.LL("_UI_PseudoPage")) {
			{
				setDescription(InterpreterUIPlugin.LL("_UI_Wizard_DefaultDescription"));
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
				
				modelSelector = new ModelSelector(container);
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
	
	@Override
	public void createPageControls(Composite pageContainer) {
		super.createPageControls(pageContainer);
		initData();
	}
	
	@Override
	public boolean canFinish() {
		
		if (uriFieldDirty) {
			page.setMessage(InterpreterUIPlugin.LL("_UI_CheckResourceRequired"), WizardPage.WARNING);
			return false;
		} else {
			page.setMessage(null);
		}
		
		if (cfg.getModelUri() == null || cfg.getTransformationUnit() == null)
			return false;
		
		List<String> errors = new ArrayList<String>();
		
		try {
			if (cfg.getModel() == null)
				return false;
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
		
		if (!cfg.getTransformationUnit().isActivated())
			errors.add(InterpreterUIPlugin.LL("_UI_Status_TransforamtionUnitNotActivated"));
		
		if (errors.size() == 0) {
			page.setErrorMessage(null);
			if (!cfg.isModelAffectedByTransformation())
				page.setMessage(
						InterpreterUIPlugin.LL("_UI_Status_TransformationDoesNotAffectModel"),
						WizardPage.WARNING);
			else
				page.setMessage(null);
			return true;
		} else {
			StringBuffer err = new StringBuffer();
			err.append(errors.size() > 1 ? InterpreterUIPlugin.LL("_UI_Status_MultipleErrors")
					: InterpreterUIPlugin.LL("_UI_Status_SingleError"));
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
			ErrorDialog.openError(this.getShell(), InterpreterUIPlugin
					.LL("_UI_HenshinationException_Title"), InterpreterUIPlugin
					.LL("_UI_HenshinationException_Msg"), new Status(Status.ERROR,
					InterpreterUIPlugin.ID, e.getMessage()));
		}
	}
	
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
		
		for(ParameterConfiguration paramCfg:cfg.getParameterConfigurations())
			paramCfg.persist(store);
	}
	
	@Override
	public boolean unitSelected(int idx) {
		
		TransformationUnit unit = this.availableUnits.get(idx);
		
		cfg.setTransformationUnit(unit,getParameterPreferences(unit));
		
		parameterEditor.setParameters(cfg.getParameterConfigurations());
				
		fireCompletionChange();
		
		return false;
	}
	
	@Override
	public boolean modelURIChanged(URI modelUri) {
		cfg.setModelUri(modelUri);
		this.uriFieldDirty = false;
		fireCompletionChange();
		return false;
	}
	
	@Override
	public void parameterChanged(ParameterConfiguration paramCfg) {
		//cfg.parameterValues.put(parameter.getName(), value);
		fireCompletionChange();
	}
	
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
	
}
