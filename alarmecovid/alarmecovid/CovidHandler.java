package alarmecovid;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class CovidHandler implements Runnable {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    final AlarmeCovid covid;
    private ReentrantLock lock = new ReentrantLock();
    private Condition cond= lock.newCondition();
    Notificador notificador;
    int N =10;

    public CovidHandler(Socket s, AlarmeCovid covid) throws IOException {
        this.s = s;
        this.dos = new DataOutputStream(s.getOutputStream());
        this.dis = new DataInputStream(s.getInputStream());
        this.covid = covid;

    }

    private void clearArray(String[] array){
        Arrays.fill(array, null);
    }

    public void run() {
        String[] recebido = null;
        String comando;
        Conta conta = null;
        boolean entrou = false;
        boolean keepgoing = true;
        while (keepgoing) {
            try {
                dos.flush();
                if (!entrou) {
                    if (recebido != null) clearArray(recebido);
                    dos.writeUTF("Pretende efetuar registo ou login?\n" +
                            "Escreva Sair para terminar a conexao.");

                    recebido = dis.readUTF().split(":");
                    comando = recebido[0];

                    switch (comando) {
                        case "registo":
                            if(Integer.parseInt(recebido[3]) > N || Integer.parseInt(recebido[4]) > N){
                                dos.writeUTF("Posição não existe no mapa");
                            }
                            else {
                                Localizacao localizacao = new Localizacao(Integer.parseInt(recebido[3]), Integer.parseInt(recebido[4]));
                                conta = covid.registo(recebido[1], recebido[2], localizacao, true, null);
                                if (conta != null) {
                                    entrou = true;
                                    dos.writeUTF("Registo efetuado com sucesso!");
                                } else dos.writeUTF("Utilizador já existe!");
                            }
                            break;

                        case "login":
                            if (covid.login(recebido[1], recebido[2]) != null) {
                                conta = covid.login(recebido[1], recebido[2]);
                                if(!covid.getContas().getContas().get(conta.getNome()).getSaude()) {
                                    dos.writeUTF("O utilizador está infetado!\n");
                                }
                                else{
                                    dos.writeUTF("login efetuado com sucesso!");
                                    entrou = true;
                                }
                            }
                            else dos.writeUTF("Credenciais de acesso invalidas!");
                            break;
                        case "Sair":
                            keepgoing=false;
                            System.out.println("Cliente " + s + " encerrado!");
                            break;
                        default:
                            dos.writeUTF("Input invalido");
                            break;
                    }
                }
                else{
                    clearArray(recebido);
                    dos.writeUTF("1 - Saber Localizacao atual\n" +
                            "2:x:y - Saber quantas pessoas estão na localização (x,y)\n" +
                            "3:x:y - Saber quando não houver ninguém na localizacao (x,y)\n" +
                            "4:x:y - Mudar a posição para a localização (x,y)\n" +
                            "5 - Informar doenca\n" +
                            "6 - Logout\n" +
                            "Escreva 'Sair' para encerrar o cliente\n") ;
                    recebido = dis.readUTF().split(":");
                    comando = recebido[0];

                    switch (comando){
                        case "1":
                            Localizacao localizacao = conta.getLocalizacao();
                            dos.writeUTF("A sua localizacao atual é: (" + localizacao.getLinha() + "," + localizacao.getColuna() + ")");
                            break;
                        case "2":
                            if(Integer.parseInt(recebido[1]) > N || Integer.parseInt(recebido[2]) > N){
                                dos.writeUTF("Posição não existe no mapa");
                            }
                            else {
                                int totalP = covid.getNrPessoas(Integer.parseInt(recebido[1]), Integer.parseInt(recebido[2]));
                                dos.writeUTF("Existem " + totalP + " pessoas nas coordenadas (" + recebido[1] + "," + recebido[2] + ") !");
                            }
                            break;
                        case "3":
                            if(Integer.parseInt(recebido[1]) > N || Integer.parseInt(recebido[2]) > N){
                                dos.writeUTF("Posição não existe no mapa");
                            }
                            else {
                                int total = covid.getNrPessoas(Integer.parseInt(recebido[1]), Integer.parseInt(recebido[2]));
                                if (total > 0) {
                                    notificador = new Notificador(conta.getNome(), covid, Integer.parseInt(recebido[1]), Integer.parseInt(recebido[2]), dos);
                                    notificador.start();
                                    dos.writeUTF("Existem " + total + " pessoas nas coordenadas (" + recebido[1] + "," + recebido[2] + ") !");
                                } else dos.writeUTF("Posição (" + recebido[1] + "," + recebido[2] + ") está vazia!");
                            }
                            break;
                        case "4":
                            if(Integer.parseInt(recebido[1]) > N || Integer.parseInt(recebido[2]) > N){
                                dos.writeUTF("Posição não existe no mapa");
                            }
                            else{
                                covid.mudaPosicao(conta,Integer.parseInt(recebido[1]),Integer.parseInt(recebido[2]));
                                dos.writeUTF("done!");
                            }
                            break;
                        case "5":
                            covid.isInfetado(conta);
                            conta = null;
                            entrou=false;
                            dos.writeUTF("Infetado");
                            break;
                        case "6":
                            conta = null;
                            entrou = false;
                            dos.writeUTF("done!");
                            break;
                        case "Sair":
                            keepgoing=false;
                            System.out.println("Cliente " + s + " encerrado!");
                            break;
                        default:
                            break;
                    }
                }
            } catch(IOException e){
                e.printStackTrace();
            }

        }

    }
}