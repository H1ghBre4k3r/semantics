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
package de.cau.cs.kieler.scl.extensions

import de.cau.cs.kieler.scl.scl.Assignment
import de.cau.cs.kieler.scl.scl.EmptyStatement
import de.cau.cs.kieler.scl.scl.Goto
import de.cau.cs.kieler.scl.scl.Instruction
import de.cau.cs.kieler.scl.scl.InstructionStatement
import de.cau.cs.kieler.scl.scl.Statement
import javax.inject.Inject
import de.cau.cs.kieler.scl.scl.Pause
import de.cau.cs.kieler.scl.scl.Conditional
import org.eclipse.emf.ecore.EObject
import de.cau.cs.kieler.scl.basicblocks.PauseSurface
import de.cau.cs.kieler.scl.basicblocks.PauseDepth
import de.cau.cs.kieler.scl.basicblocks.PauseSurfaceImpl
import de.cau.cs.kieler.scl.basicblocks.PauseDepthImpl
import de.cau.cs.kieler.scl.scl.Parallel
import de.cau.cs.kieler.scl.basicblocks.ParallelFork
import de.cau.cs.kieler.scl.basicblocks.ParallelJoin
import de.cau.cs.kieler.scl.basicblocks.ParallelForkImpl
import de.cau.cs.kieler.scl.basicblocks.ParallelJoinImpl
import de.cau.cs.kieler.scl.scl.Annotation
import de.cau.cs.kieler.scl.scl.Program

class SCLStatementExtensions {
    
    @Inject
    extension SCLCreateExtensions
    
    def boolean isEmpty(Statement statement) {
        statement instanceof EmptyStatement
    } 
    
    def boolean hasInstruction(Statement statement) {
        statement instanceof InstructionStatement
    }
    
    def String getLabel(Statement statement) {
        if (statement.isEmpty) (statement as EmptyStatement).label else null
    }
    
    def setLabel(Statement statement, String label) {
        if (statement.isEmpty) (statement as EmptyStatement).label = label
    }
    
    def boolean isGoto(Statement statement) {
        (statement.hasInstruction && statement.getInstruction instanceof Goto)
    }

    def boolean isPause(Statement statement) {
        (statement.hasInstruction && statement.getInstruction instanceof Pause)
    }

    def boolean isConditional(Statement statement) {
        (statement.hasInstruction && statement.getInstruction instanceof Conditional)
    }

    def boolean isParallel(Statement statement) {
        (statement.hasInstruction && statement.getInstruction instanceof Parallel)
    }
    
    def boolean isAssignment(Statement statement) {
        (statement.hasInstruction && statement.getInstruction instanceof Assignment)
    }
    
    def EmptyStatement asEmptyStatement(Statement statement) {
        statement as EmptyStatement
    }
    
    def InstructionStatement asInstructionStatement(Statement statement) {
        statement as InstructionStatement
    }
    
    def Instruction getInstruction(Statement statement) {
        var instruction = (statement as InstructionStatement).instruction
        if (instruction instanceof PauseSurface) instruction = (instruction as PauseSurfaceImpl).PauseReference
        else if (instruction instanceof PauseDepth) instruction = (instruction as PauseDepthImpl).PauseReference
        else if (instruction instanceof ParallelFork) instruction = (instruction as ParallelForkImpl).ParallelReference
        else if (instruction instanceof ParallelJoin) instruction = (instruction as ParallelJoinImpl).ParallelReference
        instruction
    }

    def Instruction getTrueInstruction(Statement statement) {
        var instruction = (statement as InstructionStatement).instruction
        instruction
    }
    
    def EmptyStatement removeInstruction(Statement statement) {
        if (statement instanceof InstructionStatement) {
            val emptyStatement = createSCLEmptyStatement()
            emptyStatement.label = statement.label
            return emptyStatement
        }
        return statement as EmptyStatement
    }
    
    def Statement getStatement(Instruction instruction) {
        instruction.eContainer as Statement
    }
        
    def Goto asGoto(Instruction instruction) {
        instruction as Goto
    }
    
    def Assignment asAssignment(Instruction instruction) {
        instruction as Assignment
    }
    
    def Conditional asConditional(Instruction instruction) {
        instruction as Conditional
    }
    
    def Parallel asParallel(Instruction instruction) {
        instruction as Parallel
    }
    
    def Statement getParentStatement(Instruction instruction) {
        var EObject stmt = instruction.eContainer
        while (!(stmt.eContainer instanceof Statement)) {
            if (stmt.eContainer == null) return null
            stmt = stmt.eContainer
        }
        stmt.eContainer as Statement
    }
    
    def boolean hasAnnotation(Statement statement, String name) {
        statement.annotations.filter(e | e.getName == name).size > 0
    }
    
    def boolean hasParameter(Statement statement, String name, String parameter) {
        if (!statement.hasAnnotation(name)) return false
        statement.annotations.filter(e | e.getName == name && e.parameter.contains(parameter)).size > 0    
    }
    
    def boolean hasGlobalAnnotation(Program program, String name) {
        if (program.statements.size == 0) return false
        program.statements.head.hasAnnotation(name)
    }
    
    def boolean hasGlobalParameter(Program program, String name, String parameter) {
        if (!program.hasGlobalAnnotation(name)) return false
        program.statements.head.annotations.filter(e | e.getName == name && e.parameter.contains(parameter)).size > 0
    }
    
}