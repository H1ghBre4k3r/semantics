/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2013 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.scg.transformations.schedulers

import com.google.inject.Inject
import de.cau.cs.kieler.annotations.extensions.AnnotationsExtensions
import de.cau.cs.kieler.kexpressions.OperatorExpression
import de.cau.cs.kieler.kexpressions.OperatorType
import de.cau.cs.kieler.kexpressions.ValuedObject
import de.cau.cs.kieler.kexpressions.ValuedObjectReference
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsValuedObjectExtensions
import de.cau.cs.kieler.kico.KielerCompilerContext
import de.cau.cs.kieler.kico.KielerCompilerException
import de.cau.cs.kieler.kitt.tracing.Traceable
import de.cau.cs.kieler.scg.ControlFlow
import de.cau.cs.kieler.scg.DataDependency
import de.cau.cs.kieler.scg.Guard
import de.cau.cs.kieler.scg.Join
import de.cau.cs.kieler.scg.Node
import de.cau.cs.kieler.scg.SCGraph
import de.cau.cs.kieler.scg.ScheduleBlock
import de.cau.cs.kieler.scg.SchedulingBlock
import de.cau.cs.kieler.scg.extensions.SCGCacheExtensions
import de.cau.cs.kieler.scg.extensions.SCGCoreExtensions
import de.cau.cs.kieler.scg.features.SCGFeatures
import de.cau.cs.kieler.scg.transformations.SCGTransformations
import de.cau.cs.kieler.scg.transformations.guardExpressions.AbstractGuardExpressions
import java.util.List
import java.util.Map
import java.util.Set

/** 
 * This class is part of the SCG transformation chain. 
 * The transformation chain is used to gather important information 
 * about the schedulability of a given SCG. This is done in several key steps. 
 * You can either call the transformation manually or use KiCo to perform a series of transformations.
 * <pre>
 * SCG 
 *   |-- Dependency Analysis                        
 *   |-- Basic Block Analysis
 *   |-- Scheduler                          <== YOU ARE HERE
 *   |-- Sequentialization (new SCG)                
 * </pre>
 * 
 * @author ssm
 * @kieler.design 2013-11-27 proposed 
 * @kieler.rating 2013-11-27 proposed yellow
 */
class GuardSchedulerV2 extends AbstractScheduler implements Traceable {
        
    //-------------------------------------------------------------------------
    //--                 K I C O      C O N F I G U R A T I O N              --
    //-------------------------------------------------------------------------
    
    override getId() {
        return SCGTransformations::SCHEDULING_ID
    }

    override getName() {
        return SCGTransformations::SCHEDULING_NAME
    }

    override getProducedFeatureId() {
        return SCGFeatures::SCHEDULING_ID
    }

    override getRequiredFeatureIds() {
        return newHashSet(SCGFeatures::GUARD_EXPRESSIONS_ID)
    }

    //-------------------------------------------------------------------------


    //TODO Fix logging
    static final boolean DEBUG = true;

    def static void debug(String debugText) {
        debug(debugText, true);
    }

    def static void debug(String debugText, boolean lineBreak) {
        if (DEBUG) {
            if (lineBreak) {
                System.out.println(debugText);
            } else {
                System.out.print(debugText);
            }
        }
    }

    // -------------------------------------------------------------------------
    // -- Injections 
    // -------------------------------------------------------------------------
    @Inject
    extension SCGCoreExtensions

    @Inject
    extension SCGCacheExtensions

    @Inject
    extension AnnotationsExtensions

    @Inject
    extension KExpressionsValuedObjectExtensions

    // -------------------------------------------------------------------------
    // -- Globals 
    // -------------------------------------------------------------------------
    protected val topologicalSortVisited = <SchedulingBlock>newHashSet
    protected var Map<Node, SchedulingBlock> schedulingBlockCache = null 
    protected val schedulingBlockVOCache = <ValuedObject, Set<SchedulingBlock>>newHashMap
    protected val allSchedulingBlocks = <SchedulingBlock>newHashSet
    protected var int schedulingBlockCount
    protected val guardVOCache = <ValuedObject, Guard>newHashMap

    protected val placedVOs = <ValuedObject>newHashSet
    protected val placedSBs = <SchedulingBlock>newHashSet

