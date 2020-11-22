package ui;

import java.util.ArrayList;
import java.util.Scanner;

import connector.StudentLoginSession;
import entity.AnswerSheet;
import entity.Exam;
import entity.StudentRecord;
import exception.IdentityException;

public class StudentUI {
	StudentLoginSession sls;
	
	public StudentUI() {
		while (true) {
			try {
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(System.in);
				System.out.print("Student ID: ");
				String sid = sc.nextLine();
				System.out.print("Password: ");
				String pw = sc.nextLine();
				sls = new StudentLoginSession(sid,pw);
				
				int choice;
				
				while (true) {
					System.out.print("1 to sit exam, 2 to view exam records, 3 to exit: ");
					choice = Integer.parseInt(sc.nextLine());
					
					try {
						switch(choice) {
						case 1:
							sitExam();
							break;
						case 2:
							viewRecords();
							break;
						case 3:
							break;
						}
						
						if (choice == 3) break;
					}catch (Exception e) {
						System.err.println("Something Unexpected Happened");
					}
					
				}

				
			} catch (IdentityException e) {
				System.err.println(e.getMessage());
			} catch (Exception e) {
				System.err.println("Unknown Error");
			}
		}
	}
	
	public void sitExam() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		ArrayList<Exam> exams = sls.getAllExams();
		
		int loopVar = 1;
		for (Exam e : exams) {
			System.out.println(loopVar++ + ": " + e.toString());
		}
		
		System.out.print("Choose Exam number to sit: ");
		Exam toSit = exams.get(sc.nextInt()-1);
		
		AnswerSheet as = util.StudentUtil.sitExam(sls.getStudent(), toSit);
		
		int autoGrade = util.StudentUtil.autoGrade(as, toSit.withPaper);
		System.out.println("Grade for MC and FB: " + autoGrade);
		
		sls.storeAnswerAndAntoGrade(as, autoGrade);
	}
	
	public void viewRecords() {
		ArrayList<StudentRecord> srs = sls.checkRecord();
		for (StudentRecord sr : srs) {
			System.out.println(sr.toString());
		}
	}
	
	public static void main(String[] args) {
		new StudentUI();
	}
}
