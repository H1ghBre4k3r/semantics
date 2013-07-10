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
package de.cau.cs.kieler.scl.basicblocks;

import java.util.ArrayList;
import java.util.List;

import de.cau.cs.kieler.scl.scl.Instruction;
import de.cau.cs.kieler.scl.scl.InstructionStatement;
import de.cau.cs.kieler.scl.scl.Statement;
import de.cau.cs.kieler.scl.scl.impl.StatementSequenceImpl;

/**
 * @author ssm
 *
 */
public class BasicBlock {
    
    protected ArrayList<Statement> statements;
    
    public String CachedName;
    public String CachedEmptyName;
    public int CachedIndex;
    public List<BasicBlock> CachedPredecessors;
    public List<BasicBlock> CachedSuccessors;
    public List<BasicBlock> CachedDependencyPredecessors;
    
    public BasicBlock() {
        statements = new ArrayList<Statement>();
        CachedName = "";
        CachedEmptyName = "";
        CachedIndex = -1;
        CachedPredecessors = new ArrayList<BasicBlock>();
        CachedSuccessors = new ArrayList<BasicBlock>();
        CachedDependencyPredecessors = new ArrayList<BasicBlock>();
    }
    
    public BasicBlock(Statement headStatement) {
        statements = new ArrayList<Statement>();
        getStatements().add(headStatement);
        CachedName = "";
        CachedEmptyName = "";
        CachedIndex = -1;
        CachedPredecessors = new ArrayList<BasicBlock>();
        CachedSuccessors = new ArrayList<BasicBlock>();
        CachedDependencyPredecessors = new ArrayList<BasicBlock>();
    }
    
    public void add(Statement statement) {
        statements.add(statement);
    }
    
    public void addAll(List<Statement> statements) {
        statements.addAll(statements);
    }
    
    public Statement getHead() {
        return getStatements().get(0);
    }
    
    public List<Statement> getStatements() {
        return statements;
    }
    
    public boolean isEqual(BasicBlock basicBlock) {
        if (basicBlock.equals(this)) return true;
        if (basicBlock.getStatements().size()!=this.getStatements().size()) return false;
        if (this.getStatements().size()>0) {
            if (basicBlock.getHead() instanceof InstructionStatement) {
                if (!(this.getHead() instanceof InstructionStatement)) return false;
                Instruction bbInstruction = ((InstructionStatement)basicBlock.getHead()).getInstruction();
                Instruction ownInstruction = ((InstructionStatement)this.getHead()).getInstruction();
                if (bbInstruction instanceof PauseSurface && ownInstruction instanceof PauseSurface) 
                    if (((PauseSurfaceImpl)bbInstruction).PauseReference.equals(((PauseSurfaceImpl)ownInstruction).PauseReference)) return true;
                if (bbInstruction instanceof PauseDepth && ownInstruction instanceof PauseDepth) 
                    if (((PauseDepthImpl)bbInstruction).PauseReference.equals(((PauseDepthImpl)ownInstruction).PauseReference)) return true;
                if (bbInstruction instanceof ParallelFork && ownInstruction instanceof ParallelFork) 
                    if (((ParallelForkImpl)bbInstruction).ParallelReference.equals(((ParallelForkImpl)ownInstruction).ParallelReference)) return true;
                if (bbInstruction instanceof ParallelJoin && ownInstruction instanceof ParallelJoin) 
                    if (((ParallelJoinImpl)bbInstruction).ParallelReference.equals(((ParallelJoinImpl)ownInstruction).ParallelReference)) return true;
            }
            if (!basicBlock.getHead().equals(this.getHead())) return false;
        }
        return true;
    }
    
}
