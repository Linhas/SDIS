import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Bernardo on 03/04/2016.
 */
public class Database implements Serializable   {
    private ArrayList<Chunk> chunks;
    private HashMap<String, String> files;

    //TODO: Mudar a base de dados.
    //TODO: A base de dados deve guardar pelo menos o nº de chunks para que o Peer que pede o restore saiba como passar os chunks para um ficheiro.


    public Database(){
        chunks = new ArrayList<>();
        files = new HashMap<>();
    }

    public synchronized void addFile(String fileHash, String filename){
        files.put(fileHash, filename);
    }

    public synchronized String getFile(String fileHash){
        return files.get(fileHash);
    }

    public synchronized void removeFile(String fileHash){
        files.remove(fileHash);
    }

    public synchronized void removeChunk(String fileId, int chunkNo){
        for(int i = 0; i < chunks.size(); i++){
            if ((chunks.get(i).getFileId().equals(fileId)) && (chunks.get(i).getChunkNo() == chunkNo)){
                chunks.remove(i);
                break;
            }
        }
    }

    public synchronized void addChunk(Chunk chunk) {
        chunks.add(chunk);
    }

    public ArrayList<Chunk> getChunks() {
        return chunks;
    }
}
