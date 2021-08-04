/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2020 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.simulation.testing.processor

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import de.cau.cs.kieler.core.properties.IProperty
import de.cau.cs.kieler.core.properties.Property
import de.cau.cs.kieler.kicool.compilation.CodeContainer
import de.cau.cs.kieler.kicool.compilation.CompilationContext
import de.cau.cs.kieler.kicool.compilation.Processor
import de.cau.cs.kieler.kicool.compilation.ProcessorType
import de.cau.cs.kieler.kicool.compilation.observer.CompilationFinished
import de.cau.cs.kieler.kicool.compilation.observer.ProcessorError
import de.cau.cs.kieler.kicool.compilation.observer.ProcessorFinished
import de.cau.cs.kieler.kicool.compilation.observer.ProcessorStart
import de.cau.cs.kieler.kicool.environments.Environment
import de.cau.cs.kieler.kicool.environments.MessageObjectLink
import de.cau.cs.kieler.simulation.testing.SimulationResult
import de.cau.cs.kieler.simulation.testing.TestModelData
import de.cau.cs.kieler.simulation.testing.TestSuite
import java.util.List
import java.util.Observable
import java.util.Observer

import static extension java.lang.String.format

/**
 * @author als
 */
class JsonBenchmarkRunner extends Processor<TestSuite, CodeContainer> implements Observer {
    
    override getId() {
        "de.cau.cs.kieler.simulation.testing.benchmark.runner.json"
    }
    
    override getName() {
        "Benchmarks Runner (JSON)"
    }
    
    override getType() {
        ProcessorType.EXOGENOUS_TRANSFORMATOR
    }
    
    public static val IProperty<Boolean> PRINT_VERBOSE = 
        new Property<Boolean>("de.cau.cs.kieler.simulation.testing.benchmark.runner.json.verbose", false)
    public static val IProperty<Boolean> PRINT_VERBOSE_DEBUG = // stuff that is usually too slow
        new Property<Boolean>("de.cau.cs.kieler.simulation.testing.benchmark.runner.json.verbose.debug", false)
        
    static val gson = new GsonBuilder().setPrettyPrinting().create()
    
    override process() {
        val cc = new CodeContainer
        val results = new JsonObject
        
        if (PRINT_VERBOSE.getProperty) println("== Starting benchmarks ==")
        for (test : model.cellSet) {
            val testModel = test.rowKey
            val key = testModel.repositoryPath.fileName + ":" + testModel.modelPath.toString
            if (!results.has(key)) {
                results.add(key, new JsonObject)
            }
            
            if (PRINT_VERBOSE.getProperty) {
                println("-- Running benchmark %s on model %s".format(test.columnKey, key))
                test.value.addObserver(this)
            }
            
            val modelResults = results.get(key) as JsonObject
            val result = test.value.run(test.columnKey, test.rowKey)
            if (result !== null) {
                modelResults.add(test.columnKey, result)
            }
        }
        if (PRINT_VERBOSE.getProperty) println("== Finished benchmarks ==")
        
        cc.add("benchmark-results.json", gson.toJson(results))
        model = cc
    }
    
    protected def JsonObject run(CompilationContext test, String id, TestModelData model) {
        try {
            System.gc
            Thread.sleep(10)
            
            val result = new JsonObject
            val env = test.compile()
            
            if (test.hasErrors) {
                result.add(Environment.ERRORS.id, test.allErrors.convertToJson)
            }
            if (test.hasWarings) {
                result.add(Environment.WARNINGS.id, test.allWarnings.convertToJson)
            }
            
            val resultKeys = env.getProperty(Environment.BENCHMARK_RESULT_KEYS)
            if (resultKeys !== null) {
                for (key : resultKeys) {
                    val value = env.getProperty(new Property(key, null))
                    if (value !== null) {
                        result.add(key, value.convertToJson(key))
                    }
                }
            }
            
            if (env.model instanceof SimulationResult) {
                result.add(TraceSimulator.ID, (env.model as SimulationResult).toJson)
            }
            
            if (result.size > 0) return result
        } catch (Exception e) {
            environment.warnings.add("Test %s failed for model %s".format(id, model.modelFile), e)
            e.printStackTrace
        }
        return null
    }
    
    protected def JsonElement convertToJson(Object data, String key) {
        if (data instanceof JsonElement) {
            return data
        } else {
            try {
                return gson.toJsonTree(data)
            } catch (Exception e) {
                environment.warnings.add("Failed to convert benchmark result %s into json".format(key), e)
                e.printStackTrace
            }
        }
        return JsonNull.INSTANCE
    }
    
    protected def JsonArray convertToJson(List<MessageObjectLink> msgs) {
        val arr = new JsonArray()
        for (msg : msgs) {
            if (msg.exception !== null) {
                arr.add("%s (%s: %s)".format(msg.message?:msg.exception.message, msg.exception.class.simpleName, msg.exception.message?:""))
            } else {
                arr.add(msg.message?:"Unknown message")
            }
        }
        return arr
    }
    
    override update(Observable o, Object event) {
        switch (event) {
            CompilationFinished: {
                if (PRINT_VERBOSE.property && PRINT_VERBOSE_DEBUG.property) println("benchmark: Compilation finished in %.2fms".format(event.environment.getProperty(Environment.COMPILATION_TIME).doubleValue / 1000_000))
            }
            ProcessorStart: {
                if (PRINT_VERBOSE.property && PRINT_VERBOSE_DEBUG.property) println("benchmark: Executing processor: %s (%s)".format(event.processorInstance.name, event.processorInstance.id))
            }
            ProcessorError: {
                if (PRINT_VERBOSE.property) println("benchmark: Error in compilation:" + event.error)
            }
            ProcessorFinished: {
                if (PRINT_VERBOSE.property) {
                    val env = event.processorInstance.environment
                    if (env.errors !== null && !env.errors.empty) {
                        println("benchmark: Error(s) in processor " + event.processorInstance.name)
                        for (error : env.errors.get(Environment.REPORT_ROOT)) {
                            println(error.message)
                            if (error.exception !== null) error.exception.printStackTrace
                        }
                    }
                    if (env.warnings !== null && !env.warnings.empty) {
                        println("benchmark: Waring(s) in processor " + event.processorInstance.name)
                        for (warning : env.warnings.get(Environment.REPORT_ROOT)) {
                            println(warning.message)
                            if (warning.exception !== null) warning.exception.printStackTrace
                        }
                    }
                    if (PRINT_VERBOSE_DEBUG.property || (env.errors !== null && !env.errors.empty)) {
                        if (!env.logs.files.empty) {
                            println("benchmark: Logs of processor " + event.processorInstance.name)
                            for (log : env.logs.files) {
                                println(log.code)
                            }
                        }
                    }
                    if (PRINT_VERBOSE_DEBUG.property) println("benchmark: Processing time: %.2fms".format(env.getProperty(Environment.TRANSFORMATION_TIME).doubleValue / 1000_000))
                }
            }
        }
    }
    
}