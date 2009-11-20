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

import java.util.Map;
import java.util.HashMap;
import org.antlr.runtime.BitSet;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.statespace.*;
import org.eclipse.emf.henshin.statespace.impl.*;
}

@parser::members{
	
	// Attribute keys:
	public static final String STATE_MODEL = "mod";
	public static final String STATE_LOCATION = "loc";
	public static final String STATE_EXPLORED = "exp";
	public static final String TRANSITION_RULE = "rule";
	
	// State space resource to be used:
	private Resource resource;
	
	// Associate state names to states:
	private HashMap<String,State> states;

	// Keep only one name per transition type:
	private HashMap<String,String> transitionNames;
	
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	/*
	 * Get the state name map.
	 */
	public Map<String,State> getStates() {
		return states;
	}
	
	/*
	 * Parse a state attribute.
	 */
	private void parseAttribute(Object owner, String key, String value) throws RecognitionException {
		
		if (owner instanceof State) {
			State state = (State) owner;
			if (STATE_LOCATION.equals(key)) {
				int[] location = StateSpaceFactoryImpl.eINSTANCE.createIntegerArrayFromString(null, value);
				StateAttributes.setLocation(state,location);
			}
			else if (STATE_MODEL.equals(key)) {
				URI uri = URI.createURI(value).resolve(resource.getURI());
				Resource model = resource.getResourceSet().getResource(uri,true);
				state.setModel(model);
			}
			else if (STATE_EXPLORED.equals(key)) {
				boolean explored = "1".equals(value) || "y".equals(value) || "yes".equals(value) || "true".equals(value);
				StateAttributes.setExplored(state,explored);
			}
		}
		else if (owner instanceof Transition) {
			Transition transition = (Transition) owner;
			if (TRANSITION_RULE.equals(key)) {
				transition.setRule(value);
			}
		}
		
	}

}


stateSpace returns [StateSpace stateSpace]
@init { 
    
    // Initialise the state space:
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
	(state[$stateSpace])*
;

state [StateSpace stateSpace] returns [State state] : 
	name=ID { 
		$state = states.get($name.text); 
		$stateSpace.getStates().add($state.state);
	}
	(LBRACKET (attribute[$state] (COMMA attribute[$state])*)? RBRACKET)?
    (LINE (transition[$state])+)?
 SEMICOLON
;

transition[State state] returns [Transition transition] :
	target=ID { 
		$transition = StateSpaceFactory.INSTANCE.createTransition();
		$transition.setSource($state);
		$transition.setTarget(states.get($target.text));
	}
	(LBRACKET (attribute[$transition] (COMMA attribute[$transition])*)? RBRACKET)?
;

attribute[Object owner] :
	(key=ID (EQUAL value=(ID | INT | STRING))? { parseAttribute($owner, $key.text, $value.text); } )
;



ID  :	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*;
INT :	'0'..'9'+;
WS  :   (' '|'\t'|'\n'|'\r')+ { skip(); } ;
STRING :
		'"'
		(   EscapeSequence
        |   ~( '\\' | '"' | '\r' | '\n' )        
        )* 
        '"' {
        	// Automatically remove the surrounding quotes: 
        	setText( getText().substring(1,getText().length()-1));
        	}
;
fragment
EscapeSequence:
		'\\' (
                 'b' 
             |   't' 
             |   'n' 
             |   'f' 
             |   'r' 
             |   '\"' 
             |   '\'' 
             |   '\\' 
             |   ('0'..'3') ('0'..'7') ('0'..'7')
             |   ('0'..'7') ('0'..'7') 
             |   ('0'..'7')
             )          
;
