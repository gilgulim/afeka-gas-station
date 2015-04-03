import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.management.modelmbean.RequiredModelMBean;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import bl.*;
import XMLHandler.GasStationXMLParserHandler;
import XMLHandler.XMLParser;


public class Program {

	public static void main(String[] args) {
		File file = new File("data.xml");
		GasStation gasStation;
		Vector<Car> carsVector;
		
		try {
			GasStationXMLParserHandler gasStationXMLParser = new GasStationXMLParserHandler(file);
			gasStation = gasStationXMLParser.parseToGasStation();
			carsVector = gasStationXMLParser.getCarsVector();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		//TODO: start cleaning service
		//TODO: start all pumps
		//TODO: add cars to blocking queue
	}
	
	public void carDispatcher(Car car){
		Boolean isRequiredFuel = car.isRequiresFuel();
		Boolean isRequiredWash = car.isRequiresWash();
		
		if (isRequiredFuel){
			if(isRequiredWash){
				//decide shortest option
				//TODO: complete method;
			}else{
				//push to fuel queue
				car.getGasStaion().addCarToFuelPumpsQueue(car);
			}
		}else if(isRequiredWash){
			//push to wash queue
			car.setRequiresWash(false);
			car.getGasStaion().addCarToCleaningServiceQueue(car);
		}else{
			//exit gas station
			car.getGasStaion().dissmissCarFromGasStation(car);
			car.setGasStaion(null);
		}
	}

}
