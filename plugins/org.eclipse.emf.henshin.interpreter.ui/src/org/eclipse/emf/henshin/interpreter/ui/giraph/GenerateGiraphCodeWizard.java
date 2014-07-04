package org.eclipse.emf.henshin.interpreter.ui.giraph;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.henshin.interpreter.giraph.GiraphRuleTemplate;
import org.eclipse.emf.henshin.interpreter.giraph.GiraphUtil;
import org.eclipse.emf.henshin.interpreter.giraph.HenshinUtilTemplate;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class GenerateGiraphCodeWizard extends Wizard {

	private Unit mainUnit;

	private GiraphPage page;

	private IContainer targetContainer;
	
	public GenerateGiraphCodeWizard(Unit unit, IContainer targetContainer) {
		this.mainUnit = unit;
		this.targetContainer = targetContainer;
		setWindowTitle("Giraph Code Generator");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
    public void addPages() {
		addPage(page = new GiraphPage());
    }

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		
		try {
			
			// Rule code:
			String className = page.classNameText.getText();
			String packageName = page.packageNameText.getText();
			
			Map<String,Object> args = new HashMap<String,Object>();
			args.put("ruleData", GiraphUtil.generateRuleData(mainUnit));
			args.put("mainUnit", mainUnit);
			args.put("className", className);
			args.put("packageName", packageName);
			args.put("masterLogging", new Boolean(page.masterLoggingCheckBox.getSelection()));
			args.put("vertexLogging", new Boolean(page.vertexLoggingCheckBox.getSelection()));
			args.put("useUUIDs", new Boolean(page.uuidsCheckBox.getSelection()));
			args.put("segmentCount", 1);
			GiraphRuleTemplate template = new GiraphRuleTemplate();
			String giraphCode = template.generate(args);
			
			IFile javaRuleFile = targetContainer.getFile(new Path(className + ".java"));
			if (javaRuleFile.exists()) {
				javaRuleFile.setContents(new ByteArrayInputStream(giraphCode.getBytes()), IResource.FORCE, null);
			} else {
				javaRuleFile.create(new ByteArrayInputStream(giraphCode.getBytes()), IResource.FORCE, null);
			}

			// Data code:
			String dataCode = new HenshinUtilTemplate().generate(args);
			IFile javaDataFile = targetContainer.getFile(new Path("HenshinUtil.java"));
			if (javaDataFile.exists()) {
				javaDataFile.setContents(new ByteArrayInputStream(dataCode.getBytes()), IResource.FORCE, null);
			} else {
				javaDataFile.create(new ByteArrayInputStream(dataCode.getBytes()), IResource.FORCE, null);
			}

			// Instance code:
			if (page.jsonCheckBox.getSelection()) {
				Collection<Rule> rules = GiraphUtil.collectRules(mainUnit);
				if (!rules.isEmpty()) {
					String instanceCode = GiraphUtil.getInstanceCode(rules.iterator().next());
					IFile jsonFile = targetContainer.getFile(new Path(className + ".json"));
					if (jsonFile.exists()) {
						jsonFile.setContents(new ByteArrayInputStream(instanceCode.getBytes()), IResource.FORCE, null);
					} else {
						jsonFile.create(new ByteArrayInputStream(instanceCode.getBytes()), IResource.FORCE, null);
					}
				}
			}

			targetContainer.refreshLocal(IResource.DEPTH_INFINITE, null);
			
			IWorkbench wb = PlatformUI.getWorkbench();
			IEditorDescriptor desc = wb.getEditorRegistry().getDefaultEditor(javaRuleFile.getName());
			wb.getActiveWorkbenchWindow().getActivePage().openEditor(new FileEditorInput(javaRuleFile), desc.getId());
			
		} catch (Exception e) {
			MessageDialog.openError(getShell(), "Error", "Error generating Giraph code: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private class GiraphPage extends WizardPage {

		Text packageNameText;
		
		Text classNameText;

		Button masterLoggingCheckBox;

		Button vertexLoggingCheckBox;

		Button uuidsCheckBox;

		Button jsonCheckBox;

		public GiraphPage() {
			super("Giraph");
			setTitle("Giraph Code Generator");
			setDescription("Enter the details for the Giraph code generation.");
		}

		@Override
		public void createControl(Composite parent) {
			Composite comp = new Composite(parent, SWT.FILL);
			comp.setLayout(new GridLayout(2, false));
			Label label;

			label = new Label(comp, SWT.NONE);
			label.setText("Package name:");
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			packageNameText = new Text(comp, SWT.BORDER);
			packageNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			packageNameText.setText("org.apache.giraph.examples");
			
			label = new Label(comp, SWT.NONE);
			label.setText("Compute class name:");
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			classNameText = new Text(comp, SWT.BORDER);
			classNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			String className = mainUnit.getName();
			className = className.substring(0, 1).toUpperCase() + className.substring(1);
			classNameText.setText(className);

			label = new Label(comp, SWT.NONE);
			label.setText("Master logging:");
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			masterLoggingCheckBox = new Button(comp, SWT.CHECK);
			masterLoggingCheckBox.setSelection(true);

			label = new Label(comp, SWT.NONE);
			label.setText("Vertex logging:");
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			vertexLoggingCheckBox = new Button(comp, SWT.CHECK);

			label = new Label(comp, SWT.NONE);
			label.setText("Use Java UUIDs:");
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			uuidsCheckBox = new Button(comp, SWT.CHECK);
			uuidsCheckBox.setSelection(true);

			label = new Label(comp, SWT.NONE);
			label.setText("Example JSON:");
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			jsonCheckBox = new Button(comp, SWT.CHECK);

			setControl(comp);
		}
		
	}

}
