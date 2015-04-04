package Helpers;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {

    private Queue<T> queue = new LinkedList<T>();

    public synchronized void put(T element) throws InterruptedException {
        queue.add(element);
        notify(); 
    }

    public synchronized T take() throws InterruptedException {
        while(queue.isEmpty()) {
            wait();
        }

        T item = queue.remove();
        notify(); 
        return item;
    }
}