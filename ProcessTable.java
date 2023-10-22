import java.util.ArrayList;
import java.util.List;

public class ProcessTable {
    private List<BCP> processList;

    public ProcessTable() {
        processList = new ArrayList<>();
    }
    
    public void newList(List<BCP> programs){
        processList = programs;
    }
    
    public void addProcess(BCP bcp) {
        processList.add(bcp);
    }

    public BCP get(int index){
        return processList.get(index);
    }

    public void removeProcess(BCP bcp) {
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
