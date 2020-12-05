package entity;

public class ExamResultRecord {
	public final Student student;
	public final int grade;
	public final String eid;
	
	public ExamResultRecord(String eid, Student student, int grade) {
		this.eid = eid;
		this.student = student;
		this.grade = grade;
	}
}
