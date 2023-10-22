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
        // Ler o quantum
        int quantum = readQuantum("programas/quantum.txt");
        if (quantum <= 0) {
            System.out.println("Quantum inválido. Finalizando programa.");
            return;
        }
        // Testando se todos programas estão sendo carregados (descomente a função se for usar)
        // printPrograms(programs);
        
        // Cria um escalonado com a lista de programas, quantum e o caminho do log
        Scheduler processes = new Scheduler(programs, quantum, logFile);

        // Executa o escalonador
        processes.executeProcesses();
    }
/* 
    // Teste para ver se todos os programas estão sendo carregados
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

    //Carrega cada programa e devolve a lista de programas
    private static List<BCP> loadPrograms(String path, LogFile logFile) {
        List<BCP> programs = new ArrayList<>();
        
        try {
            Files.list(Paths.get(path))
                 .filter(Files::isRegularFile)
                 .filter(file -> !file.getFileName().toString().equals("quantum.txt")) // Ignorando o arquivo quantum.txt
                 .map(Path::toFile)
                 .forEach(file -> { //Adicionando cada arguivo a lista de programas
                     try {
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
    
    // Ler o quantum no arquivo e retornar seu valor
    private static int readQuantum(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return Integer.parseInt(br.readLine().trim());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return -1;  
        }
    }
}
