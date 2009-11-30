package org.eclipse.emf.henshin.statespace.ocl;

import java.text.ParseException;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.statespace.ModelChecker;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.util.StateSpaceModelHelper;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.helper.OCLHelper;

/**
 * OCL model checker for Henshin state spaces.
 * @author Christian Krause
 */
public class OCLModelChecker implements ModelChecker {
	
	// OCL helper:
	private OCLHelper<EClassifier, ?, ?, Constraint> helper;
	
	// Context to be used:
	private EClassifier context;
	
	// State space to be model checked:
	private StateSpace stateSpace;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.modelchecking.ModelChecker#parseFormula(java.lang.String)
	 */
	public void parseFormula(String formula) throws ParseException {
		try {
			getHelper().createInvariant(formula);
		} catch (ParserException e) {
			throw new ParseException(e.getMessage(),0);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.modelchecking.ModelChecker#checkFormula(java.lang.String)
	 */
	public boolean checkFormula(String formula) throws ParseException {
		try {
			Constraint invariant = getHelper().createInvariant(formula);
			
			// Check all state models:
			for (State state : stateSpace.getStates()) {
				Resource model = StateSpaceModelHelper.getModel(state);
				TreeIterator<EObject> contents = model.getAllContents();
				while (contents.hasNext()) {
					EObject object = contents.next();
					if (context.eClass()==context) {
						//invariant.
					}
				}
			}
			return true;
			
		} catch (ParserException e) {
			throw new ParseException(e.getMessage(),0);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.modelchecking.ModelChecker#setStateSpace(org.eclipse.emf.henshin.statespace.StateSpace)
	 */
	public void setStateSpace(StateSpace stateSpace) {
		this.stateSpace = stateSpace;
	}
	
	public void setContext(EClassifier context) {
		this.context = context;
	}
	
	/*
	 * Get the OCL helper.
	 */
	private OCLHelper<EClassifier, ?, ?, Constraint> getHelper() {
		if (helper==null) {
			OCL<?, EClassifier, ?, ?, ?, ?, ?, ?, ?, Constraint, EClass, EObject> ocl;
			ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
			helper = ocl.createOCLHelper();
		}
		// set the OCL context classifier
	    helper.setContext(context);
		return helper;
	}
	
}
