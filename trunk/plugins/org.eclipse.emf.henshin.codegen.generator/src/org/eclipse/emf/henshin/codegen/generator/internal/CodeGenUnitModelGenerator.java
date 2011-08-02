package org.eclipse.emf.henshin.codegen.generator.internal;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.emf.henshin.codegen.model.GenUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationUnit;

public class CodeGenUnitModelGenerator {
	
	public static String generate(GenUnit genUnit, String varName, String indent) {
		
		// Get some more info:
		//GenTransformation genTrafo = genUnit.getGenTransformation();
		TransformationUnit unit = genUnit.getUnit();
		
		// We use a print writer:
		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);
		String ind = indent;
		
		// First create a factory instance for the Henshin model:
		out.println(ind + "HenshinFactory factory = HenshinFactory.eINSTANCE;");
		
		// Instantiate the unit variable:
		out.println(ind + varName + " = factory.create" + unit.eClass().getName() + "();");
		
		// Now create the contents:
		if (unit instanceof Rule) {
			Rule rule = (Rule) unit;
			out.println(ind + varName + ".setLhs(factory.createGraph());");
			out.println(ind + varName + ".setRhs(factory.createGraph());");
			
		}
		
		
		// Done.
		return writer.toString();
	}
	
}
