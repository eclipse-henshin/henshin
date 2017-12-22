package org.eclipse.emf.henshin.text.ui.util;

import com.google.common.base.Objects;
import com.google.inject.Injector;
import java.io.File;
import java.util.ArrayList;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.emf.henshin.text.Henshin_textStandaloneSetup;
import org.eclipse.emf.henshin.text.henshin_text.Model;
import org.eclipse.emf.henshin.text.henshin_text.ModelElement;
import org.eclipse.emf.henshin.text.henshin_text.Unit;
import org.eclipse.emf.henshin.text.ui.util.ModifyModelUnits;
import org.eclipse.emf.mwe.utils.StandaloneSetup;
import org.eclipse.m2m.qvt.oml.BasicModelExtent;
import org.eclipse.m2m.qvt.oml.ExecutionContext;
import org.eclipse.m2m.qvt.oml.ExecutionContextImpl;
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic;
import org.eclipse.m2m.qvt.oml.ModelExtent;
import org.eclipse.m2m.qvt.oml.TransformationExecutor;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

@SuppressWarnings("all")
public class Transformation {
  /**
   * Preparing units and call the Henshin_text to Henshin transformation
   * 
   * @param textResource Normal case: Henshin_text Resource. Test case: null
   * @param qvtoPath Path of the qvto transformation
   * @param henshin_textPath Normal case: empty String. Test case: Path of the Henshin_text file
   * @return Resource of the transformed Henshin_text file
   */
  public Resource transformHenshin_textToHenshin(final Resource textResource, final String qvtoPath, final String henshin_textPath) {
    HenshinResourceSet henshinResourceSet = new HenshinResourceSet();
    Resource henshin_textResource = null;
    boolean _notEquals = (!Objects.equal(textResource, null));
    if (_notEquals) {
      henshin_textResource = textResource;
    } else {
      new StandaloneSetup().setPlatformUri("..");
      Injector injector = new Henshin_textStandaloneSetup().createInjectorAndDoEMFRegistration();
      IResourceServiceProvider serviceProvider = injector.<IResourceServiceProvider>getInstance(IResourceServiceProvider.class);
      IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", serviceProvider);
      XtextResourceSet resourceSet = injector.<XtextResourceSet>getInstance(XtextResourceSet.class);
      resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
      URI henshin_textUri = URI.createURI(("platform:/resource/org.eclipse.emf.henshin.text.transformation.tests/" + henshin_textPath));
      String TestcasePath = henshin_textPath.replace(henshin_textUri.lastSegment(), "");
      File[] projectFolders = new File(TestcasePath).listFiles();
      for (int i = 0; (i < projectFolders.length); i++) {
        {
          String[] nameArray = projectFolders[i].getName().split("\\.");
          int _length = nameArray.length;
          int _minus = (_length - 1);
          boolean _equals = nameArray[_minus].equals("ecore");
          if (_equals) {
            String _name = projectFolders[i].getName();
            String _plus = (("platform:/resource/org.eclipse.emf.henshin.text.transformation.tests/" + TestcasePath) + _name);
            resourceSet.getResource(URI.createURI(_plus), true);
          }
        }
      }
      henshin_textResource = resourceSet.getResource(henshin_textUri, true);
    }
    ArrayList<Unit> unitList = new ArrayList<Unit>();
    ArrayList<Unit> resultUnitList = new ArrayList<Unit>();
    ModifyModelUnits modifyUnit = new ModifyModelUnits(123);
    EObject _get = henshin_textResource.getContents().get(0);
    EList<ModelElement> _transformationsystem = ((Model) _get).getTransformationsystem();
    for (final ModelElement modelElement : _transformationsystem) {
      if ((modelElement instanceof Unit)) {
        unitList.add(((Unit) modelElement));
      }
    }
    for (final Unit unit : unitList) {
      resultUnitList.addAll(modifyUnit.flat(unit, 0, null));
    }
    EObject _get_1 = henshin_textResource.getContents().get(0);
    ((Model) _get_1).getTransformationsystem().removeAll(unitList);
    EObject _get_2 = henshin_textResource.getContents().get(0);
    ((Model) _get_2).getTransformationsystem().addAll(resultUnitList);
    URI transformationURI = URI.createURI(qvtoPath);
    TransformationExecutor executor = new TransformationExecutor(transformationURI);
    ExecutionContext context = new ExecutionContextImpl();
    EList<EObject> _contents = henshin_textResource.getContents();
    ModelExtent source_HenshinText = new BasicModelExtent(_contents);
    ModelExtent target_Henshin = new BasicModelExtent();
    ExecutionDiagnostic result = executor.execute(context, source_HenshinText, target_Henshin);
    int _severity = result.getSeverity();
    boolean _equals = (_severity == Diagnostic.OK);
    if (_equals) {
      String _replace = henshin_textResource.getURI().toString().replace(".henshin_text", "_henshin_text");
      String henshinUri = (_replace + ".henshin");
      Resource resourceResult = henshinResourceSet.createResource(URI.createURI(henshinUri));
      resourceResult.getContents().addAll(target_Henshin.getContents());
      return resourceResult;
    } else {
      return null;
    }
  }
}
