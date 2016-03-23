/**
 * Created by Bernardo on 16/03/2016.
 */
public class Peer extends Thread {
    private Channel mc;

    public void run(){
        System.out.println("Started peer with thread id no: " + this.getId());
        //subscribe to multicast group
        byte [] buf = null;

        mc = new Channel("Control", "224.0.0.0", 4446);
        if ((this.getId() >= 11) && (this.getId() <= 13)){
            while (!(new String(buf).equals("END"))){
            System.out.println("receiving...");
            buf = this.receive();
            System.out.println(new String(buf));}
        }
        else {
            if (this.getId() == 14)
            try {
                this.sleep(4000);
                System.out.println("sending");
                this.send("hello");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            else if (this.getId() == 15)
                try {
                    this.sleep(6000);
                    System.out.println("sending");
                    this.send("world");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            else if (this.getId() == 16)
                try {
                    this.sleep(8000);
                    System.out.println("sending");
                    this.send("END");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }

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
