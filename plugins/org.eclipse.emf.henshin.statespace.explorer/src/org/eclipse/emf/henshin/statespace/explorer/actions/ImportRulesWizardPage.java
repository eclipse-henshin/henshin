package org.eclipse.emf.henshin.statespace.explorer.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * A wizard page that displays a list of imported transformation
 * rules and that lets the user add and remove rules.
 * @author Christian Krause
 */
public class ImportRulesWizardPage extends WizardPage {
	
	// Imported rules:
	private List<Rule> rules;
	
	// Resource set used for loading:
	private ResourceSet resourceSet;
	
	/**
	 * Default constructor.
	 */
	protected ImportRulesWizardPage(ResourceSet resourceSet) {
		super("Import Rules");
		this.rules = new ArrayList<Rule>();
		this.resourceSet = resourceSet;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2,false));
		
		org.eclipse.swt.widgets.List list = new org.eclipse.swt.widgets.List(container, SWT.BORDER);
		list.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		
		
		Composite buttons = new Composite(container, SWT.NONE);
		buttons.setLayoutData(new GridData(GridData.BEGINNING));
		
		Button add = new Button(buttons, SWT.PUSH);
		add.setText("Add");
		
	}
	
	/**
	 * Get the imported rules.
	 * @return List of rules.
	 */
	public List<Rule> getRules() {
		return rules;
	}
}
