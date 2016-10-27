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
        
    public InterruptProcessor() {
    }
        
    public LinkedList<ECB> signalInterrupt(int clockTime) {
        boolean hasEventsMatchingTime = true;
        LinkedList<ECB> finishedEvents = new LinkedList<>();
                
        while (hasEventsMatchingTime) {
            if (OS.eventQueue.peek() != null && OS.eventQueue.peek().getClockTime() == clockTime && OS.eventQueue.peek().getEventType() == EventType.INTERRUPT) {
                finishedEvents.add(OS.eventQueue.poll());
            } else {
                hasEventsMatchingTime = false;
            }
        }
        
        return finishedEvents;
    }
    
    public void addEvent(PCB process, int cycles, EventType eventType) {
        ECB event = new ECB(process, cycles, eventType);
        OS.eventQueue.enQueue(event);
    }
    
    public ECB getEvent() {
        return null;
    }
}