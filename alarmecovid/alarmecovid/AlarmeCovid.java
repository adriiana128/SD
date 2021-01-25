package alarmecovid;

import java.io.IOException;
import java.util.List;

public interface AlarmeCovid {

    Conta login(String username, String password);
    Conta registo(String username, String password, Localizacao localizacao, boolean estadoSaude);
    Contas getContas() throws IOException;
    int getNrPessoas(int linha, int col);
    void isInfetado(Conta c);
    void mudaPosicao(Conta c, int x,int y);
    Contas[][] getMap();
    void await() throws InterruptedException;
    void signalAll();

}