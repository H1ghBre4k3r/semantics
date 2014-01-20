/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2013 by
 * + Christian-Albrechts-University of Kiel
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.scg.synchronizer

import de.cau.cs.kieler.scg.Join

/** 
 * This class is part of the SCG transformation chain. In particular a synchronizer is called by the scheduler
 * whenever multiple threads join again. This abstract class defines the generic interface of a synchronizer 
 * since the scheduler may choose different synchronizers for different tasks.<br>
 * The chain is used to gather important information 
 * about the schedulability of a given SCG. This is done in several key steps. Between two steps the results 
 * are cached in specifically designed metamodels for further processing. At the end of the transformation
 * chain a newly generated (and sequentialized) SCG is returned. <br>
 * You can either call the transformations manually or use the SCG transformation extensions to enrich the
 * SCGs with the desired information.<br>
 * <pre>
 * SCG 
 *   |-- Dependency Analysis 	 					
 *   |-- Basic Block Analysis
 *   |-- Scheduler
 *   |    |-- Graph analyses
 *   |    |-- Scheduling
 *   |    |   |-- Synchonize threads		<== YOU ARE HERE
 *   |    |-- Optimization
 *   |-- Sequentialization (new SCG)
 *       |-- Optimization					
 * </pre>
 * 
 * @author ssm
 * @kieler.design 2013-11-28 proposed 
 * @kieler.rating 2013-11-28 proposed yellow
 * @abstract
 */
abstract class AbstractSCGSynchronizer {
   
    /**
     * This function has to be overwritten in the derived class. It is called by the 
     * {@link #synchronize(Join)} function which is called by the scheduler 
     * and constructs the actual synchronizer.
     * 
     * @param join
     * 			the join node in question
     * @return Returns a {@code SynchronizerData} class which includes all mandatory 
     * 		data to construct a guard expression for the join node in question.
     * @abstract 
     */
    protected abstract def SynchronizerData build(Join join);
    
    /**
     * This function is the entry point for the scheduler. The scheduler calls 
     * {@code synchronize} whenever multiple threads are joined at a join node.
     * 
     * @param join 
     * 			the join node in question
     * @return Returns a {@code SynchronizerData} class which includes all mandatory 
     * 		data to construct a guard expression for the join node in question.
     */
    public def SynchronizerData synchronize(Join join) {
        build(join)
    }    
}