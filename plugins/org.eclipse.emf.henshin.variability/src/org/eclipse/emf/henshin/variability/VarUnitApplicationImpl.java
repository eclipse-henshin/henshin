package org.eclipse.emf.henshin.variability;

import org.eclipse.emf.henshin.interpreter.ApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.Assignment;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.impl.AssignmentImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
/**
 * Variability-aware {@link org.eclipse.emf.henshin.interpreter.UnitApplication UnitApplication} implementation.
 * 
 * @author Daniel Strüber
 */
public class VarUnitApplicationImpl extends UnitApplicationImpl {

	public VarUnitApplicationImpl(Engine engine) {
		super(engine);
	}

	public VarUnitApplicationImpl(Engine engine, EGraph graph, Unit unit,
			Assignment assignment) {
		super(engine, graph, unit, assignment);
	}

	@Override
	protected boolean executeRule(ApplicationMonitor monitor) {
		Rule rule = (Rule) unit;
		RuleApplication ruleApp = new VarRuleApplicationImpl(engine, graph, rule, resultAssignment);
		if (ruleApp.execute(monitor)) {
			resultAssignment = new AssignmentImpl(ruleApp.getResultMatch(), true);
			appliedRules.push(ruleApp);
			return true;  // notification is done in the rule application
		} else {
			return false;
		}
	}
	
	/*
	 * Create an UnitApplication for a given Unit.
	 */
	protected UnitApplicationImpl createApplicationFor(Unit subUnit) {
		if (resultAssignment==null) {
			resultAssignment = new AssignmentImpl(unit);
		}
		Assignment assign = new AssignmentImpl(subUnit);
		for (ParameterMapping mapping : unit.getParameterMappings()) {
			Parameter source = mapping.getSource();
			Parameter target = mapping.getTarget();
			if (target.getUnit()==subUnit) {
				assign.setParameterValue(target, resultAssignment.getParameterValue(source));
			}
		}
		return new VarUnitApplicationImpl(engine, graph, subUnit, assign);
	}
}
