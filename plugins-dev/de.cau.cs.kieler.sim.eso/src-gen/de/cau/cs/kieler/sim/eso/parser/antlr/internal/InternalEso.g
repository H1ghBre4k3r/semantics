/*
* generated by Xtext
*/
grammar InternalEso;

options {
	superClass=AbstractInternalAntlrParser;
	backtrack=true;
	
}

@lexer::header {
package de.cau.cs.kieler.sim.eso.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

@parser::header {
package de.cau.cs.kieler.sim.eso.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import de.cau.cs.kieler.sim.eso.services.EsoGrammarAccess;

}

@parser::members {

/*
  This grammar contains a lot of empty actions to work around a bug in ANTLR.
  Otherwise the ANTLR tool will create synpreds that cannot be compiled in some rare cases.
*/
 
 	private EsoGrammarAccess grammarAccess;
 	
    public InternalEsoParser(TokenStream input, EsoGrammarAccess grammarAccess) {
        this(input);
        this.grammarAccess = grammarAccess;
        registerRules(grammarAccess.getGrammar());
    }
    
    @Override
    protected String getFirstRuleName() {
    	return "tracelist";	
   	}
   	
   	@Override
   	protected EsoGrammarAccess getGrammarAccess() {
   		return grammarAccess;
   	}
}

@rulecatch { 
    catch (RecognitionException re) { 
        recover(input,re); 
        appendSkippedTokens();
    } 
}




// Entry rule entryRuletracelist
entryRuletracelist returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getTracelistRule()); }
	 iv_ruletracelist=ruletracelist 
	 { $current=$iv_ruletracelist.current; } 
	 EOF 
;

// Rule tracelist
ruletracelist returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		{ 
	        newCompositeNode(grammarAccess.getTracelistAccess().getTracesTraceParserRuleCall_0()); 
	    }
		lv_traces_0_0=ruletrace		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getTracelistRule());
	        }
       		add(
       			$current, 
       			"traces",
        		lv_traces_0_0, 
        		"trace");
	        afterParserOrEnumRuleCall();
	    }

)
)+
;





// Entry rule entryRuletrace
entryRuletrace returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getTraceRule()); }
	 iv_ruletrace=ruletrace 
	 { $current=$iv_ruletrace.current; } 
	 EOF 
;

// Rule trace
ruletrace returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='!' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getTraceAccess().getExclamationMarkKeyword_0());
    }
	otherlv_1='reset' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getTraceAccess().getResetKeyword_1());
    }
	otherlv_2=';' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getTraceAccess().getSemicolonKeyword_2());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getTraceAccess().getTicksTickParserRuleCall_3_0()); 
	    }
		lv_ticks_3_0=ruletick		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getTraceRule());
	        }
       		add(
       			$current, 
       			"ticks",
        		lv_ticks_3_0, 
        		"tick");
	        afterParserOrEnumRuleCall();
	    }

)
)+)
;





// Entry rule entryRuletick
entryRuletick returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getTickRule()); }
	 iv_ruletick=ruletick 
	 { $current=$iv_ruletick.current; } 
	 EOF 
;

// Rule tick
ruletick returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
((
	{ 
	  /* */ 
	}
    {
        $current = forceCreateModelElement(
            grammarAccess.getTickAccess().getTickAction_0(),
            $current);
    }
)(
(
		{ 
	        newCompositeNode(grammarAccess.getTickAccess().getInputSignalParserRuleCall_1_0()); 
	    }
		lv_input_1_0=rulesignal		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getTickRule());
	        }
       		add(
       			$current, 
       			"input",
        		lv_input_1_0, 
        		"signal");
	        afterParserOrEnumRuleCall();
	    }

)
)*(	otherlv_2='%' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getTickAccess().getPercentSignKeyword_2_0());
    }
