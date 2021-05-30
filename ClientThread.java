import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

public class ClientThread extends Thread{
    private Socket socket = null;
    private Data data;

    public ClientThread(Socket socket, Data data) {
        this.socket = socket;
        this.data = data;
    }


    @Override
    public void run() {
        // check is the client is on the black list
        // if it is, close the connection
        try {
            if(!data.checkClient(socket.getInetAddress())) {
                socket.close();
            }

        } catch (IOException e) {
            System.out.println("Error: client is on black list");
        }

    }
}
