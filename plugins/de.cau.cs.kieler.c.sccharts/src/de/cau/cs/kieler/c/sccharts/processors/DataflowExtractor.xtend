/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 * 
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2019,2021 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.c.sccharts.processors

import com.google.inject.Inject
import de.cau.cs.kieler.annotations.TagAnnotation
import de.cau.cs.kieler.annotations.extensions.AnnotationsExtensions
import de.cau.cs.kieler.annotations.extensions.PragmaExtensions
import de.cau.cs.kieler.c.sccharts.extensions.CDTConvertExtensions
import de.cau.cs.kieler.c.sccharts.extensions.HighlightingExtensions
import de.cau.cs.kieler.c.sccharts.extensions.ValueExtensions
import de.cau.cs.kieler.c.sccharts.util.CProcessorUtils
import de.cau.cs.kieler.kexpressions.BoolValue
import de.cau.cs.kieler.kexpressions.Expression
import de.cau.cs.kieler.kexpressions.FloatValue
import de.cau.cs.kieler.kexpressions.IntValue
import de.cau.cs.kieler.kexpressions.OperatorExpression
import de.cau.cs.kieler.kexpressions.OperatorType
import de.cau.cs.kieler.kexpressions.ReferenceDeclaration
import de.cau.cs.kieler.kexpressions.StringValue
import de.cau.cs.kieler.kexpressions.ValueType
import de.cau.cs.kieler.kexpressions.ValuedObject
import de.cau.cs.kieler.kexpressions.ValuedObjectReference
import de.cau.cs.kieler.kexpressions.VariableDeclaration
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsCreateExtensions
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsDeclarationExtensions
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsValuedObjectExtensions
import de.cau.cs.kieler.kexpressions.keffects.Assignment
import de.cau.cs.kieler.kicool.compilation.CodeContainer
import de.cau.cs.kieler.kicool.compilation.ExogenousProcessor
import de.cau.cs.kieler.kicool.deploy.ProjectInfrastructure
import de.cau.cs.kieler.sccharts.ControlflowRegion
import de.cau.cs.kieler.sccharts.DataflowRegion
import de.cau.cs.kieler.sccharts.Region
import de.cau.cs.kieler.sccharts.SCCharts
import de.cau.cs.kieler.sccharts.SCChartsFactory
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.extensions.SCChartsControlflowRegionExtensions
import de.cau.cs.kieler.sccharts.extensions.SCChartsCoreExtensions
import de.cau.cs.kieler.sccharts.extensions.SCChartsDataflowRegionExtensions
import de.cau.cs.kieler.sccharts.extensions.SCChartsStateExtensions
import de.cau.cs.kieler.sccharts.extensions.SCChartsTransitionExtensions
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList
import java.util.HashSet
import java.util.List
import java.util.Map
import org.eclipse.cdt.core.CCorePlugin
import org.eclipse.cdt.core.dom.ast.IASTArrayDeclarator
import org.eclipse.cdt.core.dom.ast.IASTArraySubscriptExpression
import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression
import org.eclipse.cdt.core.dom.ast.IASTBreakStatement
import org.eclipse.cdt.core.dom.ast.IASTCaseStatement
import org.eclipse.cdt.core.dom.ast.IASTCastExpression
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement
import org.eclipse.cdt.core.dom.ast.IASTConditionalExpression
import org.eclipse.cdt.core.dom.ast.IASTContinueStatement
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement
import org.eclipse.cdt.core.dom.ast.IASTDefaultStatement
import org.eclipse.cdt.core.dom.ast.IASTDoStatement
import org.eclipse.cdt.core.dom.ast.IASTEqualsInitializer
import org.eclipse.cdt.core.dom.ast.IASTExpression
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement
import org.eclipse.cdt.core.dom.ast.IASTFieldReference
import org.eclipse.cdt.core.dom.ast.IASTForStatement
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition
import org.eclipse.cdt.core.dom.ast.IASTIdExpression
import org.eclipse.cdt.core.dom.ast.IASTIfStatement
import org.eclipse.cdt.core.dom.ast.IASTInitializer
import org.eclipse.cdt.core.dom.ast.IASTInitializerList
import org.eclipse.cdt.core.dom.ast.IASTLiteralExpression
import org.eclipse.cdt.core.dom.ast.IASTName
import org.eclipse.cdt.core.dom.ast.IASTNode
import org.eclipse.cdt.core.dom.ast.IASTNode.CopyStyle
import org.eclipse.cdt.core.dom.ast.IASTNullStatement
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclSpecifier
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration
import org.eclipse.cdt.core.dom.ast.IASTStandardFunctionDeclarator
import org.eclipse.cdt.core.dom.ast.IASTStatement
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit
import org.eclipse.cdt.core.dom.ast.IASTTypeIdExpression
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement
import org.eclipse.cdt.core.index.IIndex
import org.eclipse.cdt.core.index.IIndexBinding
import org.eclipse.cdt.core.model.CoreModel
import org.eclipse.cdt.core.model.ICProject
import org.eclipse.cdt.core.model.ITranslationUnit
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.core.resources.IResource

import static de.cau.cs.kieler.c.sccharts.processors.CDTToStringConverter.*

/**
 * A Processor analyzing the data flow of functions within a single file of a C project and visualizing it as actor-
 * based dataflow with {@link SCCharts}.
 * 
 * @author lan, nre
 */
@SuppressWarnings("all","unchecked")
class DataflowExtractor extends ExogenousProcessor<CodeContainer, SCCharts> {

    @Inject extension AnnotationsExtensions
    @Inject extension CDTConvertExtensions
    @Inject extension HighlightingExtensions
    @Inject extension KExpressionsCreateExtensions
    @Inject extension KExpressionsDeclarationExtensions
    @Inject extension KExpressionsValuedObjectExtensions
    @Inject extension PragmaExtensions
    @Inject extension SCChartsControlflowRegionExtensions
    @Inject extension SCChartsCoreExtensions
    @Inject extension SCChartsDataflowRegionExtensions
    @Inject extension SCChartsStateExtensions
    @Inject extension SCChartsTransitionExtensions
    @Inject extension ValueExtensions

    /**
     * If the model should be serializable. Could be read from a comment in the code, currently is just hardcoded though
     */
    // true, in a more complicated way to avoid warnings all over the place.
    static final boolean serializable = true ? true : false

    /** The seperator for valued object names and its further SSA objects. */
    static final String ssaNameSeperator = serializable ? "_" : " "

    /**
     * The name of the valued objects for returns.
     * Starts with a whitespace prefix so that it is not possible to clash with real variable names.
     */
    static final String returnObjectName = ssaNameSeperator + "res"

    static final String hiddenVariableName = ssaNameSeperator + "cond_res"

    /** The variable name for the conditional expression result */
    static final String conditionalResultName = ssaNameSeperator + "conditional"

    /** The valued object suffix for the input SSA object. */
    static final String inSuffix = ssaNameSeperator + "in"
    /** The valued object suffix for the output SSA object. */
    static final String outSuffix = ssaNameSeperator + "out"
    /** The name prefix of function parameters with an unknown name. */
    static final String unknownParamName = "param"

    /** Shown name prefix for if statements. */
    static final String ifName = "if"
    /**Shown name prefix for conditions of if statements. */
    static final String ifCondName = "if_cond"
    /** Shown name prefix for then statements. */
    static final String thenName = "then"
    /** Shown name prefix for else statements. */
    static final String elseName = "else"
    /** Shown name prefix for do..while statements. */
    static final String doName = "do"
    /** Shown name prefix for for statements. */
    static final String forName = "for"
    /** Shown name prefix for while statements. */
    static final String whileName = "while"
    /** Shown name prefix for while-body statements. */
    static final String whileBodyName = "while_body"
    /** Shown name prefix for while condition. */
    static final String whileCondName = "while_cond"
    /** Shown name prefix for switch statements. */
    static final String switchName = "switch"
    /** Shown name prefix for case statements. */
    static final String caseName = "case"
    /** Shown name prefix for default case statements in switches. */
    static final String defaultCaseName = "default"
    /** Shown name for break statements in loops. */
    static final String breakName = "break"
    /** Shown name for continue statements in loops. */
    static final String continueName = "continue"
    /** Shown name for multiplexer. */
    static final String multiplexerName = "M"

    /** Name for the return combine state. */
    static final String returnCombineName = " returnCombine"
    /** Name for the sizeof state. */
    static final String sizeofName = " sizeof"

    /** Tag annotation string for custom multiplexers*/
    static final String multiplexerTag = "mult"
    /** Tag annotation string of the port group for the positive case in custom multiplexers */
    static final String posTag = "pos"
    /** Tag annotation string of the port group for the negative case in custom multiplexers */
    static final String negTag = "neg"
    
    /** annotation for break/continue states that saves in which if stmt the break/continue is in */
    static final String breakContinueAnno = "ifCounter"

    /** The index for the project the translated file is in, or null if none found. */
    var IIndex index
    /** 
     * Mapping for a function-unique object to to map to the SCCharts state created for it.
     * Either an {@link IIndexBinding} if an index for the project the translated file lies in could be created,
     * or a String identifying the function's name otherwise, together with an int for the number of parameters.
     */
    var functions = <Object, Map<Integer, State>>newHashMap
    var ifCounter = 0;
    var swCounter = 0;
    var caseCounter = 0;
    var forCounter = 0;
    var whileCounter = 0;
    var doCounter = 0;
    var referenceCounter = 0;
    var returnCombineCounter = 0;
    var sizeofCounter = 0;
    var breakCounter = 0;
    var continueCounter = 0;
    var multiplexerCounter = 0;

    // needed to add break/continue states to the correct while state
    var DataflowRegion lastWhileRegion = null
    var breakContinueFlag = false
    
    /** parent region of a state */
    val Map<State, DataflowRegion> hierarchy = newHashMap
    
    /** the valued object of a state */
    val Map<State, ValuedObject> stateObjects = newHashMap
    
    /** the valued objects of the local vars that are used for the break/continue states */
    val Map<ValuedObject, ValuedObject> breakContinueVars = newHashMap

    val Map<State, Map<String, List<ValuedObject>>> valuedObjects = newHashMap

    /** All expressions that are pointers to some variable. */
    val Map<State, Map<IASTExpression, ValuedObject>> pointers = newHashMap

    /** All variables that are pointers to another variable. */
    val Map<State, Map<String, ValuedObject>> pointerVariables = newHashMap
    
    val String addressOp = "~"

    val voWrittenIdxs = <ValuedObject, List<Expression>>newHashMap

    var ValueType currentReturnType = null

    /** The file contents, for referencing direct String representations. */
    var byte[] sourceFile

    /** 
     * The root SCCharts container of the model. Contains every function as a state, or every function and other
     * constructs modeled as states if the {@code serializable} flag is set.
     */
    var SCCharts rootSCChart

    override String getId() {
        return "de.cau.cs.kieler.c.sccharts.dataflowExtractor"
    }

    override String getName() {
        return "Dataflow Extractor"
    }

    /**
     * Getter to access the state variable map.
     * 
     * @param the scoping state containing the variables.
     * @return the used variables (as valued object lists) for the given state
     */
    def getStateVariables(State state) {
        var Map<String, List<ValuedObject>> stateVariables = null
        if (valuedObjects.containsKey(state)) {
            stateVariables = valuedObjects.get(state)
        } else {
            stateVariables = newHashMap
            valuedObjects.put(state, stateVariables)
        }
        return stateVariables
    }

    /**
     * Getter to access the pointer variable map.
     * 
     * @param the scoping state containing the variables.
     * @return the used variables (as valued object lists) for the given state
     */
    def getStatePointers(State state) {
        var Map<IASTExpression, ValuedObject> statePointers = null
        if (pointers.containsKey(state)) {
            statePointers = pointers.get(state)
        } else {
            statePointers = newHashMap
            pointers.put(state, statePointers)
        }
        return statePointers
    }
    
    def getStateVarPointers(State state) {
        var Map<String, ValuedObject> statePointers = null
        if (pointerVariables.containsKey(state)) {
            statePointers = pointerVariables.get(state)
        } else {
            statePointers = newHashMap
            pointerVariables.put(state, statePointers)
        }
        return statePointers
    }

    override void process() {
        val dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS")
        var now = LocalDateTime.now

        println("Starting Dataflow Extractor - Time: " + dtf.format(now))
        val container = getModel

        val model = container.transform

        now = LocalDateTime.now
        println("Dataflow Extractor finished - Time: " + dtf.format(now))

        setModel(model)
    }

    def SCCharts transform(CodeContainer codeContainer) {
        val infrastructure = ProjectInfrastructure.getProjectInfrastructure(environment)
        var IASTTranslationUnit ast

        // First, try to find the C/C++ project this file is defined in and create the AST and index from there.
        try {
            val ICProject[] allProjects = CoreModel.getDefault().getCModel().getCProjects();
            var IResource resource
            var ICProject project
            var boolean break = false
            for (var int i = 0; i < allProjects.size && !break; i++) {
                project = allProjects.get(i)
                resource = CProcessorUtils.findFileInProject(infrastructure.modelFile, project)
                if (resource !== null) {
                    break = true
                }
            }
            var ITranslationUnit tu = CoreModel.^default.create(resource) as ITranslationUnit
            ast = tu.getAST
            index = CCorePlugin.getIndexManager().getIndex(project);
        } catch (Throwable t) {/* Ignore. */}
        
        // If the previous method was not successful, build the AST only from the file without context and the index.
        if (ast === null) {
            ast = CProcessorUtils.findAstNoProject(environment)
        }

        // If we still do not have an AST, abort.
        if (ast === null) {
            return null
        }
        // Remember the file contents as well for direct string representations in the diagram.
        if (ast instanceof ASTNode) {
            val String filePath = ast.filePath
            if (filePath !== null) {
                sourceFile = Files.readAllBytes(Paths.get(filePath))
            }
        }

        // Create SCCharts root elements
        rootSCChart = createSCChart

        rootSCChart.pragmas.add(createStringPragma("skinpath", "dataflow"))

        // Make sure to always have the read lock on the index here and releasing it eventually.
        index?.acquireReadLock
        try {
            // Interesting index functions:
            // findBinding(IName name) 
            // findReferences(IBinding binding) 
            // findDeclarations(IBinding binding)
            // findDefinitions(IBinding binding)
            val functionDefinitions = ast.children.filter(IASTFunctionDefinition)
            // Build a scaffold for referencing all defined states in this file for each defined function.
            for (functionDefinition : functionDefinitions) {
                val state = createFunctionScaffold(functionDefinition)
                rootSCChart.rootStates += state
            }
            // Go through each function definition again and fill in the scaffolds now that all detailed functions are
            // known.
            for (functionDefinition : functionDefinitions) {
                fillFunctionScaffold(functionDefinition)
            }
        } finally {
            index?.releaseReadLock
        }

        return rootSCChart
    }

