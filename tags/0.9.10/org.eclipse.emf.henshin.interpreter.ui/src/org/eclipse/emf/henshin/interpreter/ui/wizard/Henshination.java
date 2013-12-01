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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.domain.ICompareEditingDomain;
import org.eclipse.emf.compare.domain.impl.EMFCompareEditingDomain;
import org.eclipse.emf.compare.ide.ui.internal.editor.ComparisonEditorInput;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.henshin.interpreter.ApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.BasicApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.interpreter.ui.HenshinInterpreterUIPlugin;
import org.eclipse.emf.henshin.interpreter.ui.util.Capsule;
import org.eclipse.emf.henshin.interpreter.ui.util.ParameterConfiguration;
import org.eclipse.emf.henshin.interpreter.ui.util.Tuple;
import org.eclipse.emf.henshin.interpreter.ui.util.Tuples;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * @author Gregor Bonifer, Stefan Jurack, Christian Krause
 */
public class Henshination {

	/**
	 * The unit to be applied.
	 */
	protected Unit unit;

	/**
	 * Configuration parameters.
	 */
	protected Collection<ParameterConfiguration> paramCfgs;

	/**
	 * Model URI.
	 */
	protected URI modelUri;

	/**
	 * Model resource.
	 */
	protected Resource modelResource;

	/**
	 * Used resource set.
	 */
	protected ResourceSet resourceSet;

	/**
	 * Default constructor.
	 */
	public Henshination() {
	}

	/**
	 * Get the model URI to be used.
	 * 
	 * @return The model URI.
	 */
	public URI getModelUri() {
		return modelUri;
	}

	public void setModelUri(URI modelUri) {
		this.modelUri = modelUri;
		this.modelResource = null;
		this.resourceSet = new ResourceSetImpl();
		registerImportedPackages();
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit, Collection<ParameterConfiguration> paramCfgs) {
		this.unit = unit;
		this.paramCfgs = paramCfgs;
		registerImportedPackages();
	}

	private void registerImportedPackages() {
		if (resourceSet == null || unit == null) {
			return;
		}
		for (EPackage pack : unit.getModule().getImports()) {
			String nsURI = pack.getNsURI();
			if (nsURI != null
					&& resourceSet.getPackageRegistry().getEPackage(nsURI) == null) {
				resourceSet.getPackageRegistry().put(nsURI, pack);
			}
		}
	}

	public Object getParameterValue(String parameterName) {
		ParameterConfiguration pCfg = getParameterConfiguration(parameterName);
		if (pCfg == null)
			return null;
		return pCfg.getValue();
	}

	public ParameterConfiguration getParameterConfiguration(String parameterName) {
		for (ParameterConfiguration pCfg : paramCfgs) {
			if (pCfg.getName().equals(parameterName)) {
				return pCfg;
			}
		}
		return null;
	}

	public Collection<ParameterConfiguration> getParameterConfigurations() {
		return paramCfgs;
	}

	protected Map<String, Object> prepareParameterValues() {
		Map<String, Object> result = new HashMap<String, Object>();
		for (ParameterConfiguration paramCfg : paramCfgs) {
			if (paramCfg.isClear())
				continue;
			// System.out.println(paramCfg.getName() + " => " +
			// paramCfg.getValue() + "("
			// + paramCfg.getTypeLabel() + ")[isNull:" + (paramCfg.getValue() ==
			// null) + "]");
			result.put(paramCfg.getName(), paramCfg.getValue());
		}
		// System.out.println(result);
		return result;
	}

	protected HenshinationResult applyTo(Resource model)
			throws HenshinationException {
		UnitApplication unitApplication = createUnitApplication(model);
		boolean result = runUnitApplication(unitApplication, model, false)
				.getSecond();
		return new HenshinationResult(this, unitApplication, result);
	}

