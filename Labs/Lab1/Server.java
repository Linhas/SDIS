import java.io.*;
import java.net.*;
import java.util.regex.*;

class UDPServer {
    public static void main(String args[]) throws Exception{
        if (args.length != 1){
            System.out.println("Wrong usage! \nUsage: Server <port>");
            System.exit(-1);
        }

        int port = Integer.parseInt(args[0]);

        DatagramSocket serverSocket = new DatagramSocket(port);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        System.out.println("Server receiving on port " + port);

        while(true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String message = new String( receivePacket.getData());
            System.out.println("RECEIVED: " + message);
            String rec[] = message.split(" ");
            String reply = "";
            if (rec[0].toUpperCase().equals("REGISTER")){
                if (rec.length != 3)
                    reply = "Wrong REGISTER command usage! \nUsage: REGISTER <plate number> <owner name>";
                else
                    reply = Integer.toString(register(rec[1], rec[2].trim()));
            }
            else if (rec[0].toUpperCase().equals("LOOKUP")){
                if (rec.length != 2)
                    reply = "Wrong LOOKUP command usage! \nUsage: LOOKUP <plate number>";
                else
                    reply = lookup(rec[1].trim());
            }




            InetAddress IPAddress = receivePacket.getAddress();
            port = receivePacket.getPort();
            sendData = reply.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
            System.out.println("Sent reply:" + reply);
        }
    }

    private static int register(String plate, String name) throws IOException {
        int lines = -1;
        if (!lookup(plate).equals("NOT_FOUND")){
            System.out.println("Error! Plate already exists!");
            return lines;
        }
        else{
            FileWriter out = null;
            BufferedReader in = null;
            try {
                out = new FileWriter("db.txt", true);
                out.write(plate + "\n");
                out.write(name + "\n");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error occured while trying to write to DB.");
            } finally {
                out.close();
            }

            try {
                in = new BufferedReader(new InputStreamReader(new FileInputStream("db.txt")));
                lines = 0;
                String s;
                while((s = in.readLine()) != null) {
                    System.out.println(s);
                    lines++;
                }

                lines /= 2;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Could not open db file");
            } finally {
                in.close();
            }


        }

        return lines;




    }

    private static String lookup(String plate) throws IOException {
        String name = "NOT_FOUND";
        BufferedReader in = null;
        boolean found = false;
        System.out.println("Plate: " + plate);


            File f = new File("db.txt");
            if (f.isFile()){
                try {
                    in = new BufferedReader(new InputStreamReader(new FileInputStream("db.txt")));

                    while(!found) {

                        String line1 = in.readLine();//plate number
                        if(line1 == null)
                            break;

                        String line2 = in.readLine();//
                        if(plate.equals(line1)) {
                            name = line2;
                            found = true;
                            break;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Could not open db file or IOException");
                }
                finally {
                    in.close();
                }
            }
        return name;
    }

}