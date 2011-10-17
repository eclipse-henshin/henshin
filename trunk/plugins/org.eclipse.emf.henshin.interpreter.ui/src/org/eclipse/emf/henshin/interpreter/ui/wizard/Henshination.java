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

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.diff.metamodel.ComparisonResourceSnapshot;
import org.eclipse.emf.compare.diff.metamodel.DiffFactory;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.service.DiffService;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.compare.match.service.MatchService;
import org.eclipse.emf.compare.ui.editor.ModelCompareEditorInput;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.ui.InterpreterUIPlugin;
import org.eclipse.emf.henshin.interpreter.ui.util.Capsule;
import org.eclipse.emf.henshin.interpreter.ui.util.Tuple;
import org.eclipse.emf.henshin.interpreter.ui.util.Tuples;
import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * 
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack
 */
public class Henshination {
	
	protected TransformationUnit transformationUnit;
	
	protected Collection<ParameterConfiguration> paramCfgs;
	
	protected URI modelUri;
	
	protected EObject modelRoot;
	
	protected ResourceSet resourceSet;
	
	public Henshination() {
		modelUri = null;
	}
	
	public URI getModelUri() {
		return modelUri;
	}
	
	public void setModelUri(URI modelUri) {
		this.modelUri = modelUri;
		this.modelRoot = null;
		this.resourceSet = new ResourceSetImpl();
		registerImportedPackages();
	}
	
	public TransformationUnit getTransformationUnit() {
		return transformationUnit;
	}
	
	public void setTransformationUnit(TransformationUnit transformationUnit,
			Collection<ParameterConfiguration> paramCfgs) {
		this.transformationUnit = transformationUnit;
		this.paramCfgs = paramCfgs;
		registerImportedPackages();
	}
	
	private void registerImportedPackages() {
		if (this.resourceSet == null || this.transformationUnit == null)
			return;
		TransformationSystem tSys = (TransformationSystem) this.transformationUnit.eContainer();
		for (EPackage pack : tSys.getImports())
			this.resourceSet.getPackageRegistry().put(pack.getNsURI(), pack);
	}
	
	public Object getParameterValue(String parameterName) {
		for (ParameterConfiguration pCfg : paramCfgs)
			if (pCfg.getName().equals(parameterName))
				return pCfg.getValue();
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
			System.out.println(paramCfg.getName() + " => " + paramCfg.getValue() + "("
					+ paramCfg.getTypeLabel() + ")[isNull:" + (paramCfg.getValue() == null) + "]");
			result.put(paramCfg.getName(), paramCfg.getValue());
		}
		System.out.println(result);
		
