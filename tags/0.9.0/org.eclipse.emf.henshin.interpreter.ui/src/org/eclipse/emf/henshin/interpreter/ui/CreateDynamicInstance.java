package org.eclipse.emf.henshin.interpreter.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
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
		URI instanceUri = modelUri.trimFileExtension().appendFileExtension("xmi");
		
		// Load the model:
		ResourceSet set = new ResourceSetImpl();
		Resource modelResource = set.getResource(modelUri, true);
		EPackage epackage = (EPackage) modelResource.getContents().get(0);
		
		// Choose class and create an instance:
		EClass eclass = (EClass) epackage.getEClassifiers().get(0);
		EObject instance = EcoreUtil.create(eclass);
		
		// Save and refresh folder:
		try {
			Resource instanceResource = set.createResource(instanceUri);
			instanceResource.getContents().add(instance);
			Map<Object,Object> options = new HashMap<Object,Object>();
			options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
			instanceResource.save(options);
			
			// Refresh:
			file.getParent().refreshLocal(2, new NullProgressMonitor());
		} catch (Throwable e) {
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
