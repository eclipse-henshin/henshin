package org.eclipse.emf.henshin.interpreter.impl;

import java.util.Stack;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;


/**
 * Advanced Henshin-Interpreter with debug-functionalities
 * @author Johannes Ludwig
 *
 */
public class Debugger extends Interpreter {

	private Stack<UnitApplication> done;
	private Stack<UnitApplication> undone;
	
	public Debugger(String resourcePath) {
		super(resourcePath);
		done = new Stack<UnitApplication>();
		undone = new Stack<UnitApplication>();
	}

	@Override
	public EGraph executeUnit(EGraph graph, Module mod, String unitName, Object... parameterValues) {
		EGraph res = super.executeUnit(graph, mod, unitName, parameterValues);
		 
		done.push(app);
		undone.clear();
		
		return res;
	}
	
	/**
	 * undoes the last Transformation done by this CInterpreter
	 * @return true - if the last Transformation was successfully undone. false - Otherwise
	 */
	public EGraph undo() {
		
		if(done.isEmpty())
		{
			throw new RuntimeException("No undoable Units exist");
		}
		UnitApplication u = done.pop();
		if(u.undo(getApplicationMonitor())) {
			undone.push(u);
			return u.getEGraph();
		}
		else {
			throw new RuntimeException("Failed to undo transformation");
		}
	}
	
	/**
	 * redoes the last undone Transformation undone by this CInterpreter
	 * @return true - if the Transformation was successfully redone. false - Otherwise
	 */
	public EGraph redo() {
		
		if(undone.isEmpty())
		{
			throw new RuntimeException("No redoable Units exist");
		}
		UnitApplication u = undone.pop();
		if(u.redo(getApplicationMonitor())) {
			done.push(u);
			return u.getEGraph();
		}
		else {
			throw new RuntimeException("Failed to redo transformation");
		}
	}
	
	/**
	 * Executes a Transformation-Rule on a given Match
	 * @param graph The Graph to transform
	 * @param mod The Module containing the Rule
	 * @param rule The Name of the Rule
	 * @param partial the Partial Match(can be null)
	 * @param complete the complete Match
	 * @param parameterValues the Values of the Rules' Parameters.
	 * @return true - if the Rule was successfully applied. false - Otherwise
	 */
	public EGraph executeRuleOnMatch(EGraph graph, Module mod, String ruleName, Match match, Object... parameterValues) {
		//Construct a RuleApplication
		Rule r;
		try {
			r = (Rule)mod.getUnit(ruleName);
		}
		catch(ClassCastException ex){
			throw new RuntimeException("Specified Unit is not a Rule");
		}
		if(r == null) {
			throw new RuntimeException("Specified Rule does not exist");
		}
		app = new RuleApplicationImpl(getEngine());
		app.setEGraph(graph);
		app.setUnit(r);
		//Assign the Matches.
		
		if(match != null && match.isComplete()) {

			((RuleApplication) app).setCompleteMatch(match);
			assignParameters(match,parameterValues);
		}
		else if(match != null){

			((RuleApplication) app).setPartialMatch(match);
			assignParameters(match,parameterValues);
		}
		else if(match == null) {
			((RuleApplication) app).setPartialMatch(InterpreterFactory.INSTANCE.createMatch((Rule) app.getUnit(), false));
			assignParameters(((RuleApplication) app).getPartialMatch(),parameterValues);

		}
		//Execute the Rule
		if(((RuleApplication)app).execute(getApplicationMonitor())) {
			done.push(app);
			undone.clear();
			return graph;
		}
		else {
			throw new RuntimeException("Failed to apply transformation");
		}
	}
	
	/**
	 * executeRuleOnMatch using a Filename-String for the Graph Parameter
	 */
	public EGraph executeRuleOnMatch(String inputModelPath, Module mod, String ruleName, Match match, Object... parameterValues) {
		return executeRuleOnMatch(loadGraphFromFile(inputModelPath),mod,ruleName,match,parameterValues);
	}
	
	/**
	 * executeRuleOnMatch using a Filename-String for the Module Parameter
	 */
	public EGraph executeRuleOnMatch(EGraph graph, String modulePath, String ruleName, Match match, Object... parameterValues) {
		return executeRuleOnMatch(graph,loadModuleFromFile(modulePath),ruleName,match,parameterValues);
	}
	
	/**
	 * executeRuleOnMatch using a Filename-String for the Graph- and Module-Parameter
	 */
	public EGraph executeRuleOnMatch(String inputModelPath, String modulePath, String ruleName, Match match, Object... parameterValues) {
		return executeRuleOnMatch(loadGraphFromFile(inputModelPath),loadModuleFromFile(modulePath),ruleName,match,parameterValues);
	}
	
}
