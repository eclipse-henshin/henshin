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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResource;
import org.junit.After;

/**
 * This is a class used for quickly creating henshin tests. It features
 * convenient methods for loading henshin rules and models. Some useful
 * variables:
 * <ul>
 * <li>htTransformationSystem	-- {@link TransformationSystem}</li>
 * <li>htEngine 				-- {@link Engine}</li>
 * <li>htEmfGraph	 			-- {@link EGraph}</li>
 * <li>htRule	 				-- {@link Rule}</li>
 * <li>htRuleApp	 			-- corresponding {@link RuleApplication}</li>
 * <li>htTransUnit	 			-- {@link Unit}</li>
 * <li>htUnitApp	 			-- corresponding {@link UnitApplication}</li>
 * </ul>
 * 
 * @author Felix Rieger
 * @author Stefan Jurack (sjurack)
 */
public class HenshinTest {
	
	/**
	 * TransformationSystem which has been automatically loaded and set-up for
	 * you to use.
	 */
	protected Module htTransformationSystem; // Module
	
	/**
	 * Engine which has been automatically loaded and set-up for you to use.
	 */
	protected Engine htEngine; // Engine
	
	/**
	 * EGraph.
	 */
	protected EGraph htEGraph; // EGraph
	
	/**
	 * When calling loadRule(), this will be the loaded {@link Rule}.
	 */
	protected Rule htRule; // Rule
	
	/**
	 * When calling loadRule(), this will be the {@link RuleApplication} created
	 * from engine and the loaded {@link Rule}.
	 */
	protected RuleApplication htRuleApp; // corresponding RuleApplication
	
	/**
	 * When calling loadTu(), this will be the loaded {@link Unit}
	 */
	protected Unit htTransUnit; // Unit
	
	/**
	 * When calling loadTu(), this will be the {@link UnitApplication} created
	 * from engine and the loaded {@link Unit}.
	 */
	protected UnitApplication htUnitApp; // corresponding UnitApplication
	
	/**
	 * This gets prepended to the file name when loading a graph from file via
	 * loadGraph
	 */
	protected String graphBasePath = "";
	protected String graphFileExtension = "";
	
	@After
	public void tearDown() {
		htTransformationSystem = null;
		htEngine = null;
		htEGraph = null;	
		htRule = null;
		htRuleApp = null;
		htTransUnit = null;
		htUnitApp = null;
	}
	
	/**
	 * Initialize the tests. This should be called at the start of your JUnit
	 * setUp() method.<br />
	 * <strong>Attention:</strong> This method will not load a model, which has
	 * to be loaded manually (don't forget to set <em>htEngine</em> and
	 * <em>htEmfGraph</em> to the corresponding {@link Engine} and its
	 * {@link EGraph} as well). Otherwise, other methods will not work
	 * (especially loadRule and loadTu).<br />
	 * 
	 * @param henshinFile
	 * @throws Exception
	 */
	protected void init(String henshinFile) throws Exception {
		htTransformationSystem = (Module) HenshinLoaders.loadHenshin(henshinFile);
		htEngine = InterpreterFactory.INSTANCE.createEngine();
	}
	
	/**
	 * Initialize the tests. This should be called at the start of your JUnit
	 * setUp() method.
	 * 
	 * @param henshinFile
	 *            henshin file containing the {@link Rule}s/
	 *            {@link Unit}s to test
	 * @param modelFile
	 *            model filename
	 * @param modelFileExt
	 *            model file extension
	 * @throws Exception
	 */

	protected void init(String henshinFile, String modelFile, String modelFileExt) throws Exception {
		htTransformationSystem = (Module) HenshinLoaders.loadHenshin(henshinFile);
		htEGraph = HenshinLoaders.loadGraph(modelFile, modelFileExt);
		htEngine = InterpreterFactory.INSTANCE.createEngine();
	}
	
	/**
	 * Initialize the tests. This should be called at the start of your JUnit
	 * setUp() method.
	 * 
	 * @param henshinFile
	 *            henshin file containing the {@link Rule}s/
	 *            {@link Unit}s to test
	 * @param modelFile
	 *            model filename
	 * @param modelFileExt
	 *            model file extension
	 * @param options
	 *            Options for the engige
	 * @throws Exception
	 */

