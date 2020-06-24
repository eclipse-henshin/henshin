/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.tests.framework;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

/**
 * Methods for loading test Henshin files, models and graphs.
 * 
 * @see Tools
 * @author Felix Rieger
 * @author Stefan Jurack
 * 
 */
public class HenshinLoaders {

	/**
	 * Load a Henshin file.
	 * 
	 * @param fileName Path to the Henshin file
	 * @return Module Loaded module
	 */
	public static Module loadHenshin(String fileName) {
		HenshinResourceSet resourceSet = new HenshinResourceSet();
		return resourceSet.getModule(fileName, false);
	}
	
	/**
	 * Load a Henshin file.
	 * 
	 * @param fileName Path to the Henshin file
	 * @return Module Loaded module
	 */
	public static Module loadHenshin(String fileName, HenshinResourceSet resourceSet) {
		return resourceSet.getModule(fileName, false);
	}

	/**
	 * Load model from file and create an {@link EGraph}.
	 * 
	 * @param modelFileName Path to the model file
	 * @param modelFileExt model file extension
	 * @return EmfGraph
	 */
	public static EGraph loadGraph(String modelFileName, String modelFileExt) {
		HenshinResourceSet resourceSet = new HenshinResourceSet();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("testmodel",
				new XMIResourceFactoryImpl());
		resourceSet.registerXMIResourceFactories(modelFileExt);
		Resource resource = resourceSet.getResource(modelFileName);
		return new EGraphImpl(resource);
	}

	/**
	 * Load a graph.
	 * 
	 * @param graphUri URI to the model file
	 * @return The loaded file
	 */
	public static EGraph loadGraph(URI graphUri) {
		return loadGraph(graphUri, new HenshinResourceSet());
	}

	/**
	 * Load a graph.
	 * 
	 * @param graphUri URI to the model file
	 * @return The loaded file
	 */
	public static EGraph loadGraph(URI graphUri, HenshinResourceSet resourceSet) {
		Resource resource = resourceSet.getResource(graphUri, true);
		EGraph graph = InterpreterFactory.INSTANCE.createEGraph();
		graph.addTree(resource.getContents().get(0));
		return graph;
	}
}
