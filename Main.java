import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Caminho da pasta "programas" e log file
        String programsPath = "programas";
        LogFile logFile = new LogFile("log.txt");

        // Carregar os programas
        List<String> programs = loadPrograms(programsPath, logFile);

        // Lê o quantum do arquivo
        int quantum = readQuantum("programas/quantum.txt");
        if (quantum <= 0) {
            System.out.println("Quantum inválido. Finalizando programa.");
            return;
        }
        
        // Estatísticas
        int totalSwitches = 0;
        int totalInstructions = 0;

        // Executar os programas
        while (!programs.isEmpty()) {
            List<String> finishedPrograms = new ArrayList<>();
            for (String program : programs) {
                logFile.writeLog("Executando " + program);
                
                // Exemplo: executar o programa e interromper após 'quantum' instruções
                int instructionsExecuted = runProgram(program, quantum);
                totalInstructions += instructionsExecuted;
                
                // Checar se o programa terminou
                if (isProgramFinished(program)) {
                    logFile.writeLog(program + " terminado. X=x_value. Y=y_value");
                    finishedPrograms.add(program);
                } else {
                    logFile.writeLog("Interrompendo " + program + " após " + instructionsExecuted + " instruções");
                    totalSwitches++;
                }
            }
            programs.removeAll(finishedPrograms);  // Remover programas finalizados
        }
        
        // Estatísticas
        logFile.writeLog("MEDIA DE TROCAS: " + totalSwitches);
        logFile.writeLog("MEDIA DE INSTRUCOES: " + (double) totalInstructions / totalSwitches);
        logFile.writeLog("QUANTUM: " + quantum);
        
        // Ler e exibir o log
        logFile.readLog();
    }

    private static List<String> loadPrograms(String path, LogFile logFile) {
        // Implemente a lógica para carregar os nomes/detalhes dos programas na pasta
        // e escrever mensagens no log conforme necessário.
        return new ArrayList<>(); // Retorne a lista de programas
    }
    
    private static int runProgram(String program, int quantum) {
        // Implemente a lógica para executar o programa até o quantum ou até ele terminar.
        // Retorne o número de instruções executadas.
        return 0;
    }
    
    private static boolean isProgramFinished(String program) {
        // Implemente a lógica para verificar se o programa terminou.
        return false;
    }

    private static int readQuantum(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            // Lê a primeira linha do arquivo e converte para int
            return Integer.parseInt(br.readLine().trim());
        } catch (IOException | NumberFormatException e) {
            // Trata exceções de I/O e formatação do número
            e.printStackTrace();
            return -1;  // Retorna -1 para indicar erro na leitura/conversão
        }
    }
}
