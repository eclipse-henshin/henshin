package org.eclipse.emf.henshin.giraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.jdt.core.JavaConventions;

public class GiraphValidator extends GiraphConfig {

	public GiraphValidator() {
		super();
	}

	public IStatus validateAll() {
		IStatus result = pickSevereStatus(validateNames(), validateModel());
		if (testEnvironment) {
			result = pickSevereStatus(validatePlatformForTesting(), validateSshForTesting());
		}
		return result;
	}

	public IStatus validateNames() {
		if (projectName == null || projectName.trim().isEmpty()) {
			return newStatus(IStatus.ERROR, "Empty project name");
		}
		if (packageName == null || packageName.trim().isEmpty()) {
			return newStatus(IStatus.ERROR, "Empty package name");
		}
		if (className == null || className.trim().isEmpty()) {
			return newStatus(IStatus.ERROR, "Empty class name");
		}
		if (inputName == null || inputName.trim().isEmpty()) {
			return newStatus(IStatus.ERROR, "Empty input name");
		}
		return pickSevereStatus(JavaConventions.validatePackageName(packageName, "1.6", "1.6"),
				JavaConventions.validateJavaTypeName(className, "1.6", "1.6"));
	}

	public IStatus validateModel() {
		if (mainUnit == null) {
			return newStatus(IStatus.ERROR, "Main unit not set");
		}
		List<Unit> allUnits = new ArrayList<Unit>();
		allUnits.add(mainUnit);
		allUnits.addAll(mainUnit.getSubUnits(true));
		IStatus warning = null;
		for (Unit unit : allUnits) {
			if (unit.getName() == null || unit.getName().trim().isEmpty()) {
				return newStatus(IStatus.ERROR, "Empty unit or rule name");
			}
			if (!unit.getParameters().isEmpty()) {
				warning = newStatus(IStatus.WARNING, "Unit or rule parameters are not supported");
			}
			if (unit instanceof Rule) {
				Rule rule = (Rule) unit;
				if (rule.getMultiRules().size() != 1) {
					return newStatus(IStatus.ERROR, "Rule " + rule.getName()
							+ " must have exactly 1 multi-rule, but has " + rule.getMultiRules().size() 
							+ ". You must add '*' to all node actions, e.g. " + ((char) 171) + "create*"
							+ ((char) 187) + " instead of " + ((char) 171) + "create" + ((char) 187) + ".");
				}
				if (!rule.getLhs().getNodes().isEmpty() || !rule.getRhs().getNodes().isEmpty()) {
					return newStatus(IStatus.ERROR, "Rule " + rule.getName() + " must have an empty kernel rule");
				}
				if (rule.getLhs().getFormula() != null) {
					return newStatus(IStatus.ERROR, "Rule " + rule.getName() + " must not have an associated formula");
				}
				Rule multiRule = rule.getMultiRules().get(0);
				if (!multiRule.getMultiRules().isEmpty()) {
					return newStatus(IStatus.ERROR, "Multi-rule of rule " + rule.getName()
							+ " must not have nested multi-rules");
				}
				if (multiRule.getLhs().getNodes().isEmpty()) {
					return newStatus(IStatus.ERROR, "Multi-rule LHS of rule " + rule.getName() + " empty");
				}
				for (Node node : multiRule.getActionNodes(null)) {
					if (!node.getAttributes().isEmpty()) {
						warning = newStatus(IStatus.WARNING, "Attributes are not supported");
					}
				}
				if (!rule.getAttributeConditions().isEmpty() || !multiRule.getAttributeConditions().isEmpty()) {
					warning = newStatus(IStatus.WARNING, "Attribute conditions are not supported");
				}
			}
		}
		if (warning != null) {
			return warning;
		}
		return Status.OK_STATUS;
	}

	public static IStatus validatePlatformForTesting() {
		if (Platform.OS_WIN32.equals(Platform.getOS())) {
			return newStatus(IStatus.ERROR, "Test environment not supported on Windows");
		}
		String hostname = getHostName();
		if (hostname == null || hostname.trim().isEmpty()) {
			return newStatus(IStatus.ERROR, "Error determining hostname");
		}
		for (Character ch : hostname.toCharArray()) {
			if (Character.isUpperCase(ch)) {
				return newStatus(IStatus.WARNING, "Warning: upper-case character in hostname detected (" + hostname
						+ "). If you get connection errors, consider switching to a lower-case hostname.");
			}
		}
		return Status.OK_STATUS;
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