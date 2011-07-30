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
import org.eclipse.jdt.core.IJavaProject;


public class HenshinCodeGenerator {
	
	/**
	 * Generate the transformation code. This delegates to {@link #generate(GenTransformation, IProgressMonitor)}.
	 * @param genHenshin GenHenshin model.
	 * @param monitor Progress monitor.
	 * @throws CoreException 
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
	 * @throws CoreException On errors.
	 */
	public static IStatus generate(GenTransformation genTrafo, IProgressMonitor monitor) {
		
		monitor.beginTask("Generating code", 10);
		GenHenshin genHenshin = genTrafo.getGenHenshin();
				
		try {
			String sourceDir = genHenshin.getSourceDirectory();
			
			// Create Java project:
			IJavaProject project = HenshinCodeGenUtil.createJavaProject(
					genHenshin.getPluginID(), sourceDir, "bin", new SubProgressMonitor(monitor,1));
			
			//HenshinCodeGenUtil.createFileFromString(sourceDir + "Test.java", name, content, monitor)

			// Refresh the project to get external updates:
			try {
				project.getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
			} catch (CoreException e) {}

		} catch (CoreException e) {
			return e.getStatus();
		}
		
		// Done.
		monitor.done();
		return Status.OK_STATUS;
		
	}

}
