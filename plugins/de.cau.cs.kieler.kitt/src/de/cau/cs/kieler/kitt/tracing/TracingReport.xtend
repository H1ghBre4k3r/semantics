/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2014 by
 * + Christian-Albrechts-University of Kiel
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.kitt.tracing

import de.cau.cs.kieler.kitt.tracing.internal.TracingMapping
import org.eclipse.emf.ecore.EObject

/**
 * This class analysis a mapping and checks its content against the source and target model of the transformation.
 * 
 * @author als
 * @kieler.design 2015-02-25 proposed
 * @kieler.rating 2015-02-25 proposed yellow
 */
class TracingReport {
    val TracingMapping mapping;

    val EObject source;
    public val sourceElementInMapping = newHashSet();
    public val sourceElementInTragetMapping = newHashSet();
    public val sourceElementNotInMapping = newHashSet();
    public val sourceElementNotInModel = newHashSet();

    val EObject target;
    public val targetElementInMapping = newHashSet();
    public val targetElementInTragetMapping = newHashSet();
    public val targetElementNotInMapping = newHashSet();
    public val targetElementNotInModel = newHashSet();

    new(Object sourceModel, Object targetModel, TracingMapping tracingMapping) {
        mapping = tracingMapping;

        //find correct source and target models due to inplace transformation delegation
        if (mapping.inPlace) {
            val realSource = TracingManager.getTracingChain(sourceModel).getModels(mapping).first as EObject;
            if (realSource instanceof EObject) {
                source = realSource as EObject;
            } else {
                source = null
            }
        } else if (sourceModel instanceof EObject) {
            source = sourceModel as EObject;
        } else {
            source = null
        }
        if (targetModel instanceof EObject) {
            target = targetModel as EObject;
        } else {
            target = null
        }
        analyse();
    }

    private def void analyse() {

        //check source elements
        if (source != null) {
            source.eAllContents.fold(newArrayList(source))[list, item|list.add(item); list].forEach [
                if (mapping.internalMapping.containsKey(it)) {
                    sourceElementInMapping.add(it);
                } else if (mapping.internalReverseMapping.containsKey(it)) {
                    sourceElementInTragetMapping.add(it);
                } else {
                    sourceElementNotInMapping.add(it);
                }
            ];
            sourceElementNotInModel.addAll(
                mapping.internalMapping.keySet.filter[!sourceElementInMapping.contains(it)]);
        }

        //check target elements
        if (target != null) {
            target.eAllContents.fold(newArrayList(target))[list, item|list.add(item); list].forEach [
                if (mapping.internalReverseMapping.containsKey(it)) {
                    targetElementInMapping.add(it);
                } else if (mapping.internalMapping.containsKey(it)) {
                    targetElementInTragetMapping.add(it);
                } else {
                    targetElementNotInMapping.add(it);
                }
            ];
            targetElementNotInModel.addAll(
                mapping.internalReverseMapping.keySet.filter[!targetElementInMapping.contains(it)]);
        }
    }

    def void failOnIncompleteMapping() {
        if (sourceElementInTragetMapping.empty && sourceElementNotInMapping.empty && sourceElementNotInModel.empty) {
            throw new Exception("Incomplete Mapping");
        }
    }

    def void printReport() {
        println("Mapping: " + mapping.title);
        if (sourceElementInTragetMapping.empty && sourceElementNotInMapping.empty && sourceElementNotInModel.empty) {
            println(" Source Mapping: OK");
        } else {
            println(" Source Mapping:");
            println("  Elements mapped as target elements:");
            sourceElementInTragetMapping.forEach[println("   " + it)];
            println("  Elements missing in mapping:");
            sourceElementNotInMapping.forEach[println("   " + it)];
            println("  Elements missing in model:");
            sourceElementNotInModel.forEach[println("   " + it)];
        }
        if (targetElementInTragetMapping.empty && targetElementNotInMapping.empty && targetElementNotInModel.empty) {
            println(" Target Mapping: OK");
        } else {
            println(" Target Mapping:");
            println("  Elements mapped as source elements:");
            targetElementInTragetMapping.forEach[println("   " + it)];
            println("  Elements missing in mapping:");
            targetElementNotInMapping.forEach[println("   " + it)];
            println("  Elements missing in model:");
            targetElementNotInModel.forEach[println("   " + it)];
        }
    }
}
