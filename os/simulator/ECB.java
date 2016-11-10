package os.simulator;

/**
 * ECB is a model for holding event-specific information.
 * 
 * @author BTKS
 */
public class ECB {
    
    /**
     * The process associated with the event.
     */
    private PCB process;
    
    /**
     * The clock time of the event.
     */
    private int clockTime;
    
    /**
     * The type of event.
     */
    private EventType eventType;
    
    /**
     * Construct a new Event Control Block.
     * 
     * @param process process associated with event
     * @param clockTime the clock time of the event
     * @param eventType the type of event
     */
    public ECB(PCB process, int clockTime, EventType eventType) {
        this.process = process;
        this.clockTime = clockTime;
        this.eventType = eventType;
    }

    /**
     * Returns the process associated with the event.
     */
    public PCB getProcess() {
        return process;
    }

    /**
     * Sets the process associated with the event.
     * 
     * @param process the process
     */
    public void setProcess(PCB process) {
        this.process = process;
    }

    /**
     * Returns the clock time of the event.
     * 
     * @return clock time
     */
    public int getClockTime() {
        return clockTime;
    }

    /**
     * Sets the clock time of the event.
     * 
     * @param clockTime clock time
     */
    public void setClockTime(int clockTime) {
        this.clockTime = clockTime;
    }

    /**
     * Returns the type of event.
     * 
     * @return event type
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Sets the type of event.
     * 
     * @param eventType event type
     */
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}