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
    //boolean ativo = true; desnecessário pois está sempre a True, logo vai entrar sempre naquele if
    Localizacao loc;



    public Notificador(Socket s, DataInputStream dis, DataOutputStream dos, AlarmeCovid covid, Localizacao loc) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.covid = covid;
        this.loc = loc;
    }

    @Override
    public void run() {
        while(true) {
            //if (ativo)
             Contas[][] map = covid.getMap();
                /*for (int i = 0; i < N; i++) {
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
                } */
            if(map[loc.getLinha()][loc.getColuna()].getContas().size() == 0){
                try {
                    System.out.println("A posicao (" + loc.getLinha() + "," + loc.getColuna() + ") esta livre!");
                    dos.writeUTF("A posicao (" + loc.getLinha() + "," + loc.getColuna() + ") esta livre!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
