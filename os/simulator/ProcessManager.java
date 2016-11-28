package os.simulator;

/**
 * Class for managing processes in the OS.
 * 
 * @author BTKS
 */
public class ProcessManager {
    
    /**
     * Last Process ID.
     */
    private int processId;
    
    /**
     * Construct a new Process Manager.
     * 
     * @param startingProcessId the starting Process ID
     */
    public ProcessManager(int startingProcessId) {
        this.processId = startingProcessId;
    }
    
    /**
     * Increment wait on ready processes that are waiting.
     */
    public void incrementWaitOnReadyProcesses() {
        for (int i = 0; i < OS.scheduler.getReadyQueue().size(); i++) {
            PCB p = OS.scheduler.getReadyQueue().get(i);

            if (OS.cpu.getActiveProcess() == null || p.getProcessID() != OS.cpu.getActiveProcess().getProcessID()) {
                p.setWait(p.getWait() + 1);
            }
        }
    }
}