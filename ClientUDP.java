import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class ClientUDP {
    private final int port = 9031;
    private DatagramSocket socket;
    private InetAddress host;
    private DatagramPacket inPacket, outPacket;

    public ClientUDP(InetAddress host) throws SocketException{
        this.host = host;
        socket = new DatagramSocket(port);
    }

    public void sendMessage(String message) throws IOException{
        try {
            // create new packet
            outPacket = new DatagramPacket(message.getBytes(), message.length(), host, port);

            // send packet
            socket.send(outPacket);
        } catch(IOException e) {
            System.out.println("Error: sending datagram packet");
        } finally {
            socket.close();
        }
    }

    public void receiveMessage() throws IOException {
        try {
            String messageReceived;

            // create new buffer to receive message
            byte[] buffer = new byte[256];

            // create new packet to receive
            inPacket = new DatagramPacket(buffer, buffer.length);

            // receive packet
            socket.receive(inPacket);

            // print message
            messageReceived = new String(inPacket.getData(), 0, inPacket.getLength());
            System.out.println(messageReceived);
        } catch(IOException e) {
            System.out.println("Error: receiving datagram packet");
        } finally {
            socket.close();
        }
    }
}
