package org.eclipse.emf.henshin.giraph;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.giraph.templates.BuildJarLaunchTemplate;
import org.eclipse.emf.henshin.giraph.templates.CompileXmlTemplate;
import org.eclipse.emf.henshin.giraph.templates.GetHadoopXmlTemplate;
import org.eclipse.emf.henshin.giraph.templates.GetLibsLaunchTemplate;
import org.eclipse.emf.henshin.giraph.templates.GetLibsXmlTemplate;
import org.eclipse.emf.henshin.giraph.templates.GiraphRuleTemplate;
import org.eclipse.emf.henshin.giraph.templates.HenshinUtilTemplate;
import org.eclipse.emf.henshin.giraph.templates.PomXmlTemplate;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;

public class GiraphGenerator {

	public static final String DEFAULT_PROJECT_NAME = "giraph-henshin-examples";

	public static final String DEFAULT_PACKAGE_NAME = "org.apache.giraph.henshin.examples";

	private String projectName = DEFAULT_PROJECT_NAME;

	private String packageName = DEFAULT_PACKAGE_NAME;

	private boolean masterLogging = true;

	private boolean vertexLogging = false;

	private boolean useUUIDs = true;

	public GiraphGenerator() {
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setMasterLogging(boolean masterLogging) {
		this.masterLogging = masterLogging;
	}

	public void setVertexLogging(boolean vertexLogging) {
		this.vertexLogging = vertexLogging;
	}

	public void setUseUUIDs(boolean useUUIDs) {
		this.useUUIDs = useUUIDs;
	}

	public IFile generate(Unit mainUnit, String className, IProgressMonitor monitor) throws CoreException {

		monitor.beginTask("Generating Giraph Project", 20);

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

		// Set Java project nature:
		IProjectDescription description = project.getDescription();
		description.setNatureIds(new String[] { JavaCore.NATURE_ID });
		project.setDescription(description, null);

		// Create Java project:
		IJavaProject javaProject = JavaCore.create(project);
		monitor.worked(1); // 2

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
		IFolder testenvFolder = createFolder(project, "testenv");
		IFolder graphsFolder = createFolder(testenvFolder, "graphs");
		monitor.worked(1); // 3

		// Classpath:
		List<IFile> libraries = new ArrayList<IFile>();
		List<IFile> sourceAttachments = new ArrayList<IFile>();
		for (Entry<URI, URI> lib : GiraphLibraries.LIBRARIES.entrySet()) {
			libraries.add(libFolder.getFile(lib.getKey().lastSegment()));
			sourceAttachments.add((lib.getValue() != null) ? libFolder.getFile(lib.getValue().lastSegment()) : null);
		}
		setupClassPath(javaProject, new IFolder[] { javaFolder, javaTestFolder }, libraries.toArray(new IFile[0]),
				sourceAttachments.toArray(new IFile[0]));
		monitor.worked(1); // 4

		IPackageFragment pack = javaProject.getPackageFragmentRoot(javaFolder).createPackageFragment(packageName,
				false, null);
		monitor.worked(1); // 5

		try {

			Map<String, Object> args = new HashMap<String, Object>();
			args.put("ruleData", GiraphUtil.generateRuleData(mainUnit));
			args.put("mainUnit", mainUnit);
			args.put("className", className);
			args.put("packageName", packageName);
			args.put("projectName", projectName);
			args.put("masterLogging", new Boolean(masterLogging));
			args.put("vertexLogging", new Boolean(vertexLogging));
			args.put("useUUIDs", new Boolean(useUUIDs));
			args.put("segmentCount", 1);

			// Compute class:
			String giraphCode = new GiraphRuleTemplate().generate(args);
			IFile javaUnitFile = ((IFolder) pack.getResource()).getFile(new Path(className + ".java"));
			writeFile(javaUnitFile, giraphCode);

			// Utility class:
			String utilCode = new HenshinUtilTemplate().generate(args);
			IFile javaUtilFile = ((IFolder) pack.getResource()).getFile(new Path("HenshinUtil.java"));
			writeFile(javaUtilFile, utilCode);
			monitor.worked(1); // 6

			// Example graph:
			Collection<Rule> rules = GiraphUtil.collectRules(mainUnit);
			if (!rules.isEmpty()) {
				String instanceCode = GiraphUtil.getInstanceCode(rules.iterator().next());
				IFile jsonFile = graphsFolder.getFile(new Path(className + ".json"));
				if (!jsonFile.exists()) {
					writeFile(jsonFile, instanceCode);
				}
			}
			monitor.worked(1); // 7

			// compile.xml
			String compileXml = new CompileXmlTemplate().generate(args);
			IFile compileXmlFile = assemblyFolder.getFile(new Path("compile.xml"));
			writeFile(compileXmlFile, compileXml);

			// pom.xml
			String pomXml = new PomXmlTemplate().generate(args);
			IFile pomXmlFile = project.getFile(new Path("pom.xml"));
			writeFile(pomXmlFile, pomXml);
			monitor.worked(1); // 8

			// get-hadoop.xml
			String getHadoopXml = new GetHadoopXmlTemplate().generate(args);
			IFile getHadoopXmlFile = testenvFolder.getFile(new Path("get-hadoop.xml"));
			writeFile(getHadoopXmlFile, getHadoopXml);

			// get-libs.xml
			String getLibsXml = new GetLibsXmlTemplate().generate(args);
			IFile getLibsXmlFile = project.getFile(new Path("get-libs.xml"));
			writeFile(getLibsXmlFile, getLibsXml);
			monitor.worked(1); // 9

			// get-libs.launch
			String getLibsLaunch = new GetLibsLaunchTemplate().generate(args);
			IFile getLibsLaunchFile = externalToolBuildersFolder.getFile(new Path("get-libs.launch"));
			writeFile(getLibsLaunchFile, getLibsLaunch);
			addExternalToolBuilder(project, "get-libs.launch", false);

			// build-jar.launch
			String buildJarLaunch = new BuildJarLaunchTemplate().generate(args);
			IFile buildJarLaunchFile = externalToolBuildersFolder.getFile(new Path("build-jar.launch"));
			writeFile(buildJarLaunchFile, buildJarLaunch);
			monitor.worked(1); // 10

			// Full build:
			monitor.setTaskName("Fetching Giraph and Hadoop Libraries");
			project.build(IncrementalProjectBuilder.FULL_BUILD, new SubProgressMonitor(monitor, 8));

			// Update external builders:
			removeExternalToolBuilder(project, "get-libs.launch");
			addExternalToolBuilder(project, "build-jar.launch", true);

			// Refresh:
			project.refreshLocal(IResource.DEPTH_INFINITE, new SubProgressMonitor(monitor, 2));

			monitor.done();
			return javaUnitFile;

		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, "org.eclipse.emf.henshin.giraph", 0,
					"Error generating Giraph code: " + e.getMessage(), e));
		}

	}

	private IFolder createFolder(IContainer parent, String name) throws CoreException {
		IFolder folder = parent.getFolder(new Path(name));
		if (!folder.exists()) {
			folder.create(false, true, null);
		}
		return folder;
	}

	private void writeFile(IFile file, String content) throws CoreException {
		if (file.exists()) {
			file.setContents(new ByteArrayInputStream(content.getBytes()), IResource.FORCE, null);
		} else {
			file.create(new ByteArrayInputStream(content.getBytes()), IResource.FORCE, null);
		}
	}

	private void setupClassPath(IJavaProject javaProject, IFolder[] sourceFolders, IFile[] libraries,
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

	private void addExternalToolBuilder(IProject project, String name, boolean append) throws CoreException {
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
		command.setBuilding(IncrementalProjectBuilder.INCREMENTAL_BUILD, true);
		newCommands[append ? newCommands.length - 1 : 0] = command;
		description.setBuildSpec(newCommands);
		project.setDescription(description, null);
	}

	private void removeExternalToolBuilder(IProject project, String name) throws CoreException {
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

}
