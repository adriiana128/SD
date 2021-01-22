package alarmecovid;

import java.util.List;
import java.util.Map;
import java.util.Set;
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
        lock.lock();
        try {
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
    public Conta registo(String username, String password, Localizacao localizacao, boolean estadoSaude, List<String> contatos) {
        lock.lock();
        try {
            Conta c = contas.getCliente(username);
            if (c == null) {
                c = new Conta(username, password, localizacao,estadoSaude,contatos);
                contas.addCliente(c);
                map[localizacao.getLinha()][localizacao.getColuna()].addCliente(c);
                return c;
            } else System.out.println("CovidCliente já existe!");
            return null;
        }
        finally {
            lock.unlock();
        }
    }

    public Contas getContas(){
        return this.contas;
    }


    public int getNrPessoas(int linha,int col) throws InterruptedException {
            try{
                lock.lock();
                while (map[linha][col].getContas().size() > 0) {
                    cond.await();
                }
                return map[linha][col].getContas().size();
            }finally {
                lock.unlock();
            }
    }

    public void isInfetado(Conta c){
        this.contas.getCliente(c.getNome()).isInfetado();
    }

    public String printMap() {
        StringBuilder send = new StringBuilder();
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                Map<String,Conta> contas = this.map[i][j].getContas();
                for(Conta x: contas.values())
                    send.append(x.getNome() + "(" + x.getLocalizacao().getLinha() + "," + x.getLocalizacao().getColuna() + ");\n");
            }
        return send.toString();
    }

    public void mudaPosicao(Conta c,int x,int y){

        try{
            lock.lock();
            map[c.getLocalizacao().getLinha()][c.getLocalizacao().getColuna()].getContas().remove(c.getNome());
            c.setLocalizacao(new Localizacao(x,y));
            map[c.getLocalizacao().getLinha()][c.getLocalizacao().getColuna()].getContas().put(c.getNome(),c);
            cond.signalAll();

        }
        finally {
            lock.unlock();
        }


    }

    public Contas[][] getMap() {
        return map;
    }

    public String print(Contas x){
        StringBuilder send = new StringBuilder();
        for(Conta a : x.getContas().values()) {
            send.append(a.getNome()).append(" ").append(a.getPassword()).append("\n");


        }
        return send.toString();
    }
}
