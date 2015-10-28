package org.eclipse.emf.henshin.giraph;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.giraph.templates.BuildJarLaunchTemplate;
import org.eclipse.emf.henshin.giraph.templates.CompileXmlTemplate;
import org.eclipse.emf.henshin.giraph.templates.GetLibsXmlTemplate;
import org.eclipse.emf.henshin.giraph.templates.GiraphRuleTemplate;
import org.eclipse.emf.henshin.giraph.templates.HenshinUtilTemplate;
import org.eclipse.emf.henshin.giraph.templates.InstallHadoopXmlTemplate;
import org.eclipse.emf.henshin.giraph.templates.LaunchConfTemplate;
import org.eclipse.emf.henshin.giraph.templates.LaunchEnvXmlTemplate;
import org.eclipse.emf.henshin.giraph.templates.LaunchXmlTemplate;
import org.eclipse.emf.henshin.giraph.templates.PomXmlTemplate;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;

public class GiraphGenerator extends GiraphValidator {

	protected int refreshJobs = 0;

	/**
	 * Default constructor.
	 */
	public GiraphGenerator() {
		Job.getJobManager().addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void scheduled(IJobChangeEvent event) {
				if ("Refreshing resources...".equalsIgnoreCase(event.getJob().getName())) {
					refreshJobs++;
				}
			}
		});
	}

	public IFile generate(IProgressMonitor monitor) throws CoreException {

		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}

		monitor.beginTask("Generating Giraph Project", testEnvironment ? 40 : 30);

		monitor.subTask("Executing Templates...");
		String getLibsXml, giraphCode, utilCode, compileXml, pomXml, launchEnvXml, launchConf, launchXml, installHadoopXml, buildJarLaunch, inputCode;
		try {

			// Get Java home:
			IVMInstall jvm = JavaRuntime.getDefaultVMInstall();
			String javaHome = jvm.getInstallLocation().getAbsolutePath();

			// Generate rule data:
			Map<Rule, GiraphRuleData> ruleData = GiraphUtil.generateRuleData(mainUnit);

			// Create template arguments:
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("javaHome", javaHome);
			args.put("ruleData", ruleData);
			args.put("mainUnit", mainUnit);
			args.put("className", className);
			args.put("inputName", inputName);
			args.put("packageName", packageName);
			args.put("projectName", projectName);
			args.put("hostName", getHostName());
			args.put("masterLogging", masterLogging);
			args.put("vertexLogging", vertexLogging);
			args.put("useUUIDs", useUUIDs);
			args.put("segmentCount", 1);
			args.put("stairCount", 3);

			getLibsXml = new GetLibsXmlTemplate().generate(args);
			giraphCode = new GiraphRuleTemplate().generate(args);
			utilCode = new HenshinUtilTemplate().generate(args);
			compileXml = new CompileXmlTemplate().generate(args);
			pomXml = new PomXmlTemplate().generate(args);
			launchEnvXml = new LaunchEnvXmlTemplate().generate(args);
			launchXml = new LaunchXmlTemplate().generate(args);
			launchConf = new LaunchConfTemplate().generate(args);
			installHadoopXml = new InstallHadoopXmlTemplate().generate(args);
			buildJarLaunch = new BuildJarLaunchTemplate().generate(args);

			if (inputGraph != null) {
				inputCode = GiraphUtil.getInstanceCode(inputGraph);
			} else {
				inputCode = null;
			}

		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, "org.eclipse.emf.henshin.giraph", 0,
					"Error executing templates: " + e.getMessage(), e));
		}
		monitor.worked(1); // 1

		monitor.subTask("Initializing Project...");

		// Create project:
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(projectName);
		if (!project.exists()) {
			project.create(null);
		}
		if (!project.isOpen()) {
			project.open(null);
		}
		project.refreshLocal(IResource.DEPTH_INFINITE, new SubProgressMonitor(monitor, 1));
		monitor.worked(1); // 2

		// Set Java project nature:
		IProjectDescription description = project.getDescription();
		description.setNatureIds(new String[] { JavaCore.NATURE_ID });
		project.setDescription(description, null);

		// Create Java project:
		IJavaProject javaProject = JavaCore.create(project);
		monitor.worked(1); // 3

		// Output folder:
		IFolder binFolder = createFolder(project, "bin");
		javaProject.setOutputLocation(binFolder.getFullPath(), null);

		// Source folders:
		IFolder srcFolder = createFolder(project, "src");
		IFolder mainFolder = createFolder(srcFolder, "main");
		IFolder testFolder = createFolder(srcFolder, "test");
		IFolder javaFolder = createFolder(mainFolder, "java");
		IFolder javaTestFolder = createFolder(testFolder, "java");
		IFolder assemblyFolder = createFolder(mainFolder, "assembly");
		IFolder libFolder = createFolder(project, "lib");
		IFolder externalToolBuildersFolder = createFolder(project, ".externalToolBuilders");
		IFolder inputFolder = createFolder(project, "input");
		createFolder(project, "output");
		IFolder launchFolder = createFolder(project, "launch");
		monitor.worked(1); // 4

		// Generate lib/get-libs.xml
		IFile getLibsXmlFile = writeFile(libFolder, "get-libs.xml", getLibsXml);
		monitor.worked(1); // 5

		// Fetch the libraries:
		monitor.subTask("Fetching Giraph and Hadoop Libraries...");
		AntRunner runner = new AntRunner();
		runner.setBuildFileLocation(getLibsXmlFile.getLocation().toOSString());
		runner.setArguments("-Dmessage=\"Fetching Giraph and Hadoop Libraries\" -verbose");
		runner.run(new SubProgressMonitor(monitor, 5)); // 10

		monitor.subTask("Generating Code...");

		// Set classpath:
		List<IFile> libraries = new ArrayList<IFile>();
		List<IFile> sourceAttachments = new ArrayList<IFile>();
		for (Entry<URI, URI> lib : GiraphLibraries.LIBRARIES.entrySet()) {
			libraries.add(libFolder.getFile(lib.getKey().lastSegment()));
			sourceAttachments.add((lib.getValue() != null) ? libFolder.getFile(lib.getValue().lastSegment()) : null);
		}
		setupClassPath(javaProject, new IFolder[] { javaFolder, javaTestFolder }, libraries.toArray(new IFile[0]),
				sourceAttachments.toArray(new IFile[0]));

		// Create package:
		IPackageFragmentRoot packRoot = javaProject.getPackageFragmentRoot(javaFolder);
		IPackageFragment pack = packRoot.createPackageFragment(packageName, false, null);

		// Write compute class:
		IFile javaUnitFile = writeFile((IFolder) pack.getResource(), className + ".java", giraphCode);
		monitor.worked(1); // 11

		// Write utility class:
		writeFile((IFolder) pack.getResource(), "HenshinUtil.java", utilCode);
		monitor.worked(1); // 12

		// Example graph:
		if (inputCode != null) {
			IFile jsonFile = inputFolder.getFile(new Path(inputName + ".json"));
			if (!jsonFile.exists()) {
				writeFile(jsonFile, inputCode);
			}
		}
		monitor.worked(1); // 13

		// Launch files:
		writeFile(launchFolder, className + ".xml", launchXml);
		writeFile(launchFolder, className + ".launch", launchConf);
		monitor.worked(1); // 14

		// compile.xml
		writeFile(assemblyFolder, "compile.xml", compileXml);
		monitor.worked(1); // 15

		// pom.xml
		writeFile(project, "pom.xml", pomXml);
		monitor.worked(1); // 16

		// launch-env.xml
		writeFile(project, "launch-env.xml", launchEnvXml);
		monitor.worked(1); // 17

		// build-jar.launch
		writeFile(externalToolBuildersFolder, "build-jar.launch", buildJarLaunch);
		monitor.worked(1); // 18

		// Update external builders:
		final int refreshJobsBefore = refreshJobs;
		removeExternalToolBuilder(project, "build-jar.launch");
		addExternalToolBuilder(project, "build-jar.launch", true);
		monitor.worked(1); // 19

		if (testEnvironment) {
			monitor.subTask("Installing Hadoop Test Environment...");

			// testenv/install-hadoop.xml
			IFolder testenvFolder = createFolder(project, "testenv");
			IFile installHadoop = writeFile(testenvFolder, "install-hadoop.xml", installHadoopXml);

			// Fetch Hadoop:
			runner = new AntRunner();
			runner.setBuildFileLocation(installHadoop.getLocation().toOSString());
			runner.setArguments("-Dmessage=\"Installing Hadoop Test Environment\" -verbose");
			runner.run(new SubProgressMonitor(monitor, 10)); // 29

		}

		// Refresh:
		project.refreshLocal(IResource.DEPTH_INFINITE, new SubProgressMonitor(monitor, 1)); // 20 / 30

		// Wait for Maven refresh jobs (building jar):
		final int secs = 900;
		IProgressMonitor subMonitor = new SubProgressMonitor(monitor, 10);
		monitor.subTask("Building Jar...");
		subMonitor.beginTask("Building Jar...", secs);
		for (int i = 0; i < secs; i++) {
			if (refreshJobs > refreshJobsBefore) {
				break;
			}
			subMonitor.worked(1);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		subMonitor.done(); // 30 / 40

		monitor.done();
		return javaUnitFile;

	}

	protected void addExternalToolBuilder(IProject project, String name, boolean append) throws CoreException {
		IProjectDescription description = project.getDescription();
		ICommand[] oldCommands = description.getBuildSpec();
		for (ICommand com : oldCommands) {
			String val = com.getArguments().get("LaunchConfigHandle");
			if (val != null && val.endsWith(name)) {
				return;
			}
		}
		ICommand[] newCommands = new ICommand[oldCommands.length + 1];
		System.arraycopy(oldCommands, 0, newCommands, append ? 0 : 1, oldCommands.length);
		ICommand command = description.newCommand();
		command.setBuilderName("org.eclipse.ui.externaltools.ExternalToolBuilder");
		Map<String, String> commandArgs = command.getArguments();
		commandArgs.put("LaunchConfigHandle", "<project>/.externalToolBuilders/" + name);
		command.setArguments(commandArgs);
		command.setBuilding(IncrementalProjectBuilder.FULL_BUILD, true);
		// command.setBuilding(IncrementalProjectBuilder.INCREMENTAL_BUILD, true);
		newCommands[append ? newCommands.length - 1 : 0] = command;
		description.setBuildSpec(newCommands);
		project.setDescription(description, null);
	}

	protected IFolder createFolder(IContainer parent, String name) throws CoreException {
		IFolder folder = parent.getFolder(new Path(name));
		if (!folder.exists()) {
			folder.create(false, true, null);
		}
		return folder;
	}

	protected void removeExternalToolBuilder(IProject project, String name) throws CoreException {
		IProjectDescription description = project.getDescription();
		ICommand[] oldCommands = description.getBuildSpec();
		List<ICommand> newCommands = new ArrayList<ICommand>();
		for (ICommand com : oldCommands) {
			String val = com.getArguments().get("LaunchConfigHandle");
			if (val == null || !val.endsWith(name)) {
				newCommands.add(com);
			}
		}
		description.setBuildSpec(newCommands.toArray(new ICommand[0]));
		project.setDescription(description, null);
	}

	protected void setupClassPath(IJavaProject javaProject, IFolder[] sourceFolders, IFile[] libraries,
			IFile[] sourceAttachments) throws CoreException {
		List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
		entries.add(JavaCore
				.newContainerEntry(new Path(
						"org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.7")));
		for (IFolder sourceFolder : sourceFolders) {
			entries.add(JavaCore.newSourceEntry(javaProject.getPackageFragmentRoot(sourceFolder).getPath()));
		}
		for (int i = 0; i < libraries.length; i++) {
			entries.add(JavaCore.newLibraryEntry(libraries[i].getFullPath(),
					sourceAttachments[i] != null ? sourceAttachments[i].getFullPath() : null, null));
		}
		javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), true, null);
	}

	protected IFile writeFile(IContainer container, String fileName, String content) throws CoreException {
		IFile file = container.getFile(new Path(fileName));
		writeFile(file, content);
		return file;
	}

	protected void writeFile(IFile file, String content) throws CoreException {
		if (file.exists()) {
			file.setContents(new ByteArrayInputStream(content.getBytes()), IResource.FORCE, null);
		} else {
			file.create(new ByteArrayInputStream(content.getBytes()), IResource.FORCE, null);
		}
	}

}
