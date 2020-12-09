package alarmecovid;

import java.util.HashMap;

public class ContasMap {
    
        private HashMap<String, Conta> contas;
    
        public ContasMap() {
            this.contas = new HashMap<>();
        }
    
        public synchronized boolean isAccountNome(String nome) {
            return contas.containsKey(nome);
        }
    
        public synchronized void addConta(Conta conta) {
            contas.put(conta.getNome(), conta);
        }
    
        public synchronized Conta getConta(String s) {
            return this.contas.get(s);
        }
    
        public synchronized boolean isValidPassword(String nome, String password) {
            return contas.get(nome).getPassword().equals(password);
        }
}