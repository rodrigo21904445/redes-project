import java.net.InetAddress;
import java.util.*;

public class Data {
	private List<String> white_list;
	private List<String> black_list;
	private HashMap<InetAddress, Boolean> hash_clients;

	public Data() {
		white_list = new ArrayList<>();
		black_list = new ArrayList<>();
		hash_clients = new HashMap<>();
	}

	public boolean checkClient(InetAddress ip) {
		return hash_clients.get(ip);
	}
}