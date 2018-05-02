package org.eclipse.emf.henshin.multicda.cda.unitTest;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.henshin.multicda.cda.tester.CDATester;
import org.eclipse.emf.henshin.multicda.cda.tester.CPATester;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.CP;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.Conditions;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.DUCR;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.MCR;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.Node;
import org.eclipse.emf.henshin.multicda.cda.tester.Tester.Options;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SuperTypeTest {

	private String path = "testData/jevsTests/extendTest/extendRules.henshin";
	private String deleteS = "deleteS";
	private String deleteC = "deleteC";
	private String useS = "useS";
	private String useC = "useC";

	@Test
	public void ASCatomic() {
		Conditions _1 = new Conditions(new Node(1));
		CDATester tester = new CDATester(path, deleteS, useC);
		assertTrue("Minimal Conflict Reasons are not 1", tester.check(new MCR(1)));
		assertTrue("Conflict Reasons are not 1", tester.check(new DUCR(1)));
		assertTrue(_1 + " not found", tester.check(_1));
		tester.ready();
	}

	@Test
	public void ASCcpa() {

		CPATester tester = new CPATester(path, new String[] { deleteS }, new String[] { useC }, new Options(Options.ESSENTIAL));
		assertTrue("Critical Pairs are not 1", tester.check(new CP(1)));
		tester.ready();

		tester = new CPATester(path, new String[] { deleteS }, new String[] { useC });
		assertTrue("Critical Pairs are not 1", tester.check(new CP(1)));
		tester.ready();
	}

	@Test
	public void BSSatomic() {
		Conditions _1 = new Conditions(new Node(1));
		CDATester tester = new CDATester(path, deleteS, useS);
		assertTrue("Minimal Conflict Reasons are not 1", tester.check(new MCR(1)));
		assertTrue("Conflict Reasons are not 1", tester.check(new DUCR(1)));
		assertTrue(_1 + " not found", tester.check(_1));
		tester.ready();
	}

	@Test
	public void BSScpa() {

		CPATester tester = new CPATester(path, new String[] { deleteS }, new String[] { useS }, new Options(Options.ESSENTIAL));
		tester.print();
		assertTrue("Critical Pairs are not 1", tester.check(new CP(1)));
		tester.ready();

		tester = new CPATester(path, new String[] { deleteS }, new String[] { useS });
		assertTrue("Critical Pairs are not 1", tester.check(new CP(1)));
		tester.ready();
	}

	@Test
	public void CCCatomic() {
		Conditions _1 = new Conditions(new Node(3));
		CDATester tester = new CDATester(path, deleteC, useC);
		assertTrue("Minimal Conflict Reasons are not 1", tester.check(new MCR(1)));
		assertTrue("Conflict Reasons are not 1", tester.check(new DUCR(1)));
		assertTrue(_1 + " not found", tester.check(_1));
		tester.ready();
	}

	@Test
	public void CCCcpa() {

		CPATester tester = new CPATester(path, new String[] { deleteC }, new String[] { useC }, new Options(Options.ESSENTIAL));
		assertTrue("Critical Pairs are not 1", tester.check(new CP(1)));
		tester.ready();

		tester = new CPATester(path, new String[] { deleteC }, new String[] { useC });
		assertTrue("Critical Pairs are not 1", tester.check(new CP(1)));
		tester.ready();
	}

	@Test
	public void DCSatomic() {
		Conditions _1 = new Conditions(new Node(3));
		CDATester tester = new CDATester(path, deleteC, useS);
		assertTrue("Minimal Conflict Reasons are not 1", tester.check(new MCR(1)));
		assertTrue("Conflict Reasons are not 1", tester.check(new DUCR(1)));
		assertTrue(_1 + " not found", tester.check(_1));
		tester.ready();
	}

	@Test
	public void DCScpa() {
		CPATester tester = new CPATester(path, new String[] { deleteC }, new String[] { useS }, new Options(Options.ESSENTIAL));
		assertTrue("Critical Pairs are not 1", tester.check(new CP(1)));
		tester.ready();

		tester = new CPATester(path, new String[] { deleteC }, new String[] { useS });
		assertTrue("Critical Pairs are not 1", tester.check(new CP(1)));
		tester.ready();
	}

}
