package alarmecovid;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Notificador extends Thread{
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    final AlarmeCovid covid;
    int N=10;
    boolean ativo = true;


    public Notificador(Socket s, DataInputStream dis, DataOutputStream dos, AlarmeCovid covid) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.covid = covid;
    }

    @Override
    public void run() {
        while(true) {
            if (ativo) {
                Contas[][] map = covid.getMap();
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if (map[i][j].getContas().size() == 0) {
                            try {
                                System.out.println("A posicao (" + i + "," + j + ") esta livre!");
                                dos.writeUTF("A posicao (" + i + "," + j + ") esta livre!");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }



    }
}
