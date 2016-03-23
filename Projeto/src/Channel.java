import java.io.IOException;

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
            mcSocket = new MulticastSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            address = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            mcSocket.joinGroup(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
