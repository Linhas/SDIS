/**
 * Created by Bernardo on 16/03/2016.
 */
public class Peer extends Thread {
    private Channel mc;

    public void run(){
        System.out.println("Started peer with thread id no: " + this.getId());

        mc.close();


    }

    public byte[] receive(){

        byte[] buffer = mc.receive();
        return buffer;
    }

    public void send (String message){
        mc.send(message);
    }
}
