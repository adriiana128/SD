package alarmecovid;

import java.security.KeyPair;
import java.util.Enumeration;
import java.util.List;

public class Conta {

    private String nome;
    private String password;
    private Localizacao localizacao;
    private boolean saudavel;
    private List<String> contatos;


    Conta(){
        nome="";
        password="";
        localizacao = new Localizacao();
        saudavel = true;
        contatos = null;
    }

    public Conta(String nome, String password,Localizacao localizacao,boolean estsaude, List<String> contatos) {
        this.nome = nome;
        this.password = password;
        this.localizacao = localizacao;
        this.saudavel = estsaude;
        this.contatos = contatos;
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
        this.saudavel = false;
    }

    public void setContatos(List<String> contatos) { this.contatos = contatos; }

    public List<String> getContatos() { return contatos; }
}