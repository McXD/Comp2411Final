package entity;

public class SemesterRecord {
	public final Student student;
	public final Subject subject;
	public final int grade;
	public final String feedback;
	
	public SemesterRecord(Student student, Subject subject, int grade, String feedback) {
		this.student = student;
		this.subject = subject;
		this.grade = grade;
		this.feedback = feedback;
	}
}
