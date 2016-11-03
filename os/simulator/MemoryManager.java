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
public class MemoryManager {
    
    private int memorySize;
    private Memory memory;
    
    public MemoryManager(int memorySize) {
        this.memorySize = memorySize;
        this.memory = new Memory(memorySize, 0);
    }
        
    public int getAvailableMemory() {
        return memory.getFree();
    }
    
    public int getAllocatedMemory() {
        return memory.getAllocated();
    }
    
    public int getMemorySize() {
        return memorySize;
    }
    
    public boolean allocateMemory(int size) {
        if (size > getAvailableMemory()) {
            return false;
        }
        
        memory.setAllocated(getAllocatedMemory() + size);
        memory.setFree(getAvailableMemory() - size);
        
        return true;
    }
    
    public boolean deallocateMemory(int size) {
        if (size > getAllocatedMemory()) {
            return false;
        }
        
        memory.setAllocated(getAllocatedMemory() - size);
        memory.setFree(getAvailableMemory() + size);
        
        return true;
    }
    
    public void addWaitingProcessesToMemory() {
        for (int i = 0; i < OS.scheduler.getWaitingQueue().size(); i++) {
            PCB process = OS.scheduler.getWaitingQueue().get(i);
            
            if (process.getMemoryAllocated() > 0) {
                continue;
            }
            
            if (allocateMemory(process.getMemoryRequired())) {
                OS.scheduler.getWaitingQueue().deQueue(process);
                process.setMemoryAllocated(process.getMemoryRequired());
                process.setState(ProcessState.READY);
                OS.scheduler.insertPCB(process, false);
            }
        }
    }
}