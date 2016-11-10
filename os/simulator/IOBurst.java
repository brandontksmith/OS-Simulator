package os.simulator;

import java.util.Random;

/**
 * Helper class for the IO Scheduler and generating random IO Bursts.
 * 
 * @author BTKS
 */
public class IOBurst {
    
    /**
     * The minimum number of cycles for I/O.
     */
    public static final int MINIMUM_IO_CYCLES = 25;
    
    /**
     * The maximum number of cycles for I/O.
     */
    public static final int MAXIMUM_IO_CYCLES = 50;
    
    /**
     * Returns a randomly generated number of cycles for simulating I/O.
     * 
     * @return randomly generated number of cycles
     */
    public static int generateIOBurst() {
        Random r = new Random();
        
        return r.nextInt(MINIMUM_IO_CYCLES) + (MAXIMUM_IO_CYCLES - MINIMUM_IO_CYCLES + 1);
    }   
}