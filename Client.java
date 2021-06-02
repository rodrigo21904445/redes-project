import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
	//private static ArrayList<ClientTCP> list_clients = new ArrayList<>();
	private static ArrayList<InetAddress> list_clients_online = new ArrayList<>();

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

	private static void usersList(ArrayList<InetAddress> list, Socket socket, ClientTCP user) {

		try{
			PrintStream output = new PrintStream(socket.getOutputStream(), true);
			String messageOut = user.getClientAddr().getHostAddress() + "@list_users_online";

			output.println(messageOut);

			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String messageIn = input.readLine();

			for(String message: messageIn.split("@")) {
				list.add(InetAddress.getByName(message.split("/")[1]));
			}

		} catch(IOException e) {
			System.out.println("No users online");
		}
	}

	private static void printListUsersOnline() {
		for(int i = 0; i < list_clients_online.size(); i++) {
			// print host address
			System.out.println(i + " - " + list_clients_online.get(i).getHostAddress() + "\n");
		}
	}

	private static void sendMessageToUser(Socket socket, InetAddress user, String message) {

		try{
			PrintStream output = new PrintStream(socket.getOutputStream(), true);
			String messageOut = user.getHostAddress() + "@" + message;

			output.println(messageOut);
			System.out.println("OK, mensagem enviada a " + user.getHostAddress() + "\n");
		} catch(IOException e) {
			System.out.println("Error: sending message to user");
		}
	}

	private static void sendMessageToAllUsers(Socket socket, String message) {

		try {
			PrintStream output = new PrintStream(socket.getOutputStream(), true);
			String messageOut = "messageToAllUsers@" + message;

			output.println(messageOut);
			System.out.println("OK, mensagem enviada a todos os utilizadores\n");
		} catch(IOException e) {
			System.out.println("Error: sending message to all users");
		}
	}

	public static void main(String[] args) throws IOException {
		ClientTCP client = new ClientTCP(InetAddress.getLocalHost().getHostAddress(), 7142);

		usersList(list_clients_online, client.getSocket(), client);

		Scanner scanner = new Scanner(System.in);

		// menu client
		menu();
		System.out.println("Opção?");
		String input = scanner.nextLine();

		do {
			switch(input) {
				case "0":
					menu();
				 	break;

				case "1":
					System.out.println("Utilizadores online:");
					printListUsersOnline();
					break;

				case "2":
					System.out.println("Que utilizador?");
					int user = Integer.parseInt(scanner.nextLine());
					System.out.println("Qual é a mensagem?");
					String messageToUser = scanner.nextLine();
					try {
						sendMessageToUser(client.getSocket(), list_clients_online.get(user), messageToUser);
					} catch (IndexOutOfBoundsException e) {
						System.out.println("Error: User doesnt exist");
					}

					break;

				case "3":
					System.out.println("Qual é a mensagem?");
					String messageToAllUsers = scanner.nextLine();
					sendMessageToAllUsers(client.getSocket(), messageToAllUsers);
					break;

				case "4":
					break;

				case "5":
					break;

				case "99":
					PrintStream output = new PrintStream(client.getSocket().getOutputStream(), true);
					output.println("desconnectClient@");
					System.out.println("Client Desconectado...");
					System.exit(0);
			}

			System.out.println("Opção?");
			input = scanner.nextLine();

		} while(!input.equals("99"));

		scanner.close();
	}
}