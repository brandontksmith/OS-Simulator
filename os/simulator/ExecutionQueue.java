/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.simulator;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author BTKS
 */
public class ExecutionQueue {
    
    Queue<PCB> processes;
    
    public ExecutionQueue() {
        this.processes = new LinkedList<PCB>();
    }
    
    public void enQueue(PCB process) {
        this.processes.add(process);
    }
    
    public void deQueue(PCB process) {
        this.processes.remove(process);
    }
    
    public boolean isEmpty() {
        return processes.isEmpty();
    }
    
    public PCB poll() {
        return processes.poll();
    }
    
    public int size() {
        return processes.size();
    }
    
    public PCB get(int index) {
        if (index > size() - 1) {
            return null;
        }
        
        return (PCB) processes.toArray()[index];
    }
}