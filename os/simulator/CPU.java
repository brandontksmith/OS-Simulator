/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.simulator;

import java.util.LinkedList;

/**
 *
 * @author BTKS
 */
public class CPU {
    
    private Clock clock;
    
    private PCB activeProcess;
    
    public CPU() {
        this.clock = new Clock();
        this.activeProcess = null;
    }
    
    public void setActiveProcess(PCB process) {
        this.activeProcess = process;
        
        if (activeProcess == null) {
            return;
        }
        
        if (!activeProcess.isArrived()) {
            activeProcess.setArrived(true);
            activeProcess.setArrival(getClock().getClock());
        }
        
        if (!activeProcess.isStarted()) {
            activeProcess.setStarted(true);
        }
        
        activeProcess.setState(ProcessState.RUN);
    }
    
    public void advanceClock() {
        clock.execute();;
        
        detectInterrupt();
                
        if (activeProcess != null) {
            String instructionName = activeProcess.getCurrentInstruction();
            activeProcess.setBurst(activeProcess.getBurst() - 1);
            activeProcess.setCPUTime(activeProcess.getCPUTime() + 1);
            
            switch (instructionName) {
                case "CALCULATE":
                    break;
                
                case "I/O":
                    activeProcess.setState(ProcessState.WAIT);
                    activeProcess.setWaitingIO(true);
                    
                    OS.scheduler.getWaitingQueue().enQueue(activeProcess);
                    OS.ioScheduler.scheduleIO(activeProcess, clock.getClock());
                    
                    break;
                
                case "YIELD":
                    detectPreemption();
                    
                    break;
                
                case "OUT":
                    String processStr = activeProcess.toString();
                    String processStrForConsole;
                    
                    processStrForConsole = "Received by System\n\n" + processStr;
                    
                    System.out.println(processStr);
                    
                    OSSimulator.desktop.addTextToConsole(processStrForConsole);
                    
                    break;
            }
            
            activeProcess.setInstructionCycles(activeProcess.getInstructionCycles() - 1);
            
            if (activeProcess.getBurst() == 0) {
                activeProcess.setFinished(true);
                activeProcess.setState(ProcessState.EXIT);
                
                OS.memoryManager.deallocateMemory(activeProcess.getMemoryAllocated());
            }
        } else {
            System.out.println("No Process Running @ " + clock.getClock());
        }
    }
        
    public void detectInterrupt() {
        LinkedList<ECB> events = OS.interruptProcessor.signalInterrupt(clock.getClock());
        
        while (!events.isEmpty()) {
            ECB event = events.poll();
            PCB process = event.getProcess();
            
            if (process.isFinished()) {
                continue;
            }
            
            process.setWaitingIO(false);
            process.incrementIoComplete();
            process.setState(ProcessState.READY);
            
            OS.scheduler.removePCB(process, true);            
            OS.scheduler.insertPCB(process, false);
        }
    }
    
    public void detectPreemption() {
        OS.scheduler.resetTimeRemaining();
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