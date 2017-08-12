package org.eclipse.emf.henshin.tests.basic;

import static org.junit.Assert.*;

import org.eclipse.emf.henshin.tests.framework.HenshinTest;
import org.eclipse.emf.henshin.tests.framework.Rules;
import org.junit.Before;
import org.junit.Test;

public class DanglingTest extends HenshinTest {
	@Before
	public void setUp() throws Exception {
		init("basic/rules/checkDangling.henshin");
		setEGraphPath("basic/models/checkDanglingModels/", "ecore");
		loadEGraph("graphWithTwoReferences");
	}

	@Test
	public void testMulti() {
		loadRule("deleteClassCMulti");
		Rules.assertRuleCanBeApplied(htRuleApp);
		assertEquals(3, htEGraph.size());
	}
	

	@Test
	public void testMulti2Level() {
		loadRule("deleteClassCMulti2Level");
		Rules.assertRuleCanBeApplied(htRuleApp);
		assertEquals(3, htEGraph.size());
	}

	@Test
	public void testTwoEdges() {
		loadRule("deleteClassCTwoEdges");
		Rules.assertRuleCanBeApplied(htRuleApp);
		assertEquals(3, htEGraph.size());
	}
	

	@Test
	public void testOneEdge() {
		loadRule("deleteClassCOneEdge");
		Rules.assertRuleCannotBeApplied(htRuleApp);
		assertEquals(8, htEGraph.size());
	}
}
