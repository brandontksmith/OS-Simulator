package os.simulator;

import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public static void sleepForFiftyMs() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(CommandInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}