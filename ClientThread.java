import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.util.HashMap;

public class ClientThread extends Thread{
    private Socket socket = null;
    private Data data;
    private static DatagramPacket outPacket;

    public ClientThread(Socket socket, Data data) {
        this.socket = socket;
        this.data = data;
    }

    public static void sendMessageUDP(String messageToSend, InetAddress ip) throws IOException {
        EchoClientUdp clientUdp = new EchoClientUdp(ip.getHostName(), messageToSend);
        EchoClientUdp.sendEcho(messageToSend);
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

            if(messageToSend.equals("list_users_online")) {
                

                PrintStream output = new PrintStream(socket.getOutputStream(), true);
                String messageOut = ;

                output.println(messageOut);
            } else {
                sendMessageUDP(messageToSend, userIp);
            }

        } catch (IOException e) {
            System.out.println("Error: client is on black list");
        }

    }
}
