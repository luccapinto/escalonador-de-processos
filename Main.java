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
        String caminhoProgramas = "programas";
        LogFile logFile = new LogFile("log.txt");

        // Carregar os programas
        List<BCP> programas = carregaProgramas(caminhoProgramas, logFile);
        // Ler o quantum
        int quantum = extraiQuantum("programas/quantum.txt");
        if (quantum <= 0) {
            System.out.println("Quantum inválido. Finalizando programa.");
            return;
        }
        // Testando se todos programas estão sendo carregados (descomente a função se for usar)
        // printprogramas(programas);
        
        // Cria um escalonado com a lista de programas, quantum e o caminho do log
        Escalonador escalonador = new Escalonador(programas, quantum, logFile);

        // Executa o escalonador
        escalonador.executaEscalonador();
/*
        // Gera múltiplos logs de uma vez para múltipl0s quantums
        int multiplo = 30; // coloca o numero de logs que você quer que sejam gerados
        for (quantum = 1; quantum <= multiplo; quantum++){
            LogFile multiplosLogFile = new LogFile("logfiles/log" + quantum + ".txt");
            List<BCP> multiplosprogramas = carregaProgramas(caminhoProgramas, logFile);
            Escalonador multiplosProcessos = new Escalonador(multiplosprogramas, quantum, multiplosLogFile);
            multiplosProcessos.executaEscalonador();
        }
*/
    }
/* 
    // Teste para ver se todos os programas estão sendo carregados
    public static void printprogramas(List<BCP> programas) {
        if (programas.isEmpty()) {
            System.out.println("Nenhum programa carregado.");
            return;
        }
        
        System.out.println("Programas carregados:");
        for (int i = 0; i < programas.size(); i++) {
            BCP programa = programas.get(i);
            System.out.println((i + 1) + ". Nome do Programa: " + programa.getName());
            // Adicione mais linhas aqui se a classe BCP tiver mais informações que você queira imprimir.
        }
    }
*/

    //Carrega cada programa e devolve a lista de programas
    private static List<BCP> carregaProgramas(String path, LogFile logFile) {
        List<BCP> programas = new ArrayList<>();
        
        try {
            Files.list(Paths.get(path))
                 .filter(Files::isRegularFile)
                 .filter(file -> !file.getFileName().toString().equals("quantum.txt")) // Ignorando o arquivo quantum.txt
                 .map(Path::toFile)
                 .forEach(file -> { //Adicionando cada arguivo a lista de programas
                     try {
                         BCP programa = new BCP(file.getPath());
                         programas.add(programa);
                     } catch (IOException e) {
                         logFile.escreveLog("Erro ao carregar o programa " + file.getName() + ": " + e.getMessage());
                         e.printStackTrace();
                     }
                 });
        } catch (IOException e) {
            logFile.escreveLog("Erro ao listar os arquivos no diretório " + path + ": " + e.getMessage());
            e.printStackTrace();
        }
        return programas;
    }
    
    // Ler o quantum no arquivo e retornar seu valor
    private static int extraiQuantum(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return Integer.parseInt(br.readLine().trim());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return -1;  
        }
    }
}
