import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
    private Queue<BCP> readyprocesses;
    private Queue<BCP> blockedprocesses;
    private int quantum;

    public Scheduler(Queue<BCP> processes, int quantum) {
        this.readyprocesses = processes;
        this.blockedprocesses = new LinkedList<>(); // Inicialização da fila de processos bloqueados
        this.quantum = quantum;
    }

    public void executeProcesses() {
        while (!readyprocesses.isEmpty() || !blockedprocesses.isEmpty()) {
            // Manipulação de processos bloqueados...
            // [Lógica para mover processos de blockedprocesses para readyprocesses conforme necessário]
            if(!blockedprocesses.isEmpty()){
                for(int i = 0; i < blockedprocesses.size(); i++){
                    ((LinkedList<BCP>) blockedprocesses).get(i).decreaseWaitTime();
                        if(((LinkedList<BCP>) blockedprocesses).get(i).getWaitTime() <= 0){
                            readyprocesses.add(((LinkedList<BCP>) blockedprocesses).get(i));
                            blockedprocesses.remove(i);
                }
            }

            BCP bcp = readyprocesses.poll();
            if (bcp != null) {
                bcp.setState("EXECUTANDO");
                logProcessAction("Executando " + bcp.getName());

                int executedInstructions = executeInstructions(bcp, quantum);
                
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
                        readyprocesses.add(bcp);
                        break;
                }
            }
            }
        }

    }
    private int executeInstructions(BCP bcp, int quantum) {
        int executedInstructions = 0;
        // Implemente a lógica de execução de instruções aqui, modificando bcp conforme necessário.
        return executedInstructions;
    }

    public void logProcessAction(String actionDetails) {
        System.out.println(actionDetails);
        // Implemente o logging para um arquivo ou outros destinos se necessário.
    }
}
