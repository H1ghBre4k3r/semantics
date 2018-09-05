package de.cau.cs.kieler.scg.transformations.schedulers

import de.cau.cs.kieler.scg.transformations.SCGTransformations
import de.cau.cs.kieler.scg.features.SCGFeatures
import com.google.inject.Inject
import de.cau.cs.kieler.scg.SCGraph
import de.cau.cs.kieler.annotations.extensions.AnnotationsExtensions
import de.cau.cs.kieler.scg.GuardDependency
import java.util.LinkedHashSet
import de.cau.cs.kieler.scg.Node
import java.util.Set
import de.cau.cs.kieler.scg.extensions.SCGCoreExtensions
import de.cau.cs.kieler.scg.SCGPlugin
import java.util.logging.Level
import de.cau.cs.kieler.scg.Assignment
import de.cau.cs.kieler.kexpressions.keffects.extensions.KEffectsExtensions
import de.cau.cs.kieler.scg.extensions.SCGControlFlowExtensions
import de.cau.cs.kieler.scg.extensions.SCGDependencyExtensions

/** 
 * @author ssm
 * @kieler.design 2016-04-07 proposed 
 * @kieler.rating 2016-04-07 proposed yellow
 */
class DCGuardScheduler extends SimpleGuardScheduler {
        
    //-------------------------------------------------------------------------
    //--                 K I C O      C O N F I G U R A T I O N              --
    //-------------------------------------------------------------------------
    
    override getId() {
        return SCGTransformations::DC_SCHEDULING_ID
    }

    override getName() {
        return SCGTransformations::DC_SCHEDULING_NAME
    }

//    override getProducedFeatureId() {
//        return SCGFeatures::SCHEDULING_ID
//    }
//
//    override getRequiredFeatureIds() {
//        return newHashSet(SCGFeatures::GUARDS_ID)
//    }

    // -------------------------------------------------------------------------
    // -- Injections 
    // -------------------------------------------------------------------------
    
    @Inject extension SCGCoreExtensions
    @Inject extension SCGDependencyExtensions
    @Inject extension AnnotationsExtensions
    @Inject extension KEffectsExtensions
    @Inject extension SCGControlFlowExtensions
    
	/**
	 * {@inherited}
	 */
    public override SCGraph transform(SCGraph scg) {
    	scg.createStringAnnotation(SCGFeatures.SCHEDULING_ID, SCGFeatures.SCHEDULING_NAME)
 
    	/** 
    	 * The {@code nodesToSchedule} {@link Set} contains the nodes that are still
    	 * not scheduled. The topological sort should remove nodes after they have been placed.
    	 */
    	val nodesToSchedule = scg.nodes.filter[ incomingLinks.filter(GuardDependency).empty ].toSet
    	val estimatedScheduleSize = nodesToSchedule.size 	

		val schedules = <Set<Node>> newArrayList
		var size = 0
    	
    	// As long as there are nodes to schedule, call the topological sort method.
    	while(!nodesToSchedule.empty) {
  	    	val schedule = new LinkedHashSet<Node>
  	    	SCGPlugin.log("*** NEW SCHEDULE ***", Level.FINE)
  	    	var startingNode = nodesToSchedule.filter[ incomingLinks.filter(GuardDependency).empty ].head
    		var Boolean newSchedule 
  	    	do {
  	    		newSchedule = true
	    		topologicalScheduling(startingNode, nodesToSchedule, schedule)
	    		for(node : schedule) {
	    			for(dep : node.dependencies) {
	    				if (dep.target.incomingLinks.filter(GuardDependency).empty && nodesToSchedule.contains(dep.target)) {
	    					newSchedule = false
	    					startingNode = dep.targetNode
    					}
	    			}
	    		}
    		} while (!newSchedule)
	    	schedules += schedule
	    	size += schedule.size
    	}
    	
    	// Create schedule dependencies for the generated schedule
    	for(schedule : schedules) {
    		SCGPlugin.log("*** NEW SCHEDULE ***", Level.FINE)
	    	for(var i = 0; i < schedule.size-1; i++) {
    			schedule.get(i).createScheduleDependency(schedule.get(i+1))
    			if (schedule.get(i) instanceof Assignment && schedule.get(i+1) instanceof Assignment) 
    			SCGPlugin.log("Scheduling dependency from " + 
    				schedule.get(i).asAssignment.valuedObject.name + " to " + 
    				schedule.get(i+1).asAssignment.valuedObject.name, Level.FINE
    			)
    		}
    	}
    	
    	// ASC schedulability output
    	if (size < estimatedScheduleSize) {
    	    val message = "The SCG is NOT asc-schedulable!"
//    	    if (context != null) {
//                context.getCompilationResult().addPostponedWarning(
//                    new KielerCompilerException(getId(), getId(), message));
//            }
    		SCGPlugin.logError(message)
    	} else {
    		SCGPlugin.log("The SCG is asc-schedulable.")
    	} 
 
 		scg   	
    }    
}