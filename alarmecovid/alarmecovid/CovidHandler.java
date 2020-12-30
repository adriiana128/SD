package alarmecovid;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CovidHandler extends Thread {
    AlarmeCovid covid = new CovidImpl();
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;


    // Constructor
    public CovidHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        String[] recebido;
        String comando;
        String devolver;
        while (true) {
            try {

                // Ask user what he wants
                dos.writeUTF("Pretende efetuar registo ou login?\n" +
                        "Escreva Sair para terminar a conexao.");

                // receive the answer from client
                recebido = dis.readUTF().split(":");
                comando = recebido[0];

                if (comando.equals("Sair")) {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }


                // write on output stream based on the
                // answer from the client
                switch (comando) {
                    case "registo":
                        covid.registo(recebido[1],recebido[2]);
                        dos.writeUTF(covid.print(covid.getContas()));
                        break;

                    case "login":
                        //devolver =;
                        //dos.writeUTF(devolver);
                        break;

                    default:
                        dos.writeUTF("Invalid input");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // closing resources
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}