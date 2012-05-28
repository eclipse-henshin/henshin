package org.eclipse.emf.henshin.examples.java2statemachine;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.LoggingApplicationMonitorImpl;
import org.eclipse.emf.henshin.interpreter.util.EGraphIsomorphyChecker;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

/**
 * M2M transformation with a JaMoPP Java model as source model and a state
 * machine model as target. This is an implementation in terms of case study 1
 * of TTC2011 (http://planet-research20.org/ttc2011/index.php?option=com_content
 * &view=article&id=118&Itemid=160).
 * 
 * @author Johannes Tietje
 * @author Stefan Jurack
 * @author Christian Krause
 */
public class Java2StateMachine {
	
	// Relative path to the example files:
	public static final String PATH = "src/org/eclipse/emf/henshin/examples/java2statemachine";
	
	// The input Java models:
	public static final String JAVA_MODEL_SMALL = "1-java-model-small.xmi";
	public static final String JAVA_MODEL_MEDIUM = "2-java-model-medium.xmi";
	//public static final String JAVA_MODEL_BIG = "3-java-model-big.xmi";
	
	// The reference result for the default Java model:
	public static final String REFERENCE_STATE_MACHINE = "reference-statemachine.xmi";
	
	/**
	 * Execute the conversion.
	 * @param path Relative path to the working directory. 
	 * @param javaModel The file name of the input java model.
	 * @param referenceModel The file name of the reference state machine model.
	 */
	@SuppressWarnings("unchecked")
	public static void run(String path, String javaModel, String referenceModel) {
		
		// Create the resource set:
		System.out.println("\nLoading Java2StateMachine transformation system...");
		HenshinResourceSet resourceSet = new HenshinResourceSet(path);
		
		// Load the transformation system:
		TransformationSystem system = resourceSet.getTransformationSystem("java2statemachine.henshin");
		
		// Create a "Package" instance to store all "CompilationUnit"s
		EObject rootPackage = null;
		List<EObject> compilationUnits = null;
		for (EPackage imported : system.getImports()) {
			if ("containers".equals(imported.getName())) {
				EClass packageClass = (EClass) imported.getEClassifier("Package");
				rootPackage = imported.getEFactoryInstance().create(packageClass);
				EReference ref = (EReference) packageClass.getEStructuralFeature("compilationUnits");
				compilationUnits = (List<EObject>) rootPackage.eGet(ref);
				break;
			}
		}
		
		// Now load the Java model and add all "CompilationUnits" to the Package:
		System.out.println("Loading Java input model in '" + javaModel + "'");
		Resource javaResource = resourceSet.getResource(javaModel);
		for (EObject object : javaResource.getContents()) {
			if ("CompilationUnit".equals(object.eClass().getName())) {
				compilationUnits.add(object);
			}
		}

		// Create a graph representation of the model:
		EGraph graph = InterpreterFactory.INSTANCE.createEGraph();
		graph.addTree(rootPackage);
		System.out.println("Model has " + graph.size() + " objects");

		// Prepare the transformation engine: 
		System.out.println("Generating state machine...");
		Engine engine = InterpreterFactory.INSTANCE.createEngine();
		UnitApplication unitApp = InterpreterFactory.INSTANCE.createUnitApplication(engine);
		unitApp.setEGraph(graph);
		unitApp.setUnit(system.findUnitByName("Start"));

		// Execute the transformation:
		LoggingApplicationMonitorImpl monitor = null;
		// new LoggingApplicationMonitorImpl();
		//monitor.setOnlyUnitApplications(true);
		//monitor.setOnlyFailures(true);
		if (!unitApp.execute(monitor)) {
			System.err.println("Error transforming model");
		}

		// Save the generated state machine:
		EObject statemachine = (EObject) unitApp.getResultParameterValue("sm");
		resourceSet.saveObject(statemachine, "generated-statemachine.xmi");
		System.out.println("Saved generated state machine in 'generated-statemachine.xmi'");
		
		// Compare it with the reference:
		if (referenceModel!=null) {
			Resource reference = resourceSet.getResource("reference-statemachine.xmi");
			if (EGraphIsomorphyChecker.resourcesAreIsomorphic(reference, statemachine.eResource())) {
				System.out.println("Generated state machine is correct.");
			} else {
				throw new RuntimeException("Generated state machine is not correct!");
			}
		}
		System.out.println();
		
	}
	
	public static void main(final String[] args) {
		run(PATH, JAVA_MODEL_SMALL, REFERENCE_STATE_MACHINE);
		//run(PATH, JAVA_MODEL_MEDIUM, REFERENCE_STATE_MACHINE);
	}

}