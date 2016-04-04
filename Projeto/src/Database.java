import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Bernardo on 03/04/2016.
 */
public class Database implements Serializable   {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Chunk> chunksSaved;
    private HashMap<String, ArrayList<Chunk>> backedUpFiles;

    //TODO: Mudar a base de dados.
    //TODO: A base de dados deve guardar pelo menos o n� de chunksSaved para que o Peer que pede o restore saiba como passar os chunksSaved para um ficheiro.


    public Database(){
        chunksSaved = new ArrayList<>();
        backedUpFiles = new HashMap<>();
    }

    public synchronized void addFile(String fileHash, ArrayList<Chunk> chunks){
        backedUpFiles.put(fileHash, chunks);
    }

    public synchronized ArrayList<Chunk> getBackedUpChunks(String fileHash){
        return backedUpFiles.get(fileHash);
    }

    public synchronized void removeFile(String fileHash){
        backedUpFiles.remove(fileHash);
    }

    public synchronized void removeChunk(String fileId, int chunkNo){
        for(int i = 0; i < chunksSaved.size(); i++){
            if ((chunksSaved.get(i).getFileId().equals(fileId)) && (chunksSaved.get(i).getChunkNo() == chunkNo)){
                chunksSaved.remove(i);
                break;
            }
        }
    }

    public synchronized int getNrOfChunksSaved(String fileHash){
        return getBackedUpChunks(fileHash).size();
    }

    public synchronized void addChunk(Chunk chunk) {
        chunksSaved.add(chunk);
    }

    public ArrayList<Chunk> getChunksSaved() {
        return chunksSaved;
    }

    public synchronized boolean findChunk(String fileHash, int chunkNo){
        boolean found = false;
        ArrayList<Chunk> chunks = getBackedUpChunks(fileHash);

        for(int i = 0; i<chunks.size(); i++){
            if (chunks.get(i).getChunkNo() == chunkNo)
                return true;
        }

        return false;
    }

    public synchronized boolean findSavedChunk(String fileid, int chunkNo){
    	for (int i = 0; i < chunksSaved.size(); i++){
    		if (chunksSaved.get(i).getFileId() == fileid && chunksSaved.get(i).getChunkNo() == chunkNo ){
    	    	return true;
    		}
    	}

		return false;

    }
}
