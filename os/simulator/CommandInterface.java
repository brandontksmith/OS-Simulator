package os.simulator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 *
 * @author BTKS
 */
public class CommandInterface implements Runnable {
    
    public static volatile Thread executionThread;
    public static volatile boolean canInvokeExecute = true;
    public static volatile LinkedList<String> commands = new LinkedList<>();
    
    private int processID;
    private ArrayList<LoadableData> loadables;
            
    public CommandInterface() {
        this.processID = 100;
        this.loadables = new ArrayList<>();        

    }
    
    public static synchronized void addCommand(String cmd) {
        CommandInterface.commands.add(cmd);
    }
    
    @Override
    public void run() {
        while (true) {
            if (!commands.isEmpty()) {
                OSSimulator.commandInterface.handleCommand(commands.poll());
            }
        }
    }
    
    public void handleCommand(String cmd) {
        if (cmd.startsWith("<")) {
            int indexOfClosingAngleBracket = cmd.indexOf(">");
            int indexOfCharacterAfterClosingAngleBracket = indexOfClosingAngleBracket + 1;
            int indexOfFirstSpace = cmd.indexOf(" ");
                        
            int cycle = Integer.parseInt(cmd.substring(1, indexOfClosingAngleBracket));
            
            switch (cmd.charAt(indexOfCharacterAfterClosingAngleBracket)) {
                case 'L':
                    String jobProgramName = cmd.substring(indexOfFirstSpace + 1, cmd.length());
                    load(cycle, jobProgramName);
                    
                    break;
                    
                case 'E':
                    CommandInterface.canInvokeExecute = false;

                    executionThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            exe(cycle);
                        }
                    });
                    
                    executionThread.start();
                    
                    break;
            }
        } else {
            if (cmd.contains("PROC")) {
                proc();
            } else if (cmd.contains("MEM")) {
                mem();
            } else if (cmd.contains("LOAD")) {
                int indexOfSpace = cmd.indexOf(" ");
                
                String jobProgramName = cmd.substring(indexOfSpace + 1, cmd.length());
                load(OS.cpu.getClock().getClock(), jobProgramName);
            } else if (cmd.contains("EXE")) {
                CommandInterface.canInvokeExecute = false;
                    
                executionThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        exe(0);
                    }
                });

                executionThread.start();
            } else if (cmd.contains("RESET")) {
                reset();
            } else if (cmd.contains("EXIT")) {
                System.exit(0);
            }
        }
    }
    
    public void proc() {
        OSSimulator.desktop.buildProcessTable();
        OSSimulator.desktop.addTextToConsole("The Process Table has been reloaded.");
    }
    
    public void mem() {
        OSSimulator.desktop.buildMemoryTable();
        OSSimulator.desktop.addTextToConsole("The Memory Table has been reloaded.");
    }
    
    public void load(int simulationCycle, String jobProgramName) {
        this.loadables.add(
            new LoadableData(simulationCycle, jobProgramName, true)
        );
    }
    
    public void loadPrograms(int clockTime) {
        ArrayList<LoadableData> newLoadables = new ArrayList<>();
        
        for (int i = 0; i < loadables.size(); i++) {
            LoadableData loadable = loadables.get(i);
            
            if (loadable.getSimulationCycle() != clockTime) {
                newLoadables.add(loadable);
                
                continue;
            }
            
            if (loadable.getIsProgram()) {
                int memorySize = 0;
                ArrayList<String> operations = new ArrayList<>();
                ArrayList<Integer> cyclesOfOperations = new ArrayList<>();
                int initialBurst = 0;
                
                try {
                    String s;
                    BufferedReader input = new BufferedReader(new FileReader(getClass().getResource("/os/simulator/programs/" + loadable.getJobProgramName()).getFile()));
                    
                    while ((s = input.readLine()) != null) {
                        StringTokenizer st = new StringTokenizer(s);
                        
                        String operation = st.nextToken();
                        
                        if (!operation.equals("MEM_REQ")) {
                            operations.add(operation);
                        }
                        
                        if (st.hasMoreTokens()) {
                            String cyclesUnformatted = st.nextToken();
                            
                            int indexOfClosingAngleBracket = cyclesUnformatted.indexOf(">");

                            if (operation.equals("MEM_REQ")) {
                                memorySize = Integer.parseInt(cyclesUnformatted.substring(1, indexOfClosingAngleBracket - 2));
                            } else {
                                String numberOfCyclesStr = cyclesUnformatted.substring(1, indexOfClosingAngleBracket);
                                int numberOfCycles = 0;

                                if (numberOfCyclesStr.equals("?")) {
                                    numberOfCycles = 1;
                                } else {
                                    numberOfCycles = Integer.parseInt(numberOfCyclesStr);
                                }
                                
                                initialBurst += numberOfCycles;
                                cyclesOfOperations.add(numberOfCycles);
                            }
                        } else {
                            initialBurst += 1;
                            cyclesOfOperations.add(1);
                        }
                    }
                    
                    input.close();
		} catch (FileNotFoundException fnfe) {
                    System.out.println(fnfe.getMessage());
		} catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
		}
                
                if (OS.memoryManager.allocateMemory(memorySize)) {
                    PCB process = new PCB(loadable.getJobProgramName(), processID++, ProcessState.READY, 0, memorySize, memorySize, 
                            operations, cyclesOfOperations, loadable.getSimulationCycle(), initialBurst);
                    
                    OS.scheduler.insertPCB(process, false);
                    OS.processes.enQueue(process);
                } else {
                    PCB process = new PCB(loadable.getJobProgramName(), processID++, ProcessState.NEW, 0, memorySize, 0,
                            operations, cyclesOfOperations, loadable.getSimulationCycle(), initialBurst);
                    
                    OS.scheduler.insertPCB(process, true);
                    OS.processes.enQueue(process);
                }
            }
        }
        
        loadables = newLoadables;
    }
    
    public void exe(int cycles) {        
        while (cycles == 0 || OS.cpu.getClock().getClock() <= cycles) {
            if (OS.cpu.getActiveProcess() == null && OS.scheduler.getWaitingQueue().isEmpty() && OS.scheduler.getReadyQueue().isEmpty() && loadables.isEmpty()) {
                CommandInterface.canInvokeExecute = true;
                
                break;
            }
            
            if (!loadables.isEmpty()) {
                loadPrograms(OS.cpu.getClock().getClock());
            }
            
            OS.dispatcher.dispatch();
            OS.cpu.advanceClock();
            
            OS.processManager.incrementWaitOnReadyProcesses();
            OS.memoryManager.addWaitingProcessesToMemory();
                        
            OSSimulator.desktop.buildMemoryTable();
            OSSimulator.desktop.buildProcessTable();
            
            OSSimulator.sleepForFiftyMs();
        }
    }
    
    public void reset() {
        executionThread.stop();
        
        processID = 100;
        loadables.clear();
        
        OS.initializeOS();
        
        OSSimulator.desktop.buildMemoryTable();
        OSSimulator.desktop.buildProcessTable();
    }
    
    public void promptUser() {}    
}