package util;
import java.util.ArrayList;
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
		String[] mcAnswers = sheet.mcAnswer.split(" ");
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
			f.check(fbAnswers[loopVar++]);
		}
		
		return mcGrade + fbGrade;	
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
}
