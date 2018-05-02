package org.eclipse.emf.henshin.multicda.cda.unitTest;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.henshin.multicda.cda.tester.CDATester;
import org.eclipse.emf.henshin.multicda.cda.tester.CPATester;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.CP;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.DUCR;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.ICP;
import org.eclipse.emf.henshin.multicda.cda.tester.Tester.Options;
//import org.eclipse.emf.henshin.cpa.atomic.tester.Condition.MinimalConditions;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoopsTest {

	private String path = "testData/jevsTests/loop/Loops.henshin";
	private String loop = "executeLoop";
	private String loop2 = "executeLoop2";
	private String loop3 = "executeNonLoop";

	@Test

	public void A() {

		DUCR _1 = new DUCR(7);
		CDATester tester = new CDATester(path, loop3, new Options(true, false, true, true));
		assertTrue(_1 + " not correct", tester.check(_1));

	}

	@Test
	@Ignore
	public void B() {
		CP _1 = new CP(49);
		CPATester tester = new CPATester(path, new String[]{loop3});
		assertTrue(_1 + " not correct", tester.check(_1));
		tester.ready();
	}
	
	@Test
	@Ignore
	public void B2() {
		ICP _1 = new ICP(7);
		CPATester tester = new CPATester(path, new String[]{loop3});
		assertTrue(_1 + " not correct", tester.check(_1));
		tester.ready();
	}
	

	@Test
	@Ignore
	public void C() {
		CPATester tester = new CPATester(path, new String[]{loop3});
		tester.ready();
	}
}