    /**
     * Creates an {@link SCCharts} {@link State} scaffold for the given function definition with all in- and outputs
     * set, but a yet empty contained dataflow region. To fill that, call
     * {@link #fillFunctionScaffold(IASTFunctionDefinition)} after scaffolding all functions that should be visualized.
     */
    def State createFunctionScaffold(IASTFunctionDefinition func) {
        // Determine function name
        val funcDeclarator = func.getDeclarator
        val funcName = funcDeclarator.getName
        val uniqueFunctionIdentifier = CProcessorUtils.nameToIdentifier(funcName, index)

        // Determine the number of parameters.
        var int params = 0;
        if (funcDeclarator instanceof IASTStandardFunctionDeclarator) {
            params = funcDeclarator.getParameters.size
        }

        // Create the state
        val State state = createState(funcName.toString + ssaNameSeperator + params)
        state.label = funcName.toString
        val map = <Integer, State>newHashMap
        map.put(params, state)
        functions.put(uniqueFunctionIdentifier, map)
        state.label = funcName.toString

        if (!serializable) {
            // Insert text highlighting annotations
            state.insertHighlightAnnotations(func)
        }

        // Create a hashmap for the functions valued objects if needed
        val Map<String, List<ValuedObject>> stateVariables = getStateVariables(state)

        // Create inputs for the functions parameters
        if (funcDeclarator instanceof IASTStandardFunctionDeclarator) {
            val parameters = funcDeclarator.getParameters
            val Map<ValueType, VariableDeclaration> declarations = newHashMap
            for (par : parameters) {

                val declSpecifier = par.getDeclSpecifier
                if (declSpecifier instanceof IASTSimpleDeclSpecifier) {
                    // Determine parameter type
                    val inputType = declSpecifier.type.cdtTypeConversion
                    if (!declarations.containsKey(inputType)) {
                        val newDecl = createVariableDeclaration
                        state.declarations += newDecl
                        newDecl.type = inputType
                        newDecl.input = true
                        declarations.put(inputType, newDecl)
                    }
                    val decl = declarations.get(inputType)
                    // Determine parameter name
                    val varName = par.getDeclarator.getName.toString
                    val varList = <ValuedObject>newArrayList

                    // Create valued object for the input
                    val vo = decl.createValuedObject(varName + inSuffix)
                    vo.label = varName
                    if (!serializable) {
                        vo.insertHighlightAnnotations(par)
                    }

                    // Attach the valued object to its list and the list to the map
                    varList.add(vo)
                    stateVariables.put(varName, varList)
                }
            }
        }

        // Determine function output
        val outputDeclSpecifier = func.getDeclSpecifier
        if (outputDeclSpecifier instanceof IASTSimpleDeclSpecifier) {
            val type = (outputDeclSpecifier as IASTSimpleDeclSpecifier).type.cdtTypeConversion
            currentReturnType = type
            if (type !== null) {
                val varList = addReturnDecl(state)
                stateVariables.put(returnObjectName, varList)
            }
        }

        // TODO: determine array/pointer function outputs.
        // Easy approach (as done here): every array/pointer is written to and set as output.
        // Medium approach: For each function, determine if the array/pointed variable is written to directly.
        // Complex approach: same as medium, just also include analysis of renamed array/pointer variables and tracking it
        // through function call hierarchies.  
        // For each array/pointer parameter, determine if it has been written to in the function
        // by analyzing the valued objects for said parameter. If written to, add another output
        // declaration with an assignment of the final value to that output declaration.
        if (funcDeclarator instanceof IASTStandardFunctionDeclarator) {
            val parameters = funcDeclarator.getParameters
            for (par : parameters) {
                val isArray = par.getDeclSpecifier instanceof IASTSimpleDeclSpecifier &&
                    par.declarator instanceof IASTArrayDeclarator
                val isPointer = par.declarator.pointerOperators !== null && !par.declarator.pointerOperators.isEmpty
                if (isArray || isPointer) {
                    val type = isArray ? (outputDeclSpecifier as IASTSimpleDeclSpecifier).type.cdtTypeConversion
                        : (par.declSpecifier as IASTSimpleDeclSpecifier).type.cdtTypeConversion
                    // Determine parameter name
                    val varName = par.getDeclarator.getName.toString
                    val varList = stateVariables.get(varName)
                    // Create output declaration                    
                    val outDecl = createVariableDeclaration
                    outDecl.type = type
                    outDecl.output = true
                    state.declarations += outDecl

                    // Create valued object for the output.
                    val outputVo = outDecl.createValuedObject(varName + outSuffix)
                    outputVo.label = varName
                    if (!serializable) {
                        outputVo.insertHighlightAnnotations(par)
                    }

                    // Attach the valued object to its list.
                    varList.add(outputVo)
                }
            }
        }

        return state
    }

    /**
     * Fills a scaffolded {@link SCCharts} {@link State} for the given function definition and fills its contained
     * dataflow region. The scaffolds for all references states need to be created first by
     * {@link #createFunctionScaffold(IASTFunctionDefinition)}.
     */
    def void fillFunctionScaffold(IASTFunctionDefinition func) {
        // Determine function name
        val funcDeclarator = func.getDeclarator
        val funcName = funcDeclarator.getName
        val uniqueFunctionIdentifier = CProcessorUtils.nameToIdentifier(funcName, index)

        // Fetch the state
        val state = functions.get(uniqueFunctionIdentifier).values.head

        // Translate function body
        val body = func.getBody
        var DataflowRegion bodyRegion
        if (body instanceof IASTCompoundStatement) {
            bodyRegion = buildCompound(body, state)
            state.regions += bodyRegion
            bodyRegion.label = funcName.toString
        } else {
            println("ERROR: Body of " + funcName.toString + " is not a Compound Statement!")
        }
    }

    /**
     * Create a state representation for a call of an undefined function. The in- and outputs are either guessed by
     * the function call or determined by looking for the function in the AST/index.
     * 
     * @param funcName The name this function state should have.
     * @param funcCall The AST function call expression of which to get the data from to create the state.
     * @param parentState The parent state that will be containing this state, for reference.
     * @param dRegion The parent dataflow region that will be containing this state, for reference.
     * @return A new state for a function unknown in the AST.
     */
    def State createUnknownFunctionScaffold(IASTName funcName, IASTFunctionCallExpression funcCall, State parentState,
        DataflowRegion dRegion) {
        val params = funcCall.arguments.size
        // Create the state
        val state = createState(funcName.toString + ssaNameSeperator + params)
        state.label = funcName.toString
        state.annotations += createTagAnnotation("Hide")
        if (serializable) {
            rootSCChart.rootStates += state
        }
        state.label = funcName.toString
        val bodyRegion = state.createDataflowRegion(funcName.toString)
        bodyRegion.label = funcName.toString

        // Create an output
        val outputDecl = createVariableDeclaration
        outputDecl.output = true
        outputDecl.type = ValueType::INT
        state.declarations += outputDecl
        val outputVO = outputDecl.createValuedObject(returnObjectName + outSuffix)
        outputVO.label = returnObjectName

        // Create inputs for the functions parameters.
        // Additionally, for each array/pointer argument also add an output as the function could potentially write to
        // it.
        val declaration = createVariableDeclaration
        declaration.input = true
        declaration.type = ValueType::INT
        state.declarations += declaration
        for (arg : funcCall.arguments) {
            var needsOutput = true
            // Determine parameter name and label
            var String varName
            var String varLabel
            if (arg instanceof IASTIdExpression) {
                varName = arg.getName.toString
                varLabel = varName
                val parentDeclarations = dRegion.declarations.filter(VariableDeclaration)
                val varName_ = varName
                val inputVO = parentDeclarations.map[it.valuedObjects].flatten.findFirst [
                    it.name.substring(0, it.name.indexOf(ssaNameSeperator)).equals(varName_)
                ]
                if (inputVO === null || inputVO.cardinalities.empty) {
                    needsOutput = false
                }
            } else if (arg instanceof IASTUnaryExpression &&
                (arg as IASTUnaryExpression).operator === IASTUnaryExpression.op_amper &&
                (arg as IASTUnaryExpression).operand instanceof IASTIdExpression) {
                val idExpression = (arg as IASTUnaryExpression).operand as IASTIdExpression
                varName = idExpression.name.toString
                varLabel = '&' + varName
                val varName_ = varName
                val parentDeclarations = dRegion.declarations.filter(VariableDeclaration) +
                    parentState.declarations.filter(VariableDeclaration)
                val inputVO = parentDeclarations.map[it.valuedObjects].flatten.findFirst [
                    it.name.substring(0, it.name.indexOf(ssaNameSeperator)).equals(varName_)
                ]
                getStatePointers(state).put(arg as IASTUnaryExpression, inputVO)
                needsOutput = true
            } else {
                needsOutput = false
                varName = unknownParamName + ssaNameSeperator + funcCall.arguments.indexOf(arg)
            }

            // Create valued object for the input
            val vo = declaration.createValuedObject(varName + inSuffix)
            vo.label = varLabel
            if (!serializable) {
                vo.insertHighlightAnnotations(arg)
            }

            if (needsOutput) {
                // Create output declaration
                val outDecl = createVariableDeclaration
                outDecl.output = true
                outDecl.type = ValueType::INT
                state.declarations += outDecl

                // Create valued object for the output.
                val outputVo = outDecl.createValuedObject(varName + outSuffix)
                outputVo.label = varName
                if (!serializable) {
                    outputVo.insertHighlightAnnotations(arg)
                }
            }
        }

        // Remember this state scaffold in the map.
        val identifier = CProcessorUtils.nameToIdentifier(funcName, index)
        val map = functions.get(identifier) ?: <Integer, State>newHashMap
        map.put(params, state)
        functions.put(identifier, map)

        return state
    }

    /**
     * Create the dataflow region for a compound statement and extract each containing statement
     */
    def DataflowRegion buildCompound(IASTCompoundStatement body, State bodyState) {
        return buildCompound(body, bodyState, null)
    }

    /**
     * Create the dataflow region for a compound statement and extract each containing statement.
     * Additional declarations are added to the DF region as well, without their initializations, if any.
     */
    def DataflowRegion buildCompound(IASTCompoundStatement body, State bodyState,
        IASTStatement additionalDeclarations) {
        val dfRegion = createDataflowRegion("DF-" + bodyState.name)
        if (!serializable) {
            dfRegion.insertHighlightAnnotations(body)
        }

        addAdditionalDeclarations(additionalDeclarations, bodyState, dfRegion)

        if (bodyState.name.contains(whileName + ssaNameSeperator)) {
            lastWhileRegion = dfRegion
        }

        val statements = body.getStatements
        for (stmt : statements) {
            buildStatement(stmt, bodyState, dfRegion)
            if (bodyState.name.contains(whileName + ssaNameSeperator)) {
                lastWhileRegion = dfRegion
            }
        }
        if (breakContinueFlag && bodyState.name.contains(whileName + ssaNameSeperator)) {
            finalizeBreakAndContinueStates(body, bodyState, dfRegion)
            breakContinueFlag = false
        }
        linkOutputs(bodyState, dfRegion)

        return dfRegion
    }


    /**
     * Searches the outputs that should be led into a break multiplexer 
     *  since they depend on whether a break is taken or not taken.
     * 
     */
    private def findBreakOutputs(IASTCompoundStatement whileBody, IASTIfStatement ifStmt, State parentState) {
        var breakFound = false
        var HashSet<String> outputs = new HashSet()
        for (stmt : whileBody.statements) {
            if (stmt instanceof IASTIfStatement && !breakFound) {
                if (containsSearchedIfStmt(stmt as IASTIfStatement, ifStmt)) {
                    breakFound = true
                    outputs.addAll(breakDependableVarsInIfTree(ifStmt, parentState))          
                }
            } else {
                if (breakFound) {
                    outputs.addAll(findOutputs(stmt, parentState, true))
                }
            }
        }
        return outputs
    }
    
    /**
     * Checks if the passed if statement is the searched if statement or if the latter 
     *  is a parent of the passed if statement.
     */
    private def Boolean containsSearchedIfStmt(IASTIfStatement outerIf, IASTIfStatement searchedIf) {
        if (outerIf == searchedIf) {
            return true;
        }
        val thenStmts = (outerIf.thenClause as IASTCompoundStatement).statements.toList
        
        var List<IASTStatement> elseStmts
        if (outerIf.elseClause !== null) {
            elseStmts = (outerIf.elseClause as IASTCompoundStatement).statements.toList
        }
        
        val List<IASTStatement> allStmts = newArrayList
        if (outerIf.elseClause !== null) {
            allStmts.addAll(elseStmts)
        }
        allStmts.addAll(thenStmts)
        
        for (s : allStmts) {
            if (s instanceof IASTIfStatement && containsSearchedIfStmt(s as IASTIfStatement, searchedIf)) {
                return true;
            }
        }

        return false;
    }
    
    /**
     * Walks upwards from a nested if statement including a break and collects the variables that depend on whether
     * the break is taken or not taken.
     */

    private def breakDependableVarsInIfTree(IASTIfStatement searchedIf, State parentState){
        var HashSet<String> vars = <String>newHashSet
        
        var currentlySearchedIf = searchedIf
        var parentClause = currentlySearchedIf.parent as IASTCompoundStatement
        var ifFound = false
        
        while (parentClause.parent instanceof IASTIfStatement) {
            for (s : parentClause.statements) {
                // Look in the current level whether the searchedIf is present and add all variables to the result
                // that are textually defined below it
                if (!ifFound) {
                    if (s == currentlySearchedIf) {
                        ifFound = true
                    }
                } else {
                    vars.addAll(findOutputs(s, parentState, true))
                }
            }
            // Step one if level upwards
            currentlySearchedIf = parentClause.parent as IASTIfStatement
            parentClause = currentlySearchedIf.parent as IASTCompoundStatement
            ifFound = false

        }
        return vars
    }

    /**
     *  Create a declaration of a local variable and attach it to the dataflow region
     */
    def dispatch void buildStatement(IASTDeclarationStatement stmt, State rootState, DataflowRegion dRegion) {
        val declaration = stmt.getDeclaration
        if (declaration instanceof IASTSimpleDeclaration) {
            val decl = addDeclaration(declaration, rootState, dRegion)
            dRegion.declarations += decl
            decl.annotations += createTagAnnotation("Hide")
        }
    }

    /**
     * Translate an expression
     */
    def dispatch void buildStatement(IASTExpressionStatement stmt, State rootState, DataflowRegion dRegion) {
        val expression = stmt.getExpression

        createExpression(expression, rootState, dRegion)
    }

    /**
     * Create the expression of an expression statement.
     * 
     * @param expression The expression to create.
     * @param rootState The state to link this expression to.
     * @param dRegion The region to link this expression to.
     * @return a reference to the expression value, if applicable, {@code null} otherwise.
     */
    def ValuedObjectReference createExpression(IASTExpression expression, State rootState, DataflowRegion dRegion) {
        // Translate binary and unary expressions
        if (expression instanceof IASTBinaryExpression) {
            createBinaryAssignment(expression, rootState, dRegion)
            return null
        } else if (expression instanceof IASTUnaryExpression) {
            createUnaryAssignment(expression, rootState, dRegion)
            return null
        // Create a function call by referencing the function state    
        } else if (expression instanceof IASTFunctionCallExpression) {
            return createFunctionCall(expression, rootState, dRegion)
        } else if (expression instanceof IASTConditionalExpression) {
            return createConditionalExpression(expression, rootState, dRegion)
        }
    }

    /**
     * Translate If statement
     */
    def dispatch void buildStatement(IASTIfStatement stmt, State rootState, DataflowRegion dRegion) {
        buildIf(stmt, rootState, dRegion)
    }

    /**
     * Translate Switch statment
     */
    def dispatch void buildStatement(IASTSwitchStatement stmt, State rootState, DataflowRegion dRegion) {
        buildSwitch(stmt, rootState, dRegion)
    }

    /**
     * Translate For statement
     */
    def dispatch void buildStatement(IASTForStatement stmt, State rootState, DataflowRegion dRegion) {
        buildFor(stmt, rootState, dRegion)
    }

    /**
     * Translate While statement
     */
    def dispatch void buildStatement(IASTWhileStatement stmt, State rootState, DataflowRegion dRegion) {
//        buildWhile(stmt, rootState, dRegion)
        buildWhileDF(stmt, rootState, dRegion)
    }

    /**
     * Translate Do statement
     */
    def dispatch void buildStatement(IASTDoStatement stmt, State rootState, DataflowRegion dRegion) {
        buildDo(stmt, rootState, dRegion)
    }

    /**
     * Translate a Break statement
     */
    def dispatch void buildStatement(IASTBreakStatement stmt, State rootState, DataflowRegion dRegion) {
        breakContinueFlag = true
        buildBreakOrContinue(stmt, rootState, dRegion)
    }
    
    /**
     * Translate a Continue statement
     */
    def dispatch void buildStatement(IASTContinueStatement stmt, State rootState, DataflowRegion dRegion) {
        breakContinueFlag = true
        buildBreakOrContinue(stmt, rootState, dRegion)
    }

    /**
     * Translate Return statement
     */
    def dispatch void buildStatement(IASTReturnStatement stmt, State rootState, DataflowRegion dRegion) {
        val retKExpr = createKExpression(stmt.getReturnValue, rootState, dRegion)

        val retVO = rootState.findValuedObjectByName(returnObjectName, true, dRegion)
        if (!serializable) {
            retVO.insertHighlightAnnotations(stmt)
        }
        dRegion.equations += createDataflowAssignment(retVO, retKExpr)
    }

    /**
     * Translate all children of a CompoundStatement
     */
    def dispatch void buildStatement(IASTCompoundStatement stmt, State rootState, DataflowRegion dRegion) {
        for (child : stmt.getStatements) {
            buildStatement(child, rootState, dRegion)
        }
    }

    /**
     * Translate a Null statement. Does nothing.
     */
    def dispatch void buildStatement(IASTNullStatement stmt, State rootState, DataflowRegion dRegion) {
    }

    /**
     * Fallback translation of any not yet covered statement. Does nothing.
     */
    def dispatch void buildStatement(IASTStatement stmt, State rootState, DataflowRegion dRegion) {
        println("DataflowExtractor: Unsupported statement class detected! " + stmt.class.name)
    }

    /**
     * Extract the body of an if Statement
     */
    def void buildIf(IASTIfStatement ifStmt, State rootState, DataflowRegion dRegion) {
        buildConditionalDF(ifStmt, ifStmt.conditionExpression, ifStmt.thenClause, ifStmt.elseClause, rootState, dRegion)
    }

    /**
     * Create a state for a conditional expression, linked with its in- and outputs.
     */
    def ValuedObjectReference createConditionalExpression(IASTConditionalExpression expr, State rootState,
        DataflowRegion dRegion) {
        buildConditional(expr, expr.logicalConditionExpression, expr.positiveResultExpression,
            expr.negativeResultExpression, rootState, dRegion)
    }

