import java.util.List;
import java.util.ArrayList;
public class ProcessTable {
    private List<BCP> processList;

    public ProcessTable() {
        processList = new ArrayList<>();
    }
    
    public void addProcess(BCP bcp) {
        processList.add(bcp);
    }

    public void removeProcess(BCP bcp) {
        processList.remove(bcp);
    }
}
