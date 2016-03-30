import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by Bernardo on 16/03/2016.
 */
public class Peer {

    private static Listener backupListener, restoreListener, controlListener;



    public static void main(String[] args) {

        String message = receiveMessage();
        System.out.println(message);


        controlListener = new Listener("Control", "224.1.1.0", 1000);
        backupListener = new Listener("Backup", "224.1.1.1", 1001);
        restoreListener = new Listener("Restore", "224.1.1.2", 1002);

        controlListener.start();
        backupListener.start();
        restoreListener.start();


    }

    public static Listener getRestoreListener() {
        return restoreListener;
    }

    public static Listener getControlListener() {
        return controlListener;
    }

    public static Listener getBackupListener() {
        return backupListener;
    }

    public static String receiveMessage(){
        int port =  1234; //Integer.parseInt(args[1]);
        byte[] receiveData = new byte[256];

        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String message = new String(packet.getData());

        return message;
    }
}
