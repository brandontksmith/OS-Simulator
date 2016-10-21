/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.simulator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author BTKS
 */
public class CommandInterface {
    
    private static final int ROUND_ROBIN_TIME_QUANTUM = 10;
    private static final int MEMORY_SIZE_IN_KB = 256;
    
    private int processID;
    private ArrayList<LoadableData> loadables;
    private ExecutionQueue waitingQueue;
    
    private Scheduler scheduler;
    private Clock clock;
    private CPU cpu;
    private MemoryManager memoryManager;
    
    public CommandInterface() {
        this.processID = 100;
        this.loadables = new ArrayList<>();
        this.waitingQueue = new ExecutionQueue();
        this.scheduler = new Scheduler(ROUND_ROBIN_TIME_QUANTUM);
        this.clock = new Clock();
        this.cpu = new CPU(scheduler, clock);
        this.memoryManager = new MemoryManager(MEMORY_SIZE_IN_KB);

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
                    exe(cycle);
                    
                    break;
            }
        } else {
            if (cmd.contains("PROC")) {
                proc();
            } else if (cmd.contains("MEM")) {
                mem();
            } else if (cmd.contains("LOAD")) {
                
            } else if (cmd.contains("EXE")) {
                exe(0);
            } else if (cmd.contains("RESET")) {
                reset();
            } else if (cmd.contains("EXIT")) {
                System.exit(0);
            }
        }
    }
    
    public void proc() {}
    public void mem() {}
    
    public void load(int simulationCycle, String jobProgramName) {
        this.loadables.add(
            new LoadableData(simulationCycle, jobProgramName, true)
        );
    }
    
    public void exe(int cycles) {
        for (int i = 0; i < loadables.size(); i++) {
            LoadableData loadable = loadables.get(i);
            
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
                        operations.add(operation);
                        
                        if (st.hasMoreTokens()) {
                            String cyclesUnformatted = st.nextToken();
                            
                            int indexOfClosingAngleBracket = cyclesUnformatted.indexOf(">");

                            if (operation.equals("MEM_REQ")) {
                                memorySize = Integer.parseInt(cyclesUnformatted.substring(1, indexOfClosingAngleBracket - 2));
                            } else {
                                String numberOfCyclesStr = cyclesUnformatted.substring(1, indexOfClosingAngleBracket);
                                int numberOfCycles = 0;

                                if (numberOfCyclesStr.equals("?")) {
                                    numberOfCycles = IOBurst.generateIOBurst();
                                } else {
                                    numberOfCycles = Integer.parseInt(numberOfCyclesStr);
                                }
                                
                                initialBurst += numberOfCycles;
                                cyclesOfOperations.add(numberOfCycles);
                            }
                        } else {
                            cyclesOfOperations.add(0);
                        }
                    }
                    
                    input.close();
		} catch (FileNotFoundException fnfe) {
                    System.out.println(fnfe.getMessage());
		} catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
		}
                
                if (memoryManager.allocateMemory(memorySize)) {
                    PCB process = new PCB(processID++, ProcessState.READY, 1, memorySize, 
                            operations, cyclesOfOperations, loadable.getSimulationCycle(), initialBurst);
                    scheduler.insertPCB(process);
                } else {
                    PCB process = new PCB(processID++, ProcessState.WAIT, 1, memorySize, 
                            operations, cyclesOfOperations, loadable.getSimulationCycle(), initialBurst);
                    
                    waitingQueue.enQueue(process);
                }
            }
        }
        
        loadables.clear();
        
        while (cycles == 0 || clock.getClock() <= cycles) {
            if (cpu.getActiveProcess() == null && waitingQueue.isEmpty() && scheduler.getReadyQueue().isEmpty()) {
                break;
            }
            
            cpu.setActiveProcess(scheduler.getNextProcess(clock.getClock()));
            
            PCB activeProcess = cpu.getActiveProcess();
            
            if (activeProcess != null) {
                if (activeProcess.getArrival() == cpu.getClock().getClock()) {
                    activeProcess.setArrived(true);
                    activeProcess.setStarted(true);
                    activeProcess.setState(ProcessState.RUN);
                }
                
                activeProcess.setBurst(activeProcess.getBurst() - 1);
                
                if (activeProcess.getBurst() == 0) {
                    activeProcess.setFinished(true);
                    cpu.setActiveProcess(null);
                    memoryManager.deallocateMemory(activeProcess.getMemoryAllocated());
                }
            }
            
            for (int i = 0; i < scheduler.getReadyQueue().size(); i++) {
                PCB p = scheduler.getReadyQueue().get(i);
                
                if (activeProcess == null || p.getProcessID() != activeProcess.getProcessID()) {
                    p.setWait(p.getWait() + 1);
                }
            }
            
            for (int i = 0; i < waitingQueue.size(); i++) {
                PCB process = waitingQueue.get(i);
                
                if (memoryManager.allocateMemory(process.getMemoryAllocated())) {
                    waitingQueue.deQueue(process);
                    process.setState(ProcessState.READY);
                    scheduler.insertPCB(process);
                }
            }
            
            if (activeProcess != null) {
                System.out.println("Process ID: " + activeProcess.getProcessID() + ", "
                        + "Wait: " + activeProcess.getWait() + ", "
                        + "Burst: " + activeProcess.getBurst() + ", "
                        + "Arrived: " + activeProcess.isArrived() + ", "
                        + "Active: " + activeProcess.isActive() + ", "
                        + "Finished: " + activeProcess.isFinished() + ", "
                        + "Started: " + activeProcess.isStarted()
                );
            }
            
            cpu.advanceClock();
        }
    }
    
    public void reset() {}
    
    public void promptUser() {}
    
        public class LoadableData {
        
        private int simulationCycle;
        private String jobProgramName;
        private boolean isProgram;
        
        public LoadableData(int simulationCycle, String jobProgramName, boolean isProgram) {
            this.simulationCycle = simulationCycle;
            this.jobProgramName = jobProgramName;
            this.isProgram = isProgram;
        }

        public int getSimulationCycle() {
            return simulationCycle;
        }

        public void setSimulationCycle(int simulationCycle) {
            this.simulationCycle = simulationCycle;
        }

        public String getJobProgramName() {
            return jobProgramName;
        }

        public void setJobProgramName(String jobProgramName) {
            this.jobProgramName = jobProgramName;
        }

        public boolean getIsProgram() {
            return isProgram;
        }

        public void setIsProgram(boolean isProgram) {
            this.isProgram = isProgram;
        }
    }
}