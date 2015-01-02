package org.eclipse.emf.henshin.giraph;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.eclipse.emf.henshin.giraph.templates.GiraphRuleTemplate;
import org.eclipse.emf.henshin.giraph.templates.HenshinUtilTemplate;
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

	private String projectName = "giraph-henshin";

	private String packageName = "org.apache.giraph.henshin";

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
		IFolder binFolder = project.getFolder("bin");
		if (!binFolder.exists()) {
			binFolder.create(false, true, null);
		}
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
		IFolder srcFolder = project.getFolder("src");
		if (!srcFolder.exists()) {
			srcFolder.create(false, true, null);
		}
		IFolder mainFolder = srcFolder.getFolder("main");
		if (!mainFolder.exists()) {
			mainFolder.create(false, true, null);
		}
		IFolder javaFolder = mainFolder.getFolder("java");
		if (!javaFolder.exists()) {
			javaFolder.create(false, true, null);
		}

		IPackageFragmentRoot packRoot = javaProject.getPackageFragmentRoot(javaFolder);
		IClasspathEntry[] oldEntries = javaProject.getRawClasspath();
		IClasspathEntry[] newEntries = new IClasspathEntry[oldEntries.length + 1];
		System.arraycopy(oldEntries, 0, newEntries, 0, oldEntries.length);
		newEntries[oldEntries.length] = JavaCore.newSourceEntry(packRoot.getPath());
		javaProject.setRawClasspath(newEntries, null);

		IPackageFragment pack = javaProject.getPackageFragmentRoot(javaFolder).createPackageFragment(
				"org.apache.giraph.henshin", false, null);

		try {

			Map<String, Object> args = new HashMap<String, Object>();
			args.put("ruleData", GiraphUtil.generateRuleData(mainUnit));
			args.put("mainUnit", mainUnit);
			args.put("className", className);
			args.put("packageName", packageName);
			args.put("masterLogging", new Boolean(masterLogging));
			args.put("vertexLogging", new Boolean(vertexLogging));
			args.put("useUUIDs", new Boolean(useUUIDs));
			args.put("segmentCount", 1);
			GiraphRuleTemplate template = new GiraphRuleTemplate();
			String giraphCode = template.generate(args);

			// ICompilationUnit cu = pack.createCompilationUnit("HenshinUtil", buffer.toString(), false, null);
			IFile javaUnitFile = ((IFolder) pack.getResource()).getFile(new Path(className + ".java"));
			if (javaUnitFile.exists()) {
				javaUnitFile.setContents(new ByteArrayInputStream(giraphCode.getBytes()), IResource.FORCE, null);
			} else {
				javaUnitFile.create(new ByteArrayInputStream(giraphCode.getBytes()), IResource.FORCE, null);
			}

			// Data code:
			String dataCode = new HenshinUtilTemplate().generate(args);
			IFile javaDataFile = ((IFolder) pack.getResource()).getFile(new Path("HenshinUtil.java"));
			if (javaDataFile.exists()) {
				javaDataFile.setContents(new ByteArrayInputStream(dataCode.getBytes()), IResource.FORCE, null);
			} else {
				javaDataFile.create(new ByteArrayInputStream(dataCode.getBytes()), IResource.FORCE, null);
			}

			// Instance code:
			if (exampleJSON) {
				Collection<Rule> rules = GiraphUtil.collectRules(mainUnit);
				if (!rules.isEmpty()) {
					String instanceCode = GiraphUtil.getInstanceCode(rules.iterator().next());
					IFile jsonFile = project.getFile(new Path(className + ".json"));
					if (jsonFile.exists()) {
						jsonFile.setContents(new ByteArrayInputStream(instanceCode.getBytes()), IResource.FORCE, null);
					} else {
						jsonFile.create(new ByteArrayInputStream(instanceCode.getBytes()), IResource.FORCE, null);
					}
				}
			}

			project.refreshLocal(IResource.DEPTH_INFINITE, null);

			return javaUnitFile;

		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, "org.eclipse.emf.henshin.giraph", 0,
					"Error generating Giraph code: " + e.getMessage(), e));
		}

	}

}
