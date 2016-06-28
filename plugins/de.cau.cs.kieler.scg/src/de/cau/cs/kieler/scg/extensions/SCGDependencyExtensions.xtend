/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2016 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.scg.extensions

import de.cau.cs.kieler.scg.ScgFactory
import de.cau.cs.kieler.scg.ExpressionDependency
import de.cau.cs.kieler.scg.GuardDependency
import de.cau.cs.kieler.scg.ControlDependency
import de.cau.cs.kieler.scg.Assignment
import de.cau.cs.kieler.scg.ScheduleDependency
import de.cau.cs.kieler.scg.Node
import de.cau.cs.kieler.scg.DataDependency
import de.cau.cs.kieler.scg.DataDependencyType
import com.google.inject.Inject
import de.cau.cs.kieler.core.kexpressions.IntValue
import de.cau.cs.kieler.core.kexpressions.extensions.KExpressionsValueExtensions
import java.util.List
import de.cau.cs.kieler.scg.Entry
import java.util.Map
import de.cau.cs.kieler.scg.Fork

/**
 * The SCG Extensions are a collection of common methods for SCG queries and manipulation.
 * The class is separated in several categories. If a category growths too big, it may be 
 * desired to relocate its functions in a specialized extensions class. At the moment the class
 * contains functions for the following tasks.
 * <ul>
 *   <li>Valued object handling</li>
 *   <li>Control flow queries</li>
 *   <li>Thread management</li>
 *   <li>Basic block and scheduling block qeuries</li>
 *   <li>Scheduling problem management</li>
 *   <li>Expression helper</li>
 * </ul> 
 * SCG model copy functions and transformation helper are already relocated to their appropriate
 * extensions.
 * 
 * @author ssm
 * @kieler.design 2016-02-24 proposed 
 * @kieler.rating 2016-02-24 proposed yellow
 */
class SCGDependencyExtensions { 
	
	@Inject
	extension SCGCoreExtensions
	
	@Inject
	extension SCGControlFlowExtensions
	
	@Inject
	extension KExpressionsValueExtensions
	
	 def DataDependency createDataDependency(DataDependencyType type) {
	 	ScgFactory::eINSTANCE.createDataDependency => [ 
	 		it.type = type
	 	]
	 }

    def DataDependency createDataDependency(Node source, Node target, DataDependencyType type) {
    	type.createDataDependency => [ 
    		source.dependencies += it
    		it.target = target
    	]
    }

    def ExpressionDependency createExpressionDependency(Node source, Node target) {
    	ScgFactory::eINSTANCE.createExpressionDependency => [ 
    		source.dependencies += it
    		it.target = target
    	]
    }

    def GuardDependency createGuardDependency(Node source, Node target) {
    	ScgFactory::eINSTANCE.createGuardDependency => [ 
    		source.dependencies += it
    		it.target = target
    	]
    }
    
    def ControlDependency createControlDependency(Node source, Node target) {
    	ScgFactory::eINSTANCE.createControlDependency => [ 
    		source.dependencies += it
    		it.target = target
    	]
    }
    
    def ScheduleDependency createScheduleDependency(Node source, Node target) {
    	ScgFactory::eINSTANCE.createScheduleDependency => [ 
    		source.dependencies += it
    		it.target = target
    	]
    }
    
    def DataDependency checkAndSetConfluence(DataDependency dependency) {
    	val sourceNode = dependency.eContainer as Node
    	val targetNode = dependency.target
    	dependency.confluent = false
    	if (sourceNode instanceof Assignment && targetNode instanceof Assignment) {
    		val sourceExpression = sourceNode.asAssignment.expression
    		val targetExpression = targetNode.asAssignment.expression
    		if (sourceExpression.isSameValue(targetExpression)) {
    			dependency.confluent = true
    		}
    	}
    	dependency
    }
    
    def DataDependency checkAndSetConcurrency(DataDependency dependency, Map<Node, List<Entry>> nodeMapping) {
    	val sourceNode = dependency.eContainer as Node
    	val targetNode = dependency.target
    	dependency.concurrent = false
    	
    	val sourceThreads = nodeMapping.get(sourceNode)
    	val targetThreads = nodeMapping.get(targetNode)
    	for(sourceEntry : sourceThreads) {
    		var Fork sourceFork = null
    		val sourcePredecessor = sourceEntry.getAllPreviousHeadNode
    		if (sourcePredecessor instanceof Fork) sourceFork = sourcePredecessor as Fork
    		if (sourceFork != null) {
    			for(targetEntry : targetThreads) {
    				var Fork targetFork = null
    				val targetPredecessor = targetEntry.getAllPreviousHeadNode
    				if (targetPredecessor instanceof Fork) targetFork = targetPredecessor as Fork
   					if (targetFork != null && sourceFork == targetFork && sourceEntry != targetEntry) {
   						dependency.concurrent = true;
   						return dependency
   					}
    			}
    		}
    	}
    	
		dependency		    	
    }    
	
}