    /**
     * Build a conditional expression or statement, as in a ternary conditional expression using ? and : or an if[else]
     * construct. This variant builds the conditional in a dataflow-alike fashion with seperate then and else dataflow
     * regions connected via a conditional based on the condition.
     */
    private def ValuedObjectReference buildConditionalDF(IASTNode node, IASTExpression condition, IASTNode positive,
        IASTNode negative, State rootState, DataflowRegion dRegion) {

        val localIfCounter = ifCounter
        ifCounter++

        // Create the state to represent the if statement.
        val ifState = createState(ifName + ssaNameSeperator + localIfCounter)
        ifState.annotations += createTagAnnotation("Hide")
        if (serializable) {
            rootSCChart.rootStates += ifState
        }
        
        // Create a reference for this if state in the containing dataflow-region.
        val refDecl = createReferenceDeclaration
        dRegion.declarations += refDecl
        refDecl.setReference(ifState)
        val ifObj = refDecl.createValuedObject(ifName + ssaNameSeperator + localIfCounter)
        if (!serializable) {
            ifObj.insertHighlightAnnotations(node)
        }
        
        hierarchy.put(ifState, dRegion)
        stateObjects.put(ifState, ifObj)

        // Set the in and outputs of the state
        setInputs(node, rootState, ifState, dRegion, ifObj)
        setOutputs(node, rootState, ifState, dRegion, ifObj)

        // Create the dataflow region for the if state
        val ifRegion = ifState.createDataflowRegion("")
        if (!serializable) {
            ifRegion.insertHighlightAnnotations(node)
        }
        ifRegion.label = ifName + ssaNameSeperator + localIfCounter

        // Create if state, its dataflow region, and conditional obj
        val condRes = createCondDF(localIfCounter, node as IASTStatement, ifState, ifRegion)
        val condRegion = condRes.key
        val condState = condRes.value.key
        val conditionalObj = condRes.value.value
        
        hierarchy.put(condState, ifRegion)
        
        // inlining the output of the condition
        var ValuedObject condOutputVo = findCondOutputVo(condRegion, condState, conditionalObj, ifRegion)

        // Create a then state and according reference into the if dataflow region.
        val thenState = createState(ifName + ssaNameSeperator + localIfCounter + thenName)
        thenState.annotations += createTagAnnotation("Hide")
        if (serializable) {
            rootSCChart.rootStates += thenState
        }
        val thenRefDecl = createReferenceDeclaration
        ifRegion.declarations += thenRefDecl
        thenRefDecl.setReference(thenState)
        val thenObj = thenRefDecl.createValuedObject(ifName + ssaNameSeperator + localIfCounter + thenName)
        if (!serializable) {
            thenObj.insertHighlightAnnotations(positive)
        }
        
        hierarchy.put(thenState, ifRegion)
        stateObjects.put(thenState, thenObj)
        
        // Set the inputs of the state
        setInputs(positive, ifState, thenState, ifRegion, thenObj)
        // Set the outputs of the then/else states to refer to, but do not add the equations for them yet, as we want to
        // route them into a conditional.
        setOutputs(positive, ifState, thenState, ifRegion, thenObj, false)

        // Create the region for the 'then' part
        val thenRegion = createDFRegionForNode(positive, thenState, ifState, ifRegion, thenObj)
        thenState.regions += thenRegion
        thenRegion.label = thenName

        // If an else is given the region is also created
        var State elseState
        var ValuedObject elseObj
        var DataflowRegion elseRegion
        if (negative !== null) {
            // Create an else state and according reference into the if dataflow region.
            elseState = createState(ifName + ssaNameSeperator + localIfCounter + elseName)
            elseState.annotations += createTagAnnotation("Hide")
            if (serializable) {
                rootSCChart.rootStates += elseState
            }
            val elseRefDecl = createReferenceDeclaration
            ifRegion.declarations += elseRefDecl
            elseRefDecl.setReference(elseState)
            elseObj = elseRefDecl.createValuedObject(ifName + ssaNameSeperator + localIfCounter + elseName)
            if (!serializable) {
                elseObj.insertHighlightAnnotations(negative)
            }

            hierarchy.put(elseState, ifRegion)
            stateObjects.put(elseState, elseObj)

            // Set the in and outputs of the state
            setInputs(negative, ifState, elseState, ifRegion, elseObj)
            setOutputs(negative, ifState, elseState, ifRegion, elseObj, false)

            // Create the region for the 'else' part
            elseRegion = createDFRegionForNode(negative, elseState, ifState, ifRegion, elseObj)
            elseState.regions += elseRegion
            elseRegion.label = elseName
        }

        // Create the conditional for each output of the then/else states.
        // Determine which outputs have been set in their respective state
        var positiveOutputs = findOutputs(positive, ifState, false)
        if (getStateVariables(thenState).containsKey(returnObjectName)) {
            positiveOutputs += returnObjectName
        }
        var negativeOutputs = <String>newHashSet
        if (negative !== null) {
            negativeOutputs += findOutputs(negative, ifState, false)
            if (getStateVariables(elseState).containsKey(returnObjectName)) {
                negativeOutputs += returnObjectName
            }
        }
        val HashSet<String> allOutputs = new HashSet
        allOutputs.addAll(positiveOutputs)
        allOutputs.addAll(negativeOutputs)

        // Lists for collecting inputs and outputs for the custom multiplexer
        var multInputs = new ArrayList
        var multOutputs = new ArrayList

        // For each output, find the output VO and connect them to a conditional
        for (output : allOutputs) {
            // Find the output VO for the then state
            var ValuedObject innerPositiveOutputVo
            if (positiveOutputs.contains(output)) {
                innerPositiveOutputVo = thenState.declarations.filter(VariableDeclaration).filter[it.isOutput].flatMap [
                    it.valuedObjects
                ].filter[it.label == output].head
            }
            // Find the output VO for the else state
            var ValuedObject innerNegativeOutputVo
            if (negativeOutputs.contains(output)) {
                innerNegativeOutputVo = elseState.declarations.filter(VariableDeclaration).filter[it.isOutput].flatMap [
                    it.valuedObjects
                ].filter[it.label == output].head
            }

            var ValuedObject positiveOutputVo
            if (innerPositiveOutputVo !== null) {
                positiveOutputVo = createOutputVo(thenName, localIfCounter, ifState, thenObj, ifRegion,
                    innerPositiveOutputVo)

            }

            var ValuedObject negativeOutputVo
            if (innerNegativeOutputVo !== null) {
                negativeOutputVo = createOutputVo(elseName, localIfCounter, ifState, elseObj, ifRegion,
                    innerNegativeOutputVo)
            }

            // If the variable is not written to in the positive or negative case, use the input VO as the output. 
            val ifOutputVo = ifState.declarations.filter(VariableDeclaration).filter[it.isInput].flatMap [
                it.valuedObjects
            ].filter[it.label == output].head
            if (positiveOutputVo === null) {
                positiveOutputVo = ifOutputVo
            }
            if (negativeOutputVo === null) {
                negativeOutputVo = ifOutputVo
            }

            // Collect the outputVos in the multInputs list for the custom multiplexer
            if (positiveOutputVo === null) {
                negativeOutputVo.addTagAnnotation(negTag);
                multInputs.add(negativeOutputVo)
            } else if (negativeOutputVo === null) {
                positiveOutputVo.addTagAnnotation(posTag)
                multInputs.add(positiveOutputVo)
            } else {
                positiveOutputVo.addTagAnnotation(posTag)
                negativeOutputVo.addTagAnnotation(negTag)
                multInputs.add(positiveOutputVo)
                multInputs.add(negativeOutputVo)
            }

            val outputVO = ifState.findOutputVar(output)

            multOutputs.add(outputVO)

        }
        
        if (!multInputs.empty) {
            createMultiplexer(ifRegion, condOutputVo, multInputs, multOutputs);
        }

        linkReturn(ifState, rootState, ifRegion, dRegion, ifObj)

        // If there is some assignment to the hidden variable from a conditional expression, use it as the sub-reference
        val subReference = (getStateVariables(ifState) + getStateVariables(thenState) + getStateVariables(elseState)).
            filter [ key, value |
                key.equals(hiddenVariableName)
            ].values.head?.last?.reference
        return ifObj.reference => [
            it.subReference = subReference
        ]
    }

    /**
     * Build a conditional expression or statement, as in a ternary conditional expression using ? and : or an if[else]
     * construct. This variant builds the conditional in a state machine-alike fashion with seperate then and else
     * states.
     */
    private def ValuedObjectReference buildConditional(IASTNode node, IASTExpression condition, IASTNode positive,
        IASTNode negative, State rootState, DataflowRegion dRegion) {

        // Create the state to represent the if statement.
        val ifState = createState(ifName + ssaNameSeperator + ifCounter)
        ifState.annotations += createTagAnnotation("Hide")
        if (serializable) {
            rootSCChart.rootStates += ifState
        }

        // Create a reference for this if state in the containing dataflow-region.
        val refDecl = createReferenceDeclaration
        dRegion.declarations += refDecl
        refDecl.setReference(ifState)
        val ifObj = refDecl.createValuedObject(ifName + ssaNameSeperator + ifCounter)
        if (!serializable) {
            ifObj.insertHighlightAnnotations(node)
        }
        ifCounter++

        // Set the in and outputs of the state
        setInputs(node, rootState, ifState, dRegion, ifObj)
        setOutputs(node, rootState, ifState, dRegion, ifObj)

        // Create the controlflow region for the if state and the initial state
        val cRegion = ifState.createControlflowRegion("")
        val initState = cRegion.createState("init")
        initState.label = ""
        if (!serializable) {
            initState.insertHighlightAnnotations(node)
        }
        initState.initial = true

        // Create the state for the then part
        val State thenState = createStateForNode(positive, cRegion, rootState, dRegion, ifObj, thenName)
        val thenTransition = initState.createImmediateTransitionTo(thenState)
        thenTransition.trigger = (condition).createKExpression(ifState, dRegion)
        thenTransition.label = exprToString(condition, sourceFile)
        // If an else is given the state is also created
        var State elseState
        if (negative !== null) {
            elseState = createStateForNode(negative, cRegion, rootState, dRegion, ifObj, elseName)
            initState.createImmediateTransitionTo(elseState)
        }

        assignOutputs(ifState, ifObj, rootState, dRegion)

        // If there is some assignment to the hidden variable from a conditional expression, use it as the sub-reference
        val subReference = (getStateVariables(ifState) + getStateVariables(thenState) + getStateVariables(elseState)).
            filter [ key, value |
                key.equals(hiddenVariableName)
            ].values.head?.last?.reference
        return ifObj.reference => [
            it.subReference = subReference
        ]
    }

    /**
     * Creates and returns a new state in the {@code parentRegion} of the {@code currentState} with a {@code name}
     * that contains the dataflow behavior of the {@code node}.
     */
    private def State createStateForNode(IASTNode node, ControlflowRegion parentCRegion, State parentState,
        DataflowRegion parentDRegion, ValuedObject parentReference, String name) {
        val newState = parentCRegion.createState(name)
        newState.label = name
        if (!serializable) {
            newState.insertHighlightAnnotations(node)
        }
        // Set the in and outputs of the state
        setInputs(node, parentState, newState, parentDRegion, null, false)
        setOutputs(node, parentState, newState, parentDRegion, null, false)

        val DataflowRegion region = createDFRegionForNode(node, newState, parentState, parentDRegion, parentReference)
        region.label = name
        newState.regions += region

        return newState
    }

    /**
     * Creates and returns a new dataflow region using the in/outputs of the {@code currentState} for the given
     * {@code node}.
     */
    private def DataflowRegion createDFRegionForNode(IASTNode node, State currentState, State parentState,
        DataflowRegion parentRegion, ValuedObject parentReference) {
        return createDFRegionForNode(node, currentState, parentState, parentRegion, parentReference, null)
    }

    /**
     * Creates and returns a new dataflow region using the in/outputs of the {@code currentState} for the given
     * {@code node}. The {@code additionalDeclarations} can be used if this region needs some declarations defined
     * as well that should not be visible from the outside. Used for example for 'for' loop's initializer statement.
     * 
     * @param node The node to create the region for.
     * @param currentState The state this region should be contained in.
     * @param parentState The parent state containing the currentState.
     * @param parentRegion The dataflow region containing the state for this new region.
     * @param parentReference The reference valued object used for the parentState.
     * @param additionalDeclarations Any additional declarations that need to be known for creating this state.
     * @return The new dataflow region.
     */
    private def DataflowRegion createDFRegionForNode(IASTNode node, State currentState, State parentState,
        DataflowRegion parentRegion, ValuedObject parentReference, IASTStatement additionalDeclarations) {
        var DataflowRegion newRegion
        if (node instanceof IASTCompoundStatement) {
            newRegion = buildCompound(node, currentState, additionalDeclarations)
        } else {
            newRegion = createDataflowRegion("DF-" + currentState.name)
            if (!serializable) {
                newRegion.insertHighlightAnnotations(node)
            }

            addAdditionalDeclarations(additionalDeclarations, currentState, newRegion)

            // If this is a single statement, it is translated by the function for that.
            // If the node is an expression, this expression is created and wired up and assigned to a hidden variable,
            // used if this region is further used in an assignment, for example as in ternary conditional expressions.
            if (node instanceof IASTStatement) {
                buildStatement(node, currentState, newRegion)
            } else if (node instanceof IASTExpression) {
                val output = createKExpression(node, currentState, newRegion)

                // use this as the output of the state, declare that and add an equation to it.
                val hiddenDecl = createVariableDeclaration

                if (output.isBooleanOperatorExpression) {
                    hiddenDecl.type = ValueType::BOOL
                } else {
                    hiddenDecl.type = ValueType::INT
                }
                hiddenDecl.output = true
                currentState.declarations += hiddenDecl

                // Set hidden var name
                var varName = hiddenVariableName
                val varList = <ValuedObject>newArrayList

                // Create valued object
                val hiddenVO = hiddenDecl.createValuedObject(varName + outSuffix)
                hiddenVO.label = varName

                // Attach valued object to the listing
                varList.add(hiddenVO)

                getStateVariables(currentState).put(varName, varList)
                if (!serializable) {
                    hiddenVO.insertHighlightAnnotations(node)
                }
                newRegion.equations += createDataflowAssignment(hiddenVO, output)
            }

            linkOutputs(currentState, newRegion)
        }

        linkReturn(currentState, parentState, newRegion, parentRegion, parentReference)
        return newRegion
    }

    /**
     * Links the return output of the {@code currentRegion} to its parent.
     * 
     * @param currentState The state containing the current region.
     * @param parentState The state containing the current state.
     * @param currentRegion The region that may define a return output that needs to be linked.
     * @param parentRegion The parent region, where the new assignment is put.
     * @param parentReference The reference object used for the parent state in the parent region.
     */
    def void linkReturn(State currentState, State parentState, DataflowRegion currentRegion,
        DataflowRegion parentRegion, ValuedObject parentReference) {
        val returnDeclaration = currentState.declarations.findFirst [
            it.valuedObjects.findFirst[it.name.startsWith(returnObjectName)] !== null
        ]
        if (returnDeclaration !== null) {
            val returnedVO = returnDeclaration.valuedObjects.last
            returnedVO.label = returnObjectName
            // Look if there already is a valued object for the return statements of the parent state.
            val vos = getStateVariables(currentState)
            var returnObjects = vos.get(returnObjectName)
            if (returnObjects === null) {
                returnObjects = newArrayList

                // Create a declaration with the same type as the return function.
                val returnDecl = createVariableDeclaration
                returnDecl.type = currentReturnType
                returnDecl.output = true
                parentState.declarations += returnDecl

                val returnVO = returnDecl.createValuedObject(returnObjectName + outSuffix)
                returnVO.label = returnObjectName
                returnObjects += returnVO
                vos.put(returnObjectName, returnObjects)
            }

            val retVO = parentState.findValuedObjectByName(returnObjectName, true, parentRegion)

            // res_i = parentState.res_out
            val source = parentReference.reference => [
                subReference = returnedVO.reference
            ]
            parentRegion.equations += createDataflowAssignment(retVO, source)
        }
    }

