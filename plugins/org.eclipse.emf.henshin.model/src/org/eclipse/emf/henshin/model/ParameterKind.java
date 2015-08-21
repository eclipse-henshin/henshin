/**
 */
package org.eclipse.emf.henshin.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Parameter Kind</b></em>'
 * and utility methods to work with them. A literal object of this enumeration consists of the following fields:
 * <em>value</em>: The integer value of the literal
 * <em>name</em>: The name of the literal
 * <em>literal</em>: The String representation of the literal
 * <em>alias</em>: An alias that can be used to tie user input to the literal. By default alias will be the <em>name</em> in lower case
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getParameterKind()
 * @model
 * @generated
 */
public enum ParameterKind implements Enumerator {
	/**
	 * The '<em><b>UNKNOWN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNKNOWN_VALUE
	 * @generated NOT
	 * This is necessary to be able to set the alias to "?"
	 * @ordered
	 */
	UNKNOWN(0, "UNKNOWN", "UNKNOWN", "?"),

	/**
	 * The '<em><b>IN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #IN_VALUE
	 * @generated
	 * @ordered
	 */
	IN(1, "IN", "IN"),

	/**
	 * The '<em><b>OUT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OUT_VALUE
	 * @generated
	 * @ordered
	 */
	OUT(2, "OUT", "OUT"),

	/**
	 * The '<em><b>INOUT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INOUT_VALUE
	 * @generated
	 * @ordered
	 */
	INOUT(3, "INOUT", "INOUT"),

	/**
	 * The '<em><b>VAR</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VAR_VALUE
	 * @generated
	 * @ordered
	 */
	VAR(4, "VAR", "VAR");

	/**
	 * The '<em><b>UNKNOWN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>UNKNOWN</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UNKNOWN
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int UNKNOWN_VALUE = 0;

	/**
	 * The '<em><b>IN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>IN</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #IN
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int IN_VALUE = 1;

	/**
	 * The '<em><b>OUT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>OUT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OUT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OUT_VALUE = 2;

	/**
	 * The '<em><b>INOUT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>INOUT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INOUT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int INOUT_VALUE = 3;

	/**
	 * The '<em><b>VAR</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>VAR</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #VAR
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int VAR_VALUE = 4;

	/**
	 * An array of all the '<em><b>Parameter Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ParameterKind[] VALUES_ARRAY =
		new ParameterKind[] {
			UNKNOWN,
			IN,
			OUT,
			INOUT,
			VAR,
		};
		
	/**
	 * A public read-only list of all the '<em><b>Parameter Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ParameterKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Parameter Kind</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ParameterKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ParameterKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Parameter Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ParameterKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ParameterKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}
	
	/**
	 * Returns the '<em><b>Parameter Kind</b></em>' literal with the specified name or alias.
	 * @param string the name or alias
	 * @return the '<em><b>Parameter Kind</b></em>' with the specified name or alias
	 */
	public static ParameterKind getByString(String string) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ParameterKind result = VALUES_ARRAY[i];
			if (result.getName().equals(string) || result.getAlias().equals(string)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Parameter Kind</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ParameterKind get(int value) {
		switch (value) {
			case UNKNOWN_VALUE: return UNKNOWN;
			case IN_VALUE: return IN;
			case OUT_VALUE: return OUT;
			case INOUT_VALUE: return INOUT;
			case VAR_VALUE: return VAR;
		}
		return null;
	}
	
	/**
	 * This returns a regular expression alternation group containing all valid aliases or literals.
	 * Format: ( <code>alias1</code> | <code>literal1</code> | <code>alias2</code> | <code>literal2</code> | ... )
	 * @return the regular expression matching all valid string representations of kinds
	 */
	public static String getValidStringItemsRegex() {
		String result = "(";
		
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ParameterKind kind = VALUES_ARRAY[i];
			result += kind.getAlias();
			result += "|";
			result += kind.getLiteral();
			if(i < VALUES_ARRAY.length - 1) {
				result += "|";
			}
		}
		result += ")";
		return result;
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
	 * @generated NOT
	 */
	private ParameterKind(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
		this.alias = name.toLowerCase();
	}

	/**
	 * The alias that is associated with this literal
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	private final String alias;
	
	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	private ParameterKind(int value, String name, String literal, String alias) {
		this.value = value;
		this.name = name;
		this.literal = literal;
		this.alias = alias;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getAlias() {
	  return alias;
	}
	
	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //ParameterKind
