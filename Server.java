import java.net.*;
import java.io.*;

public class Server {

	public static void main(String[] args) {
		ServerSocket server = null;

		try {
			// start at port 7142
			server = new ServerSocket(7142);
			System.out.println("Server started on port 7142...");

		} catch (IOException i) {
			System.out.println("Erro");
		}

		// initialize socket
		Socket socket = null;

		while(true) {
			// waits for connection
			// receive socket
			try {
				if(server != null) {
					socket = server.accept();
				}
			} catch(IOException i) {
				System.out.println("Error: server error");
			}
		}
	}
}