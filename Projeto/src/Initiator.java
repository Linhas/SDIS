import javax.xml.bind.SchemaOutputResolver;
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
			fileToTreat = new ManageChunk(argumentList.get(1), Integer.parseInt(argumentList.get(2)));
			backupFunction(argumentList.get(1), Integer.parseInt(argumentList.get(2)));
			try {
				fileToTreat.getListOfChunks();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (argumentList.get(0).equals("RESTORE")) {
			//TODO: mandar para o MC
			   


		} else if (argumentList.get(0).equals("b")) {

		} else if (argumentList.get(0).equals("BAcCKUP")) {

		}

	}

	public void backupFunction(String fileN, int repDegree) {
		ManageChunk fileToTreat = new ManageChunk(fileN, repDegree);
		int tries;

		if (fileToTreat.countNumberOfChunks()) {
			fileToTreat.sha256();

			ArrayList<Chunk> chunks = null;

			try {
				chunks = fileToTreat.getListOfChunks();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Peer.getDb().addFile(fileToTreat.getFileId(), chunks);


			if (chunks.size() > 0) {
				for (Chunk chunk : chunks) {
					tries = 0;
					int time = 500;
					while (Peer.getDb().getBackedUpChunks(chunk.getFileId()).get(chunk.getChunkNo()).getCurrentRepDeg() < repDegree && tries < Constants.TRIES) {
						sendMessageBackup(chunk);

						try {
							sleep(time);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						time += time;
						tries++;
					}

					if (Peer.getDb().getBackedUpChunks(chunk.getFileId()).get(chunk.getChunkNo()).getCurrentRepDeg() < repDegree){
						System.out.println("TIMETOUT!!");
					}

				}
			} else {
				System.out.println("This wont do. There are no chunks to Backup");
			}
		}
	}


	
    public void sendMessageBackup(Chunk chunk) {
		System.out.println("Sending chunk: " + chunk.getChunkNo());
		byte[] msg, msgHeader;
	    byte[] body;

		
		String msgHeaderTemp = "PUTCHUNK" + " " + Constants.VERSION + " " + this.getId() + " "+ chunk.getFileId() + " "
							+ chunk.getChunkNo() + " " + chunk.getReplicationDeg() + " " 
							+ Constants.CRLF + Constants.CRLF;

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
			outputStream.write(body);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		message = outputStream.toByteArray( );
		
		Peer.backupListener.channel.send(message);
	
	}
	
	public void setMessage(byte[] message) {
		this.message = message;
	}

}
