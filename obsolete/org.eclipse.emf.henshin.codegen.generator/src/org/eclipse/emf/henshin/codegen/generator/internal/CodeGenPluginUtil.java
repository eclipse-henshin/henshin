package org.eclipse.emf.henshin.codegen.generator.internal;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

public class CodeGenPluginUtil {
	
	/**
	 * Create or update a plug-in project.
	 * @param name Name of the project (this is the folder name)
	 * @param pluginId The plug-in ID.
	 * @param requiredPlugins List of required plug-ins.
	 * @param srcFolder The source folder.
	 * @param binFolder The class folder.
	 * @param monitor Progress monitor.
	 * @return The corresponding Java project.
	 * @throws CoreException On errors.
	 */
	public static IJavaProject createPluginProject(String name, String pluginId, List<String> requiredPlugins,
			String srcFolder, String binFolder, IProgressMonitor monitor) throws CoreException {
		
		monitor.beginTask("Creating plug-in project", 5);
		
		// Create the Java project:
		IJavaProject javaProject = CodeGenJavaUtil.createJavaProject(name, 
				srcFolder, binFolder, new SubProgressMonitor(monitor, 1));
		
		// Get the project:
		IProject project = javaProject.getProject();
		
		// Update the plug-in nature.
		CodeGenProjectUtil.addProjectNature(project, "org.eclipse.pde.PluginNature", new SubProgressMonitor(monitor, 1));
		
		// Update the build commands:
		updateBuildCommands(project, new SubProgressMonitor(monitor, 1));
		
		// Create the manifest file:
		createManifest(project, pluginId, requiredPlugins, srcFolder, binFolder, new SubProgressMonitor(monitor, 1));
		
		// Update the class path:
		IClasspathEntry entry = JavaCore.newContainerEntry(new Path("org.eclipse.pde.core.requiredPlugins"));
		CodeGenJavaUtil.addClasspathEntry(javaProject, entry);
		monitor.worked(1);
		
		// Done.
		monitor.done();
		return javaProject;
		
	}

	private static void updateBuildCommands(IProject project, IProgressMonitor monitor) throws CoreException {
		
		// Get the project's build commands:
		IProjectDescription description = project.getDescription();
		List<ICommand> buildCommands = new ArrayList<ICommand>(Arrays.asList(description.getBuildSpec()));
		
		// Add the manifest builder if necessary:
		String manifestBuilder = "org.eclipse.pde.ManifestBuilder";
		if (!buildCommands.contains(manifestBuilder)) {
			ICommand command = description.newCommand();
			command.setBuilderName(manifestBuilder);
			buildCommands.add(command);
		}

		// Add the schema builder if necessary:
		String schemaBuilder = "org.eclipse.pde.SchemaBuilder";
		if (!buildCommands.contains(schemaBuilder)) {
			ICommand command = description.newCommand();
			command.setBuilderName(schemaBuilder);
			buildCommands.add(command);
		}
		
		// Now update the build commands:
		description.setBuildSpec(buildCommands.toArray(new ICommand[0]));
		project.setDescription(description, monitor);
		monitor.done();

	}
	
	private static void createManifest(IProject project, String pluginId, 
			List<String> requiredPlugins, String srcFolder, String binFolder,
			IProgressMonitor monitor) throws CoreException {
		
		monitor.beginTask("Creating Manifest", 3);
		
		// Create the META-INF folder if necessary:
		IFolder metainf = project.getFolder("META-INF");
		if (!metainf.exists()) {
			metainf.create(true, true, new SubProgressMonitor(monitor, 1));
		} else {
			monitor.worked(1);
		}
		
		// Create the manifest file if it does not exist yet:
		String manifest = "MANIFEST.MF";
		if (!metainf.getFile(manifest).exists()) {
			StringWriter writer = new StringWriter();
			PrintWriter out = new PrintWriter(writer);
			out.println("Manifest-Version: 1.0");
			out.println("Bundle-ManifestVersion: 2");
			out.println("Bundle-SymbolicName: " + pluginId);
			out.println("Bundle-Version: 0.1.0");
			out.println("Bundle-RequiredExecutionEnvironment: JavaSE-1.6");
			for (int i=0; i<requiredPlugins.size(); i++) {
				String plugin = requiredPlugins.get(i);
				if (i==0) {
					plugin = "Require-Bundle: " + plugin;
				} else {
					plugin = " " + plugin;					
				}
				if (i<requiredPlugins.size()-1) {
					plugin = plugin + ",";
				}
				out.println(plugin);
			}
			CodeGenFileUtil.createFileFromString(metainf, manifest, 
					writer.toString(), false, new SubProgressMonitor(monitor, 1));			
		} else {
			monitor.worked(1);
		}
		
		// Create the build.properties file:
		String buildProps = "build.properties";
		if (!project.getFile(buildProps).exists()) {
			StringWriter writer = new StringWriter();
			PrintWriter out = new PrintWriter(writer);
			out.println("source.. = " + srcFolder + "/");
			out.println("output.. = " + binFolder + "/");		
			CodeGenFileUtil.createFileFromString(project, buildProps, 
					writer.toString(), false, new SubProgressMonitor(monitor, 1));
		} else {
			monitor.worked(1);
		}
		
		// That's it.
		monitor.done();
		
	}
}
