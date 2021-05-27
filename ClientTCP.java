import java.net.*;
import java.io.*;
import java.util.*;


public class ClientTCP {
	private Socket socket = null;
	private String addr;
	private int port;


	public ClientTCP(String addr, int port) {
		this.addr = addr;
		this.port = port;

		try {
			// establish connection 
			socket = new Socket(addr, port);
		
		} catch (IOException i){
			System.out.println("Waiting for connection...");
		}
	}

	public InetAddress getClientAddr() {
		try {
			// return local host
			return InetAddress.getLocalHost();
		} catch(IOException e) {
			System.out.println("Error: address of client");
		}

		return null;
	}

	public static void main(String[] args) {

	}
}