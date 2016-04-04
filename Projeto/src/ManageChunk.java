import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;

public class ManageChunk {

	private String fileName;

	private int repDegree;
	private String fileId;
	private byte[] fileH;

	private File file;
	private static long fileSize;
	private int numberOfChunks;

	private int id;

	public ManageChunk(String fileName, int repDegree) {
		this.repDegree = repDegree;
		this.fileName = fileName;
		file = new File(fileName);
	}
/*
 * Counts number of chunks in a file by dividing file
 */
	public boolean countNumberOfChunks(){
		
		if(!file.isFile()){
			System.out.printf("Something is wrong with this file %s Are you sure it exits?", fileName);
			return false;
		}
	fileSize = file.length();
	numberOfChunks = (int) (fileSize/Constants.CHUNKSIZE) +1;
	return true;
	}
	
/*
 * Get list of chunks so they can be sent
 */
	public ArrayList<Chunk> getListOfChunks() throws IOException{
		
		ArrayList<Chunk> totalChunks = new ArrayList<Chunk>(numberOfChunks);
		FileInputStream fileStream = null;
		byte[] data;
		
		int totalSize = 0;
		int chunkSize = 0;
		
		try {
			fileStream = new FileInputStream(fileName);
			
			for(int i = 0; i < numberOfChunks; i++){
				if(i == numberOfChunks - 1){chunkSize = (int) (fileSize - totalSize);}
				else {chunkSize = Constants.CHUNKSIZE;}
				
				if(chunkSize == 0){
					//(String idFile, int chunkn, int repliDeg, byte[] data)
					Chunk chunk = new Chunk(fileId, i, repDegree, null);
					totalChunks.add(chunk);
				}
				else{
					data = new byte[chunkSize];
					int dataSize = fileStream.read(data);
					Chunk chunk = new Chunk(fileId, i, repDegree, data);
					totalChunks.add(chunk);
					totalSize += dataSize;
				}
			} 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		return totalChunks;
	}

/*
 *  Creates fileId with Sha256 used to identify file 
 */
	public void sha256() {
	    try{
	    	
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        //to identify will use fileName and fileSize
	        String inForId = fileName + " " + fileSize;
	        
	       
	        digest.update(inForId.getBytes("UTF-8"));
	        fileH = digest.digest();
	      //convert byte array to hexadecimal see: StackOverflow
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < fileH.length; i++) {
	            String hex = Integer.toHexString(0xff & fileH[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	         fileId = hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}
/*
 * Get File Id	
 */
	public String getFileId(){
		return fileId;
	}
/*
 * SAVE CHUNKS, definir diretorio??
 	*
 */
	public void saveChunk(Chunk chunk){
		String filename = chunk.getFileId() + "//" + chunk.getChunkNo() + ".part";
		File targetFile = new File(filename);
		File parent = targetFile.getParentFile();
		if(!parent.exists() && !parent.mkdirs()){
			parent.mkdir();
		}



		Path file = Paths.get(filename);
		if(chunk.getData().length > 0)
		{
		try {
			Files.write(file, chunk.getData());
			//FileOutputStream writer = new FileOutputStream(filechunk);
			//writer.write(chunk.getData());
			//writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		
	} 

public String getFileName(){
	return fileName;
}

}
