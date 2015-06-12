/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2015 by
 * + Christian-Albrechts-University of Kiel
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.sccharts.featuregroups

import com.google.common.collect.Sets
import de.cau.cs.kieler.kico.features.FeatureGroup
import de.cau.cs.kieler.sccharts.features.SCChartsFeature

/**
 * SyncCharts Feature Group.
 * 
 * @author cmot
 * @kieler.design 2015-03-12 proposed 
 * @kieler.rating 2015-03-12 proposed yellow
 */
class SyncCharts extends FeatureGroup {

    //-------------------------------------------------------------------------
    //--                 K I C O      C O N F I G U R A T I O N              --
    //-------------------------------------------------------------------------
    override getId() {
        return SCChartsFeatureGroup::SYNCCHARTS_ID
    }

    override getName() {
        return SCChartsFeatureGroup::SYNCCHARTS_NAME
    }

    override getFeatureIds() {
        Sets.newHashSet(SCChartsFeature::SIGNAL_ID, SCChartsFeature::COUNTDELAY_ID,
             SCChartsFeature::SUSPEND_ID, SCChartsFeature::PRE_ID)
    }

//-------------------------------------------------------------------------
}
