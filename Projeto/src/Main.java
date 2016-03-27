/**
 * Created by Bernardo on 16/03/2016.
 */
public class Main {

    private static Listener backupListener, restoreListener, controlListener;


    public static void main(String[] args) {

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
}
