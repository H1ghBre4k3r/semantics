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
package de.cau.cs.kieler.kicool.compilation.internal

import de.cau.cs.kieler.kicool.compilation.Environment
import org.eclipse.emf.ecore.EObject

import static extension org.eclipse.xtext.EcoreUtil2.*
import java.util.List
import java.util.LinkedList
import de.cau.cs.kieler.kicool.compilation.IKiCoolCloneable
import de.cau.cs.kieler.kicool.KVPair

/**
 * Internal class for handling the processor environments.
 * 
 * @author ssm
 * @kieler.design 2017-02-19 proposed
 * @kieler.rating 2017-02-19 proposed yellow  
 */
class EnvironmentManager {
    
    public static val ENVIRONMENT_SOURCEMODEL = "sourceModel"
    public static val ENVIRONMENT_INPLACE = "inplace"
    
    static def setSourceModel(Environment environment, Object model) {
        environment.data.put(ENVIRONMENT_SOURCEMODEL, model)
    }
    
    static def getSourceModel(Environment environment) {
        environment.data.get(ENVIRONMENT_SOURCEMODEL)
    }
    
    static def setInplaceCompilation(Environment environment, boolean inplace) {
        environment.data.put(ENVIRONMENT_INPLACE, inplace)
    }
    
    static def boolean getInplaceCompilation(Environment environment) {
        environment.data.get(ENVIRONMENT_INPLACE) as Boolean
    }
    
 
    static def Environment preparePrimeEnvironment(Environment environment) {
        val prime = new Environment()
        prime.copyFromEnvironment(environment)
        
        prime
    }   
    
    static def void copyFromEnvironment(Environment prime, Environment environment) {
        val inplace = environment.inplaceCompilation
     
        for(k : environment.data.keySet) {
            val v = environment.data.get(k)
            if (v instanceof EObject) {
                if (inplace) {
                    prime.data.put(k, v)
                } else {
                    prime.data.put(k, v.copy)
                }
            } else {
                if (v instanceof Integer) {
                    prime.data.put(k, new Integer(v))
                } else if (v instanceof Boolean) {
                    prime.data.put(k, new Boolean(v))
                } else if (v instanceof Double) {
                    prime.data.put(k, new Double(v))
                } else if (v instanceof Long) {
                    prime.data.put(k, new Long(v))
                } else if (v instanceof String) {
                    prime.data.put(k, new String(v))
                } else if (v instanceof IKiCoolCloneable) {
                    if (!v.volatile) {
                        if (inplace) {
                            prime.data.put(k, v)
                        } else {
                            prime.data.put(k, v.cloneObject)
                        }
                    }
                } else if (v instanceof List<?>) {
                    if (k.equals(Environment.ERRORS)) {
                        prime.data.put(k, new LinkedList<String>(v as List<String>))
                    }
                } else {
                    prime.data.put(k, v)
                    if (!inplace) {
                        System.err.println("Prime environment wants to copy value of key \"" + k + "\", but the value "+ 
                            "does not seem to be cloneable. This might be ok, but you should resolve this.");
                    }
                }
            }
        }   
    }
    
    static def processEnvironmentSetter(Environment environment, List<KVPair> kvPairList) {
        for(pair : kvPairList) {
            var Object setTo = null
            
            if (pair.isIsKeyValue) {
                setTo = environment.data.get(pair.value)
            } else {
                val v = pair.value
                try {
                    val myInt = Integer.parseInt(v)
                    setTo = myInt
                } catch (NumberFormatException e) {
                    try {
                        val myDouble = Double.parseDouble(v)
                        setTo = myDouble
                    } catch (NumberFormatException e2) {
                        if (v.equalsIgnoreCase("true")) setTo = true
                        else if (v.equalsIgnoreCase("false")) setTo = false
                        else setTo = v
                    }
                } 
            }
            
            environment.data.put(pair.key, setTo)
        }
    }
}