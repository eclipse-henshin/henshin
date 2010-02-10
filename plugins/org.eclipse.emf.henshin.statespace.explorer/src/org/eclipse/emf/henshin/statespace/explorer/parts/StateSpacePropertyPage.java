package org.eclipse.emf.henshin.statespace.explorer.parts;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.explorer.StateSpaceExplorerPlugin;
import org.eclipse.emf.henshin.statespace.impl.StateSpaceManagerImpl;
import org.eclipse.emf.henshin.statespace.resource.StateSpaceResource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.PropertyPage;

/**
 * Property page for state spaces.
 * @author Christian Krause
 */
public class StateSpacePropertyPage extends PropertyPage {
	
	// Labels:
	private Label statesLabel, transitionsLabel, rulesLabel;

	
	private void addSection(Composite parent) {
		
		Composite composite = createDefaultComposite(parent);
		
		Label label = new Label(composite, SWT.NONE);
		label.setText("States:");
		statesLabel = new Label(composite, SWT.NONE);

		label = new Label(composite, SWT.NONE);
		label.setText("Transitions:");
		transitionsLabel = new Label(composite, SWT.NONE);

		label = new Label(composite, SWT.NONE);
		label.setText("Rules:");
		rulesLabel = new Label(composite, SWT.NONE);

		StateSpace stateSpace = loadStateSpace();
		statesLabel.setText(stateSpace.getStates().size() + " (" + stateSpace.getOpenStates().size() + " open)");
		transitionsLabel.setText(stateSpace.getTransitionCount()+"");
		rulesLabel.setText(stateSpace.getRules().size()+"");

	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);

		addSection(composite);
		return composite;
		
	}

	private Composite createDefaultComposite(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);

		return composite;
		
	}
	
	private StateSpace loadStateSpace() {
		
		ResourceSet resourceSet = new ResourceSetImpl();
    	URI uri = URI.createPlatformResourceURI(((IResource) getElement()).getFullPath().toString(), false);
    	
    	StateSpaceResource resource = null;
    	StateSpaceManager manager = null;
    	
		try {			
			// Perform the loading:
			resource = (StateSpaceResource) resourceSet.getResource(uri, true);
			
			// Load the state space manager:
			manager = new StateSpaceManagerImpl(resource.getStateSpace());
			
		}
		catch (Throwable e) {
			StateSpaceExplorerPlugin.getInstance().logError("Error loading state space", e);
			MessageDialog.openError(getShell(), "Load State Space", "Error loading state space. See the error log for mor information.");
		}
		
		return manager.getStateSpace();
		
	}
	
}