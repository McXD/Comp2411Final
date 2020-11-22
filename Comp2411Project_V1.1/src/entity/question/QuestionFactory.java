package entity.question;

public class QuestionFactory {
	public static McQuestion genMc(String text, String optionA, String optionB, String optionC, String optionD,
			String answer, int point, boolean flag) {
		return new McQuestion(text,optionA,optionB,optionC,optionD,answer,point,flag);
	}
	
	public static FbQuestion genFb(String text, String answer, int point, boolean flag) {
		return new FbQuestion(text, answer, point, flag);
	}
	
	public static FlQuestion genFl(String text, int point, boolean flag) {
		return new FlQuestion(text, point, flag);
	}
}
