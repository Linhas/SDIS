import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by Bernardo on 26/03/2016.
 */
public class Sender {

    public static void main(String[] args) {
        //apenas para testar o envio de uma mensasgem!
        //daqui
        MulticastSocket mc = null;
        try {
            mc = new MulticastSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String send = "PUTCHUNK 1.0 id fileid chunkNo repdeg\r\n" + 32;

        byte[] buf = send.getBytes();
        DatagramPacket packet = null;
        try {
            packet = new DatagramPacket(buf, buf.length, InetAddress.getByName("225.1.1.1"), 1001);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            mc.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //até aqui

    }
}
