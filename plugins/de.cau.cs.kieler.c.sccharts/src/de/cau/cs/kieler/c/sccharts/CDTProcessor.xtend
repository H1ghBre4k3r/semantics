/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 * 
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2015 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.c.sccharts

import com.google.inject.Inject
import de.cau.cs.kieler.core.kexpressions.Declaration
import de.cau.cs.kieler.core.kexpressions.Expression
import de.cau.cs.kieler.core.kexpressions.KExpressionsFactory
import de.cau.cs.kieler.core.kexpressions.OperatorExpression
import de.cau.cs.kieler.core.kexpressions.OperatorType
import de.cau.cs.kieler.core.kexpressions.ValueType
import de.cau.cs.kieler.core.kexpressions.ValuedObject
import de.cau.cs.kieler.core.kexpressions.ValuedObjectReference
import de.cau.cs.kieler.core.kexpressions.extensions.KExpressionsCreateExtensions
import de.cau.cs.kieler.core.kexpressions.extensions.KExpressionsDeclarationExtensions
import de.cau.cs.kieler.core.kexpressions.extensions.KExpressionsValuedObjectExtensions
import de.cau.cs.kieler.core.kexpressions.keffects.Assignment
import de.cau.cs.kieler.core.kexpressions.keffects.KEffectsFactory
import de.cau.cs.kieler.sccharts.ControlflowRegion
import de.cau.cs.kieler.sccharts.DataflowRegion
import de.cau.cs.kieler.sccharts.SCChartsFactory
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.TransitionType
import de.cau.cs.kieler.sccharts.extensions.SCChartsExtension
import java.util.HashMap
import java.util.Map
import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression
import org.eclipse.cdt.core.dom.ast.IASTExpression
import org.eclipse.cdt.core.dom.ast.IASTNode
import org.eclipse.cdt.core.dom.ast.IASTStatement
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression
import org.eclipse.cdt.core.model.ITranslationUnit
import org.eclipse.cdt.internal.core.dom.parser.c.CASTBinaryExpression
import org.eclipse.cdt.internal.core.dom.parser.c.CASTBreakStatement
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCaseStatement
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompoundStatement
import org.eclipse.cdt.internal.core.dom.parser.c.CASTDeclarationStatement
import org.eclipse.cdt.internal.core.dom.parser.c.CASTDeclarator
import org.eclipse.cdt.internal.core.dom.parser.c.CASTDefaultStatement
import org.eclipse.cdt.internal.core.dom.parser.c.CASTEqualsInitializer
import org.eclipse.cdt.internal.core.dom.parser.c.CASTExpressionStatement
import org.eclipse.cdt.internal.core.dom.parser.c.CASTForStatement
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionCallExpression
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDeclarator
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDefinition
import org.eclipse.cdt.internal.core.dom.parser.c.CASTIdExpression
import org.eclipse.cdt.internal.core.dom.parser.c.CASTIfStatement
import org.eclipse.cdt.internal.core.dom.parser.c.CASTParameterDeclaration
import org.eclipse.cdt.internal.core.dom.parser.c.CASTProblem
import org.eclipse.cdt.internal.core.dom.parser.c.CASTReturnStatement
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclSpecifier
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSwitchStatement
import org.eclipse.cdt.internal.core.dom.parser.c.CASTUnaryExpression
import org.eclipse.cdt.internal.core.dom.parser.c.CASTWhileStatement
import org.eclipse.cdt.internal.ui.editor.CEditor
import org.eclipse.emf.ecore.EObject
import org.eclipse.ui.IEditorPart
import de.cau.cs.kieler.sccharts.EntryAction
import org.eclipse.cdt.core.model.ITranslationUnit
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import java.util.ArrayList
import java.util.List
import de.cau.cs.kieler.core.kexpressions.TextExpression
import de.cau.cs.kieler.core.annotations.extensions.AnnotationsExtensions

/**
 * @author ssm
 * 
 */
 
class CDTProcessor {

    @Inject
    extension KExpressionsCreateExtensions

    @Inject
    extension KExpressionsDeclarationExtensions

    @Inject
    extension KExpressionsValuedObjectExtensions

    @Inject
    extension SCChartsExtension

    @Inject
    extension AnnotationsExtensions
    
    public static val RETURNVONAME = "return"

    val VOSet = <ValuedObject>newHashSet
    var _trc = 0

    var Declaration globalDeclaration

