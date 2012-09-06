/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.ui;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.model.HenshinRegistry;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class ApplyTrafoUnit extends AbstractHandler {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getActiveMenuSelection(event);
		Object firstElement = selection.getFirstElement();
		
		if (firstElement instanceof IFile) {
			IFile file = (IFile) firstElement;
			
			String filename = file.getRawLocationURI().getRawPath();
			
			ResourceSet resourceSet = new ResourceSetImpl();
			Resource res = resourceSet.getResource(URI.createFileURI(filename), true);
			
			EGraph graph = InterpreterFactory.INSTANCE.createEGraph();
			Engine engine = InterpreterFactory.INSTANCE.createEngine();
			
			for (EObject obj : res.getContents()) {
				graph.addTree(obj);
			}
			
			String unitName = event.getParameter("org.eclipse.emf.henshin.UnitParameter");
			String trafoName = event.getParameter("org.eclipse.emf.henshin.TrafoSystemParameter");
			
			Module module = (Module) HenshinRegistry.INSTANCE.getTransformationSystemByName(trafoName);
			Unit unit = module.getUnit(unitName);
			
			UnitApplication unitApplication = InterpreterFactory.INSTANCE.createUnitApplication(engine);
			unitApplication.setEGraph(graph);
			unitApplication.setUnit(unit);
			boolean result = unitApplication.execute(null);
			
			if (result) {
				res.getContents().clear();
				res.getContents().addAll(graph.getRoots());
			}
			
			try {
				res.save(null);
			}
			catch (Exception e) {
				System.err.println(e);
			}
		}
		
		return null;
	}
}
