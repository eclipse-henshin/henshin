package org.eclipse.emf.henshin.text.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.emf.henshin.text.services.Henshin_textGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalHenshin_textParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_DECIMAL", "RULE_NEGATIVE", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'ePackageImport'", "'.'", "'rule'", "'('", "','", "')'", "'{'", "'}'", "'unit'", "'javaImport'", "'checkDangling'", "'injectiveMatching'", "'conditions'", "'['", "']'", "'graph'", "'preserve'", "'create'", "'delete'", "'forbid'", "'require'", "'edges'", "'->'", "':'", "'node'", "'reuse'", "'='", "'set'", "'multiRule'", "'matchingFormula'", "'formula'", "'OR'", "'XOR'", "'AND'", "'!'", "'conditionGraph'", "'strict'", "'rollback'", "'independent'", "'if'", "'then'", "'else'", "'priority'", "'for'", "'while'", "'true'", "'false'", "'=='", "'!='", "'>='", "'<='", "'>'", "'<'", "'+'", "'-'", "'*'", "'/'", "'IN'", "'OUT'", "'INOUT'", "'VAR'", "'EBigDecimal'", "'EBigInteger'", "'EBoolean'", "'EBooleanObject'", "'EByte'", "'EByteArray'", "'EByteObject'", "'EChar'", "'ECharacterObject'", "'EDate'", "'EDiagnosticChain'", "'EDouble'", "'EDoubleObject'", "'EEList'", "'EEnumerator'", "'EFeatureMap'", "'EFeatureMapEntry'", "'EFloat'", "'EFloatObject'", "'EInt'", "'EIntegerObject'", "'ETreeIterator'", "'EInvocationTargetException'", "'EJavaClass'", "'EJavaObject'", "'ELong'", "'ELongObject'", "'EMap'", "'EResource'", "'EResourceSet'", "'EShort'", "'EShortObject'", "'EString'"
    };
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


        public InternalHenshin_textParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalHenshin_textParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalHenshin_textParser.tokenNames; }
    public String getGrammarFileName() { return "InternalHenshin_text.g"; }



     	private Henshin_textGrammarAccess grammarAccess;

        public InternalHenshin_textParser(TokenStream input, Henshin_textGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "Model";
       	}

       	@Override
       	protected Henshin_textGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleModel"
    // InternalHenshin_text.g:65:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;


        try {
            // InternalHenshin_text.g:65:46: (iv_ruleModel= ruleModel EOF )
            // InternalHenshin_text.g:66:2: iv_ruleModel= ruleModel EOF
            {
             newCompositeNode(grammarAccess.getModelRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleModel=ruleModel();

            state._fsp--;

             current =iv_ruleModel; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // InternalHenshin_text.g:72:1: ruleModel returns [EObject current=null] : ( ( (lv_ePackageimports_0_0= ruleEPackageImport ) )+ ( (lv_transformationsystem_1_0= ruleModelElement ) )* ) ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        EObject lv_ePackageimports_0_0 = null;

        EObject lv_transformationsystem_1_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:78:2: ( ( ( (lv_ePackageimports_0_0= ruleEPackageImport ) )+ ( (lv_transformationsystem_1_0= ruleModelElement ) )* ) )
            // InternalHenshin_text.g:79:2: ( ( (lv_ePackageimports_0_0= ruleEPackageImport ) )+ ( (lv_transformationsystem_1_0= ruleModelElement ) )* )
            {
            // InternalHenshin_text.g:79:2: ( ( (lv_ePackageimports_0_0= ruleEPackageImport ) )+ ( (lv_transformationsystem_1_0= ruleModelElement ) )* )
            // InternalHenshin_text.g:80:3: ( (lv_ePackageimports_0_0= ruleEPackageImport ) )+ ( (lv_transformationsystem_1_0= ruleModelElement ) )*
            {
            // InternalHenshin_text.g:80:3: ( (lv_ePackageimports_0_0= ruleEPackageImport ) )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==13) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalHenshin_text.g:81:4: (lv_ePackageimports_0_0= ruleEPackageImport )
            	    {
            	    // InternalHenshin_text.g:81:4: (lv_ePackageimports_0_0= ruleEPackageImport )
            	    // InternalHenshin_text.g:82:5: lv_ePackageimports_0_0= ruleEPackageImport
            	    {

            	    					newCompositeNode(grammarAccess.getModelAccess().getEPackageimportsEPackageImportParserRuleCall_0_0());
            	    				
            	    pushFollow(FOLLOW_3);
            	    lv_ePackageimports_0_0=ruleEPackageImport();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getModelRule());
            	    					}
            	    					add(
            	    						current,
            	    						"ePackageimports",
            	    						lv_ePackageimports_0_0,
            	    						"org.eclipse.emf.henshin.text.Henshin_text.EPackageImport");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);

            // InternalHenshin_text.g:99:3: ( (lv_transformationsystem_1_0= ruleModelElement ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==15||LA2_0==21) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalHenshin_text.g:100:4: (lv_transformationsystem_1_0= ruleModelElement )
            	    {
            	    // InternalHenshin_text.g:100:4: (lv_transformationsystem_1_0= ruleModelElement )
            	    // InternalHenshin_text.g:101:5: lv_transformationsystem_1_0= ruleModelElement
            	    {

            	    					newCompositeNode(grammarAccess.getModelAccess().getTransformationsystemModelElementParserRuleCall_1_0());
            	    				
            	    pushFollow(FOLLOW_4);
            	    lv_transformationsystem_1_0=ruleModelElement();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getModelRule());
            	    					}
            	    					add(
            	    						current,
            	    						"transformationsystem",
            	    						lv_transformationsystem_1_0,
            	    						"org.eclipse.emf.henshin.text.Henshin_text.ModelElement");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleEPackageImport"
    // InternalHenshin_text.g:122:1: entryRuleEPackageImport returns [EObject current=null] : iv_ruleEPackageImport= ruleEPackageImport EOF ;
    public final EObject entryRuleEPackageImport() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEPackageImport = null;


        try {
            // InternalHenshin_text.g:122:55: (iv_ruleEPackageImport= ruleEPackageImport EOF )
            // InternalHenshin_text.g:123:2: iv_ruleEPackageImport= ruleEPackageImport EOF
            {
             newCompositeNode(grammarAccess.getEPackageImportRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEPackageImport=ruleEPackageImport();

            state._fsp--;

             current =iv_ruleEPackageImport; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEPackageImport"


    // $ANTLR start "ruleEPackageImport"
    // InternalHenshin_text.g:129:1: ruleEPackageImport returns [EObject current=null] : (otherlv_0= 'ePackageImport' ( ( ruleEString ) ) ) ;
    public final EObject ruleEPackageImport() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;


        	enterRule();

        try {
            // InternalHenshin_text.g:135:2: ( (otherlv_0= 'ePackageImport' ( ( ruleEString ) ) ) )
            // InternalHenshin_text.g:136:2: (otherlv_0= 'ePackageImport' ( ( ruleEString ) ) )
            {
            // InternalHenshin_text.g:136:2: (otherlv_0= 'ePackageImport' ( ( ruleEString ) ) )
            // InternalHenshin_text.g:137:3: otherlv_0= 'ePackageImport' ( ( ruleEString ) )
            {
            otherlv_0=(Token)match(input,13,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getEPackageImportAccess().getEPackageImportKeyword_0());
            		
            // InternalHenshin_text.g:141:3: ( ( ruleEString ) )
            // InternalHenshin_text.g:142:4: ( ruleEString )
            {
            // InternalHenshin_text.g:142:4: ( ruleEString )
            // InternalHenshin_text.g:143:5: ruleEString
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEPackageImportRule());
            					}
            				

            					newCompositeNode(grammarAccess.getEPackageImportAccess().getRefEPackageCrossReference_1_0());
            				
            pushFollow(FOLLOW_2);
            ruleEString();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEPackageImport"


    // $ANTLR start "entryRuleEString"
    // InternalHenshin_text.g:161:1: entryRuleEString returns [String current=null] : iv_ruleEString= ruleEString EOF ;
    public final String entryRuleEString() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleEString = null;


        try {
            // InternalHenshin_text.g:161:47: (iv_ruleEString= ruleEString EOF )
            // InternalHenshin_text.g:162:2: iv_ruleEString= ruleEString EOF
            {
             newCompositeNode(grammarAccess.getEStringRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEString=ruleEString();

            state._fsp--;

             current =iv_ruleEString.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEString"


    // $ANTLR start "ruleEString"
    // InternalHenshin_text.g:168:1: ruleEString returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) ;
    public final AntlrDatatypeRuleToken ruleEString() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;


        	enterRule();

        try {
            // InternalHenshin_text.g:174:2: ( (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) )
            // InternalHenshin_text.g:175:2: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            {
            // InternalHenshin_text.g:175:2: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            // InternalHenshin_text.g:176:3: this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )*
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_6); 

            			current.merge(this_ID_0);
            		

            			newLeafNode(this_ID_0, grammarAccess.getEStringAccess().getIDTerminalRuleCall_0());
            		
            // InternalHenshin_text.g:183:3: (kw= '.' this_ID_2= RULE_ID )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==14) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalHenshin_text.g:184:4: kw= '.' this_ID_2= RULE_ID
            	    {
            	    kw=(Token)match(input,14,FOLLOW_5); 

            	    				current.merge(kw);
            	    				newLeafNode(kw, grammarAccess.getEStringAccess().getFullStopKeyword_1_0());
            	    			
            	    this_ID_2=(Token)match(input,RULE_ID,FOLLOW_6); 

            	    				current.merge(this_ID_2);
            	    			

            	    				newLeafNode(this_ID_2, grammarAccess.getEStringAccess().getIDTerminalRuleCall_1_1());
            	    			

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEString"


    // $ANTLR start "entryRuleModelElement"
    // InternalHenshin_text.g:201:1: entryRuleModelElement returns [EObject current=null] : iv_ruleModelElement= ruleModelElement EOF ;
    public final EObject entryRuleModelElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModelElement = null;


        try {
            // InternalHenshin_text.g:201:53: (iv_ruleModelElement= ruleModelElement EOF )
            // InternalHenshin_text.g:202:2: iv_ruleModelElement= ruleModelElement EOF
            {
             newCompositeNode(grammarAccess.getModelElementRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleModelElement=ruleModelElement();

            state._fsp--;

             current =iv_ruleModelElement; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModelElement"


    // $ANTLR start "ruleModelElement"
    // InternalHenshin_text.g:208:1: ruleModelElement returns [EObject current=null] : ( (otherlv_0= 'rule' () ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleParameter ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameter ) ) )* )? otherlv_7= ')' otherlv_8= '{' ( (lv_ruleElements_9_0= ruleRuleElement ) )+ otherlv_10= '}' ) | (otherlv_11= 'unit' () ( (lv_name_13_0= RULE_ID ) ) otherlv_14= '(' ( ( (lv_parameters_15_0= ruleParameter ) ) (otherlv_16= ',' ( (lv_parameters_17_0= ruleParameter ) ) )* )? otherlv_18= ')' otherlv_19= '{' ( (lv_unitElements_20_0= ruleUnitElement ) )+ otherlv_21= '}' ) ) ;
    public final EObject ruleModelElement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token lv_name_13_0=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        Token otherlv_21=null;
        EObject lv_parameters_4_0 = null;

        EObject lv_parameters_6_0 = null;

        EObject lv_ruleElements_9_0 = null;

        EObject lv_parameters_15_0 = null;

        EObject lv_parameters_17_0 = null;

        EObject lv_unitElements_20_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:214:2: ( ( (otherlv_0= 'rule' () ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleParameter ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameter ) ) )* )? otherlv_7= ')' otherlv_8= '{' ( (lv_ruleElements_9_0= ruleRuleElement ) )+ otherlv_10= '}' ) | (otherlv_11= 'unit' () ( (lv_name_13_0= RULE_ID ) ) otherlv_14= '(' ( ( (lv_parameters_15_0= ruleParameter ) ) (otherlv_16= ',' ( (lv_parameters_17_0= ruleParameter ) ) )* )? otherlv_18= ')' otherlv_19= '{' ( (lv_unitElements_20_0= ruleUnitElement ) )+ otherlv_21= '}' ) ) )
            // InternalHenshin_text.g:215:2: ( (otherlv_0= 'rule' () ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleParameter ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameter ) ) )* )? otherlv_7= ')' otherlv_8= '{' ( (lv_ruleElements_9_0= ruleRuleElement ) )+ otherlv_10= '}' ) | (otherlv_11= 'unit' () ( (lv_name_13_0= RULE_ID ) ) otherlv_14= '(' ( ( (lv_parameters_15_0= ruleParameter ) ) (otherlv_16= ',' ( (lv_parameters_17_0= ruleParameter ) ) )* )? otherlv_18= ')' otherlv_19= '{' ( (lv_unitElements_20_0= ruleUnitElement ) )+ otherlv_21= '}' ) )
            {
            // InternalHenshin_text.g:215:2: ( (otherlv_0= 'rule' () ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleParameter ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameter ) ) )* )? otherlv_7= ')' otherlv_8= '{' ( (lv_ruleElements_9_0= ruleRuleElement ) )+ otherlv_10= '}' ) | (otherlv_11= 'unit' () ( (lv_name_13_0= RULE_ID ) ) otherlv_14= '(' ( ( (lv_parameters_15_0= ruleParameter ) ) (otherlv_16= ',' ( (lv_parameters_17_0= ruleParameter ) ) )* )? otherlv_18= ')' otherlv_19= '{' ( (lv_unitElements_20_0= ruleUnitElement ) )+ otherlv_21= '}' ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==15) ) {
                alt10=1;
            }
            else if ( (LA10_0==21) ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // InternalHenshin_text.g:216:3: (otherlv_0= 'rule' () ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleParameter ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameter ) ) )* )? otherlv_7= ')' otherlv_8= '{' ( (lv_ruleElements_9_0= ruleRuleElement ) )+ otherlv_10= '}' )
                    {
                    // InternalHenshin_text.g:216:3: (otherlv_0= 'rule' () ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleParameter ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameter ) ) )* )? otherlv_7= ')' otherlv_8= '{' ( (lv_ruleElements_9_0= ruleRuleElement ) )+ otherlv_10= '}' )
                    // InternalHenshin_text.g:217:4: otherlv_0= 'rule' () ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_parameters_4_0= ruleParameter ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameter ) ) )* )? otherlv_7= ')' otherlv_8= '{' ( (lv_ruleElements_9_0= ruleRuleElement ) )+ otherlv_10= '}'
                    {
                    otherlv_0=(Token)match(input,15,FOLLOW_5); 

                    				newLeafNode(otherlv_0, grammarAccess.getModelElementAccess().getRuleKeyword_0_0());
                    			
                    // InternalHenshin_text.g:221:4: ()
                    // InternalHenshin_text.g:222:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getModelElementAccess().getRuleAction_0_1(),
                    						current);
                    				

                    }

                    // InternalHenshin_text.g:228:4: ( (lv_name_2_0= RULE_ID ) )
                    // InternalHenshin_text.g:229:5: (lv_name_2_0= RULE_ID )
                    {
                    // InternalHenshin_text.g:229:5: (lv_name_2_0= RULE_ID )
                    // InternalHenshin_text.g:230:6: lv_name_2_0= RULE_ID
                    {
                    lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_7); 

                    						newLeafNode(lv_name_2_0, grammarAccess.getModelElementAccess().getNameIDTerminalRuleCall_0_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getModelElementRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"name",
                    							lv_name_2_0,
                    							"org.eclipse.xtext.common.Terminals.ID");
                    					

                    }


                    }

                    otherlv_3=(Token)match(input,16,FOLLOW_8); 

                    				newLeafNode(otherlv_3, grammarAccess.getModelElementAccess().getLeftParenthesisKeyword_0_3());
                    			
                    // InternalHenshin_text.g:250:4: ( ( (lv_parameters_4_0= ruleParameter ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameter ) ) )* )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0==RULE_ID||(LA5_0>=70 && LA5_0<=73)) ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // InternalHenshin_text.g:251:5: ( (lv_parameters_4_0= ruleParameter ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameter ) ) )*
                            {
                            // InternalHenshin_text.g:251:5: ( (lv_parameters_4_0= ruleParameter ) )
                            // InternalHenshin_text.g:252:6: (lv_parameters_4_0= ruleParameter )
                            {
                            // InternalHenshin_text.g:252:6: (lv_parameters_4_0= ruleParameter )
                            // InternalHenshin_text.g:253:7: lv_parameters_4_0= ruleParameter
                            {

                            							newCompositeNode(grammarAccess.getModelElementAccess().getParametersParameterParserRuleCall_0_4_0_0());
                            						
                            pushFollow(FOLLOW_9);
                            lv_parameters_4_0=ruleParameter();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getModelElementRule());
                            							}
                            							add(
                            								current,
                            								"parameters",
                            								lv_parameters_4_0,
                            								"org.eclipse.emf.henshin.text.Henshin_text.Parameter");
                            							afterParserOrEnumRuleCall();
                            						

                            }


                            }

                            // InternalHenshin_text.g:270:5: (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameter ) ) )*
                            loop4:
                            do {
                                int alt4=2;
                                int LA4_0 = input.LA(1);

                                if ( (LA4_0==17) ) {
                                    alt4=1;
                                }


                                switch (alt4) {
                            	case 1 :
                            	    // InternalHenshin_text.g:271:6: otherlv_5= ',' ( (lv_parameters_6_0= ruleParameter ) )
                            	    {
                            	    otherlv_5=(Token)match(input,17,FOLLOW_10); 

                            	    						newLeafNode(otherlv_5, grammarAccess.getModelElementAccess().getCommaKeyword_0_4_1_0());
                            	    					
                            	    // InternalHenshin_text.g:275:6: ( (lv_parameters_6_0= ruleParameter ) )
                            	    // InternalHenshin_text.g:276:7: (lv_parameters_6_0= ruleParameter )
                            	    {
                            	    // InternalHenshin_text.g:276:7: (lv_parameters_6_0= ruleParameter )
                            	    // InternalHenshin_text.g:277:8: lv_parameters_6_0= ruleParameter
                            	    {

                            	    								newCompositeNode(grammarAccess.getModelElementAccess().getParametersParameterParserRuleCall_0_4_1_1_0());
                            	    							
                            	    pushFollow(FOLLOW_9);
                            	    lv_parameters_6_0=ruleParameter();

                            	    state._fsp--;


                            	    								if (current==null) {
                            	    									current = createModelElementForParent(grammarAccess.getModelElementRule());
                            	    								}
                            	    								add(
                            	    									current,
                            	    									"parameters",
                            	    									lv_parameters_6_0,
                            	    									"org.eclipse.emf.henshin.text.Henshin_text.Parameter");
                            	    								afterParserOrEnumRuleCall();
                            	    							

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop4;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_7=(Token)match(input,18,FOLLOW_11); 

                    				newLeafNode(otherlv_7, grammarAccess.getModelElementAccess().getRightParenthesisKeyword_0_5());
                    			
                    otherlv_8=(Token)match(input,19,FOLLOW_12); 

                    				newLeafNode(otherlv_8, grammarAccess.getModelElementAccess().getLeftCurlyBracketKeyword_0_6());
                    			
                    // InternalHenshin_text.g:304:4: ( (lv_ruleElements_9_0= ruleRuleElement ) )+
                    int cnt6=0;
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( ((LA6_0>=22 && LA6_0<=25)||LA6_0==28) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // InternalHenshin_text.g:305:5: (lv_ruleElements_9_0= ruleRuleElement )
                    	    {
                    	    // InternalHenshin_text.g:305:5: (lv_ruleElements_9_0= ruleRuleElement )
                    	    // InternalHenshin_text.g:306:6: lv_ruleElements_9_0= ruleRuleElement
                    	    {

                    	    						newCompositeNode(grammarAccess.getModelElementAccess().getRuleElementsRuleElementParserRuleCall_0_7_0());
                    	    					
                    	    pushFollow(FOLLOW_13);
                    	    lv_ruleElements_9_0=ruleRuleElement();

                    	    state._fsp--;


                    	    						if (current==null) {
                    	    							current = createModelElementForParent(grammarAccess.getModelElementRule());
                    	    						}
                    	    						add(
                    	    							current,
                    	    							"ruleElements",
                    	    							lv_ruleElements_9_0,
                    	    							"org.eclipse.emf.henshin.text.Henshin_text.RuleElement");
                    	    						afterParserOrEnumRuleCall();
                    	    					

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt6 >= 1 ) break loop6;
                                EarlyExitException eee =
                                    new EarlyExitException(6, input);
                                throw eee;
                        }
                        cnt6++;
                    } while (true);

                    otherlv_10=(Token)match(input,20,FOLLOW_2); 

                    				newLeafNode(otherlv_10, grammarAccess.getModelElementAccess().getRightCurlyBracketKeyword_0_8());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalHenshin_text.g:329:3: (otherlv_11= 'unit' () ( (lv_name_13_0= RULE_ID ) ) otherlv_14= '(' ( ( (lv_parameters_15_0= ruleParameter ) ) (otherlv_16= ',' ( (lv_parameters_17_0= ruleParameter ) ) )* )? otherlv_18= ')' otherlv_19= '{' ( (lv_unitElements_20_0= ruleUnitElement ) )+ otherlv_21= '}' )
                    {
                    // InternalHenshin_text.g:329:3: (otherlv_11= 'unit' () ( (lv_name_13_0= RULE_ID ) ) otherlv_14= '(' ( ( (lv_parameters_15_0= ruleParameter ) ) (otherlv_16= ',' ( (lv_parameters_17_0= ruleParameter ) ) )* )? otherlv_18= ')' otherlv_19= '{' ( (lv_unitElements_20_0= ruleUnitElement ) )+ otherlv_21= '}' )
                    // InternalHenshin_text.g:330:4: otherlv_11= 'unit' () ( (lv_name_13_0= RULE_ID ) ) otherlv_14= '(' ( ( (lv_parameters_15_0= ruleParameter ) ) (otherlv_16= ',' ( (lv_parameters_17_0= ruleParameter ) ) )* )? otherlv_18= ')' otherlv_19= '{' ( (lv_unitElements_20_0= ruleUnitElement ) )+ otherlv_21= '}'
                    {
                    otherlv_11=(Token)match(input,21,FOLLOW_5); 

                    				newLeafNode(otherlv_11, grammarAccess.getModelElementAccess().getUnitKeyword_1_0());
                    			
                    // InternalHenshin_text.g:334:4: ()
                    // InternalHenshin_text.g:335:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getModelElementAccess().getUnitAction_1_1(),
                    						current);
                    				

                    }

                    // InternalHenshin_text.g:341:4: ( (lv_name_13_0= RULE_ID ) )
                    // InternalHenshin_text.g:342:5: (lv_name_13_0= RULE_ID )
                    {
                    // InternalHenshin_text.g:342:5: (lv_name_13_0= RULE_ID )
                    // InternalHenshin_text.g:343:6: lv_name_13_0= RULE_ID
                    {
                    lv_name_13_0=(Token)match(input,RULE_ID,FOLLOW_7); 

                    						newLeafNode(lv_name_13_0, grammarAccess.getModelElementAccess().getNameIDTerminalRuleCall_1_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getModelElementRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"name",
                    							lv_name_13_0,
                    							"org.eclipse.xtext.common.Terminals.ID");
                    					

                    }


                    }

                    otherlv_14=(Token)match(input,16,FOLLOW_8); 

                    				newLeafNode(otherlv_14, grammarAccess.getModelElementAccess().getLeftParenthesisKeyword_1_3());
                    			
                    // InternalHenshin_text.g:363:4: ( ( (lv_parameters_15_0= ruleParameter ) ) (otherlv_16= ',' ( (lv_parameters_17_0= ruleParameter ) ) )* )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==RULE_ID||(LA8_0>=70 && LA8_0<=73)) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // InternalHenshin_text.g:364:5: ( (lv_parameters_15_0= ruleParameter ) ) (otherlv_16= ',' ( (lv_parameters_17_0= ruleParameter ) ) )*
                            {
                            // InternalHenshin_text.g:364:5: ( (lv_parameters_15_0= ruleParameter ) )
                            // InternalHenshin_text.g:365:6: (lv_parameters_15_0= ruleParameter )
                            {
                            // InternalHenshin_text.g:365:6: (lv_parameters_15_0= ruleParameter )
                            // InternalHenshin_text.g:366:7: lv_parameters_15_0= ruleParameter
                            {

                            							newCompositeNode(grammarAccess.getModelElementAccess().getParametersParameterParserRuleCall_1_4_0_0());
                            						
                            pushFollow(FOLLOW_9);
                            lv_parameters_15_0=ruleParameter();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getModelElementRule());
                            							}
                            							add(
                            								current,
                            								"parameters",
                            								lv_parameters_15_0,
                            								"org.eclipse.emf.henshin.text.Henshin_text.Parameter");
                            							afterParserOrEnumRuleCall();
                            						

                            }


                            }

                            // InternalHenshin_text.g:383:5: (otherlv_16= ',' ( (lv_parameters_17_0= ruleParameter ) ) )*
                            loop7:
                            do {
                                int alt7=2;
                                int LA7_0 = input.LA(1);

                                if ( (LA7_0==17) ) {
                                    alt7=1;
                                }


                                switch (alt7) {
                            	case 1 :
                            	    // InternalHenshin_text.g:384:6: otherlv_16= ',' ( (lv_parameters_17_0= ruleParameter ) )
                            	    {
                            	    otherlv_16=(Token)match(input,17,FOLLOW_10); 

                            	    						newLeafNode(otherlv_16, grammarAccess.getModelElementAccess().getCommaKeyword_1_4_1_0());
                            	    					
                            	    // InternalHenshin_text.g:388:6: ( (lv_parameters_17_0= ruleParameter ) )
                            	    // InternalHenshin_text.g:389:7: (lv_parameters_17_0= ruleParameter )
                            	    {
                            	    // InternalHenshin_text.g:389:7: (lv_parameters_17_0= ruleParameter )
                            	    // InternalHenshin_text.g:390:8: lv_parameters_17_0= ruleParameter
                            	    {

                            	    								newCompositeNode(grammarAccess.getModelElementAccess().getParametersParameterParserRuleCall_1_4_1_1_0());
                            	    							
                            	    pushFollow(FOLLOW_9);
                            	    lv_parameters_17_0=ruleParameter();

                            	    state._fsp--;


                            	    								if (current==null) {
                            	    									current = createModelElementForParent(grammarAccess.getModelElementRule());
                            	    								}
                            	    								add(
                            	    									current,
                            	    									"parameters",
                            	    									lv_parameters_17_0,
                            	    									"org.eclipse.emf.henshin.text.Henshin_text.Parameter");
                            	    								afterParserOrEnumRuleCall();
                            	    							

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop7;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_18=(Token)match(input,18,FOLLOW_11); 

                    				newLeafNode(otherlv_18, grammarAccess.getModelElementAccess().getRightParenthesisKeyword_1_5());
                    			
                    otherlv_19=(Token)match(input,19,FOLLOW_14); 

                    				newLeafNode(otherlv_19, grammarAccess.getModelElementAccess().getLeftCurlyBracketKeyword_1_6());
                    			
                    // InternalHenshin_text.g:417:4: ( (lv_unitElements_20_0= ruleUnitElement ) )+
                    int cnt9=0;
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( (LA9_0==RULE_ID||LA9_0==19||(LA9_0>=49 && LA9_0<=52)||(LA9_0>=55 && LA9_0<=57)) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // InternalHenshin_text.g:418:5: (lv_unitElements_20_0= ruleUnitElement )
                    	    {
                    	    // InternalHenshin_text.g:418:5: (lv_unitElements_20_0= ruleUnitElement )
                    	    // InternalHenshin_text.g:419:6: lv_unitElements_20_0= ruleUnitElement
                    	    {

                    	    						newCompositeNode(grammarAccess.getModelElementAccess().getUnitElementsUnitElementParserRuleCall_1_7_0());
                    	    					
                    	    pushFollow(FOLLOW_15);
                    	    lv_unitElements_20_0=ruleUnitElement();

                    	    state._fsp--;


                    	    						if (current==null) {
                    	    							current = createModelElementForParent(grammarAccess.getModelElementRule());
                    	    						}
                    	    						add(
                    	    							current,
                    	    							"unitElements",
                    	    							lv_unitElements_20_0,
                    	    							"org.eclipse.emf.henshin.text.Henshin_text.UnitElement");
                    	    						afterParserOrEnumRuleCall();
                    	    					

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt9 >= 1 ) break loop9;
                                EarlyExitException eee =
                                    new EarlyExitException(9, input);
                                throw eee;
                        }
                        cnt9++;
                    } while (true);

                    otherlv_21=(Token)match(input,20,FOLLOW_2); 

                    				newLeafNode(otherlv_21, grammarAccess.getModelElementAccess().getRightCurlyBracketKeyword_1_8());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModelElement"


    // $ANTLR start "entryRuleRuleElement"
    // InternalHenshin_text.g:445:1: entryRuleRuleElement returns [EObject current=null] : iv_ruleRuleElement= ruleRuleElement EOF ;
    public final EObject entryRuleRuleElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRuleElement = null;


        try {
            // InternalHenshin_text.g:445:52: (iv_ruleRuleElement= ruleRuleElement EOF )
            // InternalHenshin_text.g:446:2: iv_ruleRuleElement= ruleRuleElement EOF
            {
             newCompositeNode(grammarAccess.getRuleElementRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRuleElement=ruleRuleElement();

            state._fsp--;

             current =iv_ruleRuleElement; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRuleElement"


    // $ANTLR start "ruleRuleElement"
    // InternalHenshin_text.g:452:1: ruleRuleElement returns [EObject current=null] : (this_JavaImport_0= ruleJavaImport | this_CheckDangling_1= ruleCheckDangling | this_InjectiveMatching_2= ruleInjectiveMatching | this_Conditions_3= ruleConditions | this_Graph_4= ruleGraph ) ;
    public final EObject ruleRuleElement() throws RecognitionException {
        EObject current = null;

        EObject this_JavaImport_0 = null;

        EObject this_CheckDangling_1 = null;

        EObject this_InjectiveMatching_2 = null;

        EObject this_Conditions_3 = null;

        EObject this_Graph_4 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:458:2: ( (this_JavaImport_0= ruleJavaImport | this_CheckDangling_1= ruleCheckDangling | this_InjectiveMatching_2= ruleInjectiveMatching | this_Conditions_3= ruleConditions | this_Graph_4= ruleGraph ) )
            // InternalHenshin_text.g:459:2: (this_JavaImport_0= ruleJavaImport | this_CheckDangling_1= ruleCheckDangling | this_InjectiveMatching_2= ruleInjectiveMatching | this_Conditions_3= ruleConditions | this_Graph_4= ruleGraph )
            {
            // InternalHenshin_text.g:459:2: (this_JavaImport_0= ruleJavaImport | this_CheckDangling_1= ruleCheckDangling | this_InjectiveMatching_2= ruleInjectiveMatching | this_Conditions_3= ruleConditions | this_Graph_4= ruleGraph )
            int alt11=5;
            switch ( input.LA(1) ) {
            case 22:
                {
                alt11=1;
                }
                break;
            case 23:
                {
                alt11=2;
                }
                break;
            case 24:
                {
                alt11=3;
                }
                break;
            case 25:
                {
                alt11=4;
                }
                break;
            case 28:
                {
                alt11=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }

            switch (alt11) {
                case 1 :
                    // InternalHenshin_text.g:460:3: this_JavaImport_0= ruleJavaImport
                    {

                    			newCompositeNode(grammarAccess.getRuleElementAccess().getJavaImportParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_JavaImport_0=ruleJavaImport();

                    state._fsp--;


                    			current = this_JavaImport_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalHenshin_text.g:469:3: this_CheckDangling_1= ruleCheckDangling
                    {

                    			newCompositeNode(grammarAccess.getRuleElementAccess().getCheckDanglingParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_CheckDangling_1=ruleCheckDangling();

                    state._fsp--;


                    			current = this_CheckDangling_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalHenshin_text.g:478:3: this_InjectiveMatching_2= ruleInjectiveMatching
                    {

                    			newCompositeNode(grammarAccess.getRuleElementAccess().getInjectiveMatchingParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_InjectiveMatching_2=ruleInjectiveMatching();

                    state._fsp--;


                    			current = this_InjectiveMatching_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalHenshin_text.g:487:3: this_Conditions_3= ruleConditions
                    {

                    			newCompositeNode(grammarAccess.getRuleElementAccess().getConditionsParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_Conditions_3=ruleConditions();

                    state._fsp--;


                    			current = this_Conditions_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 5 :
                    // InternalHenshin_text.g:496:3: this_Graph_4= ruleGraph
                    {

                    			newCompositeNode(grammarAccess.getRuleElementAccess().getGraphParserRuleCall_4());
                    		
                    pushFollow(FOLLOW_2);
                    this_Graph_4=ruleGraph();

                    state._fsp--;


                    			current = this_Graph_4;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRuleElement"


    // $ANTLR start "entryRuleJavaImport"
    // InternalHenshin_text.g:508:1: entryRuleJavaImport returns [EObject current=null] : iv_ruleJavaImport= ruleJavaImport EOF ;
    public final EObject entryRuleJavaImport() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJavaImport = null;


        try {
            // InternalHenshin_text.g:508:51: (iv_ruleJavaImport= ruleJavaImport EOF )
            // InternalHenshin_text.g:509:2: iv_ruleJavaImport= ruleJavaImport EOF
            {
             newCompositeNode(grammarAccess.getJavaImportRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleJavaImport=ruleJavaImport();

            state._fsp--;

             current =iv_ruleJavaImport; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJavaImport"


    // $ANTLR start "ruleJavaImport"
    // InternalHenshin_text.g:515:1: ruleJavaImport returns [EObject current=null] : (otherlv_0= 'javaImport' ( (lv_packagename_1_0= ruleEString ) ) ) ;
    public final EObject ruleJavaImport() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        AntlrDatatypeRuleToken lv_packagename_1_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:521:2: ( (otherlv_0= 'javaImport' ( (lv_packagename_1_0= ruleEString ) ) ) )
            // InternalHenshin_text.g:522:2: (otherlv_0= 'javaImport' ( (lv_packagename_1_0= ruleEString ) ) )
            {
            // InternalHenshin_text.g:522:2: (otherlv_0= 'javaImport' ( (lv_packagename_1_0= ruleEString ) ) )
            // InternalHenshin_text.g:523:3: otherlv_0= 'javaImport' ( (lv_packagename_1_0= ruleEString ) )
            {
            otherlv_0=(Token)match(input,22,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getJavaImportAccess().getJavaImportKeyword_0());
            		
            // InternalHenshin_text.g:527:3: ( (lv_packagename_1_0= ruleEString ) )
            // InternalHenshin_text.g:528:4: (lv_packagename_1_0= ruleEString )
            {
            // InternalHenshin_text.g:528:4: (lv_packagename_1_0= ruleEString )
            // InternalHenshin_text.g:529:5: lv_packagename_1_0= ruleEString
            {

            					newCompositeNode(grammarAccess.getJavaImportAccess().getPackagenameEStringParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_packagename_1_0=ruleEString();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getJavaImportRule());
            					}
            					set(
            						current,
            						"packagename",
            						lv_packagename_1_0,
            						"org.eclipse.emf.henshin.text.Henshin_text.EString");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJavaImport"


    // $ANTLR start "entryRuleCheckDangling"
    // InternalHenshin_text.g:550:1: entryRuleCheckDangling returns [EObject current=null] : iv_ruleCheckDangling= ruleCheckDangling EOF ;
    public final EObject entryRuleCheckDangling() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCheckDangling = null;


        try {
            // InternalHenshin_text.g:550:54: (iv_ruleCheckDangling= ruleCheckDangling EOF )
            // InternalHenshin_text.g:551:2: iv_ruleCheckDangling= ruleCheckDangling EOF
            {
             newCompositeNode(grammarAccess.getCheckDanglingRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCheckDangling=ruleCheckDangling();

            state._fsp--;

             current =iv_ruleCheckDangling; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCheckDangling"


    // $ANTLR start "ruleCheckDangling"
    // InternalHenshin_text.g:557:1: ruleCheckDangling returns [EObject current=null] : (otherlv_0= 'checkDangling' ( (lv_checkDangling_1_0= ruleEBoolean ) ) ) ;
    public final EObject ruleCheckDangling() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        AntlrDatatypeRuleToken lv_checkDangling_1_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:563:2: ( (otherlv_0= 'checkDangling' ( (lv_checkDangling_1_0= ruleEBoolean ) ) ) )
            // InternalHenshin_text.g:564:2: (otherlv_0= 'checkDangling' ( (lv_checkDangling_1_0= ruleEBoolean ) ) )
            {
            // InternalHenshin_text.g:564:2: (otherlv_0= 'checkDangling' ( (lv_checkDangling_1_0= ruleEBoolean ) ) )
            // InternalHenshin_text.g:565:3: otherlv_0= 'checkDangling' ( (lv_checkDangling_1_0= ruleEBoolean ) )
            {
            otherlv_0=(Token)match(input,23,FOLLOW_16); 

            			newLeafNode(otherlv_0, grammarAccess.getCheckDanglingAccess().getCheckDanglingKeyword_0());
            		
            // InternalHenshin_text.g:569:3: ( (lv_checkDangling_1_0= ruleEBoolean ) )
            // InternalHenshin_text.g:570:4: (lv_checkDangling_1_0= ruleEBoolean )
            {
            // InternalHenshin_text.g:570:4: (lv_checkDangling_1_0= ruleEBoolean )
            // InternalHenshin_text.g:571:5: lv_checkDangling_1_0= ruleEBoolean
            {

            					newCompositeNode(grammarAccess.getCheckDanglingAccess().getCheckDanglingEBooleanParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_checkDangling_1_0=ruleEBoolean();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCheckDanglingRule());
            					}
            					set(
            						current,
            						"checkDangling",
            						lv_checkDangling_1_0,
            						"org.eclipse.emf.henshin.text.Henshin_text.EBoolean");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCheckDangling"


    // $ANTLR start "entryRuleInjectiveMatching"
    // InternalHenshin_text.g:592:1: entryRuleInjectiveMatching returns [EObject current=null] : iv_ruleInjectiveMatching= ruleInjectiveMatching EOF ;
    public final EObject entryRuleInjectiveMatching() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInjectiveMatching = null;


        try {
            // InternalHenshin_text.g:592:58: (iv_ruleInjectiveMatching= ruleInjectiveMatching EOF )
            // InternalHenshin_text.g:593:2: iv_ruleInjectiveMatching= ruleInjectiveMatching EOF
            {
             newCompositeNode(grammarAccess.getInjectiveMatchingRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleInjectiveMatching=ruleInjectiveMatching();

            state._fsp--;

             current =iv_ruleInjectiveMatching; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInjectiveMatching"


    // $ANTLR start "ruleInjectiveMatching"
    // InternalHenshin_text.g:599:1: ruleInjectiveMatching returns [EObject current=null] : (otherlv_0= 'injectiveMatching' ( (lv_injectiveMatching_1_0= ruleEBoolean ) ) ) ;
    public final EObject ruleInjectiveMatching() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        AntlrDatatypeRuleToken lv_injectiveMatching_1_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:605:2: ( (otherlv_0= 'injectiveMatching' ( (lv_injectiveMatching_1_0= ruleEBoolean ) ) ) )
            // InternalHenshin_text.g:606:2: (otherlv_0= 'injectiveMatching' ( (lv_injectiveMatching_1_0= ruleEBoolean ) ) )
            {
            // InternalHenshin_text.g:606:2: (otherlv_0= 'injectiveMatching' ( (lv_injectiveMatching_1_0= ruleEBoolean ) ) )
            // InternalHenshin_text.g:607:3: otherlv_0= 'injectiveMatching' ( (lv_injectiveMatching_1_0= ruleEBoolean ) )
            {
            otherlv_0=(Token)match(input,24,FOLLOW_16); 

            			newLeafNode(otherlv_0, grammarAccess.getInjectiveMatchingAccess().getInjectiveMatchingKeyword_0());
            		
            // InternalHenshin_text.g:611:3: ( (lv_injectiveMatching_1_0= ruleEBoolean ) )
            // InternalHenshin_text.g:612:4: (lv_injectiveMatching_1_0= ruleEBoolean )
            {
            // InternalHenshin_text.g:612:4: (lv_injectiveMatching_1_0= ruleEBoolean )
            // InternalHenshin_text.g:613:5: lv_injectiveMatching_1_0= ruleEBoolean
            {

            					newCompositeNode(grammarAccess.getInjectiveMatchingAccess().getInjectiveMatchingEBooleanParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_injectiveMatching_1_0=ruleEBoolean();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getInjectiveMatchingRule());
            					}
            					set(
            						current,
            						"injectiveMatching",
            						lv_injectiveMatching_1_0,
            						"org.eclipse.emf.henshin.text.Henshin_text.EBoolean");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInjectiveMatching"


    // $ANTLR start "entryRuleConditions"
    // InternalHenshin_text.g:634:1: entryRuleConditions returns [EObject current=null] : iv_ruleConditions= ruleConditions EOF ;
    public final EObject entryRuleConditions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConditions = null;


        try {
            // InternalHenshin_text.g:634:51: (iv_ruleConditions= ruleConditions EOF )
            // InternalHenshin_text.g:635:2: iv_ruleConditions= ruleConditions EOF
            {
             newCompositeNode(grammarAccess.getConditionsRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConditions=ruleConditions();

            state._fsp--;

             current =iv_ruleConditions; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConditions"


    // $ANTLR start "ruleConditions"
    // InternalHenshin_text.g:641:1: ruleConditions returns [EObject current=null] : (otherlv_0= 'conditions' otherlv_1= '[' ( (lv_attributeConditions_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_attributeConditions_4_0= ruleExpression ) ) )* otherlv_5= ']' ) ;
    public final EObject ruleConditions() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_attributeConditions_2_0 = null;

        EObject lv_attributeConditions_4_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:647:2: ( (otherlv_0= 'conditions' otherlv_1= '[' ( (lv_attributeConditions_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_attributeConditions_4_0= ruleExpression ) ) )* otherlv_5= ']' ) )
            // InternalHenshin_text.g:648:2: (otherlv_0= 'conditions' otherlv_1= '[' ( (lv_attributeConditions_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_attributeConditions_4_0= ruleExpression ) ) )* otherlv_5= ']' )
            {
            // InternalHenshin_text.g:648:2: (otherlv_0= 'conditions' otherlv_1= '[' ( (lv_attributeConditions_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_attributeConditions_4_0= ruleExpression ) ) )* otherlv_5= ']' )
            // InternalHenshin_text.g:649:3: otherlv_0= 'conditions' otherlv_1= '[' ( (lv_attributeConditions_2_0= ruleExpression ) ) (otherlv_3= ',' ( (lv_attributeConditions_4_0= ruleExpression ) ) )* otherlv_5= ']'
            {
            otherlv_0=(Token)match(input,25,FOLLOW_17); 

            			newLeafNode(otherlv_0, grammarAccess.getConditionsAccess().getConditionsKeyword_0());
            		
            otherlv_1=(Token)match(input,26,FOLLOW_18); 

            			newLeafNode(otherlv_1, grammarAccess.getConditionsAccess().getLeftSquareBracketKeyword_1());
            		
            // InternalHenshin_text.g:657:3: ( (lv_attributeConditions_2_0= ruleExpression ) )
            // InternalHenshin_text.g:658:4: (lv_attributeConditions_2_0= ruleExpression )
            {
            // InternalHenshin_text.g:658:4: (lv_attributeConditions_2_0= ruleExpression )
            // InternalHenshin_text.g:659:5: lv_attributeConditions_2_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getConditionsAccess().getAttributeConditionsExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_19);
            lv_attributeConditions_2_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getConditionsRule());
            					}
            					add(
            						current,
            						"attributeConditions",
            						lv_attributeConditions_2_0,
            						"org.eclipse.emf.henshin.text.Henshin_text.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalHenshin_text.g:676:3: (otherlv_3= ',' ( (lv_attributeConditions_4_0= ruleExpression ) ) )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==17) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalHenshin_text.g:677:4: otherlv_3= ',' ( (lv_attributeConditions_4_0= ruleExpression ) )
            	    {
            	    otherlv_3=(Token)match(input,17,FOLLOW_18); 

            	    				newLeafNode(otherlv_3, grammarAccess.getConditionsAccess().getCommaKeyword_3_0());
            	    			
            	    // InternalHenshin_text.g:681:4: ( (lv_attributeConditions_4_0= ruleExpression ) )
            	    // InternalHenshin_text.g:682:5: (lv_attributeConditions_4_0= ruleExpression )
            	    {
            	    // InternalHenshin_text.g:682:5: (lv_attributeConditions_4_0= ruleExpression )
            	    // InternalHenshin_text.g:683:6: lv_attributeConditions_4_0= ruleExpression
            	    {

            	    						newCompositeNode(grammarAccess.getConditionsAccess().getAttributeConditionsExpressionParserRuleCall_3_1_0());
            	    					
            	    pushFollow(FOLLOW_19);
            	    lv_attributeConditions_4_0=ruleExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getConditionsRule());
            	    						}
            	    						add(
            	    							current,
            	    							"attributeConditions",
            	    							lv_attributeConditions_4_0,
            	    							"org.eclipse.emf.henshin.text.Henshin_text.Expression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

            otherlv_5=(Token)match(input,27,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getConditionsAccess().getRightSquareBracketKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConditions"


    // $ANTLR start "entryRuleGraph"
    // InternalHenshin_text.g:709:1: entryRuleGraph returns [EObject current=null] : iv_ruleGraph= ruleGraph EOF ;
    public final EObject entryRuleGraph() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGraph = null;


        try {
            // InternalHenshin_text.g:709:46: (iv_ruleGraph= ruleGraph EOF )
            // InternalHenshin_text.g:710:2: iv_ruleGraph= ruleGraph EOF
            {
             newCompositeNode(grammarAccess.getGraphRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleGraph=ruleGraph();

            state._fsp--;

             current =iv_ruleGraph; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGraph"


    // $ANTLR start "ruleGraph"
    // InternalHenshin_text.g:716:1: ruleGraph returns [EObject current=null] : ( () otherlv_1= 'graph' otherlv_2= '{' ( (lv_graphElements_3_0= ruleGraphElements ) )* otherlv_4= '}' ) ;
    public final EObject ruleGraph() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_graphElements_3_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:722:2: ( ( () otherlv_1= 'graph' otherlv_2= '{' ( (lv_graphElements_3_0= ruleGraphElements ) )* otherlv_4= '}' ) )
            // InternalHenshin_text.g:723:2: ( () otherlv_1= 'graph' otherlv_2= '{' ( (lv_graphElements_3_0= ruleGraphElements ) )* otherlv_4= '}' )
            {
            // InternalHenshin_text.g:723:2: ( () otherlv_1= 'graph' otherlv_2= '{' ( (lv_graphElements_3_0= ruleGraphElements ) )* otherlv_4= '}' )
            // InternalHenshin_text.g:724:3: () otherlv_1= 'graph' otherlv_2= '{' ( (lv_graphElements_3_0= ruleGraphElements ) )* otherlv_4= '}'
            {
            // InternalHenshin_text.g:724:3: ()
            // InternalHenshin_text.g:725:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getGraphAccess().getGraphAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,28,FOLLOW_11); 

            			newLeafNode(otherlv_1, grammarAccess.getGraphAccess().getGraphKeyword_1());
            		
            otherlv_2=(Token)match(input,19,FOLLOW_20); 

            			newLeafNode(otherlv_2, grammarAccess.getGraphAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalHenshin_text.g:739:3: ( (lv_graphElements_3_0= ruleGraphElements ) )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0>=29 && LA13_0<=34)||(LA13_0>=37 && LA13_0<=38)||(LA13_0>=41 && LA13_0<=42)) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalHenshin_text.g:740:4: (lv_graphElements_3_0= ruleGraphElements )
            	    {
            	    // InternalHenshin_text.g:740:4: (lv_graphElements_3_0= ruleGraphElements )
            	    // InternalHenshin_text.g:741:5: lv_graphElements_3_0= ruleGraphElements
            	    {

            	    					newCompositeNode(grammarAccess.getGraphAccess().getGraphElementsGraphElementsParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_20);
            	    lv_graphElements_3_0=ruleGraphElements();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getGraphRule());
            	    					}
            	    					add(
            	    						current,
            	    						"graphElements",
            	    						lv_graphElements_3_0,
            	    						"org.eclipse.emf.henshin.text.Henshin_text.GraphElements");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

            otherlv_4=(Token)match(input,20,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getGraphAccess().getRightCurlyBracketKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGraph"


    // $ANTLR start "entryRuleActionType"
    // InternalHenshin_text.g:766:1: entryRuleActionType returns [String current=null] : iv_ruleActionType= ruleActionType EOF ;
    public final String entryRuleActionType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleActionType = null;


        try {
            // InternalHenshin_text.g:766:50: (iv_ruleActionType= ruleActionType EOF )
            // InternalHenshin_text.g:767:2: iv_ruleActionType= ruleActionType EOF
            {
             newCompositeNode(grammarAccess.getActionTypeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleActionType=ruleActionType();

            state._fsp--;

             current =iv_ruleActionType.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleActionType"


    // $ANTLR start "ruleActionType"
    // InternalHenshin_text.g:773:1: ruleActionType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'preserve' | kw= 'create' | kw= 'delete' | kw= 'forbid' | kw= 'require' ) ;
    public final AntlrDatatypeRuleToken ruleActionType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalHenshin_text.g:779:2: ( (kw= 'preserve' | kw= 'create' | kw= 'delete' | kw= 'forbid' | kw= 'require' ) )
            // InternalHenshin_text.g:780:2: (kw= 'preserve' | kw= 'create' | kw= 'delete' | kw= 'forbid' | kw= 'require' )
            {
            // InternalHenshin_text.g:780:2: (kw= 'preserve' | kw= 'create' | kw= 'delete' | kw= 'forbid' | kw= 'require' )
            int alt14=5;
            switch ( input.LA(1) ) {
            case 29:
                {
                alt14=1;
                }
                break;
            case 30:
                {
                alt14=2;
                }
                break;
            case 31:
                {
                alt14=3;
                }
                break;
            case 32:
                {
                alt14=4;
                }
                break;
            case 33:
                {
                alt14=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // InternalHenshin_text.g:781:3: kw= 'preserve'
                    {
                    kw=(Token)match(input,29,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getActionTypeAccess().getPreserveKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalHenshin_text.g:787:3: kw= 'create'
                    {
                    kw=(Token)match(input,30,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getActionTypeAccess().getCreateKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalHenshin_text.g:793:3: kw= 'delete'
                    {
                    kw=(Token)match(input,31,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getActionTypeAccess().getDeleteKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalHenshin_text.g:799:3: kw= 'forbid'
                    {
                    kw=(Token)match(input,32,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getActionTypeAccess().getForbidKeyword_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalHenshin_text.g:805:3: kw= 'require'
                    {
                    kw=(Token)match(input,33,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getActionTypeAccess().getRequireKeyword_4());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleActionType"


    // $ANTLR start "entryRuleGraphElements"
    // InternalHenshin_text.g:814:1: entryRuleGraphElements returns [EObject current=null] : iv_ruleGraphElements= ruleGraphElements EOF ;
    public final EObject entryRuleGraphElements() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGraphElements = null;


        try {
            // InternalHenshin_text.g:814:54: (iv_ruleGraphElements= ruleGraphElements EOF )
            // InternalHenshin_text.g:815:2: iv_ruleGraphElements= ruleGraphElements EOF
            {
             newCompositeNode(grammarAccess.getGraphElementsRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleGraphElements=ruleGraphElements();

            state._fsp--;

             current =iv_ruleGraphElements; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGraphElements"


    // $ANTLR start "ruleGraphElements"
    // InternalHenshin_text.g:821:1: ruleGraphElements returns [EObject current=null] : (this_Edges_0= ruleEdges | this_Node_1= ruleNode | this_Formula_2= ruleFormula | this_MultiRule_3= ruleMultiRule | this_MultiRuleReuseNode_4= ruleMultiRuleReuseNode ) ;
    public final EObject ruleGraphElements() throws RecognitionException {
        EObject current = null;

        EObject this_Edges_0 = null;

        EObject this_Node_1 = null;

        EObject this_Formula_2 = null;

        EObject this_MultiRule_3 = null;

        EObject this_MultiRuleReuseNode_4 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:827:2: ( (this_Edges_0= ruleEdges | this_Node_1= ruleNode | this_Formula_2= ruleFormula | this_MultiRule_3= ruleMultiRule | this_MultiRuleReuseNode_4= ruleMultiRuleReuseNode ) )
            // InternalHenshin_text.g:828:2: (this_Edges_0= ruleEdges | this_Node_1= ruleNode | this_Formula_2= ruleFormula | this_MultiRule_3= ruleMultiRule | this_MultiRuleReuseNode_4= ruleMultiRuleReuseNode )
            {
            // InternalHenshin_text.g:828:2: (this_Edges_0= ruleEdges | this_Node_1= ruleNode | this_Formula_2= ruleFormula | this_MultiRule_3= ruleMultiRule | this_MultiRuleReuseNode_4= ruleMultiRuleReuseNode )
            int alt15=5;
            switch ( input.LA(1) ) {
            case 34:
                {
                alt15=1;
                }
                break;
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 37:
                {
                alt15=2;
                }
                break;
            case 42:
                {
                alt15=3;
                }
                break;
            case 41:
                {
                alt15=4;
                }
                break;
            case 38:
                {
                alt15=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // InternalHenshin_text.g:829:3: this_Edges_0= ruleEdges
                    {

                    			newCompositeNode(grammarAccess.getGraphElementsAccess().getEdgesParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Edges_0=ruleEdges();

                    state._fsp--;


                    			current = this_Edges_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalHenshin_text.g:838:3: this_Node_1= ruleNode
                    {

                    			newCompositeNode(grammarAccess.getGraphElementsAccess().getNodeParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_Node_1=ruleNode();

                    state._fsp--;


                    			current = this_Node_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalHenshin_text.g:847:3: this_Formula_2= ruleFormula
                    {

                    			newCompositeNode(grammarAccess.getGraphElementsAccess().getFormulaParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_Formula_2=ruleFormula();

                    state._fsp--;


                    			current = this_Formula_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalHenshin_text.g:856:3: this_MultiRule_3= ruleMultiRule
                    {

                    			newCompositeNode(grammarAccess.getGraphElementsAccess().getMultiRuleParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_MultiRule_3=ruleMultiRule();

                    state._fsp--;


                    			current = this_MultiRule_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 5 :
                    // InternalHenshin_text.g:865:3: this_MultiRuleReuseNode_4= ruleMultiRuleReuseNode
                    {

                    			newCompositeNode(grammarAccess.getGraphElementsAccess().getMultiRuleReuseNodeParserRuleCall_4());
                    		
                    pushFollow(FOLLOW_2);
                    this_MultiRuleReuseNode_4=ruleMultiRuleReuseNode();

                    state._fsp--;


                    			current = this_MultiRuleReuseNode_4;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGraphElements"


    // $ANTLR start "entryRuleEdges"
    // InternalHenshin_text.g:877:1: entryRuleEdges returns [EObject current=null] : iv_ruleEdges= ruleEdges EOF ;
    public final EObject entryRuleEdges() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEdges = null;


        try {
            // InternalHenshin_text.g:877:46: (iv_ruleEdges= ruleEdges EOF )
            // InternalHenshin_text.g:878:2: iv_ruleEdges= ruleEdges EOF
            {
             newCompositeNode(grammarAccess.getEdgesRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEdges=ruleEdges();

            state._fsp--;

             current =iv_ruleEdges; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEdges"


    // $ANTLR start "ruleEdges"
    // InternalHenshin_text.g:884:1: ruleEdges returns [EObject current=null] : (otherlv_0= 'edges' otherlv_1= '[' ( (lv_edges_2_0= ruleEdge ) ) (otherlv_3= ',' ( (lv_edges_4_0= ruleEdge ) ) )* otherlv_5= ']' ) ;
    public final EObject ruleEdges() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_edges_2_0 = null;

        EObject lv_edges_4_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:890:2: ( (otherlv_0= 'edges' otherlv_1= '[' ( (lv_edges_2_0= ruleEdge ) ) (otherlv_3= ',' ( (lv_edges_4_0= ruleEdge ) ) )* otherlv_5= ']' ) )
            // InternalHenshin_text.g:891:2: (otherlv_0= 'edges' otherlv_1= '[' ( (lv_edges_2_0= ruleEdge ) ) (otherlv_3= ',' ( (lv_edges_4_0= ruleEdge ) ) )* otherlv_5= ']' )
            {
            // InternalHenshin_text.g:891:2: (otherlv_0= 'edges' otherlv_1= '[' ( (lv_edges_2_0= ruleEdge ) ) (otherlv_3= ',' ( (lv_edges_4_0= ruleEdge ) ) )* otherlv_5= ']' )
            // InternalHenshin_text.g:892:3: otherlv_0= 'edges' otherlv_1= '[' ( (lv_edges_2_0= ruleEdge ) ) (otherlv_3= ',' ( (lv_edges_4_0= ruleEdge ) ) )* otherlv_5= ']'
            {
            otherlv_0=(Token)match(input,34,FOLLOW_17); 

            			newLeafNode(otherlv_0, grammarAccess.getEdgesAccess().getEdgesKeyword_0());
            		
            otherlv_1=(Token)match(input,26,FOLLOW_7); 

            			newLeafNode(otherlv_1, grammarAccess.getEdgesAccess().getLeftSquareBracketKeyword_1());
            		
            // InternalHenshin_text.g:900:3: ( (lv_edges_2_0= ruleEdge ) )
            // InternalHenshin_text.g:901:4: (lv_edges_2_0= ruleEdge )
            {
            // InternalHenshin_text.g:901:4: (lv_edges_2_0= ruleEdge )
            // InternalHenshin_text.g:902:5: lv_edges_2_0= ruleEdge
            {

            					newCompositeNode(grammarAccess.getEdgesAccess().getEdgesEdgeParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_19);
            lv_edges_2_0=ruleEdge();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getEdgesRule());
            					}
            					add(
            						current,
            						"edges",
            						lv_edges_2_0,
            						"org.eclipse.emf.henshin.text.Henshin_text.Edge");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalHenshin_text.g:919:3: (otherlv_3= ',' ( (lv_edges_4_0= ruleEdge ) ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==17) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalHenshin_text.g:920:4: otherlv_3= ',' ( (lv_edges_4_0= ruleEdge ) )
            	    {
            	    otherlv_3=(Token)match(input,17,FOLLOW_7); 

            	    				newLeafNode(otherlv_3, grammarAccess.getEdgesAccess().getCommaKeyword_3_0());
            	    			
            	    // InternalHenshin_text.g:924:4: ( (lv_edges_4_0= ruleEdge ) )
            	    // InternalHenshin_text.g:925:5: (lv_edges_4_0= ruleEdge )
            	    {
            	    // InternalHenshin_text.g:925:5: (lv_edges_4_0= ruleEdge )
            	    // InternalHenshin_text.g:926:6: lv_edges_4_0= ruleEdge
            	    {

            	    						newCompositeNode(grammarAccess.getEdgesAccess().getEdgesEdgeParserRuleCall_3_1_0());
            	    					
            	    pushFollow(FOLLOW_19);
            	    lv_edges_4_0=ruleEdge();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getEdgesRule());
            	    						}
            	    						add(
            	    							current,
            	    							"edges",
            	    							lv_edges_4_0,
            	    							"org.eclipse.emf.henshin.text.Henshin_text.Edge");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

            otherlv_5=(Token)match(input,27,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getEdgesAccess().getRightSquareBracketKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEdges"


    // $ANTLR start "entryRuleEdge"
    // InternalHenshin_text.g:952:1: entryRuleEdge returns [EObject current=null] : iv_ruleEdge= ruleEdge EOF ;
    public final EObject entryRuleEdge() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEdge = null;


        try {
            // InternalHenshin_text.g:952:45: (iv_ruleEdge= ruleEdge EOF )
            // InternalHenshin_text.g:953:2: iv_ruleEdge= ruleEdge EOF
            {
             newCompositeNode(grammarAccess.getEdgeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEdge=ruleEdge();

            state._fsp--;

             current =iv_ruleEdge; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEdge"


    // $ANTLR start "ruleEdge"
    // InternalHenshin_text.g:959:1: ruleEdge returns [EObject current=null] : (otherlv_0= '(' ( (lv_actiontype_1_0= ruleActionType ) )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '->' ( (otherlv_4= RULE_ID ) ) otherlv_5= ':' ( ( ruleEString ) ) otherlv_7= ')' ) ;
    public final EObject ruleEdge() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_actiontype_1_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:965:2: ( (otherlv_0= '(' ( (lv_actiontype_1_0= ruleActionType ) )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '->' ( (otherlv_4= RULE_ID ) ) otherlv_5= ':' ( ( ruleEString ) ) otherlv_7= ')' ) )
            // InternalHenshin_text.g:966:2: (otherlv_0= '(' ( (lv_actiontype_1_0= ruleActionType ) )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '->' ( (otherlv_4= RULE_ID ) ) otherlv_5= ':' ( ( ruleEString ) ) otherlv_7= ')' )
            {
            // InternalHenshin_text.g:966:2: (otherlv_0= '(' ( (lv_actiontype_1_0= ruleActionType ) )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '->' ( (otherlv_4= RULE_ID ) ) otherlv_5= ':' ( ( ruleEString ) ) otherlv_7= ')' )
            // InternalHenshin_text.g:967:3: otherlv_0= '(' ( (lv_actiontype_1_0= ruleActionType ) )? ( (otherlv_2= RULE_ID ) ) otherlv_3= '->' ( (otherlv_4= RULE_ID ) ) otherlv_5= ':' ( ( ruleEString ) ) otherlv_7= ')'
            {
            otherlv_0=(Token)match(input,16,FOLLOW_21); 

            			newLeafNode(otherlv_0, grammarAccess.getEdgeAccess().getLeftParenthesisKeyword_0());
            		
            // InternalHenshin_text.g:971:3: ( (lv_actiontype_1_0= ruleActionType ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( ((LA17_0>=29 && LA17_0<=33)) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalHenshin_text.g:972:4: (lv_actiontype_1_0= ruleActionType )
                    {
                    // InternalHenshin_text.g:972:4: (lv_actiontype_1_0= ruleActionType )
                    // InternalHenshin_text.g:973:5: lv_actiontype_1_0= ruleActionType
                    {

                    					newCompositeNode(grammarAccess.getEdgeAccess().getActiontypeActionTypeParserRuleCall_1_0());
                    				
                    pushFollow(FOLLOW_5);
                    lv_actiontype_1_0=ruleActionType();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getEdgeRule());
                    					}
                    					set(
                    						current,
                    						"actiontype",
                    						lv_actiontype_1_0,
                    						"org.eclipse.emf.henshin.text.Henshin_text.ActionType");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalHenshin_text.g:990:3: ( (otherlv_2= RULE_ID ) )
            // InternalHenshin_text.g:991:4: (otherlv_2= RULE_ID )
            {
            // InternalHenshin_text.g:991:4: (otherlv_2= RULE_ID )
            // InternalHenshin_text.g:992:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEdgeRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_22); 

            					newLeafNode(otherlv_2, grammarAccess.getEdgeAccess().getSourceRuleNodeTypesCrossReference_2_0());
            				

            }


            }

            otherlv_3=(Token)match(input,35,FOLLOW_5); 

            			newLeafNode(otherlv_3, grammarAccess.getEdgeAccess().getHyphenMinusGreaterThanSignKeyword_3());
            		
            // InternalHenshin_text.g:1007:3: ( (otherlv_4= RULE_ID ) )
            // InternalHenshin_text.g:1008:4: (otherlv_4= RULE_ID )
            {
            // InternalHenshin_text.g:1008:4: (otherlv_4= RULE_ID )
            // InternalHenshin_text.g:1009:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEdgeRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_23); 

            					newLeafNode(otherlv_4, grammarAccess.getEdgeAccess().getTargetRuleNodeTypesCrossReference_4_0());
            				

            }


            }

            otherlv_5=(Token)match(input,36,FOLLOW_5); 

            			newLeafNode(otherlv_5, grammarAccess.getEdgeAccess().getColonKeyword_5());
            		
            // InternalHenshin_text.g:1024:3: ( ( ruleEString ) )
            // InternalHenshin_text.g:1025:4: ( ruleEString )
            {
            // InternalHenshin_text.g:1025:4: ( ruleEString )
            // InternalHenshin_text.g:1026:5: ruleEString
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEdgeRule());
            					}
            				

            					newCompositeNode(grammarAccess.getEdgeAccess().getTypeEReferenceCrossReference_6_0());
            				
            pushFollow(FOLLOW_24);
            ruleEString();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_7=(Token)match(input,18,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getEdgeAccess().getRightParenthesisKeyword_7());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEdge"


    // $ANTLR start "entryRuleNode"
    // InternalHenshin_text.g:1048:1: entryRuleNode returns [EObject current=null] : iv_ruleNode= ruleNode EOF ;
    public final EObject entryRuleNode() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNode = null;


        try {
            // InternalHenshin_text.g:1048:45: (iv_ruleNode= ruleNode EOF )
            // InternalHenshin_text.g:1049:2: iv_ruleNode= ruleNode EOF
            {
             newCompositeNode(grammarAccess.getNodeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleNode=ruleNode();

            state._fsp--;

             current =iv_ruleNode; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNode"


    // $ANTLR start "ruleNode"
    // InternalHenshin_text.g:1055:1: ruleNode returns [EObject current=null] : ( ( (lv_actiontype_0_0= ruleActionType ) )? otherlv_1= 'node' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( ( ruleEString ) ) (otherlv_5= '{' ( (lv_attribute_6_0= ruleAttribute ) )* otherlv_7= '}' )? ) ;
    public final EObject ruleNode() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_actiontype_0_0 = null;

        EObject lv_attribute_6_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:1061:2: ( ( ( (lv_actiontype_0_0= ruleActionType ) )? otherlv_1= 'node' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( ( ruleEString ) ) (otherlv_5= '{' ( (lv_attribute_6_0= ruleAttribute ) )* otherlv_7= '}' )? ) )
            // InternalHenshin_text.g:1062:2: ( ( (lv_actiontype_0_0= ruleActionType ) )? otherlv_1= 'node' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( ( ruleEString ) ) (otherlv_5= '{' ( (lv_attribute_6_0= ruleAttribute ) )* otherlv_7= '}' )? )
            {
            // InternalHenshin_text.g:1062:2: ( ( (lv_actiontype_0_0= ruleActionType ) )? otherlv_1= 'node' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( ( ruleEString ) ) (otherlv_5= '{' ( (lv_attribute_6_0= ruleAttribute ) )* otherlv_7= '}' )? )
            // InternalHenshin_text.g:1063:3: ( (lv_actiontype_0_0= ruleActionType ) )? otherlv_1= 'node' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ':' ( ( ruleEString ) ) (otherlv_5= '{' ( (lv_attribute_6_0= ruleAttribute ) )* otherlv_7= '}' )?
            {
            // InternalHenshin_text.g:1063:3: ( (lv_actiontype_0_0= ruleActionType ) )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( ((LA18_0>=29 && LA18_0<=33)) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalHenshin_text.g:1064:4: (lv_actiontype_0_0= ruleActionType )
                    {
                    // InternalHenshin_text.g:1064:4: (lv_actiontype_0_0= ruleActionType )
                    // InternalHenshin_text.g:1065:5: lv_actiontype_0_0= ruleActionType
                    {

                    					newCompositeNode(grammarAccess.getNodeAccess().getActiontypeActionTypeParserRuleCall_0_0());
                    				
                    pushFollow(FOLLOW_25);
                    lv_actiontype_0_0=ruleActionType();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getNodeRule());
                    					}
                    					set(
                    						current,
                    						"actiontype",
                    						lv_actiontype_0_0,
                    						"org.eclipse.emf.henshin.text.Henshin_text.ActionType");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            otherlv_1=(Token)match(input,37,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getNodeAccess().getNodeKeyword_1());
            		
            // InternalHenshin_text.g:1086:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalHenshin_text.g:1087:4: (lv_name_2_0= RULE_ID )
            {
            // InternalHenshin_text.g:1087:4: (lv_name_2_0= RULE_ID )
            // InternalHenshin_text.g:1088:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_23); 

            					newLeafNode(lv_name_2_0, grammarAccess.getNodeAccess().getNameIDTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getNodeRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_2_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_3=(Token)match(input,36,FOLLOW_5); 

            			newLeafNode(otherlv_3, grammarAccess.getNodeAccess().getColonKeyword_3());
            		
            // InternalHenshin_text.g:1108:3: ( ( ruleEString ) )
            // InternalHenshin_text.g:1109:4: ( ruleEString )
            {
            // InternalHenshin_text.g:1109:4: ( ruleEString )
            // InternalHenshin_text.g:1110:5: ruleEString
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getNodeRule());
            					}
            				

            					newCompositeNode(grammarAccess.getNodeAccess().getNodetypeEClassCrossReference_4_0());
            				
            pushFollow(FOLLOW_26);
            ruleEString();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalHenshin_text.g:1124:3: (otherlv_5= '{' ( (lv_attribute_6_0= ruleAttribute ) )* otherlv_7= '}' )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==19) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalHenshin_text.g:1125:4: otherlv_5= '{' ( (lv_attribute_6_0= ruleAttribute ) )* otherlv_7= '}'
                    {
                    otherlv_5=(Token)match(input,19,FOLLOW_27); 

                    				newLeafNode(otherlv_5, grammarAccess.getNodeAccess().getLeftCurlyBracketKeyword_5_0());
                    			
                    // InternalHenshin_text.g:1129:4: ( (lv_attribute_6_0= ruleAttribute ) )*
                    loop19:
                    do {
                        int alt19=2;
                        int LA19_0 = input.LA(1);

                        if ( (LA19_0==RULE_ID||(LA19_0>=29 && LA19_0<=33)||LA19_0==40) ) {
                            alt19=1;
                        }


                        switch (alt19) {
                    	case 1 :
                    	    // InternalHenshin_text.g:1130:5: (lv_attribute_6_0= ruleAttribute )
                    	    {
                    	    // InternalHenshin_text.g:1130:5: (lv_attribute_6_0= ruleAttribute )
                    	    // InternalHenshin_text.g:1131:6: lv_attribute_6_0= ruleAttribute
                    	    {

                    	    						newCompositeNode(grammarAccess.getNodeAccess().getAttributeAttributeParserRuleCall_5_1_0());
                    	    					
                    	    pushFollow(FOLLOW_27);
                    	    lv_attribute_6_0=ruleAttribute();

                    	    state._fsp--;


                    	    						if (current==null) {
                    	    							current = createModelElementForParent(grammarAccess.getNodeRule());
                    	    						}
                    	    						add(
                    	    							current,
                    	    							"attribute",
                    	    							lv_attribute_6_0,
                    	    							"org.eclipse.emf.henshin.text.Henshin_text.Attribute");
                    	    						afterParserOrEnumRuleCall();
                    	    					

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop19;
                        }
                    } while (true);

                    otherlv_7=(Token)match(input,20,FOLLOW_2); 

                    				newLeafNode(otherlv_7, grammarAccess.getNodeAccess().getRightCurlyBracketKeyword_5_2());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNode"


    // $ANTLR start "entryRuleMultiRuleReuseNode"
    // InternalHenshin_text.g:1157:1: entryRuleMultiRuleReuseNode returns [EObject current=null] : iv_ruleMultiRuleReuseNode= ruleMultiRuleReuseNode EOF ;
    public final EObject entryRuleMultiRuleReuseNode() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiRuleReuseNode = null;


        try {
            // InternalHenshin_text.g:1157:59: (iv_ruleMultiRuleReuseNode= ruleMultiRuleReuseNode EOF )
            // InternalHenshin_text.g:1158:2: iv_ruleMultiRuleReuseNode= ruleMultiRuleReuseNode EOF
            {
             newCompositeNode(grammarAccess.getMultiRuleReuseNodeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMultiRuleReuseNode=ruleMultiRuleReuseNode();

            state._fsp--;

             current =iv_ruleMultiRuleReuseNode; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMultiRuleReuseNode"


    // $ANTLR start "ruleMultiRuleReuseNode"
    // InternalHenshin_text.g:1164:1: ruleMultiRuleReuseNode returns [EObject current=null] : (otherlv_0= 'reuse' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '{' ( (lv_attribute_3_0= ruleAttribute ) )* otherlv_4= '}' )? ) ;
    public final EObject ruleMultiRuleReuseNode() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_attribute_3_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:1170:2: ( (otherlv_0= 'reuse' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '{' ( (lv_attribute_3_0= ruleAttribute ) )* otherlv_4= '}' )? ) )
            // InternalHenshin_text.g:1171:2: (otherlv_0= 'reuse' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '{' ( (lv_attribute_3_0= ruleAttribute ) )* otherlv_4= '}' )? )
            {
            // InternalHenshin_text.g:1171:2: (otherlv_0= 'reuse' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '{' ( (lv_attribute_3_0= ruleAttribute ) )* otherlv_4= '}' )? )
            // InternalHenshin_text.g:1172:3: otherlv_0= 'reuse' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '{' ( (lv_attribute_3_0= ruleAttribute ) )* otherlv_4= '}' )?
            {
            otherlv_0=(Token)match(input,38,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getMultiRuleReuseNodeAccess().getReuseKeyword_0());
            		
            // InternalHenshin_text.g:1176:3: ( (otherlv_1= RULE_ID ) )
            // InternalHenshin_text.g:1177:4: (otherlv_1= RULE_ID )
            {
            // InternalHenshin_text.g:1177:4: (otherlv_1= RULE_ID )
            // InternalHenshin_text.g:1178:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMultiRuleReuseNodeRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_26); 

            					newLeafNode(otherlv_1, grammarAccess.getMultiRuleReuseNodeAccess().getNameNodeCrossReference_1_0());
            				

            }


            }

            // InternalHenshin_text.g:1189:3: (otherlv_2= '{' ( (lv_attribute_3_0= ruleAttribute ) )* otherlv_4= '}' )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==19) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalHenshin_text.g:1190:4: otherlv_2= '{' ( (lv_attribute_3_0= ruleAttribute ) )* otherlv_4= '}'
                    {
                    otherlv_2=(Token)match(input,19,FOLLOW_27); 

                    				newLeafNode(otherlv_2, grammarAccess.getMultiRuleReuseNodeAccess().getLeftCurlyBracketKeyword_2_0());
                    			
                    // InternalHenshin_text.g:1194:4: ( (lv_attribute_3_0= ruleAttribute ) )*
                    loop21:
                    do {
                        int alt21=2;
                        int LA21_0 = input.LA(1);

                        if ( (LA21_0==RULE_ID||(LA21_0>=29 && LA21_0<=33)||LA21_0==40) ) {
                            alt21=1;
                        }


                        switch (alt21) {
                    	case 1 :
                    	    // InternalHenshin_text.g:1195:5: (lv_attribute_3_0= ruleAttribute )
                    	    {
                    	    // InternalHenshin_text.g:1195:5: (lv_attribute_3_0= ruleAttribute )
                    	    // InternalHenshin_text.g:1196:6: lv_attribute_3_0= ruleAttribute
                    	    {

                    	    						newCompositeNode(grammarAccess.getMultiRuleReuseNodeAccess().getAttributeAttributeParserRuleCall_2_1_0());
                    	    					
                    	    pushFollow(FOLLOW_27);
                    	    lv_attribute_3_0=ruleAttribute();

                    	    state._fsp--;


                    	    						if (current==null) {
                    	    							current = createModelElementForParent(grammarAccess.getMultiRuleReuseNodeRule());
                    	    						}
                    	    						add(
                    	    							current,
                    	    							"attribute",
                    	    							lv_attribute_3_0,
                    	    							"org.eclipse.emf.henshin.text.Henshin_text.Attribute");
                    	    						afterParserOrEnumRuleCall();
                    	    					

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop21;
                        }
                    } while (true);

                    otherlv_4=(Token)match(input,20,FOLLOW_2); 

                    				newLeafNode(otherlv_4, grammarAccess.getMultiRuleReuseNodeAccess().getRightCurlyBracketKeyword_2_2());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMultiRuleReuseNode"


    // $ANTLR start "entryRuleAttribute"
    // InternalHenshin_text.g:1222:1: entryRuleAttribute returns [EObject current=null] : iv_ruleAttribute= ruleAttribute EOF ;
    public final EObject entryRuleAttribute() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAttribute = null;


        try {
            // InternalHenshin_text.g:1222:50: (iv_ruleAttribute= ruleAttribute EOF )
            // InternalHenshin_text.g:1223:2: iv_ruleAttribute= ruleAttribute EOF
            {
             newCompositeNode(grammarAccess.getAttributeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAttribute=ruleAttribute();

            state._fsp--;

             current =iv_ruleAttribute; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAttribute"


    // $ANTLR start "ruleAttribute"
    // InternalHenshin_text.g:1229:1: ruleAttribute returns [EObject current=null] : ( ( ( (lv_actiontype_0_0= ruleActionType ) )? ( ( ruleEString ) ) otherlv_2= '=' ( (lv_value_3_0= ruleExpression ) ) ) | ( ( (lv_update_4_0= 'set' ) ) ( ( ruleEString ) ) otherlv_6= '=' ( (lv_value_7_0= ruleExpression ) ) ) ) ;
    public final EObject ruleAttribute() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token lv_update_4_0=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_actiontype_0_0 = null;

        EObject lv_value_3_0 = null;

        EObject lv_value_7_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:1235:2: ( ( ( ( (lv_actiontype_0_0= ruleActionType ) )? ( ( ruleEString ) ) otherlv_2= '=' ( (lv_value_3_0= ruleExpression ) ) ) | ( ( (lv_update_4_0= 'set' ) ) ( ( ruleEString ) ) otherlv_6= '=' ( (lv_value_7_0= ruleExpression ) ) ) ) )
            // InternalHenshin_text.g:1236:2: ( ( ( (lv_actiontype_0_0= ruleActionType ) )? ( ( ruleEString ) ) otherlv_2= '=' ( (lv_value_3_0= ruleExpression ) ) ) | ( ( (lv_update_4_0= 'set' ) ) ( ( ruleEString ) ) otherlv_6= '=' ( (lv_value_7_0= ruleExpression ) ) ) )
            {
            // InternalHenshin_text.g:1236:2: ( ( ( (lv_actiontype_0_0= ruleActionType ) )? ( ( ruleEString ) ) otherlv_2= '=' ( (lv_value_3_0= ruleExpression ) ) ) | ( ( (lv_update_4_0= 'set' ) ) ( ( ruleEString ) ) otherlv_6= '=' ( (lv_value_7_0= ruleExpression ) ) ) )
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==RULE_ID||(LA24_0>=29 && LA24_0<=33)) ) {
                alt24=1;
            }
            else if ( (LA24_0==40) ) {
                alt24=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }
            switch (alt24) {
                case 1 :
                    // InternalHenshin_text.g:1237:3: ( ( (lv_actiontype_0_0= ruleActionType ) )? ( ( ruleEString ) ) otherlv_2= '=' ( (lv_value_3_0= ruleExpression ) ) )
                    {
                    // InternalHenshin_text.g:1237:3: ( ( (lv_actiontype_0_0= ruleActionType ) )? ( ( ruleEString ) ) otherlv_2= '=' ( (lv_value_3_0= ruleExpression ) ) )
                    // InternalHenshin_text.g:1238:4: ( (lv_actiontype_0_0= ruleActionType ) )? ( ( ruleEString ) ) otherlv_2= '=' ( (lv_value_3_0= ruleExpression ) )
                    {
                    // InternalHenshin_text.g:1238:4: ( (lv_actiontype_0_0= ruleActionType ) )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( ((LA23_0>=29 && LA23_0<=33)) ) {
                        alt23=1;
                    }
                    switch (alt23) {
                        case 1 :
                            // InternalHenshin_text.g:1239:5: (lv_actiontype_0_0= ruleActionType )
                            {
                            // InternalHenshin_text.g:1239:5: (lv_actiontype_0_0= ruleActionType )
                            // InternalHenshin_text.g:1240:6: lv_actiontype_0_0= ruleActionType
                            {

                            						newCompositeNode(grammarAccess.getAttributeAccess().getActiontypeActionTypeParserRuleCall_0_0_0());
                            					
                            pushFollow(FOLLOW_5);
                            lv_actiontype_0_0=ruleActionType();

                            state._fsp--;


                            						if (current==null) {
                            							current = createModelElementForParent(grammarAccess.getAttributeRule());
                            						}
                            						set(
                            							current,
                            							"actiontype",
                            							lv_actiontype_0_0,
                            							"org.eclipse.emf.henshin.text.Henshin_text.ActionType");
                            						afterParserOrEnumRuleCall();
                            					

                            }


                            }
                            break;

                    }

                    // InternalHenshin_text.g:1257:4: ( ( ruleEString ) )
                    // InternalHenshin_text.g:1258:5: ( ruleEString )
                    {
                    // InternalHenshin_text.g:1258:5: ( ruleEString )
                    // InternalHenshin_text.g:1259:6: ruleEString
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getAttributeRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getAttributeAccess().getNameEAttributeCrossReference_0_1_0());
                    					
                    pushFollow(FOLLOW_28);
                    ruleEString();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_2=(Token)match(input,39,FOLLOW_18); 

                    				newLeafNode(otherlv_2, grammarAccess.getAttributeAccess().getEqualsSignKeyword_0_2());
                    			
                    // InternalHenshin_text.g:1277:4: ( (lv_value_3_0= ruleExpression ) )
                    // InternalHenshin_text.g:1278:5: (lv_value_3_0= ruleExpression )
                    {
                    // InternalHenshin_text.g:1278:5: (lv_value_3_0= ruleExpression )
                    // InternalHenshin_text.g:1279:6: lv_value_3_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getAttributeAccess().getValueExpressionParserRuleCall_0_3_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_value_3_0=ruleExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getAttributeRule());
                    						}
                    						set(
                    							current,
                    							"value",
                    							lv_value_3_0,
                    							"org.eclipse.emf.henshin.text.Henshin_text.Expression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalHenshin_text.g:1298:3: ( ( (lv_update_4_0= 'set' ) ) ( ( ruleEString ) ) otherlv_6= '=' ( (lv_value_7_0= ruleExpression ) ) )
                    {
                    // InternalHenshin_text.g:1298:3: ( ( (lv_update_4_0= 'set' ) ) ( ( ruleEString ) ) otherlv_6= '=' ( (lv_value_7_0= ruleExpression ) ) )
                    // InternalHenshin_text.g:1299:4: ( (lv_update_4_0= 'set' ) ) ( ( ruleEString ) ) otherlv_6= '=' ( (lv_value_7_0= ruleExpression ) )
                    {
                    // InternalHenshin_text.g:1299:4: ( (lv_update_4_0= 'set' ) )
                    // InternalHenshin_text.g:1300:5: (lv_update_4_0= 'set' )
                    {
                    // InternalHenshin_text.g:1300:5: (lv_update_4_0= 'set' )
                    // InternalHenshin_text.g:1301:6: lv_update_4_0= 'set'
                    {
                    lv_update_4_0=(Token)match(input,40,FOLLOW_5); 

                    						newLeafNode(lv_update_4_0, grammarAccess.getAttributeAccess().getUpdateSetKeyword_1_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getAttributeRule());
                    						}
                    						setWithLastConsumed(current, "update", lv_update_4_0, "set");
                    					

                    }


                    }

                    // InternalHenshin_text.g:1313:4: ( ( ruleEString ) )
                    // InternalHenshin_text.g:1314:5: ( ruleEString )
                    {
                    // InternalHenshin_text.g:1314:5: ( ruleEString )
                    // InternalHenshin_text.g:1315:6: ruleEString
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getAttributeRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getAttributeAccess().getNameEAttributeCrossReference_1_1_0());
                    					
                    pushFollow(FOLLOW_28);
                    ruleEString();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_6=(Token)match(input,39,FOLLOW_18); 

                    				newLeafNode(otherlv_6, grammarAccess.getAttributeAccess().getEqualsSignKeyword_1_2());
                    			
                    // InternalHenshin_text.g:1333:4: ( (lv_value_7_0= ruleExpression ) )
                    // InternalHenshin_text.g:1334:5: (lv_value_7_0= ruleExpression )
                    {
                    // InternalHenshin_text.g:1334:5: (lv_value_7_0= ruleExpression )
                    // InternalHenshin_text.g:1335:6: lv_value_7_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getAttributeAccess().getValueExpressionParserRuleCall_1_3_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_value_7_0=ruleExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getAttributeRule());
                    						}
                    						set(
                    							current,
                    							"value",
                    							lv_value_7_0,
                    							"org.eclipse.emf.henshin.text.Henshin_text.Expression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAttribute"


    // $ANTLR start "entryRuleMultiRule"
    // InternalHenshin_text.g:1357:1: entryRuleMultiRule returns [EObject current=null] : iv_ruleMultiRule= ruleMultiRule EOF ;
    public final EObject entryRuleMultiRule() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiRule = null;


        try {
            // InternalHenshin_text.g:1357:50: (iv_ruleMultiRule= ruleMultiRule EOF )
            // InternalHenshin_text.g:1358:2: iv_ruleMultiRule= ruleMultiRule EOF
            {
             newCompositeNode(grammarAccess.getMultiRuleRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMultiRule=ruleMultiRule();

            state._fsp--;

             current =iv_ruleMultiRule; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMultiRule"


    // $ANTLR start "ruleMultiRule"
    // InternalHenshin_text.g:1364:1: ruleMultiRule returns [EObject current=null] : (otherlv_0= 'multiRule' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_multiruleElements_3_0= ruleRuleElement ) )+ otherlv_4= '}' ) ;
    public final EObject ruleMultiRule() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_multiruleElements_3_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:1370:2: ( (otherlv_0= 'multiRule' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_multiruleElements_3_0= ruleRuleElement ) )+ otherlv_4= '}' ) )
            // InternalHenshin_text.g:1371:2: (otherlv_0= 'multiRule' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_multiruleElements_3_0= ruleRuleElement ) )+ otherlv_4= '}' )
            {
            // InternalHenshin_text.g:1371:2: (otherlv_0= 'multiRule' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_multiruleElements_3_0= ruleRuleElement ) )+ otherlv_4= '}' )
            // InternalHenshin_text.g:1372:3: otherlv_0= 'multiRule' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_multiruleElements_3_0= ruleRuleElement ) )+ otherlv_4= '}'
            {
            otherlv_0=(Token)match(input,41,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getMultiRuleAccess().getMultiRuleKeyword_0());
            		
            // InternalHenshin_text.g:1376:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalHenshin_text.g:1377:4: (lv_name_1_0= RULE_ID )
            {
            // InternalHenshin_text.g:1377:4: (lv_name_1_0= RULE_ID )
            // InternalHenshin_text.g:1378:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_11); 

            					newLeafNode(lv_name_1_0, grammarAccess.getMultiRuleAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMultiRuleRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,19,FOLLOW_12); 

            			newLeafNode(otherlv_2, grammarAccess.getMultiRuleAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalHenshin_text.g:1398:3: ( (lv_multiruleElements_3_0= ruleRuleElement ) )+
            int cnt25=0;
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( ((LA25_0>=22 && LA25_0<=25)||LA25_0==28) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // InternalHenshin_text.g:1399:4: (lv_multiruleElements_3_0= ruleRuleElement )
            	    {
            	    // InternalHenshin_text.g:1399:4: (lv_multiruleElements_3_0= ruleRuleElement )
            	    // InternalHenshin_text.g:1400:5: lv_multiruleElements_3_0= ruleRuleElement
            	    {

            	    					newCompositeNode(grammarAccess.getMultiRuleAccess().getMultiruleElementsRuleElementParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_13);
            	    lv_multiruleElements_3_0=ruleRuleElement();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getMultiRuleRule());
            	    					}
            	    					add(
            	    						current,
            	    						"multiruleElements",
            	    						lv_multiruleElements_3_0,
            	    						"org.eclipse.emf.henshin.text.Henshin_text.RuleElement");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt25 >= 1 ) break loop25;
                        EarlyExitException eee =
                            new EarlyExitException(25, input);
                        throw eee;
                }
                cnt25++;
            } while (true);

            otherlv_4=(Token)match(input,20,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getMultiRuleAccess().getRightCurlyBracketKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMultiRule"


    // $ANTLR start "entryRuleFormula"
    // InternalHenshin_text.g:1425:1: entryRuleFormula returns [EObject current=null] : iv_ruleFormula= ruleFormula EOF ;
    public final EObject entryRuleFormula() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFormula = null;


        try {
            // InternalHenshin_text.g:1425:48: (iv_ruleFormula= ruleFormula EOF )
            // InternalHenshin_text.g:1426:2: iv_ruleFormula= ruleFormula EOF
            {
             newCompositeNode(grammarAccess.getFormulaRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFormula=ruleFormula();

            state._fsp--;

             current =iv_ruleFormula; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFormula"


    // $ANTLR start "ruleFormula"
    // InternalHenshin_text.g:1432:1: ruleFormula returns [EObject current=null] : (otherlv_0= 'matchingFormula' otherlv_1= '{' otherlv_2= 'formula' ( (lv_formula_3_0= ruleLogic ) ) ( (lv_conditionGraphs_4_0= ruleConditionGraph ) )+ otherlv_5= '}' ) ;
    public final EObject ruleFormula() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject lv_formula_3_0 = null;

        EObject lv_conditionGraphs_4_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:1438:2: ( (otherlv_0= 'matchingFormula' otherlv_1= '{' otherlv_2= 'formula' ( (lv_formula_3_0= ruleLogic ) ) ( (lv_conditionGraphs_4_0= ruleConditionGraph ) )+ otherlv_5= '}' ) )
            // InternalHenshin_text.g:1439:2: (otherlv_0= 'matchingFormula' otherlv_1= '{' otherlv_2= 'formula' ( (lv_formula_3_0= ruleLogic ) ) ( (lv_conditionGraphs_4_0= ruleConditionGraph ) )+ otherlv_5= '}' )
            {
            // InternalHenshin_text.g:1439:2: (otherlv_0= 'matchingFormula' otherlv_1= '{' otherlv_2= 'formula' ( (lv_formula_3_0= ruleLogic ) ) ( (lv_conditionGraphs_4_0= ruleConditionGraph ) )+ otherlv_5= '}' )
            // InternalHenshin_text.g:1440:3: otherlv_0= 'matchingFormula' otherlv_1= '{' otherlv_2= 'formula' ( (lv_formula_3_0= ruleLogic ) ) ( (lv_conditionGraphs_4_0= ruleConditionGraph ) )+ otherlv_5= '}'
            {
            otherlv_0=(Token)match(input,42,FOLLOW_11); 

            			newLeafNode(otherlv_0, grammarAccess.getFormulaAccess().getMatchingFormulaKeyword_0());
            		
            otherlv_1=(Token)match(input,19,FOLLOW_29); 

            			newLeafNode(otherlv_1, grammarAccess.getFormulaAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,43,FOLLOW_30); 

            			newLeafNode(otherlv_2, grammarAccess.getFormulaAccess().getFormulaKeyword_2());
            		
            // InternalHenshin_text.g:1452:3: ( (lv_formula_3_0= ruleLogic ) )
            // InternalHenshin_text.g:1453:4: (lv_formula_3_0= ruleLogic )
            {
            // InternalHenshin_text.g:1453:4: (lv_formula_3_0= ruleLogic )
            // InternalHenshin_text.g:1454:5: lv_formula_3_0= ruleLogic
            {

            					newCompositeNode(grammarAccess.getFormulaAccess().getFormulaLogicParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_31);
            lv_formula_3_0=ruleLogic();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getFormulaRule());
            					}
            					set(
            						current,
            						"formula",
            						lv_formula_3_0,
            						"org.eclipse.emf.henshin.text.Henshin_text.Logic");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalHenshin_text.g:1471:3: ( (lv_conditionGraphs_4_0= ruleConditionGraph ) )+
            int cnt26=0;
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==48) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // InternalHenshin_text.g:1472:4: (lv_conditionGraphs_4_0= ruleConditionGraph )
            	    {
            	    // InternalHenshin_text.g:1472:4: (lv_conditionGraphs_4_0= ruleConditionGraph )
            	    // InternalHenshin_text.g:1473:5: lv_conditionGraphs_4_0= ruleConditionGraph
            	    {

            	    					newCompositeNode(grammarAccess.getFormulaAccess().getConditionGraphsConditionGraphParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_32);
            	    lv_conditionGraphs_4_0=ruleConditionGraph();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getFormulaRule());
            	    					}
            	    					add(
            	    						current,
            	    						"conditionGraphs",
            	    						lv_conditionGraphs_4_0,
            	    						"org.eclipse.emf.henshin.text.Henshin_text.ConditionGraph");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt26 >= 1 ) break loop26;
                        EarlyExitException eee =
                            new EarlyExitException(26, input);
                        throw eee;
                }
                cnt26++;
            } while (true);

            otherlv_5=(Token)match(input,20,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getFormulaAccess().getRightCurlyBracketKeyword_5());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFormula"


    // $ANTLR start "entryRuleLogic"
    // InternalHenshin_text.g:1498:1: entryRuleLogic returns [EObject current=null] : iv_ruleLogic= ruleLogic EOF ;
    public final EObject entryRuleLogic() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLogic = null;


        try {
            // InternalHenshin_text.g:1498:46: (iv_ruleLogic= ruleLogic EOF )
            // InternalHenshin_text.g:1499:2: iv_ruleLogic= ruleLogic EOF
            {
             newCompositeNode(grammarAccess.getLogicRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLogic=ruleLogic();

            state._fsp--;

             current =iv_ruleLogic; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLogic"


    // $ANTLR start "ruleLogic"
    // InternalHenshin_text.g:1505:1: ruleLogic returns [EObject current=null] : this_ORorXOR_0= ruleORorXOR ;
    public final EObject ruleLogic() throws RecognitionException {
        EObject current = null;

        EObject this_ORorXOR_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:1511:2: (this_ORorXOR_0= ruleORorXOR )
            // InternalHenshin_text.g:1512:2: this_ORorXOR_0= ruleORorXOR
            {

            		newCompositeNode(grammarAccess.getLogicAccess().getORorXORParserRuleCall());
            	
            pushFollow(FOLLOW_2);
            this_ORorXOR_0=ruleORorXOR();

            state._fsp--;


            		current = this_ORorXOR_0;
            		afterParserOrEnumRuleCall();
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLogic"


    // $ANTLR start "entryRuleORorXOR"
    // InternalHenshin_text.g:1523:1: entryRuleORorXOR returns [EObject current=null] : iv_ruleORorXOR= ruleORorXOR EOF ;
    public final EObject entryRuleORorXOR() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleORorXOR = null;


        try {
            // InternalHenshin_text.g:1523:48: (iv_ruleORorXOR= ruleORorXOR EOF )
            // InternalHenshin_text.g:1524:2: iv_ruleORorXOR= ruleORorXOR EOF
            {
             newCompositeNode(grammarAccess.getORorXORRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleORorXOR=ruleORorXOR();

            state._fsp--;

             current =iv_ruleORorXOR; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleORorXOR"


    // $ANTLR start "ruleORorXOR"
    // InternalHenshin_text.g:1530:1: ruleORorXOR returns [EObject current=null] : (this_AND_0= ruleAND ( () ( ( (lv_op_2_1= 'OR' | lv_op_2_2= 'XOR' ) ) ) ( (lv_right_3_0= ruleAND ) ) )* ) ;
    public final EObject ruleORorXOR() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        EObject this_AND_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:1536:2: ( (this_AND_0= ruleAND ( () ( ( (lv_op_2_1= 'OR' | lv_op_2_2= 'XOR' ) ) ) ( (lv_right_3_0= ruleAND ) ) )* ) )
            // InternalHenshin_text.g:1537:2: (this_AND_0= ruleAND ( () ( ( (lv_op_2_1= 'OR' | lv_op_2_2= 'XOR' ) ) ) ( (lv_right_3_0= ruleAND ) ) )* )
            {
            // InternalHenshin_text.g:1537:2: (this_AND_0= ruleAND ( () ( ( (lv_op_2_1= 'OR' | lv_op_2_2= 'XOR' ) ) ) ( (lv_right_3_0= ruleAND ) ) )* )
            // InternalHenshin_text.g:1538:3: this_AND_0= ruleAND ( () ( ( (lv_op_2_1= 'OR' | lv_op_2_2= 'XOR' ) ) ) ( (lv_right_3_0= ruleAND ) ) )*
            {

            			newCompositeNode(grammarAccess.getORorXORAccess().getANDParserRuleCall_0());
            		
            pushFollow(FOLLOW_33);
            this_AND_0=ruleAND();

            state._fsp--;


            			current = this_AND_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalHenshin_text.g:1546:3: ( () ( ( (lv_op_2_1= 'OR' | lv_op_2_2= 'XOR' ) ) ) ( (lv_right_3_0= ruleAND ) ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( ((LA28_0>=44 && LA28_0<=45)) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // InternalHenshin_text.g:1547:4: () ( ( (lv_op_2_1= 'OR' | lv_op_2_2= 'XOR' ) ) ) ( (lv_right_3_0= ruleAND ) )
            	    {
            	    // InternalHenshin_text.g:1547:4: ()
            	    // InternalHenshin_text.g:1548:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getORorXORAccess().getORorXORLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalHenshin_text.g:1554:4: ( ( (lv_op_2_1= 'OR' | lv_op_2_2= 'XOR' ) ) )
            	    // InternalHenshin_text.g:1555:5: ( (lv_op_2_1= 'OR' | lv_op_2_2= 'XOR' ) )
            	    {
            	    // InternalHenshin_text.g:1555:5: ( (lv_op_2_1= 'OR' | lv_op_2_2= 'XOR' ) )
            	    // InternalHenshin_text.g:1556:6: (lv_op_2_1= 'OR' | lv_op_2_2= 'XOR' )
            	    {
            	    // InternalHenshin_text.g:1556:6: (lv_op_2_1= 'OR' | lv_op_2_2= 'XOR' )
            	    int alt27=2;
            	    int LA27_0 = input.LA(1);

            	    if ( (LA27_0==44) ) {
            	        alt27=1;
            	    }
            	    else if ( (LA27_0==45) ) {
            	        alt27=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 27, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt27) {
            	        case 1 :
            	            // InternalHenshin_text.g:1557:7: lv_op_2_1= 'OR'
            	            {
            	            lv_op_2_1=(Token)match(input,44,FOLLOW_30); 

            	            							newLeafNode(lv_op_2_1, grammarAccess.getORorXORAccess().getOpORKeyword_1_1_0_0());
            	            						

            	            							if (current==null) {
            	            								current = createModelElement(grammarAccess.getORorXORRule());
            	            							}
            	            							setWithLastConsumed(current, "op", lv_op_2_1, null);
            	            						

            	            }
            	            break;
            	        case 2 :
            	            // InternalHenshin_text.g:1568:7: lv_op_2_2= 'XOR'
            	            {
            	            lv_op_2_2=(Token)match(input,45,FOLLOW_30); 

            	            							newLeafNode(lv_op_2_2, grammarAccess.getORorXORAccess().getOpXORKeyword_1_1_0_1());
            	            						

            	            							if (current==null) {
            	            								current = createModelElement(grammarAccess.getORorXORRule());
            	            							}
            	            							setWithLastConsumed(current, "op", lv_op_2_2, null);
            	            						

            	            }
            	            break;

            	    }


            	    }


            	    }

            	    // InternalHenshin_text.g:1581:4: ( (lv_right_3_0= ruleAND ) )
            	    // InternalHenshin_text.g:1582:5: (lv_right_3_0= ruleAND )
            	    {
            	    // InternalHenshin_text.g:1582:5: (lv_right_3_0= ruleAND )
            	    // InternalHenshin_text.g:1583:6: lv_right_3_0= ruleAND
            	    {

            	    						newCompositeNode(grammarAccess.getORorXORAccess().getRightANDParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_33);
            	    lv_right_3_0=ruleAND();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getORorXORRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"org.eclipse.emf.henshin.text.Henshin_text.AND");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleORorXOR"


    // $ANTLR start "entryRuleAND"
    // InternalHenshin_text.g:1605:1: entryRuleAND returns [EObject current=null] : iv_ruleAND= ruleAND EOF ;
    public final EObject entryRuleAND() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAND = null;


        try {
            // InternalHenshin_text.g:1605:44: (iv_ruleAND= ruleAND EOF )
            // InternalHenshin_text.g:1606:2: iv_ruleAND= ruleAND EOF
            {
             newCompositeNode(grammarAccess.getANDRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAND=ruleAND();

            state._fsp--;

             current =iv_ruleAND; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAND"


    // $ANTLR start "ruleAND"
    // InternalHenshin_text.g:1612:1: ruleAND returns [EObject current=null] : (this_Primary_0= rulePrimary ( () otherlv_2= 'AND' ( (lv_right_3_0= rulePrimary ) ) )* ) ;
    public final EObject ruleAND() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_Primary_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:1618:2: ( (this_Primary_0= rulePrimary ( () otherlv_2= 'AND' ( (lv_right_3_0= rulePrimary ) ) )* ) )
            // InternalHenshin_text.g:1619:2: (this_Primary_0= rulePrimary ( () otherlv_2= 'AND' ( (lv_right_3_0= rulePrimary ) ) )* )
            {
            // InternalHenshin_text.g:1619:2: (this_Primary_0= rulePrimary ( () otherlv_2= 'AND' ( (lv_right_3_0= rulePrimary ) ) )* )
            // InternalHenshin_text.g:1620:3: this_Primary_0= rulePrimary ( () otherlv_2= 'AND' ( (lv_right_3_0= rulePrimary ) ) )*
            {

            			newCompositeNode(grammarAccess.getANDAccess().getPrimaryParserRuleCall_0());
            		
            pushFollow(FOLLOW_34);
            this_Primary_0=rulePrimary();

            state._fsp--;


            			current = this_Primary_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalHenshin_text.g:1628:3: ( () otherlv_2= 'AND' ( (lv_right_3_0= rulePrimary ) ) )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==46) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // InternalHenshin_text.g:1629:4: () otherlv_2= 'AND' ( (lv_right_3_0= rulePrimary ) )
            	    {
            	    // InternalHenshin_text.g:1629:4: ()
            	    // InternalHenshin_text.g:1630:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getANDAccess().getANDLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    otherlv_2=(Token)match(input,46,FOLLOW_30); 

            	    				newLeafNode(otherlv_2, grammarAccess.getANDAccess().getANDKeyword_1_1());
            	    			
            	    // InternalHenshin_text.g:1640:4: ( (lv_right_3_0= rulePrimary ) )
            	    // InternalHenshin_text.g:1641:5: (lv_right_3_0= rulePrimary )
            	    {
            	    // InternalHenshin_text.g:1641:5: (lv_right_3_0= rulePrimary )
            	    // InternalHenshin_text.g:1642:6: lv_right_3_0= rulePrimary
            	    {

            	    						newCompositeNode(grammarAccess.getANDAccess().getRightPrimaryParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_34);
            	    lv_right_3_0=rulePrimary();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getANDRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"org.eclipse.emf.henshin.text.Henshin_text.Primary");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop29;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAND"


    // $ANTLR start "entryRulePrimary"
    // InternalHenshin_text.g:1664:1: entryRulePrimary returns [EObject current=null] : iv_rulePrimary= rulePrimary EOF ;
    public final EObject entryRulePrimary() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimary = null;


        try {
            // InternalHenshin_text.g:1664:48: (iv_rulePrimary= rulePrimary EOF )
            // InternalHenshin_text.g:1665:2: iv_rulePrimary= rulePrimary EOF
            {
             newCompositeNode(grammarAccess.getPrimaryRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePrimary=rulePrimary();

            state._fsp--;

             current =iv_rulePrimary; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePrimary"


    // $ANTLR start "rulePrimary"
    // InternalHenshin_text.g:1671:1: rulePrimary returns [EObject current=null] : ( (otherlv_0= '(' this_Logic_1= ruleLogic otherlv_2= ')' ) | ( () otherlv_4= '!' ( (lv_negation_5_0= rulePrimary ) ) ) | this_Atomic_6= ruleAtomic ) ;
    public final EObject rulePrimary() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject this_Logic_1 = null;

        EObject lv_negation_5_0 = null;

        EObject this_Atomic_6 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:1677:2: ( ( (otherlv_0= '(' this_Logic_1= ruleLogic otherlv_2= ')' ) | ( () otherlv_4= '!' ( (lv_negation_5_0= rulePrimary ) ) ) | this_Atomic_6= ruleAtomic ) )
            // InternalHenshin_text.g:1678:2: ( (otherlv_0= '(' this_Logic_1= ruleLogic otherlv_2= ')' ) | ( () otherlv_4= '!' ( (lv_negation_5_0= rulePrimary ) ) ) | this_Atomic_6= ruleAtomic )
            {
            // InternalHenshin_text.g:1678:2: ( (otherlv_0= '(' this_Logic_1= ruleLogic otherlv_2= ')' ) | ( () otherlv_4= '!' ( (lv_negation_5_0= rulePrimary ) ) ) | this_Atomic_6= ruleAtomic )
            int alt30=3;
            switch ( input.LA(1) ) {
            case 16:
                {
                alt30=1;
                }
                break;
            case 47:
                {
                alt30=2;
                }
                break;
            case RULE_ID:
                {
                alt30=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }

            switch (alt30) {
                case 1 :
                    // InternalHenshin_text.g:1679:3: (otherlv_0= '(' this_Logic_1= ruleLogic otherlv_2= ')' )
                    {
                    // InternalHenshin_text.g:1679:3: (otherlv_0= '(' this_Logic_1= ruleLogic otherlv_2= ')' )
                    // InternalHenshin_text.g:1680:4: otherlv_0= '(' this_Logic_1= ruleLogic otherlv_2= ')'
                    {
                    otherlv_0=(Token)match(input,16,FOLLOW_30); 

                    				newLeafNode(otherlv_0, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_0_0());
                    			

                    				newCompositeNode(grammarAccess.getPrimaryAccess().getLogicParserRuleCall_0_1());
                    			
                    pushFollow(FOLLOW_24);
                    this_Logic_1=ruleLogic();

                    state._fsp--;


                    				current = this_Logic_1;
                    				afterParserOrEnumRuleCall();
                    			
                    otherlv_2=(Token)match(input,18,FOLLOW_2); 

                    				newLeafNode(otherlv_2, grammarAccess.getPrimaryAccess().getRightParenthesisKeyword_0_2());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalHenshin_text.g:1698:3: ( () otherlv_4= '!' ( (lv_negation_5_0= rulePrimary ) ) )
                    {
                    // InternalHenshin_text.g:1698:3: ( () otherlv_4= '!' ( (lv_negation_5_0= rulePrimary ) ) )
                    // InternalHenshin_text.g:1699:4: () otherlv_4= '!' ( (lv_negation_5_0= rulePrimary ) )
                    {
                    // InternalHenshin_text.g:1699:4: ()
                    // InternalHenshin_text.g:1700:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getPrimaryAccess().getNotAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_4=(Token)match(input,47,FOLLOW_30); 

                    				newLeafNode(otherlv_4, grammarAccess.getPrimaryAccess().getExclamationMarkKeyword_1_1());
                    			
                    // InternalHenshin_text.g:1710:4: ( (lv_negation_5_0= rulePrimary ) )
                    // InternalHenshin_text.g:1711:5: (lv_negation_5_0= rulePrimary )
                    {
                    // InternalHenshin_text.g:1711:5: (lv_negation_5_0= rulePrimary )
                    // InternalHenshin_text.g:1712:6: lv_negation_5_0= rulePrimary
                    {

                    						newCompositeNode(grammarAccess.getPrimaryAccess().getNegationPrimaryParserRuleCall_1_2_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_negation_5_0=rulePrimary();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPrimaryRule());
                    						}
                    						set(
                    							current,
                    							"negation",
                    							lv_negation_5_0,
                    							"org.eclipse.emf.henshin.text.Henshin_text.Primary");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalHenshin_text.g:1731:3: this_Atomic_6= ruleAtomic
                    {

                    			newCompositeNode(grammarAccess.getPrimaryAccess().getAtomicParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_Atomic_6=ruleAtomic();

                    state._fsp--;


                    			current = this_Atomic_6;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePrimary"


    // $ANTLR start "entryRuleAtomic"
    // InternalHenshin_text.g:1743:1: entryRuleAtomic returns [EObject current=null] : iv_ruleAtomic= ruleAtomic EOF ;
    public final EObject entryRuleAtomic() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAtomic = null;


        try {
            // InternalHenshin_text.g:1743:47: (iv_ruleAtomic= ruleAtomic EOF )
            // InternalHenshin_text.g:1744:2: iv_ruleAtomic= ruleAtomic EOF
            {
             newCompositeNode(grammarAccess.getAtomicRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAtomic=ruleAtomic();

            state._fsp--;

             current =iv_ruleAtomic; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAtomic"


    // $ANTLR start "ruleAtomic"
    // InternalHenshin_text.g:1750:1: ruleAtomic returns [EObject current=null] : ( () ( (otherlv_1= RULE_ID ) ) ) ;
    public final EObject ruleAtomic() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalHenshin_text.g:1756:2: ( ( () ( (otherlv_1= RULE_ID ) ) ) )
            // InternalHenshin_text.g:1757:2: ( () ( (otherlv_1= RULE_ID ) ) )
            {
            // InternalHenshin_text.g:1757:2: ( () ( (otherlv_1= RULE_ID ) ) )
            // InternalHenshin_text.g:1758:3: () ( (otherlv_1= RULE_ID ) )
            {
            // InternalHenshin_text.g:1758:3: ()
            // InternalHenshin_text.g:1759:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getAtomicAccess().getConditionGraphRefAction_0(),
            					current);
            			

            }

            // InternalHenshin_text.g:1765:3: ( (otherlv_1= RULE_ID ) )
            // InternalHenshin_text.g:1766:4: (otherlv_1= RULE_ID )
            {
            // InternalHenshin_text.g:1766:4: (otherlv_1= RULE_ID )
            // InternalHenshin_text.g:1767:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getAtomicRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_2); 

            					newLeafNode(otherlv_1, grammarAccess.getAtomicAccess().getConditionGraphRefConditionGraphCrossReference_1_0());
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAtomic"


    // $ANTLR start "entryRuleConditionGraph"
    // InternalHenshin_text.g:1782:1: entryRuleConditionGraph returns [EObject current=null] : iv_ruleConditionGraph= ruleConditionGraph EOF ;
    public final EObject entryRuleConditionGraph() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConditionGraph = null;


        try {
            // InternalHenshin_text.g:1782:55: (iv_ruleConditionGraph= ruleConditionGraph EOF )
            // InternalHenshin_text.g:1783:2: iv_ruleConditionGraph= ruleConditionGraph EOF
            {
             newCompositeNode(grammarAccess.getConditionGraphRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConditionGraph=ruleConditionGraph();

            state._fsp--;

             current =iv_ruleConditionGraph; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConditionGraph"


    // $ANTLR start "ruleConditionGraph"
    // InternalHenshin_text.g:1789:1: ruleConditionGraph returns [EObject current=null] : (otherlv_0= 'conditionGraph' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_conditionGraphElements_3_0= ruleConditionGraphElements ) )* otherlv_4= '}' ) ;
    public final EObject ruleConditionGraph() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_conditionGraphElements_3_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:1795:2: ( (otherlv_0= 'conditionGraph' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_conditionGraphElements_3_0= ruleConditionGraphElements ) )* otherlv_4= '}' ) )
            // InternalHenshin_text.g:1796:2: (otherlv_0= 'conditionGraph' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_conditionGraphElements_3_0= ruleConditionGraphElements ) )* otherlv_4= '}' )
            {
            // InternalHenshin_text.g:1796:2: (otherlv_0= 'conditionGraph' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_conditionGraphElements_3_0= ruleConditionGraphElements ) )* otherlv_4= '}' )
            // InternalHenshin_text.g:1797:3: otherlv_0= 'conditionGraph' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_conditionGraphElements_3_0= ruleConditionGraphElements ) )* otherlv_4= '}'
            {
            otherlv_0=(Token)match(input,48,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getConditionGraphAccess().getConditionGraphKeyword_0());
            		
            // InternalHenshin_text.g:1801:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalHenshin_text.g:1802:4: (lv_name_1_0= RULE_ID )
            {
            // InternalHenshin_text.g:1802:4: (lv_name_1_0= RULE_ID )
            // InternalHenshin_text.g:1803:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_11); 

            					newLeafNode(lv_name_1_0, grammarAccess.getConditionGraphAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConditionGraphRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,19,FOLLOW_35); 

            			newLeafNode(otherlv_2, grammarAccess.getConditionGraphAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalHenshin_text.g:1823:3: ( (lv_conditionGraphElements_3_0= ruleConditionGraphElements ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==34||(LA31_0>=37 && LA31_0<=38)||LA31_0==42) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalHenshin_text.g:1824:4: (lv_conditionGraphElements_3_0= ruleConditionGraphElements )
            	    {
            	    // InternalHenshin_text.g:1824:4: (lv_conditionGraphElements_3_0= ruleConditionGraphElements )
            	    // InternalHenshin_text.g:1825:5: lv_conditionGraphElements_3_0= ruleConditionGraphElements
            	    {

            	    					newCompositeNode(grammarAccess.getConditionGraphAccess().getConditionGraphElementsConditionGraphElementsParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_35);
            	    lv_conditionGraphElements_3_0=ruleConditionGraphElements();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getConditionGraphRule());
            	    					}
            	    					add(
            	    						current,
            	    						"conditionGraphElements",
            	    						lv_conditionGraphElements_3_0,
            	    						"org.eclipse.emf.henshin.text.Henshin_text.ConditionGraphElements");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);

            otherlv_4=(Token)match(input,20,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getConditionGraphAccess().getRightCurlyBracketKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConditionGraph"


    // $ANTLR start "entryRuleConditionGraphElements"
    // InternalHenshin_text.g:1850:1: entryRuleConditionGraphElements returns [EObject current=null] : iv_ruleConditionGraphElements= ruleConditionGraphElements EOF ;
    public final EObject entryRuleConditionGraphElements() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConditionGraphElements = null;


        try {
            // InternalHenshin_text.g:1850:63: (iv_ruleConditionGraphElements= ruleConditionGraphElements EOF )
            // InternalHenshin_text.g:1851:2: iv_ruleConditionGraphElements= ruleConditionGraphElements EOF
            {
             newCompositeNode(grammarAccess.getConditionGraphElementsRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConditionGraphElements=ruleConditionGraphElements();

            state._fsp--;

             current =iv_ruleConditionGraphElements; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConditionGraphElements"


    // $ANTLR start "ruleConditionGraphElements"
    // InternalHenshin_text.g:1857:1: ruleConditionGraphElements returns [EObject current=null] : (this_ConditionEdges_0= ruleConditionEdges | this_ConditionNode_1= ruleConditionNode | this_Formula_2= ruleFormula | this_ConditionReuseNode_3= ruleConditionReuseNode ) ;
    public final EObject ruleConditionGraphElements() throws RecognitionException {
        EObject current = null;

        EObject this_ConditionEdges_0 = null;

        EObject this_ConditionNode_1 = null;

        EObject this_Formula_2 = null;

        EObject this_ConditionReuseNode_3 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:1863:2: ( (this_ConditionEdges_0= ruleConditionEdges | this_ConditionNode_1= ruleConditionNode | this_Formula_2= ruleFormula | this_ConditionReuseNode_3= ruleConditionReuseNode ) )
            // InternalHenshin_text.g:1864:2: (this_ConditionEdges_0= ruleConditionEdges | this_ConditionNode_1= ruleConditionNode | this_Formula_2= ruleFormula | this_ConditionReuseNode_3= ruleConditionReuseNode )
            {
            // InternalHenshin_text.g:1864:2: (this_ConditionEdges_0= ruleConditionEdges | this_ConditionNode_1= ruleConditionNode | this_Formula_2= ruleFormula | this_ConditionReuseNode_3= ruleConditionReuseNode )
            int alt32=4;
            switch ( input.LA(1) ) {
            case 34:
                {
                alt32=1;
                }
                break;
            case 37:
                {
                alt32=2;
                }
                break;
            case 42:
                {
                alt32=3;
                }
                break;
            case 38:
                {
                alt32=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;
            }

            switch (alt32) {
                case 1 :
                    // InternalHenshin_text.g:1865:3: this_ConditionEdges_0= ruleConditionEdges
                    {

                    			newCompositeNode(grammarAccess.getConditionGraphElementsAccess().getConditionEdgesParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_ConditionEdges_0=ruleConditionEdges();

                    state._fsp--;


                    			current = this_ConditionEdges_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalHenshin_text.g:1874:3: this_ConditionNode_1= ruleConditionNode
                    {

                    			newCompositeNode(grammarAccess.getConditionGraphElementsAccess().getConditionNodeParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_ConditionNode_1=ruleConditionNode();

                    state._fsp--;


                    			current = this_ConditionNode_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalHenshin_text.g:1883:3: this_Formula_2= ruleFormula
                    {

                    			newCompositeNode(grammarAccess.getConditionGraphElementsAccess().getFormulaParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_Formula_2=ruleFormula();

                    state._fsp--;


                    			current = this_Formula_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalHenshin_text.g:1892:3: this_ConditionReuseNode_3= ruleConditionReuseNode
                    {

                    			newCompositeNode(grammarAccess.getConditionGraphElementsAccess().getConditionReuseNodeParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_ConditionReuseNode_3=ruleConditionReuseNode();

                    state._fsp--;


                    			current = this_ConditionReuseNode_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConditionGraphElements"


    // $ANTLR start "entryRuleConditionEdges"
    // InternalHenshin_text.g:1904:1: entryRuleConditionEdges returns [EObject current=null] : iv_ruleConditionEdges= ruleConditionEdges EOF ;
    public final EObject entryRuleConditionEdges() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConditionEdges = null;


        try {
            // InternalHenshin_text.g:1904:55: (iv_ruleConditionEdges= ruleConditionEdges EOF )
            // InternalHenshin_text.g:1905:2: iv_ruleConditionEdges= ruleConditionEdges EOF
            {
             newCompositeNode(grammarAccess.getConditionEdgesRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConditionEdges=ruleConditionEdges();

            state._fsp--;

             current =iv_ruleConditionEdges; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConditionEdges"


    // $ANTLR start "ruleConditionEdges"
    // InternalHenshin_text.g:1911:1: ruleConditionEdges returns [EObject current=null] : (otherlv_0= 'edges' otherlv_1= '[' ( (lv_edges_2_0= ruleConditionEdge ) ) (otherlv_3= ',' ( (lv_edges_4_0= ruleConditionEdge ) ) )* otherlv_5= ']' ) ;
    public final EObject ruleConditionEdges() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_edges_2_0 = null;

        EObject lv_edges_4_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:1917:2: ( (otherlv_0= 'edges' otherlv_1= '[' ( (lv_edges_2_0= ruleConditionEdge ) ) (otherlv_3= ',' ( (lv_edges_4_0= ruleConditionEdge ) ) )* otherlv_5= ']' ) )
            // InternalHenshin_text.g:1918:2: (otherlv_0= 'edges' otherlv_1= '[' ( (lv_edges_2_0= ruleConditionEdge ) ) (otherlv_3= ',' ( (lv_edges_4_0= ruleConditionEdge ) ) )* otherlv_5= ']' )
            {
            // InternalHenshin_text.g:1918:2: (otherlv_0= 'edges' otherlv_1= '[' ( (lv_edges_2_0= ruleConditionEdge ) ) (otherlv_3= ',' ( (lv_edges_4_0= ruleConditionEdge ) ) )* otherlv_5= ']' )
            // InternalHenshin_text.g:1919:3: otherlv_0= 'edges' otherlv_1= '[' ( (lv_edges_2_0= ruleConditionEdge ) ) (otherlv_3= ',' ( (lv_edges_4_0= ruleConditionEdge ) ) )* otherlv_5= ']'
            {
            otherlv_0=(Token)match(input,34,FOLLOW_17); 

            			newLeafNode(otherlv_0, grammarAccess.getConditionEdgesAccess().getEdgesKeyword_0());
            		
            otherlv_1=(Token)match(input,26,FOLLOW_7); 

            			newLeafNode(otherlv_1, grammarAccess.getConditionEdgesAccess().getLeftSquareBracketKeyword_1());
            		
            // InternalHenshin_text.g:1927:3: ( (lv_edges_2_0= ruleConditionEdge ) )
            // InternalHenshin_text.g:1928:4: (lv_edges_2_0= ruleConditionEdge )
            {
            // InternalHenshin_text.g:1928:4: (lv_edges_2_0= ruleConditionEdge )
            // InternalHenshin_text.g:1929:5: lv_edges_2_0= ruleConditionEdge
            {

            					newCompositeNode(grammarAccess.getConditionEdgesAccess().getEdgesConditionEdgeParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_19);
            lv_edges_2_0=ruleConditionEdge();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getConditionEdgesRule());
            					}
            					add(
            						current,
            						"edges",
            						lv_edges_2_0,
            						"org.eclipse.emf.henshin.text.Henshin_text.ConditionEdge");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalHenshin_text.g:1946:3: (otherlv_3= ',' ( (lv_edges_4_0= ruleConditionEdge ) ) )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==17) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // InternalHenshin_text.g:1947:4: otherlv_3= ',' ( (lv_edges_4_0= ruleConditionEdge ) )
            	    {
            	    otherlv_3=(Token)match(input,17,FOLLOW_7); 

            	    				newLeafNode(otherlv_3, grammarAccess.getConditionEdgesAccess().getCommaKeyword_3_0());
            	    			
            	    // InternalHenshin_text.g:1951:4: ( (lv_edges_4_0= ruleConditionEdge ) )
            	    // InternalHenshin_text.g:1952:5: (lv_edges_4_0= ruleConditionEdge )
            	    {
            	    // InternalHenshin_text.g:1952:5: (lv_edges_4_0= ruleConditionEdge )
            	    // InternalHenshin_text.g:1953:6: lv_edges_4_0= ruleConditionEdge
            	    {

            	    						newCompositeNode(grammarAccess.getConditionEdgesAccess().getEdgesConditionEdgeParserRuleCall_3_1_0());
            	    					
            	    pushFollow(FOLLOW_19);
            	    lv_edges_4_0=ruleConditionEdge();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getConditionEdgesRule());
            	    						}
            	    						add(
            	    							current,
            	    							"edges",
            	    							lv_edges_4_0,
            	    							"org.eclipse.emf.henshin.text.Henshin_text.ConditionEdge");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);

            otherlv_5=(Token)match(input,27,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getConditionEdgesAccess().getRightSquareBracketKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConditionEdges"


    // $ANTLR start "entryRuleConditionEdge"
    // InternalHenshin_text.g:1979:1: entryRuleConditionEdge returns [EObject current=null] : iv_ruleConditionEdge= ruleConditionEdge EOF ;
    public final EObject entryRuleConditionEdge() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConditionEdge = null;


        try {
            // InternalHenshin_text.g:1979:54: (iv_ruleConditionEdge= ruleConditionEdge EOF )
            // InternalHenshin_text.g:1980:2: iv_ruleConditionEdge= ruleConditionEdge EOF
            {
             newCompositeNode(grammarAccess.getConditionEdgeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConditionEdge=ruleConditionEdge();

            state._fsp--;

             current =iv_ruleConditionEdge; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConditionEdge"


    // $ANTLR start "ruleConditionEdge"
    // InternalHenshin_text.g:1986:1: ruleConditionEdge returns [EObject current=null] : (otherlv_0= '(' ( (otherlv_1= RULE_ID ) ) otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) otherlv_4= ':' ( ( ruleEString ) ) otherlv_6= ')' ) ;
    public final EObject ruleConditionEdge() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;


        	enterRule();

        try {
            // InternalHenshin_text.g:1992:2: ( (otherlv_0= '(' ( (otherlv_1= RULE_ID ) ) otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) otherlv_4= ':' ( ( ruleEString ) ) otherlv_6= ')' ) )
            // InternalHenshin_text.g:1993:2: (otherlv_0= '(' ( (otherlv_1= RULE_ID ) ) otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) otherlv_4= ':' ( ( ruleEString ) ) otherlv_6= ')' )
            {
            // InternalHenshin_text.g:1993:2: (otherlv_0= '(' ( (otherlv_1= RULE_ID ) ) otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) otherlv_4= ':' ( ( ruleEString ) ) otherlv_6= ')' )
            // InternalHenshin_text.g:1994:3: otherlv_0= '(' ( (otherlv_1= RULE_ID ) ) otherlv_2= '->' ( (otherlv_3= RULE_ID ) ) otherlv_4= ':' ( ( ruleEString ) ) otherlv_6= ')'
            {
            otherlv_0=(Token)match(input,16,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getConditionEdgeAccess().getLeftParenthesisKeyword_0());
            		
            // InternalHenshin_text.g:1998:3: ( (otherlv_1= RULE_ID ) )
            // InternalHenshin_text.g:1999:4: (otherlv_1= RULE_ID )
            {
            // InternalHenshin_text.g:1999:4: (otherlv_1= RULE_ID )
            // InternalHenshin_text.g:2000:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConditionEdgeRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_22); 

            					newLeafNode(otherlv_1, grammarAccess.getConditionEdgeAccess().getSourceConditionNodeTypesCrossReference_1_0());
            				

            }


            }

            otherlv_2=(Token)match(input,35,FOLLOW_5); 

            			newLeafNode(otherlv_2, grammarAccess.getConditionEdgeAccess().getHyphenMinusGreaterThanSignKeyword_2());
            		
            // InternalHenshin_text.g:2015:3: ( (otherlv_3= RULE_ID ) )
            // InternalHenshin_text.g:2016:4: (otherlv_3= RULE_ID )
            {
            // InternalHenshin_text.g:2016:4: (otherlv_3= RULE_ID )
            // InternalHenshin_text.g:2017:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConditionEdgeRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_23); 

            					newLeafNode(otherlv_3, grammarAccess.getConditionEdgeAccess().getTargetConditionNodeTypesCrossReference_3_0());
            				

            }


            }

            otherlv_4=(Token)match(input,36,FOLLOW_5); 

            			newLeafNode(otherlv_4, grammarAccess.getConditionEdgeAccess().getColonKeyword_4());
            		
            // InternalHenshin_text.g:2032:3: ( ( ruleEString ) )
            // InternalHenshin_text.g:2033:4: ( ruleEString )
            {
            // InternalHenshin_text.g:2033:4: ( ruleEString )
            // InternalHenshin_text.g:2034:5: ruleEString
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConditionEdgeRule());
            					}
            				

            					newCompositeNode(grammarAccess.getConditionEdgeAccess().getTypeEReferenceCrossReference_5_0());
            				
            pushFollow(FOLLOW_24);
            ruleEString();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_6=(Token)match(input,18,FOLLOW_2); 

            			newLeafNode(otherlv_6, grammarAccess.getConditionEdgeAccess().getRightParenthesisKeyword_6());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConditionEdge"


    // $ANTLR start "entryRuleConditionNode"
    // InternalHenshin_text.g:2056:1: entryRuleConditionNode returns [EObject current=null] : iv_ruleConditionNode= ruleConditionNode EOF ;
    public final EObject entryRuleConditionNode() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConditionNode = null;


        try {
            // InternalHenshin_text.g:2056:54: (iv_ruleConditionNode= ruleConditionNode EOF )
            // InternalHenshin_text.g:2057:2: iv_ruleConditionNode= ruleConditionNode EOF
            {
             newCompositeNode(grammarAccess.getConditionNodeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConditionNode=ruleConditionNode();

            state._fsp--;

             current =iv_ruleConditionNode; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConditionNode"


    // $ANTLR start "ruleConditionNode"
    // InternalHenshin_text.g:2063:1: ruleConditionNode returns [EObject current=null] : (otherlv_0= 'node' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( ( ruleEString ) ) (otherlv_4= '{' ( (lv_attribute_5_0= ruleMatch ) )* otherlv_6= '}' )? ) ;
    public final EObject ruleConditionNode() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_attribute_5_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:2069:2: ( (otherlv_0= 'node' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( ( ruleEString ) ) (otherlv_4= '{' ( (lv_attribute_5_0= ruleMatch ) )* otherlv_6= '}' )? ) )
            // InternalHenshin_text.g:2070:2: (otherlv_0= 'node' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( ( ruleEString ) ) (otherlv_4= '{' ( (lv_attribute_5_0= ruleMatch ) )* otherlv_6= '}' )? )
            {
            // InternalHenshin_text.g:2070:2: (otherlv_0= 'node' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( ( ruleEString ) ) (otherlv_4= '{' ( (lv_attribute_5_0= ruleMatch ) )* otherlv_6= '}' )? )
            // InternalHenshin_text.g:2071:3: otherlv_0= 'node' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( ( ruleEString ) ) (otherlv_4= '{' ( (lv_attribute_5_0= ruleMatch ) )* otherlv_6= '}' )?
            {
            otherlv_0=(Token)match(input,37,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getConditionNodeAccess().getNodeKeyword_0());
            		
            // InternalHenshin_text.g:2075:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalHenshin_text.g:2076:4: (lv_name_1_0= RULE_ID )
            {
            // InternalHenshin_text.g:2076:4: (lv_name_1_0= RULE_ID )
            // InternalHenshin_text.g:2077:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_23); 

            					newLeafNode(lv_name_1_0, grammarAccess.getConditionNodeAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConditionNodeRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,36,FOLLOW_5); 

            			newLeafNode(otherlv_2, grammarAccess.getConditionNodeAccess().getColonKeyword_2());
            		
            // InternalHenshin_text.g:2097:3: ( ( ruleEString ) )
            // InternalHenshin_text.g:2098:4: ( ruleEString )
            {
            // InternalHenshin_text.g:2098:4: ( ruleEString )
            // InternalHenshin_text.g:2099:5: ruleEString
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConditionNodeRule());
            					}
            				

            					newCompositeNode(grammarAccess.getConditionNodeAccess().getTypeEClassCrossReference_3_0());
            				
            pushFollow(FOLLOW_26);
            ruleEString();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalHenshin_text.g:2113:3: (otherlv_4= '{' ( (lv_attribute_5_0= ruleMatch ) )* otherlv_6= '}' )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==19) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalHenshin_text.g:2114:4: otherlv_4= '{' ( (lv_attribute_5_0= ruleMatch ) )* otherlv_6= '}'
                    {
                    otherlv_4=(Token)match(input,19,FOLLOW_36); 

                    				newLeafNode(otherlv_4, grammarAccess.getConditionNodeAccess().getLeftCurlyBracketKeyword_4_0());
                    			
                    // InternalHenshin_text.g:2118:4: ( (lv_attribute_5_0= ruleMatch ) )*
                    loop34:
                    do {
                        int alt34=2;
                        int LA34_0 = input.LA(1);

                        if ( (LA34_0==RULE_ID) ) {
                            alt34=1;
                        }


                        switch (alt34) {
                    	case 1 :
                    	    // InternalHenshin_text.g:2119:5: (lv_attribute_5_0= ruleMatch )
                    	    {
                    	    // InternalHenshin_text.g:2119:5: (lv_attribute_5_0= ruleMatch )
                    	    // InternalHenshin_text.g:2120:6: lv_attribute_5_0= ruleMatch
                    	    {

                    	    						newCompositeNode(grammarAccess.getConditionNodeAccess().getAttributeMatchParserRuleCall_4_1_0());
                    	    					
                    	    pushFollow(FOLLOW_36);
                    	    lv_attribute_5_0=ruleMatch();

                    	    state._fsp--;


                    	    						if (current==null) {
                    	    							current = createModelElementForParent(grammarAccess.getConditionNodeRule());
                    	    						}
                    	    						add(
                    	    							current,
                    	    							"attribute",
                    	    							lv_attribute_5_0,
                    	    							"org.eclipse.emf.henshin.text.Henshin_text.Match");
                    	    						afterParserOrEnumRuleCall();
                    	    					

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop34;
                        }
                    } while (true);

                    otherlv_6=(Token)match(input,20,FOLLOW_2); 

                    				newLeafNode(otherlv_6, grammarAccess.getConditionNodeAccess().getRightCurlyBracketKeyword_4_2());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConditionNode"


    // $ANTLR start "entryRuleConditionReuseNode"
    // InternalHenshin_text.g:2146:1: entryRuleConditionReuseNode returns [EObject current=null] : iv_ruleConditionReuseNode= ruleConditionReuseNode EOF ;
    public final EObject entryRuleConditionReuseNode() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConditionReuseNode = null;


        try {
            // InternalHenshin_text.g:2146:59: (iv_ruleConditionReuseNode= ruleConditionReuseNode EOF )
            // InternalHenshin_text.g:2147:2: iv_ruleConditionReuseNode= ruleConditionReuseNode EOF
            {
             newCompositeNode(grammarAccess.getConditionReuseNodeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConditionReuseNode=ruleConditionReuseNode();

            state._fsp--;

             current =iv_ruleConditionReuseNode; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConditionReuseNode"


    // $ANTLR start "ruleConditionReuseNode"
    // InternalHenshin_text.g:2153:1: ruleConditionReuseNode returns [EObject current=null] : (otherlv_0= 'reuse' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '{' ( (lv_attribute_3_0= ruleMatch ) )* otherlv_4= '}' )? ) ;
    public final EObject ruleConditionReuseNode() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_attribute_3_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:2159:2: ( (otherlv_0= 'reuse' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '{' ( (lv_attribute_3_0= ruleMatch ) )* otherlv_4= '}' )? ) )
            // InternalHenshin_text.g:2160:2: (otherlv_0= 'reuse' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '{' ( (lv_attribute_3_0= ruleMatch ) )* otherlv_4= '}' )? )
            {
            // InternalHenshin_text.g:2160:2: (otherlv_0= 'reuse' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '{' ( (lv_attribute_3_0= ruleMatch ) )* otherlv_4= '}' )? )
            // InternalHenshin_text.g:2161:3: otherlv_0= 'reuse' ( (otherlv_1= RULE_ID ) ) (otherlv_2= '{' ( (lv_attribute_3_0= ruleMatch ) )* otherlv_4= '}' )?
            {
            otherlv_0=(Token)match(input,38,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getConditionReuseNodeAccess().getReuseKeyword_0());
            		
            // InternalHenshin_text.g:2165:3: ( (otherlv_1= RULE_ID ) )
            // InternalHenshin_text.g:2166:4: (otherlv_1= RULE_ID )
            {
            // InternalHenshin_text.g:2166:4: (otherlv_1= RULE_ID )
            // InternalHenshin_text.g:2167:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getConditionReuseNodeRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_26); 

            					newLeafNode(otherlv_1, grammarAccess.getConditionReuseNodeAccess().getNameConditionNodeTypesCrossReference_1_0());
            				

            }


            }

            // InternalHenshin_text.g:2178:3: (otherlv_2= '{' ( (lv_attribute_3_0= ruleMatch ) )* otherlv_4= '}' )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==19) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalHenshin_text.g:2179:4: otherlv_2= '{' ( (lv_attribute_3_0= ruleMatch ) )* otherlv_4= '}'
                    {
                    otherlv_2=(Token)match(input,19,FOLLOW_36); 

                    				newLeafNode(otherlv_2, grammarAccess.getConditionReuseNodeAccess().getLeftCurlyBracketKeyword_2_0());
                    			
                    // InternalHenshin_text.g:2183:4: ( (lv_attribute_3_0= ruleMatch ) )*
                    loop36:
                    do {
                        int alt36=2;
                        int LA36_0 = input.LA(1);

                        if ( (LA36_0==RULE_ID) ) {
                            alt36=1;
                        }


                        switch (alt36) {
                    	case 1 :
                    	    // InternalHenshin_text.g:2184:5: (lv_attribute_3_0= ruleMatch )
                    	    {
                    	    // InternalHenshin_text.g:2184:5: (lv_attribute_3_0= ruleMatch )
                    	    // InternalHenshin_text.g:2185:6: lv_attribute_3_0= ruleMatch
                    	    {

                    	    						newCompositeNode(grammarAccess.getConditionReuseNodeAccess().getAttributeMatchParserRuleCall_2_1_0());
                    	    					
                    	    pushFollow(FOLLOW_36);
                    	    lv_attribute_3_0=ruleMatch();

                    	    state._fsp--;


                    	    						if (current==null) {
                    	    							current = createModelElementForParent(grammarAccess.getConditionReuseNodeRule());
                    	    						}
                    	    						add(
                    	    							current,
                    	    							"attribute",
                    	    							lv_attribute_3_0,
                    	    							"org.eclipse.emf.henshin.text.Henshin_text.Match");
                    	    						afterParserOrEnumRuleCall();
                    	    					

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop36;
                        }
                    } while (true);

                    otherlv_4=(Token)match(input,20,FOLLOW_2); 

                    				newLeafNode(otherlv_4, grammarAccess.getConditionReuseNodeAccess().getRightCurlyBracketKeyword_2_2());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConditionReuseNode"


    // $ANTLR start "entryRuleMatch"
    // InternalHenshin_text.g:2211:1: entryRuleMatch returns [EObject current=null] : iv_ruleMatch= ruleMatch EOF ;
    public final EObject entryRuleMatch() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMatch = null;


        try {
            // InternalHenshin_text.g:2211:46: (iv_ruleMatch= ruleMatch EOF )
            // InternalHenshin_text.g:2212:2: iv_ruleMatch= ruleMatch EOF
            {
             newCompositeNode(grammarAccess.getMatchRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMatch=ruleMatch();

            state._fsp--;

             current =iv_ruleMatch; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMatch"


    // $ANTLR start "ruleMatch"
    // InternalHenshin_text.g:2218:1: ruleMatch returns [EObject current=null] : ( ( ( ruleEString ) ) otherlv_1= '=' ( (lv_value_2_0= ruleExpression ) ) ) ;
    public final EObject ruleMatch() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:2224:2: ( ( ( ( ruleEString ) ) otherlv_1= '=' ( (lv_value_2_0= ruleExpression ) ) ) )
            // InternalHenshin_text.g:2225:2: ( ( ( ruleEString ) ) otherlv_1= '=' ( (lv_value_2_0= ruleExpression ) ) )
            {
            // InternalHenshin_text.g:2225:2: ( ( ( ruleEString ) ) otherlv_1= '=' ( (lv_value_2_0= ruleExpression ) ) )
            // InternalHenshin_text.g:2226:3: ( ( ruleEString ) ) otherlv_1= '=' ( (lv_value_2_0= ruleExpression ) )
            {
            // InternalHenshin_text.g:2226:3: ( ( ruleEString ) )
            // InternalHenshin_text.g:2227:4: ( ruleEString )
            {
            // InternalHenshin_text.g:2227:4: ( ruleEString )
            // InternalHenshin_text.g:2228:5: ruleEString
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMatchRule());
            					}
            				

            					newCompositeNode(grammarAccess.getMatchAccess().getNameEAttributeCrossReference_0_0());
            				
            pushFollow(FOLLOW_28);
            ruleEString();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,39,FOLLOW_18); 

            			newLeafNode(otherlv_1, grammarAccess.getMatchAccess().getEqualsSignKeyword_1());
            		
            // InternalHenshin_text.g:2246:3: ( (lv_value_2_0= ruleExpression ) )
            // InternalHenshin_text.g:2247:4: (lv_value_2_0= ruleExpression )
            {
            // InternalHenshin_text.g:2247:4: (lv_value_2_0= ruleExpression )
            // InternalHenshin_text.g:2248:5: lv_value_2_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getMatchAccess().getValueExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMatchRule());
            					}
            					set(
            						current,
            						"value",
            						lv_value_2_0,
            						"org.eclipse.emf.henshin.text.Henshin_text.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMatch"


    // $ANTLR start "entryRuleUnitElement"
    // InternalHenshin_text.g:2269:1: entryRuleUnitElement returns [EObject current=null] : iv_ruleUnitElement= ruleUnitElement EOF ;
    public final EObject entryRuleUnitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnitElement = null;


        try {
            // InternalHenshin_text.g:2269:52: (iv_ruleUnitElement= ruleUnitElement EOF )
            // InternalHenshin_text.g:2270:2: iv_ruleUnitElement= ruleUnitElement EOF
            {
             newCompositeNode(grammarAccess.getUnitElementRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleUnitElement=ruleUnitElement();

            state._fsp--;

             current =iv_ruleUnitElement; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnitElement"


    // $ANTLR start "ruleUnitElement"
    // InternalHenshin_text.g:2276:1: ruleUnitElement returns [EObject current=null] : ( ( () ( (otherlv_1= RULE_ID ) ) otherlv_2= '(' ( ( (otherlv_3= RULE_ID ) ) (otherlv_4= ',' ( (otherlv_5= RULE_ID ) ) )* )? otherlv_6= ')' ) | (otherlv_7= '{' ( (lv_subSequence_8_0= ruleUnitElement ) )+ otherlv_9= '}' ) | this_SequentialProperties_10= ruleSequentialProperties | this_IndependentUnit_11= ruleIndependentUnit | this_ConditionalUnit_12= ruleConditionalUnit | this_PriorityUnit_13= rulePriorityUnit | this_IteratedUnit_14= ruleIteratedUnit | this_LoopUnit_15= ruleLoopUnit ) ;
    public final EObject ruleUnitElement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        EObject lv_subSequence_8_0 = null;

        EObject this_SequentialProperties_10 = null;

        EObject this_IndependentUnit_11 = null;

        EObject this_ConditionalUnit_12 = null;

        EObject this_PriorityUnit_13 = null;

        EObject this_IteratedUnit_14 = null;

        EObject this_LoopUnit_15 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:2282:2: ( ( ( () ( (otherlv_1= RULE_ID ) ) otherlv_2= '(' ( ( (otherlv_3= RULE_ID ) ) (otherlv_4= ',' ( (otherlv_5= RULE_ID ) ) )* )? otherlv_6= ')' ) | (otherlv_7= '{' ( (lv_subSequence_8_0= ruleUnitElement ) )+ otherlv_9= '}' ) | this_SequentialProperties_10= ruleSequentialProperties | this_IndependentUnit_11= ruleIndependentUnit | this_ConditionalUnit_12= ruleConditionalUnit | this_PriorityUnit_13= rulePriorityUnit | this_IteratedUnit_14= ruleIteratedUnit | this_LoopUnit_15= ruleLoopUnit ) )
            // InternalHenshin_text.g:2283:2: ( ( () ( (otherlv_1= RULE_ID ) ) otherlv_2= '(' ( ( (otherlv_3= RULE_ID ) ) (otherlv_4= ',' ( (otherlv_5= RULE_ID ) ) )* )? otherlv_6= ')' ) | (otherlv_7= '{' ( (lv_subSequence_8_0= ruleUnitElement ) )+ otherlv_9= '}' ) | this_SequentialProperties_10= ruleSequentialProperties | this_IndependentUnit_11= ruleIndependentUnit | this_ConditionalUnit_12= ruleConditionalUnit | this_PriorityUnit_13= rulePriorityUnit | this_IteratedUnit_14= ruleIteratedUnit | this_LoopUnit_15= ruleLoopUnit )
            {
            // InternalHenshin_text.g:2283:2: ( ( () ( (otherlv_1= RULE_ID ) ) otherlv_2= '(' ( ( (otherlv_3= RULE_ID ) ) (otherlv_4= ',' ( (otherlv_5= RULE_ID ) ) )* )? otherlv_6= ')' ) | (otherlv_7= '{' ( (lv_subSequence_8_0= ruleUnitElement ) )+ otherlv_9= '}' ) | this_SequentialProperties_10= ruleSequentialProperties | this_IndependentUnit_11= ruleIndependentUnit | this_ConditionalUnit_12= ruleConditionalUnit | this_PriorityUnit_13= rulePriorityUnit | this_IteratedUnit_14= ruleIteratedUnit | this_LoopUnit_15= ruleLoopUnit )
            int alt41=8;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt41=1;
                }
                break;
            case 19:
                {
                alt41=2;
                }
                break;
            case 49:
            case 50:
                {
                alt41=3;
                }
                break;
            case 51:
                {
                alt41=4;
                }
                break;
            case 52:
                {
                alt41=5;
                }
                break;
            case 55:
                {
                alt41=6;
                }
                break;
            case 56:
                {
                alt41=7;
                }
                break;
            case 57:
                {
                alt41=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 41, 0, input);

                throw nvae;
            }

            switch (alt41) {
                case 1 :
                    // InternalHenshin_text.g:2284:3: ( () ( (otherlv_1= RULE_ID ) ) otherlv_2= '(' ( ( (otherlv_3= RULE_ID ) ) (otherlv_4= ',' ( (otherlv_5= RULE_ID ) ) )* )? otherlv_6= ')' )
                    {
                    // InternalHenshin_text.g:2284:3: ( () ( (otherlv_1= RULE_ID ) ) otherlv_2= '(' ( ( (otherlv_3= RULE_ID ) ) (otherlv_4= ',' ( (otherlv_5= RULE_ID ) ) )* )? otherlv_6= ')' )
                    // InternalHenshin_text.g:2285:4: () ( (otherlv_1= RULE_ID ) ) otherlv_2= '(' ( ( (otherlv_3= RULE_ID ) ) (otherlv_4= ',' ( (otherlv_5= RULE_ID ) ) )* )? otherlv_6= ')'
                    {
                    // InternalHenshin_text.g:2285:4: ()
                    // InternalHenshin_text.g:2286:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getUnitElementAccess().getCallAction_0_0(),
                    						current);
                    				

                    }

                    // InternalHenshin_text.g:2292:4: ( (otherlv_1= RULE_ID ) )
                    // InternalHenshin_text.g:2293:5: (otherlv_1= RULE_ID )
                    {
                    // InternalHenshin_text.g:2293:5: (otherlv_1= RULE_ID )
                    // InternalHenshin_text.g:2294:6: otherlv_1= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getUnitElementRule());
                    						}
                    					
                    otherlv_1=(Token)match(input,RULE_ID,FOLLOW_7); 

                    						newLeafNode(otherlv_1, grammarAccess.getUnitElementAccess().getElementCallModelElementCrossReference_0_1_0());
                    					

                    }


                    }

                    otherlv_2=(Token)match(input,16,FOLLOW_37); 

                    				newLeafNode(otherlv_2, grammarAccess.getUnitElementAccess().getLeftParenthesisKeyword_0_2());
                    			
                    // InternalHenshin_text.g:2309:4: ( ( (otherlv_3= RULE_ID ) ) (otherlv_4= ',' ( (otherlv_5= RULE_ID ) ) )* )?
                    int alt39=2;
                    int LA39_0 = input.LA(1);

                    if ( (LA39_0==RULE_ID) ) {
                        alt39=1;
                    }
                    switch (alt39) {
                        case 1 :
                            // InternalHenshin_text.g:2310:5: ( (otherlv_3= RULE_ID ) ) (otherlv_4= ',' ( (otherlv_5= RULE_ID ) ) )*
                            {
                            // InternalHenshin_text.g:2310:5: ( (otherlv_3= RULE_ID ) )
                            // InternalHenshin_text.g:2311:6: (otherlv_3= RULE_ID )
                            {
                            // InternalHenshin_text.g:2311:6: (otherlv_3= RULE_ID )
                            // InternalHenshin_text.g:2312:7: otherlv_3= RULE_ID
                            {

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getUnitElementRule());
                            							}
                            						
                            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_9); 

                            							newLeafNode(otherlv_3, grammarAccess.getUnitElementAccess().getParametersParameterCrossReference_0_3_0_0());
                            						

                            }


                            }

                            // InternalHenshin_text.g:2323:5: (otherlv_4= ',' ( (otherlv_5= RULE_ID ) ) )*
                            loop38:
                            do {
                                int alt38=2;
                                int LA38_0 = input.LA(1);

                                if ( (LA38_0==17) ) {
                                    alt38=1;
                                }


                                switch (alt38) {
                            	case 1 :
                            	    // InternalHenshin_text.g:2324:6: otherlv_4= ',' ( (otherlv_5= RULE_ID ) )
                            	    {
                            	    otherlv_4=(Token)match(input,17,FOLLOW_5); 

                            	    						newLeafNode(otherlv_4, grammarAccess.getUnitElementAccess().getCommaKeyword_0_3_1_0());
                            	    					
                            	    // InternalHenshin_text.g:2328:6: ( (otherlv_5= RULE_ID ) )
                            	    // InternalHenshin_text.g:2329:7: (otherlv_5= RULE_ID )
                            	    {
                            	    // InternalHenshin_text.g:2329:7: (otherlv_5= RULE_ID )
                            	    // InternalHenshin_text.g:2330:8: otherlv_5= RULE_ID
                            	    {

                            	    								if (current==null) {
                            	    									current = createModelElement(grammarAccess.getUnitElementRule());
                            	    								}
                            	    							
                            	    otherlv_5=(Token)match(input,RULE_ID,FOLLOW_9); 

                            	    								newLeafNode(otherlv_5, grammarAccess.getUnitElementAccess().getParametersParameterCrossReference_0_3_1_1_0());
                            	    							

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop38;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_6=(Token)match(input,18,FOLLOW_2); 

                    				newLeafNode(otherlv_6, grammarAccess.getUnitElementAccess().getRightParenthesisKeyword_0_4());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalHenshin_text.g:2349:3: (otherlv_7= '{' ( (lv_subSequence_8_0= ruleUnitElement ) )+ otherlv_9= '}' )
                    {
                    // InternalHenshin_text.g:2349:3: (otherlv_7= '{' ( (lv_subSequence_8_0= ruleUnitElement ) )+ otherlv_9= '}' )
                    // InternalHenshin_text.g:2350:4: otherlv_7= '{' ( (lv_subSequence_8_0= ruleUnitElement ) )+ otherlv_9= '}'
                    {
                    otherlv_7=(Token)match(input,19,FOLLOW_14); 

                    				newLeafNode(otherlv_7, grammarAccess.getUnitElementAccess().getLeftCurlyBracketKeyword_1_0());
                    			
                    // InternalHenshin_text.g:2354:4: ( (lv_subSequence_8_0= ruleUnitElement ) )+
                    int cnt40=0;
                    loop40:
                    do {
                        int alt40=2;
                        int LA40_0 = input.LA(1);

                        if ( (LA40_0==RULE_ID||LA40_0==19||(LA40_0>=49 && LA40_0<=52)||(LA40_0>=55 && LA40_0<=57)) ) {
                            alt40=1;
                        }


                        switch (alt40) {
                    	case 1 :
                    	    // InternalHenshin_text.g:2355:5: (lv_subSequence_8_0= ruleUnitElement )
                    	    {
                    	    // InternalHenshin_text.g:2355:5: (lv_subSequence_8_0= ruleUnitElement )
                    	    // InternalHenshin_text.g:2356:6: lv_subSequence_8_0= ruleUnitElement
                    	    {

                    	    						newCompositeNode(grammarAccess.getUnitElementAccess().getSubSequenceUnitElementParserRuleCall_1_1_0());
                    	    					
                    	    pushFollow(FOLLOW_15);
                    	    lv_subSequence_8_0=ruleUnitElement();

                    	    state._fsp--;


                    	    						if (current==null) {
                    	    							current = createModelElementForParent(grammarAccess.getUnitElementRule());
                    	    						}
                    	    						add(
                    	    							current,
                    	    							"subSequence",
                    	    							lv_subSequence_8_0,
                    	    							"org.eclipse.emf.henshin.text.Henshin_text.UnitElement");
                    	    						afterParserOrEnumRuleCall();
                    	    					

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt40 >= 1 ) break loop40;
                                EarlyExitException eee =
                                    new EarlyExitException(40, input);
                                throw eee;
                        }
                        cnt40++;
                    } while (true);

                    otherlv_9=(Token)match(input,20,FOLLOW_2); 

                    				newLeafNode(otherlv_9, grammarAccess.getUnitElementAccess().getRightCurlyBracketKeyword_1_2());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalHenshin_text.g:2379:3: this_SequentialProperties_10= ruleSequentialProperties
                    {

                    			newCompositeNode(grammarAccess.getUnitElementAccess().getSequentialPropertiesParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_SequentialProperties_10=ruleSequentialProperties();

                    state._fsp--;


                    			current = this_SequentialProperties_10;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalHenshin_text.g:2388:3: this_IndependentUnit_11= ruleIndependentUnit
                    {

                    			newCompositeNode(grammarAccess.getUnitElementAccess().getIndependentUnitParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_IndependentUnit_11=ruleIndependentUnit();

                    state._fsp--;


                    			current = this_IndependentUnit_11;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 5 :
                    // InternalHenshin_text.g:2397:3: this_ConditionalUnit_12= ruleConditionalUnit
                    {

                    			newCompositeNode(grammarAccess.getUnitElementAccess().getConditionalUnitParserRuleCall_4());
                    		
                    pushFollow(FOLLOW_2);
                    this_ConditionalUnit_12=ruleConditionalUnit();

                    state._fsp--;


                    			current = this_ConditionalUnit_12;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 6 :
                    // InternalHenshin_text.g:2406:3: this_PriorityUnit_13= rulePriorityUnit
                    {

                    			newCompositeNode(grammarAccess.getUnitElementAccess().getPriorityUnitParserRuleCall_5());
                    		
                    pushFollow(FOLLOW_2);
                    this_PriorityUnit_13=rulePriorityUnit();

                    state._fsp--;


                    			current = this_PriorityUnit_13;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 7 :
                    // InternalHenshin_text.g:2415:3: this_IteratedUnit_14= ruleIteratedUnit
                    {

                    			newCompositeNode(grammarAccess.getUnitElementAccess().getIteratedUnitParserRuleCall_6());
                    		
                    pushFollow(FOLLOW_2);
                    this_IteratedUnit_14=ruleIteratedUnit();

                    state._fsp--;


                    			current = this_IteratedUnit_14;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 8 :
                    // InternalHenshin_text.g:2424:3: this_LoopUnit_15= ruleLoopUnit
                    {

                    			newCompositeNode(grammarAccess.getUnitElementAccess().getLoopUnitParserRuleCall_7());
                    		
                    pushFollow(FOLLOW_2);
                    this_LoopUnit_15=ruleLoopUnit();

                    state._fsp--;


                    			current = this_LoopUnit_15;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnitElement"


    // $ANTLR start "entryRuleSequentialProperties"
    // InternalHenshin_text.g:2436:1: entryRuleSequentialProperties returns [EObject current=null] : iv_ruleSequentialProperties= ruleSequentialProperties EOF ;
    public final EObject entryRuleSequentialProperties() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSequentialProperties = null;


        try {
            // InternalHenshin_text.g:2436:61: (iv_ruleSequentialProperties= ruleSequentialProperties EOF )
            // InternalHenshin_text.g:2437:2: iv_ruleSequentialProperties= ruleSequentialProperties EOF
            {
             newCompositeNode(grammarAccess.getSequentialPropertiesRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSequentialProperties=ruleSequentialProperties();

            state._fsp--;

             current =iv_ruleSequentialProperties; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSequentialProperties"


    // $ANTLR start "ruleSequentialProperties"
    // InternalHenshin_text.g:2443:1: ruleSequentialProperties returns [EObject current=null] : (this_Strict_0= ruleStrict | this_Rollback_1= ruleRollback ) ;
    public final EObject ruleSequentialProperties() throws RecognitionException {
        EObject current = null;

        EObject this_Strict_0 = null;

        EObject this_Rollback_1 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:2449:2: ( (this_Strict_0= ruleStrict | this_Rollback_1= ruleRollback ) )
            // InternalHenshin_text.g:2450:2: (this_Strict_0= ruleStrict | this_Rollback_1= ruleRollback )
            {
            // InternalHenshin_text.g:2450:2: (this_Strict_0= ruleStrict | this_Rollback_1= ruleRollback )
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==49) ) {
                alt42=1;
            }
            else if ( (LA42_0==50) ) {
                alt42=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 42, 0, input);

                throw nvae;
            }
            switch (alt42) {
                case 1 :
                    // InternalHenshin_text.g:2451:3: this_Strict_0= ruleStrict
                    {

                    			newCompositeNode(grammarAccess.getSequentialPropertiesAccess().getStrictParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Strict_0=ruleStrict();

                    state._fsp--;


                    			current = this_Strict_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalHenshin_text.g:2460:3: this_Rollback_1= ruleRollback
                    {

                    			newCompositeNode(grammarAccess.getSequentialPropertiesAccess().getRollbackParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_Rollback_1=ruleRollback();

                    state._fsp--;


                    			current = this_Rollback_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSequentialProperties"


    // $ANTLR start "entryRuleStrict"
    // InternalHenshin_text.g:2472:1: entryRuleStrict returns [EObject current=null] : iv_ruleStrict= ruleStrict EOF ;
    public final EObject entryRuleStrict() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStrict = null;


        try {
            // InternalHenshin_text.g:2472:47: (iv_ruleStrict= ruleStrict EOF )
            // InternalHenshin_text.g:2473:2: iv_ruleStrict= ruleStrict EOF
            {
             newCompositeNode(grammarAccess.getStrictRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStrict=ruleStrict();

            state._fsp--;

             current =iv_ruleStrict; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStrict"


    // $ANTLR start "ruleStrict"
    // InternalHenshin_text.g:2479:1: ruleStrict returns [EObject current=null] : (otherlv_0= 'strict' ( (lv_strict_1_0= ruleEBoolean ) ) ) ;
    public final EObject ruleStrict() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        AntlrDatatypeRuleToken lv_strict_1_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:2485:2: ( (otherlv_0= 'strict' ( (lv_strict_1_0= ruleEBoolean ) ) ) )
            // InternalHenshin_text.g:2486:2: (otherlv_0= 'strict' ( (lv_strict_1_0= ruleEBoolean ) ) )
            {
            // InternalHenshin_text.g:2486:2: (otherlv_0= 'strict' ( (lv_strict_1_0= ruleEBoolean ) ) )
            // InternalHenshin_text.g:2487:3: otherlv_0= 'strict' ( (lv_strict_1_0= ruleEBoolean ) )
            {
            otherlv_0=(Token)match(input,49,FOLLOW_16); 

            			newLeafNode(otherlv_0, grammarAccess.getStrictAccess().getStrictKeyword_0());
            		
            // InternalHenshin_text.g:2491:3: ( (lv_strict_1_0= ruleEBoolean ) )
            // InternalHenshin_text.g:2492:4: (lv_strict_1_0= ruleEBoolean )
            {
            // InternalHenshin_text.g:2492:4: (lv_strict_1_0= ruleEBoolean )
            // InternalHenshin_text.g:2493:5: lv_strict_1_0= ruleEBoolean
            {

            					newCompositeNode(grammarAccess.getStrictAccess().getStrictEBooleanParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_strict_1_0=ruleEBoolean();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getStrictRule());
            					}
            					set(
            						current,
            						"strict",
            						lv_strict_1_0,
            						"org.eclipse.emf.henshin.text.Henshin_text.EBoolean");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStrict"


    // $ANTLR start "entryRuleRollback"
    // InternalHenshin_text.g:2514:1: entryRuleRollback returns [EObject current=null] : iv_ruleRollback= ruleRollback EOF ;
    public final EObject entryRuleRollback() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRollback = null;


        try {
            // InternalHenshin_text.g:2514:49: (iv_ruleRollback= ruleRollback EOF )
            // InternalHenshin_text.g:2515:2: iv_ruleRollback= ruleRollback EOF
            {
             newCompositeNode(grammarAccess.getRollbackRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRollback=ruleRollback();

            state._fsp--;

             current =iv_ruleRollback; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRollback"


    // $ANTLR start "ruleRollback"
    // InternalHenshin_text.g:2521:1: ruleRollback returns [EObject current=null] : (otherlv_0= 'rollback' ( (lv_rollback_1_0= ruleEBoolean ) ) ) ;
    public final EObject ruleRollback() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        AntlrDatatypeRuleToken lv_rollback_1_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:2527:2: ( (otherlv_0= 'rollback' ( (lv_rollback_1_0= ruleEBoolean ) ) ) )
            // InternalHenshin_text.g:2528:2: (otherlv_0= 'rollback' ( (lv_rollback_1_0= ruleEBoolean ) ) )
            {
            // InternalHenshin_text.g:2528:2: (otherlv_0= 'rollback' ( (lv_rollback_1_0= ruleEBoolean ) ) )
            // InternalHenshin_text.g:2529:3: otherlv_0= 'rollback' ( (lv_rollback_1_0= ruleEBoolean ) )
            {
            otherlv_0=(Token)match(input,50,FOLLOW_16); 

            			newLeafNode(otherlv_0, grammarAccess.getRollbackAccess().getRollbackKeyword_0());
            		
            // InternalHenshin_text.g:2533:3: ( (lv_rollback_1_0= ruleEBoolean ) )
            // InternalHenshin_text.g:2534:4: (lv_rollback_1_0= ruleEBoolean )
            {
            // InternalHenshin_text.g:2534:4: (lv_rollback_1_0= ruleEBoolean )
            // InternalHenshin_text.g:2535:5: lv_rollback_1_0= ruleEBoolean
            {

            					newCompositeNode(grammarAccess.getRollbackAccess().getRollbackEBooleanParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_rollback_1_0=ruleEBoolean();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getRollbackRule());
            					}
            					set(
            						current,
            						"rollback",
            						lv_rollback_1_0,
            						"org.eclipse.emf.henshin.text.Henshin_text.EBoolean");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRollback"


    // $ANTLR start "entryRuleList"
    // InternalHenshin_text.g:2556:1: entryRuleList returns [EObject current=null] : iv_ruleList= ruleList EOF ;
    public final EObject entryRuleList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleList = null;


        try {
            // InternalHenshin_text.g:2556:45: (iv_ruleList= ruleList EOF )
            // InternalHenshin_text.g:2557:2: iv_ruleList= ruleList EOF
            {
             newCompositeNode(grammarAccess.getListRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleList=ruleList();

            state._fsp--;

             current =iv_ruleList; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleList"


    // $ANTLR start "ruleList"
    // InternalHenshin_text.g:2563:1: ruleList returns [EObject current=null] : ( (lv_subElements_0_0= ruleUnitElement ) )+ ;
    public final EObject ruleList() throws RecognitionException {
        EObject current = null;

        EObject lv_subElements_0_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:2569:2: ( ( (lv_subElements_0_0= ruleUnitElement ) )+ )
            // InternalHenshin_text.g:2570:2: ( (lv_subElements_0_0= ruleUnitElement ) )+
            {
            // InternalHenshin_text.g:2570:2: ( (lv_subElements_0_0= ruleUnitElement ) )+
            int cnt43=0;
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==RULE_ID||LA43_0==19||(LA43_0>=49 && LA43_0<=52)||(LA43_0>=55 && LA43_0<=57)) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalHenshin_text.g:2571:3: (lv_subElements_0_0= ruleUnitElement )
            	    {
            	    // InternalHenshin_text.g:2571:3: (lv_subElements_0_0= ruleUnitElement )
            	    // InternalHenshin_text.g:2572:4: lv_subElements_0_0= ruleUnitElement
            	    {

            	    				newCompositeNode(grammarAccess.getListAccess().getSubElementsUnitElementParserRuleCall_0());
            	    			
            	    pushFollow(FOLLOW_38);
            	    lv_subElements_0_0=ruleUnitElement();

            	    state._fsp--;


            	    				if (current==null) {
            	    					current = createModelElementForParent(grammarAccess.getListRule());
            	    				}
            	    				add(
            	    					current,
            	    					"subElements",
            	    					lv_subElements_0_0,
            	    					"org.eclipse.emf.henshin.text.Henshin_text.UnitElement");
            	    				afterParserOrEnumRuleCall();
            	    			

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt43 >= 1 ) break loop43;
                        EarlyExitException eee =
                            new EarlyExitException(43, input);
                        throw eee;
                }
                cnt43++;
            } while (true);


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleList"


    // $ANTLR start "entryRuleIndependentUnit"
    // InternalHenshin_text.g:2592:1: entryRuleIndependentUnit returns [EObject current=null] : iv_ruleIndependentUnit= ruleIndependentUnit EOF ;
    public final EObject entryRuleIndependentUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIndependentUnit = null;


        try {
            // InternalHenshin_text.g:2592:56: (iv_ruleIndependentUnit= ruleIndependentUnit EOF )
            // InternalHenshin_text.g:2593:2: iv_ruleIndependentUnit= ruleIndependentUnit EOF
            {
             newCompositeNode(grammarAccess.getIndependentUnitRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleIndependentUnit=ruleIndependentUnit();

            state._fsp--;

             current =iv_ruleIndependentUnit; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIndependentUnit"


    // $ANTLR start "ruleIndependentUnit"
    // InternalHenshin_text.g:2599:1: ruleIndependentUnit returns [EObject current=null] : (otherlv_0= 'independent' otherlv_1= '[' ( (lv_listOfLists_2_0= ruleList ) ) (otherlv_3= ',' ( (lv_listOfLists_4_0= ruleList ) ) )* otherlv_5= ']' ) ;
    public final EObject ruleIndependentUnit() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_listOfLists_2_0 = null;

        EObject lv_listOfLists_4_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:2605:2: ( (otherlv_0= 'independent' otherlv_1= '[' ( (lv_listOfLists_2_0= ruleList ) ) (otherlv_3= ',' ( (lv_listOfLists_4_0= ruleList ) ) )* otherlv_5= ']' ) )
            // InternalHenshin_text.g:2606:2: (otherlv_0= 'independent' otherlv_1= '[' ( (lv_listOfLists_2_0= ruleList ) ) (otherlv_3= ',' ( (lv_listOfLists_4_0= ruleList ) ) )* otherlv_5= ']' )
            {
            // InternalHenshin_text.g:2606:2: (otherlv_0= 'independent' otherlv_1= '[' ( (lv_listOfLists_2_0= ruleList ) ) (otherlv_3= ',' ( (lv_listOfLists_4_0= ruleList ) ) )* otherlv_5= ']' )
            // InternalHenshin_text.g:2607:3: otherlv_0= 'independent' otherlv_1= '[' ( (lv_listOfLists_2_0= ruleList ) ) (otherlv_3= ',' ( (lv_listOfLists_4_0= ruleList ) ) )* otherlv_5= ']'
            {
            otherlv_0=(Token)match(input,51,FOLLOW_17); 

            			newLeafNode(otherlv_0, grammarAccess.getIndependentUnitAccess().getIndependentKeyword_0());
            		
            otherlv_1=(Token)match(input,26,FOLLOW_14); 

            			newLeafNode(otherlv_1, grammarAccess.getIndependentUnitAccess().getLeftSquareBracketKeyword_1());
            		
            // InternalHenshin_text.g:2615:3: ( (lv_listOfLists_2_0= ruleList ) )
            // InternalHenshin_text.g:2616:4: (lv_listOfLists_2_0= ruleList )
            {
            // InternalHenshin_text.g:2616:4: (lv_listOfLists_2_0= ruleList )
            // InternalHenshin_text.g:2617:5: lv_listOfLists_2_0= ruleList
            {

            					newCompositeNode(grammarAccess.getIndependentUnitAccess().getListOfListsListParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_19);
            lv_listOfLists_2_0=ruleList();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getIndependentUnitRule());
            					}
            					add(
            						current,
            						"listOfLists",
            						lv_listOfLists_2_0,
            						"org.eclipse.emf.henshin.text.Henshin_text.List");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalHenshin_text.g:2634:3: (otherlv_3= ',' ( (lv_listOfLists_4_0= ruleList ) ) )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==17) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // InternalHenshin_text.g:2635:4: otherlv_3= ',' ( (lv_listOfLists_4_0= ruleList ) )
            	    {
            	    otherlv_3=(Token)match(input,17,FOLLOW_14); 

            	    				newLeafNode(otherlv_3, grammarAccess.getIndependentUnitAccess().getCommaKeyword_3_0());
            	    			
            	    // InternalHenshin_text.g:2639:4: ( (lv_listOfLists_4_0= ruleList ) )
            	    // InternalHenshin_text.g:2640:5: (lv_listOfLists_4_0= ruleList )
            	    {
            	    // InternalHenshin_text.g:2640:5: (lv_listOfLists_4_0= ruleList )
            	    // InternalHenshin_text.g:2641:6: lv_listOfLists_4_0= ruleList
            	    {

            	    						newCompositeNode(grammarAccess.getIndependentUnitAccess().getListOfListsListParserRuleCall_3_1_0());
            	    					
            	    pushFollow(FOLLOW_19);
            	    lv_listOfLists_4_0=ruleList();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getIndependentUnitRule());
            	    						}
            	    						add(
            	    							current,
            	    							"listOfLists",
            	    							lv_listOfLists_4_0,
            	    							"org.eclipse.emf.henshin.text.Henshin_text.List");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop44;
                }
            } while (true);

            otherlv_5=(Token)match(input,27,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getIndependentUnitAccess().getRightSquareBracketKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIndependentUnit"


    // $ANTLR start "entryRuleConditionalUnit"
    // InternalHenshin_text.g:2667:1: entryRuleConditionalUnit returns [EObject current=null] : iv_ruleConditionalUnit= ruleConditionalUnit EOF ;
    public final EObject entryRuleConditionalUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConditionalUnit = null;


        try {
            // InternalHenshin_text.g:2667:56: (iv_ruleConditionalUnit= ruleConditionalUnit EOF )
            // InternalHenshin_text.g:2668:2: iv_ruleConditionalUnit= ruleConditionalUnit EOF
            {
             newCompositeNode(grammarAccess.getConditionalUnitRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConditionalUnit=ruleConditionalUnit();

            state._fsp--;

             current =iv_ruleConditionalUnit; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConditionalUnit"


    // $ANTLR start "ruleConditionalUnit"
    // InternalHenshin_text.g:2674:1: ruleConditionalUnit returns [EObject current=null] : (otherlv_0= 'if' otherlv_1= '(' ( (lv_if_2_0= ruleUnitElement ) )+ otherlv_3= ')' otherlv_4= 'then' otherlv_5= '{' ( (lv_then_6_0= ruleUnitElement ) )+ otherlv_7= '}' (otherlv_8= 'else' otherlv_9= '{' ( (lv_else_10_0= ruleUnitElement ) )+ otherlv_11= '}' )? ) ;
    public final EObject ruleConditionalUnit() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        EObject lv_if_2_0 = null;

        EObject lv_then_6_0 = null;

        EObject lv_else_10_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:2680:2: ( (otherlv_0= 'if' otherlv_1= '(' ( (lv_if_2_0= ruleUnitElement ) )+ otherlv_3= ')' otherlv_4= 'then' otherlv_5= '{' ( (lv_then_6_0= ruleUnitElement ) )+ otherlv_7= '}' (otherlv_8= 'else' otherlv_9= '{' ( (lv_else_10_0= ruleUnitElement ) )+ otherlv_11= '}' )? ) )
            // InternalHenshin_text.g:2681:2: (otherlv_0= 'if' otherlv_1= '(' ( (lv_if_2_0= ruleUnitElement ) )+ otherlv_3= ')' otherlv_4= 'then' otherlv_5= '{' ( (lv_then_6_0= ruleUnitElement ) )+ otherlv_7= '}' (otherlv_8= 'else' otherlv_9= '{' ( (lv_else_10_0= ruleUnitElement ) )+ otherlv_11= '}' )? )
            {
            // InternalHenshin_text.g:2681:2: (otherlv_0= 'if' otherlv_1= '(' ( (lv_if_2_0= ruleUnitElement ) )+ otherlv_3= ')' otherlv_4= 'then' otherlv_5= '{' ( (lv_then_6_0= ruleUnitElement ) )+ otherlv_7= '}' (otherlv_8= 'else' otherlv_9= '{' ( (lv_else_10_0= ruleUnitElement ) )+ otherlv_11= '}' )? )
            // InternalHenshin_text.g:2682:3: otherlv_0= 'if' otherlv_1= '(' ( (lv_if_2_0= ruleUnitElement ) )+ otherlv_3= ')' otherlv_4= 'then' otherlv_5= '{' ( (lv_then_6_0= ruleUnitElement ) )+ otherlv_7= '}' (otherlv_8= 'else' otherlv_9= '{' ( (lv_else_10_0= ruleUnitElement ) )+ otherlv_11= '}' )?
            {
            otherlv_0=(Token)match(input,52,FOLLOW_7); 

            			newLeafNode(otherlv_0, grammarAccess.getConditionalUnitAccess().getIfKeyword_0());
            		
            otherlv_1=(Token)match(input,16,FOLLOW_14); 

            			newLeafNode(otherlv_1, grammarAccess.getConditionalUnitAccess().getLeftParenthesisKeyword_1());
            		
            // InternalHenshin_text.g:2690:3: ( (lv_if_2_0= ruleUnitElement ) )+
            int cnt45=0;
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==RULE_ID||LA45_0==19||(LA45_0>=49 && LA45_0<=52)||(LA45_0>=55 && LA45_0<=57)) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalHenshin_text.g:2691:4: (lv_if_2_0= ruleUnitElement )
            	    {
            	    // InternalHenshin_text.g:2691:4: (lv_if_2_0= ruleUnitElement )
            	    // InternalHenshin_text.g:2692:5: lv_if_2_0= ruleUnitElement
            	    {

            	    					newCompositeNode(grammarAccess.getConditionalUnitAccess().getIfUnitElementParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_39);
            	    lv_if_2_0=ruleUnitElement();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getConditionalUnitRule());
            	    					}
            	    					add(
            	    						current,
            	    						"if",
            	    						lv_if_2_0,
            	    						"org.eclipse.emf.henshin.text.Henshin_text.UnitElement");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt45 >= 1 ) break loop45;
                        EarlyExitException eee =
                            new EarlyExitException(45, input);
                        throw eee;
                }
                cnt45++;
            } while (true);

            otherlv_3=(Token)match(input,18,FOLLOW_40); 

            			newLeafNode(otherlv_3, grammarAccess.getConditionalUnitAccess().getRightParenthesisKeyword_3());
            		
            otherlv_4=(Token)match(input,53,FOLLOW_11); 

            			newLeafNode(otherlv_4, grammarAccess.getConditionalUnitAccess().getThenKeyword_4());
            		
            otherlv_5=(Token)match(input,19,FOLLOW_14); 

            			newLeafNode(otherlv_5, grammarAccess.getConditionalUnitAccess().getLeftCurlyBracketKeyword_5());
            		
            // InternalHenshin_text.g:2721:3: ( (lv_then_6_0= ruleUnitElement ) )+
            int cnt46=0;
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==RULE_ID||LA46_0==19||(LA46_0>=49 && LA46_0<=52)||(LA46_0>=55 && LA46_0<=57)) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalHenshin_text.g:2722:4: (lv_then_6_0= ruleUnitElement )
            	    {
            	    // InternalHenshin_text.g:2722:4: (lv_then_6_0= ruleUnitElement )
            	    // InternalHenshin_text.g:2723:5: lv_then_6_0= ruleUnitElement
            	    {

            	    					newCompositeNode(grammarAccess.getConditionalUnitAccess().getThenUnitElementParserRuleCall_6_0());
            	    				
            	    pushFollow(FOLLOW_15);
            	    lv_then_6_0=ruleUnitElement();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getConditionalUnitRule());
            	    					}
            	    					add(
            	    						current,
            	    						"then",
            	    						lv_then_6_0,
            	    						"org.eclipse.emf.henshin.text.Henshin_text.UnitElement");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt46 >= 1 ) break loop46;
                        EarlyExitException eee =
                            new EarlyExitException(46, input);
                        throw eee;
                }
                cnt46++;
            } while (true);

            otherlv_7=(Token)match(input,20,FOLLOW_41); 

            			newLeafNode(otherlv_7, grammarAccess.getConditionalUnitAccess().getRightCurlyBracketKeyword_7());
            		
            // InternalHenshin_text.g:2744:3: (otherlv_8= 'else' otherlv_9= '{' ( (lv_else_10_0= ruleUnitElement ) )+ otherlv_11= '}' )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==54) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // InternalHenshin_text.g:2745:4: otherlv_8= 'else' otherlv_9= '{' ( (lv_else_10_0= ruleUnitElement ) )+ otherlv_11= '}'
                    {
                    otherlv_8=(Token)match(input,54,FOLLOW_11); 

                    				newLeafNode(otherlv_8, grammarAccess.getConditionalUnitAccess().getElseKeyword_8_0());
                    			
                    otherlv_9=(Token)match(input,19,FOLLOW_14); 

                    				newLeafNode(otherlv_9, grammarAccess.getConditionalUnitAccess().getLeftCurlyBracketKeyword_8_1());
                    			
                    // InternalHenshin_text.g:2753:4: ( (lv_else_10_0= ruleUnitElement ) )+
                    int cnt47=0;
                    loop47:
                    do {
                        int alt47=2;
                        int LA47_0 = input.LA(1);

                        if ( (LA47_0==RULE_ID||LA47_0==19||(LA47_0>=49 && LA47_0<=52)||(LA47_0>=55 && LA47_0<=57)) ) {
                            alt47=1;
                        }


                        switch (alt47) {
                    	case 1 :
                    	    // InternalHenshin_text.g:2754:5: (lv_else_10_0= ruleUnitElement )
                    	    {
                    	    // InternalHenshin_text.g:2754:5: (lv_else_10_0= ruleUnitElement )
                    	    // InternalHenshin_text.g:2755:6: lv_else_10_0= ruleUnitElement
                    	    {

                    	    						newCompositeNode(grammarAccess.getConditionalUnitAccess().getElseUnitElementParserRuleCall_8_2_0());
                    	    					
                    	    pushFollow(FOLLOW_15);
                    	    lv_else_10_0=ruleUnitElement();

                    	    state._fsp--;


                    	    						if (current==null) {
                    	    							current = createModelElementForParent(grammarAccess.getConditionalUnitRule());
                    	    						}
                    	    						add(
                    	    							current,
                    	    							"else",
                    	    							lv_else_10_0,
                    	    							"org.eclipse.emf.henshin.text.Henshin_text.UnitElement");
                    	    						afterParserOrEnumRuleCall();
                    	    					

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt47 >= 1 ) break loop47;
                                EarlyExitException eee =
                                    new EarlyExitException(47, input);
                                throw eee;
                        }
                        cnt47++;
                    } while (true);

                    otherlv_11=(Token)match(input,20,FOLLOW_2); 

                    				newLeafNode(otherlv_11, grammarAccess.getConditionalUnitAccess().getRightCurlyBracketKeyword_8_3());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConditionalUnit"


    // $ANTLR start "entryRulePriorityUnit"
    // InternalHenshin_text.g:2781:1: entryRulePriorityUnit returns [EObject current=null] : iv_rulePriorityUnit= rulePriorityUnit EOF ;
    public final EObject entryRulePriorityUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePriorityUnit = null;


        try {
            // InternalHenshin_text.g:2781:53: (iv_rulePriorityUnit= rulePriorityUnit EOF )
            // InternalHenshin_text.g:2782:2: iv_rulePriorityUnit= rulePriorityUnit EOF
            {
             newCompositeNode(grammarAccess.getPriorityUnitRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePriorityUnit=rulePriorityUnit();

            state._fsp--;

             current =iv_rulePriorityUnit; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePriorityUnit"


    // $ANTLR start "rulePriorityUnit"
    // InternalHenshin_text.g:2788:1: rulePriorityUnit returns [EObject current=null] : (otherlv_0= 'priority' otherlv_1= '[' ( (lv_listOfLists_2_0= ruleList ) ) (otherlv_3= ',' ( (lv_listOfLists_4_0= ruleList ) ) )* otherlv_5= ']' ) ;
    public final EObject rulePriorityUnit() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_listOfLists_2_0 = null;

        EObject lv_listOfLists_4_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:2794:2: ( (otherlv_0= 'priority' otherlv_1= '[' ( (lv_listOfLists_2_0= ruleList ) ) (otherlv_3= ',' ( (lv_listOfLists_4_0= ruleList ) ) )* otherlv_5= ']' ) )
            // InternalHenshin_text.g:2795:2: (otherlv_0= 'priority' otherlv_1= '[' ( (lv_listOfLists_2_0= ruleList ) ) (otherlv_3= ',' ( (lv_listOfLists_4_0= ruleList ) ) )* otherlv_5= ']' )
            {
            // InternalHenshin_text.g:2795:2: (otherlv_0= 'priority' otherlv_1= '[' ( (lv_listOfLists_2_0= ruleList ) ) (otherlv_3= ',' ( (lv_listOfLists_4_0= ruleList ) ) )* otherlv_5= ']' )
            // InternalHenshin_text.g:2796:3: otherlv_0= 'priority' otherlv_1= '[' ( (lv_listOfLists_2_0= ruleList ) ) (otherlv_3= ',' ( (lv_listOfLists_4_0= ruleList ) ) )* otherlv_5= ']'
            {
            otherlv_0=(Token)match(input,55,FOLLOW_17); 

            			newLeafNode(otherlv_0, grammarAccess.getPriorityUnitAccess().getPriorityKeyword_0());
            		
            otherlv_1=(Token)match(input,26,FOLLOW_14); 

            			newLeafNode(otherlv_1, grammarAccess.getPriorityUnitAccess().getLeftSquareBracketKeyword_1());
            		
            // InternalHenshin_text.g:2804:3: ( (lv_listOfLists_2_0= ruleList ) )
            // InternalHenshin_text.g:2805:4: (lv_listOfLists_2_0= ruleList )
            {
            // InternalHenshin_text.g:2805:4: (lv_listOfLists_2_0= ruleList )
            // InternalHenshin_text.g:2806:5: lv_listOfLists_2_0= ruleList
            {

            					newCompositeNode(grammarAccess.getPriorityUnitAccess().getListOfListsListParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_19);
            lv_listOfLists_2_0=ruleList();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPriorityUnitRule());
            					}
            					add(
            						current,
            						"listOfLists",
            						lv_listOfLists_2_0,
            						"org.eclipse.emf.henshin.text.Henshin_text.List");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalHenshin_text.g:2823:3: (otherlv_3= ',' ( (lv_listOfLists_4_0= ruleList ) ) )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==17) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // InternalHenshin_text.g:2824:4: otherlv_3= ',' ( (lv_listOfLists_4_0= ruleList ) )
            	    {
            	    otherlv_3=(Token)match(input,17,FOLLOW_14); 

            	    				newLeafNode(otherlv_3, grammarAccess.getPriorityUnitAccess().getCommaKeyword_3_0());
            	    			
            	    // InternalHenshin_text.g:2828:4: ( (lv_listOfLists_4_0= ruleList ) )
            	    // InternalHenshin_text.g:2829:5: (lv_listOfLists_4_0= ruleList )
            	    {
            	    // InternalHenshin_text.g:2829:5: (lv_listOfLists_4_0= ruleList )
            	    // InternalHenshin_text.g:2830:6: lv_listOfLists_4_0= ruleList
            	    {

            	    						newCompositeNode(grammarAccess.getPriorityUnitAccess().getListOfListsListParserRuleCall_3_1_0());
            	    					
            	    pushFollow(FOLLOW_19);
            	    lv_listOfLists_4_0=ruleList();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getPriorityUnitRule());
            	    						}
            	    						add(
            	    							current,
            	    							"listOfLists",
            	    							lv_listOfLists_4_0,
            	    							"org.eclipse.emf.henshin.text.Henshin_text.List");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop49;
                }
            } while (true);

            otherlv_5=(Token)match(input,27,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getPriorityUnitAccess().getRightSquareBracketKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePriorityUnit"


    // $ANTLR start "entryRuleIteratedUnit"
    // InternalHenshin_text.g:2856:1: entryRuleIteratedUnit returns [EObject current=null] : iv_ruleIteratedUnit= ruleIteratedUnit EOF ;
    public final EObject entryRuleIteratedUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIteratedUnit = null;


        try {
            // InternalHenshin_text.g:2856:53: (iv_ruleIteratedUnit= ruleIteratedUnit EOF )
            // InternalHenshin_text.g:2857:2: iv_ruleIteratedUnit= ruleIteratedUnit EOF
            {
             newCompositeNode(grammarAccess.getIteratedUnitRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleIteratedUnit=ruleIteratedUnit();

            state._fsp--;

             current =iv_ruleIteratedUnit; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIteratedUnit"


    // $ANTLR start "ruleIteratedUnit"
    // InternalHenshin_text.g:2863:1: ruleIteratedUnit returns [EObject current=null] : ( () otherlv_1= 'for' otherlv_2= '(' ( (lv_iterations_3_0= ruleExpression ) ) otherlv_4= ')' otherlv_5= '{' ( (lv_subElement_6_0= ruleUnitElement ) )+ otherlv_7= '}' ) ;
    public final EObject ruleIteratedUnit() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        EObject lv_iterations_3_0 = null;

        EObject lv_subElement_6_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:2869:2: ( ( () otherlv_1= 'for' otherlv_2= '(' ( (lv_iterations_3_0= ruleExpression ) ) otherlv_4= ')' otherlv_5= '{' ( (lv_subElement_6_0= ruleUnitElement ) )+ otherlv_7= '}' ) )
            // InternalHenshin_text.g:2870:2: ( () otherlv_1= 'for' otherlv_2= '(' ( (lv_iterations_3_0= ruleExpression ) ) otherlv_4= ')' otherlv_5= '{' ( (lv_subElement_6_0= ruleUnitElement ) )+ otherlv_7= '}' )
            {
            // InternalHenshin_text.g:2870:2: ( () otherlv_1= 'for' otherlv_2= '(' ( (lv_iterations_3_0= ruleExpression ) ) otherlv_4= ')' otherlv_5= '{' ( (lv_subElement_6_0= ruleUnitElement ) )+ otherlv_7= '}' )
            // InternalHenshin_text.g:2871:3: () otherlv_1= 'for' otherlv_2= '(' ( (lv_iterations_3_0= ruleExpression ) ) otherlv_4= ')' otherlv_5= '{' ( (lv_subElement_6_0= ruleUnitElement ) )+ otherlv_7= '}'
            {
            // InternalHenshin_text.g:2871:3: ()
            // InternalHenshin_text.g:2872:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getIteratedUnitAccess().getIteratedUnitAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,56,FOLLOW_7); 

            			newLeafNode(otherlv_1, grammarAccess.getIteratedUnitAccess().getForKeyword_1());
            		
            otherlv_2=(Token)match(input,16,FOLLOW_18); 

            			newLeafNode(otherlv_2, grammarAccess.getIteratedUnitAccess().getLeftParenthesisKeyword_2());
            		
            // InternalHenshin_text.g:2886:3: ( (lv_iterations_3_0= ruleExpression ) )
            // InternalHenshin_text.g:2887:4: (lv_iterations_3_0= ruleExpression )
            {
            // InternalHenshin_text.g:2887:4: (lv_iterations_3_0= ruleExpression )
            // InternalHenshin_text.g:2888:5: lv_iterations_3_0= ruleExpression
            {

            					newCompositeNode(grammarAccess.getIteratedUnitAccess().getIterationsExpressionParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_24);
            lv_iterations_3_0=ruleExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getIteratedUnitRule());
            					}
            					set(
            						current,
            						"iterations",
            						lv_iterations_3_0,
            						"org.eclipse.emf.henshin.text.Henshin_text.Expression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_4=(Token)match(input,18,FOLLOW_11); 

            			newLeafNode(otherlv_4, grammarAccess.getIteratedUnitAccess().getRightParenthesisKeyword_4());
            		
            otherlv_5=(Token)match(input,19,FOLLOW_14); 

            			newLeafNode(otherlv_5, grammarAccess.getIteratedUnitAccess().getLeftCurlyBracketKeyword_5());
            		
            // InternalHenshin_text.g:2913:3: ( (lv_subElement_6_0= ruleUnitElement ) )+
            int cnt50=0;
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==RULE_ID||LA50_0==19||(LA50_0>=49 && LA50_0<=52)||(LA50_0>=55 && LA50_0<=57)) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // InternalHenshin_text.g:2914:4: (lv_subElement_6_0= ruleUnitElement )
            	    {
            	    // InternalHenshin_text.g:2914:4: (lv_subElement_6_0= ruleUnitElement )
            	    // InternalHenshin_text.g:2915:5: lv_subElement_6_0= ruleUnitElement
            	    {

            	    					newCompositeNode(grammarAccess.getIteratedUnitAccess().getSubElementUnitElementParserRuleCall_6_0());
            	    				
            	    pushFollow(FOLLOW_15);
            	    lv_subElement_6_0=ruleUnitElement();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getIteratedUnitRule());
            	    					}
            	    					add(
            	    						current,
            	    						"subElement",
            	    						lv_subElement_6_0,
            	    						"org.eclipse.emf.henshin.text.Henshin_text.UnitElement");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt50 >= 1 ) break loop50;
                        EarlyExitException eee =
                            new EarlyExitException(50, input);
                        throw eee;
                }
                cnt50++;
            } while (true);

            otherlv_7=(Token)match(input,20,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getIteratedUnitAccess().getRightCurlyBracketKeyword_7());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIteratedUnit"


    // $ANTLR start "entryRuleLoopUnit"
    // InternalHenshin_text.g:2940:1: entryRuleLoopUnit returns [EObject current=null] : iv_ruleLoopUnit= ruleLoopUnit EOF ;
    public final EObject entryRuleLoopUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLoopUnit = null;


        try {
            // InternalHenshin_text.g:2940:49: (iv_ruleLoopUnit= ruleLoopUnit EOF )
            // InternalHenshin_text.g:2941:2: iv_ruleLoopUnit= ruleLoopUnit EOF
            {
             newCompositeNode(grammarAccess.getLoopUnitRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLoopUnit=ruleLoopUnit();

            state._fsp--;

             current =iv_ruleLoopUnit; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLoopUnit"


    // $ANTLR start "ruleLoopUnit"
    // InternalHenshin_text.g:2947:1: ruleLoopUnit returns [EObject current=null] : (otherlv_0= 'while' otherlv_1= '{' ( (lv_subElement_2_0= ruleUnitElement ) )+ otherlv_3= '}' ) ;
    public final EObject ruleLoopUnit() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_subElement_2_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:2953:2: ( (otherlv_0= 'while' otherlv_1= '{' ( (lv_subElement_2_0= ruleUnitElement ) )+ otherlv_3= '}' ) )
            // InternalHenshin_text.g:2954:2: (otherlv_0= 'while' otherlv_1= '{' ( (lv_subElement_2_0= ruleUnitElement ) )+ otherlv_3= '}' )
            {
            // InternalHenshin_text.g:2954:2: (otherlv_0= 'while' otherlv_1= '{' ( (lv_subElement_2_0= ruleUnitElement ) )+ otherlv_3= '}' )
            // InternalHenshin_text.g:2955:3: otherlv_0= 'while' otherlv_1= '{' ( (lv_subElement_2_0= ruleUnitElement ) )+ otherlv_3= '}'
            {
            otherlv_0=(Token)match(input,57,FOLLOW_11); 

            			newLeafNode(otherlv_0, grammarAccess.getLoopUnitAccess().getWhileKeyword_0());
            		
            otherlv_1=(Token)match(input,19,FOLLOW_14); 

            			newLeafNode(otherlv_1, grammarAccess.getLoopUnitAccess().getLeftCurlyBracketKeyword_1());
            		
            // InternalHenshin_text.g:2963:3: ( (lv_subElement_2_0= ruleUnitElement ) )+
            int cnt51=0;
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==RULE_ID||LA51_0==19||(LA51_0>=49 && LA51_0<=52)||(LA51_0>=55 && LA51_0<=57)) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // InternalHenshin_text.g:2964:4: (lv_subElement_2_0= ruleUnitElement )
            	    {
            	    // InternalHenshin_text.g:2964:4: (lv_subElement_2_0= ruleUnitElement )
            	    // InternalHenshin_text.g:2965:5: lv_subElement_2_0= ruleUnitElement
            	    {

            	    					newCompositeNode(grammarAccess.getLoopUnitAccess().getSubElementUnitElementParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_15);
            	    lv_subElement_2_0=ruleUnitElement();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getLoopUnitRule());
            	    					}
            	    					add(
            	    						current,
            	    						"subElement",
            	    						lv_subElement_2_0,
            	    						"org.eclipse.emf.henshin.text.Henshin_text.UnitElement");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt51 >= 1 ) break loop51;
                        EarlyExitException eee =
                            new EarlyExitException(51, input);
                        throw eee;
                }
                cnt51++;
            } while (true);

            otherlv_3=(Token)match(input,20,FOLLOW_2); 

            			newLeafNode(otherlv_3, grammarAccess.getLoopUnitAccess().getRightCurlyBracketKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLoopUnit"


    // $ANTLR start "entryRuleParameter"
    // InternalHenshin_text.g:2990:1: entryRuleParameter returns [EObject current=null] : iv_ruleParameter= ruleParameter EOF ;
    public final EObject entryRuleParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameter = null;


        try {
            // InternalHenshin_text.g:2990:50: (iv_ruleParameter= ruleParameter EOF )
            // InternalHenshin_text.g:2991:2: iv_ruleParameter= ruleParameter EOF
            {
             newCompositeNode(grammarAccess.getParameterRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleParameter=ruleParameter();

            state._fsp--;

             current =iv_ruleParameter; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParameter"


    // $ANTLR start "ruleParameter"
    // InternalHenshin_text.g:2997:1: ruleParameter returns [EObject current=null] : ( ( (lv_kind_0_0= ruleParameterKindRule ) )? ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_type_3_0= ruleParameterType ) ) ) ;
    public final EObject ruleParameter() throws RecognitionException {
        EObject current = null;

        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Enumerator lv_kind_0_0 = null;

        EObject lv_type_3_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:3003:2: ( ( ( (lv_kind_0_0= ruleParameterKindRule ) )? ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_type_3_0= ruleParameterType ) ) ) )
            // InternalHenshin_text.g:3004:2: ( ( (lv_kind_0_0= ruleParameterKindRule ) )? ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_type_3_0= ruleParameterType ) ) )
            {
            // InternalHenshin_text.g:3004:2: ( ( (lv_kind_0_0= ruleParameterKindRule ) )? ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_type_3_0= ruleParameterType ) ) )
            // InternalHenshin_text.g:3005:3: ( (lv_kind_0_0= ruleParameterKindRule ) )? ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_type_3_0= ruleParameterType ) )
            {
            // InternalHenshin_text.g:3005:3: ( (lv_kind_0_0= ruleParameterKindRule ) )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( ((LA52_0>=70 && LA52_0<=73)) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // InternalHenshin_text.g:3006:4: (lv_kind_0_0= ruleParameterKindRule )
                    {
                    // InternalHenshin_text.g:3006:4: (lv_kind_0_0= ruleParameterKindRule )
                    // InternalHenshin_text.g:3007:5: lv_kind_0_0= ruleParameterKindRule
                    {

                    					newCompositeNode(grammarAccess.getParameterAccess().getKindParameterKindRuleEnumRuleCall_0_0());
                    				
                    pushFollow(FOLLOW_5);
                    lv_kind_0_0=ruleParameterKindRule();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getParameterRule());
                    					}
                    					set(
                    						current,
                    						"kind",
                    						lv_kind_0_0,
                    						"org.eclipse.emf.henshin.text.Henshin_text.ParameterKindRule");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalHenshin_text.g:3024:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalHenshin_text.g:3025:4: (lv_name_1_0= RULE_ID )
            {
            // InternalHenshin_text.g:3025:4: (lv_name_1_0= RULE_ID )
            // InternalHenshin_text.g:3026:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_23); 

            					newLeafNode(lv_name_1_0, grammarAccess.getParameterAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getParameterRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,36,FOLLOW_42); 

            			newLeafNode(otherlv_2, grammarAccess.getParameterAccess().getColonKeyword_2());
            		
            // InternalHenshin_text.g:3046:3: ( (lv_type_3_0= ruleParameterType ) )
            // InternalHenshin_text.g:3047:4: (lv_type_3_0= ruleParameterType )
            {
            // InternalHenshin_text.g:3047:4: (lv_type_3_0= ruleParameterType )
            // InternalHenshin_text.g:3048:5: lv_type_3_0= ruleParameterType
            {

            					newCompositeNode(grammarAccess.getParameterAccess().getTypeParameterTypeParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_2);
            lv_type_3_0=ruleParameterType();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getParameterRule());
            					}
            					set(
            						current,
            						"type",
            						lv_type_3_0,
            						"org.eclipse.emf.henshin.text.Henshin_text.ParameterType");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParameter"


    // $ANTLR start "entryRuleParameterType"
    // InternalHenshin_text.g:3069:1: entryRuleParameterType returns [EObject current=null] : iv_ruleParameterType= ruleParameterType EOF ;
    public final EObject entryRuleParameterType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterType = null;


        try {
            // InternalHenshin_text.g:3069:54: (iv_ruleParameterType= ruleParameterType EOF )
            // InternalHenshin_text.g:3070:2: iv_ruleParameterType= ruleParameterType EOF
            {
             newCompositeNode(grammarAccess.getParameterTypeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleParameterType=ruleParameterType();

            state._fsp--;

             current =iv_ruleParameterType; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParameterType"


    // $ANTLR start "ruleParameterType"
    // InternalHenshin_text.g:3076:1: ruleParameterType returns [EObject current=null] : ( ( (lv_enumType_0_0= ruleType ) ) | ( ( ruleEString ) ) ) ;
    public final EObject ruleParameterType() throws RecognitionException {
        EObject current = null;

        Enumerator lv_enumType_0_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:3082:2: ( ( ( (lv_enumType_0_0= ruleType ) ) | ( ( ruleEString ) ) ) )
            // InternalHenshin_text.g:3083:2: ( ( (lv_enumType_0_0= ruleType ) ) | ( ( ruleEString ) ) )
            {
            // InternalHenshin_text.g:3083:2: ( ( (lv_enumType_0_0= ruleType ) ) | ( ( ruleEString ) ) )
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( ((LA53_0>=74 && LA53_0<=106)) ) {
                alt53=1;
            }
            else if ( (LA53_0==RULE_ID) ) {
                alt53=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 53, 0, input);

                throw nvae;
            }
            switch (alt53) {
                case 1 :
                    // InternalHenshin_text.g:3084:3: ( (lv_enumType_0_0= ruleType ) )
                    {
                    // InternalHenshin_text.g:3084:3: ( (lv_enumType_0_0= ruleType ) )
                    // InternalHenshin_text.g:3085:4: (lv_enumType_0_0= ruleType )
                    {
                    // InternalHenshin_text.g:3085:4: (lv_enumType_0_0= ruleType )
                    // InternalHenshin_text.g:3086:5: lv_enumType_0_0= ruleType
                    {

                    					newCompositeNode(grammarAccess.getParameterTypeAccess().getEnumTypeTypeEnumRuleCall_0_0());
                    				
                    pushFollow(FOLLOW_2);
                    lv_enumType_0_0=ruleType();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getParameterTypeRule());
                    					}
                    					set(
                    						current,
                    						"enumType",
                    						lv_enumType_0_0,
                    						"org.eclipse.emf.henshin.text.Henshin_text.Type");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalHenshin_text.g:3104:3: ( ( ruleEString ) )
                    {
                    // InternalHenshin_text.g:3104:3: ( ( ruleEString ) )
                    // InternalHenshin_text.g:3105:4: ( ruleEString )
                    {
                    // InternalHenshin_text.g:3105:4: ( ruleEString )
                    // InternalHenshin_text.g:3106:5: ruleEString
                    {

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getParameterTypeRule());
                    					}
                    				

                    					newCompositeNode(grammarAccess.getParameterTypeAccess().getTypeEClassCrossReference_1_0());
                    				
                    pushFollow(FOLLOW_2);
                    ruleEString();

                    state._fsp--;


                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParameterType"


    // $ANTLR start "entryRuleEBoolean"
    // InternalHenshin_text.g:3124:1: entryRuleEBoolean returns [String current=null] : iv_ruleEBoolean= ruleEBoolean EOF ;
    public final String entryRuleEBoolean() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleEBoolean = null;


        try {
            // InternalHenshin_text.g:3124:48: (iv_ruleEBoolean= ruleEBoolean EOF )
            // InternalHenshin_text.g:3125:2: iv_ruleEBoolean= ruleEBoolean EOF
            {
             newCompositeNode(grammarAccess.getEBooleanRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEBoolean=ruleEBoolean();

            state._fsp--;

             current =iv_ruleEBoolean.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEBoolean"


    // $ANTLR start "ruleEBoolean"
    // InternalHenshin_text.g:3131:1: ruleEBoolean returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'true' | kw= 'false' ) ;
    public final AntlrDatatypeRuleToken ruleEBoolean() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalHenshin_text.g:3137:2: ( (kw= 'true' | kw= 'false' ) )
            // InternalHenshin_text.g:3138:2: (kw= 'true' | kw= 'false' )
            {
            // InternalHenshin_text.g:3138:2: (kw= 'true' | kw= 'false' )
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==58) ) {
                alt54=1;
            }
            else if ( (LA54_0==59) ) {
                alt54=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 54, 0, input);

                throw nvae;
            }
            switch (alt54) {
                case 1 :
                    // InternalHenshin_text.g:3139:3: kw= 'true'
                    {
                    kw=(Token)match(input,58,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getEBooleanAccess().getTrueKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalHenshin_text.g:3145:3: kw= 'false'
                    {
                    kw=(Token)match(input,59,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getEBooleanAccess().getFalseKeyword_1());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEBoolean"


    // $ANTLR start "entryRuleExpression"
    // InternalHenshin_text.g:3154:1: entryRuleExpression returns [EObject current=null] : iv_ruleExpression= ruleExpression EOF ;
    public final EObject entryRuleExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpression = null;


        try {
            // InternalHenshin_text.g:3154:51: (iv_ruleExpression= ruleExpression EOF )
            // InternalHenshin_text.g:3155:2: iv_ruleExpression= ruleExpression EOF
            {
             newCompositeNode(grammarAccess.getExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExpression=ruleExpression();

            state._fsp--;

             current =iv_ruleExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpression"


    // $ANTLR start "ruleExpression"
    // InternalHenshin_text.g:3161:1: ruleExpression returns [EObject current=null] : this_OrExpression_0= ruleOrExpression ;
    public final EObject ruleExpression() throws RecognitionException {
        EObject current = null;

        EObject this_OrExpression_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:3167:2: (this_OrExpression_0= ruleOrExpression )
            // InternalHenshin_text.g:3168:2: this_OrExpression_0= ruleOrExpression
            {

            		newCompositeNode(grammarAccess.getExpressionAccess().getOrExpressionParserRuleCall());
            	
            pushFollow(FOLLOW_2);
            this_OrExpression_0=ruleOrExpression();

            state._fsp--;


            		current = this_OrExpression_0;
            		afterParserOrEnumRuleCall();
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpression"


    // $ANTLR start "entryRuleOrExpression"
    // InternalHenshin_text.g:3179:1: entryRuleOrExpression returns [EObject current=null] : iv_ruleOrExpression= ruleOrExpression EOF ;
    public final EObject entryRuleOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrExpression = null;


        try {
            // InternalHenshin_text.g:3179:53: (iv_ruleOrExpression= ruleOrExpression EOF )
            // InternalHenshin_text.g:3180:2: iv_ruleOrExpression= ruleOrExpression EOF
            {
             newCompositeNode(grammarAccess.getOrExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleOrExpression=ruleOrExpression();

            state._fsp--;

             current =iv_ruleOrExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOrExpression"


    // $ANTLR start "ruleOrExpression"
    // InternalHenshin_text.g:3186:1: ruleOrExpression returns [EObject current=null] : (this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'OR' ( (lv_right_3_0= ruleAndExpression ) ) )* ) ;
    public final EObject ruleOrExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_AndExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:3192:2: ( (this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'OR' ( (lv_right_3_0= ruleAndExpression ) ) )* ) )
            // InternalHenshin_text.g:3193:2: (this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'OR' ( (lv_right_3_0= ruleAndExpression ) ) )* )
            {
            // InternalHenshin_text.g:3193:2: (this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'OR' ( (lv_right_3_0= ruleAndExpression ) ) )* )
            // InternalHenshin_text.g:3194:3: this_AndExpression_0= ruleAndExpression ( () otherlv_2= 'OR' ( (lv_right_3_0= ruleAndExpression ) ) )*
            {

            			newCompositeNode(grammarAccess.getOrExpressionAccess().getAndExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_43);
            this_AndExpression_0=ruleAndExpression();

            state._fsp--;


            			current = this_AndExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalHenshin_text.g:3202:3: ( () otherlv_2= 'OR' ( (lv_right_3_0= ruleAndExpression ) ) )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==44) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // InternalHenshin_text.g:3203:4: () otherlv_2= 'OR' ( (lv_right_3_0= ruleAndExpression ) )
            	    {
            	    // InternalHenshin_text.g:3203:4: ()
            	    // InternalHenshin_text.g:3204:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getOrExpressionAccess().getOrExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    otherlv_2=(Token)match(input,44,FOLLOW_18); 

            	    				newLeafNode(otherlv_2, grammarAccess.getOrExpressionAccess().getORKeyword_1_1());
            	    			
            	    // InternalHenshin_text.g:3214:4: ( (lv_right_3_0= ruleAndExpression ) )
            	    // InternalHenshin_text.g:3215:5: (lv_right_3_0= ruleAndExpression )
            	    {
            	    // InternalHenshin_text.g:3215:5: (lv_right_3_0= ruleAndExpression )
            	    // InternalHenshin_text.g:3216:6: lv_right_3_0= ruleAndExpression
            	    {

            	    						newCompositeNode(grammarAccess.getOrExpressionAccess().getRightAndExpressionParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_43);
            	    lv_right_3_0=ruleAndExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getOrExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"org.eclipse.emf.henshin.text.Henshin_text.AndExpression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop55;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOrExpression"


    // $ANTLR start "entryRuleAndExpression"
    // InternalHenshin_text.g:3238:1: entryRuleAndExpression returns [EObject current=null] : iv_ruleAndExpression= ruleAndExpression EOF ;
    public final EObject entryRuleAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAndExpression = null;


        try {
            // InternalHenshin_text.g:3238:54: (iv_ruleAndExpression= ruleAndExpression EOF )
            // InternalHenshin_text.g:3239:2: iv_ruleAndExpression= ruleAndExpression EOF
            {
             newCompositeNode(grammarAccess.getAndExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAndExpression=ruleAndExpression();

            state._fsp--;

             current =iv_ruleAndExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAndExpression"


    // $ANTLR start "ruleAndExpression"
    // InternalHenshin_text.g:3245:1: ruleAndExpression returns [EObject current=null] : (this_EqualityExpression_0= ruleEqualityExpression ( () otherlv_2= 'AND' ( (lv_right_3_0= ruleEqualityExpression ) ) )* ) ;
    public final EObject ruleAndExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_EqualityExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:3251:2: ( (this_EqualityExpression_0= ruleEqualityExpression ( () otherlv_2= 'AND' ( (lv_right_3_0= ruleEqualityExpression ) ) )* ) )
            // InternalHenshin_text.g:3252:2: (this_EqualityExpression_0= ruleEqualityExpression ( () otherlv_2= 'AND' ( (lv_right_3_0= ruleEqualityExpression ) ) )* )
            {
            // InternalHenshin_text.g:3252:2: (this_EqualityExpression_0= ruleEqualityExpression ( () otherlv_2= 'AND' ( (lv_right_3_0= ruleEqualityExpression ) ) )* )
            // InternalHenshin_text.g:3253:3: this_EqualityExpression_0= ruleEqualityExpression ( () otherlv_2= 'AND' ( (lv_right_3_0= ruleEqualityExpression ) ) )*
            {

            			newCompositeNode(grammarAccess.getAndExpressionAccess().getEqualityExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_34);
            this_EqualityExpression_0=ruleEqualityExpression();

            state._fsp--;


            			current = this_EqualityExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalHenshin_text.g:3261:3: ( () otherlv_2= 'AND' ( (lv_right_3_0= ruleEqualityExpression ) ) )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==46) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // InternalHenshin_text.g:3262:4: () otherlv_2= 'AND' ( (lv_right_3_0= ruleEqualityExpression ) )
            	    {
            	    // InternalHenshin_text.g:3262:4: ()
            	    // InternalHenshin_text.g:3263:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getAndExpressionAccess().getAndExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    otherlv_2=(Token)match(input,46,FOLLOW_18); 

            	    				newLeafNode(otherlv_2, grammarAccess.getAndExpressionAccess().getANDKeyword_1_1());
            	    			
            	    // InternalHenshin_text.g:3273:4: ( (lv_right_3_0= ruleEqualityExpression ) )
            	    // InternalHenshin_text.g:3274:5: (lv_right_3_0= ruleEqualityExpression )
            	    {
            	    // InternalHenshin_text.g:3274:5: (lv_right_3_0= ruleEqualityExpression )
            	    // InternalHenshin_text.g:3275:6: lv_right_3_0= ruleEqualityExpression
            	    {

            	    						newCompositeNode(grammarAccess.getAndExpressionAccess().getRightEqualityExpressionParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_34);
            	    lv_right_3_0=ruleEqualityExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getAndExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"org.eclipse.emf.henshin.text.Henshin_text.EqualityExpression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop56;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAndExpression"


    // $ANTLR start "entryRuleEqualityExpression"
    // InternalHenshin_text.g:3297:1: entryRuleEqualityExpression returns [EObject current=null] : iv_ruleEqualityExpression= ruleEqualityExpression EOF ;
    public final EObject entryRuleEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEqualityExpression = null;


        try {
            // InternalHenshin_text.g:3297:59: (iv_ruleEqualityExpression= ruleEqualityExpression EOF )
            // InternalHenshin_text.g:3298:2: iv_ruleEqualityExpression= ruleEqualityExpression EOF
            {
             newCompositeNode(grammarAccess.getEqualityExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEqualityExpression=ruleEqualityExpression();

            state._fsp--;

             current =iv_ruleEqualityExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEqualityExpression"


    // $ANTLR start "ruleEqualityExpression"
    // InternalHenshin_text.g:3304:1: ruleEqualityExpression returns [EObject current=null] : (this_ComparisonExpression_0= ruleComparisonExpression ( () ( ( (lv_op_2_1= '==' | lv_op_2_2= '!=' ) ) ) ( (lv_right_3_0= ruleComparisonExpression ) ) )* ) ;
    public final EObject ruleEqualityExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        EObject this_ComparisonExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:3310:2: ( (this_ComparisonExpression_0= ruleComparisonExpression ( () ( ( (lv_op_2_1= '==' | lv_op_2_2= '!=' ) ) ) ( (lv_right_3_0= ruleComparisonExpression ) ) )* ) )
            // InternalHenshin_text.g:3311:2: (this_ComparisonExpression_0= ruleComparisonExpression ( () ( ( (lv_op_2_1= '==' | lv_op_2_2= '!=' ) ) ) ( (lv_right_3_0= ruleComparisonExpression ) ) )* )
            {
            // InternalHenshin_text.g:3311:2: (this_ComparisonExpression_0= ruleComparisonExpression ( () ( ( (lv_op_2_1= '==' | lv_op_2_2= '!=' ) ) ) ( (lv_right_3_0= ruleComparisonExpression ) ) )* )
            // InternalHenshin_text.g:3312:3: this_ComparisonExpression_0= ruleComparisonExpression ( () ( ( (lv_op_2_1= '==' | lv_op_2_2= '!=' ) ) ) ( (lv_right_3_0= ruleComparisonExpression ) ) )*
            {

            			newCompositeNode(grammarAccess.getEqualityExpressionAccess().getComparisonExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_44);
            this_ComparisonExpression_0=ruleComparisonExpression();

            state._fsp--;


            			current = this_ComparisonExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalHenshin_text.g:3320:3: ( () ( ( (lv_op_2_1= '==' | lv_op_2_2= '!=' ) ) ) ( (lv_right_3_0= ruleComparisonExpression ) ) )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( ((LA58_0>=60 && LA58_0<=61)) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // InternalHenshin_text.g:3321:4: () ( ( (lv_op_2_1= '==' | lv_op_2_2= '!=' ) ) ) ( (lv_right_3_0= ruleComparisonExpression ) )
            	    {
            	    // InternalHenshin_text.g:3321:4: ()
            	    // InternalHenshin_text.g:3322:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalHenshin_text.g:3328:4: ( ( (lv_op_2_1= '==' | lv_op_2_2= '!=' ) ) )
            	    // InternalHenshin_text.g:3329:5: ( (lv_op_2_1= '==' | lv_op_2_2= '!=' ) )
            	    {
            	    // InternalHenshin_text.g:3329:5: ( (lv_op_2_1= '==' | lv_op_2_2= '!=' ) )
            	    // InternalHenshin_text.g:3330:6: (lv_op_2_1= '==' | lv_op_2_2= '!=' )
            	    {
            	    // InternalHenshin_text.g:3330:6: (lv_op_2_1= '==' | lv_op_2_2= '!=' )
            	    int alt57=2;
            	    int LA57_0 = input.LA(1);

            	    if ( (LA57_0==60) ) {
            	        alt57=1;
            	    }
            	    else if ( (LA57_0==61) ) {
            	        alt57=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 57, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt57) {
            	        case 1 :
            	            // InternalHenshin_text.g:3331:7: lv_op_2_1= '=='
            	            {
            	            lv_op_2_1=(Token)match(input,60,FOLLOW_18); 

            	            							newLeafNode(lv_op_2_1, grammarAccess.getEqualityExpressionAccess().getOpEqualsSignEqualsSignKeyword_1_1_0_0());
            	            						

            	            							if (current==null) {
            	            								current = createModelElement(grammarAccess.getEqualityExpressionRule());
            	            							}
            	            							setWithLastConsumed(current, "op", lv_op_2_1, null);
            	            						

            	            }
            	            break;
            	        case 2 :
            	            // InternalHenshin_text.g:3342:7: lv_op_2_2= '!='
            	            {
            	            lv_op_2_2=(Token)match(input,61,FOLLOW_18); 

            	            							newLeafNode(lv_op_2_2, grammarAccess.getEqualityExpressionAccess().getOpExclamationMarkEqualsSignKeyword_1_1_0_1());
            	            						

            	            							if (current==null) {
            	            								current = createModelElement(grammarAccess.getEqualityExpressionRule());
            	            							}
            	            							setWithLastConsumed(current, "op", lv_op_2_2, null);
            	            						

            	            }
            	            break;

            	    }


            	    }


            	    }

            	    // InternalHenshin_text.g:3355:4: ( (lv_right_3_0= ruleComparisonExpression ) )
            	    // InternalHenshin_text.g:3356:5: (lv_right_3_0= ruleComparisonExpression )
            	    {
            	    // InternalHenshin_text.g:3356:5: (lv_right_3_0= ruleComparisonExpression )
            	    // InternalHenshin_text.g:3357:6: lv_right_3_0= ruleComparisonExpression
            	    {

            	    						newCompositeNode(grammarAccess.getEqualityExpressionAccess().getRightComparisonExpressionParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_44);
            	    lv_right_3_0=ruleComparisonExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getEqualityExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"org.eclipse.emf.henshin.text.Henshin_text.ComparisonExpression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop58;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEqualityExpression"


    // $ANTLR start "entryRuleComparisonExpression"
    // InternalHenshin_text.g:3379:1: entryRuleComparisonExpression returns [EObject current=null] : iv_ruleComparisonExpression= ruleComparisonExpression EOF ;
    public final EObject entryRuleComparisonExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComparisonExpression = null;


        try {
            // InternalHenshin_text.g:3379:61: (iv_ruleComparisonExpression= ruleComparisonExpression EOF )
            // InternalHenshin_text.g:3380:2: iv_ruleComparisonExpression= ruleComparisonExpression EOF
            {
             newCompositeNode(grammarAccess.getComparisonExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleComparisonExpression=ruleComparisonExpression();

            state._fsp--;

             current =iv_ruleComparisonExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleComparisonExpression"


    // $ANTLR start "ruleComparisonExpression"
    // InternalHenshin_text.g:3386:1: ruleComparisonExpression returns [EObject current=null] : (this_PlusOrMinusExpression_0= rulePlusOrMinusExpression ( () ( ( (lv_op_2_1= '>=' | lv_op_2_2= '<=' | lv_op_2_3= '>' | lv_op_2_4= '<' ) ) ) ( (lv_right_3_0= rulePlusOrMinusExpression ) ) )* ) ;
    public final EObject ruleComparisonExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        Token lv_op_2_3=null;
        Token lv_op_2_4=null;
        EObject this_PlusOrMinusExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:3392:2: ( (this_PlusOrMinusExpression_0= rulePlusOrMinusExpression ( () ( ( (lv_op_2_1= '>=' | lv_op_2_2= '<=' | lv_op_2_3= '>' | lv_op_2_4= '<' ) ) ) ( (lv_right_3_0= rulePlusOrMinusExpression ) ) )* ) )
            // InternalHenshin_text.g:3393:2: (this_PlusOrMinusExpression_0= rulePlusOrMinusExpression ( () ( ( (lv_op_2_1= '>=' | lv_op_2_2= '<=' | lv_op_2_3= '>' | lv_op_2_4= '<' ) ) ) ( (lv_right_3_0= rulePlusOrMinusExpression ) ) )* )
            {
            // InternalHenshin_text.g:3393:2: (this_PlusOrMinusExpression_0= rulePlusOrMinusExpression ( () ( ( (lv_op_2_1= '>=' | lv_op_2_2= '<=' | lv_op_2_3= '>' | lv_op_2_4= '<' ) ) ) ( (lv_right_3_0= rulePlusOrMinusExpression ) ) )* )
            // InternalHenshin_text.g:3394:3: this_PlusOrMinusExpression_0= rulePlusOrMinusExpression ( () ( ( (lv_op_2_1= '>=' | lv_op_2_2= '<=' | lv_op_2_3= '>' | lv_op_2_4= '<' ) ) ) ( (lv_right_3_0= rulePlusOrMinusExpression ) ) )*
            {

            			newCompositeNode(grammarAccess.getComparisonExpressionAccess().getPlusOrMinusExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_45);
            this_PlusOrMinusExpression_0=rulePlusOrMinusExpression();

            state._fsp--;


            			current = this_PlusOrMinusExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalHenshin_text.g:3402:3: ( () ( ( (lv_op_2_1= '>=' | lv_op_2_2= '<=' | lv_op_2_3= '>' | lv_op_2_4= '<' ) ) ) ( (lv_right_3_0= rulePlusOrMinusExpression ) ) )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( ((LA60_0>=62 && LA60_0<=65)) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // InternalHenshin_text.g:3403:4: () ( ( (lv_op_2_1= '>=' | lv_op_2_2= '<=' | lv_op_2_3= '>' | lv_op_2_4= '<' ) ) ) ( (lv_right_3_0= rulePlusOrMinusExpression ) )
            	    {
            	    // InternalHenshin_text.g:3403:4: ()
            	    // InternalHenshin_text.g:3404:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getComparisonExpressionAccess().getComparisonExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalHenshin_text.g:3410:4: ( ( (lv_op_2_1= '>=' | lv_op_2_2= '<=' | lv_op_2_3= '>' | lv_op_2_4= '<' ) ) )
            	    // InternalHenshin_text.g:3411:5: ( (lv_op_2_1= '>=' | lv_op_2_2= '<=' | lv_op_2_3= '>' | lv_op_2_4= '<' ) )
            	    {
            	    // InternalHenshin_text.g:3411:5: ( (lv_op_2_1= '>=' | lv_op_2_2= '<=' | lv_op_2_3= '>' | lv_op_2_4= '<' ) )
            	    // InternalHenshin_text.g:3412:6: (lv_op_2_1= '>=' | lv_op_2_2= '<=' | lv_op_2_3= '>' | lv_op_2_4= '<' )
            	    {
            	    // InternalHenshin_text.g:3412:6: (lv_op_2_1= '>=' | lv_op_2_2= '<=' | lv_op_2_3= '>' | lv_op_2_4= '<' )
            	    int alt59=4;
            	    switch ( input.LA(1) ) {
            	    case 62:
            	        {
            	        alt59=1;
            	        }
            	        break;
            	    case 63:
            	        {
            	        alt59=2;
            	        }
            	        break;
            	    case 64:
            	        {
            	        alt59=3;
            	        }
            	        break;
            	    case 65:
            	        {
            	        alt59=4;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 59, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt59) {
            	        case 1 :
            	            // InternalHenshin_text.g:3413:7: lv_op_2_1= '>='
            	            {
            	            lv_op_2_1=(Token)match(input,62,FOLLOW_18); 

            	            							newLeafNode(lv_op_2_1, grammarAccess.getComparisonExpressionAccess().getOpGreaterThanSignEqualsSignKeyword_1_1_0_0());
            	            						

            	            							if (current==null) {
            	            								current = createModelElement(grammarAccess.getComparisonExpressionRule());
            	            							}
            	            							setWithLastConsumed(current, "op", lv_op_2_1, null);
            	            						

            	            }
            	            break;
            	        case 2 :
            	            // InternalHenshin_text.g:3424:7: lv_op_2_2= '<='
            	            {
            	            lv_op_2_2=(Token)match(input,63,FOLLOW_18); 

            	            							newLeafNode(lv_op_2_2, grammarAccess.getComparisonExpressionAccess().getOpLessThanSignEqualsSignKeyword_1_1_0_1());
            	            						

            	            							if (current==null) {
            	            								current = createModelElement(grammarAccess.getComparisonExpressionRule());
            	            							}
            	            							setWithLastConsumed(current, "op", lv_op_2_2, null);
            	            						

            	            }
            	            break;
            	        case 3 :
            	            // InternalHenshin_text.g:3435:7: lv_op_2_3= '>'
            	            {
            	            lv_op_2_3=(Token)match(input,64,FOLLOW_18); 

            	            							newLeafNode(lv_op_2_3, grammarAccess.getComparisonExpressionAccess().getOpGreaterThanSignKeyword_1_1_0_2());
            	            						

            	            							if (current==null) {
            	            								current = createModelElement(grammarAccess.getComparisonExpressionRule());
            	            							}
            	            							setWithLastConsumed(current, "op", lv_op_2_3, null);
            	            						

            	            }
            	            break;
            	        case 4 :
            	            // InternalHenshin_text.g:3446:7: lv_op_2_4= '<'
            	            {
            	            lv_op_2_4=(Token)match(input,65,FOLLOW_18); 

            	            							newLeafNode(lv_op_2_4, grammarAccess.getComparisonExpressionAccess().getOpLessThanSignKeyword_1_1_0_3());
            	            						

            	            							if (current==null) {
            	            								current = createModelElement(grammarAccess.getComparisonExpressionRule());
            	            							}
            	            							setWithLastConsumed(current, "op", lv_op_2_4, null);
            	            						

            	            }
            	            break;

            	    }


            	    }


            	    }

            	    // InternalHenshin_text.g:3459:4: ( (lv_right_3_0= rulePlusOrMinusExpression ) )
            	    // InternalHenshin_text.g:3460:5: (lv_right_3_0= rulePlusOrMinusExpression )
            	    {
            	    // InternalHenshin_text.g:3460:5: (lv_right_3_0= rulePlusOrMinusExpression )
            	    // InternalHenshin_text.g:3461:6: lv_right_3_0= rulePlusOrMinusExpression
            	    {

            	    						newCompositeNode(grammarAccess.getComparisonExpressionAccess().getRightPlusOrMinusExpressionParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_45);
            	    lv_right_3_0=rulePlusOrMinusExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getComparisonExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"org.eclipse.emf.henshin.text.Henshin_text.PlusOrMinusExpression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop60;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleComparisonExpression"


    // $ANTLR start "entryRulePlusOrMinusExpression"
    // InternalHenshin_text.g:3483:1: entryRulePlusOrMinusExpression returns [EObject current=null] : iv_rulePlusOrMinusExpression= rulePlusOrMinusExpression EOF ;
    public final EObject entryRulePlusOrMinusExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePlusOrMinusExpression = null;


        try {
            // InternalHenshin_text.g:3483:62: (iv_rulePlusOrMinusExpression= rulePlusOrMinusExpression EOF )
            // InternalHenshin_text.g:3484:2: iv_rulePlusOrMinusExpression= rulePlusOrMinusExpression EOF
            {
             newCompositeNode(grammarAccess.getPlusOrMinusExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePlusOrMinusExpression=rulePlusOrMinusExpression();

            state._fsp--;

             current =iv_rulePlusOrMinusExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePlusOrMinusExpression"


    // $ANTLR start "rulePlusOrMinusExpression"
    // InternalHenshin_text.g:3490:1: rulePlusOrMinusExpression returns [EObject current=null] : (this_MulOrDivExpression_0= ruleMulOrDivExpression ( ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMulOrDivExpression ) ) )* ) ;
    public final EObject rulePlusOrMinusExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject this_MulOrDivExpression_0 = null;

        EObject lv_right_5_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:3496:2: ( (this_MulOrDivExpression_0= ruleMulOrDivExpression ( ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMulOrDivExpression ) ) )* ) )
            // InternalHenshin_text.g:3497:2: (this_MulOrDivExpression_0= ruleMulOrDivExpression ( ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMulOrDivExpression ) ) )* )
            {
            // InternalHenshin_text.g:3497:2: (this_MulOrDivExpression_0= ruleMulOrDivExpression ( ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMulOrDivExpression ) ) )* )
            // InternalHenshin_text.g:3498:3: this_MulOrDivExpression_0= ruleMulOrDivExpression ( ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMulOrDivExpression ) ) )*
            {

            			newCompositeNode(grammarAccess.getPlusOrMinusExpressionAccess().getMulOrDivExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_46);
            this_MulOrDivExpression_0=ruleMulOrDivExpression();

            state._fsp--;


            			current = this_MulOrDivExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalHenshin_text.g:3506:3: ( ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMulOrDivExpression ) ) )*
            loop62:
            do {
                int alt62=2;
                int LA62_0 = input.LA(1);

                if ( ((LA62_0>=66 && LA62_0<=67)) ) {
                    alt62=1;
                }


                switch (alt62) {
            	case 1 :
            	    // InternalHenshin_text.g:3507:4: ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMulOrDivExpression ) )
            	    {
            	    // InternalHenshin_text.g:3507:4: ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) )
            	    int alt61=2;
            	    int LA61_0 = input.LA(1);

            	    if ( (LA61_0==66) ) {
            	        alt61=1;
            	    }
            	    else if ( (LA61_0==67) ) {
            	        alt61=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 61, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt61) {
            	        case 1 :
            	            // InternalHenshin_text.g:3508:5: ( () otherlv_2= '+' )
            	            {
            	            // InternalHenshin_text.g:3508:5: ( () otherlv_2= '+' )
            	            // InternalHenshin_text.g:3509:6: () otherlv_2= '+'
            	            {
            	            // InternalHenshin_text.g:3509:6: ()
            	            // InternalHenshin_text.g:3510:7: 
            	            {

            	            							current = forceCreateModelElementAndSet(
            	            								grammarAccess.getPlusOrMinusExpressionAccess().getPlusExpressionLeftAction_1_0_0_0(),
            	            								current);
            	            						

            	            }

            	            otherlv_2=(Token)match(input,66,FOLLOW_18); 

            	            						newLeafNode(otherlv_2, grammarAccess.getPlusOrMinusExpressionAccess().getPlusSignKeyword_1_0_0_1());
            	            					

            	            }


            	            }
            	            break;
            	        case 2 :
            	            // InternalHenshin_text.g:3522:5: ( () otherlv_4= '-' )
            	            {
            	            // InternalHenshin_text.g:3522:5: ( () otherlv_4= '-' )
            	            // InternalHenshin_text.g:3523:6: () otherlv_4= '-'
            	            {
            	            // InternalHenshin_text.g:3523:6: ()
            	            // InternalHenshin_text.g:3524:7: 
            	            {

            	            							current = forceCreateModelElementAndSet(
            	            								grammarAccess.getPlusOrMinusExpressionAccess().getMinusExpressionLeftAction_1_0_1_0(),
            	            								current);
            	            						

            	            }

            	            otherlv_4=(Token)match(input,67,FOLLOW_18); 

            	            						newLeafNode(otherlv_4, grammarAccess.getPlusOrMinusExpressionAccess().getHyphenMinusKeyword_1_0_1_1());
            	            					

            	            }


            	            }
            	            break;

            	    }

            	    // InternalHenshin_text.g:3536:4: ( (lv_right_5_0= ruleMulOrDivExpression ) )
            	    // InternalHenshin_text.g:3537:5: (lv_right_5_0= ruleMulOrDivExpression )
            	    {
            	    // InternalHenshin_text.g:3537:5: (lv_right_5_0= ruleMulOrDivExpression )
            	    // InternalHenshin_text.g:3538:6: lv_right_5_0= ruleMulOrDivExpression
            	    {

            	    						newCompositeNode(grammarAccess.getPlusOrMinusExpressionAccess().getRightMulOrDivExpressionParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_46);
            	    lv_right_5_0=ruleMulOrDivExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getPlusOrMinusExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_5_0,
            	    							"org.eclipse.emf.henshin.text.Henshin_text.MulOrDivExpression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop62;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePlusOrMinusExpression"


    // $ANTLR start "entryRuleMulOrDivExpression"
    // InternalHenshin_text.g:3560:1: entryRuleMulOrDivExpression returns [EObject current=null] : iv_ruleMulOrDivExpression= ruleMulOrDivExpression EOF ;
    public final EObject entryRuleMulOrDivExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMulOrDivExpression = null;


        try {
            // InternalHenshin_text.g:3560:59: (iv_ruleMulOrDivExpression= ruleMulOrDivExpression EOF )
            // InternalHenshin_text.g:3561:2: iv_ruleMulOrDivExpression= ruleMulOrDivExpression EOF
            {
             newCompositeNode(grammarAccess.getMulOrDivExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMulOrDivExpression=ruleMulOrDivExpression();

            state._fsp--;

             current =iv_ruleMulOrDivExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMulOrDivExpression"


    // $ANTLR start "ruleMulOrDivExpression"
    // InternalHenshin_text.g:3567:1: ruleMulOrDivExpression returns [EObject current=null] : (this_PrimaryExpression_0= rulePrimaryExpression ( () ( ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) ) ) ( (lv_right_3_0= rulePrimaryExpression ) ) )* ) ;
    public final EObject ruleMulOrDivExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        EObject this_PrimaryExpression_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:3573:2: ( (this_PrimaryExpression_0= rulePrimaryExpression ( () ( ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) ) ) ( (lv_right_3_0= rulePrimaryExpression ) ) )* ) )
            // InternalHenshin_text.g:3574:2: (this_PrimaryExpression_0= rulePrimaryExpression ( () ( ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) ) ) ( (lv_right_3_0= rulePrimaryExpression ) ) )* )
            {
            // InternalHenshin_text.g:3574:2: (this_PrimaryExpression_0= rulePrimaryExpression ( () ( ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) ) ) ( (lv_right_3_0= rulePrimaryExpression ) ) )* )
            // InternalHenshin_text.g:3575:3: this_PrimaryExpression_0= rulePrimaryExpression ( () ( ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) ) ) ( (lv_right_3_0= rulePrimaryExpression ) ) )*
            {

            			newCompositeNode(grammarAccess.getMulOrDivExpressionAccess().getPrimaryExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_47);
            this_PrimaryExpression_0=rulePrimaryExpression();

            state._fsp--;


            			current = this_PrimaryExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalHenshin_text.g:3583:3: ( () ( ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) ) ) ( (lv_right_3_0= rulePrimaryExpression ) ) )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( ((LA64_0>=68 && LA64_0<=69)) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // InternalHenshin_text.g:3584:4: () ( ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) ) ) ( (lv_right_3_0= rulePrimaryExpression ) )
            	    {
            	    // InternalHenshin_text.g:3584:4: ()
            	    // InternalHenshin_text.g:3585:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getMulOrDivExpressionAccess().getMulOrDivExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalHenshin_text.g:3591:4: ( ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) ) )
            	    // InternalHenshin_text.g:3592:5: ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) )
            	    {
            	    // InternalHenshin_text.g:3592:5: ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) )
            	    // InternalHenshin_text.g:3593:6: (lv_op_2_1= '*' | lv_op_2_2= '/' )
            	    {
            	    // InternalHenshin_text.g:3593:6: (lv_op_2_1= '*' | lv_op_2_2= '/' )
            	    int alt63=2;
            	    int LA63_0 = input.LA(1);

            	    if ( (LA63_0==68) ) {
            	        alt63=1;
            	    }
            	    else if ( (LA63_0==69) ) {
            	        alt63=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 63, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt63) {
            	        case 1 :
            	            // InternalHenshin_text.g:3594:7: lv_op_2_1= '*'
            	            {
            	            lv_op_2_1=(Token)match(input,68,FOLLOW_18); 

            	            							newLeafNode(lv_op_2_1, grammarAccess.getMulOrDivExpressionAccess().getOpAsteriskKeyword_1_1_0_0());
            	            						

            	            							if (current==null) {
            	            								current = createModelElement(grammarAccess.getMulOrDivExpressionRule());
            	            							}
            	            							setWithLastConsumed(current, "op", lv_op_2_1, null);
            	            						

            	            }
            	            break;
            	        case 2 :
            	            // InternalHenshin_text.g:3605:7: lv_op_2_2= '/'
            	            {
            	            lv_op_2_2=(Token)match(input,69,FOLLOW_18); 

            	            							newLeafNode(lv_op_2_2, grammarAccess.getMulOrDivExpressionAccess().getOpSolidusKeyword_1_1_0_1());
            	            						

            	            							if (current==null) {
            	            								current = createModelElement(grammarAccess.getMulOrDivExpressionRule());
            	            							}
            	            							setWithLastConsumed(current, "op", lv_op_2_2, null);
            	            						

            	            }
            	            break;

            	    }


            	    }


            	    }

            	    // InternalHenshin_text.g:3618:4: ( (lv_right_3_0= rulePrimaryExpression ) )
            	    // InternalHenshin_text.g:3619:5: (lv_right_3_0= rulePrimaryExpression )
            	    {
            	    // InternalHenshin_text.g:3619:5: (lv_right_3_0= rulePrimaryExpression )
            	    // InternalHenshin_text.g:3620:6: lv_right_3_0= rulePrimaryExpression
            	    {

            	    						newCompositeNode(grammarAccess.getMulOrDivExpressionAccess().getRightPrimaryExpressionParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_47);
            	    lv_right_3_0=rulePrimaryExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getMulOrDivExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"org.eclipse.emf.henshin.text.Henshin_text.PrimaryExpression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop64;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMulOrDivExpression"


    // $ANTLR start "entryRulePrimaryExpression"
    // InternalHenshin_text.g:3642:1: entryRulePrimaryExpression returns [EObject current=null] : iv_rulePrimaryExpression= rulePrimaryExpression EOF ;
    public final EObject entryRulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryExpression = null;


        try {
            // InternalHenshin_text.g:3642:58: (iv_rulePrimaryExpression= rulePrimaryExpression EOF )
            // InternalHenshin_text.g:3643:2: iv_rulePrimaryExpression= rulePrimaryExpression EOF
            {
             newCompositeNode(grammarAccess.getPrimaryExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePrimaryExpression=rulePrimaryExpression();

            state._fsp--;

             current =iv_rulePrimaryExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePrimaryExpression"


    // $ANTLR start "rulePrimaryExpression"
    // InternalHenshin_text.g:3649:1: rulePrimaryExpression returns [EObject current=null] : ( ( () otherlv_1= '(' ( (lv_expression_2_0= ruleExpression ) ) otherlv_3= ')' ) | ( () otherlv_5= '!' ( (lv_expression_6_0= rulePrimaryExpression ) ) ) | this_AtomicExpression_7= ruleAtomicExpression ) ;
    public final EObject rulePrimaryExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_expression_2_0 = null;

        EObject lv_expression_6_0 = null;

        EObject this_AtomicExpression_7 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:3655:2: ( ( ( () otherlv_1= '(' ( (lv_expression_2_0= ruleExpression ) ) otherlv_3= ')' ) | ( () otherlv_5= '!' ( (lv_expression_6_0= rulePrimaryExpression ) ) ) | this_AtomicExpression_7= ruleAtomicExpression ) )
            // InternalHenshin_text.g:3656:2: ( ( () otherlv_1= '(' ( (lv_expression_2_0= ruleExpression ) ) otherlv_3= ')' ) | ( () otherlv_5= '!' ( (lv_expression_6_0= rulePrimaryExpression ) ) ) | this_AtomicExpression_7= ruleAtomicExpression )
            {
            // InternalHenshin_text.g:3656:2: ( ( () otherlv_1= '(' ( (lv_expression_2_0= ruleExpression ) ) otherlv_3= ')' ) | ( () otherlv_5= '!' ( (lv_expression_6_0= rulePrimaryExpression ) ) ) | this_AtomicExpression_7= ruleAtomicExpression )
            int alt65=3;
            switch ( input.LA(1) ) {
            case 16:
                {
                alt65=1;
                }
                break;
            case 47:
                {
                alt65=2;
                }
                break;
            case RULE_ID:
            case RULE_STRING:
            case RULE_DECIMAL:
            case RULE_NEGATIVE:
            case RULE_INT:
            case 58:
            case 59:
                {
                alt65=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 65, 0, input);

                throw nvae;
            }

            switch (alt65) {
                case 1 :
                    // InternalHenshin_text.g:3657:3: ( () otherlv_1= '(' ( (lv_expression_2_0= ruleExpression ) ) otherlv_3= ')' )
                    {
                    // InternalHenshin_text.g:3657:3: ( () otherlv_1= '(' ( (lv_expression_2_0= ruleExpression ) ) otherlv_3= ')' )
                    // InternalHenshin_text.g:3658:4: () otherlv_1= '(' ( (lv_expression_2_0= ruleExpression ) ) otherlv_3= ')'
                    {
                    // InternalHenshin_text.g:3658:4: ()
                    // InternalHenshin_text.g:3659:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getPrimaryExpressionAccess().getBracketExpressionAction_0_0(),
                    						current);
                    				

                    }

                    otherlv_1=(Token)match(input,16,FOLLOW_18); 

                    				newLeafNode(otherlv_1, grammarAccess.getPrimaryExpressionAccess().getLeftParenthesisKeyword_0_1());
                    			
                    // InternalHenshin_text.g:3669:4: ( (lv_expression_2_0= ruleExpression ) )
                    // InternalHenshin_text.g:3670:5: (lv_expression_2_0= ruleExpression )
                    {
                    // InternalHenshin_text.g:3670:5: (lv_expression_2_0= ruleExpression )
                    // InternalHenshin_text.g:3671:6: lv_expression_2_0= ruleExpression
                    {

                    						newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getExpressionExpressionParserRuleCall_0_2_0());
                    					
                    pushFollow(FOLLOW_24);
                    lv_expression_2_0=ruleExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPrimaryExpressionRule());
                    						}
                    						set(
                    							current,
                    							"expression",
                    							lv_expression_2_0,
                    							"org.eclipse.emf.henshin.text.Henshin_text.Expression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_3=(Token)match(input,18,FOLLOW_2); 

                    				newLeafNode(otherlv_3, grammarAccess.getPrimaryExpressionAccess().getRightParenthesisKeyword_0_3());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalHenshin_text.g:3694:3: ( () otherlv_5= '!' ( (lv_expression_6_0= rulePrimaryExpression ) ) )
                    {
                    // InternalHenshin_text.g:3694:3: ( () otherlv_5= '!' ( (lv_expression_6_0= rulePrimaryExpression ) ) )
                    // InternalHenshin_text.g:3695:4: () otherlv_5= '!' ( (lv_expression_6_0= rulePrimaryExpression ) )
                    {
                    // InternalHenshin_text.g:3695:4: ()
                    // InternalHenshin_text.g:3696:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getPrimaryExpressionAccess().getNotExpressionAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_5=(Token)match(input,47,FOLLOW_18); 

                    				newLeafNode(otherlv_5, grammarAccess.getPrimaryExpressionAccess().getExclamationMarkKeyword_1_1());
                    			
                    // InternalHenshin_text.g:3706:4: ( (lv_expression_6_0= rulePrimaryExpression ) )
                    // InternalHenshin_text.g:3707:5: (lv_expression_6_0= rulePrimaryExpression )
                    {
                    // InternalHenshin_text.g:3707:5: (lv_expression_6_0= rulePrimaryExpression )
                    // InternalHenshin_text.g:3708:6: lv_expression_6_0= rulePrimaryExpression
                    {

                    						newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getExpressionPrimaryExpressionParserRuleCall_1_2_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_expression_6_0=rulePrimaryExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPrimaryExpressionRule());
                    						}
                    						set(
                    							current,
                    							"expression",
                    							lv_expression_6_0,
                    							"org.eclipse.emf.henshin.text.Henshin_text.PrimaryExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalHenshin_text.g:3727:3: this_AtomicExpression_7= ruleAtomicExpression
                    {

                    			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getAtomicExpressionParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_AtomicExpression_7=ruleAtomicExpression();

                    state._fsp--;


                    			current = this_AtomicExpression_7;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePrimaryExpression"


    // $ANTLR start "entryRuleAtomicExpression"
    // InternalHenshin_text.g:3739:1: entryRuleAtomicExpression returns [EObject current=null] : iv_ruleAtomicExpression= ruleAtomicExpression EOF ;
    public final EObject entryRuleAtomicExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAtomicExpression = null;


        try {
            // InternalHenshin_text.g:3739:57: (iv_ruleAtomicExpression= ruleAtomicExpression EOF )
            // InternalHenshin_text.g:3740:2: iv_ruleAtomicExpression= ruleAtomicExpression EOF
            {
             newCompositeNode(grammarAccess.getAtomicExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAtomicExpression=ruleAtomicExpression();

            state._fsp--;

             current =iv_ruleAtomicExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAtomicExpression"


    // $ANTLR start "ruleAtomicExpression"
    // InternalHenshin_text.g:3746:1: ruleAtomicExpression returns [EObject current=null] : ( ( () ( (otherlv_1= RULE_ID ) ) ) | ( () ( (lv_value_3_0= ruleEString ) ) otherlv_4= '(' ( ( (lv_javaParameter_5_0= ruleExpression ) ) (otherlv_6= ',' ( (lv_javaParameter_7_0= ruleExpression ) ) )* )? otherlv_8= ')' ) | ( () ( (lv_value_10_0= ruleJavaAttribute ) ) ) | ( () ( (lv_value_12_0= RULE_STRING ) ) ) | ( () ( (lv_value_14_0= RULE_DECIMAL ) ) ) | ( () ( (lv_value_16_0= RULE_NEGATIVE ) ) ) | ( () ( (lv_value_18_0= RULE_INT ) ) ) | ( () ( (lv_value_20_0= ruleEBoolean ) ) ) ) ;
    public final EObject ruleAtomicExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token lv_value_12_0=null;
        Token lv_value_14_0=null;
        Token lv_value_16_0=null;
        Token lv_value_18_0=null;
        AntlrDatatypeRuleToken lv_value_3_0 = null;

        EObject lv_javaParameter_5_0 = null;

        EObject lv_javaParameter_7_0 = null;

        AntlrDatatypeRuleToken lv_value_10_0 = null;

        AntlrDatatypeRuleToken lv_value_20_0 = null;



        	enterRule();

        try {
            // InternalHenshin_text.g:3752:2: ( ( ( () ( (otherlv_1= RULE_ID ) ) ) | ( () ( (lv_value_3_0= ruleEString ) ) otherlv_4= '(' ( ( (lv_javaParameter_5_0= ruleExpression ) ) (otherlv_6= ',' ( (lv_javaParameter_7_0= ruleExpression ) ) )* )? otherlv_8= ')' ) | ( () ( (lv_value_10_0= ruleJavaAttribute ) ) ) | ( () ( (lv_value_12_0= RULE_STRING ) ) ) | ( () ( (lv_value_14_0= RULE_DECIMAL ) ) ) | ( () ( (lv_value_16_0= RULE_NEGATIVE ) ) ) | ( () ( (lv_value_18_0= RULE_INT ) ) ) | ( () ( (lv_value_20_0= ruleEBoolean ) ) ) ) )
            // InternalHenshin_text.g:3753:2: ( ( () ( (otherlv_1= RULE_ID ) ) ) | ( () ( (lv_value_3_0= ruleEString ) ) otherlv_4= '(' ( ( (lv_javaParameter_5_0= ruleExpression ) ) (otherlv_6= ',' ( (lv_javaParameter_7_0= ruleExpression ) ) )* )? otherlv_8= ')' ) | ( () ( (lv_value_10_0= ruleJavaAttribute ) ) ) | ( () ( (lv_value_12_0= RULE_STRING ) ) ) | ( () ( (lv_value_14_0= RULE_DECIMAL ) ) ) | ( () ( (lv_value_16_0= RULE_NEGATIVE ) ) ) | ( () ( (lv_value_18_0= RULE_INT ) ) ) | ( () ( (lv_value_20_0= ruleEBoolean ) ) ) )
            {
            // InternalHenshin_text.g:3753:2: ( ( () ( (otherlv_1= RULE_ID ) ) ) | ( () ( (lv_value_3_0= ruleEString ) ) otherlv_4= '(' ( ( (lv_javaParameter_5_0= ruleExpression ) ) (otherlv_6= ',' ( (lv_javaParameter_7_0= ruleExpression ) ) )* )? otherlv_8= ')' ) | ( () ( (lv_value_10_0= ruleJavaAttribute ) ) ) | ( () ( (lv_value_12_0= RULE_STRING ) ) ) | ( () ( (lv_value_14_0= RULE_DECIMAL ) ) ) | ( () ( (lv_value_16_0= RULE_NEGATIVE ) ) ) | ( () ( (lv_value_18_0= RULE_INT ) ) ) | ( () ( (lv_value_20_0= ruleEBoolean ) ) ) )
            int alt68=8;
            alt68 = dfa68.predict(input);
            switch (alt68) {
                case 1 :
                    // InternalHenshin_text.g:3754:3: ( () ( (otherlv_1= RULE_ID ) ) )
                    {
                    // InternalHenshin_text.g:3754:3: ( () ( (otherlv_1= RULE_ID ) ) )
                    // InternalHenshin_text.g:3755:4: () ( (otherlv_1= RULE_ID ) )
                    {
                    // InternalHenshin_text.g:3755:4: ()
                    // InternalHenshin_text.g:3756:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getAtomicExpressionAccess().getParameterValueAction_0_0(),
                    						current);
                    				

                    }

                    // InternalHenshin_text.g:3762:4: ( (otherlv_1= RULE_ID ) )
                    // InternalHenshin_text.g:3763:5: (otherlv_1= RULE_ID )
                    {
                    // InternalHenshin_text.g:3763:5: (otherlv_1= RULE_ID )
                    // InternalHenshin_text.g:3764:6: otherlv_1= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getAtomicExpressionRule());
                    						}
                    					
                    otherlv_1=(Token)match(input,RULE_ID,FOLLOW_2); 

                    						newLeafNode(otherlv_1, grammarAccess.getAtomicExpressionAccess().getValueParameterCrossReference_0_1_0());
                    					

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalHenshin_text.g:3777:3: ( () ( (lv_value_3_0= ruleEString ) ) otherlv_4= '(' ( ( (lv_javaParameter_5_0= ruleExpression ) ) (otherlv_6= ',' ( (lv_javaParameter_7_0= ruleExpression ) ) )* )? otherlv_8= ')' )
                    {
                    // InternalHenshin_text.g:3777:3: ( () ( (lv_value_3_0= ruleEString ) ) otherlv_4= '(' ( ( (lv_javaParameter_5_0= ruleExpression ) ) (otherlv_6= ',' ( (lv_javaParameter_7_0= ruleExpression ) ) )* )? otherlv_8= ')' )
                    // InternalHenshin_text.g:3778:4: () ( (lv_value_3_0= ruleEString ) ) otherlv_4= '(' ( ( (lv_javaParameter_5_0= ruleExpression ) ) (otherlv_6= ',' ( (lv_javaParameter_7_0= ruleExpression ) ) )* )? otherlv_8= ')'
                    {
                    // InternalHenshin_text.g:3778:4: ()
                    // InternalHenshin_text.g:3779:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getAtomicExpressionAccess().getJavaClassValueAction_1_0(),
                    						current);
                    				

                    }

                    // InternalHenshin_text.g:3785:4: ( (lv_value_3_0= ruleEString ) )
                    // InternalHenshin_text.g:3786:5: (lv_value_3_0= ruleEString )
                    {
                    // InternalHenshin_text.g:3786:5: (lv_value_3_0= ruleEString )
                    // InternalHenshin_text.g:3787:6: lv_value_3_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getAtomicExpressionAccess().getValueEStringParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_7);
                    lv_value_3_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getAtomicExpressionRule());
                    						}
                    						set(
                    							current,
                    							"value",
                    							lv_value_3_0,
                    							"org.eclipse.emf.henshin.text.Henshin_text.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_4=(Token)match(input,16,FOLLOW_48); 

                    				newLeafNode(otherlv_4, grammarAccess.getAtomicExpressionAccess().getLeftParenthesisKeyword_1_2());
                    			
                    // InternalHenshin_text.g:3808:4: ( ( (lv_javaParameter_5_0= ruleExpression ) ) (otherlv_6= ',' ( (lv_javaParameter_7_0= ruleExpression ) ) )* )?
                    int alt67=2;
                    int LA67_0 = input.LA(1);

                    if ( ((LA67_0>=RULE_ID && LA67_0<=RULE_INT)||LA67_0==16||LA67_0==47||(LA67_0>=58 && LA67_0<=59)) ) {
                        alt67=1;
                    }
                    switch (alt67) {
                        case 1 :
                            // InternalHenshin_text.g:3809:5: ( (lv_javaParameter_5_0= ruleExpression ) ) (otherlv_6= ',' ( (lv_javaParameter_7_0= ruleExpression ) ) )*
                            {
                            // InternalHenshin_text.g:3809:5: ( (lv_javaParameter_5_0= ruleExpression ) )
                            // InternalHenshin_text.g:3810:6: (lv_javaParameter_5_0= ruleExpression )
                            {
                            // InternalHenshin_text.g:3810:6: (lv_javaParameter_5_0= ruleExpression )
                            // InternalHenshin_text.g:3811:7: lv_javaParameter_5_0= ruleExpression
                            {

                            							newCompositeNode(grammarAccess.getAtomicExpressionAccess().getJavaParameterExpressionParserRuleCall_1_3_0_0());
                            						
                            pushFollow(FOLLOW_9);
                            lv_javaParameter_5_0=ruleExpression();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getAtomicExpressionRule());
                            							}
                            							add(
                            								current,
                            								"javaParameter",
                            								lv_javaParameter_5_0,
                            								"org.eclipse.emf.henshin.text.Henshin_text.Expression");
                            							afterParserOrEnumRuleCall();
                            						

                            }


                            }

                            // InternalHenshin_text.g:3828:5: (otherlv_6= ',' ( (lv_javaParameter_7_0= ruleExpression ) ) )*
                            loop66:
                            do {
                                int alt66=2;
                                int LA66_0 = input.LA(1);

                                if ( (LA66_0==17) ) {
                                    alt66=1;
                                }


                                switch (alt66) {
                            	case 1 :
                            	    // InternalHenshin_text.g:3829:6: otherlv_6= ',' ( (lv_javaParameter_7_0= ruleExpression ) )
                            	    {
                            	    otherlv_6=(Token)match(input,17,FOLLOW_18); 

                            	    						newLeafNode(otherlv_6, grammarAccess.getAtomicExpressionAccess().getCommaKeyword_1_3_1_0());
                            	    					
                            	    // InternalHenshin_text.g:3833:6: ( (lv_javaParameter_7_0= ruleExpression ) )
                            	    // InternalHenshin_text.g:3834:7: (lv_javaParameter_7_0= ruleExpression )
                            	    {
                            	    // InternalHenshin_text.g:3834:7: (lv_javaParameter_7_0= ruleExpression )
                            	    // InternalHenshin_text.g:3835:8: lv_javaParameter_7_0= ruleExpression
                            	    {

                            	    								newCompositeNode(grammarAccess.getAtomicExpressionAccess().getJavaParameterExpressionParserRuleCall_1_3_1_1_0());
                            	    							
                            	    pushFollow(FOLLOW_9);
                            	    lv_javaParameter_7_0=ruleExpression();

                            	    state._fsp--;


                            	    								if (current==null) {
                            	    									current = createModelElementForParent(grammarAccess.getAtomicExpressionRule());
                            	    								}
                            	    								add(
                            	    									current,
                            	    									"javaParameter",
                            	    									lv_javaParameter_7_0,
                            	    									"org.eclipse.emf.henshin.text.Henshin_text.Expression");
                            	    								afterParserOrEnumRuleCall();
                            	    							

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop66;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_8=(Token)match(input,18,FOLLOW_2); 

                    				newLeafNode(otherlv_8, grammarAccess.getAtomicExpressionAccess().getRightParenthesisKeyword_1_4());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalHenshin_text.g:3860:3: ( () ( (lv_value_10_0= ruleJavaAttribute ) ) )
                    {
                    // InternalHenshin_text.g:3860:3: ( () ( (lv_value_10_0= ruleJavaAttribute ) ) )
                    // InternalHenshin_text.g:3861:4: () ( (lv_value_10_0= ruleJavaAttribute ) )
                    {
                    // InternalHenshin_text.g:3861:4: ()
                    // InternalHenshin_text.g:3862:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getAtomicExpressionAccess().getJavaAttributeValueAction_2_0(),
                    						current);
                    				

                    }

                    // InternalHenshin_text.g:3868:4: ( (lv_value_10_0= ruleJavaAttribute ) )
                    // InternalHenshin_text.g:3869:5: (lv_value_10_0= ruleJavaAttribute )
                    {
                    // InternalHenshin_text.g:3869:5: (lv_value_10_0= ruleJavaAttribute )
                    // InternalHenshin_text.g:3870:6: lv_value_10_0= ruleJavaAttribute
                    {

                    						newCompositeNode(grammarAccess.getAtomicExpressionAccess().getValueJavaAttributeParserRuleCall_2_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_value_10_0=ruleJavaAttribute();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getAtomicExpressionRule());
                    						}
                    						set(
                    							current,
                    							"value",
                    							lv_value_10_0,
                    							"org.eclipse.emf.henshin.text.Henshin_text.JavaAttribute");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalHenshin_text.g:3889:3: ( () ( (lv_value_12_0= RULE_STRING ) ) )
                    {
                    // InternalHenshin_text.g:3889:3: ( () ( (lv_value_12_0= RULE_STRING ) ) )
                    // InternalHenshin_text.g:3890:4: () ( (lv_value_12_0= RULE_STRING ) )
                    {
                    // InternalHenshin_text.g:3890:4: ()
                    // InternalHenshin_text.g:3891:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getAtomicExpressionAccess().getStringValueAction_3_0(),
                    						current);
                    				

                    }

                    // InternalHenshin_text.g:3897:4: ( (lv_value_12_0= RULE_STRING ) )
                    // InternalHenshin_text.g:3898:5: (lv_value_12_0= RULE_STRING )
                    {
                    // InternalHenshin_text.g:3898:5: (lv_value_12_0= RULE_STRING )
                    // InternalHenshin_text.g:3899:6: lv_value_12_0= RULE_STRING
                    {
                    lv_value_12_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

                    						newLeafNode(lv_value_12_0, grammarAccess.getAtomicExpressionAccess().getValueSTRINGTerminalRuleCall_3_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getAtomicExpressionRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"value",
                    							lv_value_12_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalHenshin_text.g:3917:3: ( () ( (lv_value_14_0= RULE_DECIMAL ) ) )
                    {
                    // InternalHenshin_text.g:3917:3: ( () ( (lv_value_14_0= RULE_DECIMAL ) ) )
                    // InternalHenshin_text.g:3918:4: () ( (lv_value_14_0= RULE_DECIMAL ) )
                    {
                    // InternalHenshin_text.g:3918:4: ()
                    // InternalHenshin_text.g:3919:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getAtomicExpressionAccess().getNumberValueAction_4_0(),
                    						current);
                    				

                    }

                    // InternalHenshin_text.g:3925:4: ( (lv_value_14_0= RULE_DECIMAL ) )
                    // InternalHenshin_text.g:3926:5: (lv_value_14_0= RULE_DECIMAL )
                    {
                    // InternalHenshin_text.g:3926:5: (lv_value_14_0= RULE_DECIMAL )
                    // InternalHenshin_text.g:3927:6: lv_value_14_0= RULE_DECIMAL
                    {
                    lv_value_14_0=(Token)match(input,RULE_DECIMAL,FOLLOW_2); 

                    						newLeafNode(lv_value_14_0, grammarAccess.getAtomicExpressionAccess().getValueDECIMALTerminalRuleCall_4_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getAtomicExpressionRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"value",
                    							lv_value_14_0,
                    							"org.eclipse.emf.henshin.text.Henshin_text.DECIMAL");
                    					

                    }


                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalHenshin_text.g:3945:3: ( () ( (lv_value_16_0= RULE_NEGATIVE ) ) )
                    {
                    // InternalHenshin_text.g:3945:3: ( () ( (lv_value_16_0= RULE_NEGATIVE ) ) )
                    // InternalHenshin_text.g:3946:4: () ( (lv_value_16_0= RULE_NEGATIVE ) )
                    {
                    // InternalHenshin_text.g:3946:4: ()
                    // InternalHenshin_text.g:3947:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getAtomicExpressionAccess().getIntegerValueAction_5_0(),
                    						current);
                    				

                    }

                    // InternalHenshin_text.g:3953:4: ( (lv_value_16_0= RULE_NEGATIVE ) )
                    // InternalHenshin_text.g:3954:5: (lv_value_16_0= RULE_NEGATIVE )
                    {
                    // InternalHenshin_text.g:3954:5: (lv_value_16_0= RULE_NEGATIVE )
                    // InternalHenshin_text.g:3955:6: lv_value_16_0= RULE_NEGATIVE
                    {
                    lv_value_16_0=(Token)match(input,RULE_NEGATIVE,FOLLOW_2); 

                    						newLeafNode(lv_value_16_0, grammarAccess.getAtomicExpressionAccess().getValueNEGATIVETerminalRuleCall_5_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getAtomicExpressionRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"value",
                    							lv_value_16_0,
                    							"org.eclipse.emf.henshin.text.Henshin_text.NEGATIVE");
                    					

                    }


                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalHenshin_text.g:3973:3: ( () ( (lv_value_18_0= RULE_INT ) ) )
                    {
                    // InternalHenshin_text.g:3973:3: ( () ( (lv_value_18_0= RULE_INT ) ) )
                    // InternalHenshin_text.g:3974:4: () ( (lv_value_18_0= RULE_INT ) )
                    {
                    // InternalHenshin_text.g:3974:4: ()
                    // InternalHenshin_text.g:3975:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getAtomicExpressionAccess().getNaturalValueAction_6_0(),
                    						current);
                    				

                    }

                    // InternalHenshin_text.g:3981:4: ( (lv_value_18_0= RULE_INT ) )
                    // InternalHenshin_text.g:3982:5: (lv_value_18_0= RULE_INT )
                    {
                    // InternalHenshin_text.g:3982:5: (lv_value_18_0= RULE_INT )
                    // InternalHenshin_text.g:3983:6: lv_value_18_0= RULE_INT
                    {
                    lv_value_18_0=(Token)match(input,RULE_INT,FOLLOW_2); 

                    						newLeafNode(lv_value_18_0, grammarAccess.getAtomicExpressionAccess().getValueINTTerminalRuleCall_6_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getAtomicExpressionRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"value",
                    							lv_value_18_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalHenshin_text.g:4001:3: ( () ( (lv_value_20_0= ruleEBoolean ) ) )
                    {
                    // InternalHenshin_text.g:4001:3: ( () ( (lv_value_20_0= ruleEBoolean ) ) )
                    // InternalHenshin_text.g:4002:4: () ( (lv_value_20_0= ruleEBoolean ) )
                    {
                    // InternalHenshin_text.g:4002:4: ()
                    // InternalHenshin_text.g:4003:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getAtomicExpressionAccess().getBoolValueAction_7_0(),
                    						current);
                    				

                    }

                    // InternalHenshin_text.g:4009:4: ( (lv_value_20_0= ruleEBoolean ) )
                    // InternalHenshin_text.g:4010:5: (lv_value_20_0= ruleEBoolean )
                    {
                    // InternalHenshin_text.g:4010:5: (lv_value_20_0= ruleEBoolean )
                    // InternalHenshin_text.g:4011:6: lv_value_20_0= ruleEBoolean
                    {

                    						newCompositeNode(grammarAccess.getAtomicExpressionAccess().getValueEBooleanParserRuleCall_7_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_value_20_0=ruleEBoolean();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getAtomicExpressionRule());
                    						}
                    						set(
                    							current,
                    							"value",
                    							lv_value_20_0,
                    							"org.eclipse.emf.henshin.text.Henshin_text.EBoolean");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAtomicExpression"


    // $ANTLR start "entryRuleJavaAttribute"
    // InternalHenshin_text.g:4033:1: entryRuleJavaAttribute returns [String current=null] : iv_ruleJavaAttribute= ruleJavaAttribute EOF ;
    public final String entryRuleJavaAttribute() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleJavaAttribute = null;


        try {
            // InternalHenshin_text.g:4033:53: (iv_ruleJavaAttribute= ruleJavaAttribute EOF )
            // InternalHenshin_text.g:4034:2: iv_ruleJavaAttribute= ruleJavaAttribute EOF
            {
             newCompositeNode(grammarAccess.getJavaAttributeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleJavaAttribute=ruleJavaAttribute();

            state._fsp--;

             current =iv_ruleJavaAttribute.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJavaAttribute"


    // $ANTLR start "ruleJavaAttribute"
    // InternalHenshin_text.g:4040:1: ruleJavaAttribute returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID kw= '.' this_ID_2= RULE_ID ) ;
    public final AntlrDatatypeRuleToken ruleJavaAttribute() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;


        	enterRule();

        try {
            // InternalHenshin_text.g:4046:2: ( (this_ID_0= RULE_ID kw= '.' this_ID_2= RULE_ID ) )
            // InternalHenshin_text.g:4047:2: (this_ID_0= RULE_ID kw= '.' this_ID_2= RULE_ID )
            {
            // InternalHenshin_text.g:4047:2: (this_ID_0= RULE_ID kw= '.' this_ID_2= RULE_ID )
            // InternalHenshin_text.g:4048:3: this_ID_0= RULE_ID kw= '.' this_ID_2= RULE_ID
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_49); 

            			current.merge(this_ID_0);
            		

            			newLeafNode(this_ID_0, grammarAccess.getJavaAttributeAccess().getIDTerminalRuleCall_0());
            		
            kw=(Token)match(input,14,FOLLOW_5); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getJavaAttributeAccess().getFullStopKeyword_1());
            		
            this_ID_2=(Token)match(input,RULE_ID,FOLLOW_2); 

            			current.merge(this_ID_2);
            		

            			newLeafNode(this_ID_2, grammarAccess.getJavaAttributeAccess().getIDTerminalRuleCall_2());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJavaAttribute"


    // $ANTLR start "ruleParameterKindRule"
    // InternalHenshin_text.g:4071:1: ruleParameterKindRule returns [Enumerator current=null] : ( (enumLiteral_0= 'IN' ) | (enumLiteral_1= 'OUT' ) | (enumLiteral_2= 'INOUT' ) | (enumLiteral_3= 'VAR' ) ) ;
    public final Enumerator ruleParameterKindRule() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalHenshin_text.g:4077:2: ( ( (enumLiteral_0= 'IN' ) | (enumLiteral_1= 'OUT' ) | (enumLiteral_2= 'INOUT' ) | (enumLiteral_3= 'VAR' ) ) )
            // InternalHenshin_text.g:4078:2: ( (enumLiteral_0= 'IN' ) | (enumLiteral_1= 'OUT' ) | (enumLiteral_2= 'INOUT' ) | (enumLiteral_3= 'VAR' ) )
            {
            // InternalHenshin_text.g:4078:2: ( (enumLiteral_0= 'IN' ) | (enumLiteral_1= 'OUT' ) | (enumLiteral_2= 'INOUT' ) | (enumLiteral_3= 'VAR' ) )
            int alt69=4;
            switch ( input.LA(1) ) {
            case 70:
                {
                alt69=1;
                }
                break;
            case 71:
                {
                alt69=2;
                }
                break;
            case 72:
                {
                alt69=3;
                }
                break;
            case 73:
                {
                alt69=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 69, 0, input);

                throw nvae;
            }

            switch (alt69) {
                case 1 :
                    // InternalHenshin_text.g:4079:3: (enumLiteral_0= 'IN' )
                    {
                    // InternalHenshin_text.g:4079:3: (enumLiteral_0= 'IN' )
                    // InternalHenshin_text.g:4080:4: enumLiteral_0= 'IN'
                    {
                    enumLiteral_0=(Token)match(input,70,FOLLOW_2); 

                    				current = grammarAccess.getParameterKindRuleAccess().getINEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getParameterKindRuleAccess().getINEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalHenshin_text.g:4087:3: (enumLiteral_1= 'OUT' )
                    {
                    // InternalHenshin_text.g:4087:3: (enumLiteral_1= 'OUT' )
                    // InternalHenshin_text.g:4088:4: enumLiteral_1= 'OUT'
                    {
                    enumLiteral_1=(Token)match(input,71,FOLLOW_2); 

                    				current = grammarAccess.getParameterKindRuleAccess().getOUTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getParameterKindRuleAccess().getOUTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalHenshin_text.g:4095:3: (enumLiteral_2= 'INOUT' )
                    {
                    // InternalHenshin_text.g:4095:3: (enumLiteral_2= 'INOUT' )
                    // InternalHenshin_text.g:4096:4: enumLiteral_2= 'INOUT'
                    {
                    enumLiteral_2=(Token)match(input,72,FOLLOW_2); 

                    				current = grammarAccess.getParameterKindRuleAccess().getINOUTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getParameterKindRuleAccess().getINOUTEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalHenshin_text.g:4103:3: (enumLiteral_3= 'VAR' )
                    {
                    // InternalHenshin_text.g:4103:3: (enumLiteral_3= 'VAR' )
                    // InternalHenshin_text.g:4104:4: enumLiteral_3= 'VAR'
                    {
                    enumLiteral_3=(Token)match(input,73,FOLLOW_2); 

                    				current = grammarAccess.getParameterKindRuleAccess().getVAREnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getParameterKindRuleAccess().getVAREnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParameterKindRule"


    // $ANTLR start "ruleType"
    // InternalHenshin_text.g:4114:1: ruleType returns [Enumerator current=null] : ( (enumLiteral_0= 'EBigDecimal' ) | (enumLiteral_1= 'EBigInteger' ) | (enumLiteral_2= 'EBoolean' ) | (enumLiteral_3= 'EBooleanObject' ) | (enumLiteral_4= 'EByte' ) | (enumLiteral_5= 'EByteArray' ) | (enumLiteral_6= 'EByteObject' ) | (enumLiteral_7= 'EChar' ) | (enumLiteral_8= 'ECharacterObject' ) | (enumLiteral_9= 'EDate' ) | (enumLiteral_10= 'EDiagnosticChain' ) | (enumLiteral_11= 'EDouble' ) | (enumLiteral_12= 'EDoubleObject' ) | (enumLiteral_13= 'EEList' ) | (enumLiteral_14= 'EEnumerator' ) | (enumLiteral_15= 'EFeatureMap' ) | (enumLiteral_16= 'EFeatureMapEntry' ) | (enumLiteral_17= 'EFloat' ) | (enumLiteral_18= 'EFloatObject' ) | (enumLiteral_19= 'EInt' ) | (enumLiteral_20= 'EIntegerObject' ) | (enumLiteral_21= 'ETreeIterator' ) | (enumLiteral_22= 'EInvocationTargetException' ) | (enumLiteral_23= 'EJavaClass' ) | (enumLiteral_24= 'EJavaObject' ) | (enumLiteral_25= 'ELong' ) | (enumLiteral_26= 'ELongObject' ) | (enumLiteral_27= 'EMap' ) | (enumLiteral_28= 'EResource' ) | (enumLiteral_29= 'EResourceSet' ) | (enumLiteral_30= 'EShort' ) | (enumLiteral_31= 'EShortObject' ) | (enumLiteral_32= 'EString' ) ) ;
    public final Enumerator ruleType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;
        Token enumLiteral_6=null;
        Token enumLiteral_7=null;
        Token enumLiteral_8=null;
        Token enumLiteral_9=null;
        Token enumLiteral_10=null;
        Token enumLiteral_11=null;
        Token enumLiteral_12=null;
        Token enumLiteral_13=null;
        Token enumLiteral_14=null;
        Token enumLiteral_15=null;
        Token enumLiteral_16=null;
        Token enumLiteral_17=null;
        Token enumLiteral_18=null;
        Token enumLiteral_19=null;
        Token enumLiteral_20=null;
        Token enumLiteral_21=null;
        Token enumLiteral_22=null;
        Token enumLiteral_23=null;
        Token enumLiteral_24=null;
        Token enumLiteral_25=null;
        Token enumLiteral_26=null;
        Token enumLiteral_27=null;
        Token enumLiteral_28=null;
        Token enumLiteral_29=null;
        Token enumLiteral_30=null;
        Token enumLiteral_31=null;
        Token enumLiteral_32=null;


        	enterRule();

        try {
            // InternalHenshin_text.g:4120:2: ( ( (enumLiteral_0= 'EBigDecimal' ) | (enumLiteral_1= 'EBigInteger' ) | (enumLiteral_2= 'EBoolean' ) | (enumLiteral_3= 'EBooleanObject' ) | (enumLiteral_4= 'EByte' ) | (enumLiteral_5= 'EByteArray' ) | (enumLiteral_6= 'EByteObject' ) | (enumLiteral_7= 'EChar' ) | (enumLiteral_8= 'ECharacterObject' ) | (enumLiteral_9= 'EDate' ) | (enumLiteral_10= 'EDiagnosticChain' ) | (enumLiteral_11= 'EDouble' ) | (enumLiteral_12= 'EDoubleObject' ) | (enumLiteral_13= 'EEList' ) | (enumLiteral_14= 'EEnumerator' ) | (enumLiteral_15= 'EFeatureMap' ) | (enumLiteral_16= 'EFeatureMapEntry' ) | (enumLiteral_17= 'EFloat' ) | (enumLiteral_18= 'EFloatObject' ) | (enumLiteral_19= 'EInt' ) | (enumLiteral_20= 'EIntegerObject' ) | (enumLiteral_21= 'ETreeIterator' ) | (enumLiteral_22= 'EInvocationTargetException' ) | (enumLiteral_23= 'EJavaClass' ) | (enumLiteral_24= 'EJavaObject' ) | (enumLiteral_25= 'ELong' ) | (enumLiteral_26= 'ELongObject' ) | (enumLiteral_27= 'EMap' ) | (enumLiteral_28= 'EResource' ) | (enumLiteral_29= 'EResourceSet' ) | (enumLiteral_30= 'EShort' ) | (enumLiteral_31= 'EShortObject' ) | (enumLiteral_32= 'EString' ) ) )
            // InternalHenshin_text.g:4121:2: ( (enumLiteral_0= 'EBigDecimal' ) | (enumLiteral_1= 'EBigInteger' ) | (enumLiteral_2= 'EBoolean' ) | (enumLiteral_3= 'EBooleanObject' ) | (enumLiteral_4= 'EByte' ) | (enumLiteral_5= 'EByteArray' ) | (enumLiteral_6= 'EByteObject' ) | (enumLiteral_7= 'EChar' ) | (enumLiteral_8= 'ECharacterObject' ) | (enumLiteral_9= 'EDate' ) | (enumLiteral_10= 'EDiagnosticChain' ) | (enumLiteral_11= 'EDouble' ) | (enumLiteral_12= 'EDoubleObject' ) | (enumLiteral_13= 'EEList' ) | (enumLiteral_14= 'EEnumerator' ) | (enumLiteral_15= 'EFeatureMap' ) | (enumLiteral_16= 'EFeatureMapEntry' ) | (enumLiteral_17= 'EFloat' ) | (enumLiteral_18= 'EFloatObject' ) | (enumLiteral_19= 'EInt' ) | (enumLiteral_20= 'EIntegerObject' ) | (enumLiteral_21= 'ETreeIterator' ) | (enumLiteral_22= 'EInvocationTargetException' ) | (enumLiteral_23= 'EJavaClass' ) | (enumLiteral_24= 'EJavaObject' ) | (enumLiteral_25= 'ELong' ) | (enumLiteral_26= 'ELongObject' ) | (enumLiteral_27= 'EMap' ) | (enumLiteral_28= 'EResource' ) | (enumLiteral_29= 'EResourceSet' ) | (enumLiteral_30= 'EShort' ) | (enumLiteral_31= 'EShortObject' ) | (enumLiteral_32= 'EString' ) )
            {
            // InternalHenshin_text.g:4121:2: ( (enumLiteral_0= 'EBigDecimal' ) | (enumLiteral_1= 'EBigInteger' ) | (enumLiteral_2= 'EBoolean' ) | (enumLiteral_3= 'EBooleanObject' ) | (enumLiteral_4= 'EByte' ) | (enumLiteral_5= 'EByteArray' ) | (enumLiteral_6= 'EByteObject' ) | (enumLiteral_7= 'EChar' ) | (enumLiteral_8= 'ECharacterObject' ) | (enumLiteral_9= 'EDate' ) | (enumLiteral_10= 'EDiagnosticChain' ) | (enumLiteral_11= 'EDouble' ) | (enumLiteral_12= 'EDoubleObject' ) | (enumLiteral_13= 'EEList' ) | (enumLiteral_14= 'EEnumerator' ) | (enumLiteral_15= 'EFeatureMap' ) | (enumLiteral_16= 'EFeatureMapEntry' ) | (enumLiteral_17= 'EFloat' ) | (enumLiteral_18= 'EFloatObject' ) | (enumLiteral_19= 'EInt' ) | (enumLiteral_20= 'EIntegerObject' ) | (enumLiteral_21= 'ETreeIterator' ) | (enumLiteral_22= 'EInvocationTargetException' ) | (enumLiteral_23= 'EJavaClass' ) | (enumLiteral_24= 'EJavaObject' ) | (enumLiteral_25= 'ELong' ) | (enumLiteral_26= 'ELongObject' ) | (enumLiteral_27= 'EMap' ) | (enumLiteral_28= 'EResource' ) | (enumLiteral_29= 'EResourceSet' ) | (enumLiteral_30= 'EShort' ) | (enumLiteral_31= 'EShortObject' ) | (enumLiteral_32= 'EString' ) )
            int alt70=33;
            switch ( input.LA(1) ) {
            case 74:
                {
                alt70=1;
                }
                break;
            case 75:
                {
                alt70=2;
                }
                break;
            case 76:
                {
                alt70=3;
                }
                break;
            case 77:
                {
                alt70=4;
                }
                break;
            case 78:
                {
                alt70=5;
                }
                break;
            case 79:
                {
                alt70=6;
                }
                break;
            case 80:
                {
                alt70=7;
                }
                break;
            case 81:
                {
                alt70=8;
                }
                break;
            case 82:
                {
                alt70=9;
                }
                break;
            case 83:
                {
                alt70=10;
                }
                break;
            case 84:
                {
                alt70=11;
                }
                break;
            case 85:
                {
                alt70=12;
                }
                break;
            case 86:
                {
                alt70=13;
                }
                break;
            case 87:
                {
                alt70=14;
                }
                break;
            case 88:
                {
                alt70=15;
                }
                break;
            case 89:
                {
                alt70=16;
                }
                break;
            case 90:
                {
                alt70=17;
                }
                break;
            case 91:
                {
                alt70=18;
                }
                break;
            case 92:
                {
                alt70=19;
                }
                break;
            case 93:
                {
                alt70=20;
                }
                break;
            case 94:
                {
                alt70=21;
                }
                break;
            case 95:
                {
                alt70=22;
                }
                break;
            case 96:
                {
                alt70=23;
                }
                break;
            case 97:
                {
                alt70=24;
                }
                break;
            case 98:
                {
                alt70=25;
                }
                break;
            case 99:
                {
                alt70=26;
                }
                break;
            case 100:
                {
                alt70=27;
                }
                break;
            case 101:
                {
                alt70=28;
                }
                break;
            case 102:
                {
                alt70=29;
                }
                break;
            case 103:
                {
                alt70=30;
                }
                break;
            case 104:
                {
                alt70=31;
                }
                break;
            case 105:
                {
                alt70=32;
                }
                break;
            case 106:
                {
                alt70=33;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 70, 0, input);

                throw nvae;
            }

            switch (alt70) {
                case 1 :
                    // InternalHenshin_text.g:4122:3: (enumLiteral_0= 'EBigDecimal' )
                    {
                    // InternalHenshin_text.g:4122:3: (enumLiteral_0= 'EBigDecimal' )
                    // InternalHenshin_text.g:4123:4: enumLiteral_0= 'EBigDecimal'
                    {
                    enumLiteral_0=(Token)match(input,74,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEBigDecimalEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getTypeAccess().getEBigDecimalEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalHenshin_text.g:4130:3: (enumLiteral_1= 'EBigInteger' )
                    {
                    // InternalHenshin_text.g:4130:3: (enumLiteral_1= 'EBigInteger' )
                    // InternalHenshin_text.g:4131:4: enumLiteral_1= 'EBigInteger'
                    {
                    enumLiteral_1=(Token)match(input,75,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEBigIntegerEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getTypeAccess().getEBigIntegerEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalHenshin_text.g:4138:3: (enumLiteral_2= 'EBoolean' )
                    {
                    // InternalHenshin_text.g:4138:3: (enumLiteral_2= 'EBoolean' )
                    // InternalHenshin_text.g:4139:4: enumLiteral_2= 'EBoolean'
                    {
                    enumLiteral_2=(Token)match(input,76,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEBooleanEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getTypeAccess().getEBooleanEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalHenshin_text.g:4146:3: (enumLiteral_3= 'EBooleanObject' )
                    {
                    // InternalHenshin_text.g:4146:3: (enumLiteral_3= 'EBooleanObject' )
                    // InternalHenshin_text.g:4147:4: enumLiteral_3= 'EBooleanObject'
                    {
                    enumLiteral_3=(Token)match(input,77,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEBooleanObjectEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getTypeAccess().getEBooleanObjectEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalHenshin_text.g:4154:3: (enumLiteral_4= 'EByte' )
                    {
                    // InternalHenshin_text.g:4154:3: (enumLiteral_4= 'EByte' )
                    // InternalHenshin_text.g:4155:4: enumLiteral_4= 'EByte'
                    {
                    enumLiteral_4=(Token)match(input,78,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEByteEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getTypeAccess().getEByteEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalHenshin_text.g:4162:3: (enumLiteral_5= 'EByteArray' )
                    {
                    // InternalHenshin_text.g:4162:3: (enumLiteral_5= 'EByteArray' )
                    // InternalHenshin_text.g:4163:4: enumLiteral_5= 'EByteArray'
                    {
                    enumLiteral_5=(Token)match(input,79,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEByteArrayEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getTypeAccess().getEByteArrayEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;
                case 7 :
                    // InternalHenshin_text.g:4170:3: (enumLiteral_6= 'EByteObject' )
                    {
                    // InternalHenshin_text.g:4170:3: (enumLiteral_6= 'EByteObject' )
                    // InternalHenshin_text.g:4171:4: enumLiteral_6= 'EByteObject'
                    {
                    enumLiteral_6=(Token)match(input,80,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEByteObjectEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_6, grammarAccess.getTypeAccess().getEByteObjectEnumLiteralDeclaration_6());
                    			

                    }


                    }
                    break;
                case 8 :
                    // InternalHenshin_text.g:4178:3: (enumLiteral_7= 'EChar' )
                    {
                    // InternalHenshin_text.g:4178:3: (enumLiteral_7= 'EChar' )
                    // InternalHenshin_text.g:4179:4: enumLiteral_7= 'EChar'
                    {
                    enumLiteral_7=(Token)match(input,81,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getECharEnumLiteralDeclaration_7().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_7, grammarAccess.getTypeAccess().getECharEnumLiteralDeclaration_7());
                    			

                    }


                    }
                    break;
                case 9 :
                    // InternalHenshin_text.g:4186:3: (enumLiteral_8= 'ECharacterObject' )
                    {
                    // InternalHenshin_text.g:4186:3: (enumLiteral_8= 'ECharacterObject' )
                    // InternalHenshin_text.g:4187:4: enumLiteral_8= 'ECharacterObject'
                    {
                    enumLiteral_8=(Token)match(input,82,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getECharacterObjectEnumLiteralDeclaration_8().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_8, grammarAccess.getTypeAccess().getECharacterObjectEnumLiteralDeclaration_8());
                    			

                    }


                    }
                    break;
                case 10 :
                    // InternalHenshin_text.g:4194:3: (enumLiteral_9= 'EDate' )
                    {
                    // InternalHenshin_text.g:4194:3: (enumLiteral_9= 'EDate' )
                    // InternalHenshin_text.g:4195:4: enumLiteral_9= 'EDate'
                    {
                    enumLiteral_9=(Token)match(input,83,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEDateEnumLiteralDeclaration_9().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_9, grammarAccess.getTypeAccess().getEDateEnumLiteralDeclaration_9());
                    			

                    }


                    }
                    break;
                case 11 :
                    // InternalHenshin_text.g:4202:3: (enumLiteral_10= 'EDiagnosticChain' )
                    {
                    // InternalHenshin_text.g:4202:3: (enumLiteral_10= 'EDiagnosticChain' )
                    // InternalHenshin_text.g:4203:4: enumLiteral_10= 'EDiagnosticChain'
                    {
                    enumLiteral_10=(Token)match(input,84,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEDiagnosticChainEnumLiteralDeclaration_10().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_10, grammarAccess.getTypeAccess().getEDiagnosticChainEnumLiteralDeclaration_10());
                    			

                    }


                    }
                    break;
                case 12 :
                    // InternalHenshin_text.g:4210:3: (enumLiteral_11= 'EDouble' )
                    {
                    // InternalHenshin_text.g:4210:3: (enumLiteral_11= 'EDouble' )
                    // InternalHenshin_text.g:4211:4: enumLiteral_11= 'EDouble'
                    {
                    enumLiteral_11=(Token)match(input,85,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEDoubleEnumLiteralDeclaration_11().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_11, grammarAccess.getTypeAccess().getEDoubleEnumLiteralDeclaration_11());
                    			

                    }


                    }
                    break;
                case 13 :
                    // InternalHenshin_text.g:4218:3: (enumLiteral_12= 'EDoubleObject' )
                    {
                    // InternalHenshin_text.g:4218:3: (enumLiteral_12= 'EDoubleObject' )
                    // InternalHenshin_text.g:4219:4: enumLiteral_12= 'EDoubleObject'
                    {
                    enumLiteral_12=(Token)match(input,86,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEDoubleObjectEnumLiteralDeclaration_12().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_12, grammarAccess.getTypeAccess().getEDoubleObjectEnumLiteralDeclaration_12());
                    			

                    }


                    }
                    break;
                case 14 :
                    // InternalHenshin_text.g:4226:3: (enumLiteral_13= 'EEList' )
                    {
                    // InternalHenshin_text.g:4226:3: (enumLiteral_13= 'EEList' )
                    // InternalHenshin_text.g:4227:4: enumLiteral_13= 'EEList'
                    {
                    enumLiteral_13=(Token)match(input,87,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEEListEnumLiteralDeclaration_13().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_13, grammarAccess.getTypeAccess().getEEListEnumLiteralDeclaration_13());
                    			

                    }


                    }
                    break;
                case 15 :
                    // InternalHenshin_text.g:4234:3: (enumLiteral_14= 'EEnumerator' )
                    {
                    // InternalHenshin_text.g:4234:3: (enumLiteral_14= 'EEnumerator' )
                    // InternalHenshin_text.g:4235:4: enumLiteral_14= 'EEnumerator'
                    {
                    enumLiteral_14=(Token)match(input,88,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEEnumeratorEnumLiteralDeclaration_14().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_14, grammarAccess.getTypeAccess().getEEnumeratorEnumLiteralDeclaration_14());
                    			

                    }


                    }
                    break;
                case 16 :
                    // InternalHenshin_text.g:4242:3: (enumLiteral_15= 'EFeatureMap' )
                    {
                    // InternalHenshin_text.g:4242:3: (enumLiteral_15= 'EFeatureMap' )
                    // InternalHenshin_text.g:4243:4: enumLiteral_15= 'EFeatureMap'
                    {
                    enumLiteral_15=(Token)match(input,89,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEFeatureMapEnumLiteralDeclaration_15().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_15, grammarAccess.getTypeAccess().getEFeatureMapEnumLiteralDeclaration_15());
                    			

                    }


                    }
                    break;
                case 17 :
                    // InternalHenshin_text.g:4250:3: (enumLiteral_16= 'EFeatureMapEntry' )
                    {
                    // InternalHenshin_text.g:4250:3: (enumLiteral_16= 'EFeatureMapEntry' )
                    // InternalHenshin_text.g:4251:4: enumLiteral_16= 'EFeatureMapEntry'
                    {
                    enumLiteral_16=(Token)match(input,90,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEFeatureMapEntryEnumLiteralDeclaration_16().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_16, grammarAccess.getTypeAccess().getEFeatureMapEntryEnumLiteralDeclaration_16());
                    			

                    }


                    }
                    break;
                case 18 :
                    // InternalHenshin_text.g:4258:3: (enumLiteral_17= 'EFloat' )
                    {
                    // InternalHenshin_text.g:4258:3: (enumLiteral_17= 'EFloat' )
                    // InternalHenshin_text.g:4259:4: enumLiteral_17= 'EFloat'
                    {
                    enumLiteral_17=(Token)match(input,91,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEFloatEnumLiteralDeclaration_17().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_17, grammarAccess.getTypeAccess().getEFloatEnumLiteralDeclaration_17());
                    			

                    }


                    }
                    break;
                case 19 :
                    // InternalHenshin_text.g:4266:3: (enumLiteral_18= 'EFloatObject' )
                    {
                    // InternalHenshin_text.g:4266:3: (enumLiteral_18= 'EFloatObject' )
                    // InternalHenshin_text.g:4267:4: enumLiteral_18= 'EFloatObject'
                    {
                    enumLiteral_18=(Token)match(input,92,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEFloatObjectEnumLiteralDeclaration_18().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_18, grammarAccess.getTypeAccess().getEFloatObjectEnumLiteralDeclaration_18());
                    			

                    }


                    }
                    break;
                case 20 :
                    // InternalHenshin_text.g:4274:3: (enumLiteral_19= 'EInt' )
                    {
                    // InternalHenshin_text.g:4274:3: (enumLiteral_19= 'EInt' )
                    // InternalHenshin_text.g:4275:4: enumLiteral_19= 'EInt'
                    {
                    enumLiteral_19=(Token)match(input,93,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEIntEnumLiteralDeclaration_19().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_19, grammarAccess.getTypeAccess().getEIntEnumLiteralDeclaration_19());
                    			

                    }


                    }
                    break;
                case 21 :
                    // InternalHenshin_text.g:4282:3: (enumLiteral_20= 'EIntegerObject' )
                    {
                    // InternalHenshin_text.g:4282:3: (enumLiteral_20= 'EIntegerObject' )
                    // InternalHenshin_text.g:4283:4: enumLiteral_20= 'EIntegerObject'
                    {
                    enumLiteral_20=(Token)match(input,94,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEIntegerObjectEnumLiteralDeclaration_20().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_20, grammarAccess.getTypeAccess().getEIntegerObjectEnumLiteralDeclaration_20());
                    			

                    }


                    }
                    break;
                case 22 :
                    // InternalHenshin_text.g:4290:3: (enumLiteral_21= 'ETreeIterator' )
                    {
                    // InternalHenshin_text.g:4290:3: (enumLiteral_21= 'ETreeIterator' )
                    // InternalHenshin_text.g:4291:4: enumLiteral_21= 'ETreeIterator'
                    {
                    enumLiteral_21=(Token)match(input,95,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getETreeIteratorEnumLiteralDeclaration_21().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_21, grammarAccess.getTypeAccess().getETreeIteratorEnumLiteralDeclaration_21());
                    			

                    }


                    }
                    break;
                case 23 :
                    // InternalHenshin_text.g:4298:3: (enumLiteral_22= 'EInvocationTargetException' )
                    {
                    // InternalHenshin_text.g:4298:3: (enumLiteral_22= 'EInvocationTargetException' )
                    // InternalHenshin_text.g:4299:4: enumLiteral_22= 'EInvocationTargetException'
                    {
                    enumLiteral_22=(Token)match(input,96,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEInvocationTargetExceptionEnumLiteralDeclaration_22().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_22, grammarAccess.getTypeAccess().getEInvocationTargetExceptionEnumLiteralDeclaration_22());
                    			

                    }


                    }
                    break;
                case 24 :
                    // InternalHenshin_text.g:4306:3: (enumLiteral_23= 'EJavaClass' )
                    {
                    // InternalHenshin_text.g:4306:3: (enumLiteral_23= 'EJavaClass' )
                    // InternalHenshin_text.g:4307:4: enumLiteral_23= 'EJavaClass'
                    {
                    enumLiteral_23=(Token)match(input,97,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEJavaClassEnumLiteralDeclaration_23().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_23, grammarAccess.getTypeAccess().getEJavaClassEnumLiteralDeclaration_23());
                    			

                    }


                    }
                    break;
                case 25 :
                    // InternalHenshin_text.g:4314:3: (enumLiteral_24= 'EJavaObject' )
                    {
                    // InternalHenshin_text.g:4314:3: (enumLiteral_24= 'EJavaObject' )
                    // InternalHenshin_text.g:4315:4: enumLiteral_24= 'EJavaObject'
                    {
                    enumLiteral_24=(Token)match(input,98,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEJavaObjectEnumLiteralDeclaration_24().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_24, grammarAccess.getTypeAccess().getEJavaObjectEnumLiteralDeclaration_24());
                    			

                    }


                    }
                    break;
                case 26 :
                    // InternalHenshin_text.g:4322:3: (enumLiteral_25= 'ELong' )
                    {
                    // InternalHenshin_text.g:4322:3: (enumLiteral_25= 'ELong' )
                    // InternalHenshin_text.g:4323:4: enumLiteral_25= 'ELong'
                    {
                    enumLiteral_25=(Token)match(input,99,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getELongEnumLiteralDeclaration_25().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_25, grammarAccess.getTypeAccess().getELongEnumLiteralDeclaration_25());
                    			

                    }


                    }
                    break;
                case 27 :
                    // InternalHenshin_text.g:4330:3: (enumLiteral_26= 'ELongObject' )
                    {
                    // InternalHenshin_text.g:4330:3: (enumLiteral_26= 'ELongObject' )
                    // InternalHenshin_text.g:4331:4: enumLiteral_26= 'ELongObject'
                    {
                    enumLiteral_26=(Token)match(input,100,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getELongObjectEnumLiteralDeclaration_26().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_26, grammarAccess.getTypeAccess().getELongObjectEnumLiteralDeclaration_26());
                    			

                    }


                    }
                    break;
                case 28 :
                    // InternalHenshin_text.g:4338:3: (enumLiteral_27= 'EMap' )
                    {
                    // InternalHenshin_text.g:4338:3: (enumLiteral_27= 'EMap' )
                    // InternalHenshin_text.g:4339:4: enumLiteral_27= 'EMap'
                    {
                    enumLiteral_27=(Token)match(input,101,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEMapEnumLiteralDeclaration_27().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_27, grammarAccess.getTypeAccess().getEMapEnumLiteralDeclaration_27());
                    			

                    }


                    }
                    break;
                case 29 :
                    // InternalHenshin_text.g:4346:3: (enumLiteral_28= 'EResource' )
                    {
                    // InternalHenshin_text.g:4346:3: (enumLiteral_28= 'EResource' )
                    // InternalHenshin_text.g:4347:4: enumLiteral_28= 'EResource'
                    {
                    enumLiteral_28=(Token)match(input,102,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEResourceEnumLiteralDeclaration_28().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_28, grammarAccess.getTypeAccess().getEResourceEnumLiteralDeclaration_28());
                    			

                    }


                    }
                    break;
                case 30 :
                    // InternalHenshin_text.g:4354:3: (enumLiteral_29= 'EResourceSet' )
                    {
                    // InternalHenshin_text.g:4354:3: (enumLiteral_29= 'EResourceSet' )
                    // InternalHenshin_text.g:4355:4: enumLiteral_29= 'EResourceSet'
                    {
                    enumLiteral_29=(Token)match(input,103,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEResourceSetEnumLiteralDeclaration_29().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_29, grammarAccess.getTypeAccess().getEResourceSetEnumLiteralDeclaration_29());
                    			

                    }


                    }
                    break;
                case 31 :
                    // InternalHenshin_text.g:4362:3: (enumLiteral_30= 'EShort' )
                    {
                    // InternalHenshin_text.g:4362:3: (enumLiteral_30= 'EShort' )
                    // InternalHenshin_text.g:4363:4: enumLiteral_30= 'EShort'
                    {
                    enumLiteral_30=(Token)match(input,104,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEShortEnumLiteralDeclaration_30().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_30, grammarAccess.getTypeAccess().getEShortEnumLiteralDeclaration_30());
                    			

                    }


                    }
                    break;
                case 32 :
                    // InternalHenshin_text.g:4370:3: (enumLiteral_31= 'EShortObject' )
                    {
                    // InternalHenshin_text.g:4370:3: (enumLiteral_31= 'EShortObject' )
                    // InternalHenshin_text.g:4371:4: enumLiteral_31= 'EShortObject'
                    {
                    enumLiteral_31=(Token)match(input,105,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEShortObjectEnumLiteralDeclaration_31().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_31, grammarAccess.getTypeAccess().getEShortObjectEnumLiteralDeclaration_31());
                    			

                    }


                    }
                    break;
                case 33 :
                    // InternalHenshin_text.g:4378:3: (enumLiteral_32= 'EString' )
                    {
                    // InternalHenshin_text.g:4378:3: (enumLiteral_32= 'EString' )
                    // InternalHenshin_text.g:4379:4: enumLiteral_32= 'EString'
                    {
                    enumLiteral_32=(Token)match(input,106,FOLLOW_2); 

                    				current = grammarAccess.getTypeAccess().getEStringEnumLiteralDeclaration_32().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_32, grammarAccess.getTypeAccess().getEStringEnumLiteralDeclaration_32());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleType"

    // Delegated rules


    protected DFA68 dfa68 = new DFA68(this);
    static final String dfa_1s = "\14\uffff";
    static final String dfa_2s = "\1\uffff\1\11\10\uffff\1\13\1\uffff";
    static final String dfa_3s = "\2\4\5\uffff\1\4\2\uffff\1\4\1\uffff";
    static final String dfa_4s = "\1\73\1\105\5\uffff\1\4\2\uffff\1\105\1\uffff";
    static final String dfa_5s = "\2\uffff\1\4\1\5\1\6\1\7\1\10\1\uffff\1\2\1\1\1\uffff\1\3";
    static final String dfa_6s = "\14\uffff}>";
    static final String[] dfa_7s = {
            "\1\1\1\2\1\3\1\4\1\5\61\uffff\2\6",
            "\1\11\11\uffff\1\7\1\uffff\1\10\2\11\1\uffff\1\11\6\uffff\1\11\1\uffff\5\11\6\uffff\1\11\3\uffff\1\11\1\uffff\1\11\15\uffff\12\11",
            "",
            "",
            "",
            "",
            "",
            "\1\12",
            "",
            "",
            "\1\13\11\uffff\1\10\1\uffff\1\10\2\13\1\uffff\1\13\6\uffff\1\13\1\uffff\5\13\6\uffff\1\13\3\uffff\1\13\1\uffff\1\13\15\uffff\12\13",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA68 extends DFA {

        public DFA68(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 68;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "3753:2: ( ( () ( (otherlv_1= RULE_ID ) ) ) | ( () ( (lv_value_3_0= ruleEString ) ) otherlv_4= '(' ( ( (lv_javaParameter_5_0= ruleExpression ) ) (otherlv_6= ',' ( (lv_javaParameter_7_0= ruleExpression ) ) )* )? otherlv_8= ')' ) | ( () ( (lv_value_10_0= ruleJavaAttribute ) ) ) | ( () ( (lv_value_12_0= RULE_STRING ) ) ) | ( () ( (lv_value_14_0= RULE_DECIMAL ) ) ) | ( () ( (lv_value_16_0= RULE_NEGATIVE ) ) ) | ( () ( (lv_value_18_0= RULE_INT ) ) ) | ( () ( (lv_value_20_0= ruleEBoolean ) ) ) )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x000000000020A002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000208002L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000040010L,0x00000000000003C0L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000060000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000010L,0x00000000000003C0L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000013C00000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000013D00000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x039E000000080010L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x039E000000180010L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0C00000000000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0C008000000101F0L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000008020000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x00000667E0100000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x00000003E0000010L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x00000103E0100010L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000800000010010L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0001000000100000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000300000000002L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000046400100000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000040010L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x039E000000080012L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x039E0000000C0010L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0040000000000002L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000010L,0x000007FFFFFFFC00L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000100000000002L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x3000000000000002L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0xC000000000000002L,0x0000000000000003L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000002L,0x000000000000000CL});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000030L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0C008000000501F0L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000004000L});

}