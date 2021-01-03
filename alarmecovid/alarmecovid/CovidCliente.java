package alarmecovid;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class CovidCliente {

    public static void main(String[] args) throws Exception {

        try
        {
            Scanner scn = new Scanner(System.in);

            InetAddress ip = InetAddress.getByName("localhost");
            Socket s = new Socket(ip, 12345);

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            while (true)
            {
                System.out.println(dis.readUTF());
                String tosend = scn.nextLine();
                dos.writeUTF(tosend);

                if(tosend.equals("Sair"))
                {
                    System.out.println("A fechar a conexao : " + s);
                    s.close();
                    System.out.println("Conexao fechada");
                    break;
                }

                String received = dis.readUTF();
                System.out.println(received);
            }

            scn.close();
            dis.close();
            dos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
