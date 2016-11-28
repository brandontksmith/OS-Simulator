package os.simulator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * OS Simulator Main Class
 * @author BTKS
 */
public class OSSimulator {
    
    /**
     * Static reference to the Command Interface.
     */
    public static volatile CommandInterface commandInterface;
    
    /**
     * Static reference to the OS Desktop.
     */
    public static Desktop desktop;
    
    /**
     * Static reference to the Command Interface Thread.
     */
    public static Thread commandInterfaceThread;
    
    /**
     * Starts and initializes the OS Simulator.
     * 
     * @param args command line arguments
     */
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
    
    /**
     * Timer for slowing down the OS Simulator.
     */
    public static void sleepForFiftyMs() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(CommandInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}