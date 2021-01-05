package alarmecovid;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class CovidImpl implements AlarmeCovid {

    private final Contas contas;
    //private final Mapa<Integer,Integer,Contas> mapa;
    private final Contas[][] map;
    private int N;
    private ReentrantLock lock = new ReentrantLock();

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
        //this.mapa = new Mapa<>();

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
    public Conta registo(String username, String password,Localizacao localizacao,String estadoSaude) {
        lock.lock();
        try {
            Conta c = contas.getCliente(username);
            if (c == null) {
                c = new Conta(username, password, localizacao,estadoSaude);
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


    public int getNrPessoas(int linha,int col){
        int conta= 0;
        //conta = mapa.get(linha,col).getContas().size();
        conta = map[linha][col].getContas().size();

        return conta;
    }


    public String getLocalVazio(){
        StringBuilder locais = new StringBuilder();
        int i,j,k=0;
        for(i=0;i<N;i++)
            for(j=0;j<N;j++)
                if(map[i][j].getContas().size()==0) locais.append("(" + i + "," + j + ");\n");

        if(locais.toString().isEmpty()) return "Não há locais vazios";
        else return locais.toString();
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



    public String print(Contas x){
        StringBuilder send = new StringBuilder();
        for(Conta a : x.getContas().values()) {
            send.append(a.getNome()).append(" ").append(a.getPassword()).append("\n");


        }
        return send.toString();
    }
}
