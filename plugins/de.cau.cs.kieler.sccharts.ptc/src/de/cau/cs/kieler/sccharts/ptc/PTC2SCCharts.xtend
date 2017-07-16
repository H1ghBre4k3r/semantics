package de.cau.cs.kieler.sccharts.ptc;

import com.google.inject.Injector
import de.cau.cs.kieler.sccharts.ControlflowRegion
import de.cau.cs.kieler.sccharts.SCChartsFactory
import de.cau.cs.kieler.sccharts.State
import de.cau.cs.kieler.sccharts.extensions.SCChartsExtension
import de.cau.cs.kieler.sccharts.text.sct.SctStandaloneSetup
import java.util.HashMap
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.xml.type.AnyType
import com.google.inject.Inject
import org.eclipse.emf.edit.tree.TreeNode
import org.eclipse.emf.ecore.EReference
import de.cau.cs.kieler.kexpressions.KExpressionsFactory
import de.cau.cs.kieler.kexpressions.ValueType
import de.cau.cs.kieler.sccharts.Transition
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsExtensionOLD
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsCreateExtensions
import de.cau.cs.kieler.kexpressions.extensions.KExpressionsValuedObjectExtensions
import de.cau.cs.kieler.kexpressions.ValuedObject
import de.cau.cs.kieler.kexpressions.keffects.extensions.KEffectsExtensions
import de.cau.cs.kieler.sccharts.ptc.xmi.XMIModel.Element
import de.cau.cs.kieler.sccharts.ptx.xmi.XMIModelExtensions
import de.cau.cs.kieler.sccharts.ptx.xmi.UMLActionExpression
import de.cau.cs.kieler.sccharts.Action
import de.cau.cs.kieler.kexpressions.Declaration
import javax.swing.undo.UndoManager
import de.cau.cs.kieler.sccharts.StateType

/**
 * Import SCCharts from PTC
 * 
 * @author cmot
 * @kieler.design 2017-06-15 proposed cmot
 * @kieler.rating 2017-06-15 proposed yellow
 */
public class PTC2SCCharts {

    @Inject
    extension XMIModelExtensions

    @Inject
    extension PTCXMIUMLExtensions

    @Inject
    extension KEffectsExtensions

    @Inject
    extension SCChartsExtension;

    @Inject
    extension KExpressionsCreateExtensions;

    @Inject
    extension KExpressionsValuedObjectExtensions;

    // GuiceModulesExecutableExtensionFactory
    HashMap<EObject, EObject> src2target = new HashMap();
    HashMap<EObject, EObject> target2src = new HashMap();

    HashMap<String, EObject> id2src = new HashMap();
    HashMap<EObject, String> src2id = new HashMap();

    HashMap<String, ValuedObject> id2input = new HashMap();
    HashMap<String, String> Operation2Name = new HashMap();
    HashMap<String, ValuedObject> id2output = new HashMap();

    HashMap<String, ValuedObject> name2localValuedObject = new HashMap();

    /** Create an injector to load the transformation via guice. */
    private static Injector injector = new SctStandaloneSetup().createInjectorAndDoEMFRegistration();

    def println(String text) {
        SCChartsPTCPlugin.printConsole(text)
    }

    def String fixId(String name) {
        if (name == null) {
            return "Empty"
        }
        var returnName = name
        returnName = returnName.replace(".", "_")
        returnName = returnName.replace("#", "_")
        returnName = returnName.replace("&", "")
        returnName = returnName.replace(">", "")
        returnName = returnName.replace("<", "")
        returnName = returnName.replace(":", "")
        returnName = returnName.replace(";", "")
        returnName = returnName.replace(",", "_")
        returnName = returnName.replace(" ", "")
        returnName = returnName.replace("-", "")
        returnName = returnName.replace("*", "")
        returnName = returnName.replace("/", "")
        returnName = returnName.replace("\\", "")
        returnName = returnName.replace("\"", "")
        returnName = returnName.replace("'", "")
        returnName = returnName.replace("=", "Equals")
        return returnName
    }

//    def Object name(EObject content) {
//        return "EObject:null";
//    }
    def State current(List<State> targetModel) {
        if (targetModel.nullOrEmpty) {
            src2target.clear
            target2src.clear
            id2src.clear
            name2localValuedObject.clear
            src2id.clear
            id2input.clear
            id2output.clear
            Operation2Name.clear
            var scchart = SCChartsFactory::eINSTANCE.createState;
            targetModel.add(scchart)
        }
        return targetModel.last
    }

