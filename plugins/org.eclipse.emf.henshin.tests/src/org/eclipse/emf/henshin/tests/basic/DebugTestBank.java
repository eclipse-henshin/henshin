package org.eclipse.emf.henshin.tests.basic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.debug.core.DebugException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.examples.bank.BankExample;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.DebugEngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.MatchImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.interpreter.info.RuleInfo;
import org.eclipse.emf.henshin.interpreter.matching.conditions.ApplicationCondition;
import org.eclipse.emf.henshin.interpreter.matching.conditions.DebugApplicationCondition;
import org.eclipse.emf.henshin.interpreter.matching.conditions.DebugApplicationCondition.ConstraintType;
import org.eclipse.emf.henshin.interpreter.matching.conditions.DebugApplicationCondition.DebugLevel;
import org.eclipse.emf.henshin.interpreter.matching.conditions.DebugApplicationCondition.DebugState;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.emf.henshin.tests.examples.ExamplesTest;
import org.junit.Test;

/**
 * Test the debug behavior using the bank example.
 * Note: in the long term it may be necessary to create systematic tests that are independent from the bank example
 */
public class DebugTestBank {

	private static final String BANK_EXAMPLE_PATH = ExamplesTest.EXAMPLES_PATH + BankExample.PATH + "/";

	static DebugApplicationCondition debugAC;
	
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

	//////////////////////// STEP INTO
	/**
	 * calls only stepInto() until the transformation is done. Checks the state after every step.
	 * @throws DebugException 
	 */
	@Test
	public void testStepIntoFailure() throws DebugException {
		initRuleTransferMoneyFailure();
				
		// "step into" from Variable level to Value level
		stepInto();
		assertDebugStateEquals(DebugLevel.VALUE, 0, clientCharles, ConstraintType.NONE, -1);
		
		// step into to charles' first contraint type (should be the type constraint)
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientCharles, ConstraintType.TYPE, -1);
		
