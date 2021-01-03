package alarmecovid;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class CovidHandler extends Thread {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    final AlarmeCovid covid;


    public CovidHandler(Socket s, DataInputStream dis, DataOutputStream dos, AlarmeCovid covid) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.covid = covid;

    }

    private void clearArray(String[] array){
        Arrays.fill(array, null);
    }

    @Override
    public void run() {
        String[] recebido = null;
        String comando;
        Conta conta = null;
        boolean entrou = false;
        while (true) {
            try {
                if (!entrou) {
                    if (recebido != null) clearArray(recebido);
                    dos.writeUTF("Pretende efetuar registo ou login?\n" +
                            "Escreva Sair para terminar a conexao.");

                    recebido = dis.readUTF().split(":");
                    comando = recebido[0];

                    if (comando.equals("Sair")) {
                        System.out.println("Cliente" + this.s + " sai...");
                        System.out.println("A fechar a conexao.");
                        this.s.close();
                        System.out.println("Conexao fechada");
                        break;
                    }
                    switch (comando) {
                        case "registo":
                            Localizacao localizacao = new Localizacao(Integer.parseInt(recebido[3]), Integer.parseInt(recebido[4]));
                            conta = covid.registo(recebido[1], recebido[2], localizacao);
                            if( covid.getContas().getCliente(recebido[1]).getPassword().equals(recebido[2])) entrou= true;
                            dos.writeUTF(covid.print(covid.getContas()));
                            break;

                        case "login":
                            conta = covid.login(recebido[1], recebido[2]);
                            if (covid.login(recebido[1], recebido[2]) != null) {
                                dos.writeUTF("login efetuado com sucesso!");
                                entrou = true;
                            }
                            else dos.writeUTF("Credenciais de acesso invalidas!");
                            break;

                        default:
                            dos.writeUTF("Input invalido");
                            break;
                    }
                }
                else{
                    clearArray(recebido);
                    dos.writeUTF("1-Saber Localizacao atual\n" +
                                "2:x:y - Saber quantas pessoas estao na localizacao tal\n" +
                                "3 - Saber localizacao vazia\n" +
                                "4 - Informar doenca\n" +
                                "5 - logout\n" +
                                "6 - Fechar ligacao ao servidor\n");
                    recebido = dis.readUTF().split(":");
                    comando = recebido[0];

                    switch (comando){
                        case "1":
                            Localizacao localizacao = conta.getLocalizacao();
                            dos.writeUTF("A sua localizacao atual é:" + localizacao.getLinha() + "," + localizacao.getColuna());
                            break;
                        case "2":
                            dos.writeUTF(recebido[1] + " " + recebido[2]);
                            int total = covid.getNrPessoas(Integer.parseInt(recebido[1]),Integer.parseInt(recebido[2]));
                            dos.writeUTF("Existem " + total + " pessoas nas coordenadas (" + recebido[1] + "," + recebido[2] + ") !");
                            break;
                        case "3":
                            break;
                        case "4":
                            break;
                        case "5":
                            conta = null;
                            entrou = false;
                            break;
                        case "6":
                            break;
                        default:
                            break;
                    }
                }
            } catch(IOException e){
                e.printStackTrace();
            }

        }

        try {
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}