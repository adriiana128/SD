package alarmecovid;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Reader implements Runnable {
    private Socket s;
    private DataOutputStream dos;
    private DataInputStream dis;


    public Reader(Socket s) throws IOException {
        this.s = s;
        this.dos = new DataOutputStream(s.getOutputStream());
        this.dis = new DataInputStream(s.getInputStream());

    }

    @Override
    public void run() {
        while (true){
            try {
                System.out.println(dis.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
