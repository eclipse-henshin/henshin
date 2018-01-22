package org.eclipse.emf.henshin.text.ui.util;

import com.google.common.base.Objects;
import com.google.inject.Injector;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
      StandaloneSetup _standaloneSetup = new StandaloneSetup();
      _standaloneSetup.setPlatformUri("..");
      Henshin_textStandaloneSetup _henshin_textStandaloneSetup = new Henshin_textStandaloneSetup();
      Injector injector = _henshin_textStandaloneSetup.createInjectorAndDoEMFRegistration();
      IResourceServiceProvider serviceProvider = injector.<IResourceServiceProvider>getInstance(IResourceServiceProvider.class);
      Map<String, Object> _extensionToFactoryMap = IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap();
      _extensionToFactoryMap.put("ecore", serviceProvider);
      XtextResourceSet resourceSet = injector.<XtextResourceSet>getInstance(XtextResourceSet.class);
      resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
      URI henshin_textUri = URI.createURI(("platform:/resource/org.eclipse.emf.henshin.text.transformation.tests/" + henshin_textPath));
      String _lastSegment = henshin_textUri.lastSegment();
      String TestcasePath = henshin_textPath.replace(_lastSegment, "");
      File _file = new File(TestcasePath);
      File[] projectFolders = _file.listFiles();
      for (int i = 0; (i < projectFolders.length); i++) {
        {
          File _get = projectFolders[i];
          String _name = _get.getName();
          String[] nameArray = _name.split("\\.");
          int _length = nameArray.length;
          int _minus = (_length - 1);
          String _get_1 = nameArray[_minus];
          boolean _equals = _get_1.equals("ecore");
          if (_equals) {
            File _get_2 = projectFolders[i];
            String _name_1 = _get_2.getName();
            String _plus = (("platform:/resource/org.eclipse.emf.henshin.text.transformation.tests/" + TestcasePath) + _name_1);
            URI _createURI = URI.createURI(_plus);
            resourceSet.getResource(_createURI, true);
          }
        }
      }
      Resource _resource = resourceSet.getResource(henshin_textUri, true);
      henshin_textResource = _resource;
    }
    ArrayList<Unit> unitList = new ArrayList<Unit>();
    ArrayList<Unit> resultUnitList = new ArrayList<Unit>();
    ModifyModelUnits modifyUnit = new ModifyModelUnits(123);
    EList<EObject> _contents = henshin_textResource.getContents();
    EObject _get = _contents.get(0);
    EList<ModelElement> _transformationsystem = ((Model) _get).getTransformationsystem();
    for (final ModelElement modelElement : _transformationsystem) {
      if ((modelElement instanceof Unit)) {
        unitList.add(((Unit) modelElement));
      }
    }
    for (final Unit unit : unitList) {
      List<Unit> _flat = modifyUnit.flat(unit, 0, null);
      resultUnitList.addAll(_flat);
    }
    EList<EObject> _contents_1 = henshin_textResource.getContents();
    EObject _get_1 = _contents_1.get(0);
    EList<ModelElement> _transformationsystem_1 = ((Model) _get_1).getTransformationsystem();
    _transformationsystem_1.removeAll(unitList);
    EList<EObject> _contents_2 = henshin_textResource.getContents();
    EObject _get_2 = _contents_2.get(0);
    EList<ModelElement> _transformationsystem_2 = ((Model) _get_2).getTransformationsystem();
    _transformationsystem_2.addAll(resultUnitList);
    URI transformationURI = URI.createURI(qvtoPath);
    TransformationExecutor executor = new TransformationExecutor(transformationURI);
    ExecutionContext context = new ExecutionContextImpl();
    EList<EObject> _contents_3 = henshin_textResource.getContents();
    ModelExtent source_HenshinText = new BasicModelExtent(_contents_3);
    ModelExtent target_Henshin = new BasicModelExtent();
    ExecutionDiagnostic result = executor.execute(context, source_HenshinText, target_Henshin);
    int _severity = result.getSeverity();
    boolean _equals = (_severity == Diagnostic.OK);
    if (_equals) {
      URI _uRI = henshin_textResource.getURI();
      String _string = _uRI.toString();
      String _replace = _string.replace(".henshin_text", "_henshin_text");
      String henshinUri = (_replace + ".henshin");
      URI _createURI = URI.createURI(henshinUri);
      Resource resourceResult = henshinResourceSet.createResource(_createURI);
      EList<EObject> _contents_4 = resourceResult.getContents();
      List<EObject> _contents_5 = target_Henshin.getContents();
      _contents_4.addAll(_contents_5);
      return resourceResult;
    } else {
      return null;
    }
  }
}
