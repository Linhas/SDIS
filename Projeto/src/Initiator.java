import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Initiator extends Thread {
	private byte[] message;
	private ArrayList<String> argumentList;
	private ManageChunk fileToTreat;

	public Initiator(byte[] msg) {
		this.setMessage(msg);
		argumentList = new ArrayList<>();

	}

	public void run() {
		String aux;
		BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(message)));
		System.out.println("Hey jude, you are running");

		// get header
		aux = "";
		try {
			aux = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] rawHeader = aux.split("\\s+");

		for (String field : rawHeader) {
			argumentList.add(field.trim());
		}

		if (argumentList.get(0).equals("BACKUP")) {

			System.out.println(argumentList.get(1));
			fileToTreat = new ManageChunk(argumentList.get(1), Integer.parseInt(argumentList.get(2)));
			backupFunction(argumentList.get(1), Integer.parseInt(argumentList.get(2)));
			try {
				fileToTreat.getListOfChunks();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (argumentList.get(0).equals("a")) {

		} else if (argumentList.get(0).equals("b")) {

		} else if (argumentList.get(0).equals("BAcCKUP")) {

		}

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
	    				Peer.backupListener.setChunk(chunk);
	    				Peer.backupListener.sendMessage();
					}
	    		Peer.db.addFile(fileToTreat.getFileId(), fileToTreat.getFileName());
	    		
	    	}
	    }

	public byte[] getMessage() {
		return message;
	}

	public void setMessage(byte[] message) {
		this.message = message;
	}

}
