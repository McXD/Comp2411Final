package entity;

public class AnswerSheet {
	public final Student owner;
	public final Exam ofExam;
	public final String mcAnswer;
	public final String fbAnswer;
	public final String flAnswer;
	
	public AnswerSheet(Student owner, Exam ofExam, String mcAnswer, String fbAnswer, String flAnswer) {
		this.owner = owner;
		this.ofExam = ofExam;
		this.mcAnswer = mcAnswer;
		this.fbAnswer = fbAnswer;
		this.flAnswer = flAnswer;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("MC: ");
		sb.append(mcAnswer + "\n");
		sb.append("FB: ");
		sb.append(fbAnswer + "\n");
		sb.append("FL: ");
		sb.append(flAnswer + "\n");
		
		return sb.toString();
	}
	
	public String toString(int dull) {
		String[] flAnswers = flAnswer.split("/");
		StringBuilder sb = new StringBuilder();
		int index = 1;
		for (String s : flAnswers) {
			sb.append(index + ". " + s + "\n\n");
		}
		
		return sb.toString();
	}
}
