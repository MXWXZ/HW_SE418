import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) {
        String ip = "127.0.0.1";
        int port = 8170;
        try {
            System.out.println("Connecting to: " + ip + ":" + port);
            Socket client = new Socket(ip, port);
            System.out.println("Connected!");
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            while (true) {
                out.writeUTF("pull");
                String ret = in.readUTF();
                if (ret.equals("null")) {
                    System.out.println("Container empty, sleep 2s...");
                    sleep(2000);
                } else {
                    System.out.println("Consume " + ret);
                    sleep(3000);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
