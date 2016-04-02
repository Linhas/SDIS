import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;



// comando java TestApp 225.1.1.1:1001 Backup chunk.txt 1

public class TestApp {
	private static String ipAddress;
	private static int port;
	private static String command;
	private static String operationSpecs;
	private static DatagramSocket socket = null;


	public static void main(String argv[]) throws Exception {



		if (argv.length < 3) {
			System.out.println("Usage: <peer_ap> <sub_protocol> <opnd_1> <opnd_2>");
		}
		splitPeerApp(argv[0]);


		checkSubProtocol(argv[1]);
		concat(argv);
		

		sendMessage();
		
		
		//waitForAnswer(command);


		
	}
	/*
	private static void waitForAnswer(Integer command2) {
		switch (command2){
		case 1:
			backupListener = new Listener("Backup", ipAddress, port);
			backupListener.start();
			boolean waiting = true;
			while(waiting){
				System.out.println("He did receive");
				waiting = false;
			}
			System.exit(0);
			//PUTCHUNK <Version> <SenderId> <FileId> <ChunkNo> <ReplicationDeg> <CRLF><CRLF><Body>
			//ficar a ouvir o backup
			break;
		case 2:
			restoreListener = new Listener("Restore", "224.1.1.2", 1002);
	        restoreListener.start();
			//GETCHUNK <Version> <SenderId> <FileId> <ChunkNo> <CRLF><CRLF>
			// Ficar a ouvir o restore
			break;
		case 3:
			//DELETE <Version> <SenderId> <FileId> <CRLF><CRLF>
			break;
		case 4:
			//REMOVED <Version> <SenderId> <FileId> <ChunkNo> <CRLF><CRLF>
			break;
		
		}
		
	}
	*/

	private static void concat(String[] argv) {
		StringBuffer result = new StringBuffer();
		for (int i = 2; i < argv.length; i++) {
			result.append(argv[i]);
			result.append(" ");
		}
		operationSpecs = result.toString();
	}

	private static void checkSubProtocol(String com) {
		String comma= com.toUpperCase();
		if (comma.equals("BACKUP")){
			command = "BACKUP";
		} else if (comma.equals("RESTORE")){
			command = "RESTORE";
		} else if (comma.equals("DELETE")){
			command = "DELETE";
		} else if(comma.equals("SPACE_RECLAIM")) {
			command = "SPACE_RECLAIM";
		} else {
			System.out.println("Error in command input. Commads accepted: BACKUP, RESTORE, DELETE and SPACE_RECLAIM.");
		}
	}

	private static void splitPeerApp(String string) {
		String[] array;
		try {
			array = string.split(":");
			ipAddress = array[0];
			port = Integer.parseInt(array[1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Access point format must be: <IP address>:<port number>");
		}
	}

	private static void sendMessage() {


		//Se tiver de ir para um MDB multicast data channel isto também está mal
		//eheh
		System.out.println("(>^.^)> ");
		System.out.printf("\nTestApp sending command %s, %s to IP address %s - Port number %d\n", command, operationSpecs, ipAddress, port);

		try {
			socket	= new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}

		InetAddress address = null;
		try {
			address = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}



		byte[] message = new byte[256];

		message = (command + " " + operationSpecs + Constants.CRLF).getBytes();

		DatagramPacket packet = new DatagramPacket(message, message.length, address, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Message sent!");







        System.out.println(new String(message, StandardCharsets.UTF_8));
	}
	
	
}