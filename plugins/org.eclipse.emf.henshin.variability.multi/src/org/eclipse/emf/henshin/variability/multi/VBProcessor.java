package org.eclipse.emf.henshin.variability.multi;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;

public abstract class VBProcessor {

	public abstract EGraphImpl createEGraphAndCollectPCs(List<EObject> roots, Map<EObject, String> pcsP);

	public abstract void createNewVariabilityAnnotations(List<EObject> roots, Map<EObject, String> pcsP);

	public abstract void deleteObsoleteVariabilityAnnotations(List<EObject> roots, Map<EObject, String> pcsP);
}
