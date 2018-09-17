package org.eclipse.emf.henshin.tests.basic;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.examples.bank.BankExample;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.debug.DebugValueObject;
import org.eclipse.emf.henshin.interpreter.debug.HenshinDebugValue;
import org.eclipse.emf.henshin.interpreter.debug.HenshinDebugVariable;
import org.eclipse.emf.henshin.interpreter.impl.DebugEngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.MatchImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.interpreter.info.RuleInfo;
import org.eclipse.emf.henshin.interpreter.matching.conditions.ApplicationCondition;
import org.eclipse.emf.henshin.interpreter.matching.conditions.DebugApplicationCondition.ConstraintType;
import org.eclipse.emf.henshin.interpreter.matching.conditions.DebugApplicationCondition.DebugLevel;
import org.eclipse.emf.henshin.interpreter.matching.conditions.DebugApplicationCondition.DebugState;
import org.eclipse.emf.henshin.interpreter.matching.conditions.TestDebugApplicationCondition;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.emf.henshin.tests.examples.ExamplesTest;
import org.junit.After;
import org.junit.Test;

/**
 * Test the debug behavior using the bank example. Note: in the long term it may
 * be necessary to create systematic tests that are independent from the bank
 * example
 */
public class HenshinBreakpointsTest {

	private static final String BANK_EXAMPLE_PATH = ExamplesTest.EXAMPLES_PATH + BankExample.PATH + "/";

	static TestDebugApplicationCondition debugAC;

	static HenshinResourceSet resourceSet;
	static Module module;
	static EGraph graph;
	static DebugEngineImpl engine;

	// some useful EObjects
	static EObject bank;
	static EList<?> clients;
	static EObject clientAlice;
	static EObject clientBob;
	static EObject clientCharles;
	static EObject account4;
	static EObject account2;
	static EObject account3;

	static DebugState resultCode;
	static Map<Variable, EObject> expectedMatch;

	// TODO: eklemma (code coverage)

	//////////////////////// STEP INTO
	/**
	 * Test VariableBreakpoint
	 * 
	 * @throws DebugException
	 */
	@Test
	public void testSetVariableBeakpoint() throws DebugException {
		// Initialize rule
		initRuleTransferMoneyFailure();

		// Get current stack frames
		IStackFrame[] stackFrames = debugAC.getStackFrames(null);

		// Get client variable
		HenshinDebugVariable clientHenshinDebugVariable = (HenshinDebugVariable) stackFrames[0].getVariables()[0];
		Variable clientVariable = (Variable) clientHenshinDebugVariable.getVariable();

		// Set variable breakpoint for 'Client'
		debugAC.setVariableBreakpoint(clientVariable);

		// Do the stepping necessary to check whether we stopped at the correct
		// spot or not
		debugAC.resume();

		// Get new stack frames
		IStackFrame[] newStackFrames = debugAC.getStackFrames(null);

		// Check whether correct debug state was reached by using string
		// comparisons. There we have the advantage to automatically check what
		// the GUI shows as well
		if (newStackFrames.length > 0) {
			// Get top of stack frame to see if we suspend the application at
			// the correct spot
			HenshinDebugVariable nextClientHenshinDebugVariable = (HenshinDebugVariable) newStackFrames[0]
					.getVariables()[0];
			assertEquals(true, nextClientHenshinDebugVariable.getName().equals("Variable"));
		}
	}

	/**
	 * Test ValueBreakpoint
	 * 
	 * @throws DebugException
	 */
	@Test
	public void testSetValueBeakpoint() throws DebugException {
		// Initialize rule
		initRuleTransferMoneyFailure();

		// Step into once to reach a value which we set a breakpoint for
		stepInto();

		// Get current stack frames
		IStackFrame[] stackFrames = debugAC.getStackFrames(null);

		// Get client variable
		HenshinDebugVariable var = (HenshinDebugVariable) stackFrames[1].getVariables()[0];
		HenshinDebugValue val = (HenshinDebugValue) var.getValue();

		// get index and set value breakpoint with index and value
		int index = val.getIndexInDomain();

		// Set variable breakpoint for 'Client'
		debugAC.setValueBreakpoint(val, index);

		// Terminate application to reach the specific value we set a breakpoint
		// for again
		debugAC.terminate();

		// Restart application
		initRuleTransferMoneyFailure();

		// Resume the application to stop at the breakpoint we set earlier
		debugAC.resume();

		// Get new stack frames
		IStackFrame[] newStackFrames = debugAC.getStackFrames(null);

		// Check whether correct debug state was reached by using string
		// comparisons. There we have the advantage to automatically check what
		// the GUI shows as well
		if (newStackFrames.length > 0) {
			// Get top of stack frame to see if we suspend the application at
			// the correct spot
			HenshinDebugVariable clientVariable = (HenshinDebugVariable) newStackFrames[1].getVariables()[0];
			HenshinDebugValue clientCharlesValue = (HenshinDebugValue) clientVariable.getValue();
			String clientCharlesValueString = clientCharlesValue.getValueString();

			// Check if we reached client 'Charles' again
			assertEquals(true, clientCharlesValueString.equals("Client Charles"));
		}
	}

