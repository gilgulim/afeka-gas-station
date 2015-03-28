import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import XMLHandler.XMLParser;


public class Program {

	public static void main(String[] args) {
		File file = new File("data.xml");
		try {
			XMLParser xmlParser = new XMLParser(file);
			
			System.out.println(xmlParser.getDoc().getDocumentElement().getNodeName());
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
