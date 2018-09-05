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
package de.cau.cs.kieler.kicool.deploy.processor

import de.cau.cs.kieler.core.model.properties.IProperty
import de.cau.cs.kieler.core.model.properties.Property
import de.cau.cs.kieler.kicool.deploy.ProjectInfrastructure
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.List
import java.util.concurrent.TimeUnit
import java.io.File

/**
 * @author als
 * @kieler.design proposed
 * @kieler.rating proposed yellow
 */
abstract class AbstractSystemCompilerProcessor<I> extends AbstractDeploymentProcessor<I> {
    
    public static val IProperty<Boolean> INCLUDE_GENERATED_FILES = 
        new Property<Boolean>("de.cau.cs.kieler.kicool.deploy.compiler.sources.include-generated", true)

    public static val IProperty<List<String>> SOURCES = 
        new Property<List<String>>("de.cau.cs.kieler.kicool.deploy.compiler.sources", null)
        
    public static val IProperty<String> BIN_FOLDER = 
        new Property<String>("de.cau.cs.kieler.kicool.deploy.compiler.folder.bin", "bin")
        
    public static val IProperty<String> ADDITIONAL_OPTIONS = 
        new Property<String>("de.cau.cs.kieler.kicool.deploy.compiler.options", "")
        
    public static val IProperty<Long> TIMEOUT_SEC = 
        new Property<Long>("de.cau.cs.kieler.kicool.deploy.compiler.timeout", 60L)
    
    def invoke(List<String> command, File directory) {
        logger.println("Invoking command: " + command.join(" "))
        val pb = new ProcessBuilder(command)
        pb.directory(directory)
        pb.redirectErrorStream(true)
        
        try {
            val p = pb.start
            val pReader = new BufferedReader(new InputStreamReader(p.inputStream));
            var String line = null;
            while ( (line = pReader.readLine()) !== null) {
                logger.println(line)
            }
            
            val timeout = environment.getProperty(TIMEOUT_SEC)?:TIMEOUT_SEC.^default
            val intime = p.waitFor(timeout, TimeUnit.SECONDS)
            if (!intime) {
                environment.errors.add("Command execution timed out (" + timeout + " sec)")
                logger.print("ERROR: Command execution timed out (" + timeout + " sec)")
            }
            
            logger.println("Exit value: " + p.exitValue)
            return p.exitValue
        } catch (Exception e) {
            environment.errors.add("Error while invoking command", e)
            logger.print("ERROR: Exception while invoking command")
            e.printStackTrace(logger)
        }
    }
    
}