	/**
	 * Test ConstraintTypeBreakpoint
	 * 
	 * @throws DebugException
	 */
	@Test
	public void testSetConstraintTypeBeakpoint() throws DebugException {
		// Initialize rule
		initRuleTransferMoneyFailure();

		// Step into twice to reach a constraint type we want to set a
		// breakpoint for
		stepInto();
		stepInto();

		// Get current stack frames
		IStackFrame[] stackFrames = debugAC.getStackFrames(null);

		// Get client variable
		HenshinDebugVariable var = (HenshinDebugVariable) stackFrames[2].getVariables()[0];
		DebugValueObject value = (DebugValueObject) var.getValue();

		// Set constraint type breakpoint for 'Type'
		debugAC.setConstraintTypeBreakpoint(value.getValueString().toUpperCase());

		// Resume the application to stop at the breakpoint we set earlier
		debugAC.resume();

		// Get new stack frames
		IStackFrame[] newStackFrames = debugAC.getStackFrames(null);

		// Check whether correct debug state was reached by using string
		// comparisons. There we have the advantage to automatically check what
		// the GUI shows as well
		if (newStackFrames.length > 0) {
			// Get top of stack frame to see if we suspend the application at
			// the correct spot
			HenshinDebugVariable constraintTypeVariable = (HenshinDebugVariable) newStackFrames[2].getVariables()[0];
			HenshinDebugValue constraintTypeValue = (HenshinDebugValue) constraintTypeVariable.getValue();
			String constraintTypeValueString = constraintTypeValue.getValueString();

			// Check if we reached constraint type 'Type' again
			assertEquals(true, constraintTypeValueString.equals("Type"));
		}
	}

	/**
	 * Test ConstraintTypeBreakpoint
	 * 
	 * @throws DebugException
	 */
	@Test
	public void testSetConstraintInstanceBeakpoint() throws DebugException {
		// Initialize rule
		initRuleTransferMoneyFailure();

		// Step into three times to reach a constraint instance we want to set a
		// breakpoint for
		stepInto();
		stepInto();
		stepInto();

		// Get current stack frames
		IStackFrame[] stackFrames = debugAC.getStackFrames(null);

		// Get constraint instance label variable
		HenshinDebugVariable var = (HenshinDebugVariable) stackFrames[3].getVariables()[1];
		DebugValueObject value = (DebugValueObject) var.getValue();
		String constraintInstanceValueString = value.getValueString();

		// Set constraint instance breakpoint for 'Type = Client'
		debugAC.setConstraintInstanceBreakpoint(constraintInstanceValueString);

		// Resume the application to stop at the breakpoint we set earlier
		debugAC.resume();

		// Get new stack frames
		IStackFrame[] newStackFrames = debugAC.getStackFrames(null);

		// Check whether correct debug state was reached by using string
		// comparisons. There we have the advantage to automatically check what
		// the GUI shows as well
		if (newStackFrames.length > 0) {
			// Get top of stack frame to see if we suspend the application at
			// the correct spot
			HenshinDebugVariable newVar = (HenshinDebugVariable) newStackFrames[3].getVariables()[1];
			DebugValueObject newValue = (DebugValueObject) newVar.getValue();
			String newConstraintInstanceValueString = newValue.getValueString();

			// Check if we reached constraint instance 'Type = Client' again
			assertEquals(true, newConstraintInstanceValueString.equals(constraintInstanceValueString));
		}
	}

