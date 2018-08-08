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
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NanoTest {

	private static CPATester eTester;
	private static CDATester aTester;
	private static String henshin = "testData/visualContracts-nanoXML/nanoXML.henshin";
	private static List<Rule> rules;
	private static List<Reason> initsNormal = new ArrayList<>();
	private static List<CriticalPair> initspNormal = new ArrayList<>();
	private static String resultNormal = "";

	@BeforeClass
	public static void prepare() {
		rules = new ArrayList<Rule>();
		String resource = henshin.substring(0, henshin.lastIndexOf("/") + 1);
		String mFile = henshin.substring(henshin.lastIndexOf("/") + 1, henshin.length());
		HenshinResourceSet resourceSet = new HenshinResourceSet(resource);
		Module module = resourceSet.getModule(mFile, false);
		for (Unit u : new ArrayList<>(module.getUnits()))
			if (u instanceof Rule) {
//				Rule prepared = RulePreparator.prepareRule((Rule) u);
				rules.add((Rule) u);
			}
	}

	@Test
	public void AbeideRegelnNormal() {
		Options options = new Options(Options.ESSENTIAL);
		options.remove(Options.PRINT_RESULT);
		for (Rule r : rules) {
			for (Rule r2 : rules) {
				aTester = new CDATester(r, r2, options);
				Set<Reason> result1 = aTester.getResult();
				initsNormal.addAll(result1);

				Set<Rule> a = new HashSet<Rule>();
				Set<Rule> b = new HashSet<Rule>();
				a.add(r);
				b.add(r2);
				eTester = new CPATester(a, b, options);
				Set<CriticalPair> result2 = new HashSet<CriticalPair>(eTester.getResult());
				initspNormal.addAll(result2);

				int icr = aTester.getResult().size();
				int icp = eTester.getResult().size();
				if (icr != icp) {
					resultNormal += "\t\t" + r.getName() + " --> " + r2.getName() + "\nCDA: " + aTester + "\nCPA: "
							+ eTester + "\n";
				} else {
					int matches = aTester.compare(eTester.getResult()).size();
					if (icr != matches || icp != matches)
						resultNormal += "\t\t" + r.getName() + " --> " + r2.getName() + "\nNot all matches are found: "
								+ matches + " matches of " + icr + " Reasons and " + icp + " Critical Pairs\n";
				}
			}
		}
	}

	@AfterClass
	public static void after() {
		System.out.println("_____________________________________________\n");
		System.out.println("Result of Initial Dependency Reasons [CDA]: " + initsNormal.size());
		System.out.println("Result of Initial Dependency Pairs [CPA]: " + initspNormal.size());
		System.err.println("\n_____________________________________________\nAll Errors:\n" + resultNormal);
	}
}
