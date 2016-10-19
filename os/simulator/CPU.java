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
    
    public CPU(Scheduler scheduler, Clock clock) {
        this.scheduler = scheduler;
        this.clock = clock;
    }
        
    public void advanceClock() {
        clock.execute();
    }
    
    public void detectInterrupt() {}
    public void detectPreemption() {}
    
}