import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Scheduler {
    private Queue<BCP> readyQueue;
    private Queue<BCP> blockedQueue;
    private int quantum;
    private LogFile logFile;

    public Scheduler(Queue<BCP> readyQueue, Queue<BCP> blockedQueue, int quantum, LogFile logFile) {
        this.readyQueue = readyQueue;
        this.blockedQueue = blockedQueue;
        this.quantum = quantum;
        this.logFile = logFile;
    }

    public void loadProcesses(String dirPath) {
    File folder = new File(dirPath);
    File[] listOfFiles = folder.listFiles();

    if (listOfFiles == null) {
        // handle error: directory not found or IO error
        return;
    }

    for (File file : listOfFiles) {
        if (file.isFile() && file.getName().endsWith(".txt")) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String name = br.readLine().trim();
                String xLine = br.readLine().trim();
                String yLine = br.readLine().trim();

                int xValue = Integer.parseInt(xLine.substring(2).trim());
                int yValue = Integer.parseInt(yLine.substring(2).trim());

                // Extracting instructions and states from the rest of the file
                List<String> instructions = new ArrayList<>();
                String line;
                while ((line = br.readLine()) != null) {
                    instructions.add(line.trim());
                }

                BCP process = new BCP(name, xValue, yValue, instructions);

                readyQueue.add(process);

            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
                // handle error: file not readable or invalid format
            }
        }
    }
}


public void executeProcesses() {
    while (!readyQueue.isEmpty() || !blockedQueue.isEmpty()) {
        // Selecionar próximo BCP a ser executado na readyQueue...
        BCP bcp = readyQueue.poll();

        // Verificando se o BCP não é nulo (por segurança)
        if (bcp != null) {
            // Definir estado como executando
            bcp.setState("EXECUTANDO");
            logProcessAction("Executando " + bcp.getName());

            // Exemplo: Execute até 'quantum' instruções ou até o processo bloquear/terminar
            int executedInstructions = executeInstructions(bcp, quantum);

            // Verifica o estado após a execução de instruções
            switch (bcp.getState()) {
                case "BLOQUEADO":
                    logProcessAction(bcp.getName() + " bloqueado após " + executedInstructions + " instruções.");
                    blockedQueue.add(bcp);
                    break;
                case "TERMINADO":
                    logProcessAction(bcp.getName() + " terminado. X=" + bcp.getX() + ". Y=" + bcp.getY());
                    break;
                case "EXECUTANDO":
                    logProcessAction("Interrompendo " + bcp.getName() + " após " + executedInstructions + " instruções.");
                    readyQueue.add(bcp); // Retorna para a fila de prontos para continuar execução posteriormente
                    break;
            }
        }

        // ... [Demais lógicas, como lidar com processos bloqueados, etc.]
    }
}

    private int executeInstructions(BCP bcp, int quantum) {
    int executedInstructions = 0;

    // Sua lógica para execução das instruções aqui. 
    // Atualizar os estados de 'bcp' conforme necessário.

    return executedInstructions;
}

    public void logProcessAction(String actionDetails) {
        System.out.println(actionDetails);
        // TODO: Implement logging to a file or other destinations if needed...
    }
}
