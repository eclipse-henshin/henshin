package org.eclipse.emf.henshin.text.ui.util

import org.eclipse.emf.henshin.text.henshin_text.Unit
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.henshin.text.henshin_text.impl.ConditionalUnitImpl
import org.eclipse.emf.henshin.text.henshin_text.ConditionalUnit
import org.eclipse.emf.henshin.text.henshin_text.Call
import org.eclipse.emf.henshin.text.henshin_text.Henshin_textFactory
import org.eclipse.emf.henshin.text.henshin_text.impl.IteratedUnitImpl
import org.eclipse.emf.henshin.text.henshin_text.IteratedUnit
import org.eclipse.emf.henshin.text.henshin_text.impl.LoopUnitImpl
import org.eclipse.emf.henshin.text.henshin_text.LoopUnit
import org.eclipse.emf.henshin.text.henshin_text.impl.PriorityUnitImpl
import org.eclipse.emf.henshin.text.henshin_text.PriorityUnit
import org.eclipse.emf.henshin.text.henshin_text.impl.IndependentUnitImpl
import org.eclipse.emf.henshin.text.henshin_text.IndependentUnit
import org.eclipse.emf.henshin.text.henshin_text.SequentialProperties
import org.eclipse.emf.henshin.text.henshin_text.UnitElement
import org.eclipse.emf.henshin.text.henshin_text.Parameter
import com.google.common.collect.Iterables
import org.eclipse.emf.henshin.text.henshin_text.impl.CallImpl
import org.eclipse.emf.henshin.text.henshin_text.impl.SequentialPropertiesImpl
import java.util.Random
import org.eclipse.emf.henshin.text.henshin_text.ParameterType
import org.eclipse.emf.henshin.text.henshin_text.Type


public class ModifyModelUnits {
	
	private var long seed
	private var testSetup=false
	
	new(long seed){
		this.seed=seed
		this.testSetup=true
	}
	
	new(){}
	
