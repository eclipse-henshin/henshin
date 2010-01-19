package org.eclipse.emf.henshin.statespace.explorer.actions;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.henshin.statespace.explorer.parts.StateSpaceExplorer;
import org.eclipse.jface.wizard.Wizard;

/**
 * A wizard for importing rules into a statespace.
 * @author Christian Krause
 */
public class ImportRulesWizard extends Wizard {
	
	// State space explorer to be used:
	private StateSpaceExplorer explorer;
	
	/**
	 * Default constructor.
	 * @param explorer Explorer to be used.
	 */
	public ImportRulesWizard(StateSpaceExplorer explorer) {
		this.explorer = explorer;
		setNeedsProgressMonitor(false);
		setWindowTitle("Import Rules");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
    public void addPages() {
		ResourceSet resourceSet = explorer.getStateSpaceManager().getStateSpace().eResource().getResourceSet();
		addPage(new ImportRulesWizardPage(resourceSet));
    }
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		return true;
	}
	
}
