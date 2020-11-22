package ui;

import java.util.Scanner;

public class MainUI {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			try {
				System.out.println("Login as 1)Teacher 2)Student");
				int choice = Integer.parseInt(sc.nextLine());
				
				switch(choice) {
				case 1:
					new TeacherUI();
					break;
				case 2:
					new StudentUI();
					break;
				default:
					System.err.println("Invalid input: " + choice);
				}
			}catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
}
