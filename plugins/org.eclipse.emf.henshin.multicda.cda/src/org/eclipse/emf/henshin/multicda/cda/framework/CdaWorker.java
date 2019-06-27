package org.eclipse.emf.henshin.multicda.cda.framework;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.emf.henshin.multicda.cda.ConflictAnalysis;
import org.eclipse.emf.henshin.multicda.cda.DependencyAnalysis;
import org.eclipse.emf.henshin.multicda.cda.MultiGranularAnalysis;
import org.eclipse.emf.henshin.multicda.cda.Utils;
import org.eclipse.emf.henshin.multicda.cda.framework.Condition.AtomSize;
import org.eclipse.emf.henshin.multicda.cda.framework.Condition.Conditions;
import org.eclipse.emf.henshin.multicda.cda.framework.Condition.ConditionsSet;
import org.eclipse.emf.henshin.multicda.cda.framework.Condition.MinimalSize;
import org.eclipse.emf.henshin.multicda.cda.framework.Condition.Provable;
import org.eclipse.emf.henshin.multicda.cda.framework.Condition.ReasonSize;
import org.eclipse.emf.henshin.multicda.cda.framework.Condition.StateProvider;
import org.eclipse.emf.henshin.multicda.cda.framework.Condition.State;
import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;
import org.eclipse.emf.henshin.multicda.cda.units.Span;
import org.eclipse.emf.henshin.multicda.cda.units.SymmetricReason;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;
import org.eclipse.emf.henshin.preprocessing.RulePreparator;

/**
 * This worker provides execution, analysis and testing for Multi CDA
 * @author Jevgenij Huebert
 */
public class CdaWorker extends Worker {
	private MultiGranularAnalysis analyser;
	public Set<Rule> first = new HashSet<>();
	public Set<Rule> second = new HashSet<>();
	private Set<Reason> minimalReasons = new TreeSet<>();
	private Set<Reason> reasons = new TreeSet<>();
	private Set<Atom> atoms = new HashSet<>();
	private int iCheckedCounter = 0;
	private Options options;
	private boolean reseted = true;
	public static Map<String, Set<String>> ignore = new HashMap<>();
	private Module module;

	public CdaWorker(String henshin, String rule, Options... options) {
		this(henshin, rule, rule, options);
	}

	/**
	 * Executes all rules containing in the henshin file.
	 * 
	 * @param henshin
	 *            path to the Henshin file
	 * @param options
	 *            1:dependency, 2:prepare, 3:nonDeletionSecondRule, 4:printHeader,
	 *            5:printResult, 6:silent
	 */
	public CdaWorker(String henshin, Options... options) {
		this(henshin, null, null, options);
	}

	/**
	 * Run CDA
	 * 
	 * @param henshin
	 *            path to the henshin file
	 * @param firstRule
	 *            name of the first rule
	 * @param secondRule
	 *            name of the second rule
	 * @param options
	 *            1:dependency, 2:prepare, 3:nonDeletionSecondRule, 4:printHeader,
	 *            5:printResult, 6:silent
	 */
	public CdaWorker(String henshin, String firstRule, String secondRule, Options... options) {
		this.options = new Options();
		if (options.length != 0)
			this.options = options[0];
		if (henshin.isEmpty()
				|| ((firstRule != null && !firstRule.isEmpty()) ^ (secondRule != null && !secondRule.isEmpty())))
			return;
		HenshinResourceSet resourceSet = new HenshinResourceSet(henshin.substring(0, henshin.lastIndexOf("/") + 1));
		try {
			module = resourceSet.getModule(henshin.substring(henshin.lastIndexOf("/") + 1, henshin.length()), false);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}
		if (firstRule == null) {
			firstRule = "All";
			for (Unit u : module.getUnits())
				if (u instanceof Rule)
					first.add((Rule) u);
		} else
			first.add((Rule) module.getUnit(firstRule));
		if (secondRule == null) {
			secondRule = "All";
			for (Unit u : module.getUnits())
				if (u instanceof Rule)
					second.add((Rule) u);
		} else
			second.add((Rule) module.getUnit(secondRule));

		if (first == null)
			System.err.println("Rule " + firstRule + " not found");
		if (second == null)
			System.err.println("Rule " + secondRule + " not found");
		if (first == null || second == null)
			return;
		for (Rule r1 : first)
			for (Rule r2 : second) {
				if (r1 != null && r2 != null
						&& (ignore.get(r1.getName()) == null || !ignore.get(r1.getName()).contains(r2.getName()))) {
					CdaWorker tester = new CdaWorker(r1, r2, options);
					analyser = tester.analyser;
					NAME = tester.NAME;
					minimalReasons.addAll(tester.getMinimalReasons());
					atoms.addAll(tester.getAtoms());
					reasons.addAll(tester.getResult());
				}
			}
		reseted = false;
	}

