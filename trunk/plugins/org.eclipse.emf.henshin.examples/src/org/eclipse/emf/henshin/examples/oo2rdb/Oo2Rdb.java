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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EcorePackageImpl;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * This class triggers a transformation of an Ecore model into a relational
 * (database) format. This is one example of an exogenous transformation with
 * Henshin.<br>
 * If you have questions or remarks feel free to drop a message in our developer
 * mailing list <a
 * href="mailto:henshin-dev@eclipse.org">henshin-dev@eclipse.org</a>.
 * 
 * 
 * @author Stefan Jurack (sjurack)
 * 
 */
public class Oo2Rdb extends ATrafo {
	
	public static final String PATH = "src/org/eclipse/emf/henshin/examples/oo2rdb/model/";
	public static final String ECORE_RDB = PATH + "rdb.ecore";
	public static final String HENSHIN_OO2RDB = PATH + "oo2rdb.henshin";
	
	// The OO model to be translated into an RDB schema
	public static final String ECORE_CARRENTAL = PATH + "/CarRental.ecore";
		
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.examples.oo2rdb.ATrafo#start()
	 */
	@Override
	public void start() {
		super.start();
		
		/*
		 * Register the rdb model. While Henshin is capable of dealing with
		 * absolute/relative local paths too, we strongly encourage the usage of
		 * the EMF Package Registry as a flexible and location-independent way.
		 * You may also find the "Henshin --> Register EPackages" context menu
		 * action of .ecore files in the Package Explorer useful.
		 */
		ModelHelper.registerEPackageByEcoreFile(URI.createFileURI(ECORE_RDB));
		
		performOo2RdbTransformation();
	}// start
	
	/**
	 * This method actually contains the code which triggers the transformation
	 * process.
	 */
	private void performOo2RdbTransformation() {
		
		/*
		 * TODO: I will create a more meaningful example in short terms. The
		 * present one show a lot anyhow.
		 */

		TransformationSystem ts = (TransformationSystem) loadModel(HENSHIN_OO2RDB);
		EObject rootObject = loadModel(ECORE_CARRENTAL);
		EmfGraph emfGraph = new EmfGraph();
		emfGraph.addRoot(EcorePackageImpl.eINSTANCE);
		emfGraph.addRoot(rootObject);
		EmfEngine engine = new EmfEngine(emfGraph);
		TransformationUnit unit = ts.findUnitByName("Start");
		UnitApplication unitApp = new UnitApplication(engine, unit);
		boolean success = unitApp.execute();
		if (success) {
			EObject result = (EObject) unitApp.getParameterValue("schema");
			System.out.println(result);
			saveModel(PATH + "Result.xmi", result);
		} else {
			System.err.println("Application failed!");
		}
	}// performOo2RdbTransformation
	
	/**
	 * Main.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Oo2Rdb o = new Oo2Rdb();
		o.start();
		
	}// main
	
}// class
