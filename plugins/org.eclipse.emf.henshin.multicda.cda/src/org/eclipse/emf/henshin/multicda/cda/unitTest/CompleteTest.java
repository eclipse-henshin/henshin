package org.eclipse.emf.henshin.multicda.cda.unitTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.emf.henshin.multicda.cda.tester.CDATester;
import org.eclipse.emf.henshin.multicda.cda.tester.CPATester;
import org.eclipse.emf.henshin.multicda.cda.tester.Tester.Options;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CompleteTest {

	private static CDATester cda;
	private static CPATester cpa;
	private static List<Rule> rules;
	private static String henshin = "testData/jevsTests/complete.henshin";
	private static Options options = new Options();
	private static boolean execute = true;
	private static boolean withNACsPACs = true;
	private static boolean justAttrs = false;

	private static String errorMessage = "";

	@BeforeClass
	public static void init() {
	}

	@Before
	public void prepare() {
		withNACsPACs = false;
		execute = true;
		justAttrs = false;
	}

	@After
	public void execute() {
		if (execute) {
			rules = new ArrayList<Rule>();
			String resource = henshin.substring(0, henshin.lastIndexOf("/") + 1);
			String mFile = henshin.substring(henshin.lastIndexOf("/") + 1, henshin.length());
			HenshinResourceSet resourceSet = new HenshinResourceSet(resource);

			Module module = resourceSet.getModule(mFile, false);

			for (Unit u : new ArrayList<>(module.getUnits()))
				if (u instanceof Rule)
					if (!justAttrs || u.getName().endsWith("A"))
						rules.add((Rule) u);
			Set<Reason> cdaResult = new HashSet<>();
			Set<CriticalPair> cpaResult = new HashSet<>();
			for (Rule r : rules) {
				if ((r.getLhs().getPACs().isEmpty() && r.getLhs().getNACs().isEmpty() || withNACsPACs)
						&& r.getMultiRules().isEmpty())
					for (Rule r2 : rules)
						if (r2.getLhs().getPACs().isEmpty() && r2.getLhs().getNACs().isEmpty()
								&& r2.getMultiRules().isEmpty() || withNACsPACs) {
							cda = new CDATester(r, r2, options);
							cdaResult.addAll(cda.getReasons());
							List<Rule> a = new ArrayList<Rule>();
							List<Rule> b = new ArrayList<Rule>();
							a.add(r);
							b.add(r2);
//							cpa = new CPATester(a, b, options);
//							cpaResult.addAll(cpa.getCriticalPairs());
//							int icr = cda.getReasons().size();
//							int icp = cpa.getCriticalPairs().size();
//							String error = "";
//							if (icr != icp) {
//								error += "\n\t\ta) " + r.getName() + " --> " + r2.getName() + "\nCDA: " + cda
//										+ "\nCPA: " + cpa + "\n" + CDATester.print(cda.getReasons(), false, false)
//										+ "\n__________________\n"
//										+ CPATester.printCP(cpa.getCriticalPairs(), false, false) + "\n";
//							} else {
//								int matches = cda.compare(cpa.getCriticalPairs()).size();
//								if (icr != matches || icp != matches)
//									error += "\n\t\tb) " + r.getName() + " --> " + r2.getName()
//											+ "\nNot all matches are found: " + matches + " matches of " + icr
//											+ " Reasons and " + icp + " Critical Pairs\n"
//											+ CDATester.print(cda.getReasons(), false, false) + "\n__________________\n"
//											+ CPATester.printCP(cpa.getCriticalPairs(), false, false) + "\n";
//							}
//							if (!error.isEmpty()) {
//								System.out.println(error);
//								System.out.println();
//							}
//							errorMessage += error;
						}
			}
		}
	}

	@AfterClass
	public static void results() {
		System.out.println("Finished...");
//		System.err.println("________________________________________\nErrors found:\n" + errorMessage);
	}

	@Test
	public void testOne() {
		withNACsPACs = true;
		justAttrs = true;
//		execute = false;
//		options.add(Options.DEPENDENCY);
		String r1 = "complex1";
		String r2 = "complex1";
		if (!execute) {
			options.remove(Options.DEPENDENCY);
			cda = new CDATester(henshin, r1, r2, options);
			cpa = new CPATester(henshin, new String[] { r1 }, new String[] { r2 }, options);
//			cda.compare(cpa.getCriticalPairs());
//			cda.ready();
//			cpa.ready();
//			options.add(Options.DEPENDENCY);
			System.out.println("______________________________________________________________________________");
			cda = new CDATester(henshin, r1, r2, options);
			cpa = new CPATester(henshin, new String[] { r1 }, new String[] { r2 }, options);
//			cda.compare(cpa.getCriticalPairs());
//			cda.ready();
//			cpa.ready();
		} else {
			options.remove(Options.PRINT_RESULT);
//			options.remove(Options.PRINT_HEADER);
		}
	}

}