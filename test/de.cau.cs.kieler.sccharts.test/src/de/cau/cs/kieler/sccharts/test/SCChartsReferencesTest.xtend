/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2017 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.sccharts.test

import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.text.SCTXStandaloneSetup
import de.cau.cs.kieler.sccharts.text.services.SCTXGrammarAccess
import de.cau.cs.kieler.test.common.repository.AbstractXTextModelRepositoryTest
import de.cau.cs.kieler.test.common.repository.ModelsRepositoryTestRunner
import de.cau.cs.kieler.test.common.repository.TestModelData
import org.eclipse.xtext.resource.XtextResource
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*
import org.eclipse.xtext.Keyword

/**
 * Tests if all reference in SCCharts are correctly linked.
 * 
 * @author als
 * @kieler.design proposed
 * @kieler.rating proposed yellow
 */
@RunWith(ModelsRepositoryTestRunner)
class SCChartsReferencesTest extends AbstractXTextModelRepositoryTest<State> {
    
    /** Sct Parser Injector */
    static val resourceSetInjector = new SCTXStandaloneSetup().createInjectorAndDoEMFRegistration
    /** The SCCharts grammar */
    static val grammar = resourceSetInjector.getInstance(SCTXGrammarAccess)
    
    //-----------------------------------------------------------------------------------------------------------------
    
    /**
     * Constructor
     */
    new() {
        super(resourceSetInjector)
    }
    
    /**
     * {@inheritDoc}
     */
    override filter(TestModelData modelData) {
        return modelData.modelProperties.contains("sccharts") && !modelData.resourceSetID.nullOrEmpty
    }
    
    @Test
    def void testReferences(State scc, TestModelData modelData) {
        val keyword = grammar.stateAccess.isKeyword_8_0_0
        for (res : scc.eResource.resourceSet.resources.filter(XtextResource)) {
            val parserNodes = res.parseResult.rootNode
            parserNodes.asTreeIterable.filter[
                semanticElement instanceof State 
                && grammarElement.eClass.equals(keyword.eClass)
                && (grammarElement as Keyword).value == keyword.value
            ].forEach[
                assertTrue("Referenced state " + (semanticElement as State).id + " in " + res.URI.segment(res.URI.segmentCount - 1) + " cannot be resolved",
                    (semanticElement as State).reference.scope !== null)
            ]
        }
    }
      
}