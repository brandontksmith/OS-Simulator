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
public class CPU {
    
    private Scheduler scheduler;
    private MemoryManager memoryManager;
    private InterruptProcessor interruptProcessor;
    private Clock clock;
    
    private PCB activeProcess;
    
    public CPU(Scheduler scheduler, MemoryManager memoryManager, InterruptProcessor interruptProcessor) {
        this.scheduler = scheduler;
        this.memoryManager = memoryManager;
        this.interruptProcessor = interruptProcessor;
        this.clock = new Clock();
        this.activeProcess = null;
    }
    
    public void setActiveProcess(PCB process) {
        this.activeProcess = process;
    }
    
    public void advanceClock() {
        if (activeProcess != null) {
            activeProcess.setBurst(activeProcess.getBurst() - 1);

            if (activeProcess.getInstructionCycles() <= 0) {
                activeProcess.setNextInstructionIndex(activeProcess.getNextInstructionIndex() + 1);
                activeProcess.setInstructionCycles(activeProcess.getCycles().get(activeProcess.getNextInstructionIndex()));

                String instructionName = activeProcess.getOperations().get(activeProcess.getNextInstructionIndex());

                if (instructionName.equals("CALCULATE")) {
                    // calculation
                } else if (instructionName.equals("I/O")) {
                    activeProcess.setState(ProcessState.WAIT);
                    scheduler.getWaitingQueue().enQueue(activeProcess);
                } else if (instructionName.equals("YIELD")) {
                } else if (instructionName.equals("OUT")) {
                }
            }

            activeProcess.setInstructionCycles(activeProcess.getInstructionCycles() - 1);

            if (!activeProcess.isArrived()) {
                activeProcess.setArrived(true);
                activeProcess.setArrival(getClock().getClock());
            }

            if (!activeProcess.isStarted()) {
                activeProcess.setStarted(true);
            }

            if (!activeProcess.isActive()) {
                activeProcess.setActive(true);
                activeProcess.setState(ProcessState.RUN);
            }

            if (activeProcess.getBurst() == 0) {
                activeProcess.setFinished(true);
                activeProcess.setState(ProcessState.EXIT);
                memoryManager.deallocateMemory(activeProcess.getMemoryAllocated());
            }
        }
        
        clock.execute();
    }
        
    public void detectInterrupt() {
        
    }
    
    public void detectPreemption() {
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public Clock getClock() {
        return clock;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }
    
    public PCB getActiveProcess() {
        return activeProcess;
    }
}