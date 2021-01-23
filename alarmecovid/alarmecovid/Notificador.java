package alarmecovid;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Notificador extends Thread {
    private String user;
    private AlarmeCovid covid;
    private int linha, col;
    private ReentrantLock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();
    private DataOutputStream dos;
    private boolean ocupado = true;

    public Notificador(String user, AlarmeCovid covid, int linha, int col, DataOutputStream dos) {
        this.user = user;
        this.covid = covid;
        this.linha = linha;
        this.col = col;
        this.dos = dos;
    }

    @Override
    public void run() {
        try {
            while (ocupado) {
                Contas[][] map = covid.getMap();
                while (map[linha][col].getContas().size() > 0) {
                    covid.await();
                }
                covid.addNotificacoes(user,new Localizacao(linha,col));
                ocupado = false;

            }
        } catch (InterruptedException  e) {
            e.printStackTrace();
        }
    }
}