    /**
     * Extract the body of a switch statement
     */
    def void buildSwitch(IASTSwitchStatement swStmt, State rootState, DataflowRegion dRegion) {
        // Create the State representing the switch
        val swState = createState(switchName + ssaNameSeperator + swCounter)
        swState.annotations += createTagAnnotation("Hide")
        if (serializable) {
            rootSCChart.rootStates += swState
        }

        // Create the reference for the containing dataflow region
        val refDecl = createReferenceDeclaration
        dRegion.declarations += refDecl
        refDecl.setReference(swState)
        val swObj = refDecl.createValuedObject(switchName + ssaNameSeperator + swCounter)
        if (!serializable) {
            swObj.insertHighlightAnnotations(swStmt)
        }
        swCounter++

        // Set the In and Outputs
        setInputs(swStmt, rootState, swState, dRegion, swObj)
        setOutputs(swStmt, rootState, swState, dRegion, swObj)

        // Create the controlflow region
        val cRegion = swState.createControlflowRegion("")
        if (!serializable) {
            cRegion.insertHighlightAnnotations(swStmt.getBody)
        }

        // Create the controller expression label
        swStmt.getControllerExpression.createKExpression(swState, dRegion)
        val controller = exprToString(swStmt.getControllerExpression, sourceFile)
        cRegion.label = "switch (" + controller + ")"

        // Create the initial state
        val initState = cRegion.createState("init")
        initState.label = ""
        if (!serializable) {
            initState.insertHighlightAnnotations(swStmt.getBody)
        }
        initState.initial = true

        // Translate the switch body
        val swBody = swStmt.getBody as IASTCompoundStatement
        var State noBreakCase
        var State activeCase
        var DataflowRegion activeDRegion
        for (child : swBody.children) {

            // Create a new state for case or default statements
            if (child instanceof IASTCaseStatement || child instanceof IASTDefaultStatement) {
                // Save the previous case state
                noBreakCase = activeCase
                if (activeCase !== null) {
                    linkOutputs(swState, activeDRegion)
                }
                // Create the state
                activeCase = cRegion.createState(ssaNameSeperator + caseName + caseCounter++)
                activeCase.label = ""
                if (!serializable) {
                    activeCase.insertHighlightAnnotations(child)
                }

                setInputs(swBody, rootState, activeCase, dRegion, null, false)
                setOutputs(swBody, rootState, activeCase, dRegion, null, false)
                // Create its dataflow region
                activeDRegion = createDataflowRegion("")
                activeCase.regions += activeDRegion
                if (!serializable) {
                    activeDRegion.insertHighlightAnnotations(child)
                }

                // Create the transition from the previous case state to the new if needed
                if (noBreakCase !== null) {
                    noBreakCase.createImmediateTransitionTo(activeCase)
                    noBreakCase = null
                }

                // Create a triggered transition for a case state 
                if (child instanceof IASTCaseStatement) {
                    val caseTransition = initState.createImmediateTransitionTo(activeCase)
                    caseTransition.trigger = child.getExpression.createKExpression(swState, dRegion)
                    activeCase.label = controller + " == " + exprToString(child.getExpression, sourceFile)
                // Create a transition without a trigger for a default statement    
                } else {
                    initState.createImmediateTransitionTo(activeCase)
                    activeCase.label = defaultCaseName
                }
            // Create a transition to the final state for break statements    
            } else if (child instanceof IASTBreakStatement) {
                assignOutputs(swState, swObj, rootState, dRegion)
                activeCase = null
            // Translate other statements into the currently active dataflow region    
            } else {
                buildStatement(child as IASTStatement, activeCase, activeDRegion)
            }

        }
        // If the last statement is missing a break, link inputs anyway.
        if (!(swBody.children.last instanceof IASTBreakStatement)) {
            assignOutputs(swState, swObj, rootState, dRegion)
        }
        linkReturn(activeCase, rootState, activeDRegion, dRegion, swObj)
        assignOutputs(swState, swObj, rootState, dRegion)
    }

    /**
     * Translate a For Statement
     */
    def void buildFor(IASTForStatement forStmt, State rootState, DataflowRegion dRegion) {
        // Create the state
        val forState = createState(forName + ssaNameSeperator + forCounter)
        forState.annotations += createTagAnnotation("Hide")
        if (serializable) {
            rootSCChart.rootStates += forState
        }

        // Reference the state in the containing dataflow region
        val refDecl = createReferenceDeclaration
        dRegion.declarations += refDecl
        refDecl.setReference(forState)
        val forObj = refDecl.createValuedObject(forName + ssaNameSeperator + forCounter)
        if (!serializable) {
            forObj.insertHighlightAnnotations(forStmt)
        }

        // Set the in and outputs
        setInputs(forStmt, rootState, forState, dRegion, forObj)
        setOutputs(forStmt, rootState, forState, dRegion, forObj)

        // Set the loop conditions as label
        val condExpr = exprToString(forStmt.getConditionExpression, sourceFile)
        var initExpr = stmtToString(forStmt.getInitializerStatement, sourceFile)
        if (initExpr.endsWith(";")) {
            initExpr = initExpr.substring(0, initExpr.length - 1)
        }
        val itExpr = exprToString(forStmt.getIterationExpression, sourceFile)
        forState.label = "for (" + initExpr + "; " + condExpr + "; " + itExpr + ")"
        forCounter++

        // Translate the body
        val forDBodyRegion = createDFRegionForNode(forStmt.getBody, forState, rootState, dRegion, forObj,
            forStmt.getInitializerStatement)
        forState.regions += forDBodyRegion
        forDBodyRegion.label = forState.label
    }

    /**
     * Translate a while statement
     */
    def buildWhile(IASTWhileStatement whileStmt, State rootState, DataflowRegion dRegion) {
        // Create the state        
        val whileState = createState(whileName + ssaNameSeperator + whileCounter)
        whileState.annotations += createTagAnnotation("Hide")
        hierarchy.put(whileState, dRegion)
        if (serializable) {
            rootSCChart.rootStates += whileState
        }

        // Create the reference in the containing dataflow region
        val refDecl = createReferenceDeclaration
        dRegion.declarations += refDecl
        refDecl.setReference(whileState)
        val whileObj = refDecl.createValuedObject(whileName + ssaNameSeperator + whileCounter)
        if (!serializable) {
            whileObj.insertHighlightAnnotations(whileStmt)
        }

        // Set the in and outputs
        setInputs(whileStmt, rootState, whileState, dRegion, whileObj)
        setOutputs(whileStmt, rootState, whileState, dRegion, whileObj)

        // Create the condition expression label
        createKExpression(whileStmt.getCondition, whileState, dRegion)
        whileState.label = "while (" + exprToString(whileStmt.getCondition, sourceFile) + ")"
        whileCounter++

        // Translate the loop body
        val DataflowRegion whileDBodyRegion = createDFRegionForNode(whileStmt.getBody, whileState, rootState, dRegion,
            whileObj)
        whileState.regions += whileDBodyRegion
        whileDBodyRegion.label = whileState.label

    }

    /**
     * Builds a state with an accompanying DataflowRegion for a while statement. 
     * This variant also synthesizes the condition expression of the while statement as dataflow.
     * The body and the condition header receive their own state in a parent whileState.
     * 
     * @param whileStmt While statement from the AST of the program
     * @param rootState rootState in which the whileState should be embedded
     * @param dRegion DataflowRegion of the rootState
     */
    def ValuedObjectReference buildWhileDF(IASTWhileStatement whileStmt, State rootState, DataflowRegion dRegion) {
        val localWhileCounter = whileCounter
        whileCounter++

        // Create the state to represent the while statement.
        val whileState = createState(whileName + ssaNameSeperator + localWhileCounter)
        whileState.annotations += createTagAnnotation("Hide")
        hierarchy.put(whileState, dRegion)
        
        if (serializable) {
            rootSCChart.rootStates += whileState
        }

        // Create a reference for this while state in the containing dataflow-region.
        val refDecl = createReferenceDeclaration
        dRegion.declarations += refDecl
        refDecl.setReference(whileState)
        val whileObj = refDecl.createValuedObject(whileName + ssaNameSeperator + localWhileCounter)
        if (!serializable) {
            whileObj.insertHighlightAnnotations(whileStmt)
        }
        
        hierarchy.put(whileState, dRegion)
        stateObjects.put(whileState, whileObj)

        // Set the in and outputs of the state
        setInputs(whileStmt, rootState, whileState, dRegion, whileObj)
        setOutputs(whileStmt, rootState, whileState, dRegion, whileObj)

        // Create the dataflow region for the while state
        val whileRegion = whileState.createDataflowRegion("")
        if (!serializable) {
            whileRegion.insertHighlightAnnotations(whileStmt)
        }
        whileRegion.label = whileName + ssaNameSeperator + localWhileCounter

        // Create the cond state, region, and object
        val condRes = createCondDF(localWhileCounter, whileStmt, whileState, whileRegion)
        val condRegion = condRes.key
        val condState = condRes.value.key
        val condObj = condRes.value.value
        
        hierarchy.put(condState, whileRegion)
        stateObjects.put(condState, condObj)

        // Create the body state and according reference into the while dataflow region.
        val bodyState = createState(whileName + ssaNameSeperator + localWhileCounter + whileBodyName)
        bodyState.annotations += createTagAnnotation("Hide")
        hierarchy.put(bodyState, whileRegion)
        
        if (serializable) {
            rootSCChart.rootStates += bodyState
        }
        val bodyRefDecl = createReferenceDeclaration
        whileRegion.declarations += bodyRefDecl
        bodyRefDecl.setReference(bodyState)
        val bodyObj = bodyRefDecl.createValuedObject(whileName + ssaNameSeperator + localWhileCounter + whileBodyName)
        if (!serializable) {
            bodyObj.insertHighlightAnnotations(whileStmt.getBody)
        }

        // Set the inputs of the state
        setInputs(whileStmt.getBody, whileState, bodyState, whileRegion, bodyObj)
        // Set the outputs of the body state to refer to, but do not add the equations for them yet, as we want to
        // route them into a conditional.
        setOutputs(whileStmt.getBody, whileState, bodyState, whileRegion, bodyObj, false)
        
        hierarchy.put(bodyState, whileRegion)
        stateObjects.put(bodyState, bodyObj)

        // Create the region for the body part
        val bodyRegion = createDFRegionForNode(whileStmt.getBody, bodyState, whileState, whileRegion, bodyObj)
        bodyState.regions += bodyRegion
        bodyRegion.label = whileName + ssaNameSeperator + localWhileCounter + whileBodyName

        // inlining the output of the condition
        var ValuedObject condOutputVo = findCondOutputVo(condRegion, condState, condObj, whileRegion)

        var positiveOutputs = findOutputs(whileStmt.getBody, whileState, false)
        if (getStateVariables(bodyState).containsKey(returnObjectName)) {
            positiveOutputs += returnObjectName
        }

        // For each output, find the output VO and collect them
        var multInputs = new ArrayList
        var multOutputs = new ArrayList
        for (output : positiveOutputs) {
            // Find the output VO for the body state
            var ValuedObject innerOutputVo = bodyState.declarations.filter(VariableDeclaration).filter[it.isOutput].
                flatMap [
                    it.valuedObjects
                ].filter[it.label == output].head
            
            // create corresponding output vo with "state_[varName]" as label
            var ValuedObject positiveOutputVo
            if (innerOutputVo !== null) {
                positiveOutputVo = createOutputVo(whileBodyName, localWhileCounter, whileState, bodyObj, whileRegion,
                    innerOutputVo)
            }

            // If the variable is not written to in the body, use the input VO as the output. 
            val whileOutputVo = whileState.declarations.filter(VariableDeclaration).filter[it.isInput].flatMap [
                it.valuedObjects
            ].filter[it.label == output].head
            if (positiveOutputVo === null) {
                positiveOutputVo = whileOutputVo
            }

            // output VO for negative case
            var ValuedObject negativeOutputVo = whileOutputVo
            
            // find corresponding output vo of the while state
            val outputVo = whileState.findOutputVar(output)

            if (positiveOutputVo !== null) {
                positiveOutputVo.addTagAnnotation(posTag)
                multInputs.add(positiveOutputVo)
            }
            if (negativeOutputVo !== null) {
                negativeOutputVo.addTagAnnotation(negTag)
                multInputs.add(negativeOutputVo)
            }
            multOutputs.add(outputVo)
        }
        
        // add a big multiplexer
        createMultiplexer(whileRegion, condOutputVo, multInputs, multOutputs);

        linkReturn(whileState, rootState, whileRegion, dRegion, whileObj)
        assignOutputs(whileState, whileObj, rootState, dRegion)

        // If there is some assignment to the hidden variable from a conditional expression, use it as the sub-reference
        val subReference = (getStateVariables(whileState) + getStateVariables(bodyState) +
            getStateVariables(condState)).filter [ key, value |
            key.equals(hiddenVariableName)
        ].values.head?.last?.reference
        return whileObj.reference => [
            it.subReference = subReference
        ]
    }

    /**
     * Adds a Condition State with an accompanying DataflowRegion to the surrounding parentRegion.
     * The content of the DataflowRegion is determined by the passed statement.
     * 
     * @param localCounter Counter of the surrounding statement type
     * @param stmt Statement including the condition expression
     * @param parentState Surrounding State that is meant to get an inner condition state
     * @param parentRegion Surrounding DFRegion of the parentState - The condition state is added to this DFRegion
     * @return The DataflowRegion, State and ValuedObject of the condition
     */
    private def Pair<DataflowRegion, Pair<State, ValuedObject>> createCondDF(int localCounter, IASTStatement stmt,
        State parentState, DataflowRegion parentRegion) {

        var condName = ""
        var IASTExpression condExpr = null
        switch stmt {
            case stmt instanceof IASTWhileStatement: {
                condName = whileName + ssaNameSeperator + localCounter + whileCondName
                condExpr = (stmt as IASTWhileStatement).getCondition

            }
            case stmt instanceof IASTIfStatement: {
                condName = ifName + ssaNameSeperator + localCounter + ifCondName
                condExpr = (stmt as IASTIfStatement).conditionExpression
            }
            default:
                throw new IllegalArgumentException("The type of IASTStatement is not supported in addCondDF.")
        }
        // Create the cond state
        val condState = createState(condName)
        condState.annotations += createTagAnnotation("Hide")
        if (serializable) {
            rootSCChart.rootStates += condState
        }
        val condRefDecl = createReferenceDeclaration
        parentRegion.declarations += condRefDecl
        condRefDecl.setReference(condState)
        val condObj = condRefDecl.createValuedObject(condName)
        if (!serializable) {
            condObj.insertHighlightAnnotations(condExpr)
        }

        // inputs & outputs
        setInputs(condExpr, parentState, condState, parentRegion, condObj)
        setOutputs(condExpr, parentState, condState, parentRegion, condObj, false)

        // Create the region for the condition part
        val condRegion = createDFRegionForNode(condExpr, condState, parentState, parentRegion, condObj)
        condState.regions += condRegion
        condRegion.label = condName

        return condRegion -> (condState -> condObj)
    }

    /**
     * Inlines the output of a condition in the DataflowRegion of a condition and returns the OutputVo of the condition.
     * This is useful for example when connecting the condition to a multiplexer.
     * 
     * @param condRegion DataflowRegion of the condition 
     * @param condState State that includes the DataflowRegion condRegion
     * @param condObj ReferenceDeclaration of the condition
     * @param parentRegion Region that includes the condState
     * @return the found OutputValuedObject of the condition
     */
    private def ValuedObject findCondOutputVo(DataflowRegion condRegion, State condState, ValuedObject condObj,
        DataflowRegion parentRegion) {

        val condOutput = condRegion.equations.get(0).reference.valuedObject
        var ValuedObject condOutputVo
        if (condOutput !== null) {
            // Assign the cond output to a new variable in the while region
            // For that, create a new variable declaration
            val variableDeclaration = createVariableDeclaration
            variableDeclaration.type = condOutput.type
            parentRegion.declarations += variableDeclaration
            // Retrieve the state's variable map
            var Map<String, List<ValuedObject>> stateVariables = getStateVariables(condState)
            val varName = conditionalResultName
            val varList = <ValuedObject>newArrayList
            // Reuse the vo variable, now pointing to a new VO in the while state
            condOutputVo = variableDeclaration.createValuedObject(varName)
            condOutputVo.label = varName
            varList.add(condOutputVo)
            stateVariables.put(varName, varList)
            parentRegion.declarations += variableDeclaration
            variableDeclaration.annotations += createTagAnnotation("Hide")
            val innerNegativeOutputVo_ = condOutput

            val source = condObj.reference => [
                subReference = innerNegativeOutputVo_.reference
            ]
            parentRegion.equations += createDataflowAssignment(condOutputVo, source)
        }
        return condOutputVo
    }

