/**
 * Created by Bernardo on 24/03/2016.
 */
public class Listener extends Thread {
    private Channel channel;
    private String name;

    public Listener(String name, String ip, Integer port){
        this.name = name;
        channel = new Channel(ip, port);
    }

    public void run(){
        byte[] msg;
        System.out.println(name + " Channel is listening on IP: " + channel.getAddress() + " and in Port: " + channel.getPort());

        while(true){
            msg = channel.receive();
            if (msg != null){
                if (name.equals("Control"))
                    //TODO: Control
                    ;
                else if (name.equals("Backup"))
                    new Backup(msg).start();
                else if (name.equals("Restore"))
                        //TODO: Restore
                        ;
                else
                    System.out.println("Wrong channels!");
            }
        }
    }

    public void send(byte[] message){
        channel.send(message);
    }
}
