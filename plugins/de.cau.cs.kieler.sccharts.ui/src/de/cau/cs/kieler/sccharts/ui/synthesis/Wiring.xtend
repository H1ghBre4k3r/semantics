/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright ${year} by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.sccharts.ui.synthesis

import de.cau.cs.kieler.kexpressions.Expression
import org.eclipse.xtend.lib.annotations.Accessors
import de.cau.cs.kieler.kexpressions.keffects.Assignment
import java.util.List
import de.cau.cs.kieler.kexpressions.Value
import de.cau.cs.kieler.kexpressions.ValuedObjectReference
import com.google.inject.Inject
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsValuedObjectExtensions
import de.cau.cs.kieler.kexpressions.VariableDeclaration
import de.cau.cs.kieler.core.model.Pair
import de.cau.cs.kieler.kexpressions.ValuedObject
import de.cau.cs.kieler.kexpressions.OperatorExpression
import de.cau.cs.kieler.kexpressions.ReferenceDeclaration
import de.cau.cs.kieler.kexpressions.VectorValue
import de.cau.cs.kieler.kexpressions.kext.extensions.KExtDeclarationExtensions
import de.cau.cs.kieler.kexpressions.kext.DeclarationScope
import de.cau.cs.kieler.kexpressions.IgnoreValue

/**
 * The class models a wiring instance if given a list of equations (assignments).
 * 
 * @author ssm
 * @kieler.design 2017-09-26 proposed
 * @kieler.rating 2017-09-26 proposed yellow
 *
 */
class Wiring {
    
    @Inject extension KExpressionsValuedObjectExtensions
    @Inject extension KExtDeclarationExtensions
    
    /** The single wires */  
    @Accessors val wires = <Wire> newLinkedHashSet
    
    /** Source-Sink Wires */ 
    val index = <Pair<Expression, Expression>, Wire> newHashMap
    /** Storage for valuedObject, subObject expressions */
    val semanticReferenceIndex = <Pair<ValuedObject, ValuedObject>, Expression> newHashMap

    /** Main method that receives the list of equations. */
    def createWires(List<Assignment> equations) {
        for (eq : equations) {
            eq.createWires
        }
    }
    
    /** Create a wiring for a a single assignment. */
    def void createWires(Assignment equation) {
        equation.expression.create(equation.reference)
    }
    
    /** 
     * This method creates wires between a source and a sink. Usually only one wire is created, but there are exceptions.
     * Source's and sink's class type define the characteristics of the wire. 
     * For example, if a wire's source is a literal, the wire is always seen as an interface and hence always
     * creates a "source" node.
     * As another example, if the source is a vector of values then create a wire for each of the values and 
     * connect them to the sink.
     */
    private def Wire create(Expression source, Expression sink) {
        if (source instanceof VectorValue) {
            // Vectors a treated differently because they potentially result in more than one wire.
            return source.resolveVectorValueSource(sink)
        }
        
        val wire = createWire(source, sink)
        
        // Dependening on the source's (sink's) type, we have to set wire characteristics.
        // In the case of an operator expression, we also have to create more wires.
        switch(source) {
            Value: wire.sourceIsInterface = true
            ValuedObjectReference: {
                val declaration = source.valuedObject.declaration
                if (declaration instanceof VariableDeclaration) {
                    wire.sourceIsInterface = declaration.input
                } else if (declaration instanceof ReferenceDeclaration) {
                    wire.semanticSourceReferenceDeclaration = declaration 
                }
            }
            OperatorExpression: {
                for (expression : source.subExpressions) {
                    expression.create(source)
                }    
            }
        }
        
        switch(sink) {
            ValuedObjectReference: {
                val declaration = sink.valuedObject.declaration
                if (declaration instanceof VariableDeclaration) {
                    wire.sinkIsInterface = declaration.output
                } else if (declaration instanceof ReferenceDeclaration) {
                    wire.semanticSinkReferenceDeclaration = declaration 
                }
            }
        }
        
        wire 
    }
    
