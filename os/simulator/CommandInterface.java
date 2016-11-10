package os.simulator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * This class handles commands entered by the user.
 * 
 * @author BTKS
 */
public class CommandInterface implements Runnable {
    
    /**
     * The thread that is executing the OS/CPU.
     */
    public static volatile Thread executionThread;
    
    /**
     * Whether or not a new thread can be generated for execution.
     */
    public static volatile boolean canInvokeExecute = true;
    
    /**
     * Contains a list of commands entered by the user that have not been processed.
     */
    public static volatile LinkedList<String> commands = new LinkedList<>();
    
    /**
     * Holds the next ID to assign to a new process.
     */
    private int processID;
    
    /**
     * Contains a list of programs to load.
     */
    private ArrayList<LoadableData> loadables;
    
    /**
     * Construct a new Command Interface.
     */
    public CommandInterface() {
        this.processID = OS.STARTING_PROCESS_ID;
        this.loadables = new ArrayList<>();        

    }
    
    public static synchronized void addCommand(String cmd) {
        CommandInterface.commands.add(cmd);
    }
    
    /**
     * Handles reading commands entered by the user on a new thread.
     */
    @Override
    public void run() {
        while (true) {
            if (!commands.isEmpty()) {
                OSSimulator.commandInterface.handleCommand(commands.poll());
            }
        }
    }
    
    /**
     * Parses and handles a command entered by the user on the Desktop.
     * 
     * @param cmd the command to parse
     */
    public void handleCommand(String cmd) {
        if (cmd == null) {
            return;
        }
        
        // command is associated with a clock cycle
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
    
    /**
     * Reloads the Process Table.
     */
    public void proc() {
        OSSimulator.desktop.buildProcessTable();
        OSSimulator.desktop.addTextToConsole("The Process Table has been reloaded.");
    }
    
    /**
     * Reloads the Memory Table.
     */
    public void mem() {
        OSSimulator.desktop.buildMemoryTable();
        OSSimulator.desktop.addTextToConsole("The Memory Table has been reloaded.");
    }
    
    /**
     * Loads either a Job or Program onto the Operating System.
     * 
     * @param simulationCycle the cycle to execute the program
     * @param jobProgramName the name of the job or program
     */
    public void load(int simulationCycle, String jobProgramName) {
        // get URL from jobs folder if the resource exists
        URL url = getClass().getResource("/os/simulator/jobs/" + jobProgramName);
        
        // if resource does not exist, then it should be a program and not a job
        if (url != null) {
            loadJob(jobProgramName);
        } else {
            this.loadables.add(
                new LoadableData(simulationCycle, jobProgramName, true)
            );
        }
        
    }
    
    /**
     * Loads a Job onto the Operating System.
     * 
     * @param jobProgramName the name of the job
     */
    public void loadJob(String jobProgramName) {        
        try {
            String s;
            BufferedReader input = new BufferedReader(new FileReader(getClass().getResource("/os/simulator/jobs/" + jobProgramName).getFile()));

            while ((s = input.readLine()) != null) {
                CommandInterface.addCommand(s);
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
    
    /**
     * Loads programs onto the Operating System.
     * 
     * @param clockTime the clock time of the programs to load
     */
    public void loadPrograms(int clockTime) {
        ArrayList<LoadableData> newLoadables = new ArrayList<>();
        
        for (int i = 0; i < loadables.size(); i++) {
            LoadableData loadable = loadables.get(i);
            
            // if loadable is for a different clock cycle, don't add it yet
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
                        
                        // MEM_REQ will be handled now rather than later for setting memory size
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
    
    /**
     * Execute the Operating System.
     * 
     * @param cycles number of cycles to run the OS for
     */
    public void exe(int cycles) {        
        while (cycles == 0 || OS.cpu.getClock().getClock() <= cycles) {
            // nothing else left to do, stop executing
            if (OS.cpu.getActiveProcess() == null && OS.scheduler.getWaitingQueue().isEmpty() && OS.scheduler.getReadyQueue().isEmpty() && loadables.isEmpty()) {
                CommandInterface.canInvokeExecute = true;
                
                break;
            }
            
            // load new programers onto the OS
            if (!loadables.isEmpty()) {
                loadPrograms(OS.cpu.getClock().getClock());
            }
            
            // dispatch a process to the CPU
            OS.dispatcher.dispatch();
            
            // advance the CPU by one clock
            OS.cpu.advanceClock();
            
            // increment time spent waiting on ready processes
            OS.processManager.incrementWaitOnReadyProcesses();
            
            // add any waiting processes to memory if possible
            OS.memoryManager.addWaitingProcessesToMemory();
            
            OSSimulator.desktop.buildMemoryTable();
            OSSimulator.desktop.buildProcessTable();
            
            // delay to prevent tables from refreshing faster than the eye can see
            OSSimulator.sleepForFiftyMs();
        }
    }
    
    /**
     * Resets the OS Simulator.
     */
    public void reset() {
        if (executionThread != null) {
            executionThread.stop();
        }
        
        processID = 100;
        loadables.clear();
        
        OS.initializeOS();
        
        OSSimulator.desktop.buildMemoryTable();
        OSSimulator.desktop.buildProcessTable();
    }
    
    public void promptUser() {}    
}