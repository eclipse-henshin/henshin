package org.eclipse.emf.henshin.statespace.explorer.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.resource.StateSpaceResource;

/**
 * Export wizard that converts state spaces into the AUT format.
 * @author Christian Krause
 */
public class StateSpaceCADPExportWizard extends AbstractStateSpaceExportWizard {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.explorer.actions.AbstractStateSpaceExportWizard#doExport(org.eclipse.emf.henshin.statespace.StateSpace, org.eclipse.core.resources.IFile, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected void doExport(StateSpace stateSpace, IFile file, IProgressMonitor monitor) throws Exception {
		
		// Paste into a buffer:
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		((StateSpaceResource) stateSpace.eResource()).exportAsAUT(buffer);
		
		// Ant write buffer contents to the file:
		file.create(new ByteArrayInputStream(buffer.toByteArray()), true, monitor);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.explorer.actions.AbstractStateSpaceExportWizard#getDescription()
	 */
	@Override
	protected String getDescription() {
		return "Export State Space as AUT file.";
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.explorer.actions.AbstractStateSpaceExportWizard#getFileExtension()
	 */
	@Override
	protected String getFileExtension() {
		return "aut";
	}

}
