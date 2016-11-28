package os.simulator;

/**
 * This represents the scheduler in the OS.
 * 
 * @author BTKS
 */
public class Scheduler {
    
    /**
     * Round robin time quantum value.
     */
    private int rrTimeQuantum;
    
    /**
     * Queue containing waiting processes.
     */
    private ExecutionQueue waitingQueue;
    
    /**
     * Queue containing ready processes.
     */
    private ExecutionQueue readyQueue;
    
    /**
     * The amount of time remaining for the current process.
     */
    private int timeRemaining;
    
    /**
     * The process that is currently active.
     */
    private PCB activeProcess;
    
    /**
     * Construct a new Scheduler.
     * 
     * @param rrTimeQuantum round robin time quantum value
     */
    public Scheduler(int rrTimeQuantum) {
        this.rrTimeQuantum = rrTimeQuantum;
        this.waitingQueue = new ExecutionQueue();
        this.readyQueue = new ExecutionQueue();
        this.timeRemaining = rrTimeQuantum;
        this.activeProcess = null;
    }
    
    /**
     * Returns the next process to send to the CPU.
     * 
     * @param currentTime the current clock time
     * @return process to run on the CPU
     */
    public PCB getNextProcess(int currentTime) {
        if (timeRemaining == 0 && activeProcess.getState() == ProcessState.RUN) {
            activeProcess.setState(ProcessState.READY);
            readyQueue.enQueue(activeProcess);
        }
        
        if (activeProcess == null || timeRemaining == 0 || activeProcess.isFinished() || activeProcess.getState() == ProcessState.WAIT) {
            activeProcess = readyQueue.poll();
            timeRemaining = rrTimeQuantum;
        }
        
        timeRemaining--;
        
        return activeProcess;
    }
    
    /**
     * Sets the time remaining to 0.
     */
    public void resetTimeRemaining() {
        this.timeRemaining = 0;
    }
    
    /**
     * Adds a process to either the ready or waiting queue.
     * 
     * @param process the process to add
     * @param waiting if true, the process is added to waiting queue
     */
    public void insertPCB(PCB process, boolean waiting) {
        if (waiting) {
            waitingQueue.enQueue(process);
        } else {
           readyQueue.enQueue(process);
        }
    }
    
    /**
     * Remove process from either the ready or waiting queue.
     * 
     * @param process the process to remove
     * @param waiting if true, the process is removed from the waiting queue
     */
    public void removePCB(PCB process, boolean waiting) {
        if (waiting) {
            waitingQueue.deQueue(process);
        } else {
            readyQueue.deQueue(process);
        }
    }
    
    /**
     * Returns the state of the specified process.
     * 
     * @param process the process to get the state of
     * @return the process state
     */
    public ProcessState getState(PCB process) {
        return process.getState();
    }
    
    /**
     * Sets the state of the specified process.
     * 
     * @param process the process to set the state of
     * @param state the process state
     */
    public void setState(PCB process, ProcessState state) {
        process.setState(state);
    }
    
    /**
     * Gets the wait of the specified process.
     * 
     * @param process the process to get the wait for
     * @return the wait of the specified process
     */
    public int getWait(PCB process) {
        return process.getWait();
    }
    
    /**
     * Sets the wait of the specified process.
     * 
     * @param process the process to set the wait for
     * @param wait the wait of the process
     */
    public void setWait(PCB process, int wait) {
        process.setWait(wait);
    }
    
    /**
     * Gets the arrival of the specified process.
     * 
     * @param process the process to get the arrival of
     * @return the arrival of the specified process
     */
    public int getArrival(PCB process) {
        return process.getArrival();
    }
    
    /**
     * Sets the arrival of the specified process.
     * 
     * @param process the process to set the arrival for
     * @param arrival the arrival time for the process
     */
    public void setArrival(PCB process, int arrival) {
        process.setArrival(arrival);
    }

    /**
     * Gets the CPU Time of the specified process.
     * 
     * @param process the process to get the CPU Time of
     * @return the CPU Time of the specified process
     */
    public int getCPUTime(PCB process) {
        return process.getCPUTime();
    }
    
    /**
     * Sets the CPU Time of the specified process.
     * 
     * @param process the process to set the CPU Time for
     * @param CPUTime the CPU Time of the process
     */
    public void setCPUTime(PCB process, int CPUTime) {
        process.setCPUTime(CPUTime);
    }

    /**
     * Returns the round robin time quantum.
     */
    public int getRrTimeQuantum() {
        return rrTimeQuantum;
    }

    /**
     * Sets the round robin time quantum.
     */
    public void setRrTimeQuantum(int rrTimeQuantum) {
        this.rrTimeQuantum = rrTimeQuantum;
    }
    
    /**
     * Returns the ready queue.
     */
    public ExecutionQueue getReadyQueue() {
        return readyQueue;
    }

    /**
     * Sets the ready queue.
     */
    public void setReadyQueue(ExecutionQueue readyQueue) {
        this.readyQueue = readyQueue;
    }
    
    /**
     * Returns the waiting queue.
     */
    public ExecutionQueue getWaitingQueue() {
        return waitingQueue;
    }

    /**
     * Sets the waiting queue.
     */
    public void setWaitingQueue(ExecutionQueue waitingQueue) {
        this.waitingQueue = waitingQueue;
    }
}