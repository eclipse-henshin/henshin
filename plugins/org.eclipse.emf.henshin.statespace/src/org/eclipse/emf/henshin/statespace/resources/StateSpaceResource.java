package org.eclipse.emf.henshin.statespace.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.impl.StateSpaceFactoryImpl;
import org.eclipse.emf.henshin.statespace.parser.StateSpaceLexer;
import org.eclipse.emf.henshin.statespace.parser.StateSpaceParser;

/**
 * Resource implementation for state spaces.
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceResource extends ResourceImpl {
	
	/**
	 * File extension for state space files.
	 */
	public static final String FILE_EXTENSION = "henshin_statespace";
	
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
		
		// Create a new printer:
		PrintWriter printer = new PrintWriter(out, false);
		
		// Print the header first:
		String header = printStateSpaceHeader(getStateSpace());
		if (!header.equals("")) printer.println(header);
		
		// Then the states:
		for (State state : getStateSpace().getStates()) {
			printer.println(printState(state));
		}
		
		// Flush the printer:
		printer.flush();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceImpl#doLoad(java.io.InputStream, java.util.Map)
	 */
	@Override
	protected void doLoad(InputStream in, Map<?, ?> options) throws IOException {
		 
        try {
        	// Set up the lexer:
    		ANTLRInputStream antlrIn = new ANTLRInputStream(in);		
            StateSpaceLexer lexer = new StateSpaceLexer(antlrIn);
           	CommonTokenStream tokens = new CommonTokenStream(lexer);
            
           	// Run the parser:
           	StateSpaceParser parser = new StateSpaceParser(tokens);
           	parser.setResource(this);
           	getContents().add(parser.stateSpace());
           	
        } catch (Throwable t)  {
			throw new IOException("Error loading state space", t);
        }
        
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceImpl#useZip()
	 */
	@Override
	protected boolean useZip() {
		return false;
	}
	
	/**
	 * Pretty-print a state. This is also used for serialisation.
	 * The result string includes all information about the state,
	 * including all outgoing transitions.
	 * @param state State to be printed.
	 * @return Serialised version of the state.
	 */
	public static String printState(State state) {
		
		StringBuffer result = new StringBuffer();
		result.append(state.getName() + "[");
		
		String sep = "";
		if (state.getLocation()!=null) {
			String location = StateSpaceFactoryImpl.eINSTANCE.convertIntegerArrayToString(null, state.getLocation());
			result.append(StateSpaceParser.STATE_LOCATION + "=\"" + location + "\""); 
			sep = ",";
		}
		if (state.isInitial()) {
			URI uri = state.getModel().getURI();
			if (state.eResource().getURI()!=null) {
				uri = uri.deresolve(state.eResource().getURI());
			}
			result.append(sep + StateSpaceParser.STATE_MODEL + "=\"" + uri + "\""); sep = ",";
		}
		if (state.isExplored()) {
			result.append(sep + StateSpaceParser.STATE_EXPLORED + "=1"); sep = ",";
		}
		result.append("]");
		
		// Outgoing transitions:
		if (!state.getOutgoing().isEmpty()) {
			result.append(" --");
			for (Transition transition : state.getOutgoing()) {
				result.append(" " + transition.getTarget().getName() + "[");
				if (transition.getRule()!=null) {
					result.append(StateSpaceParser.TRANSITION_RULE + "=\"" + transition.getRule() + "\"");
				}
				result.append("]");
			}
		}
		result.append(";");
		return result.toString();
		
	}
	
	/**
	 * Print the header of a state space. Serialising a complete state space is
	 * done by first printing the state space header and then printing all states
	 * using {@link #printState(State)}.
	 * @param stateSpace State space.
	 * @return Serialised version of the header information.
	 */
	public static String printStateSpaceHeader(StateSpace stateSpace) {
		return "";
	}
	
}
