import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

public class Initiator extends Thread {
	private byte[] message;
	
	public Initiator(byte[] msg) {
		this.setMessage(msg);
	}

	public void run(){
		String aux;
		BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(message)));
		System.out.println("Hey jude, you are running");
	}

	public byte[] getMessage() {
		return message;
	}

	public void setMessage(byte[] message) {
		this.message = message;
	}



}
