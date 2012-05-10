package org.eclipse.emf.henshin.examples.ecore2genmodel;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.codegen.ecore.genmodel.impl.GenModelPackageImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.impl.HenshinPackageImpl;
import org.eclipse.emf.henshin.model.resource.HenshinResourceFactory;

/**
 * This implementation of an Ecore to Genmodel transformation by <a
 * href="http://www.eclipse.org/modeling/emft/henshin/">Henshin</a> was created
 * along the <a
 * href="http://is.ieis.tue.nl/staff/pvgorp/events/TTC2010/">Transformation Tool
 * Contest 2010</a> organized as satellite workshop to <a
 * href="http://malaga2010.lcc.uma.es/">TOOLS 2010</a>.<br>
 * Authors are (in alphabetical order):
 * <ul>
 * <li>Enrico Biermann
 * <li>Claudia Ermel
 * <li>Stefan Jurack
 * </ul>
 * 
 * <i>Remark:</i> As proof of concept only, in the following source (.ecore) and
 * target (.gemodel) model files are hard-coded. However, an adaption to a
 * full-fledged plugin providing a context menu entry for ecore files is
 * straightforward.
 */
public class Ecore2GenmodelTrafo {

	/** Definition of a number of file paths */
	private static final String BASE = "src/org/eclipse/emf/henshin/examples/ecore2genmodel";

	/** Mapping model */
	private static final String ECORE_E2G = "ecore2gen.ecore";
	private static final String ECORE_E2G_FULL = BASE + ECORE_E2G;
	/** Henshin file containing relevant rules */
	private static final String HENSHIN_E2G_FULL = BASE
			+ "Ecore2Genmodel.henshin";
	/** Ecore source model to be transformed */
	private static final String ECORE_SOURCE = "flowchartdsl.ecore";
	private static final String ECORE_SOURCE_FULL = BASE + ECORE_SOURCE;
	/** Genmodel target model */
	private static final String GENMODEL_TARGET_FULL = BASE
			+ "flowchartdsl2.genmodel";

	/** Common resource set */
	ResourceSet resourceSet = new ResourceSetImpl();

	/**
	 * Method comprising the main control flow for the transformation.
	 */
	public void generateEcore2Genmodel() {
		
		initializeResourceFactories();

		TransformationSystem ts = (TransformationSystem) loadModel(HENSHIN_E2G_FULL);
		EPackage mappingModel = (EPackage) loadModel(ECORE_E2G_FULL);

		EPackage ecoreModel = (EPackage) loadModel(ECORE_SOURCE_FULL);

		// Create Henshin interpreter objects
		EGraph graphM = InterpreterFactory.INSTANCE.createEGraph();
		graphM.addTree(ecoreModel);
		Engine engineM = InterpreterFactory.INSTANCE.createEngine();

		// Generate genmodel from ecore model (without annotations).
		TransformationUnit unit1 = ts.findUnitByName("translateGenModel");
		UnitApplication unitApp1 = InterpreterFactory.INSTANCE.createUnitApplication(engineM);
		unitApp1.setEGraph(graphM);
		unitApp1.setUnit(unit1);
		
		// file name and plugin name cannot be reliably deduced by the model
		// elements thus need to be set.
		unitApp1.setParameterValue("modelFileName", ECORE_SOURCE);
		unitApp1.setParameterValue("pluginName", ecoreModel.getName());
		boolean result = unitApp1.execute(null);

		graphM.addTree(ts);
		graphM.addTree(GenModelPackage.eINSTANCE);
		graphM.addTree(mappingModel);

		// Process annotations and generate related Henshin rules.
		TransformationUnit unit2 = ts
				.findUnitByName("prepareCustomizationUnit");
		unitApp1.setUnit(unit2);
		unitApp1.execute(null);

		// Apply generated rules to transfer annotations to the genmodel.
		SequentialUnit customizationUnit = (SequentialUnit) unitApp1
				.getResultParameterValue("seqUnit");
		unitApp1.setUnit(customizationUnit);
		unitApp1.execute(null);

		// Save resulting genmodel.
		if (result) {
			System.out.println("Successful");
			GenModel gm = (GenModel) unitApp1.getResultParameterValue("genModel");
			saveGenModel(gm);
		} else {
			System.out.println("Not successful");
		}// if else

	}// generateEcore2Genmodel

	/**
	 * Saves the content of the genmodel to the specified file (see
	 * {@link #createGenModelResource()}).
	 * 
	 * @param gen
	 */
	private void saveGenModel(GenModel gen) {
		URI modelUri = URI.createFileURI(new File(GENMODEL_TARGET_FULL)
				.getAbsolutePath());
		Resource res = resourceSet.createResource(modelUri, "genmodel");
		try {
			res.getContents().add(gen);
			res.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}// try catch
	}// saveGenModel

	/**
	 * Loads the model at the given path and returns the root element.
	 * 
	 * @param modelPath
	 * @return
	 */
	private EObject loadModel(String modelPath) {
		URI modelUri = URI.createFileURI(new File(modelPath).getAbsolutePath());
		Resource resourceModel = resourceSet.getResource(modelUri, true);
		return resourceModel.getContents().get(0);
	}// loadEmfModel

	/**
	 * Registers appropriate resource factories for <b>ecore</b>,
	 * <b>genmodel</b> and <b>henshin</b> files.
	 */
	private void initializeResourceFactories() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
				"ecore", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
				"genmodel", new XMIResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
				"henshin", new HenshinResourceFactory());

		// Initialize packages
		GenModelPackageImpl.init();
		HenshinPackageImpl.init();
	}// initializeResourceFactories

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Ecore2GenmodelTrafo s = new Ecore2GenmodelTrafo();
		s.generateEcore2Genmodel();
	}// main

}// class
