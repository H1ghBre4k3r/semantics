/*
 * generated by Xtext
 */
package de.cau.cs.kieler.kicool.serializer;

import com.google.inject.Inject;
import de.cau.cs.kieler.annotations.Annotation;
import de.cau.cs.kieler.annotations.AnnotationsPackage;
import de.cau.cs.kieler.annotations.CommentAnnotation;
import de.cau.cs.kieler.annotations.Pragma;
import de.cau.cs.kieler.annotations.StringAnnotation;
import de.cau.cs.kieler.annotations.StringPragma;
import de.cau.cs.kieler.annotations.TypedStringAnnotation;
import de.cau.cs.kieler.annotations.serializer.AnnotationsSemanticSequencer;
import de.cau.cs.kieler.kicool.IntermediateReference;
import de.cau.cs.kieler.kicool.KVPair;
import de.cau.cs.kieler.kicool.KiCoolPackage;
import de.cau.cs.kieler.kicool.ProcessorAlternativeGroup;
import de.cau.cs.kieler.kicool.ProcessorGroup;
import de.cau.cs.kieler.kicool.ProcessorReference;
import de.cau.cs.kieler.kicool.ProcessorSystem;
import de.cau.cs.kieler.kicool.services.KiCoolGrammarAccess;
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
public abstract class AbstractKiCoolSemanticSequencer extends AnnotationsSemanticSequencer {

	@Inject
	private KiCoolGrammarAccess grammarAccess;
	
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
			case AnnotationsPackage.COMMENT_ANNOTATION:
				sequence_CommentAnnotation(context, (CommentAnnotation) semanticObject); 
				return; 
			case AnnotationsPackage.PRAGMA:
				sequence_PragmaTag(context, (Pragma) semanticObject); 
				return; 
			case AnnotationsPackage.STRING_ANNOTATION:
				if (rule == grammarAccess.getAnnotationRule()
						|| rule == grammarAccess.getValuedAnnotationRule()
						|| rule == grammarAccess.getKeyStringValueAnnotationRule()) {
					sequence_KeyStringValueAnnotation(context, (StringAnnotation) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getQuotedStringAnnotationRule()
						|| rule == grammarAccess.getQuotedKeyStringValueAnnotationRule()) {
					sequence_QuotedKeyStringValueAnnotation(context, (StringAnnotation) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getRestrictedTypeAnnotationRule()
						|| rule == grammarAccess.getRestrictedKeyStringValueAnnotationRule()) {
					sequence_RestrictedKeyStringValueAnnotation(context, (StringAnnotation) semanticObject); 
					return; 
				}
				else break;
			case AnnotationsPackage.STRING_PRAGMA:
				sequence_StringPragma(context, (StringPragma) semanticObject); 
				return; 
			case AnnotationsPackage.TYPED_STRING_ANNOTATION:
				if (rule == grammarAccess.getQuotedStringAnnotationRule()
						|| rule == grammarAccess.getQuotedTypedKeyStringValueAnnotationRule()) {
					sequence_QuotedTypedKeyStringValueAnnotation(context, (TypedStringAnnotation) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getRestrictedTypeAnnotationRule()
						|| rule == grammarAccess.getRestrictedTypedKeyStringValueAnnotationRule()) {
					sequence_RestrictedTypedKeyStringValueAnnotation(context, (TypedStringAnnotation) semanticObject); 
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
		else if (epackage == KiCoolPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case KiCoolPackage.INTERMEDIATE_REFERENCE:
				sequence_IntermediateReference(context, (IntermediateReference) semanticObject); 
				return; 
			case KiCoolPackage.KV_PAIR:
				sequence_KVPair(context, (KVPair) semanticObject); 
				return; 
			case KiCoolPackage.PROCESSOR_ALTERNATIVE_GROUP:
				sequence_ProcessorAlternativeGroup(context, (ProcessorAlternativeGroup) semanticObject); 
				return; 
			case KiCoolPackage.PROCESSOR_GROUP:
				sequence_ProcessorGroup(context, (ProcessorGroup) semanticObject); 
				return; 
			case KiCoolPackage.PROCESSOR_REFERENCE:
				sequence_Processor(context, (ProcessorReference) semanticObject); 
				return; 
			case KiCoolPackage.PROCESSOR_SYSTEM:
				sequence_ProcessorSystem(context, (ProcessorSystem) semanticObject); 
				return; 
			case KiCoolPackage.SYSTEM:
				sequence_System(context, (de.cau.cs.kieler.kicool.System) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Contexts:
	 *     IntermediateReference returns IntermediateReference
	 *
	 * Constraint:
	 *     (id=QualifiedID alias=EString?)
	 */
	protected void sequence_IntermediateReference(ISerializationContext context, IntermediateReference semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     KVPair returns KVPair
	 *
	 * Constraint:
	 *     (key=EString isKeyValue?='key'? value=EStringAllTypes)
	 */
	protected void sequence_KVPair(ISerializationContext context, KVPair semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ProcessorAlternativeGroup returns ProcessorAlternativeGroup
	 *
	 * Constraint:
	 *     (label=EString? processors+=ProcessorGroup processors+=ProcessorGroup+)
	 */
	protected void sequence_ProcessorAlternativeGroup(ISerializationContext context, ProcessorAlternativeGroup semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ProcessorGroup returns ProcessorGroup
	 *
	 * Constraint:
	 *     (label=EString? (processors+=Processor | processors+=ProcessorSystem | processors+=ProcessorAlternativeGroup | processors+=ProcessorGroup)+)
	 */
	protected void sequence_ProcessorGroup(ISerializationContext context, ProcessorGroup semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ProcessorSystem returns ProcessorSystem
	 *
	 * Constraint:
	 *     id=QualifiedID
	 */
	protected void sequence_ProcessorSystem(ISerializationContext context, ProcessorSystem semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, KiCoolPackage.Literals.PROCESSOR_ENTRY__ID) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, KiCoolPackage.Literals.PROCESSOR_ENTRY__ID));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getProcessorSystemAccess().getIdQualifiedIDParserRuleCall_0(), semanticObject.getId());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Processor returns ProcessorReference
	 *
	 * Constraint:
	 *     (presets+=KVPair* id=QualifiedID metric=[IntermediateReference|QualifiedID]? postsets+=KVPair*)
	 */
	protected void sequence_Processor(ISerializationContext context, ProcessorReference semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     System returns System
	 *
	 * Constraint:
	 *     (
	 *         id=QualifiedID 
	 *         version=INT 
	 *         label=EString 
	 *         inputClass=ID? 
	 *         intermediates+=IntermediateReference* 
	 *         processors=ProcessorGroup
	 *     )
	 */
	protected void sequence_System(ISerializationContext context, de.cau.cs.kieler.kicool.System semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}