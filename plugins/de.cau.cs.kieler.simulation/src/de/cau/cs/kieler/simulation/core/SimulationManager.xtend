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
package de.cau.cs.kieler.simulation.core

import java.util.List
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Status
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * The simulation manager holds a configuration of a simulation and takes care of its execution.
 * The configuration is a list of step actions to perform in a loop.
 * A single iteration of all step actions represent the execution of a macro tick.
 * <br><br>
 * Executing a simulation step will call the method of the step action on the data handler in the step action
 * and thereby updating the current state of the simulation.
 * <br><br>
 * The manager can step through the simulation handler by handler<br>
 * or using all handlers of a macro tick at once<br>
 * or executing the step actions continuously in a loop.
 * <br><br>
 * The continuously execution is done in a separate thread until it is paused (<i>playing</i> and <i>pausing</i> the simulation). 
 * 
 * @author aas
 *
 */
class SimulationManager {
    
    /**
     * Singleton instance.
     */
    public static var SimulationManager instance
    
    /**
     * List of event listeners
     */
     private static val List<SimulationListener> listeners = newArrayList
    
    /**
     * The job that executes the step actions concurrently when playing.
     */
    private var Job steppingJob
    /**
     * Is the simulation performed continuously in a separate thread? 
     */
    @Accessors(PUBLIC_GETTER)
    private var boolean isPlaying
    
    /**
     * The pause in milliseconds that is waited
     * until the next tick is simulated when in play mode. 
     */
    @Accessors
    private var int playDelay = 100
    
    /**
     * Has the simulation been stopped? 
     */
     @Accessors(PUBLIC_GETTER)
    private var boolean isStopped
    
    /**
     * Instances of the data handlers in the step actions without duplicates.
     */
    // TODO: make private
    public val List<DataHandler> dataHandlers = newArrayList()
    /**
     * The list of step actions that make up a macro tick simulation.
     */
     // TODO: make private
    public val List<StepAction> actions = newArrayList()

    /**
     * The current state of the simulation.
     */
    private var StepState currentState
    /**
     * The history of state of the simulation from old to new.
     */
    private var List<StepState> history = newArrayList()
    
    /**
     * Constructor
     */
    new() {
        if(instance != null) {
            instance.stop()
        }
        instance = this
    }
    
    /**
     * Adds a data handler
     */
    public def void addHandler(DataHandler handler) {
        if(!dataHandlers.contains(handler))
            dataHandlers.add(handler)
    }
    
    /**
     * Adds a step action.
     * A step action to read a data handler should not be added, if that handler is updated after every step anyway. 
     * In this case it is sufficient to add this handler to the list of data handlers. 
     */
    public def void addAction(StepAction.Method method, DataHandler handler) {
        addHandler(handler)
        actions.add(new StepAction(method, handler))
    }
    
    /**
     * Add simulator to an already initialized simulation.
     */
    public def void append(Simulator simulator) {
        simulator.initialize(currentPool)
        
//        println("Appended simulator")
        notifyListeners(SimulationEventType.APPEND_SIMULATION)
    }
    
    /**
     * Initialize the simulation.
     * All simulators are initilized to create the initial data pool.
     */
    public def void initialize() {
        val pool = new DataPool()
        for(handler : dataHandlers) {
            if(handler instanceof Simulator) {
                handler.initialize(pool)
            }
        }
        
        // Save initial state
        currentState = new StepState(pool, 0)
        
//        println("Initilized simulation")
        notifyListeners(SimulationEventType.INITIALIZED)
    }
    
    /**
     * Execute a single step action and save the resulting state.
     */
    public def void stepSubTick() {
        if(isStopped) {
            System.err.println("Simulation was stopped")
            return
        } else if(isPlaying) {
            pause()
        }
        
        // Apply user values
        applyUserValues()
        
        // Create following state
        val DataPool pool = createNextPool()
        // Perform action on this new state
        currentAction.apply(pool)
        // Save new state
        setNewState(pool, currentState.actionIndex + 1)
        
//        println("Sub Stepped simulation")
        notifyListeners(SimulationEventType.SUB_STEP)
    }
    
    /**
     * Execute all step actions that make up a macro tick and save the resulting state.
     */
    public def void stepMacroTick() {
        if(isStopped) {
            System.err.println("Simulation was stopped")
            return
        } else if(isPlaying) {
            pause()
        }
        
        applyUserValues()
        
        // Create following state
        val DataPool pool = createNextPool()
        // Perform actions on this new state
        val nextActionIndex = applyMacroTickActions(pool)
        // Save new state
        setNewState(pool, nextActionIndex)
        
//        println("Stepped simulation macro tick")
        notifyListeners(SimulationEventType.STEP)
    }
    
