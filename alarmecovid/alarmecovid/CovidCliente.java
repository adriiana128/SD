package alarmecovid;

import java.io.*;
import java.net.Socket;


public class CovidCliente {
    static void print(Contas x){

        for(Conta a : x.getContas().values())
            System.out.println(a.getNome() + "///" +a.getPassword());

    }

    public static void main(String[] args) throws Exception {
        AlarmeCovid covid = new CovidStub();

        covid.registo("userteste","password");

        covid.getContas();
    }
}
