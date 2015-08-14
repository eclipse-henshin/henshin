package org.eclipse.emf.henshin.sam.model.sorts;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.sam.model.samalgebra.Algebra;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.Sort;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

public class TypeSingleton {
	
	private static TypeSingleton instance;
	
	private String algebraPath = "/org.eclipse.emf.henshin.sam.model/model/standardalgebra.samalgebra";
	private Resource resource;
	private Algebra algebra;
	
	public static TypeSingleton getInstance() {
		if(instance == null) {
			instance = new TypeSingleton();
		}
		
		return instance;
	}
	
	private TypeSingleton() {
		ResourceSet resourceSet = new ResourceSetImpl();
		
		URI uri = URI.createPlatformPluginURI(this.algebraPath, false);
		this.resource = resourceSet.createResource(uri);
		
		try {
			this.resource.load(Collections.EMPTY_MAP);
			
			if(this.resource.getContents().get(0) instanceof Algebra) {
				this.algebra = (Algebra)this.resource.getContents().get(0);
			}
			else {
				throw new RuntimeException("Algebra expected, but got something different: " + this.resource.getContents().get(0));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Sort getSort(EDataType dataType) {
		if(dataType.getName().equals("EInt")) {
			return this.findSort("Integer");
		}
		else if(dataType.getName().equals("EDouble") | dataType.getName().equals("EFloat")) {
			return this.findSort("Double");
		}
		else if(dataType.getName().equals("EBoolean")) {
			return this.findSort("Boolean");
		}
		else if(dataType.getName().equals("EString")) {
			return this.findSort("String");
		}
		else {
			//throw new RuntimeException("Unknown data type " + dataType.getName() + " requested!");
			System.out.println("Unknown data type " + dataType.getName() + " requested!");
			return this.findSort("Unknown");
		}
	}
	
	public Sort getSort(String name) {
		if(name.equals("Integer")) {
			return this.findSort("Integer");
		}
		else if(name.equals("Double") | name.equals("Float")) {
			return this.findSort("Double");
		}
		else if(name.equals("Boolean")) {
			return this.findSort("Boolean");
		}
		else if(name.equals("String")) {
			return this.findSort("String");
		}
		else {
			//throw new RuntimeException("Unknown data type " + dataType.getName() + " requested!");
			System.out.println("Unknown data type " + name + " requested!");
			return this.findSort("Unknown");
		}
	}

	public EDataType getEDataType(Sort sort) {
		EDataType type = EcoreFactory.eINSTANCE.createEDataType();
		if(sort.getName().equals("Integer")) {
			type.setName("EInteger");
		}
		else if(sort.getName().equals("Double")) {
			type.setName("EDouble");
		}
		else if(sort.getName().equals("Boolean")) {
			type.setName("EBoolean");
		}
		else if(sort.getName().equals("String")) {
			type.setName("EString");
		}
		else {
			type.setName("Unknown");
			//throw new RuntimeException("Unknown data type " + sort.getName() + " requested!");
		}
		
		return type;
	}
	
	private Sort findSort(String name) {
		EList<Sort> sorts = this.algebra.getSignature().getSorts();
		
		for(Sort sort : sorts) {
			if(sort.getName().equals(name)) {
				return sort;
			}
		}
		
		throw new RuntimeException("Unknown sort requested!");
	}
}