	protected void init(String henshinFile, String modelFile, String modelFileExt,
			Map<String,Object> options) throws Exception {
		init(henshinFile, modelFile, modelFileExt);
		htEngine.getOptions().putAll(options);
	}
	
	/**
	 * Initialize the tests. This should be called at the start of your JUnit
	 * setUp() method.<br />
	 * <strong>Attention:</strong> This method will not load a
	 * {@link TransformationSystem}, which has to be loaded manually (don't
	 * forget to set <em>htTransformationSystem</em> to the loaded {@link TransformationSystem}).
	 * Otherwise, other methods will not work (especially loadRule and loadTu).
	 * 
	 * @param modelFile
	 *            model filename
	 * @param modelFileExt
	 *            model file extension
	 * @throws Exception
	 */

	protected void init(String modelFile, String modelFileExt) throws Exception {
		htEGraph = HenshinLoaders.loadGraph(modelFile, modelFileExt);
		htEngine = InterpreterFactory.INSTANCE.createEngine();
	}
	
	/**
	 * Initialize the tests. This should be called at the start of your JUnit
	 * setUp() method.<br />
	 * <strong>Attention:</strong> This method will not load a
	 * {@link TransformationSystem}, which has to be loaded manually (don't
	 * forget to set <em>htTransformationSystem</em> to the loaded {@link TransformationSystem}).
	 * Otherwise, other methods will not work (especially loadRule and loadTu).
	 * 
	 * @param modelFile
	 *            model filename
	 * @param modelFileExt
	 *            model file extension
	 * @param options
	 *            {@link TransformationOptions} the {@link Engine} should use
	 * @throws Exception
	 */
	protected void init(String modelFile, String modelFileExt, Map<String,Object> options)
			throws Exception {
		htEGraph = HenshinLoaders.loadGraph(modelFile, modelFileExt);
		htEngine = InterpreterFactory.INSTANCE.createEngine();
		htEngine.getOptions().putAll(options);
	}
	
	/**
	 * Load a {@link Rule}.<br />
	 * As a side effect, th loaded Rule will be assigned to <em>htRule</em> and the
	 * corresponding RuleApplication will be assigned to <em>htRuleApp</em>.
	 * 
	 * @param ruleName
	 */
	protected void loadRule(String ruleName) {
		htRule = (Rule) htTransformationSystem.getUnit(ruleName);
		htRuleApp = InterpreterFactory.INSTANCE.createRuleApplication(htEngine);
		htRuleApp.setRule(htRule);
		htRuleApp.setEGraph(htEGraph);
	}
	
	/**
	 * Load a {@link Rule} with a parameter<br />
	 * As a side effect, th loaded Rule will be assigned to <em>htRule</em> and the
	 * corresponding RuleApplication will be assigned to <em>htRuleApp</em>.
	 * 
	 * @param ruleName
	 *            Name of the {@link Rule}
	 * @param paramName
	 *            parameter name
	 * @param paramValue
	 *            parameter value
	 */
	protected void loadRule(String ruleName, String paramName, Object paramValue) {
		loadRule(ruleName);
		htRuleApp.setParameterValue(paramName, paramValue);
	}
	
	/**
	 * Load a {@link Rule} with parameters<br />
	 * As a side effect, th loaded Rule will be assigned to <em>htRule</em> and the
	 * corresponding RuleApplication will be assigned to <em>htRuleApp</em>.
	 * 
	 * @param ruleName
	 *            Name of the {@link Rule}
	 * @param paramMappings
	 *            Map between {@link Parameter}s and Objects or Parameter names
	 *            (Strings) and Objects
	 */
	
	protected void loadRule(String ruleName, Map<String, Object> paramMappings) {
		loadRule(ruleName);
		if (paramMappings!=null) {
			for (Entry<String,Object> entry : paramMappings.entrySet()) {
				htRuleApp.setParameterValue(entry.getKey(), entry.getValue());
			}
		}
	}
	
