package alarmecovid;

import java.util.Map;

public class CovidImpl implements AlarmeCovid {

    private final Contas contas;
    private final Mapa<Integer,Integer,Contas> mapa;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CovidImpl{");
        sb.append("contas=").append(contas);
        sb.append('}');
        return sb.toString();
    }

    public CovidImpl() {
        this.contas = new Contas();
        this.mapa = new Mapa<>();
    }

    @Override
    public Conta login(String username, String password) {
        Conta c = contas.getCliente(username);
        if(c!= null) {
            if (c.getPassword().equals(password)) return c;
            else System.out.println("Password inválida!");
        }
        else System.out.println("CovidCliente inexistente!");
        return null;
    }

    @Override
    public Conta registo(String username, String password,Localizacao localizacao) {
        Conta c = contas.getCliente(username);
        if(c==null) {
            c = new Conta(username,password,localizacao);
            contas.addCliente(c);
            mapa.put(localizacao.getLinha(),localizacao.getColuna(),contas);
            return c;
        }
        else System.out.println("CovidCliente já existe!");
        return null;
    }

    public Contas getContas(){
        return this.contas;
    }


    public int getNrPessoas(int linha,int col){
        int conta= 0;
        conta = mapa.get(linha,col).getContas().size();

        return conta;
    }

    public String print(Contas x){
        StringBuilder send = new StringBuilder();
        for(Conta a : x.getContas().values()) {
            send.append(a.getNome()).append(" ").append(a.getPassword()).append("\n");


        }
        return send.toString();
    }
}
