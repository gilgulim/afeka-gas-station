import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import bl.*;
import XMLHandler.GasStationXMLParserHandler;
import XMLHandler.XMLParser;


public class Program {

	private static BlockingQueue<Car> blockingQueue;
	
	public static void main(String[] args) {
		
		/*File file = new File("data.xml");
		GasStation gasStation;
		
		try {
			GasStationXMLParserHandler gasStationXMLParser = new GasStationXMLParserHandler(file);
			gasStation = gasStationXMLParser.parseToGasStation();
			System.out.println(gasStation);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		blockingQueueTest();

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
