package org.eclipse.emf.henshin.multicda.cda.framework;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.emf.henshin.multicda.cda.ConflictAnalysis;
import org.eclipse.emf.henshin.multicda.cpa.CDAOptions;
import org.eclipse.emf.henshin.multicda.cpa.CpaByAGG;
import org.eclipse.emf.henshin.multicda.cpa.UnsupportedRuleException;
import org.eclipse.emf.henshin.multicda.cpa.result.CPAResult;
import org.eclipse.emf.henshin.multicda.cpa.result.Conflict;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalElement;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;
import org.eclipse.emf.henshin.multicda.cpa.result.Dependency;
import org.eclipse.emf.henshin.preprocessing.NonDeletingPreparator;
import org.eclipse.emf.henshin.preprocessing.RulePreparator;

/**
 * This worker provides execution, analysis and testing for CPA
 * @author Jevgenij Huebert
 */
public class CpaWorker extends Worker {
	private CpaByAGG cpa;
	private CPAResult result;
	private Options options = new Options();

	public CpaWorker(String henshin, String[] rules, Options... options) {
		this(henshin, rules, rules, options);
	}

	public CpaWorker(List<Rule> rules, Options... options) {
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
	public CpaWorker(String henshin, Options... options) {
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
	public CpaWorker(List<Rule> first, List<Rule> second, Options... options) {
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
	public CpaWorker(String henshin, String[] first, String[] second, Options... options) {

		String r = henshin.substring(0, henshin.lastIndexOf("/") + 1);
		String m = henshin.substring(henshin.lastIndexOf("/") + 1, henshin.length());

		HenshinResourceSet resourceSet = new HenshinResourceSet(r);
		Module module = resourceSet.getModule(m, false);

		List<Rule> f = new ArrayList<Rule>();
		List<Rule> s = new ArrayList<Rule>();
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
			f = new ArrayList<Rule>(s);
		if (s.isEmpty())
			s = new ArrayList<Rule>(f);

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

	private void init(List<Rule> first, List<Rule> second, boolean firstAll, boolean secondAll, Options... opt) {
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
		o.essentialCP = options.is(Options.ESSENTIAL) || options.is(Options.INITIAL);
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

	private void printResult() {
		printCP(getResult());
	}

	@Override
	public Set<CriticalPair> getResult() {
		if (result != null)
			if (options.is(Options.ESSENTIAL))
				return new HashSet<>(result.getEssentialCriticalPairs());
			else if (options.is(Options.INITIAL))
				return new HashSet<>(result.getInitialCriticalPairs());
			else
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

	/**
	 * Print method for CPs
	 * @param cp to print
	 * @param errorOut first boolean describes, that result should be printed red. Second boolean prints the result in the consle.
	 * @return to be printed string
	 */
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