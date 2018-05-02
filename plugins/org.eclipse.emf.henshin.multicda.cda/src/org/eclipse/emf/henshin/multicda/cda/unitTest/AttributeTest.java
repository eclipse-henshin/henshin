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
public class AttributeTest {
	private String path = "testData/jevsTests/attribute/attributeRules.henshin";
	private String change = "change";
	private String changeN = "changeN";
	private String use = "use";
	private String useN = "useN";
	private String pathCases = "testData/jevsTests/attribute/attributeCasesRules.henshin";
	private String deleteAttr = "deleteAttributes";
	private String delete = "delete";

	@Test
	public void AChangeUseAtomic() {
		Conditions _1 = new Conditions(new Node(1));
		CDATester tester = new CDATester(path, change, use);
		assertTrue(new MCR(1) + " failed", tester.check(new MCR(1)));
		assertTrue(new DUCR(1) + " failed", tester.check(new DUCR(1)));
		assertTrue(_1 + " not found", tester.check(_1));
		tester.ready();
	}

	@Test
	public void AChangeUseCPA() {
		CPATester tester = new CPATester(path, new String[] { change }, new String[] { use }, new Options(Options.ESSENTIAL));
		assertTrue("Critical Pairs are not 1", tester.check(new CP(1)));
		tester.ready();

		tester = new CPATester(path, new String[] { change }, new String[] { use });
		assertTrue("Critical Pairs are not 1", tester.check(new CP(1)));
		tester.ready();
	}

	@Test
	public void BChangeUseNAtomic() {
		Conditions _1 = new Conditions(new Node(1));
		CDATester tester = new CDATester(path, change, useN);
		assertTrue("Minimal Conflict Reasons is not 1", tester.check(new MCR(1)));
		assertTrue("Initial Conflict Reasons is not 1", tester.check(new DUCR(1)));
		assertTrue(_1 + " not found", tester.check(_1));
		tester.ready();
	}

	@Test
	public void BChangeUseNCPA() {
		CPATester tester = new CPATester(path, new String[] { change }, new String[] { useN }, new Options(Options.ESSENTIAL));
		assertTrue("Critical Pairs are not 1", tester.check(new CP(1)));
		tester.ready();

		tester = new CPATester(path, new String[] { change }, new String[] { useN });
		assertTrue("Critical Pairs are not 1", tester.check(new CP(1)));
		tester.ready();
	}

	@Test
	public void CChangeNUseAtomic() {
		Conditions _1 = new Conditions(new Node(1));
		CDATester tester = new CDATester(path, changeN, use);
		assertTrue("Minimal Conflict Reasons is not 1", tester.check(new MCR(1)));
		assertTrue("Initial Conflict Reasons is not 1", tester.check(new DUCR(1)));
		assertTrue(_1 + " not found", tester.check(_1));
		tester.ready();
	}

	@Test
	public void CChangeNUseCPA() {
		CPATester tester = new CPATester(path, new String[] { changeN }, new String[] { use }, new Options(Options.ESSENTIAL));
		assertTrue("Critical Pairs are not 1", tester.check(new CP(1)));
		tester.ready();

		tester = new CPATester(path, new String[] { changeN }, new String[] { use });
		assertTrue("Critical Pairs are not 1", tester.check(new CP(1)));
		tester.ready();
	}

	@Test
	public void DChangeNUseNAtomic() {
		Conditions _1 = new Conditions(new Node(1));
		CDATester tester = new CDATester(path, changeN, useN);
		assertTrue("Minimal Conflict Reasons is not 1", tester.check(new MCR(1)));
		assertTrue("Initial Conflict Reasons is not 1", tester.check(new DUCR(1)));
		assertTrue(_1 + " not found", tester.check(_1));
		tester.ready();
	}

