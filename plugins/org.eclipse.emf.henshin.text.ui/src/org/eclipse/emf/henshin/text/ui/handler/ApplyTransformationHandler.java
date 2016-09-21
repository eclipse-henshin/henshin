package org.eclipse.emf.henshin.text.ui.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import org.eclipse.emf.henshin.interpreter.ui.wizard.HenshinWizard;
import org.eclipse.emf.henshin.interpreter.ui.wizard.HenshinWizardDialog;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.resource.HenshinResource;
import org.eclipse.emf.henshin.text.henshin_text.Model;
import org.eclipse.emf.henshin.text.henshin_text.ModelElement;
import org.eclipse.emf.henshin.text.henshin_text.Unit;
import org.eclipse.emf.henshin.text.ui.util.ModifyModelUnits;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
 
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import org.eclipse.m2m.qvt.oml.BasicModelExtent;
import org.eclipse.m2m.qvt.oml.ExecutionContext;
import org.eclipse.m2m.qvt.oml.ExecutionContextImpl;
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic;
import org.eclipse.m2m.qvt.oml.ModelExtent;
import org.eclipse.m2m.qvt.oml.TransformationExecutor;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;


public class ApplyTransformationHandler extends AbstractHandler implements IHandler{

	/**
	 * Transformiert eine henshin_text-Datei in eine henshin-Datei und ruft den ApplyTransformation-Dialog auf.
	 * 
	 * @param event ExecutionEvent
	 */
	@Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
		if(((XtextEditor)HandlerUtil.getActiveEditor(event)).isDirty()){
        	((XtextEditor)HandlerUtil.getActiveEditor(event)).doSave(null);
        }
		boolean comment=true;
		URI uri = URI.createPlatformResourceURI(((XtextEditor)HandlerUtil.getActiveEditor(event)).getResource().getFullPath().toString(), true);
		ResourceSet henshin_textResourceSet = new ResourceSetImpl();
        Resource henshin_textResource = henshin_textResourceSet.getResource(uri, true);
        Diagnostic diagnostic = Diagnostician.INSTANCE.validate(henshin_textResource.getContents().get(0));
        
