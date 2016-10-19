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
public class PCB {
    
    private ProcessState state;
    private int nextInstructionIndex;
    private int memoryAllocated;
    
    private int arrival;
    private int wait;
    private int CPUTime;
    
    public PCB(ProcessState state, int nextInstructionIndex, int memoryAllocated) {
        this.state = state;
        this.nextInstructionIndex = nextInstructionIndex;
        this.memoryAllocated = memoryAllocated;
        
        this.arrival = 0;
        this.wait = 0;
        this.CPUTime = 0;
    }

    public ProcessState getState() {
        return state;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public int getNextInstructionIndex() {
        return nextInstructionIndex;
    }

    public void setNextInstructionIndex(int nextInstructionIndex) {
        this.nextInstructionIndex = nextInstructionIndex;
    }

    public int getMemoryAllocated() {
        return memoryAllocated;
    }

    public void setMemoryAllocated(int memoryAllocated) {
        this.memoryAllocated = memoryAllocated;
    }

    public int getArrival() {
        return arrival;
    }

    public void setArrival(int arrival) {
        this.arrival = arrival;
    }

    public int getWait() {
        return wait;
    }

    public void setWait(int wait) {
        this.wait = wait;
    }

    public int getCPUTime() {
        return CPUTime;
    }

    public void setCPUTime(int CPUTime) {
        this.CPUTime = CPUTime;
    }
}