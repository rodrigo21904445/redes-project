import jdk.internal.access.JavaIOFileDescriptorAccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class Data {
	private List<InetAddress> white_list;
	private List<InetAddress> black_list;
	private HashMap<InetAddress, Boolean> hash_clients;

	public Data() {
		white_list = new ArrayList<>();
		black_list = new ArrayList<>();
		hash_clients = new HashMap<>();
	}

	public boolean checkClient(InetAddress ip) {

		if(!hash_clients.containsKey(ip)) {
			return false;
		}

		return hash_clients.get(ip);
	}

	public boolean checkWhiteList(InetAddress ip) {

		readWhiteList();

		for(InetAddress addr: white_list) {
			if(addr.getHostAddress().equals(ip.getHostAddress())) {
				return true;
			}
		}

		return false;
	}

	public boolean checkBlackList(InetAddress ip) {

		readBlackList();

		for(InetAddress addr: black_list) {
			if(addr.getHostAddress().equals(ip.getHostAddress())) {
				return true;
			}
		}

		return false;
	}

	public void putHashClient(InetAddress ip, boolean state) {
		hash_clients.put(ip, state);
	}

	private void readBlackList() {
		try {
			File blackFile = new File("blackList.txt");
			Scanner scanner = new Scanner(blackFile);

			while(scanner.hasNextLine()) {
				String ip = scanner.nextLine();
				if(!black_list.contains(InetAddress.getByName(ip))) {
					black_list.add(InetAddress.getByName(ip));
				}
			}

			scanner.close();
		} catch(FileNotFoundException e) {
			System.out.println("Error: black list file not found");
		} catch (UnknownHostException i) {
			System.out.println("Error: reading ip on black list");
		}
	}

	private void readWhiteList() {
		try {
			File whiteFile = new File("whiteList.txt");
			Scanner scanner = new Scanner(whiteFile);

			while(scanner.hasNextLine()) {
				String ip = scanner.nextLine();
				if(!white_list.contains(InetAddress.getByName(ip))) {
					white_list.add(InetAddress.getByName(ip));
				}
			}

			scanner.close();
		} catch(FileNotFoundException e) {
			System.out.println("Error: white list file not found");
		} catch (UnknownHostException i) {
			System.out.println("Error: reading ip on white list");
		}
	}

	public void writeBlackList(InetAddress ip) {
		try {
			File blackFile = new File("blackList.txt");

			// tries to create new file in case it doesnt exist
			blackFile.createNewFile();

			FileWriter writer =  new FileWriter(blackFile);

			writer.write(ip.getHostAddress());

			writer.close();
		} catch (IOException e) {
			System.out.println("Error: write on black list file");
		}
	}

	public void writeWhiteList(InetAddress ip) {
		try {
			File whiteFile = new File("whiteList.txt");

			// tries to create new file in case it doesnt exist
			whiteFile.createNewFile();

			FileWriter writer =  new FileWriter(whiteFile);

			writer.write(ip.getHostAddress());

			writer.close();
		} catch (IOException e) {
			System.out.println("Error: write on white list file");
		}
	}
}