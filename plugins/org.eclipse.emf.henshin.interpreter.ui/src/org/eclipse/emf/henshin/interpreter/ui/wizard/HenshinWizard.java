/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.ui.wizard;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.CompareUI;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.domain.ICompareEditingDomain;
import org.eclipse.emf.compare.domain.impl.EMFCompareEditingDomain;
import org.eclipse.emf.compare.ide.ui.internal.configuration.EMFCompareConfiguration;
import org.eclipse.emf.compare.ide.ui.internal.editor.ComparisonScopeEditorInput;
import org.eclipse.emf.compare.match.DefaultComparisonFactory;
import org.eclipse.emf.compare.match.DefaultEqualityHelperFactory;
import org.eclipse.emf.compare.match.DefaultMatchEngine;
import org.eclipse.emf.compare.match.IComparisonFactory;
import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.match.eobject.IEObjectMatcher;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryRegistryImpl;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.compare.utils.UseIdentifiers;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.henshin.interpreter.ui.HenshinInterpreterUIPlugin;
import org.eclipse.emf.henshin.interpreter.ui.util.ParameterConfig;
import org.eclipse.emf.henshin.interpreter.ui.util.TransformOperation;
import org.eclipse.emf.henshin.interpreter.ui.wizard.ModelSelector.ModelSelectorListener;
import org.eclipse.emf.henshin.interpreter.ui.wizard.ParameterEditTable.ParameterChangeListener;
import org.eclipse.emf.henshin.interpreter.ui.wizard.UnitSelector.UnitSelectionListener;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Gregor Bonifer, Stefan Jurack, Christian Krause
 */
@SuppressWarnings("restriction")
public class HenshinWizard extends Wizard implements UnitSelectionListener,
		ModelSelectorListener, ParameterChangeListener {

	protected Unit initialUnit;

	protected Module module;

	protected List<Unit> allUnits;

	protected List<Unit> outerUnits;
	
	protected List<CompletionListener> completionListeners = new ArrayList<HenshinWizard.CompletionListener>();

	protected TransformOperation transformOperation;

	protected HenshinWizardPage page;

	/*
	 * Private constructor.
	 */
	private HenshinWizard() {
		setWindowTitle(HenshinInterpreterUIPlugin.LL("_UI_Wizard"));
		setNeedsProgressMonitor(true);
	}

	/**
	 * Constructor.
	 * 
	 * @param module
	 *            Module to be used.
	 */
	public HenshinWizard(Module module) {
		this();
		this.module = module;
	}

	/**
	 * Constructor.
	 * 
	 * @param unit
	 *            Unit to be applied.
	 */
	public HenshinWizard(Unit unit) {
		this(unit.getModule());
		this.initialUnit = unit;
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

		// Create a fresh resource set for storing a copy of the module:
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource oldModuleResource = module.eResource();
		Resource newModuleResource = resourceSet.createResource(oldModuleResource.getURI());

		// Copy the original module into the fresh resource:
		Copier copier = new Copier();
		Collection<EObject> copies = copier.copyAll(oldModuleResource.getContents());
	    copier.copyReferences();		
		newModuleResource.getContents().addAll(copies);
	    
		// Now switch to the copied versions:
		module = (Module) copier.get(module);
		if (initialUnit != null) {
			initialUnit = (Unit) copier.get(initialUnit);
		}

		// Manually initialize the package registry:
		for (EPackage ePackage : module.getImports()) {
			resourceSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
		}
		
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
		
		if(!outerUnits.contains(initialUnit)){
			page.unitSelector.unitFilter.setSelection(true);
		}
		
		page.unitSelector.setSelectableUnits(
				selectableUnitLabels.toArray(new String[0]),
				outerUnitLabels.toArray(new String[0]));
		page.unitSelector.setSelection(initIdx);

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
		page.outputSelector.addModelSelectorListener(HenshinWizard.this);
		page.parameterEditor.addParameterChangeListener(HenshinWizard.this);
	}

	protected List<ParameterConfig> getParameterPreferences(Unit unit) {
		List<ParameterConfig> result = new ArrayList<ParameterConfig>();
		for (Parameter param : unit.getParameters()) {
			result.add(new ParameterConfig(param));
		}
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
		IFile file = getFile(transformOperation.getOutputUri().toString());
		page.setMessage(null);
		if (file != null && file.exists()) {
			page.setMessage("Warning: Output file exists already and will be overridden.", IMessageProvider.WARNING);
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

			if (page.openCompare.getSelection()) {	// Load the two input models
				ResourceSet resourceSet1 = new ResourceSetImpl();
				ResourceSet resourceSet2 = new ResourceSetImpl();
				String xmi1 = page.outputSelector.getModelURI();
				String xmi2 = page.inputSelector.getModelURI();
				load(xmi1, resourceSet1);
				load(xmi2, resourceSet2);

				// Configure EMF Compare
				IEObjectMatcher matcher = DefaultMatchEngine.createDefaultEObjectMatcher(UseIdentifiers.NEVER);
				IComparisonFactory comparisonFactory = new DefaultComparisonFactory(new DefaultEqualityHelperFactory());
				IMatchEngine.Factory matchEngineFactory = new MatchEngineFactoryImpl(matcher, comparisonFactory);
			        matchEngineFactory.setRanking(20);
			        IMatchEngine.Factory.Registry matchEngineRegistry = new MatchEngineFactoryRegistryImpl();
			        matchEngineRegistry.add(matchEngineFactory);
				EMFCompare comparator = EMFCompare.builder().setMatchEngineFactoryRegistry(matchEngineRegistry).build();

				// Compare the two models
				IComparisonScope scope = new DefaultComparisonScope(resourceSet1, resourceSet2, null);
			    ICompareEditingDomain editingDomain = EMFCompareEditingDomain.create(resourceSet1, resourceSet2, null);
			    AdapterFactory adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
			    
				CompareEditorInput input = new ComparisonScopeEditorInput(new EMFCompareConfiguration(new CompareConfiguration()), 
			        editingDomain, adapterFactory, comparator, scope);

			    CompareUI.openCompareEditor(input);
			}

		} catch (InvocationTargetException e) {
			String message = "Error applying transformation";
			if (e.getCause() != null && e.getCause().getMessage() != null) {
				message = e.getCause().getMessage();
			}
			MessageDialog.openError(getShell(), getWindowTitle(), message);
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}

	private void load(String absolutePath, ResourceSet resourceSet) {
		URI uri = URI.createURI(absolutePath);

		System.out.println(uri);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());

		// Resource will be loaded within the resource set
		resourceSet.getResource(uri, true);
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.henshin.interpreter.ui.wizard.widgets.UnitSelector.
	 * UnitSelectionListener#unitSelected(int)
	 */
	@Override
	public boolean unitSelected(int idx, boolean showInnerUnits) {
		Unit unit = showInnerUnits ? this.allUnits.get(idx) : this.outerUnits.get(idx);
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
	public void parameterChanged(ParameterConfig paramCfg) {
		// cfg.parameterValues.put(parameter.getName(), value);
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
