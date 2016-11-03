package os.simulator;

public class Dispatcher {
    
    private PCB getProcessFromScheduler() {
        return OS.scheduler.getNextProcess(0);
    }
    
    public void dispatch() {
        PCB process = getProcessFromScheduler();
        OS.cpu.setActiveProcess(process);
    }
}