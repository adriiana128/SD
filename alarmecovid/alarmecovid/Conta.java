package alarmecovid;

import java.security.KeyPair;

public class Conta {

    private String nome;
    private String password;
    private Localizacao localizacao;


    Conta(){
        nome="";
        password="";
        localizacao = new Localizacao();
    }

    public Conta(String nome, String password,Localizacao localizacao) {
        this.nome = nome;
        this.password = password;
        this.localizacao = localizacao;
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
}