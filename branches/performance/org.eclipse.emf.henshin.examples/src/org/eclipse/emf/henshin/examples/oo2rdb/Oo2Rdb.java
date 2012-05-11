/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Philipps-University Marburg
 *******************************************************************************/
package org.eclipse.emf.henshin.examples.oo2rdb;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EcorePackageImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.model.TransformationSystem;
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
	
	public static void main(String[] args) {

		// Load the transformation and the input model:
		HenshinResourceSet resourceSet = new HenshinResourceSet("src/org/eclipse/emf/henshin/examples/oo2rdb");
		TransformationSystem trafo = resourceSet.getTransformationSystem("oo2rdb.henshin");
		Resource carRental = resourceSet.getResource("CarRental.ecore");
		
		// Initialize the Henshin graph:
		EGraph graph = InterpreterFactory.INSTANCE.createEGraph();
		graph.addTree(carRental.getContents().get(0));				
		for (EClassifier classifier : EcorePackageImpl.eINSTANCE.getEClassifiers()) {
			if (classifier instanceof EDataType) {
				graph.add(classifier);	// (we need the Ecore datatypes as well)
			}
		}
		
		// Initialize the interpreter:
		Engine engine = InterpreterFactory.INSTANCE.createEngine();
		TransformationUnit unit = trafo.findUnitByName("Start");
		UnitApplication unitApp = InterpreterFactory.INSTANCE.createUnitApplication(engine);
		unitApp.setUnit(unit);
		unitApp.setEGraph(graph);
		
		// Execute the transformation unit:
		if (unitApp.execute(null)) {
			EObject result = (EObject) unitApp.getResultParameterValue("schema");
			resourceSet.saveObject(result, "generated-result.xmi");
			System.out.println("Saved result in generated-result.xmi");
		} else {
			System.out.println("Transformation failed!");
		}
		
	}
	
}
