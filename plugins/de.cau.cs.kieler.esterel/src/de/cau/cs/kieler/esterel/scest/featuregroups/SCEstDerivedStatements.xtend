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
package de.cau.cs.kieler.esterel.scest.featuregroups

import com.google.common.collect.Sets
import de.cau.cs.kieler.kico.features.FeatureGroup
import de.cau.cs.kieler.esterel.scest.features.SCEstFeature

/**
 * @author mrb
 *
 */
class SCEstDerivedStatements extends FeatureGroup {

    //-------------------------------------------------------------------------
    //--                 K I C O      C O N F I G U R A T I O N              --
    //-------------------------------------------------------------------------
    override getId() {
        return SCEstFeatureGroup::SCESTDERIVEDSTATEMENTS_ID
    }

    override getName() {
        return SCEstFeatureGroup::SCESTDERIVEDSTATEMENTS_NAME
    }

    override getFeatureIds() {
        Sets.newHashSet(
            SCEstFeature::ABORT_ID,
            SCEstFeature::AWAIT_ID,
            SCEstFeature::DO_ID,
            SCEstFeature::EVERYDO_ID
        )
    }
}