    /**
     * Assigns the given vo {@code output} to a new "state_[varName]" variable in {@code region}.
     * 
     * @param name Name of the {@code state}, used for the variable name. If null only the label of {@code output} is 
     *          used
     * @param counter The counter of {@code state}
     * @param state The state the created variable should be in
     * @param obj The object of the state the vo {@code output} is in
     * @param region The region of {@code state}
     * @param output The vo for which the new vo should be created
     *
     */
    private def createOutputVo(String name, int counter, State state, ValuedObject obj, DataflowRegion region,
        ValuedObject output) {
        var ValuedObject outputVo
        // Assign the state output to a new "state_[varName]" variable in the region
        // For that, create a new variable declaration
        val variableDeclaration = createVariableDeclaration
        variableDeclaration.type = output.type
        region.declarations += variableDeclaration
        // Retrieve the state's variable map
        val Map<String, List<ValuedObject>> stateVariables = getStateVariables(state)
        val varName = name !== null ? name + ssaNameSeperator + counter + output.label : output.label
        // Reuse the vo variable, now pointing to a new VO in the state
        outputVo = variableDeclaration.createValuedObject(varName)
        outputVo.label = varName
        region.declarations += variableDeclaration
        variableDeclaration.annotations += createTagAnnotation("Hide")
        val innerPositiveOutputVo_ = output
        // This assignment is of the form 'varName = obj.varName'
        val source = obj.reference => [
            subReference = innerPositiveOutputVo_.reference
        ]
        region.equations += createDataflowAssignment(outputVo, source)
        
        // Add the new create valued object to the ssa list and valued object list
        if (stateVariables.containsKey(output.label)) {
            var varList = stateVariables.get(output.label)
            varList.add(outputVo)
        } else {
            var varList = <ValuedObject>newArrayList
            varList.add(outputVo)
            stateVariables.put(output.label, varList)
        }

        return outputVo
    }

