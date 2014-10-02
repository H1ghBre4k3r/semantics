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

import com.google.common.collect.ImmutableList
import com.google.inject.Inject
import de.cau.cs.kieler.core.kexpressions.Expression
import de.cau.cs.kieler.core.kexpressions.KExpressionsFactory
import de.cau.cs.kieler.core.kexpressions.OperatorExpression
import de.cau.cs.kieler.core.kexpressions.OperatorType
import de.cau.cs.kieler.core.kexpressions.extensions.KExpressionsExtension
import de.cau.cs.kieler.scg.Exit
import de.cau.cs.kieler.scg.Join
import de.cau.cs.kieler.scg.Predecessor
import de.cau.cs.kieler.scg.Surface
import de.cau.cs.kieler.scg.extensions.SCGControlFlowExtensions
import de.cau.cs.kieler.scg.extensions.SCGThreadExtensions
import de.cau.cs.kieler.scg.extensions.ThreadPathType
import de.cau.cs.kieler.scg.sequentializer.EmptyExpression
import de.cau.cs.kieler.core.annotations.extensions.AnnotationsExtensions
import java.util.Map
import de.cau.cs.kieler.scg.Node
import de.cau.cs.kieler.scg.SchedulingBlock
import de.cau.cs.kieler.scg.extensions.SCGCoreExtensions
import de.cau.cs.kieler.kico.AbstractKielerCompilerAncillaryData
import de.cau.cs.kieler.scg.analyzer.PotentialInstantaneousLoopResult
import java.util.List
import de.cau.cs.kieler.scg.BasicBlock
import java.util.Set
import de.cau.cs.kieler.scg.Depth
import de.cau.cs.kieler.scg.ScgFactory
import de.cau.cs.kieler.core.kexpressions.ValuedObjectReference
import de.cau.cs.kieler.scg.SCGraph
import de.cau.cs.kieler.scg.extensions.SCGDeclarationExtensions
import de.cau.cs.kieler.core.kexpressions.Declaration
import de.cau.cs.kieler.core.kexpressions.ValuedObject

/** 
 * This class is part of the SCG transformation chain. In particular a synchronizer is called by the scheduler
 * whenever multiple threads join again. <br>
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
 *   |    |   |-- Synchonize threads			<== YOU ARE HERE
 *   |    |-- Optimization
 *   |-- Sequentialization (new SCG)
 *       |-- Optimization					
 * </pre>
 * 
 * @author ssm
 * @kieler.design 2013-11-28 proposed 
 * @kieler.rating 2013-11-28 proposed yellow
 * 
 * @extends AbstractSCGSynchronizer
 */

class DepthJoinSynchronizer extends SurfaceSynchronizer {
 
    // -------------------------------------------------------------------------
    // -- Injections 
    // -------------------------------------------------------------------------
    
    @Inject
    extension KExpressionsExtension
    
    @Inject
    extension SCGCoreExtensions    
    
    @Inject
    extension SCGDeclarationExtensions
    
    @Inject
    extension SCGControlFlowExtensions
    
    @Inject
    extension SCGThreadExtensions

    @Inject
    extension AnnotationsExtensions
    
    public var Declaration schizophrenicDeclaration = null
   
    public static val SYNCHRONIZER_ID = "de.cau.cs.kieler.scg.synchronizer.depthJoin"
    
    public static val SCHIZOPHRENIC_SUFFIX = "_s"

    // -------------------------------------------------------------------------
    // -- Synchronizer
    // -------------------------------------------------------------------------
    
       
    override String getId() {
        return SYNCHRONIZER_ID
    }
    
    override isSynchronizable(Iterable<ThreadPathType> threadPathTypes) {
        var synchronizable = false

		if ((!threadPathTypes.filter[ it == ThreadPathType::DELAYED ].empty) &&
			(!threadPathTypes.filter[ it == ThreadPathType::POTENTIAL_INSTANTANEOUS ].empty)
		) {
			synchronizable = true
		}         
        
        synchronizable
    }
    
