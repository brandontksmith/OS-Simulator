/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.simulator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BTKS
 */
public class ExecutionQueue implements QueueInterface {
    
    List<PCB> processes;
    
    public ExecutionQueue() {
        this.processes = new ArrayList<PCB>();
    }
    
    @Override
    public void enQueue(PCB process) {
        this.processes.add(process);
    }
    
    @Override
    public void deQueue(PCB process) {
        this.processes.remove(process);
    }
}