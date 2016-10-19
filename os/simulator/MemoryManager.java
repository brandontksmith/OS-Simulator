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
}