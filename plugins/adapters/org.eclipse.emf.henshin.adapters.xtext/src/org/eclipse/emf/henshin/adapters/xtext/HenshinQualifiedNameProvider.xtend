package org.eclipse.emf.henshin.adapters.xtext

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.util.IResourceScopeCache
import org.eclipse.xtext.util.Tuples

import static extension org.eclipse.emf.henshin.adapters.xtext.NamingHelper.*

class HenshinQualifiedNameProvider extends IQualifiedNameProvider.AbstractImpl {
	
	static val HENSHIN_CACHE_KEY ="HENSHIN_CACHE_KEY"
	
	@Inject
	val IResourceScopeCache cache = IResourceScopeCache.NullImpl.INSTANCE;
	
	override getFullyQualifiedName(EObject obj) {
		cache.get(Tuples.pair(obj, HENSHIN_CACHE_KEY), obj.eResource(), [
			val name = obj.name
					
			if (name === null) {
				null
			} else {
				val qualifiedName = QualifiedName.create(name)
				if (obj.eContainer !== null) {
					val parentsQualifiedName = getFullyQualifiedName(obj.eContainer)
					if (parentsQualifiedName === null) {
						null
					} else {
						parentsQualifiedName.append(qualifiedName)	
					}
				} else {
					qualifiedName
				}
				
			}
		])
	}	
}
