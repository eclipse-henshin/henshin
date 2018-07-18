package org.eclipse.emf.henshin.multicda.cda.tester;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.emf.henshin.multicda.cda.ConflictAnalysis;
import org.eclipse.emf.henshin.multicda.cda.runner.RulePreparator;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.CP;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.Conditions;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.CriticalPairConditions;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.CriticalPairRightConditions;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.ICP;
import org.eclipse.emf.henshin.multicda.cda.tester.Tester.Options;
import org.eclipse.emf.henshin.multicda.cpa.CDAOptions;
import org.eclipse.emf.henshin.multicda.cpa.CpaByAGG;
import org.eclipse.emf.henshin.multicda.cpa.UnsupportedRuleException;
import org.eclipse.emf.henshin.multicda.cpa.result.CPAResult;
import org.eclipse.emf.henshin.multicda.cpa.result.Conflict;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalElement;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;
import org.eclipse.emf.henshin.multicda.cpa.result.Dependency;
import org.eclipse.emf.henshin.preprocessing.NonDeletingPreparator;

public class CPATester extends Tester {
	private CpaByAGG cpa;
	private CPAResult result;
	private String checked = "";
	private int checkedCounter = 0;
	private int checkedRightCounter = 0;
	private Options options = new Options();

	public CPATester(String henshin, String[] rules, Options... options) {
		this(henshin, rules, rules, options);
	}

	public CPATester(Set<Rule> rules, Options... options) {
		this(rules, rules, options);
	}

	/**
	 * All rules in the Henshin file will be analyzed with each other.
	 * 
	 * @param henshin
	 *            path to the Henshin file.
	 * @param options
	 *            1:dependency, 2:essential, 3:prepare, 4:noneDeletionSecondRule,
	 *            5:printHeader, 6:printResults, 7:silent
	 */
	public CPATester(String henshin, Options... options) {
		this(henshin, new String[] {}, new String[] {}, options);
	}

	/**
	 * All rules from first list will be analyzed with all rules from the second
	 * list. The rule lists should not be empty!
	 * 
	 * @param first
	 *            the first rules as list of Rules
	 * @param second
	 *            the second rules as list of Rules
	 * @param options
	 *            1:dependency, 2:essential, 3:prepare, 4:noneDeletionSecondRule,
	 *            5:printHeader, 6:printResults, 7:silent
	 */
	public CPATester(Set<Rule> first, Set<Rule> second, Options... options) {
		init(first, second, false, false, options);
	}

	/**
	 * All rules from first list will be analyzed with all rules from the second
	 * list. If a List of rules is empty, it means all rules of the Henshin file
	 * will be analysed!
	 * 
	 * @param henshin
	 *            Path to the henshin file
	 * @param first
	 *            the first rule as string array
	 * @param second
	 *            the second rule as string array
	 * @param options
	 *            1:dependency, 2:essential, 3:prepare, 4:noneDeletionSecondRule,
	 *            5:printHeader, 6:printResults, 7:silent
	 */
	public CPATester(String henshin, String[] first, String[] second, Options... options) {

		String r = henshin.substring(0, henshin.lastIndexOf("/") + 1);
		String m = henshin.substring(henshin.lastIndexOf("/") + 1, henshin.length());

		HenshinResourceSet resourceSet = new HenshinResourceSet(r);
		Module module = resourceSet.getModule(m, false);

		Set<Rule> f = new HashSet<Rule>();
		Set<Rule> s = new HashSet<Rule>();
		for (String firstRule : first) {
			Rule rule = (Rule) module.getUnit(firstRule);
			if (rule == null) {
				System.err.println("Rule " + firstRule + " not found");
				return;
			}
			f.add(rule);
		}
		for (String secondRule : second) {
			Rule rule = (Rule) module.getUnit(secondRule);
			if (rule == null) {
				System.err.println("Rule " + secondRule + " not found");
				return;
			}
			s.add(rule);
		}
		if (f.isEmpty())
			f = new HashSet<>(s);
		if (s.isEmpty())
			s = new HashSet<>(f);

		boolean firstAll = false;
		boolean secondAll = false;
		if (f.isEmpty()) {
			firstAll = true;
			for (Unit u : module.getUnits())
				if (u instanceof Rule)
					f.add((Rule) u);
		}
		if (s.isEmpty()) {
			secondAll = true;
			for (Unit u : module.getUnits())
				if (u instanceof Rule)
					s.add((Rule) u);
		}
		init(f, s, firstAll, secondAll, options);
	}

