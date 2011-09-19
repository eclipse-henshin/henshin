/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.testframework;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.HenshinGraph;
import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.TransformationSystem;

/**
 * Methods for loading henshin files, models and graphs.
 * 
 * @see Tools
 * @author Felix Rieger
 * @author Stefan Jurack (sjurack)
 * 
 */
public class HenshinLoaders {
	public final static String HENSHIN_FILE_EXTENSION = "henshin";
	
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
	 * Load model from file and create an {@link EmfEngine}
	 * 
	 * @param modelFileName
	 *            Path to the model file
	 * @param modelFileExt
	 *            model file extension
	 * @return EmfEngine
	 */
	public static EmfEngine loadEngine(String modelFileName, String modelFileExt) {
		return (new EmfEngine(loadGraph(modelFileName, modelFileExt)));
	}
	
	/**
	 * Load model from file and create an {@link EmfGraph}
	 * 
	 * @param modelFileName
	 *            Path to the model file
	 * @param modelFileExt
	 *            model file extension
	 * @return EmfGraph
	 */
	public static EmfGraph loadGraph(String modelFileName, String modelFileExt) {
		ModelHelper.registerFileExtension(modelFileExt);
		EObject graphRoot = ModelHelper.loadFile(modelFileName);
		
		EmfGraph graph = new EmfGraph();
		graph.addRoot(graphRoot);
		
		return graph;
	}
	
	/**
	 * Load embedded graph from a henshin file
	 * 
	 * @param embeddedGraphName
	 *            Name of the embedded graph
	 * @param ts
	 *            {@link TransformationSystem}
	 * @return
	 */
	public static EmfGraph loadEmbeddedGraph(String embeddedGraphName, TransformationSystem ts)
			throws NullPointerException {
		HenshinGraph hgr = null;
		for (Graph g : ts.getInstances()) {
			if (g.getName().equals(embeddedGraphName)) {
				hgr = new HenshinGraph(g);
				break;
			}
		}
		
		if (hgr == null) {
			throw new NullPointerException("couldn't find embedded graph " + embeddedGraphName);
		}
		return (EmfGraph) hgr;
	}
	
	/**
	 * Load embedded graph from a henshin file
	 * 
	 * @param embeddedGraphName
	 *            Name of the embedded graph
	 * @param ts
	 *            {@link TransformationSystem}
	 * @return
	 */
	public static EmfGraph loadGraph(String embeddedGraphName, TransformationSystem ts) {
		// this method is just for having similar method names for loading
		// graphs
		return loadEmbeddedGraph(embeddedGraphName, ts);
	}
	
	public static EmfGraph loadGraph(URI graphUri) {
		Resource resourceModel = new ResourceSetImpl().getResource(graphUri, true);
		EmfGraph egr = new EmfGraph();
		egr.addRoot(resourceModel.getContents().get(0));
		return egr;
	}
	
}
