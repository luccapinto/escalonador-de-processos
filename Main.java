import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        String programsPath = "programas";
        LogFile logFile = new LogFile("log.txt");

        // Carregar os programas
        List<BCP> programs = loadPrograms(programsPath, logFile);
        
        int quantum = readQuantum("programas/quantum.txt");
        if (quantum <= 0) {
            System.out.println("Quantum inválido. Finalizando programa.");
            return;
        }
        // printPrograms(programs);
        Scheduler processes = new Scheduler(programs, quantum);
        processes.executeProcesses();
    }
/* 
    public static void printPrograms(List<BCP> programs) {
        if (programs.isEmpty()) {
            System.out.println("Nenhum programa carregado.");
            return;
        }
        
        System.out.println("Programas carregados:");
        for (int i = 0; i < programs.size(); i++) {
            BCP program = programs.get(i);
            System.out.println((i + 1) + ". Nome do Programa: " + program.getName());
            // Adicione mais linhas aqui se a classe BCP tiver mais informações que você queira imprimir.
        }
    }
*/

    private static List<BCP> loadPrograms(String path, LogFile logFile) {
        List<BCP> programs = new ArrayList<>();
        
        try {
            Files.list(Paths.get(path))
                 .filter(Files::isRegularFile)
                 .filter(file -> !file.getFileName().toString().equals("quantum.txt")) // Ignorando o arquivo quantum.txt
                 .map(Path::toFile)
                 .forEach(file -> {
                     try {
                         logFile.writeLog("Carregando " + file.getName());
                         BCP program = new BCP(file.getPath());
                         programs.add(program);
                     } catch (IOException e) {
                         logFile.writeLog("Erro ao carregar o programa " + file.getName() + ": " + e.getMessage());
                         e.printStackTrace();
                     }
                 });
        } catch (IOException e) {
            logFile.writeLog("Erro ao listar os arquivos no diretório " + path + ": " + e.getMessage());
            e.printStackTrace();
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
