package alarmecovid;

public class AlarmeCovid {

    public static void main(String[] args) {
        try{
        System.out.println("Alarme Covid");
        Cliente cliente = new Cliente();
        cliente.getInputsDoThings();
        }catch(Exception e){
            System.out.println("Erro!");
        }
    }
}