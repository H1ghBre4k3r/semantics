/*
 * generated by Xtext
 */
package de.cau.cs.kieler.sccharts.text.actions.formatting;

import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.formatting.impl.FormattingConfig;
import org.eclipse.xtext.util.Pair;

import de.cau.cs.kieler.core.annotations.text.formatting.AnnotationsFormatter;
import de.cau.cs.kieler.core.kexpressions.text.formatting.KEXTFormatter;
import de.cau.cs.kieler.sccharts.text.actions.services.ActionsGrammarAccess;

/**
 * This class contains custom formatting description.
 *
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#formatting on how and when to
 * use it
 *
 * Also see {@link org.eclipse.xtext.xtext.XtextFormattingTokenSerializer} as an example
 */
public class ActionsFormatter extends KEXTFormatter {

    @Override
    protected void configureFormatting(FormattingConfig c) {
        ActionsGrammarAccess f = (ActionsGrammarAccess) getGrammarAccess();
        customConfigureFormatting(c, f);
    }

    /**
     * Method contains actual formatting instructions while GrammarAccess class maybe parameterized
     * allowing the reuse within KitsFormatter.
     *
     * @param c
     *            FormattingConfig provided by caller
     * @param f
     *            GrammarAccess provided by caller
     */
    protected void customConfigureFormatting(FormattingConfig c, ActionsGrammarAccess f) {
        super.customConfigureFormatting(c, f.getKEXTGrammarAccess());

        // avoid space in valued Emission like 'X (5 + 7)' -> 'X(5 + 7)'
        c.setNoSpace().before(f.getEmissionAccess().getLeftParenthesisKeyword_2_0());

        // There is no type any more
        // avoid space in textual effect like '/ "foo" (java)' -> '/ "foo"(java)'
        //c.setNoSpace().before(f.getTextEffectAccess().getLeftParenthesisKeyword_1_0());
        
        /*
         * ACTIONS
         */
        c.setLinewrap().after(f.getEntryActionRule());
        c.setLinewrap().after(f.getDuringActionRule());
        c.setLinewrap().after(f.getExitActionRule());
        c.setLinewrap().after(f.getSuspendActionRule());
        c.setNoSpace().before(f.getEntryActionAccess().getSemicolonKeyword_4());
        c.setNoSpace().before(f.getDuringActionAccess().getSemicolonKeyword_5());
        c.setNoSpace().before(f.getExitActionAccess().getSemicolonKeyword_4());
        c.setNoSpace().before(f.getSuspendActionAccess().getSemicolonKeyword_5());
        c.setNoSpace().after(f.getNotExpressionAccess().getOperatorNotOperatorEnumRuleCall_0_1_0());
       
       c.setNoSpace().before(f.getTransitionAccess().getSemicolonKeyword_7_2_0());
       c.setNoSpace().before(f.getDuringActionAccess().getSemicolonKeyword_4_2_0());
       c.setNoSpace().before(f.getExitActionAccess().getSemicolonKeyword_3_2_0());
       
       // Emission ( value ) -> (value)
       c.setNoSpace().after(f.getEmissionAccess().getLeftParenthesisKeyword_2_0());
       c.setNoSpace().before(f.getEmissionAccess().getRightParenthesisKeyword_2_2());
       
       c.setNoSpace().before(f.getAssignmentAccess().getLeftSquareBracketKeyword_2_0());
       c.setNoSpace().after(f.getAssignmentAccess().getLeftSquareBracketKeyword_2_0());
       c.setNoSpace().before(f.getAssignmentAccess().getRightSquareBracketKeyword_2_2());

       
       c.setNoSpace().after(f.getFunctionCallEffectAccess().getLessThanSignKeyword_1());
       c.setNoSpace().before(f.getFunctionCallEffectAccess().getGreaterThanSignKeyword_4());
       c.setNoSpace().before(f.getFunctionCallEffectAccess().getLeftParenthesisKeyword_3_0_0());
       c.setNoSpace().after(f.getParameterAccess().getCallByReferenceAmpersandKeyword_0_1_0());
       
       for (Keyword comma : f.findKeywords(",")) {
           c.setNoLinewrap().before(comma);
           c.setNoSpace().before(comma);
       }
       
       for (Pair<Keyword, Keyword> pair : f.findKeywordPairs("(", ")")) {
           Grammar g = EcoreUtil2.getContainerOfType(pair.getFirst(), Grammar.class);

           // For all pairs '(' ')' that are introduced in KExpression and Grammars
           //  leveraging KExpressions (i.e. not Annotations) ...
           if (g != null && !g.getName().equals(AnnotationsFormatter.LANGUAGE_NAME)) {
               // ... don't insert space after left parenthesis
               c.setNoSpace().after(pair.getFirst());
               // ... don't insert space before right parenthesis
               c.setNoSpace().before(pair.getSecond());
               c.setNoLinewrap().after(pair.getSecond());
           }
       }       
    }
}