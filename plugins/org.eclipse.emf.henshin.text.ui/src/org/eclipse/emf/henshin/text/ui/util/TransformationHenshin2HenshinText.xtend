package org.eclipse.emf.henshin.text.ui.util

import java.lang.reflect.Field
import org.eclipse.emf.common.util.Diagnostic
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.m2m.internal.qvt.oml.InternalTransformationExecutor.TracesAwareExecutor
import org.eclipse.m2m.qvt.oml.BasicModelExtent
import org.eclipse.m2m.qvt.oml.ExecutionContext
import org.eclipse.m2m.qvt.oml.ExecutionContextImpl
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic
import org.eclipse.m2m.qvt.oml.ModelExtent
import org.eclipse.m2m.qvt.oml.TransformationExecutor
import java.io.IOException

class TransformationHenshin2HenshinText {

	String qvtoPath = "platform:/plugin/org.eclipse.emf.henshin.text.transformation/transforms/Henshin_text2HenshinTransformation/Henshin2Henshin_textTransformation.qvto"

	/**
	 * Transforms a Henshin module, given as a Resource, to a Henshin_text model. Also produces a
	 * trace model from the source to the target model.
	 * 	  
	 * @param henshinResource The input model
	 * @param serialize Serialize the output and trace models automatically?
	 * @return A pair of Resources: 1. The target Henshin_text model. 2. A QVTo trace model.
	 */
	def Pair<Resource, Resource> transformHenshinToHenshin_text(Resource henshinResource, boolean serialize) {
		var URI transformationURI = URI.createURI(qvtoPath)
		var TransformationExecutor executor = new TransformationExecutor(transformationURI)
		var ExecutionContext context = new ExecutionContextImpl()
		var ModelExtent source_Henshin = new BasicModelExtent(henshinResource.getContents())
		var ModelExtent target_HenshinText = new BasicModelExtent()
		var ExecutionDiagnostic result = executor.execute(context, source_Henshin, target_HenshinText)

		if (result.getSeverity() == Diagnostic.OK) {
			var resultModel = target_HenshinText.getContents().get(0)

			var String henshinUri = henshinResource.getURI().toString() + "_text.xmi"
			var ResourceSet resourceSet = new ResourceSetImpl()
			var Resource resourceResult = resourceSet.createResource(URI.createURI(henshinUri))
			resourceResult.getContents().add(resultModel)

			var Field fExecutorField = executor.getClass().getDeclaredField("fExector")
			fExecutorField.setAccessible(true);
			var fExecutor = fExecutorField.get(executor) as TracesAwareExecutor
			var String traceUri = henshinResource.getURI().toString() + "_text.trace"
			var Resource resourceTrace = resourceSet.createResource(URI.createURI(traceUri))
			resourceTrace.getContents().addAll(fExecutor.traces)

			if (serialize) {
				try {
					resourceResult.save(null);
					resourceTrace.save(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			return new Pair(resourceResult, resourceTrace)
		} else {
			println("Not OK: " + result)
			return null
		}
	}

}
