import java.io.*;
import java.net.*;
class UDPClient {
    public static void main(String args[]) throws Exception {
        if (args.length < 4 || args.length > 5){
            System.out.print("Wrong usage! \nUsage: Client <host_name> <port_number> <oper> <opnd>*");
            System.exit(-1);
        }
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName(hostname);

        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        String message = "";

        if(args[2].toUpperCase().equals("REGISTER"))
            message = args[2] + " " + args[3] + " " + args[4];
        else if(args[2].toUpperCase().equals("LOOKUP")){
            message = args[2] + " " + args[3];}
        else{
            System.out.println("Error! Wrong program usage!");
            System.exit(-1);
        }
        sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        clientSocket.send(sendPacket);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        String serverReply = new String(receivePacket.getData());
        System.out.println("FROM SERVER:" + serverReply);
        clientSocket.close();
    }
}