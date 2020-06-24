package org.eclipse.emf.henshin.interpreter.impl;

import org.eclipse.emf.henshin.interpreter.ApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.Assignment;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterKind;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

public class Interpreter {
	
	private Engine engine;
	private ApplicationMonitor applicationMonitor;
	protected HenshinResourceSet res;
	protected UnitApplication app;

	/**
	 * Constructs an Interpreter.
	 * @param resourcePath the path where the Resource-Files are located at.
	 */
	public Interpreter(String resourcePath) {
		res = new HenshinResourceSet(resourcePath);
		applicationMonitor = null;
		engine = new EngineImpl();
	}
	
	/**
	 * Executes a Unit with the given ParameterValues on the given Graph
	 * @param graph The Graph to execute the transformation on
	 * @param mod The Module that contains the Unit
	 * @param unit The Name of the Unit
	 * @param parameterValues List of ParameterValues using "Object..." syntax. Has to be in same order as all the "in" Parameters of the specified Unit!
	 * @return the transformed EGraph
	 */
	public EGraph executeUnit(EGraph graph, Module mod, String unitName, Object... parameterValues) {
		//Get the UnitApplication
		Unit u = mod.getUnit(unitName);
		if(u == null) {
			throw new RuntimeException("Unit: "+unitName+" doesn't exist in this Module");
		}
		app = new UnitApplicationImpl(engine);
		app.setEGraph(graph);
		app.setUnit(u);
		//Assign the Parameters
		if(app.getAssignment() == null)
		{
			app.setAssignment(InterpreterFactory.INSTANCE.createAssignment(app.getUnit(), false));
				}
		assignParameters(app.getAssignment(),parameterValues);
		//Execute the Unit.
		if(!app.execute(getApplicationMonitor())) {
			throw new RuntimeException("Failed to apply transformation");
		}
		return graph;
	}

	/**
	 * executeUnit using a Filename-String for the Graph Parameter.
	 */
	public EGraph executeUnit(String inputModelPath, Module mod, String unitName, Object... parameterValues) {
		return executeUnit(loadGraphFromFile(inputModelPath), mod, unitName, parameterValues);
	}
	
	/**
	 * executeUnit using a Filename-String for the Module Parameter.
	 */
	public EGraph executeUnit(EGraph graph, String modulePath, String unitName, Object... parameterValues) {
		return executeUnit(graph, loadModuleFromFile(modulePath), unitName, parameterValues);
	}

	/**
	 * executeUnit using a Filename-String for the Module- and Graph-Parameter.
	 */
	public EGraph executeUnit(String inputModelPath, String modulePath, String unitName, Object... parameterValues) {
		return executeUnit(loadGraphFromFile(inputModelPath), loadModuleFromFile(modulePath), unitName, parameterValues);
	}
	
	protected void assignParameters(Assignment assignment, Object[] values) {
		Unit u = assignment.getUnit();
		int inCounter = 0; //counts the Number of in-Parameters
		for(int i = 0; i<u.getParameters().size() ;i++) { 
			if(u.getParameters().get(i).getKind().equals(ParameterKind.IN) 
			|| u.getParameters().get(i).getKind().equals(ParameterKind.INOUT))
			{
				Parameter param = u.getParameters().get(i);
				//test for correct ParameterType
				
				try {
					if(!param.getType().isInstance(values[i])) {
						throw new RuntimeException("Parameter Value for "+param.getName()+" does not have the correct Type");
					}
					assignment.setParameterValue(param, values[i]);
					inCounter++;
				}
				catch(ArrayIndexOutOfBoundsException ex)
				{
					throw new RuntimeException("Too less ParameterValues were specified!");
				}
			}
			
		}
		if(inCounter < values.length)
		{
			throw new RuntimeException("Too much ParameterValues were specified!");
		}
	}

	/**
	 * Returns the Results of the specified Parameter of the last executed Unit
	 * @param the Name of the Parameter
	 * @return the Parameter Value
	 */
	public Object getResultParameterValue(String param) {
		Object res = app.getResultParameterValue(param);
		return res;
	}

	/**
	 * Saves the given EGraph into a File. Format is .xmi
	 * @param graph The EGraph to save
	 * @param filename Name of the File to save the Graph into.
	 */
	public void saveGraph(EGraph graph, String filename) {
		res.saveEObject(graph.getRoots().get(0), filename);
	}
	
	//Helper-Methods
	
	protected EGraph loadGraphFromFile(String inputModelPath) {
		return new EGraphImpl(res.getResource(inputModelPath));
	}
	
	protected Module loadModuleFromFile(String inputModulePath) {
		return res.getModule(inputModulePath);
	}

	//Getters/Setters
	
	public ApplicationMonitor getApplicationMonitor() {
		return applicationMonitor;
	}

	public void setApplicationMonitor(ApplicationMonitor applicationMonitor) {
		this.applicationMonitor = applicationMonitor;
	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;

	}

}
