package os.simulator;

/**
 * This class holds program data, which will be loaded as a process onto the OS.
 * 
 * @author BTKS
 */
public class LoadableData {

    /**
     * The cycle to start/load the process/program.
     */
    private int simulationCycle;
    
    /**
     * The name of the program.
     */
    private String jobProgramName;
    
    /**
     * This should always be true.
     */
    private boolean isProgram;

    /**
     * Construct a new LoadableData.
     * 
     * @param simulationCycle the cycle to load the program
     * @param jobProgramName the name of the program
     * @param isProgram true if its a program (always a program)
     */
    public LoadableData(int simulationCycle, String jobProgramName, boolean isProgram) {
        this.simulationCycle = simulationCycle;
        this.jobProgramName = jobProgramName;
        this.isProgram = isProgram;
    }

    /**
     * Returns the clock time to start the program.
     * 
     * @return clock time
     */
    public int getSimulationCycle() {
        return simulationCycle;
    }

    /**
     * Sets the clock time to start the program.
     * 
     * @param simulationCycle clock time
     */
    public void setSimulationCycle(int simulationCycle) {
        this.simulationCycle = simulationCycle;
    }

    /**
     * Returns the name of the program.
     * 
     * @return the name of the program
     */
    public String getJobProgramName() {
        return jobProgramName;
    }

    /**
     * Sets the name of the program.
     * 
     * @param jobProgramName the name of the program
     */
    public void setJobProgramName(String jobProgramName) {
        this.jobProgramName = jobProgramName;
    }

    /**
     * Returns if this is a program or not.
     * 
     * @return true if this is a program
     */
    public boolean getIsProgram() {
        return isProgram;
    }

    /**
     * Sets if this is a program or not.
     * 
     * @param isProgram should be true
     */
    public void setIsProgram(boolean isProgram) {
        this.isProgram = isProgram;
    }
}