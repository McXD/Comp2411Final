package util;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import entity.AnswerSheet;
import entity.Class0;
import entity.Exam;
import entity.ExamInfo;
import entity.ExamResultRecord;
import entity.Paper;
import entity.Semester;
import entity.Subject;
import entity.Teacher;
import entity.question.FbQuestion;
import entity.question.FlQuestion;
import entity.question.McQuestion;
import entity.question.QuestionFactory;
public class TeacherUtil {
	
	public static McQuestion designMc() {
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Input text: ");
		String text = sc.nextLine();
		
		System.out.print("Input Option A: ");
		String optionA = sc.nextLine();
		System.out.print("Input Option B: ");
		String optionB = sc.nextLine();
		System.out.print("Input Option C: ");
		String optionC = sc.nextLine();
		System.out.print("Input Option D: ");
		String optionD = sc.nextLine();
		
		System.out.print("Input Answer: ");
		String answer = sc.nextLine();
		
		System.out.print("Input point: ");
		int point = Integer.parseInt(sc.nextLine());
		
		System.out.print("Madatory?[Y/N]");
		boolean flag = (sc.nextLine().toLowerCase().compareTo("Y") == 0);
		
		return QuestionFactory.genMc(text,optionA,optionB,optionC,optionD,answer,point,flag);
	}
	
	public static FbQuestion designFb() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Input text: ");
		String text = sc.nextLine();
		
		System.out.print("Input Answer: ");
		String answer = sc.nextLine();
		
		System.out.print("Input point: ");
		int point = Integer.parseInt(sc.nextLine());
		
		System.out.print("Madatory?[Y/N]");
		boolean flag = (sc.nextLine().toLowerCase().compareTo("Y") == 0);
		
		return QuestionFactory.genFb(text,answer, point, flag);
	}
	
	public static FlQuestion designFl() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Input text: ");
		String text = sc.nextLine();
		
		System.out.print("Input point: ");
		int point = Integer.parseInt(sc.nextLine());
		
		System.out.print("Madatory?[Y/N]");
		boolean flag = (sc.nextLine().toLowerCase().compareTo("Y") == 0);

		return QuestionFactory.genFl(text, point, flag);
	}
	
	public static Exam makeExam(Teacher who) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Input Exam id: ");
		String eid = sc.nextLine();
		
		System.out.print("Input Class id: ");
		Class0 forClass  = new Class0(sc.nextLine());
		
		System.out.print("Input Subject id: ");
		Subject onSubject = new Subject(sc.nextLine());
		
		System.out.println("Input Paper id: ");
		Paper paper = new Paper(sc.nextLine());
		
		System.out.println("Input Exam date: ");
		boolean invalid = true;
		LocalDateTime date = null;
		while (invalid) {
			try {
				date = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm:ss a"));
				invalid = false;
			}catch(DateTimeException e) {
				System.err.println(e.getMessage());
				System.err.println("Input date should like this: " + "28-Apr-2015 01:25:00 PM");
			}
		}
		
		System.out.println("Input Duration(in minute): ");
		int dura = Integer.parseInt(sc.nextLine());
		
		while (true) {
			System.out.println("Choose question type to design (1:MC, 2:FB, 3:FL, 4:quit)");
			int choice = Integer.parseInt(sc.nextLine());
			
			switch(choice) {
			
			case 1:
				paper.addMc(designMc());
				break;
			case 2:
				paper.addFb(designFb());
				break;
			case 3:
				paper.addFl(designFl());
				break;
			case 4:
				break;
			}
			
			if (choice == 4) break;
		}
		
		sc.close();
		return new Exam(who, eid, forClass, onSubject, paper, date, dura);
		
	}

	
	public static Object[] grade(AnswerSheet sheet) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		System.out.println(sheet.toString());
		System.out.print("Grading: ");
		int grade = Integer.parseInt(sc.nextLine());
		System.out.print("Feedback: ");
		String feedback = sc.nextLine();
		
		return new Object[] {grade,feedback};
	}
	
	public static void addQuestionToPaper(Object question, Paper paper) {
		if (!(question instanceof McQuestion || question instanceof FbQuestion || question instanceof FlQuestion)) {
			throw new IllegalArgumentException();
		}
		
		if (question instanceof McQuestion) {
			McQuestion mcNew = (McQuestion) question;
			paper.addMc(mcNew);
		}else if (question instanceof FbQuestion) {
			FbQuestion fbNew = (FbQuestion) question;
			paper.addFb(fbNew);
		}else {
			FlQuestion flNew = (FlQuestion) question;
			paper.addFl(flNew);
		}
	}
	
	public static boolean checkAuthority(Teacher teacher, Class0 clas, Subject sub) {
		return teacher.canSetExam(clas, sub);
	}
	
	public static int checkDuplicity(ArrayList<ExamInfo> already, ExamInfo toSet) {
		for (ExamInfo i:already) {
			if (i.eid.compareTo(toSet.eid) == 0) return 1; //duplicated eid
			
			if (i.cid.compareTo(toSet.cid) == 0 && i.subid.compareTo(toSet.subid) == 0) {
				//check if they are in the same semester
				int startYear = Integer.parseInt(i.cid.substring(0, 2)) + 2000;
				if (Semester.inSemester(i.start, startYear) == Semester.inSemester(toSet.start, startYear)) return 2; //double sets
			}
		}
		return 0;
	}
	
	/**
	 * Get all finished exams for current semester
	 * @param exams
	 * @return
	 */
	public static ArrayList<Exam> getAllFinishedExams(ArrayList<Exam> exams){
		ArrayList<Exam> result = new ArrayList<Exam>();
		LocalDateTime now = LocalDateTime.now();
		for (Exam e:exams) {
			//both finished and in current semester
			int startYear = Integer.parseInt(e.forClass.cid.substring(0, 2)) + 2000;
			if (e.start.plusMinutes(e.duration).isBefore(now) && Semester.inSemester(LocalDateTime.now(), startYear) == Semester.inSemester(e.start, startYear)) result.add(e);
		}
		return result;
	}
	
	public static String getExamInfoForTeacher(Exam exam) {
		return String.format("%-20s %-20s %-20s", exam.eid, exam.forClass, exam.onSubject);
	}
	
	public static String summarizePaper(Paper paper) {
		return String.format("You have designed %d MCs, %d FBs, %d FLs" , paper.getMcs().size(), paper.getFbs().size(), paper.getFls().size());
	}
	
	public static Object[][] record2Table(ArrayList<ExamResultRecord> records){
		Object[][] result = new Object[records.size()][];
		int ptr = 0;
		for (ExamResultRecord e : records) {
			result[ptr++] = new String[] {
					e.student.sid,
					e.student.name,
					e.grade + "",
					CommonUtil.convertGrade(e.grade)
			};
		}
		return result;
	}

	public static double[] summarize0(ArrayList<ExamResultRecord> records) {
		//already sorted
		if (records.size() == 0) return new double[] {0,0,0,0};
		
		
		int avg = 0;
		for (ExamResultRecord r : records) {
			avg += r.grade;
		}
		avg /= records.size();
		
		return new double[] {records.get(0).grade, records.get(records.size()-1).grade, avg, records.get((records.size()-1)/2).grade};
	}
	
}
