package alarmecovid;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class CovidServer {


    public static void main(String[] args) throws IOException {
        AlarmeCovid covid = new CovidImpl();
        ServerSocket ss = new ServerSocket(12345);


        while(true){
            Socket s = null;
            try{
                s = ss.accept();
                System.out.println("Novo cliente conectado: " + s);
                var dos = new DataOutputStream(s.getOutputStream());
                var dis = new DataInputStream(s.getInputStream());

                System.out.println("A dar uma nova thread ao cliente");

                Thread t = new CovidHandler(s,dis,dos,covid);

                t.start();
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
                }
            }
        }

}



