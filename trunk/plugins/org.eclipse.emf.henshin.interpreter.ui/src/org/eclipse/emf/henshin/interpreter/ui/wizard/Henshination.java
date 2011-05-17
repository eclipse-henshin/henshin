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
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.ui.InterpreterUIPlugin;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * 
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack
 */
public class Henshination {
	
	protected TransformationUnit transformationUnit;
	
	protected Map<String, Object> parameterValues = new HashMap<String, Object>();
	
	protected URI modelUri;
	
	protected ResourceSet resourceSet;
	
	public Henshination() {
		resourceSet = new ResourceSetImpl();
		modelUri = null;
	}
	
	public URI getModelUri() {
		return modelUri;
	}
	
	public void setModelUri(URI modelUri) {
		this.modelUri = modelUri;
	}
	
	public TransformationUnit getTransformationUnit() {
		return transformationUnit;
	}
	
	public void setTransformationUnit(TransformationUnit transformationUnit) {
		this.transformationUnit = transformationUnit;
		
		if (transformationUnit.eResource() != null
				&& transformationUnit.eResource().getResourceSet() != null)
			this.resourceSet = transformationUnit.eResource().getResourceSet();
		
		for (Parameter p : this.transformationUnit.getParameters())
			this.parameterValues.put(p.getName(), "");
	}
	
	public void setParameterValue(String parameterName, Object value) {
		this.parameterValues.put(parameterName, value);
	}
	
	public Collection<String> getParameterNames() {
		return parameterValues.keySet();
	}
	
	public Object getParameterValue(String parameterName) {
		return parameterValues.get(parameterName);
	}
	
	protected HenshinationResult applyTo(EObject model) throws HenshinationException {
		UnitApplication unitApplication = createUnitApplication(model);
		try {
			unitApplication.execute();
		} catch (Exception e) {
			throw new HenshinationException(e, unitApplication);
		}
		
		return new HenshinationResult(this, unitApplication);
	}
	
	protected UnitApplication createUnitApplication(EObject model) {
		EmfGraph context = new EmfGraph();
		context.addRoot(model);
		EmfEngine engine = new EmfEngine(context);
		UnitApplication unitApplication = new UnitApplication(engine, transformationUnit);
		unitApplication.setParameterValues(parameterValues);
		return unitApplication;
	}
	
	public boolean isModelAffectedByTtransformation() {
		TransformationSystem tSys = (TransformationSystem) transformationUnit.eContainer();
		for (EPackage ep : tSys.getImports()) {
			EcoreUtil.resolveAll(ep);
		}
		return tSys.getImports().contains(getModel().eClass().getEPackage());
	}
	
	public HenshinationPreview createPreview() throws HenshinationException {
		EObject originalModel = createCopy(getModel());
		EObject previewModel = getModel();
		HenshinationResult result = applyTo(previewModel);
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
		ResourceSet rs = new ResourceSetImpl();
		Resource r = rs.createResource(eobj.eResource().getURI());
		try {
			r.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return r.getContents().get(0);
		
		// EObject copy = EcoreUtil.copy(eobj);
		// Resource resource = new ResourceImpl();
		// resource.setURI(URI.createFileURI("."));
		// resource.getContents().add(copy);
		// return copy;
	}
	
	public EObject getModel() {
		if (modelUri == null || modelUri.isEmpty()) return null;
		
		ResourceSet resSet = (this.resourceSet != null) ? this.resourceSet : new ResourceSetImpl();
		Map<String, Object> extReg = resSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
		if (!extReg.containsKey(modelUri.fileExtension()))
			extReg.put(modelUri.fileExtension(), new XMIResourceFactoryImpl());
		Resource res;
		
		try {
			res = resSet.getResource(modelUri, true);
		} catch (Exception e) {
			throw new RuntimeException("Unable to load Resource");
		}
		if (res.getContents().size() == 0) throw new RuntimeException("Resource is empty");
		
		return res.getContents().get(0);
		
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + transformationUnit.getName() + " "
				+ parameterValues.toString();
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
					unitApplication.execute();
					resource.save(null);
				} catch (Exception e) {
					return new Status(Status.ERROR, InterpreterUIPlugin.ID, e.getMessage());
				}
				return Status.OK_STATUS;
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
