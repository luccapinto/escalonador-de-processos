import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogFile {
    private String filePath;

    public LogFile(String filePath) { // Cria um novo arquivo de log em vez de somente ficar adicionando novas linhas
        this.filePath = filePath;
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
            // File is created
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Escritor de logs - Esse sim adiciona novas linhas
    public void writeLog(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { // true for append mode
            writer.write(message);
            writer.newLine(); // Add new line at the end of the message
        } catch (IOException e) {
            System.err.println("Error writing the log message: " + e.getMessage());
        }
    }

    // Getters and Setters
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}