/**
 * generated by Xtext 2.10.0
 */
package de.cau.cs.kieler.cview.model.cViewModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Component Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see de.cau.cs.kieler.cview.model.cViewModel.CViewModelPackage#getComponentType()
 * @model
 * @generated
 */
public enum ComponentType implements Enumerator
{
  /**
   * The '<em><b>DIR</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #DIR_VALUE
   * @generated
   * @ordered
   */
  DIR(0, "DIR", "DIR"),

  /**
   * The '<em><b>FILE</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #FILE_VALUE
   * @generated
   * @ordered
   */
  FILE(1, "FILE", "FILE"),

  /**
   * The '<em><b>FUNC</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #FUNC_VALUE
   * @generated
   * @ordered
   */
  FUNC(2, "FUNC", "FUNC"),

  /**
   * The '<em><b>COMPOUND</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #COMPOUND_VALUE
   * @generated
   * @ordered
   */
  COMPOUND(3, "COMPOUND", "COMPOUND"),

  /**
   * The '<em><b>READER</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #READER_VALUE
   * @generated
   * @ordered
   */
  READER(4, "READER", "READER"),

  /**
   * The '<em><b>WRITER</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #WRITER_VALUE
   * @generated
   * @ordered
   */
  WRITER(5, "WRITER", "WRITER");

  /**
   * The '<em><b>DIR</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>DIR</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #DIR
   * @model
   * @generated
   * @ordered
   */
  public static final int DIR_VALUE = 0;

  /**
   * The '<em><b>FILE</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>FILE</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #FILE
   * @model
   * @generated
   * @ordered
   */
  public static final int FILE_VALUE = 1;

  /**
   * The '<em><b>FUNC</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>FUNC</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #FUNC
   * @model
   * @generated
   * @ordered
   */
  public static final int FUNC_VALUE = 2;

  /**
   * The '<em><b>COMPOUND</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>COMPOUND</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #COMPOUND
   * @model
   * @generated
   * @ordered
   */
  public static final int COMPOUND_VALUE = 3;

  /**
   * The '<em><b>READER</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>READER</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #READER
   * @model
   * @generated
   * @ordered
   */
  public static final int READER_VALUE = 4;

  /**
   * The '<em><b>WRITER</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>WRITER</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #WRITER
   * @model
   * @generated
   * @ordered
   */
  public static final int WRITER_VALUE = 5;

  /**
   * An array of all the '<em><b>Component Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final ComponentType[] VALUES_ARRAY =
    new ComponentType[]
    {
      DIR,
      FILE,
      FUNC,
      COMPOUND,
      READER,
      WRITER,
    };

  /**
   * A public read-only list of all the '<em><b>Component Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<ComponentType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Component Type</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param literal the literal.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static ComponentType get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      ComponentType result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Component Type</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param name the name.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static ComponentType getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      ComponentType result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Component Type</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the integer value.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static ComponentType get(int value)
  {
    switch (value)
    {
      case DIR_VALUE: return DIR;
      case FILE_VALUE: return FILE;
      case FUNC_VALUE: return FUNC;
      case COMPOUND_VALUE: return COMPOUND;
      case READER_VALUE: return READER;
      case WRITER_VALUE: return WRITER;
    }
    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final int value;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final String name;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final String literal;

  /**
   * Only this class can construct instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private ComponentType(int value, String name, String literal)
  {
    this.value = value;
    this.name = name;
    this.literal = literal;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getValue()
  {
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getLiteral()
  {
    return literal;
  }

  /**
   * Returns the literal value of the enumerator, which is its string representation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    return literal;
  }
  
} //ComponentType