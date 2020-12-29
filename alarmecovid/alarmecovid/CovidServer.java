package alarmecovid;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class CovidServer {


    static void print(Contas x){

        for(Conta a : x.getContas().values())
            System.out.println(a.getNome() + "///" +a.getPassword());

    }

    public static void main(String[] args) throws IOException {
        AlarmeCovid covid = new CovidImpl();
        ServerSocket ss = new ServerSocket(12345);

        while(true){
            try(var s = ss.accept()){
                var dos = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
                var dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));

                while(true){
                    switch(dis.readUTF()){
                        case "login":
                        case "registo":
                            String username = dis.readUTF();
                            String password = dis.readUTF();
                            covid.registo(username,password);
                            dos.flush();
                            break;

                    }
                }
            }
        }

    }


}
