package os.simulator;

/**
 * This class represents a model for Memory.
 * 
 * @author BTKS
 */
public class Memory {
    
    /**
     * Amount of memory allocated.
     */
    private int allocated;
    
    /**
     * Amount of memory that is free/available.
     */
    private int free;
    
    /**
     * Construct a new Memory.
     * @param free amount of free memory
     * @param allocated amount of allocated memory
     */
    public Memory(int free, int allocated) {
        this.free = free;
        this.allocated = allocated;
    }
    
    /**
     * Returns the amount of memory allocated.
     * 
     * @return the amount of memory allocated
     */
    public int getAllocated() {
        return allocated;
    }

    /**
     * Sets the amount of memory allocated.
     * 
     * @param allocated the amount of memory allocated
     */
    public void setAllocated(int allocated) {
        this.allocated = allocated;
    }
    
    /**
     * Returns the amount of memory that is free.
     * 
     * @return the amount of memory available
     */
    public int getFree() {
        return free;
    }
    
    /**
     * Sets the amount of memory that is free.
     * 
     * @param free the amount of memory available
     */
    public void setFree(int free) {
        this.free = free;
    }
}