import java.net.*;
import java.io.*;

public class Server {

	public static class ClientThread implements Runnable{
		private Socket socket;
		
		public ClientThread(Socket socket){
			this.socket = socket;
		}

		public void run() {
			try {
				// read input stream
				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				// read output stream
				PrintStream output = new PrintStream(socket.getOutputStream());
				// put input in a buffer
				String buffer = input.readLine();
				// print the input
				output.println(buffer);
				// close socket
				socket.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	public static void main(String[] args) {
		try {
			// start at port 7142
			ServerSocket server = new ServerSocket(7142);
			System.out.println("Server started on port 7142...");

		} catch(IOException i) {
			System.out.println("Erro");
		}

		// initialize socket
		Socket socket = null;

		while(true) {
			// waits for connection
			// receive socket

			try {
				socket = server.accept();
					
			} catch(IOException i) {
				System.out.println("Erro");
			}

			// create new thread for every connection made
			//Thread thread = new Thread(new ClientThread(socket));
			
			// execute thread
			//thread.start();
		}
	}
}