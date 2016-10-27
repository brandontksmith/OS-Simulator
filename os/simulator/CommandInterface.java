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
        
    private int processID;
    private ArrayList<LoadableData> loadables;
    
    public Scheduler scheduler;
    public CPU cpu;
    public MemoryManager memoryManager;
    public InterruptProcessor interruptProcessor;
    
    public CommandInterface() {
        this.processID = 100;
        this.loadables = new ArrayList<>();
        
        this.scheduler = new Scheduler(OSSimulator.ROUND_ROBIN_TIME_QUANTUM);
        this.memoryManager = new MemoryManager(OSSimulator.MEMORY_SIZE_IN_KB);
        this.interruptProcessor = new InterruptProcessor(scheduler);
        this.cpu = new CPU(scheduler, memoryManager, interruptProcessor);

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
                    PCB process = new PCB(processID++, ProcessState.READY, 0, memorySize, 
                            operations, cyclesOfOperations, loadable.getSimulationCycle(), initialBurst);
                    
                    scheduler.insertPCB(process, false);
                } else {
                    PCB process = new PCB(processID++, ProcessState.WAIT, 0, memorySize, 
                            operations, cyclesOfOperations, loadable.getSimulationCycle(), initialBurst);
                    
                    scheduler.insertPCB(process, true);
                }
            }
        }
        
        loadables = newLoadables;
    }
    
    public void exe(int cycles) {        
        while (cycles == 0 || cpu.getClock().getClock() <= cycles) {
            if (cpu.getActiveProcess() == null && scheduler.getWaitingQueue().isEmpty() && scheduler.getReadyQueue().isEmpty() && loadables.isEmpty()) {
                break;
            }
            
            loadPrograms(cpu.getClock().getClock());
            
            cpu.setActiveProcess(scheduler.getNextProcess(0));
            cpu.advanceClock();
            
            for (int i = 0; i < scheduler.getReadyQueue().size(); i++) {
                PCB p = scheduler.getReadyQueue().get(i);

                if (cpu.getActiveProcess() == null || p.getProcessID() != cpu.getActiveProcess().getProcessID()) {
                    p.setWait(p.getWait() + 1);
                }
            }

            for (int i = 0; i < scheduler.getWaitingQueue().size(); i++) {
                PCB process = scheduler.getWaitingQueue().get(i);

                if (memoryManager.allocateMemory(process.getMemoryAllocated())) {
                    scheduler.getWaitingQueue().deQueue(process);
                    process.setState(ProcessState.READY);
                    scheduler.insertPCB(process, true);
                }
            }

            if (cpu.getActiveProcess() != null) {
                System.out.println("Process ID: " + cpu.getActiveProcess().getProcessID() + ", "
                        + "Wait: " + cpu.getActiveProcess().getWait() + ", "
                        + "Burst: " + cpu.getActiveProcess().getBurst() + ", "
                        + "Arrived: " + cpu.getActiveProcess().isArrived() + ", "
                        + "Active: " + cpu.getActiveProcess().isActive() + ", "
                        + "Finished: " + cpu.getActiveProcess().isFinished() + ", "
                        + "Started: " + cpu.getActiveProcess().isStarted() + ", "
                        + "Next Instruction: " + cpu.getActiveProcess().getNextInstructionIndex() + ", "
                        + "Instruction Cycles: " + cpu.getActiveProcess().getInstructionCycles() + ", "
                        + "Current Instruction: " + cpu.getActiveProcess().getOperations().get(cpu.getActiveProcess().getNextInstructionIndex())
                );
            }
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