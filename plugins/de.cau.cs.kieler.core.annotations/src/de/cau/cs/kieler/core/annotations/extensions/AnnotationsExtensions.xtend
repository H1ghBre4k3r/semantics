package de.cau.cs.kieler.core.annotations.extensions

import de.cau.cs.kieler.core.annotations.AnnotationsFactory
import de.cau.cs.kieler.core.annotations.Annotation
import de.cau.cs.kieler.core.annotations.Annotatable
import de.cau.cs.kieler.core.annotations.StringAnnotation
import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import de.cau.cs.kieler.core.annotations.CommentAnnotation
import java.util.List
import de.cau.cs.kieler.core.annotations.TypedStringAnnotation

/**
 * Annotations extensions
 * 
 * @author ssm
 */
class AnnotationsExtensions {
	
	def public Annotation getAnnotation(Annotatable annotatable, String name) {
		annotatable.getAnnotations(name)?.head
	} 

    def public Iterable<Annotation> getAnnotations(Annotatable annotatable, String name) {
        annotatable.getAllAnnotations(name)
    } 
	
	
	def public String getStringAnnotationValue(Annotatable annotatable, String name) {
		val annotation = annotatable.getAnnotation(name)
		if (annotation != null) 
			(annotation as StringAnnotation).values.head
		else
			""
	}

	def public Annotatable createStringAnnotation(Annotatable source, String name, String value) {
		source => [ annotations += name.createStringAnnotation(value) ]
	}
	
	def public Annotation createStringAnnotation(String name, String value) {
		AnnotationsFactory::eINSTANCE.createStringAnnotation => [
			it.name = name
			it.values += value
		]
	}
		
	def public void copyAnnotations(Annotatable source, Annotatable target) {
	    source.annotations.forEach[
	        target.annotations += it.copy
	    ]
	}
	
	def public boolean hasAnnotation(Annotatable annotatable, String name) {
		!annotatable.annotations.nullOrEmpty && !annotatable.annotations.filter[ it.name == name].empty
	}
	
	
	def public boolean hasCommentAnnotation(Annotatable annotatable) {
	   !annotatable.annotations.nullOrEmpty && !annotatable.annotations.filter(typeof(CommentAnnotation)).empty    
	}
	
	def public List<CommentAnnotation> getCommentAnnotations(Annotatable annotatable) {
	    annotatable.annotations.filter(typeof(CommentAnnotation)).toList
	}
	
	
	def public boolean hasTypedAnnotation(Annotatable annotatable) {
       !annotatable.annotations.nullOrEmpty && !annotatable.typedAnnotations.empty    
    }
    
    def public List<TypedStringAnnotation> getTypedAnnotations(Annotatable annotatable) {
        annotatable.annotations.filter(typeof(TypedStringAnnotation)).toList
    }
    
    def public List<TypedStringAnnotation> getTypedAnnotations(Annotatable annotatable, String name) {
        annotatable.getAllAnnotations(name).filter(typeof(TypedStringAnnotation)).toList
    }
    
    
    def asStringAnnotation(Annotation annotation) {
        annotation as StringAnnotation
    }
}