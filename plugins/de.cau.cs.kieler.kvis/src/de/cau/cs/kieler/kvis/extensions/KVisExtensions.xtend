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
package de.cau.cs.kieler.kvis.extensions

import de.cau.cs.kieler.kexpressions.BoolValue
import de.cau.cs.kieler.kexpressions.FloatValue
import de.cau.cs.kieler.kexpressions.IntValue
import de.cau.cs.kieler.kexpressions.OperatorType
import de.cau.cs.kieler.kexpressions.StringValue
import de.cau.cs.kieler.kexpressions.Value
import de.cau.cs.kieler.kvis.kvis.Action
import de.cau.cs.kieler.kvis.kvis.AndExpression
import de.cau.cs.kieler.kvis.kvis.Animation
import de.cau.cs.kieler.kvis.kvis.AttributeMapping
import de.cau.cs.kieler.kvis.kvis.Comparison
import de.cau.cs.kieler.kvis.kvis.Condition
import de.cau.cs.kieler.kvis.kvis.Domain
import de.cau.cs.kieler.kvis.kvis.Mapping
import de.cau.cs.kieler.kvis.kvis.SimulationOperation
import de.cau.cs.kieler.kvis.kvis.VariableReference
import de.cau.cs.kieler.simulation.core.DataPool
import de.cau.cs.kieler.simulation.core.NDimensionalArray
import de.cau.cs.kieler.simulation.core.SimulationManager

/**
 * @author aas
 *
 */
class KVisExtensions {
    public def Object getVariableValue(VariableReference ref, DataPool pool) {
        val modelName = ref?.model?.name
        val variableName = ref?.name
        if(variableName != null) {
            val variable = pool.getVariable(modelName, variableName);
            if(variable == null) {
                throw new IllegalArgumentException("Variable '"+variableName+"' was not found in the data pool.")
            }
            if(variable.value instanceof NDimensionalArray) {
                val array = variable.value as NDimensionalArray
                return array.get(ref.indices)
            } else {
                return variable.value
            }
        }
        return null
    }
    
    public def boolean eval(Condition cond, DataPool pool) {
        if(cond instanceof AndExpression) {
            return (cond as AndExpression).eval(pool)
        } else if(cond instanceof Comparison) {
            return (cond as Comparison).eval(pool)
        }
        return true
    }
    
    public def boolean eval(Comparison cond, DataPool pool) {
        if(cond != null) {
            val leftValue = cond.left.getVariableValue(pool)
            val right = cond.right
            var Object rightValue
            
            if(right instanceof Value) {
                rightValue = right.primitiveValue
            } else if(right instanceof VariableReference) {
                rightValue = right.getVariableValue(pool)
            }
            if( leftValue != null && rightValue != null) {
                try {
                    switch(cond.relation) {
                        case OperatorType.EQ : return leftValue.equalsValue(rightValue)
                        case OperatorType.NE : return !leftValue.equalsValue(rightValue)
                        case OperatorType.GT : return leftValue.doubleValue > rightValue.doubleValue
                        case OperatorType.LT : return leftValue.doubleValue < rightValue.doubleValue
                        case OperatorType.GEQ : return leftValue.doubleValue >= rightValue.doubleValue
                        case OperatorType.LEQ : return leftValue.doubleValue <= rightValue.doubleValue
                        default: {
                            return false
                        }
                    }
                } catch(IllegalArgumentException e) {
                    return false
                }        
            }
            cond.left
        }
        return true
    }
    
    public def boolean eval(AndExpression cond, DataPool pool) {
        // Because we have all comparision combined by and,
        // we can immediately return false, if any of them evaluates to false.
        if(!cond.left.eval(pool)) {
            return false
        } else if(!cond.right.eval(pool)) {
            return false
        }
        return true
    }
    
    public def Object getPrimitiveValue(Value value) {
        if(value instanceof StringValue) {
            return value.value
        } else if(value instanceof FloatValue) {
            return value.value
        } else if(value instanceof IntValue) {
            return value.value
        } else if(value instanceof BoolValue) {
            return value.value
        }
        return null
    }
    