		// step into the first (and only) TypeConstraint, constraint index should now be 0
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 0, clientCharles, ConstraintType.TYPE, 0);
		
		// step into should now go to the attribute constraints
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientCharles, ConstraintType.ATTRIBUTE, -1);
		
		// step into the first (and only) AttributeConstraint (his name)
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 0, clientCharles, ConstraintType.ATTRIBUTE, 0);
		
		// now it should continue with the next value (Bob), because charles' attribute "name" does not match
		stepInto();
		assertDebugStateEquals(DebugLevel.VALUE, 0, clientBob, ConstraintType.NONE, -1);
		
		// step into to bob's first contraint type (should be the type constraint)
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientBob, ConstraintType.TYPE, -1);
		
		// step into the first (and only) TypeConstraint, constraint index should now be 0
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 0, clientBob, ConstraintType.TYPE, 0);
		
		// step into should now go to the attribute constraints
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientBob, ConstraintType.ATTRIBUTE, -1);
		
		// step into the first (and only) AttributeConstraint (his name)
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 0, clientBob, ConstraintType.ATTRIBUTE, 0);

		// this time it should be valid and go to the next available constraint type (ReferenceConstraint)
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientBob, ConstraintType.REFERENCE, -1);
		
		// and step into the first individual reference constraint (i.e. constraintIndex=0)
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 0, clientBob, ConstraintType.REFERENCE, 0);
		
		// the constraint should be valid and as there are no other constraints to check, the step 
		// continues to the next variable (Account)
		stepInto();
		assertDebugStateEquals(DebugLevel.VARIABLE, 1, null, ConstraintType.NONE, -1);
		
		// go to the first value
		stepInto();
		assertDebugStateEquals(DebugLevel.VALUE, 1, account2, ConstraintType.NONE, -1);
		
		// go to the first constraint type (the "type" constraint)
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 1, account2, ConstraintType.TYPE, -1);
		
		// go to the first individual constraint (index: 0)
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 1, account2, ConstraintType.TYPE, 0);
		
		// constraint valid, go to the next constraint type (attribute)
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 1, account2, ConstraintType.ATTRIBUTE, -1);
		
		// go to the first attribute constraint (id)
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 1, account2, ConstraintType.ATTRIBUTE, 0);
		
		// first constraint is valid, go to the next attribute (credit)
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 1, account2, ConstraintType.ATTRIBUTE, 1);
		
		// not enough credit => constraint invalid, go to lower index variable (client again)
		// NOTE: when coming back from a higher variable index, the old match (in this case client Bob)
		// is still stored as the current value of the domainSlot. This has to be considered when checking the debug state.
		// (Every occurrence of this is marked with the comment 'value')
		stepInto();
		assertDebugStateEquals(DebugLevel.VARIABLE, 0, clientBob, ConstraintType.NONE, -1);
		
		// go to the next & last value (alice)
		stepInto();
		assertDebugStateEquals(DebugLevel.VALUE, 0, clientAlice, ConstraintType.NONE, -1);
		
		// go to the type constraint
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientAlice, ConstraintType.TYPE, -1);
		
		// go to the first individual constraint
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 0, clientAlice, ConstraintType.TYPE, 0);
		
		// constraint valid => go to the attribute constraint
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientAlice, ConstraintType.ATTRIBUTE, -1);
		
		// go to the first attribute constraint (name)
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 0, clientAlice, ConstraintType.ATTRIBUTE, 0);
		
		// attribute constraint is not valid, and there are no other values (clients) left to check
		debugAC.stepInto();
		assertEnd(false);

		assertDebugStateEquals(DebugLevel.NONE, -1, null, ConstraintType.NONE, -1);

	}

	/**
	 * executes every Step Over transition in the negative transferMoney example.
	 * steps over all the variables first, then all the values, then all the constraint types.
	 * (The lowest level - Constraint Instances - are covered in the stepInto test)
	 * @throws DebugException 
	 */
	@Test
	public void testStepOverFailure() throws DebugException {
		// VARIABLES
		initRuleTransferMoneyFailure();
		
		stepOver();
		assertDebugStateEquals(DebugLevel.VARIABLE, 1, null, ConstraintType.NONE, -1);
		
		stepOver();
		// value
		assertDebugStateEquals(DebugLevel.VARIABLE, 0, clientBob, ConstraintType.NONE, -1);
		
		debugAC.stepOver();
		assertEnd(false);

		assertDebugStateEquals(DebugLevel.NONE, -1, null, ConstraintType.NONE, -1);
		
		// VALUES
		initRuleTransferMoneyFailure();
		
		stepInto();
		assertDebugStateEquals(DebugLevel.VALUE, 0, clientCharles, ConstraintType.NONE, -1);
		
		// no match -> go to next value
		stepOver();
		assertDebugStateEquals(DebugLevel.VALUE, 0, clientBob, ConstraintType.NONE, -1);
		
		// match -> go to next variable (index++)
		stepOver();
		assertDebugStateEquals(DebugLevel.VARIABLE, 1, null, ConstraintType.NONE, -1);
		
		stepInto();
		assertDebugStateEquals(DebugLevel.VALUE, 1, account2, ConstraintType.NONE, -1);

		// no match and no other values -> go to previous variable (index--)
		stepOver();
		// value
		assertDebugStateEquals(DebugLevel.VARIABLE, 0, clientBob, ConstraintType.NONE, -1);

		stepInto();
		assertDebugStateEquals(DebugLevel.VALUE, 0, clientAlice, ConstraintType.NONE, -1);
		
		// last value is also invalid -> should return end_false (i.e. no match found)
		debugAC.stepOver();
		assertEnd(false);
		
		assertDebugStateEquals(DebugLevel.NONE, -1, null, ConstraintType.NONE, -1);
		
		// CONSTRAINT TYPES
		initRuleTransferMoneyFailure();
				
		// "step into" from Variable level to Value level
		stepInto();
		assertDebugStateEquals(DebugLevel.VALUE, 0, clientCharles, ConstraintType.NONE, -1);
		
		// step into to charles' first contraint type (should be the type constraint)
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientCharles, ConstraintType.TYPE, -1);
		
		// step over to the attribute constraint type
		stepOver();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientCharles, ConstraintType.ATTRIBUTE, -1);
		
		// no match, and no constraint types left -> go to next value (bob)
		stepOver();
		assertDebugStateEquals(DebugLevel.VALUE, 0, clientBob, ConstraintType.NONE, -1);
		
		// step into bob's constraint type
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientBob, ConstraintType.TYPE, -1);
		
		// step over to the next constraint type (attribute)
		stepOver();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientBob, ConstraintType.ATTRIBUTE, -1);
		
		// step over to the next constraint type (Reference)
		stepOver();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientBob, ConstraintType.REFERENCE, -1);
		
		// reference constraint matches -> go to next variable
		stepOver();
		assertDebugStateEquals(DebugLevel.VARIABLE, 1, null, ConstraintType.NONE, -1);
		
		// go to the first value
		stepInto();
		assertDebugStateEquals(DebugLevel.VALUE, 1, account2, ConstraintType.NONE, -1);
		
		// go to the first constraint type (the "type" constraint)
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 1, account2, ConstraintType.TYPE, -1);
		
		// step over to the next constraint type (attribute)
		stepOver();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 1, account2, ConstraintType.ATTRIBUTE, -1);
		
		// step over, attribute "credit" does not match -> go to previous variable
		stepOver();
		// value
		assertDebugStateEquals(DebugLevel.VARIABLE, 0, clientBob, ConstraintType.NONE, -1);
		
		stepInto(); // go to alice
		stepInto(); // go to alice's first constraint type
		
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientAlice, ConstraintType.TYPE, -1);
		
		stepOver();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientAlice, ConstraintType.ATTRIBUTE, -1);
		
		debugAC.stepOver();
		assertEnd(false);
		assertDebugStateEquals(DebugLevel.NONE, -1, null, ConstraintType.NONE, -1);
	}	
	
		
	/**
	 * executes every possible stepReturn in the negative tranferMoney example
	 * and checks whether the debug state changes correctly after each step
	 * @throws DebugException 
	 */
	@Test
	public void testStepReturnFailure() throws DebugException {
		initRuleTransferMoneyFailure();
		stepReturn();
		assertDebugStateEquals(DebugLevel.VARIABLE, 1, null, ConstraintType.NONE, -1);
		
		initRuleTransferMoneyFailure();
		stepInto(); // value charles
		assertDebugStateEquals(DebugLevel.VALUE, 0, clientCharles, ConstraintType.NONE, -1);		
		stepReturn();
		assertDebugStateEquals(DebugLevel.VARIABLE, 1, null, ConstraintType.NONE, -1);
		
		initRuleTransferMoneyFailure();
		stepInto();
		stepInto(); // charles' "type" constraint type
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientCharles, ConstraintType.TYPE, -1);
		stepReturn();
		assertDebugStateEquals(DebugLevel.VALUE, 0, clientBob, ConstraintType.NONE, -1);		
		
		initRuleTransferMoneyFailure();
		stepInto();
		stepInto();
		stepInto(); // charles' first "type" constraint
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 0, clientCharles, ConstraintType.TYPE, 0);
		stepReturn();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientCharles, ConstraintType.ATTRIBUTE, -1);
		
		// charles' attribute constraint type
		stepReturn();
		assertDebugStateEquals(DebugLevel.VALUE, 0, clientBob, ConstraintType.NONE, -1);
		
		initRuleTransferMoneyFailure();
		stepInto();
		stepInto();
		stepOver();
		stepInto(); // charles' first attribute constraint
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 0, clientCharles, ConstraintType.ATTRIBUTE, 0);
		stepReturn();
		assertDebugStateEquals(DebugLevel.VALUE, 0, clientBob, ConstraintType.NONE, -1);
		
		// value bob
		stepReturn();
		assertDebugStateEquals(DebugLevel.VARIABLE, 1, null, ConstraintType.NONE, -1);
		
		initRuleTransferMoneyFailure();
		stepInto();
		stepOver();
		stepInto(); // bobs "type" constraint type
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientBob, ConstraintType.TYPE, -1);
		stepReturn();
		assertDebugStateEquals(DebugLevel.VARIABLE, 1, null, ConstraintType.NONE, -1);
		
		initRuleTransferMoneyFailure();
		stepInto();
		stepOver();
		stepInto();
		stepInto(); // bobs first "type" constraint
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 0, clientBob, ConstraintType.TYPE, 0);
		stepReturn();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientBob, ConstraintType.ATTRIBUTE, -1);
		
		// bobs "attribute" constraint type
		stepReturn();
		assertDebugStateEquals(DebugLevel.VARIABLE, 1, null, ConstraintType.NONE, -1);

		initRuleTransferMoneyFailure();
		stepInto();
		stepOver();
		stepInto();
		stepOver();
		stepInto(); // bobs first attribute constraint
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 0, clientBob, ConstraintType.ATTRIBUTE, 0);
		stepReturn();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientBob, ConstraintType.REFERENCE, -1);
		
		// bobs "reference" constraint type
		stepReturn();
		assertDebugStateEquals(DebugLevel.VARIABLE, 1, null, ConstraintType.NONE, -1);
		
		initRuleTransferMoneyFailure();
		stepInto();
		stepOver();
		stepInto();
		stepOver();
		stepOver();
		stepInto(); // bobs first reference constraint (accounts)
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 0, clientBob, ConstraintType.REFERENCE, 0);
		stepReturn();
		assertDebugStateEquals(DebugLevel.VARIABLE, 1, null, ConstraintType.NONE, -1);
		
		// step return from variable "account" (index 1)
		stepReturn();
		assertDebugStateEquals(DebugLevel.VARIABLE, 0, clientBob, ConstraintType.NONE, -1);
		
		initRuleTransferMoneyFailure();
		stepOver();
		stepInto();
		assertDebugStateEquals(DebugLevel.VALUE, 1, account2, ConstraintType.NONE, -1);
		stepReturn();
		// value
		assertDebugStateEquals(DebugLevel.VARIABLE, 0, clientBob, ConstraintType.NONE, -1);

		initRuleTransferMoneyFailure();
		stepOver();
		stepInto();
		stepInto(); // account2's "type" constraint type
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 1, account2, ConstraintType.TYPE, -1);
		stepReturn();
		// value
		assertDebugStateEquals(DebugLevel.VARIABLE, 0, clientBob, ConstraintType.NONE, -1);
		
		initRuleTransferMoneyFailure();
		stepOver();
		stepInto();
		stepInto();
		stepInto(); // account2's first "type" constraint
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 1, account2, ConstraintType.TYPE, 0);
		stepReturn();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 1, account2, ConstraintType.ATTRIBUTE, -1);
		
		// account2's "attribute" constraint type
		stepReturn();
		// value
		assertDebugStateEquals(DebugLevel.VARIABLE, 0, clientBob, ConstraintType.NONE, -1);
		
		initRuleTransferMoneyFailure();
		stepOver();
		stepInto();
		stepInto();
		stepOver();
		stepInto(); // account2's first "attribute" constraint (id)
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 1, account2, ConstraintType.ATTRIBUTE, 0);
		stepReturn();
		// value
		assertDebugStateEquals(DebugLevel.VARIABLE, 0, clientBob, ConstraintType.NONE, -1);
		
		initRuleTransferMoneyFailure();
		stepOver();
		stepInto();
		stepInto();
		stepOver();
		stepInto();
		stepOver(); // account2's second "attribute" constraint (credit)
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 1, account2, ConstraintType.ATTRIBUTE, 1);
		stepReturn();
		// value
		assertDebugStateEquals(DebugLevel.VARIABLE, 0, clientBob, ConstraintType.NONE, -1);
		
		// variable "client" (index 0)
		debugAC.stepReturn();
		assertEnd(false);
		assertDebugStateEquals(DebugLevel.NONE, -1, null, ConstraintType.NONE, -1);
		
		initRuleTransferMoneyFailure();
		stepOver();
		stepOver();
		stepInto(); // value alice
		assertDebugStateEquals(DebugLevel.VALUE, 0, clientAlice, ConstraintType.NONE, -1);
		debugAC.stepReturn();
		assertEnd(false);
		assertDebugStateEquals(DebugLevel.NONE, -1, null, ConstraintType.NONE, -1);
		
		initRuleTransferMoneyFailure();
		stepOver();
		stepOver();
		stepInto();
		stepInto(); // alice's "type" constraint type
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientAlice, ConstraintType.TYPE, -1);
		debugAC.stepReturn();
		assertEnd(false);
		assertDebugStateEquals(DebugLevel.NONE, -1, null, ConstraintType.NONE, -1);
		
		initRuleTransferMoneyFailure();
		stepOver();
		stepOver();
		stepInto();
		stepInto();
		stepInto(); // alice's first "type" constraint
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 0, clientAlice, ConstraintType.TYPE, 0);
		stepReturn();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 0, clientAlice, ConstraintType.ATTRIBUTE, -1);
		
		// alice's "attribute" constraint type
		debugAC.stepReturn();
		assertEnd(false);
		assertDebugStateEquals(DebugLevel.NONE, -1, null, ConstraintType.NONE, -1);
		
		initRuleTransferMoneyFailure();
		stepOver();
		stepOver();
		stepInto();
		stepInto();
		stepOver();
		stepInto(); // alice's first "attribute" constraint
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 0, clientAlice, ConstraintType.ATTRIBUTE, 0);
		debugAC.stepReturn();
		assertEnd(false);
		assertDebugStateEquals(DebugLevel.NONE, -1, null, ConstraintType.NONE, -1);

	}
	
	/**
	 * Checks the stepInto transitions in the positive transferMoney example, starting at the point
	 * where account2's credit (which is now sufficient) is examined (the other transitions are the
	 * same as in {@link #testStepIntoFailure()}
	 * @throws DebugException 
	 */
	@Test
	public void testStepIntoSuccess() throws DebugException {
		initRuleTransferMoneySuccess();
		stepOver();
		stepInto();
		stepInto();
		stepOver();
		stepInto();
		stepOver();
		// now we are at the "credit" constraint of the account 2
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 1, account2, ConstraintType.ATTRIBUTE, 1);
		
		// go to account2's reference constraint
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 1, account2, ConstraintType.REFERENCE, -1);
		
		// go to the first reference constraint (reference to 'client')
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 1, account2, ConstraintType.REFERENCE, 0);
		
		// constraint is valid, and there are no other constraints.
		// => go to next variable (Account accountTo)
		stepInto();
		assertDebugStateEquals(DebugLevel.VARIABLE, 2, null, ConstraintType.NONE, -1);
		
		// go to the first receiver account (account4)
		stepInto();
		assertDebugStateEquals(DebugLevel.VALUE, 2, account4, ConstraintType.NONE, -1);
		
		// go to account4's "type" constrain type
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 2, account4, ConstraintType.TYPE, -1);
		
		// account4's first type constraint
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 2, account4, ConstraintType.TYPE, 0);
		
		// go to the attribute constraints
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 2, account4, ConstraintType.ATTRIBUTE, -1);
		
		// account4's first attribute constraint (id)
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 2, account4, ConstraintType.ATTRIBUTE, 0);
		
		// id mismatch => next value (account3)
		stepInto();
		assertDebugStateEquals(DebugLevel.VALUE, 2, account3, ConstraintType.NONE, -1);
		
		// account3's "type" constraint type
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 2, account3, ConstraintType.TYPE, -1);
		
		// account3's first type constraint
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 2, account3, ConstraintType.TYPE, 0);
		
		// go to the attribute constraints
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 2, account3, ConstraintType.ATTRIBUTE, -1);
		
		// account3's first attribute constraint (id)
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 2, account3, ConstraintType.ATTRIBUTE, 0);
		
		// id matches => go to next attribute constraint (credit)
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 2, account3, ConstraintType.ATTRIBUTE, 1);
		
		// credit matches => finished (success)
		debugAC.stepInto();
		assertEnd(true);
		assertDebugStateEquals(DebugLevel.VARIABLE, 2, account3, ConstraintType.NONE, -1);
		
		// check the matched values of the Domain Slots
