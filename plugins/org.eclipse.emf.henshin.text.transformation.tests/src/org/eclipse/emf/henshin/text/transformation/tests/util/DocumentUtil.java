package org.eclipse.emf.henshin.text.transformation.tests.util;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.emf.common.util.URI;
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
	public Document getDocument(String path) throws SAXException, IOException {
		return this.documentBuilder.parse(new File(path));
	}

	/**
	 * Saves given resource
	 *
	 * @param resource Resource which should be saved
	 * @return Success: Path of the saved resource. Error: Error message
	 */
	public String saveResource(Resource resource) throws IOException {
		resource.save(Collections.emptyMap());
		URI uri = resource.getURI();
		//FIXME: check delimiter
		return String.join("/", uri.segmentsList().subList(uri.segmentCount() - 3, uri.segmentCount()));
	}


}
