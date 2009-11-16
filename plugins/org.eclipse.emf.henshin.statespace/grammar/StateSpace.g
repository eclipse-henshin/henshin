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
private Map<String,State> states;

public Map<String,State> getStates() {
	return states;
}

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

start returns [StateSpace stateSpace]
@init { 
	$stateSpace = new StateSpace(); 
	states = new HashMap<String,State>();
} :
	(state { $stateSpace.getStates().add($state.s); })*
;

state returns [State s] : 
	name=ID { $s = new State($name.text); }
	(LBRACKET (attribute[$s] (COMMA attribute[$s])*)? RBRACKET)?
    (
    LINE (LPAREN rule=ID target=ID RPAREN { new Transition(s, states.get(target), $rule.text); } )+
    )?
 SEMICOLON
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