	/**
	 * 
	 * @throws DebugException
	 */
	@Test
	public void testSetMultipleBeakpoints() throws DebugException {
		// Initialize rule
		initRuleTransferMoneyFailure();

		// Step into once to reach value 'Charles' which we set a breakpoint for
		stepInto();

		// Get current stack frames
		IStackFrame[] stackFramesCharles = debugAC.getStackFrames(null);

		// Get value 'Charles'
		HenshinDebugVariable varCharles = (HenshinDebugVariable) stackFramesCharles[1].getVariables()[0];
		HenshinDebugValue valCharles = (HenshinDebugValue) varCharles.getValue();

		// Step into once to reach value 'Bob' which we set a breakpoint for
		stepInto();

		// Get current stack frames
		IStackFrame[] stackFramesBob = debugAC.getStackFrames(null);
		
		// Get value 'Bob'
		HenshinDebugVariable varBob = (HenshinDebugVariable) stackFramesBob[1].getVariables()[0];
		HenshinDebugValue valBob = (HenshinDebugValue) varBob.getValue();

		// get index and set value breakpoint with index and value
		int indexCharles = valCharles.getIndexInDomain();
		int indexBob = valBob.getIndexInDomain();

		// Set value breakpoints for 'Charles' and 'Bob'
		debugAC.setValueBreakpoint(valCharles, indexCharles);
		debugAC.setValueBreakpoint(valBob, indexBob);

		// Terminate application to reach the specific value we set a breakpoint
		// for again
		debugAC.terminate();

		// Restart application
		initRuleTransferMoneyFailure();

		// Resume the application to stop at the breakpoint we set earlier ('Charles')
		debugAC.resume();

		// Get new stack frames
		IStackFrame[] newStackFramesCharles = debugAC.getStackFrames(null);

		// Check whether correct debug state was reached by using string
		// comparisons. There we have the advantage to automatically check what
		// the GUI shows as well
		if (newStackFramesCharles.length > 0) {
			// Get top of stack frame to see if we suspend the application at
			// the correct spot
			HenshinDebugVariable clientVariable = (HenshinDebugVariable) newStackFramesCharles[1].getVariables()[0];
			HenshinDebugValue clientCharlesValue = (HenshinDebugValue) clientVariable.getValue();
			String clientCharlesValueString = clientCharlesValue.getValueString();

			// Check if we reached client 'Charles' again
			assertEquals(true, clientCharlesValueString.equals("Client Charles"));
		}
		
		// Resume the application to stop at the breakpoint we set earlier ('Bob')
		debugAC.resume();		
		
		// Get new stack frames
		IStackFrame[] newStackFramesBob = debugAC.getStackFrames(null);

		// Check whether correct debug state was reached by using string
		// comparisons. There we have the advantage to automatically check what
		// the GUI shows as well
		if (newStackFramesBob.length > 0) {
			// Get top of stack frame to see if we suspend the application at
			// the correct spot
			HenshinDebugVariable clientVariable = (HenshinDebugVariable) newStackFramesBob[1].getVariables()[0];
			HenshinDebugValue clientCharlesValue = (HenshinDebugValue) clientVariable.getValue();
			String clientCharlesValueString = clientCharlesValue.getValueString();

			// Check if we reached client 'Bob' again
			assertEquals(true, clientCharlesValueString.equals("Client Bob"));
		}
	}

	/**
	 * 
	 * @throws DebugException
	 */
	@Test
	public void testSetNoBreakpoints() throws DebugException {
		// Initialize rule
		initRuleTransferMoneyFailure();

		// Go through whole process
		debugAC.resume();
		
		// Get current stack frames
		IStackFrame[] stackFrames = debugAC.getStackFrames(null);
		
		// There should be no stack frames because we couldn't find a match
		assertEquals(true, stackFrames.length == 0);
	}

	/**
	 * 
	 * @throws DebugException
	 */
	@Test
	public void testSetBreakpointForAnotherRule() throws DebugException {
		// Initialize rule
		initRuleTransferMoneyFailure();

		// Step into once to reach a value which we set a breakpoint for
		stepInto();

		// Get current stack frames
		IStackFrame[] stackFrames = debugAC.getStackFrames(null);

		// Get client 'Charles'
		HenshinDebugVariable var = (HenshinDebugVariable) stackFrames[1].getVariables()[0];
		HenshinDebugValue val = (HenshinDebugValue) var.getValue();

		// get index and set value breakpoint with index and value
		int index = val.getIndexInDomain();

		// Set variable breakpoint for 'Client'
		debugAC.setValueBreakpoint(val, index);

		// Terminate application to reach the specific value we set a breakpoint
		// for again
		debugAC.terminate();

		// Restart application
		initRuleTransferMoneyFailure();

		// Resume the application to stop at the breakpoint we set earlier
		debugAC.resume();

		// Get new stack frames
		IStackFrame[] newStackFrames = debugAC.getStackFrames(null);

		// Check whether correct debug state was reached by using string
		// comparisons. There we have the advantage to automatically check what
		// the GUI shows as well
		if (newStackFrames.length > 0) {
			// Get top of stack frame to see if we suspend the application at
			// the correct spot
			HenshinDebugVariable clientVariable = (HenshinDebugVariable) newStackFrames[1].getVariables()[0];
			HenshinDebugValue clientCharlesValue = (HenshinDebugValue) clientVariable.getValue();
			String clientCharlesValueString = clientCharlesValue.getValueString();

			// Check if we reached client 'Charles' again
			assertEquals(true, clientCharlesValueString.equals("Client Charles"));
		}
		
		
		// Restart the application with another rule.
		debugAC.terminate();
		
		// Init rule (success this time instrad of failure)
		initRuleTransferMoneySuccess();
		
		// Resume application
		debugAC.resume();
		
		// Get new stack frames
		IStackFrame[] newStackFramesOtherRule = debugAC.getStackFrames(null);

		// Check whether correct debug state was reached by using string
		// comparisons. There we have the advantage to automatically check what
		// the GUI shows as well
		if (newStackFramesOtherRule.length > 0) {
			// Get top of stack frame to see if we suspend the application at
			// the correct spot
			HenshinDebugVariable clientVariable = (HenshinDebugVariable) newStackFramesOtherRule[1].getVariables()[0];
			HenshinDebugValue clientCharlesValue = (HenshinDebugValue) clientVariable.getValue();
			String clientCharlesValueString = clientCharlesValue.getValueString();

			// Check if we reached client 'Charles' again
			assertEquals(true, clientCharlesValueString.equals("Client Charles"));
		}
	}
	
