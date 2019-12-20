package org.eclipse.emf.henshin.variability.configuration.ui.parts;

import org.eclipse.emf.henshin.diagram.edit.parts.RuleEditPart;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewSite;

/**
 * 
 * @author Stefan Schulz
 *
 */
public interface ILinkedWithEditorView {

	void editorSelectionChanged(IEditorPart activeEditor);

	void selectedRuleChanged(RuleEditPart ruleEditPart);

	IViewSite getViewSite();

}
