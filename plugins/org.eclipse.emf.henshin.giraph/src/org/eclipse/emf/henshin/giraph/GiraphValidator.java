package org.eclipse.emf.henshin.giraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.JavaConventions;

public class GiraphValidator extends GiraphConfig {

	public GiraphValidator() {
		super();
	}

	public IStatus validateAll() {
		IStatus result = pickSevereStatus(validateNames());
		if (testEnvironment) {
			result = pickSevereStatus(validatePlatformForTesting(), validateSshForTesting());
		}
		return result;
	}

	public static IStatus validatePlatformForTesting() {
		if (Platform.OS_WIN32.equals(Platform.getOS())) {
			return newStatus(IStatus.ERROR, "Test environment not support on Windows");
		}
		return Status.OK_STATUS;
	}

	public IStatus validateNames() {
		if (projectName == null || projectName.trim().isEmpty()) {
			return newStatus(IStatus.ERROR, "Missing project name");
		}
		if (packageName == null || packageName.trim().isEmpty()) {
			return newStatus(IStatus.ERROR, "Missing package name");
		}
		if (className == null || className.trim().isEmpty()) {
			return newStatus(IStatus.ERROR, "Missing class name");
		}
		if (inputName == null || inputName.trim().isEmpty()) {
			return newStatus(IStatus.ERROR, "Missing input name");
		}
		return pickSevereStatus(JavaConventions.validatePackageName(packageName, "1.6", "1.6"),
				JavaConventions.validateJavaTypeName(className, "1.6", "1.6"));
	}

	public static IStatus validateSshForTesting() {
		IStatus errorStatus = newStatus(IStatus.ERROR,
				"SSH public-key login to localhost not working. This is required for running Hadoop. "
						+ "See http://giraph.apache.org/quick_start.html to fix it.");
		try {
			Process process = Runtime.getRuntime().exec(
					new String[] { "ssh", "-o", "BatchMode=yes", "-o", "ConnectTimeout=10", "-o",
							"StrictHostKeyChecking=no", getHostName(), "echo", "OK" });
			if (process.waitFor() != 0) {
				return errorStatus;
			}
		} catch (IOException e) {
			return errorStatus;
		} catch (InterruptedException e) {
			return errorStatus;
		}
		return Status.OK_STATUS;
	}

	private static IStatus newStatus(int severity, String message) {
		return new Status(severity, "org.eclipse.emf.henshin.giraph", message);
	}

	private IStatus pickSevereStatus(IStatus... status) {
		int maxSeverity = IStatus.OK;
		for (IStatus s : status) {
			if (s.getSeverity() > maxSeverity) {
				maxSeverity = s.getSeverity();
			}
		}
		for (IStatus s : status) {
			if (s.getSeverity() == maxSeverity) {
				return s;
			}
		}
		return null;
	}

	protected static String getHostName() {
		String hostname = null;
		try {
			String line;
			Process p = Runtime.getRuntime().exec("hostname");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				if (!line.trim().isEmpty()) {
					hostname = line;
					break;
				}
			}
			input.close();
		} catch (IOException e) {
			hostname = null;
		}
		if (hostname == null) {
			hostname = "localhost";
		}
		return hostname;
	}

}