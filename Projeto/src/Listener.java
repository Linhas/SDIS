import java.net.InetAddress;

/**
 * Created by Bernardo on 24/03/2016.
 */
public class Listener extends Thread {
	protected Channel channel;
	private String name;

	public Listener(String name, String ip, Integer port) {

		this.name = name;
		if (name.equals("Initiator"))
            channel = new UDPChannel(ip, port);
        else
            channel = new MulticastChannel(ip, port);
	}

	public void run() {
		byte[] msg;
		System.out.println(
				name + " Channel is listening on IP: " + channel.getAddress() + " and in Port: " + channel.getPort());

		while (true) {
			msg = channel.receive();
			if (msg != null) {
				System.out.println("Received: " + new String(msg));

				if (name.equals("Control"))
					Peer.getExecutor().submit(new Control(msg));
				else if (name.equals("Backup"))
					Peer.getExecutor().submit(new Backup(msg));
				else if (name.equals("Restore"))
					// TODO: Restore
					;
				else if (name.equals("Initiator"))
					Peer.getExecutor().submit(new Initiator(msg));
				else
					System.out.println("Wrong channels!");
			}
		}
	}

	public void send(byte[] message) {
		channel.send(message);
	}

	public void sendMessage() {
		// TODO Auto-generated method stub
		
	}

	 public InetAddress getAddress() {
	        return channel.getAddress();
	    }
	 public int getPort(){
		 
		 return channel.getPort();
	 }
	public void setChunk(Chunk chunk) {
		// TODO Auto-generated method stub
		
	}

}
