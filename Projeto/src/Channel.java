import java.io.IOException;

import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class Channel {
    private InetAddress address;
    private Integer port;
    private MulticastSocket mcSocket;
    private String name;


    public Channel(String name, String ip, Integer port){
        this.port = port;
        this.name = name;

        try {
            this.address = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        System.out.println("IP is: " + ip);
        try {
            mcSocket = new MulticastSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mcSocket.joinGroup(this.address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String message){
        DatagramPacket packet = null;
        byte[] buf = message.getBytes();

        packet = new DatagramPacket(buf, buf.length, address, 4446);


        try {
            this.mcSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public byte[] receive(){
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        try {
            this.mcSocket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return packet.getData();

    }

    public void close(){
        this.mcSocket.close();
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public MulticastSocket getMcSocket() {
        return mcSocket;
    }

    public void setMcSocket(MulticastSocket mcSocket) {
        this.mcSocket = mcSocket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
