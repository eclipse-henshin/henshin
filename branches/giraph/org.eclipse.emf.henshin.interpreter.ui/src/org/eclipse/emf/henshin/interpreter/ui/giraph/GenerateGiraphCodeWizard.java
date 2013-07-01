package org.eclipse.emf.henshin.interpreter.ui.giraph;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.henshin.interpreter.giraph.GiraphRuleData;
import org.eclipse.emf.henshin.interpreter.giraph.GiraphRuleTemplate;
import org.eclipse.emf.henshin.interpreter.giraph.HenshinUtilTemplate;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
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

	private Rule rule;

	private GiraphPage page;

	private IContainer targetContainer;
	
	public GenerateGiraphCodeWizard(Rule rule, IContainer targetContainer) {
		this.rule = rule;
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
			args.put("data", new GiraphRuleData(rule));
			args.put("className", className);
			args.put("packageName", packageName);
			args.put("logging", new Boolean(page.loggingCheckBox.getSelection()));
			args.put("applicationCount", Integer.parseInt(page.applicationsText.getText()));
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
			String instanceCode = getInstanceCode();
			IFile jsonFile = targetContainer.getFile(new Path(className + ".json"));
			if (jsonFile.exists()) {
				jsonFile.setContents(new ByteArrayInputStream(instanceCode.getBytes()), IResource.FORCE, null);
			} else {
				jsonFile.create(new ByteArrayInputStream(instanceCode.getBytes()), IResource.FORCE, null);
			}

			targetContainer.refreshLocal(IResource.DEPTH_INFINITE, null);
			
			IWorkbench wb = PlatformUI.getWorkbench();
			IEditorDescriptor desc = wb.getEditorRegistry().getDefaultEditor(javaRuleFile.getName());
			wb.getActiveWorkbenchWindow().getActivePage().openEditor(new FileEditorInput(javaRuleFile), desc.getId());
			
		} catch (CoreException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	private String getInstanceCode() {
		StringBuffer json = new StringBuffer();
		GiraphRuleData data = new GiraphRuleData(rule);
		List<ENamedElement> types = new ArrayList<ENamedElement>(data.typeConstants.keySet());
		for (int i=0; i<rule.getLhs().getNodes().size(); i++) {
			Node n = rule.getLhs().getNodes().get(i);
			json.append("[[" + i + "]," + types.indexOf(n.getType()) + ",[");
			for (int j=0; j<n.getOutgoing().size(); j++) {
				Edge e = n.getOutgoing().get(j);
				int trg = rule.getLhs().getNodes().indexOf(e.getTarget());
				json.append("[[" + trg + "]," + types.indexOf(e.getType()) + "]");
				if (j<n.getOutgoing().size()-1) json.append(",");
			}
			json.append("]]\n");
		}
		return json.toString();
	}
	
	private class GiraphPage extends WizardPage {

		Text packageNameText;
		
		Text classNameText;

		Text applicationsText;

		Button loggingCheckBox;
		
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
			label.setText("Rule class name:");
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			classNameText = new Text(comp, SWT.BORDER);
			classNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			String className = rule.getName();
			className = className.substring(0, 1).toUpperCase() + className.substring(1);
			classNameText.setText(className);

			label = new Label(comp, SWT.NONE);
			label.setText("Default number of applications:");
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			applicationsText = new Text(comp, SWT.BORDER);
			applicationsText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			applicationsText.setText("1");

			label = new Label(comp, SWT.NONE);
			label.setText("Enable logging:");
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			loggingCheckBox = new Button(comp, SWT.CHECK);
			loggingCheckBox.setSelection(true);
		
			setControl(comp);
		}
		
	}

}