    def void map(Element element, EObject target) {
        src2target.put(element, target);
        target2src.put(target, element);

        id2src.put(element.id, element);
        src2id.put(element, element.id);
    }

    def EObject src2target(EObject src) {
        return src2target.get(src)
    }

    def EObject target2src(EObject target) {
        return target2src.get(target)
    }

    def EObject id2src(String id) {
        return id2src.get(id)
    }

    def String src2id(EObject src) {
        return src2id.get(src)
    }

    def ValuedObject id2input(List<State> targetModel, Element element, String id) {
        val returnValue = id2input.get(id)
        if (returnValue != null) {
            return returnValue
        } else {
            // Not yet declared, declare it newly
            val rootElement = element.root
            val varName = id.searchInputName(rootElement);
            println("INPUT EXTRAXT from '" + id + "' ==> '" + varName + "'")

            val declaration = KExpressionsFactory::eINSTANCE.createDeclaration
            declaration.input = true
            declaration.type = ValueType::BOOL
            targetModel.current.declarations.add(declaration)
            val valuedObject = createValuedObject(targetModel.current, varName, declaration)
            element.map(valuedObject)
            println("EVENT IN PUT:" + id + " (" + valuedObject.name + ")");
            id2input.put(id, valuedObject)
            return valuedObject
        }
    }

    def ValuedObject getParamValuedObject(List<State> targetModel, String paramName) {
        if (!id2input.containsKey(paramName)) {
            // Insert new constant (intput)
            val declaration = KExpressionsFactory::eINSTANCE.createDeclaration
            declaration.input = true
            declaration.type = ValueType::INT
            targetModel.current.declarations.add(declaration)
            val valuedObject = createValuedObject(targetModel.current, paramName, declaration)
            id2input.put(paramName, valuedObject)
            println("PARAM IN PUT:" + paramName + " (" + valuedObject.name + ")");
        }
        return id2input.get(paramName);
    }

    def ValuedObject getLocalValuedObject(List<State> targetModel, String localName) {
        if (!name2localValuedObject.containsKey(localName)) {
            // Insert new constant (intput)
            val declaration = KExpressionsFactory::eINSTANCE.createDeclaration
            declaration.input = false
            declaration.output = false
            declaration.type = ValueType::INT
            targetModel.current.declarations.add(declaration)
            val valuedObject = createValuedObject(targetModel.current, localName, declaration)
            name2localValuedObject.put(localName, valuedObject)
            println("LOCAL IN PUT:" + localName + " (" + valuedObject.name + ")");
        }
        return name2localValuedObject.get(localName);
    }

    def ValuedObject getReferencedValuedObject(List<State> targetModel, String localName) {
        // If a references variable already exists as local, then return this, but make it input
        // else
        // create a new input
        var returnValuedObject = getLocalValuedObject(targetModel, localName)
        var decl = (returnValuedObject.eContainer as Declaration);
        if (decl != null) {
            decl.input = true
        }
        return returnValuedObject;
    }

