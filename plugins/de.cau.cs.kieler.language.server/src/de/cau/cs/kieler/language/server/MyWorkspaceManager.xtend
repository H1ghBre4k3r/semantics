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
package de.cau.cs.kieler.language.server

import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.ide.server.WorkspaceManager
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.validation.Issue

/**
 * @author sdo
 *
 */
class MyWorkspaceManager extends WorkspaceManager {
    override void initialize(URI baseDir, (URI, Iterable<Issue>)=>void issueAcceptor, CancelIndicator cancelIndicator) {
        refreshWorkspaceConfig(cancelIndicator)
    }
}