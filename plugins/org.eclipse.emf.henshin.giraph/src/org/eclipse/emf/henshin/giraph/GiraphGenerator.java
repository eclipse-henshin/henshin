package org.eclipse.emf.henshin.giraph;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.henshin.giraph.templates.CompileXmlTemplate;
import org.eclipse.emf.henshin.giraph.templates.GetLibsXmlTemplate;
import org.eclipse.emf.henshin.giraph.templates.GiraphRuleTemplate;
import org.eclipse.emf.henshin.giraph.templates.HenshinUtilTemplate;
import org.eclipse.emf.henshin.giraph.templates.PomXmlTemplate;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;

public class GiraphGenerator {

	public static final String DEFAULT_PROJECT_NAME = "giraph-henshin-examples";

	public static final String DEFAULT_PACKAGE_NAME = "org.apache.giraph.henshin.examples";

	private String projectName = DEFAULT_PROJECT_NAME;

	private String packageName = DEFAULT_PACKAGE_NAME;

	private boolean masterLogging = true;

	private boolean vertexLogging = false;

	private boolean useUUIDs = true;

	private boolean exampleJSON = false;

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

	public void setExampleJSON(boolean exampleJSON) {
		this.exampleJSON = exampleJSON;
	}

	public IFile generate(Unit mainUnit, String className, IProgressMonitor monitor) throws CoreException {

		// Create project:
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(projectName);
		if (!project.exists()) {
			project.create(null);
		}
		if (!project.isOpen()) {
			project.open(null);
		}

		// Set Java project nature:
		IProjectDescription description = project.getDescription();
		description.setNatureIds(new String[] { JavaCore.NATURE_ID });
		project.setDescription(description, null);

		// Create Java project:
		IJavaProject javaProject = JavaCore.create(project);

		// Output folder:
		IFolder binFolder = createFolder(project, "bin");
		javaProject.setOutputLocation(binFolder.getFullPath(), null);

		// Classpath entries:
		List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
		IVMInstall vmInstall = JavaRuntime.getDefaultVMInstall();
		LibraryLocation[] locations = JavaRuntime.getLibraryLocations(vmInstall);
		for (LibraryLocation element : locations) {
			entries.add(JavaCore.newLibraryEntry(element.getSystemLibraryPath(), null, null));
		}
		javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), null);

		// Source folders:
		IFolder srcFolder = createFolder(project, "src");
		IFolder mainFolder = createFolder(srcFolder, "main");
		IFolder testFolder = createFolder(srcFolder, "test");
		IFolder javaFolder = createFolder(mainFolder, "java");
		IFolder javaTestFolder = createFolder(testFolder, "java");
		IFolder assemblyFolder = createFolder(mainFolder, "assembly");

		IPackageFragmentRoot mainPackRoot = javaProject.getPackageFragmentRoot(javaFolder);
		IPackageFragmentRoot testPackRoot = javaProject.getPackageFragmentRoot(javaTestFolder);
		IClasspathEntry[] oldEntries = javaProject.getRawClasspath();
		IClasspathEntry[] newEntries = new IClasspathEntry[oldEntries.length + 2];
		System.arraycopy(oldEntries, 0, newEntries, 0, oldEntries.length);
		newEntries[oldEntries.length] = JavaCore.newSourceEntry(mainPackRoot.getPath());
		newEntries[oldEntries.length + 1] = JavaCore.newSourceEntry(testPackRoot.getPath());
		javaProject.setRawClasspath(newEntries, null);

		IPackageFragment pack = javaProject.getPackageFragmentRoot(javaFolder).createPackageFragment(packageName,
				false, null);

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

			// Instance code:
			if (exampleJSON) {
				Collection<Rule> rules = GiraphUtil.collectRules(mainUnit);
				if (!rules.isEmpty()) {
					String instanceCode = GiraphUtil.getInstanceCode(rules.iterator().next());
					IFile jsonFile = project.getFile(new Path(className + ".json"));
					writeFile(jsonFile, instanceCode);
				}
			}

			// compile.xml
			String compileXml = new CompileXmlTemplate().generate(args);
			IFile compileXmlFile = assemblyFolder.getFile(new Path("compile.xml"));
			writeFile(compileXmlFile, compileXml);

			// pom.xml
			String pomXml = new PomXmlTemplate().generate(args);
			IFile pomXmlFile = project.getFile(new Path("pom.xml"));
			writeFile(pomXmlFile, pomXml);

			// get-libs.xml
			String getLibsXml = new GetLibsXmlTemplate().generate(args);
			IFile getLibsXmlFile = project.getFile(new Path("get-libs.xml"));
			writeFile(getLibsXmlFile, getLibsXml);

			// Refresh:
			project.refreshLocal(IResource.DEPTH_INFINITE, null);
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
}
