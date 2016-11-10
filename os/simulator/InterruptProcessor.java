package os.simulator;

import java.util.LinkedList;

/**
 * The Interrupt Processor monitors events and generates interrupts, specifically for I/O.
 * @author BTKS
 */
public class InterruptProcessor {
    
    /**
     * Set to true when I/O has been started.
     */
    private boolean ioIsStarted;
    
    /**
     * Construct a new Interrupt Processor.
     */
    public InterruptProcessor() {
        this.ioIsStarted = false;
    }
    
    /**
     * Signals that an interrupt has occurred at the specified clock time.
     * @param clockTime the clock time
     * @return a list of the events that occurred at the specified time
     */
    public LinkedList<ECB> signalInterrupt(int clockTime) {
        boolean hasEventsMatchingTime = true;
        LinkedList<ECB> finishedEvents = new LinkedList<>();
                
        while (ioIsStarted && hasEventsMatchingTime) {
            if (OS.eventQueue.peek() != null && OS.eventQueue.peek().getClockTime() == clockTime && OS.eventQueue.peek().getEventType() == EventType.INTERRUPT) {
                finishedEvents.add(getEvent());
            } else {
                hasEventsMatchingTime = false;
            }
        }
        
        if (ioIsStarted && OS.eventQueue.isEmpty()) {
            ioIsStarted = false;
        }
        
        return finishedEvents;
    }
    
    /**
     * Add an event to the Event Queue.
     * @param process the process associated with the event
     * @param cycles the number of cycles the event will take
     * @param eventType the event type
     */
    public void addEvent(PCB process, int cycles, EventType eventType) {
        ECB event = new ECB(process, cycles, eventType);
        OS.eventQueue.enQueue(event);
    }
    
    /**
     * Removes the event at the top of the queue.
     * 
     * @return the event at the top of the queue
     */
    public ECB getEvent() {
        return OS.eventQueue.poll();
    }

    /**
     * Returns the started state of I/O.
     * 
     * @return true if IO has started
     */
    public boolean isIoIsStarted() {
        return ioIsStarted;
    }

    /**
     * Sets the started state of I/O.
     * 
     * @param ioIsStarted should be true or false
     */
    public void setIoIsStarted(boolean ioIsStarted) {
        this.ioIsStarted = ioIsStarted;
    }
}