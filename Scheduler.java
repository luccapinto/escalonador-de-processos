import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<BCP> readyprocesses;
    private List<BCP> blockedprocesses;
    private int quantum;

    public Scheduler(List<BCP> processes, int quantum) {
        this.readyprocesses = processes;
        this.blockedprocesses = new ArrayList<>(); // Inicialização da fila de processos bloqueados
        this.quantum = quantum;
    }

    public void executeProcesses() {
        LogFile logFile = new LogFile("log.txt");
        int i = 0;
        while (!readyprocesses.isEmpty() && !blockedprocesses.isEmpty()) {
            if(i >= readyprocesses.size()){
                i = 0;
            }
            // Manipulação de processos bloqueados...
            // [Lógica para mover processos de blockedprocesses para readyprocesses conforme necessário]
            if(!blockedprocesses.isEmpty()){
                for(int n = 0; i < blockedprocesses.size(); n++){
                    blockedprocesses.get(n).decreaseWaitTime();
                        if(blockedprocesses.get(n).getWaitTime() <= 0){
                            readyprocesses.add((blockedprocesses).get(n));
                            blockedprocesses.remove(n);
                }
            }

            BCP bcp = readyprocesses.get(i);
            if (bcp != null) {
                bcp.setState("EXECUTANDO");
                logFile.writeLog("Executando " + bcp.getName());

                int executedInstructions = executeInstructions(bcp, quantum);
                switch (bcp.getState()) {
                    case "BLOQUEADO":
                        logFile.writeLog(bcp.getName() + " bloqueado após " + executedInstructions + " instruções.");
                        blockedprocesses.add(bcp);
                        break;
                    case "TERMINADO":
                        logFile.writeLog(bcp.getName() + " terminado. X=" + bcp.getX() + ". Y=" + bcp.getY());
                        break;
                    case "EXECUTANDO":
                        logFile.writeLog("Interrompendo " + bcp.getName() + " após " + executedInstructions + " instruções.");
                        readyprocesses.add(bcp);
                        break;
                }
            }
            }
        }

    }

    private int executeInstructions(BCP bcp, int quantum) {
        int executedInstructions = 0;
        for(int j = 0; readyprocesses.get(0).getPc() < readyprocesses.get(0).getInstructions().size() && j < quantum; readyprocesses.get(0).incrementPc(), j++){
            String comando = readyprocesses.get(0).getInstructions().get(readyprocesses.get(0).getPc()).substring(0, 2); 
            switch(comando){
                case "CO":
                executedInstructions++;
                break;
                case "E/":
                    readyprocesses.get(0).setWaitTime(2);
                    blockedprocesses.add(readyprocesses.get(0));
                    bcp.setState("BLOQUEADO");
                    j=quantum; //força saída do loop
                    executedInstructions++;
                break;
                case "SA":
                    bcp.setState("TERMINADO");
                    j=quantum; //força saída do loop
                break;
            }
        }
        return executedInstructions;
    }

    public void logProcessAction(String actionDetails) {
        System.out.println(actionDetails);
        // Implemente o logging para um arquivo ou outros destinos se necessário.
    }
}
