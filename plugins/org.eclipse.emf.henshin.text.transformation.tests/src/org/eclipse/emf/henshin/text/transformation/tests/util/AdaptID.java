package org.eclipse.emf.henshin.text.transformation.tests.util;

import java.io.File;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class AdaptID {
	
	
	/**
	 * Adapt ids in transformed henshin_text file (transformationXML) to fit the ids in the original (originalXML)
	 * 
	 * @param originalXML Original henshin transformation
	 * @param transformationXML Transformed henshin_text transformation
	 * @param adaptPath Path where the adapted henshin_text transformation file should be saved
	 */
	public void adaptID(Document originalXML,Document transformationXML,String adaptPath){
		adaptIDinNodes(originalXML.getChildNodes(),transformationXML.getChildNodes(),transformationXML.getChildNodes());
		try{
			//Save adaped henshin Transformation
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Result output = new StreamResult(new File(adaptPath));
			Source input = new DOMSource(transformationXML);
			transformer.transform(input, output);
		}catch(TransformerException  e) {
			Assert.fail("Can not save adapted file");
		}
	}
	
	
	/**
	 * Iterates through all Nodes in originalNodes and checks if they have an attribute called xmi:id and replace its value in the corresponding element in transformationNodes
	 * 
	 * @param originalNodes Nodes from the original henshin transformation (dependent on recursion)
	 * @param transformationNodes Nodes from the transformed henshin_text file (dependent on recursion)
	 * @param transformationXML Nodes from the transformed henshin_text file 
	 */
	private void adaptIDinNodes(NodeList originalNodes,NodeList transformationNodes,NodeList transformationXML){
		if(originalNodes.getLength()>=transformationNodes.getLength()){
			for(int i=0;(i<originalNodes.getLength()&&i<transformationNodes.getLength());i++){
				if(originalNodes.item(i).hasAttributes()){
					if((originalNodes.item(i).getAttributes().getNamedItem("xmi:id")!=null)&&(transformationNodes.item(i).getAttributes().getNamedItem("xmi:id")!=null)&&
							(originalNodes.item(i).getAttributes().getNamedItem("xmi:id").getNodeValue()!=transformationNodes.item(i).getAttributes().getNamedItem("xmi:id").getNodeValue())&&
							(originalNodes.item(i).getNodeName()==transformationNodes.item(i).getNodeName())){
						replace(transformationNodes.item(i).getAttributes().getNamedItem("xmi:id").getNodeValue(),originalNodes.item(i).getAttributes().getNamedItem("xmi:id").getNodeValue(),transformationXML);
					}
				}
				adaptIDinNodes(originalNodes.item(i).getChildNodes(),transformationNodes.item(i).getChildNodes(),transformationNodes);
			}
		}
		
	}
	
	
	/**
	 * Replaces the attribute value oldID with newID
	 * 
	 * @param oldID Attribute value which should be replaced
	 * @param newID New attribute value
	 * @param transformationNodes List of nodes whose attribute values are to be replaced
	 */
	private void replace(String oldID, String newID,NodeList transformationNodes){
		for(int i=0;i<transformationNodes.getLength();i++){
			if(transformationNodes.item(i).hasAttributes()){
				for(int j=0;j<transformationNodes.item(i).getAttributes().getLength();j++){
					transformationNodes.item(i).getAttributes().item(j).setNodeValue(transformationNodes.item(i).getAttributes().item(j).getNodeValue().replace(oldID,newID));
				}
			}
			replace(oldID,newID,transformationNodes.item(i).getChildNodes());
		}
	}

}