    var State funcGlobalState

    Map<String, State> functionStates = new HashMap<String, State>();
    List<State> connectorStates = new ArrayList<State>();
    String parameterStr

    def EObject createFromEditor(IEditorPart editor) {
        val cEditor = editor as CEditor
        val tu = cEditor.translationUnit
        tu.transform as State
    }

    def EObject transform(ITranslationUnit translationUnit) {
        if (translationUnit == null) {
            return null
        }
        val ast = translationUnit.getAST
        if (ast.children != null) {
            ast.children.printASTNodes("")
        } else {
            return null
        }

        VOSet.clear

        // Root State (program)
        val surroundmodel = scc.createState => [ root |
            root.label = translationUnit.file.name
            root.id = root.label
            ast.children.forEach [ func | // for each function set a new region 
                if (func instanceof CASTFunctionDefinition) {
                    val rootFunctionDefinition = func as CASTFunctionDefinition

                    val model = rootFunctionDefinition.transformFunction

                    scc.createControlflowRegion => [ region |
                        region.id = "_main"
                        region.label = ""
                        root.regions += region;
                        region.states += model
                    ]
                }
            ]
        ]
        removeConnectorStates();
        surroundmodel.createStringAnnotation("cgeneratedscchart","")
        surroundmodel
    }

    def State transformFunction(CASTFunctionDefinition function) {

        // org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDefinition@6c704a9e
        // org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclSpecifier@403302b0
        // org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDeclarator@54f591bd
        // fib
        val returnDeclaration = kex.createDeclaration => [
            type = parseCDTType(function.children.filter(typeof(CASTSimpleDeclSpecifier)).head.type)
            output = true
        ]

        val returnValue = kex.createValuedObject => [
            name = "return"
            returnDeclaration.valuedObjects += it
            VOSet += it
        ]

        // create the scchart model for the function 
        val declarator = function.children.filter(typeof(CASTFunctionDeclarator)).head
        // Function State 
        val model = scc.createState => [ root |
            root.label = declarator.children.head.toString
            root.id = root.label

            root.declarations += returnDeclaration
            scc.createControlflowRegion => [
                id = "_main"
                label = id
                root.regions += it
            ]
        ]

        // parameters
        funcGlobalState = model

        // createDeclaration
        // org.eclipse.cdt.internal.core.dom.parser.c.CASTParameterDeclaration@46333ade
        // org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclSpecifier@4d5de8d3
        // org.eclipse.cdt.internal.core.dom.parser.c.CASTDeclarator@50609c53
        // n        
        declarator.children.filter(typeof(CASTParameterDeclaration)).forEach [ parameter |
            val iName = parameter.children.filter(typeof(CASTDeclarator)).head.children.head.toString
            val intParamater = kex.createDeclaration => [
                type = parseCDTType(parameter.children.filter(typeof(CASTSimpleDeclSpecifier)).head.type)
                input = true
                model.declarations += it

            ]
            kex.createValuedObject => [
                name = iName
                intParamater.valuedObjects += it
                VOSet += it
            ]
        ]

        val compound = function.children.filter(typeof(CASTCompoundStatement)).head
        if (compound != null) {
            compound.transformCompound(null, model)
        }

        model
    }

    def State transformCompound(CASTCompoundStatement cs, State state, State parentState) {
        var State newState
        if (state == null) {
            newState = scc.createState => [ s |
                s.id = "_S" + trC
                s.label = s.id
                s.initial = true

                parentState.regions.filter(typeof(ControlflowRegion)).head.states += s
            ]
        } else {
            newState = state
        }
        val stateF = newState

        // org.eclipse.cdt.internal.core.dom.parser.c.CASTCompoundStatement@76b7365e
        // org.eclipse.cdt.internal.core.dom.parser.c.CASTDeclarationStatement@76529235
        // org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration@26fd1243
        // org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclSpecifier@30ba00b4
        // org.eclipse.cdt.internal.core.dom.parser.c.CASTDeclarator@384426a
        // fl
        // org.eclipse.cdt.internal.core.dom.parser.c.CASTEqualsInitializer@60243ef3
        // 0        
        var actualState = stateF
        for (statement : cs.children.filter(IASTStatement)) {
            actualState = (statement as IASTStatement).transformStatement(actualState)
        }
        actualState
    }

