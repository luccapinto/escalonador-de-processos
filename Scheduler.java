import java.util.Queue;

public class Scheduler {
    private Queue<BCP> readyprocesses;
    private Queue<BCP> blockedprocesses;
    private int quantum;

    public Scheduler(Queue<BCP> processes, int quantum) {
        this.readyprocesses = processes;
        this.quantum = quantum;
    }

public void executeProcesses() {
    while (!readyprocesses.isEmpty() || !blockedprocesses.isEmpty()) {
        // Selecionar próximo BCP a ser executado na readyQueue...
        BCP bcp = readyprocesses.poll();

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
                    blockedprocesses.add(bcp);
                    break;
                case "TERMINADO":
                    logProcessAction(bcp.getName() + " terminado. X=" + bcp.getX() + ". Y=" + bcp.getY());
                    break;
                case "EXECUTANDO":
                    logProcessAction("Interrompendo " + bcp.getName() + " após " + executedInstructions + " instruções.");
                    readyprocesses.add(bcp); // Retorna para a fila de prontos para continuar execução posteriormente
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
