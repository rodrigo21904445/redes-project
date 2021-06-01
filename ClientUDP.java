import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class ClientUDP {
    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];

    public ClientUDP() throws SocketException {
        socket = new DatagramSocket(9031);
    }

    public void run() {
        running = true;

        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                String received
                        = new String(packet.getData(), 0, packet.getLength());

                System.out.println(received);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }

    public static void main(String[] args) throws SocketException {
        ClientUDP client = new ClientUDP();
        client.run();
    }
}
