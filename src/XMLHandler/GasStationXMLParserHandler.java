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

	public GasStation parseToGasStation() {
		Node rootNode = xmlParser.getNode("GasStation", xmlParser.getChildNode());
		String gasStationName = xmlParser.getNodeAttr("name", rootNode);
		String fuelPricePerLiter = xmlParser.getNodeAttr("pricePerLiter", rootNode);
		String numOfPumps = xmlParser.getNodeAttr("numOfPumps", rootNode);
		String washPrice = xmlParser.getNodeAttr("washPrice", rootNode);
		String secondsPerAutoClean = xmlParser.getNodeAttr("secondsPerAutoClean", rootNode);
		
		gasStation = new GasStation(gasStationName);
		gasStation.setFuelPricePerLiter(Float.parseFloat(fuelPricePerLiter));
		gasStation.setCarWashPrice(Integer.parseInt(washPrice));
		gasStation.setAutoWashTimeToClean(Integer.parseInt(secondsPerAutoClean));
		
		for (int i=0; i<Integer.parseInt(numOfPumps); i++){
			FuelPump fuelPump = new FuelPump();
			gasStation.addPump(fuelPump);
		}
		
		NodeList rootEntitiesNodeList = rootNode.getChildNodes();
		
		Node fuelPoolNode = xmlParser.getNode("MainFuelPool", rootEntitiesNodeList);
		String maxCapacity = xmlParser.getNodeAttr("maxCapacity", fuelPoolNode);
		String currentCapacity = xmlParser.getNodeAttr("currentCapacity", fuelPoolNode);
		
		FuelRepository fuelRep = new FuelRepository(Integer.parseInt(currentCapacity), Integer.parseInt(maxCapacity));
		gasStation.setFuelRep(fuelRep);
		
		Node cleanServiceNode = xmlParser.getNode("CleaningService", rootEntitiesNodeList);
		String numOfMachines = xmlParser.getNodeAttr("numOfMachines", cleanServiceNode);
		
		CleaningServices cleaningService = new CleaningServices();
		NodeList teamsNodeList = cleanServiceNode.getChildNodes();
		
		for (int i=0; i<teamsNodeList.getLength(); i++){
			if(teamsNodeList.item(i).getNodeName().equals("Team")){
				Node teamNode = xmlParser.getNodeByIndex(teamsNodeList, i);
				NodeList employeesNodeList = teamNode.getChildNodes();
				WashingTeam washTeam = new WashingTeam();
				
				for (int j=0; j < employeesNodeList.getLength() ; j++){
					if(employeesNodeList.item(j).getNodeName().equals("Employee")){
						Node employeeNode = xmlParser.getNodeByIndex(employeesNodeList, j);	
						String employeeName = xmlParser.getNodeAttr("name", employeeNode);
						washTeam.addEmployee(employeeName);
					}
				}
				cleaningService.addWashTeam(washTeam);
			}
		}
		
		for (int i=0; i<Integer.parseInt(numOfMachines); i++){
			WashingMachine washMachine = new WashingMachine();
			cleaningService.addWashMachine(washMachine);
		}
		gasStation.setCleaningSrv(cleaningService);
		
		Node carsNode = xmlParser.getNode("Cars", rootEntitiesNodeList);
		NodeList carNodeList = carsNode.getChildNodes();
		
		for (int i=0; i< carNodeList.getLength() ; i++){
			if (carNodeList.item(i).getNodeName().equals("Car")){
				Node carNode = xmlParser.getNodeByIndex(carNodeList, i);
				String carId = xmlParser.getNodeAttr("id", carNode);
				String wantClean = xmlParser.getNodeAttr("wantCleaning", carNode);
				
				boolean wantCleanBool = false;
				if (wantClean != ""){
					wantCleanBool = Boolean.parseBoolean(wantClean);
				}
				System.out.println();
				NodeList wantFuelNodeList = carNode.getChildNodes();
				boolean wantFuelBool = false;
				
				if (wantFuelNodeList.getLength() != 0){
					wantFuelBool = true;
				}

				Car car = new Car(Integer.parseInt(carId), wantCleanBool, wantFuelBool);
				
				for (int j=0; j<wantFuelNodeList.getLength(); j++){
					if(wantFuelNodeList.item(j).getNodeName().equals("WantsFuel")){
						Node wantFuelNode = xmlParser.getNodeByIndex(wantFuelNodeList, j);
						String numOfLiters = xmlParser.getNodeAttr("numOfLiters", wantFuelNode);
						String pumpNum = xmlParser.getNodeAttr("pumpNum", wantFuelNode);
						
						car.setFuelAmountRequired(Integer.parseInt(numOfLiters));
						car.setPumpIndex(Integer.parseInt(pumpNum));
					}	
				}
				gasStation.addCar(car);
			}
		}	
		return gasStation;
	}
}
