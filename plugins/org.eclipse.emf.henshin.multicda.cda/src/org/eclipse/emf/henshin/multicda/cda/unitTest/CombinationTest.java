package org.eclipse.emf.henshin.multicda.cda.unitTest;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.henshin.multicda.cda.tester.CDATester;
import org.eclipse.emf.henshin.multicda.cda.tester.CPATester;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.CP;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.Conditions;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.Edge;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.DUCR;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.MCR;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.Node;
import org.eclipse.emf.henshin.multicda.cda.tester.Tester.Options;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CombinationTest {

	private String path = "testData/refactoring/refactorings.henshin";

	@Test
	public void ADeDeAtomic() {
		Conditions _1 = new Conditions(new Edge(5, 6), new Node(2), new Edge(1, 3), new Edge(2, 6), new Edge(3, 5),
				new Node(3), new Node(5), new Edge(1, 2));
		Conditions _2 = new Conditions(new Edge(2, 6), new Node(2), new Edge(1, 2));
		Conditions _3 = new Conditions(new Edge(5, 6), new Node(3), new Node(5), new Edge(1, 3), new Edge(3, 5));

		CDATester tester = new CDATester(path, "decapsulateAttribute");
		assertTrue("Minimal Conflict Reasons are not 2", tester.check(new MCR(2)));
		assertTrue("Initial Conflict Reasons are not 3", tester.check(new DUCR(3)));
		assertTrue(_1 + " not found", tester.check(_1));
		assertTrue(_2 + " not found", tester.check(_2));
		assertTrue(_3 + " not found", tester.check(_3));
		tester.ready();
	}

	@Test
	public void ADeDeCpa() {

		CPATester tester = new CPATester(path, new String[] { "decapsulateAttribute" },
				new Options().add(Options.ESSENTIAL));
		assertTrue("Critical Pairs are not 3", tester.check(new CP(3)));
		tester.ready();

		tester = new CPATester(path, new String[] { "decapsulateAttribute" });
		assertTrue("Critical Pairs are not 5", tester.check(new CP(5)));
		tester.ready();
	}

	@Test
	public void BDePuAtomic() {
		Conditions _1 = new Conditions(new Edge(5, 6), new Node(2), new Edge(1, 3), new Edge(2, 6), new Edge(3, 5),
				new Node(3), new Node(5), new Edge(1, 2));
		Conditions _2 = new Conditions(new Edge(2, 6), new Node(2), new Edge(1, 2));
		Conditions _3 = new Conditions(new Edge(5, 6), new Node(3), new Node(5), new Edge(1, 3), new Edge(3, 5));

		CDATester tester = new CDATester(path, "decapsulateAttribute", "pullUpEncapsulatedAttribute");
		assertTrue("Minimal Conflict Reasons are not 2", tester.check(new MCR(2)));
		assertTrue("Initial Conflict Reasons are not 3", tester.check(new DUCR(3)));
		assertTrue(_1 + " not found", tester.check(_1));
		assertTrue(_2 + " not found", tester.check(_2));
		assertTrue(_3 + " not found", tester.check(_3));
		tester.ready();
	}

	@Test
	public void BDePuCpa() {

		CPATester tester = new CPATester(path, new String[] { "decapsulateAttribute" },
				new String[] { "pullUpEncapsulatedAttribute" }, new Options().add(Options.ESSENTIAL));
		assertTrue("Critical Pairs are not 3", tester.check(new CP(3)));
		tester.ready();

		tester = new CPATester(path, new String[] { "decapsulateAttribute" },
				new String[] { "pullUpEncapsulatedAttribute" });
		assertTrue("Critical Pairs are not 6", tester.check(new CP(6)));
		tester.ready();
	}

	@Test
	public void CPuPuAtomic() {
		Conditions _1 = new Conditions(new Edge(11, 12));
		Conditions _2 = new Conditions(new Edge(11, 13));
		Conditions _3 = new Conditions(new Edge(11, 14));
		Conditions _4 = new Conditions(new Edge(11, 12), new Edge(11, 13));
		Conditions _5 = new Conditions(new Edge(11, 12), new Edge(11, 14));
		Conditions _6 = new Conditions(new Edge(11, 13), new Edge(11, 14));
		Conditions _7 = new Conditions(new Edge(11, 12), new Edge(11, 13), new Edge(11, 14));

		CDATester tester = new CDATester(path, "pullUpEncapsulatedAttribute");
		assertTrue("Minimal Conflict Reasons are not 5", tester.check(new MCR(5)));
		assertTrue("Initial Conflict Reasons are not 7", tester.check(new DUCR(13))); // 13
		assertTrue(_1 + " not found", tester.check(_1));
		assertTrue(_2 + " not found", tester.check(_2));
		assertTrue(_3 + " not found", tester.check(_3));
		assertTrue(_4 + " not found", tester.check(_4));
		assertTrue(_5 + " not found", tester.check(_5));
		assertTrue(_6 + " not found", tester.check(_6));
		assertTrue(_7 + " not found", tester.check(_7));
		tester.ready();
	}

	@Test
	public void CPuPuCpa() {

		CPATester tester = new CPATester(path, new String[] { "pullUpEncapsulatedAttribute" },
				new Options().add(Options.ESSENTIAL));
		assertTrue("Critical Pairs are not 13", tester.check(new CP(13)));
		tester.ready();

		tester = new CPATester(path, new String[] { "pullUpEncapsulatedAttribute" });
		assertTrue("Critical Pairs are not 5", tester.check(new CP(5)));
		tester.ready();
	}

	@Test
	public void DPuDeAtomic() {
		Conditions _1 = new Conditions(new Edge(11, 12));
		Conditions _2 = new Conditions(new Edge(11, 13));
		Conditions _3 = new Conditions(new Edge(11, 14));
		Conditions _4 = new Conditions(new Edge(11, 12), new Edge(11, 13));
		Conditions _5 = new Conditions(new Edge(11, 12), new Edge(11, 14));
		Conditions _6 = new Conditions(new Edge(11, 13), new Edge(11, 14));
		Conditions _7 = new Conditions(new Edge(11, 12), new Edge(11, 13), new Edge(11, 14));

		CDATester tester = new CDATester(path, "pullUpEncapsulatedAttribute", "decapsulateAttribute");
		assertTrue("Minimal Conflict Reasons are not 5", tester.check(new MCR(5)));
		assertTrue("Initial Conflict Reasons are not 13", tester.check(new DUCR(13)));
		assertTrue(_1 + " not found", tester.check(_1));
		assertTrue(_2 + " not found", tester.check(_2));
		assertTrue(_3 + " not found", tester.check(_3));
		assertTrue(_4 + " not found", tester.check(_4));
		assertTrue(_5 + " not found", tester.check(_5));
		assertTrue(_6 + " not found", tester.check(_6));
		assertTrue(_7 + " not found", tester.check(_7));
		tester.ready();
	}

	@Test
	public void DPuDeCpa() {

		CPATester tester = new CPATester(path, new String[] { "pullUpEncapsulatedAttribute" },
				new String[] { "decapsulateAttribute" }, new Options().add(Options.ESSENTIAL));
		assertTrue("Critical Pairs are not 1", tester.check(new CP(1)));
		tester.ready();

		tester = new CPATester(path, new String[] { "pullUpEncapsulatedAttribute" },
				new String[] { "decapsulateAttribute" });
		assertTrue("Critical Pairs are not 7", tester.check(new CP(7)));
		tester.ready();
	}

}
