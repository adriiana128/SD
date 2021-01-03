package alarmecovid;

public class Localizacao {
    private int linha;
    private int coluna;

    public Localizacao() {
        this.linha = -1;
        this.coluna = -1;
    }

    public Localizacao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }


}
