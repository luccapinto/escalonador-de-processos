import java.util.ArrayList;
import java.util.List;

public class ProcessTable {
    private List<BCP> processList;

    public ProcessTable() { // Cria a tabela de processos, vamos usar lista para isso
        processList = new ArrayList<>();
    }
    
    public void newList(List<BCP> programs){ // Importa a lista de programas para a tabela de processos
        processList = programs;
    }
    
    public void addProcess(BCP bcp) { // Adiciona um processo à lista
        processList.add(bcp);
    }

    public BCP get(int index){ // Pega o processo que está em determinada posição da lista
        return processList.get(index);
    }

    public void removeProcess(BCP bcp) { // Remove o processo da lista
        processList.remove(bcp);
    }
    public int size(){ 
        return processList.size();
    }

    public String getProcessName(int index) {
        return processList.get(index).getName();
    }

    public Boolean isEmpty(){
        return processList.isEmpty();
    }
    
}
