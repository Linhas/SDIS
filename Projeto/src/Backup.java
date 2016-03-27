

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Bernardo on 25/03/2016.
 */
public class Backup extends Thread{
    private byte[] message;
    private ArrayList<String> header;
    private byte[] body;

    public Backup(byte[] message){
        this.message = message;
        header = new ArrayList<>();
    }

    public void run() {

        String aux;
        BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(message)));

        //get header
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

        //get body (message-header)
        System.out.println("TESTING!!!!!");

        byte[] body = Arrays.copyOfRange(message, aux.length() + 4, message.length);

        System.out.println("body: "+ new String(body));

        System.out.println("end of testing!!!!");



        if (header.get(0).equals("PUTCHUNK")){
            if (header.get(1).equals(Constants.VERSION)){

            }

        }
        else
            System.out.println("Invalid message!");

    }


}
