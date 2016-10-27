/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.simulator;

import java.util.ArrayList;

/**
 *
 * @author BTKS
 */
public class PCB {
    
    private int processID;
    
    private ProcessState state;
    private int nextInstructionIndex;
    private int memoryAllocated;
    
    private ArrayList<String> operations;
    private ArrayList<Integer> cycles;
    
    private int instructionCycles;
    
    private int initialBurst;
    private int burst;
    private int arrival;
    private int wait;
    private int CPUTime;
    
    private boolean arrived;
    private boolean started;
    private boolean active;
    private boolean finished;
    
    public PCB(int processID, ProcessState state, int nextInstructionIndex,
            int memoryAllocated, ArrayList<String> operations, ArrayList<Integer> cycles, 
            int arrival, int initialBurst) {
        this.processID = processID;
        this.state = state;
        this.nextInstructionIndex = nextInstructionIndex;
        this.memoryAllocated = memoryAllocated;
        
        this.operations = operations;
        this.cycles = cycles;
        this.instructionCycles = cycles.get(nextInstructionIndex);
        
        this.initialBurst = initialBurst;
        this.burst = initialBurst;
        this.arrival = arrival;
        this.wait = 0;
        this.CPUTime = 0;        
        this.arrived = false;
        this.started = false;
        this.active = false;
        this.finished = false;
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

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public ArrayList<String> getOperations() {
        return operations;
    }

    public void setOperations(ArrayList<String> operations) {
        this.operations = operations;
    }

    public ArrayList<Integer> getCycles() {
        return cycles;
    }

    public void setCycles(ArrayList<Integer> cycles) {
        this.cycles = cycles;
    }
    
    public int getBurst() {
        return burst;
    }

    public void setBurst(int burst) {
        this.burst = burst;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isArrived() {
        return arrived;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public int getInitialBurst() {
        return initialBurst;
    }

    public void setInitialBurst(int initialBurst) {
        this.initialBurst = initialBurst;
    }

    public int getInstructionCycles() {
        return instructionCycles;
    }

    public void setInstructionCycles(int instructionCycles) {
        this.instructionCycles = instructionCycles;
    }
}