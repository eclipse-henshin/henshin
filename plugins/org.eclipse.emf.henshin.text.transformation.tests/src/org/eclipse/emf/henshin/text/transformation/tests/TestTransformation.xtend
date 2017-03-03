package org.eclipse.emf.henshin.text.transformation.tests

import org.w3c.dom.Document
import org.eclipse.emf.henshin.text.transformation.tests.util.AdaptID
import org.eclipse.emf.henshin.text.transformation.tests.util.Compare
import org.junit.Assert
import org.junit.Test
import org.eclipse.emf.henshin.text.transformation.tests.util.DocumentUtil
import java.io.File
import org.eclipse.emf.henshin.text.ui.util.Transformation

class TestTransformation {
	val DocumentUtil document= new DocumentUtil()
	val AdaptID adaptID =new AdaptID()
	val Compare compare=new Compare()
	val Transformation transfom=new Transformation()
	val transformationPath="platform:/resource/org.eclipse.emf.henshin.text.transformation/transforms/Henshin_text2HenshinTransformation/Henshin_text2HenshinTransformation.qvto"

	
	@Test
	def bankTest(){
		val originalPath="testCases/bank/bank.henshin"
		val transformationPath=document.saveResource(transfom.transformHenshin_textToHenshin(null,transformationPath,"testCases/bank/bank.henshin_text"))
		val adaptPath=transformationPath.replace("henshin_text.henshin","henshin_textAdapt.henshin")
		assertModelEquality(originalPath,transformationPath,adaptPath)	
		cleanUp(transformationPath)	
		cleanUp(adaptPath)	 
	}
	
	@Test
	def gridFullNestedTest(){
		val originalPath="testCases/grid-full_nestedUnits/grid-full.henshin"
		val transformationPath=document.saveResource(transfom.transformHenshin_textToHenshin(null,transformationPath,"testCases/grid-full_nestedUnits/grid-full.henshin_text"))
		val adaptPath=transformationPath.replace("henshin_text.henshin","henshin_textAdapt.henshin")
		assertModelEquality(originalPath,transformationPath,adaptPath)
		cleanUp(transformationPath)	
		cleanUp(adaptPath)		 
	}
	
	@Test
	def gridFullTest(){
		val originalPath="testCases/grid-full/grid-full.henshin"
		val transformationPath=document.saveResource(transfom.transformHenshin_textToHenshin(null,transformationPath,"testCases/grid-full/grid-full.henshin_text"))
		val adaptPath=transformationPath.replace("henshin_text.henshin","henshin_textAdapt.henshin")
		assertModelEquality(originalPath,transformationPath,adaptPath)
		cleanUp(transformationPath)	
		cleanUp(adaptPath)		 
	}
	
	@Test
	def moviesTest(){
		val originalPath="testCases/movies/movies.henshin"
		val transformationPath=document.saveResource(transfom.transformHenshin_textToHenshin(null,transformationPath,"testCases/movies/movies.henshin_text"))
		val adaptPath=transformationPath.replace("henshin_text.henshin","henshin_textAdapt.henshin")
		assertModelEquality(originalPath,transformationPath,adaptPath)
		cleanUp(transformationPath)	
		cleanUp(adaptPath)		 
	}
	
	@Test
	def mutualexclusionTest(){
		val originalPath="testCases/mutualexclusion/mutualexclusion.henshin"
		val transformationPath=document.saveResource(transfom.transformHenshin_textToHenshin(null,transformationPath,"testCases/mutualexclusion/mutualexclusion.henshin_text"))
		val adaptPath=transformationPath.replace("henshin_text.henshin","henshin_textAdapt.henshin")
		assertModelEquality(originalPath,transformationPath,adaptPath)
		cleanUp(transformationPath)	
		cleanUp(adaptPath)		 
	}
	
	def void assertModelEquality(String originalPath,String transformationPath,String adaptPath){	
		if(adaptPath.equals("Error! Can not save!")){
			Assert::fail(adaptPath)
		}
		val Document originalXML = document.getDocument(originalPath)
		var Document transformationXML = document.getDocument(transformationPath)
		if((originalXML==null) || (transformationXML==null)){
			Assert::fail("Can not load file!");
		}
		adaptID.adaptID(originalXML,transformationXML,adaptPath)
		transformationXML = document.getDocument(adaptPath)
		var message=compare.compareAttributesByNode(originalXML.getChildNodes(),transformationXML.getChildNodes())
		if(!(message.equals(""))){
			Assert::fail(message)
		}
		message=compare.emfCompare(originalPath,adaptPath)
		if(!(message.equals(""))){
			Assert::fail(message)
		}
	}
	
	def void cleanUp(String path){
		val File currentFile = new File(path);
   		//currentFile.delete()
	}

}