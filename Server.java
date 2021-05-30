import java.net.*;
import java.io.*;

public class Server {
	public static Data data;

	public static Data getData() {
		return data;
	}

	public static void main(String[] args) {
		data = new Data();
		ServerSocket server = null;

		try {
			// start at port 7142
			server = new ServerSocket(7142);
			System.out.println("Server started on port 7142...");

		} catch (IOException i) {
			System.out.println("Erro: starting server");
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

				// create new thread
				Thread thread = new ClientThread(socket, data);

				// run the thread
				thread.start();
			} catch(IOException i) {
				System.out.println("Error: server error");
			}
		}
	}
}