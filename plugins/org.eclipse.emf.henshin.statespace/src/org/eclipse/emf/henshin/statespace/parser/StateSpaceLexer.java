// $ANTLR 3.0.1 StateSpace.g 2009-11-16 16:43:41

package org.eclipse.emf.henshin.statespace.parser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class StateSpaceLexer extends Lexer {
    public static final int LBRACKET=8;
    public static final int RPAREN=7;
    public static final int WS=14;
    public static final int COMMA=10;
    public static final int EQUAL=5;
    public static final int SEMICOLON=11;
    public static final int INT=13;
    public static final int RBRACKET=9;
    public static final int ID=12;
    public static final int Tokens=15;
    public static final int EOF=-1;
    public static final int LINE=4;
    public static final int LPAREN=6;
    public StateSpaceLexer() {;} 
    public StateSpaceLexer(CharStream input) {
        super(input);
    }
    public String getGrammarFileName() { return "StateSpace.g"; }

    // $ANTLR start LINE
    public final void mLINE() throws RecognitionException {
        try {
            int _type = LINE;
            // StateSpace.g:6:6: ( '--' )
            // StateSpace.g:6:8: '--'
            {
            match("--"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end LINE

    // $ANTLR start EQUAL
    public final void mEQUAL() throws RecognitionException {
        try {
            int _type = EQUAL;
            // StateSpace.g:7:7: ( '=' )
            // StateSpace.g:7:9: '='
            {
            match('='); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end EQUAL

    // $ANTLR start LPAREN
    public final void mLPAREN() throws RecognitionException {
        try {
            int _type = LPAREN;
            // StateSpace.g:8:8: ( '(' )
            // StateSpace.g:8:10: '('
            {
            match('('); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end LPAREN

    // $ANTLR start RPAREN
    public final void mRPAREN() throws RecognitionException {
        try {
            int _type = RPAREN;
            // StateSpace.g:9:8: ( ')' )
            // StateSpace.g:9:10: ')'
            {
            match(')'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RPAREN

    // $ANTLR start LBRACKET
    public final void mLBRACKET() throws RecognitionException {
        try {
            int _type = LBRACKET;
            // StateSpace.g:10:10: ( '[' )
            // StateSpace.g:10:12: '['
            {
            match('['); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end LBRACKET

    // $ANTLR start RBRACKET
    public final void mRBRACKET() throws RecognitionException {
        try {
            int _type = RBRACKET;
            // StateSpace.g:11:10: ( ']' )
            // StateSpace.g:11:12: ']'
            {
            match(']'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RBRACKET

    // $ANTLR start COMMA
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            // StateSpace.g:12:7: ( ',' )
            // StateSpace.g:12:9: ','
            {
            match(','); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end COMMA

    // $ANTLR start SEMICOLON
    public final void mSEMICOLON() throws RecognitionException {
        try {
            int _type = SEMICOLON;
            // StateSpace.g:13:11: ( ';' )
            // StateSpace.g:13:13: ';'
            {
            match(';'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end SEMICOLON

    // $ANTLR start ID
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            // StateSpace.g:105:5: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // StateSpace.g:105:7: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }

            // StateSpace.g:105:31: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<='9')||(LA1_0>='A' && LA1_0<='Z')||LA1_0=='_'||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // StateSpace.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end ID

    // $ANTLR start INT
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            // StateSpace.g:108:5: ( ( '0' .. '9' )+ )
            // StateSpace.g:108:7: ( '0' .. '9' )+
            {
            // StateSpace.g:108:7: ( '0' .. '9' )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // StateSpace.g:108:7: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end INT

    // $ANTLR start WS
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            // StateSpace.g:111:5: ( ( ' ' | '\\t' | '\\n' | '\\r' )+ )
            // StateSpace.g:111:9: ( ' ' | '\\t' | '\\n' | '\\r' )+
            {
            // StateSpace.g:111:9: ( ' ' | '\\t' | '\\n' | '\\r' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='\t' && LA3_0<='\n')||LA3_0=='\r'||LA3_0==' ') ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // StateSpace.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);

             skip(); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end WS

    public void mTokens() throws RecognitionException {
        // StateSpace.g:1:8: ( LINE | EQUAL | LPAREN | RPAREN | LBRACKET | RBRACKET | COMMA | SEMICOLON | ID | INT | WS )
        int alt4=11;
        switch ( input.LA(1) ) {
        case '-':
            {
            alt4=1;
            }
            break;
        case '=':
            {
            alt4=2;
            }
            break;
        case '(':
            {
            alt4=3;
            }
            break;
        case ')':
            {
            alt4=4;
            }
            break;
        case '[':
            {
            alt4=5;
            }
            break;
        case ']':
            {
            alt4=6;
            }
            break;
        case ',':
            {
            alt4=7;
            }
            break;
        case ';':
            {
            alt4=8;
            }
            break;
        case 'A':
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'F':
        case 'G':
        case 'H':
        case 'I':
        case 'J':
        case 'K':
        case 'L':
        case 'M':
        case 'N':
        case 'O':
        case 'P':
        case 'Q':
        case 'R':
        case 'S':
        case 'T':
        case 'U':
        case 'V':
        case 'W':
        case 'X':
        case 'Y':
        case 'Z':
        case '_':
        case 'a':
        case 'b':
        case 'c':
        case 'd':
        case 'e':
        case 'f':
        case 'g':
        case 'h':
        case 'i':
        case 'j':
        case 'k':
        case 'l':
        case 'm':
        case 'n':
        case 'o':
        case 'p':
        case 'q':
        case 'r':
        case 's':
        case 't':
        case 'u':
        case 'v':
        case 'w':
        case 'x':
        case 'y':
        case 'z':
            {
            alt4=9;
            }
            break;
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            {
            alt4=10;
            }
            break;
        case '\t':
        case '\n':
        case '\r':
        case ' ':
            {
            alt4=11;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("1:1: Tokens : ( LINE | EQUAL | LPAREN | RPAREN | LBRACKET | RBRACKET | COMMA | SEMICOLON | ID | INT | WS );", 4, 0, input);

            throw nvae;
        }

        switch (alt4) {
            case 1 :
                // StateSpace.g:1:10: LINE
                {
                mLINE(); 

                }
                break;
            case 2 :
                // StateSpace.g:1:15: EQUAL
                {
                mEQUAL(); 

                }
                break;
            case 3 :
                // StateSpace.g:1:21: LPAREN
                {
                mLPAREN(); 

                }
                break;
            case 4 :
                // StateSpace.g:1:28: RPAREN
                {
                mRPAREN(); 

                }
                break;
            case 5 :
                // StateSpace.g:1:35: LBRACKET
                {
                mLBRACKET(); 

                }
                break;
            case 6 :
                // StateSpace.g:1:44: RBRACKET
                {
                mRBRACKET(); 

                }
                break;
            case 7 :
                // StateSpace.g:1:53: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 8 :
                // StateSpace.g:1:59: SEMICOLON
                {
                mSEMICOLON(); 

                }
                break;
            case 9 :
                // StateSpace.g:1:69: ID
                {
                mID(); 

                }
                break;
            case 10 :
                // StateSpace.g:1:72: INT
                {
                mINT(); 

                }
                break;
            case 11 :
                // StateSpace.g:1:76: WS
                {
                mWS(); 

                }
                break;

        }

    }


 

}