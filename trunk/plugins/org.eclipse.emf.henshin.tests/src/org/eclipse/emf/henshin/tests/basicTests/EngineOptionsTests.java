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
package org.eclipse.emf.henshin.tests.basicTests;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.testframework.HenshinTest;
import org.eclipse.emf.henshin.testframework.Matches;
import org.eclipse.emf.henshin.testframework.Rules;
import org.eclipse.emf.henshin.testframework.Tools;
import org.junit.Before;
import org.junit.Test;


/**
 * tests several engine options
 * 
 * @author Felix Rieger
 * @author Stefan Jurack (sjurack)
 * 
 */
public class EngineOptionsTests extends HenshinTest {
	
	@Before
	public void setUp() throws Exception {
		init("basicTestRules/engineOptionsTests.henshin");
		setModelGraphProperties("basicTestModels/engineOptionsTestsModels/", "testmodel");
	}
	
	@Test
	public void testNonInjective1() {
		/**
		 * graph:
		 * 
		 *      n1
		 *     /
		 *   n2
		 */
		loadGraph("nonInjective1");
		
		Collection<EObject> objGroup = new ArrayList<EObject>();
		objGroup.add(Tools.getFirstElementFromOCLQueryResult("self.nodename='n1'", htEmfGraph));
		objGroup.add(Tools.getFirstElementFromOCLQueryResult("self.nodename='n2'", htEmfGraph));
		objGroup.add(Tools.getFirstElementFromOCLQueryResult("self.nodename='n2'", htEmfGraph));
		
		loadRule("non-injectiveMatching");
		Rules.assertRuleHasNoMatch(htRuleApp); // Rule should have no match, as
												// we're looking for a Node with
												// two child nodes, but the
												// graph contains just a Node
												// with one child node.
		htEngine.getOptions().setInjective(false);
		loadRule("non-injectiveMatching");
		Rules.assertRuleHasNMatches(htRuleApp, 1); // Rule should have exactly 1
													// match
		Matches.assertOnlyGroupIsMatched(htRuleApp, objGroup); // This match
																// should
																// contain n1,
																// n2, n2
	}
	
	@Test
	public void testNonInjective2() {
		/**
		 * graph:
		 * 
		 *       n1
		 *      /  \
		 *    n2   n3
		 */
		loadGraph("nonInjective2");
		
		Collection<EObject> objGroup = new ArrayList<EObject>();
		objGroup.add(Tools.getFirstElementFromOCLQueryResult("self.nodename='n1'", htEmfGraph));
		objGroup.add(Tools.getFirstElementFromOCLQueryResult("self.nodename='n2'", htEmfGraph));
		objGroup.add(Tools.getFirstElementFromOCLQueryResult("self.nodename='n3'", htEmfGraph));
		
		loadRule("non-injectiveMatching");
		
		// assert Rule is correct for injective matching
		
		Rules.assertRuleHasNMatches(htRuleApp, 2); // expected matches: n1 <->
													// (n2, n3) ; n1 <-> (n3,
													// n2)
		Matches.assertOnlyGroupIsMatched(htRuleApp, objGroup);
		
		// turn off injective matching and try again
		
		Collection<EObject> ninjObjGroup1 = new ArrayList<EObject>();
		ninjObjGroup1
				.add(Tools.getFirstElementFromOCLQueryResult("self.nodename='n1'", htEmfGraph));
		ninjObjGroup1
				.add(Tools.getFirstElementFromOCLQueryResult("self.nodename='n2'", htEmfGraph));
		ninjObjGroup1
				.add(Tools.getFirstElementFromOCLQueryResult("self.nodename='n2'", htEmfGraph));
		
		Collection<EObject> ninjObjGroup2 = new ArrayList<EObject>();
		ninjObjGroup2
				.add(Tools.getFirstElementFromOCLQueryResult("self.nodename='n1'", htEmfGraph));
		ninjObjGroup2
				.add(Tools.getFirstElementFromOCLQueryResult("self.nodename='n3'", htEmfGraph));
		ninjObjGroup2
				.add(Tools.getFirstElementFromOCLQueryResult("self.nodename='n3'", htEmfGraph));
		
		htEngine.getOptions().setInjective(false);
		loadRule("non-injectiveMatching");
		Rules.assertRuleHasNMatches(htRuleApp, 4); // expected matches: n1 <->
													// (n2, n3) ; n1 <-> (n3,
													// n2) ; n1 <-> (n2, n2) ;
													// n1 <-> (n3, n3)
		
		for (Match ma : htRuleApp.findAllMatches()) {
			if (ma.getNodeMapping().containsValue(
					Tools.getFirstElementFromOCLQueryResult("self.nodename='n2'", htEmfGraph))) {
				if (ma.getNodeMapping().containsValue(
						Tools.getFirstElementFromOCLQueryResult("self.nodename='n3'", htEmfGraph))) {
					Matches.assertMatchIsGroup(ma, objGroup);
				} else {
					Matches.assertMatchIsGroup(ma, ninjObjGroup1);
				}
			} else {
				Matches.assertMatchIsGroup(ma, ninjObjGroup2);
			}
		}
		
	}
	
}