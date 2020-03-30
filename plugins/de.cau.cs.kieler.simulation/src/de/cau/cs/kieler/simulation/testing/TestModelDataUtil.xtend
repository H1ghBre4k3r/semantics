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
package de.cau.cs.kieler.simulation.testing

import java.util.Comparator
import java.util.List

/**
 * Common utility for the {@link TestModelData}.
 * 
 * @author als
 */
class TestModelDataUtil {
    
    /**
     * @return the value of the name property or the given default value if not available.
     */
    static def boolean getAdditionalBooleanProperty(TestModelData data, String name, boolean defaultValue ) {
        if (data.additionalProperties.containsKey(name)) {
            try {
                return Boolean.parseBoolean(data.additionalProperties.get(name))
            } catch (Exception e) {
            }
        }
        return defaultValue
    }
        
    /**
     * @return the value of the name property or the given default value if not available.
     */
    static def int getAdditionalIntegerProperty(TestModelData data, String name, int defaultValue ) {
        if (data.additionalProperties.containsKey(name)) {
            try {
                return Integer.parseInt(data.additionalProperties.get(name))
            } catch (Exception e) {
            }
        }
        return defaultValue
    }    
    
    /**
     * @return the first order result of the given list of comparators.
     */
    static def int hierachicalCompare(List<Comparator<TestModelData>> comerators, TestModelData a, TestModelData b) {
        return comerators.map[it.compare(a, b)].findFirst[it != 0] ?: 0
    }
    
    /**
     * A comparator based on the specified ResourceSetID of two models.
     */
    public static val ResourceSetIDComparator = new Comparator<TestModelData>(){
        
        override compare(TestModelData a, TestModelData b) {
            return switch(a) {
                case a.resourceSetID == b.resourceSetID: 0
                case a.resourceSetID === null: -1
                case b.resourceSetID === null: 1
                default: a.resourceSetID.compareTo(b.resourceSetID)
            } 
        }
        
    }
    
    /**
     * A comparator based on the model path of two models.
     */
    public static val ModelPathComparator = new Comparator<TestModelData>(){
        
        override compare(TestModelData a, TestModelData b) {
            return switch(a) {
                case a.modelPath == b.modelPath: 0
                default: a.modelPath.compareTo(b.modelPath)
            }
        }
        
    }
    
    /**
     * A comparator based on the model folder of two models.
     */
    public static val ModelFolderComparator = new Comparator<TestModelData>(){
        
        override compare(TestModelData a, TestModelData b) {
            return switch(a) {
                case a.modelPath.parent == b.modelPath.parent: 0
                case a.modelPath.parent === null: -1
                default: a.modelPath.parent.compareTo(b.modelPath.parent)
            }
        }
        
    }
    
    /**
     * A comparator based on the model file name of two models.
     */
    public static val ModelFileNameComparator = new Comparator<TestModelData>(){
        
        override compare(TestModelData a, TestModelData b) {
            return switch(a) {
                case a.modelPath.fileName == b.modelPath.fileName: 0
                default: a.modelPath.fileName.compareTo(b.modelPath.fileName)
            }
        }
        
    }
    
    /**
     * A comparator based on optional "complexity" property of two models.
     */    
    public static val ComplexityComparator = new Comparator<TestModelData>(){
        
        override compare(TestModelData a, TestModelData b) {
            return b.getAdditionalIntegerProperty("complexity", 0) - a.getAdditionalIntegerProperty("complexity", 0)
        }
        
    }

}
