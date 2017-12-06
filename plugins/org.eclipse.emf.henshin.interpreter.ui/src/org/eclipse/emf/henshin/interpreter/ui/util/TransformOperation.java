/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.ui.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.henshin.interpreter.ApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.Assignment;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.AssignmentImpl;
import org.eclipse.emf.henshin.interpreter.impl.BasicApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.ui.HenshinInterpreterUIPlugin;
import org.eclipse.emf.henshin.interpreter.util.InterpreterUtil;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

public class TransformOperation extends WorkspaceModifyOperation {

	/**
	 * The unit to be applied.
	 */
	protected Unit unit;

	/**
	 * Configuration parameters.
	 */
	protected Collection<ParameterConfig> paramCfgs;

	/**
	 * Input Model URI.
	 */
	protected URI inputUri;

	/**
	 * Output Model URI.
	 */
	protected URI outputUri;

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit, Collection<ParameterConfig> paramCfgs) {
		this.unit = unit;
		this.paramCfgs = paramCfgs;
	}

	public void setInputURI(URI inputUri) {
		this.inputUri = inputUri;
	}

	public void setOutputURI(URI outputUri) {
		this.outputUri = outputUri;
	}

	public URI getInputUri() {
		return inputUri;
	}

	public URI getOutputUri() {
		return outputUri;
	}

	public Object getParameterValue(String parameterName) {
		ParameterConfig pCfg = getParameterConfiguration(parameterName);
		if (pCfg == null) {
			return null;
		}
		return pCfg.getValue();
	}

	public ParameterConfig getParameterConfiguration(String parameterName) {
		for (ParameterConfig pCfg : paramCfgs) {
			if (pCfg.getName().equals(parameterName)) {
				return pCfg;
			}
		}
		return null;
	}

	public Collection<ParameterConfig> getParameterConfigurations() {
		return paramCfgs;
	}

	public Map<String, Object> prepareParameterValues() {
		Map<String, Object> result = new HashMap<String, Object>();
		for (ParameterConfig paramCfg : paramCfgs) {
			if (paramCfg.isClear()) {
				continue;
			}
			result.put(paramCfg.getName(), paramCfg.getValue());
		}
		return result;
	}

	@Override
	protected void execute(final IProgressMonitor monitor)
			throws CoreException, InvocationTargetException,
			InterruptedException {

		monitor.beginTask("", 10);
		monitor.subTask("Initializing transformation...");

		ResourceSet resourceSet;
		if (unit.eResource() != null && unit.eResource().getResourceSet() != null) {
			resourceSet = unit.eResource().getResourceSet();
		} else {
			resourceSet = new ResourceSetImpl();
		}

		
		Resource input = resourceSet.getResource(inputUri, true);

		Assignment assignment = new AssignmentImpl(unit);
		for (Entry<String, Object> entry : prepareParameterValues().entrySet()) {
			Parameter param = unit.getParameter(entry.getKey());
			if (param != null) {
				assignment.setParameterValue(param, entry.getValue());
			}
		}
		monitor.worked(2);
		if (monitor.isCanceled()) {
			return;
		}

		Engine engine = new EngineImpl();
		ApplicationMonitor appMonitor = new BasicApplicationMonitor() {
			private int apps = 0;

			@Override
			public boolean isCanceled() {
				return canceled || monitor.isCanceled();
			}

			@Override
			public void notifyExecute(UnitApplication app, boolean success) {
				if (apps++ % 50 == 0) {
					monitor.subTask("Applied " + app.getUnit()
							+ (success ? "" : " (failed)")
							+ ", " + app.getEGraph().size()
							+ " objects");
				}
			}
		};

		try {
			if (!InterpreterUtil.applyToResource(assignment, engine, input,
					appMonitor) && !monitor.isCanceled()) {
				throw new CoreException(
						new Status(IStatus.WARNING,
								HenshinInterpreterUIPlugin.PLUGIN_ID,
								"Transformation could not be applied to given input model."));
			}
		} catch (Throwable t) {
			// NOTE we should use an error dialog with a "details" section showing the caught exception
			throw new CoreException(
					new Status(IStatus.ERROR,
							HenshinInterpreterUIPlugin.PLUGIN_ID,
							"Error applying transformation: " + t.getMessage(), t));
		}
		monitor.worked(4);
		if (monitor.isCanceled()) {
			return;
		}

		monitor.subTask("Saving result...");
		Resource output;
		if (inputUri.equals(outputUri)) {
			output = input;
		} else {
			output = resourceSet.createResource(outputUri);
			output.getContents().addAll(input.getContents());
		}

		Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMIResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		try {
			output.save(options);
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR,
					HenshinInterpreterUIPlugin.PLUGIN_ID,
					"Error saving transformation result.", e));
		}
		monitor.worked(2);
		if (monitor.isCanceled()) {
			return;
		}

		if (outputUri.isPlatformResource()) {
			IPath path = new Path(outputUri.toPlatformString(false));
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			if (file != null) {
				file.getParent().refreshLocal(2,
						SubMonitor.convert(monitor, 2));
			}
		}
		monitor.subTask("Finalizing transformation...");
		monitor.done();

	}

}
