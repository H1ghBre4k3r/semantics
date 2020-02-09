/**
 */
package de.cau.cs.kieler.kicool;

import de.cau.cs.kieler.annotations.AnnotationsPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.cau.cs.kieler.kicool.KiCoolFactory
 * @model kind="package"
 * @generated
 */
public interface KiCoolPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "kicool";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://kieler.cs.cau.de/kicool/0.1.0";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "kicool";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    KiCoolPackage eINSTANCE = de.cau.cs.kieler.kicool.impl.KiCoolPackageImpl.init();

    /**
     * The meta object id for the '{@link de.cau.cs.kieler.kicool.impl.SystemImpl <em>System</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see de.cau.cs.kieler.kicool.impl.SystemImpl
     * @see de.cau.cs.kieler.kicool.impl.KiCoolPackageImpl#getSystem()
     * @generated
     */
    int SYSTEM = 0;

    /**
     * The feature id for the '<em><b>Pragmas</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM__PRAGMAS = AnnotationsPackage.PRAGMATABLE__PRAGMAS;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM__ANNOTATIONS = AnnotationsPackage.PRAGMATABLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM__ID = AnnotationsPackage.PRAGMATABLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM__LABEL = AnnotationsPackage.PRAGMATABLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Processors</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM__PROCESSORS = AnnotationsPackage.PRAGMATABLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Intermediates</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM__INTERMEDIATES = AnnotationsPackage.PRAGMATABLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Config</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM__CONFIG = AnnotationsPackage.PRAGMATABLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Start Config</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM__START_CONFIG = AnnotationsPackage.PRAGMATABLE_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Public</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM__PUBLIC = AnnotationsPackage.PRAGMATABLE_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Developer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM__DEVELOPER = AnnotationsPackage.PRAGMATABLE_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Simulation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM__SIMULATION = AnnotationsPackage.PRAGMATABLE_FEATURE_COUNT + 9;

    /**
     * The number of structural features of the '<em>System</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM_FEATURE_COUNT = AnnotationsPackage.PRAGMATABLE_FEATURE_COUNT + 10;

    /**
     * The meta object id for the '{@link de.cau.cs.kieler.kicool.impl.ProcessorEntryImpl <em>Processor Entry</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see de.cau.cs.kieler.kicool.impl.ProcessorEntryImpl
     * @see de.cau.cs.kieler.kicool.impl.KiCoolPackageImpl#getProcessorEntry()
     * @generated
     */
    int PROCESSOR_ENTRY = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_ENTRY__ID = 0;

    /**
     * The number of structural features of the '<em>Processor Entry</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_ENTRY_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link de.cau.cs.kieler.kicool.impl.ProcessorReferenceImpl <em>Processor Reference</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see de.cau.cs.kieler.kicool.impl.ProcessorReferenceImpl
     * @see de.cau.cs.kieler.kicool.impl.KiCoolPackageImpl#getProcessorReference()
     * @generated
     */
    int PROCESSOR_REFERENCE = 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_REFERENCE__ID = PROCESSOR_ENTRY__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_REFERENCE__LABEL = PROCESSOR_ENTRY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Preconfig</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_REFERENCE__PRECONFIG = PROCESSOR_ENTRY_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Postconfig</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_REFERENCE__POSTCONFIG = PROCESSOR_ENTRY_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Metric</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_REFERENCE__METRIC = PROCESSOR_ENTRY_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Preprocesses</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_REFERENCE__PREPROCESSES = PROCESSOR_ENTRY_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Postprocesses</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_REFERENCE__POSTPROCESSES = PROCESSOR_ENTRY_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Silent</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_REFERENCE__SILENT = PROCESSOR_ENTRY_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Processor Reference</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_REFERENCE_FEATURE_COUNT = PROCESSOR_ENTRY_FEATURE_COUNT + 7;

    /**
     * The meta object id for the '{@link de.cau.cs.kieler.kicool.impl.ProcessorSystemImpl <em>Processor System</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see de.cau.cs.kieler.kicool.impl.ProcessorSystemImpl
     * @see de.cau.cs.kieler.kicool.impl.KiCoolPackageImpl#getProcessorSystem()
     * @generated
     */
    int PROCESSOR_SYSTEM = 3;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_SYSTEM__ID = PROCESSOR_ENTRY__ID;

    /**
     * The number of structural features of the '<em>Processor System</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_SYSTEM_FEATURE_COUNT = PROCESSOR_ENTRY_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link de.cau.cs.kieler.kicool.impl.ProcessorGroupImpl <em>Processor Group</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see de.cau.cs.kieler.kicool.impl.ProcessorGroupImpl
     * @see de.cau.cs.kieler.kicool.impl.KiCoolPackageImpl#getProcessorGroup()
     * @generated
     */
    int PROCESSOR_GROUP = 4;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_GROUP__ID = PROCESSOR_ENTRY__ID;

    /**
     * The feature id for the '<em><b>Processors</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_GROUP__PROCESSORS = PROCESSOR_ENTRY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_GROUP__LABEL = PROCESSOR_ENTRY_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Processor Group</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_GROUP_FEATURE_COUNT = PROCESSOR_ENTRY_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link de.cau.cs.kieler.kicool.impl.ProcessorAlternativeGroupImpl <em>Processor Alternative Group</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see de.cau.cs.kieler.kicool.impl.ProcessorAlternativeGroupImpl
     * @see de.cau.cs.kieler.kicool.impl.KiCoolPackageImpl#getProcessorAlternativeGroup()
     * @generated
     */
    int PROCESSOR_ALTERNATIVE_GROUP = 5;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_ALTERNATIVE_GROUP__ID = PROCESSOR_GROUP__ID;

    /**
     * The feature id for the '<em><b>Processors</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_ALTERNATIVE_GROUP__PROCESSORS = PROCESSOR_GROUP__PROCESSORS;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_ALTERNATIVE_GROUP__LABEL = PROCESSOR_GROUP__LABEL;

    /**
     * The number of structural features of the '<em>Processor Alternative Group</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESSOR_ALTERNATIVE_GROUP_FEATURE_COUNT = PROCESSOR_GROUP_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link de.cau.cs.kieler.kicool.impl.IntermediateReferenceImpl <em>Intermediate Reference</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see de.cau.cs.kieler.kicool.impl.IntermediateReferenceImpl
     * @see de.cau.cs.kieler.kicool.impl.KiCoolPackageImpl#getIntermediateReference()
     * @generated
     */
    int INTERMEDIATE_REFERENCE = 6;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_REFERENCE__ID = PROCESSOR_REFERENCE__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_REFERENCE__LABEL = PROCESSOR_REFERENCE__LABEL;

    /**
     * The feature id for the '<em><b>Preconfig</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_REFERENCE__PRECONFIG = PROCESSOR_REFERENCE__PRECONFIG;

    /**
     * The feature id for the '<em><b>Postconfig</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_REFERENCE__POSTCONFIG = PROCESSOR_REFERENCE__POSTCONFIG;

    /**
     * The feature id for the '<em><b>Metric</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_REFERENCE__METRIC = PROCESSOR_REFERENCE__METRIC;

    /**
     * The feature id for the '<em><b>Preprocesses</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_REFERENCE__PREPROCESSES = PROCESSOR_REFERENCE__PREPROCESSES;

    /**
     * The feature id for the '<em><b>Postprocesses</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_REFERENCE__POSTPROCESSES = PROCESSOR_REFERENCE__POSTPROCESSES;

    /**
     * The feature id for the '<em><b>Silent</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_REFERENCE__SILENT = PROCESSOR_REFERENCE__SILENT;

    /**
     * The feature id for the '<em><b>Alias</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_REFERENCE__ALIAS = PROCESSOR_REFERENCE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Intermediate Reference</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_REFERENCE_FEATURE_COUNT = PROCESSOR_REFERENCE_FEATURE_COUNT + 1;

    /**
     * Returns the meta object for class '{@link de.cau.cs.kieler.kicool.System <em>System</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>System</em>'.
     * @see de.cau.cs.kieler.kicool.System
     * @generated
     */
    EClass getSystem();

    /**
     * Returns the meta object for the attribute '{@link de.cau.cs.kieler.kicool.System#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see de.cau.cs.kieler.kicool.System#getId()
     * @see #getSystem()
     * @generated
     */
    EAttribute getSystem_Id();

    /**
     * Returns the meta object for the attribute '{@link de.cau.cs.kieler.kicool.System#getLabel <em>Label</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Label</em>'.
     * @see de.cau.cs.kieler.kicool.System#getLabel()
     * @see #getSystem()
     * @generated
     */
    EAttribute getSystem_Label();

    /**
     * Returns the meta object for the containment reference '{@link de.cau.cs.kieler.kicool.System#getProcessors <em>Processors</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Processors</em>'.
     * @see de.cau.cs.kieler.kicool.System#getProcessors()
     * @see #getSystem()
     * @generated
     */
    EReference getSystem_Processors();

    /**
     * Returns the meta object for the containment reference list '{@link de.cau.cs.kieler.kicool.System#getIntermediates <em>Intermediates</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Intermediates</em>'.
     * @see de.cau.cs.kieler.kicool.System#getIntermediates()
     * @see #getSystem()
     * @generated
     */
    EReference getSystem_Intermediates();

    /**
     * Returns the meta object for the containment reference '{@link de.cau.cs.kieler.kicool.System#getConfig <em>Config</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Config</em>'.
     * @see de.cau.cs.kieler.kicool.System#getConfig()
     * @see #getSystem()
     * @generated
     */
    EReference getSystem_Config();

    /**
     * Returns the meta object for the containment reference '{@link de.cau.cs.kieler.kicool.System#getStartConfig <em>Start Config</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Start Config</em>'.
     * @see de.cau.cs.kieler.kicool.System#getStartConfig()
     * @see #getSystem()
     * @generated
     */
    EReference getSystem_StartConfig();

    /**
     * Returns the meta object for the attribute '{@link de.cau.cs.kieler.kicool.System#isPublic <em>Public</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Public</em>'.
     * @see de.cau.cs.kieler.kicool.System#isPublic()
     * @see #getSystem()
     * @generated
     */
    EAttribute getSystem_Public();

    /**
     * Returns the meta object for the attribute '{@link de.cau.cs.kieler.kicool.System#isDeveloper <em>Developer</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Developer</em>'.
     * @see de.cau.cs.kieler.kicool.System#isDeveloper()
     * @see #getSystem()
     * @generated
     */
    EAttribute getSystem_Developer();

    /**
     * Returns the meta object for the attribute '{@link de.cau.cs.kieler.kicool.System#isSimulation <em>Simulation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Simulation</em>'.
     * @see de.cau.cs.kieler.kicool.System#isSimulation()
     * @see #getSystem()
     * @generated
     */
    EAttribute getSystem_Simulation();

    /**
     * Returns the meta object for class '{@link de.cau.cs.kieler.kicool.ProcessorEntry <em>Processor Entry</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Processor Entry</em>'.
     * @see de.cau.cs.kieler.kicool.ProcessorEntry
     * @generated
     */
    EClass getProcessorEntry();

    /**
     * Returns the meta object for the attribute '{@link de.cau.cs.kieler.kicool.ProcessorEntry#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see de.cau.cs.kieler.kicool.ProcessorEntry#getId()
     * @see #getProcessorEntry()
     * @generated
     */
    EAttribute getProcessorEntry_Id();

    /**
     * Returns the meta object for class '{@link de.cau.cs.kieler.kicool.ProcessorReference <em>Processor Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Processor Reference</em>'.
     * @see de.cau.cs.kieler.kicool.ProcessorReference
     * @generated
     */
    EClass getProcessorReference();

    /**
     * Returns the meta object for the attribute '{@link de.cau.cs.kieler.kicool.ProcessorReference#getLabel <em>Label</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Label</em>'.
     * @see de.cau.cs.kieler.kicool.ProcessorReference#getLabel()
     * @see #getProcessorReference()
     * @generated
     */
    EAttribute getProcessorReference_Label();

    /**
     * Returns the meta object for the containment reference '{@link de.cau.cs.kieler.kicool.ProcessorReference#getPreconfig <em>Preconfig</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Preconfig</em>'.
     * @see de.cau.cs.kieler.kicool.ProcessorReference#getPreconfig()
     * @see #getProcessorReference()
     * @generated
     */
    EReference getProcessorReference_Preconfig();

    /**
     * Returns the meta object for the containment reference '{@link de.cau.cs.kieler.kicool.ProcessorReference#getPostconfig <em>Postconfig</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Postconfig</em>'.
     * @see de.cau.cs.kieler.kicool.ProcessorReference#getPostconfig()
     * @see #getProcessorReference()
     * @generated
     */
    EReference getProcessorReference_Postconfig();

    /**
     * Returns the meta object for the reference '{@link de.cau.cs.kieler.kicool.ProcessorReference#getMetric <em>Metric</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Metric</em>'.
     * @see de.cau.cs.kieler.kicool.ProcessorReference#getMetric()
     * @see #getProcessorReference()
     * @generated
     */
    EReference getProcessorReference_Metric();

    /**
     * Returns the meta object for the containment reference list '{@link de.cau.cs.kieler.kicool.ProcessorReference#getPreprocesses <em>Preprocesses</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Preprocesses</em>'.
     * @see de.cau.cs.kieler.kicool.ProcessorReference#getPreprocesses()
     * @see #getProcessorReference()
     * @generated
     */
    EReference getProcessorReference_Preprocesses();

    /**
     * Returns the meta object for the containment reference list '{@link de.cau.cs.kieler.kicool.ProcessorReference#getPostprocesses <em>Postprocesses</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Postprocesses</em>'.
     * @see de.cau.cs.kieler.kicool.ProcessorReference#getPostprocesses()
     * @see #getProcessorReference()
     * @generated
     */
    EReference getProcessorReference_Postprocesses();

    /**
     * Returns the meta object for the attribute '{@link de.cau.cs.kieler.kicool.ProcessorReference#isSilent <em>Silent</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Silent</em>'.
     * @see de.cau.cs.kieler.kicool.ProcessorReference#isSilent()
     * @see #getProcessorReference()
     * @generated
     */
    EAttribute getProcessorReference_Silent();

    /**
     * Returns the meta object for class '{@link de.cau.cs.kieler.kicool.ProcessorSystem <em>Processor System</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Processor System</em>'.
     * @see de.cau.cs.kieler.kicool.ProcessorSystem
     * @generated
     */
    EClass getProcessorSystem();

    /**
     * Returns the meta object for class '{@link de.cau.cs.kieler.kicool.ProcessorGroup <em>Processor Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Processor Group</em>'.
     * @see de.cau.cs.kieler.kicool.ProcessorGroup
     * @generated
     */
    EClass getProcessorGroup();

    /**
     * Returns the meta object for the containment reference list '{@link de.cau.cs.kieler.kicool.ProcessorGroup#getProcessors <em>Processors</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Processors</em>'.
     * @see de.cau.cs.kieler.kicool.ProcessorGroup#getProcessors()
     * @see #getProcessorGroup()
     * @generated
     */
    EReference getProcessorGroup_Processors();

    /**
     * Returns the meta object for the attribute '{@link de.cau.cs.kieler.kicool.ProcessorGroup#getLabel <em>Label</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Label</em>'.
     * @see de.cau.cs.kieler.kicool.ProcessorGroup#getLabel()
     * @see #getProcessorGroup()
     * @generated
     */
    EAttribute getProcessorGroup_Label();

    /**
     * Returns the meta object for class '{@link de.cau.cs.kieler.kicool.ProcessorAlternativeGroup <em>Processor Alternative Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Processor Alternative Group</em>'.
     * @see de.cau.cs.kieler.kicool.ProcessorAlternativeGroup
     * @generated
     */
    EClass getProcessorAlternativeGroup();

    /**
     * Returns the meta object for class '{@link de.cau.cs.kieler.kicool.IntermediateReference <em>Intermediate Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Intermediate Reference</em>'.
     * @see de.cau.cs.kieler.kicool.IntermediateReference
     * @generated
     */
    EClass getIntermediateReference();

    /**
     * Returns the meta object for the attribute '{@link de.cau.cs.kieler.kicool.IntermediateReference#getAlias <em>Alias</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Alias</em>'.
     * @see de.cau.cs.kieler.kicool.IntermediateReference#getAlias()
     * @see #getIntermediateReference()
     * @generated
     */
    EAttribute getIntermediateReference_Alias();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    KiCoolFactory getKiCoolFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each operation of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link de.cau.cs.kieler.kicool.impl.SystemImpl <em>System</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see de.cau.cs.kieler.kicool.impl.SystemImpl
         * @see de.cau.cs.kieler.kicool.impl.KiCoolPackageImpl#getSystem()
         * @generated
         */
        EClass SYSTEM = eINSTANCE.getSystem();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SYSTEM__ID = eINSTANCE.getSystem_Id();

        /**
         * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SYSTEM__LABEL = eINSTANCE.getSystem_Label();

        /**
         * The meta object literal for the '<em><b>Processors</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SYSTEM__PROCESSORS = eINSTANCE.getSystem_Processors();

        /**
         * The meta object literal for the '<em><b>Intermediates</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SYSTEM__INTERMEDIATES = eINSTANCE.getSystem_Intermediates();

        /**
         * The meta object literal for the '<em><b>Config</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SYSTEM__CONFIG = eINSTANCE.getSystem_Config();

        /**
         * The meta object literal for the '<em><b>Start Config</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SYSTEM__START_CONFIG = eINSTANCE.getSystem_StartConfig();

        /**
         * The meta object literal for the '<em><b>Public</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SYSTEM__PUBLIC = eINSTANCE.getSystem_Public();

        /**
         * The meta object literal for the '<em><b>Developer</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SYSTEM__DEVELOPER = eINSTANCE.getSystem_Developer();

        /**
         * The meta object literal for the '<em><b>Simulation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SYSTEM__SIMULATION = eINSTANCE.getSystem_Simulation();

        /**
         * The meta object literal for the '{@link de.cau.cs.kieler.kicool.impl.ProcessorEntryImpl <em>Processor Entry</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see de.cau.cs.kieler.kicool.impl.ProcessorEntryImpl
         * @see de.cau.cs.kieler.kicool.impl.KiCoolPackageImpl#getProcessorEntry()
         * @generated
         */
        EClass PROCESSOR_ENTRY = eINSTANCE.getProcessorEntry();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROCESSOR_ENTRY__ID = eINSTANCE.getProcessorEntry_Id();

        /**
         * The meta object literal for the '{@link de.cau.cs.kieler.kicool.impl.ProcessorReferenceImpl <em>Processor Reference</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see de.cau.cs.kieler.kicool.impl.ProcessorReferenceImpl
         * @see de.cau.cs.kieler.kicool.impl.KiCoolPackageImpl#getProcessorReference()
         * @generated
         */
        EClass PROCESSOR_REFERENCE = eINSTANCE.getProcessorReference();

        /**
         * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROCESSOR_REFERENCE__LABEL = eINSTANCE.getProcessorReference_Label();

        /**
         * The meta object literal for the '<em><b>Preconfig</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESSOR_REFERENCE__PRECONFIG = eINSTANCE.getProcessorReference_Preconfig();

        /**
         * The meta object literal for the '<em><b>Postconfig</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESSOR_REFERENCE__POSTCONFIG = eINSTANCE.getProcessorReference_Postconfig();

        /**
         * The meta object literal for the '<em><b>Metric</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESSOR_REFERENCE__METRIC = eINSTANCE.getProcessorReference_Metric();

        /**
         * The meta object literal for the '<em><b>Preprocesses</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESSOR_REFERENCE__PREPROCESSES = eINSTANCE.getProcessorReference_Preprocesses();

        /**
         * The meta object literal for the '<em><b>Postprocesses</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESSOR_REFERENCE__POSTPROCESSES = eINSTANCE.getProcessorReference_Postprocesses();

        /**
         * The meta object literal for the '<em><b>Silent</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROCESSOR_REFERENCE__SILENT = eINSTANCE.getProcessorReference_Silent();

        /**
         * The meta object literal for the '{@link de.cau.cs.kieler.kicool.impl.ProcessorSystemImpl <em>Processor System</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see de.cau.cs.kieler.kicool.impl.ProcessorSystemImpl
         * @see de.cau.cs.kieler.kicool.impl.KiCoolPackageImpl#getProcessorSystem()
         * @generated
         */
        EClass PROCESSOR_SYSTEM = eINSTANCE.getProcessorSystem();

        /**
         * The meta object literal for the '{@link de.cau.cs.kieler.kicool.impl.ProcessorGroupImpl <em>Processor Group</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see de.cau.cs.kieler.kicool.impl.ProcessorGroupImpl
         * @see de.cau.cs.kieler.kicool.impl.KiCoolPackageImpl#getProcessorGroup()
         * @generated
         */
        EClass PROCESSOR_GROUP = eINSTANCE.getProcessorGroup();

        /**
         * The meta object literal for the '<em><b>Processors</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESSOR_GROUP__PROCESSORS = eINSTANCE.getProcessorGroup_Processors();

        /**
         * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROCESSOR_GROUP__LABEL = eINSTANCE.getProcessorGroup_Label();

        /**
         * The meta object literal for the '{@link de.cau.cs.kieler.kicool.impl.ProcessorAlternativeGroupImpl <em>Processor Alternative Group</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see de.cau.cs.kieler.kicool.impl.ProcessorAlternativeGroupImpl
         * @see de.cau.cs.kieler.kicool.impl.KiCoolPackageImpl#getProcessorAlternativeGroup()
         * @generated
         */
        EClass PROCESSOR_ALTERNATIVE_GROUP = eINSTANCE.getProcessorAlternativeGroup();

        /**
         * The meta object literal for the '{@link de.cau.cs.kieler.kicool.impl.IntermediateReferenceImpl <em>Intermediate Reference</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see de.cau.cs.kieler.kicool.impl.IntermediateReferenceImpl
         * @see de.cau.cs.kieler.kicool.impl.KiCoolPackageImpl#getIntermediateReference()
         * @generated
         */
        EClass INTERMEDIATE_REFERENCE = eINSTANCE.getIntermediateReference();

        /**
         * The meta object literal for the '<em><b>Alias</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute INTERMEDIATE_REFERENCE__ALIAS = eINSTANCE.getIntermediateReference_Alias();

    }

} //KiCoolPackage
