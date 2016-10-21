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
    
    private int rrTimeQuantum;
    private ExecutionQueue readyQueue;
    private int timeRemaining;
    
    private PCB activeProcess;
    
    public Scheduler(int rrTimeQuantum) {
        this.rrTimeQuantum = rrTimeQuantum;
        this.readyQueue = new ExecutionQueue();
        this.timeRemaining = rrTimeQuantum;
        this.activeProcess = null;
    }
    
    public PCB getNextProcess(int currentTime) {
        if (timeRemaining == 0) {
            readyQueue.enQueue(activeProcess);
        }
        
        if (activeProcess == null || timeRemaining == 0 || activeProcess.isFinished()) {
            activeProcess = readyQueue.poll();
            timeRemaining = rrTimeQuantum;
        }
        
        timeRemaining--;
        
        return activeProcess;
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

    public int getRrTimeQuantum() {
        return rrTimeQuantum;
    }

    public void setRrTimeQuantum(int rrTimeQuantum) {
        this.rrTimeQuantum = rrTimeQuantum;
    }
    
    public ExecutionQueue getReadyQueue() {
        return readyQueue;
    }

    public void setReadyQueue(ExecutionQueue readyQueue) {
        this.readyQueue = readyQueue;
    }
}