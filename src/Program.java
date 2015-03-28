import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import bl.GasStation;
import XMLHandler.XMLParser;


public class Program {

	public static void main(String[] args) {
		File file = new File("data.xml");
		GasStation gasStation;
		
		try {
			XMLParser xmlParser = new XMLParser(file);
			gasStation = xmlParser.parseToGasStation();
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
