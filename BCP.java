import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BCP {
    private String Nome;
    private int x;
    private int y;
    private int pc; // Program Counter
    private String estado; // PRONTO, EXECUTANDO, BLOQUEADO, TERMINADO
    private List<String> instrucoes; // Lista de instruções do processo
    private int TempoEspera; // Tempo de espera do processo (quando executa E/S tem que esperar por ex)

    public BCP(String filePath) throws IOException {
        this.instrucoes = new ArrayList<>();
        this.pc = 0;
        this.TempoEspera = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Pega o nome do programa na primeira linha do arquivo
            Nome = br.readLine().trim();

            // Extrai as informações das outras linhas (X, Y ou instrução)
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                instrucoes.add(line);
            }

            estado = "PRONTO"; // Define o estado do processo como pronto para executar
        } catch (IOException e) {
            throw new IOException("Erro ao ler o arquivo " + filePath, e);
        } catch (NumberFormatException e) {
            throw new IOException("Erro ao converter números no arquivo " + filePath, e);
        }
    }

    // Getters e Setters

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public  List<String>  getInstrucoes() {
        return instrucoes;
    }

    public void setInstrucoes( List<String>  instrucoes) {
        this.instrucoes = instrucoes;
    }

    public int getTempoEspera() {
        return TempoEspera;
    }

    public void setTempoEspera(int TempoEspera) {
        this.TempoEspera = TempoEspera;
    }

    public void decrementaTempoEspera(){
        this.TempoEspera--;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public void incrementaPc(){
        this.pc++;
    }

}