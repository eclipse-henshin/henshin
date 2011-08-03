package org.eclipse.emf.henshin.codegen.generator.internal;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;

/**
 * Helper methods for dealing with projects, folders and files.
 * @author Christian Krause
 */
public class CodeGenFileUtil {
	
	/**
	 * Create a file from a given string content. The argument folder
	 * has to exist already. No check is performed. 
	 * @param folder Folder where the file is created.
	 * @param name Name of the file.
	 * @param content Content of the file.
	 * @param monitor ProgressMonitor to be used.
	 * @return The created file.
	 * @throws CoreException If any kind of IO exception occurs.
	 */
	public static IFile createFileFromString(IContainer container, String name, 
			String content, boolean override, IProgressMonitor monitor) throws CoreException {
		if (content==null) {
			content = "";
		}
		monitor.beginTask("Creating " + name, 1);
		IFile file = container.getFile(new Path(name));
		InputStream input = new ByteArrayInputStream(content.getBytes());
		if (file.exists()) {
			if (override) {
				file.setContents(input, true, true, new SubProgressMonitor(monitor, 1));
			} else {
				monitor.worked(1);
			}
		} else {
			file.create(input, true, new SubProgressMonitor(monitor, 1));
		}
		try { 
			input.close();
		} catch (IOException e) {
			// Not critical.
		}
		monitor.done();		
		return file;
	}
	
	
	/**
	 * Copy a plug-in file into a project. This is useful for libraries, JAR-files etc.
	 * @param srcPlugin Plug-in containing the source file.
	 * @param srcPath Relative path of the source file.
	 * @param destProject Destination project.
	 * @param destPath Relative path of the destination.
	 * @param monitor Progress monitor to be used.
	 * @return The destination file.
	 * @throws CoreException If an IOException occurs.
	 */
	public static IFile copyFile(String srcPlugin, String srcPath, IProject destProject, String destPath, IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("Copying " + srcPath, 1);
		IFile destination = destProject.getFile(srcPath);
		if (destination.exists()) {
			monitor.done();
			return destination;
		}		
		try {
			// Find the file.
			URL bundleURL = FileLocator.find(Platform.getBundle(srcPlugin), Path.fromOSString(srcPath), null);
			URL fileURL = FileLocator.toFileURL(bundleURL);
			String absolutePath = fileURL.toString().replaceFirst(fileURL.getProtocol()+":", "").replace('/', java.io.File.separatorChar);
			// Copy the file.
			FileInputStream in = new FileInputStream(new java.io.File(absolutePath));
			destination.create(in, true, new SubProgressMonitor(monitor, 1));
		}
		catch (IOException e) {
			Status status = new Status(IStatus.ERROR, srcPlugin, 1, "Error copying file: " + srcPath, e);
			throw new CoreException(status);
		}
		finally {
			monitor.done();
		}
		return destination;
	}
	
}
