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
public class OS {
    
    public static final int ROUND_ROBIN_TIME_QUANTUM = 10;
    public static final int MEMORY_SIZE_IN_KB = 256;
    public static final int STARTING_PROCESS_ID = 100;
    
    public static volatile ExecutionQueue processes;
    public static volatile EventQueue eventQueue;
    public static volatile Scheduler scheduler;
    public static volatile IOScheduler ioScheduler;
    public static volatile MemoryManager memoryManager;
    public static volatile InterruptProcessor interruptProcessor;
    public static volatile CPU cpu;
    public static volatile Dispatcher dispatcher;
    public static volatile ProcessManager processManager;
    
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