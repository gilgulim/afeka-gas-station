package XMLHandler;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.beans.util.Cache;
import com.sun.org.apache.bcel.internal.generic.NEW;

import bl.Car;
import bl.CleaningServices;
import bl.FuelPump;
import bl.FuelRepository;
import bl.GasStation;
import bl.Person;
import bl.WashingMachine;
import bl.WashingTeam;

public class GasStationXMLParserHandler {
	private XMLParser xmlParser;
	private GasStation gasStation;
	
	public GasStationXMLParserHandler(File xmlFile) throws ParserConfigurationException, SAXException, IOException{
		xmlParser = new XMLParser(xmlFile);
	}

	public FuelRepository parseToFuelRepository(Node fuelRepNode){
		FuelRepository fuelRep;
		String maxCapacity = xmlParser.getNodeAttr("maxCapacity", fuelRepNode);
		String currentCapacity = xmlParser.getNodeAttr("currentCapacity", fuelRepNode);
		
		fuelRep = new FuelRepository(Integer.parseInt(currentCapacity), Integer.parseInt(maxCapacity));
		
		return fuelRep;	
	}
	
	public FuelPump parseToFuelPump(){
		FuelPump fp = new FuelPump();
		return fp;
	}
	
	public CleaningServices parseToCleanSrv(Node cleanSrvNode){
		CleaningServices cleanSrv = new CleaningServices();
		NodeList teamsNodeList = cleanSrvNode.getChildNodes();
		String numOfMachines = xmlParser.getNodeAttr("numOfMachines", cleanSrvNode);
		
		for (int i=0; i<teamsNodeList.getLength(); i++){
			if(teamsNodeList.item(i).getNodeName().equals("Team")){
				Node teamNode = xmlParser.getNodeByIndex(teamsNodeList, i);
				WashingTeam washingTeam = parseToWashingTeam(teamNode);
				cleanSrv.addWashTeam(washingTeam);
			}
		}
		
		for (int i=0; i<Integer.parseInt(numOfMachines); i++){
			WashingMachine washMachine = new WashingMachine();
			cleanSrv.addWashMachine(washMachine);
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
		
		car = new Car(Integer.parseInt(carId), wantCleanBool, wantFuelBool);
		
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
	
	public GasStation parseToGasStation() {
		Node rootNode = xmlParser.getNode("GasStation", xmlParser.getChildNode());
		String gasStationName = xmlParser.getNodeAttr("name", rootNode);
		gasStation = new GasStation(gasStationName);
		
		String fuelPricePerLiter = xmlParser.getNodeAttr("pricePerLiter", rootNode);
		gasStation.setFuelPricePerLiter(Float.parseFloat(fuelPricePerLiter));
		
		String washPrice = xmlParser.getNodeAttr("washPrice", rootNode);
		gasStation.setCarWashPrice(Integer.parseInt(washPrice));
		
		String secondsPerAutoClean = xmlParser.getNodeAttr("secondsPerAutoClean", rootNode);
		gasStation.setAutoWashTimeToClean(Integer.parseInt(secondsPerAutoClean));
		
		String numOfPumps = xmlParser.getNodeAttr("numOfPumps", rootNode);
		for (int i=0; i<Integer.parseInt(numOfPumps); i++){
			gasStation.addPump(parseToFuelPump());
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
				gasStation.addCar(parseToCar(carNode));
			}
		}	
		return gasStation;
	}
}
