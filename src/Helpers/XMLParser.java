package Helpers;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bl.CleaningServices;
import bl.GasStation;
import bl.Person;
import bl.WashingTeam;

public class XMLParser {

	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;
	
	public XMLParser(File xmlFile) throws ParserConfigurationException, SAXException, IOException{
		dbFactory = DocumentBuilderFactory.newInstance();
		dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize(); 
	}
	
	public NodeList getChildNode() {
	    return doc.getChildNodes();
	}
	
	public Node getNode(String tagName, NodeList nodes) {
	    for ( int i = 0;  i < nodes.getLength(); i++ ) {
	        Node node = nodes.item(i);
	        if (node.getNodeName().equals(tagName)) {
	            return node;
	        }
	    }
	 
	    return null;
	}
	
	public Node getNodeByIndex(NodeList nodes, int index){
		if (index >= nodes.getLength()){
			return null;
		}
		return nodes.item(index);
	}
 
	public String getNodeValue(String tagName, NodeList nodes ) {
	    for ( int i = 0; i < nodes.getLength(); i++ ) {
	        Node node = nodes.item(i);
	        if (node.getNodeName().equals(tagName)) {
	            NodeList childNodes = node.getChildNodes();
	            for (int j = 0; j < childNodes.getLength(); j++ ) {
	                Node data = childNodes.item(j);
	                if ( data.getNodeType() == Node.TEXT_NODE )
	                    return data.getNodeValue();
	            }
	        }
	    }
	    return "";
	}
	 
	public String getNodeAttr(String attrName, Node node ) {
	    NamedNodeMap attrs = node.getAttributes();
	    for (int i = 0; i < attrs.getLength(); i++ ) {
	        Node attr = attrs.item(i);
	        if (attr.getNodeName().equals(attrName)) {
	            return attr.getNodeValue();
	        }
	    }
	    return "";
	}

}


