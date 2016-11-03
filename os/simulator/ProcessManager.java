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
public class ProcessManager {
    
    private int processId;
    
    public ProcessManager(int startingProcessId) {
        this.processId = startingProcessId;
    }
    
    public void incrementWaitOnReadyProcesses() {
        for (int i = 0; i < OS.scheduler.getReadyQueue().size(); i++) {
            PCB p = OS.scheduler.getReadyQueue().get(i);

            if (OS.cpu.getActiveProcess() == null || p.getProcessID() != OS.cpu.getActiveProcess().getProcessID()) {
                p.setWait(p.getWait() + 1);
            }
        }
    }
}