    def State transformStatement(IASTStatement statement, State parent) {

        var actualState = parent;
        if (statement instanceof CASTIfStatement) {
            actualState = statement.transformIf(actualState)
        }
        if (statement instanceof CASTReturnStatement) {
            actualState = statement.transformReturn(actualState)
        }
        if (statement instanceof CASTExpressionStatement) {
            actualState = statement.transformExpression(actualState)
        }
        if (statement instanceof CASTForStatement) {
            actualState = statement.transformFor(actualState)
        }
        if (statement instanceof CASTWhileStatement) {
            actualState = statement.transformWhile(actualState)
        }
        if (statement instanceof CASTSwitchStatement) {
            actualState = statement.transformSwitch(actualState)
        }
        if (statement instanceof CASTBinaryExpression) {
            actualState = statement.createDataflow(actualState)
        }
        if (statement instanceof CASTDeclarationStatement) {
            // parentState.regions.filter(typeof(ControlflowRegion)).head.states += actualState
            actualState = statement.castdeclaration(actualState)
        }

        actualState
    }

    def State transformSwitch(CASTSwitchStatement statement, State state) {
        val f = statement

        val connectorState = scc.createState => [ s |
            s.id = trC.toString
            s.label = s.id
            s.setTypeConnector
            s.regions += scc.createControlflowRegion => [
                id = s.id + "_r"
                label = ""
            ]
            state.parentRegion.states += s
        ]
        connectorStates.add(connectorState)
        val switchVar = statement.children.filter(typeof(CASTIdExpression)).head.syntax.toString
        var lastState = state;
        var Expression kExp = null;
        var parent = state;
        var Boolean defaultExpression = false;
        var Boolean casebefore=false;
        for (caseExpression : f.children.filter(typeof(CASTCompoundStatement)).head.children.filter(
            typeof(IASTStatement))) {

            if (caseExpression instanceof CASTCaseStatement) {
                // caseExpression.transformStatement(state)
                if (!casebefore){//Break
                    kExp = (switchVar + " == " + caseExpression.children.filter(typeof(IASTExpression)).head.toString).
                        createTextExpression
                    if (lastState != parent && !parent.final) {
                        val backConnector = scc.createTransition => [
                            targetState = connectorState
                            immediate = true
                        ]
                        parent.outgoingTransitions += backConnector
                    }
                    parent = lastState
                    casebefore=true;
                
                }else{//no break
                    kExp = (switchVar + " == " + caseExpression.children.filter(typeof(IASTExpression)).head.toString).
                        createTextExpression
                    if (lastState != parent && !parent.final) {
                        val notBreakState = scc.createState => [ s |
                            s.id = trC.toString
                            s.label = createLabel(statement)
                            
                            state.parentRegion.states += s
                        ]
                        
                        
                        val backConnector = scc.createTransition => [
                            targetState = notBreakState
                            immediate = true
                        ]
                         val connector = scc.createTransition => [
                            targetState = notBreakState
                            immediate = true
                        ]
                        lastState.outgoingTransitions+=connector
                        parent.outgoingTransitions += backConnector
                    lastState=notBreakState;
                    parent = notBreakState
                    casebefore=true;
                    
                    }
                }
            } else if (caseExpression instanceof CASTBreakStatement) {
                casebefore=false;
                
                //parent=lastState
            } else if (caseExpression instanceof CASTDefaultStatement) {
                if (parent != lastState && !parent.final) {
                    val backConnector = scc.createTransition => [
                        targetState = connectorState
                        immediate = true
                    ]
                    parent.outgoingTransitions += backConnector
                }
                parent = lastState;
                defaultExpression = true;

            } else {

                parent = caseExpression.transformStatement(parent);
                if (!defaultExpression) {
                    lastState.outgoingTransitions.last.trigger = kExp
                }

                // lastTransition.targetState=trueState
                
            }

        // caseExpression.children.filter(typeof(IASTStatement)).head.transformStatement(state)
        }
        if (!parent.final) {
            var backConnector = scc.createTransition => [
                targetState = connectorState
                immediate = true
            ]
            parent.outgoingTransitions += backConnector
        }

        if (!defaultExpression) {
            var backConnector = scc.createTransition => [
                targetState = connectorState
                immediate = true
            ]
            state.outgoingTransitions += backConnector
        }

        connectorState
    }

