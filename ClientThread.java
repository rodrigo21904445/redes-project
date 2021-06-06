import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientThread extends Thread{
    private Socket socket = null;
    private Data data;
    private static DatagramPacket outPacket;
    private ArrayList<InetAddress> list_users_online;

    public ClientThread(Socket socket, Data data) {
        this.socket = socket;
        this.data = data;
        list_users_online = new ArrayList<>();
    }

    public void sendMessageUDP(String messageToSend, InetAddress ip) throws IOException {
        EchoClientUdp clientUdp = new EchoClientUdp(ip.getHostName(), messageToSend);
        EchoClientUdp.sendEcho(messageToSend);
    }

    public void sendMessageUDPToAllUsers(String messageToSend) throws IOException {

        for (InetAddress ip: list_users_online) {
            EchoClientUdp clientUdp = new EchoClientUdp(ip.getHostName(), messageToSend);
            EchoClientUdp.sendEcho(messageToSend);
        }
    }


    @Override
    public void run() {
        // check is the client is on the black list
        // if it is, close the connection
        while(true) {
            try {

                while(socket == null) {
                    System.out.println("Waiting for connection");
                    Thread.sleep(10000);
                }

                InetAddress addr = socket.getInetAddress();

                if(data.checkBlackList(addr)) {
                    data.putHashClient(addr, false);
                    socket.close();
                    System.out.println("Closing socket...");
                    return;
                }

                data.putHashClient(addr, true);
                data.writeWhiteList(addr);

                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String messageIn = input.readLine();
                String messageToSend = messageIn.split("@")[1];

                if(messageToSend.equals("list_users_online")) {
                    String messageOut = "";

                    PrintStream output = new PrintStream(socket.getOutputStream(), true);

                    for(Map.Entry i: data.getHashClients().entrySet()) {
                        if(data.checkClient((InetAddress) i.getKey())) {
                            messageOut += ((InetAddress)i.getKey()).toString() + "@";
                            list_users_online.add((InetAddress)i.getKey());
                        }
                    }

                    output.println(messageOut);
                } else if(messageIn.split("@")[0].equals("messageToAllUsers")) {
                    sendMessageUDPToAllUsers(messageToSend);

                } else if(messageIn.split("@")[0].equals("desconnectClient")) {
                    socket.close();

                } else if(messageIn.split("@")[0].equals("whiteList")) {
                    String messageWhiteList = "";
                    PrintStream outputWhiteList = new PrintStream(socket.getOutputStream(), true);

                    for(InetAddress ip: data.getWhiteList()) {
                        messageWhiteList += ip.getHostAddress() + "@";
                    }

                    outputWhiteList.println(messageWhiteList);

                } else if(messageIn.split("@")[0].equals("blackList")) {
                    data.readBlackList();

                    String messageBlackList = "";
                    PrintStream outputBlackList = new PrintStream(socket.getOutputStream(), true);

                    for(InetAddress ip: data.getBlackList()) {
                        messageBlackList += ip.getHostAddress() + "@";
                    }

                    //System.out.println(messageBlackList);
                    outputBlackList.println(messageBlackList);

                } else{
                    InetAddress userIp = InetAddress.getByName(messageIn.split("@")[0]);
                    sendMessageUDP(messageToSend, userIp);
                }

            } catch (IOException  e) {
                System.out.println("Error: client is on black list");
            } catch (InterruptedException i) {
                System.out.println("Error: waiting for connection");
            }
        }
    }
}
