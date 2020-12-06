package util;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import entity.*;
import entity.question.FbQuestion;
import entity.question.FlQuestion;
import entity.question.McQuestion;

public class StudentUtil {
	
	public static AnswerSheet makeSheet(Student bywhom, Exam ofExam, String mcAnswer, String fbAnswer, String flAnswer) {
		return new AnswerSheet(bywhom, ofExam, flAnswer, flAnswer, flAnswer);
	}
	
	public static int autoGrade(AnswerSheet sheet, Paper paper) {
		//check mc answer
		//assume that answers are stored in this format: "A B C D E .."
		int mcGrade = 0;
		String[] mcAnswers = sheet.mcAnswer.split("/");
		ArrayList<McQuestion> checkMc = paper.getMcs();
		int loopVar = 0;
		for (McQuestion m : checkMc) {
			mcGrade += m.check(mcAnswers[loopVar++]);
		}
		
		//check fb answer
		//assume that answers are stored in this format: "cat, dog / fish, turtle"
		int fbGrade = 0;
		String[] fbAnswers = sheet.fbAnswer.split("/");
		ArrayList<FbQuestion> checkFb = paper.getFbs();
		loopVar = 0;
		for (FbQuestion f : checkFb) {
			fbGrade += f.check(fbAnswers[loopVar++]);
		}
		System.out.println("mcGrade = " + mcGrade);
		System.out.println("fbGrade = " + fbGrade);
		int temp = mcGrade + fbGrade;
		return temp>100 ? 100 : temp;	
	}
	
	public static AnswerSheet sitExam(Student student, Exam exam) {
		
		Paper paper = exam.withPaper;
		Scanner sc = new Scanner(System.in);
		
		StringBuilder sbMc = new StringBuilder();
		ArrayList<McQuestion> mcs = paper.getMcs();
		
		System.out.println("Multi-choice Questions");
		for (McQuestion mc : mcs) {
			System.out.println(mc.toString());
			sbMc.append(sc.nextLine() + " ");
		}
		
		System.out.println();
		
		System.out.println("Fill in blanks");
		StringBuilder sbFb = new StringBuilder();
		ArrayList<FbQuestion> fbs = paper.getFbs();
		for (FbQuestion fb : fbs) {
			System.out.println(fb.toString());
			sbFb.append(sc.nextLine() + "/");
		}

		System.out.println();
		
		System.out.println("Answer the Questions below");
		StringBuilder sbFl = new StringBuilder();
		ArrayList<FlQuestion> fls = paper.getFls();
		for (FlQuestion fl : fls) {
			System.out.println(fl.toString());
			sbFl.append(sc.nextLine() + "/");
		}
		
		sc.close();
		
		return new AnswerSheet(student,exam, sbMc.toString(), sbFb.toString(), sbFl.toString());
	}
	
	public static ArrayList<Exam> getUnsatExams(ArrayList<Exam> allExams){
		//in order
		
		LocalDateTime now = LocalDateTime.now();
		ArrayList<Exam> result = new ArrayList<Exam>();
		
		for (Exam e:allExams) {
			if (e.start.isAfter(now)) result.add(e);
		}
		
		result.sort(new Comparator<Exam>() {

			@Override
			public int compare(Exam arg0, Exam arg1) {
				if (arg0.start.isAfter(arg1.start)) return 1;
				else if (arg0.start.isBefore(arg1.start)) return -1;
				else return 0;
			}
			
		});
		
		return result;
	}
	
	public static Object[][] makeTableRocords(ArrayList<Exam> exams){
		Object[][] result = new Object[exams.size()][];
		for(int i=0; i<exams.size(); i++) {
			//examiner, subject, start, duration
			result[i] = new Object[] {exams.get(i).creator.name, exams.get(i).onSubject.toString(), 
					exams.get(i).start.toString(), exams.get(i).duration};
			}
		return result;
	}
	
	public static boolean canSitNow(LocalDateTime examTime) {
		LocalDateTime now = LocalDateTime.now();
		
		if (now.getYear() == examTime.getYear()) {
			if (now.getMonth() == examTime.getMonth()) {
				if (now.getHour() == examTime.getHour()) {
					if (now.getMinute()-examTime.getMinute() < 5 && now.getMinute() - examTime.getMinute() > -5) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public static Object[][] getRecordTable(ArrayList<StudentRecord> records){
		Object[][] result = new Object[records.size()][];
		int i = 0;
		for (StudentRecord r : records) {
			result[i++] = new Object[]{r.tid, r.eid, r.grade, r.feedback};
		}
		
		return result;
	}
	
	public static ArrayList<Semester> getAvailableSememsters() {
		ArrayList<Semester> result= new ArrayList<Semester>();
		LocalDateTime now = LocalDateTime.now();
		Semester cur = Semester.inSemester(now);
		for(Semester s : Semester.values()) {
			if (s.ordinal() <= cur.ordinal()) {
				result.add(s);
			}
		}
		return result;
	}
	
	public static Object[][] semeterRecord2Table(ArrayList<SemesterRecord> records) {
		Object[][] result = new Object[records.size()][];
		int i = 0;
		for (SemesterRecord r : records) {
			result[i++] = new Object[]{r.subject.sub_id, r.grade, CommonUtil.convertGrade(r.grade)};
		}
		return result;
	}
	
	public static String summarize(ArrayList<SemesterRecord> records) {
		if (records.size() == 0) return "No record found";
		
		records.sort(new Comparator<SemesterRecord>() {
			@Override
			public int compare(SemesterRecord arg0, SemesterRecord arg1) {
				return arg0.grade - arg1.grade;
			}
		});
		
		int avg = 0;
		for (SemesterRecord s : records) {
			avg += s.grade;
		}
		avg /= records.size();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("Best: " + records.get(0).grade + "\n");
		sb.append("Poorest: " + records.get(records.size()-1).grade + "\n");
		sb.append("Average: " + avg + "\n");
		sb.append("Median: " + records.get((records.size()-1)/2).grade + "\n");
		
		return sb.toString();
	}
}
