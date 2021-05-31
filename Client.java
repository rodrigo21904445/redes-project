import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
	private static ArrayList<ClientTCP> list_clients = new ArrayList<>();

	private static void menu() {
		System.out.println("MENU CLIENTE\n");
		System.out.println("0  - Menu inicial");
		System.out.println("1  - Listar utilizadores online");
		System.out.println("2  - Enviar mensagem a um utilizador");
		System.out.println("3  - Enviar mensagem a todos os utilizadores");
		System.out.println("4  - Lista branca de utilizadores");
		System.out.println("5  - Lista negra de utilizadores");
		System.out.println("99 - Sair\n");
	}	

	private static void usersList(ArrayList<ClientTCP> list) {
		
		//list all connected clients
		for(int i = 0; i < list.size(); i++) {
			// print host address
			System.out.println(i + " - " + list.get(i).getClientAddr().getHostAddress() + "\n");
		}
	}

	private static void sendMessageToUser(Socket socket, ClientTCP user, String message) {

		try{
			PrintStream output = new PrintStream(socket.getOutputStream(), true);
			String messageOut = user.getClientAddr().getHostAddress() + "@" + message;

			output.println(messageOut);
		} catch(IOException e) {
			System.out.println("Error: sending message to user");
		}
	}

	private static void sendMessageToAllUsers(Socket socket, String message) {

		try {
			PrintStream output = new PrintStream(socket.getOutputStream(), true);

			output.println(message);
		} catch(IOException e) {
			System.out.println("Error: sending message to all users");
		}
	}

	public static void main(String[] args) throws UnknownHostException, SocketException {
		ClientTCP client = new ClientTCP(InetAddress.getLocalHost().getHostAddress(), 7142);
		ClientUDP clientUDP = new ClientUDP(InetAddress.getLocalHost());

		try {
			clientUDP.receiveMessage();
		} catch (IOException e) {
			System.out.println("Error: receiving message by udp");
		}

		// client is connected
		if(client.getClientAddr() != null) {
			list_clients.add(client);
		}

		Scanner scanner = new Scanner(System.in);

		// menu client
		menu();
		String input = scanner.nextLine();

		do {
			switch(input) {
				case "0":
					menu();
				 	break;

				case "1":
					System.out.println("Utilizadores online:");
					usersList(list_clients);
					break;

				case "2":
					System.out.println("Que utilizador?\n");
					int user = Integer.parseInt(scanner.nextLine());
					System.out.println("Qual é a mensagem?\n");
					String messageToUser = scanner.nextLine();
					sendMessageToUser(client.getSocket(), list_clients.get(user), messageToUser);
					break;

				case "3":
					System.out.println("Qual é a mensagem?\n");
					String messageToAllUsers = scanner.nextLine();
					sendMessageToAllUsers(client.getSocket(), messageToAllUsers);
					break;

				case "4":
					break;

				case "5":
					break;
			}

			input = scanner.nextLine();

		} while(input != "99");

		scanner.close();
	}
}