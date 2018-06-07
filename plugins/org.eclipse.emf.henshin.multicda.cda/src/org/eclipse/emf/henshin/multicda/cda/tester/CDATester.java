package org.eclipse.emf.henshin.multicda.cda.tester;

/**
 * @author Jevgenij Huebert
 */
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.emf.henshin.multicda.cda.ConflictAnalysis;
import org.eclipse.emf.henshin.multicda.cda.DependencyAnalysis;
import org.eclipse.emf.henshin.multicda.cda.MultiGranularAnalysis;
import org.eclipse.emf.henshin.multicda.cda.Utils;
import org.eclipse.emf.henshin.multicda.cda.runner.RulePreparator;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.CDDR;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.CDDRConditions;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.CRDR;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.CRDRConditions;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.CUDR;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.CUDRConditions;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.Conditions;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.DDCR;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.DDCRConditions;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.DRCR;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.DRCRConditions;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.DUCR;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.DUCRConditions;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.Edge;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.MCR;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.MinimalReasonConditions;
import org.eclipse.emf.henshin.multicda.cda.tester.Condition.Node;
import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.DoubleSpan;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;
import org.eclipse.emf.henshin.multicda.cda.units.Span;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;

public class CDATester extends Tester {
	public boolean PrintFounds = true;
	private MultiGranularAnalysis analyser;
	Set<Rule> first = new HashSet<>();
	public Set<Rule> second = new HashSet<>();
	private Set<MinimalReason> minimalReasons = new HashSet<>();
	private Set<Reason> reasons = new HashSet<>();
	private Set<Atom> atoms = new HashSet<>();
	private String checked = "";
	private int iCheckedCounter = 0;
	private Options options;

