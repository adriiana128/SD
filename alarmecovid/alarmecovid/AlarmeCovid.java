package alarmecovid;

import java.io.IOException;

public interface AlarmeCovid {

    Conta login(String username, String password);
    Conta registo(String username, String password);
    Contas getContas() throws IOException;


}