    override getExcludedPredecessors(Join join, Map<Node, SchedulingBlock> schedulingBlockCache, 
    	List<AbstractKielerCompilerAncillaryData> ancillaryData) {
        val excludeSet = <Predecessor> newHashSet
        
//        val pilData = ancillaryData.filter(typeof(PotentialInstantaneousLoopResult)).head.criticalNodes.toSet
//        val exitNodes = join.allPrevious.map[ eContainer as Exit ]
//      	val joinPredecessors = schedulingBlockCache.get(join).basicBlock.predecessors.toSet
//        
//        for(exit : exitNodes) {
//        	if (pilData.contains(exit)) {
//        		val exitBasicBlock = schedulingBlockCache.get(exit).basicBlock
//        		var Predecessor exitPredecessor = null 
//        		for (jPred : joinPredecessors) {
//        			if (exitBasicBlock == jPred.getBasicBlock) {
//        				exitPredecessor = jPred
//        			}
//        		}
//        		
//	        	val predecessors = exitBasicBlock.predecessors.toSet
//    	    	for(predecessor : predecessors) {
//        			val predecessorNodes = <Node> newArrayList
//        			predecessor.getBasicBlock.schedulingBlocks.forEach[ predecessorNodes += it.nodes ]
//        			var criticalPath = true
//        			for(node : predecessorNodes) {
//        				if (!pilData.contains(node)) {
//        					criticalPath = false
//        				}
//        			}
//        			
//        			if (criticalPath) {
//        				excludeSet += exitPredecessor
//        			}
//        		}
//        	}
//        }
        
        return excludeSet
    }
    
	override getAdditionalPredecessors(Join join, Map<Node, SchedulingBlock> schedulingBlockCache, List<AbstractKielerCompilerAncillaryData> ancillaryData) {
		val includeSet = <Predecessor> newHashSet
		
//        val exitNodes = join.allPrevious.map[ eContainer as Exit ]
//        
//        for(exit : exitNodes) {
//        	val shallowDepths/*withLochNessMonsters*/ = exit.entry.getShallowThreadNodes.filter(typeof(Depth))
//        	for(depth : shallowDepths) {
//        		val newPredecessor = ScgFactory::eINSTANCE.createPredecessor
//        		newPredecessor.basicBlock = schedulingBlockCache.get(depth).basicBlock
//        		includeSet += newPredecessor
//        	}
//        }
        	
        includeSet
	}    

    
    override protected SynchronizerData build(Join join) {
        // Create a new SynchronizerData class which holds the data to return.
        var data = new SynchronizerData() => [ setJoin(join) ]
        
        // Since we are working we completely enriched SCGs, we can use the SCG extensions 
        // to retrieve the scheduling block of the join node in question.
        val joinSB = join.getCachedSchedulingBlock
        
        val pilData = compilerContext.compilationResult.ancillaryData.filter(typeof(PotentialInstantaneousLoopResult)).head.criticalNodes.toSet
        
        // The valued object of the GuardExpression of the synchronizer is the guard of the
        // scheduling block of the join node. 
        data.guardExpression.valuedObject = joinSB.guard.valuedObject

        // Create a new expression that determines if at least on thread exits in this tick instance.
        // At first this simple scheduler assumes that the fork node spawns more than one thread.
        // Hence, we create an or-operator expression. 
        val terminationExpression = KExpressionsFactory::eINSTANCE.createOperatorExpression => 
            [ setOperator(OperatorType::OR) ]
        
        data.createEmptyExpressions(terminationExpression)
        data.createGuardExpression(terminationExpression)
        //data.guardExpression.expression = join.graph.fixSchizophrenicExpression(data.guardExpression.expression) 
        
        data.fixEmptyExpressions.fixSynchronizerExpression
    }    
    
    protected def Expression fixSchizophrenicExpression(SCGraph scg, Expression expression) {
        if (expression instanceof ValuedObjectReference) {
            val vor = (expression as ValuedObjectReference)
            val newVO = schizophrenicDeclaration.findValuedObjectByName(vor.valuedObject.name + SCHIZOPHRENIC_SUFFIX)
            if (newVO != null) {
                vor.valuedObject = newVO 
            }
        } else if (expression instanceof OperatorExpression) {
            val vors = (expression as OperatorExpression).eAllContents.filter(typeof(ValuedObjectReference))
            for(vor : vors.toIterable) {
                val newVO = schizophrenicDeclaration.findValuedObjectByName(vor.valuedObject.name + SCHIZOPHRENIC_SUFFIX)
                if (newVO != null) {
                    vor.valuedObject = newVO 
                }
            }
        }
        
        expression
    }   
    
    def ValuedObject findValuedObjectByName(Declaration declaration, String name) {
        for(vo : declaration.valuedObjects) {
            if (vo.name == name) return vo
        }
        return null
    }  
    
}