import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

/**
 * Created by Bernardo on 03/04/2016.
 */
public class MulticastChannel extends Channel {
    private MulticastSocket mcSocket;


    public MulticastChannel(String ip, Integer port) {
        super(ip, port);
        try {
            mcSocket = new MulticastSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

            try {
                mcSocket.joinGroup(this.address);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void send(byte[] message){
        DatagramPacket packet = null;

        packet = new DatagramPacket(message, message.length, address, this.port);
        try {
            this.mcSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public byte[] receive(){
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        try {
            this.mcSocket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return packet.getData();

    }

    public void close(){
        this.mcSocket.close();
    }

    public MulticastSocket getMcSocket() {
        return mcSocket;
    }

    public void setMcSocket(MulticastSocket mcSocket) {
        this.mcSocket = mcSocket;
    }

}
