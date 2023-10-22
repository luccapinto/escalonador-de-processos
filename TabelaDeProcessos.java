import java.util.ArrayList;
import java.util.List;

public class TabelaDeProcessos {
    private List<BCP> processList;

    public TabelaDeProcessos() { // Cria a tabela de processos, vamos usar lista para isso
        processList = new ArrayList<>();
    }
    
    public void novaLista(List<BCP> programs){ // Importa a lista de programas para a tabela de processos
        processList = programs;
    }
    
    public void addProcesso(BCP bcp) { // Adiciona um processo à lista
        processList.add(bcp);
    }

    public BCP get(int index){ // Pega o processo que está em determinada posição da lista
        return processList.get(index);
    }

    public void removeProcesso(BCP bcp) { // Remove o processo da lista
        processList.remove(bcp);
    }
    public int size(){ 
        return processList.size();
    }

    public String getNomeProcesso(int index) {
        return processList.get(index).getNome();
    }

    public Boolean isEmpty(){
        return processList.isEmpty();
    }
    
}
