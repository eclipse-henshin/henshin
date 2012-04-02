package org.eclipse.emf.henshin.interpreter.ui;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.interpreter.util.HenshinRegistry;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

@Deprecated
public class RegisterTrafoSystem extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getActiveMenuSelection(event);
		Object firstElement = selection.getFirstElement();
		
		if (firstElement instanceof IFile) {
			IFile file = (IFile) firstElement;
			
			String filename = file.getRawLocationURI().getRawPath();
			
			ResourceSet resourceSet = new ResourceSetImpl();
			Resource res = resourceSet.getResource(
					URI.createFileURI(filename), true);
			
			for (Object obj: res.getContents()) {
				if (obj instanceof TransformationSystem) {
					HenshinRegistry.instance.registerTransformationSystem((TransformationSystem) obj);
				}
			}
		}
		
		return null;
	}
}
