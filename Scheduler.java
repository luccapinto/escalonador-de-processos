import java.io.File;
import java.util.Queue;

public class Scheduler {
    Queue<Process> readyQueue;
    Queue<Process> blockedQueue;
    int quantum;
    
    public void loadProcesses(String dirPath) {
    File folder = new File(dirPath);
    File[] listOfFiles = folder.listFiles();

    for (File file : listOfFiles) {
        if (file.isFile() && file.getName().endsWith(".txt")) {
            Process process = new Process();
            // Código para ler o arquivo e inicializar o objeto `process`...
            // ...
            readyQueue.add(process);
        }
    }
}

    
    public void executeProcesses() {
        while(!readyQueue.isEmpty() || !blockedQueue.isEmpty()) {
            // Selecionar próximo processo a ser executado na readyQueue...
            Process process = readyQueue.poll();
            
            // Código para executar as instruções do processo...
            // Decidir se deve continuar executando, bloquear por E/S ou terminar baseado nas instruções.
            // ...
            
            // Log actions
            logProcessAction("Executando " + process.name);
            // ...
        }
}


    public void logProcessAction(String actionDetails) {
        // Implementação para registrar as ações do processo.
    }

    public enum ProcessState {
        READY, BLOCKED, EXECUTING, TERMINATED
    }

    
    

    // ... outros métodos conforme a necessidade
}

