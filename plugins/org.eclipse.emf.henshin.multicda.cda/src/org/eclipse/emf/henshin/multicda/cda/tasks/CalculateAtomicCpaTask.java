package org.eclipse.emf.henshin.multicda.cda.tasks;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.ConflictAnalysis;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalDeleteReadConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;

public class CalculateAtomicCpaTask implements Callable<Set<Atom>> {

	Rule firstRule;
	Rule secondRule;

	long conflictAtomRunTime;

	AtomicResultContainer resultKeeper;

	public CalculateAtomicCpaTask(AtomicResultContainer resultKeeper) {

		this.resultKeeper = resultKeeper;

		this.firstRule = resultKeeper.getFirstRule();
		this.secondRule = resultKeeper.getSecondRule();
	}

	@Override
	public Set<Atom> call() throws Exception {
//		System.out.println("CALLL!");

		ConflictAnalysis atomicCoreCPA = new ConflictAnalysis(firstRule, secondRule);

		Set<Atom> computeConflictAtoms = new HashSet<>();

		long normalStartTime = System.currentTimeMillis();

		try {
			computeConflictAtoms = new HashSet<>(atomicCoreCPA.computeAtoms());
		} catch (NullPointerException e) {
			System.err.println("Timeout!");
		}

		long normalEndTime = System.currentTimeMillis();
		conflictAtomRunTime = normalEndTime - normalStartTime;

		resultKeeper.addResult(computeConflictAtoms);
		resultKeeper.setCalculationTime(conflictAtomRunTime);
		resultKeeper.setCandidates(new HashSet<>(atomicCoreCPA.computeAtoms()));
		resultKeeper.setMinimalConflictReasons(new HashSet<>(atomicCoreCPA.computeResultsCoarse()));

		Set<MinimalConflictReason> minimalConflictReasons = new HashSet<MinimalConflictReason>();
		for (Reason conflictReason : atomicCoreCPA.computeResultsCoarse()) {
			minimalConflictReasons.add(new MinimalDeleteReadConflictReason(conflictReason));
		}
		long conflictReasonStartTime = System.currentTimeMillis();
		Set<ConflictReason> initialReasons = atomicCoreCPA.computeResultsFine();

		Set<Reason> filteredConflictReasons = new HashSet<Reason>();
		for (Reason initialReason : initialReasons) {
			Set<Mapping> mappingsInRule1 = initialReason.getMappingsInRule1();
			Set<Mapping> mappingsInRule2 = initialReason.getMappingsInRule2();
//			List<Edge> danglingEdges = atomicCoreCPA.findDanglingEdgesByLHSOfRule2(mappingsInRule1, secondRule, mappingsInRule2);
//			if(danglingEdges.size() == 0){
//				filteredConflictReasons.add(initialReason);
//			}
		}

		long conflictReasonEndTime = System.currentTimeMillis();
		long conflictReasonAdditionalRunTime = conflictReasonEndTime - conflictReasonStartTime;
		long conflictReasonOverallRuneTime = conflictAtomRunTime + conflictReasonAdditionalRunTime;

		resultKeeper.setConflictReasonOverallTime(conflictReasonOverallRuneTime);
		resultKeeper.setConflictReasons(filteredConflictReasons);

		return computeConflictAtoms;
	}

}
