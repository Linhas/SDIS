import java.util.ArrayList;

/**
 * Created by Bernardo on 24/03/2016.
 */
public class Control extends Thread {
    private byte[] message;
    ArrayList<String> header;

    public Control(byte[] message){
        this.message = message;
        header = new ArrayList<>();
    }

    public void run(){

        //get header
        header = Utils.splitMessage(message);
        header.remove(0);

        if (header.get(0).equals("STORED")){
            if (header.get(1).equals(Constants.VERSION)){
                if(Peer.getDb().findChunk(header.get(3), Integer.parseInt(header.get(4)))){
                    Peer.getDb().incrementDeg(header.get(3), Integer.parseInt(header.get(4)));
                } else
                    System.out.println("Cant find chunk!");
            } else
                System.out.println("Wrong version");
        } else if (header.get(0).equals("DELETE")){
        	if(Peer.getDb().findFile(header.get(3))){
        		ManageChunk manChunk = new ManageChunk(header.get(3));
        		manChunk.deleteChunkFile(header.get(3));
        		Peer.getDb().removeFileSaved(header.get(3));
                
            } else
                System.out.println("Cant find chunk!");
        } else
            System.out.println("Invalid Message!!");
    }

}