	/**
	 * Entpackt die in einer Unit geschachtelten Subunits in einzelne elementare Units
	 * 
	 * @param unit Unit mit geschachtelten Subunits
	 * @param level Ebene der Schachtelung 
	 * @return Liste von elementaren Units
	 */
	def List<Unit> flat(Unit unit,int level,Call unitCall){
		var List<Unit> erg=new ArrayList<Unit>()
		
		if(isSequence(unit.unitElements)==false){
			if(unit.unitElements.get(0) instanceof ConditionalUnitImpl){
				var help=unit.unitElements.get(0) as ConditionalUnit
				if(!isCallOnly(help.getIf)){
					var Unit newSubUnit=createUnit(unit.name+"IF"+level+"_"+returnRandomeNumber(),help.getIf,unit.parameters.clone())
					(unit.unitElements.get(0)as ConditionalUnit).getIf.clear()
					var Call call=Henshin_textFactory.eINSTANCE.createCall()
					call.setElementCall(newSubUnit)
					call.parameters.addAll(unit.parameters)
					(unit.unitElements.get(0)as ConditionalUnit).getIf.add(call)
					var List<Unit> newSubUnitList=flat(newSubUnit,level+1,call)
					erg.addAll(newSubUnitList)
				}else if (unitCall!=null){
					var parameterIndex=0
					for(p:unitCall.parameters){
						var indexInSubCall=(help.getIf.get(0) as Call).parameters.indexOf(p)
						if(indexInSubCall!=-1){
							(help.getIf.get(0) as Call).parameters.set(indexInSubCall,unitCall.elementCall.parameters.get(parameterIndex))
						}
						parameterIndex++
					}
				}
				if(!isCallOnly(help.getThen)){
					var Unit newSubUnit=createUnit(unit.name+"THEN"+level+"_"+returnRandomeNumber(),help.getThen,unit.parameters.clone())
					(unit.unitElements.get(0)as ConditionalUnit).getThen.clear()
					var Call call=Henshin_textFactory.eINSTANCE.createCall()
					call.setElementCall(newSubUnit)
					call.parameters.addAll(unit.parameters)
					(unit.unitElements.get(0)as ConditionalUnit).getThen.add(call)
					var List<Unit> newSubUnitList=flat(newSubUnit,level+1,call)
					erg.addAll(newSubUnitList)
				}else if (unitCall!=null){
					var parameterIndex=0
					for(p:unitCall.parameters){
						var indexInSubCall=(help.getThen.get(0) as Call).parameters.indexOf(p)
						if(indexInSubCall!=-1){
							(help.getThen.get(0) as Call).parameters.set(indexInSubCall,unitCall.elementCall.parameters.get(parameterIndex))
						}
						parameterIndex++
					}
				}
				if(help.getElse().size>0){
					if(!isCallOnly(help.getElse)){
						var Unit newSubUnit=createUnit(unit.name+"ELSE"+level+"_"+returnRandomeNumber(),help.getElse,unit.parameters.clone())
						(unit.unitElements.get(0)as ConditionalUnit).getElse.clear()
						var Call call=Henshin_textFactory.eINSTANCE.createCall()
						call.setElementCall(newSubUnit)
						call.parameters.addAll(unit.parameters)
						(unit.unitElements.get(0)as ConditionalUnit).getElse.add(call)
						var List<Unit> newSubUnitList=flat(newSubUnit,level+1,call)
						erg.addAll(newSubUnitList)
					}else if (unitCall!=null){
						var parameterIndex=0
						for(p:unitCall.parameters){
							var indexInSubCall=(help.getElse.get(0) as Call).parameters.indexOf(p)
							if(indexInSubCall!=-1){
								(help.getThen.get(0) as Call).parameters.set(indexInSubCall,unitCall.elementCall.parameters.get(parameterIndex))
							}
							parameterIndex++
						}
					}
				}
				erg.add(unit)
			}else if(unit.unitElements.get(0) instanceof IteratedUnitImpl){
				var help=unit.unitElements.get(0) as IteratedUnit
				if(!isCallOnly(help.subElement)){
					var Unit newSubUnit=createUnit(unit.name+"ITERATED"+level+"_"+returnRandomeNumber(),help.subElement,unit.parameters.clone())
					(unit.unitElements.get(0)as IteratedUnit).subElement.clear()
					var Call call=Henshin_textFactory.eINSTANCE.createCall()
					call.setElementCall(newSubUnit)
					call.parameters.addAll(unit.parameters)
					for(p:unit.parameters){
						var Parameter param=Henshin_textFactory.eINSTANCE.createParameter()
					}
					(unit.unitElements.get(0)as IteratedUnit).subElement.add(call)
					erg.add(unit)
					var List<Unit> newSubUnitList=flat(newSubUnit,level+1,call)
					erg.addAll(newSubUnitList)
				}else{
					if (unitCall!=null){
						var parameterIndex=0
						for(p:unitCall.parameters){
							var indexInSubCall=(help.subElement.get(0) as Call).parameters.indexOf(p)
							if(indexInSubCall!=-1){
								(help.subElement.get(0) as Call).parameters.set(indexInSubCall,unitCall.elementCall.parameters.get(parameterIndex))
							}
							parameterIndex++
						}
				  	}
					erg.add(unit)
				}
			}else if(unit.unitElements.get(0) instanceof LoopUnitImpl){
				var help=unit.unitElements.get(0) as LoopUnit
				if(!isCallOnly(help.subElement)){
					var Unit newSubUnit=createUnit(unit.name+"LOOP"+level+"_"+returnRandomeNumber(),help.subElement,unit.parameters.clone())
					(unit.unitElements.get(0)as LoopUnit).subElement.clear()
					var Call call=Henshin_textFactory.eINSTANCE.createCall()
					call.setElementCall(newSubUnit)
					call.parameters.addAll(unit.parameters)
					(unit.unitElements.get(0)as LoopUnit).subElement.add(call)
					erg.add(unit)
					var List<Unit> newSubUnitList=flat(newSubUnit,level+1,call)
					erg.addAll(newSubUnitList)
				}else{
					if (unitCall!=null){
						var parameterIndex=0
						for(p:unitCall.parameters){
							var indexInSubCall=(help.subElement.get(0) as Call).parameters.indexOf(p)
							if(indexInSubCall!=-1){
								(help.subElement.get(0) as Call).parameters.set(indexInSubCall,unitCall.elementCall.parameters.get(parameterIndex))
							}
							parameterIndex++
						}
				  	}
					erg.add(unit)
				}
			}else if(unit.unitElements.get(0) instanceof PriorityUnitImpl){
				var help=unit.unitElements.get(0) as PriorityUnit
				var index=0
				for(sub:help.listOfLists){
					if(!isCallOnly(sub.subElements)){
						var Unit newSubUnit=createUnit(unit.name+"PRIORITY"+level+"_"+index+"_"+returnRandomeNumber(),sub.subElements,unit.parameters.clone())
						(unit.unitElements.get(0)as PriorityUnit).listOfLists.get(index).subElements.clear()
						var Call call=Henshin_textFactory.eINSTANCE.createCall()
						call.setElementCall(newSubUnit)
						call.parameters.addAll(unit.parameters)
						(unit.unitElements.get(0)as PriorityUnit).listOfLists.get(index).subElements.add(call)
						var List<Unit> newSubUnitList=flat(newSubUnit,level+1,call)
						erg.addAll(newSubUnitList)
						index++
					}else if (unitCall!=null){
					var parameterIndex=0
					for(p:unitCall.parameters){
						var indexInSubCall=(sub.subElements.get(0) as Call).parameters.indexOf(p)
						if(indexInSubCall!=-1){
							(sub.subElements.get(0) as Call).parameters.set(indexInSubCall,unitCall.elementCall.parameters.get(parameterIndex))
						}
						parameterIndex++
					}
				  }
				}
				erg.add(unit)
			}else if(unit.unitElements.get(0) instanceof IndependentUnitImpl){
				var help=unit.unitElements.get(0) as IndependentUnit
				var index=0
				for(sub:help.listOfLists){
					if(!isCallOnly(sub.subElements)){
						var Unit newSubUnit=createUnit(unit.name+"INDEPENDENT"+level+"_"+index+"_"+returnRandomeNumber(),sub.subElements,unit.parameters.clone())
						(unit.unitElements.get(0)as IndependentUnit).listOfLists.get(index).subElements.clear()
						var Call call=Henshin_textFactory.eINSTANCE.createCall()
						call.setElementCall(newSubUnit)
						call.parameters.addAll(unit.parameters)
						(unit.unitElements.get(0)as IndependentUnit).listOfLists.get(index).subElements.add(call)
						var List<Unit> newSubUnitList=flat(newSubUnit,level+1,call)
						erg.addAll(newSubUnitList)
						index++
					}else if(unitCall!=null){
						var parameterIndex=0
						for(p:unitCall.parameters){
							var indexInSubCall=(sub.subElements.get(0) as Call).parameters.indexOf(p)
							if(indexInSubCall!=-1){
								(sub.subElements.get(0) as Call).parameters.set(indexInSubCall,unitCall.elementCall.parameters.get(parameterIndex))
							}
							parameterIndex++
						}
				  	}
				}
				erg.add(unit)
			}
		}else {
			if(isCallSequence(unit.unitElements)){
				erg.add(unit)
			}else{
				var index=0
				for(element:unit.unitElements){
					if(!(element instanceof SequentialProperties)&&!(element instanceof Call)){
						var List<UnitElement> subElements=new ArrayList()
						if(element.subSequence.size>0){
							subElements.addAll(element.subSequence)
							element.subSequence.clear()
						}else{
							subElements.add(element)
						}
						var Unit newSubUnit=createUnit(unit.name+"Sequence"+level+"_"+index+"_"+returnRandomeNumber(),subElements,unit.parameters.clone())
						var Call call=Henshin_textFactory.eINSTANCE.createCall()
						call.setElementCall(newSubUnit)
						call.parameters.addAll(unit.parameters)
						unit.unitElements.set(index,call)
						var List<Unit> newSubUnitList=flat(newSubUnit,level+1,call)
						erg.addAll(newSubUnitList)
					}else if ((unitCall!=null)&&(element instanceof Call)){
						var parameterIndex=0
						for(p:unitCall.parameters){
							var indexInSubCall=(element as Call).parameters.indexOf(p)
							if(indexInSubCall!=-1){
								(element as Call).parameters.set(indexInSubCall,unitCall.elementCall.parameters.get(parameterIndex))	
							}
							parameterIndex++
						}
				  	}
					index++
				}
				erg.add(unit)
			}
		}
		return erg
	}
	
