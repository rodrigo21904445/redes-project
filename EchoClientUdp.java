import java.io.IOException;
import java.net.*;

public class EchoClientUdp {
    private static DatagramSocket socket;
    private static InetAddress address;
    private static String message;

    private static byte[] buf;

    public EchoClientUdp(String address, String message) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        this.address = InetAddress.getByName(address);
        this.message = message;
    }

    public static void sendEcho(String msg) throws IOException {
        buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 9031);
        socket.send(packet);
    }
}