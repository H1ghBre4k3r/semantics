/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2015 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.sccharts.tsccharts.transformation

import com.google.inject.Inject
import de.cau.cs.kieler.core.annotations.extensions.AnnotationsExtensions
import de.cau.cs.kieler.kico.features.Feature
import de.cau.cs.kieler.scg.SCGraph

/**
 * TTP Feature.
 * 
 * @author als
 * @kieler.design 2016-01-20 proposed 
 * @kieler.rating 2016-01-20 proposed yellow
 *
 */
class TTPFeature extends Feature {

    //-------------------------------------------------------------------------
    //--                 K I C O      C O N F I G U R A T I O N              --
    //-------------------------------------------------------------------------
    override getId() {
        return TimingAnalysisTransformations::TTP_FEATURE_ID
    }

    override getName() {
        return TimingAnalysisTransformations::TTP_FEATURE_NAME
    }

    //-------------------------------------------------------------------------
    @Inject
    extension AnnotationsExtensions

    // This method checks, if this feature is contained in a model
    def isContained(SCGraph scg) {
        return scg.hasAnnotation(TimingAnalysisTransformations::TTP_FEATURE_ID)
    }
}