    /** 
     * This method splits up vector values and creates wires for each value.
     * This is only possible if the sink is a referenced object (with more input ports).
     */
    protected def Wire resolveVectorValueSource(VectorValue source, Expression sink) {
        var sinkTarget = sink
        val isReferenceSink = sink.getReferenceDeclarationReference instanceof DeclarationScope
        val valuedObjectList = if (isReferenceSink) sink.getReferenceDeclarationReference.asDeclarationScope.valuedObjects.filter[ input ].toList 
            else null
        var i = 0
        for (vector : source.values) {
            if (!(vector instanceof IgnoreValue)) {
                if (isReferenceSink) {
                    if (i < valuedObjectList.size) {
                        sinkTarget = sink.asValuedObjectReference.valuedObject.reference 
                        sinkTarget.asValuedObjectReference.subReference = valuedObjectList.get(i).reference
                     } else {
                         sinkTarget = sink
                     }
                }
                vector.create(sinkTarget)
            }
            i++
        }
        return null
    }

    /** 
     * This method create a single wire between a source and a sink.
     * Before creating a wire, the method checks if such a wire already exists and returns it if so.
     * Additionally, even if such a wire does not exist, it is possible that a semantic source or sink of the wire exists.
     * For example, if you assign "A = I" and then "O = A" then both A and O will have I as source.
     * The method redirects semantic sources and sinks automatically. 
     */
    protected def Wire createWire(Expression source, Expression sink) {
        val oldWire = index.get(new Pair<Expression, Expression>(source, sink))
        if (oldWire != null) return oldWire
        
        val wire = new Wire(source, sink, this) 
        
        var semanticSource = source
        var semanticSink = sink
        if (source instanceof ValuedObjectReference) {
            var existingSemanticReference = semanticReferenceIndex.get(new Pair<ValuedObject, ValuedObject>(source.valuedObject, null))
            if (existingSemanticReference != null) {
                // Directly connect the semantic source to the source of an already existing wire. 
                if (existingSemanticReference instanceof ValuedObjectReference) {
                    val existingWire = existingSemanticReference.getSemanticSinkWire
                    if (existingWire != null && source.subReference == null) {
                        // We don't want to use the existing semantic wire to the node if the subreference points to 
                        // a referenced node. Therefore, only redirect to the existing wire if there is no subreference. 
                        existingSemanticReference = existingWire.semanticSource
                    }
                }
                semanticSource = existingSemanticReference
            } else {
                semanticReferenceIndex.put(new Pair<ValuedObject, ValuedObject>(source.valuedObject, null), source)
            }
        } 
        if (sink instanceof ValuedObjectReference) {
            val srValuedObject = null // if (sink.subReference != null) sink.subReference.valuedObject else null
            val existingSemanticReference = semanticReferenceIndex.get(new Pair<ValuedObject, ValuedObject>(sink.valuedObject, srValuedObject))
            if (existingSemanticReference != null) {
                semanticSink = existingSemanticReference
            } else {
                semanticReferenceIndex.put(new Pair<ValuedObject, ValuedObject>(sink.valuedObject, srValuedObject), sink)
            }
        }
        wire.semanticSource = semanticSource
        wire.semanticSink = semanticSink
        
        wires += wire
        index.put(new Pair<Expression, Expression>(source, sink), wire)
        
        return wire
    }
    
    /** Retrieve an existing wire from the index. */
    def protected Wire getExistingWire(Expression source, Expression target) {
        return index.get(new Pair<Expression, Expression>(source, target))
    } 
    
    /** Retrieve the first wire with a matching semantic source. */
    def Wire getSemanticSourceWire(ValuedObjectReference valuedObjectReference) {
        val expression = semanticReferenceIndex.get(new Pair<ValuedObject, ValuedObject>(valuedObjectReference.valuedObject, null))
        for (wire : wires) {
            if (wire.semanticSource == expression) return wire
        }
        return null
    }

    /** Retrieve the first wire with a matching semantic sink. */
    def Wire getSemanticSinkWire(ValuedObjectReference valuedObjectReference) {
        val expression = semanticReferenceIndex.get(new Pair<ValuedObject, ValuedObject>(valuedObjectReference.valuedObject, null))
        for (wire : wires) {
            if (wire.semanticSink == expression) return wire
        }
        return null
    }
    
}