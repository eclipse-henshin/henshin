package org.eclipse.emf.henshin.text.ui.util

import java.util.ArrayList
import org.eclipse.emf.common.util.Diagnostic
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet
import org.eclipse.emf.henshin.text.henshin_text.Model
import org.eclipse.emf.henshin.text.henshin_text.Unit
import org.eclipse.m2m.qvt.oml.BasicModelExtent
import org.eclipse.m2m.qvt.oml.ExecutionContext
import org.eclipse.m2m.qvt.oml.ExecutionContextImpl
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic
import org.eclipse.m2m.qvt.oml.ModelExtent
import org.eclipse.m2m.qvt.oml.TransformationExecutor

class Transformation {
	 /**
	  * Preparing units and call the Henshin_text to Henshin transformation
	  *
	  * @param henshin_textResource Normal case: Henshin_text Resource. Test case: null
	  * @param qvtoPath Path of the qvto transformation
	  * @return Resource of the transformed Henshin_text file
	  */
	def Resource transformHenshin_textToHenshin(Resource henshin_textResource, String qvtoPath) {
		var HenshinResourceSet henshinResourceSet = new HenshinResourceSet()

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

		var URI transformationURI = URI.createURI(qvtoPath)
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