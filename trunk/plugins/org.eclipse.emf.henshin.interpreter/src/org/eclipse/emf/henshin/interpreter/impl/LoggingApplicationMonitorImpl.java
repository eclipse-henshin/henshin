package org.eclipse.emf.henshin.interpreter.impl;

import java.io.PrintStream;

import org.eclipse.emf.henshin.interpreter.Assignment;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.UnitApplication;

/**
 * An application monitor with logging capabilities.
 * @author Christian Krause
 *
 */
public class LoggingApplicationMonitorImpl extends ApplicationMonitorImpl {
	
	// Print stream to be used for logging:
	protected PrintStream logStream = System.out;
	
	// Whether to print logs only for rule applications:
	protected boolean onlyRuleApplications = false;

	// Whether to print logs only for unit (not rule) applications:
	protected boolean onlyUnitApplications = false;

	// Whether to print logs only for successful unit applications:
	protected boolean onlySuccesses = false;
	
	// Whether to print logs only for failed unit applications:
	protected boolean onlyFailures = false;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.impl.ApplicationMonitorImpl#cancel()
	 */
	@Override
	public void cancel() {
		super.cancel();
		if (logStream!=null) {
			logStream.println("=== CANCEL REQUESTED ===\n");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.impl.ApplicationMonitorImpl#cancelAndUndo()
	 */
	@Override
	public void cancelAndUndo() {
		super.cancelAndUndo();
		if (logStream!=null) {
			logStream.println("=== CANCEL AND UNDO REQUESTED ===\n");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.impl.ApplicationMonitorImpl#notifyExecute(org.eclipse.emf.henshin.interpreter.UnitApplication, boolean)
	 */
	@Override
	public void notifyExecute(UnitApplication application, boolean success) {
		super.notifyExecute(application, success);
		logStep(application, success, "EXECUTED");
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.impl.ApplicationMonitorImpl#notifyUndo(org.eclipse.emf.henshin.interpreter.UnitApplication, boolean)
	 */
	@Override
	public void notifyUndo(UnitApplication application, boolean success) {
		super.notifyUndo(application, success);
		logStep(application, success, "UNDONE");
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.impl.ApplicationMonitorImpl#notifyRedo(org.eclipse.emf.henshin.interpreter.UnitApplication, boolean)
	 */
	@Override
	public void notifyRedo(UnitApplication application, boolean success) {
		super.notifyExecute(application, success);
		logStep(application, success, "REDONE");
	}
	
	/*
	 * Print log info about an execution step.
	 */
	protected void logStep(UnitApplication application, boolean success, String stepKind) {
		if (logStream==null) {
			return;
		}
		if (onlyRuleApplications && !(application instanceof RuleApplication)) {
			return;
		}
		if (onlyUnitApplications && (application instanceof RuleApplication)) {
			return;
		}
		if (onlySuccesses && !success) {
			return;
		}
		if (onlyFailures && success) {
			return;
		}
		logStream.println("=== " + stepKind +  
				((application instanceof RuleApplication) ? " RULE " : " UNIT ") + 
				"'" + application.getUnit().getName() + "' [" + String.valueOf(success).toUpperCase() + "] ===\n");
		if (application instanceof RuleApplication) {
			RuleApplication ruleApp = (RuleApplication) application;
			if (success) {
				logStream.println(ruleApp.getCompleteMatch());
				logStream.println(ruleApp.getResultMatch());					
			} else {
				Match match = ruleApp.getPartialMatch();
				if (match!=null && !match.isEmpty()) {
					logStream.println("Partial " + ruleApp.getPartialMatch().toString().replaceFirst("Match", "match"));
				}
			}
		} else {
			Assignment assignment = application.getAssignment();
			Assignment resultAssignment = application.getResultAssignment();
			if (assignment!=null && !assignment.isEmpty()) {
				logStream.println(assignment);
			}
			if (success && resultAssignment!=null && !resultAssignment.isEmpty()) {
				logStream.println(resultAssignment);			
			}
		}
	}

	public void setLogStream(PrintStream logStream) {
		this.logStream = logStream;
	}

	public void setOnlyRuleApplications(boolean onlyRuleApplications) {
		this.onlyRuleApplications = onlyRuleApplications;
	}

	public void setOnlyUnitApplications(boolean onlyUnitApplications) {
		this.onlyUnitApplications = onlyUnitApplications;
	}

	public void setOnlySuccesses(boolean onlySuccesses) {
		this.onlySuccesses = onlySuccesses;
	}

	public void setOnlyFailures(boolean onlyFailures) {
		this.onlyFailures = onlyFailures;
	}
	
}
