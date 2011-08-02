package org.eclipse.emf.henshin.codegen.generator.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

/**
 * Helper methods for dealing with Java projects (uses JDT).
 * @author Christian Krause
 */
public class CodeGenJavaUtil {
	
	/**
	 * Create a Java project. If the project exists already, nothing is changed.
	 * If the project exists, but is closed, it is opened, but besides this,
	 * nothing is done. If the project does not exist already, further 
	 * initialization is done:
	 * <ul>
	 * <li>The Java project nature is explicitly added to the project. 
	 * <li>Default <code>src</code> and <code>bin</code> folders are created.
	 * <li>The classpath is initialized with the source folder and the default JRE libraries.
	 * </ul>
	 * @param name Name of the Java project.
	 * @param srcFolder Initial source folder.
	 * @param binFolder Initial binaries folder.
	 * @param monitor Progress monitor to be used.
	 * @return Instance of IJavaProject.
	 * @throws CoreException If an exception occurs during creation.
	 */
	public static IJavaProject createJavaProject(String name, String srcFolder, String binFolder, IProgressMonitor monitor) throws CoreException {
		
		monitor.beginTask("Creating Java project", 6);
		IProject project = CodeGenFileUtil.createProject(name, new SubProgressMonitor(monitor, 1));

		// Create source folder.  
		IFolder folder = project.getFolder(srcFolder);
		if (!folder.exists()) {
			folder.create(true, true, new SubProgressMonitor(monitor, 1));
		} else {
			monitor.worked(1);
		}

		// Create binaries folder.  
		folder = project.getFolder(binFolder);
		if (!folder.exists()) {
			folder.create(true, true, new SubProgressMonitor(monitor, 1));
		} else {
			monitor.worked(1);
		}
		
		// Set Java project nature.
		String[] natures = { JavaCore.NATURE_ID };
		IProjectDescription description = project.getDescription();
		description.setNatureIds(natures);
		project.setDescription(description, new SubProgressMonitor(monitor, 1));
		
		// Create the Java project.
		IJavaProject javaProject = JavaCore.create(project);			

		// Set Java src and bin folder.
		IPath srcPath = project.getFullPath().append(srcFolder);
		IPath binPath = project.getFullPath().append(binFolder);
		
		javaProject.setOutputLocation(binPath, new SubProgressMonitor(monitor, 1));

		// Adjust the classpath.
		if (javaProject.getRawClasspath().length<=2) {
			IClasspathEntry[] classpath = new IClasspathEntry[2];
			classpath[0] = JavaCore.newSourceEntry(srcPath);
			classpath[1] = JavaCore.newContainerEntry(new Path("org.eclipse.jdt.launching.JRE_CONTAINER"));
			javaProject.setRawClasspath(classpath, new SubProgressMonitor(monitor, 1));
		} else {
			monitor.worked(1);
		}
		
		monitor.done();
		return javaProject;
	}
	
	/**
	 * Same as {@link #createJavaProject(String, String, String, IProgressMonitor)}, but
	 * with <code>src</code> as source folder and <code>bin</code>
	 * as initial binaries folder.
	 * @param name Name of the Java project.
	 * @param monitor Progress monitor to be used.
	 * @return Instance of IJavaProject.
	 * @throws CoreException If an exception occurs during creation.
	 */
	public static IJavaProject createJavaProject(String name, IProgressMonitor monitor) throws CoreException {
		return createJavaProject(name, "src", "bin", monitor);
	}
		
	/**
	 * Add a library to a Java project. This library might be a JAR or a ZIP file.
	 * The file is copied from a specified plugin and automatically added to the
	 * classpath of the Java project.
	 */
	public static IFile addLibrary(String pluginID, String path, IJavaProject javaProject, IProgressMonitor monitor) throws CoreException {

		// Copy the library file.
		IFile library = CodeGenFileUtil.copyFile(pluginID, path, javaProject.getProject(), path, monitor);
		IPath fullPath = library.getFullPath();

		// Add it to the classpath.
		addClasspathEntry(javaProject, JavaCore.newLibraryEntry(fullPath, fullPath, fullPath));
		return library;
		
	}
	
	/**
	 * Create a Java package in a specified project + source folder.
	 * If the folder existed already, nothing happens. It is not checked
	 * whether the source folder exists / is in the classpath of the project.
	 * @param name Name of the package to be created.
	 * @param javaProject Java project, that will be containing the package. 
	 * @param srcFolder Source folder where it should be created.
	 * @return IFolder instance of the package folder.
	 * @throws CoreException If a directory cannot be created.
	 */
	public static IFolder createPackage(String name, IJavaProject javaProject, String srcFolder) throws CoreException {

		String[] parts = name.split("\\.");
		IProject project = javaProject.getProject();
		IPath path = new Path(srcFolder);
		IFolder folder = null;
		
		for (int j=0; j<parts.length; j++) {
			path = path.append(parts[j]);
			folder = project.getFolder(path);
			if (!folder.exists()) {
				folder.create(true, true, new NullProgressMonitor());
			}
		}
		return folder;
		
	}
	
	/**
	 * Same as {@link #createPackage(String, IJavaProject, String)}, but
	 * using <code>src</code> as the source folder. Again, no
	 * check is be done, whether this folder actually exists.
	 * @param name Name of the package to be created.
	 * @param javaProject Java project, that will be containing the package. 
	 * @return IFolder instance of the package folder.
	 * @throws CoreException If a directory cannot be created.
	 */
	public static IFolder createPackage(String name, IJavaProject javaProject) throws CoreException {
		return createPackage(name, javaProject, "src");
	}
	
	/**
	 * Check if a classpath entry exists already and add it if not.
	 * @param javaProject Java project.
	 * @param entry Classpath entry to be added.
	 * @throws CoreException If any kind of exception occures.
	 */
	public static void addClasspathEntry(IJavaProject javaProject, IClasspathEntry entry) throws CoreException {
		
		IClasspathEntry[] classpath = javaProject.getRawClasspath();
		for (int i=0; i<classpath.length; i++) {
			if (classpath[i].getPath().equals(entry.getPath())) return;
		}
		
		IClasspathEntry[] newClasspath = new IClasspathEntry[classpath.length+1];		
		System.arraycopy(classpath, 0, newClasspath, 0, classpath.length);
		newClasspath[classpath.length] = entry;
		
		javaProject.setRawClasspath(newClasspath, new NullProgressMonitor());
		
	}
	
}
