/*
 * generated by Xtext
 */
package de.cau.cs.kieler.core.kexpressions.ui.contentassist.antlr;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.RecognitionException;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.AbstractContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;

import com.google.inject.Inject;

import de.cau.cs.kieler.core.kexpressions.services.KExpressionsGrammarAccess;

public class KExpressionsParser extends AbstractContentAssistParser {
	
	@Inject
	private KExpressionsGrammarAccess grammarAccess;
	
	private Map<AbstractElement, String> nameMappings;
	
	@Override
	protected de.cau.cs.kieler.core.kexpressions.ui.contentassist.antlr.internal.InternalKExpressionsParser createParser() {
		de.cau.cs.kieler.core.kexpressions.ui.contentassist.antlr.internal.InternalKExpressionsParser result = new de.cau.cs.kieler.core.kexpressions.ui.contentassist.antlr.internal.InternalKExpressionsParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}
	
	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getExpressionAccess().getAlternatives(), "rule__Expression__Alternatives");
					put(grammarAccess.getNotOrValuedExpressionAccess().getAlternatives(), "rule__NotOrValuedExpression__Alternatives");
					put(grammarAccess.getNotExpressionAccess().getAlternatives(), "rule__NotExpression__Alternatives");
					put(grammarAccess.getNegExpressionAccess().getAlternatives(), "rule__NegExpression__Alternatives");
					put(grammarAccess.getAtomicExpressionAccess().getAlternatives(), "rule__AtomicExpression__Alternatives");
					put(grammarAccess.getAtomicValuedExpressionAccess().getAlternatives(), "rule__AtomicValuedExpression__Alternatives");
					put(grammarAccess.getValuedObjectTestExpressionAccess().getAlternatives(), "rule__ValuedObjectTestExpression__Alternatives");
					put(grammarAccess.getValuedObjectTestExpressionAccess().getOperatorAlternatives_0_1_0(), "rule__ValuedObjectTestExpression__OperatorAlternatives_0_1_0");
					put(grammarAccess.getFunctionCallAccess().getAlternatives_2(), "rule__FunctionCall__Alternatives_2");
					put(grammarAccess.getAnyTypeAccess().getAlternatives(), "rule__AnyType__Alternatives");
					put(grammarAccess.getAnnotationAccess().getAlternatives(), "rule__Annotation__Alternatives");
					put(grammarAccess.getValuedAnnotationAccess().getAlternatives(), "rule__ValuedAnnotation__Alternatives");
					put(grammarAccess.getRestrictedAnnotationAccess().getAlternatives(), "rule__RestrictedAnnotation__Alternatives");
					put(grammarAccess.getEStringAccess().getAlternatives(), "rule__EString__Alternatives");
					put(grammarAccess.getEStringBooleanAccess().getAlternatives(), "rule__EStringBoolean__Alternatives");
					put(grammarAccess.getCompareOperatorAccess().getAlternatives(), "rule__CompareOperator__Alternatives");
					put(grammarAccess.getValueTypeAccess().getAlternatives(), "rule__ValueType__Alternatives");
					put(grammarAccess.getCombineOperatorAccess().getAlternatives(), "rule__CombineOperator__Alternatives");
					put(grammarAccess.getLogicalOrExpressionAccess().getGroup(), "rule__LogicalOrExpression__Group__0");
					put(grammarAccess.getLogicalOrExpressionAccess().getGroup_1(), "rule__LogicalOrExpression__Group_1__0");
					put(grammarAccess.getLogicalOrExpressionAccess().getGroup_1_1(), "rule__LogicalOrExpression__Group_1_1__0");
					put(grammarAccess.getLogicalAndExpressionAccess().getGroup(), "rule__LogicalAndExpression__Group__0");
					put(grammarAccess.getLogicalAndExpressionAccess().getGroup_1(), "rule__LogicalAndExpression__Group_1__0");
					put(grammarAccess.getLogicalAndExpressionAccess().getGroup_1_1(), "rule__LogicalAndExpression__Group_1_1__0");
					put(grammarAccess.getBitwiseOrExpressionAccess().getGroup(), "rule__BitwiseOrExpression__Group__0");
					put(grammarAccess.getBitwiseOrExpressionAccess().getGroup_1(), "rule__BitwiseOrExpression__Group_1__0");
					put(grammarAccess.getBitwiseOrExpressionAccess().getGroup_1_1(), "rule__BitwiseOrExpression__Group_1_1__0");
					put(grammarAccess.getBitwiseAndExpressionAccess().getGroup(), "rule__BitwiseAndExpression__Group__0");
					put(grammarAccess.getBitwiseAndExpressionAccess().getGroup_1(), "rule__BitwiseAndExpression__Group_1__0");
					put(grammarAccess.getBitwiseAndExpressionAccess().getGroup_1_1(), "rule__BitwiseAndExpression__Group_1_1__0");
					put(grammarAccess.getCompareOperationAccess().getGroup(), "rule__CompareOperation__Group__0");
					put(grammarAccess.getCompareOperationAccess().getGroup_1(), "rule__CompareOperation__Group_1__0");
					put(grammarAccess.getNotExpressionAccess().getGroup_0(), "rule__NotExpression__Group_0__0");
					put(grammarAccess.getAddExpressionAccess().getGroup(), "rule__AddExpression__Group__0");
					put(grammarAccess.getAddExpressionAccess().getGroup_1(), "rule__AddExpression__Group_1__0");
					put(grammarAccess.getAddExpressionAccess().getGroup_1_1(), "rule__AddExpression__Group_1_1__0");
					put(grammarAccess.getSubExpressionAccess().getGroup(), "rule__SubExpression__Group__0");
					put(grammarAccess.getSubExpressionAccess().getGroup_1(), "rule__SubExpression__Group_1__0");
					put(grammarAccess.getSubExpressionAccess().getGroup_1_1(), "rule__SubExpression__Group_1_1__0");
					put(grammarAccess.getMultExpressionAccess().getGroup(), "rule__MultExpression__Group__0");
					put(grammarAccess.getMultExpressionAccess().getGroup_1(), "rule__MultExpression__Group_1__0");
					put(grammarAccess.getMultExpressionAccess().getGroup_1_1(), "rule__MultExpression__Group_1_1__0");
					put(grammarAccess.getDivExpressionAccess().getGroup(), "rule__DivExpression__Group__0");
					put(grammarAccess.getDivExpressionAccess().getGroup_1(), "rule__DivExpression__Group_1__0");
					put(grammarAccess.getDivExpressionAccess().getGroup_1_1(), "rule__DivExpression__Group_1_1__0");
					put(grammarAccess.getModExpressionAccess().getGroup(), "rule__ModExpression__Group__0");
					put(grammarAccess.getModExpressionAccess().getGroup_1(), "rule__ModExpression__Group_1__0");
					put(grammarAccess.getModExpressionAccess().getGroup_1_1(), "rule__ModExpression__Group_1_1__0");
					put(grammarAccess.getNegExpressionAccess().getGroup_0(), "rule__NegExpression__Group_0__0");
					put(grammarAccess.getAtomicExpressionAccess().getGroup_2(), "rule__AtomicExpression__Group_2__0");
					put(grammarAccess.getAtomicValuedExpressionAccess().getGroup_3(), "rule__AtomicValuedExpression__Group_3__0");
					put(grammarAccess.getValuedObjectTestExpressionAccess().getGroup_0(), "rule__ValuedObjectTestExpression__Group_0__0");
					put(grammarAccess.getValuedObjectReferenceAccess().getGroup(), "rule__ValuedObjectReference__Group__0");
					put(grammarAccess.getValuedObjectReferenceAccess().getGroup_1(), "rule__ValuedObjectReference__Group_1__0");
					put(grammarAccess.getFunctionCallAccess().getGroup(), "rule__FunctionCall__Group__0");
					put(grammarAccess.getFunctionCallAccess().getGroup_2_0(), "rule__FunctionCall__Group_2_0__0");
					put(grammarAccess.getFunctionCallAccess().getGroup_2_0_2(), "rule__FunctionCall__Group_2_0_2__0");
					put(grammarAccess.getParameterAccess().getGroup(), "rule__Parameter__Group__0");
					put(grammarAccess.getParameterAccess().getGroup_0(), "rule__Parameter__Group_0__0");
					put(grammarAccess.getTagAnnotationAccess().getGroup(), "rule__TagAnnotation__Group__0");
					put(grammarAccess.getKeyStringValueAnnotationAccess().getGroup(), "rule__KeyStringValueAnnotation__Group__0");
					put(grammarAccess.getKeyStringValueAnnotationAccess().getGroup_3(), "rule__KeyStringValueAnnotation__Group_3__0");
					put(grammarAccess.getTypedKeyStringValueAnnotationAccess().getGroup(), "rule__TypedKeyStringValueAnnotation__Group__0");
					put(grammarAccess.getTypedKeyStringValueAnnotationAccess().getGroup_6(), "rule__TypedKeyStringValueAnnotation__Group_6__0");
					put(grammarAccess.getQuotedKeyStringValueAnnotationAccess().getGroup(), "rule__QuotedKeyStringValueAnnotation__Group__0");
					put(grammarAccess.getQuotedKeyStringValueAnnotationAccess().getGroup_3(), "rule__QuotedKeyStringValueAnnotation__Group_3__0");
					put(grammarAccess.getQuotedTypedKeyStringValueAnnotationAccess().getGroup(), "rule__QuotedTypedKeyStringValueAnnotation__Group__0");
					put(grammarAccess.getQuotedTypedKeyStringValueAnnotationAccess().getGroup_6(), "rule__QuotedTypedKeyStringValueAnnotation__Group_6__0");
					put(grammarAccess.getKeyBooleanValueAnnotationAccess().getGroup(), "rule__KeyBooleanValueAnnotation__Group__0");
					put(grammarAccess.getKeyIntValueAnnotationAccess().getGroup(), "rule__KeyIntValueAnnotation__Group__0");
					put(grammarAccess.getKeyFloatValueAnnotationAccess().getGroup(), "rule__KeyFloatValueAnnotation__Group__0");
					put(grammarAccess.getExtendedIDAccess().getGroup(), "rule__ExtendedID__Group__0");
					put(grammarAccess.getExtendedIDAccess().getGroup_1(), "rule__ExtendedID__Group_1__0");
					put(grammarAccess.getExtendedIDAccess().getGroup_2(), "rule__ExtendedID__Group_2__0");
					put(grammarAccess.getIntegerAccess().getGroup(), "rule__Integer__Group__0");
					put(grammarAccess.getFloategerAccess().getGroup(), "rule__Floateger__Group__0");
					put(grammarAccess.getLogicalOrExpressionAccess().getOperatorAssignment_1_1_0(), "rule__LogicalOrExpression__OperatorAssignment_1_1_0");
					put(grammarAccess.getLogicalOrExpressionAccess().getSubExpressionsAssignment_1_1_1(), "rule__LogicalOrExpression__SubExpressionsAssignment_1_1_1");
					put(grammarAccess.getLogicalAndExpressionAccess().getOperatorAssignment_1_1_0(), "rule__LogicalAndExpression__OperatorAssignment_1_1_0");
					put(grammarAccess.getLogicalAndExpressionAccess().getSubExpressionsAssignment_1_1_1(), "rule__LogicalAndExpression__SubExpressionsAssignment_1_1_1");
					put(grammarAccess.getBitwiseOrExpressionAccess().getOperatorAssignment_1_1_0(), "rule__BitwiseOrExpression__OperatorAssignment_1_1_0");
					put(grammarAccess.getBitwiseOrExpressionAccess().getSubExpressionsAssignment_1_1_1(), "rule__BitwiseOrExpression__SubExpressionsAssignment_1_1_1");
					put(grammarAccess.getBitwiseAndExpressionAccess().getOperatorAssignment_1_1_0(), "rule__BitwiseAndExpression__OperatorAssignment_1_1_0");
					put(grammarAccess.getBitwiseAndExpressionAccess().getSubExpressionsAssignment_1_1_1(), "rule__BitwiseAndExpression__SubExpressionsAssignment_1_1_1");
					put(grammarAccess.getCompareOperationAccess().getOperatorAssignment_1_1(), "rule__CompareOperation__OperatorAssignment_1_1");
					put(grammarAccess.getCompareOperationAccess().getSubExpressionsAssignment_1_2(), "rule__CompareOperation__SubExpressionsAssignment_1_2");
					put(grammarAccess.getNotExpressionAccess().getOperatorAssignment_0_1(), "rule__NotExpression__OperatorAssignment_0_1");
					put(grammarAccess.getNotExpressionAccess().getSubExpressionsAssignment_0_2(), "rule__NotExpression__SubExpressionsAssignment_0_2");
					put(grammarAccess.getAddExpressionAccess().getOperatorAssignment_1_1_0(), "rule__AddExpression__OperatorAssignment_1_1_0");
					put(grammarAccess.getAddExpressionAccess().getSubExpressionsAssignment_1_1_1(), "rule__AddExpression__SubExpressionsAssignment_1_1_1");
					put(grammarAccess.getSubExpressionAccess().getOperatorAssignment_1_1_0(), "rule__SubExpression__OperatorAssignment_1_1_0");
					put(grammarAccess.getSubExpressionAccess().getSubExpressionsAssignment_1_1_1(), "rule__SubExpression__SubExpressionsAssignment_1_1_1");
					put(grammarAccess.getMultExpressionAccess().getOperatorAssignment_1_1_0(), "rule__MultExpression__OperatorAssignment_1_1_0");
					put(grammarAccess.getMultExpressionAccess().getSubExpressionsAssignment_1_1_1(), "rule__MultExpression__SubExpressionsAssignment_1_1_1");
					put(grammarAccess.getDivExpressionAccess().getOperatorAssignment_1_1_0(), "rule__DivExpression__OperatorAssignment_1_1_0");
					put(grammarAccess.getDivExpressionAccess().getSubExpressionsAssignment_1_1_1(), "rule__DivExpression__SubExpressionsAssignment_1_1_1");
					put(grammarAccess.getModExpressionAccess().getOperatorAssignment_1_1_0(), "rule__ModExpression__OperatorAssignment_1_1_0");
					put(grammarAccess.getModExpressionAccess().getSubExpressionsAssignment_1_1_1(), "rule__ModExpression__SubExpressionsAssignment_1_1_1");
					put(grammarAccess.getNegExpressionAccess().getOperatorAssignment_0_1(), "rule__NegExpression__OperatorAssignment_0_1");
					put(grammarAccess.getNegExpressionAccess().getSubExpressionsAssignment_0_2(), "rule__NegExpression__SubExpressionsAssignment_0_2");
					put(grammarAccess.getValuedObjectTestExpressionAccess().getOperatorAssignment_0_1(), "rule__ValuedObjectTestExpression__OperatorAssignment_0_1");
					put(grammarAccess.getValuedObjectTestExpressionAccess().getSubExpressionsAssignment_0_3(), "rule__ValuedObjectTestExpression__SubExpressionsAssignment_0_3");
					put(grammarAccess.getValuedObjectReferenceAccess().getValuedObjectAssignment_0(), "rule__ValuedObjectReference__ValuedObjectAssignment_0");
					put(grammarAccess.getValuedObjectReferenceAccess().getIndicesAssignment_1_1(), "rule__ValuedObjectReference__IndicesAssignment_1_1");
					put(grammarAccess.getFunctionCallAccess().getFunctionNameAssignment_1(), "rule__FunctionCall__FunctionNameAssignment_1");
					put(grammarAccess.getFunctionCallAccess().getParametersAssignment_2_0_1(), "rule__FunctionCall__ParametersAssignment_2_0_1");
					put(grammarAccess.getFunctionCallAccess().getParametersAssignment_2_0_2_1(), "rule__FunctionCall__ParametersAssignment_2_0_2_1");
					put(grammarAccess.getParameterAccess().getPureOutputAssignment_0_0(), "rule__Parameter__PureOutputAssignment_0_0");
					put(grammarAccess.getParameterAccess().getCallByReferenceAssignment_0_1(), "rule__Parameter__CallByReferenceAssignment_0_1");
					put(grammarAccess.getParameterAccess().getExpressionAssignment_1(), "rule__Parameter__ExpressionAssignment_1");
					put(grammarAccess.getTextExpressionAccess().getTextAssignment(), "rule__TextExpression__TextAssignment");
					put(grammarAccess.getIntValueAccess().getValueAssignment(), "rule__IntValue__ValueAssignment");
					put(grammarAccess.getFloatValueAccess().getValueAssignment(), "rule__FloatValue__ValueAssignment");
					put(grammarAccess.getBoolValueAccess().getValueAssignment(), "rule__BoolValue__ValueAssignment");
					put(grammarAccess.getStringValueAccess().getValueAssignment(), "rule__StringValue__ValueAssignment");
					put(grammarAccess.getCommentAnnotationAccess().getValuesAssignment(), "rule__CommentAnnotation__ValuesAssignment");
					put(grammarAccess.getTagAnnotationAccess().getNameAssignment_1(), "rule__TagAnnotation__NameAssignment_1");
					put(grammarAccess.getKeyStringValueAnnotationAccess().getNameAssignment_1(), "rule__KeyStringValueAnnotation__NameAssignment_1");
					put(grammarAccess.getKeyStringValueAnnotationAccess().getValuesAssignment_2(), "rule__KeyStringValueAnnotation__ValuesAssignment_2");
					put(grammarAccess.getKeyStringValueAnnotationAccess().getValuesAssignment_3_1(), "rule__KeyStringValueAnnotation__ValuesAssignment_3_1");
					put(grammarAccess.getTypedKeyStringValueAnnotationAccess().getNameAssignment_1(), "rule__TypedKeyStringValueAnnotation__NameAssignment_1");
					put(grammarAccess.getTypedKeyStringValueAnnotationAccess().getTypeAssignment_3(), "rule__TypedKeyStringValueAnnotation__TypeAssignment_3");
					put(grammarAccess.getTypedKeyStringValueAnnotationAccess().getValuesAssignment_5(), "rule__TypedKeyStringValueAnnotation__ValuesAssignment_5");
					put(grammarAccess.getTypedKeyStringValueAnnotationAccess().getValuesAssignment_6_1(), "rule__TypedKeyStringValueAnnotation__ValuesAssignment_6_1");
					put(grammarAccess.getQuotedKeyStringValueAnnotationAccess().getNameAssignment_1(), "rule__QuotedKeyStringValueAnnotation__NameAssignment_1");
					put(grammarAccess.getQuotedKeyStringValueAnnotationAccess().getValuesAssignment_2(), "rule__QuotedKeyStringValueAnnotation__ValuesAssignment_2");
					put(grammarAccess.getQuotedKeyStringValueAnnotationAccess().getValuesAssignment_3_1(), "rule__QuotedKeyStringValueAnnotation__ValuesAssignment_3_1");
					put(grammarAccess.getQuotedTypedKeyStringValueAnnotationAccess().getNameAssignment_1(), "rule__QuotedTypedKeyStringValueAnnotation__NameAssignment_1");
					put(grammarAccess.getQuotedTypedKeyStringValueAnnotationAccess().getTypeAssignment_3(), "rule__QuotedTypedKeyStringValueAnnotation__TypeAssignment_3");
					put(grammarAccess.getQuotedTypedKeyStringValueAnnotationAccess().getValuesAssignment_5(), "rule__QuotedTypedKeyStringValueAnnotation__ValuesAssignment_5");
					put(grammarAccess.getQuotedTypedKeyStringValueAnnotationAccess().getValuesAssignment_6_1(), "rule__QuotedTypedKeyStringValueAnnotation__ValuesAssignment_6_1");
					put(grammarAccess.getKeyBooleanValueAnnotationAccess().getNameAssignment_1(), "rule__KeyBooleanValueAnnotation__NameAssignment_1");
					put(grammarAccess.getKeyBooleanValueAnnotationAccess().getValueAssignment_2(), "rule__KeyBooleanValueAnnotation__ValueAssignment_2");
					put(grammarAccess.getKeyIntValueAnnotationAccess().getNameAssignment_1(), "rule__KeyIntValueAnnotation__NameAssignment_1");
					put(grammarAccess.getKeyIntValueAnnotationAccess().getValueAssignment_2(), "rule__KeyIntValueAnnotation__ValueAssignment_2");
					put(grammarAccess.getKeyFloatValueAnnotationAccess().getNameAssignment_1(), "rule__KeyFloatValueAnnotation__NameAssignment_1");
					put(grammarAccess.getKeyFloatValueAnnotationAccess().getValueAssignment_2(), "rule__KeyFloatValueAnnotation__ValueAssignment_2");
				}
			};
		}
		return nameMappings.get(element);
	}
	
	@Override
	protected Collection<FollowElement> getFollowElements(AbstractInternalContentAssistParser parser) {
		try {
			de.cau.cs.kieler.core.kexpressions.ui.contentassist.antlr.internal.InternalKExpressionsParser typedParser = (de.cau.cs.kieler.core.kexpressions.ui.contentassist.antlr.internal.InternalKExpressionsParser) parser;
			typedParser.entryRuleRoot();
			return typedParser.getFollowElements();
		} catch(RecognitionException ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}
	
	public KExpressionsGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(KExpressionsGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}