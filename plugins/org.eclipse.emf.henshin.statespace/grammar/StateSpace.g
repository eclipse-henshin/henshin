grammar StateSpace;

tokens {
	LINE  	    = '--' ;
	ARROW  	    = '->' ;
	EQUAL  	    = '=' ;
	LPAREN  	= '(' ;
	RPAREN  	= ')' ;
	LBRACKET  	= '[' ;
	RBRACKET  	= ']' ;
	COMMA		= ',';
	SEMICOLON	= ';';
	RULE_CMD	= '#rule';
}

@lexer::header{
package org.eclipse.emf.henshin.statespace.parser;
}

@parser::header{
package org.eclipse.emf.henshin.statespace.parser;

import java.util.Map;
import java.util.HashMap;
import org.antlr.runtime.BitSet;
import org.eclipse.emf.ecore.resource.*;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.model.*;
import org.eclipse.emf.henshin.statespace.*;
import org.eclipse.emf.henshin.statespace.impl.*;
}

@parser::members{
	
	// Attribute keys:
	public static final String MODEL_KEY = "model";
	public static final String DATA_KEY = "data";
	
	// State space resource to be used:
	private Resource resource;
	
	// Cached value of the resource set:
	private ResourceSet resourceSet;
	
	// Associate state names to states:
	private HashMap<String,State> states;

	// Associate names to rules:
	private HashMap<String,Rule> rules;
	
	public void setResource(Resource resource) {
		this.resource = resource;
		this.resourceSet = resource.getResourceSet();
	}
	
	/*
	 * Get the state name map.
	 */
	public Map<String,State> getStates() {
		return states;
	}

	/*
	 * Get the rule name map.
	 */
	public Map<String,Rule> getRules() {
		return rules;
	}
	
	/*
	 * Parse a state attribute.
	 */
	private void parseAttribute(Object owner, String key, String value) throws RecognitionException {
		
		if (owner instanceof State) {
			State state = (State) owner;
			if (DATA_KEY.equals(key)) {
				int[] data = ((StateSpaceFactoryImpl) StateSpaceFactory.eINSTANCE).createIntegerArrayFromString(null, value);
				state.setData(data);
			}
			else if (MODEL_KEY.equals(key)) {
				URI uri = URI.createURI(value).resolve(resource.getURI());
				state.setModel(resourceSet.getResource(uri,true));
			}
		}
		
	}

}


stateSpace returns [StateSpace stateSpace]
@init { 
    
    // Initialize the state space:
	$stateSpace = StateSpaceFactory.eINSTANCE.createStateSpace();
	
	// Create a state name map: 
	states = new HashMap<String,State>() {
		@Override
		public State get(Object name) {
			// Create a new state if it does not exist yet:
			if (!containsKey(name)) {
				State state = StateSpaceFactory.eINSTANCE.createState();
				state.setName((String) name);
				put((String) name, state);
			}
			return super.get(name);
		}
	};
	
	// Create a rule name map:
	rules = new HashMap<String,Rule>();
	
} :
	// Parse content:
	(rule[$stateSpace] | state[$stateSpace] SEMICOLON)*
;


rule [StateSpace stateSpace] returns [Rule rule] :
	RULE_CMD name=ID url=STRING {
		URI uri = URI.createURI($url.text).resolve(resource.getURI());
		$rule = (Rule) resourceSet.getEObject(uri,true);
		if ($rule!=null) $stateSpace.getRules().add($rule.rule);
	}
;


state [StateSpace stateSpace] returns [State state] : 
	name=ID { 
		$state = states.get($name.text); 
		$stateSpace.getStates().add($state.state);
	}
	(LBRACKET (attribute[$state] (COMMA attribute[$state])*)? RBRACKET)?
    (transition[$state])*
;


transition[State state] returns [Transition transition] :
	LINE ruleName=ID COMMA match=INT ARROW target=ID {
		$transition = StateSpaceFactory.eINSTANCE.createTransition();
		$transition.setSource($state);
		$transition.setTarget(states.get($target.text));
		$transition.setRule(rules.get($ruleName.text));
		$transition.setMatch(Integer.parseInt($match.text));
	}
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
