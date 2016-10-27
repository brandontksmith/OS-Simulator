/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.simulator;

/**
 *
 * @author BTKS
 */
public class InterruptProcessor {
    
    private EventQueue eventQueue;
    private Scheduler scheduler;
    
    public InterruptProcessor(Scheduler scheduler) {
        this.eventQueue = new EventQueue();
        this.scheduler = scheduler;
    }
    
    public void signalInterrupt(int clockTime) {
        boolean hasEventsMatchingTime = true;
        
        while (hasEventsMatchingTime) {
            if (eventQueue.peek().getClockTime() == clockTime && eventQueue.peek().getEventType() == EventType.INTERRUPT) {
                ECB event = eventQueue.poll();
            } else {
                hasEventsMatchingTime = false;
            }
        }
    }
    
    public void addEvent(PCB process, int cycles, EventType eventType) {
        ECB event = new ECB(process, cycles, eventType);
        this.eventQueue.enQueue(event);
    }
    
    public void getEvent() {}

    public EventQueue getEventQueue() {
        return eventQueue;
    }

    public void setEventQueue(EventQueue eventQueue) {
        this.eventQueue = eventQueue;
    }
}
