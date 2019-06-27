package org.eclipse.emf.henshin.multicda.cda.tasks;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;

public class AtomicResultContainer {
	
	Rule firstRule;
	Rule secondRule; 



	Set<Atom> atomicCoreCpaConflictAtoms;
	Set<Atom> atomicCoreCpaCandidates;
	Set<ConflictReason> atomicCoreCpaMinimalConflictsReasons;
	
	
	long minimalConflictReasonRunTime;
	private long conflictReasonOverallRuneTime;
	private Set<Reason> initialReasons;

	public AtomicResultContainer(Rule firstRule, Rule secondRule) {
		this.firstRule = firstRule;
		this.secondRule = secondRule;
	}


	public Rule getFirstRule() {
		return firstRule;
	}

	public Rule getSecondRule() {
		return secondRule;
	}


	public void setCalculationTime(long normalRunTime) {
		this.minimalConflictReasonRunTime = normalRunTime;
	}

//	public long getNormalRunTime() {
//		return minimalConflictReasonRunTime;
//	}


	public Set<Atom> getConflictAtoms() {
		if(atomicCoreCpaConflictAtoms == null){
			return new HashSet<>();
		}else {			
			return atomicCoreCpaConflictAtoms;
		}
	}


	public Set<Atom> getCandidates() { 
		if(atomicCoreCpaCandidates == null){
			return new HashSet<>();
		}else {			
			return atomicCoreCpaCandidates;
		}
	}
	

	public void setCandidates(Set<Atom> atomicCoreCpaCandidates) {
		this.atomicCoreCpaCandidates = atomicCoreCpaCandidates;;
	}


	public Set<ConflictReason> getMinimalConflictReasons() { 
		if(atomicCoreCpaMinimalConflictsReasons == null){
			return new HashSet<>();
		}else {			
			return atomicCoreCpaMinimalConflictsReasons;
		}
	}


	public void addResult(Set<Atom> atomicCoreCpaConflictAtoms) {
		this.atomicCoreCpaConflictAtoms = atomicCoreCpaConflictAtoms;
	}


	public void setMinimalConflictReasons(Set<ConflictReason> overallReasons) {
		this.atomicCoreCpaMinimalConflictsReasons = overallReasons;
	}


	public void setConflictReasonOverallTime(long conflictReasonOverallRuneTime) {
		this.conflictReasonOverallRuneTime = conflictReasonOverallRuneTime;
	}


	public void setConflictReasons(Set<Reason> initialReasons) {
		this.initialReasons = initialReasons;
	}


	public Set<Reason> getConflictReasons() {
		if(initialReasons == null){
			return new HashSet<Reason>();
		}else {			
			return initialReasons;
		}
	}


	public long getRunTimeOfMinimalConflictReasons() {
		return minimalConflictReasonRunTime;
	}


	public long getConflictReasonOverallTime() {
		return conflictReasonOverallRuneTime;
	}

}
