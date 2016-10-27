/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.simulator;

import java.util.PriorityQueue;
import java.util.Comparator;

/**
 *
 * @author BTKS
 */
public class EventQueue {
    
    public PriorityQueue<ECB> events;
    
    public EventQueue() {
        this.events = new PriorityQueue<ECB>(10, new EventClockTimeComparator());
    }
    
    public void enQueue(ECB event) {
        this.events.add(event);
    }
    
    public void deQueue(ECB event) {
        this.events.remove(event);
    }
    
    public boolean isEmpty() {
        return events.isEmpty();
    }
    
    public ECB poll() {
        return events.poll();
    }
    
    public ECB peek() {
        return events.peek();
    }
    
    public int size() {
        return events.size();
    }
    
    public class EventClockTimeComparator implements Comparator<ECB>
    {
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