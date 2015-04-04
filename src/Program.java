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

	private static BlockingQueue<Car> blockingQueue;
	
	public static void main(String[] args) {
		File file = new File("data.xml");
		GasStation gasStation;
		Vector<Car> carsVector;
		AutoCleaningDispatcher cleaningTeamDispatcher;
		
		try {
			GasStationXMLParserHandler gasStationXMLParser = new GasStationXMLParserHandler(file);
			gasStation = gasStationXMLParser.parseToGasStation();
			cleaningTeamDispatcher = new AutoCleaningDispatcher(gasStation);
			cleaningTeamDispatcher.start();
			carsVector = gasStationXMLParser.getCarsVector();
			
			for (int i=0; i<carsVector.size(); i++){
				cleaningTeamDispatcher.addCarToAutoWashQueue(carsVector.elementAt(i));
			}
					
			
		} catch (ParserConfigurationException | SAXException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		
		//blockingQueueTest();
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
				//car.getGasStaion().addCarToFuelPumpsQueue(car);
			}
		}else if(isRequiredWash){
			//push to wash queue
			car.setRequiresWash(false);
			//car.getGasStaion().addCarToCleaningServiceQueue(car);
		}else{
			//exit gas station
			car.setGasStaion(null);
		}
	}
	
	
	public static void blockingQueueTest(){
		
		blockingQueue = new LinkedBlockingDeque<Car>();
		
		
		Thread popThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				System.out.println("POP_THREAD: Polling a car from the queue");
				try {
					blockingQueue.take();
					System.out.println("POP_THREAD: Car polled from the queue");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		
		
		Thread pushThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				
				try {
					System.out.println("PUSH_THREAD: Waiting for a while");
					Thread.sleep(8000);
				
				
					System.out.println("PUSH_THREAD: Pushing a car to the queue");
					blockingQueue.put(new Car(1, true, true,null));
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		popThread.start();
		pushThread.start();
		
		
	}

}