		return result;
	}
	
	protected HenshinationResult applyTo(EObject model) throws HenshinationException {
		UnitApplication unitApplication = createUnitApplication(model);
		boolean result = runUnitApplication(unitApplication, false).getSecond();
		return new HenshinationResult(this, unitApplication, result);
	}
	
	/**
	 * 
	 * @param ua
	 * @param undoOnCancel
	 * @return {@link Tuple} (not canceled, application result)
	 */
	protected Tuple<Boolean, Boolean> runUnitApplication(final UnitApplication ua,
			final boolean undoOnCancel) {
		final UnitApplication.ApplicationMonitor appMon = ua.getApplicationMonitor();
		final Capsule<Boolean> result = new Capsule<Boolean>();
		IRunnableWithProgress unitApplicationMonitor = new IRunnableWithProgress() {
			@Override
			public synchronized void run(IProgressMonitor progMon) {
				Thread unitAppThread = new Thread(new Runnable() {
					public void run() {
						result.setValue(ua.execute());
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
			new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getShell()).run(true, true, unitApplicationMonitor);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// return result;
		return new Tuple<Boolean, Boolean>(!appMon.isCanceled(), result.getValue());
	}
	
	protected UnitApplication createUnitApplication(EObject model) {
		EmfGraph context = new EmfGraph();
		context.addRoot(model);
		EmfEngine engine = new EmfEngine(context);
		UnitApplication unitApplication = new UnitApplication(engine, transformationUnit);
		unitApplication.setParameterValues(prepareParameterValues());
		return unitApplication;
	}
	
	public boolean isModelAffectedByTransformation() {
		TransformationSystem tSys = (TransformationSystem) transformationUnit.eContainer();
		for (EPackage ep : tSys.getImports())
			EcoreUtil.resolveAll(ep);
		return tSys.getImports().contains(getModel().eClass().getEPackage());
	}
	
	public HenshinationResultView createPreview() throws HenshinationException {
		EObject originalModel = createCopy(getModel());
		EObject previewModel = createCopy(getModel());
		HenshinationResult result = applyTo(previewModel);
		if (!result.isSuccess()) {
			return new HenshinationResultView() {
				@Override
				public void showDialog(Shell shell) {
					MessageBox mb = new MessageBox(shell, SWT.ICON_WARNING |  SWT.OK );
					mb.setText(InterpreterUIPlugin.LL("_UI_Preview_ApplicationNotSuccessful_Title"));
					mb.setMessage(InterpreterUIPlugin.LL("_UI_Preview_ApplicationNotSuccessful_Message"));
					mb.open();
				}
			};
		}
		try {
			MatchModel matchModel = MatchService.doMatch(previewModel, originalModel,
					Collections.<String, Object> emptyMap());
			DiffModel diffModel = DiffService.doDiff(matchModel);
			ComparisonResourceSnapshot snapshot = DiffFactory.eINSTANCE
					.createComparisonResourceSnapshot();
			snapshot.setMatch(matchModel);
			snapshot.setDiff(diffModel);
			return new HenshinationPreview(new ModelCompareEditorInput(snapshot), result);
		} catch (InterruptedException e) {
			return new HenshinationPreview(null, result);
		}
	}
	
	protected EObject createCopy(EObject eobj) {
		EObject result = EcoreUtil.copy(eobj);
		Resource resource = new ResourceImpl();
		resource.setURI(eobj.eResource().getURI());
		resource.getContents().add(result);
		return result;
	}
	
	public EObject getModel() {
		
		if (modelRoot != null)
			return modelRoot;
		
		if (modelUri == null || modelUri.isEmpty())
			return null;
		
		Map<String, Object> extReg = resourceSet.getResourceFactoryRegistry()
				.getExtensionToFactoryMap();
		
		if (!extReg.containsKey(modelUri.fileExtension()))
			extReg.put(modelUri.fileExtension(), new XMIResourceFactoryImpl());
		Resource res;
		
		try {
			res = resourceSet.getResource(modelUri, true);
		} catch (Exception e) {
			throw new RuntimeException("Unable to load Resource");
		}
		
		if (res.getContents().size() == 0)
			throw new RuntimeException("Resource is empty");
		
		modelRoot = res.getContents().get(0);
		
		return modelRoot;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + transformationUnit.getName() + " "
				+ paramCfgs.toString();
	}
	
	public IUndoableOperation getUndoableOperation() {
		
		ResourceSet resSet = (this.resourceSet != null) ? this.resourceSet : new ResourceSetImpl();
		Map<String, Object> extReg = resSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
		if (!extReg.containsKey(modelUri.fileExtension()))
			extReg.put(modelUri.fileExtension(), new XMIResourceFactoryImpl());
		
		final Resource resource = resSet.getResource(modelUri, true);
		final UnitApplication unitApplication = createUnitApplication(resource.getContents().get(0));
		
		String title = InterpreterUIPlugin.LL("_UI_UndoableOperation_Henshin") + ": "
				+ getTransformationUnit().getName();
		
		IUndoableOperation operation = new AbstractOperation(title) {
			
			@Override
			public IStatus execute(IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				
				try {
					if (Tuples.and(runUnitApplication(unitApplication, true))) {
						resource.save(null);
						return Status.OK_STATUS;
					}
					return new Status(Status.ERROR, InterpreterUIPlugin.ID, "Canceled by user!");
				} catch (Exception e) {
					return new Status(Status.ERROR, InterpreterUIPlugin.ID, e.getMessage());
				}
				
			}
			
			@Override
			public IStatus redo(IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				try {
					unitApplication.redo();
					resource.save(null);
				} catch (Exception e) {
					return new Status(Status.ERROR, InterpreterUIPlugin.ID, e.getMessage());
				}
				return Status.OK_STATUS;
			}
			
			@Override
			public IStatus undo(IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				try {
					unitApplication.undo();
					resource.save(null);
				} catch (Exception e) {
					return new Status(Status.ERROR, InterpreterUIPlugin.ID, e.getMessage());
				}
				return Status.OK_STATUS;
			}
		};
		operation.addContext(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		return operation;
	}
	
}
