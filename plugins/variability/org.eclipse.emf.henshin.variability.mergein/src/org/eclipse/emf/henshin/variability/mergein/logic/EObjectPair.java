package org.eclipse.emf.henshin.variability.mergein.logic;

import org.eclipse.emf.ecore.EObject;

public class EObjectPair {
	private EObject element1;
	public EObjectPair(EObject element1, EObject element2) {
		super();
		this.element1 = element1;
		this.element2 = element2;
	}
	public EObject getElement1() {
		return element1;
	}
	public void setElement1(EObject element1) {
		this.element1 = element1;
	}
	public EObject getElement2() {
		return element2;
	}
	public void setElement2(EObject element2) {
		this.element2 = element2;
	}
	private EObject element2;
	@Override
	public int hashCode() {
		return element1.hashCode()+100*element2.hashCode();
	}
}
