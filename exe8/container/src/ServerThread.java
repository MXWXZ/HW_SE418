import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket;
    private int kind;

    ServerThread(Socket socket, int kind) {
        this.socket = socket;
        this.kind = kind;
    }

    public void run() {
        while (true) {
            try {
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                if (kind == 1) {
                    Container.push(in.readUTF());
                } else if (in.readUTF().equals("pull")) {
                    out.writeUTF(Container.pop());
                }
            } catch (EOFException e) {
                System.out.println("Connection closed: " + socket.getRemoteSocketAddress());
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
