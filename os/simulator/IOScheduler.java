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
        
    public int scheduleIO(PCB process, int clockTime) {
        int cycles = IOBurst.generateIOBurst();
        
        OS.interruptProcessor.addEvent(process, clockTime + cycles, EventType.INTERRUPT);
        
        if (!OS.interruptProcessor.isIoIsStarted()) {
            startIO();
        }
        
        return cycles;
    }
    
    public void startIO() {
        OS.interruptProcessor.setIoIsStarted(true);
    }
}