	/**
	 * Run CDA
	 * 
	 * @param first
	 *            rule
	 * @param second
	 *            rule
	 * @param options
	 *            1:dependency, 2:prepare, 3:nonDeletionSecondRule, 4:printHeader,
	 *            5:printResult, 6:silent
	 */
	public CdaWorker(Rule first, Rule second, Options... options) {
		this.first.add(first);
		this.second.add(second);
		this.options = new Options();
		if (options.length != 0)
			this.options = options[0];
		init();
	}

	protected void init() {
		reseted = false;
		NAME = "CDA Tester";
		Rule f = first.iterator().next();
		Rule s = second.iterator().next();
		assertTrue(print("No first rules are found", true, false), f != null);
		assertTrue(print("No second rules are found", true, false), s != null);

		if (options.is(Options.PREPARE)) {
			if (first != second) {
				f = RulePreparator.prepareRule(f);
				s = RulePreparator.prepareRule(s);
			} else {
				f = RulePreparator.prepareRule(f);
				s = f;
			}
		}

		if (options.is(Options.DEPENDENCY))
			analyser = new DependencyAnalysis(f, s);
		else
			analyser = new ConflictAnalysis(f, s);

		atoms = new HashSet<>(analyser.computeAtoms());
		minimalReasons = new HashSet<>(analyser.computeResultsCoarse());
		reasons = new HashSet<>(analyser.computeResultsFine());
		for (Reason r : reasons)
			if (r.is("CFCR")) {
				System.out.println(r + "\n" + r.getRule1().getName() + "\n" + r.getRule2().getName());
			}
		if (options.is(Options.PRINT_HEADER) && (!options.is(Options.PRINT_WHEN_RESULT) || !reasons.isEmpty())) {
			System.out.println("\n\t\t  " + f.getName() + " --> " + s.getName() + "\n\t\t\tCDA");
			print(options.toCDAString() + "\n");
		}
		if (options.is(Options.PRINT_RESULT) && (!options.is(Options.PRINT_WHEN_RESULT) || !reasons.isEmpty())) {
			CdaWorker.print(new TreeSet<>(reasons), false, options.is(Options.PRINT_COMPLETE));
			print();
			System.out.println();
		}
	}

	public Set<Reason> getMinimalReasons() {
		return minimalReasons;
	}

	/**
	 * @return
	 */
	@Override
	public Set<Reason> getResult() {
		return reasons;
	}

	/**
	 * @return
	 */
	public Set<Atom> getAtoms() {
		return atoms;
	}

	public boolean check(Provable... conditions) {
		if (reseted)
			return false;
		return check(true, false, false, conditions);
	}

	private boolean check(boolean printError, boolean print, boolean assertError, Provable... conditions) {
		boolean result = false;
		for (Provable p : conditions) {
			result = iChecker(p, printError);
			String ruleNames = getRuleNames();
			String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
			if (assertError)
				assertTrue(methodName + ": " + p + "   '" + ruleNames + "' - failed", result);
			if (result && print)
				System.out.println(methodName + ": " + p + "   '" + ruleNames + "' - succeed");
			if (p instanceof Conditions && !result && printError)
				System.err.println(methodName + ": " + p + "   '" + ruleNames + "' - failed");
		}
		return result;
	}

