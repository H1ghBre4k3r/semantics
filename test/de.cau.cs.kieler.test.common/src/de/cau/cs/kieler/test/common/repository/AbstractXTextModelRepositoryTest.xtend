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
package de.cau.cs.kieler.test.common.repository

import com.google.common.reflect.TypeToken
import com.google.inject.Guice
import com.google.inject.Injector
import de.cau.cs.kieler.simulation.testing.TestModelData
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.XtextResourceSet

import static extension com.google.common.base.Preconditions.*
import static extension de.cau.cs.kieler.simulation.testing.TestModelDataUtil.*

/**
 * An abstract {@link IModelsRepositoryTest} which automatically parses XText models.
 * 
 * @author als
 * @kieler.design proposed
 * @kieler.rating proposed yellow
 */
abstract class AbstractXTextModelRepositoryTest<T extends EObject> implements IModelsRepositoryTest<T> {
    
    /** The list of hierarchical compare predicates */
    protected val compareHierarchy = newLinkedList(ModelFolderComparator, ComplexityComparator, ModelFileNameComparator)  
    /** Injector used for resource set creation */
    private val Injector resourceSetInjector;
    
    /**
     * Constructor with specific injector for resource set creation.
     * @param resourceSetInjector the injector. Can be null if not used in tests.
     */
    new(Injector resourceSetInjector) {
        this.resourceSetInjector = resourceSetInjector
    }
    
    /**
     * {@inheritDoc}
     */
    override getModelType() {
        return (new TypeToken<T>(getClass()) {}).rawType
    }

    /**
     * {@inheritDoc}
     */
    override T loadModel(TestModelData data) {
        return data.load(resourceSetInjector) as T
    }
    
    /**
     * {@inheritDoc}
     */   
    override compare(TestModelData a, TestModelData b) {
        return compareHierarchy.hierachicalCompare(a, b)
    }

    /**
     * @return the correct XtextResourceSet for the given uri based in its file extension.
     */
    def XtextResourceSet getXtextResourceSet(URI uri) {
        if (resourceSetInjector !== null) {
            return resourceSetInjector.getInstance(XtextResourceSet);
        } else {
            uri.checkNotNull
            val registry = Guice.createInjector().getInstance(IResourceServiceProvider.Registry)
            return registry.getResourceServiceProvider(uri).get(XtextResourceSet)
        }
    }

}