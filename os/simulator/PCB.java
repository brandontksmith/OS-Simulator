package os.simulator;

import java.util.ArrayList;

/**
 * This class is a model for the Process Control Block (PCB).
 * 
 * @author BTKS
 */
public class PCB {
    
    /**
     * The name of the program or process.
     */
    private String programName;
    
    /**
     * The Process ID.
     */
    private int processID;
    
    /**
     * The current state of the process.
     */
    private ProcessState state;
    
    /**
     * The current instruction index of the process.
     */
    private int instructionIndex;
    
    /**
     * The amount of memory allocated to the process.
     */
    private int memoryAllocated;
    
    /**
     * The amount of memory required by the process.
     */
    private int memoryRequired;
    
    /**
     * A list of operations in the process.
     */
    private ArrayList<String> operations;
    
    /**
     * A list of the amount of cycles required for each operation.
     */
    private ArrayList<Integer> cycles;
    
    /**
     * The number of instruction cycles.
     */
    private int instructionCycles;
    
    /**
     * The initial burst.
     */
    private int initialBurst;
    
    /**
     * The burst.
     */
    private int burst;
    
    /**
     * The arrival of the process.
     */
    private int arrival;
    
    /**
     * The wait of the process.
     */
    private int wait;
    
    /**
     * The CPU Time of the process.
     */
    private int CPUTime;
    
    /**
     * The amount of IO complete.
     */
    private int ioComplete;
    
    /**
     * True if the process has arrived.
     */
    private boolean arrived;
    
    /**
     * True if the process has started.
     */
    private boolean started;
    
    /**
     * True if the process has finished.
     */
    private boolean finished;
    
    /**
     * True if the process is waiting IO.
     */
    private boolean waitingIO;
    
    /**
     * Construct a new Process Control Block.
     * 
     * @param programName the name of the program
     * @param processID the ID of the process
     * @param state the initial state of the process
     * @param instructionIndex the starting instruction index
     * @param memoryRequired the amount of memory required
     * @param memoryAllocated the amount of memory allocated
     * @param operations operations in the process
     * @param cycles the amount of cycles for each corresponding operation
     * @param arrival the arrival time of the process
     * @param initialBurst the initial burst of the process
     */
    public PCB(String programName, int processID, ProcessState state, int instructionIndex, 
            int memoryRequired, int memoryAllocated, ArrayList<String> operations,
            ArrayList<Integer> cycles, int arrival, int initialBurst) {
        this.programName = programName;
        this.processID = processID;
        this.state = state;
        this.instructionIndex = instructionIndex;
        this.memoryRequired = memoryRequired;
        this.memoryAllocated = memoryAllocated;
        
        this.operations = operations;
        this.cycles = cycles;
        this.instructionCycles = cycles.get(instructionIndex);
        
        this.initialBurst = initialBurst;
        this.burst = initialBurst;
        this.arrival = arrival;
        this.wait = 0;
        this.CPUTime = 0;        
        this.arrived = false;
        this.started = false;
        this.finished = false;
    }
    
    /**
     * Returns the state of the process.
     */
    public ProcessState getState() {
        return state;
    }

    /**
     * Sets the state of the process.
     */
    public void setState(ProcessState state) {
        this.state = state;
    }
    
    /**
     * Returns the memory allocated by the process.
     */
    public int getMemoryAllocated() {
        return memoryAllocated;
    }

    /**
     * Sets the memory allocated by the process.
     */
    public void setMemoryAllocated(int memoryAllocated) {
        this.memoryAllocated = memoryAllocated;
    }

    /**
     * Returns the arrival time of the process.
     */
    public int getArrival() {
        return arrival;
    }

    /**
     * Sets the arrival time of the process.
     */
    public void setArrival(int arrival) {
        this.arrival = arrival;
    }

    /**
     * Returns the amount of time spent waiting by the process.
     */
    public int getWait() {
        return wait;
    }

    /**
     * Sets the amount of time spent waiting by the process.
     */
    public void setWait(int wait) {
        this.wait = wait;
    }

    /**
     * Returns the amount of time used by the process.
     */
    public int getCPUTime() {
        return CPUTime;
    }

    /**
     * Sets the amount of time used by the process.
     */
    public void setCPUTime(int CPUTime) {
        this.CPUTime = CPUTime;
    }

    /**
     * Returns the process ID.
     */
    public int getProcessID() {
        return processID;
    }

