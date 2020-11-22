package ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import connector.TeacherLoginSession;
import entity.AnswerSheet;
import entity.Exam;
import exception.IdentityException;

public class TeacherUI {
	TeacherLoginSession tls;
	
	public TeacherUI() {
			try {
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(System.in);
				System.out.print("Teacher ID: ");
				String tid = sc.nextLine();
				System.out.print("Password: ");
				String pw = sc.nextLine();
				tls = new TeacherLoginSession(tid,pw);
			
				int choice;
				
				while (true) {
					System.out.print("1 to schedule exam, 2 to grade papers, 3 to exit: ");
					
					//For some reason, it doesn't block
					choice = Integer.parseInt(sc.nextLine());
					
					try {
						switch(choice) {
						case 1:
							makeExam();
							break;
						case 2:
							grade();
							break;
						case 3:
							break;
						}
						
						if (choice == 3) break;
					}catch (SQLException se) {
						System.err.println(se.getMessage());
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
					
			}catch (IdentityException e) {
					System.err.println(e.getMessage());
			}catch (Exception ea) {
					ea.printStackTrace();
					System.err.println("Unknown Error");
			}
	}
		
	
	public void makeExam() {
		Exam examMade = util.TeacherUtil.makeExam(tls.getTeacher());
		tls.setExam(examMade);
	}
	
	public void grade() throws SQLException {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.print("Input exam id: ");
		String eid = sc.nextLine();
		
		Exam toGrade = new Exam(tls.getTeacher(), eid, null, null, null, null,0);
		ArrayList<AnswerSheet> sheets = tls.getSheetForExam(toGrade);
		
		for (AnswerSheet as : sheets) {
			Object[] toFeed = util.TeacherUtil.grade(as);
			tls.grade(as, (Integer) toFeed[0], (String) toFeed[1]);
		}
	}
	
	public static void main(String[] args) {
		new TeacherUI();
	}
}
