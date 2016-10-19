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
public class Memory {
    
    private int allocated;
    private int free;
    
    public Memory(int free, int allocated) {
        this.free = free;
        this.allocated = allocated;
    }
    
    public int getAllocated() {
        return allocated;
    }

    public void setAllocated(int allocated) {
        this.allocated = allocated;
    }
    public int getFree() {
        return free;
    }
    public void setFree(int free) {
        this.free = free;
    }
}