    def State transformWhile(CASTWhileStatement statement, State state) {
        val f = statement

        // condition
        val exp = f.condition

        val kExp = exp.createKExpression

        val condState = scc.createState => [ s |
            s.id = trC.toString
            s.label = createLabel(statement)
            // s.setTypeConnector
//            s.regions += scc.createControlflowRegion => [
//                id = s.id + "_r"
//                label = ""
//            ]
            state.parentRegion.states += s
        ]

        // Connects if to the parent
        val ifConnector = scc.createTransition => [
            targetState = condState
            immediate = true
            state.outgoingTransitions += it
            type == TransitionType::TERMINATION
        ]

        val trueState = scc.createState => [ s |
            s.id = trC + "T"
            s.label = createLabel(statement)
            state.parentRegion.states += s
        ]

        val trueTrans = scc.createTransition => [
            targetState = trueState
            immediate = true
            condState.outgoingTransitions += it
            trigger = kExp
        ]

        val body = f.body as CASTCompoundStatement

        val bodyState = body.transformCompound(trueState, trueState)

        val backTransition = scc.createTransition => [
            targetState = condState
            immediate = true
            bodyState.outgoingTransitions += it
        ]

        condState

    }

    def State castdeclaration(CASTDeclarationStatement statement, State parent) {

        val simpleDecl = statement.children.filter(typeof(CASTSimpleDeclaration)).head
        val decl = simpleDecl.children.filter(typeof(CASTDeclarator)).head
        if (decl != null) {

            val iName = decl.children.head.toString
            val intParamater = kex.createDeclaration => [
                type = parseCDTType(simpleDecl.children.filter(typeof(CASTSimpleDeclSpecifier)).head.type)
                input = false
                funcGlobalState.declarations += it
            ]

            val value = kex.createValuedObject => [
                name = iName
                intParamater.valuedObjects += it
                VOSet += it
            ]

            val initializer = decl.children.filter(typeof(CASTEqualsInitializer)).head
            if (initializer != null) {
                val initVarState = scc.createState => [ s |
                    s.id = parent.id + "T"
                    s.label = createLabel(statement)

                    parent.parentRegion.states += s
                ]
                val trans = scc.createTransition => [
                    targetState = initVarState
                    immediate = true
                    parent.outgoingTransitions += it

                ]

                if (initializer.children.head instanceof IASTExpression) {
                    var exp = (initializer.children.head as IASTExpression)
                    var entryAction = initVarState.createEntryAction
//                    var textExpression=kex.createTextExpression
//                    textExpression.text = exp
                    entryAction.createAssignment(value, exp.createKExpression)

                    return initVarState

                // if (initializer.children.head instanceof CASTBinaryExpression){
                // System.out.println("text")
                // return (initializer.children.head as IASTStatement).transformStatement(parent)
                // }
                }
            } else {
                return parent
            }

        // Neuer State wenn nicht Null sonst den alten zurück geben. 
//            value.initialValue = initializer.casteEqualsInitializer(
//                parseCDTType(simpleDecl.children.filter(typeof(CASTSimpleDeclSpecifier)).head.type))
        }
        parent
    }

    def Expression casteEqualsInitializer(CASTEqualsInitializer initializer, ValueType type) {
        if (initializer != null) {
//            if (type == ValueType::INT) {
//                val intval = kex.createIntValue
//                intval.value = Integer.parseInt(initializer.children.head.toString)
//                intval
//            } else {
//                System.out.println("test")
//                val intval = null
//                intval
//            }
            return (initializer.children.head as IASTExpression).createKExpression
        } else {
            null
        }

    }

