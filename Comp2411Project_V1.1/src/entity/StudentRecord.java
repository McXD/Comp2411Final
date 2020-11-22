package entity;

public class StudentRecord {
	public final String tid;
	public final String eid;
	public final String feedback;
	public final int grade;
	
	public StudentRecord(String tid, String eid, String feedback, int grade) {
		this.tid = tid;
		this.eid = eid;
		this.feedback = feedback;
		this.grade = grade;
	}
	
	@Override
	public String toString() {
		return String.format("%10s %6d %s",eid, grade, feedback);
	}
}
