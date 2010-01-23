package org.eclipse.emf.henshin.statespace.resource;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.Transition;

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
		if (readShort()!=StateSpaceSerializer.MARKER) throw new IOException("Marker not found"); // Marker
		int ruleCount = readShort(); // Rule count
		int stateCount = readInt(); // State count
		int transitionCount = readInt(); // Transition count
		int[] data = readData(); // Metadata
		
		// Set metadata:
		stateSpace.setTransitionCount(transitionCount);
		stateSpace.setData(data);
		
		// Load rules:
		for (int i=0; i<ruleCount; i++) {
			URI uri = URI.createURI(readString());
			URI resolved = uri.resolve(resource.getURI());
			Rule rule = (Rule) resource.getResourceSet().getEObject(resolved,true);
			rule.eResource().setURI(uri.trimFragment());
			stateSpace.getRules().add(rule);
		}
		
		// Create states:
		for (int i=0; i<stateCount; i++) {			
			State state = StateSpaceFactory.eINSTANCE.createState();
			state.setIndex(i);
			stateSpace.getStates().add(state);
		}
		
		// Load states and transitions:
		for (State state : stateSpace.getStates()) {
			
			// Initial states:
			String modelUri = readString();
			if (modelUri!=null) {
				URI uri = URI.createURI(modelUri);
				URI resolved = uri.resolve(resource.getURI());
				Resource model = resource.getResourceSet().getResource(resolved,true);
				model.setURI(uri);
				state.setModel(model);
			}
			
			// Metadata:
			state.setData(readData());
			
			// Transitions:
			int transitions = readShort();
			for (int i=0; i<transitions; i++) {				
				Transition transition = StateSpaceFactory.eINSTANCE.createTransition();
				transition.setRule(stateSpace.getRules().get(readShort()));
				transition.setMatch(readShort());
				transition.setTarget(stateSpace.getStates().get(readInt()));
				state.getOutgoing().add(transition);				
			}
			
		}
		
		// We expect EOF now:
		if (in.read()>0) {
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
		if (in.read(buffer,0,4)!=4) throw new IOException("Unexpected end of file");
		return (buffer[0] << 24) + ((buffer[1] & 0xFF) << 16) +
				((buffer[2] & 0xFF) << 8) + (buffer[3] & 0xFF);
	}

	/*
	 * Read a short from the stream.
	 */
	private int readShort() throws IOException {
		if (in.read(buffer,0,2)!=2) throw new IOException("Unexpected end of file");
		return (buffer[0] << 8) + (buffer[1] & 0xFF);
	}
	
	// Internal buffer:
	private byte[] buffer = new byte[4];
	
}
