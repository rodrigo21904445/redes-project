import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
	private static ArrayList<ClientTCP> list_clients = new ArrayList<>();
	private static ArrayList<InetAddress> list_clients_online = new ArrayList<>();

	private static void menu() {
		System.out.println("MENU CLIENTE\n");
		System.out.println("0  - Menu inicial");
		System.out.println("1  - Listar utilizadores online");
		System.out.println("2  - Enviar mensagem a um utilizador");
		System.out.println("3  - Enviar mensagem a todos os utilizadores");
		System.out.println("4  - Lista branca de utilizadores");
		System.out.println("5  - Lista negra de utilizadores");
		System.out.println("99 - Sair");
	}	

	private static void usersList(ArrayList<InetAddress> list, Socket socket, ClientTCP user) {

		try{
			PrintStream output = new PrintStream(socket.getOutputStream(), true);
			String messageOut = user.getClientAddr().getHostAddress() + "@list_users_online";

			output.println(messageOut);

			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String messageIn = input.readLine();

			// client is connected
			list_clients_online.add(InetAddress.getByName(messageIn));

		} catch(IOException e) {
			System.out.println("Error: list users online");
		}

		//list all connected clients
		for(int i = 0; i < list.size(); i++) {
			// print host address
			System.out.println(i + " - " + list.get(i).getHostAddress() + "\n");
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

		Scanner scanner = new Scanner(System.in);

		// menu client
		menu();
		String input = scanner.nextLine();

		do {
			// menu client
			menu();
			switch(input) {
				case "0":
					menu();
				 	break;

				case "1":
					System.out.println("Utilizadores online:");
					usersList(list_clients_online, client.getSocket(), client);
					break;

				case "2":
					System.out.println("Que utilizador?");
					int user = Integer.parseInt(scanner.nextLine());
					System.out.println("Qual é a mensagem?");
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