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
package de.cau.cs.kieler.sccharts.processors.tabels

import de.cau.cs.kieler.sccharts.Transition
import java.util.List

/**
 * @author stu114663
 *
 */
interface ITableBuilder {
    def List<List<String>> build()
    def void insertHeader()
    def void insertTransition(Transition outTrans)
}