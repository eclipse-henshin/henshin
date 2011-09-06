package org.eclipse.emf.henshin.interpreter.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Stefan Jurack (sjurack)
 */
public class RegisterEcore implements IObjectActionDelegate {
	
	private final List<IFile> list = new ArrayList<IFile>();
	
	@Override
	public void run(IAction action) {
		for (IFile file : list) {
			// String filename = file.getRawLocationURI().getRawPath();
			URI uri = URI.createPlatformResourceURI(file.getFullPath().toOSString(), true);
			EPackage p = ModelHelper.registerEPackageByEcoreFile(uri);

			registerEPackageRec(p, file, "");
			
		}
	}// run
	
	private void registerEPackageRec(EPackage p, IFile file, String breadcrumb) {
		if (p != null) {
			ModelHelper.registerEPackage(p);
			String msg = "EPackage " + breadcrumb + p.getName() + "  (" + p.getNsURI() + ")" + " registered";
			InterpreterUIPlugin.getPlugin().getLog()
					.log(new Status(Status.INFO, InterpreterUIPlugin.ID, Status.OK, msg, null));
			for (EPackage sp : p.getESubpackages()) {
				registerEPackageRec(sp, file, breadcrumb + p.getName() + ".");
			}
		} else {
			String msg = "EPackage in Ecore file " + file.getFullPath().toOSString()
					+ " could not be registered";
			InterpreterUIPlugin
					.getPlugin()
					.getLog()
					.log(new Status(Status.ERROR, InterpreterUIPlugin.ID, Status.ERROR, msg,
							null));
		}// if else
	}
	
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		list.clear();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sel = (IStructuredSelection) selection;
			for (Object o : sel.toArray()) {
				/*
				 * We know that this is an IFile due to the filter in the
				 * plugin.xml
				 */
				this.list.add((IFile) o);
			}
		}
	}// selectionChanged
	
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}
	
}// class