        if(henshin_textResource.getErrors().isEmpty()&&((diagnostic.getSeverity()==Diagnostic.OK)||(diagnostic.getSeverity()==Diagnostic.WARNING))){
        	if(((Model) henshin_textResource.getContents().get(0)).getTransformationsystem().size()>0){
        	String modelElementName=getCalledElementName(event).replace(" ", "");

        	//Modify Units
            List<Unit> unitList=new ArrayList<Unit>();
            List<Unit> resultUnitList=new ArrayList<Unit>();
            ModifyModelUnits modifyUnit=new ModifyModelUnits();
    		for (ModelElement modelElement : ((Model) henshin_textResource.getContents().get(0)).getTransformationsystem()) {
    			if(modelElement instanceof Unit){
    				unitList.add((Unit)modelElement);	
    			}
    			if(modelElement.getName().equals(modelElementName)){
    				comment=false;
    			}
    		}
    		if(!comment){
    			for(Unit unit:unitList){
    				resultUnitList.addAll(modifyUnit.flat(unit,0,null));
    			}
    			((Model) henshin_textResource.getContents().get(0)).getTransformationsystem().removeAll(unitList);
    			((Model) henshin_textResource.getContents().get(0)).getTransformationsystem().addAll(resultUnitList);
          
    			//Transform henshin_text2henshin
    			URI transformationURI = URI.createURI("platform:/plugin/org.eclipse.emf.henshin.text.transformation/transforms/Henshin_text2HenshinTransformation/Henshin_text2HenshinTransformation.qvto");
    			TransformationExecutor executor = new TransformationExecutor(transformationURI);
    			ExecutionContext context = new ExecutionContextImpl();
    			ModelExtent source_HenshinText = new BasicModelExtent(henshin_textResource.getContents());		
    			ModelExtent target_Henshin = new BasicModelExtent();
    			ExecutionDiagnostic result = executor.execute(context, source_HenshinText, target_Henshin);
           
    			if(result.getSeverity() == Diagnostic.OK){
    				//Prepare for Tranformation-Dialog
    				ResourceSet resourceSetTransform = new ResourceSetImpl();
    				String uriMy=uri.toString().replace(".","_")+".henshin";
    				Resource resourceResult = resourceSetTransform.createResource(URI.createURI(uriMy));
    				resourceResult.getContents().addAll(target_Henshin.getContents());
    				List <org.eclipse.emf.henshin.model.Unit> help=orderUnits(((Module) target_Henshin.getContents().get(0)).getUnits());
    				((Module) target_Henshin.getContents().get(0)).getUnits().clear();
    				((Module) target_Henshin.getContents().get(0)).getUnits().addAll(help);
    				org.eclipse.emf.henshin.model.Unit unit=getUnitByName(((Module) target_Henshin.getContents().get(0)).getUnits(),modelElementName);
            	
 //   				try{
    					//Speicher ich .henshin
    				//	resourceResult.save(Collections.EMPTY_MAP);
//    					List<EObject> data = source_HenshinText.getContents();
//    					//Save .heninsh file
//    					Resource.Factory.Registry registry = Resource.Factory.Registry.INSTANCE;
//    					Map<String, Object> map = registry.getExtensionToFactoryMap();
//    					map.put("key", new XMIResourceFactoryImpl());
//    					ResourceSet resSet = new ResourceSetImpl();
//    					String Myuri=henshin_textResource.getURI().toString().replace(".","_")+"_transInput";
//    					Resource resourceResultMy = resSet.createResource(URI.createURI(Myuri));
//    					resourceResultMy.getContents().addAll(data);
//    					//resourceResultMy.save(Collections.EMPTY_MAP);
    					
    					
//    					//xtextResource.save(Collections.EMPTY_MAP);
//    				}catch(IOException e){
//    					// TODO Auto-generated catch block
//    					e.printStackTrace();
//    				}
            	
    				//Call Tranformation-Dialog
    				HenshinWizard tWiz = new HenshinWizard(unit);
    				HenshinWizardDialog dialog = new HenshinWizardDialog(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), tWiz);
    				dialog.open();
    			}else{
    				Status errorStatus = new Status(IStatus.ERROR, "org.eclipse.emf.henshin.text.ui", "Can`t transform "+((XtextEditor)HandlerUtil.getActiveEditor(event)).getResource().getName()+"!");
    				ErrorDialog.openError(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), "Apply Henshin Transformation", null,errorStatus);
    			}
    		}else{
    			Status errorStatus = new Status(IStatus.ERROR, "org.eclipse.emf.henshin.text.ui", "Can`t transform comments!");
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
        return null;
    }
 
	
	private String getCalledElementName(ExecutionEvent event){
		String documentString=((XtextEditor)HandlerUtil.getActiveEditor(event)).getDocument().get();
    	String eventString="";
    	String modelElementName="";
    	int lastIndex=0;
    	int eventIndex=((org.eclipse.jface.text.TextSelection)HandlerUtil.getShowInSelection(event)).getOffset();
    	if(eventIndex==documentString.length()){
    		eventIndex=eventIndex-1;
    	}
    	if((documentString.charAt(eventIndex)!='\n')&&(documentString.charAt(eventIndex)!='\r')){
    		for(int j=eventIndex;(j>=0 && j>=eventIndex-6);j--){
    			if((documentString.charAt(j)==' ')||(documentString.charAt(j)=='\n')||(documentString.charAt(j)=='\r')){
    				break;
    			}
    			eventString=documentString.charAt(j)+eventString;
    			if((documentString.charAt(j)=='r')||((documentString.charAt(j)=='u')&&(documentString.charAt(j+1)=='n'))){
    				break;
    			}
    		}
    		if(eventIndex+1<documentString.length()){
    			for(int j=eventIndex+1;j<eventIndex+6;j++){
    				lastIndex=j;
    				if((documentString.charAt(j)==' ')||(documentString.charAt(j)=='\n')||(documentString.charAt(j)=='\r')){
    					break;
    				}
    				eventString=eventString+documentString.charAt(j);
    			}
    		}
    	}
    	if(eventString.equals("rule")||eventString.equals("unit")){
    		for(int j=lastIndex+1;documentString.charAt(j)!='(';j++){
    			modelElementName=modelElementName+documentString.charAt(j);
    		}
    	}else{
    		boolean forward=false;
			IDocument editorDocument=((IDocument)((XtextEditor)HandlerUtil.getActiveEditor(event)).getDocument());
			FindReplaceDocumentAdapter helpDocumentAdapter=new FindReplaceDocumentAdapter(editorDocument);
			try {
				IRegion region=helpDocumentAdapter.find(eventIndex, "rule", false, true, true,false);
				IRegion unitRegion=helpDocumentAdapter.find(eventIndex, "unit", false, true, true,false);
				if((region==null)&&(unitRegion==null)){
					region=helpDocumentAdapter.find(eventIndex, "rule", true, true, true,false);
					unitRegion=helpDocumentAdapter.find(eventIndex, "unit", true, true, true,false);
					forward=true;
				}
				if((region==null)&&(unitRegion!=null)){
					region=unitRegion;
				}else if((region!=null)&&(unitRegion!=null)){
					int eventOffset=((org.eclipse.jface.text.TextSelection)HandlerUtil.getShowInSelection(event)).getOffset();
					if(((eventOffset-region.getOffset()>eventOffset-unitRegion.getOffset())&&(!forward))||((eventOffset-region.getOffset()<eventOffset-unitRegion.getOffset())&&(forward))){
						region=unitRegion;
					}
				}
				if(region!=null){
					for(int i=1;i<documentString.length();i++){
						if(documentString.charAt(region.getOffset()+region.getLength()+i)!='('){
							modelElementName=modelElementName+documentString.charAt(region.getOffset()+region.getLength()+i);
						}else{
							break;
						}
					}
				}
			}catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		return modelElementName;
	}
	
	
	
	
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

   private org.eclipse.emf.henshin.model.Unit getUnitByName(List<org.eclipse.emf.henshin.model.Unit> units,String name){
    	for(org.eclipse.emf.henshin.model.Unit unit:units){
    		if(unit.getName().equals(name)){
    			return unit;
    		}
    	}
    	return null;
    }
    
}