(	otherlv_3='Output:' 
    {
    	newLeafNode(otherlv_3, grammarAccess.getTickAccess().getOutputKeyword_2_1_0());
    }

    |(	otherlv_4='Output' 
    {
    	newLeafNode(otherlv_4, grammarAccess.getTickAccess().getOutputKeyword_2_1_1_0());
    }
	otherlv_5=':' 
    {
    	newLeafNode(otherlv_5, grammarAccess.getTickAccess().getColonKeyword_2_1_1_1());
    }
))(
(
		{ 
	        newCompositeNode(grammarAccess.getTickAccess().getOutputSignalParserRuleCall_2_2_0()); 
	    }
		lv_output_6_0=rulesignal		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getTickRule());
	        }
       		add(
       			$current, 
       			"output",
        		lv_output_6_0, 
        		"signal");
	        afterParserOrEnumRuleCall();
	    }

)
)*)?(
(
		{ 
	        newCompositeNode(grammarAccess.getTickAccess().getExtraInfosKvpairParserRuleCall_3_0()); 
	    }
		lv_extraInfos_7_0=rulekvpair		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getTickRule());
	        }
       		add(
       			$current, 
       			"extraInfos",
        		lv_extraInfos_7_0, 
        		"kvpair");
	        afterParserOrEnumRuleCall();
	    }

)
)*(	otherlv_8='%%' 
    {
    	newLeafNode(otherlv_8, grammarAccess.getTickAccess().getPercentSignPercentSignKeyword_4_0());
    }
(	otherlv_9='Output:' 
    {
    	newLeafNode(otherlv_9, grammarAccess.getTickAccess().getOutputKeyword_4_1_0());
    }

    |(	otherlv_10='Output' 
    {
    	newLeafNode(otherlv_10, grammarAccess.getTickAccess().getOutputKeyword_4_1_1_0());
    }
	otherlv_11=':' 
    {
    	newLeafNode(otherlv_11, grammarAccess.getTickAccess().getColonKeyword_4_1_1_1());
    }
))(
(
		{ 
	        newCompositeNode(grammarAccess.getTickAccess().getExtraInfosOutputKvpairParserRuleCall_4_2_0()); 
	    }
		lv_extraInfosOutput_12_0=rulekvpair		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getTickRule());
	        }
       		add(
       			$current, 
       			"extraInfosOutput",
        		lv_extraInfosOutput_12_0, 
        		"kvpair");
	        afterParserOrEnumRuleCall();
	    }

)
)*)?	otherlv_13=';' 
    {
    	newLeafNode(otherlv_13, grammarAccess.getTickAccess().getSemicolonKeyword_5());
    }
)
;





// Entry rule entryRulesignal
entryRulesignal returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getSignalRule()); }
	 iv_rulesignal=rulesignal 
	 { $current=$iv_rulesignal.current; } 
	 EOF 
;

// Rule signal
rulesignal returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
((
(
		lv_name_0_0=RULE_ALPHANUMSPECIAL
		{
			newLeafNode(lv_name_0_0, grammarAccess.getSignalAccess().getNameALPHANUMSPECIALTerminalRuleCall_0_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getSignalRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"name",
        		lv_name_0_0, 
        		"ALPHANUMSPECIAL");
	    }

)
)(
(
		lv_valued_1_0=	'(' 
    {
        newLeafNode(lv_valued_1_0, grammarAccess.getSignalAccess().getValuedLeftParenthesisKeyword_1_0());
    }
 
	    {
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getSignalRule());
	        }
       		setWithLastConsumed($current, "valued", true, "(");
	    }

)
)?((
(
(
		{ 
	        newCompositeNode(grammarAccess.getSignalAccess().getValEsoIntParserRuleCall_2_0_0_0()); 
	    }
		lv_val_2_1=ruleEsoInt		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getSignalRule());
	        }
       		set(
       			$current, 
       			"val",
        		lv_val_2_1, 
        		"EsoInt");
	        afterParserOrEnumRuleCall();
	    }

    |		{ 
	        newCompositeNode(grammarAccess.getSignalAccess().getValEsoFloatParserRuleCall_2_0_0_1()); 
	    }
		lv_val_2_2=ruleEsoFloat		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getSignalRule());
	        }
       		set(
       			$current, 
       			"val",
        		lv_val_2_2, 
        		"EsoFloat");
	        afterParserOrEnumRuleCall();
	    }

    |		{ 
	        newCompositeNode(grammarAccess.getSignalAccess().getValEsoBoolParserRuleCall_2_0_0_2()); 
	    }
		lv_val_2_3=ruleEsoBool		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getSignalRule());
	        }
       		set(
       			$current, 
       			"val",
        		lv_val_2_3, 
        		"EsoBool");
	        afterParserOrEnumRuleCall();
	    }

    |		{ 
	        newCompositeNode(grammarAccess.getSignalAccess().getValEsoStringParserRuleCall_2_0_0_3()); 
	    }
		lv_val_2_4=ruleEsoString		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getSignalRule());
	        }
       		set(
       			$current, 
       			"val",
        		lv_val_2_4, 
        		"EsoString");
	        afterParserOrEnumRuleCall();
	    }

)

)
)	otherlv_3=')' 
    {
    	newLeafNode(otherlv_3, grammarAccess.getSignalAccess().getRightParenthesisKeyword_2_1());
    }
)?)
;





