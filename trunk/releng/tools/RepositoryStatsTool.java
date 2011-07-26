import java.io.FileWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class RepositoryStatsTool {
	
	private static final String BASE_URL = "http://www.eclipse.org/modeling/emft/henshin";
	
	private static void updateRepository(String file, String build) throws Exception {
		
		// Update the XML:
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document dom = db.parse(file);
		Element root = dom.getDocumentElement();
		NodeList properties = root.getElementsByTagName("properties");
		if (properties!=null) {
			for (int i=0 ; i<properties.getLength(); i++) {
				Element elem = (Element) properties.item(i);
				Element parent = (Element) elem.getParentNode();
				if ("repository".equals(parent.getNodeName())) {
					int size = Integer.parseInt(elem.getAttribute("size"));
					elem.setAttribute("size", String.valueOf(size + 1));
					Element newNode = dom.createElement("property");
					newNode.setAttribute("name", "p2.statsURI");
					newNode.setAttribute("value", BASE_URL);
					elem.appendChild(newNode);
				}
				else if ("artifact".equals(parent.getNodeName()) &&
						"org.eclipse.emf.henshin.model".equals(parent.getAttribute("id"))) {
					int size = Integer.parseInt(elem.getAttribute("size"));
					elem.setAttribute("size", String.valueOf(size + 1));
					Element newNode = dom.createElement("property");
					newNode.setAttribute("name", "download.stats");
					newNode.setAttribute("value", "stats-update-" + build + ".php");
					elem.appendChild(newNode);
				}
				
			}
		}

		// Save the result:
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		FileWriter writer = new FileWriter(file+"1");
		StreamResult result = new StreamResult(writer);
		DOMSource source = new DOMSource(dom);
		transformer.transform(source, result);
		writer.close();
	}
	
	public static void main(String argv[]) {
		if (argv.length!=2) {
			System.err.println("I need two parameters: the artifacts.xml file and the build type, e.g. 'N' or 'R'");
			System.exit(1);
		}
		try {
			updateRepository(argv[0], argv[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
