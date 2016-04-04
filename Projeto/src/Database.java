import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bernardo on 03/04/2016.
 */
public class Database implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Chunk> chunksSaved;
	private HashMap<String, ArrayList<Chunk>> backedUpFiles;

	private HashMap<String, String> backedUpFiles2;

	public Database() {
		chunksSaved = new ArrayList<>();
		backedUpFiles = new HashMap<>();
		backedUpFiles2 = new HashMap<>();
	}

	public synchronized void addFile(String fileHash, ArrayList<Chunk> chunks) {
		backedUpFiles.put(fileHash, chunks);
	}

	public synchronized ArrayList<Chunk> getBackedUpChunks(String fileHash) {
		return backedUpFiles.get(fileHash);
	}

	public synchronized void removeFile(String fileHash) {

		backedUpFiles.remove(fileHash);
		backedUpFiles2.remove(fileHash);
	}

	public synchronized void removeChunk(String fileId, int chunkNo) {
		for (int i = 0; i < chunksSaved.size(); i++) {
			if ((chunksSaved.get(i).getFileId().equals(fileId)) && (chunksSaved.get(i).getChunkNo() == chunkNo)) {
				chunksSaved.remove(i);
				break;
			}
		}
	}

	public synchronized void removeFileSaved(String fileId) {
		for (int i = 0; i < chunksSaved.size(); i++) {
			if (chunksSaved.get(i).getFileId().equals(fileId)) {
				chunksSaved.remove(i);
			}
		}
	}

	public synchronized int getNrOfChunksSaved(String fileHash) {
		return getBackedUpChunks(fileHash).size();
	}

	public synchronized void addChunk(Chunk chunk) {
		chunksSaved.add(chunk);
	}

	public ArrayList<Chunk> getChunksSaved() {
		return chunksSaved;
	}

	public synchronized boolean findChunk(String fileHash, int chunkNo) {
		ArrayList<Chunk> chunks = getBackedUpChunks(fileHash);

		for (int i = 0; i < chunks.size(); i++) {
			if (chunks.get(i).getChunkNo() == chunkNo)
				return true;
		}

		return false;
	}

	public synchronized boolean findChunksSaved(String fileHash, int chunkNo) {
		ArrayList<Chunk> all = chunksSaved;

		ArrayList<Chunk> chunks = null;

		for (Chunk chunk : all) {
			if (chunk.getFileId().equals(fileHash)) {
				chunks.add(chunk);
			}
		}

		for (int i = 0; i < chunks.size(); i++) {
			if (chunks.get(i).getChunkNo() == chunkNo)
				return true;
		}

		return false;
	}

	public synchronized Chunk returnFoundChunk(String fileHash, int chunkNo) {
		ArrayList<Chunk> all = chunksSaved;

		ArrayList<Chunk> chunks = null;

		for (Chunk chunk : all) {
			if (chunk.getFileId().equals(fileHash)) {
				chunks.add(chunk);
			}
		}

		Chunk answer = null;
		for (int i = 0; i < chunks.size(); i++) {
			if (chunks.get(i).getChunkNo() == chunkNo)
				return answer;
		}

		return answer;
	}

	public synchronized boolean findSavedChunk(String fileid, int chunkNo) {
		for (int i = 0; i < chunksSaved.size(); i++) {
			if (chunksSaved.get(i).getFileId() == fileid && chunksSaved.get(i).getChunkNo() == chunkNo) {
				return true;
			}
		}

		return false;

	}

	public synchronized int getChunkPos(String fileHash, int chunkNo) {
		ArrayList<Chunk> chunks = getBackedUpChunks(fileHash);

		for (int i = 0; i < chunks.size(); i++) {
			if (chunks.get(i).getChunkNo() == chunkNo)
				return i;
		}
		return -1;
	}

	public synchronized void incrementDeg(String fileHash, int chunkNo) {
		backedUpFiles.get(fileHash).stream().filter(chunk -> chunk.getChunkNo() == chunkNo)
				.forEach(Chunk::incCurrentRepDeg);
	}

	public void addFileMap(String fileHash, String fileName) {
		backedUpFiles2.put(fileHash, fileName);

	}

	public synchronized String getFileHash(String filename) {

		for (Map.Entry<String, String> entry : backedUpFiles2.entrySet()) {
			filename = entry.getValue();
			return entry.getKey();
		}
		return null;

	}

	public synchronized boolean findFile(String fileHash) {
		for (Chunk chunk : chunksSaved) {
			if (chunk.getFileId().equals(fileHash))
				return true;
		}
		return false;

	}

	// version 2
	/*
	 * public synchronized void removeFile2(String fileHash) {
	 * backedUpFiles2.remove(fileHash); }
	 */
	// version 2
	/*
	 * public synchronized void addFile(String fileHash, String filename) {
	 * backedUpFiles2.put(fileHash, filename); }
	 */
	// version 2
	/*
	 * public synchronized ArrayList<Chunk> getBackedUpChunks2(String fileHash)
	 * { ArrayList<Chunk> chunklist = null;
	 * 
	 * for(Chunk chunk : chunksSaved){ if (chunk.getFileId().equals(fileHash)){
	 * chunklist.add(chunk); } } return chunklist; }
	 */

	// version 2
	/*
	 * public synchronized void incrementDeg2(String fileHash, int chunkNo) {
	 * 
	 * for(int i = 0; i < chunksSaved.size(); i++){
	 * 
	 * if(chunksSaved.get(i).getFileId() == fileHash &&
	 * chunksSaved.get(i).getChunkNo() == chunkNo){
	 * chunksSaved.get(i).incCurrentRepDeg(); } }
	 * 
	 * }
	 */

	// version 2
	/*
	 * public synchronized boolean findChunk2(String fileHash, int chunkNo) {
	 * ArrayList<Chunk> chunks = chunksSaved;
	 * 
	 * for (int i = 0; i < chunks.size(); i++) { if (chunks.get(i).getChunkNo()
	 * == chunkNo) return true; }
	 * 
	 * return false; }
	 */
	// version 2
	/*
	 * public synchronized String getFileHash(String filename){
	 * for(Map.Entry<String, String> entry : backedUpFiles2.entrySet()){ String
	 * file = entry.getValue(); // ArrayList<Chunk> que tem chunks return
	 * entry.getKey(); } return null;}
	 */
	// version 2
	/*
	 * public synchronized int getNrOfChunksSaved2(String fileHash) { return
	 * chunksSaved.size(); }
	 */

}