// Entry rule entryRulekvpair
entryRulekvpair returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getKvpairRule()); }
	 iv_rulekvpair=rulekvpair 
	 { $current=$iv_rulekvpair.current; } 
	 EOF 
;

// Rule kvpair
rulekvpair returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
((
	{ 
	  /* */ 
	}
    {
        $current = forceCreateModelElement(
            grammarAccess.getKvpairAccess().getKvpairAction_0(),
            $current);
    }
)	otherlv_1='%%' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getKvpairAccess().getPercentSignPercentSignKeyword_1());
    }
(
(
		lv_key_2_0=RULE_ALPHANUMSPECIAL
		{
			newLeafNode(lv_key_2_0, grammarAccess.getKvpairAccess().getKeyALPHANUMSPECIALTerminalRuleCall_2_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getKvpairRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"key",
        		lv_key_2_0, 
        		"ALPHANUMSPECIAL");
	    }

)
)	otherlv_3='=' 
    {
    	newLeafNode(otherlv_3, grammarAccess.getKvpairAccess().getEqualsSignKeyword_3());
    }
(
(
(
		{ 
	        newCompositeNode(grammarAccess.getKvpairAccess().getValueEsoJsonParserRuleCall_4_0_0()); 
	    }
		lv_value_4_1=ruleEsoJson		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getKvpairRule());
	        }
       		set(
       			$current, 
       			"value",
        		lv_value_4_1, 
        		"EsoJson");
	        afterParserOrEnumRuleCall();
	    }

    |		{ 
	        newCompositeNode(grammarAccess.getKvpairAccess().getValueEsoStringParserRuleCall_4_0_1()); 
	    }
		lv_value_4_2=ruleEsoString		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getKvpairRule());
	        }
       		set(
       			$current, 
       			"value",
        		lv_value_4_2, 
        		"EsoString");
	        afterParserOrEnumRuleCall();
	    }

    |		{ 
	        newCompositeNode(grammarAccess.getKvpairAccess().getValueEsoFloatParserRuleCall_4_0_2()); 
	    }
		lv_value_4_3=ruleEsoFloat		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getKvpairRule());
	        }
       		set(
       			$current, 
       			"value",
        		lv_value_4_3, 
        		"EsoFloat");
	        afterParserOrEnumRuleCall();
	    }

    |		{ 
	        newCompositeNode(grammarAccess.getKvpairAccess().getValueEsoBoolParserRuleCall_4_0_3()); 
	    }
		lv_value_4_4=ruleEsoBool		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getKvpairRule());
	        }
       		set(
       			$current, 
       			"value",
        		lv_value_4_4, 
        		"EsoBool");
	        afterParserOrEnumRuleCall();
	    }

    |		{ 
	        newCompositeNode(grammarAccess.getKvpairAccess().getValueEsoIntParserRuleCall_4_0_4()); 
	    }
		lv_value_4_5=ruleEsoInt		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getKvpairRule());
	        }
       		set(
       			$current, 
       			"value",
        		lv_value_4_5, 
        		"EsoInt");
	        afterParserOrEnumRuleCall();
	    }

)

)
))
;





