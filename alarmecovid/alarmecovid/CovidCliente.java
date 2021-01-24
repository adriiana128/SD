package alarmecovid;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class CovidCliente {

    public static void main(String[] args) {

        try
        {
            Scanner scn = new Scanner(System.in);

            InetAddress ip = InetAddress.getByName("localhost");
            Socket s = new Socket(ip, 12345);

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            Thread t = new Thread(new Reader(s));
            t.start();

            while (true) {
                String tosend = scn.nextLine();
                dos.writeUTF(tosend);
                if(tosend.equals("Sair"))
                {
                    System.out.println("A fechar a conexao : " + s);
                    System.out.println("Conexao fechada");
                    break;
                }

            }

            scn.close();
            dis.close();
            dos.close();
            s.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
