import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Server extends Thread {
    private ServerSocket serverSocket;
    private int kind;

    Server(int port, int kind) {
        this.kind = kind;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        System.out.println("Waiting client at: " + serverSocket.getLocalSocketAddress() + " ...");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Client come in: " + socket.getRemoteSocketAddress());
                ServerThread t = new ServerThread(socket, kind);
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Container.setWall(10);
        Container.setTimeout(10000);

        new Server(1926, 1).start();
        new Server(8170, 2).start();
    }
}
