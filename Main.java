import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        String programsPath = "programas";
        LogFile logFile = new LogFile("log.txt");

        int quantum = readQuantum("programas/quantum.txt");
        if (quantum <= 0) {
            System.out.println("Quantum inválido. Finalizando programa.");
            return;
        }
        
        // Criando a fila de BCPs
        Queue<BCP> readyQueue = new LinkedList<>();
        Queue<BCP> blockedQueue = new LinkedList<>();

        // Carregando os BCPs
        try {
            Files.list(Paths.get(programsPath))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(file -> {
                        try {
                            BCP bcp = new BCP(file.getPath());
                            readyQueue.add(bcp);
                            logFile.writeLog("Carregando " + bcp.getName());
                        } catch (IOException e) {
                            e.printStackTrace();
                            // Lide com o erro conforme necessário
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
            // Trate o erro conforme a necessidade do seu aplicativo.
        }

        // Instanciando e iniciando o Scheduler
        Scheduler scheduler = new Scheduler(readyQueue, blockedQueue, quantum, logFile);
        scheduler.executeProcesses();
        
        // Logando informações finais
        logFile.writeLog("QUANTUM: " + quantum);
        logFile.readLog();
    }

    private static int readQuantum(String path) {
        try (var br = Files.newBufferedReader(Path.of(path))) {
            return Integer.parseInt(br.readLine().trim());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
