package os.simulator;

public class Clock {
    
    /**
     * The number of cycles the CPU has executed.
     */
    private int cycles = 0;
    
    /**
     * Advances the clock by one cycle.
     */
    public void execute() {
        cycles++;
    }
    
    /**
     * Returns the current CPU Time (number of cycles).
     */
    public int getClock() {
        return cycles;
    }   
}