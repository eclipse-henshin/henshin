package org.eclipse.emf.henshin.text.ui.util

import org.eclipse.emf.common.util.Diagnostic
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.m2m.qvt.oml.BasicModelExtent
import org.eclipse.m2m.qvt.oml.ExecutionContext
import org.eclipse.m2m.qvt.oml.ExecutionContextImpl
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic
import org.eclipse.m2m.qvt.oml.ModelExtent
import org.eclipse.m2m.qvt.oml.TransformationExecutor

class TransformationHenshin2HenshinText {
	
	/**
	 * Preparing units and call the Henshin to Henshin_text transformation
	 */
	def Resource transformHenshinToHenshin_text(Resource henshinResource, String qvtoPath) {
		var URI transformationURI = URI.createURI(qvtoPath)
		var TransformationExecutor executor = new TransformationExecutor(transformationURI)
		var ExecutionContext context = new ExecutionContextImpl()
		var ModelExtent source_Henshin = new BasicModelExtent(henshinResource.getContents())		
		var ModelExtent target_HenshinText = new BasicModelExtent()
		var ExecutionDiagnostic result = executor.execute(context, source_Henshin, target_HenshinText)
		
		if(result.getSeverity()==Diagnostic.OK){	
			var resultModel = target_HenshinText.getContents().get(0)
			println(resultModel)
			var String henshinUri=henshinResource.getURI().toString()+"_text.xmi"
			var ResourceSet resourceSet = new ResourceSetImpl()
			var Resource resourceResult = resourceSet.createResource(URI.createURI(henshinUri))
			resourceResult.getContents().add(resultModel)
			resourceResult.save(null)
		
			println(resourceResult)	
			return null
		}else{
			println("Not OK: "+result)
			return null
		}
	}

}
