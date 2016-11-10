package os.simulator;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A queue for holding processes used by the Scheduler.
 * 
 * @author BTKS
 */
public class ExecutionQueue {
    
    /**
     * A queue of the processes.
     */
    Queue<PCB> processes;
    
    /**
     * Construct a new Execution Queue.
     */
    public ExecutionQueue() {
        this.processes = new LinkedList<PCB>();
    }
    
    /**
     * Add a process to the queue.
     * 
     * @param process the process
     */
    public void enQueue(PCB process) {
        this.processes.add(process);
    }
    
    /**
     * Remove a process from the queue.
     * 
     * @param process the process
     */
    public void deQueue(PCB process) {
        this.processes.remove(process);
    }
    
    /**
     * Check if there are any processes in the queue.
     * 
     * @return true if the queue is empty
     */
    public boolean isEmpty() {
        return processes.isEmpty();
    }
    
    /**
     * Removes a process from the queue.
     * 
     * @return the process
     */
    public PCB poll() {
        return processes.poll();
    }
    
    /**
     * Returns the size of the queue.
     * 
     * @return size of the queue
     */
    public int size() {
        return processes.size();
    }
    
    /**
     * Returns the process at a specified index.
     * 
     * @param index the index of the process in the queue
     * @return the process at the specified index if one exists
     */
    public PCB get(int index) {
        if (index > size() - 1) {
            return null;
        }
        
        return (PCB) processes.toArray()[index];
    }
}