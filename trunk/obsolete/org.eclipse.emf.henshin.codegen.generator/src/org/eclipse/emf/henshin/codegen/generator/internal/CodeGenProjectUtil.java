package org.eclipse.emf.henshin.codegen.generator.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

public class CodeGenProjectUtil {

	/**
	 * Create a project with the given name. If the project
	 * exists already, it will be returned. Further, the
	 * project is opened automatically.
	 * @param name Name of the project.
	 * @param monitor Progress monitor.
	 * @return The open project.
	 * @throws CoreException Exception, if project creation fails.
	 */
	public static IProject createProject(String name, IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("Creating project", 2);
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);		
		if (!project.exists()) {
			project.create(new SubProgressMonitor(monitor, 1));
		}
		if (!project.isOpen()) {
			project.open(new SubProgressMonitor(monitor, 1));
		}
		monitor.done();
		return project;
	}
	

	public static void addProjectNature(IProject project, String natureId, IProgressMonitor monitor) throws CoreException {
		IProjectDescription description = project.getDescription();
		List<String> natures = new ArrayList<String>(Arrays.asList(description.getNatureIds()));
		if (!natures.contains(natureId)) {
			natures.add(natureId);
			description.setNatureIds(natures.toArray(new String[0]));
			project.setDescription(description, monitor);
		}
		monitor.done();
	}
	
}
