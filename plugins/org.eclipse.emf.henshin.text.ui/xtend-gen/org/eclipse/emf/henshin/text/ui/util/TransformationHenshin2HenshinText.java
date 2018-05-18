package org.eclipse.emf.henshin.text.ui.util;

import java.util.List;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.m2m.qvt.oml.BasicModelExtent;
import org.eclipse.m2m.qvt.oml.ExecutionContext;
import org.eclipse.m2m.qvt.oml.ExecutionContextImpl;
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic;
import org.eclipse.m2m.qvt.oml.ModelExtent;
import org.eclipse.m2m.qvt.oml.TransformationExecutor;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class TransformationHenshin2HenshinText {
  /**
   * Preparing units and call the Henshin to Henshin_text transformation
   */
  public Resource transformHenshinToHenshin_text(final Resource henshinResource, final String qvtoPath) {
    try {
      URI transformationURI = URI.createURI(qvtoPath);
      TransformationExecutor executor = new TransformationExecutor(transformationURI);
      ExecutionContext context = new ExecutionContextImpl();
      EList<EObject> _contents = henshinResource.getContents();
      ModelExtent source_Henshin = new BasicModelExtent(_contents);
      ModelExtent target_HenshinText = new BasicModelExtent();
      ExecutionDiagnostic result = executor.execute(context, source_Henshin, target_HenshinText);
      int _severity = result.getSeverity();
      boolean _equals = (_severity == Diagnostic.OK);
      if (_equals) {
        List<EObject> _contents_1 = target_HenshinText.getContents();
        EObject resultModel = _contents_1.get(0);
        InputOutput.<EObject>println(resultModel);
        URI _uRI = henshinResource.getURI();
        String _string = _uRI.toString();
        String henshinUri = (_string + "_text.xmi");
        ResourceSet resourceSet = new ResourceSetImpl();
        URI _createURI = URI.createURI(henshinUri);
        Resource resourceResult = resourceSet.createResource(_createURI);
        EList<EObject> _contents_2 = resourceResult.getContents();
        _contents_2.add(resultModel);
        resourceResult.save(null);
        InputOutput.<Resource>println(resourceResult);
        return null;
      } else {
        InputOutput.<String>println(("Not OK: " + result));
        return null;
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
