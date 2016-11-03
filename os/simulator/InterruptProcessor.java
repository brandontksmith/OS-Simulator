/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.simulator;

import java.util.LinkedList;

/**
 *
 * @author BTKS
 */
public class InterruptProcessor {
    
    private boolean ioIsStarted;
    
    public InterruptProcessor() {
        this.ioIsStarted = false;
    }
        
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
    
    public void addEvent(PCB process, int cycles, EventType eventType) {
        ECB event = new ECB(process, cycles, eventType);
        OS.eventQueue.enQueue(event);
    }
    
    public ECB getEvent() {
        return OS.eventQueue.poll();
    }

    public boolean isIoIsStarted() {
        return ioIsStarted;
    }

    public void setIoIsStarted(boolean ioIsStarted) {
        this.ioIsStarted = ioIsStarted;
    }
}