    def ValuedObject getOutputValuedObject(List<State> targetModel, String outputName, ValueType type, boolean signal) {
        if (!id2output.containsKey(outputName)) {
            // Insert new constant (intput)
            val declaration = KExpressionsFactory::eINSTANCE.createDeclaration
            declaration.input = false
            declaration.output = true
            declaration.type = type
            declaration.signal = signal
            targetModel.current.declarations.add(declaration)
            val valuedObject = createValuedObject(targetModel.current, outputName, declaration)
            id2output.put(outputName, valuedObject)
            println("OUTPUT IN PUT:" + outputName + " (" + valuedObject.name + ")");
        }
        return id2output.get(outputName);

//        var valuedObject = id2output.get(body)
//        if (valuedObject != null) {
//            return valuedObject
//        } else {
//            // Not yet declared, declare it newly
//            //val rootElement = element.root
//            val declaration = KExpressionsFactory::eINSTANCE.createDeclaration
//            declaration.output = true
//            declaration.type = ValueType::PURE
//            declaration.signal = true
//            //(rootElement.src2target as State).declarations.add(declaration)
//            targetModel.current.declarations.add(declaration)
//            valuedObject = createValuedObject(targetModel.current, signalName, declaration)
//            element.map(valuedObject)
//            println("EVENT OUT PUT:" + body + " (" + valuedObject.name + ")");
//            id2input.put(signalName, valuedObject)
//            return valuedObject
//        }
    }

    def void body2output(Action action, List<State> targetModel, Element element, String body) {
        val outputName = body.extractOutputName
        val outputParam = body.extractOutputParam

        if (outputName == null) {
            // @OPTION
            // Only take the first expression (if multiple)
            var bodyExpression = body
            if (bodyExpression.contains(";")) {
                bodyExpression = body.substring(0, bodyExpression.indexOf(";"));
            }

            println("Parsing expression '" + bodyExpression + "' ...")
            try {
                val expr = UMLActionExpression.parse(bodyExpression);
                println(" DONE.");

                // All assign variables need to exist as local variables
                for (localValObjName : expr.assignedEntities) {
                    // Possibly add new input (INT only supported!)
                    val local = targetModel.getLocalValuedObject(localValObjName.name)
                }
                // @OPTION
                // All references variables need to exist either as local variables
                // or if not, they need to additional inputs
                for (refValObjName : expr.referencedEntities) {
                    // Only for REAL entities, not for numbers!!!
                    if (refValObjName.isEntity) {
                        val input = targetModel.getReferencedValuedObject(refValObjName.name)
                    }
                }

            } catch (Exception e) {
                println(" ERROR.");
            }

            // Add a host code effect
            action.addEffect(asHostcodeEffect(body))
            return;
        }

        val signalName = outputName.fixId
        if (outputParam != null) {
            // @OPTION type
            // We need an integer input
            if (UMLActionExpression::isEntity(outputParam)) {
                // Parameter is INT input
                val ValuedObject param = getParamValuedObject(targetModel, "P_" + outputParam)
                val ValuedObject outVar = targetModel.getOutputValuedObject(signalName, ValueType::INT, false)
                action.addAssignment(outVar.assign(param.reference))
            } else {
                // @OPTION type
                val outputParamInt = UMLActionExpression::getInteger(outputParam)
                // Parameter is integer number
                val ValuedObject outVar = targetModel.getOutputValuedObject(signalName, ValueType::INT, false)
                action.addAssignment(outVar.assign(outputParamInt.createIntValue))
            }
        } else {
            // A signal outut is sufficient
            val ValuedObject outSignal = targetModel.getOutputValuedObject(signalName, ValueType::PURE, true)
            action.addEffect(outSignal.emit)
        }
    }

    def String extractOutputParam(String actionBody) {
        val i = actionBody.indexOf("(")
        val i2 = actionBody.indexOf(")", i)
        if (i > 0 && i2 > 0 && i2 > i + 1) {
            return actionBody.substring(i + 1, i2).fixId
        }
        return null;
    }

    def String extractOutputName(String actionBody) {
        val i = actionBody.indexOf("(")
        if (i > 0) {
            return actionBody.substring(0, i)
        }
        return null;
    }

