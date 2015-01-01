package org.eclipse.emf.henshin.giraph;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.GitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.TextProgressMonitor;

/**
 * Giraph setup helper.
 */
public class GiraphSetup {

	private final File gitRoot;

	public GiraphSetup(File gitRoot) {
		this.gitRoot = gitRoot;
	}

	public GiraphSetup() {
		this(new File(System.getProperty("user.home") + File.separator + "git"));
	}

	public void update(IProgressMonitor monitor) throws RuntimeException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		try {
			fetch("https://github.com/apache/giraph.git", "release-1.1", "giraph-1.1.0");
			fetch("https://github.com/apache/hadoop.git", "branch-0.20.203", "hadoop-0.20.203");
		} catch (GitAPIException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void fetch(String uri, String branch, String dest) throws GitAPIException, IOException {
		File destDir = new File(gitRoot, dest);
		GitCommand<?> command;
		ProgressMonitor monitor = new TextProgressMonitor();
		if (destDir.exists()) {
			command = Git.open(destDir).pull().setProgressMonitor(monitor);
		} else {
			command = Git.cloneRepository().setURI(uri).setBranch(branch).setDirectory(destDir)
					.setProgressMonitor(monitor);
		}
		command.call();
	}

	public static void main(String[] args) {
		GiraphSetup setup = new GiraphSetup();
		setup.update(null);
	}

}
