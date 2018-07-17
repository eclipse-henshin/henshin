package org.eclipse.emf.henshin.adapters.xtext

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.henshin.model.Attribute
import org.eclipse.emf.henshin.model.Edge
import org.eclipse.emf.henshin.model.NamedElement

class NamingHelper {
	static dispatch def name(EObject eo) { null }
	static dispatch def name(NamedElement ne) { ne.name }
	static dispatch def name(Edge e) { '''[«e.source.name»->«e.target.name»:«e.type.name»]'''.toString }
	static dispatch def name(Attribute a) { if (a.type !== null) { a.type.name } else "" }
}