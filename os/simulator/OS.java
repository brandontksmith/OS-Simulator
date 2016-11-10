package os.simulator;

/**
 * The Operating System.
 * 
 * @author BTKS
 */
public class OS {
    
    /**
     * Constant for the Round Robin Time Quantum (q).
     */
    public static final int ROUND_ROBIN_TIME_QUANTUM = 10;
    
    /**
     * Constant for the Memory Size (in kb).
     */
    public static final int MEMORY_SIZE_IN_KB = 256;
    
    /**
     * Constant for the Starting Process ID.
     */
    public static final int STARTING_PROCESS_ID = 100;
    
    /**
     * Queue of processes in the OS.
     */
    public static volatile ExecutionQueue processes;
    
    /**
     * Queue of events in the OS.
     */
    public static volatile EventQueue eventQueue;
    
    /**
     * The OS Scheduler.
     */
    public static volatile Scheduler scheduler;
    
    /**
     * The OS I/O Scheduler.
     */
    public static volatile IOScheduler ioScheduler;
    
    /**
     * The OS Memory Manager.
     */
    public static volatile MemoryManager memoryManager;
    
    /**
     * The OS Interrupt Processor.
     */
    public static volatile InterruptProcessor interruptProcessor;
    
    /**
     * The OS CPU.
     */
    public static volatile CPU cpu;
    
    /**
     * The OS Dispatcher.
     */
    public static volatile Dispatcher dispatcher;
    
    /**
     * The OS Process Manager.
     */
    public static volatile ProcessManager processManager;
    
    /**
     * Initializes the OS and all required components.
     */
    public static void initializeOS() {
        OS.processes = new ExecutionQueue();
        OS.eventQueue = new EventQueue();
        OS.scheduler = new Scheduler(OS.ROUND_ROBIN_TIME_QUANTUM);
        OS.ioScheduler = new IOScheduler();
        OS.memoryManager = new MemoryManager(OS.MEMORY_SIZE_IN_KB);
        OS.interruptProcessor = new InterruptProcessor();
        OS.cpu = new CPU();
        OS.dispatcher = new Dispatcher();
        OS.processManager = new ProcessManager(OS.STARTING_PROCESS_ID);
    }
}