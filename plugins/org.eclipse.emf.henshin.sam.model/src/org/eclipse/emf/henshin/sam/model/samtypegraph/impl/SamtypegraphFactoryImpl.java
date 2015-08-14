/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtypegraph.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.henshin.sam.model.samtypegraph.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SamtypegraphFactoryImpl extends EFactoryImpl implements SamtypegraphFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SamtypegraphFactory init() {
		try {
			SamtypegraphFactory theSamtypegraphFactory = (SamtypegraphFactory)EPackage.Registry.INSTANCE.getEFactory(SamtypegraphPackage.eNS_URI);
			if (theSamtypegraphFactory != null) {
				return theSamtypegraphFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SamtypegraphFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SamtypegraphFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case SamtypegraphPackage.NODE_TYPE: return createNodeType();
			case SamtypegraphPackage.EDGE_TYPE: return createEdgeType();
			case SamtypegraphPackage.ATTRIBUTE_TYPE: return createAttributeType();
			case SamtypegraphPackage.CONTINUOUS_ATTR: return createContinuousAttr();
			case SamtypegraphPackage.INHERITANCE_RELATION: return createInheritanceRelation();
			case SamtypegraphPackage.TYPE_GRAPH: return createTypeGraph();
			case SamtypegraphPackage.ATTRIBUTE_TYPE_CONDITION: return createAttributeTypeCondition();
			case SamtypegraphPackage.CARDINALITY: return createCardinality();
			case SamtypegraphPackage.DATA_TYPE: return createDataType();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case SamtypegraphPackage.EDGE_DIRECTION:
				return createEdgeDirectionFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case SamtypegraphPackage.EDGE_DIRECTION:
				return convertEdgeDirectionToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeType createNodeType() {
		NodeTypeImpl nodeType = new NodeTypeImpl();
		return nodeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EdgeType createEdgeType() {
		EdgeTypeImpl edgeType = new EdgeTypeImpl();
		return edgeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeType createAttributeType() {
		AttributeTypeImpl attributeType = new AttributeTypeImpl();
		return attributeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContinuousAttr createContinuousAttr() {
		ContinuousAttrImpl continuousAttr = new ContinuousAttrImpl();
		return continuousAttr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InheritanceRelation createInheritanceRelation() {
		InheritanceRelationImpl inheritanceRelation = new InheritanceRelationImpl();
		return inheritanceRelation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeGraph createTypeGraph() {
		TypeGraphImpl typeGraph = new TypeGraphImpl();
		return typeGraph;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeTypeCondition createAttributeTypeCondition() {
		AttributeTypeConditionImpl attributeTypeCondition = new AttributeTypeConditionImpl();
		return attributeTypeCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Cardinality createCardinality() {
		CardinalityImpl cardinality = new CardinalityImpl();
		return cardinality;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataType createDataType() {
		DataTypeImpl dataType = new DataTypeImpl();
		return dataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EdgeDirection createEdgeDirectionFromString(EDataType eDataType, String initialValue) {
		EdgeDirection result = EdgeDirection.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEdgeDirectionToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SamtypegraphPackage getSamtypegraphPackage() {
		return (SamtypegraphPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SamtypegraphPackage getPackage() {
		return SamtypegraphPackage.eINSTANCE;
	}

} //SamtypegraphFactoryImpl
