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

import com.google.inject.Inject
import java.util.ArrayList
import de.cau.cs.kieler.scl.scl.Statement
import de.cau.cs.kieler.scl.scl.Instruction
import de.cau.cs.kieler.scl.scl.Goto
import de.cau.cs.kieler.scl.scl.StatementScope
import de.cau.cs.kieler.scl.scl.Pause
import de.cau.cs.kieler.scl.scl.Thread
//import de.cau.cs.kieler.yakindu.sccharts.model.stext.synctext.VariableDefinition
import de.cau.cs.kieler.scl.scl.VariableDefinition
import org.yakindu.sct.model.sgraph.Event
import de.cau.cs.kieler.yakindu.sccharts.model.stext.synctext.EventDefinition
import de.cau.cs.kieler.scl.scl.Assignment
import org.yakindu.sct.model.sgraph.Effect
import de.cau.cs.kieler.yakindu.sccharts.model.stext.synctext.ReactionEffect
import org.yakindu.sct.model.stext.stext.AssignmentExpression
import org.yakindu.sct.model.stext.stext.ElementReferenceExpression
import de.cau.cs.kieler.scl.scl.Conditional
import org.yakindu.sct.model.sgraph.Trigger
import de.cau.cs.kieler.yakindu.sccharts.model.stext.synctext.ReactionTrigger
import java.util.List

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import org.yakindu.base.types.impl.PrimitiveTypeImpl
import org.yakindu.base.types.Type
import org.yakindu.sct.model.stext.stext.Expression
import org.yakindu.sct.model.stext.stext.AssignmentOperator
//import de.cau.cs.kieler.yakindu.sccharts.model.stext.synctext.VariableDefinition
import org.yakindu.sct.model.sgraph.Declaration
import org.yakindu.base.types.TypesFactory
import org.yakindu.sct.model.stext.types.STextDefaulTypeSystem

class SCLCreateExtensions {
  
    @Inject
    extension SCLFactoryExtensions
    
    @Inject
    extension STextDefaulTypeSystem
    
    @Inject
    extension SCLExpressionExtensions
    
    // ======================================================================================================
    // ==                       C R E A T E   M E T A M O D E L   E X T E N S I O N                        ==
    // ======================================================================================================
    
    /*
     * Methods for easy object creation
     */
    
    // Create a new list of EObjects
    def createNewStatementList()
    {
        new ArrayList<Statement>
    }
    
    // Create a new list of EObjects and insert the first instruction
    def createNewStatementList(Statement statement) {
        createNewStatementList.add(statement);
    }
    
    def createNewInstructionList(Instruction instruction) {
        val statement = SCL.createInstructionStatement()
        statement.instruction = instruction
        createNewStatementList(statement)
    }
    
    def createSCLInstructionStatement() {
        SCL.createInstructionStatement()
    }
    
    def createSCLInstructionStatement(Instruction instruction) {
        val statement = createSCLInstructionStatement()
        statement.instruction = instruction
        statement
    }
    
    def createStatement(Instruction instruction) {
        createSCLInstructionStatement(instruction)
    }
    
    def List<Statement> createStatements(List<Assignment> instructions) {
        val list = new ArrayList<Statement>
        instructions.forEach(e|list.add(e.createStatement))
        list
    }
   
    def createSCLEmptyStatement() {
        SCL.createEmptyStatement()
    }
    
    // Create a goto statement with target string
    def Goto createSCLGoto(String targetLabelName)
    {
        var goto = SCL.createGoto()
        goto.setTargetLabel(targetLabelName)
        goto
    }
    
    // Create a SCL comment 
//    def Annotation createSCLComment(String commentString) {
//        var comment = SCL.createAnnotation();
//        comment.setComment('// '+commentString);
//        comment;
//    }
    
    // Create a SCL label
    def Statement createSCLEmptyStatement(String labelName) {
        val statement = SCL.createEmptyStatement()
        statement.setLabel(labelName)
        statement
    }
    
    def StatementScope createSCLStatementScope() {
        SCL.createStatementScope()
    }
    
    // Create a SCL pause
    def Pause createSCLPause() {
        SCL.createPause()
    }
    
   
   // Create a new VariableDeclaration object
    def VariableDefinition createSCLVariableDefinition() {
        SCL.createVariableDefinition()
    }
    
    // Create a new VariableDeclaration or return an old one, if it already exists in the given context
    def VariableDefinition create varDef: SCL.createVariableDefinition()
        createVariableDefinition(Declaration definition) {
        varDef.setName(definition.getName());
        if (definition instanceof EventDefinition) {
            val eventDefinition = definition as EventDefinition;
            varDef.setInput(eventDefinition.isInput);
            varDef.setOutput(eventDefinition.isOutput);
            if (eventDefinition.varInitialValue != null) varDef.setInitialValue(eventDefinition.varInitialValue)
            
            if (eventDefinition.getType() != null) {
                varDef.setType(eventDefinition.getType());
            }
        }
        if (definition instanceof de.cau.cs.kieler.yakindu.sccharts.model.stext.synctext.VariableDefinition) {
            val varD = definition as de.cau.cs.kieler.yakindu.sccharts.model.stext.synctext.VariableDefinition
            varDef.setInput(varD.isInput)
            varDef.setOutput(varD.isOutput)
            if (varD.initialValue != null) varDef.setInitialValue(varD.initialValue)
            if (varD.type != null) varDef.setType(varD.type)
        }
    }


