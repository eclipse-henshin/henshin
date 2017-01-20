package org.eclipse.emf.henshin.text.ui.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;

import org.eclipse.emf.henshin.interpreter.ui.wizard.HenshinWizard;
import org.eclipse.emf.henshin.interpreter.ui.wizard.HenshinWizardDialog;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.text.henshin_text.Model;
import org.eclipse.emf.henshin.text.henshin_text.ModelElement;
import org.eclipse.emf.henshin.text.ui.util.Transformation;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.inject.Inject;



public class ApplyTransformationHandler extends AbstractHandler implements IHandler{

	/**
	 * 
	 * Transforms a henshin_text file to a henshin file and calls the ApplyTransformation dialog.
	 * 
	 * @param event ExecutionEvent
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		Resource henshin_textResource = null;
		String modelElementName = "";

		if (HandlerUtil.getActiveMenuSelection(event) instanceof org.eclipse.jface.viewers.TreeSelection) {

			IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveMenuSelection(event);

			// We expect exactly one element:
			if (selection.size()==1) {
				Object first = selection.getFirstElement();

				//if on IFile (e.g. explorer)
				if (first instanceof IFile) {
					IFile file = (IFile) first;

					// Must be a Henshin file:
					if ("henshin_text".equals(file.getFileExtension())) {

						URI uri = URI.createPlatformResourceURI(file.getFullPath().toOSString(), true);

						// Create a resource set and load the resource::
						ResourceSet henshin_textResourceSet = new ResourceSetImpl();
						henshin_textResource = henshin_textResourceSet.getResource(uri, true);	
					}
				}
			}
		}
		else if (HandlerUtil.getActiveMenuSelection(event) instanceof TextSelection)
		{
			if(((XtextEditor)HandlerUtil.getActiveEditor(event)).isDirty()){
				((XtextEditor)HandlerUtil.getActiveEditor(event)).doSave(null);
			}

			URI uri = URI.createPlatformResourceURI(((XtextEditor)HandlerUtil.getActiveEditor(event)).getResource().getFullPath().toString(), true);
			ResourceSet henshin_textResourceSet = new ResourceSetImpl();
			henshin_textResource = henshin_textResourceSet.getResource(uri, true);
			
			modelElementName=getCalledElementName(event).replace(" ", "");
		}
		

		if (henshin_textResource != null) {
			Diagnostic diagnostic = Diagnostician.INSTANCE.validate(henshin_textResource.getContents().get(0));
			//boolean comment=true;       
			if(henshin_textResource.getErrors().isEmpty()&&((diagnostic.getSeverity()==Diagnostic.OK)||(diagnostic.getSeverity()==Diagnostic.WARNING))){
				if(((Model) henshin_textResource.getContents().get(0)).getTransformationsystem().size()>0){

					ResourceSet resourceSetTransform = new ResourceSetImpl();
					String uriMy=henshin_textResource.getURI().toString().replace(".","_")+".henshin";
					Resource resourceResult = resourceSetTransform.createResource(URI.createURI(uriMy));
					Transformation transformation=new Transformation();
					resourceResult=transformation.transformHenshin_textToHenshin(henshin_textResource,"platform:/plugin/org.eclipse.emf.henshin.text.transformation/transforms/Henshin_text2HenshinTransformation/Henshin_text2HenshinTransformation.qvto","");
					if(resourceResult!=null){
						//Prepare for Transformation-Dialog
						List <org.eclipse.emf.henshin.model.Unit> help=orderUnits(((Module) resourceResult.getContents().get(0)).getUnits());
						((Module) resourceResult.getContents().get(0)).getUnits().clear();
						((Module) resourceResult.getContents().get(0)).getUnits().addAll(help);
						org.eclipse.emf.henshin.model.Unit unit=getUnitByName(((Module) resourceResult.getContents().get(0)).getUnits(),modelElementName);

						//Call Transformation-Dialog
						HenshinWizard tWiz;
						if (unit!= null)
						{
							tWiz = new HenshinWizard(unit);
						}
						else {
							tWiz = new HenshinWizard((Module) resourceResult.getContents().get(0));
						}
						HenshinWizardDialog dialog = new HenshinWizardDialog(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), tWiz);
						dialog.open();
					}else{
						Status errorStatus = new Status(IStatus.ERROR, "org.eclipse.emf.henshin.text.ui", "Can`t transform "+((XtextEditor)HandlerUtil.getActiveEditor(event)).getResource().getName()+"!");
						ErrorDialog.openError(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), "Apply Henshin Transformation", null,errorStatus);
					}
				}else{
					Status errorStatus = new Status(IStatus.ERROR, "org.eclipse.emf.henshin.text.ui", "Please define a rule or a unit!");
					ErrorDialog.openError(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), "Apply Henshin Transformation", null,errorStatus);
				}
			}else{
				Status errorStatus = new Status(IStatus.ERROR, "org.eclipse.emf.henshin.text.ui", "Please fix existing errors in "+((XtextEditor)HandlerUtil.getActiveEditor(event)).getResource().getName()+"!");
				ErrorDialog.openError(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), "Apply Henshin Transformation", null,errorStatus);
			}
		}else {
			Status errorStatus = new Status(IStatus.ERROR, "org.eclipse.emf.henshin.text.ui", "Handler needs to be executed on henshin_text file or inside the henshin text editor!");
			ErrorDialog.openError(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), "Apply Henshin Transformation", null,errorStatus);
		}
		return null;
    }
 
		@Inject EObjectAtOffsetHelper eObjectAtOffsetHelper;
		
		private String getCalledElementName(ExecutionEvent event){
			EObject astObject = null;
			
			int eventIndex=((org.eclipse.jface.text.TextSelection)HandlerUtil.getShowInSelection(event)).getOffset();
					
			// get selected AST element
			XtextEditor editor = EditorUtils.getActiveXtextEditor(event);
			if (editor != null) {
				astObject = editor.getDocument().readOnly(new IUnitOfWork<EObject, XtextResource>() {
					public EObject exec(XtextResource localResource) throws Exception {
						return eObjectAtOffsetHelper.resolveContainedElementAt(localResource, eventIndex);
					}
				});
			}
				
			// run up the hierarchy until we find a top rule/unit and use its name as selection
			while (astObject != null && !(astObject instanceof ModelElement && (astObject.eContainer() instanceof Module || astObject.eContainer() instanceof Model)))
			{
				//System.out.println(((ModelElement) astObject).getName());
				astObject = astObject.eContainer();
			}
	
			if (astObject instanceof ModelElement)
				return ((ModelElement) astObject).getName();
			else	
				return "";
		}
	
	
	
	

	/**
	 * Order the list of units by normal defined units and automatically created nested units.
	 * 
	 * @param units List of units
	 * @return Ordered list 
	*/
    private List<org.eclipse.emf.henshin.model.Unit> orderUnits(EList<org.eclipse.emf.henshin.model.Unit> units){
    	List<org.eclipse.emf.henshin.model.Unit> outerUnits=new ArrayList<org.eclipse.emf.henshin.model.Unit>();
    	List<org.eclipse.emf.henshin.model.Unit> innerUnits=new ArrayList<org.eclipse.emf.henshin.model.Unit>();
    	for(org.eclipse.emf.henshin.model.Unit u:units){
    		boolean isOuterUnit = true;
			for (org.eclipse.emf.henshin.model.Unit outerUnit : units) {
				if (outerUnit.getSubUnits(true).contains(u)) {
					isOuterUnit = false;
					break;
				}
			}
			if(isOuterUnit){
				innerUnits.add(u);
			}else{
				outerUnits.add(u);
			}	
    	}
    	innerUnits.addAll(outerUnits);
		return innerUnits;		
	}


    /**
     * Search in a list of units a unit by its name
     * 
     * @param units List of units
     * @param name Name of the searched unit
     * @return Unit-Object with the given name
     */
   private org.eclipse.emf.henshin.model.Unit getUnitByName(List<org.eclipse.emf.henshin.model.Unit> units,String name){
    	for(org.eclipse.emf.henshin.model.Unit unit:units){
    		if(unit.getName().equals(name)){
    			return unit;
    		}
    	}
    	return null;
    }
    
}