	private void init(Set<Rule> first, Set<Rule> second, boolean firstAll, boolean secondAll, Options... opt) {
		if (opt.length != 0)
			options = opt[0];
		String ff = "", ss = "";
		if (firstAll)
			ff = "All";
		else
			for (Rule nameF : first)
				ff += (ff.isEmpty() ? "" : ", ") + nameF.getName();
		if (secondAll)
			ss = "All";
		else
			for (Rule nameS : second)
				ss += (ss.isEmpty() ? "" : ", ") + nameS.getName();
		for (Rule r1 : first)
			r1 = ConflictAnalysis.prepare(r1);
		for (Rule r2 : second)
			r2 = ConflictAnalysis.prepare(r2);

		CDAOptions o = new CDAOptions();
		o.essentialCP = options.is(Options.ESSENTIAL);
		o.setReduceSameRuleAndSameMatch(false);
		o.setIgnoreSameRules(false);
		o.setIgnoreMultiplicities(true);

		cpa = new CpaByAGG();
		NAME = "CPA Tester";
		try {

			if (options.is(Options.PREPARE)) {
				first = RulePreparator.prepareRule(first);
				second = RulePreparator.prepareRule(second);
			}
			if (options.is(Options.NONE_DELETION_SECOND_RULE))
				second = NonDeletingPreparator.prepareNoneDeletingsVersionsRules(second);
			cpa.init(new HashSet<>(first), new HashSet<>(second), o);
		} catch (UnsupportedRuleException e) {
			System.err.println(e.getMessage());
		}

		PrintStream original = System.out;
		if (options.is(Options.SILENT)) {
			System.setOut(new NullPrintStream());
		}

		if (options.is(Options.DEPENDENCY))
			result = cpa.runDependencyAnalysis();
		else
			result = cpa.runConflictAnalysis();

		if (options.is(Options.SILENT))
			System.setOut(original);

		if (options.is(Options.PRINT_HEADER) && (!options.is(Options.PRINT_WHEN_RESULT) || !getResult().isEmpty())) {
			System.out.println("\n\t\t  " + ff + " --> " + ss + "\n\t\t\tCPA "
					+ (options.is(Options.ESSENTIAL) ? "Essential" : ""));
			print(options + "\n");
		}
		if (options.is(Options.PRINT_HEADER))
			if (options.is(Options.PRINT_RESULT)
					&& (!options.is(Options.PRINT_WHEN_RESULT) || !getResult().isEmpty())) {
				printResult();
				print();
				System.out.println();
			}
	}

	@Override
	public boolean check(Class<?> type, Condition... conditions) {
		List<Condition> edgeNode = new ArrayList<>();
		for (Condition condition : conditions) {
			if (condition instanceof CP) {
				if (!condition.proove(result.getCriticalPairs().size()))
					return false;
				print(condition + " accepted");
			} else if (condition instanceof Edge || condition instanceof Node)
				edgeNode.add(condition);
			else if (condition instanceof ICP && !condition.proove(result.getCriticalPairs().size()))
				return false;
			else
				System.err.println("This condition don't belong here --> " + condition);
		}
		if (edgeNode.size() == 0)
			return true;

		if (type == Conditions.class || type == CriticalPairConditions.class) {
			for (CriticalPair criticalPair : result) {
				Set<EObject> elements = new HashSet<>();
				for (CriticalElement e : criticalPair.getCriticalElements()) {
					elements.add(e.elementInFirstRule);
					elements.add(e.elementInSecondRule);
				}

				// System.out.println(elements);
				if (!checked.contains("Initial" + elements + "") && checkReasons(elements, edgeNode)) {
					print("Found: " + elements + "\twith " + type.getSimpleName() + " " + getContent(conditions));
					checked += "Initial" + elements + "\n";
					checkedCounter++;
					return true;
				}
			}
		}
		if (type == Conditions.class || type == CriticalPairRightConditions.class) {
			for (CriticalPair criticalPair : result) {
				Set<EObject> elements = new HashSet<>();
				for (CriticalElement e : criticalPair.getCriticalElements()) {
					elements.add(e.elementInSecondRule);
					elements.add(e.elementInFirstRule);
				}

				// System.out.println(elements);
				if (!checked.contains("Minimal" + elements + "") && checkReasons(elements, edgeNode)) {
					print("Found Right: " + elements + "\twith " + type.getSimpleName() + " " + getContent(conditions));
					checked += "Minimal" + elements + "\n";
					checkedRightCounter++;
					return true;
				}
			}
		}
		return false;
	}

	private void printResult() {
		printCP(getResult());
	}

	public Set<CriticalPair> getResult() {
		if (result != null)
			return new HashSet<>(result.getCriticalPairs());
		return new HashSet<>();
	}

	@Override
	public String toString() {
		if (!options.is(Options.DEPENDENCY))
			return getResult().size() + " Critical Pairs.";
		else
			return getResult().size() + " Dependency Critical Pairs.";
	}

	public static String printCP(Set<CriticalPair> cp, boolean... errorOut) {
		String result = "";
		for (CriticalPair criticalPair : cp) {
			criticalPair.getMinimalModel();
			String criticalPairKind = "";
			if (criticalPair instanceof Conflict) {
				criticalPairKind = ((Conflict) criticalPair).getConflictKind().toString();
			} else if (criticalPair instanceof Dependency) {
				criticalPairKind = ((Dependency) criticalPair).getDependencyKind().toString();
			}
			String r = "";
			for (CriticalElement e : criticalPair.getCriticalElements())
				r += ", [" + e + "]";
			result += criticalPairKind + ": " + r.substring(2) + "\n";
		}
		if (errorOut.length <= 1 || errorOut.length > 1 && errorOut[1])
			if (errorOut.length == 0 || errorOut.length != 0 && !errorOut[0])
				System.out.println(result);
			else
				System.err.println("\n" + result + "\n");
		return result;
	}

}

class NullPrintStream extends PrintStream {

	public NullPrintStream() {
		super(new NullByteArrayOutputStream());
	}

	private static class NullByteArrayOutputStream extends ByteArrayOutputStream {

		@Override
		public void write(int b) {
			// do nothing
		}

		@Override
		public void write(byte[] b, int off, int len) {
			// do nothing
		}

		@Override
		public void writeTo(OutputStream out) throws IOException {
			// do nothing
		}

	}

}