	private String getRuleNames() {
		String result = "";
		for (Rule r : first)
			if (r == null)
				result += ", first rule not found";
			else
				result += ", \"" + r.getName() + "\"";
		result = result.substring(2) + ", ";
		String temp = "";
		for (Rule r : second)
			if (r == null)
				temp += ", second rule not found";
			else
				temp += ", \"" + r.getName() + "\"";
		return result + (temp.isEmpty() ? "" : temp.substring(2));
	}

	private boolean iChecker(Provable conditions, boolean printError) {
		String methodName = Thread.currentThread().getStackTrace()[4].getMethodName();
		String ruleNames = getRuleNames();
		if (conditions instanceof ReasonSize) {
			ReasonSize rs = (ReasonSize) conditions;
			boolean failed = false;
			if (rs instanceof AtomSize)
				failed = !rs.proove(getAtoms().size());
			else if (rs instanceof MinimalSize)
				failed = !rs.proove(getMinimalReasons().size());
			else
				failed = !rs.proove(getResult().size());
			if (failed) {
				if (printError)
					System.err.println(
							methodName + ": " + conditions + "   '" + ruleNames + "' - failed:  not " + conditions
									+ " but " + getResult().size() + "\n" + print(getResult(), false, true, false));
				return false;
			} else
				return true;
		}
		if (conditions instanceof ConditionsSet) {
			Set<Span> spans = new HashSet<>();
			ConditionsSet cs = (ConditionsSet) conditions;
			if (cs.atoms)
				spans.addAll(atoms);
			if (cs.minimals)
				spans.addAll(minimalReasons);
			if (cs.reasons)
				spans.addAll(reasons);
			Set<Span> remained = new HashSet<>();
			for (Span r : spans)
				if (r instanceof Atom && cs.atoms
						|| (r instanceof Reason && ((Reason) r).isMinimalReason() && cs.minimals)
						|| (r instanceof Reason && !((Reason) r).isMinimalReason() && cs.reasons))
					if (!cs.proove(r))
						remained.add(r);
			if (!cs.getRest().isEmpty()) {
				boolean error = false;
				for (Provable p : cs.getRest())
					if ((p.getState().is(StateProvider.ATOM) && cs.atoms) || (p.getState().is(StateProvider.MINIMAL) && cs.minimals)
							|| (!p.getState().is(StateProvider.MINIMAL) && !p.getState().is(StateProvider.ATOM) && cs.reasons)) {
						error = true;
						if (printError)
							System.err.println(methodName + ": " + p + "\n - failed: not found!");
					}
				return !error;
			} else if (printError && !remained.isEmpty()) {
				System.err.println(methodName + ": folowing Reasons were not defined in your conditions:\n"
						+ print(remained, false, false, false));
			}
			return true;
		} else if (conditions instanceof Conditions)
			if (((Conditions) conditions).state.is(State.Atom)) {
				for (Atom a : atoms)
					if (conditions.proove(a))
						return true;
			} else if (((Conditions) conditions).state.is(State.Minimal)) {
				for (Reason m : minimalReasons)
					if (conditions.proove(m))
						return true;
			} else
				for (Reason r : getResult()) {
					if (conditions.proove(r))
						return true;
				}
		else if (printError)
			System.err.println(methodName + ": Condition '" + conditions + "' was not found in '" + ruleNames + "'");
		return false;
	}

	private Set<? extends Reason> getSymmetricReasons() {
		Set<Reason> result = new HashSet<>();
		for (Reason r : getResult())
			if (r instanceof SymmetricReason)
				result.add(r);

		return result;
	}

