grammar StateSpace;

tokens {
	LINE  	    = '--' ;
	EQUAL  	    = '=' ;
	LPAREN  	= '(' ;
	RPAREN  	= ')' ;
	LBRACKET  	= '[' ;
	RBRACKET  	= ']' ;
	COMMA		= ',';
	SEMICOLON	= ';';
}

@lexer::header{
package org.eclipse.emf.henshin.statespace.parser;
}

@parser::header{
package org.eclipse.emf.henshin.statespace.parser;

import java.util.*;
import org.antlr.runtime.BitSet;
import org.eclipse.emf.henshin.statespace.*;
}

@parser::members{

// Associate state names to states:
private HashMap<String,State> states;

// Keep only one name per transition type:
private HashMap<String,String> transitionNames;

/*
 * Get the state name map.
 */
public Map<String,State> getStates() {
	return states;
}

/*
 * Parse a state attribute.
 */
private void parseAttribute(State state, String key, String value) throws RecognitionException {
	if ("x".equals(key) || "y".equals(key)) {
		try {
			int coordinate = Integer.parseInt(value);
			if ("x".equals(key)) state.setLocation(coordinate, state.getY()); else
			if ("y".equals(key)) state.setLocation(state.getX(), coordinate);
		} catch (Throwable t) {
			throw new RecognitionException();
		}
	}
	else throw new RecognitionException();
}

}


stateSpace returns [StateSpace stateSpace]
@init { 
    
    // Initialize state space:
	$stateSpace = StateSpaceFactory.INSTANCE.createStateSpace();
	
	// Create a state name map: 
	states = new HashMap<String,State>() {
		
		@Override
		public State get(Object name) {
			// Create a new state if it does not exist yet:
			if (!containsKey(name)) {
				State state = StateSpaceFactory.INSTANCE.createState();
				state.setName((String) name);
				put((String) name, state);
			}
			return super.get(name);
		}
		
	};
	
	// Create a transition name map:
	transitionNames = new HashMap<String,String>() {
		
		@Override
		public String get(Object name) {
			if (!containsKey(name)) {
				put((String) name, (String) name);
			}
			return super.get(name);
		}
		
	};
} :
	// Parse all states:
	(state { $stateSpace.getStates().add($state.state); })*
;

state returns [State state] : 
	name=ID { $state = states.get($name.text); }
	(LBRACKET (attribute[$state] (COMMA attribute[$state])*)? RBRACKET)?
    (LINE (transition[$state])+)?
 SEMICOLON
;

transition[State state] returns [Transition transition] :
	LPAREN rule=ID COMMA target=ID RPAREN { 
		$transition = StateSpaceFactory.INSTANCE.createTransition();
		$transition.setSource($state);
		$transition.setTarget(states.get($target.text));
		$transition.setRule(transitionNames.get($rule.text));
	}
;

attribute[State s] :
	(key=ID (EQUAL value=(ID | INT))? { parseAttribute($s, $key.text, $value.text); } )
;


ID  :	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
    ;

INT :	'0'..'9'+
    ;

WS  :   (' '|'\t'|'\n'|'\r')+ { skip(); }
    ;
