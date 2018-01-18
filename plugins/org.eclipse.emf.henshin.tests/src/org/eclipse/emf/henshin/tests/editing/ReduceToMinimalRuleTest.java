package org.eclipse.emf.henshin.tests.editing;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.RuleMinimizer;
import org.eclipse.emf.henshin.tests.framework.HenshinLoaders;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Timo Kehrer, Manuel Ohrndorf
 */
public class ReduceToMinimalRuleTest {

	private Rule testCase1;

	@Before
	public void setUp() throws Exception {
		testCase1 = (Rule) HenshinLoaders.loadHenshin("editing/ReduceToMinimalRule/default.henshin").getUnits().get(0);
	}

	@Test
	public void testGeneralizeRuleCase1() {
		// Do the reduction
		RuleMinimizer.reduceToMinimalRule(testCase1);

		// Check the result

		// Nodes:
		Assert.assertEquals(4, testCase1.getLhs().getNodes().size());
		Assert.assertEquals(5, testCase1.getRhs().getNodes().size());

		// Edges:
		Assert.assertEquals(1, testCase1.getLhs().getEdges().size());
		Assert.assertEquals(2, testCase1.getRhs().getEdges().size());
	}
}