	private Set<? extends Reason> getAsymmetricReasons() {
		Set<Reason> result = new HashSet<>();
		for (Reason r : getResult())
			if (!(r instanceof SymmetricReason))
				result.add(r);

		return result;
	}

	/**
	 * Resets this tester. After that you can check your conditions again.
	 */
	public void reset() {
		reseted = true;
		reasons.clear();
		minimalReasons.clear();
		atoms.clear();
		cps = null;
	}

	/**
	 * Prints and returns the printed String of the given reasons
	 * 
	 * @param reasons
	 *            to print
	 * @param errorCompleteOut
	 *            describes if the printed String should be an Error and if the
	 *            output String should be printed at all or just returned back.
	 *            default settings are: error = false, out = true
	 * @return printed String of reasons
	 */
	public static String print(Set<? extends Span> reasons, boolean... errorCompleteOut) {
		List<Span> result = new ArrayList<>();
		result.addAll(reasons);
		return print(result, errorCompleteOut);
	}

	public static String print(List<? extends Span> reasons, boolean... errorCompleteOut) {
		String result = "";
		boolean error = errorCompleteOut.length != 0 && errorCompleteOut[0];
		boolean out = errorCompleteOut.length < 3 || errorCompleteOut[2];
		for (Span reason : reasons)
			result += reason + "\n";
		if (out)
			if (error)
				System.err.println("\n" + result + "\n");
			else
				System.out.println(result);
		return result;

	}

	/**
	 * Returns the Rules in a List
	 * 
	 * @return list with rule1 and rule2
	 */
	public Set<Rule> getRules() {
		Set<Rule> result = new HashSet<>(first);
		result.addAll(second);
		return result;
	}

	private Set<CriticalPair> cps = null;

	/**
	 * Compares reasons with given Critical Pairs. If a match was not found it can
	 * be asserted.
	 * 
	 * @param cps
	 *            to compare
	 * @param asserts
	 *            true if an assertion should be done by failed comparison
	 * @return Map of matched critical Pairs and Reasons
	 */
	public Map<CriticalPair, Reason> compare(Set<CriticalPair> cps, boolean... assertsPrint) {
		this.cps = cps;
		comparedResults = Utils.compare(cps, getResult());
		if (assertsPrint.length < 2 || assertsPrint[1])
			if (comparedResults.size() == getResult().size() && comparedResults.size() == cps.size())
				System.out.println("Comparison finished normaly...");
			else
				System.err.println("Not all CPs are perfectly matched with CRs.\nFound matches: "
						+ comparedResults.size() + " of " + getResult().size() + " CRs and " + cps.size() + " CPs");
		assertTrue(
				"\nNot all CPs are perfectly matched with CRs.\nFound matches: " + comparedResults.size() + " of "
						+ getResult().size() + " CRs and " + cps.size() + " CPs",
				(assertsPrint.length == 0 || !assertsPrint[0])
						|| comparedResults.size() == getResult().size() && comparedResults.size() == cps.size());
		return comparedResults;
	}

	@Override
	public void ready() {
		int iRest = reasons.size() - iCheckedCounter;
		if (iRest > 0)
			print("Not all Conflict Reasons are tested. " + iRest + (iRest == 1 ? " is" : " are") + " remaining.");
		if (comparedResults != null)
			print("Found matches: " + comparedResults.size() + " of " + getResult().size() + " Reasons and "
					+ cps.size() + " Critical Pairs",
					comparedResults.size() != getResult().size() || comparedResults.size() != cps.size());
		super.ready();
		reset();
	}

	@Override
	public String toString() {
		if (analyser instanceof ConflictAnalysis)
			return reasons.size() + (reasons.size() > 1 ? " Conflict Reasons" : " Conflict Reason");

		if (analyser instanceof DependencyAnalysis)
			return reasons.size() + (reasons.size() > 1 ? " Dependency Reasons" : " Dependency Reason");
		else
			return super.toString();

	}

	public Options getOptions() {
		return options;
	}

	public Module getModule() {
		return module;
	}
}