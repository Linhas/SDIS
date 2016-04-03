import java.io.IOException;

import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;


public abstract class Channel {
    protected InetAddress address;
    protected Integer port;

    public Channel(String ip, Integer port){
        this.port = port;

        try {
            this.address = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        System.out.println("IP is: " + ip);
    }

    public abstract byte[] receive();
    public abstract void send(byte[] msg);

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
}
