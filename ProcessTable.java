import java.util.LinkedList;
import java.util.Queue;

public class ProcessTable {
    private Queue<BCP> processList;

    public ProcessTable() {
        processList = new LinkedList<>();
    }
    
    public void addProcess(BCP bcp) {
        processList.add(bcp);
    }

    public void removeProcess(BCP bcp) {
        processList.remove(bcp);
    }
}
