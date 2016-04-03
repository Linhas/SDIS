import java.util.ArrayList;
import java.util.Arrays;

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
        header = Utils.splitMessage(message);
        String aux = header.get(0);
        header.remove(0);

        if (header.get(0).equals("CHUNK")){
            if (header.get(1).equals(Constants.VERSION)){
                byte[] body = Arrays.copyOfRange(message, aux.length()+4, message.length);
                Chunk chunk = new Chunk(Integer.parseInt(header.get(2)), header.get(3), Integer.parseInt(header.get(4)), Integer.parseInt(header.get(5)), body);



            }
        }




    }

}
