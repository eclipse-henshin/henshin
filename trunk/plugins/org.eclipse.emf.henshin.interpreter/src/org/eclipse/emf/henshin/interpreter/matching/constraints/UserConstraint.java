package org.eclipse.emf.henshin.interpreter.matching.constraints;

import java.util.Map;

import org.eclipse.emf.henshin.interpreter.EGraph;

public interface UserConstraint extends Constraint {
	
	boolean check(DomainSlot slot, Variable variable, Map<Variable, DomainSlot> domainMap, EGraph graph);
	
	boolean unlock(Variable sender,DomainSlot slot);
	
}
