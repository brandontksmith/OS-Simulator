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
    
    public static volatile CommandInterface commandInterface;
    
    public static Desktop desktop;
    
    public static Thread commandInterfaceThread;
        
    public static void main(String[] args) {        
        commandInterface = new CommandInterface();
        
        OS.initializeOS();
        
        new Thread(commandInterface).start();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                OSSimulator.desktop = new Desktop();
                OSSimulator.desktop.setVisible(true);
            }
        });
    }
}