    // -------------------------------------------------------------------------
    // -- Scheduler 
    // -------------------------------------------------------------------------
    /**
     * The simple scheduler travels through the list of scheduling blocks and tries to generate their
     * guard expressions. Therefore, all preconditions, especially predecessor blocks, of a scheduling block 
     * must be met before creating such an expression. If a block cannot be processed at a time, it is skipped 
     * in this iteration and will be processed in a later one. Thus, if in one iteration no block can be processed,
     * a fixed point is reached and the graph is not scheduable by this scheduler.  
     */
    protected override SchedulingConstraints orderSchedulingBlocks(SCGraph scg) {
        val schedulingBlocks = <SchedulingBlock>newLinkedList
        scg.basicBlocks.filter[!isDeadBlock].forEach[it.schedulingBlocks.forEach[schedulingBlocks += it]]

        //        val guardList = <Guard> newArrayList => [ addAll(scg.guards.filter[ !isDead ]) ]
        new SchedulingConstraints => [
            it.schedulingBlocks = schedulingBlocks
        //        	it.guards = guardList
        ]
    }

    protected def void topologicalPlacement(
        SchedulingBlock schedulingBlock,
        Set<SchedulingBlock> remainingSchedulingBlocks,
        List<ScheduleBlock> schedule,
        SchedulingConstraints constraints,
        SCGraph scg,
        String indent
    ) {
        if (!topologicalSortVisited.contains(schedulingBlock)) {

            topologicalSortVisited.add(schedulingBlock)

            val VOR = <ValuedObject>newArrayList
            val SBs = <SchedulingBlock>newArrayList
            val lastVOs = <ValuedObject>newArrayList
            val addGuardBeforeScheduledBlock = <Guard>newHashSet

            val neededNodes = <Node>newHashSet

            if (schedulingBlock.guards.head.expression instanceof ValuedObjectReference) {
                VOR += (schedulingBlock.guards.head.expression as ValuedObjectReference).valuedObject
            } else {
                if (schedulingBlock.guards.head.expression instanceof OperatorExpression &&
                    (schedulingBlock.guards.head.expression as OperatorExpression).operator != OperatorType::PRE) {
                    schedulingBlock.guards.head.expression.eAllContents.filter(typeof(ValuedObjectReference)).map[
                        valuedObject].forEach[VOR += it]
                    for (vo : VOR) {
                        if (vo.name.startsWith(AbstractGuardExpressions::CONDITIONAL_EXPRESSION_PREFIX))
                            lastVOs += vo
                    }
                    VOR -= lastVOs
                }
            }

            if (schedulingBlock != null) {
                val dependencies = schedulingBlock.getAllDataDependencies(scg)
                if (!dependencies.empty) {
                    debug(indent + "Scheduling block" + schedulingBlock.label + " has dependencies: ", false)
                    for (dependency : dependencies) {
                        if (dependency.concurrent && !dependency.confluent) {
                            val sb = schedulingBlockCache.get(dependency.eContainer)
                            debug(sb.label + " (" + sb.guards.head.valuedObject.name + ")", false)
                            neededNodes += dependency.target

                            // TODO: VERIFY!
                            //			                if (guard.schizophrenic) {
                            //			                    val schizoGuard = scg.guards.filter[ it.schedulingBlockLink == sb && it.schizophrenic].toList
                            //			                    if (!schizoGuard.empty) {
                            //			                        VOR += schizoGuard.head.valuedObject
                            //                                    System.out.print(" using " + schizoGuard.head.valuedObject.name)			                        
                            //			                    }
                            //			                } else {
                            VOR += sb.guards.head.valuedObject
                            SBs += sb

                        //  			                }
                        }
                    }
                    debug("")
                }
            }

            //			if (!guard.originalObject.empty) {
            //				for(volatile : guard.originalObject) {
            ////					if (!placedVOs.contains(volatile)) {
            //						VOR -= volatile
            //						guard.expression = guard.expression.replace(volatile, TRUE)
            ////					}						
            //				}
            //			}
            
                debug(indent + "Placing scheduling block " + schedulingBlock.label + ": ", false)
                for (ref : VOR) {
                    debug(ref.name + " ", false)
                }
                debug("")

            for (ref : VOR) {
                debug(indent + "Testing VOR " + ref.name, false)
                if (!placedVOs.contains(ref)) {
                    debug(" not placed.")

                    //					val tpGuard = guardCache.get(ref)
                    val vorSBList = schedulingBlockVOCache.get(ref)
                    if (vorSBList != null) {
                        for (sb : schedulingBlockVOCache.get(ref)) {
                            sb.topologicalPlacement(remainingSchedulingBlocks, schedule, constraints, scg, indent + "  ")
                        }
                    } else {
                        // This usually happens to empty guards
                        debug(indent + ref.name + " has no associated scheduling block!")
                        val guard = guardVOCache.get(ref)
                        addGuardBeforeScheduledBlock += guard
                        placedVOs += ref
                        
                        // Add the guarded scheduling blocks to the list of needed scheduling blocks
                        guard.expression.getAllReferences.forEach[
                            if (!it.valuedObject.name.equals(AbstractGuardExpressions.GOGUARDNAME)) {
                                if (it.valuedObject != null) {
                                    val sb = schedulingBlockVOCache.get(it.valuedObject)
                                    if (sb != null) {
                                        SBs += sb
                                    }
                                }
                            }
                        ]
                    }
                } else {
                    debug(" placed.")
                }
                
            }
            
            for(sb : SBs) {
                if (!placedSBs.contains(sb)) {
                    sb.topologicalPlacement(remainingSchedulingBlocks, schedule, constraints, scg, indent + "  ")
                }
            }

            var placeable = true
            for (ref : VOR) {
                if (!placedVOs.contains(ref)) {

                    //					if (guard.volatile.contains(ref)) {
                    //						System.out.println(indent + ref.name + " too volatile... removing.")
                    //						guard.expression = guard.expression.replace(ref, TRUE)
                    //					} else {
                        debug(indent + ref.name + " not placed!")
                    placeable = false

                //					}
                }
            }
            for(sb : SBs) {
                if (!placedSBs.contains(sb)) {
                    debug(indent + sb.label + " not placed!")
                    placeable = false
                }
            }

            if (!(schedulingBlock.nodes.head instanceof Join)) {
                neededNodes += schedulingBlock.nodes.head.incoming.filter(typeof(ControlFlow)).map[eContainer as Node]
                for (node : neededNodes) {
                    var prevSB = schedulingBlockCache.get(node)
                    if (!topologicalSortVisited.contains(prevSB)) {
                        debug(indent + "Previous SB is missing: " + prevSB.label)
                        prevSB.topologicalPlacement(remainingSchedulingBlocks, schedule, constraints, scg, indent + "  ")
                        if (!topologicalSortVisited.contains(prevSB)) {
                            placeable = false
                        }
                    }
                }
            }

            if (placeable) {

                for (ref : lastVOs) {
                    if (!placedVOs.contains(ref)) {

                        ////						val tpGuard = guardCache.get(ref)
                        //						for (sb : schedulingBlockVOCache.get(ref)) {
                        //							sb.topologicalPlacement(remainingSchedulingBlocks, schedule, constraints, scg, indent + "  ")
                        //						}
                        addGuardBeforeScheduledBlock += guardVOCache.get(ref)
                        placedVOs += ref
                    }
                }

                debug(indent + "  Scheduling block " + schedulingBlock.label + " placed.")
                
// FIXME: broken in scg version 3
//                val scheduleBlock = ScgFactory::eINSTANCE.createScheduleBlock => [
//                    it.schedulingBlock = schedulingBlock
//                    if (!addGuardBeforeScheduledBlock.empty) {
//                        it.additionalGuards.addAll(addGuardBeforeScheduledBlock)
//                    }
//                ]

//                schedule += scheduleBlock
                placedVOs += schedulingBlock.guards.head.valuedObject
                placedSBs += schedulingBlock
                remainingSchedulingBlocks -= schedulingBlock
            }

        }

    }

