package org.eclipse.emf.henshin.variability.ui;

import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroupDetectionResult;
import org.eclipse.emf.henshin.variability.mergein.clustering.GreedySubCloneClustererStaticThreshold;
import org.eclipse.emf.henshin.variability.mergein.clustering.MergeClusterer;
import org.eclipse.emf.henshin.variability.mergein.refactoring.popup.actions.MergeAction;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.action.Action;

import mergeSuggestion.MergeSuggestion;

public class MergeClusteredRulesAction extends Action {

	public final static String ID = "org.eclipse.emf.henshin.variability.ui.MergeClusteredRulesActionID";

	CloneGroupDetectionResult cloneDetectionResult;
	TransactionalEditingDomain editingDomain;

	public MergeClusteredRulesAction(
			CloneGroupDetectionResult cloneDetectionResult,
			TransactionalEditingDomain editingDomain) {
		this.cloneDetectionResult = cloneDetectionResult;
		this.editingDomain = editingDomain;
	}

	@Override
	public void run() {
		MergeClusterer clusterer = new GreedySubCloneClustererStaticThreshold(
				cloneDetectionResult, 0, 0.55, true);
		final MergeSuggestion mergeSuggestion = clusterer
				.createMergeSuggestion();
		editingDomain.getCommandStack().execute(
				new RecordingCommand(editingDomain) {
					public void doExecute() {
						MergeAction mergeAction = new MergeAction();
						mergeAction.setMergeSuggestion(mergeSuggestion);
						mergeAction.run(null);
					}
				});
	}
}
