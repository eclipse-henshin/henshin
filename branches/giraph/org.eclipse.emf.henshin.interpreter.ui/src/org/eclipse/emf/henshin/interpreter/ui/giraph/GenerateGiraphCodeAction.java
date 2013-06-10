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
				String className = getClassName(rule.getName());
				String giraphCode = getGiraphCode(rule, className);
				IFile javaFile = container.getFile(new Path(className + ".java"));
				if (javaFile.exists()) {
					javaFile.setContents(new ByteArrayInputStream(giraphCode.getBytes()), IResource.FORCE, null);
				} else {
					javaFile.create(new ByteArrayInputStream(giraphCode.getBytes()), IResource.FORCE, null);
				}
				container.refreshLocal(IResource.DEPTH_INFINITE, null);
				
				IWorkbench wb = PlatformUI.getWorkbench();
				IEditorDescriptor desc = wb.getEditorRegistry().getDefaultEditor(javaFile.getName());
				wb.getActiveWorkbenchWindow().getActivePage().openEditor(new FileEditorInput(javaFile), desc.getId());
				
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		

	}

	private static String getGiraphCode(Rule rule, String className) {
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("rule", rule);
		args.put("className", className);
		GiraphRuleTemplate template = new GiraphRuleTemplate();
		return template.generate(args);
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
