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