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

    private static final int ROUND_ROBIN_TIME_QUANTUM = 10;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Desktop().setVisible(true);
            }
        });
        
        Scheduler scheduler = new Scheduler(0, ROUND_ROBIN_TIME_QUANTUM);
        Clock clock = new Clock();
        
        CPU cpu = new CPU(scheduler, clock);
    }
    
    public void simulate() {
        while (true) {
            
        }
    }
}