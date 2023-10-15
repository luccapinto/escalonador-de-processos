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
        while (!readyprocesses.isEmpty() || !blockedprocesses.isEmpty()) {
            if(i >= readyprocesses.size()){
                i = 0;
            }
            // Manipulação de processos bloqueados...
            // [Lógica para mover processos de blockedprocesses para readyprocesses conforme necessário]
            if(!blockedprocesses.isEmpty()){
                for(int n = 0; n < blockedprocesses.size(); n++){
                    blockedprocesses.get(n).decreaseWaitTime();
                        if(blockedprocesses.get(n).getWaitTime() <= 0){
                            readyprocesses.add((blockedprocesses).get(n));
                            blockedprocesses.remove(n);
                        }
                }
            }

            BCP bcp = readyprocesses.get(i);
            if (bcp != null) {
                bcp.setState("EXECUTANDO");
                logFile.writeLog("Executando " + bcp.getName());
                int pcinicial = bcp.getPc();
                int executedInstructions = executeInstructions(bcp, quantum, i);
                switch (bcp.getState()) {
                    case "BLOQUEADO":
                        logFile.writeLog(bcp.getName() + " bloqueado após " + executedInstructions + " instruções.");
                        break;
                    case "TERMINADO":
                        logFile.writeLog(bcp.getName() + " terminado. X=" + bcp.getX() + ". Y=" + bcp.getY());
                        break;
                    case "EXECUTANDO":
                        if(bcp.getPc() - pcinicial == 0){
                            bcp.setState("TERMINADO");
                            readyprocesses.remove(i);
                        }
                        else{
                            logFile.writeLog("Interrompendo " + bcp.getName() + " após " + executedInstructions + " instruções.");
                            readyprocesses.add(bcp);
                        }
                        break;
                }
                i++;
            }
        }
    }


    private int executeInstructions(BCP bcp, int quantum, int index) {
        int executedInstructions = 0;
        for(int j = 0; readyprocesses.get(index).getPc() < readyprocesses.get(index).getInstructions().size() && j < quantum; readyprocesses.get(index).incrementPc(), j++){
            String comando = readyprocesses.get(index).getInstructions().get(readyprocesses.get(index).getPc()).substring(0, 2); 
            switch(comando){
                case "CO":
                executedInstructions++;
                break;
                case "E/":
                    readyprocesses.get(index).setWaitTime(2);
                    blockedprocesses.add(readyprocesses.get(index));
                    readyprocesses.remove(index);
                    bcp.setState("BLOQUEADO");
                    j=quantum; //força saída do loop
                    executedInstructions++;
                break;
                case "SA":
                    bcp.setState("TERMINADO");
                    readyprocesses.remove(index);
                    j=quantum; //força saída do loop
                break;
            }
        }
        return executedInstructions;
    }
}
