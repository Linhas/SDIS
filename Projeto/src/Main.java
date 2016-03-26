import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by Bernardo on 16/03/2016.
 */
public class Main {

    private static ControlListener controlListener;
    private static BackupListener backupListener;
    private static RestoreListener restoreListener;


    public static void main(String[] args) {

        //controlListener = new ControlListener("224.0.0.0", 1000);
        backupListener = new BackupListener("225.1.1.1", 1001);
        //restoreListener = new RestoreListener("224.0.0.2", 1002);

        //controlListener.start();
        backupListener.start();
        //restoreListener.start();


    }

    public static RestoreListener getRestoreListener() {
        return restoreListener;
    }

    public static ControlListener getControlListener() {
        return controlListener;
    }

    public static BackupListener getBackupListener() {
        return backupListener;
    }
}
