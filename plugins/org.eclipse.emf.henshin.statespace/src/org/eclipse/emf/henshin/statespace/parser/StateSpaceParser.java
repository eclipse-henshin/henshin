// $ANTLR 3.0.1 StateSpace.g 2010-01-19 12:06:51

package org.eclipse.emf.henshin.statespace.parser;

import java.util.Map;
import java.util.HashMap;
import org.antlr.runtime.BitSet;
import org.eclipse.emf.ecore.resource.*;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.model.*;
import org.eclipse.emf.henshin.statespace.*;
import org.eclipse.emf.henshin.statespace.impl.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class StateSpaceParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LINE", "ARROW", "EQUAL", "LPAREN", "RPAREN", "LBRACKET", "RBRACKET", "COMMA", "SEMICOLON", "RULE_CMD", "ID", "STRING", "INT", "WS", "EscapeSequence"
    };
    public static final int LBRACKET=9;
    public static final int RPAREN=8;
    public static final int WS=17;
    public static final int COMMA=11;
    public static final int EQUAL=6;
    public static final int RULE_CMD=13;
    public static final int ARROW=5;
    public static final int INT=16;
    public static final int SEMICOLON=12;
    public static final int RBRACKET=10;
    public static final int ID=14;
    public static final int EOF=-1;
    public static final int EscapeSequence=18;
    public static final int LINE=4;
    public static final int LPAREN=7;
    public static final int STRING=15;

        public StateSpaceParser(TokenStream input) {
            super(input);
        }
        

    public String[] getTokenNames() { return tokenNames; }
    public String getGrammarFileName() { return "StateSpace.g"; }


    	
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




    // $ANTLR start stateSpace
    // StateSpace.g:92:1: stateSpace returns [StateSpace stateSpace] : ( rule[$stateSpace] | state[$stateSpace] SEMICOLON )* ;
    public final StateSpace stateSpace() throws RecognitionException {
        StateSpace stateSpace = null;

         
            
            // Initialize the state space:
        	stateSpace = StateSpaceFactory.eINSTANCE.createStateSpace();
        	
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
        	

        try {
            // StateSpace.g:115:3: ( ( rule[$stateSpace] | state[$stateSpace] SEMICOLON )* )
            // StateSpace.g:117:2: ( rule[$stateSpace] | state[$stateSpace] SEMICOLON )*
            {
            // StateSpace.g:117:2: ( rule[$stateSpace] | state[$stateSpace] SEMICOLON )*
            loop1:
            do {
                int alt1=3;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==RULE_CMD) ) {
                    alt1=1;
                }
                else if ( (LA1_0==ID) ) {
                    alt1=2;
                }


                switch (alt1) {
            	case 1 :
            	    // StateSpace.g:117:3: rule[$stateSpace]
            	    {
            	    pushFollow(FOLLOW_rule_in_stateSpace167);
            	    rule(stateSpace);
            	    _fsp--;


            	    }
            	    break;
            	case 2 :
            	    // StateSpace.g:117:23: state[$stateSpace] SEMICOLON
            	    {
            	    pushFollow(FOLLOW_state_in_stateSpace172);
            	    state(stateSpace);
            	    _fsp--;

            	    match(input,SEMICOLON,FOLLOW_SEMICOLON_in_stateSpace175); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return stateSpace;
    }
    // $ANTLR end stateSpace


    // $ANTLR start rule
    // StateSpace.g:121:1: rule[StateSpace stateSpace] returns [Rule rule] : RULE_CMD name= ID url= STRING ;
    public final Rule rule(StateSpace stateSpace) throws RecognitionException {
        Rule rule = null;

        Token name=null;
        Token url=null;

        try {
            // StateSpace.g:121:50: ( RULE_CMD name= ID url= STRING )
            // StateSpace.g:122:2: RULE_CMD name= ID url= STRING
            {
            match(input,RULE_CMD,FOLLOW_RULE_CMD_in_rule194); 
            name=(Token)input.LT(1);
            match(input,ID,FOLLOW_ID_in_rule198); 
            url=(Token)input.LT(1);
            match(input,STRING,FOLLOW_STRING_in_rule202); 

            		URI uri = URI.createURI(url.getText()).resolve(resource.getURI());
            		rule = (Rule) resourceSet.getEObject(uri,true);
            		if (rule!=null) stateSpace.getRules().add(rule);
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return rule;
    }
    // $ANTLR end rule


    // $ANTLR start state
    // StateSpace.g:130:1: state[StateSpace stateSpace] returns [State state] : name= ID ( LBRACKET ( attribute[$state] ( COMMA attribute[$state] )* )? RBRACKET )? ( transition[$state] )* ;
    public final State state(StateSpace stateSpace) throws RecognitionException {
        State state = null;

        Token name=null;

        try {
            // StateSpace.g:130:53: (name= ID ( LBRACKET ( attribute[$state] ( COMMA attribute[$state] )* )? RBRACKET )? ( transition[$state] )* )
            // StateSpace.g:131:2: name= ID ( LBRACKET ( attribute[$state] ( COMMA attribute[$state] )* )? RBRACKET )? ( transition[$state] )*
            {
            name=(Token)input.LT(1);
            match(input,ID,FOLLOW_ID_in_state224); 
             
            		state = states.get(name.getText()); 
            		stateSpace.getStates().add(state);
            	
            // StateSpace.g:135:2: ( LBRACKET ( attribute[$state] ( COMMA attribute[$state] )* )? RBRACKET )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==LBRACKET) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // StateSpace.g:135:3: LBRACKET ( attribute[$state] ( COMMA attribute[$state] )* )? RBRACKET
                    {
                    match(input,LBRACKET,FOLLOW_LBRACKET_in_state230); 
                    // StateSpace.g:135:12: ( attribute[$state] ( COMMA attribute[$state] )* )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0==ID) ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // StateSpace.g:135:13: attribute[$state] ( COMMA attribute[$state] )*
                            {
                            pushFollow(FOLLOW_attribute_in_state233);
                            attribute(state);
                            _fsp--;

                            // StateSpace.g:135:31: ( COMMA attribute[$state] )*
                            loop2:
                            do {
                                int alt2=2;
                                int LA2_0 = input.LA(1);

                                if ( (LA2_0==COMMA) ) {
                                    alt2=1;
                                }


                                switch (alt2) {
                            	case 1 :
                            	    // StateSpace.g:135:32: COMMA attribute[$state]
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_state237); 
                            	    pushFollow(FOLLOW_attribute_in_state239);
                            	    attribute(state);
                            	    _fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop2;
                                }
                            } while (true);


                            }
                            break;

                    }

                    match(input,RBRACKET,FOLLOW_RBRACKET_in_state246); 

                    }
                    break;

            }

            // StateSpace.g:136:5: ( transition[$state] )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==LINE) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // StateSpace.g:136:6: transition[$state]
            	    {
            	    pushFollow(FOLLOW_transition_in_state255);
            	    transition(state);
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return state;
    }
    // $ANTLR end state


    // $ANTLR start transition
    // StateSpace.g:140:1: transition[State state] returns [Transition transition] : LINE ruleName= ID COMMA match= INT ARROW target= ID ;
    public final Transition transition(State state) throws RecognitionException {
        Transition transition = null;

        Token ruleName=null;
        Token match=null;
        Token target=null;

        try {
            // StateSpace.g:140:57: ( LINE ruleName= ID COMMA match= INT ARROW target= ID )
            // StateSpace.g:141:2: LINE ruleName= ID COMMA match= INT ARROW target= ID
            {
            match(input,LINE,FOLLOW_LINE_in_transition274); 
            ruleName=(Token)input.LT(1);
            match(input,ID,FOLLOW_ID_in_transition278); 
            match(input,COMMA,FOLLOW_COMMA_in_transition280); 
            match=(Token)input.LT(1);
            match(input,INT,FOLLOW_INT_in_transition284); 
            match(input,ARROW,FOLLOW_ARROW_in_transition286); 
            target=(Token)input.LT(1);
            match(input,ID,FOLLOW_ID_in_transition290); 

            		transition = StateSpaceFactory.eINSTANCE.createTransition();
            		transition.setSource(state);
            		transition.setTarget(states.get(target.getText()));
            		transition.setRule(rules.get(ruleName.getText()));
            		transition.setMatch(Integer.parseInt(match.getText()));
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return transition;
    }
    // $ANTLR end transition


    // $ANTLR start attribute
    // StateSpace.g:151:1: attribute[Object owner] : (key= ID ( EQUAL value= ( ID | INT | STRING ) )? ) ;
    public final void attribute(Object owner) throws RecognitionException {
        Token key=null;
        Token value=null;

        try {
            // StateSpace.g:151:25: ( (key= ID ( EQUAL value= ( ID | INT | STRING ) )? ) )
            // StateSpace.g:152:2: (key= ID ( EQUAL value= ( ID | INT | STRING ) )? )
            {
            // StateSpace.g:152:2: (key= ID ( EQUAL value= ( ID | INT | STRING ) )? )
            // StateSpace.g:152:3: key= ID ( EQUAL value= ( ID | INT | STRING ) )?
            {
            key=(Token)input.LT(1);
            match(input,ID,FOLLOW_ID_in_attribute307); 
            // StateSpace.g:152:10: ( EQUAL value= ( ID | INT | STRING ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==EQUAL) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // StateSpace.g:152:11: EQUAL value= ( ID | INT | STRING )
                    {
                    match(input,EQUAL,FOLLOW_EQUAL_in_attribute310); 
                    value=(Token)input.LT(1);
                    if ( (input.LA(1)>=ID && input.LA(1)<=INT) ) {
                        input.consume();
                        errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_attribute314);    throw mse;
                    }


                    }
                    break;

            }

             parseAttribute(owner, key.getText(), value.getText()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end attribute


 

    public static final BitSet FOLLOW_rule_in_stateSpace167 = new BitSet(new long[]{0x0000000000006002L});
    public static final BitSet FOLLOW_state_in_stateSpace172 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_SEMICOLON_in_stateSpace175 = new BitSet(new long[]{0x0000000000006002L});
    public static final BitSet FOLLOW_RULE_CMD_in_rule194 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ID_in_rule198 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_STRING_in_rule202 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_state224 = new BitSet(new long[]{0x0000000000000212L});
    public static final BitSet FOLLOW_LBRACKET_in_state230 = new BitSet(new long[]{0x0000000000004400L});
    public static final BitSet FOLLOW_attribute_in_state233 = new BitSet(new long[]{0x0000000000000C00L});
    public static final BitSet FOLLOW_COMMA_in_state237 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_attribute_in_state239 = new BitSet(new long[]{0x0000000000000C00L});
    public static final BitSet FOLLOW_RBRACKET_in_state246 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_transition_in_state255 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_LINE_in_transition274 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ID_in_transition278 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COMMA_in_transition280 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_INT_in_transition284 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ARROW_in_transition286 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ID_in_transition290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_attribute307 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_EQUAL_in_attribute310 = new BitSet(new long[]{0x000000000001C000L});
    public static final BitSet FOLLOW_set_in_attribute314 = new BitSet(new long[]{0x0000000000000002L});

}