    /**
     *       <trigger xmi:type = "uml:Trigger" xmi:id = "_95069729-de3a-46ae-a522-d8c12a976b7ctrigger" event = "_95069729-de3a-46ae-a522-d8c12a976b7cCall">
     *       <packagedElement xmi:type = "uml:CallEvent" xmi:id = "_95069729-de3a-46ae-a522-d8c12a976b7cCall" operation = "_6f19a978-401b-4faa-adec-a586db001883">
     *       <ownedOperation xmi:type = "uml:Operation" xmi:id = "_6f19a978-401b-4faa-adec-a586db001883" name = "ReqAllowed" visibility = "public">

     */
    public static var int inputCounter = 0

    def String searchInputName(String eventid, Element element) {
        for (child : element.eAllContents.toList) {
            if (child instanceof Element) {
                if (child.isUMLSignalEvent) {
                    println("element.id = " + child.id + " == " + eventid + " = eventid");
                    if (child.id == eventid) {
                        return "I_" + child.name
                    }
                }
            }

        }
        return eventid.searchInputNameFallback(element)
    }

    def String searchInputNameFallback(String eventid, Element element) {
        for (child : element.eAllContents.toList) {
            if (child instanceof Element) {
                if (child.isUMLOperation) {
                    Operation2Name.put(child.id, child.name)
                }
            }
        }
        for (child : element.eAllContents.toList) {
            if (child instanceof Element) {
                if (child.isUMLCallEvent) {
                    if (eventid == child.id) {
                        val operationId = child.operation
                        val operationName = Operation2Name.get(operationId)
                        if (operationName != null) {
                            val inputName = "I_" + operationName
                            return inputName
                        }
                    }
                }
            }

        }
        inputCounter++
        return "I_" + inputCounter
    }

    def void transformStateMachine(List<State> targetModel, Element element) {
        src2target.clear
        target2src.clear
        id2src.clear
        name2localValuedObject.clear
        src2id.clear
        id2input.clear
        id2output.clear
        Operation2Name.clear
        var scchart = SCChartsFactory::eINSTANCE.createState;
        targetModel.add(scchart)
        scchart.id = element.name.fixId;
        println("CREATE STATEMACHINE '" + scchart.id + "' for " + element.hashCode)
        element.map(scchart)
        targetModel.transformGeneral(element)
    }

    def void transformRegion(List<State> targetModel, Element element, EObject srcParent) {
        println("CREATE REGION for parent " + srcParent.hashCode)
        val state = src2target.get(srcParent) as State;
        val region = state.createControlflowRegion("Region");
        element.map(region)
        targetModel.transformGeneral(element)
    }

    def void transformPseudostate(List<State> targetModel, Element element, EObject srcParent) {
        // Find region to create state in
        val srcParentRegion = src2target.get(srcParent)
        if (!(srcParentRegion instanceof ControlflowRegion)) {
            println(
                "ERROR: Element '" + srcParentRegion.id +
                    "' is not a region. Cannot create initial or connector state '" + element.id + "' here. ")
            return
        }

        // if (element.name.startsWith("Initial")) {
        if (element.kind != "junction") {
            println("CREATE INIT STATE '" + element.name + "' with id " + element.id)
            val state = (src2target.get(srcParent) as ControlflowRegion).createInitialState(element.name.fixId).
                uniqueName;
            element.map(state)
            targetModel.transformGeneral(element)
        } else {
            println("CREATE CONNECTOR STATE '" + element.name + "' with id " + element.id)
            val state = (src2target.get(srcParent) as ControlflowRegion).createState(element.name.fixId).uniqueName;
            state.setTypeConnector
            element.map(state)
            targetModel.transformGeneral(element)
        }
    }

    def void transformFinalState(List<State> targetModel, Element element, EObject srcParent) {
        println("CREATE FINAL STATE '" + element.name + "' with id " + element.id)
        val state = (src2target.get(srcParent) as ControlflowRegion).createFinalState(element.name.fixId).uniqueName;
        element.map(state)
        targetModel.transformGeneral(element)
    }

