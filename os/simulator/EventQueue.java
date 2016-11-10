package os.simulator;

import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * A queue for holding Events (ECB).
 * 
 * @author BTKS
 */
public class EventQueue {
    
    /**
     * Priority queue for the events.
     */
    public PriorityQueue<ECB> events;
    
    /**
     * Construct a new EventQueue.
     */
    public EventQueue() {
        this.events = new PriorityQueue<ECB>(10, new EventClockTimeComparator());
    }
    
    /**
     * Add an event to the queue.
     * 
     * @param event the event
     */
    public void enQueue(ECB event) {
        this.events.add(event);
    }
    
    /**
     * Remove the specified event from the queue.
     * 
     * @param event the event
     */
    public void deQueue(ECB event) {
        this.events.remove(event);
    }
    
    /**
     * Check if the queue is empty.
     * @return true if the queue is empty
     */
    public boolean isEmpty() {
        return events.isEmpty();
    }
    
    /**
     * Remove the event from the queue.
     * 
     * @return the event
     */
    public ECB poll() {
        return events.poll();
    }
    
    /**
     * Returns the event from the queue without removing it.
     * 
     * @return the event
     */
    public ECB peek() {
        return events.peek();
    }
    
    /**
     * Returns the size of the queue.
     * 
     * @return size of the queue
     */
    public int size() {
        return events.size();
    }
    
    /**
     * A comparator used for comparing clock times on the events.
     */
    public class EventClockTimeComparator implements Comparator<ECB> {
        @Override
        public int compare(ECB event1, ECB event2)
        {
            if (event1.getClockTime() < event2.getClockTime()) {
                return -1;
            }
            
            if (event1.getClockTime() > event2.getClockTime()) {
                return 1;
            }
            
            return 0;
        }
    }
}