	//////////////////// TEARDOWN METHODS ////////////////////
	
	@After
	public void removeAllBreakpoints() {
		// Get all breakpoints
		IBreakpoint[] breakpoints = debugAC.getBreakpoints();
		// Get the breakpoint manager
		IBreakpointManager manager = debugAC.getManager();
		// Remove all breakpoints
		try {
			manager.removeBreakpoints(breakpoints, true);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//////////////////// HELPER METHODS ////////////////////

	/**
	 * Initializes the transfer money rule and the first variable for a "no
	 * match" outcome (insufficient credit)
	 */
	private void initRuleTransferMoneyFailure() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("client", "Bob");
		params.put("fromId", 2);
		params.put("toId", 3);
		params.put("amount", 300); // too much
		initRule("transferMoney", params, false);
	}

	/**
	 * Initializes the transfer money rule and the first variable for a "match
	 * found" outcome
	 */
	private void initRuleTransferMoneySuccess() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("client", "Bob");
		params.put("fromId", 2);
		params.put("toId", 3);
		params.put("amount", 50);
		initRule("transferMoney", params, true);
	}

	private void initRule(String ruleName, Map<String, Object> params, boolean compareMatch) {
		// TODO this is a lot of overhead, as initRule is called often
		{
			// Create a resource set with a base directory:
			resourceSet = new HenshinResourceSet(BANK_EXAMPLE_PATH);

			// Load the module:
			module = resourceSet.getModule("bank.henshin", false);

			// Create an engine:
			engine = new DebugEngineImpl();

			engine.getOptions().put(Engine.OPTION_DETERMINISTIC, true);
			// engine.getOptions().put(Engine.OPTION_SORT_VARIABLES, false);

			// (re)load the example model into the eGraph
			graph = new EGraphImpl(resourceSet.getResource("example-bank.xmi"));

			// initialize some useful EObjects
			bank = graph.getRoots().get(0);
			clients = (EList<?>) bank.eGet(bank.eClass().getEStructuralFeature("clients"));

			clientAlice = (EObject) clients.get(0);
			clientBob = (EObject) clients.get(1);
			clientCharles = (EObject) clients.get(2);

			EList<?> accounts = (EList<?>) bank.eGet(bank.eClass().getEStructuralFeature("accounts"));
			account2 = (EObject) accounts.get(1);
			account3 = (EObject) accounts.get(2);
			account4 = (EObject) accounts.get(3);
		}

		new UnitApplicationImpl(engine).setEGraph(graph);

		Rule rule = (Rule) module.getUnit(ruleName);
		Match partialMatch = new MatchImpl(rule);

		for (Map.Entry<String, Object> param : params.entrySet()) {
			partialMatch.setParameterValue(rule.getParameter(param.getKey()), param.getValue());
		}

		// if necessary, get the expected result to compare it later
		if (compareMatch) {
			expectedMatch = getExpectedMatch(ruleName, params);
		}

		debugAC = engine.getTestDebugApplicationCondition(rule, graph, partialMatch, null);
		initFirstVariable();
	}

