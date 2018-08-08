package org.eclipse.emf.henshin.interpreter.debug;

import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EGraph;

public class DebugValueList extends HenshinDebugValue {

	private List<?> valueList;
	
	public DebugValueList(IDebugTarget target, EGraph graph, String declaredType, List<?> valueList) {
		super(target, graph, declaredType);
		
		if (declaredType == null)
			throw new IllegalArgumentException("declaredType must not be null");
		
		this.valueString = declaredType + "[" + valueList.size() + "]";
		this.actualType = declaredType + "[]";
		this.valueList = valueList;
		
		childrenVariables = new HenshinDebugVariable[valueList.size()];
	}

	@Override
	public IVariable[] getVariables() throws DebugException {
		if (hasVariables()) {		

			for (int i = 0; i < valueList.size(); i++) {				

				// current list element
				Object listElem = valueList.get(i);
				EObject valueEObj = (EObject) listElem;
				
				// get whole domain (e.g. domain for :Client could be 'charles', 'bob', 'alice')
				List<EObject> domain = graph.getDomain(valueEObj.eClass(), false);
				int index = domain.indexOf(valueEObj);
								
				// listElem is another list				
				if (listElem instanceof List<?>) {
					childrenVariables[i] = new HenshinDebugVariable(
							(HenshinDebugTarget) getDebugTarget(),
							"["+i+"]",
							new DebugValueList(
									(HenshinDebugTarget) getDebugTarget(),
									graph,
									declaredType,
									valueList)
							);
				// listElem is plain EObject
				} else if (listElem instanceof EObject) {
					// create new henshin debug variable
					childrenVariables[i] = new HenshinDebugVariable(
							(HenshinDebugTarget) getDebugTarget(),
							"["+i+"]",
							new DebugValueEObject(
									(HenshinDebugTarget) getDebugTarget(),
									graph,
									declaredType, 
									(EObject) listElem,
									index)
							);
				// listElem is object
				} else {
					// 
					childrenVariables[i] = new HenshinDebugVariable(
							(HenshinDebugTarget) getDebugTarget(),
							"["+i+"]",
							new DebugValueObject(
									(HenshinDebugTarget) getDebugTarget(),
									graph,
									declaredType,
									listElem,
									index)
							);
				}
			}
			return childrenVariables;
		}
		else {

			return new IVariable[0];

		}
	}
}