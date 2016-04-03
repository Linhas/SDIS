import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;


/**
 * Created by Bernardo on 16/03/2016.
 */
public class Peer {

    private static Listener backupListener;
    private static Listener restoreListener;
    private static Listener controlListener;

    private static Listener tryListener;





    public static void main(String[] args) {
//    	 byte[] message = receiveMessage();


 //   	 ArrayList<String> splitMsg = Utils.splitMessage(message);
    	 //se restore
    	 //se space recclaim
    	 //se delete


        controlListener = new Listener("Control", "224.1.1.0", 1000);
        backupListener = new Listener("Backup", "224.1.1.1", 1001);
        restoreListener = new Listener("Restore", "224.1.1.2", 1002);
        tryListener = new Listener("Initiator", "localhost", 1234);
        

        controlListener.start();
        backupListener.start();
        restoreListener.start();
        tryListener.start();



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

    public static Listener getTryListener() {
        return tryListener;
    }
}
