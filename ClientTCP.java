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
		
		} catch(UnknownHostException e) {
			System.out.println("Waiting for connection...");
			
		} catch (IOException i){
			System.out.println("Waiting for connection...");
		}
	}

	public String getClientAddr() {
		return addr;
	}

}