package org.eclipse.emf.henshin.codegen.generator;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.henshin.codegen.model.GenHenshin;
import org.eclipse.emf.henshin.codegen.model.GenTransformation;


public class HenshinCodeGenerator {
	
	/**
	 * Generate the transformation code. This delegates to {@link #generate(GenTransformation, IProgressMonitor)}.
	 * @param genHenshin GenHenshin model.
	 * @param monitor Progress monitor.
	 * @return Status.
	 */
	public static IStatus generate(GenHenshin genHenshin, IProgressMonitor monitor) {
		monitor.beginTask("Generate Transformation Code...", genHenshin.getGenTransformations().size());
		IStatus result = Status.OK_STATUS;
		for (GenTransformation genTrafo : genHenshin.getGenTransformations()) {
			IStatus status = generate(genTrafo, new SubProgressMonitor(monitor,1));
			if (status.getSeverity()>result.getSeverity()) {
				result = status;
			}
		}
		monitor.done();
		return result;
	}

	/**
	 * Generate the transformation code.
	 * @param genTrafo GenTransformation model.
	 * @param monitor Progress monitor.
	 * @return Status.
	 */
	public static IStatus generate(GenTransformation genTrafo, IProgressMonitor monitor) {
		
	    // Get project root folder as absolute file system path:
	    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
	    IResource resource = root.findMember(new Path(genTrafo.getGenHenshin().getDirectory()));
	    String containerName = resource.getLocation().toPortableString();

	    // createJavaProject("ddd", monitor);
	    	    
	    // Refresh the project to get external updates:
	    try {
			resource.refreshLocal(IResource.DEPTH_INFINITE, monitor);
		} catch (CoreException e) {}
		
		// Done.
		return Status.OK_STATUS;
		
	}

}
