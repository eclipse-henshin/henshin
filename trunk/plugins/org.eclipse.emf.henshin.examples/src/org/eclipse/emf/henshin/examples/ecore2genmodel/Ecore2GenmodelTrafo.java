package org.eclipse.emf.henshin.examples.ecore2genmodel;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

/**
 * This implementation of an Ecore to Genmodel transformation by 
 * <a href="http://www.eclipse.org/modeling/emft/henshin/">Henshin</a> 
 * was created in the context of the 
 * <a href="http://is.ieis.tue.nl/staff/pvgorp/events/TTC2010/">Transformation Tool
 * Contest 2010</a> organized as satellite workshop to the 
 * <a href="http://malaga2010.lcc.uma.es/">TOOLS 2010</a> conference.<br>
 * Authors are (in alphabetical order):
 * <ul>
 * <li>Enrico Biermann</li>
 * <li>Claudia Ermel</li>
 * <li>Stefan Jurack</li>
 * <li>Christian Krause</li>
 * </ul>
 * 
 * <i>Remark:</i> As proof of concept only, in the following source (*.ecore) and
 * target (*.genmodel) model files are hard-coded. An adaptation to a full-fledged 
 * plug-in providing a context menu entry for Ecore files is straightforward.
 * 
 */
public class Ecore2GenmodelTrafo {

	// Base directory relative to the plug-in root:
	public static final String PATH = "src/org/eclipse/emf/henshin/examples/ecore2genmodel";

	/**
	 * Method comprising the main control flow for the transformation.
	 */
	public static void generateEcore2Genmodel(String path) {
		
		// Create a resource set:
		HenshinResourceSet resourceSet = new HenshinResourceSet(path);
		
		// Register Genmodel package (everything else is automatically registered):
		resourceSet.registerXMIResourceFactories("genmodel");
		GenModelPackage.eINSTANCE.getName();
		
		// Load the transformation system:
		TransformationSystem system = resourceSet.getTransformationSystem("Ecore2Genmodel.henshin");
		
		// Load Ecore files:
		EPackage mappingModel = (EPackage) resourceSet.getObject("ecore2gen.ecore");
		EPackage ecoreModel = (EPackage) resourceSet.getObject("flowchartdsl.ecore");

		// Create the object graph:
		EGraph graph = InterpreterFactory.INSTANCE.createEGraph();
		graph.addTree(ecoreModel);
		
		// Prepare the interpreter engine:
		Engine engine = InterpreterFactory.INSTANCE.createEngine();
		UnitApplication unitApp = InterpreterFactory.INSTANCE.createUnitApplication(engine);

		// Generate genmodel from ecore model (without annotations).
		unitApp.setEGraph(graph);
		unitApp.setUnit(system.findUnitByName("translateGenModel"));
		
		// File name and plug-in name cannot be reliably deduced by the model elements, thus need to be set:
		unitApp.setParameterValue("modelFileName", "flowchartdsl.ecore");
		unitApp.setParameterValue("pluginName", ecoreModel.getName());
		
		// Execute the transformation unit:
		if (!unitApp.execute(null)) {
			System.err.println("Error generating Genmodel");
		}
		
		// Get the generated Genmodel:
		GenModel gm = (GenModel) unitApp.getResultParameterValue("genModel");
		
		graph.addTree(system);
		graph.addTree(GenModelPackage.eINSTANCE);
		graph.addTree(mappingModel);

		// Process annotations and generate related Henshin rules:
		unitApp.setUnit(system.findUnitByName("prepareCustomizationUnit"));
		unitApp.execute(null);

		// Apply generated rules to transfer annotations to the genmodel.
		unitApp.setUnit((TransformationUnit) unitApp.getResultParameterValue("seqUnit"));
		unitApp.execute(null);

		// Save the generated Genmodel:
		resourceSet.saveObject(gm, "flowchartdsl2.genmodel");
		System.out.println("Saved the result to flowchartdsl2.genmodel");

	}

	public static void main(String[] args) {
		generateEcore2Genmodel(PATH);
	}
	
}