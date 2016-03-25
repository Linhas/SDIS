/**
 * Created by Bernardo on 25/03/2016.
 */
public class Backup extends Thread{
    String message;

    public Backup(byte[] message){
        this.message = new String(message);
    }

    public void run() {
        System.out.println(this.message);
    }
}