    protected def boolean createSchedule(SCGraph scg, List<ScheduleBlock> schedule, SchedulingConstraints constraints,
        KielerCompilerContext context) {

        val remainingSchedulingBlocks = <SchedulingBlock>newHashSet
        remainingSchedulingBlocks += constraints.schedulingBlocks

        topologicalSortVisited.clear

        for (sb : constraints.schedulingBlocks.immutableCopy) {
            if (!topologicalSortVisited.contains(sb)) {
                sb.topologicalPlacement(remainingSchedulingBlocks, schedule, constraints, scg, "")
            }
        }

        debug("Schedule: ", false)
        for (g : schedule) {
            debug(g.schedulingBlock.label + " ", false)
        }
        debug("")

        remainingSchedulingBlocks.size == 0
    }

    /**
	 * The build method builds the schedules for the given SCG. It uses the information from the dependency and
	 * basic block analyses to build the guard expressions of the blocks. A schedule itself is an ordered list
	 * of scheduling blocks. The next transformation step can use this information to create a new SCG containing
	 * the guard expressions in the correct order.<br>
     * <pre>
     * Scheduler: Analyses --> Build schedule --> Optimization
     * </pre>
	 * This method is called by the interface. There is no need to call it directly.
	 * 
	 * @param scg
	 * 			the source SCG
	 * @return Returns the enriched SCG model.
	 */
    override public SCGraph schedule(SCGraph scg, KielerCompilerContext context) {
        // KiCo does this check via feature isContained
        // if (scg.hasAnnotation(SCGFeatures.SCHEDULING_ID)) {
        //     return scg
        // }

        // Create a new schedule using the scgsched factory.
// FIXME: broken in scg version 3
//        val schedule = ScgFactory::eINSTANCE.createSchedule

        schedulingBlockCache.clear
        allSchedulingBlocks.clear
        schedulingBlockVOCache.clear
        schedulingBlockCache = scg.createSchedulingBlockCache
        schedulingBlockCount = schedulingBlockCache.size
        scg.basicBlocks.filter[!isDeadBlock].forEach [
            allSchedulingBlocks.addAll(schedulingBlocks)
            for (sb : schedulingBlocks) {
                val vo = sb.guards.head.valuedObject
                if (schedulingBlockVOCache.keySet.contains(vo)) {
                    val sbSet = schedulingBlockVOCache.get(vo)
                    sbSet += sb
                } else {
                    val sbSet = <SchedulingBlock>newHashSet
                    sbSet += sb
                    schedulingBlockVOCache.put(vo, sbSet)
                }
            }
        ]

        // Create and fill a list for all scheduling blocks.
        val schedulingConstraints = scg.orderSchedulingBlocks

        placedVOs.clear
        scg.declarations.forEach [ decl |
            placedVOs += decl.valuedObjects
        ]

        guardVOCache.clear
        scg.guards.forEach [ g |
//            if (!g.schedulingBlockLink.basicBlock.deadBlock)
                guardVOCache.put(g.valuedObject, g)
        ]
        
        placedSBs.clear;

        val scedList = <ScheduleBlock>newLinkedList
        var schedulable = scg.createSchedule(scedList, schedulingConstraints, context)
//        schedule.scheduleBlocks += scedList

        // Print out results on the console
        // and add the scheduling information to the graph.
        if (!schedulable) {
            if (context != null) {
                context.getCompilationResult().addPostponedWarning(
                    new KielerCompilerException(getId(), getId(), "The SCG is NOT ASC-schedulable!"));
            }
            System::out.println("The SCG is NOT ASC-schedulable!")
//            scg.schedules.add(schedule)
        } else {
            System::out.println("The SCG is ASC-schedulable.")
//            scg.schedules.add(schedule)
        }
        
        scg.createStringAnnotation(SCGFeatures.SCHEDULING_ID, SCGFeatures.SCHEDULING_NAME);
        scg
    }

    private def Set<DataDependency> getAllDataDependencies(SchedulingBlock schedulingBlock, SCGraph scg) {
        val returnSet = <DataDependency>newHashSet;

//        returnSet += schedulingBlock.dependencies.filter(typeof(DataDependency))

        for(dependency : schedulingBlock.dependencies.filter(typeof(DataDependency))) {
            val node = dependency.eContainer as Node
            val sb = schedulingBlockCache.get(node)
            if (!sb.basicBlock.deadBlock) returnSet += dependency
        }

        returnSet
    }

}