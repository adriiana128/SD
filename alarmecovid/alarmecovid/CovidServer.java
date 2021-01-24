package alarmecovid;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CovidServer {


    public static void main(String[] args) throws IOException {
        int N =10;
        Contas[][] map = initMap(N);
        AlarmeCovid covid = new CovidImpl(map,N);
        ServerSocket ss = new ServerSocket(12345);

        while(true){
            Socket s = null;
            try{
                s = ss.accept();
                System.out.println("Novo cliente conectado: " + s);

                System.out.println("A dar uma nova thread ao cliente");

                Thread t = new Thread(new CovidHandler(s,covid));

                t.start();
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }

    private static Contas[][] initMap(int n) {
        Contas[][] map = new Contas[n][n];
        for(int i =0;i<n;i++)
            for(int j =0 ; j<n ; j++)
                map[i][j] = new Contas();

            return map;
    }

}



