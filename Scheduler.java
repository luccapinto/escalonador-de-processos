import java.util.List;

public class Scheduler {
    private ProcessTable readyprocesses; // Fila de processos prontos
    private ProcessTable blockedprocesses; // Fila de processo bloqueados
    private int quantum;
    private double ntrocas =0;
    private double nprocessos =0;
    private double ninstrucoes;
    private LogFile logFile;

    public Scheduler(List<BCP> processes, int quantum, LogFile logFile) {
        readyprocesses = new ProcessTable();
        readyprocesses.newList(processes);
        blockedprocesses = new ProcessTable();
        this.quantum = quantum;
        this.nprocessos = processes.size();
        this.logFile = logFile;
    }

    public void executeProcesses() {
        for(int i = 0; i < readyprocesses.size(); i++){
            logFile.writeLog("Carregando: " + readyprocesses.get(i).getName());
        }
        int i = 0;
        while (!readyprocesses.isEmpty() || !blockedprocesses.isEmpty()) {
            if(i >= readyprocesses.size()){ // Evitar que não saia da lista
                i = 0;
            }

            // Se a lista de processos prontos estiver vazia, decrementa os processos bloqueados até pelo menos um estar pronto para executar
            if(readyprocesses.isEmpty()){
                for(int n = 0; n < blockedprocesses.size(); n++){
                    BCP livre = blockedprocesses.get(n);
                    livre.decreaseWaitTime();
                        if(livre.getWaitTime() <= 0){
                            readyprocesses.addProcess(livre);
                            blockedprocesses.removeProcess(livre);
                        }
                }
            }
            
            for (int j = 0; j < readyprocesses.size(); j++) {
                BCP bcp = readyprocesses.get(i);
                if (bcp != null) {
                    bcp.setState("EXECUTANDO");
                    logFile.writeLog("Executando " + bcp.getName());
                    int pcinicial = bcp.getPc();
                    int executedInstructions = executeInstructions(bcp, quantum, i, logFile); 
                    // Executa as instruções e verifica o estado após as execuções - Escreve no log de acordo com o estado
                    switch (bcp.getState()) {
                        case "BLOQUEADO":
                            logFile.writeLog("Interrompendo " + bcp.getName() + " após " + executedInstructions + " instruções.");
                            break;
                        case "TERMINADO":
                            logFile.writeLog(bcp.getName() + " terminado. X=" + bcp.getX() + ". Y=" + bcp.getY());
                            break;
                        case "EXECUTANDO":
                            if(bcp.getPc() - pcinicial == 0){ // Se não tem nenhuma instrução, finaliza o processo
                                bcp.setState("TERMINADO");
                                readyprocesses.removeProcess(bcp);
                                logFile.writeLog(bcp.getName() + " terminado. X=" + bcp.getX() + ". Y=" + bcp.getY());
                            }
                            else{
                                logFile.writeLog("Interrompendo " + bcp.getName() + " após " + executedInstructions + " instruções.");
                            }
                            break;
                        }
                    i++;
                    ntrocas++; //atualiza  o total de trocas
                    ninstrucoes += executedInstructions; //atualiza o total de instruções executadas
                }

            }

        }
        logFile.writeLog("MEDIA DE TROCAS: " + String.format("%.2f", (double) ntrocas / nprocessos));
        logFile.writeLog("MEDIA DE INSTRUCOES: " + String.format("%.2f", (double) ninstrucoes / ntrocas));
        logFile.writeLog("QUANTUM: " + quantum);
    
    }

    private int executeInstructions(BCP bcp, int quantum, int index, LogFile logFile) {
        int executedInstructions = 0;
        List<String> instructions = readyprocesses.get(index).getInstructions(); // Salva o numero de instruções para evitar saida do array
    
        for (int j = 0; bcp.getPc() < instructions.size() && j < quantum; bcp.incrementPc(), j++) {
            String comando = instructions.get(bcp.getPc()).substring(0, 2); 
            switch(comando) {

                case "CO": // Conta como comando executado
                    executedInstructions++;
                    break;

                case "E/": // Instrução de E/S - Conta como comando executado e adiciona na lista de bloqueados
                    readyprocesses.get(index).setWaitTime(2);
                    blockedprocesses.addProcess(bcp);
                    readyprocesses.removeProcess(bcp);
                    bcp.setState("BLOQUEADO");
                    logFile.writeLog("E/S iniciada em " + bcp.getName());
                    j = quantum;
                    executedInstructions++;
                    break;

                case "SA": // Instrução de SAIDA - Conta como comando executado e remove da lista
                    bcp.setState("TERMINADO");
                    readyprocesses.removeProcess(bcp);
                    j = quantum;
                    executedInstructions++;
                    break;
            }
            // Decrementa o tempo de espera de toda a lista de bloqueados após cada execução
            for(int n = 0; n < blockedprocesses.size(); n++){
                BCP livre = blockedprocesses.get(n);
                livre.decreaseWaitTime();
                    if(livre.getWaitTime() <= 0){
                        readyprocesses.addProcess(livre);
                        blockedprocesses.removeProcess(livre);
                    }
                }
        }
        return executedInstructions;
    }
    
 
}