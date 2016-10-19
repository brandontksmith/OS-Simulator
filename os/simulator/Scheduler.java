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
public class Scheduler {
    
    private int currentTime;
    private int rrTimeQuantum;
    private PCB activeProcess;
    private ExecutionQueue readyQueue;
    
    public Scheduler(int currentTime, int rrTimeQuantum) {
        this.currentTime = currentTime;
        this.rrTimeQuantum = rrTimeQuantum;
        this.activeProcess = null;
        this.readyQueue = new ExecutionQueue();
    }
    
    public void insertPCB(PCB process) {
        readyQueue.enQueue(process);
    }
    
    public void removePCB(PCB process) {
        readyQueue.deQueue(process);
    }
    
    public ProcessState getState(PCB process) {
        return process.getState();
    }
    
    public void setState(PCB process, ProcessState state) {
        process.setState(state);
    }
    
    public int getWait(PCB process) {
        return process.getWait();
    }
    
    public void setWait(PCB process, int wait) {
        process.setWait(wait);
    }
    
    public int getArrival(PCB process) {
        return process.getArrival();
    }
    public void setArrival(PCB process, int arrival) {
        process.setArrival(arrival);
    }

    public int getCPUTime(PCB process) {
        return process.getCPUTime();
    }
    public void setCPUTime(PCB process, int CPUTime) {
        process.setCPUTime(CPUTime);
    } 
}