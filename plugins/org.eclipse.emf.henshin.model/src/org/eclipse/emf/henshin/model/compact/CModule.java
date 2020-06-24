package org.eclipse.emf.henshin.model.compact;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.IteratedUnit;
import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

/**
 * Compact Module.
 * Contains a {@link org.eclipse.emf.henshin.model.Module]-Instance 
 * Provides compact Methods to perform basic-operations on said Instance.
 * @author Johannes Ludwig
 */
public class CModule {

	private Module module;
	
	/**
	 * Creates a CModule as Facade for the given Module
	 * @param module the given Module-Instance
	 */
	public CModule(Module module) {
		setModule(module);
	}
	
	/**
	 * Creates a Module with the specified Name
	 * @param name Name of the Module
	 */
	public CModule(String name) {
		this(HenshinFactory.eINSTANCE.createModule());
		getModule().setName(name);
	}
	
	
	//Static Methods
	/**
	 * Loads a CModule from a *.henshin File
	 * @param filepath relative Filepath from working directory.
	 * @return the loaded CModule
	 */
	public static CModule loadFromFile(String filePath) {
		String[] path = filePath.split("/");
		String name = path[path.length-1];
		path[path.length-1] ="";
		HenshinResourceSet res = new HenshinResourceSet(String.join("/",path));
		Module mod = res.getModule(name);
		
		return new CModule(mod);
	}

	//Methods for Module-Management
	
	/**
	 * Adds a given EPackage to the Imports of the Module-Instance
	 * @param pack the Package to be imported.
	 */
	public CModule addImport(EPackage pack) {
		module.getImports().add(pack);
		new HenshinResourceSet().getPackageRegistry().putIfAbsent(pack.getNsURI(),pack);
		return this;
	}
	
	/**
	 * Adds an EPackage from an *.ecore-file to the Imports of the Module-Instance.
	 * registers the given Package in the local PackageRegistry.
	 * @param fileName The filename of the *.ecore-File
	 */
	public CModule addImportsFromFile(String filePath) {
		//Split the fileName into File-Path and File-Name
		String[] path = filePath.split("/");
		String name = path[path.length-1];
		path[path.length-1] ="";
		
		HenshinResourceSet res = new HenshinResourceSet(String.join("/",path));
		for(EPackage pack : res.registerDynamicEPackages(name))
		{
			addImport(pack);
		}
		return this;
	}
	
	/**
	 * Saves the Module-Instance at the given path in *.henshin format
	 * @param filename relative path coming from the ResourcePath of the Module
	 */
	public void save(String fileName) {
		String[] path = fileName.split("/");
		String name = path[path.length-1];
		path[path.length-1] ="";
		
		HenshinResourceSet res = new HenshinResourceSet(String.join("/",path));
		res.saveEObject(module, name+".henshin");
	}
	
	/**
	 * Saves the Module-Instance in a "Module-Name".henshin file.
	 */
	public void save() {
		save(module.getName());
	}
	
	//Methods for Unit-Management
	/**
	 * adds the given Rule into the Module and builds a CRule ontop of it.
	 * @param rule the given Rule
	 * @return the built CRule containing the given Rule.
	 */
	public CRule addRule(Rule rule) {
		CRule cr = new CRule(rule);
		module.getUnits().add(rule);
		return cr;
	}
	
	/**
	 * adds the given Unit into the Module and builds a CUnit ontop of it.
	 * @param unit the given Unit
	 * @return the built CUnit containing the given Unit.
	 */
	public CUnit addUnit(Unit unit) {
		CUnit cu = new CUnit(unit);
		module.getUnits().add(unit);
		return cu;
	}
	/**
	 * creates a {@link org.eclipse.emf.henshin.model.compact.CRule} and adds it into the Unit-List of the Module-Instance
	 * @param name the Name of the contained {@link org.eclipse.emf.henshin.model.Rule}
	 */
	public CRule createRule(String name) {
		CRule rule = new CRule(name);
		module.getUnits().add(rule.getUnit());
		return rule;
	}
	
