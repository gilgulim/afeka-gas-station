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
		
		try {
			GasStationXMLParserHandler gasStationXMLParser = new GasStationXMLParserHandler(file);
			gasStation = gasStationXMLParser.parseToGasStation();

			carsVector = gasStationXMLParser.getCarsVector();
					
			cleanSrvTest(gasStation, carsVector);
		} catch (ParserConfigurationException | SAXException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		
		//blockingQueueTest();
		//TODO: start cleaning service
		//TODO: start all pumps
		//TODO: add cars to blocking queue
	}
	
	public static void cleanSrvTest(GasStation gasStation, Vector<Car> carsVector) throws InterruptedException {
		for(int i=0; i<carsVector.size(); i++){
			gasStation.getCleaningSrv().addCarToAutoWashQueue(carsVector.get(i));
		}
		CleaningServices cleanSrv = gasStation.getCleaningSrv();
		Runnable rCleanSrv = cleanSrv;
		Thread tCleanSrv = new Thread(rCleanSrv);
		tCleanSrv.start();
		CleaningTeamsManager cleanTeamMngr = cleanSrv.getCleanTeamMngr();
		Runnable rCleanTeamMngr = cleanTeamMngr;
		Thread tCleanTeamMngr = new Thread(rCleanTeamMngr);
		tCleanTeamMngr.start();
		tCleanSrv.start();
		
				
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