// Entry rule entryRuleEsoInt
entryRuleEsoInt returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getEsoIntRule()); }
	 iv_ruleEsoInt=ruleEsoInt 
	 { $current=$iv_ruleEsoInt.current; } 
	 EOF 
;

// Rule EsoInt
ruleEsoInt returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		lv_value_0_0=RULE_INT
		{
			newLeafNode(lv_value_0_0, grammarAccess.getEsoIntAccess().getValueINTTerminalRuleCall_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getEsoIntRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"value",
        		lv_value_0_0, 
        		"INT");
	    }

)
)
;





// Entry rule entryRuleEsoString
entryRuleEsoString returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getEsoStringRule()); }
	 iv_ruleEsoString=ruleEsoString 
	 { $current=$iv_ruleEsoString.current; } 
	 EOF 
;

// Rule EsoString
ruleEsoString returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		lv_value_0_0=RULE_STRING
		{
			newLeafNode(lv_value_0_0, grammarAccess.getEsoStringAccess().getValueSTRINGTerminalRuleCall_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getEsoStringRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"value",
        		lv_value_0_0, 
        		"STRING");
	    }

)
)
;





// Entry rule entryRuleEsoFloat
entryRuleEsoFloat returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getEsoFloatRule()); }
	 iv_ruleEsoFloat=ruleEsoFloat 
	 { $current=$iv_ruleEsoFloat.current; } 
	 EOF 
;

// Rule EsoFloat
ruleEsoFloat returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		lv_value_0_0=RULE_FLOAT
		{
			newLeafNode(lv_value_0_0, grammarAccess.getEsoFloatAccess().getValueFLOATTerminalRuleCall_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getEsoFloatRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"value",
        		lv_value_0_0, 
        		"FLOAT");
	    }

)
)
;





// Entry rule entryRuleEsoBool
entryRuleEsoBool returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getEsoBoolRule()); }
	 iv_ruleEsoBool=ruleEsoBool 
	 { $current=$iv_ruleEsoBool.current; } 
	 EOF 
;

// Rule EsoBool
ruleEsoBool returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		lv_value_0_0=RULE_BOOL
		{
			newLeafNode(lv_value_0_0, grammarAccess.getEsoBoolAccess().getValueBOOLTerminalRuleCall_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getEsoBoolRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"value",
        		lv_value_0_0, 
        		"BOOL");
	    }

)
)
;





// Entry rule entryRuleEsoJson
entryRuleEsoJson returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getEsoJsonRule()); }
	 iv_ruleEsoJson=ruleEsoJson 
	 { $current=$iv_ruleEsoJson.current; } 
	 EOF 
;

// Rule EsoJson
ruleEsoJson returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		lv_value_0_0=RULE_ALPHANUMSPECIAL
		{
			newLeafNode(lv_value_0_0, grammarAccess.getEsoJsonAccess().getValueALPHANUMSPECIALTerminalRuleCall_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getEsoJsonRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"value",
        		lv_value_0_0, 
        		"ALPHANUMSPECIAL");
	    }

)
)
;





RULE_SPECIAL : (','|'.'|'/'|'@'|'#'|'$'|'&'|'*'|':'|'+'|'-'|'_');

RULE_BOOL : ('true'|'false');

RULE_FLOAT : ('+'|'-')? ('0'..'9')+ '.' ('0'..'9')+;

RULE_INT : ('0'..'9')+;

RULE_ALPHANUMSPECIAL : ('0'..'9')* ('a'..'z'|'A'..'Z'|RULE_SPECIAL) ('a'..'z'|'A'..'Z'|'0'..'9'|RULE_SPECIAL)*;

RULE_STRING : ('"' ('\\' ('b'|'t'|'n'|'f'|'r'|'u'|'"'|'\''|'\\')|~(('\\'|'"')))* '"'|'\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'u'|'"'|'\''|'\\')|~(('\\'|'\'')))* '\'');

RULE_WS : (' '|'\t'|'\r'|'\n')+;

