import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Arrays;



// comando java TestApp 225.1.1.1:1001 Backup chunk.txt 1

public class TestApp {

	private static String ipAddress;
	private static int port;
	private static String command;
	private static String operationSpecs;

    private static MulticastSocket mcSocket;

	public static void main(String argv[]) throws Exception {

		if (argv.length < 3) {
			System.out.println("Usage: <peer_ap> <sub_protocol> <opnd_1> <opnd_2>");
		}
		splitPeerApp(argv[0]);
		checkSubProtocol(argv[1]);
		concat(argv);
		

		try {
            mcSocket = new MulticastSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
		sendMessage();

		// <peer_ap> <sub_protocol> <opnd_1> <opnd_2>
	}

	private static void concat(String[] argv) {
		StringBuffer result = new StringBuffer();
		for (int i = 2; i < argv.length; i++) {
			result.append(argv[i]);
			result.append(" ");
		}
		operationSpecs = result.toString();
	}

	private static void checkSubProtocol(String com) {
		command = com.toUpperCase();
		if (command.equals("BACKUP") || command.equals("RESTORE") || command.equals("DELETE")
				|| command.equals("SPACE_RECLAIM")) {

		} else {
			System.out.println("Error in command input. Commads accepted: BACKUP, RESTORE, DELETE and SPACE_RECLAIM");
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
		System.out.println("(>^.^)> ");
		System.out.printf("\nTestApp sending command %s, %s to IP address %s - Port number %d\n", command, operationSpecs, ipAddress, port);
		
		DatagramPacket packet = null;
		byte[] message = new byte[256];
		message = (command + " " + operationSpecs).getBytes();
		
        try {
			packet = new DatagramPacket(message, message.length, InetAddress.getByName(ipAddress), port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        try {
            mcSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(message));
	}
}
