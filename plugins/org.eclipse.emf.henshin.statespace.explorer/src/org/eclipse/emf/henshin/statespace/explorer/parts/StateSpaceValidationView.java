package org.eclipse.emf.henshin.statespace.explorer.parts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.StateSpacePlugin;
import org.eclipse.emf.henshin.statespace.StateSpaceValidationResult;
import org.eclipse.emf.henshin.statespace.StateSpaceValidator;
import org.eclipse.emf.henshin.statespace.explorer.jobs.ValidateStateSpaceJob;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

/**
 * A simple view that serves as a front-end for state space validators.
 * @author Christian Krause
 */
public class StateSpaceValidationView extends ViewPart implements ISelectionListener {
	
	// Multi-line text widget for the property to be checked:
	private Text propertyText;
	
	// Combo for choosing the current validator:
	private Combo validatorCombo;
	
	// Button for starting the validation:
	private Button validateButton;
	
	// Label for displaying the currently selected statespace file:
	private Label selectionLabel;
	
	// State space validators.
	private List<StateSpaceValidator> validators;
	
	// Currently used state space manager:
	private StateSpaceManager stateSpaceManager;

	// Validation job.
	private ValidateStateSpaceJob validationJob;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		
		parent = new Composite(parent, SWT.NONE);
		parent.setLayout(new GridLayout(3,false));
		
		propertyText = new Text(parent, SWT.MULTI | SWT.BORDER);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 3;
		propertyText.setLayoutData(data);
		
		selectionLabel = new Label(parent, SWT.RIGHT);
		selectionLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		validatorCombo = new Combo(parent, SWT.BORDER);
		validatorCombo.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		for (StateSpaceValidator validator : validators) {
			validatorCombo.add(validator.getName());
		}
		
		validateButton = new Button(parent, SWT.PUSH);
		validateButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		validateButton.setText("Validate");
		validateButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}
			public void widgetSelected(SelectionEvent e) {
				performValidation();
			}
		});
		
		if (validators.isEmpty()) {
			validatorCombo.setEnabled(false);
			validateButton.setEnabled(false);
		} else {
			validatorCombo.select(0);
		}
		
	}

	public void performValidation() {
		
		validateButton.setEnabled(false);
		StateSpaceValidator validator = validators.get(validatorCombo.getSelectionIndex());
		
		validationJob = new ValidateStateSpaceJob(propertyText.getText(), validator, null);
		validationJob.setUser(true);
		validationJob.addJobChangeListener(new JobChangeAdapter() {
			public void done(IJobChangeEvent event) {
				finished();
			}			
		});
		validationJob.schedule();
		
	}

	private void finished() {
		final StateSpaceValidationResult result = validationJob.getValidationResult();
		getSite().getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (result.isValid()) {
					MessageDialog.openInformation(getSite().getShell(), "Validation", "Property satified.");
				} else {
					MessageDialog.openError(getSite().getShell(), "Validation", "Property not satified.");					
				}
				validationJob = null;
				validateButton.setEnabled(true);
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite, org.eclipse.ui.IMemento)
	 */
    public void init(IViewSite site, IMemento memento) throws PartInitException {
    	super.init(site, memento);
    	
		// Load the validators:
		validators = new ArrayList<StateSpaceValidator>();
		validators.addAll(StateSpacePlugin.INSTANCE.getStateSpaceValidators().values());
		Collections.sort(validators, new Comparator<StateSpaceValidator>() {
			public int compare(StateSpaceValidator v1, StateSpaceValidator v2) {
				String n1 = v1.getName();
				String n2 = v2.getName();
				return (n1!=null && n2!=null) ? n1.compareTo(n2) : 0;
			}
		});
		
		site.getWorkbenchWindow().getSelectionService().addSelectionListener(this);
		
    }
	
    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.part.WorkbenchPart#dispose()
     */
    @Override
    public void dispose() {
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(this);
    	super.dispose();
    }
    
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		propertyText.setFocus();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		String filename = "none";
		if (part instanceof StateSpaceExplorer) {
			stateSpaceManager = ((StateSpaceExplorer) part).getStateSpaceManager();
			filename = part.getTitle();
		}
		boolean enabled = (stateSpaceManager!=null && !validators.isEmpty());
		
		selectionLabel.setText("Validating " + filename + " using");
		validateButton.setEnabled(enabled);
		
	}

}
