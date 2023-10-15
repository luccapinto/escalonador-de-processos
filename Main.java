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
        List<String> programs = loadPrograms(programsPath, logFile);
        
        int quantum = readQuantum("programas/quantum.txt");
        if (quantum <= 0) {
            System.out.println("Quantum inválido. Finalizando programa.");
            return;
        }

        int totalSwitches = 0;
        int totalInstructions = 0;

        while (!programs.isEmpty()) {
            List<String> finishedPrograms = new ArrayList<>();
            for (String program : programs) {
                logFile.writeLog("Executando " + program);
                
                int instructionsExecuted = runProgram(program, quantum);
                totalInstructions += instructionsExecuted;
                
                if (isProgramFinished(program)) {
                    logFile.writeLog(program + " terminado. X=x_value. Y=y_value");
                    finishedPrograms.add(program);
                } else {
                    logFile.writeLog("Interrompendo " + program + " após " + instructionsExecuted + " instruções");
                    totalSwitches++;
                }
            }
            programs.removeAll(finishedPrograms);
        }
        
        logFile.writeLog("MEDIA DE TROCAS: " + totalSwitches);
        if(totalSwitches != 0) {
            logFile.writeLog("MEDIA DE INSTRUCOES: " + (double) totalInstructions / totalSwitches);
        } else {
            logFile.writeLog("MEDIA DE INSTRUCOES: NaN");
        }
        logFile.writeLog("QUANTUM: " + quantum);
        
        logFile.readLog();
    }

    private static List<String> loadPrograms(String path, LogFile logFile) {
        List<String> programs = new ArrayList<>();
        try {
            Files.list(Paths.get(path))
                 .filter(Files::isRegularFile)
                 .map(Path::toFile)
                 .forEach(file -> {
                     logFile.writeLog("Carregando " + file.getName());
                     programs.add(file.getName().replace(".txt", ""));
                 });
        } catch (IOException e) {
            e.printStackTrace();
            // Trate o erro conforme a necessidade do seu aplicativo.
        }
        return programs;
    }
    
    private static int runProgram(String program, int quantum) {
        // Mocked execution logic. Replace with actual logic for running programs.
        int instructionsExecuted = (int)(Math.random() * quantum) + 1; // Random number from 1 to quantum
        return instructionsExecuted;
    }
    
    private static boolean isProgramFinished(String program) {
        // Mocked finish check logic. Replace with actual logic for checking program finish state.
        return Math.random() > 0.5; // Randomly finishes or not
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
