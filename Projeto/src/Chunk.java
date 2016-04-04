import java.util.ArrayList;

/**
 * Created by Cavaleiro on 23/03/2016.
 */
public class Chunk {
	// The max size of each chunk is 64KByte
	public static int MAX_SIZE = 64000;
	//Each chunk is identified by the pair (fileId, chunkNo)
	
	private int chunkNo;
	private String fileId;
	private int id;
	private int currentRepD;
	
	private int replicationDeg;
	
	//actual replication degree of a chunk may be different from the one that is desired
	private int actualReplicationDeg;
	
	private byte[] data;
	
	//should know where chunks are stored? Se calhar uma String para sabermos quem eles sao?
	private ArrayList<String> peers;

	
	public Chunk(String idFile, int chunkn, int repliDeg, byte[] data){
		setFileId(idFile);
		setChunkNo(chunkn);
		setReplicationDeg(repliDeg);
		setPeers(new ArrayList<String>());
		this.currentRepD = 0;
		
		
		this.setData(data);
	}
	
	public void incCurrentRepDeg(){
		currentRepD++;
	}
	

	public void decCurrentRepDeg(){
		currentRepD--;
	}
	
	public int getCurrentRepDeg(){
		return currentRepD;
	}
	public int getChunkNo() {
		return chunkNo;
	}


	public void setChunkNo(int chunkNo) {
		this.chunkNo = chunkNo;
	}


	public String getFileId() {
		return fileId;
	}


	public void setFileId(String fileId) {
		this.fileId = fileId;
	}


	public int getReplicationDeg() {
		return replicationDeg;
	}


	public void setReplicationDeg(int replicationDeg) {
		this.replicationDeg = replicationDeg;
	}


	public int getActualReplicationDeg() {
		return actualReplicationDeg;
	}


	public void setActualReplicationDeg(int actualReplicationDeg) {
		this.actualReplicationDeg = actualReplicationDeg;
	}


	public byte[] getData() {
		return data;
	}


	public void setData(byte[] data) {
		this.data = data;
	}


	public ArrayList<String> getPeers() {
		return peers;
	}


	public void setPeers(ArrayList<String> peers) {
		this.peers = peers;
	}
	
	public void addPeers(String peer){
		peers.add(peer);
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	
	
}