	/**
	 * 
	 * @param ua
	 * @param undoOnCancel
	 * @return {@link Tuple} (not canceled, application result)
	 */
	protected Tuple<Boolean, Boolean> runUnitApplication(
			final UnitApplication ua, final Resource model,
			final boolean undoOnCancel) {

		// Remember the old root objects:
		Set<EObject> oldRoots = new HashSet<EObject>();
		oldRoots.addAll(ua.getEGraph().getRoots());

		final ApplicationMonitor appMon = new BasicApplicationMonitor();
		final Capsule<Boolean> result = new Capsule<Boolean>(false);
		IRunnableWithProgress unitApplicationMonitor = new IRunnableWithProgress() {
			@Override
			public synchronized void run(IProgressMonitor progMon) {
				Thread unitAppThread = new Thread(new Runnable() {
					public void run() {
						result.setValue(ua.execute(appMon));
					}
				});
				unitAppThread.start();
				while (unitAppThread.isAlive() && !progMon.isCanceled()) {
					try {
						this.wait(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (progMon.isCanceled() && unitAppThread.isAlive()) {
					if (undoOnCancel)
						appMon.cancelAndUndo();
					else
						appMon.cancel();
					try {
						unitAppThread.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		try {
			new ProgressMonitorDialog(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell()).run(true, true,
					unitApplicationMonitor);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Collect new root objects:
		for (EObject root : ua.getEGraph().getRoots()) {
			if (!oldRoots.contains(root)) {
				// System.out.println("Collect new root object: " + root);
				model.getContents().add(root);
			}
		}

		// return result;
		return new Tuple<Boolean, Boolean>(!appMon.isCanceled(),
				result.getValue());
	}

	protected UnitApplication createUnitApplication(Resource model) {
		EGraph graph = new EGraphImpl(model);
		Engine engine = new EngineImpl();
		UnitApplication unitApplication = new UnitApplicationImpl(engine,
				graph, unit, null);
		for (Entry<String, Object> entry : prepareParameterValues().entrySet()) {
			unitApplication.setParameterValue(entry.getKey(), entry.getValue());
		}
		return unitApplication;
	}

	public boolean isModelAffectedByTransformation() {
		Module module = unit.getModule();
		for (EPackage ep : module.getImports()) {
			EcoreUtil.resolveAll(ep);
		}
		for (EObject obj : getModel().getContents()) {
			if (module.getImports().contains(obj.eClass().getEPackage())) {
				return true;
			}
		}
		return false;
	}

	public HenshinationResultView createPreview() throws HenshinationException {

		Resource originalModel = getModel();
		Resource previewModel = createCopy(originalModel);
		HenshinationResult result = applyTo(previewModel);
		if (!result.isSuccess()) {
			return new HenshinationResultView() {
				@Override
				public void showDialog(Shell shell) {
					MessageBox mb = new MessageBox(shell, SWT.ICON_WARNING
							| SWT.OK);
					mb.setText(HenshinInterpreterUIPlugin
							.LL("_UI_Preview_ApplicationNotSuccessful_Title"));
					mb.setMessage(HenshinInterpreterUIPlugin
							.LL("_UI_Preview_ApplicationNotSuccessful_Message"));
					mb.open();
				}
			};
		}

		try {

			EMFCompare comparator = EMFCompare.builder().build();
			Comparison comparison = comparator.compare(EMFCompare
					.createDefaultScope(previewModel, originalModel, null));

			ICompareEditingDomain editingDomain = EMFCompareEditingDomain
					.create(previewModel, originalModel, null);
			AdapterFactory adapterFactory = new ComposedAdapterFactory(
					ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
			CompareEditorInput input = new ComparisonEditorInput(
					new CompareConfiguration(), comparison, editingDomain,
					adapterFactory);

			return new HenshinationPreview(input, result);
		} catch (Exception e) {
			e.printStackTrace();
			return new HenshinationPreview(null, result);
		}
	}

	protected Resource createCopy(Resource resource) {
		Resource copy = new XMIResourceImpl();
		copy.setURI(URI.createFileURI("preview.xmi"));
		copy.getContents().addAll(EcoreUtil.copyAll(resource.getContents()));
		return copy;
	}

	public Resource getModel() {

		if (modelResource != null) {
			return modelResource;
		}

		if (modelUri == null || modelUri.isEmpty()) {
			return null;
		}

		Map<String, Object> extReg = resourceSet.getResourceFactoryRegistry()
				.getExtensionToFactoryMap();

		if (!extReg.containsKey(modelUri.fileExtension())) {
			extReg.put(modelUri.fileExtension(), new XMIResourceFactoryImpl());
		}

		try {
			modelResource = resourceSet.getResource(modelUri, true);
		} catch (Exception e) {
			throw new RuntimeException("Unable to load Resource");
		}
		return modelResource;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + unit.getName() + " "
				+ paramCfgs.toString();
	}

	public IUndoableOperation getUndoableOperation() {

		ResourceSet resSet = (this.resourceSet != null) ? this.resourceSet
				: new ResourceSetImpl();
		Map<String, Object> extReg = resSet.getResourceFactoryRegistry()
				.getExtensionToFactoryMap();
		if (!extReg.containsKey(modelUri.fileExtension())) {
			extReg.put(modelUri.fileExtension(), new XMIResourceFactoryImpl());
		}

		final Resource originalModel = resSet.getResource(modelUri, true);

		final boolean createNewFile = HenshinInterpreterUIPlugin.getPlugin()
				.getPreferenceStore().getBoolean("createNewFile");

		final Resource workingModel;
		if (createNewFile) {
			workingModel = resSet.getResource(modelUri, true);
			String fileExtension = modelUri.fileExtension();
			workingModel.setURI(modelUri.trimFileExtension()
					.appendFileExtension("transformed")
					.appendFileExtension(fileExtension));
		} else {
			workingModel = originalModel;
		}

		final UnitApplication unitApplication = createUnitApplication(workingModel);

		String title = HenshinInterpreterUIPlugin
				.LL("_UI_UndoableOperation_Henshin")
				+ ": "
				+ getUnit().getName();

		IUndoableOperation operation = new AbstractOperation(title) {

			@Override
			public IStatus execute(IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {

				try {

					if (Tuples.and(runUnitApplication(unitApplication,
							workingModel, true))) {
						workingModel.save(null);
						return Status.OK_STATUS;
					}
					return new Status(Status.ERROR,
							HenshinInterpreterUIPlugin.PLUGIN_ID,
							"Canceled by user!");
				} catch (Exception e) {
					return new Status(Status.ERROR,
							HenshinInterpreterUIPlugin.PLUGIN_ID,
							e.getMessage());
				}

			}

			@Override
			public IStatus redo(IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				try {
					unitApplication.redo(null);
					workingModel.save(null);
				} catch (Exception e) {
					return new Status(Status.ERROR,
							HenshinInterpreterUIPlugin.PLUGIN_ID,
							e.getMessage());
				}
				return Status.OK_STATUS;
			}

			@Override
			public IStatus undo(IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				try {
					if (createNewFile) {
						workingModel.delete(null);
					} else {
						unitApplication.undo(null);
						workingModel.save(null);
					}
				} catch (Exception e) {
					return new Status(Status.ERROR,
							HenshinInterpreterUIPlugin.PLUGIN_ID,
							e.getMessage());
				}
				return Status.OK_STATUS;
			}
		};
		operation.addContext(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		return operation;
	}

}
