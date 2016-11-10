package os.simulator;

/**
 * This class handles the scheduling of I/O.
 * 
 * @author BTKS
 */
public class IOScheduler {
    
    /**
     * Schedule I/O and add as an event to the Interrupt Processor.
     * 
     * @param process the process that is waiting on I/O
     * @param clockTime the current clock time
     * @return the number of cycles that the I/O will take
     */
    public int scheduleIO(PCB process, int clockTime) {
        int cycles = IOBurst.generateIOBurst();
        
        OS.interruptProcessor.addEvent(process, clockTime + cycles, EventType.INTERRUPT);
        
        if (!OS.interruptProcessor.isIoIsStarted()) {
            startIO();
        }
        
        return cycles;
    }
    
    /**
     * Starts I/O by notifying the Interrupt Processor.
     */
    public void startIO() {
        OS.interruptProcessor.setIoIsStarted(true);
    }
}