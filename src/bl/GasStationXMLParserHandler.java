package bl;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Helpers.XMLParser;

public class GasStationXMLParserHandler {
	private XMLParser xmlParser;
	private GasStation gasStation;
	private Vector<Car> carsVector;
	
	public GasStationXMLParserHandler(File xmlFile) throws ParserConfigurationException, SAXException, IOException{
		xmlParser = new XMLParser(xmlFile);
		carsVector = new Vector<Car>();
	}

	public FuelRepository parseToFuelRepository(Node fuelRepNode){
		FuelRepository fuelRep;
		String maxCapacity = xmlParser.getNodeAttr("maxCapacity", fuelRepNode);
		String currentCapacity = xmlParser.getNodeAttr("currentCapacity", fuelRepNode);
		
		fuelRep = new FuelRepository(Integer.parseInt(currentCapacity), Integer.parseInt(maxCapacity));
		
		return fuelRep;	
	}
	
	
	public CleaningServices parseToCleanSrv(Node cleanSrvNode) throws InterruptedException{
		String washPriceStr = xmlParser.getNodeAttr("washPrice", cleanSrvNode);
		String autoWashTimeStr = xmlParser.getNodeAttr("autoWashTime", cleanSrvNode);
		
		CleaningServices cleanSrv = new CleaningServices(Integer.parseInt(washPriceStr), 
														Integer.parseInt(autoWashTimeStr));
		
		NodeList teamsNodeList = cleanSrvNode.getChildNodes();
		
		for (int i=0; i<teamsNodeList.getLength(); i++){
			if(teamsNodeList.item(i).getNodeName().equals("Team")){
				Node teamNode = xmlParser.getNodeByIndex(teamsNodeList, i);
				WashingTeam washingTeam = parseToWashingTeam(teamNode);
				cleanSrv.addWashTeam(washingTeam);
			}
		}
		
		return cleanSrv;
	}

	public WashingTeam parseToWashingTeam(Node teamNode) {
		WashingTeam washTeam = new WashingTeam();
		NodeList employeesNodeList = teamNode.getChildNodes();
		
		for (int j=0; j < employeesNodeList.getLength() ; j++){
			if(employeesNodeList.item(j).getNodeName().equals("Employee")){
				Node personNode = xmlParser.getNodeByIndex(employeesNodeList, j);
				Person person = parseToPerson(personNode);
				washTeam.addEmployee(person);
			}
		}
		
		return washTeam;
	}

	public Person parseToPerson(Node personNode) {
		String personName = xmlParser.getNodeAttr("name", personNode);
		Person person = new Person(personName);
		return person;
	}

	public Car parseToCar(Node carNode) {
		Car car;
		NodeList wantFuelNodeList = carNode.getChildNodes();
		String carId = xmlParser.getNodeAttr("id", carNode);
		String wantClean = xmlParser.getNodeAttr("wantCleaning", carNode);
		boolean wantFuelBool = false;
		boolean wantCleanBool = false;
		if (wantClean != ""){
			wantCleanBool = Boolean.parseBoolean(wantClean);
		}
				
		if (wantFuelNodeList.getLength() != 0){
			wantFuelBool = true;
		}
		
		car = new Car(Integer.parseInt(carId), wantCleanBool, wantFuelBool,null);
		
		for (int j=0; j<wantFuelNodeList.getLength(); j++){
			if(wantFuelNodeList.item(j).getNodeName().equals("WantsFuel")){
				Node wantFuelNode = xmlParser.getNodeByIndex(wantFuelNodeList, j);
				String numOfLiters = xmlParser.getNodeAttr("numOfLiters", wantFuelNode);
				String pumpNum = xmlParser.getNodeAttr("pumpNum", wantFuelNode);
				
				car.setFuelAmountRequired(Integer.parseInt(numOfLiters));
				car.setPumpIndex(Integer.parseInt(pumpNum));
			}	
		}
		return car;
	}
	
	public GasStation parseToGasStation() throws InterruptedException {
		Node rootNode = xmlParser.getNode("GasStation", xmlParser.getChildNode());
		String gasStationName = xmlParser.getNodeAttr("name", rootNode);
		
		String fuelPricePerLiterStr = xmlParser.getNodeAttr("pricePerLiter", rootNode);
		float fuelPricePerLiter = Float.parseFloat(fuelPricePerLiterStr);
		
		String pumpingPacePerLiterStr = xmlParser.getNodeAttr("pupingPacePerLiter", rootNode);
		int pupingPacePerLiter = Integer.parseInt(pumpingPacePerLiterStr);
		
		gasStation = new GasStation(gasStationName, fuelPricePerLiter, pupingPacePerLiter);
		
		String numOfPumpsStr = xmlParser.getNodeAttr("numOfPumps", rootNode);
		int numOfPumps =Integer.parseInt(numOfPumpsStr); 
		
		for (int i=0; i<numOfPumps; i++){
			gasStation.addPump(new FuelPump());
		}
		
		NodeList rootEntitiesNodeList = rootNode.getChildNodes();
		
		Node fuelRepNode = xmlParser.getNode("MainFuelPool", rootEntitiesNodeList);
		gasStation.setFuelRep(parseToFuelRepository(fuelRepNode));

		Node cleanServiceNode = xmlParser.getNode("CleaningService", rootEntitiesNodeList);
		gasStation.setCleaningSrv(parseToCleanSrv(cleanServiceNode));
		
		Node carsNode = xmlParser.getNode("Cars", rootEntitiesNodeList);
		NodeList carNodeList = carsNode.getChildNodes();
		
		for (int i=0; i< carNodeList.getLength() ; i++){
			if (carNodeList.item(i).getNodeName().equals("Car")){
				Node carNode = xmlParser.getNodeByIndex(carNodeList, i);
				Car car = parseToCar(carNode);
				car.setGasStaion(gasStation);
				carsVector.addElement(car);
			}
		}	
		return gasStation;
	}

	public Vector<Car> getCarsVector() {
		return carsVector;
	}
}
