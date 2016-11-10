package os.simulator;

/**
 * OS Dispatcher than hands off a new process to the CPU.
 * 
 * @author BTKS
 */
public class Dispatcher {
    
    /**
     * Fetches a process from the scheduler.
     * 
     * @return process control block for the process
     */
    private PCB getProcessFromScheduler() {
        return OS.scheduler.getNextProcess(0);
    }
    
    /**
     * Hands off the process to the CPU.
     */
    public void dispatch() {
        PCB process = getProcessFromScheduler();
        OS.cpu.setActiveProcess(process);
    }
}