    def State transformIf(CASTIfStatement ifStatement, State state) {
        val ifs = ifStatement
        val exp = ifs.children.filter(typeof(CASTBinaryExpression)).head

        val kExp = exp.createKExpression

        val trueState = scc.createState => [ s |
            s.id = "T"
            s.label = s.id

            state.parentRegion.states += s
        ]

        val connectorState = scc.createState => [ s |
            s.id = trC.toString
            s.label = s.id
            s.setTypeConnector

            state.parentRegion.states += s
        ]
        connectorStates += connectorState

        val trueTrans = scc.createTransition => [
            targetState = trueState
            immediate = true
            state.outgoingTransitions += it
            trigger = kExp
        ]

        

        val trueBody = (ifs.thenClause as CASTCompoundStatement).transformCompound(trueState, trueState)
        if (!trueBody.final) {
            val trueConnector = scc.createTransition => [
                targetState = connectorState
                immediate = true
                trueBody.outgoingTransitions += it
                type == TransitionType::TERMINATION
            ]
        }

        if (ifs.elseClause != null) {
            var State falseBody = null;
            if (ifs.elseClause instanceof CASTCompoundStatement) {
                val falseState = scc.createState => [ s |
                    s.id = "F"
                    s.label = s.id

                    state.parentRegion.states += s
                ]
                val falseTrans = scc.createTransition => [
                    targetState = falseState
                    immediate = true
                    state.outgoingTransitions += it
                ]
                falseBody = (ifs.elseClause as CASTCompoundStatement).transformCompound(falseState, state)
            } else if (ifs.elseClause instanceof CASTIfStatement) {
                falseBody = (ifs.elseClause as CASTIfStatement).transformIf(state)
            }

            if (!falseBody.final) {
                val falseConnector = scc.createTransition => [
                    targetState = connectorState
                    immediate = true

                    type == TransitionType::TERMINATION
                ]
                falseBody.outgoingTransitions += falseConnector
            }
        } else {
//            val falseState = scc.createState => [ s |
//                    s.id = "F"
//                    s.label = s.id
//
//                    state.parentRegion.states += s
//                ]
//                val falseTrans = scc.createTransition => [
//                    targetState = falseState
//                    immediate = true
//                    state.outgoingTransitions += it
//                ]
//            val falseConnector = scc.createTransition => [
//                targetState = connectorState
//                immediate = true
//                falseState.outgoingTransitions += it
//                type == TransitionType::TERMINATION
//            ]
        }
        connectorState
    }

    def State transformFor(CASTForStatement forStatement, State state) {
        val f = forStatement
        var actualState = state;
        // initializer
        if (f.initializerStatement instanceof CASTDeclarationStatement) {
            val initializationExp = f.initializerStatement as CASTDeclarationStatement
            actualState = initializationExp.castdeclaration(actualState) // add declaration to function head
        } else {
            actualState = (f.initializerStatement as CASTExpressionStatement).transformExpression(actualState)
        }

        // condition
        val exp = f.conditionExpression

        val kExp = exp.createKExpression

        val ifState = scc.createState => [ s |
            s.id = trC.toString
            s.label = s.id
            s.setTypeConnector

            state.parentRegion.states += s
        ]

        // Connects if to the parent
        val ifConnector = scc.createTransition => [
            targetState = ifState
            immediate = true

            type == TransitionType::TERMINATION
        ]
        actualState.outgoingTransitions += ifConnector

        val trueState = scc.createState => [ s |
            s.id = trC + "T"
            s.label = s.id

            state.parentRegion.states += s
        ]

        val trueTrans = scc.createTransition => [
            targetState = trueState
            immediate = true
            ifState.outgoingTransitions += it
            trigger = kExp
        ]

        val iterateState = scc.createState => [ s |
            s.id = trC + "T"
            s.label = createLabel(forStatement)

            state.parentRegion.states += s
        ]

        // iterateaction
        val body = f.body as CASTCompoundStatement

        val bodyState = body.transformCompound(trueState, trueState)
        val iterateExp = (f.iterationExpression as CASTBinaryExpression).createDataflow(iterateState)
        val iterateTrans = scc.createTransition => [
            targetState = iterateState
            immediate = true
            bodyState.outgoingTransitions += it
        ]

        val backTransition = scc.createTransition => [
            targetState = ifState
            immediate = true
            iterateExp.outgoingTransitions += it
        ]

        ifState
    }

    def ValuedObject createValuedObjectFromDeclarationStatement(CASTDeclarationStatement declaration,
        boolean withInitializationPart) {
        val simpleDecl = declaration.children.filter(typeof(CASTSimpleDeclaration)).head
        val decl = simpleDecl.children.filter(typeof(CASTDeclarator)).head

        val VO = decl.createValuedObjectFromDeclarator

        if (withInitializationPart) {
            val init = decl.children.filter(typeof(CASTEqualsInitializer))
            if (!init.empty) {
                VO.initialValue = createIntValue(Integer.parseInt(init.head.children.head.toString))
            }
        }

        VO
    }

    def ValuedObject createValuedObjectFromDeclarator(CASTDeclarator declarator) {

        // org.eclipse.cdt.internal.core.dom.parser.c.CASTDeclarator@50429fba
        // tmp
        // org.eclipse.cdt.internal.core.dom.parser.c.CASTEqualsInitializer@46049580
        // org.eclipse.cdt.internal.core.dom.parser.c.CASTIdExpression@56f32a69
        // fh        
        val iName = declarator.children.head.toString
        val lVar = kex.createValuedObject => [
            name = iName
        ]

        lVar
    }

