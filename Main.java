import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        String programsPath = "programas";
        LogFile logFile = new LogFile("log.txt");

        // Carregar os programas
        Queue<BCP> programs = loadPrograms(programsPath, logFile);
        
        int quantum = readQuantum("programas/quantum.txt");
        if (quantum <= 0) {
            System.out.println("Quantum inválido. Finalizando programa.");
            return;
        }
        Scheduler processes = new Scheduler(programs, quantum);
        processes.executeProcesses();
    }

    private static Queue<BCP> loadPrograms(String path, LogFile logFile) {
        Queue<BCP> programs = new LinkedList<>();
        try {
            Files.list(Paths.get(path))
                 .filter(Files::isRegularFile)
                 .map(Path::toFile)
                 .forEach(file -> {
                     logFile.writeLog("Carregando " + file.getName());
                 });
        } catch (IOException e) {
            e.printStackTrace();
            // Trate o erro conforme a necessidade do seu aplicativo.
        }
        return programs;
    }

    private static int readQuantum(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return Integer.parseInt(br.readLine().trim());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return -1;  
        }
    }
}
