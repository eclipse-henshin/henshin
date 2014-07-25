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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.internal.CompareAction;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.interpreter.ui.HenshinInterpreterUIPlugin;
import org.eclipse.emf.henshin.interpreter.ui.util.ParameterConfiguration;
import org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.ModelSelector.ModelSelectorListener;
import org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.ParameterEditTable.ParameterChangeListener;
import org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.UnitSelector.UnitSelectionListener;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

/**
 * @author Gregor Bonifer, Stefan Jurack, Christian Krause
 */
public class HenshinWizard extends Wizard implements UnitSelectionListener,
		ModelSelectorListener, ParameterChangeListener {

	protected Unit initialUnit;

	protected Module module;

	protected List<Unit> availableUnits;

	protected List<CompletionListener> completionListeners = new ArrayList<HenshinWizard.CompletionListener>();

	protected TransformOperation transformOperation;

	protected HenshinWizardPage page;

	protected IPreferenceStore store = HenshinInterpreterUIPlugin.getPlugin()
			.getPreferenceStore();

	protected boolean unitSelectable = true;

	/**
	 * Constructor.
	 * 
	 * @param unit
	 *            Unit to be applied.
	 */
	public HenshinWizard(Unit unit) {
		this();
		unitSelectable = false;
		this.initialUnit = unit;
		this.module = unit.getModule();
	}

	/**
	 * Constructor.
	 * 
	 * @param module
	 *            Module to be used.
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
		setNeedsProgressMonitor(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		addPage(page = new HenshinWizardPage());
		page.module = module;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.wizard.Wizard#createPageControls(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	public void createPageControls(Composite pageContainer) {
		super.createPageControls(pageContainer);
		initData();
	}

	protected void initData() {

		/*
		 * We do now reload the unit/module in a separate ResourceSet in order
		 * to work with them without corrupting the original resource set, which
		 * might be used by an editor.
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
			String unitLabel = unit.toString();
			selectableUnitLabels.add(unitLabel);
			Boolean isOuterUnit = true;
			for (Unit outerUnit : availableUnits) {
				if (outerUnit.getSubUnits(true).contains(unit)) {
					isOuterUnit = false;
					break;
				}
			}
			if (isOuterUnit) {
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
		page.unitSelector.setSelectableUnits(
				selectableUnitLabels.toArray(new String[0]),
				outerUnitLabels.toArray(new String[0]));
		page.unitSelector.setSelection(initIdx);

		// Enable selector if no unit was given in the constructor:
		page.unitSelector.setEnabled(initialUnit == null);

		transformOperation = new TransformOperation();
		if (selectedUnit != null) {
			transformOperation.setUnit(selectedUnit,
					getParameterPreferences(selectedUnit));
		}

		if (selectedUnit != null) {
			page.parameterEditor.setParameters(transformOperation
					.getParameterConfigurations());
		}

		page.unitSelector.addUnitSelectionListener(HenshinWizard.this);
		page.inputSelector.addModelSelectorListener(HenshinWizard.this);
		page.parameterEditor.addParameterChangeListener(HenshinWizard.this);
	}

	protected List<ParameterConfiguration> getParameterPreferences(Unit unit) {
		List<ParameterConfiguration> result = new ArrayList<ParameterConfiguration>();
		for (Parameter param : unit.getParameters())
			result.add(ParameterConfiguration.loadConfiguration(store,
					param.getName()));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		if (transformOperation.getInputUri() == null
				|| transformOperation.getOutputUri() == null
				|| transformOperation.getUnit() == null) {
			return false;
		}
		return true;
	}

	private IFile getFile(String uriString) {
		try {
			URI uri = URI.createURI(uriString);
			if (uri.isPlatformResource()) {
				IPath path = new Path(uri.toPlatformString(false));
				return ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			}
		} catch (IllegalArgumentException e) {
			return null;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		try {
			getContainer().run(true, true, transformOperation);

			if (page.openCompare.getSelection()) {
				IFile left = getFile(page.outputSelector.getModelURI());
				IFile right = getFile(page.inputSelector.getModelURI());
				if (left != null && right != null) {
					try {
						ISelection selection = new StructuredSelection(
								new Object[] { left, right });
						MyCompareAction c = new MyCompareAction();
						c.setActivePart(null, PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getActivePage()
								.getActivePart());
						c.run(selection);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		} catch (InvocationTargetException e) {
			MessageDialog.openError(getShell(), getWindowTitle(), e.getCause()
					.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}

	private class MyCompareAction extends CompareAction {
		public void run(ISelection selection) {
			if (super.isEnabled(selection)) {
				super.run(selection);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.UnitSelector.
	 * UnitSelectionListener#unitSelected(int)
	 */
	@Override
	public boolean unitSelected(int idx) {
		Unit unit = this.availableUnits.get(idx);
		transformOperation.setUnit(unit, getParameterPreferences(unit));
		page.parameterEditor.setParameters(transformOperation
				.getParameterConfigurations());
		fireCompletionChange();
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.ModelSelector.
	 * ModelSelectorListener#modelURIChanged(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public boolean modelURIChanged(String modelUri) {
		try {
			transformOperation.setInputURI(URI.createURI(page.inputSelector
					.getModelURI()));
			transformOperation.setOutputURI(URI.createURI(page.outputSelector
					.getModelURI()));
			fireCompletionChange();
		} catch (IllegalArgumentException e) {
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.ParameterEditTable
	 * .ParameterChangeListener
	 * #parameterChanged(org.eclipse.emf.henshin.interpreter
	 * .ui.wizard.ParameterConfiguration)
	 */
	@Override
	public void parameterChanged(ParameterConfiguration paramCfg) {
		// cfg.parameterValues.put(parameter.getName(), value);
		fireCompletionChange();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.ModelSelector.
	 * ModelSelectorListener#uriFieldDirty()
	 */
	@Override
	public void uriFieldDirty() {
		fireCompletionChange();
	}

	protected void fireCompletionChange() {
		getContainer().updateButtons();

		for (CompletionListener l : completionListeners) {
			l.completionChanged();
		}
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
