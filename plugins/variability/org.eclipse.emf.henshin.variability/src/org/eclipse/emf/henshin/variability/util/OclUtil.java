package org.eclipse.emf.henshin.variability.util;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.ecore.OCL.Helper;
import org.eclipse.ocl.ecore.OCL.Query;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.ecore.impl.EcoreFactoryImpl;
import org.eclipse.ocl.expressions.Variable;

/**
 * Utility for parsing OCL expressions (currently not used).
 * 
 * @author Daniel Strüber
 *
 */
public class OclUtil {

	@SuppressWarnings({ "rawtypes", "unchecked" }) 
	public static boolean evaluate(String queryString, EObject context,
			Map<String, Object> parameterValues) {
		OCL ocl = OCL.newInstance();

		OCLExpression queryExpression = null;
		try {
			Helper helper = ocl.createOCLHelper();
			helper.setInstanceContext(context);

			// for each input parameter, create a variable in the OCL execution
			// environment
			for (String s : parameterValues.keySet()) {
				Object value = parameterValues.get(s);
				if (value instanceof EObject) {
					ocl.getEvaluationEnvironment().add(s, value);
					Variable variable = EcoreFactoryImpl.eINSTANCE
							.createVariable();
					variable.setName(s);
					variable.setType(((EObject) value).eClass());
					helper.getEnvironment().addElement(s, variable, true);
				}
			}
			queryExpression = helper.createQuery(queryString);
		} catch (ParserException e) {
			System.err.println(e.getLocalizedMessage());
			return false;
		}

		Query query = ocl.createQuery(queryExpression);

		// for each input parameter, set the variable in the OCL execution
		// environment to the parameter value
		for (String s : parameterValues.keySet()) {
			Object value = parameterValues.get(s);
			query.getEvaluationEnvironment().add(s, value);
		}

		Object result = query.evaluate();
		if (result instanceof Boolean) {
			return (Boolean) result;
		} else
			return false;

	}
}
