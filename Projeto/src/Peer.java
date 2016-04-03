import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;


/**
 * Created by Bernardo on 16/03/2016.
 */
public class Peer {

    private static Listener backupListener, restoreListener, controlListener, tryListener;



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
/*
    public static byte[] receiveMessage(){
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
        return packet.getData();
    }*/
}
