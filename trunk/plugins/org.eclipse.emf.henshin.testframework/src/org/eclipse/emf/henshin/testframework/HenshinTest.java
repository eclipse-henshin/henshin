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

import java.io.File;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.matching.util.TransformationOptions;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.junit.After;

/**
 * This is a class used for quickly creating henshin tests. It features
 * convenient methods for loading henshin rules and models. Some useful
 * variables:
 * <ul>
 * <li>ts -- {@link TransformationSystem}</li>
 * <li>engine -- {@link EmfEngine}</li>
 * <li>graph -- engine's {@link EmfGraph}</li>
 * <li>r -- {@link Rule}</li>
 * <li>ra -- corresponding {@link RuleApplication}</li>
 * <li>tu -- {@link TransformationUnit}</li>
 * <li>ua -- corresponding {@link UnitApplication}</li>
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
	protected TransformationSystem htTransformationSystem; // TransformationSystem
	/**
	 * EmfEngine which has been automatically loaded and set-up for you to use.
	 */
	protected EmfEngine htEngine; // EmfEngine
	/**
	 * engine's graph.
	 */
	protected EmfGraph htEmfGraph; // engine's EmfGraph
	
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
	 * When calling loadTu(), this will be the loaded {@link TransformationUnit}
	 * .
	 */
	protected TransformationUnit htTransUnit; // TransformationUnit
	/**
	 * When calling loadTu(), this will be the {@link UnitApplication} created
	 * from engine and the loaded {@link TransformationUnit}.
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
		htEmfGraph = null;
		
		htRule = null;
		htRuleApp = null;
		htTransUnit = null;
		htUnitApp = null;
	}
	
	/**
	 * Initialize the tests. This should be called at the start of your JUnit
	 * setUp() method.<br />
	 * <strong>Attention:</strong> This method will not load a model, which has
	 * to be loaded manually (don't forget to set <em>engine</em> and
	 * <em>graph</em> to the corresponding {@link EmfEngine} and its
	 * {@link EmfGraph} as well). Otherwise, other methods will not work
	 * (especially loadRule and loadTu).<br />
	 * 
	 * @param henshinFile
	 * @throws Exception
	 */
	protected void init(String henshinFile) throws Exception {
		htTransformationSystem = (TransformationSystem) HenshinLoaders.loadHenshin(henshinFile);
	}
	
	/**
	 * Initialize the tests. This should be called at the start of your JUnit
	 * setUp() method.
	 * 
	 * @param henshinFile
	 *            henshin file containing the {@link Rule}s/
	 *            {@link TransformationUnit}s to test
	 * @param modelFile
	 *            model filename
	 * @param modelFileExt
	 *            model file extension
	 * @throws Exception
	 */
	protected void init(String henshinFile, String modelFile, String modelFileExt) throws Exception {
		htTransformationSystem = (TransformationSystem) HenshinLoaders.loadHenshin(henshinFile);
		htEngine = HenshinLoaders.loadEngine(modelFile, modelFileExt);
		htEmfGraph = htEngine.getEmfGraph();
	}
	
	/**
	 * Initialize the tests. This should be called at the start of your JUnit
	 * setUp() method.
	 * 
	 * @param henshinFile
	 *            henshin file containing the {@link Rule}s/
	 *            {@link TransformationUnit}s to test
	 * @param modelFile
	 *            model filename
	 * @param modelFileExt
	 *            model file extension
	 * @param options
	 *            {@link TransformationOptions} the {@link EmfEngine} should use
	 * @throws Exception
	 */
	protected void init(String henshinFile, String modelFile, String modelFileExt,
			TransformationOptions options) throws Exception {
		htTransformationSystem = (TransformationSystem) HenshinLoaders.loadHenshin(henshinFile);
		htEngine = HenshinLoaders.loadEngine(modelFile, modelFileExt);
		htEngine.setOptions(options);
		htEmfGraph = htEngine.getEmfGraph();
	}
	
	/**
	 * Initialize the tests. This should be called at the start of your JUnit
	 * setUp() method.<br />
	 * <strong>Attention:</strong> This method will not load a
	 * {@link TransformationSystem}, which has to be loaded manually (don't
	 * forget to set <em>ts</em> to the loaded {@link TransformationSystem}).
	 * Otherwise, other methods will not work (especially loadRule and loadTu).
	 * 
	 * @param modelFile
	 *            model filename
	 * @param modelFileExt
	 *            model file extension
	 * @throws Exception
	 */
	protected void init(String modelFile, String modelFileExt) throws Exception {
		htEngine = HenshinLoaders.loadEngine(modelFile, modelFileExt);
		htEmfGraph = htEngine.getEmfGraph();
	}
	
	/**
	 * Initialize the tests. This should be called at the start of your JUnit
	 * setUp() method.<br />
	 * <strong>Attention:</strong> This method will not load a
	 * {@link TransformationSystem}, which has to be loaded manually (don't
	 * forget to set <em>ts</em> to the loaded {@link TransformationSystem}).
	 * Otherwise, other methods will not work (especially loadRule and loadTu).
	 * 
	 * @param modelFile
	 *            model filename
	 * @param modelFileExt
	 *            model file extension
	 * @param options
	 *            {@link TransformationOptions} the {@link EmfEngine} should use
	 * @throws Exception
	 */
	protected void init(String modelFile, String modelFileExt, TransformationOptions options)
			throws Exception {
		htEngine = HenshinLoaders.loadEngine(modelFile, modelFileExt);
		htEngine.setOptions(options);
		htEmfGraph = htEngine.getEmfGraph();
	}
	
	/**
	 * Load a {@link Rule}.<br />
	 * As a side effect, th loaded Rule will be assigned to <em>r</em> and the
	 * corresponding RuleApplication will be assigned to <em>ra</em>.
	 * 
	 * @param ruleName
	 */
	protected void loadRule(String ruleName) {
		htRule = htTransformationSystem.findRuleByName(ruleName);
		htRuleApp = new RuleApplication(htEngine, htRule);
	}
	
	/**
	 * Load a {@link Rule} with a parameter<br />
	 * As a side effect, th loaded Rule will be assigned to <em>r</em> and the
	 * corresponding RuleApplication will be assigned to <em>ra</em>.
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
	 * As a side effect, th loaded Rule will be assigned to <em>r</em> and the
	 * corresponding RuleApplication will be assigned to <em>ra</em>.
	 * 
	 * @param ruleName
	 *            Name of the {@link Rule}
	 * @param paramMappings
	 *            Map between {@link Parameter}s and Objects or Parameter names
	 *            (Strings) and Objects
	 */
	
	protected void loadRule(String ruleName, Map<String, Object> paramMappings) {
		loadRule(ruleName);
		htRuleApp.setParameterValues(paramMappings);
	}
	
	/**
	 * Load a {@link TransformationUnit}<br />
	 * As a side effect, the loaded TransformationUnit will be assigned to
	 * <em>tu</em> and the corresponding UnitApplication will be assigned to
	 * <em>ua</em>.
	 * 
	 * @param unitName
	 *            {@link TransformationUnit} to be loaded
	 */
	protected void loadTu(String unitName) {
		htTransUnit = htTransformationSystem.findUnitByName(unitName);
		htUnitApp = new UnitApplication(htEngine, htTransUnit);
	}
	
	/**
	 * Load a {@link TransformationUnit} with a parameter. <br />
	 * As a side effect, the loaded TransformationUnit will be assigned to
	 * <em>tu</em> and the corresponding UnitApplication will be assigned to
	 * <em>ua</em>.
	 * 
	 * @param unitName
	 *            {@link TransformationUnit} to be loaded
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
	 * As a side effect, the loaded TransformationUnit will be assigned to
	 * <em>tu</em> and the corresponding UnitApplication will be assigned to
	 * <em>ua</em>.
	 * 
	 * @param unitName
	 *            {@link TransformationUnit} to be loaded
	 * @param paramMappings
	 *            {@link Parameter}s
	 */
	protected void loadTu(String unitName, Map<String, Object> paramMappings) {
		loadTu(unitName);
		htUnitApp.setParameterValues(paramMappings);
	}
	
	protected void loadGraph(EmfGraph graph) {
		htEmfGraph = graph;
		htEngine = new EmfEngine(htEmfGraph);
	}
	
	protected void loadEmbeddedGraph(String embeddedGraphName) {
		loadGraph(HenshinLoaders.loadEmbeddedGraph(embeddedGraphName, htTransformationSystem));
	}
	
	protected void loadGraph(String fileName) {
		URI graphURI = URI.createFileURI(new File(graphBasePath + fileName + "."
				+ graphFileExtension).getAbsolutePath());
		loadGraph(HenshinLoaders.loadGraph(graphURI));
	}
	
	protected void setModelGraphProperties(String graphPath, String fileExtension) {
		graphBasePath = graphPath;
		graphFileExtension = fileExtension;
	}
	
	protected URI getGraphURI(String graphName) {
		URI graphURI = URI.createFileURI(new File(graphBasePath + graphName + "."
				+ graphFileExtension).getAbsolutePath());
		return graphURI;
	}
}
