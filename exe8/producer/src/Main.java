import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) {
        String ip = "127.0.0.1";
        int port = 1926;
        try {
            System.out.println("Connecting to: " + ip + ":" + port);
            Socket client = new Socket(ip, port);
            System.out.println("Connected!");
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            for (int i = 1; ; ++i) {
                System.out.println("Produce " + client.getLocalPort() + "." + i);
                out.writeUTF(client.getLocalPort() + "." + i);
                sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
