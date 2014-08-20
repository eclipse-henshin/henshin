package org.eclipse.emf.henshin.interpreter.ui.wizard;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.interpreter.ui.HenshinInterpreterUIPlugin;
import org.eclipse.emf.henshin.interpreter.ui.wizard.ModelSelector.ModelSelectorListener;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class HenshinWizardPage extends WizardPage {

	protected static int CONTROL_OFFSET = 5;

	UnitSelector unitSelector;

	ModelSelector inputSelector;

	ModelSelector outputSelector;

	ParameterEditTable parameterEditor;

	Button openCompare;
	
	Module module;

	public HenshinWizardPage() {
		super("mainpage");
		setDescription(HenshinInterpreterUIPlugin
				.LL("_UI_Wizard_DefaultDescription"));
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, true));

		unitSelector = new UnitSelector(container);
		unitSelector.getControl().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, false));

		IResource selected = null;
		if (module != null) {
			String path = module.eResource().getURI().toPlatformString(true);
			selected = ResourcesPlugin.getWorkspace().getRoot()
					.findMember(path);
		}

		inputSelector = new ModelSelector(container, selected, false);
		inputSelector.getControl().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, false));

		outputSelector = new ModelSelector(container, selected, true);
		outputSelector.getControl().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, false));

		inputSelector.addModelSelectorListener(new ModelSelectorListener() {
			@Override
			public boolean modelURIChanged(String modelURI) {
				String output = deriveOutputURI(modelURI);
				if (output != null) {
					outputSelector.setModelURI(output);
				}
				return true;
			}
		});

		parameterEditor = new ParameterEditTable(container);
		parameterEditor.getControl().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true));

		openCompare = new Button(container, SWT.CHECK);
		openCompare.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		openCompare.setText("Open Compare");
		openCompare.setSelection(true);
		
		inputSelector.getBrowseWorkspaceButton().setFocus();
		
		setControl(container);
	}

	private String deriveOutputURI(String inputUri) {
		try {
			URI uri = URI.createURI(inputUri);
			String fileExt = uri.fileExtension();
			uri = uri.trimFileExtension();
			uri = URI.createURI(uri.toString() + "_transformed")
					.appendFileExtension(fileExt);
			return uri.toString();
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

}
