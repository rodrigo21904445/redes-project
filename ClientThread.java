import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

            InetAddress addr = socket.getInetAddress();

            if(data.checkBlackList(addr)) {
                data.putHashClient(addr, false);
                socket.close();
                System.out.println("Closing socket...");
                return;
            }

            data.putHashClient(addr, true);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String messageIn = input.readLine();

            InetAddress userIp = InetAddress.getByName(messageIn.split("@")[0]);
            String messageToSend = messageIn.split("@")[1];
            ClientUDP clientUDP = new ClientUDP(userIp);
            clientUDP.sendMessage(messageToSend);


        } catch (IOException e) {
            System.out.println("Error: client is on black list");
        }

    }
}
