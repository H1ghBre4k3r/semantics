/*
 * generated by Xtext
 */
package de.cau.cs.kieler.kvis.serializer;

import com.google.inject.Inject;
import de.cau.cs.kieler.annotations.Annotation;
import de.cau.cs.kieler.annotations.AnnotationsPackage;
import de.cau.cs.kieler.annotations.BooleanAnnotation;
import de.cau.cs.kieler.annotations.CommentAnnotation;
import de.cau.cs.kieler.annotations.FloatAnnotation;
import de.cau.cs.kieler.annotations.IntAnnotation;
import de.cau.cs.kieler.annotations.StringAnnotation;
import de.cau.cs.kieler.annotations.TypedStringAnnotation;
import de.cau.cs.kieler.kexpressions.BoolValue;
import de.cau.cs.kieler.kexpressions.FloatValue;
import de.cau.cs.kieler.kexpressions.FunctionCall;
import de.cau.cs.kieler.kexpressions.IntValue;
import de.cau.cs.kieler.kexpressions.KExpressionsPackage;
import de.cau.cs.kieler.kexpressions.OperatorExpression;
import de.cau.cs.kieler.kexpressions.StringValue;
import de.cau.cs.kieler.kexpressions.TextExpression;
import de.cau.cs.kieler.kexpressions.ValuedObjectReference;
import de.cau.cs.kieler.kexpressions.serializer.KExpressionsSemanticSequencer;
import de.cau.cs.kieler.kvis.kvis.AndExpression;
import de.cau.cs.kieler.kvis.kvis.Animation;
import de.cau.cs.kieler.kvis.kvis.AttributeMapping;
import de.cau.cs.kieler.kvis.kvis.BooleanOperator;
import de.cau.cs.kieler.kvis.kvis.Comparison;
import de.cau.cs.kieler.kvis.kvis.Domain;
import de.cau.cs.kieler.kvis.kvis.Element;
import de.cau.cs.kieler.kvis.kvis.Event;
import de.cau.cs.kieler.kvis.kvis.Interaction;
import de.cau.cs.kieler.kvis.kvis.Interval;
import de.cau.cs.kieler.kvis.kvis.KvisPackage;
import de.cau.cs.kieler.kvis.kvis.Literal;
import de.cau.cs.kieler.kvis.kvis.Mapping;
import de.cau.cs.kieler.kvis.kvis.ModelReference;
import de.cau.cs.kieler.kvis.kvis.SignedFloat;
import de.cau.cs.kieler.kvis.kvis.SignedInt;
import de.cau.cs.kieler.kvis.kvis.VariableReference;
import de.cau.cs.kieler.kvis.kvis.Visualization;
import de.cau.cs.kieler.kvis.services.KVisGrammarAccess;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public abstract class AbstractKVisSemanticSequencer extends KExpressionsSemanticSequencer {

	@Inject
	private KVisGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == AnnotationsPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case AnnotationsPackage.ANNOTATION:
				sequence_TagAnnotation(context, (Annotation) semanticObject); 
				return; 
			case AnnotationsPackage.BOOLEAN_ANNOTATION:
				sequence_KeyBooleanValueAnnotation(context, (BooleanAnnotation) semanticObject); 
				return; 
			case AnnotationsPackage.COMMENT_ANNOTATION:
				sequence_CommentAnnotation(context, (CommentAnnotation) semanticObject); 
				return; 
			case AnnotationsPackage.FLOAT_ANNOTATION:
				sequence_KeyFloatValueAnnotation(context, (FloatAnnotation) semanticObject); 
				return; 
			case AnnotationsPackage.INT_ANNOTATION:
				sequence_KeyIntValueAnnotation(context, (IntAnnotation) semanticObject); 
				return; 
			case AnnotationsPackage.STRING_ANNOTATION:
				if (rule == grammarAccess.getAnnotationRule()
						|| rule == grammarAccess.getValuedAnnotationRule()
						|| rule == grammarAccess.getKeyStringValueAnnotationRule()) {
					sequence_KeyStringValueAnnotation(context, (StringAnnotation) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getRestrictedAnnotationRule()
						|| rule == grammarAccess.getQuotedKeyStringValueAnnotationRule()) {
					sequence_QuotedKeyStringValueAnnotation(context, (StringAnnotation) semanticObject); 
					return; 
				}
				else break;
			case AnnotationsPackage.TYPED_STRING_ANNOTATION:
				if (rule == grammarAccess.getRestrictedAnnotationRule()
						|| rule == grammarAccess.getQuotedTypedKeyStringValueAnnotationRule()) {
					sequence_QuotedTypedKeyStringValueAnnotation(context, (TypedStringAnnotation) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getAnnotationRule()
						|| rule == grammarAccess.getValuedAnnotationRule()
						|| rule == grammarAccess.getTypedKeyStringValueAnnotationRule()) {
					sequence_TypedKeyStringValueAnnotation(context, (TypedStringAnnotation) semanticObject); 
					return; 
				}
				else break;
			}
		else if (epackage == KExpressionsPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case KExpressionsPackage.BOOL_VALUE:
				sequence_BoolValue(context, (BoolValue) semanticObject); 
				return; 
			case KExpressionsPackage.FLOAT_VALUE:
				sequence_FloatValue(context, (FloatValue) semanticObject); 
				return; 
			case KExpressionsPackage.FUNCTION_CALL:
				sequence_FunctionCall(context, (FunctionCall) semanticObject); 
				return; 
			case KExpressionsPackage.INT_VALUE:
				sequence_IntValue(context, (IntValue) semanticObject); 
				return; 
			case KExpressionsPackage.OPERATOR_EXPRESSION:
				if (rule == grammarAccess.getRootRule()
						|| rule == grammarAccess.getExpressionRule()
						|| rule == grammarAccess.getBoolExpressionRule()
						|| rule == grammarAccess.getLogicalOrExpressionRule()
						|| action == grammarAccess.getLogicalOrExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0()
						|| rule == grammarAccess.getLogicalAndExpressionRule()
						|| action == grammarAccess.getLogicalAndExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0()
						|| rule == grammarAccess.getBitwiseOrExpressionRule()
						|| action == grammarAccess.getBitwiseOrExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0()
						|| rule == grammarAccess.getBitwiseAndExpressionRule()
						|| action == grammarAccess.getBitwiseAndExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0()
						|| rule == grammarAccess.getCompareOperationRule()
						|| action == grammarAccess.getCompareOperationAccess().getOperatorExpressionSubExpressionsAction_1_0()
						|| rule == grammarAccess.getNotOrValuedExpressionRule()
						|| rule == grammarAccess.getNotExpressionRule()
						|| rule == grammarAccess.getValuedExpressionRule()
						|| rule == grammarAccess.getAddExpressionRule()
						|| action == grammarAccess.getAddExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0()
						|| rule == grammarAccess.getSubExpressionRule()
						|| action == grammarAccess.getSubExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0()
						|| rule == grammarAccess.getMultExpressionRule()
						|| action == grammarAccess.getMultExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0()
						|| rule == grammarAccess.getDivExpressionRule()
						|| action == grammarAccess.getDivExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0()
						|| rule == grammarAccess.getModExpressionRule()
						|| action == grammarAccess.getModExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0()
						|| rule == grammarAccess.getNegExpressionRule()
						|| rule == grammarAccess.getAtomicExpressionRule()
						|| rule == grammarAccess.getAtomicValuedExpressionRule()) {
					sequence_AddExpression_BitwiseAndExpression_BitwiseOrExpression_CompareOperation_DivExpression_LogicalAndExpression_LogicalOrExpression_ModExpression_MultExpression_NegExpression_NotExpression_SubExpression_ValuedObjectTestExpression(context, (OperatorExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getValuedObjectTestExpressionRule()) {
					sequence_ValuedObjectTestExpression(context, (OperatorExpression) semanticObject); 
					return; 
				}
				else break;
			case KExpressionsPackage.PARAMETER:
				sequence_Parameter(context, (de.cau.cs.kieler.kexpressions.Parameter) semanticObject); 
				return; 
			case KExpressionsPackage.STRING_VALUE:
				sequence_StringValue(context, (StringValue) semanticObject); 
				return; 
			case KExpressionsPackage.TEXT_EXPRESSION:
				sequence_TextExpression(context, (TextExpression) semanticObject); 
				return; 
			case KExpressionsPackage.VALUED_OBJECT_REFERENCE:
				sequence_ValuedObjectReference(context, (ValuedObjectReference) semanticObject); 
				return; 
			}
		else if (epackage == KvisPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case KvisPackage.ACTION:
				if (rule == grammarAccess.getSimulationActionRule()) {
					sequence_SimulationAction(context, (de.cau.cs.kieler.kvis.kvis.Action) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getActionRule()) {
					sequence_SimulationAction_VariableAssignment(context, (de.cau.cs.kieler.kvis.kvis.Action) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getVariableAssignmentRule()) {
					sequence_VariableAssignment(context, (de.cau.cs.kieler.kvis.kvis.Action) semanticObject); 
					return; 
				}
				else break;
			case KvisPackage.AND_EXPRESSION:
				sequence_AndExpression(context, (AndExpression) semanticObject); 
				return; 
			case KvisPackage.ANIMATION:
				sequence_Animation(context, (Animation) semanticObject); 
				return; 
			case KvisPackage.ATTRIBUTE_MAPPING:
				sequence_AttributeMapping(context, (AttributeMapping) semanticObject); 
				return; 
			case KvisPackage.BOOLEAN_OPERATOR:
				sequence_AndOperator(context, (BooleanOperator) semanticObject); 
				return; 
			case KvisPackage.COMPARISON:
				sequence_Comparison(context, (Comparison) semanticObject); 
				return; 
			case KvisPackage.DOMAIN:
				sequence_VariableDomain(context, (Domain) semanticObject); 
				return; 
			case KvisPackage.ELEMENT:
				sequence_Element(context, (Element) semanticObject); 
				return; 
			case KvisPackage.EVENT:
				sequence_Event(context, (Event) semanticObject); 
				return; 
			case KvisPackage.INTERACTION:
				sequence_Interaction(context, (Interaction) semanticObject); 
				return; 
			case KvisPackage.INTERVAL:
				sequence_Interval(context, (Interval) semanticObject); 
				return; 
			case KvisPackage.LITERAL:
				sequence_Literal(context, (Literal) semanticObject); 
				return; 
			case KvisPackage.MAPPING:
				sequence_Mapping(context, (Mapping) semanticObject); 
				return; 
			case KvisPackage.MODEL_REFERENCE:
				sequence_ModelReference(context, (ModelReference) semanticObject); 
				return; 
			case KvisPackage.SIGNED_FLOAT:
				sequence_SignedFloat(context, (SignedFloat) semanticObject); 
				return; 
			case KvisPackage.SIGNED_INT:
				sequence_SignedInt(context, (SignedInt) semanticObject); 
				return; 
			case KvisPackage.VARIABLE_REFERENCE:
				sequence_VariableReference(context, (VariableReference) semanticObject); 
				return; 
			case KvisPackage.VISUALIZATION:
				sequence_Visualization(context, (Visualization) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Contexts:
	 *     AndExpression returns AndExpression
	 *     AndExpression.AndExpression_1_0_0 returns AndExpression
	 *
	 * Constraint:
	 *     (left=AndExpression_AndExpression_1_0_0 operator='and' right=Comparison)
	 */
	protected void sequence_AndExpression(ISerializationContext context, AndExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, KvisPackage.Literals.AND_EXPRESSION__LEFT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, KvisPackage.Literals.AND_EXPRESSION__LEFT));
			if (transientValues.isValueTransient(semanticObject, KvisPackage.Literals.AND_EXPRESSION__OPERATOR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, KvisPackage.Literals.AND_EXPRESSION__OPERATOR));
			if (transientValues.isValueTransient(semanticObject, KvisPackage.Literals.AND_EXPRESSION__RIGHT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, KvisPackage.Literals.AND_EXPRESSION__RIGHT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getAndExpressionAccess().getAndExpressionLeftAction_1_0_0(), semanticObject.getLeft());
		feeder.accept(grammarAccess.getAndExpressionAccess().getOperatorAndKeyword_1_0_1_0(), semanticObject.getOperator());
		feeder.accept(grammarAccess.getAndExpressionAccess().getRightComparisonParserRuleCall_1_1_0(), semanticObject.getRight());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     AndOperator returns BooleanOperator
	 *
	 * Constraint:
	 *     AND='and'
	 */
	protected void sequence_AndOperator(ISerializationContext context, BooleanOperator semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, KvisPackage.Literals.BOOLEAN_OPERATOR__AND) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, KvisPackage.Literals.BOOLEAN_OPERATOR__AND));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getAndOperatorAccess().getANDAndKeyword_0(), semanticObject.getAND());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Animation returns Animation
	 *
	 * Constraint:
	 *     (type=ID variable=VariableReference? attributeMappings+=AttributeMapping* condition=AndExpression?)
	 */
	protected void sequence_Animation(ISerializationContext context, Animation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     AttributeMapping returns AttributeMapping
	 *
	 * Constraint:
	 *     (attribute=ID (literal=Literal | (mappings+=Mapping mappings+=Mapping*)))
	 */
	protected void sequence_AttributeMapping(ISerializationContext context, AttributeMapping semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     AndExpression returns Comparison
	 *     AndExpression.AndExpression_1_0_0 returns Comparison
	 *     Comparison returns Comparison
	 *
	 * Constraint:
	 *     (left=VariableReference relation=CompareOperator (right=Literal | right=VariableReference))
	 */
	protected void sequence_Comparison(ISerializationContext context, Comparison semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Element returns Element
	 *
	 * Constraint:
	 *     (name=ID animations+=Animation+)
	 */
	protected void sequence_Element(ISerializationContext context, Element semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Event returns Event
	 *
	 * Constraint:
	 *     (event=DOMEvent element=ID)
	 */
	protected void sequence_Event(ISerializationContext context, Event semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, KvisPackage.Literals.EVENT__EVENT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, KvisPackage.Literals.EVENT__EVENT));
			if (transientValues.isValueTransient(semanticObject, KvisPackage.Literals.EVENT__ELEMENT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, KvisPackage.Literals.EVENT__ELEMENT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getEventAccess().getEventDOMEventEnumRuleCall_0_0(), semanticObject.getEvent());
		feeder.accept(grammarAccess.getEventAccess().getElementIDTerminalRuleCall_2_0(), semanticObject.getElement());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Interaction returns Interaction
	 *
	 * Constraint:
	 *     (event=Event? actions+=Action* condition=AndExpression?)
	 */
	protected void sequence_Interaction(ISerializationContext context, Interaction semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Interval returns Interval
	 *
	 * Constraint:
	 *     ((from=IntValue | from=FloatValue) (to=IntValue | to=FloatValue))
	 */
	protected void sequence_Interval(ISerializationContext context, Interval semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Literal returns Literal
	 *
	 * Constraint:
	 *     (value=SignedInt | value=SignedFloat | value=AnyValue)
	 */
	protected void sequence_Literal(ISerializationContext context, Literal semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Mapping returns Mapping
	 *
	 * Constraint:
	 *     (variableDomain=VariableDomain attributeDomain=AttributeDomain)
	 */
	protected void sequence_Mapping(ISerializationContext context, Mapping semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, KvisPackage.Literals.MAPPING__VARIABLE_DOMAIN) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, KvisPackage.Literals.MAPPING__VARIABLE_DOMAIN));
			if (transientValues.isValueTransient(semanticObject, KvisPackage.Literals.MAPPING__ATTRIBUTE_DOMAIN) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, KvisPackage.Literals.MAPPING__ATTRIBUTE_DOMAIN));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getMappingAccess().getVariableDomainVariableDomainParserRuleCall_0_0(), semanticObject.getVariableDomain());
		feeder.accept(grammarAccess.getMappingAccess().getAttributeDomainAttributeDomainParserRuleCall_2_0(), semanticObject.getAttributeDomain());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     ModelReference returns ModelReference
	 *
	 * Constraint:
	 *     name=ID
	 */
	protected void sequence_ModelReference(ISerializationContext context, ModelReference semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, KvisPackage.Literals.MODEL_REFERENCE__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, KvisPackage.Literals.MODEL_REFERENCE__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getModelReferenceAccess().getNameIDTerminalRuleCall_0_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     SignedFloat returns SignedFloat
	 *
	 * Constraint:
	 *     (sign=Sign? value=FLOAT)
	 */
	protected void sequence_SignedFloat(ISerializationContext context, SignedFloat semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     SignedInt returns SignedInt
	 *
	 * Constraint:
	 *     (sign=Sign? value=INT)
	 */
	protected void sequence_SignedInt(ISerializationContext context, SignedInt semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     SimulationAction returns Action
	 *
	 * Constraint:
	 *     operation=SimulationOperation
	 */
	protected void sequence_SimulationAction(ISerializationContext context, de.cau.cs.kieler.kvis.kvis.Action semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, KvisPackage.Literals.ACTION__OPERATION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, KvisPackage.Literals.ACTION__OPERATION));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getSimulationActionAccess().getOperationSimulationOperationEnumRuleCall_0_0(), semanticObject.getOperation());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Action returns Action
	 *
	 * Constraint:
	 *     ((variable=VariableReference value=Literal) | operation=SimulationOperation)
	 */
	protected void sequence_SimulationAction_VariableAssignment(ISerializationContext context, de.cau.cs.kieler.kvis.kvis.Action semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     VariableAssignment returns Action
	 *
	 * Constraint:
	 *     (variable=VariableReference value=Literal)
	 */
	protected void sequence_VariableAssignment(ISerializationContext context, de.cau.cs.kieler.kvis.kvis.Action semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, KvisPackage.Literals.ACTION__VARIABLE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, KvisPackage.Literals.ACTION__VARIABLE));
			if (transientValues.isValueTransient(semanticObject, KvisPackage.Literals.ACTION__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, KvisPackage.Literals.ACTION__VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getVariableAssignmentAccess().getVariableVariableReferenceParserRuleCall_0_0(), semanticObject.getVariable());
		feeder.accept(grammarAccess.getVariableAssignmentAccess().getValueLiteralParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     VariableDomain returns Domain
	 *     AttributeDomain returns Domain
	 *
	 * Constraint:
	 *     (value=Literal | range=Interval)
	 */
	protected void sequence_VariableDomain(ISerializationContext context, Domain semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     VariableReference returns VariableReference
	 *
	 * Constraint:
	 *     (model=ModelReference? name=ID indices+=INT*)
	 */
	protected void sequence_VariableReference(ISerializationContext context, VariableReference semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Visualization returns Visualization
	 *
	 * Constraint:
	 *     (image=STRING (elements+=Element | interactions+=Interaction)*)
	 */
	protected void sequence_Visualization(ISerializationContext context, Visualization semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}