package de.cau.cs.kieler.s.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import de.cau.cs.kieler.core.annotations.Annotation;
import de.cau.cs.kieler.core.annotations.AnnotationsPackage;
import de.cau.cs.kieler.core.annotations.BooleanAnnotation;
import de.cau.cs.kieler.core.annotations.FloatAnnotation;
import de.cau.cs.kieler.core.annotations.ImportAnnotation;
import de.cau.cs.kieler.core.annotations.IntAnnotation;
import de.cau.cs.kieler.core.annotations.StringAnnotation;
import de.cau.cs.kieler.core.annotations.TypedStringAnnotation;
import de.cau.cs.kieler.core.kexpressions.BoolValue;
import de.cau.cs.kieler.core.kexpressions.DoubleValue;
import de.cau.cs.kieler.core.kexpressions.FloatValue;
import de.cau.cs.kieler.core.kexpressions.IntValue;
import de.cau.cs.kieler.core.kexpressions.KExpressionsPackage;
import de.cau.cs.kieler.core.kexpressions.OperatorExpression;
import de.cau.cs.kieler.core.kexpressions.TextExpression;
import de.cau.cs.kieler.core.kexpressions.ValuedObject;
import de.cau.cs.kieler.core.kexpressions.ValuedObjectReference;
import de.cau.cs.kieler.core.kexpressions.serializer.KExpressionsSemanticSequencer;
import de.cau.cs.kieler.s.s.Abort;
import de.cau.cs.kieler.s.s.Assignment;
import de.cau.cs.kieler.s.s.Await;
import de.cau.cs.kieler.s.s.Emit;
import de.cau.cs.kieler.s.s.Fork;
import de.cau.cs.kieler.s.s.Halt;
import de.cau.cs.kieler.s.s.HostCodeInstruction;
import de.cau.cs.kieler.s.s.If;
import de.cau.cs.kieler.s.s.Join;
import de.cau.cs.kieler.s.s.LocalSignal;
import de.cau.cs.kieler.s.s.Pause;
import de.cau.cs.kieler.s.s.Prio;
import de.cau.cs.kieler.s.s.Program;
import de.cau.cs.kieler.s.s.SPackage;
import de.cau.cs.kieler.s.s.State;
import de.cau.cs.kieler.s.s.Term;
import de.cau.cs.kieler.s.s.Trans;
import de.cau.cs.kieler.s.services.SGrammarAccess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticNodeProvider.INodesForEObjectProvider;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public abstract class AbstractSSemanticSequencer extends KExpressionsSemanticSequencer {

	@Inject
	private SGrammarAccess grammarAccess;
	
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == AnnotationsPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case AnnotationsPackage.ANNOTATION:
				if(context == grammarAccess.getAnnotationRule() ||
				   context == grammarAccess.getTagAnnotationRule()) {
					sequence_TagAnnotation(context, (Annotation) semanticObject); 
					return; 
				}
				else break;
			case AnnotationsPackage.BOOLEAN_ANNOTATION:
				if(context == grammarAccess.getAnnotationRule() ||
				   context == grammarAccess.getKeyBooleanValueAnnotationRule() ||
				   context == grammarAccess.getValuedAnnotationRule()) {
					sequence_KeyBooleanValueAnnotation(context, (BooleanAnnotation) semanticObject); 
					return; 
				}
				else break;
			case AnnotationsPackage.FLOAT_ANNOTATION:
				if(context == grammarAccess.getAnnotationRule() ||
				   context == grammarAccess.getKeyFloatValueAnnotationRule() ||
				   context == grammarAccess.getValuedAnnotationRule()) {
					sequence_KeyFloatValueAnnotation(context, (FloatAnnotation) semanticObject); 
					return; 
				}
				else break;
			case AnnotationsPackage.IMPORT_ANNOTATION:
				if(context == grammarAccess.getImportAnnotationRule()) {
					sequence_ImportAnnotation(context, (ImportAnnotation) semanticObject); 
					return; 
				}
				else break;
			case AnnotationsPackage.INT_ANNOTATION:
				if(context == grammarAccess.getAnnotationRule() ||
				   context == grammarAccess.getKeyIntValueAnnotationRule() ||
				   context == grammarAccess.getValuedAnnotationRule()) {
					sequence_KeyIntValueAnnotation(context, (IntAnnotation) semanticObject); 
					return; 
				}
				else break;
			case AnnotationsPackage.STRING_ANNOTATION:
				if(context == grammarAccess.getAnnotationRule() ||
				   context == grammarAccess.getValuedAnnotationRule()) {
					sequence_Annotation_CommentAnnotation_KeyStringValueAnnotation(context, (StringAnnotation) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getCommentAnnotationRule()) {
					sequence_CommentAnnotation(context, (StringAnnotation) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getKeyStringValueAnnotationRule()) {
					sequence_KeyStringValueAnnotation(context, (StringAnnotation) semanticObject); 
					return; 
				}
				else break;
			case AnnotationsPackage.TYPED_STRING_ANNOTATION:
				if(context == grammarAccess.getAnnotationRule() ||
				   context == grammarAccess.getTypedKeyStringValueAnnotationRule() ||
				   context == grammarAccess.getValuedAnnotationRule()) {
					sequence_TypedKeyStringValueAnnotation(context, (TypedStringAnnotation) semanticObject); 
					return; 
				}
				else break;
			}
		else if(semanticObject.eClass().getEPackage() == KExpressionsPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case KExpressionsPackage.BOOL_VALUE:
				if(context == grammarAccess.getAddExpressionRule() ||
				   context == grammarAccess.getAddExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getAtomicExpressionRule() ||
				   context == grammarAccess.getAtomicValuedExpressionRule() ||
				   context == grammarAccess.getBoolExpressionRule() ||
				   context == grammarAccess.getBoolValueRule() ||
				   context == grammarAccess.getCompareOperationRule() ||
				   context == grammarAccess.getCompareOperationAccess().getOperatorExpressionSubExpressionsAction_0_1_0() ||
				   context == grammarAccess.getDivExpressionRule() ||
				   context == grammarAccess.getDivExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getModExpressionRule() ||
				   context == grammarAccess.getModExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getMultExpressionRule() ||
				   context == grammarAccess.getMultExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getNegExpressionRule() ||
				   context == grammarAccess.getNotExpressionRule() ||
				   context == grammarAccess.getNotOrValuedExpressionRule() ||
				   context == grammarAccess.getOrAndExpressionRule() ||
				   context == grammarAccess.getOrAndExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0_0() ||
				   context == grammarAccess.getOrAndExpressionAccess().getOperatorExpressionSubExpressionsAction_1_1_0() ||
				   context == grammarAccess.getRootRule() ||
				   context == grammarAccess.getSExpressionRule() ||
				   context == grammarAccess.getSubExpressionRule() ||
				   context == grammarAccess.getSubExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getValuedExpressionRule()) {
					sequence_BoolValue(context, (BoolValue) semanticObject); 
					return; 
				}
				else break;
			case KExpressionsPackage.DOUBLE_VALUE:
				if(context == grammarAccess.getAddExpressionRule() ||
				   context == grammarAccess.getAddExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getAtomicValuedExpressionRule() ||
				   context == grammarAccess.getCompareOperationAccess().getOperatorExpressionSubExpressionsAction_0_1_0() ||
				   context == grammarAccess.getDivExpressionRule() ||
				   context == grammarAccess.getDivExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getDoubleValueRule() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getModExpressionRule() ||
				   context == grammarAccess.getModExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getMultExpressionRule() ||
				   context == grammarAccess.getMultExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getNegExpressionRule() ||
				   context == grammarAccess.getNotOrValuedExpressionRule() ||
				   context == grammarAccess.getRootRule() ||
				   context == grammarAccess.getSExpressionRule() ||
				   context == grammarAccess.getSubExpressionRule() ||
				   context == grammarAccess.getSubExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getValuedExpressionRule()) {
					sequence_DoubleValue(context, (DoubleValue) semanticObject); 
					return; 
				}
				else break;
			case KExpressionsPackage.FLOAT_VALUE:
				if(context == grammarAccess.getAddExpressionRule() ||
				   context == grammarAccess.getAddExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getAtomicValuedExpressionRule() ||
				   context == grammarAccess.getCompareOperationAccess().getOperatorExpressionSubExpressionsAction_0_1_0() ||
				   context == grammarAccess.getDivExpressionRule() ||
				   context == grammarAccess.getDivExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getFloatValueRule() ||
				   context == grammarAccess.getModExpressionRule() ||
				   context == grammarAccess.getModExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getMultExpressionRule() ||
				   context == grammarAccess.getMultExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getNegExpressionRule() ||
				   context == grammarAccess.getNotOrValuedExpressionRule() ||
				   context == grammarAccess.getRootRule() ||
				   context == grammarAccess.getSExpressionRule() ||
				   context == grammarAccess.getSubExpressionRule() ||
				   context == grammarAccess.getSubExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getValuedExpressionRule()) {
					sequence_FloatValue(context, (FloatValue) semanticObject); 
					return; 
				}
				else break;
			case KExpressionsPackage.INT_VALUE:
				if(context == grammarAccess.getAddExpressionRule() ||
				   context == grammarAccess.getAddExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getAtomicValuedExpressionRule() ||
				   context == grammarAccess.getCompareOperationAccess().getOperatorExpressionSubExpressionsAction_0_1_0() ||
				   context == grammarAccess.getDivExpressionRule() ||
				   context == grammarAccess.getDivExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getIntValueRule() ||
				   context == grammarAccess.getModExpressionRule() ||
				   context == grammarAccess.getModExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getMultExpressionRule() ||
				   context == grammarAccess.getMultExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getNegExpressionRule() ||
				   context == grammarAccess.getNotOrValuedExpressionRule() ||
				   context == grammarAccess.getRootRule() ||
				   context == grammarAccess.getSExpressionRule() ||
				   context == grammarAccess.getSubExpressionRule() ||
				   context == grammarAccess.getSubExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getValuedExpressionRule()) {
					sequence_IntValue(context, (IntValue) semanticObject); 
					return; 
				}
				else break;
			case KExpressionsPackage.OPERATOR_EXPRESSION:
				if(context == grammarAccess.getAddExpressionRule() ||
				   context == grammarAccess.getAddExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getAtomicValuedExpressionRule() ||
				   context == grammarAccess.getCompareOperationAccess().getOperatorExpressionSubExpressionsAction_0_1_0() ||
				   context == grammarAccess.getDivExpressionRule() ||
				   context == grammarAccess.getDivExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getModExpressionRule() ||
				   context == grammarAccess.getModExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getMultExpressionRule() ||
				   context == grammarAccess.getMultExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getNegExpressionRule() ||
				   context == grammarAccess.getNotOrValuedExpressionRule() ||
				   context == grammarAccess.getRootRule() ||
				   context == grammarAccess.getSExpressionRule() ||
				   context == grammarAccess.getSubExpressionRule() ||
				   context == grammarAccess.getSubExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getValuedExpressionRule()) {
					sequence_AddExpression_CompareOperation_DivExpression_Expression_ModExpression_MultExpression_NegExpression_NotExpression_OrAndExpression_SubExpression_ValuedObjectTestExpression(context, (OperatorExpression) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getAtomicExpressionRule() ||
				   context == grammarAccess.getBoolExpressionRule() ||
				   context == grammarAccess.getCompareOperationRule() ||
				   context == grammarAccess.getNotExpressionRule() ||
				   context == grammarAccess.getOrAndExpressionRule() ||
				   context == grammarAccess.getOrAndExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0_0() ||
				   context == grammarAccess.getOrAndExpressionAccess().getOperatorExpressionSubExpressionsAction_1_1_0()) {
					sequence_CompareOperation_NotExpression_OrAndExpression_ValuedObjectTestExpression(context, (OperatorExpression) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getValuedObjectTestExpressionRule()) {
					sequence_ValuedObjectTestExpression(context, (OperatorExpression) semanticObject); 
					return; 
				}
				else break;
			case KExpressionsPackage.TEXT_EXPRESSION:
				if(context == grammarAccess.getAddExpressionRule() ||
				   context == grammarAccess.getAddExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getAtomicExpressionRule() ||
				   context == grammarAccess.getAtomicValuedExpressionRule() ||
				   context == grammarAccess.getBoolExpressionRule() ||
				   context == grammarAccess.getCompareOperationRule() ||
				   context == grammarAccess.getCompareOperationAccess().getOperatorExpressionSubExpressionsAction_0_1_0() ||
				   context == grammarAccess.getDivExpressionRule() ||
				   context == grammarAccess.getDivExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getModExpressionRule() ||
				   context == grammarAccess.getModExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getMultExpressionRule() ||
				   context == grammarAccess.getMultExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getNegExpressionRule() ||
				   context == grammarAccess.getNotExpressionRule() ||
				   context == grammarAccess.getNotOrValuedExpressionRule() ||
				   context == grammarAccess.getOrAndExpressionRule() ||
				   context == grammarAccess.getOrAndExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0_0() ||
				   context == grammarAccess.getOrAndExpressionAccess().getOperatorExpressionSubExpressionsAction_1_1_0() ||
				   context == grammarAccess.getRootRule() ||
				   context == grammarAccess.getSExpressionRule() ||
				   context == grammarAccess.getSubExpressionRule() ||
				   context == grammarAccess.getSubExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getTextExpressionRule() ||
				   context == grammarAccess.getValuedExpressionRule()) {
					sequence_TextExpression(context, (TextExpression) semanticObject); 
					return; 
				}
				else break;
			case KExpressionsPackage.VALUED_OBJECT:
				if(context == grammarAccess.getValuedObjectRule()) {
					sequence_ValuedObject(context, (ValuedObject) semanticObject); 
					return; 
				}
				else break;
			case KExpressionsPackage.VALUED_OBJECT_REFERENCE:
				if(context == grammarAccess.getAddExpressionRule() ||
				   context == grammarAccess.getAddExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getAtomicExpressionRule() ||
				   context == grammarAccess.getAtomicValuedExpressionRule() ||
				   context == grammarAccess.getBoolExpressionRule() ||
				   context == grammarAccess.getCompareOperationRule() ||
				   context == grammarAccess.getCompareOperationAccess().getOperatorExpressionSubExpressionsAction_0_1_0() ||
				   context == grammarAccess.getDivExpressionRule() ||
				   context == grammarAccess.getDivExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getModExpressionRule() ||
				   context == grammarAccess.getModExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getMultExpressionRule() ||
				   context == grammarAccess.getMultExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getNegExpressionRule() ||
				   context == grammarAccess.getNotExpressionRule() ||
				   context == grammarAccess.getNotOrValuedExpressionRule() ||
				   context == grammarAccess.getOrAndExpressionRule() ||
				   context == grammarAccess.getOrAndExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0_0() ||
				   context == grammarAccess.getOrAndExpressionAccess().getOperatorExpressionSubExpressionsAction_1_1_0() ||
				   context == grammarAccess.getRootRule() ||
				   context == grammarAccess.getSExpressionRule() ||
				   context == grammarAccess.getSubExpressionRule() ||
				   context == grammarAccess.getSubExpressionAccess().getOperatorExpressionSubExpressionsAction_1_0() ||
				   context == grammarAccess.getValuedExpressionRule() ||
				   context == grammarAccess.getValuedObjectReferenceRule() ||
				   context == grammarAccess.getValuedObjectTestExpressionRule()) {
					sequence_ValuedObjectReference(context, (ValuedObjectReference) semanticObject); 
					return; 
				}
				else break;
			}
		else if(semanticObject.eClass().getEPackage() == SPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case SPackage.ABORT:
				if(context == grammarAccess.getAbortRule() ||
				   context == grammarAccess.getInstructionRule()) {
					sequence_Abort(context, (Abort) semanticObject); 
					return; 
				}
				else break;
			case SPackage.ASSIGNMENT:
				if(context == grammarAccess.getAssignmentRule() ||
				   context == grammarAccess.getInstructionRule()) {
					sequence_Assignment(context, (Assignment) semanticObject); 
					return; 
				}
				else break;
			case SPackage.AWAIT:
				if(context == grammarAccess.getAwaitRule() ||
				   context == grammarAccess.getInstructionRule()) {
					sequence_Await(context, (Await) semanticObject); 
					return; 
				}
				else break;
			case SPackage.EMIT:
				if(context == grammarAccess.getEmitRule() ||
				   context == grammarAccess.getInstructionRule()) {
					sequence_Emit(context, (Emit) semanticObject); 
					return; 
				}
				else break;
			case SPackage.FORK:
				if(context == grammarAccess.getForkRule() ||
				   context == grammarAccess.getInstructionRule()) {
					sequence_Fork(context, (Fork) semanticObject); 
					return; 
				}
				else break;
			case SPackage.HALT:
				if(context == grammarAccess.getHaltRule() ||
				   context == grammarAccess.getInstructionRule()) {
					sequence_Halt(context, (Halt) semanticObject); 
					return; 
				}
				else break;
			case SPackage.HOST_CODE_INSTRUCTION:
				if(context == grammarAccess.getHostCodeInstructionRule() ||
				   context == grammarAccess.getInstructionRule()) {
					sequence_HostCodeInstruction(context, (HostCodeInstruction) semanticObject); 
					return; 
				}
				else break;
			case SPackage.IF:
				if(context == grammarAccess.getIfRule() ||
				   context == grammarAccess.getInstructionRule()) {
					sequence_If(context, (If) semanticObject); 
					return; 
				}
				else break;
			case SPackage.JOIN:
				if(context == grammarAccess.getInstructionRule() ||
				   context == grammarAccess.getJoinRule()) {
					sequence_Join(context, (Join) semanticObject); 
					return; 
				}
				else break;
			case SPackage.LOCAL_SIGNAL:
				if(context == grammarAccess.getInstructionRule() ||
				   context == grammarAccess.getLocalSignalRule()) {
					sequence_LocalSignal(context, (LocalSignal) semanticObject); 
					return; 
				}
				else break;
			case SPackage.PAUSE:
				if(context == grammarAccess.getInstructionRule() ||
				   context == grammarAccess.getPauseRule()) {
					sequence_Pause(context, (Pause) semanticObject); 
					return; 
				}
				else break;
			case SPackage.PRIO:
				if(context == grammarAccess.getInstructionRule() ||
				   context == grammarAccess.getPrioRule()) {
					sequence_Prio(context, (Prio) semanticObject); 
					return; 
				}
				else break;
			case SPackage.PROGRAM:
				if(context == grammarAccess.getProgramRule()) {
					sequence_Program(context, (Program) semanticObject); 
					return; 
				}
				else break;
			case SPackage.STATE:
				if(context == grammarAccess.getStateRule()) {
					sequence_State(context, (State) semanticObject); 
					return; 
				}
				else break;
			case SPackage.TERM:
				if(context == grammarAccess.getInstructionRule() ||
				   context == grammarAccess.getTermRule()) {
					sequence_Term(context, (Term) semanticObject); 
					return; 
				}
				else break;
			case SPackage.TRANS:
				if(context == grammarAccess.getInstructionRule() ||
				   context == grammarAccess.getTransRule()) {
					sequence_Trans(context, (Trans) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     (continuation=[State|ID]?)
	 */
	protected void sequence_Abort(EObject context, Abort semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (variable=[ValuedObject|ID] expression=Expression)
	 */
	protected void sequence_Assignment(EObject context, Assignment semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, SPackage.Literals.ASSIGNMENT__VARIABLE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, SPackage.Literals.ASSIGNMENT__VARIABLE));
			if(transientValues.isValueTransient(semanticObject, SPackage.Literals.ASSIGNMENT__EXPRESSION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, SPackage.Literals.ASSIGNMENT__EXPRESSION));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getAssignmentAccess().getVariableValuedObjectIDTerminalRuleCall_0_0_1(), semanticObject.getVariable());
		feeder.accept(grammarAccess.getAssignmentAccess().getExpressionExpressionParserRuleCall_2_0(), semanticObject.getExpression());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (signal=[ValuedObject|ID] continuation=[State|ID]?)
	 */
	protected void sequence_Await(EObject context, Await semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (signal=[ValuedObject|ID] value=SExpression? continuation=[State|ID]?)
	 */
	protected void sequence_Emit(EObject context, Emit semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (continuation=[State|ID] priority=INT)
	 */
	protected void sequence_Fork(EObject context, Fork semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, SPackage.Literals.FORK__CONTINUATION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, SPackage.Literals.FORK__CONTINUATION));
			if(transientValues.isValueTransient(semanticObject, SPackage.Literals.FORK__PRIORITY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, SPackage.Literals.FORK__PRIORITY));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getForkAccess().getContinuationStateIDTerminalRuleCall_2_0_1(), semanticObject.getContinuation());
		feeder.accept(grammarAccess.getForkAccess().getPriorityINTTerminalRuleCall_4_0(), semanticObject.getPriority());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (continuation=[State|ID]?)
	 */
	protected void sequence_Halt(EObject context, Halt semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     hostCode=HOSTCODE
	 */
	protected void sequence_HostCodeInstruction(EObject context, HostCodeInstruction semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, SPackage.Literals.HOST_CODE_INSTRUCTION__HOST_CODE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, SPackage.Literals.HOST_CODE_INSTRUCTION__HOST_CODE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getHostCodeInstructionAccess().getHostCodeHOSTCODETerminalRuleCall_0(), semanticObject.getHostCode());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (expression=SExpression continuation=[State|ID]? instructions+=Instruction*)
	 */
	protected void sequence_If(EObject context, If semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (continuation=[State|ID]?)
	 */
	protected void sequence_Join(EObject context, Join semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     signal=[ValuedObject|ID]
	 */
	protected void sequence_LocalSignal(EObject context, LocalSignal semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, SPackage.Literals.LOCAL_SIGNAL__SIGNAL) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, SPackage.Literals.LOCAL_SIGNAL__SIGNAL));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getLocalSignalAccess().getSignalValuedObjectIDTerminalRuleCall_2_0_1(), semanticObject.getSignal());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (continuation=[State|ID]?)
	 */
	protected void sequence_Pause(EObject context, Pause semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (priority=INT continuation=[State|ID]?)
	 */
	protected void sequence_Prio(EObject context, Prio semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID priority=INT (valuedObjects+=ValuedObject valuedObjects+=ValuedObject*)? globalHostCodeInstruction=HOSTCODE? states+=State+)
	 */
	protected void sequence_Program(EObject context, Program semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID valuedObjects+=ValuedObject* instructions+=Instruction*)
	 */
	protected void sequence_State(EObject context, State semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (continuation=[State|ID]?)
	 */
	protected void sequence_Term(EObject context, Term semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     continuation=[State|ID]
	 */
	protected void sequence_Trans(EObject context, Trans semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, SPackage.Literals.TRANS__CONTINUATION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, SPackage.Literals.TRANS__CONTINUATION));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getTransAccess().getContinuationStateIDTerminalRuleCall_2_0_1(), semanticObject.getContinuation());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         annotations+=Annotation* 
	 *         input?='input'? 
	 *         output?='output'? 
	 *         static?='static'? 
	 *         signal?='signal'? 
	 *         type=ValueType? 
	 *         name=ID 
	 *         initialValue=Expression? 
	 *         combineOperator=CombineOperator?
	 *     )
	 */
	protected void sequence_ValuedObject(EObject context, ValuedObject semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
}
