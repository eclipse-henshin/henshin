/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.examples.oo2rdb;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EcorePackageImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.interpreter.util.InterpreterUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

/**
 * This class triggers a transformation of an Ecore model into a relational
 * (database) format. This is one example of an exogenous transformation with
 * Henshin.
 * 
 * @author Stefan Jurack, Christian Krause
 * 
 */
public class Oo2Rdb {
	
	/**
	 * Relative path to the example files:
	 */
	public static final String PATH = "src/org/eclipse/emf/henshin/examples/oo2rdb";
	
	/**
	 * Example OO-input model:
	 */
	public static final String EXAMPLE_OO_MODEL = "CarRental.ecore";
	
	/**
	 * Example RDB-output model (reference):
	 */
	public static final String EXAMPLE_RDB_MODEL = "CarRental-reference-result.xmi";
	
	/**
	 * Run the transformation.
	 * @param path Path to the example files.
	 * @param ooModel File name of an Ecore model.
	 * @param referenceRdbModel If set, the generated result will be compared with that model (for testing).
	 * @param saveResult Whether to save the result.
	 */
	public static void run(String path, String ooModel, String referenceRdbModel, boolean saveResult) {
		
		System.out.println("Generating Rdb model for '" + ooModel + "'...");
		
		// Load the transformation module and the input model:
		HenshinResourceSet resourceSet = new HenshinResourceSet(path);
		Module module = resourceSet.getModule("oo2rdb.henshin");
		Resource carRental = resourceSet.getResource(ooModel);
		
		// Initialize the Henshin graph:
		EGraph graph = new EGraphImpl(carRental);
		for (EClassifier classifier : EcorePackageImpl.eINSTANCE.getEClassifiers()) {
			if (classifier instanceof EDataType) {
				graph.add(classifier);	// (we need the Ecore datatypes as well)
			}
		}
		
		// Initialize the interpreter:
		Engine engine = new EngineImpl();
		TransformationUnit unit = module.getTransformationUnit("Start");
		UnitApplication unitApp = new UnitApplicationImpl(engine);
		unitApp.setUnit(unit);
		unitApp.setEGraph(graph);
		
		// Execute the transformation unit:
		InterpreterUtil.executeOrDie(unitApp, null);
		EObject result = (EObject) unitApp.getResultParameterValue("schema");
		System.out.println("Generated Rdb model.");
		
		// Compare with a reference model?
		if (referenceRdbModel!=null) {
			Resource reference = resourceSet.getResource(referenceRdbModel);
			Resource generated = new ResourceImpl();
			generated.getContents().add(result);
			if (!InterpreterUtil.areIsomorphic(reference, generated)) {
				throw new AssertionError("Unexpected result for '" + ooModel + "'");
			} else {
				System.out.println("Generated result is correct.");
			}
			
		}
		
		// save the result?
		if (saveResult) {
			String resultFile = ooModel.replaceFirst(".ecore", "-generated-result.xmi");
			resourceSet.saveObject(result, resultFile);
			System.out.println("Saved result in '" + resultFile + "'");
		}
		
	}

	public static void main(String[] args) {
		run(PATH, EXAMPLE_OO_MODEL, EXAMPLE_RDB_MODEL, true);
	}

}
