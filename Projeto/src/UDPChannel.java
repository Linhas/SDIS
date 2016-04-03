import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by Bernardo on 03/04/2016.
 */
public class UDPChannel extends Channel {

    private DatagramSocket dSocket;


    public UDPChannel(String ip, Integer port) {
        super(ip, port);

        try {
            dSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }


    }

    public void send(byte[] message){
        System.out.println("Impossible to send messages in UDP");
    }

    public byte[] receive() {
        byte[] buf = new byte[1024];

        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        try {
            dSocket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet.getData();
    }
}
