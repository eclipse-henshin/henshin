package org.eclipse.emf.henshin.text.ui.handler;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;

import org.eclipse.emf.henshin.text.ui.util.Transformation;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtext.ui.editor.XtextEditor;

public class TransformHenshin_TextHandler extends AbstractHandler implements IHandler{

	/**
	 * Transformiert eine henshin_text-Datei in eine henshin-Datei 
	 * 
	 * @param event ExecutionEvent
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if(((XtextEditor)HandlerUtil.getActiveEditor(event)).isDirty()){
        	((XtextEditor)HandlerUtil.getActiveEditor(event)).doSave(null);
        }
		URI henshin_textUri = URI.createPlatformResourceURI(((XtextEditor)HandlerUtil.getActiveEditor(event)).getResource().getFullPath().toString(), true);
		ResourceSet henshin_textResourceSet = new ResourceSetImpl();
        Resource henshin_textResource = henshin_textResourceSet.getResource(henshin_textUri, true);
        Diagnostic diagnostic = Diagnostician.INSTANCE.validate(henshin_textResource.getContents().get(0));
        if(henshin_textResource.getErrors().isEmpty()&&((diagnostic.getSeverity()==Diagnostic.OK)||(diagnostic.getSeverity()==Diagnostic.WARNING))){
			ResourceSet resourceSetTransform = new ResourceSetImpl();
			String henshinUri=henshin_textUri.toString().replace(".henshin_text","_henshin_text")+".henshin";
			Resource resourceResult = resourceSetTransform.createResource(URI.createURI(henshinUri));
			Transformation transformation=new Transformation();
			resourceResult=transformation.transformHenshin_textToHenshin(henshin_textResource,"platform:/plugin/org.eclipse.emf.henshin.text.transformation/transforms/Henshin_text2HenshinTransformation/Henshin_text2HenshinTransformation.qvto","");
			if(resourceResult!=null){
				try {
					resourceResult.save(Collections.EMPTY_MAP);
					
					try {
						//Open new .henshin file
						IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						IFile file=(IFile) ResourcesPlugin.getWorkspace().getRoot().findMember(resourceResult.getURI().toPlatformString(true));
						IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
						page.openEditor(new FileEditorInput(file), desc.getId());
					} catch (PartInitException e) {
						Status errorStatus = new Status(IStatus.ERROR, "org.eclipse.emf.henshin.text.ui", "Can`t open henshin file!");
						ErrorDialog.openError(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), "Transform to henshin file", null,errorStatus);
						e.printStackTrace();
					}
					
				} catch (IOException e) {
					Status errorStatus = new Status(IStatus.ERROR, "org.eclipse.emf.henshin.text.ui", "Can`t save transformation result!");
					ErrorDialog.openError(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), "Transform to henshin file", null,errorStatus);
					e.printStackTrace();
				}
			}else{
				Status errorStatus = new Status(IStatus.ERROR, "org.eclipse.emf.henshin.text.ui", "Can`t transform "+((XtextEditor)HandlerUtil.getActiveEditor(event)).getResource().getName()+"!");
				ErrorDialog.openError(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), "Transform to henshin file", null,errorStatus);
			}
        
        }else{
        	Status errorStatus = new Status(IStatus.ERROR, "org.eclipse.emf.henshin.text.ui", "Please fix existing errors in "+((XtextEditor)HandlerUtil.getActiveEditor(event)).getResource().getName()+"!");
        	ErrorDialog.openError(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), "Transform to henshin file", null,errorStatus);
        }
		return null;
	}

}

