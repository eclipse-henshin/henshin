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
package org.eclipse.emf.henshin.statespace.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.zip.ZipEntry;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpacePlugin;
import org.eclipse.emf.henshin.statespace.Transition;

/**
 * Resource implementation for state spaces.
 * @author Christian Krause
 */
public class StateSpaceResource extends ResourceImpl {
	
	/**
	 * File extension for state space files.
	 */
	public static final String FILE_EXTENSION = "statespace";
	
	/**
	 * Default constructor.
	 */
	public StateSpaceResource() {
		super();
	}
	
	/**
	 * Convenience constructor.
	 * @param uri URI of the resource.
	 */
	public StateSpaceResource(URI uri) {
		super(uri);
	}
	
	/**
	 * Get the state space contained in this resource.
	 * @return The state space.
	 */
	public StateSpace getStateSpace() {
		for (EObject item : getContents()) {
			if (item instanceof StateSpace) return (StateSpace) item;
		}
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceImpl#doSave(java.io.OutputStream, java.util.Map)
	 */
	@Override
	protected void doSave(OutputStream out, Map<?, ?> options) throws IOException {
		StateSpaceSerializer serializer = new StateSpaceSerializer();
		serializer.write(getStateSpace(), out);
		out.flush();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceImpl#doLoad(java.io.InputStream, java.util.Map)
	 */
	@Override
	protected void doLoad(InputStream in, Map<?, ?> options) throws IOException {
		StateSpaceDeserializer deserializer = new StateSpaceDeserializer();
		deserializer.read(this, in);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceImpl#useZip()
	 */
	@Override
	protected boolean useZip() {
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceImpl#newContentZipEntry()
	 */
	@Override
	protected ZipEntry newContentZipEntry() {
		return new ZipEntry("statespace.bin");
	}
	
	/**
	 * Export the state space in this resource into the Aldebaran format.
	 * @param out Output stream.
	 * @throws IOException On I/O errors.
	 */
	public void exportAsAUT(OutputStream out, IProgressMonitor monitor) throws IOException {
		
		// Get the state space:
		StateSpace stateSpace = getStateSpace();
		int states = stateSpace.getStates().size();
		monitor.beginTask("Exporting state space", states+1);

		// Make sure that there is exactly one initial state.
		if (stateSpace.getInitialStates().size()!=1) {
			StateSpacePlugin.INSTANCE.logError("AUT format can encode only state spaces with exactly one initial state!", null);
			if (stateSpace.getInitialStates().isEmpty()) throw new IOException();
		}
		int initial = stateSpace.getStates().indexOf(stateSpace.getInitialStates().get(0));
		
		// Write the header.
		OutputStreamWriter writer = new OutputStreamWriter(out);
		writer.write("des (" + initial + "," + stateSpace.getTransitionCount() + "," + stateSpace.getStates().size() + ")\n");
		monitor.worked(1);
		
		// Iterate over all states:
		for (int source=0; source<states; source++) {
			for (Transition transition : stateSpace.getStates().get(source).getOutgoing()) {
				writer.write("(" + source + ",");
				writer.write("\"" + transition.getRule().getName() + "\",");
				writer.write(stateSpace.getStates().indexOf(transition.getTarget()) + ")\n");			
			}
			monitor.worked(1);
		}
		writer.close();
		monitor.done();
		
	}
	
}
