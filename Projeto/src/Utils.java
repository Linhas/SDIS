import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Bernardo on 30/03/2016.
 */
public class Utils {

    public static ArrayList<String> splitMessage(byte[] message){
        ArrayList<String> split = new ArrayList<>();
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
            split.add(field.trim());
        }

        return split;
    }
}
