package de.cau.cs.kieler.core.kexpressions.parser.antlr.internal; 

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
import de.cau.cs.kieler.core.kexpressions.services.KExpressionsGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalKExpressionsParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_HOSTCODE", "RULE_INT", "RULE_FLOAT", "RULE_BOOLEAN", "RULE_COMMENT_ANNOTATION", "RULE_STRING", "RULE_ML_COMMENT", "RULE_NUMBER", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'('", "')'", "'@'", "'['", "']'", "'.'", "'=='", "'<'", "'<='", "'>'", "'>='", "'<>'", "'pre'", "'|'", "'&'", "'!'", "'+'", "'-'", "'*'", "'%'", "'/'", "'val'", "'||'", "'&&'"
    };
    public static final int RULE_BOOLEAN=8;
    public static final int RULE_ID=4;
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int RULE_ANY_OTHER=15;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int RULE_FLOAT=7;
    public static final int RULE_SL_COMMENT=13;
    public static final int EOF=-1;
    public static final int RULE_HOSTCODE=5;
    public static final int RULE_ML_COMMENT=11;
    public static final int T__19=19;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int RULE_STRING=10;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__16=16;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__18=18;
    public static final int T__36=36;
    public static final int T__17=17;
    public static final int RULE_NUMBER=12;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int RULE_INT=6;
    public static final int RULE_WS=14;
    public static final int RULE_COMMENT_ANNOTATION=9;

    // delegates
    // delegators


        public InternalKExpressionsParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalKExpressionsParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalKExpressionsParser.tokenNames; }
    public String getGrammarFileName() { return "../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g"; }



    /*
      This grammar contains a lot of empty actions to work around a bug in ANTLR.
      Otherwise the ANTLR tool will create synpreds that cannot be compiled in some rare cases.
    */
     
     	private KExpressionsGrammarAccess grammarAccess;
     	
        public InternalKExpressionsParser(TokenStream input, KExpressionsGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "Root";	
       	}
       	
       	@Override
       	protected KExpressionsGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleRoot"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:74:1: entryRuleRoot returns [EObject current=null] : iv_ruleRoot= ruleRoot EOF ;
    public final EObject entryRuleRoot() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRoot = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:75:2: (iv_ruleRoot= ruleRoot EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:76:2: iv_ruleRoot= ruleRoot EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getRootRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleRoot_in_entryRuleRoot81);
            iv_ruleRoot=ruleRoot();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleRoot; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleRoot91); if (state.failed) return current;

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
    // $ANTLR end "entryRuleRoot"


    // $ANTLR start "ruleRoot"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:83:1: ruleRoot returns [EObject current=null] : this_Expression_0= ruleExpression ;
    public final EObject ruleRoot() throws RecognitionException {
        EObject current = null;

        EObject this_Expression_0 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:86:28: (this_Expression_0= ruleExpression )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:88:2: this_Expression_0= ruleExpression
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getRootAccess().getExpressionParserRuleCall()); 
                  
            }
            pushFollow(FollowSets000.FOLLOW_ruleExpression_in_ruleRoot140);
            this_Expression_0=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_Expression_0; 
                      afterParserOrEnumRuleCall();
                  
            }

            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleRoot"


    // $ANTLR start "entryRuleExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:107:1: entryRuleExpression returns [EObject current=null] : iv_ruleExpression= ruleExpression EOF ;
    public final EObject entryRuleExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpression = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:108:2: (iv_ruleExpression= ruleExpression EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:109:2: iv_ruleExpression= ruleExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExpressionRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleExpression_in_entryRuleExpression174);
            iv_ruleExpression=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleExpression; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleExpression184); if (state.failed) return current;

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
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:116:1: ruleExpression returns [EObject current=null] : (this_BoolExpression_0= ruleBoolExpression | this_ValuedExpression_1= ruleValuedExpression ) ;
    public final EObject ruleExpression() throws RecognitionException {
        EObject current = null;

        EObject this_BoolExpression_0 = null;

        EObject this_ValuedExpression_1 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:119:28: ( (this_BoolExpression_0= ruleBoolExpression | this_ValuedExpression_1= ruleValuedExpression ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:120:1: (this_BoolExpression_0= ruleBoolExpression | this_ValuedExpression_1= ruleValuedExpression )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:120:1: (this_BoolExpression_0= ruleBoolExpression | this_ValuedExpression_1= ruleValuedExpression )
            int alt1=2;
            alt1 = dfa1.predict(input);
            switch (alt1) {
                case 1 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:121:2: this_BoolExpression_0= ruleBoolExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getExpressionAccess().getBoolExpressionParserRuleCall_0()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleBoolExpression_in_ruleExpression234);
                    this_BoolExpression_0=ruleBoolExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_BoolExpression_0; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:134:2: this_ValuedExpression_1= ruleValuedExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getExpressionAccess().getValuedExpressionParserRuleCall_1()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleValuedExpression_in_ruleExpression264);
                    this_ValuedExpression_1=ruleValuedExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_ValuedExpression_1; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleExpression"


    // $ANTLR start "entryRuleBoolExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:153:1: entryRuleBoolExpression returns [EObject current=null] : iv_ruleBoolExpression= ruleBoolExpression EOF ;
    public final EObject entryRuleBoolExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBoolExpression = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:154:2: (iv_ruleBoolExpression= ruleBoolExpression EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:155:2: iv_ruleBoolExpression= ruleBoolExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBoolExpressionRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleBoolExpression_in_entryRuleBoolExpression299);
            iv_ruleBoolExpression=ruleBoolExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBoolExpression; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleBoolExpression309); if (state.failed) return current;

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
    // $ANTLR end "entryRuleBoolExpression"


    // $ANTLR start "ruleBoolExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:162:1: ruleBoolExpression returns [EObject current=null] : this_OrAndExpression_0= ruleOrAndExpression ;
    public final EObject ruleBoolExpression() throws RecognitionException {
        EObject current = null;

        EObject this_OrAndExpression_0 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:165:28: (this_OrAndExpression_0= ruleOrAndExpression )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:167:2: this_OrAndExpression_0= ruleOrAndExpression
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getBoolExpressionAccess().getOrAndExpressionParserRuleCall()); 
                  
            }
            pushFollow(FollowSets000.FOLLOW_ruleOrAndExpression_in_ruleBoolExpression358);
            this_OrAndExpression_0=ruleOrAndExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_OrAndExpression_0; 
                      afterParserOrEnumRuleCall();
                  
            }

            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleBoolExpression"


    // $ANTLR start "entryRuleOrAndExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:186:1: entryRuleOrAndExpression returns [EObject current=null] : iv_ruleOrAndExpression= ruleOrAndExpression EOF ;
    public final EObject entryRuleOrAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrAndExpression = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:187:2: (iv_ruleOrAndExpression= ruleOrAndExpression EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:188:2: iv_ruleOrAndExpression= ruleOrAndExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOrAndExpressionRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleOrAndExpression_in_entryRuleOrAndExpression392);
            iv_ruleOrAndExpression=ruleOrAndExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOrAndExpression; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleOrAndExpression402); if (state.failed) return current;

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
    // $ANTLR end "entryRuleOrAndExpression"


    // $ANTLR start "ruleOrAndExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:195:1: ruleOrAndExpression returns [EObject current=null] : (this_CompareOperation_0= ruleCompareOperation ( ( () ( ( (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator ) ) ) ( (lv_subExpressions_3_0= ruleCompareOperation ) ) )* | ( () ( ( (lv_operator_5_1= ruleAndOperator | lv_operator_5_2= ruleLogicalAndOperator ) ) ) ( (lv_subExpressions_6_0= ruleCompareOperation ) ) )* ) ) ;
    public final EObject ruleOrAndExpression() throws RecognitionException {
        EObject current = null;

        EObject this_CompareOperation_0 = null;

        Enumerator lv_operator_2_1 = null;

        Enumerator lv_operator_2_2 = null;

        EObject lv_subExpressions_3_0 = null;

        Enumerator lv_operator_5_1 = null;

        Enumerator lv_operator_5_2 = null;

        EObject lv_subExpressions_6_0 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:198:28: ( (this_CompareOperation_0= ruleCompareOperation ( ( () ( ( (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator ) ) ) ( (lv_subExpressions_3_0= ruleCompareOperation ) ) )* | ( () ( ( (lv_operator_5_1= ruleAndOperator | lv_operator_5_2= ruleLogicalAndOperator ) ) ) ( (lv_subExpressions_6_0= ruleCompareOperation ) ) )* ) ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:199:1: (this_CompareOperation_0= ruleCompareOperation ( ( () ( ( (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator ) ) ) ( (lv_subExpressions_3_0= ruleCompareOperation ) ) )* | ( () ( ( (lv_operator_5_1= ruleAndOperator | lv_operator_5_2= ruleLogicalAndOperator ) ) ) ( (lv_subExpressions_6_0= ruleCompareOperation ) ) )* ) )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:199:1: (this_CompareOperation_0= ruleCompareOperation ( ( () ( ( (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator ) ) ) ( (lv_subExpressions_3_0= ruleCompareOperation ) ) )* | ( () ( ( (lv_operator_5_1= ruleAndOperator | lv_operator_5_2= ruleLogicalAndOperator ) ) ) ( (lv_subExpressions_6_0= ruleCompareOperation ) ) )* ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:200:2: this_CompareOperation_0= ruleCompareOperation ( ( () ( ( (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator ) ) ) ( (lv_subExpressions_3_0= ruleCompareOperation ) ) )* | ( () ( ( (lv_operator_5_1= ruleAndOperator | lv_operator_5_2= ruleLogicalAndOperator ) ) ) ( (lv_subExpressions_6_0= ruleCompareOperation ) ) )* )
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getOrAndExpressionAccess().getCompareOperationParserRuleCall_0()); 
                  
            }
            pushFollow(FollowSets000.FOLLOW_ruleCompareOperation_in_ruleOrAndExpression452);
            this_CompareOperation_0=ruleCompareOperation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_CompareOperation_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:211:1: ( ( () ( ( (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator ) ) ) ( (lv_subExpressions_3_0= ruleCompareOperation ) ) )* | ( () ( ( (lv_operator_5_1= ruleAndOperator | lv_operator_5_2= ruleLogicalAndOperator ) ) ) ( (lv_subExpressions_6_0= ruleCompareOperation ) ) )* )
            int alt6=2;
            switch ( input.LA(1) ) {
            case 29:
            case 38:
                {
                alt6=1;
                }
                break;
            case EOF:
                {
                int LA6_2 = input.LA(2);

                if ( (synpred4_InternalKExpressions()) ) {
                    alt6=1;
                }
                else if ( (true) ) {
                    alt6=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 2, input);

                    throw nvae;
                }
                }
                break;
            case 17:
                {
                int LA6_3 = input.LA(2);

                if ( (synpred4_InternalKExpressions()) ) {
                    alt6=1;
                }
                else if ( (true) ) {
                    alt6=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 3, input);

                    throw nvae;
                }
                }
                break;
            case 30:
            case 39:
                {
                alt6=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:211:2: ( () ( ( (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator ) ) ) ( (lv_subExpressions_3_0= ruleCompareOperation ) ) )*
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:211:2: ( () ( ( (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator ) ) ) ( (lv_subExpressions_3_0= ruleCompareOperation ) ) )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==29||LA3_0==38) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:211:3: () ( ( (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator ) ) ) ( (lv_subExpressions_3_0= ruleCompareOperation ) )
                    	    {
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:211:3: ()
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:212:2: 
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	  /* */ 
                    	      	
                    	    }
                    	    if ( state.backtracking==0 ) {

                    	              current = forceCreateModelElementAndAdd(
                    	                  grammarAccess.getOrAndExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0_0(),
                    	                  current);
                    	          
                    	    }

                    	    }

                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:220:2: ( ( (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator ) ) )
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:221:1: ( (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator ) )
                    	    {
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:221:1: ( (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator ) )
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:222:1: (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator )
                    	    {
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:222:1: (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator )
                    	    int alt2=2;
                    	    int LA2_0 = input.LA(1);

                    	    if ( (LA2_0==29) ) {
                    	        alt2=1;
                    	    }
                    	    else if ( (LA2_0==38) ) {
                    	        alt2=2;
                    	    }
                    	    else {
                    	        if (state.backtracking>0) {state.failed=true; return current;}
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 2, 0, input);

                    	        throw nvae;
                    	    }
                    	    switch (alt2) {
                    	        case 1 :
                    	            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:223:3: lv_operator_2_1= ruleOrOperator
                    	            {
                    	            if ( state.backtracking==0 ) {
                    	               
                    	              	        newCompositeNode(grammarAccess.getOrAndExpressionAccess().getOperatorOrOperatorEnumRuleCall_1_0_1_0_0()); 
                    	              	    
                    	            }
                    	            pushFollow(FollowSets000.FOLLOW_ruleOrOperator_in_ruleOrAndExpression488);
                    	            lv_operator_2_1=ruleOrOperator();

                    	            state._fsp--;
                    	            if (state.failed) return current;
                    	            if ( state.backtracking==0 ) {

                    	              	        if (current==null) {
                    	              	            current = createModelElementForParent(grammarAccess.getOrAndExpressionRule());
                    	              	        }
                    	                     		set(
                    	                     			current, 
                    	                     			"operator",
                    	                      		lv_operator_2_1, 
                    	                      		"OrOperator");
                    	              	        afterParserOrEnumRuleCall();
                    	              	    
                    	            }

                    	            }
                    	            break;
                    	        case 2 :
                    	            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:238:8: lv_operator_2_2= ruleLogicalOrOperator
                    	            {
                    	            if ( state.backtracking==0 ) {
                    	               
                    	              	        newCompositeNode(grammarAccess.getOrAndExpressionAccess().getOperatorLogicalOrOperatorEnumRuleCall_1_0_1_0_1()); 
                    	              	    
                    	            }
                    	            pushFollow(FollowSets000.FOLLOW_ruleLogicalOrOperator_in_ruleOrAndExpression507);
                    	            lv_operator_2_2=ruleLogicalOrOperator();

                    	            state._fsp--;
                    	            if (state.failed) return current;
                    	            if ( state.backtracking==0 ) {

                    	              	        if (current==null) {
                    	              	            current = createModelElementForParent(grammarAccess.getOrAndExpressionRule());
                    	              	        }
                    	                     		set(
                    	                     			current, 
                    	                     			"operator",
                    	                      		lv_operator_2_2, 
                    	                      		"LogicalOrOperator");
                    	              	        afterParserOrEnumRuleCall();
                    	              	    
                    	            }

                    	            }
                    	            break;

                    	    }


                    	    }


                    	    }

                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:256:2: ( (lv_subExpressions_3_0= ruleCompareOperation ) )
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:257:1: (lv_subExpressions_3_0= ruleCompareOperation )
                    	    {
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:257:1: (lv_subExpressions_3_0= ruleCompareOperation )
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:258:3: lv_subExpressions_3_0= ruleCompareOperation
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getOrAndExpressionAccess().getSubExpressionsCompareOperationParserRuleCall_1_0_2_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FollowSets000.FOLLOW_ruleCompareOperation_in_ruleOrAndExpression531);
                    	    lv_subExpressions_3_0=ruleCompareOperation();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getOrAndExpressionRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"subExpressions",
                    	              		lv_subExpressions_3_0, 
                    	              		"CompareOperation");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:275:6: ( () ( ( (lv_operator_5_1= ruleAndOperator | lv_operator_5_2= ruleLogicalAndOperator ) ) ) ( (lv_subExpressions_6_0= ruleCompareOperation ) ) )*
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:275:6: ( () ( ( (lv_operator_5_1= ruleAndOperator | lv_operator_5_2= ruleLogicalAndOperator ) ) ) ( (lv_subExpressions_6_0= ruleCompareOperation ) ) )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==30||LA5_0==39) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:275:7: () ( ( (lv_operator_5_1= ruleAndOperator | lv_operator_5_2= ruleLogicalAndOperator ) ) ) ( (lv_subExpressions_6_0= ruleCompareOperation ) )
                    	    {
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:275:7: ()
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:276:2: 
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	  /* */ 
                    	      	
                    	    }
                    	    if ( state.backtracking==0 ) {

                    	              current = forceCreateModelElementAndAdd(
                    	                  grammarAccess.getOrAndExpressionAccess().getOperatorExpressionSubExpressionsAction_1_1_0(),
                    	                  current);
                    	          
                    	    }

                    	    }

                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:284:2: ( ( (lv_operator_5_1= ruleAndOperator | lv_operator_5_2= ruleLogicalAndOperator ) ) )
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:285:1: ( (lv_operator_5_1= ruleAndOperator | lv_operator_5_2= ruleLogicalAndOperator ) )
                    	    {
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:285:1: ( (lv_operator_5_1= ruleAndOperator | lv_operator_5_2= ruleLogicalAndOperator ) )
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:286:1: (lv_operator_5_1= ruleAndOperator | lv_operator_5_2= ruleLogicalAndOperator )
                    	    {
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:286:1: (lv_operator_5_1= ruleAndOperator | lv_operator_5_2= ruleLogicalAndOperator )
                    	    int alt4=2;
                    	    int LA4_0 = input.LA(1);

                    	    if ( (LA4_0==30) ) {
                    	        alt4=1;
                    	    }
                    	    else if ( (LA4_0==39) ) {
                    	        alt4=2;
                    	    }
                    	    else {
                    	        if (state.backtracking>0) {state.failed=true; return current;}
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 4, 0, input);

                    	        throw nvae;
                    	    }
                    	    switch (alt4) {
                    	        case 1 :
                    	            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:287:3: lv_operator_5_1= ruleAndOperator
                    	            {
                    	            if ( state.backtracking==0 ) {
                    	               
                    	              	        newCompositeNode(grammarAccess.getOrAndExpressionAccess().getOperatorAndOperatorEnumRuleCall_1_1_1_0_0()); 
                    	              	    
                    	            }
                    	            pushFollow(FollowSets000.FOLLOW_ruleAndOperator_in_ruleOrAndExpression575);
                    	            lv_operator_5_1=ruleAndOperator();

                    	            state._fsp--;
                    	            if (state.failed) return current;
                    	            if ( state.backtracking==0 ) {

                    	              	        if (current==null) {
                    	              	            current = createModelElementForParent(grammarAccess.getOrAndExpressionRule());
                    	              	        }
                    	                     		set(
                    	                     			current, 
                    	                     			"operator",
                    	                      		lv_operator_5_1, 
                    	                      		"AndOperator");
                    	              	        afterParserOrEnumRuleCall();
                    	              	    
                    	            }

                    	            }
                    	            break;
                    	        case 2 :
                    	            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:302:8: lv_operator_5_2= ruleLogicalAndOperator
                    	            {
                    	            if ( state.backtracking==0 ) {
                    	               
                    	              	        newCompositeNode(grammarAccess.getOrAndExpressionAccess().getOperatorLogicalAndOperatorEnumRuleCall_1_1_1_0_1()); 
                    	              	    
                    	            }
                    	            pushFollow(FollowSets000.FOLLOW_ruleLogicalAndOperator_in_ruleOrAndExpression594);
                    	            lv_operator_5_2=ruleLogicalAndOperator();

                    	            state._fsp--;
                    	            if (state.failed) return current;
                    	            if ( state.backtracking==0 ) {

                    	              	        if (current==null) {
                    	              	            current = createModelElementForParent(grammarAccess.getOrAndExpressionRule());
                    	              	        }
                    	                     		set(
                    	                     			current, 
                    	                     			"operator",
                    	                      		lv_operator_5_2, 
                    	                      		"LogicalAndOperator");
                    	              	        afterParserOrEnumRuleCall();
                    	              	    
                    	            }

                    	            }
                    	            break;

                    	    }


                    	    }


                    	    }

                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:320:2: ( (lv_subExpressions_6_0= ruleCompareOperation ) )
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:321:1: (lv_subExpressions_6_0= ruleCompareOperation )
                    	    {
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:321:1: (lv_subExpressions_6_0= ruleCompareOperation )
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:322:3: lv_subExpressions_6_0= ruleCompareOperation
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getOrAndExpressionAccess().getSubExpressionsCompareOperationParserRuleCall_1_1_2_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FollowSets000.FOLLOW_ruleCompareOperation_in_ruleOrAndExpression618);
                    	    lv_subExpressions_6_0=ruleCompareOperation();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getOrAndExpressionRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"subExpressions",
                    	              		lv_subExpressions_6_0, 
                    	              		"CompareOperation");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleOrAndExpression"


    // $ANTLR start "entryRuleCompareOperation"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:346:1: entryRuleCompareOperation returns [EObject current=null] : iv_ruleCompareOperation= ruleCompareOperation EOF ;
    public final EObject entryRuleCompareOperation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCompareOperation = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:347:2: (iv_ruleCompareOperation= ruleCompareOperation EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:348:2: iv_ruleCompareOperation= ruleCompareOperation EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCompareOperationRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleCompareOperation_in_entryRuleCompareOperation657);
            iv_ruleCompareOperation=ruleCompareOperation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleCompareOperation; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleCompareOperation667); if (state.failed) return current;

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
    // $ANTLR end "entryRuleCompareOperation"


    // $ANTLR start "ruleCompareOperation"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:355:1: ruleCompareOperation returns [EObject current=null] : ( (this_NotOrValuedExpression_0= ruleNotOrValuedExpression ( () ( (lv_operator_2_0= ruleCompareOperator ) ) ( (lv_subExpressions_3_0= ruleNotOrValuedExpression ) ) ) ) | this_NotExpression_4= ruleNotExpression ) ;
    public final EObject ruleCompareOperation() throws RecognitionException {
        EObject current = null;

        EObject this_NotOrValuedExpression_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_subExpressions_3_0 = null;

        EObject this_NotExpression_4 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:358:28: ( ( (this_NotOrValuedExpression_0= ruleNotOrValuedExpression ( () ( (lv_operator_2_0= ruleCompareOperator ) ) ( (lv_subExpressions_3_0= ruleNotOrValuedExpression ) ) ) ) | this_NotExpression_4= ruleNotExpression ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:359:1: ( (this_NotOrValuedExpression_0= ruleNotOrValuedExpression ( () ( (lv_operator_2_0= ruleCompareOperator ) ) ( (lv_subExpressions_3_0= ruleNotOrValuedExpression ) ) ) ) | this_NotExpression_4= ruleNotExpression )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:359:1: ( (this_NotOrValuedExpression_0= ruleNotOrValuedExpression ( () ( (lv_operator_2_0= ruleCompareOperator ) ) ( (lv_subExpressions_3_0= ruleNotOrValuedExpression ) ) ) ) | this_NotExpression_4= ruleNotExpression )
            int alt7=2;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:359:2: (this_NotOrValuedExpression_0= ruleNotOrValuedExpression ( () ( (lv_operator_2_0= ruleCompareOperator ) ) ( (lv_subExpressions_3_0= ruleNotOrValuedExpression ) ) ) )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:359:2: (this_NotOrValuedExpression_0= ruleNotOrValuedExpression ( () ( (lv_operator_2_0= ruleCompareOperator ) ) ( (lv_subExpressions_3_0= ruleNotOrValuedExpression ) ) ) )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:360:2: this_NotOrValuedExpression_0= ruleNotOrValuedExpression ( () ( (lv_operator_2_0= ruleCompareOperator ) ) ( (lv_subExpressions_3_0= ruleNotOrValuedExpression ) ) )
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getCompareOperationAccess().getNotOrValuedExpressionParserRuleCall_0_0()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleNotOrValuedExpression_in_ruleCompareOperation718);
                    this_NotOrValuedExpression_0=ruleNotOrValuedExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_NotOrValuedExpression_0; 
                              afterParserOrEnumRuleCall();
                          
                    }
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:371:1: ( () ( (lv_operator_2_0= ruleCompareOperator ) ) ( (lv_subExpressions_3_0= ruleNotOrValuedExpression ) ) )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:371:2: () ( (lv_operator_2_0= ruleCompareOperator ) ) ( (lv_subExpressions_3_0= ruleNotOrValuedExpression ) )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:371:2: ()
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:372:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getCompareOperationAccess().getOperatorExpressionSubExpressionsAction_0_1_0(),
                                  current);
                          
                    }

                    }

                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:380:2: ( (lv_operator_2_0= ruleCompareOperator ) )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:381:1: (lv_operator_2_0= ruleCompareOperator )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:381:1: (lv_operator_2_0= ruleCompareOperator )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:382:3: lv_operator_2_0= ruleCompareOperator
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getCompareOperationAccess().getOperatorCompareOperatorEnumRuleCall_0_1_1_0()); 
                      	    
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleCompareOperator_in_ruleCompareOperation751);
                    lv_operator_2_0=ruleCompareOperator();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getCompareOperationRule());
                      	        }
                             		set(
                             			current, 
                             			"operator",
                              		lv_operator_2_0, 
                              		"CompareOperator");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }

                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:398:2: ( (lv_subExpressions_3_0= ruleNotOrValuedExpression ) )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:399:1: (lv_subExpressions_3_0= ruleNotOrValuedExpression )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:399:1: (lv_subExpressions_3_0= ruleNotOrValuedExpression )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:400:3: lv_subExpressions_3_0= ruleNotOrValuedExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getCompareOperationAccess().getSubExpressionsNotOrValuedExpressionParserRuleCall_0_1_2_0()); 
                      	    
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleNotOrValuedExpression_in_ruleCompareOperation772);
                    lv_subExpressions_3_0=ruleNotOrValuedExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getCompareOperationRule());
                      	        }
                             		add(
                             			current, 
                             			"subExpressions",
                              		lv_subExpressions_3_0, 
                              		"NotOrValuedExpression");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:418:2: this_NotExpression_4= ruleNotExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getCompareOperationAccess().getNotExpressionParserRuleCall_1()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleNotExpression_in_ruleCompareOperation805);
                    this_NotExpression_4=ruleNotExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_NotExpression_4; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleCompareOperation"


    // $ANTLR start "entryRuleNotOrValuedExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:437:1: entryRuleNotOrValuedExpression returns [EObject current=null] : iv_ruleNotOrValuedExpression= ruleNotOrValuedExpression EOF ;
    public final EObject entryRuleNotOrValuedExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNotOrValuedExpression = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:438:2: (iv_ruleNotOrValuedExpression= ruleNotOrValuedExpression EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:439:2: iv_ruleNotOrValuedExpression= ruleNotOrValuedExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNotOrValuedExpressionRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleNotOrValuedExpression_in_entryRuleNotOrValuedExpression840);
            iv_ruleNotOrValuedExpression=ruleNotOrValuedExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleNotOrValuedExpression; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleNotOrValuedExpression850); if (state.failed) return current;

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
    // $ANTLR end "entryRuleNotOrValuedExpression"


    // $ANTLR start "ruleNotOrValuedExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:446:1: ruleNotOrValuedExpression returns [EObject current=null] : (this_ValuedExpression_0= ruleValuedExpression | this_NotExpression_1= ruleNotExpression ) ;
    public final EObject ruleNotOrValuedExpression() throws RecognitionException {
        EObject current = null;

        EObject this_ValuedExpression_0 = null;

        EObject this_NotExpression_1 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:449:28: ( (this_ValuedExpression_0= ruleValuedExpression | this_NotExpression_1= ruleNotExpression ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:450:1: (this_ValuedExpression_0= ruleValuedExpression | this_NotExpression_1= ruleNotExpression )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:450:1: (this_ValuedExpression_0= ruleValuedExpression | this_NotExpression_1= ruleNotExpression )
            int alt8=2;
            alt8 = dfa8.predict(input);
            switch (alt8) {
                case 1 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:451:2: this_ValuedExpression_0= ruleValuedExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getNotOrValuedExpressionAccess().getValuedExpressionParserRuleCall_0()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleValuedExpression_in_ruleNotOrValuedExpression900);
                    this_ValuedExpression_0=ruleValuedExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_ValuedExpression_0; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:464:2: this_NotExpression_1= ruleNotExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getNotOrValuedExpressionAccess().getNotExpressionParserRuleCall_1()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleNotExpression_in_ruleNotOrValuedExpression930);
                    this_NotExpression_1=ruleNotExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_NotExpression_1; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleNotOrValuedExpression"


    // $ANTLR start "entryRuleNotExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:483:1: entryRuleNotExpression returns [EObject current=null] : iv_ruleNotExpression= ruleNotExpression EOF ;
    public final EObject entryRuleNotExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNotExpression = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:484:2: (iv_ruleNotExpression= ruleNotExpression EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:485:2: iv_ruleNotExpression= ruleNotExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNotExpressionRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleNotExpression_in_entryRuleNotExpression965);
            iv_ruleNotExpression=ruleNotExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleNotExpression; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleNotExpression975); if (state.failed) return current;

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
    // $ANTLR end "entryRuleNotExpression"


    // $ANTLR start "ruleNotExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:492:1: ruleNotExpression returns [EObject current=null] : ( ( () ( (lv_operator_1_0= ruleNotOperator ) ) ( (lv_subExpressions_2_0= ruleNotExpression ) ) ) | this_AtomicExpression_3= ruleAtomicExpression ) ;
    public final EObject ruleNotExpression() throws RecognitionException {
        EObject current = null;

        Enumerator lv_operator_1_0 = null;

        EObject lv_subExpressions_2_0 = null;

        EObject this_AtomicExpression_3 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:495:28: ( ( ( () ( (lv_operator_1_0= ruleNotOperator ) ) ( (lv_subExpressions_2_0= ruleNotExpression ) ) ) | this_AtomicExpression_3= ruleAtomicExpression ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:496:1: ( ( () ( (lv_operator_1_0= ruleNotOperator ) ) ( (lv_subExpressions_2_0= ruleNotExpression ) ) ) | this_AtomicExpression_3= ruleAtomicExpression )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:496:1: ( ( () ( (lv_operator_1_0= ruleNotOperator ) ) ( (lv_subExpressions_2_0= ruleNotExpression ) ) ) | this_AtomicExpression_3= ruleAtomicExpression )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==31) ) {
                alt9=1;
            }
            else if ( ((LA9_0>=RULE_ID && LA9_0<=RULE_HOSTCODE)||LA9_0==RULE_BOOLEAN||LA9_0==16||LA9_0==28||LA9_0==37) ) {
                alt9=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:496:2: ( () ( (lv_operator_1_0= ruleNotOperator ) ) ( (lv_subExpressions_2_0= ruleNotExpression ) ) )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:496:2: ( () ( (lv_operator_1_0= ruleNotOperator ) ) ( (lv_subExpressions_2_0= ruleNotExpression ) ) )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:496:3: () ( (lv_operator_1_0= ruleNotOperator ) ) ( (lv_subExpressions_2_0= ruleNotExpression ) )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:496:3: ()
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:497:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElement(
                                  grammarAccess.getNotExpressionAccess().getOperatorExpressionAction_0_0(),
                                  current);
                          
                    }

                    }

                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:505:2: ( (lv_operator_1_0= ruleNotOperator ) )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:506:1: (lv_operator_1_0= ruleNotOperator )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:506:1: (lv_operator_1_0= ruleNotOperator )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:507:3: lv_operator_1_0= ruleNotOperator
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getNotExpressionAccess().getOperatorNotOperatorEnumRuleCall_0_1_0()); 
                      	    
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleNotOperator_in_ruleNotExpression1034);
                    lv_operator_1_0=ruleNotOperator();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getNotExpressionRule());
                      	        }
                             		set(
                             			current, 
                             			"operator",
                              		lv_operator_1_0, 
                              		"NotOperator");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }

                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:523:2: ( (lv_subExpressions_2_0= ruleNotExpression ) )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:524:1: (lv_subExpressions_2_0= ruleNotExpression )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:524:1: (lv_subExpressions_2_0= ruleNotExpression )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:525:3: lv_subExpressions_2_0= ruleNotExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getNotExpressionAccess().getSubExpressionsNotExpressionParserRuleCall_0_2_0()); 
                      	    
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleNotExpression_in_ruleNotExpression1055);
                    lv_subExpressions_2_0=ruleNotExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getNotExpressionRule());
                      	        }
                             		add(
                             			current, 
                             			"subExpressions",
                              		lv_subExpressions_2_0, 
                              		"NotExpression");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:543:2: this_AtomicExpression_3= ruleAtomicExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getNotExpressionAccess().getAtomicExpressionParserRuleCall_1()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleAtomicExpression_in_ruleNotExpression1087);
                    this_AtomicExpression_3=ruleAtomicExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_AtomicExpression_3; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleNotExpression"


    // $ANTLR start "entryRuleValuedExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:562:1: entryRuleValuedExpression returns [EObject current=null] : iv_ruleValuedExpression= ruleValuedExpression EOF ;
    public final EObject entryRuleValuedExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValuedExpression = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:563:2: (iv_ruleValuedExpression= ruleValuedExpression EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:564:2: iv_ruleValuedExpression= ruleValuedExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getValuedExpressionRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleValuedExpression_in_entryRuleValuedExpression1122);
            iv_ruleValuedExpression=ruleValuedExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleValuedExpression; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleValuedExpression1132); if (state.failed) return current;

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
    // $ANTLR end "entryRuleValuedExpression"


    // $ANTLR start "ruleValuedExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:571:1: ruleValuedExpression returns [EObject current=null] : this_AddExpression_0= ruleAddExpression ;
    public final EObject ruleValuedExpression() throws RecognitionException {
        EObject current = null;

        EObject this_AddExpression_0 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:574:28: (this_AddExpression_0= ruleAddExpression )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:576:2: this_AddExpression_0= ruleAddExpression
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getValuedExpressionAccess().getAddExpressionParserRuleCall()); 
                  
            }
            pushFollow(FollowSets000.FOLLOW_ruleAddExpression_in_ruleValuedExpression1181);
            this_AddExpression_0=ruleAddExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_AddExpression_0; 
                      afterParserOrEnumRuleCall();
                  
            }

            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleValuedExpression"


    // $ANTLR start "entryRuleAddExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:595:1: entryRuleAddExpression returns [EObject current=null] : iv_ruleAddExpression= ruleAddExpression EOF ;
    public final EObject entryRuleAddExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAddExpression = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:596:2: (iv_ruleAddExpression= ruleAddExpression EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:597:2: iv_ruleAddExpression= ruleAddExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAddExpressionRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleAddExpression_in_entryRuleAddExpression1215);
            iv_ruleAddExpression=ruleAddExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAddExpression; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleAddExpression1225); if (state.failed) return current;

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
    // $ANTLR end "entryRuleAddExpression"


    // $ANTLR start "ruleAddExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:604:1: ruleAddExpression returns [EObject current=null] : (this_SubExpression_0= ruleSubExpression ( () ( (lv_operator_2_0= ruleAddOperator ) ) ( (lv_subExpressions_3_0= ruleSubExpression ) ) )* ) ;
    public final EObject ruleAddExpression() throws RecognitionException {
        EObject current = null;

        EObject this_SubExpression_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_subExpressions_3_0 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:607:28: ( (this_SubExpression_0= ruleSubExpression ( () ( (lv_operator_2_0= ruleAddOperator ) ) ( (lv_subExpressions_3_0= ruleSubExpression ) ) )* ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:608:1: (this_SubExpression_0= ruleSubExpression ( () ( (lv_operator_2_0= ruleAddOperator ) ) ( (lv_subExpressions_3_0= ruleSubExpression ) ) )* )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:608:1: (this_SubExpression_0= ruleSubExpression ( () ( (lv_operator_2_0= ruleAddOperator ) ) ( (lv_subExpressions_3_0= ruleSubExpression ) ) )* )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:609:2: this_SubExpression_0= ruleSubExpression ( () ( (lv_operator_2_0= ruleAddOperator ) ) ( (lv_subExpressions_3_0= ruleSubExpression ) ) )*
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getAddExpressionAccess().getSubExpressionParserRuleCall_0()); 
                  
            }
            pushFollow(FollowSets000.FOLLOW_ruleSubExpression_in_ruleAddExpression1275);
            this_SubExpression_0=ruleSubExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_SubExpression_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:620:1: ( () ( (lv_operator_2_0= ruleAddOperator ) ) ( (lv_subExpressions_3_0= ruleSubExpression ) ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==32) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:620:2: () ( (lv_operator_2_0= ruleAddOperator ) ) ( (lv_subExpressions_3_0= ruleSubExpression ) )
            	    {
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:620:2: ()
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:621:2: 
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	  /* */ 
            	      	
            	    }
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndAdd(
            	                  grammarAccess.getAddExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:629:2: ( (lv_operator_2_0= ruleAddOperator ) )
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:630:1: (lv_operator_2_0= ruleAddOperator )
            	    {
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:630:1: (lv_operator_2_0= ruleAddOperator )
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:631:3: lv_operator_2_0= ruleAddOperator
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getAddExpressionAccess().getOperatorAddOperatorEnumRuleCall_1_1_0()); 
            	      	    
            	    }
            	    pushFollow(FollowSets000.FOLLOW_ruleAddOperator_in_ruleAddExpression1308);
            	    lv_operator_2_0=ruleAddOperator();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getAddExpressionRule());
            	      	        }
            	             		set(
            	             			current, 
            	             			"operator",
            	              		lv_operator_2_0, 
            	              		"AddOperator");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }

            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:647:2: ( (lv_subExpressions_3_0= ruleSubExpression ) )
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:648:1: (lv_subExpressions_3_0= ruleSubExpression )
            	    {
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:648:1: (lv_subExpressions_3_0= ruleSubExpression )
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:649:3: lv_subExpressions_3_0= ruleSubExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getAddExpressionAccess().getSubExpressionsSubExpressionParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FollowSets000.FOLLOW_ruleSubExpression_in_ruleAddExpression1329);
            	    lv_subExpressions_3_0=ruleSubExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getAddExpressionRule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"subExpressions",
            	              		lv_subExpressions_3_0, 
            	              		"SubExpression");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleAddExpression"


    // $ANTLR start "entryRuleSubExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:673:1: entryRuleSubExpression returns [EObject current=null] : iv_ruleSubExpression= ruleSubExpression EOF ;
    public final EObject entryRuleSubExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSubExpression = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:674:2: (iv_ruleSubExpression= ruleSubExpression EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:675:2: iv_ruleSubExpression= ruleSubExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSubExpressionRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleSubExpression_in_entryRuleSubExpression1367);
            iv_ruleSubExpression=ruleSubExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSubExpression; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleSubExpression1377); if (state.failed) return current;

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
    // $ANTLR end "entryRuleSubExpression"


    // $ANTLR start "ruleSubExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:682:1: ruleSubExpression returns [EObject current=null] : (this_MultExpression_0= ruleMultExpression ( () ( (lv_operator_2_0= ruleSubOperator ) ) ( (lv_subExpressions_3_0= ruleMultExpression ) ) )* ) ;
    public final EObject ruleSubExpression() throws RecognitionException {
        EObject current = null;

        EObject this_MultExpression_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_subExpressions_3_0 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:685:28: ( (this_MultExpression_0= ruleMultExpression ( () ( (lv_operator_2_0= ruleSubOperator ) ) ( (lv_subExpressions_3_0= ruleMultExpression ) ) )* ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:686:1: (this_MultExpression_0= ruleMultExpression ( () ( (lv_operator_2_0= ruleSubOperator ) ) ( (lv_subExpressions_3_0= ruleMultExpression ) ) )* )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:686:1: (this_MultExpression_0= ruleMultExpression ( () ( (lv_operator_2_0= ruleSubOperator ) ) ( (lv_subExpressions_3_0= ruleMultExpression ) ) )* )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:687:2: this_MultExpression_0= ruleMultExpression ( () ( (lv_operator_2_0= ruleSubOperator ) ) ( (lv_subExpressions_3_0= ruleMultExpression ) ) )*
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getSubExpressionAccess().getMultExpressionParserRuleCall_0()); 
                  
            }
            pushFollow(FollowSets000.FOLLOW_ruleMultExpression_in_ruleSubExpression1427);
            this_MultExpression_0=ruleMultExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_MultExpression_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:698:1: ( () ( (lv_operator_2_0= ruleSubOperator ) ) ( (lv_subExpressions_3_0= ruleMultExpression ) ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==33) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:698:2: () ( (lv_operator_2_0= ruleSubOperator ) ) ( (lv_subExpressions_3_0= ruleMultExpression ) )
            	    {
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:698:2: ()
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:699:2: 
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	  /* */ 
            	      	
            	    }
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndAdd(
            	                  grammarAccess.getSubExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:707:2: ( (lv_operator_2_0= ruleSubOperator ) )
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:708:1: (lv_operator_2_0= ruleSubOperator )
            	    {
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:708:1: (lv_operator_2_0= ruleSubOperator )
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:709:3: lv_operator_2_0= ruleSubOperator
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getSubExpressionAccess().getOperatorSubOperatorEnumRuleCall_1_1_0()); 
            	      	    
            	    }
            	    pushFollow(FollowSets000.FOLLOW_ruleSubOperator_in_ruleSubExpression1460);
            	    lv_operator_2_0=ruleSubOperator();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getSubExpressionRule());
            	      	        }
            	             		set(
            	             			current, 
            	             			"operator",
            	              		lv_operator_2_0, 
            	              		"SubOperator");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }

            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:725:2: ( (lv_subExpressions_3_0= ruleMultExpression ) )
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:726:1: (lv_subExpressions_3_0= ruleMultExpression )
            	    {
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:726:1: (lv_subExpressions_3_0= ruleMultExpression )
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:727:3: lv_subExpressions_3_0= ruleMultExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getSubExpressionAccess().getSubExpressionsMultExpressionParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FollowSets000.FOLLOW_ruleMultExpression_in_ruleSubExpression1481);
            	    lv_subExpressions_3_0=ruleMultExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getSubExpressionRule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"subExpressions",
            	              		lv_subExpressions_3_0, 
            	              		"MultExpression");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleSubExpression"


    // $ANTLR start "entryRuleMultExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:751:1: entryRuleMultExpression returns [EObject current=null] : iv_ruleMultExpression= ruleMultExpression EOF ;
    public final EObject entryRuleMultExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultExpression = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:752:2: (iv_ruleMultExpression= ruleMultExpression EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:753:2: iv_ruleMultExpression= ruleMultExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMultExpressionRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleMultExpression_in_entryRuleMultExpression1519);
            iv_ruleMultExpression=ruleMultExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMultExpression; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleMultExpression1529); if (state.failed) return current;

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
    // $ANTLR end "entryRuleMultExpression"


    // $ANTLR start "ruleMultExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:760:1: ruleMultExpression returns [EObject current=null] : (this_DivExpression_0= ruleDivExpression ( () ( (lv_operator_2_0= ruleMultOperator ) ) ( (lv_subExpressions_3_0= ruleDivExpression ) ) )* ) ;
    public final EObject ruleMultExpression() throws RecognitionException {
        EObject current = null;

        EObject this_DivExpression_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_subExpressions_3_0 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:763:28: ( (this_DivExpression_0= ruleDivExpression ( () ( (lv_operator_2_0= ruleMultOperator ) ) ( (lv_subExpressions_3_0= ruleDivExpression ) ) )* ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:764:1: (this_DivExpression_0= ruleDivExpression ( () ( (lv_operator_2_0= ruleMultOperator ) ) ( (lv_subExpressions_3_0= ruleDivExpression ) ) )* )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:764:1: (this_DivExpression_0= ruleDivExpression ( () ( (lv_operator_2_0= ruleMultOperator ) ) ( (lv_subExpressions_3_0= ruleDivExpression ) ) )* )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:765:2: this_DivExpression_0= ruleDivExpression ( () ( (lv_operator_2_0= ruleMultOperator ) ) ( (lv_subExpressions_3_0= ruleDivExpression ) ) )*
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getMultExpressionAccess().getDivExpressionParserRuleCall_0()); 
                  
            }
            pushFollow(FollowSets000.FOLLOW_ruleDivExpression_in_ruleMultExpression1579);
            this_DivExpression_0=ruleDivExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_DivExpression_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:776:1: ( () ( (lv_operator_2_0= ruleMultOperator ) ) ( (lv_subExpressions_3_0= ruleDivExpression ) ) )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==34) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:776:2: () ( (lv_operator_2_0= ruleMultOperator ) ) ( (lv_subExpressions_3_0= ruleDivExpression ) )
            	    {
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:776:2: ()
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:777:2: 
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	  /* */ 
            	      	
            	    }
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndAdd(
            	                  grammarAccess.getMultExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:785:2: ( (lv_operator_2_0= ruleMultOperator ) )
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:786:1: (lv_operator_2_0= ruleMultOperator )
            	    {
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:786:1: (lv_operator_2_0= ruleMultOperator )
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:787:3: lv_operator_2_0= ruleMultOperator
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getMultExpressionAccess().getOperatorMultOperatorEnumRuleCall_1_1_0()); 
            	      	    
            	    }
            	    pushFollow(FollowSets000.FOLLOW_ruleMultOperator_in_ruleMultExpression1612);
            	    lv_operator_2_0=ruleMultOperator();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getMultExpressionRule());
            	      	        }
            	             		set(
            	             			current, 
            	             			"operator",
            	              		lv_operator_2_0, 
            	              		"MultOperator");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }

            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:803:2: ( (lv_subExpressions_3_0= ruleDivExpression ) )
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:804:1: (lv_subExpressions_3_0= ruleDivExpression )
            	    {
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:804:1: (lv_subExpressions_3_0= ruleDivExpression )
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:805:3: lv_subExpressions_3_0= ruleDivExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getMultExpressionAccess().getSubExpressionsDivExpressionParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FollowSets000.FOLLOW_ruleDivExpression_in_ruleMultExpression1633);
            	    lv_subExpressions_3_0=ruleDivExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getMultExpressionRule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"subExpressions",
            	              		lv_subExpressions_3_0, 
            	              		"DivExpression");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleMultExpression"


    // $ANTLR start "entryRuleDivExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:829:1: entryRuleDivExpression returns [EObject current=null] : iv_ruleDivExpression= ruleDivExpression EOF ;
    public final EObject entryRuleDivExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDivExpression = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:830:2: (iv_ruleDivExpression= ruleDivExpression EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:831:2: iv_ruleDivExpression= ruleDivExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDivExpressionRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleDivExpression_in_entryRuleDivExpression1671);
            iv_ruleDivExpression=ruleDivExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDivExpression; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleDivExpression1681); if (state.failed) return current;

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
    // $ANTLR end "entryRuleDivExpression"


    // $ANTLR start "ruleDivExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:838:1: ruleDivExpression returns [EObject current=null] : (this_ModExpression_0= ruleModExpression ( () ( (lv_operator_2_0= ruleDivOperator ) ) ( (lv_subExpressions_3_0= ruleModExpression ) ) )? ) ;
    public final EObject ruleDivExpression() throws RecognitionException {
        EObject current = null;

        EObject this_ModExpression_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_subExpressions_3_0 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:841:28: ( (this_ModExpression_0= ruleModExpression ( () ( (lv_operator_2_0= ruleDivOperator ) ) ( (lv_subExpressions_3_0= ruleModExpression ) ) )? ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:842:1: (this_ModExpression_0= ruleModExpression ( () ( (lv_operator_2_0= ruleDivOperator ) ) ( (lv_subExpressions_3_0= ruleModExpression ) ) )? )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:842:1: (this_ModExpression_0= ruleModExpression ( () ( (lv_operator_2_0= ruleDivOperator ) ) ( (lv_subExpressions_3_0= ruleModExpression ) ) )? )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:843:2: this_ModExpression_0= ruleModExpression ( () ( (lv_operator_2_0= ruleDivOperator ) ) ( (lv_subExpressions_3_0= ruleModExpression ) ) )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getDivExpressionAccess().getModExpressionParserRuleCall_0()); 
                  
            }
            pushFollow(FollowSets000.FOLLOW_ruleModExpression_in_ruleDivExpression1731);
            this_ModExpression_0=ruleModExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_ModExpression_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:854:1: ( () ( (lv_operator_2_0= ruleDivOperator ) ) ( (lv_subExpressions_3_0= ruleModExpression ) ) )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==36) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:854:2: () ( (lv_operator_2_0= ruleDivOperator ) ) ( (lv_subExpressions_3_0= ruleModExpression ) )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:854:2: ()
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:855:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getDivExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0(),
                                  current);
                          
                    }

                    }

                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:863:2: ( (lv_operator_2_0= ruleDivOperator ) )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:864:1: (lv_operator_2_0= ruleDivOperator )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:864:1: (lv_operator_2_0= ruleDivOperator )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:865:3: lv_operator_2_0= ruleDivOperator
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getDivExpressionAccess().getOperatorDivOperatorEnumRuleCall_1_1_0()); 
                      	    
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleDivOperator_in_ruleDivExpression1764);
                    lv_operator_2_0=ruleDivOperator();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getDivExpressionRule());
                      	        }
                             		set(
                             			current, 
                             			"operator",
                              		lv_operator_2_0, 
                              		"DivOperator");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }

                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:881:2: ( (lv_subExpressions_3_0= ruleModExpression ) )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:882:1: (lv_subExpressions_3_0= ruleModExpression )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:882:1: (lv_subExpressions_3_0= ruleModExpression )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:883:3: lv_subExpressions_3_0= ruleModExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getDivExpressionAccess().getSubExpressionsModExpressionParserRuleCall_1_2_0()); 
                      	    
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleModExpression_in_ruleDivExpression1785);
                    lv_subExpressions_3_0=ruleModExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getDivExpressionRule());
                      	        }
                             		add(
                             			current, 
                             			"subExpressions",
                              		lv_subExpressions_3_0, 
                              		"ModExpression");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleDivExpression"


    // $ANTLR start "entryRuleModExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:907:1: entryRuleModExpression returns [EObject current=null] : iv_ruleModExpression= ruleModExpression EOF ;
    public final EObject entryRuleModExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModExpression = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:908:2: (iv_ruleModExpression= ruleModExpression EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:909:2: iv_ruleModExpression= ruleModExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getModExpressionRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleModExpression_in_entryRuleModExpression1823);
            iv_ruleModExpression=ruleModExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleModExpression; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleModExpression1833); if (state.failed) return current;

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
    // $ANTLR end "entryRuleModExpression"


    // $ANTLR start "ruleModExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:916:1: ruleModExpression returns [EObject current=null] : (this_NegExpression_0= ruleNegExpression ( () ( (lv_operator_2_0= ruleModOperator ) ) ( (lv_subExpressions_3_0= ruleAtomicValuedExpression ) ) )? ) ;
    public final EObject ruleModExpression() throws RecognitionException {
        EObject current = null;

        EObject this_NegExpression_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_subExpressions_3_0 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:919:28: ( (this_NegExpression_0= ruleNegExpression ( () ( (lv_operator_2_0= ruleModOperator ) ) ( (lv_subExpressions_3_0= ruleAtomicValuedExpression ) ) )? ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:920:1: (this_NegExpression_0= ruleNegExpression ( () ( (lv_operator_2_0= ruleModOperator ) ) ( (lv_subExpressions_3_0= ruleAtomicValuedExpression ) ) )? )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:920:1: (this_NegExpression_0= ruleNegExpression ( () ( (lv_operator_2_0= ruleModOperator ) ) ( (lv_subExpressions_3_0= ruleAtomicValuedExpression ) ) )? )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:921:2: this_NegExpression_0= ruleNegExpression ( () ( (lv_operator_2_0= ruleModOperator ) ) ( (lv_subExpressions_3_0= ruleAtomicValuedExpression ) ) )?
            {
            if ( state.backtracking==0 ) {
               
              	  /* */ 
              	
            }
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getModExpressionAccess().getNegExpressionParserRuleCall_0()); 
                  
            }
            pushFollow(FollowSets000.FOLLOW_ruleNegExpression_in_ruleModExpression1883);
            this_NegExpression_0=ruleNegExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_NegExpression_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:932:1: ( () ( (lv_operator_2_0= ruleModOperator ) ) ( (lv_subExpressions_3_0= ruleAtomicValuedExpression ) ) )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==35) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:932:2: () ( (lv_operator_2_0= ruleModOperator ) ) ( (lv_subExpressions_3_0= ruleAtomicValuedExpression ) )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:932:2: ()
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:933:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndAdd(
                                  grammarAccess.getModExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0(),
                                  current);
                          
                    }

                    }

                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:941:2: ( (lv_operator_2_0= ruleModOperator ) )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:942:1: (lv_operator_2_0= ruleModOperator )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:942:1: (lv_operator_2_0= ruleModOperator )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:943:3: lv_operator_2_0= ruleModOperator
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getModExpressionAccess().getOperatorModOperatorEnumRuleCall_1_1_0()); 
                      	    
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleModOperator_in_ruleModExpression1916);
                    lv_operator_2_0=ruleModOperator();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getModExpressionRule());
                      	        }
                             		set(
                             			current, 
                             			"operator",
                              		lv_operator_2_0, 
                              		"ModOperator");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }

                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:959:2: ( (lv_subExpressions_3_0= ruleAtomicValuedExpression ) )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:960:1: (lv_subExpressions_3_0= ruleAtomicValuedExpression )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:960:1: (lv_subExpressions_3_0= ruleAtomicValuedExpression )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:961:3: lv_subExpressions_3_0= ruleAtomicValuedExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getModExpressionAccess().getSubExpressionsAtomicValuedExpressionParserRuleCall_1_2_0()); 
                      	    
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleAtomicValuedExpression_in_ruleModExpression1937);
                    lv_subExpressions_3_0=ruleAtomicValuedExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getModExpressionRule());
                      	        }
                             		add(
                             			current, 
                             			"subExpressions",
                              		lv_subExpressions_3_0, 
                              		"AtomicValuedExpression");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleModExpression"


    // $ANTLR start "entryRuleNegExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:985:1: entryRuleNegExpression returns [EObject current=null] : iv_ruleNegExpression= ruleNegExpression EOF ;
    public final EObject entryRuleNegExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNegExpression = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:986:2: (iv_ruleNegExpression= ruleNegExpression EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:987:2: iv_ruleNegExpression= ruleNegExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNegExpressionRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleNegExpression_in_entryRuleNegExpression1975);
            iv_ruleNegExpression=ruleNegExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleNegExpression; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleNegExpression1985); if (state.failed) return current;

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
    // $ANTLR end "entryRuleNegExpression"


    // $ANTLR start "ruleNegExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:994:1: ruleNegExpression returns [EObject current=null] : ( ( () ( (lv_operator_1_0= ruleSubOperator ) ) ( (lv_subExpressions_2_0= ruleNegExpression ) ) ) | this_AtomicValuedExpression_3= ruleAtomicValuedExpression ) ;
    public final EObject ruleNegExpression() throws RecognitionException {
        EObject current = null;

        Enumerator lv_operator_1_0 = null;

        EObject lv_subExpressions_2_0 = null;

        EObject this_AtomicValuedExpression_3 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:997:28: ( ( ( () ( (lv_operator_1_0= ruleSubOperator ) ) ( (lv_subExpressions_2_0= ruleNegExpression ) ) ) | this_AtomicValuedExpression_3= ruleAtomicValuedExpression ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:998:1: ( ( () ( (lv_operator_1_0= ruleSubOperator ) ) ( (lv_subExpressions_2_0= ruleNegExpression ) ) ) | this_AtomicValuedExpression_3= ruleAtomicValuedExpression )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:998:1: ( ( () ( (lv_operator_1_0= ruleSubOperator ) ) ( (lv_subExpressions_2_0= ruleNegExpression ) ) ) | this_AtomicValuedExpression_3= ruleAtomicValuedExpression )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==33) ) {
                alt15=1;
            }
            else if ( ((LA15_0>=RULE_ID && LA15_0<=RULE_BOOLEAN)||LA15_0==16||LA15_0==28||LA15_0==37) ) {
                alt15=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:998:2: ( () ( (lv_operator_1_0= ruleSubOperator ) ) ( (lv_subExpressions_2_0= ruleNegExpression ) ) )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:998:2: ( () ( (lv_operator_1_0= ruleSubOperator ) ) ( (lv_subExpressions_2_0= ruleNegExpression ) ) )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:998:3: () ( (lv_operator_1_0= ruleSubOperator ) ) ( (lv_subExpressions_2_0= ruleNegExpression ) )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:998:3: ()
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:999:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElement(
                                  grammarAccess.getNegExpressionAccess().getOperatorExpressionAction_0_0(),
                                  current);
                          
                    }

                    }

                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1007:2: ( (lv_operator_1_0= ruleSubOperator ) )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1008:1: (lv_operator_1_0= ruleSubOperator )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1008:1: (lv_operator_1_0= ruleSubOperator )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1009:3: lv_operator_1_0= ruleSubOperator
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getNegExpressionAccess().getOperatorSubOperatorEnumRuleCall_0_1_0()); 
                      	    
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleSubOperator_in_ruleNegExpression2044);
                    lv_operator_1_0=ruleSubOperator();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getNegExpressionRule());
                      	        }
                             		set(
                             			current, 
                             			"operator",
                              		lv_operator_1_0, 
                              		"SubOperator");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }

                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1025:2: ( (lv_subExpressions_2_0= ruleNegExpression ) )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1026:1: (lv_subExpressions_2_0= ruleNegExpression )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1026:1: (lv_subExpressions_2_0= ruleNegExpression )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1027:3: lv_subExpressions_2_0= ruleNegExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getNegExpressionAccess().getSubExpressionsNegExpressionParserRuleCall_0_2_0()); 
                      	    
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleNegExpression_in_ruleNegExpression2065);
                    lv_subExpressions_2_0=ruleNegExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getNegExpressionRule());
                      	        }
                             		add(
                             			current, 
                             			"subExpressions",
                              		lv_subExpressions_2_0, 
                              		"NegExpression");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1045:2: this_AtomicValuedExpression_3= ruleAtomicValuedExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getNegExpressionAccess().getAtomicValuedExpressionParserRuleCall_1()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleAtomicValuedExpression_in_ruleNegExpression2097);
                    this_AtomicValuedExpression_3=ruleAtomicValuedExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_AtomicValuedExpression_3; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleNegExpression"


    // $ANTLR start "entryRuleAtomicExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1064:1: entryRuleAtomicExpression returns [EObject current=null] : iv_ruleAtomicExpression= ruleAtomicExpression EOF ;
    public final EObject entryRuleAtomicExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAtomicExpression = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1065:2: (iv_ruleAtomicExpression= ruleAtomicExpression EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1066:2: iv_ruleAtomicExpression= ruleAtomicExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAtomicExpressionRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleAtomicExpression_in_entryRuleAtomicExpression2132);
            iv_ruleAtomicExpression=ruleAtomicExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAtomicExpression; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleAtomicExpression2142); if (state.failed) return current;

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
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1073:1: ruleAtomicExpression returns [EObject current=null] : (this_BoolValue_0= ruleBoolValue | this_ValuedObjectTestExpression_1= ruleValuedObjectTestExpression | (otherlv_2= '(' this_BoolExpression_3= ruleBoolExpression otherlv_4= ')' ) | this_TextExpression_5= ruleTextExpression ) ;
    public final EObject ruleAtomicExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject this_BoolValue_0 = null;

        EObject this_ValuedObjectTestExpression_1 = null;

        EObject this_BoolExpression_3 = null;

        EObject this_TextExpression_5 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1076:28: ( (this_BoolValue_0= ruleBoolValue | this_ValuedObjectTestExpression_1= ruleValuedObjectTestExpression | (otherlv_2= '(' this_BoolExpression_3= ruleBoolExpression otherlv_4= ')' ) | this_TextExpression_5= ruleTextExpression ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1077:1: (this_BoolValue_0= ruleBoolValue | this_ValuedObjectTestExpression_1= ruleValuedObjectTestExpression | (otherlv_2= '(' this_BoolExpression_3= ruleBoolExpression otherlv_4= ')' ) | this_TextExpression_5= ruleTextExpression )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1077:1: (this_BoolValue_0= ruleBoolValue | this_ValuedObjectTestExpression_1= ruleValuedObjectTestExpression | (otherlv_2= '(' this_BoolExpression_3= ruleBoolExpression otherlv_4= ')' ) | this_TextExpression_5= ruleTextExpression )
            int alt16=4;
            switch ( input.LA(1) ) {
            case RULE_BOOLEAN:
                {
                alt16=1;
                }
                break;
            case RULE_ID:
            case 28:
            case 37:
                {
                alt16=2;
                }
                break;
            case 16:
                {
                alt16=3;
                }
                break;
            case RULE_HOSTCODE:
                {
                alt16=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }

            switch (alt16) {
                case 1 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1078:2: this_BoolValue_0= ruleBoolValue
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getAtomicExpressionAccess().getBoolValueParserRuleCall_0()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleBoolValue_in_ruleAtomicExpression2192);
                    this_BoolValue_0=ruleBoolValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_BoolValue_0; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1091:2: this_ValuedObjectTestExpression_1= ruleValuedObjectTestExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getAtomicExpressionAccess().getValuedObjectTestExpressionParserRuleCall_1()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleValuedObjectTestExpression_in_ruleAtomicExpression2222);
                    this_ValuedObjectTestExpression_1=ruleValuedObjectTestExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_ValuedObjectTestExpression_1; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 3 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1103:6: (otherlv_2= '(' this_BoolExpression_3= ruleBoolExpression otherlv_4= ')' )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1103:6: (otherlv_2= '(' this_BoolExpression_3= ruleBoolExpression otherlv_4= ')' )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1103:8: otherlv_2= '(' this_BoolExpression_3= ruleBoolExpression otherlv_4= ')'
                    {
                    otherlv_2=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleAtomicExpression2240); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_2, grammarAccess.getAtomicExpressionAccess().getLeftParenthesisKeyword_2_0());
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getAtomicExpressionAccess().getBoolExpressionParserRuleCall_2_1()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleBoolExpression_in_ruleAtomicExpression2265);
                    this_BoolExpression_3=ruleBoolExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_BoolExpression_3; 
                              afterParserOrEnumRuleCall();
                          
                    }
                    otherlv_4=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleAtomicExpression2276); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_4, grammarAccess.getAtomicExpressionAccess().getRightParenthesisKeyword_2_2());
                          
                    }

                    }


                    }
                    break;
                case 4 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1125:2: this_TextExpression_5= ruleTextExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getAtomicExpressionAccess().getTextExpressionParserRuleCall_3()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleTextExpression_in_ruleAtomicExpression2308);
                    this_TextExpression_5=ruleTextExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_TextExpression_5; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleAtomicExpression"


    // $ANTLR start "entryRuleAtomicValuedExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1144:1: entryRuleAtomicValuedExpression returns [EObject current=null] : iv_ruleAtomicValuedExpression= ruleAtomicValuedExpression EOF ;
    public final EObject entryRuleAtomicValuedExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAtomicValuedExpression = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1145:2: (iv_ruleAtomicValuedExpression= ruleAtomicValuedExpression EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1146:2: iv_ruleAtomicValuedExpression= ruleAtomicValuedExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAtomicValuedExpressionRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleAtomicValuedExpression_in_entryRuleAtomicValuedExpression2343);
            iv_ruleAtomicValuedExpression=ruleAtomicValuedExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAtomicValuedExpression; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleAtomicValuedExpression2353); if (state.failed) return current;

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
    // $ANTLR end "entryRuleAtomicValuedExpression"


    // $ANTLR start "ruleAtomicValuedExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1153:1: ruleAtomicValuedExpression returns [EObject current=null] : (this_IntValue_0= ruleIntValue | this_FloatValue_1= ruleFloatValue | this_DoubleValue_2= ruleDoubleValue | (otherlv_3= '(' this_ValuedExpression_4= ruleValuedExpression otherlv_5= ')' ) | this_AtomicExpression_6= ruleAtomicExpression ) ;
    public final EObject ruleAtomicValuedExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject this_IntValue_0 = null;

        EObject this_FloatValue_1 = null;

        EObject this_DoubleValue_2 = null;

        EObject this_ValuedExpression_4 = null;

        EObject this_AtomicExpression_6 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1156:28: ( (this_IntValue_0= ruleIntValue | this_FloatValue_1= ruleFloatValue | this_DoubleValue_2= ruleDoubleValue | (otherlv_3= '(' this_ValuedExpression_4= ruleValuedExpression otherlv_5= ')' ) | this_AtomicExpression_6= ruleAtomicExpression ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1157:1: (this_IntValue_0= ruleIntValue | this_FloatValue_1= ruleFloatValue | this_DoubleValue_2= ruleDoubleValue | (otherlv_3= '(' this_ValuedExpression_4= ruleValuedExpression otherlv_5= ')' ) | this_AtomicExpression_6= ruleAtomicExpression )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1157:1: (this_IntValue_0= ruleIntValue | this_FloatValue_1= ruleFloatValue | this_DoubleValue_2= ruleDoubleValue | (otherlv_3= '(' this_ValuedExpression_4= ruleValuedExpression otherlv_5= ')' ) | this_AtomicExpression_6= ruleAtomicExpression )
            int alt17=5;
            alt17 = dfa17.predict(input);
            switch (alt17) {
                case 1 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1158:2: this_IntValue_0= ruleIntValue
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getAtomicValuedExpressionAccess().getIntValueParserRuleCall_0()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleIntValue_in_ruleAtomicValuedExpression2403);
                    this_IntValue_0=ruleIntValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_IntValue_0; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1171:2: this_FloatValue_1= ruleFloatValue
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getAtomicValuedExpressionAccess().getFloatValueParserRuleCall_1()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleFloatValue_in_ruleAtomicValuedExpression2433);
                    this_FloatValue_1=ruleFloatValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_FloatValue_1; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 3 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1184:2: this_DoubleValue_2= ruleDoubleValue
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getAtomicValuedExpressionAccess().getDoubleValueParserRuleCall_2()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleDoubleValue_in_ruleAtomicValuedExpression2463);
                    this_DoubleValue_2=ruleDoubleValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_DoubleValue_2; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 4 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1196:6: (otherlv_3= '(' this_ValuedExpression_4= ruleValuedExpression otherlv_5= ')' )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1196:6: (otherlv_3= '(' this_ValuedExpression_4= ruleValuedExpression otherlv_5= ')' )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1196:8: otherlv_3= '(' this_ValuedExpression_4= ruleValuedExpression otherlv_5= ')'
                    {
                    otherlv_3=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleAtomicValuedExpression2481); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getAtomicValuedExpressionAccess().getLeftParenthesisKeyword_3_0());
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getAtomicValuedExpressionAccess().getValuedExpressionParserRuleCall_3_1()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleValuedExpression_in_ruleAtomicValuedExpression2506);
                    this_ValuedExpression_4=ruleValuedExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_ValuedExpression_4; 
                              afterParserOrEnumRuleCall();
                          
                    }
                    otherlv_5=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleAtomicValuedExpression2517); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_5, grammarAccess.getAtomicValuedExpressionAccess().getRightParenthesisKeyword_3_2());
                          
                    }

                    }


                    }
                    break;
                case 5 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1218:2: this_AtomicExpression_6= ruleAtomicExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getAtomicValuedExpressionAccess().getAtomicExpressionParserRuleCall_4()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleAtomicExpression_in_ruleAtomicValuedExpression2549);
                    this_AtomicExpression_6=ruleAtomicExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_AtomicExpression_6; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleAtomicValuedExpression"


    // $ANTLR start "entryRuleValuedObjectTestExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1237:1: entryRuleValuedObjectTestExpression returns [EObject current=null] : iv_ruleValuedObjectTestExpression= ruleValuedObjectTestExpression EOF ;
    public final EObject entryRuleValuedObjectTestExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValuedObjectTestExpression = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1238:2: (iv_ruleValuedObjectTestExpression= ruleValuedObjectTestExpression EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1239:2: iv_ruleValuedObjectTestExpression= ruleValuedObjectTestExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getValuedObjectTestExpressionRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleValuedObjectTestExpression_in_entryRuleValuedObjectTestExpression2584);
            iv_ruleValuedObjectTestExpression=ruleValuedObjectTestExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleValuedObjectTestExpression; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleValuedObjectTestExpression2594); if (state.failed) return current;

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
    // $ANTLR end "entryRuleValuedObjectTestExpression"


    // $ANTLR start "ruleValuedObjectTestExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1246:1: ruleValuedObjectTestExpression returns [EObject current=null] : ( ( () ( ( (lv_operator_1_1= rulePreOperator | lv_operator_1_2= ruleValOperator ) ) ) otherlv_2= '(' ( (lv_subExpressions_3_0= ruleValuedObjectTestExpression ) ) otherlv_4= ')' ) | this_ValuedObjectReference_5= ruleValuedObjectReference ) ;
    public final EObject ruleValuedObjectTestExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        Enumerator lv_operator_1_1 = null;

        Enumerator lv_operator_1_2 = null;

        EObject lv_subExpressions_3_0 = null;

        EObject this_ValuedObjectReference_5 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1249:28: ( ( ( () ( ( (lv_operator_1_1= rulePreOperator | lv_operator_1_2= ruleValOperator ) ) ) otherlv_2= '(' ( (lv_subExpressions_3_0= ruleValuedObjectTestExpression ) ) otherlv_4= ')' ) | this_ValuedObjectReference_5= ruleValuedObjectReference ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1250:1: ( ( () ( ( (lv_operator_1_1= rulePreOperator | lv_operator_1_2= ruleValOperator ) ) ) otherlv_2= '(' ( (lv_subExpressions_3_0= ruleValuedObjectTestExpression ) ) otherlv_4= ')' ) | this_ValuedObjectReference_5= ruleValuedObjectReference )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1250:1: ( ( () ( ( (lv_operator_1_1= rulePreOperator | lv_operator_1_2= ruleValOperator ) ) ) otherlv_2= '(' ( (lv_subExpressions_3_0= ruleValuedObjectTestExpression ) ) otherlv_4= ')' ) | this_ValuedObjectReference_5= ruleValuedObjectReference )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==28||LA19_0==37) ) {
                alt19=1;
            }
            else if ( (LA19_0==RULE_ID) ) {
                alt19=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1250:2: ( () ( ( (lv_operator_1_1= rulePreOperator | lv_operator_1_2= ruleValOperator ) ) ) otherlv_2= '(' ( (lv_subExpressions_3_0= ruleValuedObjectTestExpression ) ) otherlv_4= ')' )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1250:2: ( () ( ( (lv_operator_1_1= rulePreOperator | lv_operator_1_2= ruleValOperator ) ) ) otherlv_2= '(' ( (lv_subExpressions_3_0= ruleValuedObjectTestExpression ) ) otherlv_4= ')' )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1250:3: () ( ( (lv_operator_1_1= rulePreOperator | lv_operator_1_2= ruleValOperator ) ) ) otherlv_2= '(' ( (lv_subExpressions_3_0= ruleValuedObjectTestExpression ) ) otherlv_4= ')'
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1250:3: ()
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1251:2: 
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElement(
                                  grammarAccess.getValuedObjectTestExpressionAccess().getOperatorExpressionAction_0_0(),
                                  current);
                          
                    }

                    }

                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1259:2: ( ( (lv_operator_1_1= rulePreOperator | lv_operator_1_2= ruleValOperator ) ) )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1260:1: ( (lv_operator_1_1= rulePreOperator | lv_operator_1_2= ruleValOperator ) )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1260:1: ( (lv_operator_1_1= rulePreOperator | lv_operator_1_2= ruleValOperator ) )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1261:1: (lv_operator_1_1= rulePreOperator | lv_operator_1_2= ruleValOperator )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1261:1: (lv_operator_1_1= rulePreOperator | lv_operator_1_2= ruleValOperator )
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==28) ) {
                        alt18=1;
                    }
                    else if ( (LA18_0==37) ) {
                        alt18=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 18, 0, input);

                        throw nvae;
                    }
                    switch (alt18) {
                        case 1 :
                            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1262:3: lv_operator_1_1= rulePreOperator
                            {
                            if ( state.backtracking==0 ) {
                               
                              	        newCompositeNode(grammarAccess.getValuedObjectTestExpressionAccess().getOperatorPreOperatorEnumRuleCall_0_1_0_0()); 
                              	    
                            }
                            pushFollow(FollowSets000.FOLLOW_rulePreOperator_in_ruleValuedObjectTestExpression2655);
                            lv_operator_1_1=rulePreOperator();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              	        if (current==null) {
                              	            current = createModelElementForParent(grammarAccess.getValuedObjectTestExpressionRule());
                              	        }
                                     		set(
                                     			current, 
                                     			"operator",
                                      		lv_operator_1_1, 
                                      		"PreOperator");
                              	        afterParserOrEnumRuleCall();
                              	    
                            }

                            }
                            break;
                        case 2 :
                            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1277:8: lv_operator_1_2= ruleValOperator
                            {
                            if ( state.backtracking==0 ) {
                               
                              	        newCompositeNode(grammarAccess.getValuedObjectTestExpressionAccess().getOperatorValOperatorEnumRuleCall_0_1_0_1()); 
                              	    
                            }
                            pushFollow(FollowSets000.FOLLOW_ruleValOperator_in_ruleValuedObjectTestExpression2674);
                            lv_operator_1_2=ruleValOperator();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              	        if (current==null) {
                              	            current = createModelElementForParent(grammarAccess.getValuedObjectTestExpressionRule());
                              	        }
                                     		set(
                                     			current, 
                                     			"operator",
                                      		lv_operator_1_2, 
                                      		"ValOperator");
                              	        afterParserOrEnumRuleCall();
                              	    
                            }

                            }
                            break;

                    }


                    }


                    }

                    otherlv_2=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleValuedObjectTestExpression2689); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_2, grammarAccess.getValuedObjectTestExpressionAccess().getLeftParenthesisKeyword_0_2());
                          
                    }
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1299:1: ( (lv_subExpressions_3_0= ruleValuedObjectTestExpression ) )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1300:1: (lv_subExpressions_3_0= ruleValuedObjectTestExpression )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1300:1: (lv_subExpressions_3_0= ruleValuedObjectTestExpression )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1301:3: lv_subExpressions_3_0= ruleValuedObjectTestExpression
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getValuedObjectTestExpressionAccess().getSubExpressionsValuedObjectTestExpressionParserRuleCall_0_3_0()); 
                      	    
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleValuedObjectTestExpression_in_ruleValuedObjectTestExpression2710);
                    lv_subExpressions_3_0=ruleValuedObjectTestExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getValuedObjectTestExpressionRule());
                      	        }
                             		add(
                             			current, 
                             			"subExpressions",
                              		lv_subExpressions_3_0, 
                              		"ValuedObjectTestExpression");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }

                    otherlv_4=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleValuedObjectTestExpression2722); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_4, grammarAccess.getValuedObjectTestExpressionAccess().getRightParenthesisKeyword_0_4());
                          
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1323:2: this_ValuedObjectReference_5= ruleValuedObjectReference
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getValuedObjectTestExpressionAccess().getValuedObjectReferenceParserRuleCall_1()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleValuedObjectReference_in_ruleValuedObjectTestExpression2754);
                    this_ValuedObjectReference_5=ruleValuedObjectReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_ValuedObjectReference_5; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleValuedObjectTestExpression"


    // $ANTLR start "entryRuleValuedObjectReference"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1342:1: entryRuleValuedObjectReference returns [EObject current=null] : iv_ruleValuedObjectReference= ruleValuedObjectReference EOF ;
    public final EObject entryRuleValuedObjectReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValuedObjectReference = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1343:2: (iv_ruleValuedObjectReference= ruleValuedObjectReference EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1344:2: iv_ruleValuedObjectReference= ruleValuedObjectReference EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getValuedObjectReferenceRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleValuedObjectReference_in_entryRuleValuedObjectReference2789);
            iv_ruleValuedObjectReference=ruleValuedObjectReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleValuedObjectReference; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleValuedObjectReference2799); if (state.failed) return current;

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
    // $ANTLR end "entryRuleValuedObjectReference"


    // $ANTLR start "ruleValuedObjectReference"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1351:1: ruleValuedObjectReference returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject ruleValuedObjectReference() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;

         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1354:28: ( ( (otherlv_0= RULE_ID ) ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1355:1: ( (otherlv_0= RULE_ID ) )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1355:1: ( (otherlv_0= RULE_ID ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1356:1: (otherlv_0= RULE_ID )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1356:1: (otherlv_0= RULE_ID )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1357:3: otherlv_0= RULE_ID
            {
            if ( state.backtracking==0 ) {
               
              		  /* */ 
              		
            }
            if ( state.backtracking==0 ) {

              			if (current==null) {
              	            current = createModelElement(grammarAccess.getValuedObjectReferenceRule());
              	        }
                      
            }
            otherlv_0=(Token)match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_ruleValuedObjectReference2847); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		newLeafNode(otherlv_0, grammarAccess.getValuedObjectReferenceAccess().getValuedObjectValuedObjectCrossReference_0()); 
              	
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleValuedObjectReference"


    // $ANTLR start "entryRuleTextExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1379:1: entryRuleTextExpression returns [EObject current=null] : iv_ruleTextExpression= ruleTextExpression EOF ;
    public final EObject entryRuleTextExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTextExpression = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1380:2: (iv_ruleTextExpression= ruleTextExpression EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1381:2: iv_ruleTextExpression= ruleTextExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTextExpressionRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleTextExpression_in_entryRuleTextExpression2882);
            iv_ruleTextExpression=ruleTextExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTextExpression; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleTextExpression2892); if (state.failed) return current;

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
    // $ANTLR end "entryRuleTextExpression"


    // $ANTLR start "ruleTextExpression"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1388:1: ruleTextExpression returns [EObject current=null] : ( (lv_text_0_0= RULE_HOSTCODE ) ) ;
    public final EObject ruleTextExpression() throws RecognitionException {
        EObject current = null;

        Token lv_text_0_0=null;

         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1391:28: ( ( (lv_text_0_0= RULE_HOSTCODE ) ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1392:1: ( (lv_text_0_0= RULE_HOSTCODE ) )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1392:1: ( (lv_text_0_0= RULE_HOSTCODE ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1393:1: (lv_text_0_0= RULE_HOSTCODE )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1393:1: (lv_text_0_0= RULE_HOSTCODE )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1394:3: lv_text_0_0= RULE_HOSTCODE
            {
            lv_text_0_0=(Token)match(input,RULE_HOSTCODE,FollowSets000.FOLLOW_RULE_HOSTCODE_in_ruleTextExpression2933); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_text_0_0, grammarAccess.getTextExpressionAccess().getTextHOSTCODETerminalRuleCall_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getTextExpressionRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"text",
                      		lv_text_0_0, 
                      		"HOSTCODE");
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleTextExpression"


    // $ANTLR start "entryRuleIntValue"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1418:1: entryRuleIntValue returns [EObject current=null] : iv_ruleIntValue= ruleIntValue EOF ;
    public final EObject entryRuleIntValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIntValue = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1419:2: (iv_ruleIntValue= ruleIntValue EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1420:2: iv_ruleIntValue= ruleIntValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getIntValueRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleIntValue_in_entryRuleIntValue2973);
            iv_ruleIntValue=ruleIntValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleIntValue; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleIntValue2983); if (state.failed) return current;

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
    // $ANTLR end "entryRuleIntValue"


    // $ANTLR start "ruleIntValue"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1427:1: ruleIntValue returns [EObject current=null] : ( (lv_value_0_0= RULE_INT ) ) ;
    public final EObject ruleIntValue() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1430:28: ( ( (lv_value_0_0= RULE_INT ) ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1431:1: ( (lv_value_0_0= RULE_INT ) )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1431:1: ( (lv_value_0_0= RULE_INT ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1432:1: (lv_value_0_0= RULE_INT )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1432:1: (lv_value_0_0= RULE_INT )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1433:3: lv_value_0_0= RULE_INT
            {
            lv_value_0_0=(Token)match(input,RULE_INT,FollowSets000.FOLLOW_RULE_INT_in_ruleIntValue3024); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_value_0_0, grammarAccess.getIntValueAccess().getValueINTTerminalRuleCall_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getIntValueRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"value",
                      		lv_value_0_0, 
                      		"INT");
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleIntValue"


    // $ANTLR start "entryRuleFloatValue"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1457:1: entryRuleFloatValue returns [EObject current=null] : iv_ruleFloatValue= ruleFloatValue EOF ;
    public final EObject entryRuleFloatValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFloatValue = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1458:2: (iv_ruleFloatValue= ruleFloatValue EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1459:2: iv_ruleFloatValue= ruleFloatValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFloatValueRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleFloatValue_in_entryRuleFloatValue3064);
            iv_ruleFloatValue=ruleFloatValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFloatValue; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleFloatValue3074); if (state.failed) return current;

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
    // $ANTLR end "entryRuleFloatValue"


    // $ANTLR start "ruleFloatValue"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1466:1: ruleFloatValue returns [EObject current=null] : ( (lv_value_0_0= RULE_FLOAT ) ) ;
    public final EObject ruleFloatValue() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1469:28: ( ( (lv_value_0_0= RULE_FLOAT ) ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1470:1: ( (lv_value_0_0= RULE_FLOAT ) )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1470:1: ( (lv_value_0_0= RULE_FLOAT ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1471:1: (lv_value_0_0= RULE_FLOAT )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1471:1: (lv_value_0_0= RULE_FLOAT )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1472:3: lv_value_0_0= RULE_FLOAT
            {
            lv_value_0_0=(Token)match(input,RULE_FLOAT,FollowSets000.FOLLOW_RULE_FLOAT_in_ruleFloatValue3115); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_value_0_0, grammarAccess.getFloatValueAccess().getValueFLOATTerminalRuleCall_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getFloatValueRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"value",
                      		lv_value_0_0, 
                      		"FLOAT");
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleFloatValue"


    // $ANTLR start "entryRuleBoolValue"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1496:1: entryRuleBoolValue returns [EObject current=null] : iv_ruleBoolValue= ruleBoolValue EOF ;
    public final EObject entryRuleBoolValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBoolValue = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1497:2: (iv_ruleBoolValue= ruleBoolValue EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1498:2: iv_ruleBoolValue= ruleBoolValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBoolValueRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleBoolValue_in_entryRuleBoolValue3155);
            iv_ruleBoolValue=ruleBoolValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBoolValue; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleBoolValue3165); if (state.failed) return current;

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
    // $ANTLR end "entryRuleBoolValue"


    // $ANTLR start "ruleBoolValue"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1505:1: ruleBoolValue returns [EObject current=null] : ( (lv_value_0_0= RULE_BOOLEAN ) ) ;
    public final EObject ruleBoolValue() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1508:28: ( ( (lv_value_0_0= RULE_BOOLEAN ) ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1509:1: ( (lv_value_0_0= RULE_BOOLEAN ) )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1509:1: ( (lv_value_0_0= RULE_BOOLEAN ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1510:1: (lv_value_0_0= RULE_BOOLEAN )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1510:1: (lv_value_0_0= RULE_BOOLEAN )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1511:3: lv_value_0_0= RULE_BOOLEAN
            {
            lv_value_0_0=(Token)match(input,RULE_BOOLEAN,FollowSets000.FOLLOW_RULE_BOOLEAN_in_ruleBoolValue3206); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_value_0_0, grammarAccess.getBoolValueAccess().getValueBOOLEANTerminalRuleCall_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getBoolValueRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"value",
                      		lv_value_0_0, 
                      		"BOOLEAN");
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleBoolValue"


    // $ANTLR start "entryRuleDoubleValue"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1535:1: entryRuleDoubleValue returns [EObject current=null] : iv_ruleDoubleValue= ruleDoubleValue EOF ;
    public final EObject entryRuleDoubleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDoubleValue = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1536:2: (iv_ruleDoubleValue= ruleDoubleValue EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1537:2: iv_ruleDoubleValue= ruleDoubleValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDoubleValueRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleDoubleValue_in_entryRuleDoubleValue3246);
            iv_ruleDoubleValue=ruleDoubleValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDoubleValue; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleDoubleValue3256); if (state.failed) return current;

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
    // $ANTLR end "entryRuleDoubleValue"


    // $ANTLR start "ruleDoubleValue"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1544:1: ruleDoubleValue returns [EObject current=null] : ( (lv_value_0_0= RULE_FLOAT ) ) ;
    public final EObject ruleDoubleValue() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1547:28: ( ( (lv_value_0_0= RULE_FLOAT ) ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1548:1: ( (lv_value_0_0= RULE_FLOAT ) )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1548:1: ( (lv_value_0_0= RULE_FLOAT ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1549:1: (lv_value_0_0= RULE_FLOAT )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1549:1: (lv_value_0_0= RULE_FLOAT )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1550:3: lv_value_0_0= RULE_FLOAT
            {
            lv_value_0_0=(Token)match(input,RULE_FLOAT,FollowSets000.FOLLOW_RULE_FLOAT_in_ruleDoubleValue3297); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_value_0_0, grammarAccess.getDoubleValueAccess().getValueFLOATTerminalRuleCall_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getDoubleValueRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"value",
                      		lv_value_0_0, 
                      		"FLOAT");
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleDoubleValue"


    // $ANTLR start "entryRuleAnnotation"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1576:1: entryRuleAnnotation returns [EObject current=null] : iv_ruleAnnotation= ruleAnnotation EOF ;
    public final EObject entryRuleAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnnotation = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1577:2: (iv_ruleAnnotation= ruleAnnotation EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1578:2: iv_ruleAnnotation= ruleAnnotation EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAnnotationRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleAnnotation_in_entryRuleAnnotation3339);
            iv_ruleAnnotation=ruleAnnotation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAnnotation; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleAnnotation3349); if (state.failed) return current;

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
    // $ANTLR end "entryRuleAnnotation"


    // $ANTLR start "ruleAnnotation"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1585:1: ruleAnnotation returns [EObject current=null] : (this_CommentAnnotation_0= ruleCommentAnnotation | this_TagAnnotation_1= ruleTagAnnotation | this_KeyStringValueAnnotation_2= ruleKeyStringValueAnnotation | this_TypedKeyStringValueAnnotation_3= ruleTypedKeyStringValueAnnotation | this_KeyBooleanValueAnnotation_4= ruleKeyBooleanValueAnnotation | this_KeyIntValueAnnotation_5= ruleKeyIntValueAnnotation | this_KeyFloatValueAnnotation_6= ruleKeyFloatValueAnnotation ) ;
    public final EObject ruleAnnotation() throws RecognitionException {
        EObject current = null;

        EObject this_CommentAnnotation_0 = null;

        EObject this_TagAnnotation_1 = null;

        EObject this_KeyStringValueAnnotation_2 = null;

        EObject this_TypedKeyStringValueAnnotation_3 = null;

        EObject this_KeyBooleanValueAnnotation_4 = null;

        EObject this_KeyIntValueAnnotation_5 = null;

        EObject this_KeyFloatValueAnnotation_6 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1588:28: ( (this_CommentAnnotation_0= ruleCommentAnnotation | this_TagAnnotation_1= ruleTagAnnotation | this_KeyStringValueAnnotation_2= ruleKeyStringValueAnnotation | this_TypedKeyStringValueAnnotation_3= ruleTypedKeyStringValueAnnotation | this_KeyBooleanValueAnnotation_4= ruleKeyBooleanValueAnnotation | this_KeyIntValueAnnotation_5= ruleKeyIntValueAnnotation | this_KeyFloatValueAnnotation_6= ruleKeyFloatValueAnnotation ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1589:1: (this_CommentAnnotation_0= ruleCommentAnnotation | this_TagAnnotation_1= ruleTagAnnotation | this_KeyStringValueAnnotation_2= ruleKeyStringValueAnnotation | this_TypedKeyStringValueAnnotation_3= ruleTypedKeyStringValueAnnotation | this_KeyBooleanValueAnnotation_4= ruleKeyBooleanValueAnnotation | this_KeyIntValueAnnotation_5= ruleKeyIntValueAnnotation | this_KeyFloatValueAnnotation_6= ruleKeyFloatValueAnnotation )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1589:1: (this_CommentAnnotation_0= ruleCommentAnnotation | this_TagAnnotation_1= ruleTagAnnotation | this_KeyStringValueAnnotation_2= ruleKeyStringValueAnnotation | this_TypedKeyStringValueAnnotation_3= ruleTypedKeyStringValueAnnotation | this_KeyBooleanValueAnnotation_4= ruleKeyBooleanValueAnnotation | this_KeyIntValueAnnotation_5= ruleKeyIntValueAnnotation | this_KeyFloatValueAnnotation_6= ruleKeyFloatValueAnnotation )
            int alt20=7;
            alt20 = dfa20.predict(input);
            switch (alt20) {
                case 1 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1590:2: this_CommentAnnotation_0= ruleCommentAnnotation
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getAnnotationAccess().getCommentAnnotationParserRuleCall_0()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleCommentAnnotation_in_ruleAnnotation3399);
                    this_CommentAnnotation_0=ruleCommentAnnotation();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_CommentAnnotation_0; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1603:2: this_TagAnnotation_1= ruleTagAnnotation
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getAnnotationAccess().getTagAnnotationParserRuleCall_1()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleTagAnnotation_in_ruleAnnotation3429);
                    this_TagAnnotation_1=ruleTagAnnotation();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_TagAnnotation_1; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 3 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1616:2: this_KeyStringValueAnnotation_2= ruleKeyStringValueAnnotation
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getAnnotationAccess().getKeyStringValueAnnotationParserRuleCall_2()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleKeyStringValueAnnotation_in_ruleAnnotation3459);
                    this_KeyStringValueAnnotation_2=ruleKeyStringValueAnnotation();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_KeyStringValueAnnotation_2; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 4 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1629:2: this_TypedKeyStringValueAnnotation_3= ruleTypedKeyStringValueAnnotation
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getAnnotationAccess().getTypedKeyStringValueAnnotationParserRuleCall_3()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleTypedKeyStringValueAnnotation_in_ruleAnnotation3489);
                    this_TypedKeyStringValueAnnotation_3=ruleTypedKeyStringValueAnnotation();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_TypedKeyStringValueAnnotation_3; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 5 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1642:2: this_KeyBooleanValueAnnotation_4= ruleKeyBooleanValueAnnotation
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getAnnotationAccess().getKeyBooleanValueAnnotationParserRuleCall_4()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleKeyBooleanValueAnnotation_in_ruleAnnotation3519);
                    this_KeyBooleanValueAnnotation_4=ruleKeyBooleanValueAnnotation();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_KeyBooleanValueAnnotation_4; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 6 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1655:2: this_KeyIntValueAnnotation_5= ruleKeyIntValueAnnotation
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getAnnotationAccess().getKeyIntValueAnnotationParserRuleCall_5()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleKeyIntValueAnnotation_in_ruleAnnotation3549);
                    this_KeyIntValueAnnotation_5=ruleKeyIntValueAnnotation();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_KeyIntValueAnnotation_5; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 7 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1668:2: this_KeyFloatValueAnnotation_6= ruleKeyFloatValueAnnotation
                    {
                    if ( state.backtracking==0 ) {
                       
                      	  /* */ 
                      	
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getAnnotationAccess().getKeyFloatValueAnnotationParserRuleCall_6()); 
                          
                    }
                    pushFollow(FollowSets000.FOLLOW_ruleKeyFloatValueAnnotation_in_ruleAnnotation3579);
                    this_KeyFloatValueAnnotation_6=ruleKeyFloatValueAnnotation();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_KeyFloatValueAnnotation_6; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleAnnotation"


    // $ANTLR start "entryRuleCommentAnnotation"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1689:1: entryRuleCommentAnnotation returns [EObject current=null] : iv_ruleCommentAnnotation= ruleCommentAnnotation EOF ;
    public final EObject entryRuleCommentAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCommentAnnotation = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1690:2: (iv_ruleCommentAnnotation= ruleCommentAnnotation EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1691:2: iv_ruleCommentAnnotation= ruleCommentAnnotation EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCommentAnnotationRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleCommentAnnotation_in_entryRuleCommentAnnotation3616);
            iv_ruleCommentAnnotation=ruleCommentAnnotation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleCommentAnnotation; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleCommentAnnotation3626); if (state.failed) return current;

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
    // $ANTLR end "entryRuleCommentAnnotation"


    // $ANTLR start "ruleCommentAnnotation"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1698:1: ruleCommentAnnotation returns [EObject current=null] : ( (lv_value_0_0= RULE_COMMENT_ANNOTATION ) ) ;
    public final EObject ruleCommentAnnotation() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1701:28: ( ( (lv_value_0_0= RULE_COMMENT_ANNOTATION ) ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1702:1: ( (lv_value_0_0= RULE_COMMENT_ANNOTATION ) )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1702:1: ( (lv_value_0_0= RULE_COMMENT_ANNOTATION ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1703:1: (lv_value_0_0= RULE_COMMENT_ANNOTATION )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1703:1: (lv_value_0_0= RULE_COMMENT_ANNOTATION )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1704:3: lv_value_0_0= RULE_COMMENT_ANNOTATION
            {
            lv_value_0_0=(Token)match(input,RULE_COMMENT_ANNOTATION,FollowSets000.FOLLOW_RULE_COMMENT_ANNOTATION_in_ruleCommentAnnotation3667); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_value_0_0, grammarAccess.getCommentAnnotationAccess().getValueCOMMENT_ANNOTATIONTerminalRuleCall_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getCommentAnnotationRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"value",
                      		lv_value_0_0, 
                      		"COMMENT_ANNOTATION");
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleCommentAnnotation"


    // $ANTLR start "entryRuleTagAnnotation"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1728:1: entryRuleTagAnnotation returns [EObject current=null] : iv_ruleTagAnnotation= ruleTagAnnotation EOF ;
    public final EObject entryRuleTagAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTagAnnotation = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1729:2: (iv_ruleTagAnnotation= ruleTagAnnotation EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1730:2: iv_ruleTagAnnotation= ruleTagAnnotation EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTagAnnotationRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleTagAnnotation_in_entryRuleTagAnnotation3707);
            iv_ruleTagAnnotation=ruleTagAnnotation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTagAnnotation; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleTagAnnotation3717); if (state.failed) return current;

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
    // $ANTLR end "entryRuleTagAnnotation"


    // $ANTLR start "ruleTagAnnotation"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1737:1: ruleTagAnnotation returns [EObject current=null] : (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) (otherlv_2= '(' ( (lv_annotations_3_0= ruleAnnotation ) )* otherlv_4= ')' )? ) ;
    public final EObject ruleTagAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_annotations_3_0 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1740:28: ( (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) (otherlv_2= '(' ( (lv_annotations_3_0= ruleAnnotation ) )* otherlv_4= ')' )? ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1741:1: (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) (otherlv_2= '(' ( (lv_annotations_3_0= ruleAnnotation ) )* otherlv_4= ')' )? )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1741:1: (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) (otherlv_2= '(' ( (lv_annotations_3_0= ruleAnnotation ) )* otherlv_4= ')' )? )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1741:3: otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) (otherlv_2= '(' ( (lv_annotations_3_0= ruleAnnotation ) )* otherlv_4= ')' )?
            {
            otherlv_0=(Token)match(input,18,FollowSets000.FOLLOW_18_in_ruleTagAnnotation3754); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getTagAnnotationAccess().getCommercialAtKeyword_0());
                  
            }
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1745:1: ( (lv_name_1_0= ruleExtendedID ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1746:1: (lv_name_1_0= ruleExtendedID )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1746:1: (lv_name_1_0= ruleExtendedID )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1747:3: lv_name_1_0= ruleExtendedID
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getTagAnnotationAccess().getNameExtendedIDParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FollowSets000.FOLLOW_ruleExtendedID_in_ruleTagAnnotation3775);
            lv_name_1_0=ruleExtendedID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getTagAnnotationRule());
              	        }
                     		set(
                     			current, 
                     			"name",
                      		lv_name_1_0, 
                      		"ExtendedID");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1763:2: (otherlv_2= '(' ( (lv_annotations_3_0= ruleAnnotation ) )* otherlv_4= ')' )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==16) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1763:4: otherlv_2= '(' ( (lv_annotations_3_0= ruleAnnotation ) )* otherlv_4= ')'
                    {
                    otherlv_2=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleTagAnnotation3788); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_2, grammarAccess.getTagAnnotationAccess().getLeftParenthesisKeyword_2_0());
                          
                    }
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1767:1: ( (lv_annotations_3_0= ruleAnnotation ) )*
                    loop21:
                    do {
                        int alt21=2;
                        int LA21_0 = input.LA(1);

                        if ( (LA21_0==RULE_COMMENT_ANNOTATION||LA21_0==18) ) {
                            alt21=1;
                        }


                        switch (alt21) {
                    	case 1 :
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1768:1: (lv_annotations_3_0= ruleAnnotation )
                    	    {
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1768:1: (lv_annotations_3_0= ruleAnnotation )
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1769:3: lv_annotations_3_0= ruleAnnotation
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getTagAnnotationAccess().getAnnotationsAnnotationParserRuleCall_2_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FollowSets000.FOLLOW_ruleAnnotation_in_ruleTagAnnotation3809);
                    	    lv_annotations_3_0=ruleAnnotation();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getTagAnnotationRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"annotations",
                    	              		lv_annotations_3_0, 
                    	              		"Annotation");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop21;
                        }
                    } while (true);

                    otherlv_4=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleTagAnnotation3822); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_4, grammarAccess.getTagAnnotationAccess().getRightParenthesisKeyword_2_2());
                          
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleTagAnnotation"


    // $ANTLR start "entryRuleKeyStringValueAnnotation"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1797:1: entryRuleKeyStringValueAnnotation returns [EObject current=null] : iv_ruleKeyStringValueAnnotation= ruleKeyStringValueAnnotation EOF ;
    public final EObject entryRuleKeyStringValueAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyStringValueAnnotation = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1798:2: (iv_ruleKeyStringValueAnnotation= ruleKeyStringValueAnnotation EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1799:2: iv_ruleKeyStringValueAnnotation= ruleKeyStringValueAnnotation EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getKeyStringValueAnnotationRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleKeyStringValueAnnotation_in_entryRuleKeyStringValueAnnotation3860);
            iv_ruleKeyStringValueAnnotation=ruleKeyStringValueAnnotation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleKeyStringValueAnnotation; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleKeyStringValueAnnotation3870); if (state.failed) return current;

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
    // $ANTLR end "entryRuleKeyStringValueAnnotation"


    // $ANTLR start "ruleKeyStringValueAnnotation"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1806:1: ruleKeyStringValueAnnotation returns [EObject current=null] : (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) ( (lv_value_2_0= ruleEString ) ) (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )? ) ;
    public final EObject ruleKeyStringValueAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        AntlrDatatypeRuleToken lv_value_2_0 = null;

        EObject lv_annotations_4_0 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1809:28: ( (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) ( (lv_value_2_0= ruleEString ) ) (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )? ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1810:1: (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) ( (lv_value_2_0= ruleEString ) ) (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )? )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1810:1: (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) ( (lv_value_2_0= ruleEString ) ) (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )? )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1810:3: otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) ( (lv_value_2_0= ruleEString ) ) (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )?
            {
            otherlv_0=(Token)match(input,18,FollowSets000.FOLLOW_18_in_ruleKeyStringValueAnnotation3907); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getKeyStringValueAnnotationAccess().getCommercialAtKeyword_0());
                  
            }
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1814:1: ( (lv_name_1_0= ruleExtendedID ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1815:1: (lv_name_1_0= ruleExtendedID )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1815:1: (lv_name_1_0= ruleExtendedID )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1816:3: lv_name_1_0= ruleExtendedID
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getKeyStringValueAnnotationAccess().getNameExtendedIDParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FollowSets000.FOLLOW_ruleExtendedID_in_ruleKeyStringValueAnnotation3928);
            lv_name_1_0=ruleExtendedID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getKeyStringValueAnnotationRule());
              	        }
                     		set(
                     			current, 
                     			"name",
                      		lv_name_1_0, 
                      		"ExtendedID");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1832:2: ( (lv_value_2_0= ruleEString ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1833:1: (lv_value_2_0= ruleEString )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1833:1: (lv_value_2_0= ruleEString )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1834:3: lv_value_2_0= ruleEString
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getKeyStringValueAnnotationAccess().getValueEStringParserRuleCall_2_0()); 
              	    
            }
            pushFollow(FollowSets000.FOLLOW_ruleEString_in_ruleKeyStringValueAnnotation3949);
            lv_value_2_0=ruleEString();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getKeyStringValueAnnotationRule());
              	        }
                     		set(
                     			current, 
                     			"value",
                      		lv_value_2_0, 
                      		"EString");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1850:2: (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==16) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1850:4: otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')'
                    {
                    otherlv_3=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleKeyStringValueAnnotation3962); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getKeyStringValueAnnotationAccess().getLeftParenthesisKeyword_3_0());
                          
                    }
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1854:1: ( (lv_annotations_4_0= ruleAnnotation ) )*
                    loop23:
                    do {
                        int alt23=2;
                        int LA23_0 = input.LA(1);

                        if ( (LA23_0==RULE_COMMENT_ANNOTATION||LA23_0==18) ) {
                            alt23=1;
                        }


                        switch (alt23) {
                    	case 1 :
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1855:1: (lv_annotations_4_0= ruleAnnotation )
                    	    {
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1855:1: (lv_annotations_4_0= ruleAnnotation )
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1856:3: lv_annotations_4_0= ruleAnnotation
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getKeyStringValueAnnotationAccess().getAnnotationsAnnotationParserRuleCall_3_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FollowSets000.FOLLOW_ruleAnnotation_in_ruleKeyStringValueAnnotation3983);
                    	    lv_annotations_4_0=ruleAnnotation();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getKeyStringValueAnnotationRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"annotations",
                    	              		lv_annotations_4_0, 
                    	              		"Annotation");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop23;
                        }
                    } while (true);

                    otherlv_5=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleKeyStringValueAnnotation3996); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_5, grammarAccess.getKeyStringValueAnnotationAccess().getRightParenthesisKeyword_3_2());
                          
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleKeyStringValueAnnotation"


    // $ANTLR start "entryRuleTypedKeyStringValueAnnotation"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1884:1: entryRuleTypedKeyStringValueAnnotation returns [EObject current=null] : iv_ruleTypedKeyStringValueAnnotation= ruleTypedKeyStringValueAnnotation EOF ;
    public final EObject entryRuleTypedKeyStringValueAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTypedKeyStringValueAnnotation = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1885:2: (iv_ruleTypedKeyStringValueAnnotation= ruleTypedKeyStringValueAnnotation EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1886:2: iv_ruleTypedKeyStringValueAnnotation= ruleTypedKeyStringValueAnnotation EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypedKeyStringValueAnnotationRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleTypedKeyStringValueAnnotation_in_entryRuleTypedKeyStringValueAnnotation4034);
            iv_ruleTypedKeyStringValueAnnotation=ruleTypedKeyStringValueAnnotation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypedKeyStringValueAnnotation; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleTypedKeyStringValueAnnotation4044); if (state.failed) return current;

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
    // $ANTLR end "entryRuleTypedKeyStringValueAnnotation"


    // $ANTLR start "ruleTypedKeyStringValueAnnotation"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1893:1: ruleTypedKeyStringValueAnnotation returns [EObject current=null] : (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) otherlv_2= '[' ( (lv_type_3_0= ruleExtendedID ) ) otherlv_4= ']' ( (lv_value_5_0= ruleEString ) ) (otherlv_6= '(' ( (lv_annotations_7_0= ruleAnnotation ) )* otherlv_8= ')' )? ) ;
    public final EObject ruleTypedKeyStringValueAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        AntlrDatatypeRuleToken lv_type_3_0 = null;

        AntlrDatatypeRuleToken lv_value_5_0 = null;

        EObject lv_annotations_7_0 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1896:28: ( (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) otherlv_2= '[' ( (lv_type_3_0= ruleExtendedID ) ) otherlv_4= ']' ( (lv_value_5_0= ruleEString ) ) (otherlv_6= '(' ( (lv_annotations_7_0= ruleAnnotation ) )* otherlv_8= ')' )? ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1897:1: (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) otherlv_2= '[' ( (lv_type_3_0= ruleExtendedID ) ) otherlv_4= ']' ( (lv_value_5_0= ruleEString ) ) (otherlv_6= '(' ( (lv_annotations_7_0= ruleAnnotation ) )* otherlv_8= ')' )? )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1897:1: (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) otherlv_2= '[' ( (lv_type_3_0= ruleExtendedID ) ) otherlv_4= ']' ( (lv_value_5_0= ruleEString ) ) (otherlv_6= '(' ( (lv_annotations_7_0= ruleAnnotation ) )* otherlv_8= ')' )? )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1897:3: otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) otherlv_2= '[' ( (lv_type_3_0= ruleExtendedID ) ) otherlv_4= ']' ( (lv_value_5_0= ruleEString ) ) (otherlv_6= '(' ( (lv_annotations_7_0= ruleAnnotation ) )* otherlv_8= ')' )?
            {
            otherlv_0=(Token)match(input,18,FollowSets000.FOLLOW_18_in_ruleTypedKeyStringValueAnnotation4081); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getTypedKeyStringValueAnnotationAccess().getCommercialAtKeyword_0());
                  
            }
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1901:1: ( (lv_name_1_0= ruleExtendedID ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1902:1: (lv_name_1_0= ruleExtendedID )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1902:1: (lv_name_1_0= ruleExtendedID )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1903:3: lv_name_1_0= ruleExtendedID
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getTypedKeyStringValueAnnotationAccess().getNameExtendedIDParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FollowSets000.FOLLOW_ruleExtendedID_in_ruleTypedKeyStringValueAnnotation4102);
            lv_name_1_0=ruleExtendedID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getTypedKeyStringValueAnnotationRule());
              	        }
                     		set(
                     			current, 
                     			"name",
                      		lv_name_1_0, 
                      		"ExtendedID");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_2=(Token)match(input,19,FollowSets000.FOLLOW_19_in_ruleTypedKeyStringValueAnnotation4114); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getTypedKeyStringValueAnnotationAccess().getLeftSquareBracketKeyword_2());
                  
            }
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1923:1: ( (lv_type_3_0= ruleExtendedID ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1924:1: (lv_type_3_0= ruleExtendedID )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1924:1: (lv_type_3_0= ruleExtendedID )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1925:3: lv_type_3_0= ruleExtendedID
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getTypedKeyStringValueAnnotationAccess().getTypeExtendedIDParserRuleCall_3_0()); 
              	    
            }
            pushFollow(FollowSets000.FOLLOW_ruleExtendedID_in_ruleTypedKeyStringValueAnnotation4135);
            lv_type_3_0=ruleExtendedID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getTypedKeyStringValueAnnotationRule());
              	        }
                     		set(
                     			current, 
                     			"type",
                      		lv_type_3_0, 
                      		"ExtendedID");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_4=(Token)match(input,20,FollowSets000.FOLLOW_20_in_ruleTypedKeyStringValueAnnotation4147); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_4, grammarAccess.getTypedKeyStringValueAnnotationAccess().getRightSquareBracketKeyword_4());
                  
            }
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1945:1: ( (lv_value_5_0= ruleEString ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1946:1: (lv_value_5_0= ruleEString )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1946:1: (lv_value_5_0= ruleEString )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1947:3: lv_value_5_0= ruleEString
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getTypedKeyStringValueAnnotationAccess().getValueEStringParserRuleCall_5_0()); 
              	    
            }
            pushFollow(FollowSets000.FOLLOW_ruleEString_in_ruleTypedKeyStringValueAnnotation4168);
            lv_value_5_0=ruleEString();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getTypedKeyStringValueAnnotationRule());
              	        }
                     		set(
                     			current, 
                     			"value",
                      		lv_value_5_0, 
                      		"EString");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1963:2: (otherlv_6= '(' ( (lv_annotations_7_0= ruleAnnotation ) )* otherlv_8= ')' )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==16) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1963:4: otherlv_6= '(' ( (lv_annotations_7_0= ruleAnnotation ) )* otherlv_8= ')'
                    {
                    otherlv_6=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleTypedKeyStringValueAnnotation4181); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_6, grammarAccess.getTypedKeyStringValueAnnotationAccess().getLeftParenthesisKeyword_6_0());
                          
                    }
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1967:1: ( (lv_annotations_7_0= ruleAnnotation ) )*
                    loop25:
                    do {
                        int alt25=2;
                        int LA25_0 = input.LA(1);

                        if ( (LA25_0==RULE_COMMENT_ANNOTATION||LA25_0==18) ) {
                            alt25=1;
                        }


                        switch (alt25) {
                    	case 1 :
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1968:1: (lv_annotations_7_0= ruleAnnotation )
                    	    {
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1968:1: (lv_annotations_7_0= ruleAnnotation )
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1969:3: lv_annotations_7_0= ruleAnnotation
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getTypedKeyStringValueAnnotationAccess().getAnnotationsAnnotationParserRuleCall_6_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FollowSets000.FOLLOW_ruleAnnotation_in_ruleTypedKeyStringValueAnnotation4202);
                    	    lv_annotations_7_0=ruleAnnotation();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getTypedKeyStringValueAnnotationRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"annotations",
                    	              		lv_annotations_7_0, 
                    	              		"Annotation");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop25;
                        }
                    } while (true);

                    otherlv_8=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleTypedKeyStringValueAnnotation4215); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_8, grammarAccess.getTypedKeyStringValueAnnotationAccess().getRightParenthesisKeyword_6_2());
                          
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleTypedKeyStringValueAnnotation"


    // $ANTLR start "entryRuleKeyBooleanValueAnnotation"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1997:1: entryRuleKeyBooleanValueAnnotation returns [EObject current=null] : iv_ruleKeyBooleanValueAnnotation= ruleKeyBooleanValueAnnotation EOF ;
    public final EObject entryRuleKeyBooleanValueAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyBooleanValueAnnotation = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1998:2: (iv_ruleKeyBooleanValueAnnotation= ruleKeyBooleanValueAnnotation EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1999:2: iv_ruleKeyBooleanValueAnnotation= ruleKeyBooleanValueAnnotation EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getKeyBooleanValueAnnotationRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleKeyBooleanValueAnnotation_in_entryRuleKeyBooleanValueAnnotation4253);
            iv_ruleKeyBooleanValueAnnotation=ruleKeyBooleanValueAnnotation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleKeyBooleanValueAnnotation; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleKeyBooleanValueAnnotation4263); if (state.failed) return current;

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
    // $ANTLR end "entryRuleKeyBooleanValueAnnotation"


    // $ANTLR start "ruleKeyBooleanValueAnnotation"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2006:1: ruleKeyBooleanValueAnnotation returns [EObject current=null] : (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) ( (lv_value_2_0= RULE_BOOLEAN ) ) (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )? ) ;
    public final EObject ruleKeyBooleanValueAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_value_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_annotations_4_0 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2009:28: ( (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) ( (lv_value_2_0= RULE_BOOLEAN ) ) (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )? ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2010:1: (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) ( (lv_value_2_0= RULE_BOOLEAN ) ) (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )? )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2010:1: (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) ( (lv_value_2_0= RULE_BOOLEAN ) ) (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )? )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2010:3: otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) ( (lv_value_2_0= RULE_BOOLEAN ) ) (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )?
            {
            otherlv_0=(Token)match(input,18,FollowSets000.FOLLOW_18_in_ruleKeyBooleanValueAnnotation4300); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getKeyBooleanValueAnnotationAccess().getCommercialAtKeyword_0());
                  
            }
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2014:1: ( (lv_name_1_0= ruleExtendedID ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2015:1: (lv_name_1_0= ruleExtendedID )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2015:1: (lv_name_1_0= ruleExtendedID )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2016:3: lv_name_1_0= ruleExtendedID
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getKeyBooleanValueAnnotationAccess().getNameExtendedIDParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FollowSets000.FOLLOW_ruleExtendedID_in_ruleKeyBooleanValueAnnotation4321);
            lv_name_1_0=ruleExtendedID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getKeyBooleanValueAnnotationRule());
              	        }
                     		set(
                     			current, 
                     			"name",
                      		lv_name_1_0, 
                      		"ExtendedID");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2032:2: ( (lv_value_2_0= RULE_BOOLEAN ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2033:1: (lv_value_2_0= RULE_BOOLEAN )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2033:1: (lv_value_2_0= RULE_BOOLEAN )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2034:3: lv_value_2_0= RULE_BOOLEAN
            {
            lv_value_2_0=(Token)match(input,RULE_BOOLEAN,FollowSets000.FOLLOW_RULE_BOOLEAN_in_ruleKeyBooleanValueAnnotation4338); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_value_2_0, grammarAccess.getKeyBooleanValueAnnotationAccess().getValueBOOLEANTerminalRuleCall_2_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getKeyBooleanValueAnnotationRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"value",
                      		lv_value_2_0, 
                      		"BOOLEAN");
              	    
            }

            }


            }

            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2050:2: (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==16) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2050:4: otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')'
                    {
                    otherlv_3=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleKeyBooleanValueAnnotation4356); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getKeyBooleanValueAnnotationAccess().getLeftParenthesisKeyword_3_0());
                          
                    }
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2054:1: ( (lv_annotations_4_0= ruleAnnotation ) )*
                    loop27:
                    do {
                        int alt27=2;
                        int LA27_0 = input.LA(1);

                        if ( (LA27_0==RULE_COMMENT_ANNOTATION||LA27_0==18) ) {
                            alt27=1;
                        }


                        switch (alt27) {
                    	case 1 :
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2055:1: (lv_annotations_4_0= ruleAnnotation )
                    	    {
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2055:1: (lv_annotations_4_0= ruleAnnotation )
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2056:3: lv_annotations_4_0= ruleAnnotation
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getKeyBooleanValueAnnotationAccess().getAnnotationsAnnotationParserRuleCall_3_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FollowSets000.FOLLOW_ruleAnnotation_in_ruleKeyBooleanValueAnnotation4377);
                    	    lv_annotations_4_0=ruleAnnotation();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getKeyBooleanValueAnnotationRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"annotations",
                    	              		lv_annotations_4_0, 
                    	              		"Annotation");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop27;
                        }
                    } while (true);

                    otherlv_5=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleKeyBooleanValueAnnotation4390); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_5, grammarAccess.getKeyBooleanValueAnnotationAccess().getRightParenthesisKeyword_3_2());
                          
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleKeyBooleanValueAnnotation"


    // $ANTLR start "entryRuleKeyIntValueAnnotation"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2084:1: entryRuleKeyIntValueAnnotation returns [EObject current=null] : iv_ruleKeyIntValueAnnotation= ruleKeyIntValueAnnotation EOF ;
    public final EObject entryRuleKeyIntValueAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyIntValueAnnotation = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2085:2: (iv_ruleKeyIntValueAnnotation= ruleKeyIntValueAnnotation EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2086:2: iv_ruleKeyIntValueAnnotation= ruleKeyIntValueAnnotation EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getKeyIntValueAnnotationRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleKeyIntValueAnnotation_in_entryRuleKeyIntValueAnnotation4428);
            iv_ruleKeyIntValueAnnotation=ruleKeyIntValueAnnotation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleKeyIntValueAnnotation; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleKeyIntValueAnnotation4438); if (state.failed) return current;

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
    // $ANTLR end "entryRuleKeyIntValueAnnotation"


    // $ANTLR start "ruleKeyIntValueAnnotation"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2093:1: ruleKeyIntValueAnnotation returns [EObject current=null] : (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) ( (lv_value_2_0= RULE_INT ) ) (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )? ) ;
    public final EObject ruleKeyIntValueAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_value_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_annotations_4_0 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2096:28: ( (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) ( (lv_value_2_0= RULE_INT ) ) (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )? ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2097:1: (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) ( (lv_value_2_0= RULE_INT ) ) (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )? )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2097:1: (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) ( (lv_value_2_0= RULE_INT ) ) (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )? )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2097:3: otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) ( (lv_value_2_0= RULE_INT ) ) (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )?
            {
            otherlv_0=(Token)match(input,18,FollowSets000.FOLLOW_18_in_ruleKeyIntValueAnnotation4475); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getKeyIntValueAnnotationAccess().getCommercialAtKeyword_0());
                  
            }
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2101:1: ( (lv_name_1_0= ruleExtendedID ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2102:1: (lv_name_1_0= ruleExtendedID )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2102:1: (lv_name_1_0= ruleExtendedID )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2103:3: lv_name_1_0= ruleExtendedID
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getKeyIntValueAnnotationAccess().getNameExtendedIDParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FollowSets000.FOLLOW_ruleExtendedID_in_ruleKeyIntValueAnnotation4496);
            lv_name_1_0=ruleExtendedID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getKeyIntValueAnnotationRule());
              	        }
                     		set(
                     			current, 
                     			"name",
                      		lv_name_1_0, 
                      		"ExtendedID");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2119:2: ( (lv_value_2_0= RULE_INT ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2120:1: (lv_value_2_0= RULE_INT )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2120:1: (lv_value_2_0= RULE_INT )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2121:3: lv_value_2_0= RULE_INT
            {
            lv_value_2_0=(Token)match(input,RULE_INT,FollowSets000.FOLLOW_RULE_INT_in_ruleKeyIntValueAnnotation4513); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_value_2_0, grammarAccess.getKeyIntValueAnnotationAccess().getValueINTTerminalRuleCall_2_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getKeyIntValueAnnotationRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"value",
                      		lv_value_2_0, 
                      		"INT");
              	    
            }

            }


            }

            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2137:2: (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==16) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2137:4: otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')'
                    {
                    otherlv_3=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleKeyIntValueAnnotation4531); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getKeyIntValueAnnotationAccess().getLeftParenthesisKeyword_3_0());
                          
                    }
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2141:1: ( (lv_annotations_4_0= ruleAnnotation ) )*
                    loop29:
                    do {
                        int alt29=2;
                        int LA29_0 = input.LA(1);

                        if ( (LA29_0==RULE_COMMENT_ANNOTATION||LA29_0==18) ) {
                            alt29=1;
                        }


                        switch (alt29) {
                    	case 1 :
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2142:1: (lv_annotations_4_0= ruleAnnotation )
                    	    {
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2142:1: (lv_annotations_4_0= ruleAnnotation )
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2143:3: lv_annotations_4_0= ruleAnnotation
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getKeyIntValueAnnotationAccess().getAnnotationsAnnotationParserRuleCall_3_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FollowSets000.FOLLOW_ruleAnnotation_in_ruleKeyIntValueAnnotation4552);
                    	    lv_annotations_4_0=ruleAnnotation();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getKeyIntValueAnnotationRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"annotations",
                    	              		lv_annotations_4_0, 
                    	              		"Annotation");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop29;
                        }
                    } while (true);

                    otherlv_5=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleKeyIntValueAnnotation4565); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_5, grammarAccess.getKeyIntValueAnnotationAccess().getRightParenthesisKeyword_3_2());
                          
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleKeyIntValueAnnotation"


    // $ANTLR start "entryRuleKeyFloatValueAnnotation"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2171:1: entryRuleKeyFloatValueAnnotation returns [EObject current=null] : iv_ruleKeyFloatValueAnnotation= ruleKeyFloatValueAnnotation EOF ;
    public final EObject entryRuleKeyFloatValueAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyFloatValueAnnotation = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2172:2: (iv_ruleKeyFloatValueAnnotation= ruleKeyFloatValueAnnotation EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2173:2: iv_ruleKeyFloatValueAnnotation= ruleKeyFloatValueAnnotation EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getKeyFloatValueAnnotationRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleKeyFloatValueAnnotation_in_entryRuleKeyFloatValueAnnotation4603);
            iv_ruleKeyFloatValueAnnotation=ruleKeyFloatValueAnnotation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleKeyFloatValueAnnotation; 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleKeyFloatValueAnnotation4613); if (state.failed) return current;

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
    // $ANTLR end "entryRuleKeyFloatValueAnnotation"


    // $ANTLR start "ruleKeyFloatValueAnnotation"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2180:1: ruleKeyFloatValueAnnotation returns [EObject current=null] : (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) ( (lv_value_2_0= RULE_FLOAT ) ) (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )? ) ;
    public final EObject ruleKeyFloatValueAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_value_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_annotations_4_0 = null;


         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2183:28: ( (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) ( (lv_value_2_0= RULE_FLOAT ) ) (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )? ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2184:1: (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) ( (lv_value_2_0= RULE_FLOAT ) ) (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )? )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2184:1: (otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) ( (lv_value_2_0= RULE_FLOAT ) ) (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )? )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2184:3: otherlv_0= '@' ( (lv_name_1_0= ruleExtendedID ) ) ( (lv_value_2_0= RULE_FLOAT ) ) (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )?
            {
            otherlv_0=(Token)match(input,18,FollowSets000.FOLLOW_18_in_ruleKeyFloatValueAnnotation4650); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getKeyFloatValueAnnotationAccess().getCommercialAtKeyword_0());
                  
            }
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2188:1: ( (lv_name_1_0= ruleExtendedID ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2189:1: (lv_name_1_0= ruleExtendedID )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2189:1: (lv_name_1_0= ruleExtendedID )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2190:3: lv_name_1_0= ruleExtendedID
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getKeyFloatValueAnnotationAccess().getNameExtendedIDParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FollowSets000.FOLLOW_ruleExtendedID_in_ruleKeyFloatValueAnnotation4671);
            lv_name_1_0=ruleExtendedID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getKeyFloatValueAnnotationRule());
              	        }
                     		set(
                     			current, 
                     			"name",
                      		lv_name_1_0, 
                      		"ExtendedID");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2206:2: ( (lv_value_2_0= RULE_FLOAT ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2207:1: (lv_value_2_0= RULE_FLOAT )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2207:1: (lv_value_2_0= RULE_FLOAT )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2208:3: lv_value_2_0= RULE_FLOAT
            {
            lv_value_2_0=(Token)match(input,RULE_FLOAT,FollowSets000.FOLLOW_RULE_FLOAT_in_ruleKeyFloatValueAnnotation4688); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_value_2_0, grammarAccess.getKeyFloatValueAnnotationAccess().getValueFLOATTerminalRuleCall_2_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getKeyFloatValueAnnotationRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"value",
                      		lv_value_2_0, 
                      		"FLOAT");
              	    
            }

            }


            }

            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2224:2: (otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')' )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==16) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2224:4: otherlv_3= '(' ( (lv_annotations_4_0= ruleAnnotation ) )* otherlv_5= ')'
                    {
                    otherlv_3=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleKeyFloatValueAnnotation4706); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getKeyFloatValueAnnotationAccess().getLeftParenthesisKeyword_3_0());
                          
                    }
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2228:1: ( (lv_annotations_4_0= ruleAnnotation ) )*
                    loop31:
                    do {
                        int alt31=2;
                        int LA31_0 = input.LA(1);

                        if ( (LA31_0==RULE_COMMENT_ANNOTATION||LA31_0==18) ) {
                            alt31=1;
                        }


                        switch (alt31) {
                    	case 1 :
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2229:1: (lv_annotations_4_0= ruleAnnotation )
                    	    {
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2229:1: (lv_annotations_4_0= ruleAnnotation )
                    	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2230:3: lv_annotations_4_0= ruleAnnotation
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getKeyFloatValueAnnotationAccess().getAnnotationsAnnotationParserRuleCall_3_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FollowSets000.FOLLOW_ruleAnnotation_in_ruleKeyFloatValueAnnotation4727);
                    	    lv_annotations_4_0=ruleAnnotation();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getKeyFloatValueAnnotationRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"annotations",
                    	              		lv_annotations_4_0, 
                    	              		"Annotation");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop31;
                        }
                    } while (true);

                    otherlv_5=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleKeyFloatValueAnnotation4740); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_5, grammarAccess.getKeyFloatValueAnnotationAccess().getRightParenthesisKeyword_3_2());
                          
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleKeyFloatValueAnnotation"


    // $ANTLR start "entryRuleEString"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2260:1: entryRuleEString returns [String current=null] : iv_ruleEString= ruleEString EOF ;
    public final String entryRuleEString() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleEString = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2261:2: (iv_ruleEString= ruleEString EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2262:2: iv_ruleEString= ruleEString EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getEStringRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleEString_in_entryRuleEString4781);
            iv_ruleEString=ruleEString();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleEString.getText(); 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleEString4792); if (state.failed) return current;

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
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2269:1: ruleEString returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID ) ;
    public final AntlrDatatypeRuleToken ruleEString() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_STRING_0=null;
        Token this_ID_1=null;

         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2272:28: ( (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2273:1: (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2273:1: (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID )
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==RULE_STRING) ) {
                alt33=1;
            }
            else if ( (LA33_0==RULE_ID) ) {
                alt33=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 33, 0, input);

                throw nvae;
            }
            switch (alt33) {
                case 1 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2273:6: this_STRING_0= RULE_STRING
                    {
                    this_STRING_0=(Token)match(input,RULE_STRING,FollowSets000.FOLLOW_RULE_STRING_in_ruleEString4832); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      		current.merge(this_STRING_0);
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                          newLeafNode(this_STRING_0, grammarAccess.getEStringAccess().getSTRINGTerminalRuleCall_0()); 
                          
                    }

                    }
                    break;
                case 2 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2281:10: this_ID_1= RULE_ID
                    {
                    this_ID_1=(Token)match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_ruleEString4858); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      		current.merge(this_ID_1);
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                          newLeafNode(this_ID_1, grammarAccess.getEStringAccess().getIDTerminalRuleCall_1()); 
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleEString"


    // $ANTLR start "entryRuleExtendedID"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2296:1: entryRuleExtendedID returns [String current=null] : iv_ruleExtendedID= ruleExtendedID EOF ;
    public final String entryRuleExtendedID() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleExtendedID = null;


        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2297:2: (iv_ruleExtendedID= ruleExtendedID EOF )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2298:2: iv_ruleExtendedID= ruleExtendedID EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExtendedIDRule()); 
            }
            pushFollow(FollowSets000.FOLLOW_ruleExtendedID_in_entryRuleExtendedID4904);
            iv_ruleExtendedID=ruleExtendedID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleExtendedID.getText(); 
            }
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleExtendedID4915); if (state.failed) return current;

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
    // $ANTLR end "entryRuleExtendedID"


    // $ANTLR start "ruleExtendedID"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2305:1: ruleExtendedID returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) ;
    public final AntlrDatatypeRuleToken ruleExtendedID() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;

         enterRule(); 
            
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2308:28: ( (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2309:1: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2309:1: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2309:6: this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )*
            {
            this_ID_0=(Token)match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_ruleExtendedID4955); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_ID_0);
                  
            }
            if ( state.backtracking==0 ) {
               
                  newLeafNode(this_ID_0, grammarAccess.getExtendedIDAccess().getIDTerminalRuleCall_0()); 
                  
            }
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2316:1: (kw= '.' this_ID_2= RULE_ID )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==21) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2317:2: kw= '.' this_ID_2= RULE_ID
            	    {
            	    kw=(Token)match(input,21,FollowSets000.FOLLOW_21_in_ruleExtendedID4974); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	              current.merge(kw);
            	              newLeafNode(kw, grammarAccess.getExtendedIDAccess().getFullStopKeyword_1_0()); 
            	          
            	    }
            	    this_ID_2=(Token)match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_ruleExtendedID4989); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      		current.merge(this_ID_2);
            	          
            	    }
            	    if ( state.backtracking==0 ) {
            	       
            	          newLeafNode(this_ID_2, grammarAccess.getExtendedIDAccess().getIDTerminalRuleCall_1_1()); 
            	          
            	    }

            	    }
            	    break;

            	default :
            	    break loop34;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleExtendedID"


    // $ANTLR start "ruleCompareOperator"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2337:1: ruleCompareOperator returns [Enumerator current=null] : ( (enumLiteral_0= '==' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '<=' ) | (enumLiteral_3= '>' ) | (enumLiteral_4= '>=' ) | (enumLiteral_5= '<>' ) ) ;
    public final Enumerator ruleCompareOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;

         enterRule(); 
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2339:28: ( ( (enumLiteral_0= '==' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '<=' ) | (enumLiteral_3= '>' ) | (enumLiteral_4= '>=' ) | (enumLiteral_5= '<>' ) ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2340:1: ( (enumLiteral_0= '==' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '<=' ) | (enumLiteral_3= '>' ) | (enumLiteral_4= '>=' ) | (enumLiteral_5= '<>' ) )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2340:1: ( (enumLiteral_0= '==' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '<=' ) | (enumLiteral_3= '>' ) | (enumLiteral_4= '>=' ) | (enumLiteral_5= '<>' ) )
            int alt35=6;
            switch ( input.LA(1) ) {
            case 22:
                {
                alt35=1;
                }
                break;
            case 23:
                {
                alt35=2;
                }
                break;
            case 24:
                {
                alt35=3;
                }
                break;
            case 25:
                {
                alt35=4;
                }
                break;
            case 26:
                {
                alt35=5;
                }
                break;
            case 27:
                {
                alt35=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;
            }

            switch (alt35) {
                case 1 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2340:2: (enumLiteral_0= '==' )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2340:2: (enumLiteral_0= '==' )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2340:4: enumLiteral_0= '=='
                    {
                    enumLiteral_0=(Token)match(input,22,FollowSets000.FOLLOW_22_in_ruleCompareOperator5050); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getCompareOperatorAccess().getEQEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_0, grammarAccess.getCompareOperatorAccess().getEQEnumLiteralDeclaration_0()); 
                          
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2346:6: (enumLiteral_1= '<' )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2346:6: (enumLiteral_1= '<' )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2346:8: enumLiteral_1= '<'
                    {
                    enumLiteral_1=(Token)match(input,23,FollowSets000.FOLLOW_23_in_ruleCompareOperator5067); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getCompareOperatorAccess().getLTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_1, grammarAccess.getCompareOperatorAccess().getLTEnumLiteralDeclaration_1()); 
                          
                    }

                    }


                    }
                    break;
                case 3 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2352:6: (enumLiteral_2= '<=' )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2352:6: (enumLiteral_2= '<=' )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2352:8: enumLiteral_2= '<='
                    {
                    enumLiteral_2=(Token)match(input,24,FollowSets000.FOLLOW_24_in_ruleCompareOperator5084); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getCompareOperatorAccess().getLEQEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_2, grammarAccess.getCompareOperatorAccess().getLEQEnumLiteralDeclaration_2()); 
                          
                    }

                    }


                    }
                    break;
                case 4 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2358:6: (enumLiteral_3= '>' )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2358:6: (enumLiteral_3= '>' )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2358:8: enumLiteral_3= '>'
                    {
                    enumLiteral_3=(Token)match(input,25,FollowSets000.FOLLOW_25_in_ruleCompareOperator5101); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getCompareOperatorAccess().getGTEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_3, grammarAccess.getCompareOperatorAccess().getGTEnumLiteralDeclaration_3()); 
                          
                    }

                    }


                    }
                    break;
                case 5 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2364:6: (enumLiteral_4= '>=' )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2364:6: (enumLiteral_4= '>=' )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2364:8: enumLiteral_4= '>='
                    {
                    enumLiteral_4=(Token)match(input,26,FollowSets000.FOLLOW_26_in_ruleCompareOperator5118); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getCompareOperatorAccess().getGEQEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_4, grammarAccess.getCompareOperatorAccess().getGEQEnumLiteralDeclaration_4()); 
                          
                    }

                    }


                    }
                    break;
                case 6 :
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2370:6: (enumLiteral_5= '<>' )
                    {
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2370:6: (enumLiteral_5= '<>' )
                    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2370:8: enumLiteral_5= '<>'
                    {
                    enumLiteral_5=(Token)match(input,27,FollowSets000.FOLLOW_27_in_ruleCompareOperator5135); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getCompareOperatorAccess().getNEEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_5, grammarAccess.getCompareOperatorAccess().getNEEnumLiteralDeclaration_5()); 
                          
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleCompareOperator"


    // $ANTLR start "rulePreOperator"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2380:1: rulePreOperator returns [Enumerator current=null] : (enumLiteral_0= 'pre' ) ;
    public final Enumerator rulePreOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;

         enterRule(); 
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2382:28: ( (enumLiteral_0= 'pre' ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2383:1: (enumLiteral_0= 'pre' )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2383:1: (enumLiteral_0= 'pre' )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2383:3: enumLiteral_0= 'pre'
            {
            enumLiteral_0=(Token)match(input,28,FollowSets000.FOLLOW_28_in_rulePreOperator5179); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = grammarAccess.getPreOperatorAccess().getPREEnumLiteralDeclaration().getEnumLiteral().getInstance();
                      newLeafNode(enumLiteral_0, grammarAccess.getPreOperatorAccess().getPREEnumLiteralDeclaration()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "rulePreOperator"


    // $ANTLR start "ruleOrOperator"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2393:1: ruleOrOperator returns [Enumerator current=null] : (enumLiteral_0= '|' ) ;
    public final Enumerator ruleOrOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;

         enterRule(); 
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2395:28: ( (enumLiteral_0= '|' ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2396:1: (enumLiteral_0= '|' )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2396:1: (enumLiteral_0= '|' )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2396:3: enumLiteral_0= '|'
            {
            enumLiteral_0=(Token)match(input,29,FollowSets000.FOLLOW_29_in_ruleOrOperator5222); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = grammarAccess.getOrOperatorAccess().getOREnumLiteralDeclaration().getEnumLiteral().getInstance();
                      newLeafNode(enumLiteral_0, grammarAccess.getOrOperatorAccess().getOREnumLiteralDeclaration()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleOrOperator"


    // $ANTLR start "ruleAndOperator"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2406:1: ruleAndOperator returns [Enumerator current=null] : (enumLiteral_0= '&' ) ;
    public final Enumerator ruleAndOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;

         enterRule(); 
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2408:28: ( (enumLiteral_0= '&' ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2409:1: (enumLiteral_0= '&' )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2409:1: (enumLiteral_0= '&' )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2409:3: enumLiteral_0= '&'
            {
            enumLiteral_0=(Token)match(input,30,FollowSets000.FOLLOW_30_in_ruleAndOperator5265); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = grammarAccess.getAndOperatorAccess().getANDEnumLiteralDeclaration().getEnumLiteral().getInstance();
                      newLeafNode(enumLiteral_0, grammarAccess.getAndOperatorAccess().getANDEnumLiteralDeclaration()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleAndOperator"


    // $ANTLR start "ruleNotOperator"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2419:1: ruleNotOperator returns [Enumerator current=null] : (enumLiteral_0= '!' ) ;
    public final Enumerator ruleNotOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;

         enterRule(); 
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2421:28: ( (enumLiteral_0= '!' ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2422:1: (enumLiteral_0= '!' )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2422:1: (enumLiteral_0= '!' )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2422:3: enumLiteral_0= '!'
            {
            enumLiteral_0=(Token)match(input,31,FollowSets000.FOLLOW_31_in_ruleNotOperator5308); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = grammarAccess.getNotOperatorAccess().getNOTEnumLiteralDeclaration().getEnumLiteral().getInstance();
                      newLeafNode(enumLiteral_0, grammarAccess.getNotOperatorAccess().getNOTEnumLiteralDeclaration()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleNotOperator"


    // $ANTLR start "ruleAddOperator"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2432:1: ruleAddOperator returns [Enumerator current=null] : (enumLiteral_0= '+' ) ;
    public final Enumerator ruleAddOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;

         enterRule(); 
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2434:28: ( (enumLiteral_0= '+' ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2435:1: (enumLiteral_0= '+' )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2435:1: (enumLiteral_0= '+' )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2435:3: enumLiteral_0= '+'
            {
            enumLiteral_0=(Token)match(input,32,FollowSets000.FOLLOW_32_in_ruleAddOperator5351); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = grammarAccess.getAddOperatorAccess().getADDEnumLiteralDeclaration().getEnumLiteral().getInstance();
                      newLeafNode(enumLiteral_0, grammarAccess.getAddOperatorAccess().getADDEnumLiteralDeclaration()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleAddOperator"


    // $ANTLR start "ruleSubOperator"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2445:1: ruleSubOperator returns [Enumerator current=null] : (enumLiteral_0= '-' ) ;
    public final Enumerator ruleSubOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;

         enterRule(); 
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2447:28: ( (enumLiteral_0= '-' ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2448:1: (enumLiteral_0= '-' )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2448:1: (enumLiteral_0= '-' )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2448:3: enumLiteral_0= '-'
            {
            enumLiteral_0=(Token)match(input,33,FollowSets000.FOLLOW_33_in_ruleSubOperator5394); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = grammarAccess.getSubOperatorAccess().getSUBEnumLiteralDeclaration().getEnumLiteral().getInstance();
                      newLeafNode(enumLiteral_0, grammarAccess.getSubOperatorAccess().getSUBEnumLiteralDeclaration()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleSubOperator"


    // $ANTLR start "ruleMultOperator"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2458:1: ruleMultOperator returns [Enumerator current=null] : (enumLiteral_0= '*' ) ;
    public final Enumerator ruleMultOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;

         enterRule(); 
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2460:28: ( (enumLiteral_0= '*' ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2461:1: (enumLiteral_0= '*' )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2461:1: (enumLiteral_0= '*' )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2461:3: enumLiteral_0= '*'
            {
            enumLiteral_0=(Token)match(input,34,FollowSets000.FOLLOW_34_in_ruleMultOperator5437); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = grammarAccess.getMultOperatorAccess().getMULTEnumLiteralDeclaration().getEnumLiteral().getInstance();
                      newLeafNode(enumLiteral_0, grammarAccess.getMultOperatorAccess().getMULTEnumLiteralDeclaration()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleMultOperator"


    // $ANTLR start "ruleModOperator"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2471:1: ruleModOperator returns [Enumerator current=null] : (enumLiteral_0= '%' ) ;
    public final Enumerator ruleModOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;

         enterRule(); 
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2473:28: ( (enumLiteral_0= '%' ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2474:1: (enumLiteral_0= '%' )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2474:1: (enumLiteral_0= '%' )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2474:3: enumLiteral_0= '%'
            {
            enumLiteral_0=(Token)match(input,35,FollowSets000.FOLLOW_35_in_ruleModOperator5480); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = grammarAccess.getModOperatorAccess().getMODEnumLiteralDeclaration().getEnumLiteral().getInstance();
                      newLeafNode(enumLiteral_0, grammarAccess.getModOperatorAccess().getMODEnumLiteralDeclaration()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleModOperator"


    // $ANTLR start "ruleDivOperator"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2484:1: ruleDivOperator returns [Enumerator current=null] : (enumLiteral_0= '/' ) ;
    public final Enumerator ruleDivOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;

         enterRule(); 
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2486:28: ( (enumLiteral_0= '/' ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2487:1: (enumLiteral_0= '/' )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2487:1: (enumLiteral_0= '/' )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2487:3: enumLiteral_0= '/'
            {
            enumLiteral_0=(Token)match(input,36,FollowSets000.FOLLOW_36_in_ruleDivOperator5523); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = grammarAccess.getDivOperatorAccess().getDIVEnumLiteralDeclaration().getEnumLiteral().getInstance();
                      newLeafNode(enumLiteral_0, grammarAccess.getDivOperatorAccess().getDIVEnumLiteralDeclaration()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleDivOperator"


    // $ANTLR start "ruleValOperator"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2497:1: ruleValOperator returns [Enumerator current=null] : (enumLiteral_0= 'val' ) ;
    public final Enumerator ruleValOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;

         enterRule(); 
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2499:28: ( (enumLiteral_0= 'val' ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2500:1: (enumLiteral_0= 'val' )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2500:1: (enumLiteral_0= 'val' )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2500:3: enumLiteral_0= 'val'
            {
            enumLiteral_0=(Token)match(input,37,FollowSets000.FOLLOW_37_in_ruleValOperator5566); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = grammarAccess.getValOperatorAccess().getVALEnumLiteralDeclaration().getEnumLiteral().getInstance();
                      newLeafNode(enumLiteral_0, grammarAccess.getValOperatorAccess().getVALEnumLiteralDeclaration()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleValOperator"


    // $ANTLR start "ruleLogicalOrOperator"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2510:1: ruleLogicalOrOperator returns [Enumerator current=null] : (enumLiteral_0= '||' ) ;
    public final Enumerator ruleLogicalOrOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;

         enterRule(); 
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2512:28: ( (enumLiteral_0= '||' ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2513:1: (enumLiteral_0= '||' )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2513:1: (enumLiteral_0= '||' )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2513:3: enumLiteral_0= '||'
            {
            enumLiteral_0=(Token)match(input,38,FollowSets000.FOLLOW_38_in_ruleLogicalOrOperator5609); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = grammarAccess.getLogicalOrOperatorAccess().getOREnumLiteralDeclaration().getEnumLiteral().getInstance();
                      newLeafNode(enumLiteral_0, grammarAccess.getLogicalOrOperatorAccess().getOREnumLiteralDeclaration()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleLogicalOrOperator"


    // $ANTLR start "ruleLogicalAndOperator"
    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2523:1: ruleLogicalAndOperator returns [Enumerator current=null] : (enumLiteral_0= '&&' ) ;
    public final Enumerator ruleLogicalAndOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;

         enterRule(); 
        try {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2525:28: ( (enumLiteral_0= '&&' ) )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2526:1: (enumLiteral_0= '&&' )
            {
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2526:1: (enumLiteral_0= '&&' )
            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:2526:3: enumLiteral_0= '&&'
            {
            enumLiteral_0=(Token)match(input,39,FollowSets000.FOLLOW_39_in_ruleLogicalAndOperator5652); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = grammarAccess.getLogicalAndOperatorAccess().getANDEnumLiteralDeclaration().getEnumLiteral().getInstance();
                      newLeafNode(enumLiteral_0, grammarAccess.getLogicalAndOperatorAccess().getANDEnumLiteralDeclaration()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
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
    // $ANTLR end "ruleLogicalAndOperator"

    // $ANTLR start synpred1_InternalKExpressions
    public final void synpred1_InternalKExpressions_fragment() throws RecognitionException {   
        EObject this_BoolExpression_0 = null;


        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:121:2: (this_BoolExpression_0= ruleBoolExpression )
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:121:2: this_BoolExpression_0= ruleBoolExpression
        {
        if ( state.backtracking==0 ) {
           
          	  /* */ 
          	
        }
        pushFollow(FollowSets000.FOLLOW_ruleBoolExpression_in_synpred1_InternalKExpressions234);
        this_BoolExpression_0=ruleBoolExpression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_InternalKExpressions

    // $ANTLR start synpred4_InternalKExpressions
    public final void synpred4_InternalKExpressions_fragment() throws RecognitionException {   
        Enumerator lv_operator_2_1 = null;

        Enumerator lv_operator_2_2 = null;

        EObject lv_subExpressions_3_0 = null;


        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:211:2: ( ( () ( ( (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator ) ) ) ( (lv_subExpressions_3_0= ruleCompareOperation ) ) )* )
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:211:2: ( () ( ( (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator ) ) ) ( (lv_subExpressions_3_0= ruleCompareOperation ) ) )*
        {
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:211:2: ( () ( ( (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator ) ) ) ( (lv_subExpressions_3_0= ruleCompareOperation ) ) )*
        loop38:
        do {
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==29||LA38_0==38) ) {
                alt38=1;
            }


            switch (alt38) {
        	case 1 :
        	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:211:3: () ( ( (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator ) ) ) ( (lv_subExpressions_3_0= ruleCompareOperation ) )
        	    {
        	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:211:3: ()
        	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:212:2: 
        	    {
        	    if ( state.backtracking==0 ) {
        	       
        	      	  /* */ 
        	      	
        	    }

        	    }

        	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:220:2: ( ( (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator ) ) )
        	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:221:1: ( (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator ) )
        	    {
        	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:221:1: ( (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator ) )
        	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:222:1: (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator )
        	    {
        	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:222:1: (lv_operator_2_1= ruleOrOperator | lv_operator_2_2= ruleLogicalOrOperator )
        	    int alt37=2;
        	    int LA37_0 = input.LA(1);

        	    if ( (LA37_0==29) ) {
        	        alt37=1;
        	    }
        	    else if ( (LA37_0==38) ) {
        	        alt37=2;
        	    }
        	    else {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        NoViableAltException nvae =
        	            new NoViableAltException("", 37, 0, input);

        	        throw nvae;
        	    }
        	    switch (alt37) {
        	        case 1 :
        	            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:223:3: lv_operator_2_1= ruleOrOperator
        	            {
        	            pushFollow(FollowSets000.FOLLOW_ruleOrOperator_in_synpred4_InternalKExpressions488);
        	            lv_operator_2_1=ruleOrOperator();

        	            state._fsp--;
        	            if (state.failed) return ;

        	            }
        	            break;
        	        case 2 :
        	            // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:238:8: lv_operator_2_2= ruleLogicalOrOperator
        	            {
        	            if ( state.backtracking==0 ) {
        	               
        	              	        newCompositeNode(grammarAccess.getOrAndExpressionAccess().getOperatorLogicalOrOperatorEnumRuleCall_1_0_1_0_1()); 
        	              	    
        	            }
        	            pushFollow(FollowSets000.FOLLOW_ruleLogicalOrOperator_in_synpred4_InternalKExpressions507);
        	            lv_operator_2_2=ruleLogicalOrOperator();

        	            state._fsp--;
        	            if (state.failed) return ;

        	            }
        	            break;

        	    }


        	    }


        	    }

        	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:256:2: ( (lv_subExpressions_3_0= ruleCompareOperation ) )
        	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:257:1: (lv_subExpressions_3_0= ruleCompareOperation )
        	    {
        	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:257:1: (lv_subExpressions_3_0= ruleCompareOperation )
        	    // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:258:3: lv_subExpressions_3_0= ruleCompareOperation
        	    {
        	    if ( state.backtracking==0 ) {
        	       
        	      	        newCompositeNode(grammarAccess.getOrAndExpressionAccess().getSubExpressionsCompareOperationParserRuleCall_1_0_2_0()); 
        	      	    
        	    }
        	    pushFollow(FollowSets000.FOLLOW_ruleCompareOperation_in_synpred4_InternalKExpressions531);
        	    lv_subExpressions_3_0=ruleCompareOperation();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }
        	    break;

        	default :
        	    break loop38;
            }
        } while (true);


        }
    }
    // $ANTLR end synpred4_InternalKExpressions

    // $ANTLR start synpred7_InternalKExpressions
    public final void synpred7_InternalKExpressions_fragment() throws RecognitionException {   
        EObject this_NotOrValuedExpression_0 = null;

        Enumerator lv_operator_2_0 = null;

        EObject lv_subExpressions_3_0 = null;


        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:359:2: ( (this_NotOrValuedExpression_0= ruleNotOrValuedExpression ( () ( (lv_operator_2_0= ruleCompareOperator ) ) ( (lv_subExpressions_3_0= ruleNotOrValuedExpression ) ) ) ) )
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:359:2: (this_NotOrValuedExpression_0= ruleNotOrValuedExpression ( () ( (lv_operator_2_0= ruleCompareOperator ) ) ( (lv_subExpressions_3_0= ruleNotOrValuedExpression ) ) ) )
        {
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:359:2: (this_NotOrValuedExpression_0= ruleNotOrValuedExpression ( () ( (lv_operator_2_0= ruleCompareOperator ) ) ( (lv_subExpressions_3_0= ruleNotOrValuedExpression ) ) ) )
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:360:2: this_NotOrValuedExpression_0= ruleNotOrValuedExpression ( () ( (lv_operator_2_0= ruleCompareOperator ) ) ( (lv_subExpressions_3_0= ruleNotOrValuedExpression ) ) )
        {
        if ( state.backtracking==0 ) {
           
          	  /* */ 
          	
        }
        pushFollow(FollowSets000.FOLLOW_ruleNotOrValuedExpression_in_synpred7_InternalKExpressions718);
        this_NotOrValuedExpression_0=ruleNotOrValuedExpression();

        state._fsp--;
        if (state.failed) return ;
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:371:1: ( () ( (lv_operator_2_0= ruleCompareOperator ) ) ( (lv_subExpressions_3_0= ruleNotOrValuedExpression ) ) )
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:371:2: () ( (lv_operator_2_0= ruleCompareOperator ) ) ( (lv_subExpressions_3_0= ruleNotOrValuedExpression ) )
        {
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:371:2: ()
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:372:2: 
        {
        if ( state.backtracking==0 ) {
           
          	  /* */ 
          	
        }

        }

        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:380:2: ( (lv_operator_2_0= ruleCompareOperator ) )
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:381:1: (lv_operator_2_0= ruleCompareOperator )
        {
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:381:1: (lv_operator_2_0= ruleCompareOperator )
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:382:3: lv_operator_2_0= ruleCompareOperator
        {
        if ( state.backtracking==0 ) {
           
          	        newCompositeNode(grammarAccess.getCompareOperationAccess().getOperatorCompareOperatorEnumRuleCall_0_1_1_0()); 
          	    
        }
        pushFollow(FollowSets000.FOLLOW_ruleCompareOperator_in_synpred7_InternalKExpressions751);
        lv_operator_2_0=ruleCompareOperator();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:398:2: ( (lv_subExpressions_3_0= ruleNotOrValuedExpression ) )
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:399:1: (lv_subExpressions_3_0= ruleNotOrValuedExpression )
        {
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:399:1: (lv_subExpressions_3_0= ruleNotOrValuedExpression )
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:400:3: lv_subExpressions_3_0= ruleNotOrValuedExpression
        {
        if ( state.backtracking==0 ) {
           
          	        newCompositeNode(grammarAccess.getCompareOperationAccess().getSubExpressionsNotOrValuedExpressionParserRuleCall_0_1_2_0()); 
          	    
        }
        pushFollow(FollowSets000.FOLLOW_ruleNotOrValuedExpression_in_synpred7_InternalKExpressions772);
        lv_subExpressions_3_0=ruleNotOrValuedExpression();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred7_InternalKExpressions

    // $ANTLR start synpred8_InternalKExpressions
    public final void synpred8_InternalKExpressions_fragment() throws RecognitionException {   
        EObject this_ValuedExpression_0 = null;


        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:451:2: (this_ValuedExpression_0= ruleValuedExpression )
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:451:2: this_ValuedExpression_0= ruleValuedExpression
        {
        if ( state.backtracking==0 ) {
           
          	  /* */ 
          	
        }
        pushFollow(FollowSets000.FOLLOW_ruleValuedExpression_in_synpred8_InternalKExpressions900);
        this_ValuedExpression_0=ruleValuedExpression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred8_InternalKExpressions

    // $ANTLR start synpred20_InternalKExpressions
    public final void synpred20_InternalKExpressions_fragment() throws RecognitionException {   
        EObject this_FloatValue_1 = null;


        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1171:2: (this_FloatValue_1= ruleFloatValue )
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1171:2: this_FloatValue_1= ruleFloatValue
        {
        if ( state.backtracking==0 ) {
           
          	  /* */ 
          	
        }
        pushFollow(FollowSets000.FOLLOW_ruleFloatValue_in_synpred20_InternalKExpressions2433);
        this_FloatValue_1=ruleFloatValue();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred20_InternalKExpressions

    // $ANTLR start synpred21_InternalKExpressions
    public final void synpred21_InternalKExpressions_fragment() throws RecognitionException {   
        EObject this_DoubleValue_2 = null;


        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1184:2: (this_DoubleValue_2= ruleDoubleValue )
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1184:2: this_DoubleValue_2= ruleDoubleValue
        {
        if ( state.backtracking==0 ) {
           
          	  /* */ 
          	
        }
        pushFollow(FollowSets000.FOLLOW_ruleDoubleValue_in_synpred21_InternalKExpressions2463);
        this_DoubleValue_2=ruleDoubleValue();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred21_InternalKExpressions

    // $ANTLR start synpred22_InternalKExpressions
    public final void synpred22_InternalKExpressions_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject this_ValuedExpression_4 = null;


        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1196:6: ( (otherlv_3= '(' this_ValuedExpression_4= ruleValuedExpression otherlv_5= ')' ) )
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1196:6: (otherlv_3= '(' this_ValuedExpression_4= ruleValuedExpression otherlv_5= ')' )
        {
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1196:6: (otherlv_3= '(' this_ValuedExpression_4= ruleValuedExpression otherlv_5= ')' )
        // ../de.cau.cs.kieler.core.kexpressions/src-gen/de/cau/cs/kieler/core/kexpressions/parser/antlr/internal/InternalKExpressions.g:1196:8: otherlv_3= '(' this_ValuedExpression_4= ruleValuedExpression otherlv_5= ')'
        {
        otherlv_3=(Token)match(input,16,FollowSets000.FOLLOW_16_in_synpred22_InternalKExpressions2481); if (state.failed) return ;
        pushFollow(FollowSets000.FOLLOW_ruleValuedExpression_in_synpred22_InternalKExpressions2506);
        this_ValuedExpression_4=ruleValuedExpression();

        state._fsp--;
        if (state.failed) return ;
        otherlv_5=(Token)match(input,17,FollowSets000.FOLLOW_17_in_synpred22_InternalKExpressions2517); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred22_InternalKExpressions

    // Delegated rules

    public final boolean synpred7_InternalKExpressions() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_InternalKExpressions_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred21_InternalKExpressions() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred21_InternalKExpressions_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_InternalKExpressions() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_InternalKExpressions_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred22_InternalKExpressions() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred22_InternalKExpressions_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_InternalKExpressions() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_InternalKExpressions_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred20_InternalKExpressions() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred20_InternalKExpressions_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_InternalKExpressions() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_InternalKExpressions_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA1 dfa1 = new DFA1(this);
    protected DFA7 dfa7 = new DFA7(this);
    protected DFA8 dfa8 = new DFA8(this);
    protected DFA17 dfa17 = new DFA17(this);
    protected DFA20 dfa20 = new DFA20(this);
    static final String DFA1_eotS =
        "\14\uffff";
    static final String DFA1_eofS =
        "\14\uffff";
    static final String DFA1_minS =
        "\1\4\11\0\2\uffff";
    static final String DFA1_maxS =
        "\1\45\11\0\2\uffff";
    static final String DFA1_acceptS =
        "\12\uffff\1\1\1\2";
    static final String DFA1_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\2\uffff}>";
    static final String[] DFA1_transitionS = {
            "\1\10\1\11\1\2\1\3\1\5\7\uffff\1\4\13\uffff\1\6\2\uffff\1\12"+
            "\1\uffff\1\1\3\uffff\1\7",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA1_eot = DFA.unpackEncodedString(DFA1_eotS);
    static final short[] DFA1_eof = DFA.unpackEncodedString(DFA1_eofS);
    static final char[] DFA1_min = DFA.unpackEncodedStringToUnsignedChars(DFA1_minS);
    static final char[] DFA1_max = DFA.unpackEncodedStringToUnsignedChars(DFA1_maxS);
    static final short[] DFA1_accept = DFA.unpackEncodedString(DFA1_acceptS);
    static final short[] DFA1_special = DFA.unpackEncodedString(DFA1_specialS);
    static final short[][] DFA1_transition;

    static {
        int numStates = DFA1_transitionS.length;
        DFA1_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA1_transition[i] = DFA.unpackEncodedString(DFA1_transitionS[i]);
        }
    }

    class DFA1 extends DFA {

        public DFA1(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 1;
            this.eot = DFA1_eot;
            this.eof = DFA1_eof;
            this.min = DFA1_min;
            this.max = DFA1_max;
            this.accept = DFA1_accept;
            this.special = DFA1_special;
            this.transition = DFA1_transition;
        }
        public String getDescription() {
            return "120:1: (this_BoolExpression_0= ruleBoolExpression | this_ValuedExpression_1= ruleValuedExpression )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA1_1 = input.LA(1);

                         
                        int index1_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalKExpressions()) ) {s = 10;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index1_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA1_2 = input.LA(1);

                         
                        int index1_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalKExpressions()) ) {s = 10;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index1_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA1_3 = input.LA(1);

                         
                        int index1_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalKExpressions()) ) {s = 10;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index1_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA1_4 = input.LA(1);

                         
                        int index1_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalKExpressions()) ) {s = 10;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index1_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA1_5 = input.LA(1);

                         
                        int index1_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalKExpressions()) ) {s = 10;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index1_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA1_6 = input.LA(1);

                         
                        int index1_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalKExpressions()) ) {s = 10;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index1_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA1_7 = input.LA(1);

                         
                        int index1_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalKExpressions()) ) {s = 10;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index1_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA1_8 = input.LA(1);

                         
                        int index1_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalKExpressions()) ) {s = 10;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index1_8);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA1_9 = input.LA(1);

                         
                        int index1_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalKExpressions()) ) {s = 10;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index1_9);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 1, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA7_eotS =
        "\14\uffff";
    static final String DFA7_eofS =
        "\14\uffff";
    static final String DFA7_minS =
        "\1\4\3\uffff\7\0\1\uffff";
    static final String DFA7_maxS =
        "\1\45\3\uffff\7\0\1\uffff";
    static final String DFA7_acceptS =
        "\1\uffff\1\1\11\uffff\1\2";
    static final String DFA7_specialS =
        "\4\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\uffff}>";
    static final String[] DFA7_transitionS = {
            "\1\10\1\11\2\1\1\5\7\uffff\1\4\13\uffff\1\6\2\uffff\1\12\1"+
            "\uffff\1\1\3\uffff\1\7",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            ""
    };

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "359:1: ( (this_NotOrValuedExpression_0= ruleNotOrValuedExpression ( () ( (lv_operator_2_0= ruleCompareOperator ) ) ( (lv_subExpressions_3_0= ruleNotOrValuedExpression ) ) ) ) | this_NotExpression_4= ruleNotExpression )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA7_4 = input.LA(1);

                         
                        int index7_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalKExpressions()) ) {s = 1;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index7_4);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA7_5 = input.LA(1);

                         
                        int index7_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalKExpressions()) ) {s = 1;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index7_5);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA7_6 = input.LA(1);

                         
                        int index7_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalKExpressions()) ) {s = 1;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index7_6);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA7_7 = input.LA(1);

                         
                        int index7_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalKExpressions()) ) {s = 1;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index7_7);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA7_8 = input.LA(1);

                         
                        int index7_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalKExpressions()) ) {s = 1;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index7_8);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA7_9 = input.LA(1);

                         
                        int index7_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalKExpressions()) ) {s = 1;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index7_9);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA7_10 = input.LA(1);

                         
                        int index7_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_InternalKExpressions()) ) {s = 1;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index7_10);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 7, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA8_eotS =
        "\13\uffff";
    static final String DFA8_eofS =
        "\13\uffff";
    static final String DFA8_minS =
        "\1\4\3\uffff\6\0\1\uffff";
    static final String DFA8_maxS =
        "\1\45\3\uffff\6\0\1\uffff";
    static final String DFA8_acceptS =
        "\1\uffff\1\1\10\uffff\1\2";
    static final String DFA8_specialS =
        "\4\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\10\1\11\2\1\1\5\7\uffff\1\4\13\uffff\1\6\2\uffff\1\12\1"+
            "\uffff\1\1\3\uffff\1\7",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            ""
    };

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "450:1: (this_ValuedExpression_0= ruleValuedExpression | this_NotExpression_1= ruleNotExpression )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA8_4 = input.LA(1);

                         
                        int index8_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalKExpressions()) ) {s = 1;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index8_4);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA8_5 = input.LA(1);

                         
                        int index8_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalKExpressions()) ) {s = 1;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index8_5);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA8_6 = input.LA(1);

                         
                        int index8_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalKExpressions()) ) {s = 1;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index8_6);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA8_7 = input.LA(1);

                         
                        int index8_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalKExpressions()) ) {s = 1;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index8_7);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA8_8 = input.LA(1);

                         
                        int index8_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalKExpressions()) ) {s = 1;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index8_8);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA8_9 = input.LA(1);

                         
                        int index8_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalKExpressions()) ) {s = 1;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index8_9);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 8, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA17_eotS =
        "\14\uffff";
    static final String DFA17_eofS =
        "\14\uffff";
    static final String DFA17_minS =
        "\1\4\1\uffff\2\0\10\uffff";
    static final String DFA17_maxS =
        "\1\45\1\uffff\2\0\10\uffff";
    static final String DFA17_acceptS =
        "\1\uffff\1\1\2\uffff\1\5\4\uffff\1\2\1\3\1\4";
    static final String DFA17_specialS =
        "\2\uffff\1\0\1\1\10\uffff}>";
    static final String[] DFA17_transitionS = {
            "\2\4\1\1\1\2\1\4\7\uffff\1\3\13\uffff\1\4\10\uffff\1\4",
            "",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA17_eot = DFA.unpackEncodedString(DFA17_eotS);
    static final short[] DFA17_eof = DFA.unpackEncodedString(DFA17_eofS);
    static final char[] DFA17_min = DFA.unpackEncodedStringToUnsignedChars(DFA17_minS);
    static final char[] DFA17_max = DFA.unpackEncodedStringToUnsignedChars(DFA17_maxS);
    static final short[] DFA17_accept = DFA.unpackEncodedString(DFA17_acceptS);
    static final short[] DFA17_special = DFA.unpackEncodedString(DFA17_specialS);
    static final short[][] DFA17_transition;

    static {
        int numStates = DFA17_transitionS.length;
        DFA17_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA17_transition[i] = DFA.unpackEncodedString(DFA17_transitionS[i]);
        }
    }

    class DFA17 extends DFA {

        public DFA17(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 17;
            this.eot = DFA17_eot;
            this.eof = DFA17_eof;
            this.min = DFA17_min;
            this.max = DFA17_max;
            this.accept = DFA17_accept;
            this.special = DFA17_special;
            this.transition = DFA17_transition;
        }
        public String getDescription() {
            return "1157:1: (this_IntValue_0= ruleIntValue | this_FloatValue_1= ruleFloatValue | this_DoubleValue_2= ruleDoubleValue | (otherlv_3= '(' this_ValuedExpression_4= ruleValuedExpression otherlv_5= ')' ) | this_AtomicExpression_6= ruleAtomicExpression )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA17_2 = input.LA(1);

                         
                        int index17_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred20_InternalKExpressions()) ) {s = 9;}

                        else if ( (synpred21_InternalKExpressions()) ) {s = 10;}

                         
                        input.seek(index17_2);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA17_3 = input.LA(1);

                         
                        int index17_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred22_InternalKExpressions()) ) {s = 11;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index17_3);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 17, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA20_eotS =
        "\14\uffff";
    static final String DFA20_eofS =
        "\3\uffff\1\6\7\uffff\1\6";
    static final String DFA20_minS =
        "\1\11\1\uffff\3\4\6\uffff\1\4";
    static final String DFA20_maxS =
        "\1\22\1\uffff\1\4\1\25\1\4\6\uffff\1\25";
    static final String DFA20_acceptS =
        "\1\uffff\1\1\3\uffff\1\4\1\2\1\3\1\7\1\6\1\5\1\uffff";
    static final String DFA20_specialS =
        "\14\uffff}>";
    static final String[] DFA20_transitionS = {
            "\1\1\10\uffff\1\2",
            "",
            "\1\3",
            "\1\7\1\uffff\1\11\1\10\1\12\1\6\1\7\5\uffff\3\6\1\5\1\uffff"+
            "\1\4",
            "\1\13",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\7\1\uffff\1\11\1\10\1\12\1\6\1\7\5\uffff\3\6\1\5\1\uffff"+
            "\1\4"
    };

    static final short[] DFA20_eot = DFA.unpackEncodedString(DFA20_eotS);
    static final short[] DFA20_eof = DFA.unpackEncodedString(DFA20_eofS);
    static final char[] DFA20_min = DFA.unpackEncodedStringToUnsignedChars(DFA20_minS);
    static final char[] DFA20_max = DFA.unpackEncodedStringToUnsignedChars(DFA20_maxS);
    static final short[] DFA20_accept = DFA.unpackEncodedString(DFA20_acceptS);
    static final short[] DFA20_special = DFA.unpackEncodedString(DFA20_specialS);
    static final short[][] DFA20_transition;

    static {
        int numStates = DFA20_transitionS.length;
        DFA20_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA20_transition[i] = DFA.unpackEncodedString(DFA20_transitionS[i]);
        }
    }

    class DFA20 extends DFA {

        public DFA20(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 20;
            this.eot = DFA20_eot;
            this.eof = DFA20_eof;
            this.min = DFA20_min;
            this.max = DFA20_max;
            this.accept = DFA20_accept;
            this.special = DFA20_special;
            this.transition = DFA20_transition;
        }
        public String getDescription() {
            return "1589:1: (this_CommentAnnotation_0= ruleCommentAnnotation | this_TagAnnotation_1= ruleTagAnnotation | this_KeyStringValueAnnotation_2= ruleKeyStringValueAnnotation | this_TypedKeyStringValueAnnotation_3= ruleTypedKeyStringValueAnnotation | this_KeyBooleanValueAnnotation_4= ruleKeyBooleanValueAnnotation | this_KeyIntValueAnnotation_5= ruleKeyIntValueAnnotation | this_KeyFloatValueAnnotation_6= ruleKeyFloatValueAnnotation )";
        }
    }
 

    
    private static class FollowSets000 {
        public static final BitSet FOLLOW_ruleRoot_in_entryRuleRoot81 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleRoot91 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleExpression_in_ruleRoot140 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleExpression_in_entryRuleExpression174 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleExpression184 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleBoolExpression_in_ruleExpression234 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleValuedExpression_in_ruleExpression264 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleBoolExpression_in_entryRuleBoolExpression299 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleBoolExpression309 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleOrAndExpression_in_ruleBoolExpression358 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleOrAndExpression_in_entryRuleOrAndExpression392 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleOrAndExpression402 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleCompareOperation_in_ruleOrAndExpression452 = new BitSet(new long[]{0x000000C060000002L});
        public static final BitSet FOLLOW_ruleOrOperator_in_ruleOrAndExpression488 = new BitSet(new long[]{0x00000022900101F0L});
        public static final BitSet FOLLOW_ruleLogicalOrOperator_in_ruleOrAndExpression507 = new BitSet(new long[]{0x00000022900101F0L});
        public static final BitSet FOLLOW_ruleCompareOperation_in_ruleOrAndExpression531 = new BitSet(new long[]{0x0000004020000002L});
        public static final BitSet FOLLOW_ruleAndOperator_in_ruleOrAndExpression575 = new BitSet(new long[]{0x00000022900101F0L});
        public static final BitSet FOLLOW_ruleLogicalAndOperator_in_ruleOrAndExpression594 = new BitSet(new long[]{0x00000022900101F0L});
        public static final BitSet FOLLOW_ruleCompareOperation_in_ruleOrAndExpression618 = new BitSet(new long[]{0x0000008040000002L});
        public static final BitSet FOLLOW_ruleCompareOperation_in_entryRuleCompareOperation657 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleCompareOperation667 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleNotOrValuedExpression_in_ruleCompareOperation718 = new BitSet(new long[]{0x000000000FC00000L});
        public static final BitSet FOLLOW_ruleCompareOperator_in_ruleCompareOperation751 = new BitSet(new long[]{0x00000022900101F0L});
        public static final BitSet FOLLOW_ruleNotOrValuedExpression_in_ruleCompareOperation772 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleNotExpression_in_ruleCompareOperation805 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleNotOrValuedExpression_in_entryRuleNotOrValuedExpression840 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleNotOrValuedExpression850 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleValuedExpression_in_ruleNotOrValuedExpression900 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleNotExpression_in_ruleNotOrValuedExpression930 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleNotExpression_in_entryRuleNotExpression965 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleNotExpression975 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleNotOperator_in_ruleNotExpression1034 = new BitSet(new long[]{0x00000022900101F0L});
        public static final BitSet FOLLOW_ruleNotExpression_in_ruleNotExpression1055 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleAtomicExpression_in_ruleNotExpression1087 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleValuedExpression_in_entryRuleValuedExpression1122 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleValuedExpression1132 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleAddExpression_in_ruleValuedExpression1181 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleAddExpression_in_entryRuleAddExpression1215 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleAddExpression1225 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleSubExpression_in_ruleAddExpression1275 = new BitSet(new long[]{0x0000000100000002L});
        public static final BitSet FOLLOW_ruleAddOperator_in_ruleAddExpression1308 = new BitSet(new long[]{0x00000022100101F0L});
        public static final BitSet FOLLOW_ruleSubExpression_in_ruleAddExpression1329 = new BitSet(new long[]{0x0000000100000002L});
        public static final BitSet FOLLOW_ruleSubExpression_in_entryRuleSubExpression1367 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleSubExpression1377 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMultExpression_in_ruleSubExpression1427 = new BitSet(new long[]{0x0000000200000002L});
        public static final BitSet FOLLOW_ruleSubOperator_in_ruleSubExpression1460 = new BitSet(new long[]{0x00000022100101F0L});
        public static final BitSet FOLLOW_ruleMultExpression_in_ruleSubExpression1481 = new BitSet(new long[]{0x0000000200000002L});
        public static final BitSet FOLLOW_ruleMultExpression_in_entryRuleMultExpression1519 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleMultExpression1529 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleDivExpression_in_ruleMultExpression1579 = new BitSet(new long[]{0x0000000400000002L});
        public static final BitSet FOLLOW_ruleMultOperator_in_ruleMultExpression1612 = new BitSet(new long[]{0x00000022100101F0L});
        public static final BitSet FOLLOW_ruleDivExpression_in_ruleMultExpression1633 = new BitSet(new long[]{0x0000000400000002L});
        public static final BitSet FOLLOW_ruleDivExpression_in_entryRuleDivExpression1671 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleDivExpression1681 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleModExpression_in_ruleDivExpression1731 = new BitSet(new long[]{0x0000001000000002L});
        public static final BitSet FOLLOW_ruleDivOperator_in_ruleDivExpression1764 = new BitSet(new long[]{0x00000022100101F0L});
        public static final BitSet FOLLOW_ruleModExpression_in_ruleDivExpression1785 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleModExpression_in_entryRuleModExpression1823 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleModExpression1833 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleNegExpression_in_ruleModExpression1883 = new BitSet(new long[]{0x0000000800000002L});
        public static final BitSet FOLLOW_ruleModOperator_in_ruleModExpression1916 = new BitSet(new long[]{0x00000022100101F0L});
        public static final BitSet FOLLOW_ruleAtomicValuedExpression_in_ruleModExpression1937 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleNegExpression_in_entryRuleNegExpression1975 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleNegExpression1985 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleSubOperator_in_ruleNegExpression2044 = new BitSet(new long[]{0x00000022100101F0L});
        public static final BitSet FOLLOW_ruleNegExpression_in_ruleNegExpression2065 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleAtomicValuedExpression_in_ruleNegExpression2097 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleAtomicExpression_in_entryRuleAtomicExpression2132 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleAtomicExpression2142 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleBoolValue_in_ruleAtomicExpression2192 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleValuedObjectTestExpression_in_ruleAtomicExpression2222 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_16_in_ruleAtomicExpression2240 = new BitSet(new long[]{0x00000022900101F0L});
        public static final BitSet FOLLOW_ruleBoolExpression_in_ruleAtomicExpression2265 = new BitSet(new long[]{0x0000000000020000L});
        public static final BitSet FOLLOW_17_in_ruleAtomicExpression2276 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleTextExpression_in_ruleAtomicExpression2308 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleAtomicValuedExpression_in_entryRuleAtomicValuedExpression2343 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleAtomicValuedExpression2353 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleIntValue_in_ruleAtomicValuedExpression2403 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleFloatValue_in_ruleAtomicValuedExpression2433 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleDoubleValue_in_ruleAtomicValuedExpression2463 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_16_in_ruleAtomicValuedExpression2481 = new BitSet(new long[]{0x00000022100101F0L});
        public static final BitSet FOLLOW_ruleValuedExpression_in_ruleAtomicValuedExpression2506 = new BitSet(new long[]{0x0000000000020000L});
        public static final BitSet FOLLOW_17_in_ruleAtomicValuedExpression2517 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleAtomicExpression_in_ruleAtomicValuedExpression2549 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleValuedObjectTestExpression_in_entryRuleValuedObjectTestExpression2584 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleValuedObjectTestExpression2594 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rulePreOperator_in_ruleValuedObjectTestExpression2655 = new BitSet(new long[]{0x0000000000010000L});
        public static final BitSet FOLLOW_ruleValOperator_in_ruleValuedObjectTestExpression2674 = new BitSet(new long[]{0x0000000000010000L});
        public static final BitSet FOLLOW_16_in_ruleValuedObjectTestExpression2689 = new BitSet(new long[]{0x0000002010000010L});
        public static final BitSet FOLLOW_ruleValuedObjectTestExpression_in_ruleValuedObjectTestExpression2710 = new BitSet(new long[]{0x0000000000020000L});
        public static final BitSet FOLLOW_17_in_ruleValuedObjectTestExpression2722 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleValuedObjectReference_in_ruleValuedObjectTestExpression2754 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleValuedObjectReference_in_entryRuleValuedObjectReference2789 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleValuedObjectReference2799 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_ID_in_ruleValuedObjectReference2847 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleTextExpression_in_entryRuleTextExpression2882 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleTextExpression2892 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_HOSTCODE_in_ruleTextExpression2933 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleIntValue_in_entryRuleIntValue2973 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleIntValue2983 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_INT_in_ruleIntValue3024 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleFloatValue_in_entryRuleFloatValue3064 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleFloatValue3074 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_FLOAT_in_ruleFloatValue3115 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleBoolValue_in_entryRuleBoolValue3155 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleBoolValue3165 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_BOOLEAN_in_ruleBoolValue3206 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleDoubleValue_in_entryRuleDoubleValue3246 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleDoubleValue3256 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_FLOAT_in_ruleDoubleValue3297 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleAnnotation_in_entryRuleAnnotation3339 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleAnnotation3349 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleCommentAnnotation_in_ruleAnnotation3399 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleTagAnnotation_in_ruleAnnotation3429 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleKeyStringValueAnnotation_in_ruleAnnotation3459 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleTypedKeyStringValueAnnotation_in_ruleAnnotation3489 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleKeyBooleanValueAnnotation_in_ruleAnnotation3519 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleKeyIntValueAnnotation_in_ruleAnnotation3549 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleKeyFloatValueAnnotation_in_ruleAnnotation3579 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleCommentAnnotation_in_entryRuleCommentAnnotation3616 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleCommentAnnotation3626 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_COMMENT_ANNOTATION_in_ruleCommentAnnotation3667 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleTagAnnotation_in_entryRuleTagAnnotation3707 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleTagAnnotation3717 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_18_in_ruleTagAnnotation3754 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_ruleExtendedID_in_ruleTagAnnotation3775 = new BitSet(new long[]{0x0000000000010002L});
        public static final BitSet FOLLOW_16_in_ruleTagAnnotation3788 = new BitSet(new long[]{0x0000000000060200L});
        public static final BitSet FOLLOW_ruleAnnotation_in_ruleTagAnnotation3809 = new BitSet(new long[]{0x0000000000060200L});
        public static final BitSet FOLLOW_17_in_ruleTagAnnotation3822 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleKeyStringValueAnnotation_in_entryRuleKeyStringValueAnnotation3860 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleKeyStringValueAnnotation3870 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_18_in_ruleKeyStringValueAnnotation3907 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_ruleExtendedID_in_ruleKeyStringValueAnnotation3928 = new BitSet(new long[]{0x0000000000000410L});
        public static final BitSet FOLLOW_ruleEString_in_ruleKeyStringValueAnnotation3949 = new BitSet(new long[]{0x0000000000010002L});
        public static final BitSet FOLLOW_16_in_ruleKeyStringValueAnnotation3962 = new BitSet(new long[]{0x0000000000060200L});
        public static final BitSet FOLLOW_ruleAnnotation_in_ruleKeyStringValueAnnotation3983 = new BitSet(new long[]{0x0000000000060200L});
        public static final BitSet FOLLOW_17_in_ruleKeyStringValueAnnotation3996 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleTypedKeyStringValueAnnotation_in_entryRuleTypedKeyStringValueAnnotation4034 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleTypedKeyStringValueAnnotation4044 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_18_in_ruleTypedKeyStringValueAnnotation4081 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_ruleExtendedID_in_ruleTypedKeyStringValueAnnotation4102 = new BitSet(new long[]{0x0000000000080000L});
        public static final BitSet FOLLOW_19_in_ruleTypedKeyStringValueAnnotation4114 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_ruleExtendedID_in_ruleTypedKeyStringValueAnnotation4135 = new BitSet(new long[]{0x0000000000100000L});
        public static final BitSet FOLLOW_20_in_ruleTypedKeyStringValueAnnotation4147 = new BitSet(new long[]{0x0000000000000410L});
        public static final BitSet FOLLOW_ruleEString_in_ruleTypedKeyStringValueAnnotation4168 = new BitSet(new long[]{0x0000000000010002L});
        public static final BitSet FOLLOW_16_in_ruleTypedKeyStringValueAnnotation4181 = new BitSet(new long[]{0x0000000000060200L});
        public static final BitSet FOLLOW_ruleAnnotation_in_ruleTypedKeyStringValueAnnotation4202 = new BitSet(new long[]{0x0000000000060200L});
        public static final BitSet FOLLOW_17_in_ruleTypedKeyStringValueAnnotation4215 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleKeyBooleanValueAnnotation_in_entryRuleKeyBooleanValueAnnotation4253 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleKeyBooleanValueAnnotation4263 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_18_in_ruleKeyBooleanValueAnnotation4300 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_ruleExtendedID_in_ruleKeyBooleanValueAnnotation4321 = new BitSet(new long[]{0x0000000000000100L});
        public static final BitSet FOLLOW_RULE_BOOLEAN_in_ruleKeyBooleanValueAnnotation4338 = new BitSet(new long[]{0x0000000000010002L});
        public static final BitSet FOLLOW_16_in_ruleKeyBooleanValueAnnotation4356 = new BitSet(new long[]{0x0000000000060200L});
        public static final BitSet FOLLOW_ruleAnnotation_in_ruleKeyBooleanValueAnnotation4377 = new BitSet(new long[]{0x0000000000060200L});
        public static final BitSet FOLLOW_17_in_ruleKeyBooleanValueAnnotation4390 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleKeyIntValueAnnotation_in_entryRuleKeyIntValueAnnotation4428 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleKeyIntValueAnnotation4438 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_18_in_ruleKeyIntValueAnnotation4475 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_ruleExtendedID_in_ruleKeyIntValueAnnotation4496 = new BitSet(new long[]{0x0000000000000040L});
        public static final BitSet FOLLOW_RULE_INT_in_ruleKeyIntValueAnnotation4513 = new BitSet(new long[]{0x0000000000010002L});
        public static final BitSet FOLLOW_16_in_ruleKeyIntValueAnnotation4531 = new BitSet(new long[]{0x0000000000060200L});
        public static final BitSet FOLLOW_ruleAnnotation_in_ruleKeyIntValueAnnotation4552 = new BitSet(new long[]{0x0000000000060200L});
        public static final BitSet FOLLOW_17_in_ruleKeyIntValueAnnotation4565 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleKeyFloatValueAnnotation_in_entryRuleKeyFloatValueAnnotation4603 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleKeyFloatValueAnnotation4613 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_18_in_ruleKeyFloatValueAnnotation4650 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_ruleExtendedID_in_ruleKeyFloatValueAnnotation4671 = new BitSet(new long[]{0x0000000000000080L});
        public static final BitSet FOLLOW_RULE_FLOAT_in_ruleKeyFloatValueAnnotation4688 = new BitSet(new long[]{0x0000000000010002L});
        public static final BitSet FOLLOW_16_in_ruleKeyFloatValueAnnotation4706 = new BitSet(new long[]{0x0000000000060200L});
        public static final BitSet FOLLOW_ruleAnnotation_in_ruleKeyFloatValueAnnotation4727 = new BitSet(new long[]{0x0000000000060200L});
        public static final BitSet FOLLOW_17_in_ruleKeyFloatValueAnnotation4740 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleEString_in_entryRuleEString4781 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleEString4792 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_STRING_in_ruleEString4832 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_ID_in_ruleEString4858 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleExtendedID_in_entryRuleExtendedID4904 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleExtendedID4915 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_ID_in_ruleExtendedID4955 = new BitSet(new long[]{0x0000000000200002L});
        public static final BitSet FOLLOW_21_in_ruleExtendedID4974 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_RULE_ID_in_ruleExtendedID4989 = new BitSet(new long[]{0x0000000000200002L});
        public static final BitSet FOLLOW_22_in_ruleCompareOperator5050 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_23_in_ruleCompareOperator5067 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_24_in_ruleCompareOperator5084 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_25_in_ruleCompareOperator5101 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_26_in_ruleCompareOperator5118 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_27_in_ruleCompareOperator5135 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_28_in_rulePreOperator5179 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_29_in_ruleOrOperator5222 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_30_in_ruleAndOperator5265 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_31_in_ruleNotOperator5308 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_32_in_ruleAddOperator5351 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_33_in_ruleSubOperator5394 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_34_in_ruleMultOperator5437 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_35_in_ruleModOperator5480 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_36_in_ruleDivOperator5523 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_37_in_ruleValOperator5566 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_38_in_ruleLogicalOrOperator5609 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_39_in_ruleLogicalAndOperator5652 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleBoolExpression_in_synpred1_InternalKExpressions234 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleOrOperator_in_synpred4_InternalKExpressions488 = new BitSet(new long[]{0x00000022900101F0L});
        public static final BitSet FOLLOW_ruleLogicalOrOperator_in_synpred4_InternalKExpressions507 = new BitSet(new long[]{0x00000022900101F0L});
        public static final BitSet FOLLOW_ruleCompareOperation_in_synpred4_InternalKExpressions531 = new BitSet(new long[]{0x0000004020000002L});
        public static final BitSet FOLLOW_ruleNotOrValuedExpression_in_synpred7_InternalKExpressions718 = new BitSet(new long[]{0x000000000FC00000L});
        public static final BitSet FOLLOW_ruleCompareOperator_in_synpred7_InternalKExpressions751 = new BitSet(new long[]{0x00000022900101F0L});
        public static final BitSet FOLLOW_ruleNotOrValuedExpression_in_synpred7_InternalKExpressions772 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleValuedExpression_in_synpred8_InternalKExpressions900 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleFloatValue_in_synpred20_InternalKExpressions2433 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleDoubleValue_in_synpred21_InternalKExpressions2463 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_16_in_synpred22_InternalKExpressions2481 = new BitSet(new long[]{0x00000022100101F0L});
        public static final BitSet FOLLOW_ruleValuedExpression_in_synpred22_InternalKExpressions2506 = new BitSet(new long[]{0x0000000000020000L});
        public static final BitSet FOLLOW_17_in_synpred22_InternalKExpressions2517 = new BitSet(new long[]{0x0000000000000002L});
    }


}