    public def Object apply(Mapping mapping, Object value) {
        if(mapping.attributeDomain.value != null) {
            return mapping.attributeDomain.value.primitiveValue
        } else if(mapping.attributeDomain.range != null && mapping.variableDomain.range != null) {
            val doubleValue = value.doubleValue
            val fromLow = mapping.variableDomain.range.from.primitiveValue.doubleValue
            val fromHigh = mapping.variableDomain.range.to.primitiveValue.doubleValue
            val toLow = mapping.attributeDomain.range.from.primitiveValue.doubleValue
            val toHigh = mapping.attributeDomain.range.to.primitiveValue.doubleValue
            // Vector calculation v = pos + percent*length
            val mappedValue = scale(doubleValue, fromLow, fromHigh, toLow, toHigh)
            return mappedValue
        }
        return null
    }
    
    public def double scale(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        val double percent = Math.abs(value - fromLow) / Math.abs(fromHigh - fromLow)
        val mappedValue = (toLow + percent * Math.abs(toHigh-toLow))
        return mappedValue
    }
    
    public def boolean matches(Domain domain, Object value) {
        if(domain.value != null) {
            val domValue = domain.value.primitiveValue
            return domValue.equalsValue(value)
        } else if(domain.range != null) {
            try {
                val doubleValue = value.doubleValue
                val low = domain.range.from.primitiveValue.doubleValue
                val high = domain.range.to.primitiveValue.doubleValue
                return (low <= doubleValue) && (doubleValue <= high)
            } catch(IllegalArgumentException e) {
                // Just go to 'return false' at the end of the method.
            }
        }
        return false
    }
    
    public def AttributeMapping getAttribute(Animation animation, String name) {
        return animation.attributeMappings.findFirst[it.attribute.equals(name)]
    }
    
    public def void perform(Action action, DataPool pool) {
        if(action.variable != null && action.value != null) {
            action.variable.performAssignment(action.value, pool)
        } else if(action.operation != null) {
            println(">>>>ACTION:"+action.operation+":"+action.operation.getName)
            perform(action.operation)
        }
    }
    
    public def void perform(SimulationOperation operation) {
        println("Performing simulation "+operation.getName)
        val simulation = SimulationManager.instance
        if(simulation != null && !simulation.isStopped) {
            switch(operation) {
                case SimulationOperation.STEP : {
                    simulation.stepMacroTick
                }
                case SimulationOperation.PLAY : {
                    simulation.play
                }
                case SimulationOperation.PAUSE : {
                    simulation.pause
                }
                case SimulationOperation.STOP : {
                    simulation.stop
                }
            }
        }
    }
    
    public def void performAssignment(VariableReference variableReference, Value value, DataPool pool) {
        val primitive = value.primitiveValue
        val modelName = variableReference.model?.name
        val variableName = variableReference.name
        val index = variableReference.indices
        val indexText = if(index.isNullOrEmpty) "" else index.toString
        println("Performing "+variableName+indexText+" = "+primitive)
        
        val variable = pool.getVariable(modelName, variableName)
        if(variable != null) {
            val currentValue = variable.value
            if(currentValue instanceof NDimensionalArray) {
                val newValue = currentValue.clone
                newValue.set(index, primitive)
                variable.userValue = newValue
            } else {
                variable.userValue = primitive
            }
        }
    }
    
    public def double getDoubleValue(Object value) {
        var double doubleValue
        if(value instanceof Double){
            doubleValue = value as Double
        } else if(value instanceof Float) {
            doubleValue = value as Float
        } else if(value instanceof Integer) {
            doubleValue = value as Integer
        } else if(value instanceof String) {
            doubleValue = Double.valueOf((value as String).removeQuotes)
        } else {
            throw new IllegalArgumentException("Can't convert "+value.toString+" to Double")
        }
        return doubleValue
    }
    
    public def boolean equalsValue(Object v1, Object v2) {
        if(v1 == null && v2 == null) {
            return true
        }
        if(v1 != null && v2 != null) {
            if(v1 instanceof String || v1 instanceof Boolean) {
                return v1.equals(v2)
            }
            if(v1 instanceof Number && v2 instanceof Number) {
                return v1.doubleValue.equals(v2.doubleValue)
            }
        }
        return false
    }
    
    public def String removeQuotes(String txt) {
        if(txt == null) {
            return null    
        }
        return txt.replaceAll("\"", "")
    }
}