package alarmecovid;

import java.security.KeyPair;
import java.util.Enumeration;

public class Conta {

    private String nome;
    private String password;
    private Localizacao localizacao;
    private String estadoSaude;


    Conta(){
        nome="";
        password="";
        localizacao = new Localizacao();
        estadoSaude = "Saudavel";
    }

    public Conta(String nome, String password,Localizacao localizacao,String estsaude) {
        this.nome = nome;
        this.password = password;
        this.localizacao = localizacao;
        this.estadoSaude = estsaude;
    }

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public void isInfetado(){
        this.estadoSaude = "Infetado";
    }
}