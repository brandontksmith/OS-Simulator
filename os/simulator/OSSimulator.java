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
public class OSSimulator {
    
    public static final int ROUND_ROBIN_TIME_QUANTUM = 10;
    public static final int MEMORY_SIZE_IN_KB = 256;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CommandInterface commandInterface = new CommandInterface();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Desktop(commandInterface).setVisible(true);
            }
        });        
    }    
}