import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Scheduler {
    Queue<Process> readyQueue;
    Queue<Process> blockedQueue;
    int quantum;

    public Scheduler(int quantum) {
        this.readyQueue = new LinkedList<>();
        this.blockedQueue = new LinkedList<>();
        this.quantum = quantum;
    }

    public void loadProcesses(String dirPath) throws IOException {
        File folder = new File(dirPath);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                Process process = new Process();
                
                // Assuming file format is readable and consistent
                List<String> lines = Files.readAllLines(file.toPath());
                
                process.name = lines.get(0);  // Assuming first line is name
                // Additional initialization of `process` using `lines`...
                // ...

                readyQueue.add(process);
                logProcessAction("Carregando " + process.name);
            }
        }
    }

    public void executeProcesses() {
        while (!readyQueue.isEmpty() || !blockedQueue.isEmpty()) {
            Process process = readyQueue.poll();

            if(process != null) {
                logProcessAction("Executando " + process.name);
                
                // Execute instructions here and decide whether to:
                // - Add to `blockedQueue` (if I/O or waiting is needed)
                // - Terminate (if process is finished)
                // - Add back to `readyQueue` (if more execution is needed and quantum is not exhausted)
                // ...
            }

            // Potentially move processes from `blockedQueue` to `readyQueue` here...
            // ...

        }
    }

    public void logProcessAction(String actionDetails) {
        System.out.println(actionDetails);
        // Or write to a log file if needed.
    }

    public static class Process {
        String name;
        int x, y;  // Example process state variables
        List<String> instructions;  // Placeholder for process instructions

        ProcessState state;

        // Potentially more properties and methods needed...

        // Default constructor, getters, setters...
    }

    public enum ProcessState {
        READY, BLOCKED, EXECUTING, TERMINATED
    }

    public static void main(String[] args) throws IOException {
        Scheduler scheduler = new Scheduler(3);  // Example quantum
        scheduler.loadProcesses("path_to_your_files");  // Directory path
        scheduler.executeProcesses();
    }
}
