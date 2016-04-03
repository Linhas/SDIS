import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;


/**
 * Created by Bernardo on 16/03/2016.
 */
public class Peer implements Serializable  {

    public static Listener backupListener;
    public static Listener restoreListener;
    public static Listener controlListener;
    private static Listener tryListener;
    private static Database db;





    public static void main(String[] args) {
//    	 byte[] message = receiveMessage();


 //   	 ArrayList<String> splitMsg = Utils.splitMessage(message);
    	 //se restore
    	 //se space recclaim
    	 //se delete

        db = new Database();

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

    public static Listener getRestoreListener() {
        return restoreListener;
    }

    public static Listener getControlListener() {
        return controlListener;
    }

    public static Listener getBackupListener() {
        return backupListener;
    }

    public void backupFunction(String fileN, int repDegree){
    	//if file as already been backed up

    	ManageChunk fileToTreat = new ManageChunk(fileN,repDegree );

    	if(fileToTreat.countNumberOfChunks()){
    		fileToTreat.sha256();

    		Chunk[] chunks = null;
			try {
				chunks = fileToTreat.getListOfChunks();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		if(chunks.length == 0){
    			System.out.println("This wont do. There are no chunks to Backup");
    		}
    		else
    			for(Chunk chunk : chunks) {
    				backupListener.setChunk(chunk);
    				backupListener.sendMessage();
				}
    		db.addFile(fileToTreat.getFileId(), fileToTreat.getFileName());

    	}
    }

    public static Listener getTryListener() {
        return tryListener;
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
            System.err.println("Error saving database");
            e.printStackTrace();
        }
    }
}