	@Test
	public void DChangeNUseNCPA() {
		CPATester tester = new CPATester(path, new String[] { changeN }, new String[] { useN },
				new Options(true));
		assertTrue("Critical Pairs are not 1", tester.check(new CP(1)));
		tester.ready();

		tester = new CPATester(path, new String[] { changeN }, new String[] { useN });
		assertTrue("Critical Pairs are not 1", tester.check(new CP(1)));
		tester.ready();

	}

//__________________________________________________________________________________________________
	@Test
	public void EDeleteUseCaseAtomic() {
		Conditions _1 = new Conditions(new Node(1));
		Conditions _2 = new Conditions(new Node(2));
		Conditions _3 = new Conditions(new Node(3));
		Conditions _4 = new Conditions(new Node(4));
		Conditions _5 = new Conditions(new Node(1), new Node(2));
		Conditions _6 = new Conditions(new Node(1), new Node(3));
		Conditions _7 = new Conditions(new Node(1), new Node(4));
		Conditions _8 = new Conditions(new Node(2), new Node(3));
		Conditions _9 = new Conditions(new Node(2), new Node(4));
		Conditions _10 = new Conditions(new Node(3), new Node(4));
		Conditions _11 = new Conditions(new Node(1), new Node(2), new Node(3));
		Conditions _12 = new Conditions(new Node(1), new Node(2), new Node(4));
		Conditions _13 = new Conditions(new Node(1), new Node(3), new Node(4));
		Conditions _14 = new Conditions(new Node(2), new Node(3), new Node(4));
		Conditions _15 = new Conditions(new Node(1), new Node(2), new Node(3), new Node(4));

		CDATester tester = new CDATester(pathCases, delete, use);
		assertTrue("Minimal Conflict Reasons is not 4", tester.check(new MCR(4)));
		assertTrue("Initial Conflict Reasons is not 15", tester.check(new DUCR(15)));

//		assertTrue(_test + " not found", tester.check(_test));
		assertTrue(_1 + " not found", tester.check(_1));
		assertTrue(_2 + " not found", tester.check(_2));
		assertTrue(_3 + " not found", tester.check(_3));
		assertTrue(_4 + " not found", tester.check(_4));
		assertTrue(_5 + " not found", tester.check(_5));
		assertTrue(_6 + " not found", tester.check(_6));
		assertTrue(_7 + " not found", tester.check(_7));
		assertTrue(_8 + " not found", tester.check(_8));
		assertTrue(_9 + " not found", tester.check(_9));
		assertTrue(_10 + " not found", tester.check(_10));
		assertTrue(_11 + " not found", tester.check(_11));
		assertTrue(_12 + " not found", tester.check(_12));
		assertTrue(_13 + " not found", tester.check(_13));
		assertTrue(_14 + " not found", tester.check(_14));
		assertTrue(_15 + " not found", tester.check(_15));
		tester.ready();
	}

	@Test
	public void EDeleteUseCaseCPA() {
		CPATester tester = new CPATester(pathCases, new String[] { delete }, new String[] { use }, new Options(Options.ESSENTIAL));
		assertTrue("Critical Pairs are not 15", tester.check(new CP(15)));
		tester.ready();

		tester = new CPATester(pathCases, new String[] { delete }, new String[] { use });
		assertTrue("Critical Pairs are not 15", tester.check(new CP(15)));
		tester.ready();

	}

	@Test
	public void FDeleteAttrUseCaseAtomic() {
		System.out.println("\t\t\tAtomic");
		CDATester tester = new CDATester(pathCases, deleteAttr, use);
		assertTrue("Minimal Conflict Reasons is not 0", tester.check(new MCR(0)));
		assertTrue("Initial Conflict Reasons is not 0", tester.check(new DUCR(0)));
		tester.ready();
	}

	@Test
	public void FDeleteAttrUseCaseCPA() {
		CPATester tester = new CPATester(pathCases, new String[] { deleteAttr }, new String[] { use }, new Options(Options.ESSENTIAL));
		assertTrue("Critical Pairs are not 0", tester.check(new CP(0)));
		tester.ready();

		tester = new CPATester(pathCases, new String[] { deleteAttr }, new String[] { use });
		assertTrue("Critical Pairs are not 0", tester.check(new CP(0)));
		tester.ready();

	}
}
