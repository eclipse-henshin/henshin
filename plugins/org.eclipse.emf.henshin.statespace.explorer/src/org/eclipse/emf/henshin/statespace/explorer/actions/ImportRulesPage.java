package org.eclipse.emf.henshin.statespace.explorer.actions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.statespace.explorer.StateSpaceExplorerPlugin;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * A wizard page that displays a list of imported transformation
 * rules and that lets the user add and remove rules.
 * @author Christian Krause
 */
public class ImportRulesPage extends WizardPage {
	
	// Imported rules:
	private List<Rule> rules;
	
	// SWT list for the rules:
	private org.eclipse.swt.widgets.List list;
	
	// StateSpace resource:
	private Resource stateSpaceResource;
	
	/**
	 * Default constructor.
	 */
	protected ImportRulesPage() {
		super("Import Rules");
		this.rules = new ArrayList<Rule>();
		setDescription("Add or remove transformation rules to be used for this state space.");
	}
	
	/**
	 * Set the state space resource to be used.
	 * @param resource State space resource.
	 */
	public void setStateSpaceResource(Resource resource) {
		this.stateSpaceResource = resource;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2,false));
		
		list = new org.eclipse.swt.widgets.List(container, SWT.BORDER | SWT.SINGLE);
		list.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Composite buttons = new Composite(container, SWT.NONE);
		buttons.setLayoutData(new GridData(GridData.BEGINNING));
		buttons.setLayout(new GridLayout(1,false));
		
		createButton(buttons, "Add");
		createButton(buttons, "Remove");
		//createButton(buttons, "Up");
		//createButton(buttons, "Down");
		
		setControl(container);
		
	}
	
	/*
	 * Open a selection dialog for rules.
	 */
	public void add() {
		
		// Open a selection dialog:
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(), new RuleLabelProvider(), new RuleContentProvider());
		dialog.setTitle("Select Rule");
		dialog.setMessage("Please select the transformation rule to be imported:");
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
		dialog.addFilter(new RuleViewFilter());		
		dialog.setValidator(new RuleSelectionValidator());
		dialog.setAllowMultiple(true);
		dialog.open();
		
		Object[] result = dialog.getResult();
		if (result==null) return;
		
		for (int i=0; i<result.length; i++) {
			if (result[i] instanceof Rule) {
				Rule rule = (Rule) result[i];
				if (!rules.contains(rule)) {
					rules.add(rule);
					list.add(rule.getName() + " (" + rule.eResource().getURI().lastSegment() + ")");
				}
			}
		}
		
	}
	
	/*
	 * Remove the currently selected rules.
	 */
	public void remove() {
		int index = list.getSelectionIndex();
		if (index>=0) {
			list.remove(index);
			rules.remove(index);
		}
	}
		
	/*
	 * Private helper method for creating buttons.
	 */
	private Button createButton(Composite parent, final String name) {
		Button button = new Button(parent, SWT.PUSH);
		button.setText(name);
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		final ImportRulesPage thisPage = this;
		button.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				try {
					Method method = ImportRulesPage.class.getMethod(name.toLowerCase());
					if (method!=null) method.invoke(thisPage);
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
	
	
	/*
	 * A view filter for henshin files.
	 */
	static class RuleViewFilter extends ViewerFilter {
		
		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			if (element instanceof IAdaptable) {
				IAdaptable adapter = (IAdaptable) element;
				Object adaptedResource = adapter.getAdapter(IResource.class);
				if (adaptedResource != null) {
					IResource res = (IResource) adaptedResource;
					if ("henshin".equals(res.getFileExtension()) || IResource.FILE!=res.getType()) {
						return true;
					}
				}
			}
			return element instanceof Rule;
		}
		
	}
	
	/*
	 * Internal label provider class.
	 */
	static class RuleLabelProvider extends LabelProvider implements ILabelProvider {
		
		/* 
		 * (non-Javadoc)
		 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
		 */
		@Override
		public Image getImage(Object element) {
			if (element instanceof IAdaptable) {
				final IAdaptable adaptable = (IAdaptable) element;
				final Object adapter = adaptable.getAdapter(IWorkbenchAdapter.class);
				if (adapter != null) {
					final IWorkbenchAdapter res = (IWorkbenchAdapter) adapter;
					return res.getImageDescriptor(element).createImage();
				}
			}
			if (element instanceof Rule) {
				//return (Image) HenshinEditPlugin.INSTANCE.getImage("full/obj16/Rule.gif");
			}
			return null;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(Object element) {
			if (element instanceof IAdaptable) {
				final IAdaptable adaptable = (IAdaptable) element;
				final Object adapter = adaptable.getAdapter(IWorkbenchAdapter.class);
				if (adapter != null) {
					final IWorkbenchAdapter res = (IWorkbenchAdapter) adapter;
					return res.getLabel(element);
				}
			}
			if (element instanceof Rule) {
				return ((Rule) element).getName();
			}
			return element.toString();
		}
		
	}
	
	/*
	 * Ecore content provider.
	 */
	class RuleContentProvider extends BaseWorkbenchContentProvider {
		
		/* 
		 * (non-Javadoc)
		 * @see org.eclipse.ui.model.BaseWorkbenchContentProvider#getChildren(java.lang.Object)
		 */
		@Override
		public Object[] getChildren(Object element) {
			if (element instanceof IAdaptable) {
				
				IAdaptable adapter = (IAdaptable) element;
				Object adaptedResource = adapter.getAdapter(IResource.class);
				
				if (adaptedResource != null) {
					IResource res = (IResource) adaptedResource;
					if ("henshin".equals(res.getFileExtension())) {
						
						// Construct an URI:
						URI baseURI = stateSpaceResource.getURI();
						URI uri = URI.createPlatformResourceURI(res.getFullPath().toString(), true).resolve(baseURI);
						
						// Create the resource:
						ResourceSet resourceSet = stateSpaceResource.getResourceSet();
						Resource resource = resourceSet.getResource(uri, true);
						
						List<Rule> rules = new ArrayList<Rule>();
						for (EObject item : resource.getContents()) {
							if (item instanceof TransformationSystem) {
								rules.addAll(((TransformationSystem) item).getRules());
							}
						}
						return rules.toArray();
						
					}
				}
			}
			return super.getChildren(element);
		}
	}
	
	/*
	 * Rule selection validator.
	 */
	static class RuleSelectionValidator implements ISelectionStatusValidator {
		
		/*
		 * (non-Javadoc)
		 * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
		 */
		public IStatus validate(Object[] selection) {
			if (selection.length > 0) {
				final Object obj = selection[0];
				if (obj instanceof Rule) {
					return new Status(IStatus.OK, StateSpaceExplorerPlugin.ID, "Rule selected");
				}
			}
			return new Status(IStatus.ERROR, StateSpaceExplorerPlugin.ID, "No rule selected");
		}
	};

}
