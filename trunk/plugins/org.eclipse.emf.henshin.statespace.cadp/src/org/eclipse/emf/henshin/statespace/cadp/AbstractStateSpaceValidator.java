/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved.
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace.cadp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceIndex;
import org.eclipse.emf.henshin.statespace.StateSpaceValidator;
import org.eclipse.emf.henshin.statespace.resource.StateSpaceResource;

/**
 * Abstract state space validator implementation.
 * Contains some helper methods for invoking commands
 * and converting files.
 * 
 * @author Christian Krause
 */
public abstract class AbstractStateSpaceValidator implements StateSpaceValidator {
	
	// Property to be checked:
	protected String property;
	
	// State space index:
	protected StateSpaceIndex index;
		
	/**
	 * Export a state space as an AUT file.
	 * @param stateSpace State space.
	 * @param aut The AUT file.
	 * @throws IOException On I/O errors.
	 */
	protected File exportAsAUT(StateSpace stateSpace, IProgressMonitor monitor) throws IOException {
		
		// File name prefix and suffix:
		String prefix = stateSpace.eResource().getURI().trimFileExtension().lastSegment() + "_aut_export";
		String suffix = ".aut";
		
		// Check the cached file. Make sure it is really the one we have created:
		File cached = getCachedFile(stateSpace);
		if (cached!=null) {
			String fileName = cached.getName();
			if (fileName.startsWith(prefix) && fileName.endsWith(suffix)) {
				return cached;
			}
		}
		
		// Otherwise we export the state space to an AUT file now:
		File aut = File.createTempFile(prefix, suffix);
		OutputStream out = new BufferedOutputStream(new FileOutputStream(aut));
		((StateSpaceResource) stateSpace.eResource()).exportAsAUT(out, monitor);
		
		// We cache the file of course:
		cacheFile(stateSpace, aut);
		return aut;
		
	}
	
	/**
	 * Convert a file using a given command.
	 * @param input Input file.
	 * @param output Output file.
	 * @param command Command.
	 * @throws Exception On 
	 */
	protected void convertFile(File input, File output, IProgressMonitor monitor, String... command) throws Exception {
		
		String label = "Running " + command[0] + "...";
		monitor.beginTask(label,1);
		monitor.subTask(label);
		
		command = Arrays.copyOf(command, command.length + 2);
		command[command.length-2] = input.getAbsolutePath();
		command[command.length-1] = output.getAbsolutePath();
		
		Process process = Runtime.getRuntime().exec(command);
		int exit;
		int wait = 50;
		while (true) {
			
			// Canceled?
			if (monitor.isCanceled()) {
				process.destroy();
				exit = 0;
				break;
			}
			
			// Poll:
			try {
				exit = process.exitValue();
				break;
			}
			catch (IllegalThreadStateException e) {}
			
			// Wait:
			try {
				Thread.sleep(wait);
			}
			catch (InterruptedException e) {}
			wait = (int) (wait * 1.1);
			
		}
		
		// Correct exit code?
		if (exit!=0) {
			throw new RuntimeException(command[0] + " returned exit code " + exit);
		}
		monitor.worked(1);
		monitor.done();
	}
	
	/**
	 * Write a string to a file.
	 * @param file File.
	 * @param content Content.
	 * @throws IOException I/O errors.
	 */
	protected void writeToFile(File file, String content) throws IOException {
		FileWriter writer = new FileWriter(file);
		writer.write(content);
		writer.close();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.Validator#setProperty(java.lang.String)
	 */
	@Override
	public void setProperty(String property) throws ParseException {
		this.property = property;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.Validator#setStateSpaceIndex(org.eclipse.emf.henshin.statespace.StateSpaceIndex)
	 */
	@Override
	public void setStateSpaceIndex(StateSpaceIndex index) {
		this.index = index;
	}

	
	/*
	 * FILE CACHING
	 */
	
	// Static cache for files (used internally):
	private final static Map<StateSpace,File> fileCache = Collections.synchronizedMap(new HashMap<StateSpace,File>());
	
	// Adapter that makes sure changed state spaces are removed from the file cache:
	private final static Adapter cacheAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(Notification event) {
			File file = fileCache.remove(event.getNotifier());
			if (file!=null && file.exists()) {
				file.delete();
			}
			((Notifier) event.getNotifier()).eAdapters().remove(this);
		}
	};
	
	/**
	 * Cache a file that is associated to a state space.
	 * Only one file per state space is allowed.
	 * @param stateSpace The state space.
	 * @param file The file to be cached.
	 */
	protected synchronized void cacheFile(StateSpace stateSpace, File file) {
		fileCache.put(stateSpace, file);
		stateSpace.eAdapters().add(cacheAdapter);
	}
	
	/**
	 * Get a cached file.
	 * @param stateSpace State space.
	 * @return Cached file.
	 */
	protected synchronized File getCachedFile(StateSpace stateSpace) {
		File file = fileCache.get(stateSpace);
		if (file!=null && file.exists()) {
			return file;
		} else {
			fileCache.remove(stateSpace);
			return null;
		}
	}
	
}