//		assertMatch(Arrays.asList(clientBob, account2, account3));
		assertMatch(expectedMatch);	
	}
	
	/**
	 * executes every Step Over transition in the 'successful' transferMoney example.
	 * steps over all the variables first, then all the values, then all the constraint types.
	 * @throws DebugException 
	 */
	@Test
	public void testStepOverSuccess() throws DebugException {
		// VARIABLES
		initRuleTransferMoneySuccess();
		stepOver(); // step over the client
		assertDebugStateEquals(DebugLevel.VARIABLE, 1, null, ConstraintType.NONE, -1);
		stepOver(); // step over the sender account
		assertDebugStateEquals(DebugLevel.VARIABLE, 2, null, ConstraintType.NONE, -1);
		debugAC.stepOver(); // step over the receiver account
		assertEnd(true);
		
		// VALUES
		initRuleTransferMoneySuccess();
		
		stepInto();
		assertDebugStateEquals(DebugLevel.VALUE, 0, clientCharles, ConstraintType.NONE, -1);
		
		// no match -> go to next value
		stepOver();
		assertDebugStateEquals(DebugLevel.VALUE, 0, clientBob, ConstraintType.NONE, -1);
		
		// match -> go to next variable ('fromAccount')
		stepOver();
		assertDebugStateEquals(DebugLevel.VARIABLE, 1, null, ConstraintType.NONE, -1);
		
		stepInto();
		assertDebugStateEquals(DebugLevel.VALUE, 1, account2, ConstraintType.NONE, -1);
		
		// match -> next variable ('toAccount')
		stepOver();
		assertDebugStateEquals(DebugLevel.VARIABLE, 2, null, ConstraintType.NONE, -1);
		
		stepInto();
		assertDebugStateEquals(DebugLevel.VALUE, 2, account4, ConstraintType.NONE, -1);
		
		// no match -> next value
		stepOver();
		assertDebugStateEquals(DebugLevel.VALUE, 2, account3, ConstraintType.NONE, -1);

		// match -> terminates successfully
		debugAC.stepOver();
		assertEnd(true);
		
		// CONSTRAINT TYPES
		initRuleTransferMoneySuccess();
		stepOver();
		stepInto();
		stepInto();
		stepOver();
		// now we are at the "attribute" constraint type of the account 2
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 1, account2, ConstraintType.ATTRIBUTE, -1);
		// all attribute constraints are valid => go to the reference constraints
		stepOver();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 1, account2, ConstraintType.REFERENCE, -1);
		
		// reference constraint is valid & there are no other constraints
		// => go to next variable (receiver account)
		stepOver();
		assertDebugStateEquals(DebugLevel.VARIABLE, 2, null, ConstraintType.NONE, -1);
		
		stepInto();
		stepInto();
		// now we are at the "type" constraint type of account 4
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 2, account4, ConstraintType.TYPE, -1);
		
		stepOver();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 2, account4, ConstraintType.ATTRIBUTE, -1);
		
		stepOver();
		assertDebugStateEquals(DebugLevel.VALUE, 2, account3, ConstraintType.NONE, -1);
		
		stepInto();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 2, account3, ConstraintType.TYPE, -1);
		
		stepOver();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 2, account3, ConstraintType.ATTRIBUTE, -1);
		
		debugAC.stepOver();
		assertEnd(true);
		assertDebugStateEquals(DebugLevel.VARIABLE, 2, account3, ConstraintType.NONE, -1);

		// check the matched values of the Domain Slots
