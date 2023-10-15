import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class LogFile {
    private String filePath;
    private BufferedWriter writer;

    // Constructor
    public LogFile(String filePath) {
        this.filePath = filePath;
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to write a log message to the file
    public void writeLog(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { // true for append mode
            writer.write(message);
            writer.newLine(); // Add new line at the end of the message
        } catch (IOException e) {
            System.err.println("Error writing the log message: " + e.getMessage());
        }
    }

    // Method to read the log file
    public void readLog() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the log file: " + e.getMessage());
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