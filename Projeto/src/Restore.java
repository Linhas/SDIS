import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Bernardo on 24/03/2016.
 */
public class Restore extends Thread {
    private byte[] message;
    ArrayList<String> header;

    public Restore(byte[] message){
        this.message = message;
    }

    public void run(){
    	

        String aux;

        //get header and aux
        aux = "";
        header = Utils.splitMessage(message);
        aux = header.get(0);
        header.remove(0);
        header = Utils.splitMessage(message);
        aux = header.get(0);
        header.remove(0);

        if (header.get(0).equals("GETCHUNK")){
            if (header.get(1).equals(Constants.VERSION)){
           
            	
                if ((Peer.getDb().findChunksSaved(header.get(3), Integer.parseInt(header.get(4))))) {
                	byte[] msg, msgHeader;
            		byte[] body;

            		String msgHeaderTemp = "CHUNK" + " " + Constants.VERSION + " 123 " + header.get(3) + " " + header.get(4) 
               	 + Constants.CRLF + Constants.CRLF;

            		msgHeader = msgHeaderTemp.getBytes(StandardCharsets.US_ASCII);
                	
                	body = Peer.getDb().returnFoundChunk(header.get(3), Integer.parseInt(header.get(4))).getData();
                	msg = new byte[msgHeader.length + body.length];

            		// copy data from one array into another:
            		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            		try {
            			outputStream.write(msgHeader);
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

            		message = outputStream.toByteArray();

            		
                     Random r = new Random();
                     int time = r.nextInt(401);
                     try {
                         sleep(time);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                //     Peer.getControlListener().send(message);
                     Peer.controlListener.channel.send(message);
                   //  Peer.getControlListener().send(msg.getBytes());
                     System.out.println("Sent: " + msg);
                }/*Se não existir*/
            }
        }




    }

}
