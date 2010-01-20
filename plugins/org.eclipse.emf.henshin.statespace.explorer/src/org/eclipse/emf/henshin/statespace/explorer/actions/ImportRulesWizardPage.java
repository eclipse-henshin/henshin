package org.eclipse.emf.henshin.statespace.explorer.actions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
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
	
	// SWT list for the rules:
	private org.eclipse.swt.widgets.List list;
	
	// Resource set used for loading:
	private ResourceSet resourceSet;
	
	/**
	 * Default constructor.
	 */
	protected ImportRulesWizardPage(ResourceSet resourceSet) {
		super("Import Rules");
		this.rules = new ArrayList<Rule>();
		this.resourceSet = resourceSet;
		setDescription("Add or remove transformation rules for the state space.");
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2,false));
		
		list = new org.eclipse.swt.widgets.List(container, SWT.BORDER);
		list.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Composite buttons = new Composite(container, SWT.NONE);
		buttons.setLayoutData(new GridData(GridData.BEGINNING));
		buttons.setLayout(new GridLayout(1,false));
		
		createButton(buttons, "Add");
		createButton(buttons, "Remove");
		createButton(buttons, "Up");
		createButton(buttons, "Down");
		
		setControl(container);
		
	}
	
	private void add() {
		
	}
	
	private void remove() {
		
	}
	
	private void up() {
		
	}

	private void down() {
		
	}
	
	/*
	 * Private helper method for creating buttons.
	 */
	private Button createButton(Composite parent, final String name) {
		Button button = new Button(parent, SWT.PUSH);
		button.setText(name);
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		button.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				try {
					Method method = ImportRulesWizardPage.class.getMethod(name.toLowerCase());
					if (method!=null) method.invoke(this);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
		return button;
	}
	
	/**
	 * Get the imported rules.
	 * @return List of rules.
	 */
	public List<Rule> getRules() {
		return rules;
	}
}
