package alarmecovid;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class CovidHandler implements Runnable {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    final AlarmeCovid covid;
    private ReentrantLock lock = new ReentrantLock();
    private Condition cond= lock.newCondition();
    private Thread thread;
    Notificador notificador;

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
        boolean saudavel = true;
        while (saudavel) {
            try {
                if (!entrou) {
                    if (recebido != null) clearArray(recebido);
                    dos.writeUTF("Pretende efetuar registo ou login?\n" +
                            "Escreva Sair para terminar a conexao.");

                    recebido = dis.readUTF().split(":");
                    comando = recebido[0];

                    switch (comando) {
                        case "registo":
                            Localizacao localizacao = new Localizacao(Integer.parseInt(recebido[3]), Integer.parseInt(recebido[4]));
                            conta = covid.registo(recebido[1], recebido[2], localizacao,true, null);
                            //if( covid.getContas().getCliente(recebido[1]).getPassword().equals(recebido[2])) entrou= true;
                            if(conta!= null) {
                                entrou = true;
                                dos.writeUTF("Registo efetuado com sucesso!");
                            }
                            else dos.writeUTF("Utilizador já existe!");
                            break;

                        case "login":
                            if (covid.login(recebido[1], recebido[2]) != null) {
                                conta = covid.login(recebido[1], recebido[2]);
                                if(!covid.getContas().getContas().get(conta.getNome()).getSaude()) {
                                    entrou=false;
                                    dos.writeUTF("O utilizador está infetado!\n");
                                }
                                else{
                                    dos.writeUTF("login efetuado com sucesso!");
                                    entrou = true;
                                }
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
                    dos.writeUTF("1 - Saber Localizacao atual\n" +
                                "2:x:y - Saber quantas pessoas estao na localizacao (x,y)\n" +
                                "3:x:y - Mudar a posição para a localização (x,y)\n" +
                                "4 - Informar doenca\n" +
                                "5 - Logout\n" +
                                "6 - Notificacoes\n" +
                                "Escreva 'Sair' para encerrar o cliente\n") ;
                    recebido = dis.readUTF().split(":");
                    comando = recebido[0];

                    switch (comando){
                        case "1":
                            Localizacao localizacao = conta.getLocalizacao();
                            dos.writeUTF("A sua localizacao atual é:" + localizacao.getLinha() + "," + localizacao.getColuna());
                            break;
                        case "2":
                            int total = covid.getNrPessoas(Integer.parseInt(recebido[1]),Integer.parseInt(recebido[2]));
                            if(total > 0 ) {
                                notificador = new Notificador(conta.getNome(),covid,Integer.parseInt(recebido[1]),Integer.parseInt(recebido[2]),dos);
                                notificador.start();
                                dos.writeUTF("Existem " + total + " pessoas nas coordenadas (" + recebido[1] + "," + recebido[2] + ") !");
                            }
                            else  dos.writeUTF("Posição (" + recebido[1] +"," + recebido[2] + ") está vazia!");
                            break;
                        case "3":
                            covid.mudaPosicao(conta,Integer.parseInt(recebido[1]),Integer.parseInt(recebido[2]));
                            dos.writeUTF("done!");
                            break;
                        case "4":
                            covid.isInfetado(conta);
                            saudavel = false;
                            dos.writeUTF("Infetado");
                            break;
                        case "5":
                            conta = null;
                            entrou = false;
                            dos.writeUTF("done!");
                            break;
                        case "6":
                            List<String> not = covid.notifica(conta.getNome());
                            if(not != null) dos.writeUTF(not.toString());
                            else dos.writeUTF("Não há notificações para o utilizador!");
                            break;
                        default:
                            break;
                    }
                }
            } catch(IOException | InterruptedException e){
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