    /**
     * Sets the process ID.
     */
    public void setProcessID(int processID) {
        this.processID = processID;
    }

    /**
     * Returns the operations in the process.
     */
    public ArrayList<String> getOperations() {
        return operations;
    }

    /**
     * Sets the operations in the process.
     */
    public void setOperations(ArrayList<String> operations) {
        this.operations = operations;
    }

    /**
     * Returns the cycles for the corresponding operations.
     */
    public ArrayList<Integer> getCycles() {
        return cycles;
    }

    /**
     * Sets the cycles for the corresponding operations.
     */
    public void setCycles(ArrayList<Integer> cycles) {
        this.cycles = cycles;
    }
    
    /**
     * Returns the burst of the process.
     */
    public int getBurst() {
        return burst;
    }

    /**
     * Sets the burst of the process.
     */
    public void setBurst(int burst) {
        this.burst = burst;
    }
    
    /**
     * Returns true if the process is finished.
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Sets whether or not the process is finished.
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * Returns true if the process arrived.
     */
    public boolean isArrived() {
        return arrived;
    }

    /**
     * Sets whether or not the process arrived.
     */
    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    /**
     * Returns true if the process is started.
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Sets whether or not the process is started.
     */
    public void setStarted(boolean started) {
        this.started = started;
    }

    /**
     * Returns the initial burst of the process.
     */
    public int getInitialBurst() {
        return initialBurst;
    }

    /**
     * Sets the initial burst of the process.
     */
    public void setInitialBurst(int initialBurst) {
        this.initialBurst = initialBurst;
    }

    /**
     * Returns the instruction cycle.
     */
    public int getInstructionCycles() {
        return instructionCycles;
    }

    /**
     * Sets the instruction cycles.
     */
    public void setInstructionCycles(int instructionCycles) {
        this.instructionCycles = instructionCycles;
    }
    
    /**
     * Formats the PCB object to a String.
     * 
     * @return string of the process control block
     */
    @Override
    public String toString() {
        String str = "Process ID: " + processID + "\n";
        str += "Process State: " + state + "\n";
        str += "CPU Time Remaining: " + burst + "\n";
        str += "CPU Time Used: " + CPUTime + "\n";
        str += "I/O Requests: " + ioComplete + "\n";
        str += "Memory Allocated: " + memoryAllocated + "\n";
        
        return str;
    }
    
    /**
     * Formats the PCB for a Table.
     * 
     * @return an Object array of PCB data
     */
    public Object[] formatForTable() {
        Object[] arr = {
            processID,
            state,
            memoryAllocated,
            initialBurst - burst,
            burst,
            ioComplete
        };
        
        return arr;
    }

    /**
     * Returns true if IO is complete.
     */
    public int getIoComplete() {
        return ioComplete;
    }

    /**
     * Sets the IO complete value.
     */
    public void setIoComplete(int ioComplete) {
        this.ioComplete = ioComplete;
    }

    /**
     * Returns true if the process is waiting IO.
     */
    public boolean isWaitingIO() {
        return waitingIO;
    }

    /**
     * Sets whether or not the process is waiting IO.
     */
    public void setWaitingIO(boolean waitingIO) {
        this.waitingIO = waitingIO;
    }
    
    /**
     * Increments the number of IO Complete.
     */
    public void incrementIoComplete() {
        ioComplete++;
    }

    /**
     * Returns the program name.
     */
    public String getProgramName() {
        return programName;
    }

    /**
     * Sets the name of the program.
     */
    public void setProgramName(String programName) {
        this.programName = programName;
    }
    
    /**
     * Returns the current instruction being excuted.
     * 
     * @return operation being executed
     */
    public String getCurrentInstruction() {
        if (instructionCycles == 0) {
            instructionIndex++;
            instructionCycles = cycles.get(instructionIndex);
        }
        
        return operations.get(instructionIndex);
    }

    /**
     * Returns the instruction index.
     */
    public int getInstructionIndex() {
        return instructionIndex;
    }

    /**
     * Sets the instruction index.
     */
    public void setInstructionIndex(int instructionIndex) {
        this.instructionIndex = instructionIndex;
    }
    
    /**
     * Returns the memory required by the process.
     */
    public int getMemoryRequired() {
        return memoryRequired;
    }

    /**
     * Sets the memory required by the process.
     */
    public void setMemoryRequired(int memoryRequired) {
        this.memoryRequired = memoryRequired;
    }
}