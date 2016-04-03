import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.nio.charset.StandardCharsets;
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
			
			System.out.println("Hey jude, don't let you down");
			System.out.println(argumentList.get(1));
			System.out.println(argumentList.get(2));
			fileToTreat = new ManageChunk(argumentList.get(1), Integer.parseInt(argumentList.get(2)));
			backupFunction(argumentList.get(1), Integer.parseInt(argumentList.get(2)));
			try {
				fileToTreat.getListOfChunks();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (argumentList.get(0).equals("RESTORE")) {
			//TODO: mandar para o MC

		} else if (argumentList.get(0).equals("b")) {

		} else if (argumentList.get(0).equals("BAcCKUP")) {

		}

	}
	
	 public void backupFunction(String fileN, int repDegree){
	    	//if file as already been backed up
	    	
	    	ManageChunk fileToTreat = new ManageChunk(fileN,repDegree);
			if(fileToTreat.countNumberOfChunks()){
	    		fileToTreat.sha256();
	    		
	    		Chunk[] chunks = null;
				try {
					chunks = fileToTreat.getListOfChunks();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	    		if(chunks.length > 0){
	    			for(Chunk chunk : chunks) {
	    				sendMessage(chunk);
					}
	    		Peer.db.addFile(fileToTreat.getFileId(), new ArrayList<>());
	    		
	    			
	    		}
	    		else
	    		{
	    			System.out.println("This wont do. There are no chunks to Backup");
	    			}
	    	}
	    }

	public byte[] getMessage() {
		return message;
	}

	
    public void sendMessage(Chunk chunk) {
		System.out.println("Sending chunk: " + chunk.getChunkNo());
		byte[] msg, msgHeader;
	    byte[] body;

		
		String msgHeaderTemp = "PUTCHUNK " + " " + Constants.VERSION + " " + chunk.getFileId() + " " 
							+ chunk.getChunkNo() + " " + chunk.getReplicationDeg() + " " 
							+ Constants.CRLF + Constants.CRLF;
		//porque a porra do arraycopy tava a falhar
		msgHeader = msgHeaderTemp.getBytes(StandardCharsets.US_ASCII);
		body = chunk.getData();
		
		msg = new byte[msgHeader.length + body.length];
		
	
		// copy data from one array into another:
		

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
		
		try {
			outputStream.write( msgHeader );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outputStream.write( body );
			System.out.println(body.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		message = outputStream.toByteArray( );

		DatagramPacket dataPacket = new DatagramPacket(message, message.length, Peer.backupListener.getAddress(), Peer.backupListener.getPort());
		
		Peer.controlListener.channel.send(message);
	
	}
	
	public void setMessage(byte[] message) {
		this.message = message;
	}

}
