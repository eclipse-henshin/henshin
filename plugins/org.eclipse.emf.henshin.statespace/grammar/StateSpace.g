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
			if ("x".equals(key)) state.setX(coordinate); else
			if ("y".equals(key)) state.setY(coordinate);
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
	$stateSpace = new StateSpace();
	
	// Create a state name map: 
	states = new HashMap<String,State>() {
		@Override
		public State get(Object name) {
			if (!containsKey(name)) put((String) name, new State((String) name));
			return super.get(name);
		}
	};
	
	// Create a transition name map:
	transitionNames = new HashMap<String,String>() {
		@Override
		public String get(Object name) {
			if (!containsKey(name)) put((String) name, (String) name);
			return super.get(name);
		}
	};
} :
	// Parse all states:
	(state { $stateSpace.getStates().add($state.s); })*
;

state returns [State s] : 
	name=ID { $s = states.get($name.text); }
	(LBRACKET (attribute[$s] (COMMA attribute[$s])*)? RBRACKET)?
    (LINE (transition[$s])+)?
 SEMICOLON
;

transition[State state] returns [Transition t] :
	LPAREN rule=ID COMMA target=ID RPAREN 
	{ new Transition($state, states.get($target.text), transitionNames.get($rule.text)); }
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
