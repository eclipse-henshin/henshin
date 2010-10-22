/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved.
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace.prism;

import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

/**
 * Dialog for specifying the rates of rule applications.
 * 
 * @author Christian Krause
 */
public class SpecifyRatesDialog extends ApplicationWindow {

	// Rule names.
	//private Map<Rule,String> names;

	// Rates.
	//private Map<Rule,Double> rates;

	/**
	 * Default constructor.
	 * @param shell Parent shell.
	 * @param names Rule names.
	 * @param rates Rule Rates.
	 */
	public SpecifyRatesDialog(Shell shell, Map<Rule,String> names, Map<Rule,Double> rates) {
		super(shell);
		//this.names = names;
		//this.rates = rates;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(1,false));

		Button ok = new Button(composite, SWT.PUSH);
		ok.setText("OK");
		ok.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				close();
			}
		});
		
		return composite;//super.createContents(parent);
	}
	
	@Override
    protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Specify Rates");
    	shell.setLocation(100, 100);
    	shell.setSize(250, 250);
    }

	/**
	 * Open an instance of this dialog.
	 * @param names Rule names.
	 * @param rates Rule rates.
	 */
	public static synchronized void open(final Map<Rule,String> names, final Map<Rule,Double> rates, IProgressMonitor monitor) {
		final Display display = Display.getDefault();
		done = false;
		display.asyncExec(new Runnable() {
			@Override
			public void run() {
				dialog = new SpecifyRatesDialog(display.getActiveShell(), names, rates);
				dialog.setBlockOnOpen(true);
				dialog.open();
				done = true;
			}
		});
		while (!done) {
			if (monitor.isCanceled()) {
				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						dialog.close();
					}
				});
				done = true;
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {}
		}
		
		dialog = null;
	}
	
	// Done displaying the dialog?
	private static boolean done;
	
	// Static dialog instance.
	private static SpecifyRatesDialog dialog;
	
}