	public CDATester(String henshin, String rule, Options... options) {
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
	public CDATester(String henshin, Options... options) {
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
	public CDATester(String henshin, String firstRule, String secondRule, Options... options) {
		this.options = new Options();
		if (options.length != 0)
			this.options = options[0];
		if (henshin.isEmpty()
				|| ((firstRule != null && !firstRule.isEmpty()) ^ (secondRule != null && !secondRule.isEmpty())))
			return;
		HenshinResourceSet resourceSet = new HenshinResourceSet(henshin.substring(0, henshin.lastIndexOf("/") + 1));
		Module module = null;
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

		for (Rule r1 : first)
			for (Rule r2 : second) {
				if (first == null)
					System.err.println("Rule " + firstRule + " not found");
				if (second == null)
					System.err.println("Rule " + secondRule + " not found");
				if (first == null || second == null)
					return;
				CDATester tester = new CDATester(r1, r2, options);
				analyser = tester.analyser;
				NAME = tester.NAME;
				minimalReasons.addAll(tester.getMinimalReasons());
				atoms.addAll(tester.getAtoms());
				reasons.addAll(tester.getResult());
			}
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
	public CDATester(Rule first, Rule second, Options... options) {
		this.first.add(first);
		this.second.add(second);
		this.options = new Options();
		if (options.length != 0)
			this.options = options[0];
		init();
	}

	protected void init() {
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

		// atoms = new HashSet<>(analyser.computeAtoms());
		// minimalReasons = new HashSet<>(analyser.computeResultsCoarse());
		reasons = new HashSet<>(analyser.computeResultsFine());
		if (options.is(Options.PRINT_HEADER) && (!options.is(Options.PRINT_WHEN_RESULT) || !reasons.isEmpty())) {
			System.out.println("\n\t\t  " + f.getName() + " --> " + s.getName() + "\n\t\t\tCDA");
			print(options.toCDAString() + "\n");
		}
		if (options.is(Options.PRINT_RESULT) && (!options.is(Options.PRINT_WHEN_RESULT) || !reasons.isEmpty())) {
			CDATester.print(new TreeSet<>(reasons), false, options.is(Options.PRINT_COMPLETE));
			print();
			System.out.println();
		}
	}

	public Set<MinimalReason> getMinimalReasons() {
		return minimalReasons;
	}

	/**
	 * @return
	 */
	public Set<Reason> getResult() {
		return reasons;
	}

	/**
	 * @return
	 */
	public Set<Atom> getAtoms() {
		return atoms;
	}

	@Override
	public boolean check(Condition condition) {
		return check(condition, true);
	}

	private List<Condition> edgeNode = new ArrayList<>();

	private boolean check(Condition condition, boolean printError) {
		if (condition instanceof Conditions) {
			Conditions conditions = (Conditions) condition;
			Class<?> type = condition.getClass();
			Condition[] conditionsElements = conditions.getValues();
			edgeNode = new ArrayList<>();
			for (Condition conElement : conditionsElements)
				check(conElement, printError);

			if (edgeNode.size() == 0)
				return true;
			if (type == Conditions.class || type == MinimalReasonConditions.class) {
				for (Span deleteUseConflictReason : minimalReasons) {
					if (deleteUseConflictReason instanceof Reason) {
						MinimalConflictReason minimalReason = (MinimalConflictReason) deleteUseConflictReason;
						if (iChecker(minimalReason, type, condition.name, edgeNode))
							return true;
					}
				}
			}
			// ______________________new conditions check________________________
			if ((type == DRCRConditions.class || type == DUCRConditions.class || type == DDCRConditions.class)
					&& options.is(Options.DEPENDENCY)) {
				if (printError)
					System.err.println("Condition type: '" + type + "' is not a Dependency Reason");
				return false;
			}
			if ((type == CRDRConditions.class || type == CUDRConditions.class || type == CDDRConditions.class)
					&& !options.is(Options.DEPENDENCY)) {
				if (printError)
					System.err.println("Condition type: '" + type + "' is not a Conflict Reason");
				return false;
			}
			if (type == Conditions.class || type == DRCRConditions.class || type == DUCRConditions.class
					|| type == CRDRConditions.class || type == CUDRConditions.class)
				for (Reason drReason : getDRReasons())
					if (iChecker(drReason, type, condition.name, edgeNode))
						return true;
			if (type == Conditions.class || type == DDCRConditions.class || type == DUCRConditions.class
					|| type == CDDRConditions.class || type == CUDRConditions.class)
				for (Reason ddReason : getDDReasons())
					if (iChecker(ddReason, type, condition.name, edgeNode))
						return true;
			if (printError)
				System.err.println("\n[" + condition + "] not found!\n");
			return false;
		} else {
			if (condition instanceof CUDR && options.is(Options.DEPENDENCY)
					|| condition instanceof DUCR && !options.is(Options.DEPENDENCY)) {
				if (!condition.proove(getResult().size())) {
					print(condition + " failed", true);
					return false;
				}
				print(condition + " accepted");
			} else if (condition instanceof CRDR && options.is(Options.DEPENDENCY)
					|| condition instanceof DRCR && !options.is(Options.DEPENDENCY)) {
				if (!condition.proove(getDRReasons().size())) {
					print(condition + " failed", true);
					return false;
				}
				print(condition + " accepted");
			} else if (condition instanceof CDDR && options.is(Options.DEPENDENCY)
					|| condition instanceof DDCR && !options.is(Options.DEPENDENCY)) {
				if (!condition.proove(getDDReasons().size())) {
					print(condition + " failed", true);
					return false;
				}
				print(condition + " accepted");
			} else if (condition instanceof MCR) {
				if (!condition.proove(minimalReasons.size())) {
					print(condition + " failed", true);
					return false;
				}
				print(condition + " accepted");
			} else if (condition instanceof Edge || condition instanceof Node)
				edgeNode.add(condition);
			else if (printError)
				System.err.println("This condition don't belong here --> " + condition);
			return true;
		}

	}

	private Set<? extends Reason> getDDReasons() {
		Set<Reason> result = new HashSet<>();
		for (Reason r : getResult())
			if (r instanceof DoubleSpan && ((DoubleSpan) r).isDoubleSpan())
				result.add(r);

		return result;
	}

	private Set<? extends Reason> getDRReasons() {
		Set<Reason> result = new HashSet<>();
		for (Reason r : getResult())
			if (!(r instanceof DoubleSpan) || !((DoubleSpan) r).isDoubleSpan())
				result.add(r);

		return result;
	}

	private boolean iChecker(Reason conflictReason, Class<?> type, String shortName, List<Condition> edgeNodes) {
		Set<EObject> elements = new HashSet<EObject>(conflictReason.getDeletionElementsInRule1());
		if (!checked.contains(conflictReason.getClass().getSimpleName() + "" + elements)
				&& checkReasons(elements, edgeNodes)) {
			print("Found " + shortName + ": " + elements + "\twith " + type.getSimpleName() + " " + edgeNodes);
			checked += conflictReason.getClass().getSimpleName() + "" + elements + "\n";
			iCheckedCounter++;
			return true;
		}
		return false;
	}

	/**
	 * Resets this tester. After that you can check your conditions again.
	 */
	public void reset() {
		iCheckedCounter = 0;
		checked = "";
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
	public static String print(Set<? extends Reason> reasons, boolean... errorCompleteOut) {
		String result = "";
		boolean error = errorCompleteOut.length != 0 && errorCompleteOut[0];
		boolean complete = errorCompleteOut.length < 2 || errorCompleteOut[1];
		boolean out = errorCompleteOut.length < 3 || errorCompleteOut[2];
		for (Reason reason : reasons)
			result += reason.toS2String(complete) + "\n";
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
	public Map<CriticalPair, Reason> compare(Set<CriticalPair> cps, boolean... asserts) {
		this.cps = cps;
		comparedResults = Utils.compare(cps, getResult());
		assertTrue(
				"\nNot all CPs are perfectly matched with CRs.\nFound matches: " + comparedResults.size() + " of "
						+ getResult().size() + " CRs and " + cps.size() + " CPs",
				(asserts.length == 0 || !asserts[0])
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
}