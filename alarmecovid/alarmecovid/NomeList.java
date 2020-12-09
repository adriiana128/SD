package alarmecovid;

import java.util.ArrayList;

public class NomeList {

    private ArrayList<String> list;

    public NomeList() {
        this.list = new ArrayList<>();
    }

    public synchronized boolean containsNome(String nome) {
        return this.list.contains(nome);
    }
    

    public synchronized boolean addNome(String nome) {
        return this.list.add(nome);
    }
    
    public synchronized void removeNome(String nome) {
         this.list.remove(nome);
    }
}