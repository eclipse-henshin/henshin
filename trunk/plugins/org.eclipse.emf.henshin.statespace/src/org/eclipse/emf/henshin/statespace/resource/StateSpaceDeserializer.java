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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.Model;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateEqualityHelper;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.impl.ModelImpl;

/**
 * State space deserialization class.
 * @author Christian Krause
 */
public class StateSpaceDeserializer {
	
	// Input stream.
	private InputStream in;
	
	/**
	 * Deserialize a state space.
	 * @param in Input stream.
	 * @result State space.
	 * @throws IOException on I/O errors.
	 */
	public void read(StateSpaceResource resource, InputStream in) throws IOException {
		
		this.in = in;
		
		// Create a new state space:
		StateSpace stateSpace = StateSpaceFactory.eINSTANCE.createStateSpace();
		
		// Header:
		int marker = readShort(); // Marker
		if (marker!=StateSpaceSerializer.MARKER) throw new IOException("State space file marker not found"); // Marker
		
		int version = readShort(); // Version number
		if (version<0 || version>2) throw new IOException("Unsupported format version: " + version);
		
		int equalityType = readShort(); // Equality type
		StateEqualityHelper helper = StateSpaceFactory.eINSTANCE.createStateEqualityHelper();
		helper.setUseGraphEquality((equalityType & 1)==1);
		
		// In version 2, "ignore" attributes changed to "use":
		if (version>=2) {
			helper.setUseObjectAttributes((equalityType & 2)==2);
			helper.setUseObjectKeys((equalityType & 4)==4);	
		} else {
			helper.setUseObjectAttributes((equalityType & 2)==0);
			helper.setUseObjectKeys((equalityType & 4)==0);
		}		
		stateSpace.setEqualityHelper(helper);
		
		int ruleCount = readShort(); // Rule count
		int stateCount = readInt(); // State count
		int transitionCount = readInt(); // Transition count
		
		int[] data = readData(); // Meta-data
		stateSpace.setTransitionCount(transitionCount);
		stateSpace.setData(data);
		
		// Create states:
		for (int i=0; i<stateCount; i++) {
			State state = StateSpaceFactory.eINSTANCE.createState();
			state.setIndex(i);
			stateSpace.getStates().add(state);
		}
		
		// Load rules:
		for (int i=0; i<ruleCount; i++) {
			URI uri = URI.createURI(readString());
			URI resolved = uri.resolve(resource.getURI());
			Rule rule = (Rule) resource.getResourceSet().getEObject(resolved,true);
			rule.eResource().setURI(uri.trimFragment());
			stateSpace.getRules().add(rule);
		}
			
		// Load states and transitions:
		transitionCount = 0;
		for (State state : stateSpace.getStates()) {
			
			// Initial states have a model URI:
			String modelUri = readString();
			
			// Read meta-data:
			state.setData(readData());
			
			// Check if it was an initial state:
			if (modelUri!=null) {
				
				// Load the model:
				URI uri = URI.createURI(modelUri);
				URI resolved = uri.resolve(resource.getURI());
				Resource contents = resource.getResourceSet().getResource(resolved,true);
				contents.setURI(uri);
				Model model = new ModelImpl(contents);
				if (helper.isUseObjectKeys()) {
					model.setObjectKeys(state.getObjectKeys());
				}
				state.setModel(model);
				
				// Add it to the list of initial states:
				stateSpace.getInitialStates().add(state);
			}

			// Check if it is an open state:
			if (state.isOpen()) {
				stateSpace.getOpenStates().add(state);
			}
			
			// Transitions:
			int transitions = readShort();
			for (int i=0; i<transitions; i++) {
				Transition transition = StateSpaceFactory.eINSTANCE.createTransition();
				transition.setRule(stateSpace.getRules().get(readShort()));
				if (version==0) {
					transition.setMatch(readShort());	// In version 0, the match is stored here.
				} else {
					transition.setData(readData());		// Since version 1, transitions implement Storage.
				}
				transition.setTarget(stateSpace.getStates().get(readInt()));
				state.getOutgoing().add(transition);
			}
			transitionCount += transitions;

		}
		
		// Update transition count:
		stateSpace.setTransitionCount(transitionCount);
		
		// Update the object types:
		stateSpace.updateSupportedTypes();
		
		// We expect EOF now:
		if (in.read()>=0) {
			throw new IOException("Expected end of file");
		}
		
		// Attach the state space to the resource:
		resource.getContents().clear();
		resource.getContents().add(stateSpace);
		
		this.in = null;
		
	}
	
	/*
	 * Read a string from the stream.
	 */
	private String readString() throws IOException {
		int size = readShort();
		if (size==0) return null;
		char[] string = new char[size];
		for (int i=0; i<size; i++) {
			int value = in.read();
			if (value<0) throw new IOException("Unexpected end of file");
			string[i] = (char) value;
		}
		return new String(string);
	}
	
	/*
	 * Read an integer array from the stream.
	 */
	private int[] readData() throws IOException {
		int size = readShort();
		int[] data = new int[size];
		for (int i=0; i<size; i++) {
			data[i] = readInt();
		}
		return data;
	}
	
	/*
	 * Read an integer from the stream.
	 */
	private int readInt() throws IOException {
		int b0 = in.read();
		int b1 = in.read();
		int b2 = in.read();
		int b3 = in.read();
		if (b0<0 || b1<0 || b2<0 || b3<0) throw new IOException("Unexpected end of file");
		return (b0 << 24) + ((b1 & 0xFF) << 16) + ((b2 & 0xFF) << 8) + (b3 & 0xFF);
	}

	/*
	 * Read a short from the stream.
	 */
	private int readShort() throws IOException {
		int b0 = in.read();
		int b1 = in.read();
		if (b0<0 || b1<0) throw new IOException("Unexpected end of file");
		return (b0 << 8) + (b1 & 0xFF);
	}
	
}
