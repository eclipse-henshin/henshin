/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.testframework;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.util.HenshinEGraph;
import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.resource.HenshinResource;

/**
 * Methods for loading henshin files, models and graphs.
 * 
 * @see Tools
 * @author Felix Rieger
 * @author Stefan Jurack (sjurack)
 * 
 */
public class HenshinLoaders {
	
	public final static String HENSHIN_FILE_EXTENSION = HenshinResource.FILE_EXTENSION;
	
	/*--------------------------------------
	 * LOADERS
	 * ------------------------------------- */

	/**
	 * Load a henshin file
	 * 
	 * @param fileName
	 *            Path to the henshin file
	 * @return EObject (TransformationSystem)
	 */
	public static TransformationSystem loadHenshin(String fileName) {
		ModelHelper.registerFileExtension(HENSHIN_FILE_EXTENSION);
		return (TransformationSystem) (ModelHelper.loadFile(fileName));
	}
	
	/**
	 * Load model from file and create an {@link EGraph}
	 * 
	 * @param modelFileName
	 *            Path to the model file
	 * @param modelFileExt
	 *            model file extension
	 * @return EmfGraph
	 */
	public static EGraph loadGraph(String modelFileName, String modelFileExt) {
		ModelHelper.registerFileExtension(modelFileExt);
		EObject graphRoot = ModelHelper.loadFile(modelFileName);
		
		EGraph graph = InterpreterFactory.INSTANCE.createEGraph();
		graph.addTree(graphRoot);
		
		return graph;
	}
	
	public static EGraph loadGraph(URI graphUri) {
		Resource resourceModel = new ResourceSetImpl().getResource(graphUri, true);
		EGraph egr = InterpreterFactory.INSTANCE.createEGraph();
		egr.addTree(resourceModel.getContents().get(0));
		return egr;
	}
	
}