	private def returnRandomeNumber(){
		if(this.testSetup){
			var randomGenerator = new Random(this.seed)
			return randomGenerator.nextInt(1000)
		}else{
			var randomGenerator = new Random()
			return randomGenerator.nextInt(1000)
		}
		
	}
	
/**
 * Gibt eine Kopie des übergebenen ParameterType-Objekts zurück
 * 
 * @param type Typ eines org.eclipse.emf.henshin.text.henshin_text.Parameter 
 */
	
def getParameterType(ParameterType type){
	var ParameterType result=Henshin_textFactory.eINSTANCE.createParameterType()
	if(type.type!=null){
		result.type=type.type
	}else{
		var Type value
		switch type.enumType.literal{
		case "EBigDecimal": value=Type.EBIG_DECIMAL
		case "EBigInteger": value=Type.EBIG_INTEGER
		case "EBoolean": value=Type.EBOOLEAN
		case "EBooleanObject": value=Type.EBOOLEAN_OBJECT
		case "EByte": value=Type.EBYTE
		case "EByteArray": value=Type.EBYTE_ARRAY
		case "EByteObject": value=Type.EBYTE_OBJECT
		case "EChar": value=Type.ECHAR
		case "ECharacterObject": value=Type.ECHARACTER_OBJECT
		case "EDate": value=Type.EDATE
		case "EDiagnosticChain": value=Type.EDIAGNOSTIC_CHAIN
		case "EDouble": value=Type.EDOUBLE
		case "EDoubleObject": value=Type.EDOUBLE_OBJECT
		case "EEList": value=Type.EE_LIST
		case "EEnumerator": value=Type.EENUMERATOR
		case "EFeatureMap": value=Type.EFEATURE_MAP
		case "EFeatureMapEntry": value=Type.EFEATURE_MAP_ENTRY
		case "EFloat": value=Type.EFLOAT
		case "EFloatObject": value=Type.EFLOAT_OBJECT
		case "EInt": value=Type.EINT
		case "EIntegerObject": value=Type.EINTEGER_OBJECT
		case "ETreeIterator": value=Type.ETREE_ITERATOR
		case "EInvocationTargetException": value=Type.EINVOCATION_TARGET_EXCEPTION
		case "EJavaClass": value=Type.EJAVA_CLASS
		case "EJavaObject": value=Type.EJAVA_OBJECT		
		case "ELong": value=Type.ELONG
		case "ELongObject": value=Type.ELONG_OBJECT
		case "EMap": value=Type.EMAP
		case "EResource": value=Type.ERESOURCE
		case "EResourceSet": value=Type.ERESOURCE_SET
		case "EShort": value=Type.ESHORT
		case "EShortObject": value=Type.ESHORT_OBJECT
  		case "EString": value=Type.ESTRING
  		
}

	
		result.setEnumType(value)
	}
	return result
	
}
	/**
	 * Erzeugt eine neue Unit
	 * 
	 * @param name Name der neuen Unit
	 * @param unitElement Elemente der neuen Unit 
	 * @param parameter Parameter der neuen Unit
	 * @return Neu erzeugte Unit
	 */
	def Unit createUnit(String name,List<UnitElement> unitElement,List<Parameter> parameter){
		var Unit helpUnit=Henshin_textFactory.eINSTANCE.createUnit()
		helpUnit.setName(name)
		for(param:parameter){
			var Parameter helpParameter=Henshin_textFactory.eINSTANCE.createParameter()
			helpParameter.setName(param.name+"_"+name)
			helpParameter.setKind(param.kind)
			helpParameter.setType(getParameterType(param.type))
			helpUnit.parameters.add(helpParameter)
		}
		if(isSequence(unitElement)){
			helpUnit.unitElements.addAll(unitElement)
		}else if(unitElement.get(0) instanceof ConditionalUnitImpl){
			var ConditionalUnit helpUnitElement=Henshin_textFactory.eINSTANCE.createConditionalUnit()
			helpUnitElement.getIf().addAll((unitElement.get(0)as ConditionalUnit).getIf())
			helpUnitElement.getThen().addAll((unitElement.get(0)as ConditionalUnit).getThen())
			if((unitElement.get(0)as ConditionalUnit).getElse.size>0){
				helpUnitElement.getElse().addAll((unitElement.get(0)as ConditionalUnit).getElse())
			}
			helpUnit.unitElements.add(helpUnitElement)
		}else if(unitElement.get(0) instanceof IteratedUnitImpl){
			var IteratedUnit helpUnitElement=Henshin_textFactory.eINSTANCE.createIteratedUnit()
			helpUnitElement.setIterations((unitElement.get(0)as IteratedUnit).iterations)
			helpUnitElement.subElement.addAll((unitElement.get(0)as IteratedUnit).subElement)
			helpUnit.unitElements.add(helpUnitElement)
		}else if(unitElement.get(0) instanceof LoopUnitImpl){
			var LoopUnit helpUnitElement=Henshin_textFactory.eINSTANCE.createLoopUnit()
			helpUnitElement.subElement.addAll((unitElement.get(0)as LoopUnit).subElement)
			helpUnit.unitElements.add(helpUnitElement)
		}else if(unitElement.get(0) instanceof PriorityUnitImpl){
			var PriorityUnit helpUnitElement=Henshin_textFactory.eINSTANCE.createPriorityUnit()
			helpUnitElement.listOfLists.addAll((unitElement.get(0)as PriorityUnit).listOfLists)
			helpUnit.unitElements.add(helpUnitElement)
		}else if(unitElement.get(0) instanceof IndependentUnitImpl){
			var IndependentUnit helpUnitElement=Henshin_textFactory.eINSTANCE.createIndependentUnit()
			helpUnitElement.listOfLists.addAll((unitElement.get(0)as IndependentUnit).listOfLists)
			helpUnit.unitElements.add(helpUnitElement)
		}
		return helpUnit			
	}
	
	
	/**
	 * Prueft ob eine Liste von Elementen einer Unit eine SequentialUnit bilden 
	 * 
	 * @param unitElements Uniterelemente einer Unit
	 * @return True: Unit ist eine SequentialUnit, False: Unit ist keine SequentialUnit 
	 */	
	def Boolean isSequence(List<UnitElement> unitElements){
		if((unitElements.size>1)||(Iterables.filter(unitElements,typeof(CallImpl)).size>0)||(Iterables.filter(unitElements,typeof(SequentialPropertiesImpl)).size>0)||((unitElements.size==1)&&(unitElements.get(0).subSequence.size>0))){
			return true
		}
		return false
	}

	
	/**
	 * Prüft ob eine Liste von UnitElementen nur aus einem einzelnen Call bestehen
	 * 
	 * @param elements Liste von Elementen einer Unit
	 * @return True: Liste besteht nur aus einem Call, False: Liste besteht aus mehreren Elementen oder ist kein Call
	 */
	def Boolean isCallOnly(List<UnitElement> elements){
		return (elements.size==1)&&(elements.get(0) instanceof CallImpl)
	}
	
	/**
	 * Prueft ob die Elemente einer SequentialUnit aus Calls und SequentialProperties bestehen
	 * 
	 * @param elements Elemente einer SequentialUnit
	 * @return True: Unit besteht aus Calls und SequentialProperties, False: Unit besteht aus anderen Elementen
	 */	
	def Boolean isCallSequence(List<UnitElement> elements){
		for(e:elements){
			if((e.subSequence.size>0)||!((e instanceof Call)||(e instanceof SequentialProperties))){
				return false
			}
		}
		return true
	}
	
}