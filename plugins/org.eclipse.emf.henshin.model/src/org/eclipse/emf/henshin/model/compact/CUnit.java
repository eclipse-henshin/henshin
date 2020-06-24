package org.eclipse.emf.henshin.model.compact;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterKind;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.Unit;

/**
 * Compact Unit
 * Contains an {@link org.eclipse.emf.henshin.model.Unit}-Instance
 * Provides compact methods for basic operations.
 * Serves as Superclass for Compact Rules.
 * @author Johannes Ludwig
 */
public class CUnit {

	private Unit unit;
	
	protected CUnit(Unit unit) {
		this.unit = unit;
	}
		
	/**
	 * Creates a Parameter
	 * @param kind The ParameterKind Literal. e.g. ParameterKind.VAR. Can use a String instead.
	 * @param type The DataType of the Parameter
	 * @param name Name of the Parameter
	 */
	public CUnit createParameter(ParameterKind kind, String name, EClassifier type) {
		if(kind == null)
		{
			throw new RuntimeException("No valid ParameterKind was given");
		}
		Parameter p = HenshinFactory.eINSTANCE.createParameter();
		p.setName(name);
		p.setKind(kind);
		p.setType(type);
		unit.getParameters().add(p);
		
		return this;
	}
	
	/**
	 * createParameter using a String to specifiy the ParameterType out of the Ecore-Imports of the Module
	 */
	public CUnit createParameter(ParameterKind kind, String name, String... type) {
		return createParameter(kind, name, getEClassByName(type));
	}
	
	/**
	 * createParameter using a String to specify the {@link org.eclipse.emf.henshin.model.ParameterKind}
	 */
	public CUnit createParameter(String kind, String name, EDataType type) {
		return createParameter(ParameterKind.getByString(kind), name, type);
	}
	
	/**
	 * createParameter using a String to specifiy the ParameterKind and the ParameterType.
	 */
	public CUnit createParameter(String kind, String name, String... type) {
		return createParameter(ParameterKind.getByString(kind), name, getEClassByName(type));
	}

	/**
	 * Maps a Parameter from this Unit to a specified Parameter of a specified Subunit
	 * @param from Parameter in this Unit
	 * @param to Parameter in the targeted Unit
	 * @param targetUnit the targeted Unit
	 */
	public CUnit mapParameterToSubunit(String from, String targetUnit, String to) {
		EList<Unit> subs = unit.getSubUnits(false);
		Unit sub = null;
		for(Unit u : subs) {
			if(u.getName().equals(targetUnit)) {
				sub = u;
			}
		}
		if(sub == null) {
			throw new RuntimeException("Unit: "+targetUnit+" not found");
		}
		ParameterMapping mapping = HenshinFactory.eINSTANCE.createParameterMapping();
		
		Parameter fromParam = unit.getParameter(from);
		if(fromParam == null) {
			throw new RuntimeException("Parameter: "+from+" not found");
		}
		Parameter toParam = sub.getParameter(to);
		if(toParam == null) {
			throw new RuntimeException("Parameter: "+to+" in "+targetUnit+" not found");
		}
		
		if(!fromParam.getType().equals(toParam.getType())) {
			throw new RuntimeException("Parameters do not have equal Types");
		}
		
		mapping.setSource(fromParam);
		mapping.setTarget(toParam);
		unit.getParameterMappings().add(mapping);
		return this;
	}
		
	public Unit getUnit() {
		return unit;
	}
	
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/** 
	 * @return EClass with the specified Name
	 */
	protected EClass getEClassByName(String... name) {
		EClass result = null;
		String packURI = "";
		if(name.length>1)
		{
			packURI = name[1];
		}
		
		for(EPackage pack : getUnit().getModule().getImports())
		{
			if(packURI.equals("") || packURI.equals(pack.getNsURI()))
			{
				result = (EClass) pack.getEClassifier(name[0]);
			}
		}
		if(result == null) {
			throw new RuntimeException("No Classifier for "+name+" found.");
		}
		return result;
	}

}
