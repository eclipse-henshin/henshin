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
import org.eclipse.emf.henshin.multicda.cda.ReasonFactory;
import org.eclipse.emf.henshin.multicda.cda.Utils;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.DeleteDeleteConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.DeleteReadConflictReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.CreateDeleteDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason.CreateReadDependencyReason;
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
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;
import org.eclipse.emf.henshin.multicda.cda.units.Span;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;

public class CDATester extends Tester {
	public boolean PrintFounds = true;
	private MultiGranularAnalysis analyser;
	private Rule first;
	private Rule second;
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
	 * This is a fast execution constructor and can be used with henshin files with just one Rule. This Rule will be executet with itself.
	 * 
	 * @param henshin path to the Henshin file with just one Rule
	 * @param options 1:dependency, 2:prepare, 3:nonDeletionSecondRule, 4:printHeader, 5:printResult, 6:silent
	 */
	public CDATester(String henshin, Options... options) {
		this(henshin, null, null, options);
	}

	/**
	 * Run CDA
	 * 
	 * @param henshin path to the henshin file
	 * @param firstRule name of the first rule
	 * @param secondRule name of the second rule
	 * @param options 1:dependency, 2:prepare, 3:nonDeletionSecondRule, 4:printHeader, 5:printResult, 6:silent
	 */
	public CDATester(String henshin, String firstRule, String secondRule, Options... options) {
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
		if (firstRule == null || secondRule == "") {
			firstRule = "All";
			secondRule = "All";
			int time = 0;
			for (Unit u : module.getUnits()) {
				if (u instanceof Rule) {
					if (time == 1) {
						System.err.println(
								"There are more than one rule in this module. In this case please write one or two rules that should be analysed.");
						return;
					}
					time = 1;
					first = (Rule) u;
					second = (Rule) u;
				}
			}
		} else {
			first = (Rule) module.getUnit(firstRule);
			second = (Rule) module.getUnit(secondRule);
			if (first == null)
				System.err.println("Rule " + firstRule + " not found");
			if (second == null)
				System.err.println("Rule " + secondRule + " not found");
			if (first == null || second == null)
				return;
		}
		init(options);
	}

	/**
	 * Run CDA
	 * 
	 * @param first rule
	 * @param second rule
	 * @param options 1:dependency, 2:prepare, 3:nonDeletionSecondRule, 4:printHeader, 5:printResult, 6:silent
	 */
	public CDATester(Rule first, Rule second, Options... options) {
		this.first = first;
		this.second = second;
		init(options);
	}

