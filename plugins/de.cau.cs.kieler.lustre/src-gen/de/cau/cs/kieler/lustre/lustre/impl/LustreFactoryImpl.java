/**
 * generated by Xtext 2.10.0
 */
package de.cau.cs.kieler.lustre.lustre.impl;

import de.cau.cs.kieler.lustre.lustre.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LustreFactoryImpl extends EFactoryImpl implements LustreFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static LustreFactory init()
  {
    try
    {
      LustreFactory theLustreFactory = (LustreFactory)EPackage.Registry.INSTANCE.getEFactory(LustrePackage.eNS_URI);
      if (theLustreFactory != null)
      {
        return theLustreFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new LustreFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LustreFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case LustrePackage.LUSTRE_PROGRAM: return createLustreProgram();
      case LustrePackage.PACKAGE_DECLARATION: return createPackage_Declaration();
      case LustrePackage.PACKAGE_PROVIDED: return createPackage_Provided();
      case LustrePackage.PACKAGE_PROVIDED_IO: return createPackage_Provided_IO();
      case LustrePackage.ENTITY_DECLARATION: return createEntity_Declaration();
      case LustrePackage.TYPE_DECLARATION: return createType_Declaration();
      case LustrePackage.TYPE: return createType();
      case LustrePackage.ARRAY_TYPE: return createArray_Type();
      case LustrePackage.RECORD_TYPE: return createRecord_Type();
      case LustrePackage.FIELD: return createField();
      case LustrePackage.CONSTANT_DECLARATION: return createConstant_Declaration();
      case LustrePackage.VARIABLE_DECLARATION: return createVariable_Declaration();
      case LustrePackage.NODE_DECLARATION: return createNode_Declaration();
      case LustrePackage.EQUATION: return createEquation();
      case LustrePackage.ASSERTION: return createAssertion();
      case LustrePackage.AUTOMATON: return createAutomaton();
      case LustrePackage.ASTATE: return createAState();
      case LustrePackage.ATRANSITION: return createATransition();
      case LustrePackage.LEFT_PART: return createLeft_Part();
      case LustrePackage.LEFT_LIST: return createLeft_List();
      case LustrePackage.LEFT: return createLeft();
      case LustrePackage.SELECTOR: return createSelector();
      case LustrePackage.EXPRESSION: return createExpression();
      case LustrePackage.VARIABLE_REFERENCE: return createVariableReference();
      case LustrePackage.IF_THEN_ELSE: return createIfThenElse();
      case LustrePackage.FBY: return createFby();
      case LustrePackage.ARROW: return createArrow();
      case LustrePackage.OR: return createOr();
      case LustrePackage.AND: return createAnd();
      case LustrePackage.EQUALITY: return createEquality();
      case LustrePackage.COMPARISON: return createComparison();
      case LustrePackage.MOD: return createMod();
      case LustrePackage.PLUS: return createPlus();
      case LustrePackage.MINUS: return createMinus();
      case LustrePackage.MUL: return createMul();
      case LustrePackage.DIV: return createDiv();
      case LustrePackage.NOT: return createNot();
      case LustrePackage.UMINUS: return createUMinus();
      case LustrePackage.PRE: return createPre();
      case LustrePackage.CURRENT: return createCurrent();
      case LustrePackage.BOOL_CONSTANT: return createBoolConstant();
      case LustrePackage.FLOAT_CONSTANT: return createFloatConstant();
      case LustrePackage.INT_CONSTANT: return createIntConstant();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LustreProgram createLustreProgram()
  {
    LustreProgramImpl lustreProgram = new LustreProgramImpl();
    return lustreProgram;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Package_Declaration createPackage_Declaration()
  {
    Package_DeclarationImpl package_Declaration = new Package_DeclarationImpl();
    return package_Declaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Package_Provided createPackage_Provided()
  {
    Package_ProvidedImpl package_Provided = new Package_ProvidedImpl();
    return package_Provided;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Package_Provided_IO createPackage_Provided_IO()
  {
    Package_Provided_IOImpl package_Provided_IO = new Package_Provided_IOImpl();
    return package_Provided_IO;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Entity_Declaration createEntity_Declaration()
  {
    Entity_DeclarationImpl entity_Declaration = new Entity_DeclarationImpl();
    return entity_Declaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Type_Declaration createType_Declaration()
  {
    Type_DeclarationImpl type_Declaration = new Type_DeclarationImpl();
    return type_Declaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Type createType()
  {
    TypeImpl type = new TypeImpl();
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Array_Type createArray_Type()
  {
    Array_TypeImpl array_Type = new Array_TypeImpl();
    return array_Type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Record_Type createRecord_Type()
  {
    Record_TypeImpl record_Type = new Record_TypeImpl();
    return record_Type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Field createField()
  {
    FieldImpl field = new FieldImpl();
    return field;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Constant_Declaration createConstant_Declaration()
  {
    Constant_DeclarationImpl constant_Declaration = new Constant_DeclarationImpl();
    return constant_Declaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Variable_Declaration createVariable_Declaration()
  {
    Variable_DeclarationImpl variable_Declaration = new Variable_DeclarationImpl();
    return variable_Declaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Node_Declaration createNode_Declaration()
  {
    Node_DeclarationImpl node_Declaration = new Node_DeclarationImpl();
    return node_Declaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Equation createEquation()
  {
    EquationImpl equation = new EquationImpl();
    return equation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Assertion createAssertion()
  {
    AssertionImpl assertion = new AssertionImpl();
    return assertion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Automaton createAutomaton()
  {
    AutomatonImpl automaton = new AutomatonImpl();
    return automaton;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AState createAState()
  {
    AStateImpl aState = new AStateImpl();
    return aState;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ATransition createATransition()
  {
    ATransitionImpl aTransition = new ATransitionImpl();
    return aTransition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Left_Part createLeft_Part()
  {
    Left_PartImpl left_Part = new Left_PartImpl();
    return left_Part;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Left_List createLeft_List()
  {
    Left_ListImpl left_List = new Left_ListImpl();
    return left_List;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Left createLeft()
  {
    LeftImpl left = new LeftImpl();
    return left;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Selector createSelector()
  {
    SelectorImpl selector = new SelectorImpl();
    return selector;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expression createExpression()
  {
    ExpressionImpl expression = new ExpressionImpl();
    return expression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VariableReference createVariableReference()
  {
    VariableReferenceImpl variableReference = new VariableReferenceImpl();
    return variableReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IfThenElse createIfThenElse()
  {
    IfThenElseImpl ifThenElse = new IfThenElseImpl();
    return ifThenElse;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Fby createFby()
  {
    FbyImpl fby = new FbyImpl();
    return fby;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Arrow createArrow()
  {
    ArrowImpl arrow = new ArrowImpl();
    return arrow;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Or createOr()
  {
    OrImpl or = new OrImpl();
    return or;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public And createAnd()
  {
    AndImpl and = new AndImpl();
    return and;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Equality createEquality()
  {
    EqualityImpl equality = new EqualityImpl();
    return equality;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Comparison createComparison()
  {
    ComparisonImpl comparison = new ComparisonImpl();
    return comparison;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Mod createMod()
  {
    ModImpl mod = new ModImpl();
    return mod;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Plus createPlus()
  {
    PlusImpl plus = new PlusImpl();
    return plus;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Minus createMinus()
  {
    MinusImpl minus = new MinusImpl();
    return minus;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Mul createMul()
  {
    MulImpl mul = new MulImpl();
    return mul;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Div createDiv()
  {
    DivImpl div = new DivImpl();
    return div;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Not createNot()
  {
    NotImpl not = new NotImpl();
    return not;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public UMinus createUMinus()
  {
    UMinusImpl uMinus = new UMinusImpl();
    return uMinus;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Pre createPre()
  {
    PreImpl pre = new PreImpl();
    return pre;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Current createCurrent()
  {
    CurrentImpl current = new CurrentImpl();
    return current;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BoolConstant createBoolConstant()
  {
    BoolConstantImpl boolConstant = new BoolConstantImpl();
    return boolConstant;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FloatConstant createFloatConstant()
  {
    FloatConstantImpl floatConstant = new FloatConstantImpl();
    return floatConstant;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IntConstant createIntConstant()
  {
    IntConstantImpl intConstant = new IntConstantImpl();
    return intConstant;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LustrePackage getLustrePackage()
  {
    return (LustrePackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static LustrePackage getPackage()
  {
    return LustrePackage.eINSTANCE;
  }

} //LustreFactoryImpl