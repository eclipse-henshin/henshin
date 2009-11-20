// $ANTLR 3.0.1 StateSpace.g 2009-11-20 15:55:40

package org.eclipse.emf.henshin.statespace.parser;

import java.util.Map;
import java.util.HashMap;
import org.antlr.runtime.BitSet;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.statespace.*;
import org.eclipse.emf.henshin.statespace.impl.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class StateSpaceParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LINE", "EQUAL", "LPAREN", "RPAREN", "LBRACKET", "RBRACKET", "COMMA", "SEMICOLON", "ID", "INT", "STRING", "WS", "EscapeSequence"
    };
    public static final int LBRACKET=8;
    public static final int RPAREN=7;
    public static final int WS=15;
    public static final int COMMA=10;
    public static final int EQUAL=5;
    public static final int INT=13;
    public static final int SEMICOLON=11;
    public static final int RBRACKET=9;
    public static final int ID=12;
    public static final int EOF=-1;
    public static final int EscapeSequence=16;
    public static final int LINE=4;
    public static final int LPAREN=6;
    public static final int STRING=14;

        public StateSpaceParser(TokenStream input) {
            super(input);
        }
        

    public String[] getTokenNames() { return tokenNames; }
    public String getGrammarFileName() { return "StateSpace.g"; }


    	
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




    // $ANTLR start stateSpace
    // StateSpace.g:91:1: stateSpace returns [StateSpace stateSpace] : ( state[$stateSpace] )* ;
    public final StateSpace stateSpace() throws RecognitionException {
        StateSpace stateSpace = null;

         
            
            // Initialise the state space:
        	stateSpace = StateSpaceFactory.INSTANCE.createStateSpace();
        	
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

        try {
            // StateSpace.g:125:3: ( ( state[$stateSpace] )* )
            // StateSpace.g:127:2: ( state[$stateSpace] )*
            {
            // StateSpace.g:127:2: ( state[$stateSpace] )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==ID) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // StateSpace.g:127:3: state[$stateSpace]
            	    {
            	    pushFollow(FOLLOW_state_in_stateSpace144);
            	    state(stateSpace);
            	    _fsp--;


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


    // $ANTLR start state
    // StateSpace.g:130:1: state[StateSpace stateSpace] returns [State state] : name= ID ( LBRACKET ( attribute[$state] ( COMMA attribute[$state] )* )? RBRACKET )? ( LINE ( transition[$state] )+ )? SEMICOLON ;
    public final State state(StateSpace stateSpace) throws RecognitionException {
        State state = null;

        Token name=null;

        try {
            // StateSpace.g:130:53: (name= ID ( LBRACKET ( attribute[$state] ( COMMA attribute[$state] )* )? RBRACKET )? ( LINE ( transition[$state] )+ )? SEMICOLON )
            // StateSpace.g:131:2: name= ID ( LBRACKET ( attribute[$state] ( COMMA attribute[$state] )* )? RBRACKET )? ( LINE ( transition[$state] )+ )? SEMICOLON
            {
            name=(Token)input.LT(1);
            match(input,ID,FOLLOW_ID_in_state166); 
             
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
                    match(input,LBRACKET,FOLLOW_LBRACKET_in_state172); 
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
                            pushFollow(FOLLOW_attribute_in_state175);
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
                            	    match(input,COMMA,FOLLOW_COMMA_in_state179); 
                            	    pushFollow(FOLLOW_attribute_in_state181);
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

                    match(input,RBRACKET,FOLLOW_RBRACKET_in_state188); 

                    }
                    break;

            }

            // StateSpace.g:136:5: ( LINE ( transition[$state] )+ )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==LINE) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // StateSpace.g:136:6: LINE ( transition[$state] )+
                    {
                    match(input,LINE,FOLLOW_LINE_in_state197); 
                    // StateSpace.g:136:11: ( transition[$state] )+
                    int cnt5=0;
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==ID) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // StateSpace.g:136:12: transition[$state]
                    	    {
                    	    pushFollow(FOLLOW_transition_in_state200);
                    	    transition(state);
                    	    _fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt5 >= 1 ) break loop5;
                                EarlyExitException eee =
                                    new EarlyExitException(5, input);
                                throw eee;
                        }
                        cnt5++;
                    } while (true);


                    }
                    break;

            }

            match(input,SEMICOLON,FOLLOW_SEMICOLON_in_state208); 

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
    // StateSpace.g:140:1: transition[State state] returns [Transition transition] : target= ID ( LBRACKET ( attribute[$transition] ( COMMA attribute[$transition] )* )? RBRACKET )? ;
    public final Transition transition(State state) throws RecognitionException {
        Transition transition = null;

        Token target=null;

        try {
            // StateSpace.g:140:57: (target= ID ( LBRACKET ( attribute[$transition] ( COMMA attribute[$transition] )* )? RBRACKET )? )
            // StateSpace.g:141:2: target= ID ( LBRACKET ( attribute[$transition] ( COMMA attribute[$transition] )* )? RBRACKET )?
            {
            target=(Token)input.LT(1);
            match(input,ID,FOLLOW_ID_in_transition225); 
             
            		transition = StateSpaceFactory.INSTANCE.createTransition();
            		transition.setSource(state);
            		transition.setTarget(states.get(target.getText()));
            	
            // StateSpace.g:146:2: ( LBRACKET ( attribute[$transition] ( COMMA attribute[$transition] )* )? RBRACKET )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==LBRACKET) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // StateSpace.g:146:3: LBRACKET ( attribute[$transition] ( COMMA attribute[$transition] )* )? RBRACKET
                    {
                    match(input,LBRACKET,FOLLOW_LBRACKET_in_transition231); 
                    // StateSpace.g:146:12: ( attribute[$transition] ( COMMA attribute[$transition] )* )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==ID) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // StateSpace.g:146:13: attribute[$transition] ( COMMA attribute[$transition] )*
                            {
                            pushFollow(FOLLOW_attribute_in_transition234);
                            attribute(transition);
                            _fsp--;

                            // StateSpace.g:146:36: ( COMMA attribute[$transition] )*
                            loop7:
                            do {
                                int alt7=2;
                                int LA7_0 = input.LA(1);

                                if ( (LA7_0==COMMA) ) {
                                    alt7=1;
                                }


                                switch (alt7) {
                            	case 1 :
                            	    // StateSpace.g:146:37: COMMA attribute[$transition]
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_transition238); 
                            	    pushFollow(FOLLOW_attribute_in_transition240);
                            	    attribute(transition);
                            	    _fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop7;
                                }
                            } while (true);


                            }
                            break;

                    }

                    match(input,RBRACKET,FOLLOW_RBRACKET_in_transition247); 

                    }
                    break;

            }


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
    // StateSpace.g:149:1: attribute[Object owner] : (key= ID ( EQUAL value= ( ID | INT | STRING ) )? ) ;
    public final void attribute(Object owner) throws RecognitionException {
        Token key=null;
        Token value=null;

        try {
            // StateSpace.g:149:25: ( (key= ID ( EQUAL value= ( ID | INT | STRING ) )? ) )
            // StateSpace.g:150:2: (key= ID ( EQUAL value= ( ID | INT | STRING ) )? )
            {
            // StateSpace.g:150:2: (key= ID ( EQUAL value= ( ID | INT | STRING ) )? )
            // StateSpace.g:150:3: key= ID ( EQUAL value= ( ID | INT | STRING ) )?
            {
            key=(Token)input.LT(1);
            match(input,ID,FOLLOW_ID_in_attribute263); 
            // StateSpace.g:150:10: ( EQUAL value= ( ID | INT | STRING ) )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==EQUAL) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // StateSpace.g:150:11: EQUAL value= ( ID | INT | STRING )
                    {
                    match(input,EQUAL,FOLLOW_EQUAL_in_attribute266); 
                    value=(Token)input.LT(1);
                    if ( (input.LA(1)>=ID && input.LA(1)<=STRING) ) {
                        input.consume();
                        errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_attribute270);    throw mse;
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


 

    public static final BitSet FOLLOW_state_in_stateSpace144 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_ID_in_state166 = new BitSet(new long[]{0x0000000000000910L});
    public static final BitSet FOLLOW_LBRACKET_in_state172 = new BitSet(new long[]{0x0000000000001200L});
    public static final BitSet FOLLOW_attribute_in_state175 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_state179 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_attribute_in_state181 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RBRACKET_in_state188 = new BitSet(new long[]{0x0000000000000810L});
    public static final BitSet FOLLOW_LINE_in_state197 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_transition_in_state200 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_SEMICOLON_in_state208 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_transition225 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_LBRACKET_in_transition231 = new BitSet(new long[]{0x0000000000001200L});
    public static final BitSet FOLLOW_attribute_in_transition234 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_transition238 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_attribute_in_transition240 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RBRACKET_in_transition247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_attribute263 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_EQUAL_in_attribute266 = new BitSet(new long[]{0x0000000000007000L});
    public static final BitSet FOLLOW_set_in_attribute270 = new BitSet(new long[]{0x0000000000000002L});

}