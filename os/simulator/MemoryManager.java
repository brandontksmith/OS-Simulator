package os.simulator;

/**
 * The MemoryManager manages the Memory of the OS.
 * 
 * @author BTKS
 */
public class MemoryManager {
    
    /**
     * The total size of the memory.
     */
    private int memorySize;
    
    /**
     * A reference to the memory object.
     */
    private Memory memory;
    
    /**
     * Construct a new MemoryManager.
     * 
     * @param memorySize the size of the memory
     */
    public MemoryManager(int memorySize) {
        this.memorySize = memorySize;
        this.memory = new Memory(memorySize, 0);
    }
    
    /**
     * Returns the amount of memory available.
     * 
     * @return the amount of available memory
     */
    public int getAvailableMemory() {
        return memory.getFree();
    }
    
    /**
     * Returns the amount of memory allocated.
     * 
     * @return the amount of allocated memory
     */
    public int getAllocatedMemory() {
        return memory.getAllocated();
    }
    
    /**
     * Returns the size of the memory.
     * 
     * @return the size of the memory.
     */
    public int getMemorySize() {
        return memorySize;
    }
    
    /**
     * Attempts to allocate the specified memory size.
     * 
     * @param size the size of the memory needed
     * @return true if the allocation is successful
     */
    public boolean allocateMemory(int size) {
        if (size > getAvailableMemory()) {
            return false;
        }
        
        memory.setAllocated(getAllocatedMemory() + size);
        memory.setFree(getAvailableMemory() - size);
        
        return true;
    }
    
    /**
     * Attempts to deallocate the specified memory size.
     * @param size the size of memory to release
     * @return true if the deallocation is successful
     */
    public boolean deallocateMemory(int size) {
        if (size > getAllocatedMemory()) {
            return false;
        }
        
        memory.setAllocated(getAllocatedMemory() - size);
        memory.setFree(getAvailableMemory() + size);
        
        return true;
    }
    
    /**
     * Adds waiting processes to memory if they will fit.
     */
    public void addWaitingProcessesToMemory() {
        for (int i = 0; i < OS.scheduler.getWaitingQueue().size(); i++) {
            PCB process = OS.scheduler.getWaitingQueue().get(i);
            
            if (process.getMemoryAllocated() > 0) {
                continue;
            }
            
            if (allocateMemory(process.getMemoryRequired())) {
                OS.scheduler.getWaitingQueue().deQueue(process);
                process.setMemoryAllocated(process.getMemoryRequired());
                process.setState(ProcessState.READY);
                OS.scheduler.insertPCB(process, false);
            }
        }
    }
}