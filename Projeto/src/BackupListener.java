/**
 * Created by Bernardo on 24/03/2016.
 */
public class BackupListener extends Thread {
    Channel backupChannel;

    public BackupListener(String ip, Integer port){
        backupChannel = new Channel("Backup", ip, port);
    }

    public void run(){
        byte[] msg = null;

        while(true){
            System.out.println("Listening in IP: " + backupChannel.getAddress() + " and in Port: " + backupChannel.getPort());
            msg = backupChannel.receive();
            if (msg != null){
                System.out.println("received something!!!");
                new Backup(msg).start();
            }
        }
    }
}