//		assertMatch(Arrays.asList(clientBob, account2, account3));
		assertMatch(expectedMatch);	
	}
	
	/**
	 * checks the STEP RETURN transitions starting at the "attribute" 
	 * constraint type of the sender account (account2).
	 * @throws DebugException 
	 */
	@Test
	public void testStepReturnSuccess() throws DebugException {
		initRuleTransferMoneySuccess();
		stepOver();
		stepInto();
		stepInto();
		stepOver(); // account2's "attribute" constraint type
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 1, account2, ConstraintType.ATTRIBUTE, -1);
		stepReturn();
		assertDebugStateEquals(DebugLevel.VARIABLE, 2, null, ConstraintType.NONE, -1);
		
		debugAC.stepReturn();
		assertEnd(true);
		
		initRuleTransferMoneySuccess();
		stepOver();
		stepInto();
		stepInto();
		stepOver();
		stepInto(); // account2's first "attribute" constraint (id)
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 1, account2, ConstraintType.ATTRIBUTE, 0);
		stepReturn();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 1, account2, ConstraintType.REFERENCE, -1);
		
		initRuleTransferMoneySuccess();
		stepOver();
		stepInto();
		stepInto();
		stepOver();
		stepInto();
		stepOver(); // account2's second "attribute" constraint (credit)
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 1, account2, ConstraintType.ATTRIBUTE, 1);
		stepReturn();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 1, account2, ConstraintType.REFERENCE, -1);
		
		// account2's "reference" constraint type
		stepReturn();
		assertDebugStateEquals(DebugLevel.VARIABLE, 2, null, ConstraintType.NONE, -1);
		
		initRuleTransferMoneySuccess();
		stepOver();
		stepOver();
		stepInto(); // account4 is selected as the first candidate
		assertDebugStateEquals(DebugLevel.VALUE, 2, account4, ConstraintType.NONE, -1);
		debugAC.stepReturn();
		assertEnd(true);
		
		initRuleTransferMoneySuccess();
		stepOver();
		stepOver();
		stepInto();
		stepInto(); // account4's "type" constraint type
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 2, account4, ConstraintType.TYPE, -1);
		stepReturn();
		assertDebugStateEquals(DebugLevel.VALUE, 2, account3, ConstraintType.NONE, -1);
		
		initRuleTransferMoneySuccess();
		stepOver();
		stepOver();
		stepInto();
		stepInto();
		stepInto(); // account4's "type" constraint instance
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 2, account4, ConstraintType.TYPE, 0);
		stepReturn();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 2, account4, ConstraintType.ATTRIBUTE, -1);
		
		// account4's "attribute" constraint type
		stepReturn();
		assertDebugStateEquals(DebugLevel.VALUE, 2, account3, ConstraintType.NONE, -1);
		
		// value "account3"
		debugAC.stepReturn();
		assertEnd(true);
		
		initRuleTransferMoneySuccess();
		stepOver();
		stepOver();
		stepInto();
		stepOver();
		stepInto(); // account3's "type" constraint type
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 2, account3, ConstraintType.TYPE, -1);
		debugAC.stepReturn();
		assertEnd(true);
		
		initRuleTransferMoneySuccess();
		stepOver();
		stepOver();
		stepInto();
		stepOver();
		stepInto();
		stepInto(); // account3's "type" constraint instance
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 2, account3, ConstraintType.TYPE, 0);
		debugAC.stepReturn();
		assertDebugStateEquals(DebugLevel.CONSTRAINT_TYPE, 2, account3, ConstraintType.ATTRIBUTE, -1);		
		
		// account3's "attribute" constraint type
		debugAC.canStepReturn();
		assertEnd(true);
		
		initRuleTransferMoneySuccess();
		stepOver();
		stepOver();
		stepInto();
		stepOver();
		stepInto();
		stepOver();
		stepInto(); // account3's first "attribute" constraint instance (id)
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 2, account3, ConstraintType.ATTRIBUTE, 0);
		debugAC.stepReturn();
		assertEnd(true);
		
		initRuleTransferMoneySuccess();
		stepOver();
		stepOver();
		stepInto();
		stepOver();
		stepInto();
		stepOver();
		stepInto();
		stepOver(); // account3's second "attribute" constraint instance (credit)
		assertDebugStateEquals(DebugLevel.CONSTRAINT, 2, account3, ConstraintType.ATTRIBUTE, 1);
		
		debugAC.stepReturn();
		assertEnd(true);
		
		assertMatch(expectedMatch);
	}
	
	/**
	 * Checks if the domain restriction is working as expected using the bank example.
	 */
	@Test
	public void testDomainRestriction() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("client", "Charles");
		params.put("fromId", 4);
		params.put("toId", 2);
		params.put("amount", 50);
		initRule("transferMoney", params, false);
		
		// step over the matching of the client
		stepOver();
		
		// now we are matching the sender account
		assertDebugStateEquals(DebugLevel.VARIABLE, 1, null, ConstraintType.NONE, -1);
		
		// the domain should now only contain accounts 3 and 4
		assertDomain(1, account3, account4);
		
		
		params.put("client", "Bob");
		initRule("transferMoney", params, false);
		
		// step over the matching of the client
		stepOver();
		
		// now we are matching the sender account
		assertDebugStateEquals(DebugLevel.VARIABLE, 1, null, ConstraintType.NONE, -1);
		
		// the domain should now only contain accounts 3 and 4
		assertDomain(1, account2);
				
	}

	// TODO explicitly test the backtracking (coming back from a higher variableIndex to one that already has a locked slot).
	
	//////////////////// HELPER METHODS ////////////////////
	
	/**
	 * Initializes the transfer money rule and the first variable for a 
	 * "no match" outcome (insufficient credit)
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
	 * Initializes the transfer money rule and the first variable for a "match found" outcome
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
			//engine.getOptions().put(Engine.OPTION_SORT_VARIABLES, false);
			
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
		
		debugAC = engine.getDebugApplicationCondition(rule, graph, partialMatch, null);
		initFirstVariable();
	}

	
	/**
	 * Creates a {@link Map} containing the match produced by the real {@link ApplicationCondition},
	 * using the current engine, module, and graph. The unit and parameters can be specified.
	 * @param unitName name of the Unit to be executed
	 * @param parameters a map containing the name and value of each parameter
	 * @return a map containing each variable and its corresponding matched EObject from the graph
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
	 * Initializes the first variable. After execution, the debug state is:<br><br>
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
			throw new AssertionError("debugState was \""+debugState+"\", but step should have completed");
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
	 * @param expectedDomain the expected content of the current domain
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
	 * @param expectedMatch the values in the same order as the corresponding variables
	 */
	private static void assertMatch(Map<Variable, EObject> expectedMatch) {
		if (!debugAC.checkMatch(expectedMatch)) {
			throw new AssertionError("unexpected / no match");
		}
	}
	

}
