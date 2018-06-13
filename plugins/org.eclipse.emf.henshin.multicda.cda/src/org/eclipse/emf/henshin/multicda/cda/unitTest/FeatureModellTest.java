package org.eclipse.emf.henshin.multicda.cda.unitTest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FeatureModellTest {

	private static CDATester cda;
	private static CPATester cpa;
	private static List<Rule> rules;
	private static int toTest = 0;
	private static Map<String, Set<Reason>> resultA = new HashMap<>();
	private static Map<String, Set<CriticalPair>> resultE = new HashMap<>();

	private static String[] folders = new String[] { "atomic/arbitrary_edit/", "atomic/generalization/",
			"atomic/refactoring/", "atomic/specialization/", "complex/arbitrary_edit/", "complex/generalization/",
			"complex/refactoring/", "complex/specialization/" };
	private static String pathNoAttr = "testData/featureModeling/fmedit/rules/";
	private static File[] files;

//	@BeforeClass
//	public static void before() {
//		FeatureModelPackage.eINSTANCE.eClass(); //TODO: To run this test you need the de.imotep.featuremodel.variability.metamodel project in your workspace
//	}

	private static String errorMessage = "";

	@Before
	public void prepare() {
		File folder = new File(pathNoAttr + folders[toTest]);
		files = folder.listFiles();
		rules = new ArrayList<Rule>();
		for (File file : files) {
			if (file.getPath().endsWith(".henshin")) {
				String henshin = file.getPath();
				String resource = henshin.substring(0, henshin.lastIndexOf("/") + 1);
				String mFile = henshin.substring(henshin.lastIndexOf("/") + 1, henshin.length());
				HenshinResourceSet resourceSet = new HenshinResourceSet(resource);

				Module module = resourceSet.getModule(mFile, false);

				for (Unit u : new ArrayList<>(module.getUnits()))
					if (u instanceof Rule)
						rules.add((Rule) u);
			}
		}
		Options options = new Options(Options.SILENT);
		options.remove(Options.PRINT_RESULT);
		options.add(Options.DEPENDENCY);
		Set<Reason> cdaResult = new HashSet<>();
		Set<CriticalPair> cpaResult = new HashSet<>();
		for (Rule r : rules) {
			if (r.getLhs().getPACs().isEmpty() && r.getLhs().getNACs().isEmpty() && r.getMultiRules().isEmpty())
				for (Rule r2 : rules)
					if (r2.getLhs().getPACs().isEmpty() && r2.getLhs().getNACs().isEmpty()
							&& r2.getMultiRules().isEmpty()) {
						cda = new CDATester(r, r2, options);
						cdaResult.addAll(cda.getReasons());
						List<Rule> a = new ArrayList<Rule>();
						List<Rule> b = new ArrayList<Rule>();
						a.add(r);
						b.add(r2);
						cpa = new CPATester(a, b, options);
						cpaResult.addAll(cpa.getCriticalPairs());
						int icr = cda.getReasons().size();
						int icp = cpa.getCriticalPairs().size();
						String error = "";
						if (icr != icp) {
							error += "\n\t\ta) " + r.getName() + " --> " + r2.getName() + "\nCDA: " + cda + "\nCPA: "
									+ cpa + "\n" + CDATester.print(cda.getReasons(), false) + "\n__________________\n"
									+ CPATester.printCP(cpa.getCriticalPairs(), false) + "\n";
						} else {
							int matches = cda.compare(cpa.getCriticalPairs()).size();
							if (icr != matches || icp != matches)
								error += "\n\t\tb) " + r.getName() + " --> " + r2.getName()
										+ "\nNot all matches are found: " + matches + " matches of " + icr
										+ " Reasons and " + icp + " Critical Pairs\n";
						}
						if (!error.isEmpty()) {
							System.err.println(error);
							System.out.println(cda.getReasons());
							CPATester.printCP(cpa.getCriticalPairs());
							System.out.println();
						}
						errorMessage += error;
					}
		}
		resultA.put(folders[toTest], cdaResult);
		resultE.put(folders[toTest], cpaResult);
	}

	@AfterClass
	public static void results() {
		System.err
		.println("__________________________________________________________\nErrors found:\n" + errorMessage);
		System.out.println("_________________________________________________________________________\n\nTested: "
				+ resultA.size());
		for (String folder : folders) {
			Set<Reason> cr = resultA.get(folder);
			if (cr != null)
				System.out
						.println("\nFolder Tested: " + folder + "\nFound: " + cr.size() + " Initial Conflict Reasons");
			Set<CriticalPair> cp = resultE.get(folder);
			if (cp != null) {
				System.out.println("Found: " + cp.size() + " Initial Critical Pairs");
			}
		}
	}

	@Test
	public void test1() {
		toTest++;
	}

	@Test
	public void test2() {
		toTest++;
	}

	@Test
	public void test3() {
		toTest++;
	}

	@Test
	public void test4() {
		toTest++;
	}

//	@Test
//	public void test5() {
//		toTest++;
//	}
//
//	@Test
//	public void test6() {
//		toTest++;
//	}
//
//	@Test
//	public void test7() {
//		toTest++;
//	}
//
//	@Test
//	public void test8() {
//		toTest++;
//	}
}