    def void transformState(List<State> targetModel, Element element, EObject srcParent) {
        println("CREATE STATE '" + element.name + "' with id " + element.id)
        val state = (src2target.get(srcParent) as ControlflowRegion).createState(element.name.fixId).uniqueName;
        // if name == Initialize
        element.map(state)
        targetModel.transformGeneral(element)
    }

    def transformActivity(List<State> targetModel, Element element) {
        if (element.umlType != "Activity") {
            return
        }
        if (element.type == "entry" || element.umlType == "exit") {
            val parentState = element.parentAnyState
            if (parentState != null) {
                if (element.type == "entry" || element.type == "exit") {
                    // val outputSignal = body2output(targetModel, element, element.name)
                    if (element.type == "entry") {
                        val action = (parentState.src2target as State).createEntryAction
                        // action.addEffect(asHostcodeEffect(element.name))
                        // action.addEffect(outputSignal.emit)
                        action.body2output(targetModel, element, element.name)
                    }
                    if (element.umlType == "exit") {
                        val action = (parentState.src2target as State).createExitAction
                        // action.addEffect(asHostcodeEffect(element.name))
                        // action.addEffect(outputSignal.emit)
                        action.body2output(targetModel, element, element.name)
                    }
                }
            }
        }
    }

    def transformEvent(List<State> targetModel, Element element) {
//        val varName = element.name
//        val declaration = KExpressionsFactory::eINSTANCE.createDeclaration
//        declaration.input = true
//        declaration.type = ValueType::BOOL
//        targetModel.current.declarations.add(declaration)
//        val valuedObject = createValuedObject(targetModel.current, varName, declaration)
//        element.map(valuedObject)
//        id2input.put(element.id, valuedObject)
    }

    def transformProperty(List<State> targetModel, Element element) {
//        if ((element == null) || (element.name == null)) {
//            return
//        }
//        val varName = element.name.replace(" ", "")
//
//        println(" $$$ ADDING PROPERTY: '" + varName + "'");
//        val declaration = KExpressionsFactory::eINSTANCE.createDeclaration
//        // declaration.input = true
//        declaration.type = ValueType::INT
//        targetModel.current.declarations.add(declaration)
//        val valuedObject = createValuedObject(targetModel.current, varName, declaration)
//        element.map(valuedObject)
//        id2input.put(element.id, valuedObject)
    }

    /*
     *         <packagedElement xmi:type = "uml:Class" xmi:id = "_43442151-fbf2-11d2-a53d-00104bb05af8" name = "Controller">
     *           <ownedAttribute xmi:type = "uml:Property" xmi:id = "_0018e606-fd6b-11d2-a540-00104bb05af8" name = "ped_wait" type = "_bf9531dd-fd6d-429c-9c77-72c228475b8e" visibility = "private" aggregation = "composite">
     *             <defaultValue xmi:type = "_bf9531dd-fd6d-429c-9c77-72c228475b8e" xmi:id = "_0018e606-fd6b-11d2-a540-00104bb05af8defaultValue" value = "0"/>
     *           </ownedAttribute>
     *           <ownedAttribute xmi:type = "uml:Property" xmi:id = "_98368725-fe2c-11d2-a541-00104bb05af8" name = "flashes" type = "_bf9531dd-fd6d-429c-9c77-72c228475b8e" visibility = "private" aggregation = "composite">
     *           </ownedAttribute>
     */
    def void transformTransition(List<State> targetModel, Element element) {
        println(" --> TRANSITION: from " + element.source + " to " + element.target);
        val src = element.source.id2src.src2target
        val dst = element.target.id2src.src2target

        if ((src instanceof State) && (dst instanceof State)) {
            val transition = (src as State).createTransitionTo(dst as State)
            element.map(transition)

            if ((src as State).isInitial) {
                transition.immediate = true
            }

            // @OPTION
            // Check if transition has a trigger, if not then create a dummy trigger
            if ((element.eAllContents.filter[e|(e instanceof Element) && (e as Element).UMLTrigger]).size == 0) {
                // Ignore initial transitions
                // @OPTION
                var skip = false;
                if (transition.sourceState.isInitial) {
                    if (transition.sourceState.outgoingTransitions.size == 1) {
                        skip = true;
                        println("Info: Outgoing transition from initial state will not get a dummy input trigger.")
                    }
                }
                if (transition.sourceState.type == StateType::CONNECTOR) {
                    val index = transition.sourceState.outgoingTransitions.indexOf(transition)

                    val sourceStateElement = transition.sourceState.target2src as Element;

                    val sourceStateElementTransitions = sourceStateElement.root.eAllContents.filter [ e |
                        (e instanceof Element) && (e as Element).UMLTransition &&
                            (e as Element).source.equals(sourceStateElement.id)
                    ]

                    val maxIndex = sourceStateElementTransitions.size - 1
                    if (index == maxIndex) {
                        skip = true;
                        println(
                            "Info: Default outgoing transition from connector state will not get a dummy input trigger.")
                    }
                }

                if (!skip) {
                    // No trigger, so create dummy trigger
                    val valuedObject = targetModel.id2input(element, transition.hashCode + "")
                    if (valuedObject != null) {
                        println(" --> DUMMY TRIGGER ValuedObject: " + valuedObject.name);
                        transition.trigger = valuedObject.reference
                    }
                }
            }
        }
        targetModel.transformGeneral(element)
    }

