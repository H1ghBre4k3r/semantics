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
package de.cau.cs.kieler.scg.processors.codegen.smv

import de.cau.cs.kieler.scg.processors.codegen.CodeGeneratorModuleBase

/** 
 * @author aas
 */
abstract class SmvCodeGeneratorModuleBase extends CodeGeneratorModuleBase {
    public static val GUARD_PREFIX = "_g"
    public static val PRE_GUARD_PREFIX = "_p"
    public static val CONDITIONAL_GUARD_PREFIX = "_cg"
    public static val GO_GUARD = "_GO"
    
    override String getLineCommentToken() {
        return "--";
    }
}