    def State transformReturn(CASTReturnStatement returnStatement, State state) {

        val returnState = scc.createState => [ s |
            s.id = trC.toString
            s.label = createLabel(returnStatement)
            s.final = true
            state.parentRegion.states += s
        // s.concurrencies += scc.createRegion => [
        // id = s.id + "_r"
        // label = ""
        // ]            
        // s.regions += scc.createDataflowRegion => [
        // id = s.id + "_d"
        // label = ""
        // ]
        ]

        state.outgoingTransitions += scc.createTransition => [
            targetState = returnState
            immediate = true
        ]

        val entryact = returnState.createEntryAction
        entryact.createAssignment(VOSet.filter[name.equals(RETURNVONAME)].head,
            returnStatement.returnValue.createKExpression)

        returnState
    }

    def State transformExpression(CASTExpressionStatement es, State state) {

        // org.eclipse.cdt.internal.core.dom.parser.c.CASTCompoundStatement@7f6adc09
        // org.eclipse.cdt.internal.core.dom.parser.c.CASTExpressionStatement@40941439
        // org.eclipse.cdt.internal.core.dom.parser.c.CASTBinaryExpression@39bbcb4e
        // org.eclipse.cdt.internal.core.dom.parser.c.CASTIdExpression@58208b7e
        // fh
        // org.eclipse.cdt.internal.core.dom.parser.c.CASTIdExpression@40a62a65
        // n
        if (es.expression instanceof CASTBinaryExpression) {

            val exp = es.expression as CASTBinaryExpression
            val codeState = scc.createState => [ s |
                s.id = trC + "T"
                s.label = createLabel(es)

                state.parentRegion.states += s
                val entryact = s.createEntryAction
                entryact.createAssignment((exp.operand1 as CASTIdExpression).createVOReference.valuedObject,
                    exp.operand2.createKExpression)
            ]

            val trans = scc.createTransition => [
                targetState = codeState
                immediate = true

            ]
            state.outgoingTransitions += trans

            return codeState

        }

        if (es.expression instanceof CASTFunctionCallExpression) {

            //
            val sa = scc.createState => [ s |
                s.id = trC + "T"
                s.label = createLabel(es)
                state.parentRegion.states += s
            ]

            if (functionStates.get("wasauchimmer") != null) {

                sa.referencedScope = functionStates.get("wasauchimmer")
                val bind = BindingImpl.newInstance

//                bind.actual = funcGlobalState.declarations.head
//                bind.formal = functionStates.get("wasauchimmer").valuedObjects.head
                sa.bindings += bind

                state.parentRegion.states += sa
            } else {
                val cal = (es.expression as CASTFunctionCallExpression).createFunctionCallValuedObject();

                val entryact = sa.createEntryAction
                entryact.createEmission(cal)

            }

            val trans = scc.createTransition => [
                targetState = sa
                immediate = true
            // codeState.outgoingTransitions += it
            ]
            state.outgoingTransitions += trans

            return sa

        }

        null
    }

    def ValuedObject createFunctionCallValuedObject(CASTFunctionCallExpression expression) {
        val cal = kex.createValuedObject;
        cal.name = expression.children.filter(typeof(CASTIdExpression)).head.name.toString

        cal.name = cal.name + "("

        for (parameter : expression.arguments) {
            if (parameter instanceof IASTExpression) {
                cal.name = cal.name + (parameter as IASTExpression).rawSignature + ","
            } else {
                cal.name = cal.name + parameter.toString + ","
            }

        }
        if (!expression.arguments.empty) {
            cal.name = cal.name.substring(0, cal.name.length - 1) + ")"
        } else {
            cal.name = cal.name + ")"
        }

        cal.id("test")
        return cal
    }

    def State createDataflow(CASTBinaryExpression exp, State state) {

        // org.eclipse.cdt.internal.core.dom.parser.c.CASTBinaryExpression@7940791
        // org.eclipse.cdt.internal.core.dom.parser.c.CASTIdExpression@77cc47a
        // n
        // 1
        val entryact = state.createEntryAction
        entryact.createAssignment((exp.operand1 as CASTIdExpression).createVOReference.valuedObject,
            exp.operand2.createKExpression)
        state
    }

