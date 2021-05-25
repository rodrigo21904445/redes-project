import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
	private static ArrayList<ClientTCP> list_clients = new ArrayList<>();
	private static String localhost = "127.0.0.1";
		

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

	private static void usersList(ArrayList list) {
		
		//list all connected clients
		for(int i = 0; i < list.size(); i++) {
			System.out.println(i + " - " + list.get(i) + "\n");
		}
	}


	public static void main(String[] args) {
		ClientTCP client = new ClientTCP(localhost, 7142);

		// client is connected
		list_clients.add(client);

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
					System.out.println("Utilizadores online:\n");
					usersList(list_clients);
					break;

				case "2":
					break;

				case "3":
					break;

				case "4":
					break;

				case "5":
					break;
			}

			input = scanner.nextLine();

		} while(input != "99");
		
	}
}