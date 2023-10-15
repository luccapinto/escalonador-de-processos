public class BCP {
    private int processId;
    private ProcessState state;
    private int programCounter;
    private int[] registers;

    // Constructor
    public BCP(int processId, ProcessState state, int programCounter, int[] registers) {
        this.processId = processId;
        this.state = state;
        this.programCounter = programCounter;
        this.registers = registers;
    }

    // Getters and Setters
    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public ProcessState getState() {
        return state;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

    public int[] getRegisters() {
        return registers;
    }

    public void setRegisters(int[] registers) {
        this.registers = registers;
    }

    @Override
    public String toString() {
        return "BCP{" +
                "processId=" + processId +
                ", state=" + state +
                ", programCounter=" + programCounter +
                ", registers=" + registers +
                '}';
    }
}

