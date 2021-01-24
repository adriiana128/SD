package alarmecovid;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Contas {
        private HashMap<String, Conta> clientes;
        private final ReentrantLock lock;
    
        public Contas() {
            this.lock = new ReentrantLock();
            this.clientes = new HashMap<>();
        }
    
        public void addCliente(Conta conta) {
            clientes.put(conta.getNome(), conta);
        }
    
        public Conta getCliente(String s) {
            return this.clientes.get(s);
        }


        Map<String, Conta> getContas(){
            return this.clientes;
        }

}