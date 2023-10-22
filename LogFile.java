import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogFile {
    private String caminhoArquivo;

    public LogFile(String caminhoArquivo) { // Cria um novo arquivo de log em vez de somente ficar adicionando novas linhas
        this.caminhoArquivo = caminhoArquivo;
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(caminhoArquivo));
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
    public void escreveLog(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) { // true for append mode
            writer.write(message);
            writer.newLine(); // Add new line at the end of the message
        } catch (IOException e) {
            System.err.println("Error writing the log message: " + e.getMessage());
        }
    }

    // Getters and Setters
    public String getcaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setcaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }
}