	/**
	 * Creates a {@link Map} containing the match produced by the real
	 * {@link ApplicationCondition}, using the current engine, module, and
	 * graph. The unit and parameters can be specified.
	 * 
	 * @param unitName
	 *            name of the Unit to be executed
	 * @param parameters
	 *            a map containing the name and value of each parameter
	 * @return a map containing each variable and its corresponding matched
	 *         EObject from the graph
	 */
	private Map<Variable, EObject> getExpectedMatch(String unitName, Map<String, Object> parameters) {
		UnitApplicationImpl unitApplication = new UnitApplicationImpl(engine);
		unitApplication.setEGraph(graph);
		unitApplication.setUnit(module.getUnit(unitName));

		for (Map.Entry<String, Object> param : parameters.entrySet()) {
			unitApplication.setParameterValue(param.getKey(), param.getValue());
		}

		unitApplication.execute(null);

		Match match = unitApplication.getAppliedRules().get(0).getResultMatch();
		Map<Variable, EObject> objectMatch = new HashMap<Variable, EObject>();

		Rule rule = (Rule) module.getUnit("transferMoney");

		RuleInfo ruleInfo = engine.getRuleInfo(rule);

		Map<Node, Variable> nodeVarMap = ruleInfo.getVariableInfo().getNode2variable();

		for (Node node : rule.getLhs().getNodes()) {
			Node rhsNode = rule.getMappings().getImage(node, rule.getRhs());
			EObject matchedObject = match.getNodeTarget(rhsNode);
			Variable var = nodeVarMap.get(node);
			objectMatch.put(var, matchedObject);
		}

		return objectMatch;
	}

	/**
	 * Initializes the first variable. After execution, the debug state is:<br>
	 * <br>
	 * DebugLevel = DebugLevel.VARIABLE,<br>
	 * variableIndex = 0,<br>
	 * value = null,<br>
	 * constraintType = ConstraintType.NONE,<br>
	 * constraintIndex = -1
	 */
	private void initFirstVariable() {
		// state should be like this before anything happens
		assertDebugStateEquals(DebugLevel.NONE, -1, null, ConstraintType.NONE, -1);

		// init the next (i.e. the first) variable
		debugAC.initNextVariable();
		assertStepCompleted();
		assertDebugStateEquals(DebugLevel.VARIABLE, 0, null, ConstraintType.NONE, -1);
	}

	private static void stepInto() {
		try {
			debugAC.stepInto();
			assertStepCompleted();
		} catch (DebugException e) {
			e.printStackTrace();
		}
	}

	private static void stepOver() {
		try {
			debugAC.stepOver();
			assertStepCompleted();
		} catch (DebugException e) {
			e.printStackTrace();
		}
	}

	private static void stepReturn() {
		try {
			debugAC.stepReturn();
			assertStepCompleted();
		} catch (DebugException e) {
			e.printStackTrace();
		}
	}

	private static void assertStepCompleted() {
		DebugState debugState = debugAC.getCurrentDebugState();
		if (debugState != DebugState.SUSPENDED) {
			throw new AssertionError("debugState was \"" + debugState + "\", but step should have completed");
		}
	}

	private static void assertEnd(boolean matchExpected) {
		DebugState resultState = debugAC.getCurrentDebugState();

		if (matchExpected && resultState == DebugState.TERMINATED_FALSE) {
			throw new AssertionError("match was expected, but no match was found");
		}
		if (!matchExpected && resultState == DebugState.TERMINATED_TRUE) {
			throw new AssertionError("no match was expected, but match was found");
		}
	}

	private static void assertDebugStateEquals(DebugLevel debugLevel, int variableIndex, EObject value,
			ConstraintType constraintType, int constraintIndex) {
		if (!(debugAC.checkDebugState(debugLevel, variableIndex, value, constraintType, constraintIndex))) {
			throw new AssertionError("unexpected debug state:\n" + debugAC.toString());
		}
	}

	/**
	 * @param expectedDomain
	 *            the expected content of the current domain
	 */
	private void assertDomain(int variableIndex, EObject... expectedDomain) {
		DomainSlot slot = debugAC.domainMap.get(debugAC.variables.get(variableIndex));
		Object[] actualDomain = slot.getDomain().toArray();
		if (!Arrays.equals(expectedDomain, actualDomain)) {
			throw new AssertionError("unexpected domain content: " + Arrays.toString(actualDomain));
		}
	}

	/**
	 * Checks the values of the match if one was found.
	 * 
	 * @param expectedMatch
	 *            the values in the same order as the corresponding variables
	 */
	private static void assertMatch(Map<Variable, EObject> expectedMatch) {
		if (!debugAC.checkMatch(expectedMatch)) {
			throw new AssertionError("unexpected / no match");
		}
	}

}
