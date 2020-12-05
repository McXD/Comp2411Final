package entity;

public class SemesterRecord {
	public final Student student;
	public final Subject subject;
	public final int grade;
	
	public SemesterRecord(Student student, Subject subject, int grade) {
		this.student = student;
		this.subject = subject;
		this.grade = grade;
	}
}
