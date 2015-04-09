import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import bl.*;
import Helpers.XMLParser;


public class Program extends Thread{

	//private static BlockingQueue<Car> blockingQueue;
	
	public static void main(String[] args) {
		File file = new File("data.xml");
		GasStation gasStation;
		
		try {
			GasStationXMLParserHandler gasStationXMLParser = new GasStationXMLParserHandler(file);
			gasStation = gasStationXMLParser.parseToGasStation();
			gasStation.startGasStation();
			
		} catch (ParserConfigurationException | SAXException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	
	}
}