    def transformTrigger(List<State> targetModel, Element element) {
        val transition = element.parent.src2target as Transition
        println(" --> TRIGGER FOR TRANSITION: " + transition);
        if (transition != null) {
            val event = element.event
            println("EVENT GET:" + event);
            val valuedObject = targetModel.id2input(element, event)
            if (valuedObject != null) {
                println(" --> TRIGGER ValuedObject: " + valuedObject.name);
                transition.trigger = valuedObject.reference
            }
        }
    }

    def transformOpaqueBehavior(List<State> targetModel, Element element) {
        // Parent shall be a transition
        val parent = element.parent
        if (parent != null && parent.UMLTransition) {
            // val outputSignal = body2output(targetModel, element, element.body)
            val umlTransition = parent.id.id2src
            if (umlTransition != null) {
                val eobject = (umlTransition.src2target)
                if (eobject instanceof Transition) {
                    val transition = eobject as Transition
                    transition.body2output(targetModel, element, element.body)
                // transition.addEffect(outputSignal.emit)
                }
            }
        }
    }

    def transformConstraint(List<State> targetModel, Element element) {
        //
    }

    def transformOpaqueExpression(List<State> targetModel, Element element) {
        //
    }

    def transformOperation(List<State> targetModel, Element element) {
        //
    }

    def transformParameter(List<State> targetModel, Element element) {
        //
    }

    def transformSignalEvent(List<State> targetModel, Element element) {
//        val varName = element.name
//
//        val declaration = KExpressionsFactory::eINSTANCE.createDeclaration
//        declaration.input = (element.visibility == "public")
//        declaration.type = ValueType::BOOL
//        targetModel.current.declarations.add(declaration)
//        val valuedObject = createValuedObject(targetModel.current, varName, declaration)
//        element.map(valuedObject)
//        println("EVENT PUT:" + element.id);
//        id2input.put(element.id, valuedObject)
    }

