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
public class ECB {
    
    private PCB process;
    private int clockTime;
    private EventType eventType;
    
    public ECB(PCB process, int clockTime, EventType eventType) {
        this.process = process;
        this.clockTime = clockTime;
        this.eventType = eventType;
    }

    public PCB getProcess() {
        return process;
    }

    public void setProcess(PCB process) {
        this.process = process;
    }

    public int getClockTime() {
        return clockTime;
    }

    public void setClockTime(int clockTime) {
        this.clockTime = clockTime;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}