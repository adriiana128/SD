package alarmecovid;

public class CovidImpl implements AlarmeCovid {

    private final Contas contas;


    public CovidImpl() {
        this.contas = new Contas();
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
    public Conta registo(String username, String password) {
        Conta c = contas.getCliente(username);
        if(c==null) {
            c = new Conta(username,password);
            contas.addCliente(c);
            return c;
        }
        else System.out.println("CovidCliente já existe!");
        return null;
    }

    public Contas getContas(){
        return this.contas;
    }
}
