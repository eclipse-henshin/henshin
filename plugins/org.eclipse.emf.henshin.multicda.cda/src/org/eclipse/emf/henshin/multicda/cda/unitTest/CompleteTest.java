package org.eclipse.emf.henshin.multicda.cda.unitTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.emf.henshin.multicda.cda.ConflictAnalysis;
import org.eclipse.emf.henshin.multicda.cda.tester.CDATester;
import org.eclipse.emf.henshin.multicda.cda.tester.CPATester;
import org.eclipse.emf.henshin.multicda.cda.tester.ResultCreator;
import org.eclipse.emf.henshin.multicda.cda.tester.RuleConfigurator;
import org.eclipse.emf.henshin.multicda.cda.tester.Tester.Options;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
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
			for (Rule r1 : rules) {
				System.out.println(new RuleConfigurator(r1));
				if ((r1.getLhs().getPACs().isEmpty() && r1.getLhs().getNACs().isEmpty() || withNACsPACs)
						&& r1.getMultiRules().isEmpty())
					for (Rule r2 : rules)
						if (r2.getLhs().getPACs().isEmpty() && r2.getLhs().getNACs().isEmpty()
								&& r2.getMultiRules().isEmpty() || withNACsPACs) {
							// cda = new CDATester(r1, r2, options);
							// cdaResult.addAll(cda.getResult());
							// List<Rule> a = new ArrayList<Rule>();
							// List<Rule> b = new ArrayList<Rule>();
							// a.add(r1);
							// b.add(r2);
							// cpa = new CPATester(a, b, options);
							// cpaResult.addAll(cpa.getResult());
							// int icr = cda.getResult().size();
							// int icp = cpa.getResult().size();
							// String error = "";
							// if (icr != icp) {
							// error += "\n\t\ta) " + r1.getName() + " --> " + r2.getName() + "\nCDA: " +
							// cda
							// + "\nCPA: " + cpa + "\n" + CDATester.print(cda.getResult(), false, false)
							// + "\n__________________\n" + CPATester.printCP(cpa.getResult(), false, false)
							// + "\n";
							// } else {
							// int matches = cda.compare(cpa.getResult()).size();
							// if (icr != matches || icp != matches)
							// error += "\n\t\tb) " + r1.getName() + " --> " + r2.getName()
							// + "\nNot all matches are found: " + matches + " matches of " + icr
							// + " Reasons and " + icp + " Critical Pairs\n"
							// + CDATester.print(cda.getResult(), false, false) + "\n__________________\n"
							// + CPATester.printCP(cpa.getResult(), false, false) + "\n";
							// }
							// if (!error.isEmpty()) {
							// System.out.println(error);
							// System.out.println();
							// }
							// errorMessage += error;
						}
			}
		}
	}

	@AfterClass
	public static void results() {
		System.out.println("Finished...");
		// System.err.println("________________________________________\nErrors
		// found:\n" + errorMessage);
	}

	@Test
	public void testOne() {
		withNACsPACs = true;
		justAttrs = true;
		execute = false;
		// options.add(Options.DEPENDENCY);
		String r1 = "c3";
		String r2 = "d2";
		int size = 0;
		if (!execute) {
			options.remove(Options.PRINT_HEADER, options.PRINT_RESULT);
			// options.add(Options.DEPENDENCY);
			henshin = "testData/jevsTests/test.henshin";
			ConflictAnalysis.COMPLETE_COMPUTATION = true;
			// ConflictAnalysis.OLD_COMPUTATION = true;
			cda = new CDATester(henshin, r1, r2, options);
			// cda = new CDATester(henshin, options);
			// System.out.println("OLD : " + cda);
			size += cda.getResult().size();
			// ConflictAnalysis.OLD_COMPUTATION = false;
			// cda = new CDATester(henshin, options);
			// System.out.println("NEW : " + cda);
			ResultCreator rc = new ResultCreator(cda);
			// cpa = new CPATester(henshin, new String[] { r1 }, new String[] { r2 },
			// options);
			//// cda.compare(cpa.getCriticalPairs());
			//// cda.ready();
			//// cpa.ready();
			//// options.add(Options.DEPENDENCY);
			// System.out.println("______________________________________________________________________________");
			// cda = new CDATester(henshin, r1, r2, options);
			// cpa = new CPATester(henshin, new String[] { r1 }, new String[] { r2 },
			// options);
			// cda.compare(cpa.getCriticalPairs());
			// cda.ready();
			// cpa.ready();
		} else {
			options.remove(Options.PRINT_RESULT);
			options.remove(Options.PRINT_HEADER);
		}
		System.out.println(size + " reasons found");
	}

}