    /**
     * Go back to the previous state of the simulation.
     */
    public def void stepBack() {
        if(isStopped) {
            System.err.println("Simulation was stopped")
            return
        } else if(isPlaying) {
            pause()
        }
        
        // Load state from history
        val previousState = history.last
        if(previousState != null) {
            history.remove(previousState)
            currentState = previousState
        }
        
        notifyListeners(SimulationEventType.STEP_BACK)
    }

    /**
     * Execute stepping through the simulation frequently in a dedicated thread.
     */
    public def void play() {
        if(isStopped) {
            System.err.println("Simulation was stopped")
            return
        }
        if(!isPlaying) {
            isPlaying= true
            
            steppingJob = new Job("Simulation Player") {
                override protected run(IProgressMonitor monitor) {
                    while(isPlaying) {
                        println("Playing simulation")
                        // Perform a step after a period of time
                        Thread.sleep(playDelay);
                        
                        // Create following state
                        val DataPool pool = createNextPool()
                        // Perform actions on this new state
                        val nextActionIndex = applyMacroTickActions(pool)
                        // Save new state
                        setNewState(pool, nextActionIndex)
                        
                        // Notify listeners of new state
                        notifyListeners(SimulationEventType.PLAYING)
                    }
                    notifyListeners(SimulationEventType.STEP)
                    return Status.OK_STATUS
                }
            }
            steppingJob.schedule()
        }
    }
    
    /**
     * Stop the dedecated thread for simulation execution.
     */
    public def void pause() {
        if(isStopped) {
            System.err.println("Simulation was stopped")
            return
        }
        if(isPlaying) {
            isPlaying = false
        }
        
        notifyListeners(SimulationEventType.PAUSE)
    }
    
    /**
     * Stop the simulation and all data handlers.
     */
    public def void stop() {
        if(isStopped) {
            System.err.println("Simulation was stopped")
            return
        }
        
        isStopped = true
        isPlaying = false
        for(handler : dataHandlers) {
            handler.stop()
        }
        currentState = null
        
        notifyListeners(SimulationEventType.STOP)
    }
    
    /**
     * Returns the data pool of the current state
     */
    public def DataPool getCurrentPool() {
        if(currentState == null) {
            return null
        }
        return currentState.pool
    }
    
    /**
     * Returns the step action of the current state's step action index.
     */
    public def StepAction getCurrentAction() {
        if(currentState == null) {
            return null
        }
        return getActionStep(currentState.actionIndex)
    }
    
    /**
     * Returns the step action with the given step action index.
     */
    public def StepAction getActionStep(int index) {
        if(actions.isNullOrEmpty) {
            return null
        } 
        val relativeActionIndex = index % actions.size()
        return actions.get(relativeActionIndex)
    }
    
    /**
     * Sets the current state to a new state and updates the history.
     */
    private def void setNewState(DataPool newPool, int newActionIndex) {
        if(currentState != null) {
            history.add(currentState)
        }
        currentState = new StepState(newPool, newActionIndex)
    }
    
    /**
     * Create the data pool for the following state.
     */
    private def DataPool createNextPool() {
        val pool = currentState.pool.clone()  
        pool.previousPool = currentPool
        return pool
    }
    
    /**
     * Applies all step actions that make up a macro tick to the given pool.
     */
    private def int applyMacroTickActions(DataPool pool) {
        // Round action index up to next fully done macro tick
        val macroTickActionCount = actions.size()
        val currentActionIndex = currentState.actionIndex
        val nextActionIndex = ((currentActionIndex + macroTickActionCount) / macroTickActionCount) * macroTickActionCount
        // Apply all data handlers up to next fully done macro tick
        for(var i = currentActionIndex; i < nextActionIndex; i++) {
            getActionStep(i).apply(pool)
        }
        
        return nextActionIndex
    }
    
    /**
     * Applies modifications to variables in the current pool made by the user. 
     */
    private def void applyUserValues() {
        // Apply user made changes to variable values
        for(m : currentPool.models) {
            for(v : m.variables) {
                if(v.isDirty) {
                    v.value = v.userValue
                }
            }
        }
    }
    
    /**
     * Notifies all listeners about an event.
     */
     protected def void notifyListeners(SimulationEventType type, Variable variable) {
         val e = new SimulationEvent()
         e.type = type
         e.newPool = currentPool
         e.modifiedVariable = variable
         for(l : listeners) {
             l.update(e)
         }
     }
     
     /**
     * Notifies all listeners about an event.
     */
     protected def void notifyListeners(SimulationEventType type) {
         val e = new SimulationEvent()
         e.type = type
         e.newPool = currentPool
         for(l : listeners) {
             l.update(e)
         }
     }
     
     public static def void addListener(SimulationListener listener) {
         if(!listeners.contains(listener)) {
             listeners.add(listener)
         }
     }
     
     public static def void removeListener(SimulationListener listener) {
         listeners.remove(listener)
     }
}