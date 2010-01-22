package org.eclipse.emf.henshin.statespace.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
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
	
	// Rules are associated with external names:
	private Map<Rule,String> rules = new HashMap<Rule,String>();
	
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
		
		// Print the rules:
		for (Rule rule : getStateSpace().getRules()) {
			printer.println(printRule(rule));
		}
		
		// Print the states:
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
           	
           	// Remember the rule names:
           	for (Entry<String,Rule> entry : parser.getRules().entrySet()) {
           		rules.put(entry.getValue(), entry.getKey());
           	}
           	
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
	
	/*
	 * Print a rule.
	 */
	private String printRule(Rule rule) {
		return "#rule " + getRuleName(rule) + " \"" + EcoreUtil.getURI(rule) + "\";";
	}
	
	/*
	 * Get a rule name.
	 */
	private String getRuleName(Rule rule) {
		if (!rules.containsKey(rule)) {
			int i = 0;
			String name;
			do {
				name = "r" + (i++);
			} while (rules.values().contains(name));
			rules.put(rule, name);
		}
		return rules.get(rule);
	}
	
	/**
	 * Pretty-print a state. This is also used for serialization.
	 * The result string includes all information about the state,
	 * including all outgoing transitions.
	 * @param state State to be printed.
	 * @return Serialized version of the state.
	 */
	public String printState(State state) {
		
		StringBuffer result = new StringBuffer();
		result.append(state.getName() + "[");
		
		String sep = "";
		int[] data = state.getData();
		if (data!=null) {
			String string = ((StateSpaceFactoryImpl) StateSpaceFactory.eINSTANCE).convertIntegerArrayToString(null, state.getData());
			result.append(StateSpaceParser.DATA_KEY + "=\"" + string + "\""); 
			sep = ",";
		}
		if (state.isInitial()) {
			URI uri = state.getModel().getURI();
			if (state.eResource().getURI()!=null) {
				uri = uri.deresolve(state.eResource().getURI());
			}
			result.append(sep + StateSpaceParser.MODEL_KEY + "=\"" + uri + "\"");
		}
		result.append("]");
		
		// Outgoing transitions:
		for (Transition transition : state.getOutgoing()) {
			result.append("\n\t-- " + getRuleName(transition.getRule()) + "," + transition.getMatch() + " -> " + transition.getTarget().getName());
		}
		result.append(";");
		return result.toString();
		
	}
	
}
