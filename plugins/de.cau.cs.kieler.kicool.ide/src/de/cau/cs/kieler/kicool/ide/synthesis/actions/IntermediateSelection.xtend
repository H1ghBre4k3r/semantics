/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2018 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.kicool.ide.synthesis.actions

import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * Class for holding the intermediate selection data.
 * 
 * @author ssm
 * @kieler.design 2018-04-12 proposed 
 * @kieler.rating 2018-04-12 proposed yellow
 */
class IntermediateSelection {
    @Accessors List<IntermediateSelectionEntry> entries
    
    new(de.cau.cs.kieler.kicool.compilation.Processor<?,?> processor, int intermediateIndex) {
        entries = <IntermediateSelectionEntry> newArrayList
        new IntermediateSelectionEntry(processor, intermediateIndex) => [
            entries += it
        ]
    }
    
    new(List<Pair<de.cau.cs.kieler.kicool.compilation.Processor<?,?>, Integer>> processorIndexPairs) {
        entries = <IntermediateSelectionEntry> newArrayList
        for (pair : processorIndexPairs) {
            new IntermediateSelectionEntry(pair.key, pair.value) => [
                entries += it
            ]
        }
    }
}

@Data
class IntermediateSelectionEntry {
    de.cau.cs.kieler.kicool.compilation.Processor<?,?> processor
    int intermediateIndex
}
