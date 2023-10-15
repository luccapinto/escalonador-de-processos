import java.util.HashMap;
import java.util.Map;

public class ProcessTable {
    private Map<String, BCP> table;

    public ProcessTable() {
        this.table = new HashMap<>();
    }

    public void addProcess(BCP bcp) {
        this.table.put(bcp.getName(), bcp);
    }

    public BCP getProcess(String name) {
        return this.table.get(name);
    }

    public BCP removeProcess(String name) {
        return this.table.remove(name);
    }

    // ... outros métodos conforme a necessidade ...
}
