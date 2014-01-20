/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2014 by
 * + Christian-Albrechts-University of Kiel
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.ktm.test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.google.inject.Guice;

import de.cau.cs.kieler.ktm.extensions.TransformationTreeExtensions;
import de.cau.cs.kieler.ktm.test.transformations.SCChartTestTransformation;
import de.cau.cs.kieler.ktm.transformationtree.ModelWrapper;
import de.cau.cs.kieler.sccharts.Region;

/**
 * @author als
 * 
 */
public class SCChartTest extends TestCase {

    private final SCChartTestTransformation transformation = Guice.createInjector().getInstance(
            SCChartTestTransformation.class);
    private final TransformationTreeExtensions transformationTree = Guice.createInjector()
            .getInstance(TransformationTreeExtensions.class);
    private Region abo;
    private Region aboSplitTE;
    private Region aboSplitTEConnector;
    private ModelWrapper tree;

    /**
     * {@inheritDoc}
     */
    protected void setUp() throws Exception {
        // LOAD test chart

        // Create a resource set.
        ResourceSet resourceSet = new ResourceSetImpl();

        // Register the default resource factory -- only needed for stand-alone!
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
                .put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

        // Get the URI of the model file.
        File file = new File("./artifacts/ABO.scc");
        assertTrue(file.exists());
        URI fileURI = URI.createFileURI(file.getAbsolutePath());

        // Demand load the resource for this file, here the actual loading is done.
        Resource resource = resourceSet.getResource(fileURI, true);

        assertEquals(resource.getContents().size(), 1);
        abo = (Region) resource.getContents().get(0);

        super.setUp();
    }

    /**
     * {@inheritDoc}
     */
    protected void tearDown() throws Exception {
        // SAVE results

        File file = new File("./artifacts/ABO.split.scc");
        saveSCC(aboSplitTE, file);
        assertTrue(file.exists());
        
        file = new File("./artifacts/ABO.split.connect.scc");
        saveSCC(aboSplitTEConnector, file);
        assertTrue(file.exists());
        
        
        assertNotNull(tree);

        // Create a resource set.
        ResourceSet resourceSet = new ResourceSetImpl();

        // this tells EMF to use XML to save the model
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
                .put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

        // Get the URI of the model file.
        file = new File("./artifacts/ABO.ktmt");
        URI fileURI = URI.createFileURI(file.getAbsolutePath());

        // Create a resource for this file.
        Resource resource = resourceSet.createResource(fileURI);

        // Add the model objects to the contents.
        resource.getContents().add(tree);
        // Also add referenced Objects
        if (!tree.isTransient()) {
            resource.getContents().add(tree.getRootObject().getEObject());
        }
        for (ModelWrapper model : transformationTree.succeedingModels(tree)) {
            if (!model.isTransient()) {
                resource.getContents().add(model.getRootObject().getEObject());
            }
        }

        // Save the contents of the resource to the file system.
        resource.save(Collections.EMPTY_MAP);

        assertTrue(file.exists());

        super.tearDown();
    }

    public void saveSCC(Region model, File file) throws IOException {
        // save transformed ABO
        assertNotNull(model);

        // Create a resource set.
        ResourceSet resourceSet = new ResourceSetImpl();

        // this tells EMF to use XML to save the model
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
                .put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

        // Get the URI of the model file.
        URI fileURI = URI.createFileURI(file.getAbsolutePath());

        // Create a resource for this file.
        Resource resource = resourceSet.createResource(fileURI);

        // Add the model objects to the contents.
        resource.getContents().add(model);

        // Save the contents of the resource to the file system.
        resource.save(Collections.EMPTY_MAP);
    }

    public final void testSCC() {
        assertNotNull(abo);
        // clear mapping
        transformation.extractMapping();

        aboSplitTE = transformation.transformTriggerEffect(abo);
        assertNotNull(aboSplitTE);

        ModelWrapper aboSplitTEModel =
                transformationTree.initializeTransformationTree(transformation.extractMapping(),
                        "splitTriggerEffect", abo, "coreSCChart", aboSplitTE,
                        "coreSCChart-splitTriggerEffect");
        assertNotNull(aboSplitTEModel);

        aboSplitTEConnector = transformation.transformConnector(aboSplitTE);
        assertNotNull(aboSplitTEConnector);

        ModelWrapper aboSplitTEConnectorModel =
                transformationTree.addTransformationToTree(transformation.extractMapping(),
                        aboSplitTEModel, "connector", aboSplitTE, aboSplitTEConnector,
                        "coreSCChart-splitTriggerEffect-connector");
        assertNotNull(aboSplitTEConnectorModel);

        tree = transformationTree.root(aboSplitTEConnectorModel);
    }

}
