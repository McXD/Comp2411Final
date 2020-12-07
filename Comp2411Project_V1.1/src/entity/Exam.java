package entity;

import java.time.LocalDateTime;

public class Exam {
	public final Teacher creator;
	public final String eid;
	public final Class0 forClass;
	public final Subject onSubject;
	public final Paper withPaper;
	public final LocalDateTime start;
	public final int duration;
	
	public Exam(Teacher creator, String eid, Class0 forClass, Subject onSubject, Paper withPaper,
						LocalDateTime start, int duration) throws IllegalArgumentException{
		this.creator = creator;
		if (eid.length() > 4) throw new IllegalArgumentException("Invalid Exam ID");
		this.eid = eid;
		this.forClass = forClass;
		this.onSubject = onSubject;
		this.withPaper = withPaper;
		this.start = start;
		this.duration = duration;
	}
	
	@Override
	public String toString() {
		return String.format("Class: %s\t Subject: %s\t Start: %s\t Duration:%d mins",
				forClass.cid, onSubject.sub_id, start.toString(), duration);
	}
}