    def VariableDefinition create varDef: SCL.createVariableDefinition()
        createVariableDefinition(String name, String type) {

        varDef.setName(name);
        varDef.setInput(false);
        varDef.setOutput(false);
        if (!type.nullOrEmpty) {
//            val primitiveType = TypesFactory::eINSTANCE.createPrimitiveType
//            primitiveType.setName(type)
            varDef.setType(types.findFirst[it.name==type]);
//                primitiveType as Type
//            )
        }
    }

   
    // Create a new SCL assignment statement and copy the first action in the given stext effect as 
    // expression.
    def ArrayList<Assignment> createSCLAssignment(Effect effect) {
        val assignments = new ArrayList<Assignment>
        if (effect instanceof ReactionEffect) {
            for(action : (effect as ReactionEffect).actions) {
//                 action.eAllContents.filter(typeof(AssignmentExpression)).forEach [
//                    val assignment = SCL.createAssignment();
//                    assignment.assignment = it.copy
//                    assignment.eAllContents.filter(typeof(AssignmentExpression)).forEach [
//                        val varRef = (it.varRef as ElementReferenceExpression);
//                        varRef.reference = (varRef.reference as Event).createVariableDeclaration();
//                    ]
//                    assignments.add(assignment)
//                ]
                    val assignment = SCL.createAssignment()
                    if (action instanceof ElementReferenceExpression) {
                        assignment.assignment = SText.createAssignmentExpression
                        val asgn = assignment.assignment as AssignmentExpression
                        asgn.varRef = action.copy as ElementReferenceExpression
                        asgn.setOperator(AssignmentOperator::ASSIGN)
                        asgn.setExpression(assignBoolean(true))    
                    } else {
                        assignment.assignment = action.copy as AssignmentExpression
                    }
                    assignment.eAllContents.filter(typeof(ElementReferenceExpression)).forEach [
                        it.reference = (it.reference as Declaration).createVariableDefinition();
                    ]
//                    ((assignment.assignment as AssignmentExpression).varRef as ElementReferenceExpression).reference = 
//                        (((action as AssignmentExpression).varRef as ElementReferenceExpression).reference as Event).createVariableDeclaration();
                    assignments.add(assignment)
            }
        }
        
        assignments;    
    }
    
    def Assignment createSCLAssignment(Expression expression, VariableDefinition declRef) {
        val assignment = SCL.createAssignment()
        assignment.assignment = expression
        assignment.eAllContents.filter(typeof(AssignmentExpression)).forEach [
            val varRef = (it.varRef as ElementReferenceExpression);
            varRef.reference = declRef
        ]
        assignment
    }
    
    def Assignment createSCLAssignment(VariableDefinition firstDecl, VariableDefinition secondDecl) {
        val assignment = SCL.createAssignment()
        
        val elemRef1 = SText.createElementReferenceExpression()
        elemRef1.setReference(firstDecl)
        
        val elemRef2 = SText.createElementReferenceExpression()
        elemRef2.setReference(secondDecl);
        
        val assignmentExp = SText.createAssignmentExpression()
        assignmentExp.setVarRef(elemRef1)
        assignmentExp.setOperator(AssignmentOperator::ASSIGN)
        assignmentExp.setExpression(elemRef2)
        
        assignment.assignment = assignmentExp
        
        assignment
    }
    
    // Create a new scl conditional statement
    def Conditional createSCLConditional() {
        SCL.createConditional()
    }
    
    // Create a new scl conditional and take the given stext trigger as true trigger for the conditional. 
    def Conditional createSCLConditional(Trigger trigger) {
        var conditional = SCL.createConditional()
        if (trigger instanceof ReactionTrigger) {
            val reactionTrigger = trigger as ReactionTrigger;
//            if (reactionTrigger.guardExpression != null) {
//                conditional.expression = reactionTrigger.guardExpression.copy;
//                conditional.eAllContents.filter(typeof(ElementReferenceExpression)).forEach [ e |
//                    e.reference = (e.reference as Event).createVariableDeclaration;  
//                ]
//            } else 
            if (reactionTrigger.expression != null) {
                conditional.expression = reactionTrigger.expression.copy;
                conditional.eAllContents.filter(typeof(ElementReferenceExpression)).forEach [ e |
                    e.reference = (e.reference as Declaration).createVariableDefinition;  ]
            }
        }
        
        conditional
    }    
    
    // Create a new SCL thread
    def Thread createSCLThread() {
        SCL.createThread()
    }
    
    // Create a new SCL thread and copy the given instruction list
    def Thread createSCLThread(List<Statement> statements) {
        val thread = createSCLThread()
        thread.statements.addAll(statements)
        thread
    }
    
}