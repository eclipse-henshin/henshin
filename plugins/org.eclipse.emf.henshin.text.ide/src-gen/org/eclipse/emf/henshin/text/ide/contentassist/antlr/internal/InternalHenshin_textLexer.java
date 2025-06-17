package org.eclipse.emf.henshin.text.ide.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalHenshin_textLexer extends Lexer {
    public static final int T__50=50;
    public static final int T__59=59;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int RULE_ID=4;
    public static final int RULE_INT=8;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=9;
    public static final int T__67=67;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__91=91;
    public static final int T__100=100;
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__102=102;
    public static final int T__94=94;
    public static final int T__101=101;
    public static final int T__90=90;
    public static final int RULE_NEGATIVE=7;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__99=99;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int RULE_DECIMAL=6;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int RULE_STRING=5;
    public static final int RULE_SL_COMMENT=10;
    public static final int T__77=77;
    public static final int T__78=78;
    public static final int T__79=79;
    public static final int T__73=73;
    public static final int EOF=-1;
    public static final int T__74=74;
    public static final int T__75=75;
    public static final int T__76=76;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int RULE_WS=11;
    public static final int RULE_ANY_OTHER=12;
    public static final int T__88=88;
    public static final int T__89=89;
    public static final int T__84=84;
    public static final int T__104=104;
    public static final int T__85=85;
    public static final int T__103=103;
    public static final int T__86=86;
    public static final int T__106=106;
    public static final int T__87=87;
    public static final int T__105=105;

    // delegates
    // delegators

    public InternalHenshin_textLexer() {;} 
    public InternalHenshin_textLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalHenshin_textLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalHenshin_text.g"; }

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:11:7: ( 'preserve' )
            // InternalHenshin_text.g:11:9: 'preserve'
            {
            match("preserve"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:12:7: ( 'create' )
            // InternalHenshin_text.g:12:9: 'create'
            {
            match("create"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:13:7: ( 'delete' )
            // InternalHenshin_text.g:13:9: 'delete'
            {
            match("delete"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:14:7: ( 'forbid' )
            // InternalHenshin_text.g:14:9: 'forbid'
            {
            match("forbid"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:15:7: ( 'require' )
            // InternalHenshin_text.g:15:9: 'require'
            {
            match("require"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:16:7: ( 'OR' )
            // InternalHenshin_text.g:16:9: 'OR'
            {
            match("OR"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:17:7: ( 'XOR' )
            // InternalHenshin_text.g:17:9: 'XOR'
            {
            match("XOR"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:18:7: ( 'true' )
            // InternalHenshin_text.g:18:9: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:19:7: ( 'false' )
            // InternalHenshin_text.g:19:9: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:20:7: ( '==' )
            // InternalHenshin_text.g:20:9: '=='
            {
            match("=="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:21:7: ( '!=' )
            // InternalHenshin_text.g:21:9: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:22:7: ( '>=' )
            // InternalHenshin_text.g:22:9: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:23:7: ( '<=' )
            // InternalHenshin_text.g:23:9: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:24:7: ( '>' )
            // InternalHenshin_text.g:24:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:25:7: ( '<' )
            // InternalHenshin_text.g:25:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:26:7: ( '*' )
            // InternalHenshin_text.g:26:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:27:7: ( '/' )
            // InternalHenshin_text.g:27:9: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:28:7: ( 'IN' )
            // InternalHenshin_text.g:28:9: 'IN'
            {
            match("IN"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:29:7: ( 'OUT' )
            // InternalHenshin_text.g:29:9: 'OUT'
            {
            match("OUT"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:30:7: ( 'INOUT' )
            // InternalHenshin_text.g:30:9: 'INOUT'
            {
            match("INOUT"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:31:7: ( 'VAR' )
            // InternalHenshin_text.g:31:9: 'VAR'
            {
            match("VAR"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:32:7: ( 'EBigDecimal' )
            // InternalHenshin_text.g:32:9: 'EBigDecimal'
            {
            match("EBigDecimal"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:33:7: ( 'EBigInteger' )
            // InternalHenshin_text.g:33:9: 'EBigInteger'
            {
            match("EBigInteger"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:34:7: ( 'EBoolean' )
            // InternalHenshin_text.g:34:9: 'EBoolean'
            {
            match("EBoolean"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:35:7: ( 'EBooleanObject' )
            // InternalHenshin_text.g:35:9: 'EBooleanObject'
            {
            match("EBooleanObject"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:36:7: ( 'EByte' )
            // InternalHenshin_text.g:36:9: 'EByte'
            {
            match("EByte"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__38"

    // $ANTLR start "T__39"
    public final void mT__39() throws RecognitionException {
        try {
            int _type = T__39;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:37:7: ( 'EByteArray' )
            // InternalHenshin_text.g:37:9: 'EByteArray'
            {
            match("EByteArray"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__39"

    // $ANTLR start "T__40"
    public final void mT__40() throws RecognitionException {
        try {
            int _type = T__40;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:38:7: ( 'EByteObject' )
            // InternalHenshin_text.g:38:9: 'EByteObject'
            {
            match("EByteObject"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__40"

    // $ANTLR start "T__41"
    public final void mT__41() throws RecognitionException {
        try {
            int _type = T__41;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:39:7: ( 'EChar' )
            // InternalHenshin_text.g:39:9: 'EChar'
            {
            match("EChar"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__41"

    // $ANTLR start "T__42"
    public final void mT__42() throws RecognitionException {
        try {
            int _type = T__42;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:40:7: ( 'ECharacterObject' )
            // InternalHenshin_text.g:40:9: 'ECharacterObject'
            {
            match("ECharacterObject"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__42"

    // $ANTLR start "T__43"
    public final void mT__43() throws RecognitionException {
        try {
            int _type = T__43;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:41:7: ( 'EDate' )
            // InternalHenshin_text.g:41:9: 'EDate'
            {
            match("EDate"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__43"

    // $ANTLR start "T__44"
    public final void mT__44() throws RecognitionException {
        try {
            int _type = T__44;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:42:7: ( 'EDiagnosticChain' )
            // InternalHenshin_text.g:42:9: 'EDiagnosticChain'
            {
            match("EDiagnosticChain"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__44"

    // $ANTLR start "T__45"
    public final void mT__45() throws RecognitionException {
        try {
            int _type = T__45;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:43:7: ( 'EDouble' )
            // InternalHenshin_text.g:43:9: 'EDouble'
            {
            match("EDouble"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__45"

    // $ANTLR start "T__46"
    public final void mT__46() throws RecognitionException {
        try {
            int _type = T__46;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:44:7: ( 'EDoubleObject' )
            // InternalHenshin_text.g:44:9: 'EDoubleObject'
            {
            match("EDoubleObject"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__46"

    // $ANTLR start "T__47"
    public final void mT__47() throws RecognitionException {
        try {
            int _type = T__47;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:45:7: ( 'EEList' )
            // InternalHenshin_text.g:45:9: 'EEList'
            {
            match("EEList"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__47"

    // $ANTLR start "T__48"
    public final void mT__48() throws RecognitionException {
        try {
            int _type = T__48;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:46:7: ( 'EEnumerator' )
            // InternalHenshin_text.g:46:9: 'EEnumerator'
            {
            match("EEnumerator"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__48"

    // $ANTLR start "T__49"
    public final void mT__49() throws RecognitionException {
        try {
            int _type = T__49;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:47:7: ( 'EFeatureMap' )
            // InternalHenshin_text.g:47:9: 'EFeatureMap'
            {
            match("EFeatureMap"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__49"

    // $ANTLR start "T__50"
    public final void mT__50() throws RecognitionException {
        try {
            int _type = T__50;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:48:7: ( 'EFeatureMapEntry' )
            // InternalHenshin_text.g:48:9: 'EFeatureMapEntry'
            {
            match("EFeatureMapEntry"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__50"

    // $ANTLR start "T__51"
    public final void mT__51() throws RecognitionException {
        try {
            int _type = T__51;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:49:7: ( 'EFloat' )
            // InternalHenshin_text.g:49:9: 'EFloat'
            {
            match("EFloat"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__51"

    // $ANTLR start "T__52"
    public final void mT__52() throws RecognitionException {
        try {
            int _type = T__52;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:50:7: ( 'EFloatObject' )
            // InternalHenshin_text.g:50:9: 'EFloatObject'
            {
            match("EFloatObject"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__52"

    // $ANTLR start "T__53"
    public final void mT__53() throws RecognitionException {
        try {
            int _type = T__53;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:51:7: ( 'EInt' )
            // InternalHenshin_text.g:51:9: 'EInt'
            {
            match("EInt"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__53"

    // $ANTLR start "T__54"
    public final void mT__54() throws RecognitionException {
        try {
            int _type = T__54;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:52:7: ( 'EIntegerObject' )
            // InternalHenshin_text.g:52:9: 'EIntegerObject'
            {
            match("EIntegerObject"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__54"

    // $ANTLR start "T__55"
    public final void mT__55() throws RecognitionException {
        try {
            int _type = T__55;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:53:7: ( 'ETreeIterator' )
            // InternalHenshin_text.g:53:9: 'ETreeIterator'
            {
            match("ETreeIterator"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__55"

    // $ANTLR start "T__56"
    public final void mT__56() throws RecognitionException {
        try {
            int _type = T__56;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:54:7: ( 'EInvocationTargetException' )
            // InternalHenshin_text.g:54:9: 'EInvocationTargetException'
            {
            match("EInvocationTargetException"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__56"

    // $ANTLR start "T__57"
    public final void mT__57() throws RecognitionException {
        try {
            int _type = T__57;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:55:7: ( 'EJavaClass' )
            // InternalHenshin_text.g:55:9: 'EJavaClass'
            {
            match("EJavaClass"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__57"

    // $ANTLR start "T__58"
    public final void mT__58() throws RecognitionException {
        try {
            int _type = T__58;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:56:7: ( 'EJavaObject' )
            // InternalHenshin_text.g:56:9: 'EJavaObject'
            {
            match("EJavaObject"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__58"

    // $ANTLR start "T__59"
    public final void mT__59() throws RecognitionException {
        try {
            int _type = T__59;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:57:7: ( 'ELong' )
            // InternalHenshin_text.g:57:9: 'ELong'
            {
            match("ELong"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__59"

    // $ANTLR start "T__60"
    public final void mT__60() throws RecognitionException {
        try {
            int _type = T__60;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:58:7: ( 'ELongObject' )
            // InternalHenshin_text.g:58:9: 'ELongObject'
            {
            match("ELongObject"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__60"

    // $ANTLR start "T__61"
    public final void mT__61() throws RecognitionException {
        try {
            int _type = T__61;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:59:7: ( 'EMap' )
            // InternalHenshin_text.g:59:9: 'EMap'
            {
            match("EMap"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__61"

    // $ANTLR start "T__62"
    public final void mT__62() throws RecognitionException {
        try {
            int _type = T__62;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:60:7: ( 'EResource' )
            // InternalHenshin_text.g:60:9: 'EResource'
            {
            match("EResource"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__62"

    // $ANTLR start "T__63"
    public final void mT__63() throws RecognitionException {
        try {
            int _type = T__63;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:61:7: ( 'EResourceSet' )
            // InternalHenshin_text.g:61:9: 'EResourceSet'
            {
            match("EResourceSet"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__63"

    // $ANTLR start "T__64"
    public final void mT__64() throws RecognitionException {
        try {
            int _type = T__64;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:62:7: ( 'EShort' )
            // InternalHenshin_text.g:62:9: 'EShort'
            {
            match("EShort"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__64"

    // $ANTLR start "T__65"
    public final void mT__65() throws RecognitionException {
        try {
            int _type = T__65;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:63:7: ( 'EShortObject' )
            // InternalHenshin_text.g:63:9: 'EShortObject'
            {
            match("EShortObject"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__65"

    // $ANTLR start "T__66"
    public final void mT__66() throws RecognitionException {
        try {
            int _type = T__66;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:64:7: ( 'EString' )
            // InternalHenshin_text.g:64:9: 'EString'
            {
            match("EString"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__66"

    // $ANTLR start "T__67"
    public final void mT__67() throws RecognitionException {
        try {
            int _type = T__67;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:65:7: ( 'ePackageImport' )
            // InternalHenshin_text.g:65:9: 'ePackageImport'
            {
            match("ePackageImport"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__67"

    // $ANTLR start "T__68"
    public final void mT__68() throws RecognitionException {
        try {
            int _type = T__68;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:66:7: ( '.' )
            // InternalHenshin_text.g:66:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__68"

    // $ANTLR start "T__69"
    public final void mT__69() throws RecognitionException {
        try {
            int _type = T__69;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:67:7: ( 'rule' )
            // InternalHenshin_text.g:67:9: 'rule'
            {
            match("rule"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__69"

    // $ANTLR start "T__70"
    public final void mT__70() throws RecognitionException {
        try {
            int _type = T__70;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:68:7: ( '(' )
            // InternalHenshin_text.g:68:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__70"

    // $ANTLR start "T__71"
    public final void mT__71() throws RecognitionException {
        try {
            int _type = T__71;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:69:7: ( ')' )
            // InternalHenshin_text.g:69:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__71"

    // $ANTLR start "T__72"
    public final void mT__72() throws RecognitionException {
        try {
            int _type = T__72;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:70:7: ( '{' )
            // InternalHenshin_text.g:70:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__72"

    // $ANTLR start "T__73"
    public final void mT__73() throws RecognitionException {
        try {
            int _type = T__73;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:71:7: ( '}' )
            // InternalHenshin_text.g:71:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__73"

    // $ANTLR start "T__74"
    public final void mT__74() throws RecognitionException {
        try {
            int _type = T__74;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:72:7: ( ',' )
            // InternalHenshin_text.g:72:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__74"

    // $ANTLR start "T__75"
    public final void mT__75() throws RecognitionException {
        try {
            int _type = T__75;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:73:7: ( 'unit' )
            // InternalHenshin_text.g:73:9: 'unit'
            {
            match("unit"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__75"

    // $ANTLR start "T__76"
    public final void mT__76() throws RecognitionException {
        try {
            int _type = T__76;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:74:7: ( 'javaImport' )
            // InternalHenshin_text.g:74:9: 'javaImport'
            {
            match("javaImport"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__76"

    // $ANTLR start "T__77"
    public final void mT__77() throws RecognitionException {
        try {
            int _type = T__77;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:75:7: ( 'checkDangling' )
            // InternalHenshin_text.g:75:9: 'checkDangling'
            {
            match("checkDangling"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__77"

    // $ANTLR start "T__78"
    public final void mT__78() throws RecognitionException {
        try {
            int _type = T__78;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:76:7: ( 'injectiveMatching' )
            // InternalHenshin_text.g:76:9: 'injectiveMatching'
            {
            match("injectiveMatching"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__78"

    // $ANTLR start "T__79"
    public final void mT__79() throws RecognitionException {
        try {
            int _type = T__79;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:77:7: ( 'conditions' )
            // InternalHenshin_text.g:77:9: 'conditions'
            {
            match("conditions"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__79"

    // $ANTLR start "T__80"
    public final void mT__80() throws RecognitionException {
        try {
            int _type = T__80;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:78:7: ( '[' )
            // InternalHenshin_text.g:78:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__80"

    // $ANTLR start "T__81"
    public final void mT__81() throws RecognitionException {
        try {
            int _type = T__81;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:79:7: ( ']' )
            // InternalHenshin_text.g:79:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__81"

    // $ANTLR start "T__82"
    public final void mT__82() throws RecognitionException {
        try {
            int _type = T__82;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:80:7: ( 'graph' )
            // InternalHenshin_text.g:80:9: 'graph'
            {
            match("graph"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__82"

    // $ANTLR start "T__83"
    public final void mT__83() throws RecognitionException {
        try {
            int _type = T__83;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:81:7: ( 'edges' )
            // InternalHenshin_text.g:81:9: 'edges'
            {
            match("edges"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__83"

    // $ANTLR start "T__84"
    public final void mT__84() throws RecognitionException {
        try {
            int _type = T__84;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:82:7: ( '->' )
            // InternalHenshin_text.g:82:9: '->'
            {
            match("->"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__84"

    // $ANTLR start "T__85"
    public final void mT__85() throws RecognitionException {
        try {
            int _type = T__85;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:83:7: ( ':' )
            // InternalHenshin_text.g:83:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__85"

    // $ANTLR start "T__86"
    public final void mT__86() throws RecognitionException {
        try {
            int _type = T__86;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:84:7: ( 'node' )
            // InternalHenshin_text.g:84:9: 'node'
            {
            match("node"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__86"

    // $ANTLR start "T__87"
    public final void mT__87() throws RecognitionException {
        try {
            int _type = T__87;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:85:7: ( 'reuse' )
            // InternalHenshin_text.g:85:9: 'reuse'
            {
            match("reuse"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__87"

    // $ANTLR start "T__88"
    public final void mT__88() throws RecognitionException {
        try {
            int _type = T__88;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:86:7: ( '=' )
            // InternalHenshin_text.g:86:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__88"

    // $ANTLR start "T__89"
    public final void mT__89() throws RecognitionException {
        try {
            int _type = T__89;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:87:7: ( 'multiRule' )
            // InternalHenshin_text.g:87:9: 'multiRule'
            {
            match("multiRule"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__89"

    // $ANTLR start "T__90"
    public final void mT__90() throws RecognitionException {
        try {
            int _type = T__90;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:88:7: ( 'matchingFormula' )
            // InternalHenshin_text.g:88:9: 'matchingFormula'
            {
            match("matchingFormula"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__90"

    // $ANTLR start "T__91"
    public final void mT__91() throws RecognitionException {
        try {
            int _type = T__91;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:89:7: ( 'formula' )
            // InternalHenshin_text.g:89:9: 'formula'
            {
            match("formula"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__91"

    // $ANTLR start "T__92"
    public final void mT__92() throws RecognitionException {
        try {
            int _type = T__92;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:90:7: ( 'AND' )
            // InternalHenshin_text.g:90:9: 'AND'
            {
            match("AND"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__92"

    // $ANTLR start "T__93"
    public final void mT__93() throws RecognitionException {
        try {
            int _type = T__93;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:91:7: ( '!' )
            // InternalHenshin_text.g:91:9: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__93"

    // $ANTLR start "T__94"
    public final void mT__94() throws RecognitionException {
        try {
            int _type = T__94;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:92:7: ( 'conditionGraph' )
            // InternalHenshin_text.g:92:9: 'conditionGraph'
            {
            match("conditionGraph"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__94"

    // $ANTLR start "T__95"
    public final void mT__95() throws RecognitionException {
        try {
            int _type = T__95;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:93:7: ( 'strict' )
            // InternalHenshin_text.g:93:9: 'strict'
            {
            match("strict"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__95"

    // $ANTLR start "T__96"
    public final void mT__96() throws RecognitionException {
        try {
            int _type = T__96;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:94:7: ( 'rollback' )
            // InternalHenshin_text.g:94:9: 'rollback'
            {
            match("rollback"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__96"

    // $ANTLR start "T__97"
    public final void mT__97() throws RecognitionException {
        try {
            int _type = T__97;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:95:7: ( 'independent' )
            // InternalHenshin_text.g:95:9: 'independent'
            {
            match("independent"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__97"

    // $ANTLR start "T__98"
    public final void mT__98() throws RecognitionException {
        try {
            int _type = T__98;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:96:7: ( 'if' )
            // InternalHenshin_text.g:96:9: 'if'
            {
            match("if"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__98"

    // $ANTLR start "T__99"
    public final void mT__99() throws RecognitionException {
        try {
            int _type = T__99;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:97:7: ( 'then' )
            // InternalHenshin_text.g:97:9: 'then'
            {
            match("then"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__99"

    // $ANTLR start "T__100"
    public final void mT__100() throws RecognitionException {
        try {
            int _type = T__100;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:98:8: ( 'else' )
            // InternalHenshin_text.g:98:10: 'else'
            {
            match("else"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__100"

    // $ANTLR start "T__101"
    public final void mT__101() throws RecognitionException {
        try {
            int _type = T__101;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:99:8: ( 'priority' )
            // InternalHenshin_text.g:99:10: 'priority'
            {
            match("priority"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__101"

    // $ANTLR start "T__102"
    public final void mT__102() throws RecognitionException {
        try {
            int _type = T__102;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:100:8: ( 'for' )
            // InternalHenshin_text.g:100:10: 'for'
            {
            match("for"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__102"

    // $ANTLR start "T__103"
    public final void mT__103() throws RecognitionException {
        try {
            int _type = T__103;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:101:8: ( 'while' )
            // InternalHenshin_text.g:101:10: 'while'
            {
            match("while"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__103"

    // $ANTLR start "T__104"
    public final void mT__104() throws RecognitionException {
        try {
            int _type = T__104;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:102:8: ( '+' )
            // InternalHenshin_text.g:102:10: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__104"

    // $ANTLR start "T__105"
    public final void mT__105() throws RecognitionException {
        try {
            int _type = T__105;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:103:8: ( '-' )
            // InternalHenshin_text.g:103:10: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__105"

    // $ANTLR start "T__106"
    public final void mT__106() throws RecognitionException {
        try {
            int _type = T__106;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:104:8: ( 'set' )
            // InternalHenshin_text.g:104:10: 'set'
            {
            match("set"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__106"

    // $ANTLR start "RULE_DECIMAL"
    public final void mRULE_DECIMAL() throws RecognitionException {
        try {
            int _type = RULE_DECIMAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:11457:14: ( ( '-' )? ( '0' .. '9' )+ '.' ( '0' .. '9' )+ )
            // InternalHenshin_text.g:11457:16: ( '-' )? ( '0' .. '9' )+ '.' ( '0' .. '9' )+
            {
            // InternalHenshin_text.g:11457:16: ( '-' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='-') ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // InternalHenshin_text.g:11457:16: '-'
                    {
                    match('-'); 

                    }
                    break;

            }

            // InternalHenshin_text.g:11457:21: ( '0' .. '9' )+
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
            	    // InternalHenshin_text.g:11457:22: '0' .. '9'
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

            match('.'); 
            // InternalHenshin_text.g:11457:37: ( '0' .. '9' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalHenshin_text.g:11457:38: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

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


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_DECIMAL"

    // $ANTLR start "RULE_NEGATIVE"
    public final void mRULE_NEGATIVE() throws RecognitionException {
        try {
            int _type = RULE_NEGATIVE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:11459:15: ( '-' ( '0' .. '9' )+ )
            // InternalHenshin_text.g:11459:17: '-' ( '0' .. '9' )+
            {
            match('-'); 
            // InternalHenshin_text.g:11459:21: ( '0' .. '9' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='0' && LA4_0<='9')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalHenshin_text.g:11459:22: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_NEGATIVE"

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:11461:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // InternalHenshin_text.g:11461:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // InternalHenshin_text.g:11461:11: ( '^' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='^') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalHenshin_text.g:11461:11: '^'
                    {
                    match('^'); 

                    }
                    break;

            }

            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalHenshin_text.g:11461:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='0' && LA6_0<='9')||(LA6_0>='A' && LA6_0<='Z')||LA6_0=='_'||(LA6_0>='a' && LA6_0<='z')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalHenshin_text.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ID"

    // $ANTLR start "RULE_INT"
    public final void mRULE_INT() throws RecognitionException {
        try {
            int _type = RULE_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:11463:10: ( ( '0' .. '9' )+ )
            // InternalHenshin_text.g:11463:12: ( '0' .. '9' )+
            {
            // InternalHenshin_text.g:11463:12: ( '0' .. '9' )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='0' && LA7_0<='9')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalHenshin_text.g:11463:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_INT"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:11465:13: ( ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' ) )
            // InternalHenshin_text.g:11465:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            {
            // InternalHenshin_text.g:11465:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='\"') ) {
                alt10=1;
            }
            else if ( (LA10_0=='\'') ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // InternalHenshin_text.g:11465:16: '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
                    {
                    match('\"'); 
                    // InternalHenshin_text.g:11465:20: ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )*
                    loop8:
                    do {
                        int alt8=3;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0=='\\') ) {
                            alt8=1;
                        }
                        else if ( ((LA8_0>='\u0000' && LA8_0<='!')||(LA8_0>='#' && LA8_0<='[')||(LA8_0>=']' && LA8_0<='\uFFFF')) ) {
                            alt8=2;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // InternalHenshin_text.g:11465:21: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalHenshin_text.g:11465:28: ~ ( ( '\\\\' | '\"' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop8;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // InternalHenshin_text.g:11465:48: '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // InternalHenshin_text.g:11465:53: ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )*
                    loop9:
                    do {
                        int alt9=3;
                        int LA9_0 = input.LA(1);

                        if ( (LA9_0=='\\') ) {
                            alt9=1;
                        }
                        else if ( ((LA9_0>='\u0000' && LA9_0<='&')||(LA9_0>='(' && LA9_0<='[')||(LA9_0>=']' && LA9_0<='\uFFFF')) ) {
                            alt9=2;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // InternalHenshin_text.g:11465:54: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalHenshin_text.g:11465:61: ~ ( ( '\\\\' | '\\'' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop9;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_STRING"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:11467:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // InternalHenshin_text.g:11467:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // InternalHenshin_text.g:11467:24: ( options {greedy=false; } : . )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0=='*') ) {
                    int LA11_1 = input.LA(2);

                    if ( (LA11_1=='/') ) {
                        alt11=2;
                    }
                    else if ( ((LA11_1>='\u0000' && LA11_1<='.')||(LA11_1>='0' && LA11_1<='\uFFFF')) ) {
                        alt11=1;
                    }


                }
                else if ( ((LA11_0>='\u0000' && LA11_0<=')')||(LA11_0>='+' && LA11_0<='\uFFFF')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalHenshin_text.g:11467:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            match("*/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ML_COMMENT"

    // $ANTLR start "RULE_SL_COMMENT"
    public final void mRULE_SL_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:11469:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalHenshin_text.g:11469:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalHenshin_text.g:11469:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0>='\u0000' && LA12_0<='\t')||(LA12_0>='\u000B' && LA12_0<='\f')||(LA12_0>='\u000E' && LA12_0<='\uFFFF')) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalHenshin_text.g:11469:24: ~ ( ( '\\n' | '\\r' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

            // InternalHenshin_text.g:11469:40: ( ( '\\r' )? '\\n' )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0=='\n'||LA14_0=='\r') ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalHenshin_text.g:11469:41: ( '\\r' )? '\\n'
                    {
                    // InternalHenshin_text.g:11469:41: ( '\\r' )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0=='\r') ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // InternalHenshin_text.g:11469:41: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_SL_COMMENT"

    // $ANTLR start "RULE_WS"
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:11471:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalHenshin_text.g:11471:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalHenshin_text.g:11471:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt15=0;
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( ((LA15_0>='\t' && LA15_0<='\n')||LA15_0=='\r'||LA15_0==' ') ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalHenshin_text.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt15 >= 1 ) break loop15;
                        EarlyExitException eee =
                            new EarlyExitException(15, input);
                        throw eee;
                }
                cnt15++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    // $ANTLR start "RULE_ANY_OTHER"
    public final void mRULE_ANY_OTHER() throws RecognitionException {
        try {
            int _type = RULE_ANY_OTHER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalHenshin_text.g:11473:16: ( . )
            // InternalHenshin_text.g:11473:18: .
            {
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ANY_OTHER"

    public void mTokens() throws RecognitionException {
        // InternalHenshin_text.g:1:8: ( T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | T__105 | T__106 | RULE_DECIMAL | RULE_NEGATIVE | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt16=103;
        alt16 = dfa16.predict(input);
        switch (alt16) {
            case 1 :
                // InternalHenshin_text.g:1:10: T__13
                {
                mT__13(); 

                }
                break;
            case 2 :
                // InternalHenshin_text.g:1:16: T__14
                {
                mT__14(); 

                }
                break;
            case 3 :
                // InternalHenshin_text.g:1:22: T__15
                {
                mT__15(); 

                }
                break;
            case 4 :
                // InternalHenshin_text.g:1:28: T__16
                {
                mT__16(); 

                }
                break;
            case 5 :
                // InternalHenshin_text.g:1:34: T__17
                {
                mT__17(); 

                }
                break;
            case 6 :
                // InternalHenshin_text.g:1:40: T__18
                {
                mT__18(); 

                }
                break;
            case 7 :
                // InternalHenshin_text.g:1:46: T__19
                {
                mT__19(); 

                }
                break;
            case 8 :
                // InternalHenshin_text.g:1:52: T__20
                {
                mT__20(); 

                }
                break;
            case 9 :
                // InternalHenshin_text.g:1:58: T__21
                {
                mT__21(); 

                }
                break;
            case 10 :
                // InternalHenshin_text.g:1:64: T__22
                {
                mT__22(); 

                }
                break;
            case 11 :
                // InternalHenshin_text.g:1:70: T__23
                {
                mT__23(); 

                }
                break;
            case 12 :
                // InternalHenshin_text.g:1:76: T__24
                {
                mT__24(); 

                }
                break;
            case 13 :
                // InternalHenshin_text.g:1:82: T__25
                {
                mT__25(); 

                }
                break;
            case 14 :
                // InternalHenshin_text.g:1:88: T__26
                {
                mT__26(); 

                }
                break;
            case 15 :
                // InternalHenshin_text.g:1:94: T__27
                {
                mT__27(); 

                }
                break;
            case 16 :
                // InternalHenshin_text.g:1:100: T__28
                {
                mT__28(); 

                }
                break;
            case 17 :
                // InternalHenshin_text.g:1:106: T__29
                {
                mT__29(); 

                }
                break;
            case 18 :
                // InternalHenshin_text.g:1:112: T__30
                {
                mT__30(); 

                }
                break;
            case 19 :
                // InternalHenshin_text.g:1:118: T__31
                {
                mT__31(); 

                }
                break;
            case 20 :
                // InternalHenshin_text.g:1:124: T__32
                {
                mT__32(); 

                }
                break;
            case 21 :
                // InternalHenshin_text.g:1:130: T__33
                {
                mT__33(); 

                }
                break;
            case 22 :
                // InternalHenshin_text.g:1:136: T__34
                {
                mT__34(); 

                }
                break;
            case 23 :
                // InternalHenshin_text.g:1:142: T__35
                {
                mT__35(); 

                }
                break;
            case 24 :
                // InternalHenshin_text.g:1:148: T__36
                {
                mT__36(); 

                }
                break;
            case 25 :
                // InternalHenshin_text.g:1:154: T__37
                {
                mT__37(); 

                }
                break;
            case 26 :
                // InternalHenshin_text.g:1:160: T__38
                {
                mT__38(); 

                }
                break;
            case 27 :
                // InternalHenshin_text.g:1:166: T__39
                {
                mT__39(); 

                }
                break;
            case 28 :
                // InternalHenshin_text.g:1:172: T__40
                {
                mT__40(); 

                }
                break;
            case 29 :
                // InternalHenshin_text.g:1:178: T__41
                {
                mT__41(); 

                }
                break;
            case 30 :
                // InternalHenshin_text.g:1:184: T__42
                {
                mT__42(); 

                }
                break;
            case 31 :
                // InternalHenshin_text.g:1:190: T__43
                {
                mT__43(); 

                }
                break;
            case 32 :
                // InternalHenshin_text.g:1:196: T__44
                {
                mT__44(); 

                }
                break;
            case 33 :
                // InternalHenshin_text.g:1:202: T__45
                {
                mT__45(); 

                }
                break;
            case 34 :
                // InternalHenshin_text.g:1:208: T__46
                {
                mT__46(); 

                }
                break;
            case 35 :
                // InternalHenshin_text.g:1:214: T__47
                {
                mT__47(); 

                }
                break;
            case 36 :
                // InternalHenshin_text.g:1:220: T__48
                {
                mT__48(); 

                }
                break;
            case 37 :
                // InternalHenshin_text.g:1:226: T__49
                {
                mT__49(); 

                }
                break;
            case 38 :
                // InternalHenshin_text.g:1:232: T__50
                {
                mT__50(); 

                }
                break;
            case 39 :
                // InternalHenshin_text.g:1:238: T__51
                {
                mT__51(); 

                }
                break;
            case 40 :
                // InternalHenshin_text.g:1:244: T__52
                {
                mT__52(); 

                }
                break;
            case 41 :
                // InternalHenshin_text.g:1:250: T__53
                {
                mT__53(); 

                }
                break;
            case 42 :
                // InternalHenshin_text.g:1:256: T__54
                {
                mT__54(); 

                }
                break;
            case 43 :
                // InternalHenshin_text.g:1:262: T__55
                {
                mT__55(); 

                }
                break;
            case 44 :
                // InternalHenshin_text.g:1:268: T__56
                {
                mT__56(); 

                }
                break;
            case 45 :
                // InternalHenshin_text.g:1:274: T__57
                {
                mT__57(); 

                }
                break;
            case 46 :
                // InternalHenshin_text.g:1:280: T__58
                {
                mT__58(); 

                }
                break;
            case 47 :
                // InternalHenshin_text.g:1:286: T__59
                {
                mT__59(); 

                }
                break;
            case 48 :
                // InternalHenshin_text.g:1:292: T__60
                {
                mT__60(); 

                }
                break;
            case 49 :
                // InternalHenshin_text.g:1:298: T__61
                {
                mT__61(); 

                }
                break;
            case 50 :
                // InternalHenshin_text.g:1:304: T__62
                {
                mT__62(); 

                }
                break;
            case 51 :
                // InternalHenshin_text.g:1:310: T__63
                {
                mT__63(); 

                }
                break;
            case 52 :
                // InternalHenshin_text.g:1:316: T__64
                {
                mT__64(); 

                }
                break;
            case 53 :
                // InternalHenshin_text.g:1:322: T__65
                {
                mT__65(); 

                }
                break;
            case 54 :
                // InternalHenshin_text.g:1:328: T__66
                {
                mT__66(); 

                }
                break;
            case 55 :
                // InternalHenshin_text.g:1:334: T__67
                {
                mT__67(); 

                }
                break;
            case 56 :
                // InternalHenshin_text.g:1:340: T__68
                {
                mT__68(); 

                }
                break;
            case 57 :
                // InternalHenshin_text.g:1:346: T__69
                {
                mT__69(); 

                }
                break;
            case 58 :
                // InternalHenshin_text.g:1:352: T__70
                {
                mT__70(); 

                }
                break;
            case 59 :
                // InternalHenshin_text.g:1:358: T__71
                {
                mT__71(); 

                }
                break;
            case 60 :
                // InternalHenshin_text.g:1:364: T__72
                {
                mT__72(); 

                }
                break;
            case 61 :
                // InternalHenshin_text.g:1:370: T__73
                {
                mT__73(); 

                }
                break;
            case 62 :
                // InternalHenshin_text.g:1:376: T__74
                {
                mT__74(); 

                }
                break;
            case 63 :
                // InternalHenshin_text.g:1:382: T__75
                {
                mT__75(); 

                }
                break;
            case 64 :
                // InternalHenshin_text.g:1:388: T__76
                {
                mT__76(); 

                }
                break;
            case 65 :
                // InternalHenshin_text.g:1:394: T__77
                {
                mT__77(); 

                }
                break;
            case 66 :
                // InternalHenshin_text.g:1:400: T__78
                {
                mT__78(); 

                }
                break;
            case 67 :
                // InternalHenshin_text.g:1:406: T__79
                {
                mT__79(); 

                }
                break;
            case 68 :
                // InternalHenshin_text.g:1:412: T__80
                {
                mT__80(); 

                }
                break;
            case 69 :
                // InternalHenshin_text.g:1:418: T__81
                {
                mT__81(); 

                }
                break;
            case 70 :
                // InternalHenshin_text.g:1:424: T__82
                {
                mT__82(); 

                }
                break;
            case 71 :
                // InternalHenshin_text.g:1:430: T__83
                {
                mT__83(); 

                }
                break;
            case 72 :
                // InternalHenshin_text.g:1:436: T__84
                {
                mT__84(); 

                }
                break;
            case 73 :
                // InternalHenshin_text.g:1:442: T__85
                {
                mT__85(); 

                }
                break;
            case 74 :
                // InternalHenshin_text.g:1:448: T__86
                {
                mT__86(); 

                }
                break;
            case 75 :
                // InternalHenshin_text.g:1:454: T__87
                {
                mT__87(); 

                }
                break;
            case 76 :
                // InternalHenshin_text.g:1:460: T__88
                {
                mT__88(); 

                }
                break;
            case 77 :
                // InternalHenshin_text.g:1:466: T__89
                {
                mT__89(); 

                }
                break;
            case 78 :
                // InternalHenshin_text.g:1:472: T__90
                {
                mT__90(); 

                }
                break;
            case 79 :
                // InternalHenshin_text.g:1:478: T__91
                {
                mT__91(); 

                }
                break;
            case 80 :
                // InternalHenshin_text.g:1:484: T__92
                {
                mT__92(); 

                }
                break;
            case 81 :
                // InternalHenshin_text.g:1:490: T__93
                {
                mT__93(); 

                }
                break;
            case 82 :
                // InternalHenshin_text.g:1:496: T__94
                {
                mT__94(); 

                }
                break;
            case 83 :
                // InternalHenshin_text.g:1:502: T__95
                {
                mT__95(); 

                }
                break;
            case 84 :
                // InternalHenshin_text.g:1:508: T__96
                {
                mT__96(); 

                }
                break;
            case 85 :
                // InternalHenshin_text.g:1:514: T__97
                {
                mT__97(); 

                }
                break;
            case 86 :
                // InternalHenshin_text.g:1:520: T__98
                {
                mT__98(); 

                }
                break;
            case 87 :
                // InternalHenshin_text.g:1:526: T__99
                {
                mT__99(); 

                }
                break;
            case 88 :
                // InternalHenshin_text.g:1:532: T__100
                {
                mT__100(); 

                }
                break;
            case 89 :
                // InternalHenshin_text.g:1:539: T__101
                {
                mT__101(); 

                }
                break;
            case 90 :
                // InternalHenshin_text.g:1:546: T__102
                {
                mT__102(); 

                }
                break;
            case 91 :
                // InternalHenshin_text.g:1:553: T__103
                {
                mT__103(); 

                }
                break;
            case 92 :
                // InternalHenshin_text.g:1:560: T__104
                {
                mT__104(); 

                }
                break;
            case 93 :
                // InternalHenshin_text.g:1:567: T__105
                {
                mT__105(); 

                }
                break;
            case 94 :
                // InternalHenshin_text.g:1:574: T__106
                {
                mT__106(); 

                }
                break;
            case 95 :
                // InternalHenshin_text.g:1:581: RULE_DECIMAL
                {
                mRULE_DECIMAL(); 

                }
                break;
            case 96 :
                // InternalHenshin_text.g:1:594: RULE_NEGATIVE
                {
                mRULE_NEGATIVE(); 

                }
                break;
            case 97 :
                // InternalHenshin_text.g:1:608: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 98 :
                // InternalHenshin_text.g:1:616: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 99 :
                // InternalHenshin_text.g:1:625: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 100 :
                // InternalHenshin_text.g:1:637: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 101 :
                // InternalHenshin_text.g:1:653: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 102 :
                // InternalHenshin_text.g:1:669: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 103 :
                // InternalHenshin_text.g:1:677: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA16 dfa16 = new DFA16(this);
    static final String DFA16_eotS =
        "\1\uffff\10\57\1\77\1\101\1\103\1\105\1\uffff\1\111\4\57\6\uffff\3\57\2\uffff\1\57\1\152\1\uffff\5\57\1\uffff\1\164\1\55\1\uffff\2\55\2\uffff\1\57\1\uffff\11\57\1\u0085\4\57\14\uffff\1\u008b\20\57\6\uffff\3\57\1\u00a7\2\uffff\1\57\1\uffff\1\u00a9\2\uffff\7\57\2\uffff\1\164\3\uffff\6\57\1\u00b9\5\57\1\uffff\1\u00bf\1\u00c0\3\57\1\uffff\1\u00c4\32\57\1\uffff\1\57\1\uffff\3\57\1\u00e4\1\57\1\u00e6\11\57\1\uffff\3\57\1\u00f3\1\57\2\uffff\1\u00f5\1\u00f6\1\57\1\uffff\13\57\1\u0105\4\57\1\u010a\5\57\1\u0110\1\u0111\4\57\1\u0116\2\57\1\uffff\1\57\1\uffff\11\57\1\u0123\1\57\1\u0125\1\uffff\1\57\2\uffff\1\u0127\3\57\1\u012d\1\u012f\1\u0130\7\57\1\uffff\3\57\1\u013d\1\uffff\4\57\1\u0142\2\uffff\3\57\1\u0146\1\uffff\3\57\1\u014a\2\57\1\u014d\2\57\1\u0150\1\u0151\1\57\1\uffff\1\57\1\uffff\1\57\1\uffff\5\57\1\uffff\1\57\2\uffff\2\57\1\u015d\2\57\1\u0161\6\57\1\uffff\1\57\1\u016a\2\57\1\uffff\3\57\1\uffff\2\57\1\u0172\1\uffff\2\57\1\uffff\2\57\2\uffff\1\u0177\1\u0178\10\57\1\u0182\1\uffff\3\57\1\uffff\10\57\1\uffff\1\u018e\6\57\1\uffff\1\u0195\1\u0196\2\57\2\uffff\1\u0199\2\57\1\u019d\5\57\1\uffff\13\57\1\uffff\6\57\2\uffff\2\57\1\uffff\3\57\1\uffff\16\57\1\u01c9\5\57\1\u01cf\2\57\1\u01d2\4\57\1\u01d7\12\57\1\u01e2\3\57\1\uffff\2\57\1\u01e8\2\57\1\uffff\2\57\1\uffff\1\57\1\u01ee\1\u01ef\1\57\1\uffff\1\u01f1\3\57\1\u01f5\1\u01f7\4\57\1\uffff\1\u01fc\1\u01fd\3\57\1\uffff\1\57\1\u0202\3\57\2\uffff\1\57\1\uffff\3\57\1\uffff\1\57\1\uffff\1\u020b\3\57\2\uffff\1\u020f\1\u0210\2\57\1\uffff\1\57\1\u0214\4\57\1\u0219\1\57\1\uffff\2\57\1\u021d\2\uffff\3\57\1\uffff\1\u0221\1\u0222\2\57\1\uffff\1\57\1\u0226\1\57\1\uffff\1\u0228\2\57\2\uffff\3\57\1\uffff\1\57\1\uffff\1\57\1\u0230\1\u0231\1\u0232\1\u0233\2\57\4\uffff\1\57\1\u0237\1\57\1\uffff\7\57\1\u0240\1\uffff";
    static final String DFA16_eofS =
        "\u0241\uffff";
    static final String DFA16_minS =
        "\1\0\1\162\1\150\1\145\1\141\1\145\1\122\1\117\1\150\4\75\1\uffff\1\52\1\116\1\101\1\102\1\120\6\uffff\1\156\1\141\1\146\2\uffff\1\162\1\60\1\uffff\1\157\1\141\1\116\1\145\1\150\1\uffff\1\56\1\101\1\uffff\2\0\2\uffff\1\145\1\uffff\2\145\1\156\1\154\1\162\1\154\1\161\2\154\1\60\1\124\1\122\1\165\1\145\14\uffff\1\60\1\122\1\151\1\150\1\141\1\114\1\145\1\156\1\162\1\141\1\157\1\141\1\145\1\150\1\141\1\147\1\163\6\uffff\1\151\1\166\1\144\1\60\2\uffff\1\141\1\uffff\1\56\2\uffff\1\144\1\154\1\164\1\104\1\162\1\164\1\151\2\uffff\1\56\3\uffff\1\163\1\157\1\141\1\143\1\144\1\145\1\60\1\163\1\165\1\163\1\145\1\154\1\uffff\2\60\1\145\1\156\1\125\1\uffff\1\60\1\147\1\157\1\164\1\141\1\164\1\141\1\165\1\151\1\165\1\141\1\157\1\164\1\145\1\166\1\156\1\160\1\163\1\157\1\162\1\143\2\145\1\164\1\141\2\145\1\uffff\1\160\1\uffff\1\145\1\164\1\143\1\60\1\151\1\60\1\154\1\145\1\162\1\164\1\153\1\151\1\164\1\151\1\165\1\uffff\1\145\1\151\1\145\1\60\1\142\2\uffff\2\60\1\124\1\uffff\1\104\1\154\1\145\1\162\1\145\1\147\1\142\1\163\1\155\1\164\1\141\1\60\1\157\1\145\1\141\1\147\1\60\1\157\1\162\1\151\1\153\1\163\2\60\1\111\1\143\1\160\1\150\1\60\1\151\1\150\1\uffff\1\143\1\uffff\1\145\1\162\1\151\1\145\1\104\1\164\1\145\1\144\1\154\1\60\1\162\1\60\1\uffff\1\141\2\uffff\1\60\1\145\1\156\1\145\3\60\1\156\1\154\1\164\1\145\1\165\1\164\1\147\1\uffff\1\143\1\111\1\103\1\60\1\uffff\1\165\1\164\1\156\1\141\1\60\2\uffff\1\155\1\164\1\145\1\60\1\uffff\1\122\1\151\1\164\1\60\1\166\1\164\1\60\1\141\1\151\2\60\1\141\1\uffff\1\145\1\uffff\1\143\1\uffff\1\143\1\164\1\141\1\162\1\142\1\uffff\1\143\2\uffff\1\157\1\145\1\60\2\162\1\60\1\145\1\141\1\164\1\154\2\142\1\uffff\1\162\1\60\2\147\1\uffff\1\160\1\151\1\156\1\uffff\1\165\1\156\1\60\1\uffff\1\145\1\171\1\uffff\1\156\1\157\2\uffff\2\60\1\153\1\151\1\145\1\156\1\162\1\152\1\164\1\163\1\60\1\uffff\1\141\1\145\1\142\1\uffff\1\162\1\164\1\145\1\141\2\152\1\143\1\142\1\uffff\1\60\1\145\1\157\1\166\1\144\1\154\1\147\1\uffff\2\60\1\147\1\156\2\uffff\1\60\1\155\1\147\1\60\1\141\2\145\1\164\1\142\1\uffff\1\164\1\115\1\152\1\117\1\151\1\162\1\163\3\145\1\152\1\uffff\1\111\1\162\3\145\1\106\2\uffff\1\154\1\107\1\uffff\1\141\1\145\1\142\1\uffff\1\171\1\143\1\162\1\151\1\152\1\157\1\141\1\145\1\142\1\157\1\141\1\163\2\143\1\60\1\145\1\155\1\164\1\115\1\156\1\60\1\157\1\151\1\60\1\162\1\154\1\162\1\152\1\60\1\164\1\117\1\143\1\145\1\162\1\160\1\143\1\152\1\156\1\164\1\60\2\164\1\145\1\uffff\1\143\1\160\1\60\1\141\1\164\1\uffff\1\162\1\156\1\uffff\1\141\2\60\1\145\1\uffff\1\60\1\142\1\103\1\143\2\60\1\164\1\145\1\124\1\157\1\uffff\2\60\2\164\1\157\1\uffff\1\164\1\60\1\155\1\147\1\160\2\uffff\1\143\1\uffff\1\152\1\150\1\164\1\uffff\1\156\1\uffff\1\60\1\143\1\141\1\162\2\uffff\2\60\1\162\1\143\1\uffff\1\165\1\60\1\150\1\164\1\145\1\141\1\60\1\164\1\uffff\1\164\1\162\1\60\2\uffff\1\164\1\150\1\154\1\uffff\2\60\1\143\1\151\1\uffff\1\162\1\60\1\147\1\uffff\1\60\1\151\1\141\2\uffff\1\164\1\156\1\171\1\uffff\1\145\1\uffff\1\156\4\60\1\164\1\147\4\uffff\1\105\1\60\1\170\1\uffff\1\143\1\145\1\160\1\164\1\151\1\157\1\156\1\60\1\uffff";
    static final String DFA16_maxS =
        "\1\uffff\2\162\1\145\1\157\1\165\1\125\1\117\1\162\4\75\1\uffff\1\57\1\116\1\101\1\124\1\154\6\uffff\1\156\1\141\1\156\2\uffff\1\162\1\76\1\uffff\1\157\1\165\1\116\1\164\1\150\1\uffff\1\71\1\172\1\uffff\2\uffff\2\uffff\1\151\1\uffff\2\145\1\156\1\154\1\162\1\154\1\165\2\154\1\172\1\124\1\122\1\165\1\145\14\uffff\1\172\1\122\1\171\1\150\1\157\1\156\1\154\1\156\1\162\1\141\1\157\1\141\1\145\1\164\1\141\1\147\1\163\6\uffff\1\151\1\166\1\152\1\172\2\uffff\1\141\1\uffff\1\71\2\uffff\1\144\1\154\1\164\1\104\1\162\1\164\1\151\2\uffff\1\71\3\uffff\1\163\1\157\1\141\1\143\1\144\1\145\1\172\1\163\1\165\1\163\1\145\1\154\1\uffff\2\172\1\145\1\156\1\125\1\uffff\1\172\1\147\1\157\1\164\1\141\1\164\1\141\1\165\1\151\1\165\1\141\1\157\1\166\1\145\1\166\1\156\1\160\1\163\1\157\1\162\1\143\2\145\1\164\1\141\2\145\1\uffff\1\160\1\uffff\1\145\1\164\1\143\1\172\1\151\1\172\1\154\1\145\1\162\1\164\1\153\1\151\1\164\1\151\1\165\1\uffff\1\145\1\151\1\145\1\172\1\142\2\uffff\2\172\1\124\1\uffff\1\111\1\154\1\145\1\162\1\145\1\147\1\142\1\163\1\155\1\164\1\141\1\172\1\157\1\145\1\141\1\147\1\172\1\157\1\162\1\151\1\153\1\163\2\172\1\111\1\143\1\160\1\150\1\172\1\151\1\150\1\uffff\1\143\1\uffff\1\145\1\162\1\151\1\145\1\104\1\164\1\145\1\144\1\154\1\172\1\162\1\172\1\uffff\1\141\2\uffff\1\172\1\145\1\156\1\145\3\172\1\156\1\154\1\164\1\145\1\165\1\164\1\147\1\uffff\1\143\1\111\1\117\1\172\1\uffff\1\165\1\164\1\156\1\141\1\172\2\uffff\1\155\1\164\1\145\1\172\1\uffff\1\122\1\151\1\164\1\172\1\166\1\164\1\172\1\141\1\151\2\172\1\141\1\uffff\1\145\1\uffff\1\143\1\uffff\1\143\1\164\1\141\1\162\1\142\1\uffff\1\143\2\uffff\1\157\1\145\1\172\2\162\1\172\1\145\1\141\1\164\1\154\2\142\1\uffff\1\162\1\172\2\147\1\uffff\1\160\1\151\1\156\1\uffff\1\165\1\156\1\172\1\uffff\1\145\1\171\1\uffff\1\156\1\157\2\uffff\2\172\1\153\1\151\1\145\1\156\1\162\1\152\1\164\1\163\1\172\1\uffff\1\141\1\145\1\142\1\uffff\1\162\1\164\1\145\1\141\2\152\1\143\1\142\1\uffff\1\172\1\145\1\157\1\166\1\144\1\154\1\147\1\uffff\2\172\1\147\1\156\2\uffff\1\172\1\155\1\147\1\172\1\141\2\145\1\164\1\142\1\uffff\1\164\1\115\1\152\1\117\1\151\1\162\1\163\3\145\1\152\1\uffff\1\111\1\162\3\145\1\106\2\uffff\1\154\1\163\1\uffff\1\141\1\145\1\142\1\uffff\1\171\1\143\1\162\1\151\1\152\1\157\1\141\1\145\1\142\1\157\1\141\1\163\2\143\1\172\1\145\1\155\1\164\1\115\1\156\1\172\1\157\1\151\1\172\1\162\1\154\1\162\1\152\1\172\1\164\1\117\1\143\1\145\1\162\1\160\1\143\1\152\1\156\1\164\1\172\2\164\1\145\1\uffff\1\143\1\160\1\172\1\141\1\164\1\uffff\1\162\1\156\1\uffff\1\141\2\172\1\145\1\uffff\1\172\1\142\1\103\1\143\2\172\1\164\1\145\1\124\1\157\1\uffff\2\172\2\164\1\157\1\uffff\1\164\1\172\1\155\1\147\1\160\2\uffff\1\143\1\uffff\1\152\1\150\1\164\1\uffff\1\156\1\uffff\1\172\1\143\1\141\1\162\2\uffff\2\172\1\162\1\143\1\uffff\1\165\1\172\1\150\1\164\1\145\1\141\1\172\1\164\1\uffff\1\164\1\162\1\172\2\uffff\1\164\1\150\1\154\1\uffff\2\172\1\143\1\151\1\uffff\1\162\1\172\1\147\1\uffff\1\172\1\151\1\141\2\uffff\1\164\1\156\1\171\1\uffff\1\145\1\uffff\1\156\4\172\1\164\1\147\4\uffff\1\105\1\172\1\170\1\uffff\1\143\1\145\1\160\1\164\1\151\1\157\1\156\1\172\1\uffff";
    static final String DFA16_acceptS =
        "\15\uffff\1\20\5\uffff\1\70\1\72\1\73\1\74\1\75\1\76\3\uffff\1\104\1\105\2\uffff\1\111\5\uffff\1\134\2\uffff\1\141\2\uffff\1\146\1\147\1\uffff\1\141\16\uffff\1\12\1\114\1\13\1\121\1\14\1\16\1\15\1\17\1\20\1\144\1\145\1\21\21\uffff\1\70\1\72\1\73\1\74\1\75\1\76\4\uffff\1\104\1\105\1\uffff\1\110\1\uffff\1\135\1\111\7\uffff\1\134\1\142\1\uffff\1\137\1\143\1\146\14\uffff\1\6\5\uffff\1\22\33\uffff\1\126\1\uffff\1\140\17\uffff\1\132\5\uffff\1\23\1\7\3\uffff\1\25\37\uffff\1\120\1\uffff\1\136\14\uffff\1\71\1\uffff\1\10\1\127\16\uffff\1\51\4\uffff\1\61\5\uffff\1\130\1\77\4\uffff\1\112\14\uffff\1\11\1\uffff\1\113\1\uffff\1\24\5\uffff\1\32\1\uffff\1\35\1\37\14\uffff\1\57\4\uffff\1\107\3\uffff\1\106\3\uffff\1\133\2\uffff\1\2\2\uffff\1\3\1\4\13\uffff\1\43\3\uffff\1\47\10\uffff\1\64\7\uffff\1\123\4\uffff\1\117\1\5\11\uffff\1\41\13\uffff\1\66\6\uffff\1\1\1\131\2\uffff\1\124\3\uffff\1\30\53\uffff\1\62\5\uffff\1\115\2\uffff\1\103\4\uffff\1\33\12\uffff\1\55\5\uffff\1\100\5\uffff\1\26\1\27\1\uffff\1\34\3\uffff\1\44\1\uffff\1\45\4\uffff\1\56\1\60\4\uffff\1\125\10\uffff\1\50\3\uffff\1\63\1\65\3\uffff\1\101\4\uffff\1\42\3\uffff\1\53\3\uffff\1\122\1\31\3\uffff\1\52\1\uffff\1\67\7\uffff\1\116\1\36\1\40\1\46\3\uffff\1\102\10\uffff\1\54";
    static final String DFA16_specialS =
        "\1\0\51\uffff\1\1\1\2\u0215\uffff}>";
    static final String[] DFA16_transitionS = {
            "\11\55\2\54\2\55\1\54\22\55\1\54\1\12\1\52\4\55\1\53\1\24\1\25\1\15\1\46\1\30\1\37\1\23\1\16\12\47\1\40\1\55\1\14\1\11\1\13\2\55\1\43\3\51\1\21\3\51\1\17\5\51\1\6\6\51\1\20\1\51\1\7\2\51\1\34\1\55\1\35\1\50\1\51\1\55\2\51\1\2\1\3\1\22\1\4\1\36\1\51\1\33\1\32\2\51\1\42\1\41\1\51\1\1\1\51\1\5\1\44\1\10\1\31\1\51\1\45\3\51\1\26\1\55\1\27\uff82\55",
            "\1\56",
            "\1\61\6\uffff\1\62\2\uffff\1\60",
            "\1\63",
            "\1\65\15\uffff\1\64",
            "\1\66\11\uffff\1\70\5\uffff\1\67",
            "\1\71\2\uffff\1\72",
            "\1\73",
            "\1\75\11\uffff\1\74",
            "\1\76",
            "\1\100",
            "\1\102",
            "\1\104",
            "",
            "\1\107\4\uffff\1\110",
            "\1\112",
            "\1\113",
            "\1\114\1\115\1\116\1\117\1\120\2\uffff\1\121\1\123\1\uffff\1\124\1\125\4\uffff\1\126\1\127\1\122",
            "\1\130\23\uffff\1\131\7\uffff\1\132",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\141",
            "\1\142",
            "\1\144\7\uffff\1\143",
            "",
            "",
            "\1\147",
            "\12\151\4\uffff\1\150",
            "",
            "\1\154",
            "\1\156\23\uffff\1\155",
            "\1\157",
            "\1\161\16\uffff\1\160",
            "\1\162",
            "",
            "\1\166\1\uffff\12\165",
            "\32\57\4\uffff\1\57\1\uffff\32\57",
            "",
            "\0\167",
            "\0\167",
            "",
            "",
            "\1\171\3\uffff\1\172",
            "",
            "\1\173",
            "\1\174",
            "\1\175",
            "\1\176",
            "\1\177",
            "\1\u0080",
            "\1\u0081\3\uffff\1\u0082",
            "\1\u0083",
            "\1\u0084",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u0086",
            "\1\u0087",
            "\1\u0088",
            "\1\u0089",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\12\57\7\uffff\16\57\1\u008a\13\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u008c",
            "\1\u008d\5\uffff\1\u008e\11\uffff\1\u008f",
            "\1\u0090",
            "\1\u0091\7\uffff\1\u0092\5\uffff\1\u0093",
            "\1\u0094\41\uffff\1\u0095",
            "\1\u0096\6\uffff\1\u0097",
            "\1\u0098",
            "\1\u0099",
            "\1\u009a",
            "\1\u009b",
            "\1\u009c",
            "\1\u009d",
            "\1\u009e\13\uffff\1\u009f",
            "\1\u00a0",
            "\1\u00a1",
            "\1\u00a2",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u00a3",
            "\1\u00a4",
            "\1\u00a6\5\uffff\1\u00a5",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "",
            "",
            "\1\u00a8",
            "",
            "\1\166\1\uffff\12\151",
            "",
            "",
            "\1\u00aa",
            "\1\u00ab",
            "\1\u00ac",
            "\1\u00ad",
            "\1\u00ae",
            "\1\u00af",
            "\1\u00b0",
            "",
            "",
            "\1\166\1\uffff\12\165",
            "",
            "",
            "",
            "\1\u00b1",
            "\1\u00b2",
            "\1\u00b3",
            "\1\u00b4",
            "\1\u00b5",
            "\1\u00b6",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\1\57\1\u00b7\12\57\1\u00b8\15\57",
            "\1\u00ba",
            "\1\u00bb",
            "\1\u00bc",
            "\1\u00bd",
            "\1\u00be",
            "",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u00c1",
            "\1\u00c2",
            "\1\u00c3",
            "",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u00c5",
            "\1\u00c6",
            "\1\u00c7",
            "\1\u00c8",
            "\1\u00c9",
            "\1\u00ca",
            "\1\u00cb",
            "\1\u00cc",
            "\1\u00cd",
            "\1\u00ce",
            "\1\u00cf",
            "\1\u00d0\1\uffff\1\u00d1",
            "\1\u00d2",
            "\1\u00d3",
            "\1\u00d4",
            "\1\u00d5",
            "\1\u00d6",
            "\1\u00d7",
            "\1\u00d8",
            "\1\u00d9",
            "\1\u00da",
            "\1\u00db",
            "\1\u00dc",
            "\1\u00dd",
            "\1\u00de",
            "\1\u00df",
            "",
            "\1\u00e0",
            "",
            "\1\u00e1",
            "\1\u00e2",
            "\1\u00e3",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u00e5",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u00e7",
            "\1\u00e8",
            "\1\u00e9",
            "\1\u00ea",
            "\1\u00eb",
            "\1\u00ec",
            "\1\u00ed",
            "\1\u00ee",
            "\1\u00ef",
            "",
            "\1\u00f0",
            "\1\u00f1",
            "\1\u00f2",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u00f4",
            "",
            "",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u00f7",
            "",
            "\1\u00f8\4\uffff\1\u00f9",
            "\1\u00fa",
            "\1\u00fb",
            "\1\u00fc",
            "\1\u00fd",
            "\1\u00fe",
            "\1\u00ff",
            "\1\u0100",
            "\1\u0101",
            "\1\u0102",
            "\1\u0103",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\4\57\1\u0104\25\57",
            "\1\u0106",
            "\1\u0107",
            "\1\u0108",
            "\1\u0109",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u010b",
            "\1\u010c",
            "\1\u010d",
            "\1\u010e",
            "\1\u010f",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u0112",
            "\1\u0113",
            "\1\u0114",
            "\1\u0115",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u0117",
            "\1\u0118",
            "",
            "\1\u0119",
            "",
            "\1\u011a",
            "\1\u011b",
            "\1\u011c",
            "\1\u011d",
            "\1\u011e",
            "\1\u011f",
            "\1\u0120",
            "\1\u0121",
            "\1\u0122",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u0124",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "",
            "\1\u0126",
            "",
            "",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u0128",
            "\1\u0129",
            "\1\u012a",
            "\12\57\7\uffff\1\u012b\15\57\1\u012c\13\57\4\uffff\1\57\1\uffff\32\57",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\1\u012e\31\57",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u0131",
            "\1\u0132",
            "\1\u0133",
            "\1\u0134",
            "\1\u0135",
            "\1\u0136",
            "\1\u0137",
            "",
            "\1\u0138",
            "\1\u0139",
            "\1\u013a\13\uffff\1\u013b",
            "\12\57\7\uffff\16\57\1\u013c\13\57\4\uffff\1\57\1\uffff\32\57",
            "",
            "\1\u013e",
            "\1\u013f",
            "\1\u0140",
            "\1\u0141",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "",
            "",
            "\1\u0143",
            "\1\u0144",
            "\1\u0145",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "",
            "\1\u0147",
            "\1\u0148",
            "\1\u0149",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u014b",
            "\1\u014c",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u014e",
            "\1\u014f",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u0152",
            "",
            "\1\u0153",
            "",
            "\1\u0154",
            "",
            "\1\u0155",
            "\1\u0156",
            "\1\u0157",
            "\1\u0158",
            "\1\u0159",
            "",
            "\1\u015a",
            "",
            "",
            "\1\u015b",
            "\1\u015c",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u015e",
            "\1\u015f",
            "\12\57\7\uffff\16\57\1\u0160\13\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u0162",
            "\1\u0163",
            "\1\u0164",
            "\1\u0165",
            "\1\u0166",
            "\1\u0167",
            "",
            "\1\u0168",
            "\12\57\7\uffff\16\57\1\u0169\13\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u016b",
            "\1\u016c",
            "",
            "\1\u016d",
            "\1\u016e",
            "\1\u016f",
            "",
            "\1\u0170",
            "\1\u0171",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "",
            "\1\u0173",
            "\1\u0174",
            "",
            "\1\u0175",
            "\1\u0176",
            "",
            "",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u0179",
            "\1\u017a",
            "\1\u017b",
            "\1\u017c",
            "\1\u017d",
            "\1\u017e",
            "\1\u017f",
            "\1\u0180",
            "\12\57\7\uffff\16\57\1\u0181\13\57\4\uffff\1\57\1\uffff\32\57",
            "",
            "\1\u0183",
            "\1\u0184",
            "\1\u0185",
            "",
            "\1\u0186",
            "\1\u0187",
            "\1\u0188",
            "\1\u0189",
            "\1\u018a",
            "\1\u018b",
            "\1\u018c",
            "\1\u018d",
            "",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u018f",
            "\1\u0190",
            "\1\u0191",
            "\1\u0192",
            "\1\u0193",
            "\1\u0194",
            "",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u0197",
            "\1\u0198",
            "",
            "",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u019a",
            "\1\u019b",
            "\12\57\7\uffff\16\57\1\u019c\13\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u019e",
            "\1\u019f",
            "\1\u01a0",
            "\1\u01a1",
            "\1\u01a2",
            "",
            "\1\u01a3",
            "\1\u01a4",
            "\1\u01a5",
            "\1\u01a6",
            "\1\u01a7",
            "\1\u01a8",
            "\1\u01a9",
            "\1\u01aa",
            "\1\u01ab",
            "\1\u01ac",
            "\1\u01ad",
            "",
            "\1\u01ae",
            "\1\u01af",
            "\1\u01b0",
            "\1\u01b1",
            "\1\u01b2",
            "\1\u01b3",
            "",
            "",
            "\1\u01b4",
            "\1\u01b6\53\uffff\1\u01b5",
            "",
            "\1\u01b7",
            "\1\u01b8",
            "\1\u01b9",
            "",
            "\1\u01ba",
            "\1\u01bb",
            "\1\u01bc",
            "\1\u01bd",
            "\1\u01be",
            "\1\u01bf",
            "\1\u01c0",
            "\1\u01c1",
            "\1\u01c2",
            "\1\u01c3",
            "\1\u01c4",
            "\1\u01c5",
            "\1\u01c6",
            "\1\u01c7",
            "\12\57\7\uffff\22\57\1\u01c8\7\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u01ca",
            "\1\u01cb",
            "\1\u01cc",
            "\1\u01cd",
            "\1\u01ce",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u01d0",
            "\1\u01d1",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u01d3",
            "\1\u01d4",
            "\1\u01d5",
            "\1\u01d6",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u01d8",
            "\1\u01d9",
            "\1\u01da",
            "\1\u01db",
            "\1\u01dc",
            "\1\u01dd",
            "\1\u01de",
            "\1\u01df",
            "\1\u01e0",
            "\1\u01e1",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u01e3",
            "\1\u01e4",
            "\1\u01e5",
            "",
            "\1\u01e6",
            "\1\u01e7",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u01e9",
            "\1\u01ea",
            "",
            "\1\u01eb",
            "\1\u01ec",
            "",
            "\1\u01ed",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u01f0",
            "",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u01f2",
            "\1\u01f3",
            "\1\u01f4",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\12\57\7\uffff\4\57\1\u01f6\25\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u01f8",
            "\1\u01f9",
            "\1\u01fa",
            "\1\u01fb",
            "",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u01fe",
            "\1\u01ff",
            "\1\u0200",
            "",
            "\1\u0201",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u0203",
            "\1\u0204",
            "\1\u0205",
            "",
            "",
            "\1\u0206",
            "",
            "\1\u0207",
            "\1\u0208",
            "\1\u0209",
            "",
            "\1\u020a",
            "",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u020c",
            "\1\u020d",
            "\1\u020e",
            "",
            "",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u0211",
            "\1\u0212",
            "",
            "\1\u0213",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u0215",
            "\1\u0216",
            "\1\u0217",
            "\1\u0218",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u021a",
            "",
            "\1\u021b",
            "\1\u021c",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "",
            "",
            "\1\u021e",
            "\1\u021f",
            "\1\u0220",
            "",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u0223",
            "\1\u0224",
            "",
            "\1\u0225",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u0227",
            "",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u0229",
            "\1\u022a",
            "",
            "",
            "\1\u022b",
            "\1\u022c",
            "\1\u022d",
            "",
            "\1\u022e",
            "",
            "\1\u022f",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u0234",
            "\1\u0235",
            "",
            "",
            "",
            "",
            "\1\u0236",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            "\1\u0238",
            "",
            "\1\u0239",
            "\1\u023a",
            "\1\u023b",
            "\1\u023c",
            "\1\u023d",
            "\1\u023e",
            "\1\u023f",
            "\12\57\7\uffff\32\57\4\uffff\1\57\1\uffff\32\57",
            ""
    };

    static final short[] DFA16_eot = DFA.unpackEncodedString(DFA16_eotS);
    static final short[] DFA16_eof = DFA.unpackEncodedString(DFA16_eofS);
    static final char[] DFA16_min = DFA.unpackEncodedStringToUnsignedChars(DFA16_minS);
    static final char[] DFA16_max = DFA.unpackEncodedStringToUnsignedChars(DFA16_maxS);
    static final short[] DFA16_accept = DFA.unpackEncodedString(DFA16_acceptS);
    static final short[] DFA16_special = DFA.unpackEncodedString(DFA16_specialS);
    static final short[][] DFA16_transition;

    static {
        int numStates = DFA16_transitionS.length;
        DFA16_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA16_transition[i] = DFA.unpackEncodedString(DFA16_transitionS[i]);
        }
    }

    class DFA16 extends DFA {

        public DFA16(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 16;
            this.eot = DFA16_eot;
            this.eof = DFA16_eof;
            this.min = DFA16_min;
            this.max = DFA16_max;
            this.accept = DFA16_accept;
            this.special = DFA16_special;
            this.transition = DFA16_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | T__105 | T__106 | RULE_DECIMAL | RULE_NEGATIVE | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA16_0 = input.LA(1);

                        s = -1;
                        if ( (LA16_0=='p') ) {s = 1;}

                        else if ( (LA16_0=='c') ) {s = 2;}

                        else if ( (LA16_0=='d') ) {s = 3;}

                        else if ( (LA16_0=='f') ) {s = 4;}

                        else if ( (LA16_0=='r') ) {s = 5;}

                        else if ( (LA16_0=='O') ) {s = 6;}

                        else if ( (LA16_0=='X') ) {s = 7;}

                        else if ( (LA16_0=='t') ) {s = 8;}

                        else if ( (LA16_0=='=') ) {s = 9;}

                        else if ( (LA16_0=='!') ) {s = 10;}

                        else if ( (LA16_0=='>') ) {s = 11;}

                        else if ( (LA16_0=='<') ) {s = 12;}

                        else if ( (LA16_0=='*') ) {s = 13;}

                        else if ( (LA16_0=='/') ) {s = 14;}

                        else if ( (LA16_0=='I') ) {s = 15;}

                        else if ( (LA16_0=='V') ) {s = 16;}

                        else if ( (LA16_0=='E') ) {s = 17;}

                        else if ( (LA16_0=='e') ) {s = 18;}

                        else if ( (LA16_0=='.') ) {s = 19;}

                        else if ( (LA16_0=='(') ) {s = 20;}

                        else if ( (LA16_0==')') ) {s = 21;}

                        else if ( (LA16_0=='{') ) {s = 22;}

                        else if ( (LA16_0=='}') ) {s = 23;}

                        else if ( (LA16_0==',') ) {s = 24;}

                        else if ( (LA16_0=='u') ) {s = 25;}

                        else if ( (LA16_0=='j') ) {s = 26;}

                        else if ( (LA16_0=='i') ) {s = 27;}

                        else if ( (LA16_0=='[') ) {s = 28;}

                        else if ( (LA16_0==']') ) {s = 29;}

                        else if ( (LA16_0=='g') ) {s = 30;}

                        else if ( (LA16_0=='-') ) {s = 31;}

                        else if ( (LA16_0==':') ) {s = 32;}

                        else if ( (LA16_0=='n') ) {s = 33;}

                        else if ( (LA16_0=='m') ) {s = 34;}

                        else if ( (LA16_0=='A') ) {s = 35;}

                        else if ( (LA16_0=='s') ) {s = 36;}

                        else if ( (LA16_0=='w') ) {s = 37;}

                        else if ( (LA16_0=='+') ) {s = 38;}

                        else if ( ((LA16_0>='0' && LA16_0<='9')) ) {s = 39;}

                        else if ( (LA16_0=='^') ) {s = 40;}

                        else if ( ((LA16_0>='B' && LA16_0<='D')||(LA16_0>='F' && LA16_0<='H')||(LA16_0>='J' && LA16_0<='N')||(LA16_0>='P' && LA16_0<='U')||LA16_0=='W'||(LA16_0>='Y' && LA16_0<='Z')||LA16_0=='_'||(LA16_0>='a' && LA16_0<='b')||LA16_0=='h'||(LA16_0>='k' && LA16_0<='l')||LA16_0=='o'||LA16_0=='q'||LA16_0=='v'||(LA16_0>='x' && LA16_0<='z')) ) {s = 41;}

                        else if ( (LA16_0=='\"') ) {s = 42;}

                        else if ( (LA16_0=='\'') ) {s = 43;}

                        else if ( ((LA16_0>='\t' && LA16_0<='\n')||LA16_0=='\r'||LA16_0==' ') ) {s = 44;}

                        else if ( ((LA16_0>='\u0000' && LA16_0<='\b')||(LA16_0>='\u000B' && LA16_0<='\f')||(LA16_0>='\u000E' && LA16_0<='\u001F')||(LA16_0>='#' && LA16_0<='&')||LA16_0==';'||(LA16_0>='?' && LA16_0<='@')||LA16_0=='\\'||LA16_0=='`'||LA16_0=='|'||(LA16_0>='~' && LA16_0<='\uFFFF')) ) {s = 45;}

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA16_42 = input.LA(1);

                        s = -1;
                        if ( ((LA16_42>='\u0000' && LA16_42<='\uFFFF')) ) {s = 119;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA16_43 = input.LA(1);

                        s = -1;
                        if ( ((LA16_43>='\u0000' && LA16_43<='\uFFFF')) ) {s = 119;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 16, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}