    def createDataflow(CASTDeclarator declarator, State state) {

        // org.eclipse.cdt.internal.core.dom.parser.c.CASTDeclarator@50429fba
        // tmp
        // org.eclipse.cdt.internal.core.dom.parser.c.CASTEqualsInitializer@46049580
        // org.eclipse.cdt.internal.core.dom.parser.c.CASTIdExpression@56f32a69
        // fh        createDataflow
        val iName = declarator.children.head.toString
        val lVar = kex.createValuedObject => [
            name = iName
            VOSet += it
        ]

        // funcGlobalState.valuedObjects += lVar
        val init = declarator.initializer
        if (init != null) {
            val value = init.children.head
            var Expression kExp
            if (value instanceof CASTIdExpression) {
                kExp = (value as CASTIdExpression).createKExpression
            } else {
                kExp = createIntValue(Integer.parseInt(value.toString))
            }
            val df = state.dataflowRegion

            val eq = scc.createEquation
            eq.valuedObject = lVar
            eq.expression = kExp

            df.equations += eq
        }
    }

    def Expression createKExpression(IASTExpression exp) {
        if (exp instanceof CASTIdExpression) {
            return (exp as CASTIdExpression).createVOReference
        } else if (exp instanceof CASTFunctionCallExpression) {
            return (exp as CASTFunctionCallExpression).transformFunctionCall
        } else if (exp instanceof CASTBinaryExpression) {
            return (exp as CASTBinaryExpression).createKExpression
        } else if ((exp instanceof CASTUnaryExpression)) {
            (exp as CASTUnaryExpression).createUnaryExpression
        } else {
            return exp.toString.createStringValue;
        // return Integer.parseInt(exp.toString).createIntValue
        }
    }

    def Expression createUnaryExpression(CASTUnaryExpression expression) {
        (expression.children.head as IASTExpression).createKExpression
    }

    def Expression transformFunctionCall(CASTFunctionCallExpression expression) {

        val opExp = kex.createOperatorExpression

        expression.createFunctionCallValuedObject

        // opExp.addValuedObject(expression.createFunctionCallValuedObject) //= expression.functionNameExpression.createKExpression
        val exp = kex.createTextExpression
        exp.text = expression.createFunctionCallValuedObject.name

        // exp.addValuedObject(expression.createFunctionCallValuedObject)
        //
        // for (IASTExpression child : expression.arguments.filter(IASTExpression)) {
        // opExp.subExpressions += createKExpression(child)
        // }
        return exp // opExp
    }

    def Expression createKExpression(CASTBinaryExpression exp) {

        // org.eclipse.cdt.internal.core.dom.parser.c.CASTBinaryExpression@7940791
        // org.eclipse.cdt.internal.core.dom.parser.c.CASTIdExpression@77cc47a
        // n
        // 1
        val opExp = kex.createOperatorExpression
        switch exp.operator {
            case IASTBinaryExpression::op_lessEqual:
                opExp.operator = OperatorType::LEQ
            case IASTBinaryExpression::op_greaterEqual:
                opExp.operator = OperatorType::GEQ
            case IASTBinaryExpression::op_lessThan:
                opExp.operator = OperatorType::LT
            case IASTBinaryExpression::op_greaterThan:
                opExp.operator = OperatorType::GT
            case IASTBinaryExpression::op_plus:
                opExp.operator = OperatorType::ADD
            case IASTBinaryExpression::op_binaryAnd:
                opExp.operator = OperatorType::BITWISE_AND
            case IASTBinaryExpression::op_binaryOr:
                opExp.operator = OperatorType::BITWISE_OR
            // case IASTBinaryExpression::op_binaryXor:     opExp.operator = OperatorType::XOR
            case IASTBinaryExpression::op_divide:
                opExp.operator = OperatorType::DIV
            case IASTBinaryExpression::op_minus:
                opExp.operator = OperatorType::SUB
            case IASTBinaryExpression::op_modulo:
                opExp.operator = OperatorType::MOD
            case IASTBinaryExpression::op_multiply:
                opExp.operator = OperatorType::MULT
        }

        for (arg : exp.children) {
            if (arg instanceof CASTIdExpression) {
                opExp.subExpressions += (arg as CASTIdExpression).createVOReference
            } else if (arg instanceof CASTFunctionCallExpression) {

                opExp.subExpressions += (arg as CASTFunctionCallExpression).createKExpression
            } else if ((arg instanceof CASTBinaryExpression)) {
                opExp.subExpressions += kex.createOperatorExpression
            } else if ((arg instanceof CASTUnaryExpression)) {
                opExp.subExpressions += arg.createKExpression;
            } else {
                opExp.subExpressions += Integer.parseInt(arg.toString).createIntValue
            }
        }

        opExp
    }

