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
public interface QueueInterface {
    
    public void enQueue(PCB process);
    public void deQueue(PCB process);
    
}
