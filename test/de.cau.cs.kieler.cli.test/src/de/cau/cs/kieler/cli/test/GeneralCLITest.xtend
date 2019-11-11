/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 * 
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2019 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.cli.test

import de.cau.cs.kieler.core.Platform
import java.io.File
import java.nio.file.Files
import org.junit.BeforeClass
import org.junit.Test

import static org.junit.Assert.assertEquals

/** 
 * @author als
 */
class GeneralCLITest extends AbstractCLITest {
    
    static val artifact = new File("../../build/de.cau.cs.kieler.kicool.cli/target/exe/kico-" + platformExePostfix )
    static val compiler = new File("./bin/kico" + (Platform.isWindows ? ".bat" : "")) // this is needed because if the windows version is used, then the file has to end with .bat
    
    @BeforeClass
    static def void setUpOnce() throws Exception {
        setupCompiler(artifact, compiler)
    }

    @Test
    def void testIndentityCompile() {
        val dir = setupTest("identity-compile")
        val src = new File(dir, "sctx/abo/abo.sctx")
        val dest = new File(dir, "sctx/abo/abo.result.sctx")
        
        // compiler
        val command = #[compiler.path, "-v", src.path, "-o", dest.path]
        assertEquals("Exit value not zero", 0, command.invoke)
        
        // check results
        assertExists(dest)
        val srcContent = new String(Files.readAllBytes(src.toPath))
        val destContent = new String(Files.readAllBytes(dest.toPath))
        assertEquals(srcContent.replaceAll("\\s+",""), destContent.replaceAll("\\s+",""))
    }
    
    @Test
    def void testSimulationExeCompile() {
        val dir = setupTest("simulation-exe-compile")
        val src = new File(dir, "sctx/abo/abo.sctx")
        val dest = new File(dir, "sctx/abo/simulation.exe")
        val dest2 = new File(dir, "sctx/abo/sim")
        
        // compiler
        val command = #[compiler.path, "-v", "-s", "de.cau.cs.kieler.sccharts.simulation.netlist.c", src.path]
        assertEquals("Exit value not zero", 0, command.invoke)
        
        // check results
        assertExists(dest)
        
        // compiler
        val command2 = #[compiler.path, "-v", "-s", "de.cau.cs.kieler.sccharts.simulation.netlist.c", "-o", dest2.path, src.path]
        assertEquals("Exit value not zero", 0, command2.invoke)
        
        // check results
        assertExists(dest2)
    }
    
    @Test
    def void testSimulationJarCompile() {
        val dir = setupTest("simulation-jar-compile")
        val src = new File(dir, "sctx/abo/abo.sctx")
        val dest = new File(dir, "sctx/abo/Simulation.jar")
        val dest2 = new File(dir, "sctx/abo/sim.jar")
        
        // compiler
        val command = #[compiler.path, "-v", "-s", "de.cau.cs.kieler.sccharts.simulation.netlist.java", src.path]
        assertEquals("Exit value not zero", 0, command.invoke)
        
        // check results
        assertExists(dest)
        
        // compiler
        val command2 = #[compiler.path, "-v", "-s", "de.cau.cs.kieler.sccharts.simulation.netlist.java", "-o", dest2.path, src.path]
        assertEquals("Exit value not zero", 0, command2.invoke)
        
        // check results
        assertExists(dest2)
    }
}
