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
public class CPU {
    
    private Scheduler scheduler;
    private Clock clock;
    private PCB activeProcess;
    
    public CPU(Scheduler scheduler, Clock clock) {
        this.scheduler = scheduler;
        this.clock = clock;
        this.activeProcess = null;
    }
    
    public void run() {
        
    }
        
    public void advanceClock() {
        clock.execute();
    }
        
    public void detectInterrupt() {}
    
    public void detectPreemption() {}

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public Clock getClock() {
        return clock;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public PCB getActiveProcess() {
        return activeProcess;
    }

    public void setActiveProcess(PCB activeProcess) {
        this.activeProcess = activeProcess;
    }
}