package alarmecovid;

import java.io.IOException;
import java.util.List;
import java.util.function.LongUnaryOperator;

public interface AlarmeCovid {

    Conta login(String username, String password);
    Conta registo(String username, String password, Localizacao localizacao, boolean estadoSaude, List<String> contatos);
    Contas getContas() throws IOException;
    String print(Contas x);
    int getNrPessoas(int linha, int col) throws InterruptedException;

    String printMap();
    void isInfetado(Conta c);
    void mudaPosicao(Conta c, int x,int y);
    Contas[][] getMap();
    void await() throws InterruptedException;
    void signalAll();
    List<String> notifica(String user);
    void addNotificacoes(String user,Localizacao loc);
}