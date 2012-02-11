package org.eclipse.emf.henshin.interpreter.ui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Christian Krause
 */
public class CreateDynamicInstance implements IObjectActionDelegate {
	
	private IFile file;
	
	@Override
	public void run(IAction action) {
		
		URI modelUri = URI.createPlatformResourceURI(file.getFullPath().toOSString(), true);
		
		// Load the model:
		ResourceSet set = new ResourceSetImpl();
		Resource modelResource = set.getResource(modelUri, true);
		EPackage epackage = (EPackage) modelResource.getContents().get(0);
		String modelFile = file.getName();
		String nsURI = epackage.getNsURI();
		String prefix = epackage.getNsPrefix();
		String root = epackage.getEClassifiers().get(0).getName();
		
		String contents = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<" + prefix + ":" + root + " xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" " + 
				"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " + 
				"xmlns:" + prefix + "=\"" + nsURI + "\" " + 
				"xsi:schemaLocation=\"" + nsURI + " " + modelFile + " \" />";
				
		// Save and refresh folder:
		try {
			IContainer folder = file.getParent();
			IFile xmi = folder.getFile(new Path(file.getName().replace(".ecore", ".xmi")));
			if (xmi.exists()) {
				xmi.delete(true, new NullProgressMonitor());
			}
			InputStream source = new ByteArrayInputStream(contents.getBytes());
			xmi.create(source, true, new NullProgressMonitor());
			folder.refreshLocal(2, new NullProgressMonitor());
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
	}
		
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		file = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sel = (IStructuredSelection) selection;
			file = (IFile) sel.getFirstElement();
		}
		action.setEnabled(file != null);
	}
	
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}
	
}
