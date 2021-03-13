/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.tool.commands;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.ocl.pivot.Constraint;
import org.eclipse.ocl.pivot.ExpressionInOCL;
import org.eclipse.ocl.pivot.Model;
import org.eclipse.ocl.pivot.PivotFactory;
import org.eclipse.ocl.pivot.PivotPackage;

import org.eclipse.emf.henshin.ocl2ac.ocl2gc.core.Translator;
import graph.GraphFactory;
import laxcondition.Condition;
import laxcondition.LaxCondition;
import laxcondition.LaxconditionFactory;

public class OCL2LaxCondCommand extends Translator {

	public HashMap<Condition, List<String>> mapCon2Var;

	public OCL2LaxCondCommand(IFile oclasFile, IFile ecoreFile) {
		this.oclasFile = oclasFile;
		this.ecoreFile = ecoreFile;
		this.invariants = new BasicEList<Constraint>();
		this.laxconditionFactory = LaxconditionFactory.eINSTANCE;
		this.mapCon2Var = new HashMap<Condition, List<String>>();
		this.graphFactory = GraphFactory.eINSTANCE;
		this.oclFactory = PivotFactory.eINSTANCE;
		this.varIndex = 1;
		initModels();
		prepareOCLModel();
		refactorOCLModel();

	}

	/**
	 * supporting static ePackage for a given meta-model
	 */
	protected void initModels() {
		URI uriOclAS = URI.createPlatformResourceURI(this.oclasFile.getFullPath().toString(), true);
		URI uriEcore = URI.createPlatformResourceURI(this.ecoreFile.getFullPath().toString(), true);
		if (uriOclAS != null && uriEcore != null) {
			// Load the input models and the corresponding invariants
			PivotPackage.eINSTANCE.eClass();
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
			Map<String, Object> m = reg.getExtensionToFactoryMap();
			m.put("*.oclas", new XMIResourceFactoryImpl());
			ResourceSet resSet = new ResourceSetImpl();
			Resource resourceOclAS = resSet.getResource(uriOclAS, true);
			this.oclModel = (Model) resourceOclAS.getContents().get(0);
			// Load Ecore for meta/type model references
			Resource resourceEcore = resSet.getResource(uriEcore, true);
			EPackage dynamicEPackage = (EPackage) resourceEcore.getContents().get(0);
			this.setTypeModel(EPackage.Registry.INSTANCE.getEPackage(dynamicEPackage.getNsURI()));
			if (this.getTypeModel() == null)
				this.setTypeModel(dynamicEPackage);
			System.out.println(getTypeModel());
		}
	}

	/**
	 * The entry point
	 * 
	 * @return
	 */
	public List<Condition> getSetofLaxConditions() {
		long start = System.currentTimeMillis();
		Date date = new GregorianCalendar().getTime();
		List<Condition> arrayList = new ArrayList<Condition>();
		System.out.println("### The number of OCL Invariants is: " + invariants.size() + " ####");
		for (Constraint inv : invariants) {
			System.out.println("=====[ Invariant Name: " + inv.getName() + " ]=====");
			if (inv.getOwnedSpecification() != null) {
				ExpressionInOCL exprInOcl = null;
				exprInOcl = (ExpressionInOCL) inv.getOwnedSpecification();
				System.out.println("---[ Invariant Body: " + exprInOcl.getOwnedBody() + " ]---");

				if (exprInOcl.getOwnedBody() != null) {
					try {
						Condition condition = laxconditionFactory.createCondition();
						condition.setName(inv.getName());
						condition.setTypeGraph(this.getTypeModel());
						// The entry point
						LaxCondition laxCondition = translate_I(inv);
						if (laxCondition == null)
							continue;
						condition.setLaxCondition(laxCondition);
						// get variables
						this.mapCon2Var.put(condition, varNames);
						varNames = new ArrayList<String>();
						arrayList.add(condition);
					} catch (Exception e) {
						System.err.println("The OCL constraint " + inv.getName() + " is not well translated");

						System.out.println(e.getMessage());
					}
				}
			}
		}

		long stop = System.currentTimeMillis();
		System.out.println((stop - start));
		return arrayList;
	}

}