	/**
	 * Load a {@link Unit}<br />
	 * As a side effect, the loaded Unit will be assigned to
	 * <em>htTransUnit</em> and the corresponding UnitApplication will be assigned to
	 * <em>htUnitApp</em>.
	 * 
	 * @param unitName
	 *            {@link Unit} to be loaded
	 */
	protected void loadTu(String unitName) {
		htTransUnit = htTransformationSystem.getUnit(unitName);
		htUnitApp = InterpreterFactory.INSTANCE.createUnitApplication(htEngine);
		htUnitApp.setUnit(htTransUnit);
		htUnitApp.setEGraph(htEGraph);
	}
	
	/**
	 * Load a {@link Unit} with a parameter. <br />
	 * As a side effect, the loaded Unit will be assigned to
	 * <em>htTransUnit</em> and the corresponding UnitApplication will be assigned to
	 * <em>htUnitApp</em>.
	 * 
	 * @param unitName
	 *            {@link Unit} to be loaded
	 * @param paramName
	 *            parameter name
	 * @param paramValue
	 *            parameter value
	 */
	protected void loadTu(String unitName, String paramName, Object paramValue) {
		loadTu(unitName);
		htUnitApp.setParameterValue(paramName, paramValue);
	}
	
	/**
	 * Load a transformation Unit with parameters. <br />
	 * As a side effect, the loaded Unit will be assigned to
	 * <em>htTransUnit</em> and the corresponding UnitApplication will be assigned to
	 * <em>htUnitApp</em>.
	 * 
	 * @param unitName
	 *            {@link Unit} to be loaded
	 * @param paramMappings
	 *            {@link Parameter}s
	 */
	protected void loadTu(String unitName, Map<String, Object> paramMappings) {
		loadTu(unitName);
		if (paramMappings!=null) {
			for (Entry<String,Object> entry : paramMappings.entrySet()) {
				htUnitApp.setParameterValue(entry.getKey(), entry.getValue());
			}
		}
	}
	
	/**
	 * Load a graph and create a new EmfEngine for the graph (htEngine)
	 * @param graph
	 */
	protected void loadGraph(EGraph graph) {
		htEGraph = graph;
		htEngine = InterpreterFactory.INSTANCE.createEngine();
	}
		
	/**
	 * Load a graph and create a new EmfEngine for the graph (htEngine)
	 * @param graphName		name of the graph. Loaded graph is 
	 * 						graphBasePath + graphName + "." + graphFileExtension
	 */
	protected void loadGraph(String graphName) {
		URI graphURI = URI.createFileURI(new File(graphBasePath + graphName + "."
				+ graphFileExtension).getAbsolutePath());
		loadGraph(HenshinLoaders.loadGraph(graphURI));
	}
	
	/**
	 * Set graph base path and file extension
	 * @param graphPath		Path in which all graphs can be found
	 * @param fileExtension	Common file extension for all graphs
	 */
	protected void setModelGraphProperties(String graphPath, String fileExtension) {
		graphBasePath = graphPath;
		graphFileExtension = fileExtension;
	}
	
	/**
	 * Get a graph's URI. 
	 * @param graphName	name of the graph. The returned URI is the URI for
	 * 					graphBasePath + graphName + "." + graphFileExtension
	 * @return
	 */
	protected URI getGraphURI(String graphName) {
		URI graphURI = URI.createFileURI(new File(graphBasePath + graphName + "."
				+ graphFileExtension).getAbsolutePath());
		return graphURI;
	}
	
	/**
	 * Recursively find Henshin files in a given path.
	 * @param path Path where to look for Henshin files.
	 * @return List of full paths of the found Henshin files.
	 */
	protected List<File> findHenshinFiles(File path) {
		List<File> files = new ArrayList<File>();
		addHenshinFiles(path, files);
		Collections.sort(files);
		return files;
	}
	
	/*
	 * Recursively search for Henshin files.
	 */
	private void addHenshinFiles(File file, List<File> stateSpaceFiles) {
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				addHenshinFiles(child, stateSpaceFiles);
			}
		} else {
			if (file.getName().endsWith("." + HenshinResource.FILE_EXTENSION)) {
				stateSpaceFiles.add(file);
			}
		}
	}

}
