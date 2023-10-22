import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BCP {
    private String name;
    private int x;
    private int y;
    private int pc; // Program Counter
    private String state; // PRONTO, EXECUTANDO, BLOQUEADO, TERMINADO
    private List<String> instructions; // Lista de instruções do processo
    private int waitTime; // Tempo de espera do processo (quando executa E/S tem que esperar por ex)

    public BCP(String filePath) throws IOException {
        this.instructions = new ArrayList<>();
        this.pc = 0;
        this.waitTime = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Pega o nome do programa na primeira linha do arquivo
            name = br.readLine().trim();

            // Extrai as informações das outras linhas (X, Y ou instrução)
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("X=")) {
                    x = Integer.parseInt(line.split("=")[1]);
                } else if (line.startsWith("Y=")) {
                    y = Integer.parseInt(line.split("=")[1]);
                } else {
                    instructions.add(line);
                }
            }

            state = "PRONTO"; // Define o estado do processo como pronto para executar
        } catch (IOException e) {
            throw new IOException("Erro ao ler o arquivo " + filePath, e);
        } catch (NumberFormatException e) {
            throw new IOException("Erro ao converter números no arquivo " + filePath, e);
        }
    }

    // Getters e Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public  List<String>  getInstructions() {
        return instructions;
    }

    public void setInstructions( List<String>  instructions) {
        this.instructions = instructions;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public void decreaseWaitTime(){
        this.waitTime--;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public void incrementPc(){
        this.pc++;
    }

}