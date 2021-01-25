package alarmecovid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class CovidImpl implements AlarmeCovid {

    private final Contas contas;
    private final Contas[][] map;
    private int N;
    private ReentrantLock lock = new ReentrantLock();
    private Condition cond= lock.newCondition();



    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CovidImpl{");
        sb.append("contas=").append(contas);
        sb.append('}');
        return sb.toString();
    }

    public CovidImpl(Contas[][] map,int N) {
        this.map = map;
        this.contas = new Contas();
        this.N = N;

    }

    @Override
    public Conta login(String username, String password) {
        try {
            lock.lock();
            Conta c = contas.getCliente(username);
            if (c != null) {
                if (c.getPassword().equals(password)) return c;
                else System.out.println("Password inválida!");
            } else System.out.println("CovidCliente inexistente!");
            return null;
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public Conta registo(String username, String password, Localizacao localizacao, boolean estadoSaude) {
        try {
            lock.lock();
            Conta c = contas.getCliente(username);
            if (c == null) {
                c = new Conta(username, password, localizacao,estadoSaude);
                contas.addCliente(c);
                map[localizacao.getLinha()][localizacao.getColuna()].addCliente(c);
                return c;
            } else return null;
        }
        finally {
            lock.unlock();
        }
    }

    public Contas getContas(){
        try {
            lock.lock();
            return this.contas;
        }
        finally {
            lock.unlock();
        }
    }


    public int getNrPessoas(int linha,int col) {
            try{
                lock.lock();
                return map[linha][col].getContas().size();
            }finally {
                lock.unlock();
            }
    }

    public void isInfetado(Conta c){
        try{
            lock.lock();
            this.contas.getCliente(c.getNome()).isInfetado();
        }
        finally{
            lock.unlock();
        }
    }


    public void mudaPosicao(Conta c,int x,int y){

        try{
            lock.lock();
            map[c.getLocalizacao().getLinha()][c.getLocalizacao().getColuna()].getContas().remove(c.getNome());
            c.setLocalizacao(new Localizacao(x,y));
            map[c.getLocalizacao().getLinha()][c.getLocalizacao().getColuna()].getContas().put(c.getNome(),c);
            signalAll();

        }
        finally {
            lock.unlock();
        }
    }


    public void await() throws InterruptedException {
        try {
            lock.lock();
            cond.await();
        }
        finally {
            lock.unlock();
        }
    }

    public void signalAll(){
        try {
            lock.lock();
            cond.signalAll();

        }
        finally {
            lock.unlock();
        }
    }


    public Contas[][] getMap() {
        try{
            lock.lock();
            return map;
        }
        finally {
            lock.unlock();
        }
    }


}

