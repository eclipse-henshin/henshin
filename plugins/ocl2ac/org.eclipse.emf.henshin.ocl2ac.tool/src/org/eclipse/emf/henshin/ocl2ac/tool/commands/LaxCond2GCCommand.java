/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.tool.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.util.InterpreterUtil;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;

import org.eclipse.emf.henshin.ocl2ac.ocl2gc.core.Completer;
import laxcondition.Condition;
import laxcondition.LaxconditionPackage;
import laxcondition.util.extensions.LaxConditionSimplifier;
import nestedcondition.NestedCondition;
import nestedcondition.NestedConstraint;
import nestedcondition.NestedconditionFactory;
import nestedcondition.Variable;
import nestedcondition.util.extensions.NestedConditionSimplifier;

public class LaxCond2GCCommand {

	private Shell shell;
	HashMap<Condition, List<String>> mapCon2Var = null;

	/**
	 * Constructor for TransformAction.
	 */
	public LaxCond2GCCommand() {
		super();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public NestedConstraint translate(Condition compactCondition) {
		mapCon2Var = new HashMap<Condition, List<String>>();
		long start = System.currentTimeMillis();
		// 0. Construct mapCon2Var
		mapCon2Var.put(compactCondition, getDistinctVariableNames(compactCondition));
		// 1. Simplify each lax condition
		simplifyLaxCondition(compactCondition);
		// 2. Complete each lax condition to NestedConstraints
		NestedConstraint nestedGraphConstraint = completeLaxConditions(mapCon2Var, compactCondition);
		// 3. Simplify each NestedConstraint
		simplifyNestedConstraints(nestedGraphConstraint);
		long stop = System.currentTimeMillis();
		long translationTime = stop - start;
		// System.out.println("[#" + translationTime + ")]");
		return nestedGraphConstraint;
	}

	/**
	 * 
	 * @param cond
	 *            of LaxconditionPackage
	 * @return
	 */
	private List<String> getDistinctVariableNames(Condition cond) {
		List<String> varNames = new ArrayList<String>();
		HenshinFactory factory = HenshinFactory.eINSTANCE;
		Rule rule = factory.createRule();
		Parameter param = factory.createParameter("p");
		rule.getParameters().add(param);
		Node sourceLHS = factory.createNode(rule.getLhs(), LaxconditionPackage.Literals.VARIABLE, "");
		sourceLHS.setName(param.getName());
		Node sourceRHS = factory.createNode(rule.getRhs(), LaxconditionPackage.Literals.VARIABLE, "");
		sourceRHS.setName(param.getName());
		Mapping sourceMapping = factory.createMapping(sourceLHS, sourceRHS);
		rule.getMappings().add(sourceMapping);

		HenshinPackage.eINSTANCE.eClass();
		EGraph graph = new EGraphImpl(cond);
		Engine engine = new EngineImpl();
		List<Match> allMatches = InterpreterUtil.findAllMatches(engine, rule, graph, null);
		for (Match m : allMatches) {
			Object parameterValue = m.getParameterValue(param);
			if (parameterValue instanceof laxcondition.Variable) {
				laxcondition.Variable v = (laxcondition.Variable) parameterValue;
				if (v.getName() != null) {
					if (!varNames.contains(v.getName())) {
						varNames.add(v.getName());
					}
				}
			}
		}

		return varNames;
	}

	/**
	 * 
	 * @param arrayListNestedConstraints
	 */
	private void simplifyNestedConstraints(NestedConstraint nestedconstrain) {
		try {
			NestedConditionSimplifier simplifierNGC = new NestedConditionSimplifier(nestedconstrain);
			boolean bc = simplifierNGC.simplify();
		} catch (Exception e) {
			System.err.println("The nestedconstrain " + nestedconstrain.getName() + " is not simplified well");
		}
	}

	/**
	 * 
	 * @param translator
	 * @param arrayListLaxConditions
	 * @return
	 */
	private NestedConstraint completeLaxConditions(HashMap<Condition, List<String>> mapCon2Var, Condition condition) {
		List<String> conditionVarNames = null;
		NestedConstraint nestedConstraint = null;
		try {
			conditionVarNames = mapCon2Var.get(condition);
			Completer completer = new Completer(condition);
			long timeNeeded = completer.complete();
			nestedConstraint = completer.getConstraint();
			NestedCondition nestedCondition = nestedConstraint.getCondition();

			if (conditionVarNames != null)
				addVariables2Condition(nestedCondition, conditionVarNames);

		} catch (Exception e) {
			System.err.println("The condition " + condition.getName() + " is not completed well");
		}

		return nestedConstraint;
	}

	/**
	 * 
	 * @param arrayListLaxConditions
	 */
	private void simplifyLaxCondition(Condition condition) {
		try {
			LaxConditionSimplifier simplifier = new LaxConditionSimplifier(condition);
			simplifier.simplify();
		} catch (Exception e) {
			System.err.println("The condition " + condition.getName() + " is not simplified well");
		}
	}

	/**
	 * Add variables to the completed nested condition
	 * 
	 * @param nestedCondition
	 * @param conditionVarNames
	 */
	private void addVariables2Condition(NestedCondition nestedCondition, List<String> conditionVarNames) {
		for (String v : conditionVarNames) {
			Variable conVar = NestedconditionFactory.eINSTANCE.createVariable();
			conVar.setName(v);
			if (!nestedCondition.getVariables().contains(conVar))
				nestedCondition.getVariables().add(conVar);
		}
	}

}