    def transformGeneral(List<State> targetModel, Element element) {
        for (childElement : element.children) {
            // println("UML TYPE:" + childElement.umlType)
            if (childElement.id.endsWith("Event")) {
                targetModel.transformEvent(childElement)
            } else if (childElement.isUMLStateMachine) {
                targetModel.transformStateMachine(childElement)
            } else if (childElement.isUMLRegion) {
                targetModel.transformRegion(childElement, element)
            } else if (childElement.isUMLPseudostate) {
                targetModel.transformPseudostate(childElement, element)
            } else if (childElement.isUMLFinalState) {
                targetModel.transformFinalState(childElement, element)
            } else if (childElement.isUMLState) {
                targetModel.transformState(childElement, element)
            } else if (childElement.isUMLActivity) {
                targetModel.transformActivity(childElement)
            } else if (childElement.isUMLTransition) {
                targetModel.transformTransition(childElement)
            } else if (childElement.isUMLTrigger) {
                targetModel.transformTrigger(childElement)
            } else if (childElement.isUMLOpaqueBehavior) {
                targetModel.transformOpaqueBehavior(childElement)
            } else if (childElement.isUMLOpaqueExpression) {
                targetModel.transformOpaqueExpression(childElement)
            } else if (childElement.isUMLOperation) {
                targetModel.transformOperation(childElement)
            } else if (childElement.isUMLParameter) {
                targetModel.transformParameter(childElement)
            } else if (childElement.isUMLOpaqueBehavior) {
                targetModel.transformOpaqueBehavior(childElement)
            } else if (childElement.isUMLProperty) {
                targetModel.transformProperty(childElement)
            } else if (childElement.isUMLSignalEvent) {
                targetModel.transformSignalEvent(childElement)
            } else if (childElement.children.size > 0) {
                // A container
                targetModel.transformGeneral(childElement)
            }

        }
    }

