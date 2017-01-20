package org.eclipse.emf.henshin.text.transformation.tests.util;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.emf.ecore.resource.Resource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;



public class DocumentUtil {
	private DocumentBuilderFactory factory;
	private DocumentBuilder documentBuilder;
	
	public DocumentUtil() throws ParserConfigurationException{
		this.factory = DocumentBuilderFactory.newInstance();
		this.documentBuilder=factory.newDocumentBuilder();
	}
	
	/**
	 * Parse the file at the given path and return it as Document-Object
	 * @param path Path of the file which should be parsed
	 * @return Document-Object of the parsed file
	 */
	public Document getDocument(String path){	
		try {
			Document document = this.documentBuilder.parse(new File(path));
			return document;
		} catch (SAXException | IOException e) {
			return null;
		}
	}
	
	/**
	 * Saves given resource
	 * @param resource Resource which should be saved
	 * @return Success: Path of the saved resource. Error: Error message 
	 */
	public String saveResource(Resource resource){
		try{
			resource.save(Collections.EMPTY_MAP);
			return resource.getURI().toString().replace("platform:/resource/org.eclipse.emf.henshin.text.transformation.tests/","");
		}catch(IOException e){
			return "Error! Can not save!";
		}
	}
	

}
