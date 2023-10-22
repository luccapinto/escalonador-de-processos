import java.util.List;

public class Escalonador {
    private TabelaDeProcessos listaDeProntos; // Fila de processos prontos
    private TabelaDeProcessos listaDeBloqueados; // Fila de processo bloqueados
    private int quantum;
    private double ntrocas = 0;
    private double nprocessos;
    private double ninstrucoes = 0;
    private LogFile logFile;

    public Escalonador(List<BCP> processes, int quantum, LogFile logFile) {
        listaDeProntos = new TabelaDeProcessos();
        listaDeProntos.novaLista(processes);
        listaDeBloqueados = new TabelaDeProcessos();
        this.quantum = quantum;
        this.nprocessos = processes.size();
        this.logFile = logFile;
    }

    public void executaEscalonador() {
        for(int i = 0; i < listaDeProntos.size(); i++){
            logFile.escreveLog("Carregando: " + listaDeProntos.get(i).getNome());
        }
        int i = 0;
        while (!listaDeProntos.isEmpty() || !listaDeBloqueados.isEmpty()) {
            if(i >= listaDeProntos.size()){ // Evitar que não saia da lista
                i = 0;
            }

            // Se a lista de processos prontos estiver vazia, decrementa os processos bloqueados até pelo menos um estar pronto para executar
            if(listaDeProntos.isEmpty()){
                for(int n = 0; n < listaDeBloqueados.size(); n++){
                    BCP livre = listaDeBloqueados.get(n);
                    livre.decrementaTempoEspera();
                        if(livre.getTempoEspera() <= 0){
                            listaDeProntos.addProcesso(livre);
                            listaDeBloqueados.removeProcesso(livre);
                        }
                }
            }
            
            for (i = 0; i < listaDeProntos.size(); i++) {
                BCP bcp = listaDeProntos.get(i);
                if (bcp != null) {
                    bcp.setEstado("EXECUTANDO");
                    logFile.escreveLog("Executando " + bcp.getNome());
                    int pcinicial = bcp.getPc();
                    int instrucoesExecutadas = executaProcesso(bcp, quantum, logFile); 
                    // Executa as instruções e verifica o estado após as execuções - Escreve no log de acordo com o estado
                    switch (bcp.getEstado()) {
                        case "BLOQUEADO":
                            logFile.escreveLog("Interrompendo " + bcp.getNome() + " após " + instrucoesExecutadas + " instruções.");
                            break;
                        case "TERMINADO":
                            logFile.escreveLog(bcp.getNome() + " terminado. X=" + bcp.getX() + ". Y=" + bcp.getY());
                            break;
                        case "EXECUTANDO":
                            if(bcp.getPc() - pcinicial == 0){ // Se não tem nenhuma instrução, finaliza o processo
                                bcp.setEstado("TERMINADO");
                                listaDeProntos.removeProcesso(bcp);
                                logFile.escreveLog(bcp.getNome() + " terminado. X=" + bcp.getX() + ". Y=" + bcp.getY());
                            }
                            else{
                                logFile.escreveLog("Interrompendo " + bcp.getNome() + " após " + instrucoesExecutadas + " instruções.");
                            }
                            break;
                        }
                    i++;
                    ntrocas++; //atualiza  o total de trocas
                    ninstrucoes += instrucoesExecutadas; //atualiza o total de instruções executadas
                }

            }

        }
        logFile.escreveLog("MEDIA DE TROCAS: " + String.format("%.2f", (double) ntrocas / nprocessos));
        logFile.escreveLog("MEDIA DE INSTRUCOES: " + String.format("%.2f", (double) ninstrucoes / ntrocas));
        logFile.escreveLog("QUANTUM: " + quantum);
    
    }

    private int executaProcesso(BCP bcp, int quantum, LogFile logFile) {
        int instrucoesExecutadas = 0;
        List<String> instrucoes = bcp.getInstrucoes(); // Salva o numero de instruções para evitar saida do array
    
        for (int j = 0; bcp.getPc() < instrucoes.size() && j < quantum; bcp.incrementaPc(), j++) {
            String comando = instrucoes.get(bcp.getPc()).substring(0, 2); 
            switch(comando) {

                case "CO": // Conta como comando executado
                    instrucoesExecutadas++;
                    break;

                case "E/": // Instrução de E/S - Conta como comando executado e adiciona na lista de bloqueados
                    bcp.setTempoEspera(2);
                    listaDeBloqueados.addProcesso(bcp);
                    listaDeProntos.removeProcesso(bcp);
                    bcp.setEstado("BLOQUEADO");
                    logFile.escreveLog("E/S iniciada em " + bcp.getNome());
                    j = quantum;
                    instrucoesExecutadas++;
                    break;

                case "SA": // Instrução de SAIDA - Conta como comando executado e remove da lista
                    bcp.setEstado("TERMINADO");
                    listaDeProntos.removeProcesso(bcp);
                    j = quantum;
                    instrucoesExecutadas++;
                    break;
                case "X=":
                    bcp.setX(Integer.parseInt(instrucoes.get(bcp.getPc()).substring(2)));
                    instrucoesExecutadas++;
                    break;
                case "Y=":
                    bcp.setY(Integer.parseInt(instrucoes.get(bcp.getPc()).substring(2)));
                    instrucoesExecutadas++;
                    break;
            }
            // Decrementa o tempo de espera de toda a lista de bloqueados após cada execução
            for(int n = 0; n < listaDeBloqueados.size(); n++){
                BCP livre = listaDeBloqueados.get(n);
                livre.decrementaTempoEspera();
                    if(livre.getTempoEspera() <= 0){
                        listaDeProntos.addProcesso(livre);
                        listaDeBloqueados.removeProcesso(livre);
                    }
                }
        }
        return instrucoesExecutadas;
    }
    
 
}