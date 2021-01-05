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

        Conta registo (String username, String password,Localizacao localizacao,String estSaude)  {
            lock.lock();
            try{
                Conta c = clientes.get(username);

                if(c==null){
                    c = new Conta(username,password,localizacao,estSaude);
                    clientes.put(username,c);

                    return c;
                }
                else {
                    System.out.println("Conta existe!");
                }
            }
            finally {
                lock.unlock();
            }
            return null;
        }

        Conta login(String username, String password){
            lock.lock();

            try{
            Conta c = clientes.get(username);

                if(c!=null){
                    if(c.getPassword().equals(password))
                        return c;
                    else{
                        System.out.println("Password invalida");
                    }
                }
                else{
                    System.out.println("Conta nao existe");
                }
            }
            finally {
                lock.unlock();
            }
            return null;
        }

        Map<String, Conta> getContas(){
            return this.clientes;
        }

}