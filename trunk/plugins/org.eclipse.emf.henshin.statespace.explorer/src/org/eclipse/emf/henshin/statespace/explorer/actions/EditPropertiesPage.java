package org.eclipse.emf.henshin.statespace.explorer.actions;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * Wizard page for editing state space properties.
 * @author Christian Krause
 */
public class EditPropertiesPage extends WizardPage {
	
	// Properties map.
	private Map<String,String> properties;
	
	// Dirty flag.
	private boolean dirty;
	
	/**
	 * Default constructor.
	 */
	protected EditPropertiesPage() {
		super("Edit State Space Properties");
		setDescription("Edit the properties of this state space.");
		properties = new LinkedHashMap<String,String>();
		dirty = false;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2,false));
		
		
		Composite buttons = new Composite(container, SWT.NONE);
		buttons.setLayoutData(new GridData(GridData.BEGINNING));
		buttons.setLayout(new GridLayout(1,false));
		
		createButton(buttons, "Add");
		createButton(buttons, "Remove");
		
		setControl(container);
		
	}
	
	/*
	 * Add a new property.
	 */
	public void add() {
	}

	/*
	 * Remove the currently selected property.
	 */
	public void remove() {
	}

	/**
	 * Get the properties.
	 * @return The properties.
	 */
	public Map<String, String> getProperties() {
		return properties;
	}
	
	/**
	 * Check if the properties were changed.	
	 * @return Dirty flag.
	 */
	public boolean isDirty() {
		return dirty;
	}
	
	/*
	 * Private helper method for creating buttons.
	 */
	private Button createButton(Composite parent, final String name) {
		Button button = new Button(parent, SWT.PUSH);
		button.setText(name);
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		final EditPropertiesPage thisPage = this;
		button.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				try {
					Method method = EditPropertiesPage.class.getMethod(name.toLowerCase());
					if (method!=null) method.invoke(thisPage);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
		return button;
	}
	
}