    def Assignment createAssignment(CASTUnaryExpression unary) {
        val assignment = KEffectsFactory.eINSTANCE.createAssignment

        val VOR = (unary.operand as CASTIdExpression).createVOReference
        assignment.valuedObject = VOR.valuedObject
        assignment.expression = kex.createOperatorExpression => [
            subExpressions += VOR
            subExpressions += 1.createIntValue
        ]
        if (unary.operator == IASTUnaryExpression::op_postFixIncr)
            (assignment.expression as OperatorExpression).operator = OperatorType::ADD
        else if (unary.operator == IASTUnaryExpression::op_postFixDecr)
            (assignment.expression as OperatorExpression).operator = OperatorType::SUB

        assignment
    }

    def ValuedObjectReference createVOReference(CASTIdExpression idExp) {
        val VO = VOSet.filter[name.equals(idExp.children.head.toString)].head
        if (VO != null) {
            return VO.reference
        }
        return null
    }

    def printASTNodes(Iterable<IASTNode> nodes, String indent) {
        if(nodes == null) return;
        for (node : nodes) {
            System.out.println(indent + node.toString)

            if (node instanceof CASTProblem) {
                val prob = node as CASTProblem
                System.out.println(indent + " == " + prob.message + " ==");
                for (arg : prob.arguments) {
                    System.out.println(indent + "     " + prob.message + " ");
                }
            }

            node.children.printASTNodes(indent + " ")
        }
    }

    private def scc() {
        SCChartsFactory::eINSTANCE
    }

    private def kex() {
        KExpressionsFactory::eINSTANCE
    }

    private def ControlflowRegion getControlflowRegion(State state) {
        state.regions.filter(typeof(ControlflowRegion)).head
    }

    private def DataflowRegion getDataflowRegion(State state) {
        state.regions.filter(typeof(DataflowRegion)).head
    }

    private def State getParent(State state) {
        state.parentRegion.parentState
    }

    private def int trC() {
        _trc = _trc + 1
        _trc
    }

    private def ValueType parseCDTType(int n) {
        switch n {
            case 1: null
            case 2: ValueType::STRING
            case 3: ValueType::INT
            case 4: ValueType::FLOAT
            case 5: ValueType::DOUBLE
            case 6: ValueType::BOOL
            case 7: ValueType::STRING
            case 8: null
            case 9: null
            case 10: null
            case 11: ValueType::STRING
            case 12: ValueType::STRING
            default: null
        }
    }

    def EntryAction createEntryAction(State state) {
        val action = SCChartsFactory::eINSTANCE.createEntryAction
        state.localActions.add(action);

        action
    }
    
    private def String createLabel(IASTNode node) {
        var option = getLabelOption
        switch (option) {
            case 1: return createLineLabel(node)
            case 2: return ""
            default: return createLineLabel(node)
        }
    }

    private def String createLineLabel(IASTNode node) {
        return "line: " + (node as IASTStatement).nodeLocations.head.asFileLocation.startingLineNumber;
    }

    private def int getLabelOption() {
        return 1;
    }

    private def int getMultiEntryOption() {
        return 0;
    // return 1;
    }

    private def void removeConnectorStates() {
        for (var int i = 0; i < connectorStates.length(); i++) {

            if (connectorStates.get(i).outgoingTransitions.empty) {
                connectorStates.get(i).parentRegion.states.remove(connectorStates.get(i))
            // connectorStates.remove(i);
            }else if (connectorStates.get(i).incomingTransitions.empty){
                connectorStates.get(i).parentRegion.states.remove(connectorStates.get(i))
            }
        // state.outgoingTransitions.head.targetState=state
        // state.incomingTransitions+=state.outgoingTransitions.head
        // state.parentRegion.states.remove(state);
//            for(var int i=0; i<state.incomingTransitions.length(); i++) {
//                
//                state.incomingTransitions.get(i).targetState = targetstate
//                
//            }
//            for (transi:state.incomingTransitions){
//                transi.targetState = targetstate
//                //System.out.println(transi);
//            }
        //
        }
    }
//    private createExpression    
}
