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
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author BTKS
 */
public class CommandInterface implements Runnable {
    
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
                    PCB process = new PCB(loadable.getJobProgramName(), processID++, ProcessState.READY, 0, memorySize, 
                            operations, cyclesOfOperations, loadable.getSimulationCycle(), initialBurst);
                    
                    OS.scheduler.insertPCB(process, false);
                    OS.processes.enQueue(process);
                } else {
                    PCB process = new PCB(loadable.getJobProgramName(), processID++, ProcessState.NEW, 0, memorySize, 
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
            if (OS.cpu.getActiveProcess() != null) {
                System.out.println("Process ID: " + OS.cpu.getActiveProcess().getProcessID() + ", "
                        + "Wait: " + OS.cpu.getActiveProcess().getWait() + ", "
                        + "Burst: " + OS.cpu.getActiveProcess().getBurst() + ", "
                        + "Arrived: " + OS.cpu.getActiveProcess().isArrived() + ", "
                        + "Finished: " + OS.cpu.getActiveProcess().isFinished() + ", "
                        + "Started: " + OS.cpu.getActiveProcess().isStarted() + ", "
                        + "Next Instruction: " + OS.cpu.getActiveProcess().getInstructionIndex() + ", "
                        + "Instruction Cycles: " + OS.cpu.getActiveProcess().getInstructionCycles() + ", "
                        + "Current Instruction: " + OS.cpu.getActiveProcess().getOperations().get(OS.cpu.getActiveProcess().getInstructionIndex())
                );
            }
            
            if (OS.cpu.getActiveProcess() == null && OS.scheduler.getWaitingQueue().isEmpty() && OS.scheduler.getReadyQueue().isEmpty() && loadables.isEmpty()) {
                break;
            }
            
            if (!loadables.isEmpty()) {
                loadPrograms(OS.cpu.getClock().getClock());
            }
            
            OS.cpu.setActiveProcess(OS.scheduler.getNextProcess(0));
            OS.cpu.advanceClock();
            
            for (int i = 0; i < OS.scheduler.getReadyQueue().size(); i++) {
                PCB p = OS.scheduler.getReadyQueue().get(i);

                if (OS.cpu.getActiveProcess() == null || p.getProcessID() != OS.cpu.getActiveProcess().getProcessID()) {
                    p.setWait(p.getWait() + 1);
                }
            }
            
            for (int i = 0; i < OS.scheduler.getWaitingQueue().size(); i++) {
                PCB process = OS.scheduler.getWaitingQueue().get(i);
                
                if (process.isWaitingIO()) {
                    continue;
                }
                
                if (OS.memoryManager.allocateMemory(process.getMemoryAllocated())) {
                    OS.scheduler.getWaitingQueue().deQueue(process);
                    process.setState(ProcessState.READY);
                    OS.scheduler.insertPCB(process, false);
                }
            }
            
            JTable memoryTable = OSSimulator.desktop.jTable1;
            JTable processTable = OSSimulator.desktop.jTable2;
            
            while(memoryTable.getRowCount() > 0) {
                ((DefaultTableModel) memoryTable.getModel()).removeRow(0);
            }
            
            int row = 0;
            
            for (int i = 0; i < OS.processes.size(); i++) {
                PCB process = OS.processes.get(i);
                
                if (process.isFinished() || process.getState() == ProcessState.NEW) {
                    continue;
                }
                
                ((DefaultTableModel) memoryTable.getModel()).insertRow(row++, new Object[]{
                    process.getProgramName(),
                    process.getMemoryAllocated()
                });
            }
            
            while(processTable.getRowCount() > 0) {
                ((DefaultTableModel) processTable.getModel()).removeRow(0);
            }
            
            row = 0;
            
            for (int i = 0; i < OS.processes.size(); i++) {
                PCB process = OS.processes.get(i);
                
                ((DefaultTableModel) processTable.getModel()).insertRow(row++, process.formatForTable());
            }
            
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(CommandInterface.class.getName()).log(Level.SEVERE, null, ex);
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