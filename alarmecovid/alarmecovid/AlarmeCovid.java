package alarmecovid;

import java.io.IOException;

public interface AlarmeCovid {

    Conta login(String username, String password);
    Conta registo(String username, String password,Localizacao localizacao,String estadoSaude);
    Contas getContas() throws IOException;
    String print(Contas x);
    int getNrPessoas(int linha, int col);
    String getLocalVazio();
    String printMap();
    void isInfetado(Conta c);


}