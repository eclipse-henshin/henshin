package org.eclipse.emf.henshin.statespace.external.prism;

import java.io.File;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.external.AbstractFileBasedValidator;
import org.eclipse.emf.henshin.statespace.validation.ValidationResult;

/**
 * 
 * @author Christian Krause
 */
public class MDPStateSpaceValidator extends AbstractFileBasedValidator {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator#validate(org.eclipse.emf.henshin.statespace.StateSpace, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public ValidationResult validate(StateSpace stateSpace,
			IProgressMonitor monitor) throws Exception {

		monitor.beginTask("Checking PCTL property...", 4);
		
		// Generate the CSL file.
		File pctlFile = createTempFile("property", ".pctl", 
				PRISMUtil.expandLabels(property, index, new SubProgressMonitor(monitor, 1)));
		
		// Generate the model file.
		File traFile = export(stateSpace, new MDPStateSpaceExporter(), null, "nm",  new SubProgressMonitor(monitor, 1));
		
		// Invoke the PRISM tool:
		monitor.subTask("Running PRISM...");
		Map<String, String> constants = PRISMUtil.getAllProbs(stateSpace, true);
		Process process = PRISMUtil.invokePRISM(stateSpace, traFile, pctlFile, null, constants, true,  new SubProgressMonitor(monitor, 1));
		
		// Parse the experiments:
		return PRISMExperiment.parseValidationResult(stateSpace, process, new SubProgressMonitor(monitor, 1));
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.Validator#usesProperty()
	 */
	@Override
	public boolean usesProperty() {
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.Validator#getName()
	 */
	@Override
	public String getName() {
		return "PRISM MDP";
	}

}
