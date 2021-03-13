/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.utils.henshin.simplification;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.ProfilingApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.interpreter.util.InterpreterUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

public class NasHenshinCommand {

	private EObject root;
	private Engine engine;
	private EGraph graph;
	private Module grammarModule;
	private String henshinFilePathName;
	private UnitApplication mainUnitApplication;
	private ProfilingApplicationMonitor monitor;

	public NasHenshinCommand(String henshinFilePathName, EObject root) {
		this.root = root;
		this.henshinFilePathName = henshinFilePathName;
		initGrammar();
	}

	private void initGrammar() {
		// Load the transformation module
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
		URI uri = URI.createFileURI(henshinFilePathName);
		Resource resource = resourceSet.getResource(uri, true);
		grammarModule = (Module) resource.getContents().get(0);
		// Initialize the interpreter
		graph = new EGraphImpl(root);
		engine = new EngineImpl();
		engine.getOptions().put(Engine.OPTION_DETERMINISTIC, false);
	}

	public UnitApplication prepareUnitApplication(Unit unit) {
		mainUnitApplication = new UnitApplicationImpl(engine, graph, unit, null);
		return mainUnitApplication;
	}

	public boolean run(Unit unit) {
		// Initialize the transformation units
		mainUnitApplication = new UnitApplicationImpl(engine, graph, unit, null);
		// Initialize the monitor
		monitor = new ProfilingApplicationMonitor();
		return mainUnitApplication.execute(monitor);
	}

	public boolean run(UnitApplication mainUnitApplication) {
		// Initialize the monitor
		monitor = new ProfilingApplicationMonitor();
		return mainUnitApplication.execute(monitor);
	}

	public void save() {
		HenshinResourceSet hrs = new HenshinResourceSet();
		hrs.saveEObject(getRoot(), getRoot().eResource().getURI());
	}

	public List<Match> findAllMatches(Rule rule) {
		return InterpreterUtil.findAllMatches(engine, rule, graph, null);
	}

	public EObject getRoot() {
		return root;
	}

	public Module getGrammarModule() {
		return grammarModule;
	}

}
