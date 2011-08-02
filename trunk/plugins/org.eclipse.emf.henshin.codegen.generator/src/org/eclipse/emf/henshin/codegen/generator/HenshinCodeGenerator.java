package org.eclipse.emf.henshin.codegen.generator;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.henshin.codegen.generator.internal.CodeGenFileUtil;
import org.eclipse.emf.henshin.codegen.generator.internal.CodeGenJavaUtil;
import org.eclipse.emf.henshin.codegen.model.GenHenshin;
import org.eclipse.emf.henshin.codegen.model.GenTransformation;
import org.eclipse.emf.henshin.codegen.model.TransformationEngine;
import org.eclipse.emf.henshin.codegen.templates.GenTransformationAdhoc;
import org.eclipse.emf.henshin.codegen.templates.GenTransformationInterface;
import org.eclipse.emf.henshin.codegen.templates.GenTransformationInterpreter;
import org.eclipse.jdt.core.IJavaProject;

/**
 * Henshin code generator.
 * @author Christian Krause
 */
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
		
		monitor.beginTask("Generating code", 4);
		GenHenshin genHenshin = genTrafo.getGenHenshin();
		
		try {
			
			// Create Java project:
			IJavaProject project = CodeGenJavaUtil.createJavaProject(
					genHenshin.getBaseDirectory(), genHenshin.getSourceDirectory(), "bin", new SubProgressMonitor(monitor,1));
			
			// Start the main code generation:
			String baseName = genTrafo.getTransformationClassFormatted();
			
			// Generate interface:
			if (!genHenshin.isSupressInterfaces()) {
				IFolder interfacePackage = CodeGenJavaUtil.createPackage(genHenshin.getInterfacePackage(), project);
				String interfaceName = genHenshin.applyInterfacePattern(baseName);
				CodeGenFileUtil.createFileFromString(
						interfacePackage, interfaceName + ".java", generate(genTrafo, true), 
						new SubProgressMonitor(monitor,1));
			} else {
				monitor.worked(1);
			}
			
			// Generate implementation:
			IFolder implementationPackage = CodeGenJavaUtil.createPackage(genHenshin.getImplementationPackage(), project);
			String implementationName = genHenshin.applyImplementationPattern(baseName);
			CodeGenFileUtil.createFileFromString(
					implementationPackage, implementationName + ".java", generate(genTrafo, false), 
					new SubProgressMonitor(monitor,1));

			// Refresh the project to get external updates:
			project.getProject().refreshLocal(IResource.DEPTH_INFINITE, new SubProgressMonitor(monitor,1));

		} catch (CoreException e) {
			return e.getStatus();
		}
		
		// Done.
		monitor.done();
		return Status.OK_STATUS;
		
	}

	/*
	 * Generate the code for a GenTransformation model.
	 */
	private static String generate(GenTransformation genTrafo, boolean interface_) {
		if (interface_) {
			return new GenTransformationInterface().generate(genTrafo);
		}
		if (genTrafo.getEngine()==TransformationEngine.INTERPRETER) {
			return new GenTransformationInterpreter().generate(genTrafo);
		} else {
			return new GenTransformationAdhoc().generate(genTrafo);			
		}
	}
	
}
