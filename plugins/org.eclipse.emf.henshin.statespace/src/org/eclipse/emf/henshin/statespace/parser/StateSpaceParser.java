// $ANTLR 3.0.1 StateSpace.g 2009-11-03 15:08:07

package org.eclipse.emf.henshin.statespace.parser;

import java.util.*;
import org.antlr.runtime.BitSet;
import org.eclipse.emf.henshin.statespace.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class StateSpaceParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LINE", "EQUAL", "LPAREN", "RPAREN", "LBRACKET", "RBRACKET", "COMMA", "SEMICOLON", "ID", "INT", "WS"
    };
    public static final int LBRACKET=8;
    public static final int RPAREN=7;
    public static final int WS=14;
    public static final int COMMA=10;
    public static final int EQUAL=5;
    public static final int INT=13;
    public static final int SEMICOLON=11;
    public static final int RBRACKET=9;
    public static final int ID=12;
    public static final int EOF=-1;
    public static final int LINE=4;
    public static final int LPAREN=6;

        public StateSpaceParser(TokenStream input) {
            super(input);
        }
        

    public String[] getTokenNames() { return tokenNames; }
    public String getGrammarFileName() { return "StateSpace.g"; }


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




    // $ANTLR start start
    // StateSpace.g:48:1: start returns [StateSpace stateSpace] : ( state )* ;
    public final StateSpace start() throws RecognitionException {
        StateSpace stateSpace = null;

        State state1 = null;


         
        	stateSpace = new StateSpace(); 
        	states = new HashMap<String,State>();

        try {
            // StateSpace.g:52:3: ( ( state )* )
            // StateSpace.g:53:2: ( state )*
            {
            // StateSpace.g:53:2: ( state )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==ID) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // StateSpace.g:53:3: state
            	    {
            	    pushFollow(FOLLOW_state_in_start141);
            	    state1=state();
            	    _fsp--;

            	     stateSpace.getStates().add(state1); 

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
    // $ANTLR end start


    // $ANTLR start state
    // StateSpace.g:56:1: state returns [State s] : name= ID ( LBRACKET ( attribute[$s] ( COMMA attribute[$s] )* )? RBRACKET )? ( LINE ( LPAREN rule= ID target= ID RPAREN )+ )? SEMICOLON ;
    public final State state() throws RecognitionException {
        State s = null;

        Token name=null;
        Token rule=null;
        Token target=null;

        try {
            // StateSpace.g:56:25: (name= ID ( LBRACKET ( attribute[$s] ( COMMA attribute[$s] )* )? RBRACKET )? ( LINE ( LPAREN rule= ID target= ID RPAREN )+ )? SEMICOLON )
            // StateSpace.g:57:2: name= ID ( LBRACKET ( attribute[$s] ( COMMA attribute[$s] )* )? RBRACKET )? ( LINE ( LPAREN rule= ID target= ID RPAREN )+ )? SEMICOLON
            {
            name=(Token)input.LT(1);
            match(input,ID,FOLLOW_ID_in_state162); 
             s = new State(name.getText()); 
            // StateSpace.g:58:2: ( LBRACKET ( attribute[$s] ( COMMA attribute[$s] )* )? RBRACKET )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==LBRACKET) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // StateSpace.g:58:3: LBRACKET ( attribute[$s] ( COMMA attribute[$s] )* )? RBRACKET
                    {
                    match(input,LBRACKET,FOLLOW_LBRACKET_in_state168); 
                    // StateSpace.g:58:12: ( attribute[$s] ( COMMA attribute[$s] )* )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0==ID) ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // StateSpace.g:58:13: attribute[$s] ( COMMA attribute[$s] )*
                            {
                            pushFollow(FOLLOW_attribute_in_state171);
                            attribute(s);
                            _fsp--;

                            // StateSpace.g:58:27: ( COMMA attribute[$s] )*
                            loop2:
                            do {
                                int alt2=2;
                                int LA2_0 = input.LA(1);

                                if ( (LA2_0==COMMA) ) {
                                    alt2=1;
                                }


                                switch (alt2) {
                            	case 1 :
                            	    // StateSpace.g:58:28: COMMA attribute[$s]
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_state175); 
                            	    pushFollow(FOLLOW_attribute_in_state177);
                            	    attribute(s);
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

                    match(input,RBRACKET,FOLLOW_RBRACKET_in_state184); 

                    }
                    break;

            }

            // StateSpace.g:59:5: ( LINE ( LPAREN rule= ID target= ID RPAREN )+ )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==LINE) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // StateSpace.g:60:5: LINE ( LPAREN rule= ID target= ID RPAREN )+
                    {
                    match(input,LINE,FOLLOW_LINE_in_state198); 
                    // StateSpace.g:60:10: ( LPAREN rule= ID target= ID RPAREN )+
                    int cnt5=0;
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==LPAREN) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // StateSpace.g:60:11: LPAREN rule= ID target= ID RPAREN
                    	    {
                    	    match(input,LPAREN,FOLLOW_LPAREN_in_state201); 
                    	    rule=(Token)input.LT(1);
                    	    match(input,ID,FOLLOW_ID_in_state205); 
                    	    target=(Token)input.LT(1);
                    	    match(input,ID,FOLLOW_ID_in_state209); 
                    	    match(input,RPAREN,FOLLOW_RPAREN_in_state211); 
                    	     new Transition(s, states.get(target), rule.getText()); 

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

            match(input,SEMICOLON,FOLLOW_SEMICOLON_in_state226); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return s;
    }
    // $ANTLR end state


    // $ANTLR start attribute
    // StateSpace.g:65:1: attribute[State s] : (key= ID ( EQUAL value= ( ID | INT ) )? ) ;
    public final void attribute(State s) throws RecognitionException {
        Token key=null;
        Token value=null;

        try {
            // StateSpace.g:65:20: ( (key= ID ( EQUAL value= ( ID | INT ) )? ) )
            // StateSpace.g:66:2: (key= ID ( EQUAL value= ( ID | INT ) )? )
            {
            // StateSpace.g:66:2: (key= ID ( EQUAL value= ( ID | INT ) )? )
            // StateSpace.g:66:3: key= ID ( EQUAL value= ( ID | INT ) )?
            {
            key=(Token)input.LT(1);
            match(input,ID,FOLLOW_ID_in_attribute240); 
            // StateSpace.g:66:10: ( EQUAL value= ( ID | INT ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==EQUAL) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // StateSpace.g:66:11: EQUAL value= ( ID | INT )
                    {
                    match(input,EQUAL,FOLLOW_EQUAL_in_attribute243); 
                    value=(Token)input.LT(1);
                    if ( (input.LA(1)>=ID && input.LA(1)<=INT) ) {
                        input.consume();
                        errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_attribute247);    throw mse;
                    }


                    }
                    break;

            }

             parseAttribute(s, key.getText(), value.getText()); 

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


 

    public static final BitSet FOLLOW_state_in_start141 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_ID_in_state162 = new BitSet(new long[]{0x0000000000000910L});
    public static final BitSet FOLLOW_LBRACKET_in_state168 = new BitSet(new long[]{0x0000000000001200L});
    public static final BitSet FOLLOW_attribute_in_state171 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_state175 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_attribute_in_state177 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RBRACKET_in_state184 = new BitSet(new long[]{0x0000000000000810L});
    public static final BitSet FOLLOW_LINE_in_state198 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_LPAREN_in_state201 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_state205 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_state209 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RPAREN_in_state211 = new BitSet(new long[]{0x0000000000000840L});
    public static final BitSet FOLLOW_SEMICOLON_in_state226 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_attribute240 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_EQUAL_in_attribute243 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_set_in_attribute247 = new BitSet(new long[]{0x0000000000000002L});

}