	protected void init(Options... opt) {
		options = new Options();
		if (opt.length != 0)
			options = opt[0];
		NAME = "CDA Tester";
		if (options.is(Options.PRINT_HEADER))
			System.out.println("\n\t\t  " + first.getName() + " --> " + second.getName() + "\n\t\t\tCDA");
		assertTrue(print("First rule not found", true, false), first != null && first instanceof Rule);
		assertTrue(print("Second rule not found", true, false), second != null && second instanceof Rule);

		if (options.is(Options.PREPARE)) {
			if (first != second) {
				first = RulePreparator.prepareRule(first);
				second = RulePreparator.prepareRule(second);
			} else {
				first = RulePreparator.prepareRule(first);
				second = first;
			}
		}

		if (options.is(Options.DEPENDENCY))
			analyser = new DependencyAnalysis(first, second);
		else
			analyser = new ConflictAnalysis(first, second);
//		atoms = new HashSet<>(analyser.computeAtoms());
//		minimalReasons = new HashSet<>(analyser.computeResultsCoarse());
		reasons = new HashSet<>(analyser.computeResultsFine());
		if (options.is(Options.PRINT_HEADER))
			print(options.toCDAString() + "\n");
		if (options.is(Options.PRINT_RESULT)) {
			CDATester.print(new TreeSet<>(reasons));
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
	public Set<Reason> getReasons() {
		return reasons;
	}

	/**
	 * @return
	 */
	public Set<Reason> getDDReasons() {
		Set<Reason> result = new HashSet<>();
		for (Reason ddcr : reasons)
			if (ddcr instanceof DeleteDeleteConflictReason || ddcr instanceof CreateDeleteDependencyReason)
				result.add(ddcr);
		return result;
	}

	/**
	 * @return
	 */
	public Set<Reason> getDRReasons() {
		Set<Reason> result = new HashSet<>();
		for (Reason drcr : reasons)
			if (drcr instanceof DeleteReadConflictReason || drcr instanceof CreateReadDependencyReason)
				result.add(drcr);
		return result;
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
			//______________________new conditions check________________________
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
				for (Span drReason : getDRReasons())
					if (iChecker(drReason, type, condition.name, edgeNode))
						return true;
			if (type == Conditions.class || type == DDCRConditions.class || type == DUCRConditions.class
					|| type == CDDRConditions.class || type == CUDRConditions.class)
				for (Span ddReason : getDDReasons())
					if (iChecker(ddReason, type, condition.name, edgeNode))
						return true;
			if (printError)
				System.err.println("\n[" + condition + "] not found!\n");
			return false;
		} else {
			if (condition instanceof CUDR && options.is(Options.DEPENDENCY)
					|| condition instanceof DUCR && !options.is(Options.DEPENDENCY)) {
				if (!condition.proove(getReasons().size())) {
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

	private boolean iChecker(Span conflictReason, Class<?> type, String shortName, List<Condition> edgeNodes) {
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
	 * @param reasons to print
	 * @param errorOut describes if the printed String should be an Error and if the output String should be printed at all or just returned back.
	 *            default settings are: error = false, out = true
	 * @return printed String of reasons
	 */
	public static String print(Set<? extends Reason> reasons, boolean... errorOut) {
		String result = "";
		for (Reason reason : reasons)
			result += reason.toS2String() + "\n";
		if (errorOut.length <= 1 || errorOut.length > 1 && errorOut[1])
			if (errorOut.length == 0 || errorOut.length != 0 && !errorOut[0])
				System.out.println(result);
			else
				System.err.println("\n" + result + "\n");
		return result;

	}

	/**
	 * Returns the Rules in a List
	 * 
	 * @return list with rule1 and rule2
	 */
	public List<Rule> getRules() {
		ArrayList<Rule> result = new ArrayList<>();
		result.add(first);
		result.add(second);
		return result;
	}

	private List<CriticalPair> cps = null;

	/**
	 * Compares reasons with given Critical Pairs. If a match was not found it can be asserted.
	 * 
	 * @param cps to compare
	 * @param asserts true if an assertion should be done by failed comparison
	 * @return Map of matched critical Pairs and Reasons
	 */
	public Map<CriticalPair, Reason> compare(List<CriticalPair> cps, boolean... asserts) {
		this.cps = cps;
		comparedResults = Utils.compare(cps, getReasons());
		assertTrue(
				"\nNot all CPs are perfectly matched with CRs.\nFound matches: " + comparedResults.size() + " of "
						+ getReasons().size() + " CRs and " + cps.size() + " CPs",
				(asserts.length == 0 || !asserts[0])
						|| comparedResults.size() == getReasons().size() && comparedResults.size() == cps.size());
		return comparedResults;
	}

	@Override
	public void ready() {
		int iRest = reasons.size() - iCheckedCounter;
		if (iRest > 0)
			print("Not all Conflict Reasons are tested. " + iRest + (iRest == 1 ? " is" : " are") + " remaining.");
		if (comparedResults != null)
			print("Found matches: " + comparedResults.size() + " of " + getReasons().size() + " Reasons and "
					+ cps.size() + " Critical Pairs",
					comparedResults.size() != getReasons().size() || comparedResults.size() != cps.size());
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
}