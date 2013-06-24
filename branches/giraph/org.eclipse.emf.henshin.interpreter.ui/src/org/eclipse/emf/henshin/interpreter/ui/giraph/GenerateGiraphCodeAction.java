package org.eclipse.emf.henshin.interpreter.ui.giraph;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class GenerateGiraphCodeAction implements IObjectActionDelegate {

	private final List<Rule> rules = new ArrayList<Rule>();
	
	private IFile file;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		
		IContainer container = file.getParent();
		for (Rule rule : rules) {
			try {
				
				// Rule code:
				String className = getClassName(rule.getName());
				String giraphCode = getGiraphCode(rule, className);
				IFile javaRuleFile = container.getFile(new Path(className + ".java"));
				if (javaRuleFile.exists()) {
					javaRuleFile.setContents(new ByteArrayInputStream(giraphCode.getBytes()), IResource.FORCE, null);
				} else {
					javaRuleFile.create(new ByteArrayInputStream(giraphCode.getBytes()), IResource.FORCE, null);
				}

				// Data code:
				String dataCode = new HenshinUtilTemplate().generate(null);
				IFile javaDataFile = container.getFile(new Path("HenshinUtil.java"));
				if (javaDataFile.exists()) {
					javaDataFile.setContents(new ByteArrayInputStream(dataCode.getBytes()), IResource.FORCE, null);
				} else {
					javaDataFile.create(new ByteArrayInputStream(dataCode.getBytes()), IResource.FORCE, null);
				}

				// Instance code:
				String instanceCode = getInstanceCode(rule);
				IFile jsonFile = container.getFile(new Path(className + ".json"));
				if (jsonFile.exists()) {
					jsonFile.setContents(new ByteArrayInputStream(instanceCode.getBytes()), IResource.FORCE, null);
				} else {
					jsonFile.create(new ByteArrayInputStream(instanceCode.getBytes()), IResource.FORCE, null);
				}

				container.refreshLocal(IResource.DEPTH_INFINITE, null);
				
				IWorkbench wb = PlatformUI.getWorkbench();
				IEditorDescriptor desc = wb.getEditorRegistry().getDefaultEditor(javaRuleFile.getName());
				wb.getActiveWorkbenchWindow().getActivePage().openEditor(new FileEditorInput(javaRuleFile), desc.getId());
				
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		

	}

	private static String getGiraphCode(Rule rule, String className) {
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("data", new GiraphRuleData(rule));
		args.put("className", className);
		GiraphRuleTemplate template = new GiraphRuleTemplate();
		return template.generate(args);
	}
	
	private static String getInstanceCode(Rule rule) {
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
	
	private static String getClassName(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		rules.clear();
		if (selection instanceof IStructuredSelection) {
			Iterator<?> it = ((IStructuredSelection) selection).iterator();
			while (it.hasNext()) {
				Object next = it.next();
				if (next instanceof IGraphicalEditPart) {
					rules.add((Rule) ((IGraphicalEditPart) next).getNotationView().getElement());
				}
			}
		}
		action.setEnabled(!rules.isEmpty());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart part) {
		file = null;
		if (part instanceof IEditorPart) {
			IEditorPart editor = (IEditorPart) part;
			IEditorInput input = editor.getEditorInput();
			if (input instanceof IFileEditorInput) {
				file = ((IFileEditorInput) input).getFile();
			}
		}
		action.setEnabled(file!=null);
	}

}
