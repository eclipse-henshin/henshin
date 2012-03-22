package org.eclipse.emf.henshin.tests.basicTests;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.testframework.GraphTransformations;
import org.eclipse.emf.henshin.testframework.HenshinLoaders;
import org.eclipse.emf.henshin.testframework.HenshinTest;
import org.eclipse.emf.henshin.testframework.Rules;
import org.eclipse.emf.henshin.testframework.Tools;
import org.eclipse.emf.henshin.testframework.TransformationUnits;
import org.junit.Before;
import org.junit.Test;

public class MultiruleTests extends HenshinTest {
	
	@Before
	public void setUp() throws Exception {
		init("basicTestRules/multiruleTests.henshin");
		setModelGraphProperties("basicTestModels/multiruleTestsModels/", "testmodel");
	}
	
	/**
	 * Test nested rules separately
	 */
	@Test
	public void testSeparateMatch() {
		loadGraph("nested2initial");
		loadRule("matchm-M-MM");
		RuleApplication ruleApp = new RuleApplication(htEngine, htRule.getMultiRules().get(0));
		Rules.assertRuleHasNMatches(ruleApp, 2);	// will match (n1 ; n1-1) and (n1 ; n1-2)
		
		loadRule("matchm-M-MM");
		RuleApplication ruleApp2 = new RuleApplication(htEngine, htRule.getMultiRules().get(0).getMultiRules().get(0));
		Rules.assertRuleHasNMatches(ruleApp2, 4);	// will match (n1;n1-1;v1-1-1), (n1;n1-1;v1-1-2), (n1;n1-2;v1-2-1), (n1;n1-2;v1-2-2)
	}
	
	/**
	 * Tests the nested rules' forall-semantics
	 * 
	 * if the outermost rule can be matched, the inner rules are matched with forall-semantics
	 * i.e. even if they can not be matched, this will count as a match
	 */
	@Test
	public void testForallSemantics() {
		loadGraph("nested2initial");
		loadRule("matchm-M-MM");
		Rules.assertRuleHasNMatches(htRuleApp, 3);	// 3 matches: n1 (full match w/ all nested rules), n1-1 (no subrules matched), n1-2 (no subrules matched)
	}
	
	/**
	 * Tests handling a parameter in the innermost multi-rule.
	 * Parameters should work intuitively, i.e. not overwrite each other
	 * 
	 * This test will increment v1-1-1, v1-1-2, v1-2-1 and v1-2-2 by 1, leading to values 4, 8, 14 and 18 
	 */
	@Test
	public void testInnermostParameter() {
		loadGraph("nested2initial");
		loadRule("innermostParameter");
		
		// XXX: Very hacky, prone to break
		// This should be replaced by a more elegant and less error-prone solution
		Match m = htRuleApp.findAllMatches().get(2);
		
		htRuleApp.setMatch(m);
		GraphTransformations.assertTransformsGraph(htRuleApp, HenshinLoaders.loadGraph(getGraphURI("graphAfter_innermostParameter")), 0.9);
	}
	
	/**
	 * Tests handling a parameter on multiple rule nesting levels.
	 * Parameters should work intuitively, i.e. not overwrite each other
	 * 
	 * This test will rename v1-1-1, v1-1-2, v1-2-1 and v1-2-2 by prepending their parent node's name, i.e.
	 * n1-1_v1-1-1, n1-1_v1-1-2, n1-2_v1-2-1, n1-2_v1-2-2
	 * it will also increment them as in testInnermostParameter
	 */
	@Test
	public void testMultilevelParameter() {
		loadGraph("nested2initial");
		loadRule("multilevelParameter");
		
		// XXX: Very hacky, prone to break
		// This should be replaced by a more elegant and less error-prone solution
		Match m = htRuleApp.findAllMatches().get(2);
		
		htRuleApp.setMatch(m);
		GraphTransformations.assertTransformsGraph(htRuleApp, HenshinLoaders.loadGraph(getGraphURI("graphAfter_multilevelParameter")), 0.9);

		// TODO: Research/fix this!    Current behavior: Vals are renamed to n1-1_*
	}
	
	@Test
	public void testMultiruleMatch() throws IOException {
		loadGraph("nested2initial");
		loadRule("matchMM");
		Tools.printMatches(htRuleApp.findAllMatches());
		
		System.out.println(" ++++++++++++++++++++++++++++++ ");
		
		loadGraph("nested2initial");
		loadRule("matchM");
		Tools.printMatches(htRuleApp.findAllMatches());
		
		System.out.println(" ############################### ");
		
		loadGraph("nested2initial");
		loadRule("matchM-MM");
		Tools.printMatches(htRuleApp.findAllMatches());
		
		for (Match m : htRuleApp.findAllMatches()) {
			for (Entry<Rule, List<Match>> e : m.getNestedMatches().entrySet()) {
				System.out.println("´´´´´´´\t" + e.getKey());
				Tools.printMatches(e.getValue());

			}
		}
		
		System.out.println("****************************");
		
		loadGraph("nested2initial");
		loadRule("matchm-M-MM");
		Tools.printMatches(htRuleApp.findAllMatches());
		
		/*
		for (Match m : htRuleApp.findAllMatches()) {
			printSubmatchesRec(m, 1);
			System.out.println("!!!!!!!!!!!!!");
		} */
		
		// ---------------
		
		loadGraph("nested2initial");
		loadRule("multilevelParameter");
		List<Match> myMatches = htRuleApp.findAllMatches();

/**		for(int i = 0; i < myMatches.size(); i++) {
			System.out.println(i);
			loadGraph("nested2initial");
			loadRule("multilevelParameter");
			List<Match> matches = htRuleApp.findAllMatches();
			Match currentMatch = matches.get(i);
			htRuleApp.setMatch(currentMatch);
			htRuleApp.apply();
			Tools.persist(Tools.getGraphRoot(htEmfGraph), "myStuff-match" + i + ".testmodel");
		}**/
		
		
	/*	loadRule("nested2levelRule");
		loadGraph("nested2initial");
		Tools.printMatches(htRuleApp.findAllMatches());
	*/	
/*		loadRule("getN1");
		Match n1Match = htRuleApp.getMatch();
		
		loadRule("matchm-M-MM");
		htRuleApp.setMatch(n1Match);
		
		
		
		System.out.println("apply rule: " + htRuleApp.apply());*/

		
		
		//Tools.persist(Tools.getGraphRoot(htEmfGraph), "myStuff.testmodel");
		
		
		//htRuleApp.findAllMatches()
	}
	
	/*
	public void printSubmatchesRec(Match m, int ident) {
		
		for (EObject eo : m.getNodeMapping().values()) {
			System.out.println(getTabs(ident) + eo);
		}
		System.out.println("--");
		
		
		if (m.getNestedMatches().size() == 0) {
			return;
		}

		for (Entry<Rule, List<Match>> e : m.getNestedMatches().entrySet()) {
			System.out.println(getTabs(ident+1) + "R: " + e.getKey());
			for (Match ma : e.getValue()) {
				printSubmatchesRec(ma, ident+1);
			}
		}
	}
	
	private String getTabs(int i) {
		String tmp = "";
		while(i-- > 0) {
			tmp = tmp + "\t";
		}
		
		return tmp;
	} */
	
}
