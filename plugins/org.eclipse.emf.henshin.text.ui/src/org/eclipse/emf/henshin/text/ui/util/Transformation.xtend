package org.eclipse.emf.henshin.text.ui.util

import org.eclipse.emf.ecore.resource.Resource
import java.util.ArrayList
import org.eclipse.emf.henshin.text.henshin_text.Unit
import org.eclipse.emf.henshin.text.henshin_text.Model
import org.eclipse.emf.common.util.URI
import org.eclipse.m2m.qvt.oml.TransformationExecutor
import org.eclipse.m2m.qvt.oml.ExecutionContext
import org.eclipse.m2m.qvt.oml.ModelExtent
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic
import org.eclipse.m2m.qvt.oml.ExecutionContextImpl
import org.eclipse.m2m.qvt.oml.BasicModelExtent
import org.eclipse.emf.common.util.Diagnostic
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet
import java.io.File

import org.eclipse.emf.henshin.text.Henshin_textStandaloneSetup
import org.eclipse.emf.henshin.text.ui.util.ModifyModelUnits
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.XtextResourceSet

import com.google.inject.Injector

class Transformation {
	 /**
	  * Preparing units and call the Henshin_text to Henshin transformation
	  * 
	  * @param textResource Normal case: Henshin_text Resource. Test case: null
	  * @param qvtoPath Path of the qvto transformation
	  * @param henshin_textPath Normal case: empty String. Test case: Path of the Henshin_text file
	  * @return Resource of the transformed Henshin_text file
	  */
	def Resource transformHenshin_textToHenshin(Resource textResource,String qvtoPath,String henshin_textPath){
		var HenshinResourceSet henshinResourceSet = new HenshinResourceSet()
		var Resource henshin_textResource
		if(textResource!=null){
			henshin_textResource = textResource
		}else{
			//Load henshin_textFile standalone and imported EPackages
			new org.eclipse.emf.mwe.utils.StandaloneSetup().setPlatformUri("..")
			var Injector injector = new Henshin_textStandaloneSetup().createInjectorAndDoEMFRegistration()
			var IResourceServiceProvider serviceProvider = injector.getInstance(IResourceServiceProvider)
			IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", serviceProvider)
			var XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet)
			resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE)
			var URI henshin_textUri=org.eclipse.emf.common.util.URI.createURI("platform:/resource/org.eclipse.emf.henshin.text.transformation.tests/"+henshin_textPath)

			var String TestcasePath=henshin_textPath.replace(henshin_textUri.lastSegment(), "")
			var File[] projectFolders = new File(TestcasePath).listFiles()	
			for(var i=0;i<projectFolders.length;i++){
				var nameArray=projectFolders.get(i).getName().split("\\.")
				if(nameArray.get((nameArray.length-1)).equals("ecore")){
					resourceSet.getResource(org.eclipse.emf.common.util.URI.createURI("platform:/resource/org.eclipse.emf.henshin.text.transformation.tests/"+TestcasePath+projectFolders.get(i).getName()), true)
				}
			}
			henshin_textResource = resourceSet.getResource(henshin_textUri, true)
		}

		//Modify Units
		var ArrayList<Unit> unitList=new ArrayList<Unit>()
		var ArrayList<Unit> resultUnitList=new ArrayList<Unit>() 
		var ModifyModelUnits modifyUnit=new ModifyModelUnits(123)
		for(modelElement:(henshin_textResource.getContents().get(0) as Model).getTransformationsystem()){
			if(modelElement instanceof Unit){
				unitList.add(modelElement as Unit)	
			}
		}
		for(Unit unit:unitList){
			resultUnitList.addAll(modifyUnit.flat(unit,0,null))
		}
		(henshin_textResource.getContents().get(0) as Model).getTransformationsystem().removeAll(unitList)
		(henshin_textResource.getContents().get(0) as Model).getTransformationsystem().addAll(resultUnitList)

		var URI transformationURI = org.eclipse.emf.common.util.URI.createURI(qvtoPath)
		var TransformationExecutor executor = new TransformationExecutor(transformationURI)
		var ExecutionContext context = new ExecutionContextImpl()
		var ModelExtent source_HenshinText = new BasicModelExtent(henshin_textResource.getContents())		
		var ModelExtent target_Henshin = new BasicModelExtent()
		var ExecutionDiagnostic result = executor.execute(context, source_HenshinText, target_Henshin)
		if(result.getSeverity()==Diagnostic.OK){	
			var String henshinUri=henshin_textResource.getURI().toString().replace(".henshin_text","_henshin_text")+".henshin"
			var Resource resourceResult = henshinResourceSet.createResource(URI.createURI(henshinUri))
			resourceResult.getContents().addAll(target_Henshin.getContents())
			return resourceResult
		}else{
			return null
		}
	}
	
}