package org.eclipse.emf.henshin.text.transformation.tests

import com.google.inject.Injector
import java.io.File
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.plugin.EcorePlugin
import org.eclipse.emf.henshin.text.Henshin_textStandaloneSetup
import org.eclipse.emf.henshin.text.transformation.tests.util.AdaptID
import org.eclipse.emf.henshin.text.transformation.tests.util.Compare
import org.eclipse.emf.henshin.text.transformation.tests.util.DocumentUtil
import org.eclipse.emf.henshin.text.ui.util.Transformation
import org.eclipse.emf.mwe.utils.StandaloneSetup
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.XtextResourceSet
import org.junit.Assert
import org.junit.Test
import org.w3c.dom.Document
import org.eclipse.core.runtime.FileLocator

class TestTransformation {
	val DocumentUtil document= new DocumentUtil()
	val AdaptID adaptID =new AdaptID()
	val Compare compare=new Compare()
	val Transformation transfom=new Transformation()
	val resourceURLPrefix = EcorePlugin.IS_ECLIPSE_RUNNING ? "platform:/plugin" : "platform:/resource"
	val text2HenshinTransformationPath = resourceURLPrefix +
		"/org.eclipse.emf.henshin.text.transformation/transforms/Henshin_text2HenshinTransformation/Henshin_text2HenshinTransformation.qvto"

	def createResource(String henshin_textPath) {
		// Load henshin_textFile standalone and imported EPackages
		new StandaloneSetup().setPlatformUri("..")
		var Injector injector = new Henshin_textStandaloneSetup().createInjectorAndDoEMFRegistration()
		var IResourceServiceProvider serviceProvider = injector.getInstance(IResourceServiceProvider)
		IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", serviceProvider)
		var XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet)
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE)

		var uri = resourceURLPrefix + "/org.eclipse.emf.henshin.text.transformation.tests/" + henshin_textPath
		if (EcorePlugin.IS_ECLIPSE_RUNNING) {
			uri = FileLocator.toFileURL(java.net.URI.create(uri).toURL).toURI.toString
		}
		var URI henshin_textUri = URI.createURI(uri)

		var String testcasePath = henshin_textPath.replace(henshin_textUri.lastSegment(), "")
		var File[] projectFolders = new File(testcasePath).listFiles()
		for (projectFolder : projectFolders) {
			if (projectFolder.getName().endsWith(".ecore")) {
				val other = henshin_textUri.trimSegments(1).appendSegment(projectFolder.getName())
				resourceSet.getResource(other, true)
			}
		}
		return resourceSet.getResource(henshin_textUri, true)
	}

	@Test
	def bankTest(){
		val originalPath="testCases/bank/bank.henshin"
		val transformationPath = document.saveResource(transfom.transformHenshin_textToHenshin(createResource(originalPath + "_text"), text2HenshinTransformationPath))
		val adaptPath=transformationPath.replace("henshin_text.henshin","henshin_textAdapt.henshin")
		assertModelEquality(originalPath,transformationPath,adaptPath)
		cleanUp(transformationPath)
		cleanUp(adaptPath)
	}

	@Test
	def gridFullNestedTest(){
		val originalPath="testCases/grid-full_nestedUnits/grid-full.henshin"
		val transformationPath = document.saveResource(transfom.transformHenshin_textToHenshin(createResource(originalPath + "_text"), text2HenshinTransformationPath))
		val adaptPath=transformationPath.replace("henshin_text.henshin","henshin_textAdapt.henshin")
		assertModelEquality(originalPath,transformationPath,adaptPath)
		cleanUp(transformationPath)
		cleanUp(adaptPath)
	}

	@Test
	def gridFullTest(){
		val originalPath="testCases/grid-full/grid-full.henshin"
		val transformationPath = document.saveResource(transfom.transformHenshin_textToHenshin(createResource(originalPath + "_text"), text2HenshinTransformationPath))
		val adaptPath=transformationPath.replace("henshin_text.henshin","henshin_textAdapt.henshin")
		assertModelEquality(originalPath,transformationPath,adaptPath)
		cleanUp(transformationPath)
		cleanUp(adaptPath)
	}

	@Test
	def moviesTest(){
		val originalPath="testCases/movies/movies.henshin"
		val transformationPath = document.saveResource(transfom.transformHenshin_textToHenshin(createResource(originalPath + "_text"), text2HenshinTransformationPath))
		val adaptPath=transformationPath.replace("henshin_text.henshin","henshin_textAdapt.henshin")
		assertModelEquality(originalPath,transformationPath,adaptPath)
		cleanUp(transformationPath)
		cleanUp(adaptPath)
	}

	@Test
	def mutualexclusionTest(){
		val originalPath="testCases/mutualexclusion/mutualexclusion.henshin"
		val transformationPath = document.saveResource(transfom.transformHenshin_textToHenshin(createResource(originalPath + "_text"), text2HenshinTransformationPath))
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