    /**
     * Creates a multiplexer state based on an referenced SCCharts state that merges the outputs of (two) inner states
     * based on a condition. The edge to the conditional input is laid out at the south of the multiplexer.
     * 
     * @param parentRegion Region of the parent state that includes the Multiplexer
     * @param condOutputVo ValuedObject of the output representing the condition "choosing" the input branch 
     * @param multInputs Inputs of the multiplexer
     * @param multOutputs Outputs of the multiplexer
     * 
     */
    private def createMultiplexer(DataflowRegion parentRegion, ValuedObject condOutputVo, 
        ArrayList<ValuedObject> multInputs, ArrayList<ValuedObject> multOutputs
    ) {
        // Create the multiplexer state and according reference into the while dataflow region.
        val multState = createState(multiplexerName + ssaNameSeperator + multiplexerCounter)
        multState.addStringAnnotation("figure", "BigMult.kgt")
        multState.annotations += createTagAnnotation("Hide")
        if (serializable) {
            rootSCChart.rootStates += multState
        }
        val multRefDecl = createReferenceDeclaration
        parentRegion.declarations += multRefDecl
        multRefDecl.setReference(multState)
        val multObj = multRefDecl.createValuedObject(multiplexerName + ssaNameSeperator + multiplexerCounter)
        multObj.label = ""

        // set in and outputs opf the multiplexer
        setMultInput(multInputs, multState, parentRegion, multObj, false)
        setMultInput(#[condOutputVo], multState, parentRegion, multObj, true)
        setMultOutputs(multOutputs, multState, parentRegion, multObj)

        // Create the region for the body part
        val multRegion = multState.createDataflowRegion(multiplexerName + ssaNameSeperator + multiplexerCounter)
        multState.regions += multRegion

        // Add tag annotation so that the multiplexer is identifiable in EquationSynthesis in a clean way
        multObj.addTagAnnotation(multiplexerTag)
        multiplexerCounter++
    }

    /**
     * Sets the output of a custom multiplexer state. 
     * 
     * @param outputs The outs as ValuedObjects that leave  the multiplexer
     * @param newState The state of the multiplexer.
     * @param dRegion The parent DataflowRegion including the multiplexer's state.
     * @param refObj the ReferenceDeclaration of the multiplexer
     */
    private def setMultOutputs(ArrayList<ValuedObject> outputs, State newState, DataflowRegion dRegion, 
        ValuedObject refObj
    ) {
        // Retrieve the state's valued object map
        val Map<String, List<ValuedObject>> stateVariables = getStateVariables(newState)
        for (outputVO : outputs) {
            // create output vo
            val innerOutputVO = createVar(outputVO, newState, stateVariables, outSuffix, "")
            // Create the assignment
            val target = outputVO
            if (refObj === null) {
                dRegion.equations += createDataflowAssignment(target, innerOutputVO.reference)
            } else {
                val source = refObj.reference => [
                    subReference = innerOutputVO.reference
                ]
                dRegion.equations += createDataflowAssignment(target, source)
            }
        }
    }

    /**
     * Sets the inputs of a custom multiplexer state. 
     * 
     * @param inputs The inputs as ValuedObjects that are led into the multiplexer
     * @param newState The state of the multiplexer.
     * @param dRegion The parent DataflowRegion including the multiplexer's state.
     * @param refObj the ReferenceDeclaration of the multiplexer
     * @param cond Signifies whether the input should be interpreted as the condition
     */
    private def setMultInput(List<ValuedObject> inputs, State newState, DataflowRegion dRegion, ValuedObject refObj,
        boolean cond) {
        // Retrieve the state's valued object map
        val Map<String, List<ValuedObject>> stateVariables = getStateVariables(newState)
        for (inputVO : inputs) {
            // label should not be null
            val label = inputVO.label !== null ? inputVO.label : inputVO.name.split(ssaNameSeperator).get(0)
            inputVO.label = label
            
            // create input vo
            val innerInputVO = createVar(inputVO, newState, stateVariables, inSuffix, "")

            // Create the assignment
            if (refObj === null) {
                val eq = createDataflowAssignment(innerInputVO, inputVO.reference)
                if (cond) {
                    eq.addStringAnnotation("toPort", "in1")
                }
                dRegion.equations += eq
            } else {
                val eq = createDataflowAssignment(refObj, innerInputVO, inputVO.reference)
                if (cond) {
                    eq.addStringAnnotation("toPort", "in1")
                }
                dRegion.equations += eq
            }
        }
    }
       
    /** Creates in the given {@code state} an output variable for {@code vo} and updates {@code stateVars}.
     * 
     * @param vo The valued object for which an var should be created
     * @param state The state that should contain the output 
     * @param stateVars Variables of {@code state}
     * @param suffix Suffix for the variable label
     */
    private def createVar(ValuedObject vo, State state, Map<String, List<ValuedObject>> stateVars, String suffix, String l) {
        val decl = createVariableDeclaration
        decl.type = vo.type
        
        switch (suffix) {
            case outSuffix: decl.output = true
            case inSuffix: decl.input = true
        }

        var label = ""
        if (!l.equals("")) {
            label = l + ssaNameSeperator
        }
        label = vo.label !== null ? label + vo.label : label + vo.name.split(ssaNameSeperator).get(0)
        state.declarations += decl
        val varVO = decl.createValuedObject(label + suffix)
        varVO.label = label
        
        if (!vo.annotations.empty) {
            varVO.addTagAnnotation((vo.annotations.get(0) as TagAnnotation).name)
        }

        // Add the new create valued object to the ssa list and valued object list
        if (stateVars.containsKey(label)) {
            var varList = stateVars.get(label)
            varList.add(varVO)
        } else {
            var varList = <ValuedObject>newArrayList
            varList.add(varVO)
            stateVars.put(label, varList)
        }
        return varVO
    }
   
    /**
     * Translates a break or continue statement depending on the passed statement.  
     *
     * @param passedStmt Determines whether a break or continue stmt should be translated
     * 
     */
    def void buildBreakOrContinue(IASTStatement passedStmt, State parentState, DataflowRegion parentRegion){
        var localName = ""
        var anno = ""
        var localCounter = 0
        
        // Change stmt type dependant variables
        switch(passedStmt){
            IASTBreakStatement:{
                localName = breakName
                anno = DataflowExtractor.breakContinueAnno
                localCounter = breakCounter
                breakCounter++
            }
            IASTContinueStatement:{
                localName = continueName
                anno = DataflowExtractor.breakContinueAnno
                localCounter = continueCounter
                continueCounter++
            }
        }
        
        // Create the state
        val newState = createState(localName + ssaNameSeperator + localCounter)
        newState.annotations += createTagAnnotation("Hide")
        newState.annotations += createStringAnnotation(anno, "" + (ifCounter-1))
        newState.addStringAnnotation("figure", "BigMult.kgt")
        if (serializable) {
            rootSCChart.rootStates += newState
        }

        // determine the current while state
        var State whileState = null
        for (state : rootSCChart.rootStates) {
            if (state.name.contains(whileName + ssaNameSeperator + (whileCounter - 1))) {
                whileState = state
            }
        }
        
        // Move upwards in the AST until finding the parent while statement
        var stmt = passedStmt as IASTNode
        while (!(stmt instanceof IASTWhileStatement)) {
            stmt = stmt.parent
        }
        var whileStmt = stmt as IASTWhileStatement

        // create ref decl and valued  object
        val newRefDecl = createReferenceDeclaration
        lastWhileRegion.declarations += newRefDecl
        newRefDecl.setReference(newState)
        val newObj = newRefDecl.createValuedObject(localName + ssaNameSeperator + localCounter)
        newObj.annotations += createTagAnnotation(multiplexerTag)
        newObj.label = ""

        // Create the region for the body part
        val newRegion = newState.createDataflowRegion("")
        newState.regions += newRegion
        newRegion.label = localName + ssaNameSeperator + localCounter
        
        // determine the variables which values depend on the break stmt
        var breakDependableVars = findBreakOutputs(whileStmt.getBody as IASTCompoundStatement,
        passedStmt.parent.parent as IASTIfStatement, whileState)  
        
        // search for the latest valued object for each of the breakDependableVar
        val List<ValuedObject> vars = new ArrayList
        for (depVar : breakDependableVars) {
            var ValuedObject vo = null
            var region = parentRegion
            var state = parentState
            // input does not count as the latest vo unless it is in the surrounding while state
            while (vo === null || (vo.name.contains(inSuffix) && 
                !state.name.startsWith(whileName + ssaNameSeperator)
            )) {
                region = hierarchy.get(state)
                val name = region.label !== null && !region.label.equals("") ? region.label : 
                    region.name.split("-").get(1)
                state = rootSCChart.rootStates.filter[s | s.name.equals(name)].head
                vo = findValuedObjectByName(state, depVar, false, region)
            }
            
            // if the latest vo is in the while body, the correct instance of the vo must be taken
            // (otherwise the output of an if-state, that changes the variable after the break-stmt, is taken)
            if (state.name.startsWith(whileName + ssaNameSeperator)) {
                val varList = getStateVariables(state).get(depVar)
                if (varList !== null && varList.length >= 3) {
                    vo = varList.get(varList.length - 3)
                }
            }
            
            vars.add(vo)
        }
        
        // if the vos are not in the surrounding while state (in which the break state is) they must be passed to it
        val List<ValuedObject> inputs = new ArrayList
        for (v : vars) {
            var vo = v
            val cont = v.eContainer.eContainer
            if (breakContinueVars.containsKey(v)) {
                // the while state already contains a vo for this var
                vo = breakContinueVars.get(v)
            } else if (cont instanceof DataflowRegion && 
                !(cont as DataflowRegion).name.contains(whileName + ssaNameSeperator)
            ){
                // create output variable for the region
                var pR = cont as DataflowRegion
                val regName = pR.label !== null && !pR.label.equals("") ? pR.label : pR.name.split("-").get(1)
                var pS = rootSCChart.rootStates.filter[s | s.name.equals(regName)].head
                vo = createVar(v, pS, getStateVariables(pS), outSuffix, localName + "Var" + ssaNameSeperator 
                    + localCounter
                )
                var obj = stateObjects.get(pS)
                
                // Create the assignment
                pR.equations += createDataflowAssignment(vo, v.reference)
                
                // update parents
                pR = hierarchy.get(vo.eContainer.eContainer as State)
                
                val name = pR.label !== null && !pR.label.equals("") ? pR.label : pR.name.split("-").get(1)
                pS = rootSCChart.rootStates.filter[s|s.name.equals(name)].head

                vo = createOutputVo(null, localCounter, pS, obj, pR, vo)

                // repeat until while state is reached
                while (!pS.name.startsWith(whileName + ssaNameSeperator)) {
                    val outputVar = createVar(vo, pS, getStateVariables(pS), outSuffix, "")
                    pR.equations += createDataflowAssignment(outputVar, vo.reference)

                    pR = hierarchy.get(outputVar.eContainer.eContainer as State)
                    val rN = pR.label !== null && !pR.label.equals("") ? pR.label : pR.name.split("-").get(1)

                    obj = stateObjects.get(pS)
                    pS = rootSCChart.rootStates.filter[s|s.name.equals(rN)].head
                    vo = createOutputVo(null, localCounter, pS, obj, pR, outputVar)
                }
            }
            breakContinueVars.put(v, vo)
            inputs.add(vo)
        }
        
        // set tag annotation and input
        inputs.forEach[v | v.addTagAnnotation(posTag)]
        setMultInput(inputs, newState, lastWhileRegion, newObj, false)
        
        // adjust name and label of the state variables
        val name = localName
        getStateVariables(newState).forEach[p1, p2| p2.get(0).name = name + ssaNameSeperator + p2.get(0).name;
                                      p2.get(0).label = name + ssaNameSeperator + p2.get(0).label
        ]
        
        hierarchy.put(newState, lastWhileRegion)
        stateObjects.put(newState, newObj)
    }

   /**
     * Finalizes already created break/continue states in a specific while.
     * This means for example that it adds inputs and outputs to them.
     * 
     *   @param stmt compound statement/do-block of the while 
     *   @param whileState The state of the while
     *   @param whileRegion The while state's dataflow region
     */
    private def finalizeBreakAndContinueStates(IASTCompoundStatement stmt, State whileState, 
        DataflowRegion whileRegion
    ) {
        // collect all break and continue states in the while loop
        val List<State> breakStates = collectStates(breakName, whileRegion)
        val List<State> continueStates = collectStates(continueName, whileRegion)
        
        // finalize every break and continue state
        finalize(breakStates, breakName, whileState, whileRegion)
        finalize(continueStates, continueName, whileState, whileRegion)
    }
    
    /**
     * Collects all states from a DataflowRegion that have a name starting with a specific prefix.
     * @param prefix The prefix that determines which states are meant to be collected.
     * @param dataflowRegion The states are collected from this dataflow region.
     */ 
    private def List<State> collectStates(String prefix, DataflowRegion dataflowRegion){
        var List<State> states = new ArrayList
        for (decl : dataflowRegion.declarations) {
            if (decl instanceof ReferenceDeclaration) {
                val state = decl.reference as State
                if (state.name.startsWith(prefix + ssaNameSeperator)) {
                    states.add(state)
                }
            }
        }
        
         states.sortBy[state | state.name]
         return states
    }
    
     /**
     * Actual work logic of finalizeBreakAndContinue.
     * Finalizes already created break/continue states in a specific while.
     * This means for example that it adds inputs and outputs to them.
     * 
     *   @param stmt compound statement/do-block of the while 
     *   @param whileState The state of the while
     *   @param whileRegion The while state's dataflow region
     */
    private def void finalize(List<State>states, String name, State whileState, DataflowRegion whileRegion){
        for (var i = states.length - 1; i >= 0; i--) {
            val breakState = states.get(i)
            val breakObj = stateObjects.get(breakState)

            // determine the inputs of the break state
            val vars = new ArrayList()
            getStateVariables(breakState).forEach[key, value| val labels = key.split(ssaNameSeperator); 
                vars.add(labels.get(labels.length -1))
            ]
            var breakInputs = new ArrayList()
            for (label : vars) {
                var vo = whileState.findValuedObjectByName(label, false, lastWhileRegion)
                vo.label = label
                vo.addTagAnnotation(negTag)
                breakInputs.add(vo)
            }
    
            // create output vars
            val breakVars = getStateVariables(breakState)
            for (input : breakInputs) {
                val innerOutputVO = createVar(input, breakState, breakVars, outSuffix, "")
                createOutputVo(name, Integer.parseInt(breakState.name.substring(name.length+1)), whileState, 
                    breakObj, whileRegion, innerOutputVO
                )
            }
    
            // determine the if state the break stmt is in
            var State ifState
            val localIfCounter = Integer.parseInt(breakState.getStringAnnotationValue(
                DataflowExtractor.breakContinueAnno
            ))
            for (state : rootSCChart.rootStates) {
                if (state.name.equals(ifName + ssaNameSeperator + localIfCounter)) {
                    ifState = state
                }
            }
            
            // condition is passed to the while region in which the break state is
            // and all conditions on the way are collected
            val conditions = collectAndPassThroughConds(ifState)
            
            var ValuedObject condInput = conditions.get(0)
            
            // if there is more than one condition they are connected with an "AND" operator
            if (conditions.size > 1) {
                // create local var for result auf "AND"
                val label = hiddenVariableName
                val decl = createVariableDeclaration
                whileRegion.declarations += decl
                decl.type = ValueType.BOOL
                decl.annotations += createTagAnnotation("Hide")
                condInput = decl.createValuedObject(label)
                condInput.label = label
    
                // combine the conditions with an "AND" operator
                val expr = createLogicalAndExpression() => [
                            for (cond : conditions) {
                                it.subExpressions += cond.reference
                            }
                        ]
                
                whileRegion.equations += createDataflowAssignment(condInput, expr)
            }
            
            // set the inputs of the break state  
            setMultInput(breakInputs, breakState, whileRegion, breakObj, false)
            setMultInput(#[condInput], breakState, whileRegion, breakObj, true)
        }
    }
    
    /**
     * Collects if-conditions and passes them through to the embedding while state.
     * 
     * @param startState The state in which should be started
     */
    private def collectAndPassThroughConds(State startState) {
        var List<ValuedObject> conditions = new ArrayList
        var parentRegion = startState.regions.get(0) as DataflowRegion
        while (parentRegion !== lastWhileRegion) {
            val parentState = parentRegion.eContainer as State
            
            // checks if another if state is is reached
            if (parentState.name.startsWith(ifName + ssaNameSeperator)) {
                // get the local conditional vo in the if state
                for (decl : parentRegion.declarations) {
                    if (decl instanceof VariableDeclaration) {
                        val vo = decl.valuedObjects.get(0)
                        if (vo.name.equals(conditionalResultName)) {
                            conditions.add(vo)
                        }
                    }
                }
            }
            
            // for each condition an output vo must be created
            val List<ValuedObject> condOutputs = new ArrayList
            for (var j = 0; j < conditions.length; j++) {
                val condOutput = conditions.get(j)
                val label = condOutput.label.startsWith(ifName + ssaNameSeperator) ? 
                    condOutput.label : parentState.name + hiddenVariableName
                val vL = getStateVariables(parentState).get(label)
                
                // if an output already exists for the condition this output can be used, 
                // otherwise a new output must be created
                if (vL !== null) {
                    condOutputs.add(vL.get(vL.length-1))
                } else {                
                    // create condition output in the parent state
                    val outputDecl = createVariableDeclaration
            
                    outputDecl.type = ValueType::BOOL
                    outputDecl.output = true
                    parentState.declarations += outputDecl
                    
                    val varName = label
                    val varList = <ValuedObject>newArrayList
            
                    val outputVO = outputDecl.createValuedObject(varName + outSuffix)
                    outputVO.label = varName
            
                    varList.add(outputVO)
            
                    getStateVariables(parentState).put(varName, varList)
                    parentRegion.equations += createDataflowAssignment(outputVO, condOutput.reference)
                    
                    val parentObj = stateObjects.get(parentState)
                    val pS = parentRegion.eContainer as State
                    
                    
                    // condition vo that can be used by the next state
                    condOutputs.add(createOutputVo(null, 0, pS, parentObj, 
                        hierarchy.get(parentState), outputVO
                    ))
                }
            }
            parentRegion = hierarchy.get(parentState)
            conditions = condOutputs
        }
        return conditions
    }

    /**
     * Translate a Do statement
     */
    def void buildDo(IASTDoStatement doStmt, State rootState, DataflowRegion dRegion) {
        // Create the state        
        val doState = createState(doName + ssaNameSeperator + doCounter)
        doState.annotations += createTagAnnotation("Hide")
        if (serializable) {
            rootSCChart.rootStates += doState
        }

        // Create the reference in the containing dataflow region
        val refDecl = createReferenceDeclaration
        dRegion.declarations += refDecl
        refDecl.setReference(doState)
        val doObj = refDecl.createValuedObject(doName + ssaNameSeperator + doCounter)
        if (!serializable) {
            doObj.insertHighlightAnnotations(doStmt)
        }

        // Set the in and outputs
        setInputs(doStmt, rootState, doState, dRegion, doObj)
        setOutputs(doStmt, rootState, doState, dRegion, doObj)

        // Create the condition expression label
        createKExpression(doStmt.getCondition, doState, dRegion)
        doState.label = "do ... while (" + exprToString(doStmt.getCondition, sourceFile) + ")"
        doCounter++

        // Translate the body
        val doDBodyRegion = createDFRegionForNode(doStmt.getBody, doState, rootState, dRegion, doObj)
        doState.regions += doDBodyRegion
        doDBodyRegion.label = doState.label

    }

    /**
     * From some statement containing additional declarations, only those declarations and no initialization are
     * added to the dataflow region. This way any variables declared in the additional statement are known in the 
     * given region.
     * 
     * @param additionalDeclarations The declaration possibly containing some declarations.
     * @param bodyState The state in which the dataflow region resides.
     * @param dfRegion The dataflow region to add the declarations to.
     */
    def void addAdditionalDeclarations(IASTStatement additionalDeclarations, State bodyState, DataflowRegion dfRegion) {
        if (additionalDeclarations instanceof IASTDeclarationStatement) {
            // Strip the initialization away from a copy of the declaration.  
            val usedDeclarators = additionalDeclarations.copy
            val declaration = (usedDeclarators as IASTDeclarationStatement).declaration
            if (declaration instanceof IASTSimpleDeclaration) {
                declaration.declarators.forEach[initializer = null]
            }
            buildStatement(usedDeclarators, bodyState, dfRegion)
        }
    }

    /**
     * Create a variable declaration
     */
    def VariableDeclaration addDeclaration(IASTSimpleDeclaration declaration, State state, DataflowRegion dRegion) {

        // Create the declaration with the cdt type
        val variableDeclaration = createVariableDeclaration
        variableDeclaration.type = (declaration.getDeclSpecifier as IASTSimpleDeclSpecifier).type.cdtTypeConversion
        if (!serializable) {
            variableDeclaration.insertHighlightAnnotations(declaration)
        }

        // Retrieve the state's variable map
        var Map<String, List<ValuedObject>> stateVariables = getStateVariables(state)

        // Create a valued object for each declared variable 
        val declarators = declaration.getDeclarators
        for (decl : declarators) {
            // Retrieve the variables name
            var varName = decl.getName.toString
            // Create a SSA list for the variable
            var varList = <ValuedObject>newArrayList

            // Create the valued object
            val vo = variableDeclaration.createValuedObject(varName + ssaNameSeperator + "0")
            vo.label = varName
            if (!serializable) {
                vo.insertHighlightAnnotations(decl)
            }
            if (decl instanceof IASTArrayDeclarator) {
                vo.cardinalities += decl.arrayModifiers.map [
                    val expr = it.constantExpression?.createKExpression(state, dRegion)
                    if (expr !== null) {
                        return expr
                    } else {
                        // For declarations such as 'char string[] = "foo"' there is no constant expression,
                        // so just add some cardinality to the array, that SCChart does not really matter about anyways
                        // for the visual representation.
                        return createIntValue(1)
                    }
                ]
            }

            // Add the valued object and the ssa list to the respective elements    
            varList.add(vo)
            stateVariables.put(varName, varList)

            // Add the initialization assignment if needed
            if (decl.getInitializer !== null) {
                vo.initializeValuedObject(decl.getInitializer, state, dRegion)
            }
        }
        return variableDeclaration
    }

    /**
     * Add an initialization assignment to the given valued object
     */
    def void initializeValuedObject(ValuedObject vo, IASTInitializer initializer, State state, DataflowRegion dRegion) {
        if (initializer instanceof IASTEqualsInitializer) {
            var Expression initExpr
            if (initializer.children.head instanceof IASTFunctionCallExpression) {
                val expression = initializer.children.head as IASTFunctionCallExpression
                initExpr = createFunctionCall(expression, state, dRegion)
            } else {
                // Simply translate the expression
                initExpr = createKExpression(initializer.children.head, state, dRegion)
            }
            
            // TODO: pointer WIP
            var  statePointers = getStateVarPointers(state)
            if (initExpr instanceof OperatorExpression && 
                (initExpr as OperatorExpression).operator.literal.equals(addressOp)
            ) {
                val ref = (initExpr as OperatorExpression).subExpressions.get(0) as ValuedObjectReference
                statePointers.put(vo.name.split(ssaNameSeperator).get(0), ref.valuedObject)
            }
            dRegion.equations += createDataflowAssignment(vo, initExpr)
        }
    }

    /**
     * Connect the outputs of a control statement state to the respective valued objects of the containing state
     */
    def void setOutputs(IASTNode stmt, State rootState, State newState, DataflowRegion dRegion, ValuedObject refObj) {
        setOutputs(stmt, rootState, newState, dRegion, refObj, true)
    }

    /**
     * Connect the outputs of a control statement state to the respective valued objects of the containing state
     */
    def void setOutputs(IASTNode stmt, State rootState, State newState, DataflowRegion dRegion, ValuedObject refObj,
        boolean addAssignments) {
        // Retrieve the set of outputs of the control statement
        var outputs = findOutputs(stmt, rootState, false)

        // Retrieve the state valued object map
        var Map<String, List<ValuedObject>> stateVariables = getStateVariables(newState)

        // Create an output declaration for each found output
        for (output : outputs) {
            // Get the respective valued object of the containing state
            val outputVO = rootState.findValuedObjectByName(output, addAssignments, dRegion)
            if (!serializable) {
                outputVO.insertHighlightAnnotations(stmt)
            }
            val outputRootDecl = outputVO.getVariableDeclaration
            val outputType = outputRootDecl.getType

            // Create a declaration and valued object for the output 
            val decl = createVariableDeclaration
            newState.declarations += decl
            decl.type = outputType
            decl.output = true
            val innerOutputVO = decl.createValuedObject(output + outSuffix)
            innerOutputVO.label = output
            if (!serializable) {
                innerOutputVO.insertHighlightAnnotations(stmt)
            }

            // Add the new create valued object to the ssa list and valued object list
            if (stateVariables.containsKey(output)) {
                var varList = stateVariables.get(output)
                varList.add(innerOutputVO)
            } else {
                var varList = <ValuedObject>newArrayList
                varList.add(innerOutputVO)
                stateVariables.put(output, varList)
            }

            if (addAssignments) {
                // Create the assignment
                val target = outputVO
                if (refObj === null) {
                    dRegion.equations += createDataflowAssignment(target, innerOutputVO.reference)
                } else {
                    val source = refObj.reference => [
                        subReference = innerOutputVO.reference
                    ]
                    dRegion.equations += createDataflowAssignment(target, source)
                }
            }
        }

        // If the statement contains a return statement, add a return declaration to the region.
        val isReturn = findReturn(stmt)
        val hasReturn = getStateVariables(newState).containsKey(returnObjectName)
        if (isReturn && !hasReturn) {
            val varList = addReturnDecl(newState)
            if (varList !== null) {
                stateVariables.put(returnObjectName, varList)
            }
        }
    }

    /**
     * Adds a return declaration to the given declaration scope.
     * 
     * @param state The state to add the declaration to.
     * @return A new list with a new valued object for the return value for this declaration.
     */
    def List<ValuedObject> addReturnDecl(State state) {
        // Add a new return declaration and initialize it with the _out valued object.
        if (currentReturnType !== null) {
            // Determine output type
            val retDecl = createVariableDeclaration
            retDecl.type = currentReturnType
            retDecl.output = true
            state.declarations += retDecl

            // Set output name
            var varName = returnObjectName
            val varList = <ValuedObject>newArrayList

            // Create valued object
            val resVO = retDecl.createValuedObject(varName + outSuffix)
            resVO.label = varName

            // Attach valued object to the listing
            varList.add(resVO)
            return varList
        }
    }

    /**
     * Returns true if the statement or any of its children are a return statement, false otherwise.
     * 
     * @param statement The AST node to search the return statement in.
     * @return If a return statement was found.
     */
    def boolean findReturn(IASTNode statement) {
        if (statement instanceof IASTReturnStatement) {
            return true
        } else {
            for (child : statement.children) {
                val childHasReturn = findReturn(child)
                if (childHasReturn) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * Find the outputs from the given control statement.
     * 
     * @param stmt The control statement
     * @param parentState The state this control statement is contained in.
     * @param checkId If IdExpressions should also be considered an output. Should be false for any call from the
     *                outside.
     */
    def HashSet<String> findOutputs(IASTNode stmt, State parentState, boolean checkId) {
        var outputs = <String>newHashSet

        switch (stmt) {
            IASTIdExpression case checkId: {
                val varName = (stmt as IASTIdExpression).getName.toString
                if(getStateVariables(parentState).containsKey(varName)) outputs += varName
            }
            IASTBinaryExpression: {
                // Consider non-local variables that are target of an assignment.
                val operator = stmt.getOperator
                if ((operator >= IASTBinaryExpression.op_assign) &&
                    (operator <= IASTBinaryExpression.op_binaryOrAssign)) {
                    val op1 = stmt.getOperand1
                    if (op1 instanceof IASTIdExpression) {
                        outputs += findOutputs(op1, parentState, true)
                    } else if (op1 instanceof IASTArraySubscriptExpression) {
                        outputs += findOutputs(op1.arrayExpression, parentState, true)
                    }
                    // Also consider the source of the expression, as assignments may be nested in other expressions,
                    // such as in 'var1 = var2 = 0' or 'callFunc(var1 = 0)'.
                    outputs += findOutputs(stmt.operand2, parentState, false)
                }
            }
            IASTUnaryExpression: {
                // Consider non-local
                val operator = stmt.getOperator
                val writingUnaryOps = #[IASTUnaryExpression.op_prefixIncr, IASTUnaryExpression.op_prefixDecr,
                    IASTUnaryExpression.op_postFixIncr, IASTUnaryExpression.op_postFixDecr]
                if (writingUnaryOps.contains(operator)) {
                    val operand = stmt.operand
                    if (operand instanceof IASTIdExpression) {
                        outputs += findOutputs(operand, parentState, true)
                    } else if (operand instanceof IASTArraySubscriptExpression) {
                        outputs += findOutputs(operand.arrayExpression, parentState, true)
                    }
                }

                // Consider pointers to variables
                if (operator === IASTUnaryExpression.op_amper && stmt.operand instanceof IASTIdExpression) {
                    val idExpression = stmt.operand as IASTIdExpression
                    val varName = idExpression.name.toString
                    outputs += varName
                }

            }
            default: {
                // Check every child for other statements.
                for (child : stmt.children) {
                    outputs += findOutputs(child, parentState, false)
                }
            }
        }
        return outputs
    }

    /**
     * Connect the Inputs of the given control statement to their respective valued objects of the containing state
     */
    def void setInputs(IASTNode stmt, State rootState, State newState, DataflowRegion dRegion, ValuedObject refObj) {
        setInputs(stmt, rootState, newState, dRegion, refObj, true)
    }

    /**
     * Connect the Inputs of the given control statement to their respective valued objects of the containing state
     */
    def void setInputs(IASTNode stmt, State rootState, State newState, DataflowRegion dRegion, ValuedObject refObj,
        boolean addAssigments) {
        // Find the needed inputs of the given control statement
        var inputs = findInputs(stmt, rootState)

        // All outputs are also shown as inputs. As no control statement is required to be executed, this allows
        // to be interpreted as "take either the output of this control statement or the previous input value".
        var outputs = findOutputs(stmt, rootState, false)
        inputs.addAll(outputs)

        // Retrieve the state's valued object map
        var Map<String, List<ValuedObject>> stateVariables = getStateVariables(newState)

        for (input : inputs) {
            // Retrieve the respective valued object of the containing state 
            var inputVO = rootState.findValuedObjectByName(input, false, dRegion)
            val inputRootDecl = inputVO.getVariableDeclaration
            val inputType = inputRootDecl.getType

            // Create the input for the control statement state
            val decl = createVariableDeclaration
            newState.declarations += decl
            decl.type = inputType
            decl.input = true
            val innerInputVO = decl.createValuedObject(input + inSuffix)
            innerInputVO.label = input
            if (!serializable) {
                innerInputVO.insertHighlightAnnotations(stmt)
            }

            // Add the new create valued object to the ssa list and valued object list
            if (stateVariables.containsKey(input)) {
                var varList = stateVariables.get(input)
                varList.add(innerInputVO)
            } else {
                var varList = <ValuedObject>newArrayList
                varList.add(innerInputVO)
                stateVariables.put(input, varList)
            }

            if (addAssigments) {
                // Create the assignment
                if (refObj === null) {
                    dRegion.equations += createDataflowAssignment(innerInputVO, inputVO.reference)
                } else {
                    dRegion.equations += createDataflowAssignment(refObj, innerInputVO, inputVO.reference)
                }
            }
        }
    }

    /**
     * Find the Inputs needed for the given control statement
     */
    def HashSet<String> findInputs(IASTNode stmt, State parentState) {
        var inputs = <String>newHashSet
        // Add a found non local variable use
        if (stmt instanceof IASTIdExpression) {
            val varName = stmt.getName.toString
            if(getStateVariables(parentState).containsKey(varName)) inputs += varName
        // Consider only variables that are not target of an assignment    
        } else if (stmt instanceof IASTBinaryExpression) {
            val operator = stmt.getOperator
            if ((operator >= IASTBinaryExpression.op_assign) && (operator <= IASTBinaryExpression.op_binaryOrAssign)) {
                inputs = findInputs(stmt.getOperand2, parentState)
                // If operand1 is an array, we consider it to be "read" as well.
                if (stmt.operand1 instanceof IASTArraySubscriptExpression) {
                    inputs += findInputs(stmt.getOperand1, parentState)
                }
            } else {
                inputs = findInputs(stmt.getOperand1, parentState)
                inputs += findInputs(stmt.getOperand2, parentState)
            }
        // Consider only arguments of a function call    
        } else if (stmt instanceof IASTFunctionCallExpression) {
            val arguments = stmt.getArguments
            for (argument : arguments) {
                inputs += findInputs(argument, parentState)
            }
        // Test all children of other statements    
        } else {
            for (child : stmt.children) {
                inputs += findInputs(child, parentState)
            }
        }
        return inputs
    }

    /**
     * Find the state for the given function identifier.
     * @param identifier The {@code IASTName} object from the AST identifying the function.
     */
    def State findFunctionState(IASTName identifier, int numParams) {
        val mappingIdentifier = CProcessorUtils.nameToIdentifier(identifier, index)
        return functions.get(mappingIdentifier)?.get(numParams)
    }

    /**
     * Find the state for the given function name, or create a new unknown function state and remember it.
     * 
     * @param expression The function call expression to find the state for.
     * @param state The surrounding state that will contain this state, for reference.
     * @param dRegion The surrounding dataflow region that will contain this state, for reference.
     * @return The state for the function.
     */
    def State findFunctionState(IASTFunctionCallExpression expression, State state, DataflowRegion dRegion) {
        val funcName = (expression.getFunctionNameExpression as IASTIdExpression).getName
        val existingFunction = findFunctionState(funcName, expression.arguments.size)
        if (existingFunction === null) {
            return createUnknownFunctionScaffold(funcName, expression, state, dRegion)
        }
        return existingFunction
    }

    /**
     * Find the valued object of the given state by its variables name and creates a new valued object if needed
     */
    def ValuedObject findValuedObjectByName(State state, String name, Expression index, boolean writing,
        Region dRegion) {
        // Retrieve the last valued object of the variables list
        var ValuedObject vo
        val varList = getStateVariables(state).get(name)
        if (varList === null) {
            return null
        }
        vo = varList.get(varList.length - 1)
        var varName = vo.getName()

        // If the last object is the outSuffix object the preceding valued object has to be used
        var onlyOut = false
        var hasOut = false
        if ((varName.contains(outSuffix)) && (varName.lastIndexOf(outSuffix) == (varName.length - outSuffix.length))) {
            hasOut = true
            if (varList.length > 1) {
                vo = varList.get(varList.length - 2)
                varName = vo.getName()

            // If the list only contains the outSuffix valued object, a preceding one has to be created  
            } else {
                val OffsetAnno = vo.getAnnotations("Offset")
                val LengthAnno = vo.getAnnotations("Length")
                onlyOut = true
                var varDecl = vo.getVariableDeclaration
                varName = varName.substring(0, varName.length - outSuffix.length) + ssaNameSeperator + "0"
                val type = varDecl.type

                // If the original variable declaration was attached to the state the new one should be attached to the
                // dataflow region
                if (!dRegion.declarations.contains(varDecl)) {
                    varDecl = createVariableDeclaration
                    varDecl.annotations += createTagAnnotation("Hide")
                    varDecl.type = type
                    dRegion.declarations += varDecl
                }

                // Create the valued object and add it to the variable list, before the outSuffix object
                vo = varDecl.createValuedObject(varName)
                if (!serializable) {
                    if(OffsetAnno.length > 0) vo.annotations += OffsetAnno.head
                    if(LengthAnno.length > 0) vo.annotations += LengthAnno.head
                }
                varList.add(0, vo)
            }
        }

        // For Arrays only create a new VO if is written to for a first time or after a read, or if the specific index
        // has been written to before to still preserve SSA (at least for constant expressions), but connect what
        // belongs together.
        val boolean isArray = index !== null
        var boolean newArrayVONeeded = false
        if (isArray) {
            var alreadyWrittenIdxs = voWrittenIdxs.get(vo)

            if (writing) {
                if (alreadyWrittenIdxs === null || alreadyWrittenIdxs.empty) {
                    // A first write to an array needs a new VO.
                    alreadyWrittenIdxs = newArrayList
                    voWrittenIdxs.put(vo, alreadyWrittenIdxs)
                    newArrayVONeeded = true
                } else if (alreadyWrittenIdxs.exists[it.expressionEquals(index)]) {
                    // Any subsequent write to that array only needs a new VO if it is on a different array index.
                    newArrayVONeeded = true
                } else {
                    alreadyWrittenIdxs.add(index)
                }
            } else if (alreadyWrittenIdxs !== null) {
                // If this is a read to an array that has been written to before, clear the alreadyWrittenIdxs, so that
                // a new VO will be created on the next write to it.
                alreadyWrittenIdxs.clear
            }
        }

        // If the valued object is meant to be written to, a new one has to be created to preserve the SSA-form
        if (writing && !onlyOut && /* isArray => newArrayVONeeded */ (!isArray || newArrayVONeeded)) {
            var varDecl = vo.getVariableDeclaration
            var newName = ""

            // Create the right valued object suffix
            if (!(varName.lastIndexOf(inSuffix) == varName.length - inSuffix.length)) {
                val idxOfUS = varName.lastIndexOf(ssaNameSeperator)
                val varSuffix = varName.substring(idxOfUS + 1)
                val varNmr = Integer.parseInt(varSuffix)
                newName = varName.substring(0, idxOfUS + 1) + (varNmr + 1)
            } else {
                newName = varName.substring(0, varName.length - inSuffix.length) + ssaNameSeperator + "0"
            }

            // Make sure the declaration is attached to the dataflow region
            val type = varDecl.type
            if (!dRegion.declarations.contains(varDecl)) {
                varDecl = createVariableDeclaration
                varDecl.annotations += createTagAnnotation("Hide")
                varDecl.type = type
                dRegion.declarations += varDecl
            }

            // Create the valued object
            vo = varDecl.createValuedObject(newName)
            if (hasOut) {
                varList.add(varList.length - 1, vo)
            } else {
                varList.add(vo)
            }

            // If the vo is for an array, note which index has been used now.
            if (isArray && writing) {
                val writtenIdxs = newArrayList
                writtenIdxs.add(index)
                voWrittenIdxs.put(vo, writtenIdxs)
            }
        }
        return vo
    }

    /**
     * Find the valued object of the given state by its variables name and creates a new valued object if needed
     */
    def ValuedObject findValuedObjectByName(State state, String name, boolean writing, Region dRegion) {
        return findValuedObjectByName(state, name, null, writing, dRegion)
    }

    /**
     * Connects the last valued of the ssa list to the output valued object for each variable of the given state
     */
    def void linkOutputs(State state, DataflowRegion dRegion) {
        var stateVariables = getStateVariables(state)
        for (varList : stateVariables.values) {
            // Retrieve the output valued object
            val outVO = findOutputVar(varList)
            // If an output exists connect it
            if (outVO !== null && (varList.length > 1)) {
                if (varList.length > 2 && varList.head.name.startsWith(returnObjectName)) {
                    // The return value is a special case, every assignment to it comes from a possible return statement
                    // somewhere in the given state. 
                    val allReturns = varList.filter[it !== outVO]

                    // Create a state to combine all returns
                    val returnState = createState(returnCombineName + returnCombineCounter++)
                    returnState.label = returnCombineName
                    returnState.annotations += createTagAnnotation("Hide")
                    if (serializable) {
                        rootSCChart.rootStates += returnState
                    }
                    returnState.createDataflowRegion(returnCombineName) => [
                        label = returnCombineName
                    ]

                    // Create the reference to this state in the containing dataflow region
                    val refDecl = createReferenceDeclaration
                    refDecl.setReference(returnState)
                    dRegion.declarations += refDecl
                    val returnStateObject = refDecl.createValuedObject(returnCombineName)

                    // Set the inputs of the state
                    for (inputVO : allReturns) {
                        val newDecl = createVariableDeclaration
                        newDecl.type = inputVO.variableDeclaration.type
                        newDecl.input = true
                        returnState.declarations += newDecl
                        val innerInputVO = newDecl.createValuedObject(inputVO.name)
                        innerInputVO.label = inputVO.name

                        // Create the assignment
                        // The assignment is of the form 'return_combine.res_n = res_n'
                        dRegion.equations +=
                            createDataflowAssignment(returnStateObject, innerInputVO, inputVO.reference)
                    }

                    // Set the output of the state
                    val outDecl = createVariableDeclaration
                    outDecl.type = outVO.type
                    outDecl.output = true
                    returnState.declarations += outDecl
                    val innerOutputVO = outDecl.createValuedObject(outVO.name)
                    innerOutputVO.label = returnObjectName

                    // Create the assignment
                    // The assignment is of the form 'res = return_combine.res out'
                    val source = returnStateObject.reference => [
                        subReference = innerOutputVO.reference
                    ]
                    dRegion.equations += createDataflowAssignment(outVO, source)
                } else {
                    // Connect the last valued object in the list not being the output variable to the
                    // output suffixed valued object as the last SSA object is the output value for that variable.
                    // Retrieve the last ssa valued object
                    var lastVO = varList.get(varList.length - 1)
                    if (lastVO.getName.contains(outSuffix) && (varList.length > 1)) {
                        lastVO = varList.get(varList.length - 2)
                    }
                    // Copy the location information of the last ssa vo to the output vo
                    if (!serializable && outVO.annotations.length < 2) {
                        outVO.annotations += createStringAnnotation("Offset", lastVO.getStringAnnotationValue("Offset"))
                        outVO.annotations += createStringAnnotation("Length", lastVO.getStringAnnotationValue("Length"))
                    }
                    // Add the linking assignment
                    dRegion.equations += createDataflowAssignment(outVO, lastVO.reference)
                }
            }
        }
    }

    /**
     * Assigns the output valued of the ssa list to the surrounding region for each output of the given state.
     */
    def void assignOutputs(State state, ValuedObject refObject, State rootState, DataflowRegion dRegion) {
        var stateVariables = getStateVariables(state)
        for (varList : stateVariables.values) {
            // Retrieve the output valued object
            val outVO = findOutputVar(varList)
            if (outVO !== null && !varList.head.name.startsWith(returnObjectName)) {
                // All none-return object variables that are output of the state need to be assigned in the outer region.
                // This function call called with true handles that for us.
                val name = outVO.label
                val target = findValuedObjectByName(rootState, name, true, dRegion)
                // newVar = ref.output
                val source = refObject.reference => [
                    subReference = outVO.reference
                ]
                dRegion.equations += createDataflowAssignment(target, source)
            }
        }
    }

    /**
     * Find the output valued object of the given ssa list
     */
    def ValuedObject findOutputVar(List<ValuedObject> varList) {
        var ValuedObject vo = null

        for (var i = 0; (i < varList.length) && (vo === null); i++) {
            var varName = varList.get(i).getName
            if (varName.contains(outSuffix)) {
                vo = varList.get(i)
            }
        }

        return vo
    }

    /**
     * Find the output valued object for the given variable name and the given state
     */
    def ValuedObject findOutputVar(State state, String varName) {
        var ValuedObject vo = null

        var varList = getStateVariables(state).get(varName)
        vo = varList.findOutputVar

        return vo
    }

    /**
     * Returns true if the expressions definitely contain the same value. Currently only considers constant values as
     * the expressions.
     */
    def boolean expressionEquals(Expression expression1, Expression expression2) {
        if (expression1 instanceof BoolValue && expression2 instanceof BoolValue) {
            return (expression1 as BoolValue).value.equals((expression2 as BoolValue).value)
        }
        if (expression1 instanceof FloatValue && expression2 instanceof FloatValue) {
            return (expression1 as FloatValue).value.equals((expression2 as FloatValue).value)
        }
        if (expression1 instanceof IntValue && expression2 instanceof IntValue) {
            return (expression1 as IntValue).value.equals((expression2 as IntValue).value)
        }
        if (expression1 instanceof StringValue && expression2 instanceof StringValue) {
            return (expression1 as StringValue).value.equals((expression2 as StringValue).value)
        }
        // TODO: expressions such as "n" should also be considered equal. Maybe need to consider if the variables have
        // been modified in between.
        return false
    }

    /**
     * Create a list of assignments for the given binary expression.
     */
    def ArrayList<Assignment> createAssignments(IASTBinaryExpression binExpr, State funcState, DataflowRegion dRegion) {
        val operator = binExpr.getOperator
        var assignments = <Assignment>newArrayList
        var Assignment ass = null
        // Only if the binary expression is an assignment some work is needed
        if (operator == IASTBinaryExpression.op_assign) {
            var sourceExpr = binExpr.children.get(1)
            var Expression source = null
            // If source expression is a variable read retrieve the respective VO
            if (sourceExpr instanceof IASTIdExpression) {
                source = funcState.findValuedObjectByName((sourceExpr as IASTIdExpression).getName.toString, false,
                    dRegion).reference
            // Translate the expression that is meant to be assigned
            } else if (sourceExpr instanceof IASTBinaryExpression) {
                source = createKExpression(sourceExpr, funcState, dRegion)
            } else if (sourceExpr instanceof IASTUnaryExpression) {
                source = createKExpression(sourceExpr, funcState, dRegion)
            // Translate a function call that will be assigned
            } else if (sourceExpr instanceof IASTFunctionCallExpression) {
                // Create the function reference
                source = createFunctionCall(sourceExpr, funcState, dRegion)
            // Create a value expression for a literal    
            } else if (sourceExpr instanceof IASTLiteralExpression) {
                source = createValue(sourceExpr)
            } else if (sourceExpr instanceof IASTConditionalExpression) {
                source = createKExpression(sourceExpr, funcState, dRegion)
            } else if (sourceExpr instanceof IASTArraySubscriptExpression) {
                source = createKExpression(sourceExpr, funcState, dRegion)
            }

            // Retrieve the assignment target
            val targetAndIndex = retrieveTargetAndIndexExpr(binExpr.getOperand1, funcState, dRegion)
            
            // TODO: pointer WIP
            var Map<String, ValuedObject> statePointers = getStateVarPointers(funcState)
            // update pointer map if target is a pointer
            if (binExpr.getOperand1 instanceof IASTIdExpression 
                && statePointers.containsKey((binExpr.getOperand1 as IASTIdExpression).getName.toString)
            ) {
                statePointers.put(targetAndIndex.target.name.split(ssaNameSeperator).get(0), null)
            }
            // if source is "&exp" update pointer ref to the corresponding vo
            if (sourceExpr instanceof IASTUnaryExpression && 
                (sourceExpr as IASTUnaryExpression).operator === IASTUnaryExpression.op_amper
            ){
                statePointers.put(targetAndIndex.target.name.split(ssaNameSeperator).get(0), 
                    ((source as OperatorExpression).subExpressions.get(0) as ValuedObjectReference).valuedObject)
            }

            if (!serializable) {
                targetAndIndex.target.insertHighlightAnnotations(binExpr)
            }
            ass = createDataflowAssignment(targetAndIndex.target, source)
            if (targetAndIndex.index !== null) {
                ass.reference.indices += targetAndIndex.index
            }
        }
        assignments.add(ass)
        return assignments
    }

    /**
     * Retrieve the target valued object for the given target expression for the given function state and dataflow
     * region, as well as the index if the target is an array.
     */
    def ValuedObjectAndExpression retrieveTargetAndIndexExpr(IASTExpression targetExpr, State funcState,
        DataflowRegion dRegion) {
        val ValuedObjectAndExpression result = new ValuedObjectAndExpression
        if (targetExpr instanceof IASTIdExpression) {
            result.target = funcState.findValuedObjectByName(targetExpr.getName.toString, true, dRegion)
        } else if (targetExpr instanceof IASTArraySubscriptExpression) {
            val arrayIndex = targetExpr.argument.createKExpression(funcState, dRegion)
            result.target = funcState.findValuedObjectByName(exprToString(targetExpr.arrayExpression, sourceFile),
                arrayIndex, true, dRegion)
            result.index = targetExpr.argument.createKExpression(funcState, dRegion)
        } else if (targetExpr instanceof IASTUnaryExpression) {
            // TODO: pointer WIP
            val op = targetExpr.operand
            val name = (op as IASTIdExpression).getName.toString
            val map = getStateVarPointers(funcState)
            val vo = map.get(name)
            if (vo === null) {
                result.target = funcState.findValuedObjectByName(name, true, dRegion)
            } else {
                result.target = vo
            }
        } else {
            println("DataflowExtractor: Unsupported assignment target detected!" + targetExpr.expressionType)
        }
        return result
    }

    /**
     * Structure class to return a {@code ValuedObject} and an {@code Expression} in a method.
     */
    private static class ValuedObjectAndExpression {
        public ValuedObject target
        public Expression index
    }

    /**
     * Create a lazy assignment like += and add them to the given dataflow region.
     */
    def void createLazyAssignment(IASTBinaryExpression expression, State funcState, DataflowRegion dRegion,
        int operator) {
        // Create a normal assignment out of lazy assignment
        val assExpr = expression.copy(CopyStyle.withLocations)
        assExpr.setOperator = IASTBinaryExpression.op_assign
        val operatorExpr = expression.copy(CopyStyle.withLocations)
        operatorExpr.setOperator = operator
        assExpr.setOperand2 = operatorExpr

        // Translate the normal assignment
        val assignments = createAssignments(assExpr, funcState, dRegion)
        for (assignment : assignments) {
            dRegion.equations += assignment
        }
    }

    /**
     * Create the assignment out of a BinaryExpression and add them to the given dataflow region.
     */
    def void createBinaryAssignment(IASTBinaryExpression expression, State funcState, DataflowRegion dRegion) {

        switch (expression.getOperator) {
            // Translate the normal assignment
            case IASTBinaryExpression.op_assign: {
                val assignments = createAssignments(expression, funcState, dRegion)
                for (assignment : assignments) {
                    dRegion.equations += assignment
                }
            }
            // Translate lazy assignments
            case IASTBinaryExpression.op_multiplyAssign: {
                val arithmeticOP = IASTBinaryExpression.op_multiply
                createLazyAssignment(expression, funcState, dRegion, arithmeticOP)
            }
            case IASTBinaryExpression.op_divideAssign: {
                val arithmeticOP = IASTBinaryExpression.op_divide
                createLazyAssignment(expression, funcState, dRegion, arithmeticOP)
            }
            case IASTBinaryExpression.op_moduloAssign: {
                val arithmeticOP = IASTBinaryExpression.op_modulo
                createLazyAssignment(expression, funcState, dRegion, arithmeticOP)
            }
            case IASTBinaryExpression.op_plusAssign: {
                val arithmeticOP = IASTBinaryExpression.op_plus
                createLazyAssignment(expression, funcState, dRegion, arithmeticOP)
            }
            case IASTBinaryExpression.op_minusAssign: {
                val arithmeticOP = IASTBinaryExpression.op_minus
                createLazyAssignment(expression, funcState, dRegion, arithmeticOP)
            }
            case IASTBinaryExpression.op_shiftLeftAssign: {
                val arithmeticOP = IASTBinaryExpression.op_shiftLeft
                createLazyAssignment(expression, funcState, dRegion, arithmeticOP)
            }
            case IASTBinaryExpression.op_shiftRightAssign: {
                val arithmeticOP = IASTBinaryExpression.op_shiftRight
                createLazyAssignment(expression, funcState, dRegion, arithmeticOP)
            }
            case IASTBinaryExpression.op_binaryAndAssign: {
                val arithmeticOP = IASTBinaryExpression.op_binaryAnd
                createLazyAssignment(expression, funcState, dRegion, arithmeticOP)
            }
            case IASTBinaryExpression.op_binaryXorAssign: {
                val arithmeticOP = IASTBinaryExpression.op_binaryXor
                createLazyAssignment(expression, funcState, dRegion, arithmeticOP)
            }
            case IASTBinaryExpression.op_binaryOrAssign: {
                val arithmeticOP = IASTBinaryExpression.op_binaryOr
                createLazyAssignment(expression, funcState, dRegion, arithmeticOP)
            }
        }

    }

    /**
     * Translate a unary assignment and add it to the dataflow region.
     */
    def void createUnaryAssignment(IASTUnaryExpression expression, State funcState, DataflowRegion dRegion) {
        val inc = #[IASTUnaryExpression.op_postFixIncr, IASTUnaryExpression.op_prefixIncr]
        val dec = #[IASTUnaryExpression.op_postFixDecr, IASTUnaryExpression.op_prefixDecr]
        if ((inc + dec).contains(expression.operator)) {
            // Translate this pre- or postfix increment/decrement like a usual assignment such as i = i + 1
            // Create the source expression, for example i + 1
            val operator = if(inc.contains(expression.operator)) OperatorType::ADD else OperatorType::SUB
            val source = createOperatorExpression(operator) => [
                it.subExpressions += createKExpression(expression.operand, funcState, dRegion)
                it.subExpressions += createIntValue(1)
            ]

            // look for the target and add it to the dataflow region as an assignment.
            val targetAndIndex = retrieveTargetAndIndexExpr(expression.operand, funcState, dRegion)

            if (!serializable) {
                targetAndIndex.target.insertHighlightAnnotations(expression)
            }
            val Assignment ass = createDataflowAssignment(targetAndIndex.target, source)
            if (targetAndIndex.index !== null) {
                ass.reference.indices += targetAndIndex.index
            }
            dRegion.equations += ass
            return
        }

        // Create the expression
        val sourceExpression = createKExpression(expression, funcState, dRegion)
        // Retrieve the respective variable VO                
        val opName = (expression.getOperand as IASTIdExpression).getName.toString
        val opVO = funcState.findValuedObjectByName(opName, true, dRegion)

        if (!serializable) {
            opVO.insertHighlightAnnotations(expression)
        }
        // Create the Assignment        
        dRegion.equations += createDataflowAssignment(opVO, sourceExpression)

    }

    /**
     * Translate a function call expression and add it to the dataflow region.
     * 
     * @param expression The function call expression to translate into SCCharts.
     * @param state The surrounding state where this function call should be added to.
     * @param dRegion The dataflow region where this dunction call should be added to.
     * @return A reference to the valued object for this function call.
     */
    def ValuedObjectReference createFunctionCall(IASTFunctionCallExpression expression, State state,
        DataflowRegion dRegion) {
        val refState = findFunctionState(expression, state, dRegion)

        // Find an existing reference to this state the reference
        var ReferenceDeclaration refDecl = dRegion.declarations.filter(ReferenceDeclaration).findFirst [
            it.reference === refState
        ]
        // Create a new reference if none was found yet
        if (refDecl === null) {
            refDecl = createReferenceDeclaration
            refDecl.setReference(refState)
            refDecl.annotations += createTagAnnotation("Hide")
            dRegion.declarations += refDecl
        }

        // Create a valued object for the referenced state
        val refObj = refDecl.createValuedObject(refState.name + ssaNameSeperator + referenceCounter)
        refObj.label = refState.label
        if (!serializable) {
            refObj.insertHighlightAnnotations(expression)
        }

        // Create all assignments for the function call
        var index = 0
        for (argument : expression.arguments) {
            // Create the assignments from the function inputs to inputs of the referenced state.
            val argExpr = argument.createKExpression(state, dRegion)
            val stateDeclarations = refState.declarations.filter(VariableDeclaration)
            val inputVOs = stateDeclarations.filter[it.isInput].map[it.valuedObjects].flatten
            val outputVOs = stateDeclarations.filter[it.isOutput].map[it.valuedObjects].flatten.filter [
                it.name != returnObjectName + outSuffix
            ]
            val inputVO = inputVOs.get(index)
            dRegion.equations += createDataflowAssignment(refObj, inputVO, argExpr)

            // Create the assignments from the referenced state's outputs to the corresponding
            // previous input VOs.
            val outputVO = outputVOs.findFirst [
                it.name.substring(0, it.name.length - outSuffix.length).equals(
                    inputVO.name.substring(0, inputVO.name.length - inSuffix.length))
            ]
            if (outputVO !== null && argExpr instanceof ValuedObjectReference) {
                // Search for the VO in the surrounding state that was given as the argument
                val variableVOName = (argExpr as ValuedObjectReference).valuedObject.name
                val outputToVO = findValuedObjectByName(state,
                    variableVOName.substring(0, variableVOName.indexOf(ssaNameSeperator)), true, dRegion)

                val assignment = SCChartsFactory.eINSTANCE.createDataflowAssignment => [
                    it.reference = outputToVO.reference
                    it.expression = refObj.reference => [
                        it.subReference = outputVO.reference
                    ]
                ]

                dRegion.equations += assignment
            }
            // If the argument is some pointer, the output of the function has to be assigned to
            // the variable that pointer points to
            val indirectPointer = argument instanceof IASTUnaryExpression 
                && (argument as IASTUnaryExpression).operator === IASTUnaryExpression.op_amper
            
            var argumentPointer = argument instanceof IASTIdExpression ? 
                getStateVarPointers(state).get((argument as IASTIdExpression).name.toString) : null
                
            if (indirectPointer) {
                val name = ((argument as IASTUnaryExpression).operand as IASTIdExpression).name.toString
                argumentPointer = findValuedObjectByName(state, name, true, dRegion)
            }
                
            if (argumentPointer !== null && outputVO !== null) {
                val variableVOName = argumentPointer.name
                val outputToVO = findValuedObjectByName(state,
                    variableVOName.substring(0, variableVOName.indexOf(ssaNameSeperator)), true, dRegion)

                val assignment = SCChartsFactory.eINSTANCE.createDataflowAssignment => [
                    it.reference = outputToVO.reference
                    it.expression = refObj.reference => [
                        it.subReference = outputVO.reference
                    ]
                ]

                dRegion.equations += assignment
            }
            index++
        }

        referenceCounter++
        // Wire the sub-reference to the output VO of the reference state.
        return refObj.reference.outputReference(refState, returnObjectName + outSuffix)
    }

    /**
     * Translate a CDT expression into a KExpression
     */
    def Expression createKExpression(IASTNode expr, State funcState, DataflowRegion dRegion) {
        var Expression kExpression

        switch expr {
            // For variable uses create a VO reference
            IASTIdExpression: {
                var opValObj = funcState.findValuedObjectByName((expr as IASTIdExpression).getName.toString, false,
                    dRegion)
                kExpression = opValObj.reference
            }
            IASTFieldReference: {
                var opValObj = funcState.findValuedObjectByName((expr as IASTFieldReference).getFieldName.toString,
                    false, dRegion)
                kExpression = opValObj.reference
            }
            // For an array, just use the array expression (and leave out the subscript for now).
            IASTArraySubscriptExpression: {
                val indexExpression = expr.argument.createKExpression(funcState, dRegion)
                if (expr.arrayExpression instanceof IASTIdExpression) {
                    var opValObj = funcState.findValuedObjectByName(
                        (expr.arrayExpression as IASTIdExpression).getName.toString, indexExpression, false, dRegion)
                    kExpression = opValObj.reference
                } else {
                    println("Unsupported ast node to create an expression: " + expr.arrayExpression.class)
                    kExpression = expr.arrayExpression.createKExpression(funcState, dRegion)
                }
                (kExpression as ValuedObjectReference).indices += indexExpression
            }   
            // For a function call create a reference an return the functions output VO reference
            IASTFunctionCallExpression: {
                // Create the Reference
                kExpression = createFunctionCall(expr, funcState, dRegion)
            }
            // For a conditional expression create a state similar to an if    
            IASTConditionalExpression: {
                kExpression = buildConditional(expr, expr.logicalConditionExpression, expr.positiveResultExpression,
                    expr.negativeResultExpression, funcState, dRegion)
            }
            // Skip a cast and translate the inner expression
            IASTCastExpression: {
                kExpression = expr.getOperand.createKExpression(funcState, dRegion)
            }
            // Call subsequent translations for unary and binary expressions
            IASTUnaryExpression: {
                kExpression = (expr as IASTUnaryExpression).createKExpression(funcState, dRegion)
            }
            IASTBinaryExpression: {
                kExpression = (expr as IASTBinaryExpression).createKExpression(funcState, dRegion)
            }
            // Create a value expression for a literal
            IASTLiteralExpression: {
                kExpression = expr.createValue
            }
            // Extract the expression of an expression statement and translate it
            IASTExpressionStatement: {
                kExpression = expr.getExpression.createKExpression(funcState, dRegion)
            }
            IASTInitializerList: {
                kExpression = createVectorValue => [
                    values += expr.clauses.map[it.createKExpression(funcState, dRegion)]
                ]
            }
            IASTTypeIdExpression: {
                val declSpecifier = expr.typeId.declSpecifier
                if (declSpecifier instanceof IASTSimpleDeclSpecifier) {
                    val inputType = declSpecifier.type.cdtTypeConversion
                    val inputTypeName = nodeToString(expr, sourceFile)
                    kExpression = createSizeofState(null, inputType, inputTypeName, dRegion)
                }
            }
            default: {
                println("Unsupported ast node to create an expression: " + expr.class)
            }
        }
        return kExpression
    }

    /**
     * Translate a bianryExpression
     */
    def Expression createKExpression(IASTBinaryExpression binExpr, State funcState, DataflowRegion dRegion) {
        var Expression expression
        if (binExpr.getOperator() == IASTBinaryExpression.op_assign) {
            val assignments = createAssignments(binExpr, funcState, dRegion)
            for (assignment : assignments) {
                dRegion.equations += assignment
            }
            expression = assignments.head.reference.valuedObject.reference
        } else {
            // Create the operator expression with the corresponding operator
            var opType = binExpr.getOperator().cdtBinaryOpTypeConversion
            expression = opType.createOperatorExpression
            // Translate the operands and attach them    
            for (operand : binExpr.children) {
                (expression as OperatorExpression).subExpressions += operand.createKExpression(funcState, dRegion)
            }
        }

        return expression
    }

    /**
     * Translate an UnaryExpression
     */
    def Expression createKExpression(IASTUnaryExpression unExpr, State funcState, DataflowRegion dRegion) {
        // Retrieve the operator and create the operatorExpression
        var opType = unExpr.getOperator.cdtUnaryOpTypeConversion
        var OperatorExpression unKExpr

        // Test if the operator is a simple translatable operator.
        if (opType !== null) {
            unKExpr = opType.createOperatorExpression
            // Attach the operand
            val operandExpression = unExpr.getOperand.createKExpression(funcState, dRegion)
            unKExpr.subExpressions += operandExpression
            return unKExpr
        } else {
            // If the operator is not translatable as an KExpression OperatorType, check for some
            // special cases that we do handle.
            switch (unExpr.getOperator) {
                case IASTUnaryExpression.op_sizeof: {
                    // sizeof will get translated to a new sizeof operator box
                    // First, translate its operand as the input for the sizeof.
                    var operand = unExpr.getOperand
                    val sizeofInput = operand.createKExpression(funcState, dRegion)

                    // Create the operator as a sizeof state that gets wired up in its parent dataflow region.
                    return createSizeofState(sizeofInput, ValueType::INT, null, dRegion)
                }
                default: {
                    // Otherwise, translate the operand
                    var operand = unExpr.getOperand
                    return operand.createKExpression(funcState, dRegion)
                }
            }
        }
    }

    /**
     * Creates a new state for the sizeof expression.
     * 
     * @param sizeofInput The expression that is the input for this expression, or {@code null} if there is no input.
     * @param inputType The input type as a ValueType.
     * @param nodeName The name of the node, or null. Is only used if {@code sizeofInput} is null, this string is shown
     *                 as a label instead.
     * @param dRegion The region the state for this sizeof expression should be put in.
     * @return A reference to the created state's valued object.
     */
    def ValuedObjectReference createSizeofState(Expression sizeofInput, ValueType inputType, String nodeName,
        DataflowRegion dRegion) {
        val label = if (sizeofInput === null) {
                nodeName
            } else {
                sizeofName
            }

        val sizeofState = createState(label + ssaNameSeperator + sizeofCounter++)
        sizeofState.label = label
        sizeofState.annotations += createTagAnnotation("Hide")
        if (serializable) {
            rootSCChart.rootStates += sizeofState
        }
        sizeofState.createDataflowRegion(label) => [
            it.label = label
        ]
        val refDecl = createReferenceDeclaration
        dRegion.declarations += refDecl
        refDecl.setReference(sizeofState)
        val sizeofObject = refDecl.createValuedObject(label)

        // Input
        if (sizeofInput !== null) {
            val sizeofInputDecl = createVariableDeclaration => [
                type = inputType
                input = true
            ]
            sizeofState.declarations += sizeofInputDecl
            val sizeofInputVO = sizeofInputDecl.createValuedObject(inSuffix)
            sizeofInputVO.label = ""

            dRegion.equations += createDataflowAssignment(sizeofObject, sizeofInputVO, sizeofInput)
        }

        // Output
        val sizeofOutputDecl = createVariableDeclaration => [
            type = ValueType::INT
            output = true
        ]
        sizeofState.declarations += sizeofOutputDecl
        val sizeofOutputVO = sizeofOutputDecl.createValuedObject(outSuffix)
        sizeofOutputVO.label = ""

        return sizeofObject.reference => [
            subReference = sizeofOutputVO.reference
        ]
    }

    /**
     * Adds the sub reference to the output valued object of the given reference in the state.
     * 
     * @param reference The reference of which to modify the sub reference.
     * @param referenceState The state this reference belongs to.
     * @return The same reference, for convenience.
     */
    def ValuedObjectReference outputReference(ValuedObjectReference reference, State referenceState,
        String objectName) {
        return reference => [
            subReference = referenceState.declarations.filter(VariableDeclaration).map[it.valuedObjects].flatten.filter [
                name == objectName
            ].head.reference
        ]
    }

}