    def transform(EObject model) {
        println(
            "Importing SCChart from PTC IM UML Statemachines... \n Root:" + model.eClass.name + ":" +
                model.eContents.length + "\n");

        var sccharts = newArrayList() // <State>;
        sccharts.transformGeneral(model as Element)

        for (scchart : sccharts) {
            scchart.fixAllPriorities.fixAllTextualOrdersByPriorities
        }

        return sccharts

//        for (content : model.eAllContents.toList) {
//             println("---XXXXXX: " + content.eClass.name + ":" + content.eContents.length);
//             
//             if (content.eClass.name == "StateMachine") {
//             //if (content instanceof StateMachine) {
//                 println("-----XXXXXXXXX STATEMACHINE '" + (content as Element).name + "'");
//                 
//             }
//             
//        }
//        
//        //scope.eAllContents().filter(typeof(State))
//        
//        println("XXXXXX Packages: " + model.eAllContents.filter(typeof(org.eclipse.uml2.uml.Package)).size);
//
//        println("XXXXXX States: " + model.eAllContents.filter(typeof(org.eclipse.uml2.uml.State)).size);
//        
//        //println("XXXXXX SMs: " + model.eAllContents.filter(typeof(OwnedBehavior)).size);
    }

}
//    /** The Constant S_TRANSFORMATION. */
//    public static final String S_TRANSFORMATION =
//            "de.cau.cs.kieler.sccharts.commands.STransformation";
//    /** Create an injector to load the transformation via guice. */
//    private static Injector injector = new SctStandaloneSetup()
//            .createInjectorAndDoEMFRegistration();
//
//    // -------------------------------------------------------------------------
//
//    /**
//     * Instantiates a new sC charts model file handler.
//     */
//    public PTCModelFileHandler() {
//        super();
//        
//     }
//         
//        override protected createResourceInjector() {
//            throw new UnsupportedOperationException("TODO: auto-generated method stub")
//        }
//        
//        override protected getTargetExtension(EObject model, ExecutionEvent event, ISelection selection) {
//            throw new UnsupportedOperationException("TODO: auto-generated method stub")
//        }
//        
//        override protected transform(EObject model, ExecutionEvent event, ISelection selection) {
//            throw new UnsupportedOperationException("TODO: auto-generated method stub")
//        }
//        
//    }
//
//    // -------------------------------------------------------------------------
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    protected Object transform(EObject model, ExecutionEvent event, ISelection selection) {
//        Object transformed = null;
//
//        String dlgValue = showInputDialog(title, msg, "" + defaultValue);
//
//        int ticks = 0;
//        int traces = 1;
//        try {
//            if (dlgValue.contains(",")) {
//                String dlgValues[] = dlgValue.split(",");
//                traces = Integer.parseInt(dlgValues[1].trim());
//                dlgValue = dlgValues[0];
//            }
//            ticks = Integer.parseInt(dlgValue);
//        } catch (Exception e) {
//            // fallback are ticks=0, traces=1
//        }
//
//        State state = (State) model;
//
//        StringBuilder builder = new StringBuilder();
//
//        for (int trace = 0; trace < traces; trace++) {
//            builder.append("! reset;\n");
//            for (int tick = 0; tick < ticks; tick++) {
//
//                for (Declaration declaration : state.getDeclarations()) {
//                    if (declaration.isInput()) {
//                        String type = declaration.getType().getLiteral();
//
//                        String value = null;
//                        // get random value
//                        if (type.equals("int")) {
//                            value = ((int)(1000 - (Math.random() * 2000))) + "";
//                        } else if (type.equals("bool")) {
//                            if (Math.random()*2 > 1) {
//                                value =  "true";
//                            } else {
//                                value =  "false";
//                            }
//                        } else if (type.equals("pure")) {
//                            if (Math.random()*2 > 1) {
//                                value =  "PRESENT";
//                            } 
//                        } else if (type.equals("double")) {
//                            value = (1000 - (Math.random() * 2000)) + "";
//                        }
//
//                        for (ValuedObject valuedObject : declaration.getValuedObjects()) {
//                            String name = valuedObject.getName();
//
//                            if (!declaration.isSignal() || (!type.equals("pure"))) {
//                                // variables
//                                builder.append(name + "(" + value + ") ");
//                            } else {
//                                // signals
//                                if (value != null) {
//                                    builder.append(name + " ");
//                                }
//                            }
//                        }
//                    }// input
//                }// declaration
//                builder.append("\n% Output: \n;\n");
//
//            }// tick
//        }
//
//        transformed = builder.toString();
//
//        return transformed;
//    }
//
//    // -------------------------------------------------------------------------
//
//    /**
//     * {@inheritDoc}
//     */
//    public String getDiagramEditorID() {
//        return null;
//    }
//
//    // -------------------------------------------------------------------------
//
//    /**
//     * {@inheritDoc}
//     */
//    protected boolean doOpenEditor(final Object modelObject, final ExecutionEvent event,
//            final ISelection selection) {
//        return false;
//    }
//
//    // -------------------------------------------------------------------------
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    protected String getTargetExtension(EObject model, ExecutionEvent event, ISelection selection) {
//        return "eso";
//    }
//
//    // -------------------------------------------------------------------------
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    protected Injector createResourceInjector() {
//        // Force AbstractConvertModelHandler to use XMIResource (instead of Xtext Resources)
//        return injector;
//    }
//
//    // -------------------------------------------------------------------------
//
//    /**
//     * Show an input dialog with the message, a default value and a specific title.
//     * 
//     * @param title
//     *            the title of the dialog
//     * @param message
//     *            the message to present
//     * @param defaultValue
//     *            the default value
//     * 
//     * @return the string value entered or null if aborted
//     */
//    String inputDialogReturnValue = null;
//
//    private String showInputDialog(final String title, final String message,
//            final String defaultValue) {
//        inputDialogReturnValue = null;
//        Display.getDefault().syncExec(new Runnable() {
//            public void run() {
//                final Shell shell = Display.getCurrent().getShells()[0];
//                InputDialog dlg = new InputDialog(shell, title, message, defaultValue, null);
//                dlg.open();
//                if (dlg.getReturnCode() == InputDialog.OK) {
//                    inputDialogReturnValue = dlg.getValue();
//                }
//            }
//        });
//        return inputDialogReturnValue;
//    }
//
//    // -------------------------------------------------------------------------
//}
//        for (attribute : element.anyAttribute.toList) {
//            println("STATE CHILD anyAttribute XXXXXXXXXX: " + attribute.EStructuralFeature.getName + " = " + attribute.value);
//        }