	/**
	 * Creates a LoopUnit
	 * Uses the Subunits Name + "-Loop" as Name
	 * @param unit the Unit used as Subunit
	 */
	public CUnit createLoop(CUnit unit) {
		LoopUnit loop = HenshinFactory.eINSTANCE.createLoopUnit();
		loop.setName(unit.getUnit().getName()+"-Loop");
		loop.setSubUnit(unit.getUnit());
		
		module.getUnits().add(loop);
		return new CUnit(loop);
	}
	
	/**
	 * Creates an IteratedUnit
	 * Uses the Subunits Name + "-Iteration" as Name
	 * @param unit the Unit used as Subunit
	 * @param iterations a String representing the number of iterations
	 */
	public CUnit createIteration(CUnit unit, String iterations) {
		IteratedUnit iter = HenshinFactory.eINSTANCE.createIteratedUnit();
		iter.setName(unit.getUnit().getName()+"-Iteration");
		iter.setSubUnit(unit.getUnit());
		iter.setIterations(iterations);
		
		module.getUnits().add(iter);
		return new CUnit(iter);
	}
	
	/**
	 * Creates a ConditionalUnit 
	 * @param unitIf 	the IF-Subunit
	 * @param unitThen 	the THEN-Subunit
	 * @param unitElse 	the ELSE-Subunit
	 * @param name		the Name of the Unit
	 */
	public CUnit createConditional(CUnit unitIf, CUnit unitThen, CUnit unitElse, String name) {
		ConditionalUnit cond = HenshinFactory.eINSTANCE.createConditionalUnit();
		cond.setName(name);
		cond.setIf(unitIf.getUnit());
		cond.setThen(unitThen.getUnit());
		if(unitElse != null){
			cond.setElse(unitElse.getUnit());
		}
		module.getUnits().add(cond);
		return new CUnit(cond);
	}
	
	//Multi-Units
	
	/**
	 * Adds a Subunit to the end of a PriorityUnit
	 * If no PriorityUnit with the specified name exists, it will be created.
	 * @param name the name of the PriorityUnit
	 * @param unit the Unit used as Subunit
	 */
	public CUnit addToPriority(CUnit unit, String name) {
		PriorityUnit prio = (PriorityUnit) module.getUnit(name) ;
		if(prio == null)
		{
			prio = HenshinFactory.eINSTANCE.createPriorityUnit();
			prio.setName(name);
		}
		
		prio.getSubUnits().add(unit.getUnit());
		module.getUnits().add(prio);
		
		return new CUnit(prio);
	}
	
	/**
	 * Adds a Subunit to the end of a SequentialUnit
	 * If no SequentialUnit with the specified name exists, it will be created.
	 * @param name the name of the SequentialUnit
	 * @param unit the Unit used as Subunit
	 */
	public CUnit addToSequence(CUnit unit, String name) {
		SequentialUnit seq = (SequentialUnit) module.getUnit(name) ;
		if(seq == null)
		{
			
			seq = HenshinFactory.eINSTANCE.createSequentialUnit();
			seq.setName(name);
			module.getUnits().add(seq);
			
		}
		
		seq.getSubUnits().add(unit.getUnit());
		return new CUnit(seq);
	}
	
	/**
	 * Adds a Subunit to an IndependentUnit
	 * If no IndependentUnit with the specified name exists, it will be created.
	 * @param name the name of the IndependentUnit
	 * @param unit the Unit used as Subunit
	 */
	public CUnit addToIndependent(CUnit unit, String name) {
		IndependentUnit ind = (IndependentUnit) module.getUnit(name);
		if(ind == null)
		{
			ind = HenshinFactory.eINSTANCE.createIndependentUnit();
			ind.setName(name);
		}
		
		ind.getSubUnits().add(unit.getUnit());
		module.getUnits().add(ind);
		
		return new CUnit(ind);
	}

	
	//Getters/Setters
	
	public List<CUnit> getAllCUnits() {
		List<CUnit> res = new ArrayList<CUnit>();
		for(Unit u : module.getUnits())
		{
			res.add(new CUnit(u));
		}
		return res;
	}
	
	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	
	
}
