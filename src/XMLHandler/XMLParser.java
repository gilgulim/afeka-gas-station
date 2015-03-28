package XMLHandler;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bl.CleaningServices;
import bl.GasStation;
import bl.WashingTeam;

public class XMLParser {

	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;
	private GasStation gasStation;
	
	public XMLParser(File xmlFile) throws ParserConfigurationException, SAXException, IOException{
		dbFactory = DocumentBuilderFactory.newInstance();
		dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize(); //concatenate strings into single line
	}

	public GasStation parseToGasStation(){
		String gasStationName = doc.getDocumentElement().getAttribute("name");
		String fuelPricePerLiter = doc.getDocumentElement().getAttribute("pricePerLiter");
		String washPrice = doc.getDocumentElement().getAttribute("washPrice");
		String autoWashTimeToClean = doc.getDocumentElement().getAttribute("secondsPerAutoClean");
		gasStation = new GasStation(gasStationName);
		gasStation.setFuelPricePerLiter(Float.parseFloat(fuelPricePerLiter));
		gasStation.setAutoWashTimeToClean(Integer.parseInt(autoWashTimeToClean));
		gasStation.setCarWashPrice(Integer.parseInt(washPrice));
		
		gasStation.setCleaningSrv(parseToCleanSrv());
		return gasStation;
	}

	private CleaningServices parseToCleanSrv() {
		CleaningServices cleanService;
		NodeList cleanSrvXMLNode= doc.getElementsByTagName("CleaningService");
		int numOfTeams = Integer.parseInt(doc.getDocumentElement().getAttribute("numOfTeams"));
				
		for (int i=0; i< numOfTeams ; i++){
			parseToCleaningTeam(cleanService);
		}
		
		 int numOfMachines = Integer.parseInt(doc.getDocumentElement().getAttribute("numOfMachines"));
		for (int i=0; i< numOfMachines ; i++){
				
		}		 
		 
		return null;
	}

	private void parseToCleaningTeam(CleaningServices cleanService) {
		WashingTeam washingTeam;
		NodeList teamXMLNode= doc.getElementsByTagName("Team");
		int numOfEmployees = Integer.parseInt(doc.getDocumentElement().getAttribute("numOfEmployees"));
		
		for (int i=0; i< numOfEmployees ; i++){
			parseToEmployee(washingTeam);
		}		
	}

	private void parseToEmployee(WashingTeam washingTeam) {
		washingTeam.addEmployee(name);
		
	}

}
