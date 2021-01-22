/*package alarmecovid;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Notificador implements Runnable {
    private AlarmeCovid covid;
    private int linha,col;
    private ReentrantLock lock= new ReentrantLock();
    private Condition cond = lock.newCondition();

    public Notificador(AlarmeCovid covid, int linha, int col) {
        this.covid = covid;
        this.linha = linha;
        this.col = col;
    }

    @Override
    public void run() {
        try{
            lock.lock();
            while(true){
                Contas[][] map = covid.getMap();
                while(map[linha][col].getContas().size() > 0) {
                    cond.await();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}*/
