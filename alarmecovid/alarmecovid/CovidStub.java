package alarmecovid;

import java.io.*;
import java.net.Socket;

public class CovidStub implements AlarmeCovid {
    private final Socket s;
    private final DataOutputStream dos;
    private final DataInputStream dis;

    public CovidStub() throws IOException{
        this.s = new Socket("localhost",12345);
        this.dos = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
        this.dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
    }
    @Override
    public Conta login(String username, String password) {
        try{
            dos.writeUTF("login");
            dos.writeUTF(username);
            dos.writeUTF(password);
            dos.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Conta registo(String username, String password) {
        try{
            dos.writeUTF("registo");
            dos.writeUTF(username);
            dos.writeUTF(password);
            dos.flush();
            dis.readUTF();
            return new Conta(dis.readUTF(),dis.readUTF());
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Contas getContas() throws IOException {
        String user = dis.readUTF();
        String password = dis.readUTF();
        System.out.println(user+"    cliente  "+ password);
        return null;
    }
}
