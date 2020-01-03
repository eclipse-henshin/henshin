package org.eclipse.emf.henshin.adapters.xtext

import javax.inject.Provider
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.henshin.model.Attribute
import org.eclipse.emf.henshin.model.Edge
import org.eclipse.emf.henshin.model.NamedElement

class NamingHelper {
	static dispatch def name(EObject eo) { null }
	static dispatch def name(NamedElement ne) { ne.name }
	static dispatch def name(Edge e) { '''[«safe([e.source.name])»->«safe([e.target.name])»:«safe([e.type.name])»]'''.toString }
	static dispatch def name(Attribute a) { if (a.type !== null) { a.type.name } else "" }
	
	private static def safe(Provider<String> expression) {
		try {
			expression.get
		} catch (NullPointerException npe) {
			""
		}
	}
}