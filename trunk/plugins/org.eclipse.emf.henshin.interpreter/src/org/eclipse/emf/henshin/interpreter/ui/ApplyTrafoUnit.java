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
import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.interfaces.InterpreterEngine;
import org.eclipse.emf.henshin.interpreter.util.HenshinRegistry;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class ApplyTrafoUnit extends AbstractHandler {
	
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
			
			EmfGraph graph = new EmfGraph();
			InterpreterEngine engine = new EmfEngine(graph);
			
			for (EObject obj : res.getContents()) {
				graph.addRoot(obj);
			}
			
			String unitName = event.getParameter("org.eclipse.emf.henshin.UnitParameter");
			String trafoName = event.getParameter("org.eclipse.emf.henshin.TrafoSystemParameter");
			
			TransformationSystem trafoSystem = HenshinRegistry.instance.getTransformationSystemByName(trafoName);
			TransformationUnit unit = trafoSystem.findUnitByName(unitName);
			
			UnitApplication unitApplication = new UnitApplication(engine, unit);
			boolean result = unitApplication.execute();
			
			if (result) {
				res.getContents().clear();
				res.getContents().addAll(graph.getRootObjects());
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
