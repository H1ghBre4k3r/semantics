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
package de.cau.cs.kieler.sccharts.processors.csv

import de.cau.cs.kieler.kicool.compilation.ExogenousProcessor
import java.util.List
import de.cau.cs.kieler.kicool.compilation.CodeContainer

/**
 * @author stu114663
 *
 */
class CSV2Table extends ExogenousProcessor<CodeContainer, List<List<String>>> {
    
    static final String CSV_DELIMITER_CHARACTER = ","
    static final String CSV_ESCAPE_CHARACTER = "\\"
    
    override getId() {
        "de.cau.cs.kieler.sccharts.processors.CSV2Table"
    }
    
    override getName() {
        "CSV2Table"
    }
    
    override process() {
        val codeContainer = getModel
        // TODO handle multiple files?
        val sourceString = codeContainer.head.code
        // TODO check for empty model
        val CSVParser parser = new CSVParser(sourceString, CSV_DELIMITER_CHARACTER, CSV_ESCAPE_CHARACTER)
        model = parser.getTable()
    }
}