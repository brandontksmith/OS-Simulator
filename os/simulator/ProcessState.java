package os.simulator;

/**
 * Process State.
 * 
 * @author BTKS
 */
public enum ProcessState {
    NEW,                        // Process is New
    READY,                      // Process is Ready
    RUN,                        // Process is Running
    WAIT,                       // Process is Waiting
    EXIT                        // Process is Exiting
}