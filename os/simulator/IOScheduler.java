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
public class IOScheduler {
    
    public InterruptProcessor interruptProcessor;
    
    public IOScheduler(InterruptProcessor interruptProcessor) {
        this.interruptProcessor = interruptProcessor;
    }
    
    public void scheduleIO(PCB process, int clockTime) {
        int cycles = IOBurst.generateIOBurst();
        interruptProcessor.addEvent(process, clockTime + cycles, EventType.INTERRUPT);
    }
    
    public void startIO() {
        // pretend to do IO?
    }
}