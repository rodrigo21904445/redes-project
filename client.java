import java.net.*;
import java.io.*;

public class client {

	private static void menu() {
		System.out.println("MENU CLIENTE\n\n");
		System.out.println("0  - Menu inicial");
		System.out.println("1  - Listar utilizadores online");
		System.out.println("2  - Enviar mensagem a um utilizador");
		System.out.println("3  - Enviar mensagem a todos os utilizadores");
		System.out.println("4  - Lista branca de utilizadores");
		System.out.println("5  - Lista negra de utilizadores");
		System.out.println("99 - Sair\n");
	}	



	public static void public static void main(String[] args) {
		private Scanner scanner = new Scanner(System.in);

		// menu cliente
		menu();
			
		do {
			String input = scanner.nextLine()
			
			switch(input) {
				case "0":
					menu();
					break;

				case "1":
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

		} while(input != "99");
		
	}
}