package org.eclipse.emf.henshin.statespace.explorer.actions;

import java.util.Map.Entry;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.explorer.commands.ResetStateSpaceCommand;
import org.eclipse.emf.henshin.statespace.explorer.parts.StateSpaceExplorer;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;

/**
 * Wizard for editing state space properties.
 * @author Christian Krause
 */
public class EditPropertiesWizard extends Wizard {
	
	// State space explorer to be used:
	private StateSpaceExplorer explorer;
	
	// Properties page:
	private EditPropertiesPage propertiesPage;
	
	/**
	 * Default constructor.
	 * @param explorer Explorer to be used.
	 */
	public EditPropertiesWizard(StateSpaceExplorer explorer) {
		this.explorer = explorer;
		setNeedsProgressMonitor(false);
		setWindowTitle("Edit State Space Properties");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
    public void addPages() {
		
		// The state space:
		StateSpace stateSpace = explorer.getStateSpaceManager().getStateSpace();
		EMap<String,String> properties = stateSpace.getProperties();
		
		// Create the rule page:
		propertiesPage = new EditPropertiesPage();
		for (Entry<String,String> entry : properties.entrySet()) {
			propertiesPage.getProperties().put(entry.getKey(),entry.getValue());
		}
		addPage(propertiesPage);
		
    }
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		
		StateSpaceManager manager = explorer.getStateSpaceManager();
		
		boolean changed = true;
		
		if (changed) {
			if (MessageDialog.openConfirm(getShell(), "Reset", 
				"Changing the properties may affect the state space generation. " +
				"Therefore we recommend to reset the state space. Should the state space be reset?")) {
				explorer.executeCommand(new ResetStateSpaceCommand(manager));
			}
		}
		
		return true;
	}
	
}
