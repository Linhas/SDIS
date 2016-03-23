import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by Bernardo on 23/03/2016.
 */
public class Sender {
    public Sender() {
        MulticastSocket mc = null;
        try {
            mc = new MulticastSocket(4446);

        } catch (IOException e) {
            e.printStackTrace();
        }
        DatagramPacket packet = null;
        String message = "hello";
        byte[] buf = message.getBytes();


        try {
            packet = new DatagramPacket(buf, buf.length, InetAddress.getByName("224.0.0.0"), 4446);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            mc.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mc.close();
    }
}
