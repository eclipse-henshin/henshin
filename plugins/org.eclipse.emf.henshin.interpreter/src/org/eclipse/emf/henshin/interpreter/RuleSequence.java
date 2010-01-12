package org.eclipse.emf.henshin.interpreter;

import java.util.Stack;

public class RuleSequence {
	Stack<RuleApplication> sequence;
	Stack<RuleApplication> undoneApplications;

	public RuleSequence() {
		sequence = new Stack<RuleApplication>();
	}

	public void addRuleApplication(RuleApplication ruleApplication) {
		sequence.push(ruleApplication);
	}

	public void undo() {
		while (!sequence.isEmpty()) {
			undoLatest();
		}
	}

	public void redo() {
		while (!undoneApplications.isEmpty()) {
			redoLatest();
		}
	}

	public void undoLatest() {
		RuleApplication last = sequence.pop();
		last.undo();
		undoneApplications.push(last);
	}

	public void redoLatest() {
		RuleApplication last = undoneApplications.pop();
		last.undo();
		sequence.add(last);
	}
}
