import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Bernardo on 25/03/2016.
 */
public class Backup extends Thread{
    private byte[] message;
    private ArrayList<String> header;

    public Backup(byte[] message){
        this.message = message;
    }

    public void run() {
        System.out.println(new String(this.message));

        String aux;
        BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(message)));

        aux = "";
        try {
            aux = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] rawHeader = aux.split("\\s+");

        for(String field : rawHeader){
            header.add(field.trim());
        }
    }


}
