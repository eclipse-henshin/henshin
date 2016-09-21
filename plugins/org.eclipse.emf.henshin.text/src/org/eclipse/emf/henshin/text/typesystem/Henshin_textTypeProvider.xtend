package org.eclipse.emf.henshin.text.typesystem

import org.eclipse.emf.henshin.text.henshin_text.Expression
import org.eclipse.emf.henshin.text.henshin_text.StringValue
import org.eclipse.emf.henshin.text.henshin_text.NumberValue
import org.eclipse.emf.henshin.text.henshin_text.IntegerValue
import org.eclipse.emf.henshin.text.henshin_text.NaturalValue
import org.eclipse.emf.henshin.text.henshin_text.BoolValue
import org.eclipse.emf.henshin.text.henshin_text.OrExpression
import org.eclipse.emf.henshin.text.henshin_text.AndExpression
import org.eclipse.emf.henshin.text.henshin_text.EqualityExpression
import org.eclipse.emf.henshin.text.henshin_text.ComparisonExpression
import org.eclipse.emf.henshin.text.henshin_text.PlusExpression
import org.eclipse.emf.henshin.text.henshin_text.MinusExpression
import org.eclipse.emf.henshin.text.henshin_text.MulOrDivExpression
import org.eclipse.emf.henshin.text.henshin_text.NotExpression
import org.eclipse.emf.henshin.text.henshin_text.ParameterValue
import org.eclipse.emf.henshin.text.henshin_text.ParameterType
import org.eclipse.emf.henshin.text.henshin_text.JavaClassValue
import org.eclipse.emf.henshin.text.henshin_text.JavaImport
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.henshin.text.henshin_text.Rule
import org.eclipse.emf.henshin.text.henshin_text.MultiRule
import com.google.common.collect.Iterables
import org.eclipse.emf.henshin.text.henshin_text.JavaAttributeValue
import org.eclipse.emf.henshin.text.henshin_text.BracketExpression

class Henshin_textTypeProvider {
	
		
	public static val stringType = new Henshin_textStringType
	public static val numberType=new Henshin_textNumberType
	public static val boolType = new Henshin_textBoolType
	public static val complexType=new Henshin_textComplexType
	
	/**
	 * Liefert den Type eines Ausdrucks
	 * 
	 * @param expression Zu ueberpruefender Ausdruck
	 * @return Type des Ausdrucks
	 */
	def Henshin_textType typeFor(Expression expression) {
		switch (expression) {
			StringValue: return stringType
			NumberValue: return numberType
			IntegerValue: return numberType
			NaturalValue: return numberType
			BoolValue: return boolType
			OrExpression: return boolType
			AndExpression: return boolType
			EqualityExpression: return boolType
			ComparisonExpression: return boolType
			PlusExpression: return numberType
			MinusExpression: return numberType
			MulOrDivExpression: return numberType
			BracketExpression: return typeFor(expression.expression)
			NotExpression: return boolType
			ParameterValue: return typeFor(expression.value.type)
			JavaClassValue: return typeFor(expression)
			JavaAttributeValue: return typeFor(expression)
		}
	}
	
	/**
	 * Liefert den Type eines Java-Attributs
	 * 
	 * @param javaAttribute Zu ueberpruefendes Java-Attribut
	 * @return Type des Java-Attributs
	 * 
	 */
	def Henshin_textType typeFor(JavaAttributeValue javaAttribute){
		var EObject container=javaAttribute.eContainer()
		while(!(container instanceof Rule)&&!(container instanceof MultiRule)){
			container=container.eContainer()
		}
		var  Iterable<JavaImport> iterableOfJavaImportImpl=null
		if(container instanceof Rule){
			iterableOfJavaImportImpl=Iterables.filter((container as Rule).ruleElements,typeof(JavaImport))
		}else{
			iterableOfJavaImportImpl=Iterables.filter((container as MultiRule).multiruleElements,typeof(JavaImport))
		}
		for(imports:iterableOfJavaImportImpl){
	  		try{
	  			var calledClass=Class.forName(imports.packagename+"."+javaAttribute.value.split("\\.").get(0))
				for(atrib:calledClass.declaredFields){
					if(atrib.name==javaAttribute.value.split("\\.").get(1)){
						return typeForJavaType(atrib.type.name)
					}
				}
	  		}catch(Exception e){}
		}
		return stringType
	}
	
	
	/**
	 * Liefert den Type eines Java-Aufrufs
	 * 
	 * @param javaCall Zu ueberpruefender Java-Aufruf
	 * @return Type des Java-Aufrufs
	 * 
	 */
	def Henshin_textType typeFor(JavaClassValue javaCall){
		var EObject container=javaCall.eContainer()
		while(!(container instanceof Rule)&&!(container instanceof MultiRule)){
			container=container.eContainer()
		}
		var  Iterable<JavaImport> iterableOfJavaImportImpl=null
		if(container instanceof Rule){
			iterableOfJavaImportImpl=Iterables.filter((container as Rule).ruleElements,typeof(JavaImport))
		}else{
			iterableOfJavaImportImpl=Iterables.filter((container as MultiRule).multiruleElements,typeof(JavaImport))
		}
		for(imports:iterableOfJavaImportImpl){
	  		try {
	  			var calledClass=Class.forName(imports.packagename+"."+javaCall.value.split("\\.").get(0))
	  			for(methode:calledClass.getMethods()){
	  				if(methode.name==javaCall.value.split("\\.").get(1)){
	  					return typeForJavaType(methode.returnType.name)
	  				}
	  			}
	  		}catch(Exception e){}
		}
		return stringType
	}
	
	

