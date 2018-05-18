package org.eclipse.emf.henshin.text.ui.handler;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.henshin.interpreter.ui.wizard.HenshinWizard;
import org.eclipse.emf.henshin.interpreter.ui.wizard.HenshinWizardDialog;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.text.henshin_text.Model;
import org.eclipse.emf.henshin.text.ui.util.Transformation;
import org.eclipse.emf.henshin.text.ui.util.TransformationHenshin2HenshinText;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.xbase.lib.Pair;

public class TransformHenshin2Henshin_textHandler extends AbstractHandler implements IHandler {

	/**
	 * Transformiert eine henshin-Datei in eine henshin_xtext-Datei
	 * 
	 * @param event
	 *            ExecutionEvent
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Resource henshinResource = null;

		if (HandlerUtil.getActiveMenuSelection(event) instanceof org.eclipse.jface.viewers.TreeSelection) {
			IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveMenuSelection(event);

			// We expect exactly one element:
			if (selection.size() == 1) {
				Object first = selection.getFirstElement();

				// if on IFile (e.g. explorer)
				if (first instanceof IFile) {
					IFile file = (IFile) first;

					// Must be a Henshin file:
					if ("henshin".equals(file.getFileExtension())) {
						URI uri = URI.createPlatformResourceURI(file.getFullPath().toOSString(), true);
						// Create a resource set and load the resource::
						ResourceSet henshinResourceSet = new ResourceSetImpl();
						henshinResource = henshinResourceSet.getResource(uri, true);
					}
				}
			}
		}

		if (henshinResource != null) {
			Diagnostic diagnostic = Diagnostician.INSTANCE.validate(henshinResource.getContents().get(0));
			if (henshinResource.getErrors().isEmpty() && ((diagnostic.getSeverity() == Diagnostic.OK)
					|| (diagnostic.getSeverity() == Diagnostic.WARNING))) {
				TransformationHenshin2HenshinText transformation = new TransformationHenshin2HenshinText();
				transformation.transformHenshinToHenshin_text(henshinResource, true);
			}
		}

		return null;
	}

}
