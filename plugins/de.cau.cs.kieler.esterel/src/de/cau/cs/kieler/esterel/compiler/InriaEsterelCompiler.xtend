/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2018 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.esterel.compiler

import de.cau.cs.kieler.core.model.properties.IProperty
import de.cau.cs.kieler.core.model.properties.Property
import de.cau.cs.kieler.kicool.environments.Environment
import java.io.File
import java.util.ArrayList
import java.util.List
import java.util.Map

/**
 * @author als
 * @kieler.design proposed
 * @kieler.rating proposed yellow
 */
class InriaEsterelCompiler extends AbstractEsterelCompiler {

    public static val ESTEREL_EXTENSION = ".strl"

    public static val IProperty<Boolean> XES_CODE = 
        new Property<Boolean>("de.cau.cs.kieler.esterel.compiler.inria.xes", false)
        
    public static val IProperty<Boolean> PREFER_ENV = 
        new Property<Boolean>("de.cau.cs.kieler.esterel.compiler.inria.env", false)

    public static val ID = "BerryEsterelV5_100"
    
    val Environment environment
    
    new(Environment environment) {
        this.environment = environment
        
        if (environment.getProperty(PREFER_ENV)) {
            if (System.getenv().containsKey('ESTEREL')) {
                root = new File(System.getenv().get('ESTEREL'))
            }
        } else if (PROVIDERS.containsKey(ID)) {
            root = PROVIDERS.get(ID).rootDir
        }
        if (!available) { // Try ENV
            if (System.getenv().containsKey('ESTEREL')) {
                root = new File(System.getenv().get('ESTEREL'))
            }
        }
        checkExecutableFlags()
    }
    
    override getName() {
        return ID
    }
    
    override configureEnvironment(Map<String, String> map) {
        if (root !== null) {
            map.put("ESTEREL", root.absolutePath)
        }
    }
    
    override generateCodeCommand(List<File> files, ArrayList<String> options) {
        val bin = new File(root, "bin")
        val command = <String>newArrayList
        
        command += new File(bin, "esterel").absolutePath
        command += "-v"
        command += "-W"
        command += "-stat"
        command += "-size"
        
        if (environment.getProperty(XES_CODE)?:false) {
            command += "-I" // lass check -> possible runtime errors in xes
            command += "-simul"
        }
        
        command += options
        command += files.map[toString]
        
        return command
    }
    
    override getExpectedResults(List<File> files) {
        return files.filter[it.name.endsWith(ESTEREL_EXTENSION)].map[new File(it.parent, it.name.replace(ESTEREL_EXTENSION, ".c"))].toList
    }
    
    def compileXESCommand(List<File> files, List<String> options, String target) {
        val command = <String>newArrayList
        
        command += XESPath.absolutePath
        command += options
        command += files.map[toString]
        command += "-o"
        command += target
        
        return command
    }
    
    def getXESPath() {
        return new File(new File(root, "bin"), "xes")
    }
    
    def supportsXES() {
        return XESPath.exists
    }
    
    protected def checkExecutableFlags() {
        var succeeded = true
        if (isAvailable) {
            val bin = new File(root, "bin")
            for (exe : bin.listFiles) {
                if (!exe.name.contains(".") || exe.name.endsWith(".exe")) {
                    if (!exe.canExecute) {
                        val success = exe.executable = true
                        succeeded = succeeded && success
                    }
                }
            }
            if (!succeeded) {
                environment.warnings.add("Failed to set executable flag of the esterel compiler")
            }
        }
    }
        
       
}