	/**
	 * Liefert den Type eines Java-Type
	 * 
	 * @param className Zu ueberpruefender Java-Type
	 * @return Type des Java-Types
	 * 
	 */
	def Henshin_textType typeForJavaType(String className){
		switch(className){
			case "java.lang.Boolean": return boolType
			case "boolean": return boolType
			case "java.lang.Byte": return numberType
			case "byte": return numberType
			case "java.lang.Character": return stringType
			case "char": return stringType
			case "java.lang.Double": return numberType
			case "double": return numberType
			case "java.lang.Float": return numberType
			case "float": return numberType
			case "java.lang.Integer": return numberType 
			case "int": return numberType
			case "java.lang.Long": return numberType
			case "long": return numberType
			case "java.lang.Short": return numberType
			case "short": return numberType
			case "java.lang.String": return stringType
			case "string": return stringType
			default: return complexType
		}
	}

	
	/**
	 * Liefert den Type eines Parameter-Type
	 * 
	 * @param parameterType Zu ueberpruefender Parameter-Type
	 * @return Type des Parameter-Types
	 * 
	 */
	def Henshin_textType typeFor(ParameterType parameterType){
		if(parameterType.type==null){
			return typeFor(parameterType.enumType.literal)	
		}else{
			return complexType
		}
	}

	/**
	 * Liefert den Type einen E-Type
	 * 
	 * @param eType Zu ueberpruefender E-Type
	 * @return Type des E-Types
	 * 
	 */
	def Henshin_textType typeFor(String eType){
		switch (eType) {
			case "EBigDecimal" : return complexType
			case "EBigInteger" : return complexType
			case "EBoolean" : return boolType
			case "EBooleanObject" : return complexType
			case "EByte" : return numberType
			case "EByteArray" : return complexType
			case "EByteObject" : return complexType
			case "EChar" : return stringType
			case "ECharacterObject" : return complexType 
			case "EDate" : return complexType
			case "EDiagnosticChain" : return complexType
			case "EDouble" : return numberType
			case "EDoubleObject" : return complexType
			case "EEList" : return complexType
			case "EEnumerator" : return complexType
			case "EFeatureMap" : return complexType
			case "EFeatureMapEntry" : return complexType
			case "EFloat" : return numberType
			case "EFloatObject" : return complexType
			case "EInt" : return numberType
			case "EIntegerObject" : return complexType
			case "ETreeIterator" : return complexType
			case "EInvocationTargetException" : return complexType
			case "EJavaClass" : return complexType
			case "EJavaObject" : return complexType
			case "ELong" : return numberType
			case "ELongObject" : return complexType
			case "EMap" : return complexType
			case "EResource" : return complexType
			case "EResourceSet" : return complexType
			case "EShort" : return numberType
			case "EShortObject" : return complexType
			case "EString" : return stringType
		}
		return null
	}
	
	
	def isInt(Henshin_textType type){ 
		return(type==numberType)
	}
	def isString(Henshin_textType type){ 
		return (type==stringType)
	}
	def isBoolean(Henshin_textType type){ 
		return (type==boolType)
	}
	
	def isComplex(Henshin_textType type){ 
		return (type==complexType)
	}
	
}