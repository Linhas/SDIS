import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by Bernardo on 16/03/2016.
 */
public class Peer implements Serializable  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Listener backupListener;
    public static Listener restoreListener;
    public static Listener controlListener;
    private static Listener tryListener;
    public static Database db;





    public static void main(String[] args) {
//    	 byte[] message = receiveMessage();


 //   	 ArrayList<String> splitMsg = Utils.splitMessage(message);
    	 //se restore
    	 //se space recclaim
    	 //se delete

   //     db = new Database();
        load();
        
        controlListener = new Listener("Control", "224.1.1.0", 1000);
        backupListener = new Listener("Backup", "224.1.1.1", 1001);
        restoreListener = new Listener("Restore", "224.1.1.2", 1002);
        tryListener = new Listener("Initiator", "localhost", 1234);
        

        controlListener.start();
        backupListener.start();
        restoreListener.start();
        tryListener.start();

        save();


    }

    
    private static void load() {

        ObjectInputStream load = null;
        Boolean newDatabase = false;

        try {
            load = new ObjectInputStream(new FileInputStream("db.dbs"));
        } catch (FileNotFoundException e) {
            db = new Database();
            newDatabase  = true;
        } catch (IOException e) {
            System.err.println("Error creating database.dbs");
            e.printStackTrace();
        }

        if (!newDatabase ) {

            try {
                assert load != null;
                db = (Database) load.readObject();
                System.out.println("Loaded Database");
            } catch (ClassNotFoundException e) {
                System.err.println("There is no database!");
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("Error loading information!");
                e.printStackTrace();
            }
        }
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

    public static Database getDb() {
        return db;
    }

    public synchronized static void save() {

        ObjectOutputStream save = null;

        try {
            save = new ObjectOutputStream(new FileOutputStream("db.dbs"));
        } catch (FileNotFoundException e) {
            System.err.println("Database.dbs not found!");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error creating db.dbs");
            e.printStackTrace();
        }

        try {
            assert save != null;
            save.writeObject(db);
        } catch (IOException e) {
            System.err.println("Error saving infomation");
            e.printStackTrace();
        }
    }
}
