import java.util.ArrayList;
import java.util.List;

public class ProcessTable {

    private List<Process> processTable;

    public ProcessTable() {
        this.processTable = new ArrayList<>();
    }

    public void addProcess(Process process) {
        this.processTable.add(process);
    }

    public Process getProcess(String processName) {
        for (Process process : processTable) {
            if (process.name.equals(processName)) {
                return process;
            }
        }
        return null; // Caso não encontre o processo pelo nome.
    }

    public boolean removeProcess(String processName) {
        Process processToRemove = getProcess(processName);
        if (processToRemove != null) {
            this.processTable.remove(processToRemove);
            return true; // Remoção bem-sucedida
        }
        return false; // Falha na remoção
    }

    public List<Process> getProcesses() {
        return this.processTable;
    }

    // Outros métodos úteis podem ser adicionados conforme a necessidade.
}
