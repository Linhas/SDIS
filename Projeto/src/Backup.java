

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


/**
 * Created by Bernardo on 25/03/2016.
 */
public class Backup extends Thread{
    private byte[] message;
    private ArrayList<String> header;
    private byte[] body;
	private Chunk  chunk;


    public Backup(byte[] message){
        this.message = message;
        header = new ArrayList<>();
    }

    public void run() {

        String aux;

        //get header and aux
        aux = "";
        header = Utils.splitMessage(message);
        aux = header.get(0);
        header.remove(0);
        
        if (header.get(0).equals("PUTCHUNK")){
            if (header.get(1).equals(Constants.VERSION)){

                //get body (message-header)
                byte[] body = Arrays.copyOfRange(message, aux.length()+4, message.length);
                Chunk chunk = new Chunk(header.get(3), Integer.parseInt(header.get(4)), Integer.parseInt(header.get(5)), body);

                ManageChunk manageChunk = new ManageChunk(header.get(3), Integer.parseInt(header.get(5)));
                manageChunk.saveChunk(chunk);
                if (!(Peer.getDb().findSavedChunk(chunk.getFileId(), chunk.getChunkNo()))) {
                    Peer.getDb().addChunk(chunk);

                    String msg = "STORED" + " " + Constants.VERSION + " 123 " + header.get(3) + " " + header.get(4) + Constants.CRLF + Constants.CRLF;

                    Random r = new Random();
                    int time = r.nextInt(401);
                    try {
                        sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Peer.getControlListener().send(msg.getBytes());
                    System.out.println("Sent: " + msg);
                }


            }
        }
        else
            System.out.println("Invalid message!");
    }
    
    public void readMessage(DatagramPacket mess){
    	
    }
}
